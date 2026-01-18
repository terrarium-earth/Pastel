package earth.terrarium.pastel.data.models.block.deco;

import earth.terrarium.pastel.blocks.deeper_down.PyriteRipperBlock;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelTextureMaps;
import earth.terrarium.pastel.registries.client.PastelTexturedModels;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class StoneLikeBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BASAL_MARBLE_BRICKS.get()).stairs(
                                                                                           PastelBlocks.BASAL_MARBLE_BRICK_STAIRS.get())
                                                                                       .slab(
                                                                                           PastelBlocks.BASAL_MARBLE_BRICK_SLAB.get())
                                                                                       .wall(
                                                                                           PastelBlocks.BASAL_MARBLE_BRICK_WALL.get())
                                                                                       .getFamily()
        );

        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(PastelBlocks.BLACKSLAG.get()).stairs(
                                                                                 PastelBlocks.BLACKSLAG_STAIRS.get())
                                                                             .slab(PastelBlocks.BLACKSLAG_SLAB.get())
                                                                             .wall(PastelBlocks.BLACKSLAG_WALL.get())
                                                                             .getFamily(),
            PastelTexturedModels.cubeBottomTopWall(b -> b, "", b -> b, "_top", b -> b, "_top", b -> b, "")
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.COBBLED_BLACKSLAG.get()).stairs(
                                                                                         PastelBlocks.COBBLED_BLACKSLAG_STAIRS.get())
                                                                                     .slab(
                                                                                         PastelBlocks.COBBLED_BLACKSLAG_SLAB.get())
                                                                                     .wall(
                                                                                         PastelBlocks.COBBLED_BLACKSLAG_WALL.get())
                                                                                     .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BLACKSLAG_TILES.get()).stairs(
                                                                                       PastelBlocks.BLACKSLAG_TILE_STAIRS.get())
                                                                                   .slab(
                                                                                       PastelBlocks.BLACKSLAG_TILE_SLAB.get())
                                                                                   .wall(
                                                                                       PastelBlocks.BLACKSLAG_TILE_WALL.get())
                                                                                   .cracked(
                                                                                       PastelBlocks.CRACKED_BLACKSLAG_TILES.get())
                                                                                   .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BLACKSLAG_BRICKS.get()).stairs(
                                                                                        PastelBlocks.BLACKSLAG_BRICK_STAIRS.get())
                                                                                    .slab(
                                                                                        PastelBlocks.BLACKSLAG_BRICK_SLAB.get())
                                                                                    .wall(
                                                                                        PastelBlocks.BLACKSLAG_BRICK_WALL.get())
                                                                                    .cracked(
                                                                                        PastelBlocks.CRACKED_BLACKSLAG_BRICKS.get())
                                                                                    .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.POLISHED_BLACKSLAG.get()).stairs(
                                                                                          PastelBlocks.POLISHED_BLACKSLAG_STAIRS.get())
                                                                                      .slab(
                                                                                          PastelBlocks.POLISHED_BLACKSLAG_SLAB.get())
                                                                                      .wall(
                                                                                          PastelBlocks.POLISHED_BLACKSLAG_WALL.get())
                                                                                      .button(
                                                                                          PastelBlocks.POLISHED_BLACKSLAG_BUTTON.get())
                                                                                      .pressurePlate(
                                                                                          PastelBlocks.POLISHED_BLACKSLAG_PRESSURE_PLATE.get())
                                                                                      .chiseled(
                                                                                          PastelBlocks.CHISELED_POLISHED_BLACKSLAG.get())
                                                                                      .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.POLISHED_SHALE_CLAY.get()).stairs(
                                                                                           PastelBlocks.POLISHED_SHALE_CLAY_STAIRS.get())
                                                                                       .slab(
                                                                                           PastelBlocks.POLISHED_SHALE_CLAY_SLAB.get())
                                                                                       .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.get()).stairs(
                                                                                                   PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS.get())
                                                                                               .slab(
                                                                                                   PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB.get())
                                                                                               .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY.get()).stairs(
                                                                                                     PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_STAIRS.get())
                                                                                                 .slab(
                                                                                                     PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB.get())
                                                                                                 .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.SHALE_CLAY_BRICKS.get()).stairs(
                                                                                         PastelBlocks.SHALE_CLAY_BRICK_STAIRS.get())
                                                                                     .slab(
                                                                                         PastelBlocks.SHALE_CLAY_BRICK_SLAB.get())
                                                                                     .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS.get()).stairs(
                                                                                                 PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS.get())
                                                                                             .slab(
                                                                                                 PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB.get())
                                                                                             .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.SHALE_CLAY_TILES.get()).stairs(
                                                                                        PastelBlocks.SHALE_CLAY_TILE_STAIRS.get())
                                                                                    .slab(
                                                                                        PastelBlocks.SHALE_CLAY_TILE_SLAB.get())
                                                                                    .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.EXPOSED_SHALE_CLAY_TILES.get()).stairs(
                                                                                                PastelBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS.get())
                                                                                            .slab(
                                                                                                PastelBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB.get())
                                                                                            .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.WEATHERED_SHALE_CLAY_TILES.get()).stairs(
                                                                                                  PastelBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS.get())
                                                                                              .slab(
                                                                                                  PastelBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB.get())
                                                                                              .getFamily()
        );

        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(PastelBlocks.PYRITE.get()).stairs(PastelBlocks.PYRITE_STAIRS.get())
                                                                          .slab(PastelBlocks.PYRITE_SLAB.get())
                                                                          .wall(PastelBlocks.PYRITE_WALL.get())
                                                                          .getFamily(), TexturedModel.createDefault(
                b -> PastelTextureMaps.sideTopBottomWall(b, "_side", b, "_top", b, "_top", b, "_side"),
                ModelTemplates.CUBE_ALL
            )
        );
        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(PastelBlocks.PYRITE_TILES.get()).stairs(
                                                                                    PastelBlocks.PYRITE_TILE_STAIRS.get())
                                                                                .slab(
                                                                                    PastelBlocks.PYRITE_TILE_SLAB.get())
                                                                                .wall(
                                                                                    PastelBlocks.PYRITE_TILE_WALL.get())
                                                                                .getFamily(),
            TexturedModel.createDefault(
                b -> TextureMapping.cube(PastelBlocks.PYRITE_PLATING.get()), ModelTemplates.CUBE_ALL)
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.POLISHED_BONE_ASH.get()).stairs(
                                                                                         PastelBlocks.POLISHED_BONE_ASH_STAIRS.get())
                                                                                     .slab(
                                                                                         PastelBlocks.POLISHED_BONE_ASH_SLAB.get())
                                                                                     .wall(
                                                                                         PastelBlocks.POLISHED_BONE_ASH_WALL.get())
                                                                                     .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BONE_ASH_BRICKS.get()).stairs(
                                                                                       PastelBlocks.BONE_ASH_BRICK_STAIRS.get())
                                                                                   .slab(
                                                                                       PastelBlocks.BONE_ASH_BRICK_SLAB.get())
                                                                                   .wall(
                                                                                       PastelBlocks.BONE_ASH_BRICK_WALL.get())
                                                                                   .getFamily()
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BONE_ASH_TILES.get()).stairs(
                                                                                      PastelBlocks.BONE_ASH_TILE_STAIRS.get())
                                                                                  .slab(
                                                                                      PastelBlocks.BONE_ASH_TILE_SLAB.get())
                                                                                  .wall(
                                                                                      PastelBlocks.BONE_ASH_TILE_WALL.get())
                                                                                  .getFamily()
        );

        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(PastelBlocks.BASAL_MARBLE.get()).stairs(
                                                                                    PastelBlocks.BASAL_MARBLE_STAIRS.get())
                                                                                .slab(
                                                                                    PastelBlocks.BASAL_MARBLE_SLAB.get())
                                                                                .wall(
                                                                                    PastelBlocks.BASAL_MARBLE_WALL.get())
                                                                                .getFamily(), TexturedModel.CUBE
        );
        PastelModelHelper.registerBlockFamilyExceptBase(
            generators, new BlockFamily.Builder(PastelBlocks.POLISHED_BASAL_MARBLE.get()).stairs(
                                                                                             PastelBlocks.POLISHED_BASAL_MARBLE_STAIRS.get())
                                                                                         .slab(
                                                                                             PastelBlocks.POLISHED_BASAL_MARBLE_SLAB.get())
                                                                                         .wall(
                                                                                             PastelBlocks.POLISHED_BASAL_MARBLE_WALL.get())
                                                                                         .getFamily(),
            TexturedModel.createDefault(
                b -> PastelTextureMaps.sideTopBottomWall(b, "_side", b, "_top", b, "_bottom", b, "_side"),
                ModelTemplates.CUBE_BOTTOM_TOP
            )
        );
        PastelModelHelper.registerBlockFamily(
            generators, new BlockFamily.Builder(PastelBlocks.BASAL_MARBLE_TILES.get()).stairs(
                                                                                          PastelBlocks.BASAL_MARBLE_TILE_STAIRS.get())
                                                                                      .slab(
                                                                                          PastelBlocks.BASAL_MARBLE_TILE_SLAB.get())
                                                                                      .wall(
                                                                                          PastelBlocks.BASAL_MARBLE_TILE_WALL.get())
                                                                                      .getFamily()
        );

        PastelModelHelper.axisRotated(generators, PastelBlocks.BASAL_MARBLE, TexturedModel.COLUMN_ALT);
        generators.blockStateOutput.accept(PastelModelHelper.createMirroredVariantsSupplier(
                                                                PastelBlocks.BLACKSLAG.get(),
                                                                TexturedModel.COLUMN_ALT,
                                                                PastelTexturedModels.CUBE_COLUMN_MIRRORED,
                                                                generators.modelOutput
                                                            )
                                                            .with(PastelModelHelper.createAxisRotatedVariantMap()));
        PastelModelHelper.parented(generators, PastelBlocks.INFESTED_BLACKSLAG.get(), PastelBlocks.BLACKSLAG.get());
        PastelModelHelper.axisRotated(
            generators, PastelBlocks.POLISHED_BLACKSLAG_PILLAR, TexturedModel.createDefault(
                b -> PastelTextureMaps.sideEnd(b, "", PastelBlocks.CHISELED_POLISHED_BLACKSLAG.get(), ""),
                ModelTemplates.CUBE_COLUMN
            )
        );
        PastelModelHelper.simple(generators, PastelBlocks.ANCIENT_CHISELED_POLISHED_BLACKSLAG);
        PastelModelHelper.singleton(generators, PastelBlocks.SHALE_CLAY, TexturedModel.COLUMN);
        PastelModelHelper.singleton(
            generators, PastelBlocks.TILLED_SHALE_CLAY,
            PastelTexturedModels.farmland(b -> PastelBlocks.SHALE_CLAY.get(), "_side", b -> b, "")
        );
        PastelModelHelper.axisRotated(generators, PastelBlocks.PYRITE_PILE, TexturedModel.COLUMN);
        PastelModelHelper.simple(generators, PastelBlocks.PYRITE_PLATING);
        PastelModelHelper.axisRotated(generators, PastelBlocks.PYRITE_TUBING, TexturedModel.COLUMN);
        PastelModelHelper.axisRotated(
            generators, PastelBlocks.PYRITE_RELIEF,
            PastelTexturedModels.cubeColumn(
                b -> b, "_side", b -> PastelBlocks.PYRITE_TUBING.get(), "_top")
        );
        PastelModelHelper.simple(generators, PastelBlocks.PYRITE_STACK);
        PastelModelHelper.singleton(
            generators, PastelBlocks.PYRITE_PANELING,
            PastelTexturedModels.cubeColumn(b -> b, "", b -> PastelBlocks.PYRITE_PLATING.get(), "")
        );
        PastelModelHelper.singleton(
            generators, PastelBlocks.PYRITE_VENT,
            PastelTexturedModels.cubeColumn(b -> b, "", b -> PastelBlocks.PYRITE_PLATING.get(), "")
        );
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.PYRITE_RIPPER.get())
                                                                .with(PropertyDispatch.properties(
                                                                                          BlockStateProperties.FACING,
                                                                                          PyriteRipperBlock.MIRRORED
                                                                                      )
                                                                                      .select(
                                                                                          Direction.EAST, false,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               ""
                                                                                                           )
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
                                                                                          Direction.NORTH, false,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               ""
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.X_ROT,
                                                                                                               VariantProperties.Rotation.R90
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.SOUTH, false,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               ""
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.X_ROT,
                                                                                                               VariantProperties.Rotation.R270
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.WEST, false,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               ""
                                                                                                           )
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
                                                                                          Direction.UP, false,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.PYRITE_RIPPER.get(),
                                                                                              ""
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.DOWN, false,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               ""
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.X_ROT,
                                                                                                               VariantProperties.Rotation.R180
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.EAST, true,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               "_mirrored"
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R180
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.NORTH, true,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               "_mirrored"
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R90
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.SOUTH, true,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               "_mirrored"
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R270
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.WEST, true,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              PastelBlocks.PYRITE_RIPPER.get(),
                                                                                              "_mirrored"
                                                                                          )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.UP, true,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               ""
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R90
                                                                                                           )
                                                                                      )
                                                                                      .select(
                                                                                          Direction.DOWN, true,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                                               PastelBlocks.PYRITE_RIPPER.get(),
                                                                                                               ""
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.X_ROT,
                                                                                                               VariantProperties.Rotation.R180
                                                                                                           )
                                                                                                           .with(
                                                                                                               VariantProperties.Y_ROT,
                                                                                                               VariantProperties.Rotation.R90
                                                                                                           )
                                                                                      )));
        PastelModelHelper.singletonWithSoup(
            generators, PastelBlocks.PYRITE_PROJECTOR, ModelLocationUtils::getModelLocation);
        PastelModelHelper.simple(generators, PastelBlocks.PYRITE_TILES);
        PastelModelHelper.axisRotated(generators, PastelBlocks.POLISHED_BONE_ASH_PILLAR, TexturedModel.COLUMN);
        generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(
                                                                PastelBlocks.BONE_ASH_SHINGLES.get(),
                                                                ModelLocationUtils.getModelLocation(PastelBlocks.BONE_ASH_SHINGLES.get())
                                                            )
                                                            .with(
                                                                PastelModelHelper.createEastDefaultHorizontalFacingVariantMap()));
        PastelModelHelper.simple(generators, PastelBlocks.SLUSH);
        PastelModelHelper.snowy(
            generators, PastelBlocks.OVERGROWN_SLUSH, PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_side", b -> b, "_top", b -> PastelBlocks.SLUSH.get(), "", b -> b, "_top"),
            PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_snow_side", b -> b, "_snow_top", b -> PastelBlocks.SLUSH.get(), "", b -> b, "_snow_top")
        );
        PastelModelHelper.singleton(
            generators, PastelBlocks.TILLED_SLUSH,
            PastelTexturedModels.farmland(b -> PastelBlocks.SLUSH.get(), "", b -> b, "")
        );
        PastelModelHelper.simple(generators, PastelBlocks.BLACK_MATERIA);
        PastelModelHelper.simple(generators, PastelBlocks.HORNSLAKE);
        PastelModelHelper.snowy(
            generators, PastelBlocks.SAWBLADE_GRASS, PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_side", b -> b, "_top", b -> PastelBlocks.BLACKSLAG.get(), "_top", b -> b, "_top"),
            PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_snow_side", b -> b, "_snow_top", b -> PastelBlocks.BLACKSLAG.get(), "_top", b -> b,
                "_snow_top"
            )
        );
        PastelModelHelper.snowy(
            generators, PastelBlocks.SHIMMEL, PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_side", b -> b, "_top", b -> PastelBlocks.BLACKSLAG.get(), "_top",
                b -> PastelBlocks.BLACKSLAG.get(), "_top"
            ), PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_snow_side", b -> b, "_snow_top", b -> PastelBlocks.BLACKSLAG.get(), "_top",
                b -> PastelBlocks.BLACKSLAG.get(), "_top"
            )
        );
        PastelModelHelper.snowy(
            generators, PastelBlocks.OVERGROWN_BLACKSLAG, PastelTexturedModels.overgrown(
                b -> b, "_side", b -> b, "_top", b -> PastelBlocks.BLACKSLAG.get(), "_top", b -> b, "_fronds"),
            PastelTexturedModels.overgrown(
                b -> b, "_snow_side", b -> b, "_snow_top", b -> PastelBlocks.BLACKSLAG.get(), "_top", b -> b,
                "_snow_fronds"
            )
        );
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
            PastelBlocks.FLAYED_EARTH.get(), PastelModelHelper.createModelVariant(
                TexturedModel.CUBE.create(PastelBlocks.FLAYED_EARTH.get(), generators.modelOutput)),
            PastelModelHelper.createModelVariant(PastelTexturedModels.cubeAll(b -> b, "_1")
                                                                     .createWithSuffix(
                                                                         PastelBlocks.FLAYED_EARTH.get(), "_1",
                                                                         generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                PastelTexturedModels.cubeAll(b -> b, "_2")
                                    .createWithSuffix(PastelBlocks.FLAYED_EARTH.get(), "_2", generators.modelOutput))
        ));
        PastelModelHelper.singleton(
            generators, PastelBlocks.ASHEN_BLACKSLAG,
            PastelTexturedModels.cubeBottomTopParticle(
                b -> b, "_side", b -> b, "_top", b -> PastelBlocks.BLACKSLAG.get(), "_top",
                b -> b, "_top"
            )
        );
        PastelModelHelper.axisRotated(generators, PastelBlocks.BASAL_MARBLE_PILLAR, TexturedModel.COLUMN);
        PastelModelHelper.defaultUpFacing(
            generators, PastelBlocks.POLISHED_BASAL_MARBLE, TexturedModel.CUBE_TOP_BOTTOM);
        PastelModelHelper.simple(generators, PastelBlocks.DOWNSTONE);

    }

    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.INFESTED_BLACKSLAG.get(), PastelBlocks.BLACKSLAG.get());
    }
}
