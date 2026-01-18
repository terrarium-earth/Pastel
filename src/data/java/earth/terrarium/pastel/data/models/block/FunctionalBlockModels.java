package earth.terrarium.pastel.data.models.block;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.blocks.*;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleBlock;
import earth.terrarium.pastel.blocks.decay.*;
import earth.terrarium.pastel.blocks.present.PresentBlock;
import earth.terrarium.pastel.blocks.redstone.*;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.*;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.registries.DeferredBlock;

import static net.minecraft.world.level.block.Blocks.*;

public class FunctionalBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.TEA_TABLE);

        generatePrimfireModel(generators);

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.ETHEREAL_PLATFORM);

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.UNIVERSE_SPYHOLE);

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.PRESENT);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.PRESENT.get())
                                                                .with(PropertyDispatch.property(PresentBlock.VARIANT)
                                                                                      .generate(
                                                                                          variant -> PastelModelHelper.createModelVariant(
                                                                                              PastelModels.PRESENT.createWithSuffix(
                                                                                                  PastelBlocks.PRESENT.get(),
                                                                                                  "_" +
                                                                                                  variant.getSerializedName(),
                                                                                                  new TextureMapping().put(
                                                                                                                          TextureSlot.TEXTURE,
                                                                                                                          TextureMapping.getBlockTexture(
                                                                                                                              PastelBlocks.PRESENT.get(),
                                                                                                                              "_" +
                                                                                                                              variant.getSerializedName()
                                                                                                                          )
                                                                                                                      )
                                                                                                                      .put(
                                                                                                                          TextureSlot.PARTICLE,
                                                                                                                          TextureMapping.getBlockTexture(
                                                                                                                              variant.woolBase)
                                                                                                                      ),
                                                                                                  generators.modelOutput
                                                                                              )))));

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.BLOCK_FLOODER);

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.BOTTOMLESS_BUNDLE);

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.BOTTOMLESS_BUNDLE.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BottomlessBundleBlock.LOCKED,
                                                                    ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.BOTTOMLESS_BUNDLE.get(),
                                                                        "_locked"
                                                                    ), ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.BOTTOMLESS_BUNDLE.get(),
                                                                        "_unlocked"
                                                                    )
                                                                )));

        PastelModelHelper.BLOCK.singleton(
            generators, PastelBlocks.SHIMMERSTONE_LIGHT,
            PastelTexturedModels.particle(PastelTextures.SHIMMERSTONE_LIGHT)
        );
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.TEMPORAL_SHIMMERSTONE_LIGHT.get(), PastelBlocks.SHIMMERSTONE_LIGHT.get());

        decay(generators, PastelBlocks.FADING);
        decay(generators, PastelBlocks.FAILING);
        decay(generators, PastelBlocks.RUIN);
        decay(generators, PastelBlocks.FORFEITURE);


        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.DECAY_AWAY);

        PastelModelHelper.BLOCK.singleton(generators, PastelBlocks.HOVERBLOCK, TexturedModel.COLUMN);

        PastelModelHelper.BLOCK.defaultNorthHorizontalFacing(
            generators, PastelBlocks.HEARTBOUND_CHEST, ModelLocationUtils::getModelLocation);
        PastelModelHelper.BLOCK.defaultNorthHorizontalFacing(
            generators, PastelBlocks.COMPACTING_CHEST, ModelLocationUtils::getModelLocation);


        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.FABRICATION_CHEST);
        PastelModelHelper.BLOCK.defaultNorthHorizontalFacing(
            generators, PastelBlocks.FABRICATION_CHEST, ModelLocationUtils::getModelLocation);


        PastelModelHelper.BLOCK.defaultNorthHorizontalFacing(
            generators, PastelBlocks.BLACK_HOLE_CHEST, ModelLocationUtils::getModelLocation);


        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.PARTICLE_SPAWNER.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BlockStateProperties.POWERED,
                                                                    PastelModels.PARTICLE_SPAWNER.create(
                                                                        PastelBlocks.PARTICLE_SPAWNER.get(),
                                                                        PastelTextureMaps.top(
                                                                            PastelBlocks.PARTICLE_SPAWNER.get(),
                                                                            "_top"
                                                                        ), generators.modelOutput
                                                                    ), PastelModels.PARTICLE_SPAWNER.createWithSuffix(
                                                                        PastelBlocks.PARTICLE_SPAWNER.get(), "_off",
                                                                        PastelTextureMaps.top(
                                                                            PastelBlocks.PARTICLE_SPAWNER.get(),
                                                                            "_top_off"
                                                                        ), generators.modelOutput
                                                                    )
                                                                )));
        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.CREATIVE_PARTICLE_SPAWNER,
            b -> ModelLocationUtils.getModelLocation(
                PastelBlocks.PARTICLE_SPAWNER.get())
        );

        PastelModelHelper.BLOCK.defaultSouthHorizontalFacing(
            generators, PastelBlocks.BEDROCK_ANVIL, ModelLocationUtils::getModelLocation);


        PastelModelHelper.BLOCK.singletonWithSoup(generators, PastelBlocks.MEMORY, ModelLocationUtils::getModelLocation);

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(
                                                                    PastelBlocks.CRACKED_END_PORTAL_FRAME.get())
                                                                .with(PropertyDispatch.property(
                                                                                          CrackedEndPortalFrameBlock.FACING_VERTICAL)
                                                                                      .select(
                                                                                          false,
                                                                                          net.minecraft.data.models.blockstates.Variant.variant()
                                                                                      )
                                                                                      .select(
                                                                                          true,
                                                                                          net.minecraft.data.models.blockstates.Variant.variant()
                                                                                                                                       .with(
                                                                                                                                           VariantProperties.Y_ROT,
                                                                                                                                           VariantProperties.Rotation.R90
                                                                                                                                       )
                                                                                      ))
                                                                .with(PropertyDispatch.property(
                                                                                          CrackedEndPortalFrameBlock.EYE_TYPE)
                                                                                      .generate(
                                                                                          type -> PastelModelHelper.createModelVariant(
                                                                                              ModelLocationUtils.getModelLocation(
                                                                                                  PastelBlocks.CRACKED_END_PORTAL_FRAME.get(),
                                                                                                  "_" +
                                                                                                  type.getSerializedName()
                                                                                              )))));

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.LAVA_SPONGE);
        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.WET_LAVA_SPONGE);

        PastelModelHelper.BLOCK.detector(generators, PastelBlocks.BLOCK_LIGHT_DETECTOR);
        PastelModelHelper.BLOCK.detector(generators, PastelBlocks.WEATHER_DETECTOR);
        PastelModelHelper.BLOCK.detector(generators, PastelBlocks.ITEM_DETECTOR);
        PastelModelHelper.BLOCK.detector(generators, PastelBlocks.PLAYER_DETECTOR);
        PastelModelHelper.BLOCK.detector(generators, PastelBlocks.CREATURE_DETECTOR);

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.REDSTONE_TIMER);

        generateRedstoneTimerModel(generators);

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.REDSTONE_CALCULATOR);

        generateRedstoneCalculatorModel(generators);

        PastelModelHelper.BLOCK.predefinedItemModel(generators, PastelBlocks.REDSTONE_TRANSCEIVER);
        generateRedstoneTransceiverModel(generators);

        generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(
                                                                generators, PastelBlocks.BLOCK_PLACER.get(),
                                                                PastelTexturedModels.complexOrientable(
                                                                    b -> b, "_side", b -> b, "_top",
                                                                    b -> PastelBlocks.NOTCHED_POLISHED_CALCITE.get(),
                                                                    "_top", b -> b,
                                                                    "_front", b -> b, "_back", b -> b, "_side"
                                                                )
                                                            )
                                                            .with(
                                                                PastelModelHelper.createUpNorthDefaultOrientationVariantMap()));
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.BLOCK_DETECTOR.get())
                                                                .with(
                                                                    PastelModelHelper.createUpNorthDefaultOrientationVariantMap())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    BlockStateProperties.TRIGGERED,
                                                                    PastelTexturedModels.complexOrientable(
                                                                                            b -> b, "_side", b -> b,
                                                                                            "_top",
                                                                                            b -> PastelBlocks.NOTCHED_POLISHED_BASALT.get(),
                                                                                            "_top", b -> b, "_front",
                                                                                            b -> b,
                                                                                            "_back_active", b -> b,
                                                                                            "_side"
                                                                                        )
                                                                                        .createWithSuffix(
                                                                                            PastelBlocks.BLOCK_DETECTOR.get(),
                                                                                            "_active",
                                                                                            generators.modelOutput
                                                                                        ),
                                                                    PastelTexturedModels.complexOrientable(
                                                                                            b -> b, "_side", b -> b,
                                                                                            "_top",
                                                                                            b -> PastelBlocks.NOTCHED_POLISHED_BASALT.get(),
                                                                                            "_top", b -> b, "_front",
                                                                                            b -> b, "_back",
                                                                                            b -> b, "_side"
                                                                                        )
                                                                                        .create(
                                                                                            PastelBlocks.BLOCK_DETECTOR.get(),
                                                                                            generators.modelOutput
                                                                                        )
                                                                )));
        generators.blockStateOutput.accept(PastelModelHelper.createVariantsSupplier(
                                                                generators, PastelBlocks.BLOCK_BREAKER.get(),
                                                                PastelTexturedModels.complexOrientable(
                                                                    b -> b, "_side", b -> b, "_top",
                                                                    b -> PastelBlocks.POLISHED_BONE_ASH_PILLAR.get(),
                                                                    "_top", b -> b,
                                                                    "_front", b -> b, "_back", b -> b, "_side"
                                                                )
                                                            )
                                                            .with(
                                                                PastelModelHelper.createUpNorthDefaultOrientationVariantMap()));

        PastelModelHelper.BLOCK.orientable(generators, PastelBlocks.ENDER_DROPPER);
        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.ENDER_HOPPER, ModelLocationUtils::getModelLocation);

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.IMBRIFER_PORTAL.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    DeeperDownPortalBlock.FACING_UP,
                                                                    ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.IMBRIFER_PORTAL.get(), "_up"),
                                                                    ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.IMBRIFER_PORTAL.get())
                                                                )));

        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.UPGRADE_SPEED.get(), PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.UPGRADE_SPEED2.get(), PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.UPGRADE_SPEED3.get(), PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.UPGRADE_EFFICIENCY.get(), PastelBlocks.UPGRADE_EFFICIENCY.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.UPGRADE_EFFICIENCY2.get(), PastelBlocks.UPGRADE_EFFICIENCY.get());
        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.UPGRADE_YIELD.get(), PastelBlocks.UPGRADE_YIELD.get());
        PastelModelHelper.BLOCK.parented(generators, PastelBlocks.UPGRADE_YIELD2.get(), PastelBlocks.UPGRADE_YIELD.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.UPGRADE_EXPERIENCE.get(), PastelBlocks.UPGRADE_EXPERIENCE.get());
        PastelModelHelper.BLOCK.parented(
            generators, PastelBlocks.UPGRADE_EXPERIENCE2.get(), PastelBlocks.UPGRADE_EXPERIENCE.get());

        PastelModelHelper.BLOCK.simple(generators, PastelBlocks.REDSTONE_SAND);


        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.ENDER_GLASS.get())
                                                                .with(PropertyDispatch.property(
                                                                                          EnderGlassBlock.TRANSPARENCY_STATE)
                                                                                      .generate(
                                                                                          transparency -> PastelModelHelper.createModelVariant(
                                                                                              PastelTexturedModels.cubeAll(
                                                                                                                      b -> b, "_" +
                                                                                                                              transparency.getSerializedName()
                                                                                                                  )
                                                                                                                  .createWithSuffix(
                                                                                                                      PastelBlocks.ENDER_GLASS.get(),
                                                                                                                      "_" +
                                                                                                                      transparency.getSerializedName(),
                                                                                                                      generators.modelOutput
                                                                                                                  )))));

        PastelModelHelper.BLOCK.singletonWithSoup(
            generators, PastelBlocks.INCANDESCENT_AMALGAM, ModelLocationUtils::getModelLocation);

        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.AXOLOTL_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.BAT_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.BEE_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.BLAZE_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.CAT_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.CHICKEN_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.COW_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.CREEPER_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.ENDER_DRAGON_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.ENDERMAN_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.ENDERMITE_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.EVOKER_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.FISH_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.FOX_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.GHAST_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.GLOW_SQUID_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.GOAT_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.GUARDIAN_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.HORSE_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.ILLUSIONER_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.OCELOT_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.PARROT_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.PHANTOM_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.PIG_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.PIGLIN_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.POLAR_BEAR_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.PUFFERFISH_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.RABBIT_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SHEEP_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SHULKER_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SILVERFISH_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SKELETON_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SLIME_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SNOW_GOLEM_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SPIDER_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.SQUID_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.STRAY_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.STRIDER_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.TURTLE_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.WITCH_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.WITHER_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.WITHER_SKELETON_IDOL);
        PastelModelHelper.BLOCK.idol(generators, PastelBlocks.ZOMBIE_IDOL);
    }

    public static void generatePrimfireModel(BlockModelGenerators generators) {
        Condition noSides = Condition.condition()
                                     .term(PrimordialFireBlock.UP, false)
                                     .term(PrimordialFireBlock.NORTH, false)
                                     .term(PrimordialFireBlock.SOUTH, false)
                                     .term(PrimordialFireBlock.WEST, false)
                                     .term(PrimordialFireBlock.EAST, false);
        TextureMapping fire0 = new TextureMapping().put(
            TextureSlot.FIRE, TextureMapping.getBlockTexture(PastelBlocks.PRIMORDIAL_FIRE.get(), "_0"));
        TextureMapping fire1 = new TextureMapping().put(
            TextureSlot.FIRE, TextureMapping.getBlockTexture(PastelBlocks.PRIMORDIAL_FIRE.get(), "_1"));
        ResourceLocation side0 = PastelModels.FIRE_SIDE.createWithSuffix(
            PastelBlocks.PRIMORDIAL_FIRE.get(), "_side0", fire0, generators.modelOutput);
        ResourceLocation side1 = PastelModels.FIRE_SIDE.createWithSuffix(
            PastelBlocks.PRIMORDIAL_FIRE.get(), "_side1", fire1, generators.modelOutput);
        ResourceLocation sideAlt0 = PastelModels.FIRE_SIDE_ALT.createWithSuffix(
            PastelBlocks.PRIMORDIAL_FIRE.get(), "_side_alt0", fire0, generators.modelOutput);
        ResourceLocation sideAlt1 = PastelModels.FIRE_SIDE_ALT.createWithSuffix(
            PastelBlocks.PRIMORDIAL_FIRE.get(), "_side_alt1", fire1, generators.modelOutput);
        generators.blockStateOutput.accept(MultiPartGenerator.multiPart(PastelBlocks.PRIMORDIAL_FIRE.get())
                                                             .with(
                                                                 noSides, PastelModelHelper.createModelVariant(
                                                                     PastelModels.FIRE_FLOOR.createWithSuffix(
                                                                         PastelBlocks.PRIMORDIAL_FIRE.get(), "_floor0",
                                                                         fire0, generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                                                                     PastelModels.FIRE_FLOOR.createWithSuffix(
                                                                         PastelBlocks.PRIMORDIAL_FIRE.get(), "_floor1",
                                                                         fire1, generators.modelOutput
                                                                     ))
                                                             )
                                                             .with(
                                                                 Condition.condition()
                                                                          .term(PrimordialFireBlock.UP, true),
                                                                 PastelModelHelper.createModelVariant(
                                                                     PastelModels.FIRE_UP.createWithSuffix(
                                                                         PastelBlocks.PRIMORDIAL_FIRE.get(), "_up0",
                                                                         fire0, generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                                                                     PastelModels.FIRE_UP.createWithSuffix(
                                                                         PastelBlocks.PRIMORDIAL_FIRE.get(), "_up1",
                                                                         fire1, generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                                                                     PastelModels.FIRE_UP_ALT.createWithSuffix(
                                                                         PastelBlocks.PRIMORDIAL_FIRE.get(), "_up_alt0",
                                                                         fire0, generators.modelOutput
                                                                     )), PastelModelHelper.createModelVariant(
                                                                     PastelModels.FIRE_UP_ALT.createWithSuffix(
                                                                         PastelBlocks.PRIMORDIAL_FIRE.get(), "_up_alt1",
                                                                         fire1, generators.modelOutput
                                                                     ))
                                                             )
                                                             .with(
                                                                 Condition.or(
                                                                     noSides, Condition.condition()
                                                                                       .term(
                                                                                           PrimordialFireBlock.NORTH,
                                                                                           true
                                                                                       )
                                                                 ), PastelModelHelper.createModelVariant(side0),
                                                                 PastelModelHelper.createModelVariant(side1),
                                                                 PastelModelHelper.createModelVariant(sideAlt0),
                                                                 PastelModelHelper.createModelVariant(sideAlt1)
                                                             )
                                                             .with(
                                                                 Condition.or(
                                                                     noSides, Condition.condition()
                                                                                       .term(
                                                                                           PrimordialFireBlock.SOUTH,
                                                                                           true
                                                                                       )
                                                                 ), PastelModelHelper.createModelVariant(side0)
                                                                                     .with(
                                                                                         VariantProperties.Y_ROT,
                                                                                         VariantProperties.Rotation.R180
                                                                                     ),
                                                                 PastelModelHelper.createModelVariant(side1)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R180
                                                                                  ),
                                                                 PastelModelHelper.createModelVariant(sideAlt0)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R180
                                                                                  ),
                                                                 PastelModelHelper.createModelVariant(sideAlt1)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R180
                                                                                  )
                                                             )
                                                             .with(
                                                                 Condition.or(
                                                                     noSides, Condition.condition()
                                                                                       .term(
                                                                                           PrimordialFireBlock.WEST,
                                                                                           true
                                                                                       )
                                                                 ), PastelModelHelper.createModelVariant(side0)
                                                                                     .with(
                                                                                         VariantProperties.Y_ROT,
                                                                                         VariantProperties.Rotation.R270
                                                                                     ),
                                                                 PastelModelHelper.createModelVariant(side1)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R270
                                                                                  ),
                                                                 PastelModelHelper.createModelVariant(sideAlt0)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R270
                                                                                  ),
                                                                 PastelModelHelper.createModelVariant(sideAlt1)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R270
                                                                                  )
                                                             )
                                                             .with(
                                                                 Condition.or(
                                                                     noSides, Condition.condition()
                                                                                       .term(
                                                                                           PrimordialFireBlock.EAST,
                                                                                           true
                                                                                       )
                                                                 ), PastelModelHelper.createModelVariant(side0)
                                                                                     .with(
                                                                                         VariantProperties.Y_ROT,
                                                                                         VariantProperties.Rotation.R90
                                                                                     ),
                                                                 PastelModelHelper.createModelVariant(side1)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R90
                                                                                  ),
                                                                 PastelModelHelper.createModelVariant(sideAlt0)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R90
                                                                                  ),
                                                                 PastelModelHelper.createModelVariant(sideAlt1)
                                                                                  .with(
                                                                                      VariantProperties.Y_ROT,
                                                                                      VariantProperties.Rotation.R90
                                                                                  )
                                                             ));
    }

    public static void generateRedstoneTimerModel(BlockModelGenerators generators) {
        MultiPartGenerator rTimerMultipart = MultiPartGenerator.multiPart(PastelBlocks.REDSTONE_TIMER.get());
        ResourceLocation on = PastelModels.REDSTONE_TIMER.create(
            PastelBlocks.REDSTONE_TIMER.get(),
            new TextureMapping().put(PastelTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH)),
            generators.modelOutput
        );
        ResourceLocation off = PastelModels.REDSTONE_TIMER.createWithSuffix(
            PastelBlocks.REDSTONE_TIMER.get(), "_off",
            new TextureMapping().put(
                PastelTextureKeys.LIGHT,
                TextureMapping.getBlockTexture(
                    REDSTONE_TORCH, "_off")
            ), generators.modelOutput
        );
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            VariantProperties.Rotation rotation = PastelModelHelper.getSouthDefaultRotation(direction);
            rTimerMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(BlockStateProperties.POWERED, true), PastelModelHelper.createModelVariant(on)
                                                                                     .with(
                                                                                         VariantProperties.Y_ROT,
                                                                                         rotation
                                                                                     )
            );
            rTimerMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(BlockStateProperties.POWERED, false), PastelModelHelper.createModelVariant(off)
                                                                                      .with(
                                                                                          VariantProperties.Y_ROT,
                                                                                          rotation
                                                                                      )
            );
            for (RedstoneTimerBlock.TimingStep step : RedstoneTimerBlock.TimingStep.values()) {
                rTimerMultipart.with(
                    Condition.condition()
                             .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                             .term(RedstoneTimerBlock.ACTIVE_TIME, step), PastelModelHelper.createModelVariant(
                                                                                               PastelBlocks.REDSTONE_TIMER.get(), "_left_" + step.ordinal())
                                                                                           .with(
                                                                                               VariantProperties.UV_LOCK,
                                                                                               true
                                                                                           )
                                                                                           .with(
                                                                                               VariantProperties.Y_ROT,
                                                                                               rotation
                                                                                           )
                );
                rTimerMultipart.with(
                    Condition.condition()
                             .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                             .term(RedstoneTimerBlock.INACTIVE_TIME, step), PastelModelHelper.createModelVariant(
                                                                                                 PastelBlocks.REDSTONE_TIMER.get(), "_right_" + step.ordinal())
                                                                                             .with(
                                                                                                 VariantProperties.UV_LOCK,
                                                                                                 true
                                                                                             )
                                                                                             .with(
                                                                                                 VariantProperties.Y_ROT,
                                                                                                 rotation
                                                                                             )
                );
            }
        }
        generators.blockStateOutput.accept(rTimerMultipart);
    }

    public static void generateRedstoneTransceiverModel(BlockModelGenerators generators) {
        MultiPartGenerator rTransMultipart = MultiPartGenerator.multiPart(PastelBlocks.REDSTONE_TRANSCEIVER.get());
        ResourceLocation senderOn = PastelModels.REDSTONE_TRANSCEIVER_SENDER.createWithSuffix(
            PastelBlocks.REDSTONE_TRANSCEIVER.get(), "_sender",
            new TextureMapping().put(PastelTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH)),
            generators.modelOutput
        );
        ResourceLocation senderOff = PastelModels.REDSTONE_TRANSCEIVER_SENDER.createWithSuffix(
            PastelBlocks.REDSTONE_TRANSCEIVER.get(), "_sender_off",
            new TextureMapping().put(PastelTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH, "_off")),
            generators.modelOutput
        );
        ResourceLocation receiverOn = PastelModels.REDSTONE_TRANSCEIVER_RECEIVER.createWithSuffix(
            PastelBlocks.REDSTONE_TRANSCEIVER.get(), "_receiver",
            new TextureMapping().put(PastelTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH)),
            generators.modelOutput
        );
        ResourceLocation receiverOff = PastelModels.REDSTONE_TRANSCEIVER_RECEIVER.createWithSuffix(
            PastelBlocks.REDSTONE_TRANSCEIVER.get(), "_receiver_off",
            new TextureMapping().put(PastelTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH, "_off")),
            generators.modelOutput
        );
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            VariantProperties.Rotation rotation = PastelModelHelper.getSouthDefaultRotation(direction);
            rTransMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(RedstoneTransceiverBlock.SENDER, true)
                         .term(BlockStateProperties.POWERED, true), PastelModelHelper.createModelVariant(senderOn)
                                                                                     .with(
                                                                                         VariantProperties.Y_ROT,
                                                                                         rotation
                                                                                     )
            );
            rTransMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(RedstoneTransceiverBlock.SENDER, true)
                         .term(BlockStateProperties.POWERED, false), PastelModelHelper.createModelVariant(senderOff)
                                                                                      .with(
                                                                                          VariantProperties.Y_ROT,
                                                                                          rotation
                                                                                      )
            );
            rTransMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(RedstoneTransceiverBlock.SENDER, false)
                         .term(BlockStateProperties.POWERED, true), PastelModelHelper.createModelVariant(receiverOn)
                                                                                     .with(
                                                                                         VariantProperties.Y_ROT,
                                                                                         rotation
                                                                                     )
            );
            rTransMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(RedstoneTransceiverBlock.SENDER, false)
                         .term(BlockStateProperties.POWERED, false), PastelModelHelper.createModelVariant(receiverOff)
                                                                                      .with(
                                                                                          VariantProperties.Y_ROT,
                                                                                          rotation
                                                                                      )
            );
        }
        for (DyeColor color : DyeColor.values()) {
            ResourceLocation channel = PastelModels.REDSTONE_TRANSCEIVER_CHANNEL.createWithSuffix(
                PastelBlocks.REDSTONE_TRANSCEIVER.get(), "_channel_" + color.getSerializedName(),
                PastelTextureMaps.all(PastelCommon.locate("block/" + color.getSerializedName() + "_block")),
                generators.modelOutput
            );
            rTransMultipart.with(
                Condition.condition()
                         .term(RedstoneTransceiverBlock.CHANNEL, color), PastelModelHelper.createModelVariant(channel)
            );
        }
        generators.blockStateOutput.accept(rTransMultipart);
    }

    public static void generateRedstoneCalculatorModel(BlockModelGenerators generators) {
        MultiPartGenerator rCalcMultipart = MultiPartGenerator.multiPart(PastelBlocks.REDSTONE_CALCULATOR.get());
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            VariantProperties.Rotation rotation = PastelModelHelper.getSouthDefaultRotation(direction);
            rCalcMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(BlockStateProperties.POWERED, true),
                PastelModelHelper.createModelVariant(PastelBlocks.REDSTONE_CALCULATOR.get(), "_base")
                                 .with(VariantProperties.Y_ROT, rotation)
            );
            rCalcMultipart.with(
                Condition.condition()
                         .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                         .term(BlockStateProperties.POWERED, false),
                PastelModelHelper.createModelVariant(PastelBlocks.REDSTONE_CALCULATOR.get(), "_base_off")
                                 .with(VariantProperties.Y_ROT, rotation)
            );
            for (RedstoneCalculatorBlock.CalculationMode mode : RedstoneCalculatorBlock.CalculationMode.values()) {
                rCalcMultipart.with(
                    Condition.condition()
                             .term(BlockStateProperties.HORIZONTAL_FACING, direction)
                             .term(RedstoneCalculatorBlock.CALCULATION_MODE, mode),
                    PastelModelHelper.createModelVariant(
                                         PastelBlocks.REDSTONE_CALCULATOR.get(), "_" + mode.getSerializedName())
                                     .with(VariantProperties.UV_LOCK, true)
                                     .with(VariantProperties.Y_ROT, rotation)
                );
            }
        }
        generators.blockStateOutput.accept(rCalcMultipart);
    }

    public static void decay(BlockModelGenerators generators, DeferredBlock<Block> block) {
        ResourceLocation none = ModelTemplates.CUBE_ALL.createWithSuffix(
            block.get(), "_none", PastelTextureMaps.all(block.get(), "_none"), generators.modelOutput);
        ResourceLocation def = ModelTemplates.CUBE_ALL.createWithSuffix(
            block.get(), "_default", PastelTextureMaps.all(block.get(), "_default"), generators.modelOutput);
        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block.get())
                                                                .with(PropertyDispatch.property(DecayBlock.CONVERSION)
                                                                                      .select(
                                                                                          DecayBlock.Conversion.NONE,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              none)
                                                                                      )
                                                                                      .select(
                                                                                          DecayBlock.Conversion.DEFAULT,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              def)
                                                                                      )
                                                                                      .select(
                                                                                          DecayBlock.Conversion.SPECIAL,
                                                                                          PastelModelHelper.createModelVariant(
                                                                                              def)
                                                                                      )));
    }

    public static void generateItemModels(ItemModelGenerators generators) {
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.ENDER_GLASS, PastelBlocks.ENDER_GLASS.get(), "_solid");
        PastelModelHelper.registerBlockTexturedItemModel(generators, PastelBlocks.INCANDESCENT_AMALGAM.get());

        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_SPEED, PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_SPEED2, PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_SPEED3, PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_EFFICIENCY, PastelBlocks.UPGRADE_EFFICIENCY.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_EFFICIENCY2, PastelBlocks.UPGRADE_EFFICIENCY.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_YIELD, PastelBlocks.UPGRADE_YIELD.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_YIELD2, PastelBlocks.UPGRADE_YIELD.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_EXPERIENCE, PastelBlocks.UPGRADE_EXPERIENCE.get());
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.UPGRADE_EXPERIENCE2, PastelBlocks.UPGRADE_EXPERIENCE.get());

        PastelModelHelper.registerItemModel(generators, PastelBlocks.ENDER_HOPPER.asItem());

        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.CRACKED_END_PORTAL_FRAME, PastelBlocks.CRACKED_END_PORTAL_FRAME.get(), "_none");
        PastelModelHelper.registerLayeredItemModel(
            generators, PastelBlocks.MEMORY.asItem(), ModelTemplates.THREE_LAYERED_ITEM, "_base", "_overlay",
            "_brighten"
        );

        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.PARTICLE_SPAWNER, PastelBlocks.PARTICLE_SPAWNER.get(), "_off");
        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.CREATIVE_PARTICLE_SPAWNER, PastelBlocks.PARTICLE_SPAWNER.get(), "_off");

        PastelModelHelper.registerParentedItemModel(
            generators, PastelBlocks.TEMPORAL_SHIMMERSTONE_LIGHT, PastelBlocks.SHIMMERSTONE_LIGHT.get());

        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.AXOLOTL_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.BAT_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.BEE_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.BLAZE_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.CAT_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.CHICKEN_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.COW_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.CREEPER_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.ENDER_DRAGON_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.ENDERMAN_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.ENDERMITE_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.EVOKER_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.FISH_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.FOX_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.GHAST_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.GLOW_SQUID_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.GOAT_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.GUARDIAN_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.HORSE_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.ILLUSIONER_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.OCELOT_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.PARROT_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.PHANTOM_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.PIG_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.PIGLIN_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.POLAR_BEAR_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.PUFFERFISH_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.RABBIT_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SHEEP_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SHULKER_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SILVERFISH_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SKELETON_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SLIME_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SNOW_GOLEM_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SPIDER_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.SQUID_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.STRAY_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.STRIDER_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.TURTLE_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.WITCH_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.WITHER_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.WITHER_SKELETON_IDOL,PastelModels.MOB_BLOCK);
        PastelModelHelper.registerParentedItemModel(generators, PastelBlocks.ZOMBIE_IDOL,PastelModels.MOB_BLOCK);
    }
}
