package earth.terrarium.pastel.data;

import com.google.gson.JsonElement;
import earth.terrarium.pastel.blocks.decoration.CardinalFacingBlock;
import earth.terrarium.pastel.blocks.decoration.PylonBlock;
import earth.terrarium.pastel.blocks.idols.IdolBlock;
import earth.terrarium.pastel.registries.DeferredRegistrar;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTextureKeys;
import earth.terrarium.pastel.registries.client.PastelTextureMaps;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.FrontAndTop;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.BlockStateGenerator;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class PastelModelHelper {
    public static final DeferredRegistrar.Contextual<ItemModelGenerators> ITEM_MODEL_REGISTRAR
        = new DeferredRegistrar.Contextual<>(DatagenProxy.IS_DATAGEN);
    // Item Models

    public static void registerItemModel(ItemModelGenerators ctx, Item item) {
        registerItemModel(ctx, item, "");
    }

    public static void registerItemModel(ItemModelGenerators ctx, Item item, String suffix) {
        ModelTemplates.FLAT_ITEM.create(
            ModelLocationUtils.getModelLocation(item), PastelTextureMaps.layer0(item, suffix), ctx.output);
    }

    public static void registerItemModel(ItemModelGenerators ctx, Item item, ModelTemplate model) {
        model.create(ModelLocationUtils.getModelLocation(item), PastelTextureMaps.layer0(item, ""), ctx.output);
    }

    public static void registerLayeredItemModel(
        ItemModelGenerators ctx, Item item, ModelTemplate model, String suffix0, String suffix1) {
        model.create(
            ModelLocationUtils.getModelLocation(item), TextureMapping.layered(
                TextureMapping.getItemTexture(item, suffix0), TextureMapping.getItemTexture(item, suffix1)), ctx.output
        );
    }

    public static void registerLayeredItemModel(
        ItemModelGenerators ctx, Item item, ModelTemplate model, String suffix0, String suffix1, String suffix2) {
        model.create(
            ModelLocationUtils.getModelLocation(item), TextureMapping.layered(
                TextureMapping.getItemTexture(item, suffix0), TextureMapping.getItemTexture(item, suffix1),
                TextureMapping.getItemTexture(item, suffix2)
            ), ctx.output
        );
    }

    public static void registerLayeredItemModel(
        ItemModelGenerators ctx, Item item, ModelTemplate model, String suffix0, String suffix1, String suffix2,
        String suffix3
    ) {
        model.create(
            ModelLocationUtils.getModelLocation(item), TextureMapping.layered(
                                                                         TextureMapping.getItemTexture(item, suffix0)
                                                                         , TextureMapping.getItemTexture(item, suffix1),
                                                                         TextureMapping.getItemTexture(item, suffix2)
                                                                     )
                                                                     .put(
                                                                         PastelTextureKeys.LAYER3,
                                                                         TextureMapping.getItemTexture(
                                                                             item, suffix3)
                                                                     ), ctx.output
        );
    }

    public static void registerBlockTexturedItemModel(ItemModelGenerators ctx, Block block) {
        registerBlockTexturedItemModel(ctx, block, "");
    }

    public static void registerBlockTexturedItemModel(ItemModelGenerators ctx, Block block, String suffix) {
        ModelTemplates.FLAT_ITEM.create(
            ModelLocationUtils.getModelLocation(block.asItem()), PastelTextureMaps.layer0(block, suffix), ctx.output);
    }

    public static void registerParentedItemModel(ItemModelGenerators ctx, ItemLike item, Item parent) {
        registerParentedItemModel(ctx, item, parent, "");
    }

    public static void registerParentedItemModel(ItemModelGenerators ctx, ItemLike item, Block parent) {
        registerParentedItemModel(ctx, item, parent, "");
    }

    public static void registerParentedItemModel(ItemModelGenerators ctx, ItemLike item, Item parent, String suffix) {
        registerParentedItemModel(ctx, item, ModelLocationUtils.getModelLocation(parent, suffix));
    }

    public static void registerParentedItemModel(ItemModelGenerators ctx, ItemLike item, Block parent, String suffix) {
        registerParentedItemModel(ctx, item, ModelLocationUtils.getModelLocation(parent, suffix));
    }

    public static void registerParentedItemModel(
        ItemModelGenerators ctx, ItemLike item, ResourceLocation parentModelId) {
        ctx.output.accept(ModelLocationUtils.getModelLocation(item.asItem()), new DelegatedModel(parentModelId));
    }

    // Block Models

    public static BlockStateGenerator simpleMirroredBlockModel(BlockModelGenerators ctx, Block block) {
        return createMirroredVariantsSupplier(block, TexturedModel.CUBE, TexturedModel.CUBE_MIRRORED, ctx.modelOutput);
    }

    public static BlockStateGenerator logBlockModel(BlockModelGenerators ctx, Block logBlock) {
        TextureMapping textureMap = PastelTextureMaps.sideEnd(logBlock, "", logBlock, "_top");
        ResourceLocation vertical = ModelTemplates.CUBE_COLUMN.create(logBlock, textureMap, ctx.modelOutput);
        ResourceLocation horizonal = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(
            logBlock, textureMap, ctx.modelOutput);
        return MultiVariantGenerator.multiVariant(logBlock)
                                    .with(createAxisRotatedVariantMap(vertical, horizonal));
    }

    public static BlockStateGenerator woodBlockModel(BlockModelGenerators ctx, Block woodBlock, Block logBlock) {
        TextureMapping textureMap = PastelTextureMaps.sideEnd(logBlock, "", logBlock, "");
        ResourceLocation model = ModelTemplates.CUBE_COLUMN.create(woodBlock, textureMap, ctx.modelOutput);
        return MultiVariantGenerator.multiVariant(woodBlock, createModelVariant(model))
                                    .with(createAxisRotatedVariantMap());
    }

    public static BlockStateGenerator pottedPlantBlockModel(
        BlockModelGenerators ctx, FlowerPotBlock block, boolean tinted) {
        BlockModelGenerators.TintState tintType = tinted ? BlockModelGenerators.TintState.TINTED
                                                         : BlockModelGenerators.TintState.NOT_TINTED;
        TextureMapping textureMap = TextureMapping.plant(block.getPotted());
        ResourceLocation identifier = tintType.getCrossPot()
                                              .create(block, textureMap, ctx.modelOutput);
        return BlockModelGenerators.createSimpleBlock(block, identifier);
    }

    public static BlockStateGenerator glassPaneBlockModel(
        BlockModelGenerators ctx, Block glassPaneBlock, Block glassBlock) {
        TextureMapping textureMap = TextureMapping.pane(glassBlock, glassPaneBlock);
        ResourceLocation post = ModelTemplates.STAINED_GLASS_PANE_POST.create(
            glassPaneBlock, textureMap, ctx.modelOutput);
        ResourceLocation side = ModelTemplates.STAINED_GLASS_PANE_SIDE.create(
            glassPaneBlock, textureMap, ctx.modelOutput);
        ResourceLocation sideAlt = ModelTemplates.STAINED_GLASS_PANE_SIDE_ALT.create(
            glassPaneBlock, textureMap, ctx.modelOutput);
        ResourceLocation noside = ModelTemplates.STAINED_GLASS_PANE_NOSIDE.create(
            glassPaneBlock, textureMap, ctx.modelOutput);
        ResourceLocation nosideAlt = ModelTemplates.STAINED_GLASS_PANE_NOSIDE_ALT.create(
            glassPaneBlock, textureMap, ctx.modelOutput);
        ModelTemplates.FLAT_ITEM.create(
            ModelLocationUtils.getModelLocation(glassPaneBlock.asItem()), TextureMapping.layer0(glassBlock),
            ctx.modelOutput
        );
        return MultiPartGenerator.multiPart(glassPaneBlock)
                                 .with(Variant.variant()
                                              .with(VariantProperties.MODEL, post))
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.NORTH, true), Variant.variant()
                                                                                              .with(
                                                                                                  VariantProperties.MODEL,
                                                                                                  side
                                                                                              )
                                 )
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.EAST, true), Variant.variant()
                                                                                             .with(
                                                                                                 VariantProperties.MODEL,
                                                                                                 side
                                                                                             )
                                                                                             .with(
                                                                                                 VariantProperties.Y_ROT,
                                                                                                 VariantProperties.Rotation.R90
                                                                                             )
                                 )
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.SOUTH, true), Variant.variant()
                                                                                              .with(
                                                                                                  VariantProperties.MODEL,
                                                                                                  sideAlt
                                                                                              )
                                 )
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.WEST, true), Variant.variant()
                                                                                             .with(
                                                                                                 VariantProperties.MODEL,
                                                                                                 sideAlt
                                                                                             )
                                                                                             .with(
                                                                                                 VariantProperties.Y_ROT,
                                                                                                 VariantProperties.Rotation.R90
                                                                                             )
                                 )
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.NORTH, false), Variant.variant()
                                                                                               .with(
                                                                                                   VariantProperties.MODEL,
                                                                                                   noside
                                                                                               )
                                 )
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.EAST, false), Variant.variant()
                                                                                              .with(
                                                                                                  VariantProperties.MODEL,
                                                                                                  nosideAlt
                                                                                              )
                                 )
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.SOUTH, false), Variant.variant()
                                                                                               .with(
                                                                                                   VariantProperties.MODEL,
                                                                                                   nosideAlt
                                                                                               )
                                                                                               .with(
                                                                                                   VariantProperties.Y_ROT,
                                                                                                   VariantProperties.Rotation.R90
                                                                                               )
                                 )
                                 .with(
                                     Condition.condition()
                                              .term(BlockStateProperties.WEST, false), Variant.variant()
                                                                                              .with(
                                                                                                  VariantProperties.MODEL,
                                                                                                  noside
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                 );
    }

    public static void registerBlockFamily(BlockModelGenerators generators, BlockFamily family) {
        generators.family(family.getBaseBlock())
                  .generateFor(family);
    }

    public static void registerBlockFamilyExceptBase(
        BlockModelGenerators generators, BlockFamily family, TexturedModel.Provider variantFactory) {
        TexturedModel texturedModel = variantFactory.get(family.getBaseBlock());
        BlockModelGenerators.BlockFamilyProvider texturePool = generators.new BlockFamilyProvider(
            texturedModel.getMapping());
        texturePool.fullBlock = ModelLocationUtils.getModelLocation(family.getBaseBlock());
        texturePool.generateFor(family);
    }

    // Variant Suppliers

    public static MultiVariantGenerator createVariantsSupplier(Block block, ResourceLocation... modelIds) {
        return MultiVariantGenerator.multiVariant(
            block, Arrays.stream(modelIds)
                         .map(modelId -> Variant.variant()
                                                .with(
                                                    VariantProperties.MODEL, modelId))
                         .toArray(Variant[]::new)
        );
    }

    public static MultiVariantGenerator createVariantsSupplier(
        BlockModelGenerators ctx, Block block, TexturedModel.Provider factory) {
        return createVariantsSupplier(block, factory.create(block, ctx.modelOutput));
    }

    public static MultiVariantGenerator createMirroredVariantsSupplier(
        Block block, TexturedModel.Provider factory, TexturedModel.Provider mirroredFactory,
        BiConsumer<ResourceLocation, Supplier<JsonElement>> modelCollector
    ) {
        return MultiVariantGenerator.multiVariant(
            block,
            createModelVariant(factory.create(block, modelCollector)),
            createModelVariant(mirroredFactory.create(block, modelCollector))
        );
    }

    // Variant Lists

    public static List<Variant> createHorizontalRotationVariantList(ResourceLocation modelId) {
        return List.of(
            createModelVariant(modelId),
            createModelVariant(modelId).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90),
            createModelVariant(modelId).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180),
            createModelVariant(modelId).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
        );
    }

    // Variant Maps

    public static PropertyDispatch createBooleanModelMap(
        BooleanProperty property, ResourceLocation trueModel, ResourceLocation falseModel) {
        return PropertyDispatch.property(property)
                               .select(false, createModelVariant(falseModel))
                               .select(true, createModelVariant(trueModel));
    }

    public static PropertyDispatch createCardinalFacingVariantMap() {
        return PropertyDispatch.property(CardinalFacingBlock.CARDINAL_FACING)
                               .select(
                                   false, Variant.variant()
                                                 .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(true, Variant.variant());
    }

    public static PropertyDispatch createAxisRotatedVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.AXIS)
                               .select(
                                   Direction.Axis.X, Variant.variant()
                                                            .with(
                                                                VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                            .with(
                                                                VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(Direction.Axis.Y, Variant.variant())
                               .select(
                                   Direction.Axis.Z, Variant.variant()
                                                            .with(
                                                                VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                               );
    }

    public static PropertyDispatch createAxisRotatedVariantMap(
        ResourceLocation verticalModelId, ResourceLocation horizontalModelId) {
        return PropertyDispatch.property(BlockStateProperties.AXIS)
                               .select(
                                   Direction.Axis.X, createModelVariant(horizontalModelId).with(
                                                                                              VariantProperties.X_ROT
                                                                                              ,
                                                                                              VariantProperties.Rotation.R90
                                                                                          )
                                                                                          .with(
                                                                                              VariantProperties.Y_ROT,
                                                                                              VariantProperties.Rotation.R90
                                                                                          )
                               )
                               .select(Direction.Axis.Y, createModelVariant(verticalModelId))
                               .select(
                                   Direction.Axis.Z, createModelVariant(horizontalModelId).with(
                                       VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                               );
    }

    public static PropertyDispatch createUpDefaultFacingVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.FACING)
                               .select(
                                   Direction.DOWN, Variant.variant()
                                                          .with(
                                                              VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(Direction.UP, Variant.variant())
                               .select(
                                   Direction.NORTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(
                                   Direction.SOUTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(
                                   Direction.WEST, Variant.variant()
                                                          .with(
                                                              VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(
                                   Direction.EAST, Variant.variant()
                                                          .with(
                                                              VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               );
    }

    public static PropertyDispatch createDownDefaultFacingVariantMap(
        ResourceLocation horizontalModelId, ResourceLocation verticalModelId) {
        return PropertyDispatch.property(DirectionalBlock.FACING)
                               .select(Direction.DOWN, createModelVariant(verticalModelId))
                               .select(
                                   Direction.UP, createModelVariant(verticalModelId).with(
                                       VariantProperties.X_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(
                                   Direction.NORTH, createModelVariant(horizontalModelId).with(
                                       VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(
                                   Direction.SOUTH, createModelVariant(horizontalModelId).with(
                                       VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(
                                   Direction.WEST, createModelVariant(horizontalModelId).with(
                                       VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(Direction.EAST, createModelVariant(horizontalModelId));
    }

    public static PropertyDispatch createNorthDefaultFacingVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.FACING)
                               .select(
                                   Direction.DOWN, Variant.variant()
                                                          .with(
                                                              VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(
                                   Direction.UP, Variant.variant()
                                                        .with(
                                                            VariantProperties.X_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(Direction.NORTH, Variant.variant())
                               .select(
                                   Direction.SOUTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(
                                   Direction.WEST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(
                                   Direction.EAST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               );
    }

    public static PropertyDispatch createUpDefaultHorizontalFacingVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                               .select(
                                   Direction.NORTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(
                                   Direction.SOUTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(
                                   Direction.WEST, Variant.variant()
                                                          .with(
                                                              VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(
                                   Direction.EAST, Variant.variant()
                                                          .with(
                                                              VariantProperties.X_ROT, VariantProperties.Rotation.R90)
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               );
    }

    public static PropertyDispatch createNorthDefaultHorizontalFacingVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                               .select(Direction.NORTH, Variant.variant())
                               .select(
                                   Direction.SOUTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(
                                   Direction.WEST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(
                                   Direction.EAST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               );
    }

    public static PropertyDispatch createSouthDefaultHorizontalFacingVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                               .select(
                                   Direction.NORTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(Direction.SOUTH, Variant.variant())
                               .select(
                                   Direction.WEST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(
                                   Direction.EAST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               );
    }

    public static PropertyDispatch createWestDefaultHorizontalFacingVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                               .select(
                                   Direction.NORTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(
                                   Direction.SOUTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(Direction.WEST, Variant.variant())
                               .select(
                                   Direction.EAST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               );
    }

    public static PropertyDispatch createEastDefaultHorizontalFacingVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.HORIZONTAL_FACING)
                               .select(
                                   Direction.NORTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                               )
                               .select(
                                   Direction.SOUTH, Variant.variant()
                                                           .with(
                                                               VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                               )
                               .select(
                                   Direction.WEST, Variant.variant()
                                                          .with(
                                                              VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                               )
                               .select(Direction.EAST, Variant.variant());
    }

    public static PropertyDispatch createUpNorthDefaultOrientationVariantMap() {
        return PropertyDispatch.property(BlockStateProperties.ORIENTATION)
                               .select(
                                   FrontAndTop.DOWN_NORTH, Variant.variant()
                                                                  .with(
                                                                      VariantProperties.X_ROT,
                                                                      VariantProperties.Rotation.R90
                                                                  )
                               )
                               .select(
                                   FrontAndTop.DOWN_SOUTH, Variant.variant()
                                                                  .with(
                                                                      VariantProperties.X_ROT,
                                                                      VariantProperties.Rotation.R90
                                                                  )
                                                                  .with(
                                                                      VariantProperties.Y_ROT,
                                                                      VariantProperties.Rotation.R180
                                                                  )
                               )
                               .select(
                                   FrontAndTop.DOWN_WEST, Variant.variant()
                                                                 .with(
                                                                     VariantProperties.X_ROT,
                                                                     VariantProperties.Rotation.R90
                                                                 )
                                                                 .with(
                                                                     VariantProperties.Y_ROT,
                                                                     VariantProperties.Rotation.R270
                                                                 )
                               )
                               .select(
                                   FrontAndTop.DOWN_EAST, Variant.variant()
                                                                 .with(
                                                                     VariantProperties.X_ROT,
                                                                     VariantProperties.Rotation.R90
                                                                 )
                                                                 .with(
                                                                     VariantProperties.Y_ROT,
                                                                     VariantProperties.Rotation.R90
                                                                 )
                               )
                               .select(
                                   FrontAndTop.UP_NORTH, Variant.variant()
                                                                .with(
                                                                    VariantProperties.X_ROT,
                                                                    VariantProperties.Rotation.R270
                                                                )
                                                                .with(
                                                                    VariantProperties.Y_ROT,
                                                                    VariantProperties.Rotation.R180
                                                                )
                               )
                               .select(
                                   FrontAndTop.UP_SOUTH, Variant.variant()
                                                                .with(
                                                                    VariantProperties.X_ROT,
                                                                    VariantProperties.Rotation.R270
                                                                )
                               )
                               .select(
                                   FrontAndTop.UP_WEST, Variant.variant()
                                                               .with(
                                                                   VariantProperties.X_ROT,
                                                                   VariantProperties.Rotation.R270
                                                               )
                                                               .with(
                                                                   VariantProperties.Y_ROT,
                                                                   VariantProperties.Rotation.R90
                                                               )
                               )
                               .select(
                                   FrontAndTop.UP_EAST, Variant.variant()
                                                               .with(
                                                                   VariantProperties.X_ROT,
                                                                   VariantProperties.Rotation.R270
                                                               )
                                                               .with(
                                                                   VariantProperties.Y_ROT,
                                                                   VariantProperties.Rotation.R270
                                                               )
                               )
                               .select(FrontAndTop.NORTH_UP, Variant.variant())
                               .select(
                                   FrontAndTop.SOUTH_UP, Variant.variant()
                                                                .with(
                                                                    VariantProperties.Y_ROT,
                                                                    VariantProperties.Rotation.R180
                                                                )
                               )
                               .select(
                                   FrontAndTop.WEST_UP, Variant.variant()
                                                               .with(
                                                                   VariantProperties.Y_ROT,
                                                                   VariantProperties.Rotation.R270
                                                               )
                               )
                               .select(
                                   FrontAndTop.EAST_UP, Variant.variant()
                                                               .with(
                                                                   VariantProperties.Y_ROT,
                                                                   VariantProperties.Rotation.R90
                                                               )
                               );
    }

    // Variants

    public static Variant createModelVariant(ResourceLocation modelId) {
        return Variant.variant()
                      .with(VariantProperties.MODEL, modelId);
    }

    public static Variant createModelVariant(Block block, String suffix) {
        return createModelVariant(ModelLocationUtils.getModelLocation(block, suffix));
    }

    public static VariantProperties.Rotation getSouthDefaultRotation(Direction direction) {
        return switch (direction) {
            case Direction.WEST -> VariantProperties.Rotation.R90;
            case Direction.NORTH -> VariantProperties.Rotation.R180;
            case Direction.EAST -> VariantProperties.Rotation.R270;
            default -> VariantProperties.Rotation.R0;
        };
    }

//
//		public BlockRegistrar<T> withBlockModel(BiFunction<BlockModelGenerators, Block, BlockStateGenerator>
//		callback) {
//			PastelModelHelper.BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
//				Objects.requireNonNull(holder);
//				ctx.blockStateOutput.accept(callback.apply(ctx, holder.get()));
//			});
//			return this;
//		}
//
//		public BlockRegistrar<T> withBlockItemModel(BiConsumer<ItemModelGenerators, ? super T> callback) {
//			PastelModelHelper.ITEM_MODEL_REGISTRAR.defer(ctx -> {
//				if (hasItem) {
//					Objects.requireNonNull(holder);
//					callback.accept(ctx, (T) holder.get());
//				}
//			});
//			return this;
//		}
//
//		public BlockRegistrar<T> withItemModel(BiConsumer<ItemModelGenerators, Item> callback) {
//			PastelModelHelper.ITEM_MODEL_REGISTRAR.defer(ctx -> {
//				if (hasItem) {
//					Objects.requireNonNull(holder);
//					callback.accept(ctx, holder.asItem());
//				}
//			});
//			return this;
//		}
//

    public static void predefinedItemModel(BlockModelGenerators generators,DeferredBlock<Block> block){
        generators.skipAutoItemBlock(block.get());
    }
    // imported helpers from PastelBlocks
    public static void cutout(DeferredBlock<Block> block) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.CUTOUT);
    }

    public static void mippedCutout(DeferredBlock<Block> block) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.CUTOUT_MIPPED);
    }

    public static void translucent(DeferredBlock<Block> block) {
        ItemBlockRenderTypes.setRenderLayer(block.get(), RenderType.TRANSLUCENT);
    }

    public static void simple(BlockModelGenerators generators, DeferredBlock<Block> block) {
        singleton(generators, block, TexturedModel.CUBE);
    }

    @SafeVarargs
    public static void simple(BlockModelGenerators generators, DeferredBlock<Block>... blocks) {
        for (DeferredBlock<Block> block : blocks) {
            simple(generators, block);
        }
    }

    public static void simpleMirrored(
        BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput.accept(PastelModelHelper.createMirroredVariantsSupplier(
            block.get(), TexturedModel.CUBE, TexturedModel.CUBE_MIRRORED, generators.modelOutput));
    }

    public static void singleton(
        BlockModelGenerators generators, DeferredBlock<Block> block, TexturedModel.Provider factory) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), factory.create(block.get(), generators.modelOutput)));
    }

    public static void singletonWithSoup(
        BlockModelGenerators generators, DeferredBlock<Block> block,
        Function<Block, ResourceLocation> modelIdSupplier
    ) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), modelIdSupplier.apply(block.get())));
    }

    public static void parented(BlockModelGenerators generators, Block block, Block parent) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block, ModelLocationUtils.getModelLocation(parent)));
    }

    public static void axisRotated(
        BlockModelGenerators generators, DeferredBlock<Block> block, TexturedModel.Provider factory) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), factory.create(block.get(), generators.modelOutput))
                             .with(PastelModelHelper.createAxisRotatedVariantMap()));
    }

    public static void defaultUpFacing(
        BlockModelGenerators generators, DeferredBlock<Block> block, TexturedModel.Provider factory) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), factory.create(block.get(), generators.modelOutput))
                             .with(PastelModelHelper.createUpDefaultFacingVariantMap()));
    }

    public static void defaultUpFacingGetter(
        BlockModelGenerators generators, DeferredBlock<Block> block, Function<Block, ResourceLocation> modelIdGetter) {
        PastelModelHelper.createVariantsSupplier(block.get(), modelIdGetter.apply(block.get()))
                         .with(PastelModelHelper.createUpDefaultFacingVariantMap());
    }

    public static void defaultNorthHorizontalFacing(
        BlockModelGenerators generators, DeferredBlock<Block> block, Function<Block, ResourceLocation> modelIdGetter) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), modelIdGetter.apply(block.get()))
                             .with(PastelModelHelper.createNorthDefaultHorizontalFacingVariantMap()));
    }

    public static void defaultSouthHorizontalFacing(
        BlockModelGenerators generators, DeferredBlock<Block> block, Function<Block, ResourceLocation> modelIdGetter) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), modelIdGetter.apply(block.get()))
                             .with(PastelModelHelper.createSouthDefaultHorizontalFacingVariantMap()));
    }

    public static void defaultWestHorizontalFacing(
        BlockModelGenerators generators, DeferredBlock<Block> block, Function<Block, ResourceLocation> modelIdGetter) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), modelIdGetter.apply(block.get()))
                             .with(PastelModelHelper.createWestDefaultHorizontalFacingVariantMap()));
    }

    public static void defaultEastHorizontalFacing(
        BlockModelGenerators generators, DeferredBlock<Block> block, Function<Block, ResourceLocation> modelIdGetter) {
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(block.get(), modelIdGetter.apply(block.get()))
                             .with(PastelModelHelper.createEastDefaultHorizontalFacingVariantMap()));
    }

    public static void cross(BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(
            block.get(), PastelTexturedModels.cross(b -> b, "")
                                             .create(
                                                 block.get(),
                                                 generators.modelOutput
                                             )
        ));

    }

    public static void simplePlant(ItemModelGenerators generators, DeferredBlock<Block> block) {
        PastelModelHelper.registerBlockTexturedItemModel(generators, block.get());
    }

    public static void pottedPlant(
        BlockModelGenerators generators, DeferredBlock<Block> block, boolean tinted) {
        generators.blockStateOutput.accept(
            PastelModelHelper.pottedPlantBlockModel(generators, (FlowerPotBlock) block.get(), tinted));
    }

    public static void log(BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput.accept(PastelModelHelper.logBlockModel(generators, block.get()));
    }

    public static void wood(
        BlockModelGenerators generators, DeferredBlock<Block> block, DeferredBlock<Block> logBlock) {
        generators.blockStateOutput.accept(PastelModelHelper.woodBlockModel(generators, block.get(), logBlock.get()));
    }

    public static void snowy(
        BlockModelGenerators generators, DeferredBlock<Block> block, TexturedModel.Provider base,
        TexturedModel.Provider snowy
    ) {
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.SNOWY)
                                                                                      .select(
                                                                                          false,
                                                                                          PastelModelHelper.createHorizontalRotationVariantList(
                                                                                              base.create(
                                                                                                  block.get(),
                                                                                                  generators.modelOutput
                                                                                              ))
                                                                                      )
                                                                                      .select(
                                                                                          true,
                                                                                          PastelModelHelper.createHorizontalRotationVariantList(
                                                                                              snowy.createWithSuffix(
                                                                                                  block.get(), "_snow",
                                                                                                  generators.modelOutput
                                                                                              ))
                                                                                      )));
    }

    public static void redstoneLamp(BlockModelGenerators generators, DeferredBlock<Block> block) {
        ResourceLocation off = PastelTexturedModels.cubeAll(b -> b, "_off")
                                                   .createWithSuffix(block.get(), "_off", generators.modelOutput);
        ResourceLocation on = PastelTexturedModels.cubeAll(b -> b, "_on")
                                                  .createWithSuffix(block.get(), "_on", generators.modelOutput);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BlockStateProperties.LIT, on, off)));
    }

    public static void barrellike(
        BlockModelGenerators generators, DeferredBlock<Block> block, UnaryOperator<Block> bottomBlock,
        String bottomSuffix
    ) {
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(
                                                                    PastelModelHelper.createUpDefaultFacingVariantMap())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BlockStateProperties.OPEN,
                                                                    PastelTexturedModels.cubeBottomTop(
                                                                                            b -> b, "_side", b -> b,
                                                                                            "_top_open",
                                                                                            bottomBlock, bottomSuffix
                                                                                        )
                                                                                        .createWithSuffix(
                                                                                            block.get(), "_open",
                                                                                            generators.modelOutput
                                                                                        ),
                                                                    PastelTexturedModels.cubeBottomTop(
                                                                                            b -> b, "_side", b -> b,
                                                                                            "_top", bottomBlock,
                                                                                            bottomSuffix
                                                                                        )
                                                                                        .create(
                                                                                            block.get(),
                                                                                            generators.modelOutput
                                                                                        )
                                                                )));
    }

    public static void idol(BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    IdolBlock.COOLDOWN, PastelModels.MOB_BLOCK,
                                                                    PastelModels.MOB_BLOCK_COOLDOWN
                                                                )));
    }

    public static void pedestal(BlockModelGenerators generators, DeferredBlock<Block> block) {
        singleton(
            generators, block, TexturedModel.createDefault(
                b -> new TextureMapping().put(PastelTextureKeys.PEDESTAL, TextureMapping.getBlockTexture(b))
                                         .put(
                                             TextureSlot.PARTICLE, TextureMapping.getBlockTexture(b, "_particle")),
                PastelModels.PEDESTAL
            )
        );
    }

    public static void detector(BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BlockStateProperties.INVERTED,
                                                                    PastelModels.SLAB_DETECTOR.createWithSuffix(
                                                                        block.get(), "_inverted",
                                                                        PastelTextureMaps.sideTop(
                                                                            block.get(), "_side", block.get(),
                                                                            "_inverted_top"
                                                                        ), generators.modelOutput
                                                                    ), PastelModels.SLAB_DETECTOR.create(
                                                                        block.get(), PastelTextureMaps.sideTop(
                                                                            block.get(), "_side", block.get(), "_top"),
                                                                        generators.modelOutput
                                                                    )
                                                                )));
    }

    public static void orientable(BlockModelGenerators generators, DeferredBlock<Block> block) {
        ResourceLocation horizontal = ModelTemplates.CUBE_ORIENTABLE.create(
            block.get(), new TextureMapping().put(TextureSlot.TOP, TextureMapping.getBlockTexture(block.get(), "_top"))
                                             .put(
                                                 TextureSlot.SIDE, TextureMapping.getBlockTexture(
                                                     block.get(), "_side")
                                             )
                                             .put(
                                                 TextureSlot.FRONT, TextureMapping.getBlockTexture(
                                                     block.get(), "_front")
                                             ), generators.modelOutput
        );
        ResourceLocation vertical = ModelTemplates.CUBE_ORIENTABLE_VERTICAL.create(
            block.get(), new TextureMapping().put(
                                                 TextureSlot.SIDE, TextureMapping.getBlockTexture(block.get(), "_top"))
                                             .put(
                                                 TextureSlot.FRONT, TextureMapping.getBlockTexture(
                                                     block.get(), "_front_vertical")
                                             ), generators.modelOutput
        );
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PropertyDispatch.property(
                                                                                          BlockStateProperties.FACING)
                                                                                      .select(
                                                                                          Direction.DOWN,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               vertical)
                                                                                                           .with(
                                                                                                               VariantProperties.X_ROT,
                                                                                                               VariantProperties.Rotation.R180
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.UP,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              vertical)
                                                                                      )
                                                                                      .select(
                                                                                          Direction.NORTH,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              horizontal)
                                                                                      )
                                                                                      .select(
                                                                                          Direction.EAST,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               horizontal)
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R90
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.SOUTH,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               horizontal)
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R180
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.WEST,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               horizontal)
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R270
                                                                                                           )
                                                                                      )));
    }

    public static void pylon(BlockModelGenerators generators, DeferredBlock<Block> block) {
        ResourceLocation head = ModelLocationUtils.getModelLocation(block.get(), "_head");
        ResourceLocation body = ModelLocationUtils.getModelLocation(block.get(), "_body");
        ResourceLocation waist = ModelLocationUtils.getModelLocation(block.get(), "_waist");
        ResourceLocation foot = ModelLocationUtils.getModelLocation(block.get(), "_foot");
        ResourceLocation end = ModelLocationUtils.getModelLocation(block.get(), "_end");
        ResourceLocation pedestal = PastelModels.BALCITE_PYLON_PEDESTAL;
        PastelModels.BASE_PYLON_BODY.create(head, PastelTextureMaps.sideEnd(head, end), generators.modelOutput);
        PastelModels.BASE_PYLON_BODY.create(body, PastelTextureMaps.sideEnd(body, end), generators.modelOutput);
        PastelModels.BASE_PYLON_BODY.create(waist, PastelTextureMaps.sideEnd(waist, end), generators.modelOutput);
        PastelModels.BASE_PYLON_BODY.create(foot, PastelTextureMaps.sideEnd(foot, end), generators.modelOutput);
        generators.blockStateOutput.accept(MultiPartGenerator.multiPart(block.get())
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.DOWN
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.HEAD
                                                                          ), PastelModelHelper.createModelVariant(head)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R180
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.DOWN
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.BODY
                                                                          ), PastelModelHelper.createModelVariant(body)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R180
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.DOWN
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.WAIST
                                                                          ), PastelModelHelper.createModelVariant(waist)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R180
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.DOWN
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.FOOT
                                                                          ), PastelModelHelper.createModelVariant(foot)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R180
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.DOWN
                                                                          )
                                                                          .term(PylonBlock.PEDESTAL, true),
                                                                 PastelModelHelper.createModelVariant(pedestal)
                                                                                  .with(
                                                                                      VariantProperties.X_ROT,
                                                                                      VariantProperties.Rotation.R180
                                                                                  )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING, Direction.UP)
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.HEAD
                                                                          ), PastelModelHelper.createModelVariant(head)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING, Direction.UP)
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.BODY
                                                                          ), PastelModelHelper.createModelVariant(body)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING, Direction.UP)
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.WAIST
                                                                          ), PastelModelHelper.createModelVariant(waist)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING, Direction.UP)
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.FOOT
                                                                          ), PastelModelHelper.createModelVariant(foot)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING, Direction.UP)
                                                                          .term(PylonBlock.PEDESTAL, true),
                                                                 PastelModelHelper.createModelVariant(pedestal)
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.NORTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.HEAD
                                                                          ), PastelModelHelper.createModelVariant(head)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.NORTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.BODY
                                                                          ), PastelModelHelper.createModelVariant(body)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.NORTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.WAIST
                                                                          ), PastelModelHelper.createModelVariant(waist)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.NORTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.FOOT
                                                                          ), PastelModelHelper.createModelVariant(foot)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.NORTH
                                                                          )
                                                                          .term(PylonBlock.PEDESTAL, true),
                                                                 PastelModelHelper.createModelVariant(pedestal)
                                                                                  .with(
                                                                                      VariantProperties.X_ROT,
                                                                                      VariantProperties.Rotation.R90
                                                                                  )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.SOUTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.HEAD
                                                                          ), PastelModelHelper.createModelVariant(head)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.SOUTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.BODY
                                                                          ), PastelModelHelper.createModelVariant(body)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.SOUTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.WAIST
                                                                          ), PastelModelHelper.createModelVariant(waist)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.SOUTH
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.FOOT
                                                                          ), PastelModelHelper.createModelVariant(foot)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.SOUTH
                                                                          )
                                                                          .term(PylonBlock.PEDESTAL, true),
                                                                 PastelModelHelper.createModelVariant(pedestal)
                                                                                  .with(
                                                                                      VariantProperties.X_ROT,
                                                                                      VariantProperties.Rotation.R270
                                                                                  )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.WEST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.HEAD
                                                                          ), PastelModelHelper.createModelVariant(head)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.WEST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.BODY
                                                                          ), PastelModelHelper.createModelVariant(body)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.WEST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.WAIST
                                                                          ), PastelModelHelper.createModelVariant(waist)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.WEST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.FOOT
                                                                          ), PastelModelHelper.createModelVariant(foot)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R270
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.WEST
                                                                          )
                                                                          .term(PylonBlock.PEDESTAL, true),
                                                                 PastelModelHelper.createModelVariant(pedestal)
                                                                                  .with(
                                                                                      VariantProperties.X_ROT,
                                                                                      VariantProperties.Rotation.R90
                                                                                  )
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R270
                                                                                  )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.EAST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.HEAD
                                                                          ), PastelModelHelper.createModelVariant(head)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.EAST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.BODY
                                                                          ), PastelModelHelper.createModelVariant(body)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.EAST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.WAIST
                                                                          ), PastelModelHelper.createModelVariant(waist)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.EAST
                                                                          )
                                                                          .term(
                                                                              PylonBlock.SECTION,
                                                                              PylonBlock.Section.FOOT
                                                                          ), PastelModelHelper.createModelVariant(foot)
                                                                                              .with(
                                                                                                  VariantProperties.X_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                                                              .with(
                                                                                                  VariantProperties.Y_ROT,
                                                                                                  VariantProperties.Rotation.R90
                                                                                              )
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(
                                                                              BlockStateProperties.FACING,
                                                                              Direction.EAST
                                                                          )
                                                                          .term(PylonBlock.PEDESTAL, true),
                                                                 PastelModelHelper.createModelVariant(pedestal)
                                                                                  .with(
                                                                                      VariantProperties.X_ROT,
                                                                                      VariantProperties.Rotation.R90
                                                                                  )
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R90
                                                                                  )
                                                             ));
    }

    public static void cluster(BlockModelGenerators generators, DeferredBlock<Block> block, ModelTemplate model) {
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
                                                                    block.get(),
                                                                    PastelModelHelper.createModelVariant(TexturedModel.createDefault(TextureMapping::cross, model)
                                                                                                                                   .create(
                                                                                                                                       block.get(), generators.modelOutput))
                                                                )
                                                                .with(
                                                                    PastelModelHelper.createUpDefaultFacingVariantMap()));
    }

    public static void moonstoneChiseled(
        BlockModelGenerators generators, DeferredBlock<Block> block, ResourceLocation capTexture) {
        TextureMapping textureMap = PastelTextureMaps.sideLine(capTexture, TextureMapping.getBlockTexture(block.get()));
        ResourceLocation base = PastelModels.MOONSTONE_CHISELED.create(block.get(), textureMap, generators.modelOutput);
        ResourceLocation down = PastelModels.MOONSTONE_CHISELED_DOWN.createWithSuffix(
            block.get(), "_down", textureMap, generators.modelOutput);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(
                                                                    PastelModelHelper.createDownDefaultFacingVariantMap(
                                                                        ModelLocationUtils.getModelLocation(
                                                                            block.get()),
                                                                        ModelLocationUtils.getModelLocation(
                                                                            block.get(), "_down")
                                                                    )));
    }

    public static void generateInventoryParentedItemModel(ItemModelGenerators generators, DeferredBlock<Block> block) {
        registerParentedItemModel(generators, block.get(), block.get(), "_inventory");
    }
}
