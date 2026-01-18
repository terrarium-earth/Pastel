package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.deeper_down.HummingstoneBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.data.models.block.deco.BalciteBlockModels;
import earth.terrarium.pastel.data.models.block.deco.ColoredBlockModels;
import earth.terrarium.pastel.data.models.block.deco.StoneLikeBlockModels;
import earth.terrarium.pastel.data.models.block.deco.WoodLikeBlockModels;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTextureMaps;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import earth.terrarium.pastel.registries.client.PastelTextures;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.*;

public class DecoBlockModels {

    public static void registerShimmerstoneLight(
        BlockModelGenerators generators, DeferredBlock<Block> block,
        Supplier<ResourceLocation> outerSupplier
    ) {
        ResourceLocation outer = outerSupplier.get();
        ResourceLocation base = PastelModels.SHIMMERSTONE_LIGHT.create(
            block.get(), PastelTextureMaps.innerOuterParticle(
                PastelTextures.SHIMMERSTONE_LIGHT, outer, outer), generators.modelOutput
        );
        ResourceLocation mirrored = PastelModels.SHIMMERSTONE_LIGHT_MIRRORED.createWithSuffix(
            block.get(), "_mirrored", PastelTextureMaps.innerOuterParticle(
                PastelTextures.SHIMMERSTONE_LIGHT, outer, outer), generators.modelOutput
        );
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(
                                                                    PastelModelHelper.createNorthDefaultFacingVariantMap())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BlockStateProperties.INVERTED, mirrored, base)));
    }

    public static void glassPane(
        BlockModelGenerators generators, DeferredBlock<Block> block, DeferredBlock<Block> glassBlock) {
        generators.blockStateOutput.accept(
            PastelModelHelper.glassPaneBlockModel(generators, block.get(), glassBlock.get()));
    }

    public static void generateBlockModels(BlockModelGenerators generators) {
        BalciteBlockModels.generateBlockModels(generators);
        ColoredBlockModels.generateBlockModels(generators);
        StoneLikeBlockModels.generateBlockModels(generators);
        WoodLikeBlockModels.generateBlockModels(generators);


        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.ITEM_BOWL_BASALT, PastelTexturedModels.BOWL);

        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.ITEM_BOWL_CALCITE, PastelTexturedModels.BOWL);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.ITEM_ROUNDEL, PastelTexturedModels.ROUNDEL);

        PastelModelHelper.BLOCK.defaultEastHorizontalFacing(
            generators, PastelBlocks.PRIMORDIAL_WALL_TORCH, ModelLocationUtils::getModelLocation);
        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.PRIMORDIAL_TORCH, ModelLocationUtils::getModelLocation);

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.TOPAZ_GLASS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.AMETHYST_GLASS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.CITRINE_GLASS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.ONYX_GLASS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.MOONSTONE_GLASS);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.RADIANT_GLASS);
        glassPane(generators, PastelBlocks.TOPAZ_GLASS_PANE, PastelBlocks.TOPAZ_GLASS);
        glassPane(generators, PastelBlocks.AMETHYST_GLASS_PANE, PastelBlocks.AMETHYST_GLASS);
        glassPane(generators, PastelBlocks.CITRINE_GLASS_PANE, PastelBlocks.CITRINE_GLASS);
        glassPane(generators, PastelBlocks.ONYX_GLASS_PANE, PastelBlocks.ONYX_GLASS);
        glassPane(generators, PastelBlocks.MOONSTONE_GLASS_PANE, PastelBlocks.MOONSTONE_GLASS);
        glassPane(generators, PastelBlocks.RADIANT_GLASS_PANE, PastelBlocks.RADIANT_GLASS);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.TOPAZ_CHIME, PastelTexturedModels.CHIME);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.AMETHYST_CHIME, PastelTexturedModels.CHIME);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.CITRINE_CHIME, PastelTexturedModels.CHIME);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.ONYX_CHIME, PastelTexturedModels.CHIME);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.MOONSTONE_CHIME, PastelTexturedModels.CHIME);
        PastelModelHelper.BLOCK.pylon(generators, PastelBlocks.TOPAZ_PYLON);
        PastelModelHelper.BLOCK.pylon(generators, PastelBlocks.AMETHYST_PYLON);
        PastelModelHelper.BLOCK.pylon(generators, PastelBlocks.CITRINE_PYLON);
        PastelModelHelper.BLOCK.pylon(generators, PastelBlocks.ONYX_PYLON);
        PastelModelHelper.BLOCK.pylon(generators, PastelBlocks.MOONSTONE_PYLON);

        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.SEMI_PERMEABLE_GLASS.get(), GLASS);
        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS.get(), TINTED_GLASS);
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.TOPAZ_SEMI_PERMEABLE_GLASS.get(), PastelBlocks.TOPAZ_GLASS.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.AMETHYST_SEMI_PERMEABLE_GLASS.get(), PastelBlocks.AMETHYST_GLASS.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.CITRINE_SEMI_PERMEABLE_GLASS.get(), PastelBlocks.CITRINE_GLASS.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.ONYX_SEMI_PERMEABLE_GLASS.get(), PastelBlocks.ONYX_GLASS.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS.get(), PastelBlocks.MOONSTONE_GLASS.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS.get(), PastelBlocks.RADIANT_GLASS.get());


        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.HUMMINGSTONE_GLASS);
        glassPane(generators, PastelBlocks.HUMMINGSTONE_GLASS_PANE, PastelBlocks.HUMMINGSTONE_GLASS);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.HUMMINGSTONE.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    HummingstoneBlock.HUMMING,
                                                                    PastelTexturedModels.cubeAll(b -> b, "_humming")
                                                                                        .createWithSuffix(
                                                                                            PastelBlocks.HUMMINGSTONE.get(),
                                                                                            "_humming",
                                                                                            generators.modelOutput
                                                                                        ), TexturedModel.CUBE.create(
                                                                        PastelBlocks.HUMMINGSTONE.get(),
                                                                        generators.modelOutput
                                                                    )
                                                                )));
        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.WAXED_HUMMINGSTONE.get(), PastelBlocks.HUMMINGSTONE.get());

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
            PastelBlocks.ASH.get(), PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "")
                                                                                             .createWithSuffix(
                                                                                                 PastelBlocks.ASH.get(),
                                                                                                 "",
                                                                                                 generators.modelOutput
                                                                                             )),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "2")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.ASH.get(), "2",
                                                                         generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                PastelTexturedModels.cubeAll(
                                        b -> b, "3")
                                    .createWithSuffix(
                                        PastelBlocks.ASH.get(), "3", generators.modelOutput)),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "4")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.ASH.get(), "4",
                                                                         generators.modelOutput
                                                                     ))
        ));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.ASH_PILE.get())
                                                                .with(
                                                                    PropertyDispatch.property(
                                                                                        BlockStateProperties.LAYERS)
                                                                                    .generateList(height -> {
                                                                                        ResourceLocation ash
                                                                                            =
                                                                                            TextureMapping.getBlockTexture(
                                                                                                PastelBlocks.ASH.get());
                                                                                        ResourceLocation ash2
                                                                                            =
                                                                                            TextureMapping.getBlockTexture(
                                                                                                PastelBlocks.ASH.get(),
                                                                                                "2"
                                                                                            );
                                                                                        ResourceLocation ash3
                                                                                            =
                                                                                            TextureMapping.getBlockTexture(
                                                                                                PastelBlocks.ASH.get(),
                                                                                                "3"
                                                                                            );
                                                                                        ResourceLocation ash4
                                                                                            =
                                                                                            TextureMapping.getBlockTexture(
                                                                                                PastelBlocks.ASH.get(),
                                                                                                "4"
                                                                                            );
                                                                                        if (height == 8) return List.of(
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                ash),
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                ash2),
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                ash3),
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                ash4)
                                                                                        );
                                                                                        ModelTemplate layerModel
                                                                                            = new ModelTemplate(
                                                                                            Optional.of(
                                                                                                ModelLocationUtils.getModelLocation(
                                                                                                    SNOW, "_height" +
                                                                                                          height * 2
                                                                                                )), Optional.empty(),
                                                                                            TextureSlot.PARTICLE,
                                                                                            TextureSlot.TEXTURE
                                                                                        );
                                                                                        return List.of(
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                layerModel.create(
                                                                                                    PastelCommon.locate(
                                                                                                        "block" +
                                                                                                        "/ash_pile_height" +
                                                                                                        height * 2),
                                                                                                    TextureMapping.cube(
                                                                                                        ash),
                                                                                                    generators.modelOutput
                                                                                                )),
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                layerModel.create(
                                                                                                    PastelCommon.locate(
                                                                                                        "block" +
                                                                                                        "/ash2_pile_height" +
                                                                                                        height * 2),
                                                                                                    TextureMapping.cube(
                                                                                                        ash2),
                                                                                                    generators.modelOutput
                                                                                                )),
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                layerModel.create(
                                                                                                    PastelCommon.locate(
                                                                                                        "block" +
                                                                                                        "/ash3_pile_height" +
                                                                                                        height * 2),
                                                                                                    TextureMapping.cube(
                                                                                                        ash3),
                                                                                                    generators.modelOutput
                                                                                                )),
                                                                                            PastelModelHelper.createModelVariant(
                                                                                                layerModel.create(
                                                                                                    PastelCommon.locate(
                                                                                                        "block" +
                                                                                                        "/ash4_pile_height" +
                                                                                                        height * 2),
                                                                                                    TextureMapping.cube(
                                                                                                        ash4),
                                                                                                    generators.modelOutput
                                                                                                ))
                                                                                        );
                                                                                    })));


        PastelModelHelper.BLOCK.defaultNorthHorizontalFacing(
            generators, PastelBlocks.LONGING_CHIMERA, ModelLocationUtils::getModelLocation);
        PastelModelHelper.BLOCK.defaultUpFacing(generators, PastelBlocks.RESPLENDENT_BLOCK, TexturedModel.CUBE_TOP_BOTTOM);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.RESPLENDENT_CUSHION, PastelTexturedModels.CUSHION);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.RESPLENDENT_CARPET, TexturedModel.CARPET);

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.RESPLENDENT_BED);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.RESPLENDENT_BED.get())
                                                                .with(
                                                                    PastelModelHelper.createSouthDefaultHorizontalFacingVariantMap())
                                                                .with(PropertyDispatch.property(BedBlock.PART)
                                                                                      .select(
                                                                                          BedPart.HEAD,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.RESPLENDENT_BED.get(),
                                                                                              "_head"
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          BedPart.FOOT,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.RESPLENDENT_BED.get(),
                                                                                              "_foot"
                                                                                          )
                                                                                      )));


        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.JADE_PETAL_BLOCK, PastelBlocks.JADEITE_PETAL_BLOCK);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.JADE_PETAL_CARPET,
            PastelTexturedModels.carpet(b -> PastelBlocks.JADE_PETAL_BLOCK.get(), "")
        );
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.JADEITE_PETAL_CARPET,
            PastelTexturedModels.carpet(b -> PastelBlocks.JADEITE_PETAL_BLOCK.get(), "")
        );

        registerShimmerstoneLight(
            generators, PastelBlocks.STONE_SHIMMERSTONE_LIGHT, () -> PastelTextures.STONE_FLAT_LIGHT);
        registerShimmerstoneLight(
            generators, PastelBlocks.BASALT_SHIMMERSTONE_LIGHT, () -> PastelTextures.BASALT_FLAT_LIGHT);
        registerShimmerstoneLight(
            generators, PastelBlocks.CALCITE_SHIMMERSTONE_LIGHT, () -> PastelTextures.CALCITE_FLAT_LIGHT);
        registerShimmerstoneLight(
            generators, PastelBlocks.DEEPSLATE_SHIMMERSTONE_LIGHT, () -> PastelTextures.DEEPSLATE_FLAT_LIGHT);
        registerShimmerstoneLight(
            generators, PastelBlocks.BLACKSLAG_SHIMMERSTONE_LIGHT, () -> PastelTextures.BLACKSLAG_FLAT_LIGHT);
        registerShimmerstoneLight(
            generators, PastelBlocks.GRANITE_SHIMMERSTONE_LIGHT,
            () -> ModelLocationUtils.getModelLocation(POLISHED_GRANITE)
        );
        registerShimmerstoneLight(
            generators, PastelBlocks.DIORITE_SHIMMERSTONE_LIGHT,
            () -> ModelLocationUtils.getModelLocation(POLISHED_DIORITE)
        );
        registerShimmerstoneLight(
            generators, PastelBlocks.ANDESITE_SHIMMERSTONE_LIGHT,
            () -> ModelLocationUtils.getModelLocation(POLISHED_ANDESITE)
        );


        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.GLISTERING_SHOOTING_STAR, PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.FIERY_SHOOTING_STAR, PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.COLORFUL_SHOOTING_STAR, PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.PRISTINE_SHOOTING_STAR, PastelTexturedModels.SHOOTING_STAR);
        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.GEMSTONE_SHOOTING_STAR, PastelTexturedModels.SHOOTING_STAR);

        for (var head : PastelBlocks.MOB_HEADS.values()) {
            generators.blockStateOutput.accept(
                PastelModelHelper.createVariantsSupplier(head.get(), PastelModels.MOB_HEAD));
        }
        for (var head : PastelBlocks.WALL_HEADS.values()) {
            generators.blockStateOutput.accept(
                PastelModelHelper.createVariantsSupplier(head.get(), PastelModels.MOB_HEAD));
        }
    }

    public static void generateItemModels(ItemModelGenerators generators) {
        BalciteBlockModels.generateItemModels(generators);
        ColoredBlockModels.generateItemModels(generators);
        StoneLikeBlockModels.generateItemModels(generators);
        WoodLikeBlockModels.generateItemModels(generators);

        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.TOPAZ_PYLON, PastelBlocks.TOPAZ_PYLON.get(),"_head");
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.AMETHYST_PYLON, PastelBlocks.AMETHYST_PYLON.get(),"_head");
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.CITRINE_PYLON, PastelBlocks.CITRINE_PYLON.get(),"_head");
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.ONYX_PYLON, PastelBlocks.ONYX_PYLON.get(),"_head");
        PastelModelHelper.registerParentedItemModel(generators,PastelBlocks.MOONSTONE_PYLON, PastelBlocks.MOONSTONE_PYLON.get(),"_head");

        PastelModelHelper.registerItemModel(generators, PastelBlocks.PRIMORDIAL_TORCH.asItem());

        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SEMI_PERMEABLE_GLASS, GLASS);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS, TINTED_GLASS);
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.TOPAZ_SEMI_PERMEABLE_GLASS, PastelBlocks.TOPAZ_GLASS.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.AMETHYST_SEMI_PERMEABLE_GLASS, PastelBlocks.AMETHYST_GLASS.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.CITRINE_SEMI_PERMEABLE_GLASS, PastelBlocks.CITRINE_GLASS.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.ONYX_SEMI_PERMEABLE_GLASS, PastelBlocks.ONYX_GLASS.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS, PastelBlocks.MOONSTONE_GLASS.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS, PastelBlocks.RADIANT_GLASS.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.WAXED_HUMMINGSTONE, PastelBlocks.HUMMINGSTONE.get());

        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.ASH_PILE.get(), PastelBlocks.ASH_PILE.get(), "_height2");

        for (var head : PastelBlocks.MOB_HEADS.values()) {
            PastelModelHelper.registerParentedItemModel(generators, head, PastelModels.SKULL_ITEM);
        }
    }
}
