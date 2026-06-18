package earth.terrarium.pastel.data.models.block.deco;

import earth.terrarium.pastel.blocks.decoration.DiagonalBlock;
import earth.terrarium.pastel.blocks.decoration.FlexLanternBlock;
import earth.terrarium.pastel.blocks.imbrifer.WeepingGalaFrondsTipBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTextureMaps;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;

public class WoodLikeBlockModels {
    // noxshrooms are close enough to saplings
    public static void generateNoxshroomBlockModel(BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput
            .accept(
                MultiVariantGenerator
                    .multiVariant(
                        block.get(),
                        PastelModelHelper
                            .createModelVariant(
                                PastelTexturedModels
                                    .cross(b -> b, "_type_1")
                                    .createWithSuffix(
                                        block.get(),
                                        "_type_1",
                                        generators.modelOutput
                                    )
                            ),
                        PastelModelHelper
                            .createModelVariant(
                                PastelTexturedModels
                                    .cross(b -> b, "_type_2")
                                    .createWithSuffix(
                                        block.get(),
                                        "_type_2",
                                        generators.modelOutput
                                    )
                            ),
                        PastelModelHelper
                            .createModelVariant(
                                PastelTexturedModels
                                    .cross(b -> b, "_type_3")
                                    .createWithSuffix(block.get(), "_type_3", generators.modelOutput)
                            )
                    )
            );
    }

