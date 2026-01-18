package earth.terrarium.pastel.data.models.block.deco;

import earth.terrarium.pastel.blocks.conditional.colored_tree.*;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import earth.terrarium.pastel.registries.client.PastelTextureMaps;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ColoredBlockModels {
    public static void generateColoredLightBlockModel(BlockModelGenerators generators, DeferredBlock<Block> block) {
        ResourceLocation off = TexturedModel.CUBE.create(block.get(), generators.modelOutput);
        ResourceLocation on = PastelModels.COLORED_LAMP_ON.createWithSuffix(
            block.get(), "_on", PastelTextureMaps.innerOuter(
                block.get(), "_on", block.get(), "_outer"),
            generators.modelOutput
        );
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BlockStateProperties.LIT, on, off)));
    }

    public static void generateColoredSporeBlossomBlockModel(
        BlockModelGenerators generators, DeferredBlock<Block> block) {
        PastelModelHelper.singleton(
            generators, block,
            TexturedModel.createDefault(
                b -> PastelTextureMaps.flowerParticle(b, "", b, ""), PastelModels.SPORE_BLOSSOM)
        );
    }

    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BLACK_PLANKS.get()).stairs(PastelBlocks.BLACK_STAIRS.get())
                                                                                .pressurePlate(
                                                                                    PastelBlocks.BLACK_PRESSURE_PLATE.get())
                                                                                .fence(PastelBlocks.BLACK_FENCE.get())
                                                                                .fenceGate(
                                                                                    PastelBlocks.BLACK_FENCE_GATE.get())
                                                                                .button(PastelBlocks.BLACK_BUTTON.get())
                                                                                .slab(PastelBlocks.BLACK_SLAB.get())
                                                                                .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BLUE_PLANKS.get()).stairs(PastelBlocks.BLUE_STAIRS.get())
                                                                               .pressurePlate(
                                                                                   PastelBlocks.BLUE_PRESSURE_PLATE.get())
                                                                               .fence(PastelBlocks.BLUE_FENCE.get())
                                                                               .fenceGate(
                                                                                   PastelBlocks.BLUE_FENCE_GATE.get())
                                                                               .button(PastelBlocks.BLUE_BUTTON.get())
                                                                               .slab(PastelBlocks.BLUE_SLAB.get())
                                                                               .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BROWN_PLANKS.get()).stairs(PastelBlocks.BROWN_STAIRS.get())
                                                                                .pressurePlate(
                                                                                    PastelBlocks.BROWN_PRESSURE_PLATE.get())
                                                                                .fence(PastelBlocks.BROWN_FENCE.get())
                                                                                .fenceGate(
                                                                                    PastelBlocks.BROWN_FENCE_GATE.get())
                                                                                .button(PastelBlocks.BROWN_BUTTON.get())
                                                                                .slab(PastelBlocks.BROWN_SLAB.get())
                                                                                .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.CYAN_PLANKS.get()).stairs(PastelBlocks.CYAN_STAIRS.get())
                                                                               .pressurePlate(
                                                                                   PastelBlocks.CYAN_PRESSURE_PLATE.get())
                                                                               .fence(PastelBlocks.CYAN_FENCE.get())
                                                                               .fenceGate(
                                                                                   PastelBlocks.CYAN_FENCE_GATE.get())
                                                                               .button(PastelBlocks.CYAN_BUTTON.get())
                                                                               .slab(PastelBlocks.CYAN_SLAB.get())
                                                                               .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.GRAY_PLANKS.get()).stairs(PastelBlocks.GRAY_STAIRS.get())
                                                                               .pressurePlate(
                                                                                   PastelBlocks.GRAY_PRESSURE_PLATE.get())
                                                                               .fence(PastelBlocks.GRAY_FENCE.get())
                                                                               .fenceGate(
                                                                                   PastelBlocks.GRAY_FENCE_GATE.get())
                                                                               .button(PastelBlocks.GRAY_BUTTON.get())
                                                                               .slab(PastelBlocks.GRAY_SLAB.get())
                                                                               .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.GREEN_PLANKS.get()).stairs(PastelBlocks.GREEN_STAIRS.get())
                                                                                .pressurePlate(
                                                                                    PastelBlocks.GREEN_PRESSURE_PLATE.get())
                                                                                .fence(PastelBlocks.GREEN_FENCE.get())
                                                                                .fenceGate(
                                                                                    PastelBlocks.GREEN_FENCE_GATE.get())
                                                                                .button(PastelBlocks.GREEN_BUTTON.get())
                                                                                .slab(PastelBlocks.GREEN_SLAB.get())
                                                                                .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.LIGHT_BLUE_PLANKS.get()).stairs(
                                                                                         PastelBlocks.LIGHT_BLUE_STAIRS.get())
                                                                                     .pressurePlate(
                                                                                         PastelBlocks.LIGHT_BLUE_PRESSURE_PLATE.get())
                                                                                     .fence(
                                                                                         PastelBlocks.LIGHT_BLUE_FENCE.get())
                                                                                     .fenceGate(
                                                                                         PastelBlocks.LIGHT_BLUE_FENCE_GATE.get())
                                                                                     .button(
                                                                                         PastelBlocks.LIGHT_BLUE_BUTTON.get())
                                                                                     .slab(
                                                                                         PastelBlocks.LIGHT_BLUE_SLAB.get())
                                                                                     .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.LIGHT_GRAY_PLANKS.get()).stairs(
                                                                                         PastelBlocks.LIGHT_GRAY_STAIRS.get())
                                                                                     .pressurePlate(
                                                                                         PastelBlocks.LIGHT_GRAY_PRESSURE_PLATE.get())
                                                                                     .fence(
                                                                                         PastelBlocks.LIGHT_GRAY_FENCE.get())
                                                                                     .fenceGate(
                                                                                         PastelBlocks.LIGHT_GRAY_FENCE_GATE.get())
                                                                                     .button(
                                                                                         PastelBlocks.LIGHT_GRAY_BUTTON.get())
                                                                                     .slab(
                                                                                         PastelBlocks.LIGHT_GRAY_SLAB.get())
                                                                                     .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.LIME_PLANKS.get()).stairs(PastelBlocks.LIME_STAIRS.get())
                                                                               .pressurePlate(
                                                                                   PastelBlocks.LIME_PRESSURE_PLATE.get())
                                                                               .fence(PastelBlocks.LIME_FENCE.get())
                                                                               .fenceGate(
                                                                                   PastelBlocks.LIME_FENCE_GATE.get())
                                                                               .button(PastelBlocks.LIME_BUTTON.get())
                                                                               .slab(PastelBlocks.LIME_SLAB.get())
                                                                               .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.MAGENTA_PLANKS.get()).stairs(
                                                                                      PastelBlocks.MAGENTA_STAIRS.get())
                                                                                  .pressurePlate(
                                                                                      PastelBlocks.MAGENTA_PRESSURE_PLATE.get())
                                                                                  .fence(
                                                                                      PastelBlocks.MAGENTA_FENCE.get())
                                                                                  .fenceGate(
                                                                                      PastelBlocks.MAGENTA_FENCE_GATE.get())
                                                                                  .button(
                                                                                      PastelBlocks.MAGENTA_BUTTON.get())
                                                                                  .slab(PastelBlocks.MAGENTA_SLAB.get())
                                                                                  .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.ORANGE_PLANKS.get()).stairs(
                                                                                     PastelBlocks.ORANGE_STAIRS.get())
                                                                                 .pressurePlate(
                                                                                     PastelBlocks.ORANGE_PRESSURE_PLATE.get())
                                                                                 .fence(PastelBlocks.ORANGE_FENCE.get())
                                                                                 .fenceGate(
                                                                                     PastelBlocks.ORANGE_FENCE_GATE.get())
                                                                                 .button(
                                                                                     PastelBlocks.ORANGE_BUTTON.get())
                                                                                 .slab(PastelBlocks.ORANGE_SLAB.get())
                                                                                 .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.PINK_PLANKS.get()).stairs(PastelBlocks.PINK_STAIRS.get())
                                                                               .pressurePlate(
                                                                                   PastelBlocks.PINK_PRESSURE_PLATE.get())
                                                                               .fence(PastelBlocks.PINK_FENCE.get())
                                                                               .fenceGate(
                                                                                   PastelBlocks.PINK_FENCE_GATE.get())
                                                                               .button(PastelBlocks.PINK_BUTTON.get())
                                                                               .slab(PastelBlocks.PINK_SLAB.get())
                                                                               .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.PURPLE_PLANKS.get()).stairs(
                                                                                     PastelBlocks.PURPLE_STAIRS.get())
                                                                                 .pressurePlate(
                                                                                     PastelBlocks.PURPLE_PRESSURE_PLATE.get())
                                                                                 .fence(PastelBlocks.PURPLE_FENCE.get())
                                                                                 .fenceGate(
                                                                                     PastelBlocks.PURPLE_FENCE_GATE.get())
                                                                                 .button(
                                                                                     PastelBlocks.PURPLE_BUTTON.get())
                                                                                 .slab(PastelBlocks.PURPLE_SLAB.get())
                                                                                 .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.RED_PLANKS.get()).stairs(PastelBlocks.RED_STAIRS.get())
                                                                              .pressurePlate(
                                                                                  PastelBlocks.RED_PRESSURE_PLATE.get())
                                                                              .fence(PastelBlocks.RED_FENCE.get())
                                                                              .fenceGate(
                                                                                  PastelBlocks.RED_FENCE_GATE.get())
                                                                              .button(PastelBlocks.RED_BUTTON.get())
                                                                              .slab(PastelBlocks.RED_SLAB.get())
                                                                              .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.WHITE_PLANKS.get()).stairs(PastelBlocks.WHITE_STAIRS.get())
                                                                                .pressurePlate(
                                                                                    PastelBlocks.WHITE_PRESSURE_PLATE.get())
                                                                                .fence(PastelBlocks.WHITE_FENCE.get())
                                                                                .fenceGate(
                                                                                    PastelBlocks.WHITE_FENCE_GATE.get())
                                                                                .button(PastelBlocks.WHITE_BUTTON.get())
                                                                                .slab(PastelBlocks.WHITE_SLAB.get())
                                                                                .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.YELLOW_PLANKS.get()).stairs(
                                                                                     PastelBlocks.YELLOW_STAIRS.get())
                                                                                 .pressurePlate(
                                                                                     PastelBlocks.YELLOW_PRESSURE_PLATE.get())
                                                                                 .fence(PastelBlocks.YELLOW_FENCE.get())
                                                                                 .fenceGate(
                                                                                     PastelBlocks.YELLOW_FENCE_GATE.get())
                                                                                 .button(
                                                                                     PastelBlocks.YELLOW_BUTTON.get())
                                                                                 .slab(PastelBlocks.YELLOW_SLAB.get())
                                                                                 .getFamily()
        );

        PastelModelHelper.singleton(generators, PastelBlocks.WHITE_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.ORANGE_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.MAGENTA_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.LIGHT_BLUE_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.YELLOW_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.LIME_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.PINK_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.GRAY_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.LIGHT_GRAY_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.CYAN_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.PURPLE_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.BLUE_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.BROWN_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.GREEN_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.RED_CUSHION, PastelTexturedModels.TINTED_CUSHION);
        PastelModelHelper.singleton(generators, PastelBlocks.BLACK_CUSHION, PastelTexturedModels.TINTED_CUSHION);

        PastelModelHelper.cross(generators, PastelBlocks.BLACK_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.BLUE_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.BROWN_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.CYAN_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.GRAY_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.GREEN_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.LIGHT_BLUE_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.LIGHT_GRAY_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.LIME_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.MAGENTA_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.ORANGE_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.PINK_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.PURPLE_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.RED_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.WHITE_SAPLING);
        PastelModelHelper.cross(generators, PastelBlocks.YELLOW_SAPLING);


        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_BLACK_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_BLUE_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_BROWN_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_CYAN_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_GRAY_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_GREEN_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_LIGHT_BLUE_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_LIGHT_GRAY_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_LIME_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_MAGENTA_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_ORANGE_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_PINK_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_PURPLE_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_RED_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_WHITE_SAPLING, false);
        PastelModelHelper.pottedPlant(generators, PastelBlocks.POTTED_YELLOW_SAPLING, false);

        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_BLACK_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_BLUE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_BROWN_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_CYAN_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_GRAY_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_GREEN_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_LIGHT_BLUE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_LIGHT_GRAY_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_LIME_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_MAGENTA_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_ORANGE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_PINK_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_PURPLE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_RED_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_WHITE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.STRIPPED_YELLOW_LOG);

        PastelModelHelper.log(generators, PastelBlocks.BLACK_LOG);
        PastelModelHelper.log(generators, PastelBlocks.BLUE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.BROWN_LOG);
        PastelModelHelper.log(generators, PastelBlocks.CYAN_LOG);
        PastelModelHelper.log(generators, PastelBlocks.GRAY_LOG);
        PastelModelHelper.log(generators, PastelBlocks.GREEN_LOG);
        PastelModelHelper.log(generators, PastelBlocks.LIGHT_BLUE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.LIGHT_GRAY_LOG);
        PastelModelHelper.log(generators, PastelBlocks.LIME_LOG);
        PastelModelHelper.log(generators, PastelBlocks.MAGENTA_LOG);
        PastelModelHelper.log(generators, PastelBlocks.ORANGE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.PINK_LOG);
        PastelModelHelper.log(generators, PastelBlocks.PURPLE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.RED_LOG);
        PastelModelHelper.log(generators, PastelBlocks.WHITE_LOG);
        PastelModelHelper.log(generators, PastelBlocks.YELLOW_LOG);

        PastelModelHelper.wood(generators, PastelBlocks.BLACK_WOOD, PastelBlocks.BLACK_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.BLUE_WOOD, PastelBlocks.BLUE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.BROWN_WOOD, PastelBlocks.BROWN_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.CYAN_WOOD, PastelBlocks.CYAN_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.GRAY_WOOD, PastelBlocks.GRAY_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.GREEN_WOOD, PastelBlocks.GREEN_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.LIGHT_BLUE_WOOD, PastelBlocks.LIGHT_BLUE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.LIGHT_GRAY_WOOD, PastelBlocks.LIGHT_GRAY_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.LIME_WOOD, PastelBlocks.LIME_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.MAGENTA_WOOD, PastelBlocks.MAGENTA_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.ORANGE_WOOD, PastelBlocks.ORANGE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.PINK_WOOD, PastelBlocks.PINK_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.PURPLE_WOOD, PastelBlocks.PURPLE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.RED_WOOD, PastelBlocks.RED_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.WHITE_WOOD, PastelBlocks.WHITE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.YELLOW_WOOD, PastelBlocks.YELLOW_LOG);

        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_BLACK_WOOD, PastelBlocks.STRIPPED_BLACK_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_BLUE_WOOD, PastelBlocks.STRIPPED_BLUE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_BROWN_WOOD, PastelBlocks.STRIPPED_BROWN_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_CYAN_WOOD, PastelBlocks.STRIPPED_CYAN_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_GRAY_WOOD, PastelBlocks.STRIPPED_GRAY_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_GREEN_WOOD, PastelBlocks.STRIPPED_GREEN_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_LIGHT_BLUE_WOOD, PastelBlocks.STRIPPED_LIGHT_BLUE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_LIGHT_GRAY_WOOD, PastelBlocks.STRIPPED_LIGHT_GRAY_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_LIME_WOOD, PastelBlocks.STRIPPED_LIME_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_MAGENTA_WOOD, PastelBlocks.STRIPPED_MAGENTA_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_ORANGE_WOOD, PastelBlocks.STRIPPED_ORANGE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_PINK_WOOD, PastelBlocks.STRIPPED_PINK_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_PURPLE_WOOD, PastelBlocks.STRIPPED_PURPLE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_RED_WOOD, PastelBlocks.STRIPPED_RED_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_WHITE_WOOD, PastelBlocks.STRIPPED_WHITE_LOG);
        PastelModelHelper.wood(generators, PastelBlocks.STRIPPED_YELLOW_WOOD, PastelBlocks.STRIPPED_YELLOW_LOG);

        PastelModelHelper.singleton(generators, PastelBlocks.BLACK_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.BLUE_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.BROWN_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.CYAN_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.GRAY_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.GREEN_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.LIGHT_BLUE_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.LIGHT_GRAY_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.LIME_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.MAGENTA_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.ORANGE_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.PINK_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.PURPLE_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.RED_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.WHITE_LEAVES, TexturedModel.LEAVES);
        PastelModelHelper.singleton(generators, PastelBlocks.YELLOW_LEAVES, TexturedModel.LEAVES);

        PastelModelHelper.simple(generators, PastelBlocks.BLACK_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.BLUE_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.BROWN_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.CYAN_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.GRAY_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.GREEN_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.LIGHT_BLUE_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.LIGHT_GRAY_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.LIME_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.MAGENTA_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.ORANGE_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.PINK_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.PURPLE_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.RED_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.WHITE_GLOWBLOCK);
        PastelModelHelper.simple(generators, PastelBlocks.YELLOW_GLOWBLOCK);

        generateColoredLightBlockModel(generators, PastelBlocks.BLACK_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.BLUE_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.BROWN_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.CYAN_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.GRAY_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.GREEN_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.LIGHT_BLUE_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.LIGHT_GRAY_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.LIME_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.MAGENTA_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.ORANGE_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.PINK_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.PURPLE_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.RED_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.WHITE_LAMP);
        generateColoredLightBlockModel(generators, PastelBlocks.YELLOW_LAMP);

        PastelModelHelper.simple(
            generators, PastelBlocks.BLACK_BLOCK, PastelBlocks.BLUE_BLOCK, PastelBlocks.BROWN_BLOCK,
            PastelBlocks.CYAN_BLOCK, PastelBlocks.GRAY_BLOCK, PastelBlocks.GREEN_BLOCK, PastelBlocks.LIGHT_BLUE_BLOCK,
            PastelBlocks.LIGHT_GRAY_BLOCK, PastelBlocks.LIME_BLOCK, PastelBlocks.MAGENTA_BLOCK,
            PastelBlocks.ORANGE_BLOCK, PastelBlocks.PINK_BLOCK, PastelBlocks.PURPLE_BLOCK, PastelBlocks.RED_BLOCK,
            PastelBlocks.WHITE_BLOCK, PastelBlocks.YELLOW_BLOCK
        );

        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.BLACK_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.BLUE_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.BROWN_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.CYAN_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.GRAY_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.GREEN_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.LIGHT_BLUE_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.LIGHT_GRAY_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.LIME_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.MAGENTA_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.ORANGE_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.PINK_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.PURPLE_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.RED_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.WHITE_SPORE_BLOSSOM);
        generateColoredSporeBlossomBlockModel(generators, PastelBlocks.YELLOW_SPORE_BLOSSOM);
    }

    public static void generateItemModels(ItemModelGenerators generators) {
        // colored fences need inventory models, colored saplings need blocktextureditemmodels
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.BLACK_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.BLUE_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.BROWN_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.CYAN_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.GRAY_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.GREEN_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.LIGHT_BLUE_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.LIGHT_GRAY_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.LIME_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.MAGENTA_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.ORANGE_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.PINK_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.PURPLE_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.RED_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.WHITE_FENCE);
        PastelModelHelper.generateInventoryParentedItemModel(generators, PastelBlocks.YELLOW_FENCE);

        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.BLACK_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.BLUE_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.BROWN_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.CYAN_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.GRAY_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.GREEN_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.LIGHT_BLUE_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.LIGHT_GRAY_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.LIME_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.MAGENTA_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.ORANGE_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.PINK_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.PURPLE_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.RED_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.WHITE_SAPLING.get());
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.YELLOW_SAPLING.get());
    }
}
