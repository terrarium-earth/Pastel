package earth.terrarium.pastel.data.models.block.deco;

import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelTextures;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.*;

public class BalciteBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(Blocks.SMOOTH_BASALT).stairs(PastelBlocks.SMOOTH_BASALT_STAIRS.get())
                                                                     .slab(PastelBlocks.SMOOTH_BASALT_SLAB.get())
                                                                     .wall(PastelBlocks.SMOOTH_BASALT_WALL.get())
                                                                     .getFamily(), TexturedModel.CUBE
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.POLISHED_BASALT.get()).stairs(
                                                                                       PastelBlocks.POLISHED_BASALT_STAIRS.get())
                                                                                   .slab(
                                                                                       PastelBlocks.POLISHED_BASALT_SLAB.get())
                                                                                   .wall(
                                                                                       PastelBlocks.POLISHED_BASALT_WALL.get())
                                                                                   .button(
                                                                                       PastelBlocks.POLISHED_BASALT_BUTTON.get())
                                                                                   .pressurePlate(
                                                                                       PastelBlocks.POLISHED_BASALT_PRESSURE_PLATE.get())
                                                                                   .chiseled(
                                                                                       PastelBlocks.CHISELED_POLISHED_BASALT.get())
                                                                                   .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BASALT_BRICKS.get()).stairs(
                                                                                     PastelBlocks.BASALT_BRICK_STAIRS.get())
                                                                                 .slab(
                                                                                     PastelBlocks.BASALT_BRICK_SLAB.get())
                                                                                 .wall(
                                                                                     PastelBlocks.BASALT_BRICK_WALL.get())
                                                                                 .cracked(
                                                                                     PastelBlocks.CRACKED_BASALT_BRICKS.get())
                                                                                 .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BASALT_TILES.get()).stairs(
                                                                                    PastelBlocks.BASALT_TILE_STAIRS.get())
                                                                                .slab(
                                                                                    PastelBlocks.BASALT_TILE_SLAB.get())
                                                                                .wall(
                                                                                    PastelBlocks.BASALT_TILE_WALL.get())
                                                                                .cracked(
                                                                                    PastelBlocks.CRACKED_BASALT_TILES.get())
                                                                                .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.PLANED_BASALT.get()).stairs(
                                                                                     PastelBlocks.PLANED_BASALT_STAIRS.get())
                                                                                 .slab(
                                                                                     PastelBlocks.PLANED_BASALT_SLAB.get())
                                                                                 .wall(
                                                                                     PastelBlocks.PLANED_BASALT_WALL.get())
                                                                                 .getFamily()
        );
        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(Blocks.CALCITE).stairs(PastelBlocks.CALCITE_STAIRS.get())
                                                               .slab(PastelBlocks.CALCITE_SLAB.get())
                                                               .wall(PastelBlocks.CALCITE_WALL.get())
                                                               .getFamily(), TexturedModel.CUBE
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.POLISHED_CALCITE.get()).stairs(
                                                                                        PastelBlocks.POLISHED_CALCITE_STAIRS.get())
                                                                                    .slab(
                                                                                        PastelBlocks.POLISHED_CALCITE_SLAB.get())
                                                                                    .wall(
                                                                                        PastelBlocks.POLISHED_CALCITE_WALL.get())
                                                                                    .button(
                                                                                        PastelBlocks.POLISHED_CALCITE_BUTTON.get())
                                                                                    .pressurePlate(
                                                                                        PastelBlocks.POLISHED_CALCITE_PRESSURE_PLATE.get())
                                                                                    .chiseled(
                                                                                        PastelBlocks.CHISELED_POLISHED_CALCITE.get())
                                                                                    .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.CALCITE_BRICKS.get()).stairs(
                                                                                      PastelBlocks.CALCITE_BRICK_STAIRS.get())
                                                                                  .slab(
                                                                                      PastelBlocks.CALCITE_BRICK_SLAB.get())
                                                                                  .wall(
                                                                                      PastelBlocks.CALCITE_BRICK_WALL.get())
                                                                                  .cracked(
                                                                                      PastelBlocks.CRACKED_CALCITE_BRICKS.get())
                                                                                  .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.CALCITE_TILES.get()).stairs(
                                                                                     PastelBlocks.CALCITE_TILE_STAIRS.get())
                                                                                 .slab(
                                                                                     PastelBlocks.CALCITE_TILE_SLAB.get())
                                                                                 .wall(
                                                                                     PastelBlocks.CALCITE_TILE_WALL.get())
                                                                                 .cracked(
                                                                                     PastelBlocks.CRACKED_CALCITE_TILES.get())
                                                                                 .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.PLANED_CALCITE.get()).stairs(
                                                                                      PastelBlocks.PLANED_CALCITE_STAIRS.get())
                                                                                  .slab(
                                                                                      PastelBlocks.PLANED_CALCITE_SLAB.get())
                                                                                  .wall(
                                                                                      PastelBlocks.PLANED_CALCITE_WALL.get())
                                                                                  .getFamily()
        );

        PastelModelHelper.axisRotated(generators, PastelBlocks.POLISHED_BASALT_PILLAR, TexturedModel.COLUMN);
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(
                                 generators, PastelBlocks.POLISHED_BASALT_CREST.get(), TexturedModel.COLUMN)
                             .with(PastelModelHelper.createCardinalFacingVariantMap()));
        PastelModelHelper.singleton(generators, PastelBlocks.NOTCHED_POLISHED_BASALT, TexturedModel.COLUMN);

        PastelModelHelper.simple(
            generators, PastelBlocks.TOPAZ_CHISELED_BASALT, PastelBlocks.AMETHYST_CHISELED_BASALT,
            PastelBlocks.CITRINE_CHISELED_BASALT, PastelBlocks.ONYX_CHISELED_BASALT
        );
        PastelModelHelper.simple(
            generators, PastelBlocks.TOPAZ_CHISELED_CALCITE,
            PastelBlocks.AMETHYST_CHISELED_CALCITE, PastelBlocks.CITRINE_CHISELED_CALCITE,
            PastelBlocks.ONYX_CHISELED_CALCITE
        );
        PastelModelHelper.moonstoneChiseled(
            generators, PastelBlocks.MOONSTONE_CHISELED_BASALT, PastelTextures.BASALT_CAP);
        PastelModelHelper.moonstoneChiseled(
            generators, PastelBlocks.MOONSTONE_CHISELED_CALCITE, PastelTextures.CALCITE_CAP);
        PastelModelHelper.axisRotated(generators, PastelBlocks.POLISHED_CALCITE_PILLAR, TexturedModel.COLUMN);
        generators.blockStateOutput.accept(
            PastelModelHelper.createVariantsSupplier(
                                 generators, PastelBlocks.POLISHED_CALCITE_CREST.get(), TexturedModel.COLUMN)
                             .with(PastelModelHelper.createCardinalFacingVariantMap()));
        PastelModelHelper.singleton(generators, PastelBlocks.NOTCHED_POLISHED_CALCITE, TexturedModel.COLUMN);
    }
    public static void generateItemModels(ItemModelGenerators generators){
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.MOONSTONE_CHISELED_BASALT.get(), PastelBlocks.MOONSTONE_CHISELED_BASALT.get(), "_down");
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.MOONSTONE_CHISELED_CALCITE.get(), PastelBlocks.MOONSTONE_CHISELED_CALCITE.get(), "_down");
    }
}
