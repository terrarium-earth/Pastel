package earth.terrarium.pastel.data.models.block;

import com.simibubi.create.foundation.model.BakedModelHelper;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.*;
import earth.terrarium.pastel.blocks.amalgam.IncandescentAmalgamBlock;
import earth.terrarium.pastel.blocks.amalgam.IncandescentAmalgamItem;
import earth.terrarium.pastel.blocks.block_flooder.BlockFlooderBlock;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleBlock;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleItem;
import earth.terrarium.pastel.blocks.chests.BlackHoleChestBlock;
import earth.terrarium.pastel.blocks.chests.CompactingChestBlock;
import earth.terrarium.pastel.blocks.chests.FabricationChestBlock;
import earth.terrarium.pastel.blocks.chests.HeartboundChestBlock;
import earth.terrarium.pastel.blocks.cinderhearth.CinderhearthBlock;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlock;
import earth.terrarium.pastel.blocks.decay.*;
import earth.terrarium.pastel.blocks.decoration.DecayingLightBlock;
import earth.terrarium.pastel.blocks.decoration.EtherealPlatformBlock;
import earth.terrarium.pastel.blocks.decoration.WandLightBlock;
import earth.terrarium.pastel.blocks.ender.EnderDropperBlock;
import earth.terrarium.pastel.blocks.ender.EnderHopperBlock;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlock;
import earth.terrarium.pastel.blocks.energy.CrystalApothecaryBlock;
import earth.terrarium.pastel.blocks.gravity.FloatBlock;
import earth.terrarium.pastel.blocks.gravity.FloatBlockItem;
import earth.terrarium.pastel.blocks.idols.*;
import earth.terrarium.pastel.blocks.lava_sponge.LavaSpongeBlock;
import earth.terrarium.pastel.blocks.lava_sponge.WetLavaSpongeBlock;
import earth.terrarium.pastel.blocks.lava_sponge.WetLavaSpongeItem;
import earth.terrarium.pastel.blocks.memory.MemoryBlock;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.blocks.particle_spawner.CreativeParticleSpawnerBlock;
import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerBlock;
import earth.terrarium.pastel.blocks.present.PresentBlock;
import earth.terrarium.pastel.blocks.present.PresentBlockItem;
import earth.terrarium.pastel.blocks.redstone.*;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlock;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlockItem;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.entity.entity.LivingMarkerEntity;
import earth.terrarium.pastel.registries.PastelBlockSoundGroups;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFoodComponents;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.client.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Function;

import static net.minecraft.world.level.block.Blocks.*;

public class FunctionalBlockModels {
    public static void generateBlockModels(BlockModelGenerators generators) {

        generatePrimfireModel(generators);

        PastelModelHelper.simple(generators, PastelBlocks.ETHEREAL_PLATFORM);

        PastelModelHelper.simple(generators, PastelBlocks.UNIVERSE_SPYHOLE);

        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.PRESENT);
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

