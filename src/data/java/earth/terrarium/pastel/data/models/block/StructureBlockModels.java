package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.blocks.statues.StatueBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTextureKeys;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static earth.terrarium.pastel.PastelCommon.locate;
import static net.minecraft.world.level.block.Blocks.GLASS;

public class StructureBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(PastelBlocks.PRESERVATION_STONE.get()).stairs(
                                                                                          PastelBlocks.PRESERVATION_STAIRS.get())
                                                                                      .slab(
                                                                                          PastelBlocks.PRESERVATION_SLAB.get())
                                                                                      .wall(
                                                                                          PastelBlocks.PRESERVATION_WALL.get())
                                                                                      .getFamily(), TexturedModel.CUBE
        );

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.PRESERVATION_CONTROLLER);

        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.PRESERVATION_CONTROLLER, ModelLocationUtils::getModelLocation);


        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DIKE_GATE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DREAM_GATE);

        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.INVISIBLE_WALL, PastelTexturedModels.particle(b -> GLASS, ""));
        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.PRESERVATION_CHEST, ModelLocationUtils::getModelLocation);

        List<ResourceLocation> modelIds = new ArrayList<>();
        int[] tops = new int[]{0, 3, 1, 1, 2, 2, 0, 3, 1, 2, 3};
        modelIds.add(PastelTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top_" + tops[0], b -> b, "_bottom")
                                         .create(PastelBlocks.PRESERVATION_STONE.get(), generators.modelOutput));
        for (int i = 1; i <= 10; i++)
            modelIds.add(
                PastelTexturedModels.cubeBottomTop(b -> b, "_" + i, b -> b, "_top_" + tops[i], b -> b, "_bottom")
                                    .createWithSuffix(
                                        PastelBlocks.PRESERVATION_STONE.get(), "_" + i, generators.modelOutput));
        List<Variant> variants = new ArrayList<>();
        for (VariantProperties.Rotation rotation : VariantProperties.Rotation.values()) {
            variants.add(PastelModelHelper.createModelVariant(modelIds.getFirst())
                                          .with(VariantProperties.WEIGHT, 10));
            if (rotation != VariantProperties.Rotation.R0) variants.getLast()
                                                                   .with(VariantProperties.Y_ROT, rotation);
            for (int i = 1; i <= 10; i++) {
                variants.add(PastelModelHelper.createModelVariant(modelIds.get(i)));
                if (rotation != VariantProperties.Rotation.R0) variants.getLast()
                                                                       .with(VariantProperties.Y_ROT, rotation);
            }
        }
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
            PastelBlocks.PRESERVATION_STONE.get(), variants.toArray(Variant[]::new)));
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.POWDER_CHISELED_PRESERVATION_STONE,
            PastelTexturedModels.cubeColumn(
                b -> b, "",
                b -> PastelBlocks.PRESERVATION_STONE.get(),
                "_top_generic"
            )
        );
        PastelModelHelper.BLOCK.simple(
            generators, PastelBlocks.DIKE_CHISELED_PRESERVATION_STONE, PastelBlocks.DREAM_CHISELED_PRESERVATION_STONE);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.DEEP_LIGHT_CHISELED_PRESERVATION_STONE,
            PastelTexturedModels.cubeColumn(
                b -> b, "",
                b -> PastelBlocks.PRESERVATION_STONE.get(),
                "_top_generic"
            )
        );


        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.ENLIGHTENMENT_ITEM_BOWL, TexturedModel.createDefault(
                b -> new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(b, "_side"))
                                         .put(TextureSlot.TOP, TextureMapping.getBlockTexture(b, "_top"))
                                         .put(TextureSlot.BOTTOM, locate("block/item_bowl_preservation_bottom"))
                                         .put(PastelTextureKeys.INNER, locate("block/item_bowl_preservation_bottom")),
                PastelModels.BOWL
            )
        );
        PastelModelHelper.BLOCK.defaultUpFacing(
            generators, PastelBlocks.DIKE_GATE_FOUNTAIN, PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_side", b -> b, "_top", b -> PastelBlocks.PRESERVATION_STONE.get(), "",
                b -> PastelBlocks.PRESERVATION_STONE.get(), ""
            )
        );
        PastelModelHelper.BLOCK.simple(
            generators, PastelBlocks.PRESERVATION_BRICKS, PastelBlocks.SHIMMERING_PRESERVATION_BRICKS);

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.COURIER_STATUE.get())
                                                                .with(
                                                                    PastelModelHelper.createNorthDefaultHorizontalFacingVariantMap())
                                                                .with(PropertyDispatch.property(StatueBlock.HALF)
                                                                                      .select(
                                                                                          DoubleBlockHalf.LOWER,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.COURIER_STATUE.get(),
                                                                                              "_bottom"
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          DoubleBlockHalf.UPPER,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.COURIER_STATUE.get(),
                                                                                              "_top"
                                                                                          )
                                                                                      )));
        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.MANXI, (Function<Block, ResourceLocation>) b -> PastelModels.MOB_HEAD);

        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.BLACK_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.BLUE_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.BROWN_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.CYAN_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.GRAY_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.GREEN_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.LIGHT_BLUE_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.LIGHT_GRAY_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.LIME_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.MAGENTA_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.ORANGE_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.PINK_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.PURPLE_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.RED_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.WHITE_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.YELLOW_CHISELED_PRESERVATION_STONE, TexturedModel.COLUMN_ALT);



        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.PRESERVATION_GLASS, PastelBlocks.TINTED_PRESERVATION_GLASS);

        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.PRESERVATION_ROUNDEL, PastelTexturedModels.ROUNDEL);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
                                                                    PastelBlocks.PRESERVATION_BLOCK_DETECTOR.get(),
                                                                    PastelModelHelper.createModelVariant(
                                                                        PastelTexturedModels.complexOrientable(
                                                                                                b -> b, "_side",
                                                                                                b -> b, "_top",
                                                                                                b -> PastelBlocks.PRESERVATION_STONE.get(), "_top_generic", b -> b,
                                                                                                "_front", b -> b,
                                                                                                "_back", b -> b, "_side"
                                                                                            )
                                                                                            .create(PastelBlocks.PRESERVATION_BLOCK_DETECTOR.get(), generators.modelOutput))
                                                                )
                                                                .with(
                                                                    PastelModelHelper.createNorthDefaultFacingVariantMap()));
    }

    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.INVISIBLE_WALL, PastelBlocks.ETHEREAL_PLATFORM.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.COURIER_STATUE, PastelBlocks.COURIER_STATUE.get(), "_top");
    }
}