    public static void generateNoxwoodLightBlockModel(
        BlockModelGenerators generators,
        DeferredBlock<Block> block,
        DeferredBlock<Block> gillsBlock
    ) {
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                block,
                TexturedModel
                    .createDefault(
                        b -> PastelTextureMaps.sideTopInside(b, "", b, "_top", gillsBlock.get(), ""),
                        PastelModels.MULTILAYER_LIGHT
                    )
            );
    }

    public static void generateNoxwoodLanternBlockModel(BlockModelGenerators generators, DeferredBlock<Block> block) {
        generators.blockStateOutput
            .accept(
                MultiVariantGenerator
                    .multiVariant(block.get())
                    .with(
                        PropertyDispatch
                            .properties(
                                BlockStateProperties.HANGING,
                                DiagonalBlock.DIAGONAL,
                                FlexLanternBlock.TALL
                            )
                            .generate(
                                (hanging, diagonal, tall) -> {
                                    String suffix = (hanging
                                        ? "_hanging"
                                        : "") + (diagonal
                                            ? "_diagonal"
                                            : "") + (tall
                                                ? "_tall"
                                                : "_small");
                                    return PastelModelHelper
                                        .createModelVariant(
                                            PastelModels
                                                .noxwoodLantern(
                                                    suffix
                                                )
                                                .createWithSuffix(
                                                    block.get(),
                                                    suffix,
                                                    TextureMapping
                                                        .cube(
                                                            block.get()
                                                        ),
                                                    generators.modelOutput
                                                )
                                        );
                                }
                            )
                    )
            );
    }

    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper
            .registerBlockFamily(
                generators,
                new BlockFamily.Builder(PastelBlocks.SLATE_NOXWOOD_PLANKS.get())
                    .stairs(
                        PastelBlocks.SLATE_NOXWOOD_STAIRS.get()
                    )
                    .slab(
                        PastelBlocks.SLATE_NOXWOOD_SLAB.get()
                    )
                    .fence(
                        PastelBlocks.SLATE_NOXWOOD_FENCE.get()
                    )
                    .fenceGate(
                        PastelBlocks.SLATE_NOXWOOD_FENCE_GATE.get()
                    )
                    .door(
                        PastelBlocks.SLATE_NOXWOOD_DOOR.get()
                    )
                    .trapdoor(
                        PastelBlocks.SLATE_NOXWOOD_TRAPDOOR.get()
                    )
                    .button(
                        PastelBlocks.SLATE_NOXWOOD_BUTTON.get()
                    )
                    .pressurePlate(
                        PastelBlocks.SLATE_NOXWOOD_PRESSURE_PLATE.get()
                    )
                    .getFamily()
            );
        PastelModelHelper
            .registerBlockFamily(
                generators,
                new BlockFamily.Builder(PastelBlocks.EBONY_NOXWOOD_PLANKS.get())
                    .stairs(
                        PastelBlocks.EBONY_NOXWOOD_STAIRS.get()
                    )
                    .slab(
                        PastelBlocks.EBONY_NOXWOOD_SLAB.get()
                    )
                    .fence(
                        PastelBlocks.EBONY_NOXWOOD_FENCE.get()
                    )
                    .fenceGate(
                        PastelBlocks.EBONY_NOXWOOD_FENCE_GATE.get()
                    )
                    .door(
                        PastelBlocks.EBONY_NOXWOOD_DOOR.get()
                    )
                    .trapdoor(
                        PastelBlocks.EBONY_NOXWOOD_TRAPDOOR.get()
                    )
                    .button(
                        PastelBlocks.EBONY_NOXWOOD_BUTTON.get()
                    )
                    .pressurePlate(
                        PastelBlocks.EBONY_NOXWOOD_PRESSURE_PLATE.get()
                    )
                    .getFamily()
            );
        PastelModelHelper
            .registerBlockFamily(
                generators,
                new BlockFamily.Builder(PastelBlocks.IVORY_NOXWOOD_PLANKS.get())
                    .stairs(
                        PastelBlocks.IVORY_NOXWOOD_STAIRS.get()
                    )
                    .slab(
                        PastelBlocks.IVORY_NOXWOOD_SLAB.get()
                    )
                    .fence(
                        PastelBlocks.IVORY_NOXWOOD_FENCE.get()
                    )
                    .fenceGate(
                        PastelBlocks.IVORY_NOXWOOD_FENCE_GATE.get()
                    )
                    .door(
                        PastelBlocks.IVORY_NOXWOOD_DOOR.get()
                    )
                    .trapdoor(
                        PastelBlocks.IVORY_NOXWOOD_TRAPDOOR.get()
                    )
                    .button(
                        PastelBlocks.IVORY_NOXWOOD_BUTTON.get()
                    )
                    .pressurePlate(
                        PastelBlocks.IVORY_NOXWOOD_PRESSURE_PLATE.get()
                    )
                    .getFamily()
            );
        PastelModelHelper
            .registerBlockFamily(
                generators,
                new BlockFamily.Builder(PastelBlocks.CHESTNUT_NOXWOOD_PLANKS.get())
                    .stairs(
                        PastelBlocks.CHESTNUT_NOXWOOD_STAIRS.get()
                    )
                    .slab(
                        PastelBlocks.CHESTNUT_NOXWOOD_SLAB.get()
                    )
                    .fence(
                        PastelBlocks.CHESTNUT_NOXWOOD_FENCE.get()
                    )
                    .fenceGate(
                        PastelBlocks.CHESTNUT_NOXWOOD_FENCE_GATE.get()
                    )
                    .door(
                        PastelBlocks.CHESTNUT_NOXWOOD_DOOR.get()
                    )
                    .trapdoor(
                        PastelBlocks.CHESTNUT_NOXWOOD_TRAPDOOR.get()
                    )
                    .button(
                        PastelBlocks.CHESTNUT_NOXWOOD_BUTTON.get()
                    )
                    .pressurePlate(
                        PastelBlocks.CHESTNUT_NOXWOOD_PRESSURE_PLATE.get()
                    )
                    .getFamily()
            );
        PastelModelHelper
            .registerBlockFamily(
                generators,
                new BlockFamily.Builder(PastelBlocks.WEEPING_GALA_PLANKS.get())
                    .stairs(
                        PastelBlocks.WEEPING_GALA_STAIRS.get()
                    )
                    .slab(
                        PastelBlocks.WEEPING_GALA_SLAB.get()
                    )
                    .fence(
                        PastelBlocks.WEEPING_GALA_FENCE.get()
                    )
                    .fenceGate(
                        PastelBlocks.WEEPING_GALA_FENCE_GATE.get()
                    )
                    .door(
                        PastelBlocks.WEEPING_GALA_DOOR.get()
                    )
                    .trapdoor(
                        PastelBlocks.WEEPING_GALA_TRAPDOOR.get()
                    )
                    .button(
                        PastelBlocks.WEEPING_GALA_BUTTON.get()
                    )
                    .pressurePlate(
                        PastelBlocks.WEEPING_GALA_PRESSURE_PLATE.get()
                    )
                    .getFamily()
            );

        generateNoxshroomBlockModel(generators, PastelBlocks.SLATE_NOXSHROOM);
        generateNoxshroomBlockModel(generators, PastelBlocks.EBONY_NOXSHROOM);
        generateNoxshroomBlockModel(generators, PastelBlocks.IVORY_NOXSHROOM);
        generateNoxshroomBlockModel(generators, PastelBlocks.CHESTNUT_NOXSHROOM);

        PastelModelHelper.BLOCK.pottedPlant(generators, PastelBlocks.POTTED_SLATE_NOXSHROOM, false);
        PastelModelHelper.BLOCK.pottedPlant(generators, PastelBlocks.POTTED_EBONY_NOXSHROOM, false);
        PastelModelHelper.BLOCK.pottedPlant(generators, PastelBlocks.POTTED_IVORY_NOXSHROOM, false);
        PastelModelHelper.BLOCK.pottedPlant(generators, PastelBlocks.POTTED_CHESTNUT_NOXSHROOM, false);

        PastelModelHelper.BLOCK
            .axisRotated(generators, PastelBlocks.STRIPPED_SLATE_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.SLATE_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.SLATE_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.SLATE_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.SLATE_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.SLATE_NOXCAP_BLOCK, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.SLATE_NOXCAP_GILLS, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.SLATE_NOXWOOD_PILLAR, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.redstoneLamp(generators, PastelBlocks.SLATE_NOXWOOD_LAMP);
        generateNoxwoodLightBlockModel(generators, PastelBlocks.SLATE_NOXWOOD_LIGHT, PastelBlocks.SLATE_NOXCAP_GILLS);
        PastelModelHelper.BLOCK
            .barrellike(
                generators,
                PastelBlocks.SLATE_NOXWOOD_AMPHORA,
                b -> PastelBlocks.SLATE_NOXWOOD_LIGHT.get(),
                "_top"
            );
        generateNoxwoodLanternBlockModel(generators, PastelBlocks.SLATE_NOXWOOD_LANTERN);

        PastelModelHelper.BLOCK
            .axisRotated(generators, PastelBlocks.STRIPPED_EBONY_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.EBONY_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.EBONY_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.EBONY_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.EBONY_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.EBONY_NOXCAP_BLOCK, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.EBONY_NOXCAP_GILLS, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.EBONY_NOXWOOD_PILLAR, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.redstoneLamp(generators, PastelBlocks.EBONY_NOXWOOD_LAMP);
        generateNoxwoodLightBlockModel(generators, PastelBlocks.EBONY_NOXWOOD_LIGHT, PastelBlocks.EBONY_NOXCAP_GILLS);
        PastelModelHelper.BLOCK
            .barrellike(
                generators,
                PastelBlocks.EBONY_NOXWOOD_AMPHORA,
                b -> PastelBlocks.EBONY_NOXWOOD_LIGHT.get(),
                "_top"
            );
        generateNoxwoodLanternBlockModel(generators, PastelBlocks.EBONY_NOXWOOD_LANTERN);

        PastelModelHelper.BLOCK
            .axisRotated(generators, PastelBlocks.STRIPPED_IVORY_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.IVORY_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.IVORY_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.IVORY_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.IVORY_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.IVORY_NOXCAP_BLOCK, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.IVORY_NOXCAP_GILLS, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.IVORY_NOXWOOD_PILLAR, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.redstoneLamp(generators, PastelBlocks.IVORY_NOXWOOD_LAMP);
        generateNoxwoodLightBlockModel(generators, PastelBlocks.IVORY_NOXWOOD_LIGHT, PastelBlocks.IVORY_NOXCAP_GILLS);
        PastelModelHelper.BLOCK
            .barrellike(
                generators,
                PastelBlocks.IVORY_NOXWOOD_AMPHORA,
                b -> PastelBlocks.IVORY_NOXWOOD_LIGHT.get(),
                "_top"
            );
        generateNoxwoodLanternBlockModel(generators, PastelBlocks.IVORY_NOXWOOD_LANTERN);

        PastelModelHelper.BLOCK
            .axisRotated(generators, PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.CHESTNUT_NOXCAP_STEM, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.CHESTNUT_NOXCAP_HYPHAE,
                PastelTexturedModels
                    .cubeColumn(
                        b -> PastelBlocks.CHESTNUT_NOXCAP_STEM.get(),
                        "",
                        b -> PastelBlocks.CHESTNUT_NOXCAP_STEM.get(),
                        ""
                    )
            );
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.CHESTNUT_NOXCAP_BLOCK, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.CHESTNUT_NOXCAP_GILLS, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.CHESTNUT_NOXWOOD_PILLAR, TexturedModel.COLUMN_ALT);
        PastelModelHelper.BLOCK.redstoneLamp(generators, PastelBlocks.CHESTNUT_NOXWOOD_LAMP);
        generateNoxwoodLightBlockModel(
            generators,
            PastelBlocks.CHESTNUT_NOXWOOD_LIGHT,
            PastelBlocks.CHESTNUT_NOXCAP_GILLS
        );
        PastelModelHelper.BLOCK
            .barrellike(
                generators,
                PastelBlocks.CHESTNUT_NOXWOOD_AMPHORA,
                b -> PastelBlocks.CHESTNUT_NOXWOOD_LIGHT.get(),
                "_top"
            );
        generateNoxwoodLanternBlockModel(generators, PastelBlocks.CHESTNUT_NOXWOOD_LANTERN);

        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.WEEPING_GALA_SPRIG);
        PastelModelHelper.BLOCK.pottedPlant(generators, PastelBlocks.POTTED_WEEPING_GALA_SPRIG, false);
        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.WEEPING_GALA_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.BLOCK.log(generators, PastelBlocks.WEEPING_GALA_LOG);
        PastelModelHelper.BLOCK.log(generators, PastelBlocks.STRIPPED_WEEPING_GALA_LOG);
        PastelModelHelper.BLOCK.wood(generators, PastelBlocks.WEEPING_GALA_WOOD, PastelBlocks.WEEPING_GALA_LOG);
        PastelModelHelper.BLOCK
            .wood(
                generators,
                PastelBlocks.STRIPPED_WEEPING_GALA_WOOD,
                PastelBlocks.STRIPPED_WEEPING_GALA_LOG
            );
        PastelModelHelper.BLOCK.cross(generators, PastelBlocks.WEEPING_GALA_FRONDS);
        generators.blockStateOutput
            .accept(
                MultiVariantGenerator
                    .multiVariant(PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get())
                    .with(
                        PropertyDispatch
                            .property(WeepingGalaFrondsTipBlock.FORM)
                            .select(
                                WeepingGalaFrondsTipBlock.Form.TIP,
                                PastelModelHelper
                                    .createModelVariant(
                                        PastelTexturedModels
                                            .cross(
                                                b -> PastelBlocks.WEEPING_GALA_FRONDS.get(),
                                                "_tip"
                                            )
                                            .create(
                                                PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get(),
                                                generators.modelOutput
                                            )
                                    )
                            )
                            .select(
                                WeepingGalaFrondsTipBlock.Form.SPRIG,
                                PastelModelHelper
                                    .createModelVariant(
                                        PastelTexturedModels
                                            .cross(
                                                b -> PastelBlocks.WEEPING_GALA_FRONDS.get(),
                                                "_sprig"
                                            )
                                            .createWithSuffix(
                                                PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get(),
                                                "_sprig",
                                                generators.modelOutput
                                            )
                                    )
                            )
                            .select(
                                WeepingGalaFrondsTipBlock.Form.RESIN,
                                PastelModelHelper
                                    .createModelVariant(
                                        PastelTexturedModels
                                            .cross(
                                                b -> PastelBlocks.WEEPING_GALA_FRONDS.get(),
                                                "_sprig_resin"
                                            )
                                            .createWithSuffix(
                                                PastelBlocks.WEEPING_GALA_FRONDS_PLANT.get(),
                                                "_resin",
                                                generators.modelOutput
                                            )
                                    )
                            )
                    )
            );
        PastelModelHelper.BLOCK.axisRotated(generators, PastelBlocks.WEEPING_GALA_PILLAR, TexturedModel.COLUMN);
        PastelModelHelper.BLOCK.barrellike(generators, PastelBlocks.WEEPING_GALA_BARREL, b -> b, "_bottom");
        PastelModelHelper.BLOCK.barrellike(generators, PastelBlocks.WEEPING_GALA_AMPHORA, b -> b, "_bottom");
        generators.blockStateOutput
            .accept(
                MultiVariantGenerator
                    .multiVariant(PastelBlocks.WEEPING_GALA_LANTERN.get())
                    .with(
                        PropertyDispatch
                            .property(
                                BlockStateProperties.HANGING
                            )
                            .select(false, Variant.variant())
                            .select(
                                true,
                                Variant
                                    .variant()
                                    .with(
                                        VariantProperties.X_ROT,
                                        VariantProperties.Rotation.R180
                                    )
                            )
                    )
                    .with(
                        PropertyDispatch
                            .properties(
                                DiagonalBlock.DIAGONAL,
                                FlexLanternBlock.TALL
                            )
                            .generate(
                                (diagonal, tall) -> PastelModelHelper
                                    .createModelVariant(
                                        PastelTexturedModels
                                            .baseTransLantern(
                                                diagonal,
                                                tall
                                            )
                                            .createWithSuffix(
                                                PastelBlocks.WEEPING_GALA_LANTERN.get(),
                                                (diagonal
                                                    ? "_diagonal"
                                                    : "") + (tall
                                                        ? "_tall"
                                                        : "_small"),
                                                generators.modelOutput
                                            )
                                    )
                            )
                    )
            );
        PastelModelHelper.BLOCK.redstoneLamp(generators, PastelBlocks.WEEPING_GALA_LAMP);
        PastelModelHelper.BLOCK
            .axisRotated(
                generators,
                PastelBlocks.WEEPING_GALA_LIGHT,
                PastelTexturedModels.BASE_TRANS_LIGHT_CORE
            );
    }

    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.SLATE_NOXSHROOM.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.EBONY_NOXSHROOM.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.IVORY_NOXSHROOM.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.CHESTNUT_NOXSHROOM.get());

        PastelModelHelper.registerItemModel(generators, PastelBlocks.WEEPING_GALA_SPRIG.asItem());

        PastelModelHelper.registerItemModel(generators, PastelBlocks.SLATE_NOXWOOD_LANTERN.asItem(), "_item");
        PastelModelHelper.registerItemModel(generators, PastelBlocks.EBONY_NOXWOOD_LANTERN.asItem(), "_item");
        PastelModelHelper.registerItemModel(generators, PastelBlocks.IVORY_NOXWOOD_LANTERN.asItem(), "_item");
        PastelModelHelper.registerItemModel(generators, PastelBlocks.CHESTNUT_NOXWOOD_LANTERN.asItem(), "_item");

        PastelModelHelper.registerItemModel(generators, PastelBlocks.WEEPING_GALA_LANTERN.asItem(), "_item");

        PastelModelHelper
            .registerParentedItemModel(
                generators,
                PastelBlocks.WEEPING_GALA_LAMP,
                PastelBlocks.WEEPING_GALA_LAMP.get(),
                "_off"
            );
        PastelModelHelper
            .registerParentedItemModel(
                generators,
                PastelBlocks.SLATE_NOXWOOD_LAMP,
                PastelBlocks.SLATE_NOXWOOD_LAMP.get(),
                "_off"
            );
        PastelModelHelper
            .registerParentedItemModel(
                generators,
                PastelBlocks.EBONY_NOXWOOD_LAMP,
                PastelBlocks.EBONY_NOXWOOD_LAMP.get(),
                "_off"
            );
        PastelModelHelper
            .registerParentedItemModel(
                generators,
                PastelBlocks.IVORY_NOXWOOD_LAMP,
                PastelBlocks.IVORY_NOXWOOD_LAMP.get(),
                "_off"
            );
        PastelModelHelper
            .registerParentedItemModel(
                generators,
                PastelBlocks.CHESTNUT_NOXWOOD_LAMP,
                PastelBlocks.CHESTNUT_NOXWOOD_LAMP.get(),
                "_off"
            );
    }
}