        PastelModelHelper.simple(generators, PastelBlocks.BLOCK_FLOODER);

        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.BOTTOMLESS_BUNDLE);

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

        PastelModelHelper.singleton(
            generators, PastelBlocks.SHIMMERSTONE_LIGHT,
            PastelTexturedModels.particle(PastelTextures.SHIMMERSTONE_LIGHT)
        );
        PastelModelHelper.parented(
            generators, PastelBlocks.TEMPORAL_SHIMMERSTONE_LIGHT.get(), PastelBlocks.SHIMMERSTONE_LIGHT.get());

        decay(generators, PastelBlocks.FADING);
        decay(generators, PastelBlocks.FAILING);
        decay(generators, PastelBlocks.RUIN);
        decay(generators, PastelBlocks.FORFEITURE);


        PastelModelHelper.simple(generators, PastelBlocks.DECAY_AWAY);

        PastelModelHelper.singleton(generators, PastelBlocks.HOVERBLOCK, TexturedModel.COLUMN);

        PastelModelHelper.defaultNorthHorizontalFacing(
            generators, PastelBlocks.HEARTBOUND_CHEST, ModelLocationUtils::getModelLocation);
        PastelModelHelper.defaultNorthHorizontalFacing(
            generators, PastelBlocks.COMPACTING_CHEST, ModelLocationUtils::getModelLocation);


        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.FABRICATION_CHEST);
        PastelModelHelper.defaultNorthHorizontalFacing(
            generators, PastelBlocks.FABRICATION_CHEST, ModelLocationUtils::getModelLocation);


        PastelModelHelper.defaultNorthHorizontalFacing(
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
        PastelModelHelper.singletonWithSoup(
            generators, PastelBlocks.CREATIVE_PARTICLE_SPAWNER,
            b -> ModelLocationUtils.getModelLocation(
                PastelBlocks.PARTICLE_SPAWNER.get())
        );

        PastelModelHelper.defaultSouthHorizontalFacing(
            generators, PastelBlocks.BEDROCK_ANVIL, ModelLocationUtils::getModelLocation);


        PastelModelHelper.singletonWithSoup(generators, PastelBlocks.MEMORY, ModelLocationUtils::getModelLocation);

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

        PastelModelHelper.simple(generators, PastelBlocks.LAVA_SPONGE);
        PastelModelHelper.simple(generators, PastelBlocks.WET_LAVA_SPONGE);

        PastelModelHelper.detector(generators, PastelBlocks.BLOCK_LIGHT_DETECTOR);
        PastelModelHelper.detector(generators, PastelBlocks.WEATHER_DETECTOR);
        PastelModelHelper.detector(generators, PastelBlocks.ITEM_DETECTOR);
        PastelModelHelper.detector(generators, PastelBlocks.PLAYER_DETECTOR);
        PastelModelHelper.detector(generators, PastelBlocks.CREATURE_DETECTOR);

        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.REDSTONE_TIMER);

        generateRedstoneTimerModel(generators);

        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.REDSTONE_CALCULATOR);

        generateRedstoneCalculatorModel(generators);

        PastelModelHelper.predefinedItemModel(generators, PastelBlocks.REDSTONE_TRANSCEIVER);
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

        PastelModelHelper.orientable(generators, PastelBlocks.ENDER_DROPPER);
        PastelModelHelper.singletonWithSoup(
            generators, PastelBlocks.ENDER_HOPPER, ModelLocationUtils::getModelLocation);

        generators.blockStateOutput.accept(MultiVariantGenerator.multiVariant(PastelBlocks.IMBRIFER_PORTAL.get())
                                                                .with(PastelModelHelper.createBooleanModelMap(
                                                                    DeeperDownPortalBlock.FACING_UP,
                                                                    ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.IMBRIFER_PORTAL.get(), "_up"),
                                                                    ModelLocationUtils.getModelLocation(
                                                                        PastelBlocks.IMBRIFER_PORTAL.get())
                                                                )));

        PastelModelHelper.parented(generators, PastelBlocks.UPGRADE_SPEED.get(), PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.parented(generators, PastelBlocks.UPGRADE_SPEED2.get(), PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.parented(generators, PastelBlocks.UPGRADE_SPEED3.get(), PastelBlocks.UPGRADE_SPEED.get());
        PastelModelHelper.parented(
            generators, PastelBlocks.UPGRADE_EFFICIENCY.get(), PastelBlocks.UPGRADE_EFFICIENCY.get());
        PastelModelHelper.parented(
            generators, PastelBlocks.UPGRADE_EFFICIENCY2.get(), PastelBlocks.UPGRADE_EFFICIENCY.get());
        PastelModelHelper.parented(generators, PastelBlocks.UPGRADE_YIELD.get(), PastelBlocks.UPGRADE_YIELD.get());
        PastelModelHelper.parented(generators, PastelBlocks.UPGRADE_YIELD2.get(), PastelBlocks.UPGRADE_YIELD.get());
        PastelModelHelper.parented(
            generators, PastelBlocks.UPGRADE_EXPERIENCE.get(), PastelBlocks.UPGRADE_EXPERIENCE.get());
        PastelModelHelper.parented(
            generators, PastelBlocks.UPGRADE_EXPERIENCE2.get(), PastelBlocks.UPGRADE_EXPERIENCE.get());

        PastelModelHelper.simple(generators, PastelBlocks.REDSTONE_SAND);


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

        PastelModelHelper.singletonWithSoup(
            generators, PastelBlocks.INCANDESCENT_AMALGAM, ModelLocationUtils::getModelLocation);

        PastelModelHelper.idol(generators, PastelBlocks.AXOLOTL_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.BAT_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.BEE_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.BLAZE_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.CAT_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.CHICKEN_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.COW_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.CREEPER_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.ENDER_DRAGON_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.ENDERMAN_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.ENDERMITE_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.EVOKER_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.FISH_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.FOX_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.GHAST_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.GLOW_SQUID_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.GOAT_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.GUARDIAN_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.HORSE_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.ILLUSIONER_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.OCELOT_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.PARROT_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.PHANTOM_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.PIG_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.PIGLIN_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.POLAR_BEAR_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.PUFFERFISH_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.RABBIT_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SHEEP_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SHULKER_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SILVERFISH_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SKELETON_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SLIME_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SNOW_GOLEM_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SPIDER_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.SQUID_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.STRAY_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.STRIDER_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.TURTLE_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.WITCH_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.WITHER_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.WITHER_SKELETON_IDOL);
        PastelModelHelper.idol(generators, PastelBlocks.ZOMBIE_IDOL);
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
    }
}
