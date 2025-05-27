package de.dafuqs.spectrum.registries;

import com.mojang.datafixers.util.*;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.color.ItemColors;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.amalgam.IncandescentAmalgamBlock;
import de.dafuqs.spectrum.blocks.amalgam.IncandescentAmalgamItem;
import de.dafuqs.spectrum.blocks.amphora.AmphoraBlock;
import de.dafuqs.spectrum.blocks.block_flooder.BlockFlooderBlock;
import de.dafuqs.spectrum.blocks.bottomless_bundle.BottomlessBundleBlock;
import de.dafuqs.spectrum.blocks.bottomless_bundle.BottomlessBundleItem;
import de.dafuqs.spectrum.blocks.chests.BlackHoleChestBlock;
import de.dafuqs.spectrum.blocks.chests.CompactingChestBlock;
import de.dafuqs.spectrum.blocks.chests.FabricationChestBlock;
import de.dafuqs.spectrum.blocks.chests.HeartboundChestBlock;
import de.dafuqs.spectrum.blocks.cinderhearth.CinderhearthBlock;
import de.dafuqs.spectrum.blocks.conditional.CloakedOreBlock;
import de.dafuqs.spectrum.blocks.conditional.FourLeafCloverBlock;
import de.dafuqs.spectrum.blocks.conditional.GemstoneOreBlock;
import de.dafuqs.spectrum.blocks.conditional.MermaidsBrushBlock;
import de.dafuqs.spectrum.blocks.conditional.QuitoxicReedsBlock;
import de.dafuqs.spectrum.blocks.conditional.RadiatingEnderBlock;
import de.dafuqs.spectrum.blocks.conditional.StuckStormStoneBlock;
import de.dafuqs.spectrum.blocks.conditional.amaranth.AmaranthBushelBlock;
import de.dafuqs.spectrum.blocks.conditional.amaranth.AmaranthCropBlock;
import de.dafuqs.spectrum.blocks.conditional.amaranth.PottedAmaranthBushelBlock;
import de.dafuqs.spectrum.blocks.conditional.blood_orchid.BloodOrchidBlock;
import de.dafuqs.spectrum.blocks.conditional.blood_orchid.PottedBloodOrchidBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredFenceBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredFenceGateBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredLeavesBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredLightBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredLogBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredPlankBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredPressurePlateBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredSaplingBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredSlabBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredSporeBlossomBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredStairsBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredStrippedLogBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredStrippedWoodBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredWoodBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredWoodenButtonBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.PottedColoredSaplingBlock;
import de.dafuqs.spectrum.blocks.conditional.resonant_lily.PottedResonantLilyBlock;
import de.dafuqs.spectrum.blocks.conditional.resonant_lily.ResonantLilyBlock;
import de.dafuqs.spectrum.blocks.crystallarieum.CrystallarieumBlock;
import de.dafuqs.spectrum.blocks.crystallarieum.SpectrumClusterBlock;
import de.dafuqs.spectrum.blocks.decay.BlackMateriaBlock;
import de.dafuqs.spectrum.blocks.decay.DecayAwayBlock;
import de.dafuqs.spectrum.blocks.decay.DecayBlock;
import de.dafuqs.spectrum.blocks.decay.FadingBlock;
import de.dafuqs.spectrum.blocks.decay.FailingBlock;
import de.dafuqs.spectrum.blocks.decay.ForfeitureBlock;
import de.dafuqs.spectrum.blocks.decay.RuinBlock;
import de.dafuqs.spectrum.blocks.decoration.AlternatePlayerOnlyGlassBlock;
import de.dafuqs.spectrum.blocks.decoration.CardinalFacingBlock;
import de.dafuqs.spectrum.blocks.decoration.CloverBlock;
import de.dafuqs.spectrum.blocks.decoration.CushionBlock;
import de.dafuqs.spectrum.blocks.decoration.CushionedCarpetBlock;
import de.dafuqs.spectrum.blocks.decoration.CushionedFacingBlock;
import de.dafuqs.spectrum.blocks.decoration.DecayingLightBlock;
import de.dafuqs.spectrum.blocks.decoration.DiagonalBlock;
import de.dafuqs.spectrum.blocks.decoration.EtherealPlatformBlock;
import de.dafuqs.spectrum.blocks.decoration.FlexLanternBlock;
import de.dafuqs.spectrum.blocks.decoration.GemstoneChimeBlock;
import de.dafuqs.spectrum.blocks.decoration.GemstoneGlassBlock;
import de.dafuqs.spectrum.blocks.decoration.GemstonePlayerOnlyGlassBlock;
import de.dafuqs.spectrum.blocks.decoration.GlowBlock;
import de.dafuqs.spectrum.blocks.decoration.PigmentBlock;
import de.dafuqs.spectrum.blocks.decoration.ProjectorBlock;
import de.dafuqs.spectrum.blocks.decoration.PylonBlock;
import de.dafuqs.spectrum.blocks.decoration.RadiantGlassBlock;
import de.dafuqs.spectrum.blocks.decoration.ShimmerstoneBlock;
import de.dafuqs.spectrum.blocks.decoration.ShimmerstoneLightBlock;
import de.dafuqs.spectrum.blocks.decoration.ShinglesBlock;
import de.dafuqs.spectrum.blocks.decoration.SpectrumBedBlock;
import de.dafuqs.spectrum.blocks.decoration.SpectrumFacingBlock;
import de.dafuqs.spectrum.blocks.decoration.SpectrumLineFacingBlock;
import de.dafuqs.spectrum.blocks.decoration.WandLightBlock;
import de.dafuqs.spectrum.blocks.deeper_down.BlackSludgePlantBlock;
import de.dafuqs.spectrum.blocks.deeper_down.DragonboneBlock;
import de.dafuqs.spectrum.blocks.deeper_down.HummingstoneBlock;
import de.dafuqs.spectrum.blocks.deeper_down.PyriteRipperBlock;
import de.dafuqs.spectrum.blocks.deeper_down.StrippingLootPillarBlock;
import de.dafuqs.spectrum.blocks.deeper_down.WeepingGalaFrondsTipBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.AbyssalVineBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.AloeBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.AshFloraBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.BristleSproutsBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.DoomBloomBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.Dragonjag;
import de.dafuqs.spectrum.blocks.deeper_down.flora.GiantMossBallBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.MossBallBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.NightdewBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.SawbladeHollyBushBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.SmallDragonjagBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.SnappingIvyBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.TallDragonjagBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.TriStateVineBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.WeepingGalaFrondsBlock;
import de.dafuqs.spectrum.blocks.deeper_down.flora.WeepingGalaSprigBlock;
import de.dafuqs.spectrum.blocks.deeper_down.groundcover.AshBlock;
import de.dafuqs.spectrum.blocks.deeper_down.groundcover.AshPileBlock;
import de.dafuqs.spectrum.blocks.deeper_down.groundcover.BlackslagBlock;
import de.dafuqs.spectrum.blocks.deeper_down.groundcover.BlackslagVegetationBlock;
import de.dafuqs.spectrum.blocks.deeper_down.groundcover.SlushVegetationBlock;
import de.dafuqs.spectrum.blocks.deeper_down.groundcover.VariableHeightBlock;
import de.dafuqs.spectrum.blocks.enchanter.EnchanterBlock;
import de.dafuqs.spectrum.blocks.ender.EnderDropperBlock;
import de.dafuqs.spectrum.blocks.ender.EnderHopperBlock;
import de.dafuqs.spectrum.blocks.energy.ColorPickerBlock;
import de.dafuqs.spectrum.blocks.energy.CrystalApothecaryBlock;
import de.dafuqs.spectrum.blocks.farming.ExtraTickFarmlandBlock;
import de.dafuqs.spectrum.blocks.farming.TilledShaleClayBlock;
import de.dafuqs.spectrum.blocks.farming.TilledSlushBlock;
import de.dafuqs.spectrum.blocks.fluid.DragonrotFluidBlock;
import de.dafuqs.spectrum.blocks.fluid.GooFluidBlock;
import de.dafuqs.spectrum.blocks.fluid.LiquidCrystalFluidBlock;
import de.dafuqs.spectrum.blocks.fluid.MidnightSolutionFluidBlock;
import de.dafuqs.spectrum.blocks.fusion_shrine.FusionShrineBlock;
import de.dafuqs.spectrum.blocks.gemstone.SpectrumBuddingBlock;
import de.dafuqs.spectrum.blocks.gemstone.SpectrumGemstoneBlock;
import de.dafuqs.spectrum.blocks.geology.AzuriteOreBlock;
import de.dafuqs.spectrum.blocks.geology.ShimmerstoneOreBlock;
import de.dafuqs.spectrum.blocks.gravity.FloatBlock;
import de.dafuqs.spectrum.blocks.gravity.FloatBlockItem;
import de.dafuqs.spectrum.blocks.idols.AoEStatusEffectIdolBlock;
import de.dafuqs.spectrum.blocks.idols.BonemealingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.EntitySummoningIdolBlock;
import de.dafuqs.spectrum.blocks.idols.ExplosionIdolBlock;
import de.dafuqs.spectrum.blocks.idols.FallDamageNegatingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.FeedingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.FirestarterIdolBlock;
import de.dafuqs.spectrum.blocks.idols.FreezingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.IdolBlock;
import de.dafuqs.spectrum.blocks.idols.InsomniaIdolBlock;
import de.dafuqs.spectrum.blocks.idols.KnockbackIdolBlock;
import de.dafuqs.spectrum.blocks.idols.LineTeleportingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.MilkingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.PiglinTradeIdolBlock;
import de.dafuqs.spectrum.blocks.idols.ProjectileIdolBlock;
import de.dafuqs.spectrum.blocks.idols.RandomTeleportingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.ShearingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.SilverfishInsertingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.SlimeSizingIdolBlock;
import de.dafuqs.spectrum.blocks.idols.StatusEffectIdolBlock;
import de.dafuqs.spectrum.blocks.idols.VillagerConvertingIdolBlock;
import de.dafuqs.spectrum.blocks.item_bowl.ItemBowlBlock;
import de.dafuqs.spectrum.blocks.item_roundel.ItemRoundelBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeVineBulbBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeVinePetalBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeVinePlantBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeVineRootsBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeiteLotusBulbBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeiteLotusFlowerBlock;
import de.dafuqs.spectrum.blocks.jade_vines.JadeiteLotusStemBlock;
import de.dafuqs.spectrum.blocks.jade_vines.NephriteBlossomBulbBlock;
import de.dafuqs.spectrum.blocks.jade_vines.NephriteBlossomLeavesBlock;
import de.dafuqs.spectrum.blocks.jade_vines.NephriteBlossomStemBlock;
import de.dafuqs.spectrum.blocks.jade_vines.StemComponent;
import de.dafuqs.spectrum.blocks.lava_sponge.LavaSpongeBlock;
import de.dafuqs.spectrum.blocks.lava_sponge.WetLavaSpongeBlock;
import de.dafuqs.spectrum.blocks.lava_sponge.WetLavaSpongeItem;
import de.dafuqs.spectrum.blocks.memory.MemoryBlock;
import de.dafuqs.spectrum.blocks.memory.MemoryItem;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlock;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlockItem;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullType;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumWallSkullBlock;
import de.dafuqs.spectrum.blocks.particle_spawner.CreativeParticleSpawnerBlock;
import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerBlock;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.PastelNodeBlock;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.PastelNodeType;
import de.dafuqs.spectrum.blocks.pedestal.BuiltinPedestalVariant;
import de.dafuqs.spectrum.blocks.pedestal.PedestalBlock;
import de.dafuqs.spectrum.blocks.pedestal.PedestalBlockItem;
import de.dafuqs.spectrum.blocks.potion_workshop.PotionWorkshopBlock;
import de.dafuqs.spectrum.blocks.present.PresentBlock;
import de.dafuqs.spectrum.blocks.present.PresentBlockItem;
import de.dafuqs.spectrum.blocks.redstone.BlockBreakerBlock;
import de.dafuqs.spectrum.blocks.redstone.BlockDetectorBlock;
import de.dafuqs.spectrum.blocks.redstone.BlockLightDetectorBlock;
import de.dafuqs.spectrum.blocks.redstone.BlockPlacerBlock;
import de.dafuqs.spectrum.blocks.redstone.EnderGlassBlock;
import de.dafuqs.spectrum.blocks.redstone.EntityDetectorBlock;
import de.dafuqs.spectrum.blocks.redstone.ItemDetectorBlock;
import de.dafuqs.spectrum.blocks.redstone.PlayerDetectorBlock;
import de.dafuqs.spectrum.blocks.redstone.RedstoneCalculatorBlock;
import de.dafuqs.spectrum.blocks.redstone.RedstoneGravityBlock;
import de.dafuqs.spectrum.blocks.redstone.RedstoneTimerBlock;
import de.dafuqs.spectrum.blocks.redstone.RedstoneTransceiverBlock;
import de.dafuqs.spectrum.blocks.redstone.WeatherDetectorBlock;
import de.dafuqs.spectrum.blocks.shooting_star.ShootingStar;
import de.dafuqs.spectrum.blocks.shooting_star.ShootingStarBlock;
import de.dafuqs.spectrum.blocks.shooting_star.ShootingStarItem;
import de.dafuqs.spectrum.blocks.spirit_instiller.SpiritInstillerBlock;
import de.dafuqs.spectrum.blocks.spirit_sallow.OminousSaplingBlock;
import de.dafuqs.spectrum.blocks.spirit_sallow.OminousSaplingBlockItem;
import de.dafuqs.spectrum.blocks.spirit_sallow.SpiritSallowLeavesBlock;
import de.dafuqs.spectrum.blocks.spirit_sallow.SpiritVine;
import de.dafuqs.spectrum.blocks.spirit_sallow.SpiritVinesPlantBlock;
import de.dafuqs.spectrum.blocks.spirit_sallow.SpiritVinesPlantStemBlock;
import de.dafuqs.spectrum.blocks.statues.GrotesqueBlock;
import de.dafuqs.spectrum.blocks.statues.StatueBlock;
import de.dafuqs.spectrum.blocks.structure.DeepLightBlock;
import de.dafuqs.spectrum.blocks.structure.DikeGateBlock;
import de.dafuqs.spectrum.blocks.structure.DreamGateBlock;
import de.dafuqs.spectrum.blocks.structure.InvisibleWallBlock;
import de.dafuqs.spectrum.blocks.structure.ManxiBlock;
import de.dafuqs.spectrum.blocks.structure.PreservationBlockDetectorBlock;
import de.dafuqs.spectrum.blocks.structure.PreservationControllerBlock;
import de.dafuqs.spectrum.blocks.structure.PreservationRoundelBlock;
import de.dafuqs.spectrum.blocks.structure.TreasureChestBlock;
import de.dafuqs.spectrum.blocks.structure.TreasureItemBowlBlock;
import de.dafuqs.spectrum.blocks.titration_barrel.TitrationBarrelBlock;
import de.dafuqs.spectrum.blocks.upgrade.UpgradeBlock;
import de.dafuqs.spectrum.blocks.upgrade.UpgradeBlockItem;
import de.dafuqs.spectrum.blocks.upgrade.Upgradeable;
import de.dafuqs.spectrum.blocks.weathering.Weathering;
import de.dafuqs.spectrum.blocks.weathering.WeatheringBlock;
import de.dafuqs.spectrum.blocks.weathering.WeatheringSlabBlock;
import de.dafuqs.spectrum.blocks.weathering.WeatheringStairsBlock;
import de.dafuqs.spectrum.data.SpectrumModelHelper;
import de.dafuqs.spectrum.entity.SpectrumEntityTypes;
import de.dafuqs.spectrum.entity.entity.LivingMarkerEntity;
import de.dafuqs.spectrum.items.conditional.FourLeafCloverItem;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.particle.effect.ColoredFallingSporeBlossomParticleEffect;
import de.dafuqs.spectrum.particle.effect.ColoredSparkleRisingParticleEffect;
import de.dafuqs.spectrum.particle.effect.ColoredSporeBlossomAirParticleEffect;
import de.dafuqs.spectrum.recipe.pedestal.BuiltinGemstoneColor;
import de.dafuqs.spectrum.registries.SpectrumItems.IS;
import de.dafuqs.spectrum.registries.client.SpectrumModels;
import de.dafuqs.spectrum.registries.client.SpectrumTextureKeys;
import de.dafuqs.spectrum.registries.client.SpectrumTextureMaps;
import de.dafuqs.spectrum.registries.client.SpectrumTexturedModels;
import de.dafuqs.spectrum.registries.client.SpectrumTextures;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Vex;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.DragonFireball;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AttachedStemBlock;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.FungusBlock;
import net.minecraft.world.level.block.InfestedBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RedStoneOreBlock;
import net.minecraft.world.level.block.RedstoneLampBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.StemBlock;
import net.minecraft.world.level.block.TintedGlassBlock;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

import static de.dafuqs.spectrum.SpectrumCommon.locate;
import static net.minecraft.world.level.block.Blocks.AMETHYST_BLOCK;
import static net.minecraft.world.level.block.Blocks.AMETHYST_CLUSTER;
import static net.minecraft.world.level.block.Blocks.ANCIENT_DEBRIS;
import static net.minecraft.world.level.block.Blocks.ANVIL;
import static net.minecraft.world.level.block.Blocks.ATTACHED_MELON_STEM;
import static net.minecraft.world.level.block.Blocks.BASALT;
import static net.minecraft.world.level.block.Blocks.BLACKSTONE;
import static net.minecraft.world.level.block.Blocks.BONE_BLOCK;
import static net.minecraft.world.level.block.Blocks.CALCITE;
import static net.minecraft.world.level.block.Blocks.COAL_BLOCK;
import static net.minecraft.world.level.block.Blocks.COPPER_BLOCK;
import static net.minecraft.world.level.block.Blocks.DAYLIGHT_DETECTOR;
import static net.minecraft.world.level.block.Blocks.DEEPSLATE;
import static net.minecraft.world.level.block.Blocks.DEEPSLATE_IRON_ORE;
import static net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK;
import static net.minecraft.world.level.block.Blocks.DIRT;
import static net.minecraft.world.level.block.Blocks.DISPENSER;
import static net.minecraft.world.level.block.Blocks.DROPPER;
import static net.minecraft.world.level.block.Blocks.EMERALD_BLOCK;
import static net.minecraft.world.level.block.Blocks.END_STONE;
import static net.minecraft.world.level.block.Blocks.FARMLAND;
import static net.minecraft.world.level.block.Blocks.FIRE;
import static net.minecraft.world.level.block.Blocks.GLASS;
import static net.minecraft.world.level.block.Blocks.GLOWSTONE;
import static net.minecraft.world.level.block.Blocks.GOLD_BLOCK;
import static net.minecraft.world.level.block.Blocks.HOPPER;
import static net.minecraft.world.level.block.Blocks.IRON_BLOCK;
import static net.minecraft.world.level.block.Blocks.IRON_ORE;
import static net.minecraft.world.level.block.Blocks.LANTERN;
import static net.minecraft.world.level.block.Blocks.LAPIS_BLOCK;
import static net.minecraft.world.level.block.Blocks.LIGHT;
import static net.minecraft.world.level.block.Blocks.MELON;
import static net.minecraft.world.level.block.Blocks.MELON_STEM;
import static net.minecraft.world.level.block.Blocks.MUD;
import static net.minecraft.world.level.block.Blocks.NETHERRACK;
import static net.minecraft.world.level.block.Blocks.OAK_BUTTON;
import static net.minecraft.world.level.block.Blocks.OAK_FENCE;
import static net.minecraft.world.level.block.Blocks.OAK_FENCE_GATE;
import static net.minecraft.world.level.block.Blocks.OAK_LEAVES;
import static net.minecraft.world.level.block.Blocks.OAK_LOG;
import static net.minecraft.world.level.block.Blocks.OAK_PLANKS;
import static net.minecraft.world.level.block.Blocks.OAK_PRESSURE_PLATE;
import static net.minecraft.world.level.block.Blocks.OAK_SAPLING;
import static net.minecraft.world.level.block.Blocks.OAK_SLAB;
import static net.minecraft.world.level.block.Blocks.OAK_STAIRS;
import static net.minecraft.world.level.block.Blocks.OAK_WOOD;
import static net.minecraft.world.level.block.Blocks.POLISHED_ANDESITE;
import static net.minecraft.world.level.block.Blocks.POLISHED_DIORITE;
import static net.minecraft.world.level.block.Blocks.POLISHED_GRANITE;
import static net.minecraft.world.level.block.Blocks.POPPY;
import static net.minecraft.world.level.block.Blocks.PRISMARINE;
import static net.minecraft.world.level.block.Blocks.QUARTZ_BLOCK;
import static net.minecraft.world.level.block.Blocks.REDSTONE_BLOCK;
import static net.minecraft.world.level.block.Blocks.REDSTONE_LAMP;
import static net.minecraft.world.level.block.Blocks.REDSTONE_TORCH;
import static net.minecraft.world.level.block.Blocks.RED_BED;
import static net.minecraft.world.level.block.Blocks.RED_CARPET;
import static net.minecraft.world.level.block.Blocks.RED_WOOL;
import static net.minecraft.world.level.block.Blocks.REPEATER;
import static net.minecraft.world.level.block.Blocks.SAND;
import static net.minecraft.world.level.block.Blocks.SCULK;
import static net.minecraft.world.level.block.Blocks.SHORT_GRASS;
import static net.minecraft.world.level.block.Blocks.SKELETON_SKULL;
import static net.minecraft.world.level.block.Blocks.SMOOTH_BASALT;
import static net.minecraft.world.level.block.Blocks.SNOW;
import static net.minecraft.world.level.block.Blocks.SOUL_TORCH;
import static net.minecraft.world.level.block.Blocks.SOUL_WALL_TORCH;
import static net.minecraft.world.level.block.Blocks.SPONGE;
import static net.minecraft.world.level.block.Blocks.SPORE_BLOSSOM;
import static net.minecraft.world.level.block.Blocks.STONE;
import static net.minecraft.world.level.block.Blocks.STRIPPED_OAK_LOG;
import static net.minecraft.world.level.block.Blocks.STRIPPED_OAK_WOOD;
import static net.minecraft.world.level.block.Blocks.TINTED_GLASS;
import static net.minecraft.world.level.block.Blocks.WET_SPONGE;
import static net.minecraft.world.level.block.Blocks.WHITE_WOOL;
import static net.minecraft.world.level.block.Blocks.litBlockEmission;
import static net.minecraft.world.level.block.Blocks.woodenButton;

@SuppressWarnings({"unused"})
public class SpectrumBlocks {
	
	private static Properties settings(MapColor mapColor, SoundType blockSoundGroup, float strength) {
		return BlockBehaviour.Properties.of().mapColor(mapColor).sound(blockSoundGroup).strength(strength);
	}
	
	private static Properties settings(MapColor mapColor, SoundType blockSoundGroup, float strength, float resistance) {
		return settings(mapColor, blockSoundGroup, strength).explosionResistance(resistance);
	}
	
	private static Properties craftingBlock(MapColor mapColor, SoundType blockSoundGroup) {
		return settings(mapColor, blockSoundGroup, 5.0F, 8.0F).isRedstoneConductor(SpectrumBlocks::never).isViewBlocking(SpectrumBlocks::never).noOcclusion().requiresCorrectToolForDrops();
	}

	public static final DeferredRegister.Blocks COMMON_REGISTRAR = DeferredRegister.createBlocks(SpectrumCommon.MOD_ID);
	public static final DeferredRegistrar CLIENT_REGISTRAR = new DeferredRegistrar();

	public static final DeferredBlock<Block> PEDESTAL_BASIC_TOPAZ = register(pedestal(blockWithItem("pedestal_basic_topaz", () -> new PedestalBlock(craftingBlock(MapColor.DIAMOND, SpectrumBlockSoundGroups.TOPAZ_BLOCK), BuiltinPedestalVariant.BASIC_TOPAZ), block -> new PedestalBlockItem(block, IS.of(1), BuiltinPedestalVariant.BASIC_TOPAZ, "item.pastel.pedestal.tooltip.basic_topaz"), InkColors.WHITE)));
	public static final DeferredBlock<Block> PEDESTAL_BASIC_AMETHYST = register(pedestal(blockWithItem("pedestal_basic_amethyst", () -> new PedestalBlock(craftingBlock(MapColor.COLOR_PURPLE, SoundType.AMETHYST), BuiltinPedestalVariant.BASIC_AMETHYST), block -> new PedestalBlockItem(block, IS.of(1), BuiltinPedestalVariant.BASIC_AMETHYST, "item.pastel.pedestal.tooltip.basic_amethyst"), InkColors.WHITE)));
	public static final DeferredBlock<Block> PEDESTAL_BASIC_CITRINE = register(pedestal(blockWithItem("pedestal_basic_citrine", () -> new PedestalBlock(craftingBlock(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK), BuiltinPedestalVariant.BASIC_CITRINE), block -> new PedestalBlockItem(block, IS.of(1), BuiltinPedestalVariant.BASIC_CITRINE, "item.pastel.pedestal.tooltip.basic_citrine"), InkColors.WHITE)));
	public static final DeferredBlock<Block> PEDESTAL_ALL_BASIC = register(pedestal(blockWithItem("pedestal_all_basic", () -> new PedestalBlock(craftingBlock(MapColor.COLOR_PURPLE, SoundType.AMETHYST), BuiltinPedestalVariant.CMY), block -> new PedestalBlockItem(block, IS.of(1), BuiltinPedestalVariant.CMY, "item.pastel.pedestal.tooltip.all_basic"), InkColors.WHITE)));
	public static final DeferredBlock<Block> PEDESTAL_ONYX = register(pedestal(blockWithItem("pedestal_onyx", () -> new PedestalBlock(craftingBlock(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK), BuiltinPedestalVariant.ONYX), block -> new PedestalBlockItem(block, IS.of(1), BuiltinPedestalVariant.ONYX, "item.pastel.pedestal.tooltip.onyx"), InkColors.WHITE)));
	public static final DeferredBlock<Block> PEDESTAL_MOONSTONE = register(pedestal(blockWithItem("pedestal_moonstone", () -> new PedestalBlock(craftingBlock(MapColor.SNOW, SpectrumBlockSoundGroups.MOONSTONE_BLOCK), BuiltinPedestalVariant.MOONSTONE), block -> new PedestalBlockItem(block, IS.of(1), BuiltinPedestalVariant.MOONSTONE, "item.pastel.pedestal.tooltip.moonstone"), InkColors.WHITE)));

	public static final DeferredBlock<Block> FUSION_SHRINE_BASALT = register(cutout(singleton(blockWithItem("fusion_shrine_basalt", () -> new FusionShrineBlock(craftingBlock(MapColor.COLOR_BLACK, SoundType.BASALT).lightLevel(value -> value.getValue(FusionShrineBlock.LIGHT_LEVEL))), IS.of(1), InkColors.GRAY), SpectrumTexturedModels.FUSION_SHRINE)));
	public static final DeferredBlock<Block> FUSION_SHRINE_CALCITE = register(cutout(singleton(blockWithItem("fusion_shrine_calcite", () -> new FusionShrineBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE).lightLevel(value -> value.getValue(FusionShrineBlock.LIGHT_LEVEL))), IS.of(1), InkColors.GRAY), SpectrumTexturedModels.FUSION_SHRINE)));

	public static final DeferredBlock<Block> ENCHANTER = register(cutout(singletonWithSoup(blockWithItem("enchanter", () -> new EnchanterBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(1), InkColors.PURPLE), (block -> ModelLocationUtils.getModelLocation(block)))));
	public static final DeferredBlock<Block> ITEM_BOWL_BASALT = register(cutout(singleton(blockWithItem("item_bowl_basalt", () -> new ItemBowlBlock(craftingBlock(MapColor.COLOR_BLACK, SoundType.BASALT)), IS.of(16), InkColors.PINK), SpectrumTexturedModels.BOWL)));
	public static final DeferredBlock<Block> ITEM_BOWL_CALCITE = register(cutout(singleton(blockWithItem("item_bowl_calcite", () -> new ItemBowlBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(16), InkColors.PINK), SpectrumTexturedModels.BOWL)));
	public static final DeferredBlock<Block> ITEM_ROUNDEL = register(singleton(blockWithItem("item_roundel", () -> new ItemRoundelBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(16), InkColors.PINK), SpectrumTexturedModels.ROUNDEL));
	public static final DeferredBlock<Block> POTION_WORKSHOP = register(translucent(defaultNorthHorizontalFacing(blockWithItem("potion_workshop", () -> new PotionWorkshopBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(1), InkColors.PURPLE), ModelLocationUtils::getModelLocation)));
	public static final DeferredBlock<Block> SPIRIT_INSTILLER = register(singletonWithSoup(cutout(blockWithItem("spirit_instiller", () -> new SpiritInstillerBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(1), InkColors.WHITE)),(block -> ModelLocationUtils.getModelLocation(block))).withPredefinedItemModel());
	public static final DeferredBlock<Block> CRYSTALLARIEUM = register(cutout(singletonWithSoup(blockWithItem("crystallarieum", () -> new CrystallarieumBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(1), InkColors.BROWN), (block -> ModelLocationUtils.getModelLocation(block)))).withPredefinedItemModel());
	public static final DeferredBlock<Block> CINDERHEARTH = register(defaultNorthHorizontalFacing(blockWithItem("cinderhearth", () -> new CinderhearthBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(1).fireResistant(), InkColors.ORANGE), ModelLocationUtils::getModelLocation));

	public static final DeferredBlock<Block> COLOR_PICKER = register(cutout(defaultWestHorizontalFacing(blockWithItem("color_picker", () -> new ColorPickerBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(8), InkColors.GREEN), ModelLocationUtils::getModelLocation)));
	public static final DeferredBlock<Block> CRYSTAL_APOTHECARY = register(singletonWithSoup(blockWithItem("crystal_apothecary", () -> new CrystalApothecaryBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(8), InkColors.GREEN), (block -> ModelLocationUtils.getModelLocation(block))));

	private static Properties gemstone(MapColor mapColor, SoundType blockSoundGroup, int luminance) {
		return settings(mapColor, blockSoundGroup, 1.5F).forceSolidOn().noOcclusion().lightLevel((state) -> luminance).pushReaction(PushReaction.DESTROY);
	}

	private static Properties gemstoneBlock(MapColor mapColor, SoundType blockSoundGroup) {
		return settings(mapColor, blockSoundGroup, 1.5F).requiresCorrectToolForDrops();
	}

	public static <T extends SpectrumClusterBlock> BlockRegistrar<T> cluster(BlockRegistrar<T> registrar, ModelTemplate model) {
		return cutout(registrar).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block, SpectrumModelHelper.createModelVariant(TexturedModel.createDefault(TextureMapping::cross, model).create(block, ctx.modelOutput))).with(SpectrumModelHelper.createUpDefaultFacingVariantMap())).withBlockItemModel((ctx, block) -> {
			switch (block.getGrowthStage()) {
				case SpectrumClusterBlock.GrowthStage.SMALL -> SpectrumModels.SMALL_BUD_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), TextureMapping.layer0(block), ctx.output);
				case SpectrumClusterBlock.GrowthStage.MEDIUM -> SpectrumModels.MEDIUM_BUD_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), TextureMapping.layer0(block), ctx.output);
				case SpectrumClusterBlock.GrowthStage.LARGE -> SpectrumModels.LARGE_BUD_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), TextureMapping.layer0(block), ctx.output);
				case SpectrumClusterBlock.GrowthStage.CLUSTER -> SpectrumModels.CLUSTER_ITEM.create(ModelLocationUtils.getModelLocation(block.asItem()), TextureMapping.layer0(block), ctx.output);
			}
		});
	}

	public static final DeferredBlock<Block> TOPAZ_CLUSTER = register(cluster(blockWithItem("topaz_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_CYAN, SpectrumBlockSoundGroups.TOPAZ_CLUSTER, 8), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.CYAN), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> LARGE_TOPAZ_BUD = register(cluster(blockWithItem("large_topaz_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_CYAN, SpectrumBlockSoundGroups.LARGE_TOPAZ_BUD, 6), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.CYAN), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> MEDIUM_TOPAZ_BUD = register(cluster(blockWithItem("medium_topaz_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_CYAN, SpectrumBlockSoundGroups.MEDIUM_TOPAZ_BUD, 4), SpectrumClusterBlock.GrowthStage.MEDIUM), InkColors.CYAN), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> SMALL_TOPAZ_BUD = register(cluster(blockWithItem("small_topaz_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_CYAN, SpectrumBlockSoundGroups.SMALL_TOPAZ_BUD, 2), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.CYAN), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> BUDDING_TOPAZ = register(simple(blockWithItem("budding_topaz", () -> new SpectrumBuddingBlock(gemstoneBlock(MapColor.COLOR_CYAN, SpectrumBlockSoundGroups.TOPAZ_BLOCK).pushReaction(PushReaction.DESTROY).randomTicks(), SpectrumBlocks.SMALL_TOPAZ_BUD.get(), SpectrumBlocks.MEDIUM_TOPAZ_BUD.get(), SpectrumBlocks.LARGE_TOPAZ_BUD.get(), SpectrumBlocks.TOPAZ_CLUSTER.get(), SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_HIT, SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_CHIME), InkColors.CYAN)));
	public static final DeferredBlock<Block> TOPAZ_BLOCK = register(blockWithItem("topaz_block", () -> new SpectrumGemstoneBlock(gemstoneBlock(MapColor.COLOR_CYAN, SpectrumBlockSoundGroups.TOPAZ_BLOCK), SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_HIT, SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_CHIME), InkColors.CYAN).withBlockModel(SpectrumModelHelper::simpleMirroredBlockModel));

	public static final DeferredBlock<Block> CITRINE_CLUSTER = register(cluster(blockWithItem("citrine_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.CITRINE_CLUSTER, 9), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.YELLOW), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> LARGE_CITRINE_BUD = register(cluster(blockWithItem("large_citrine_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.LARGE_CITRINE_BUD, 7), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.YELLOW), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> MEDIUM_CITRINE_BUD = register(cluster(blockWithItem("medium_citrine_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.MEDIUM_CITRINE_BUD, 5), SpectrumClusterBlock.GrowthStage.MEDIUM), InkColors.YELLOW), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> SMALL_CITRINE_BUD = register(cluster(blockWithItem("small_citrine_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.SMALL_CITRINE_BUD, 3), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.YELLOW), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> BUDDING_CITRINE = register(simple(blockWithItem("budding_citrine", () -> new SpectrumBuddingBlock(gemstoneBlock(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK).pushReaction(PushReaction.DESTROY).randomTicks(), SpectrumBlocks.SMALL_CITRINE_BUD.get(), SpectrumBlocks.MEDIUM_CITRINE_BUD.get(), SpectrumBlocks.LARGE_CITRINE_BUD.get(), SpectrumBlocks.CITRINE_CLUSTER.get(), SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_CHIME), InkColors.YELLOW)));
	public static final DeferredBlock<Block> CITRINE_BLOCK = register(blockWithItem("citrine_block", () -> new SpectrumGemstoneBlock(gemstoneBlock(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK), SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_CHIME), InkColors.YELLOW).withBlockModel(SpectrumModelHelper::simpleMirroredBlockModel));

	public static final DeferredBlock<Block> ONYX_CLUSTER = register(cluster(blockWithItem("onyx_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.ONYX_CLUSTER, 6), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BLACK), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> LARGE_ONYX_BUD = register(cluster(blockWithItem("large_onyx_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.LARGE_ONYX_BUD, 5), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BLACK), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> MEDIUM_ONYX_BUD = register(cluster(blockWithItem("medium_onyx_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.MEDIUM_ONYX_BUD, 3), SpectrumClusterBlock.GrowthStage.MEDIUM), InkColors.BLACK), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> SMALL_ONYX_BUD = register(cluster(blockWithItem("small_onyx_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 1), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BLACK), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> BUDDING_ONYX = register(simple(blockWithItem("budding_onyx", () -> new SpectrumBuddingBlock(gemstoneBlock(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK).pushReaction(PushReaction.DESTROY).randomTicks(), SpectrumBlocks.SMALL_ONYX_BUD.get(), SpectrumBlocks.MEDIUM_ONYX_BUD.get(), SpectrumBlocks.LARGE_ONYX_BUD.get(), SpectrumBlocks.ONYX_CLUSTER.get(), SpectrumSoundEvents.BLOCK_ONYX_BLOCK_HIT, SpectrumSoundEvents.BLOCK_ONYX_BLOCK_CHIME), InkColors.BLACK)));
	public static final DeferredBlock<Block> ONYX_BLOCK = register(blockWithItem("onyx_block", () -> new SpectrumGemstoneBlock(gemstoneBlock(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK), SpectrumSoundEvents.BLOCK_ONYX_BLOCK_HIT, SpectrumSoundEvents.BLOCK_ONYX_BLOCK_CHIME), InkColors.BLACK).withBlockModel(SpectrumModelHelper::simpleMirroredBlockModel));

	public static final DeferredBlock<Block> MOONSTONE_CLUSTER = register(cluster(blockWithItem("moonstone_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.SNOW, SpectrumBlockSoundGroups.MOONSTONE_CLUSTER, 15), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.WHITE), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> LARGE_MOONSTONE_BUD = register(cluster(blockWithItem("large_moonstone_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.SNOW, SpectrumBlockSoundGroups.LARGE_MOONSTONE_BUD, 12), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.WHITE), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> MEDIUM_MOONSTONE_BUD = register(cluster(blockWithItem("medium_moonstone_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.SNOW, SpectrumBlockSoundGroups.MEDIUM_MOONSTONE_BUD, 9), SpectrumClusterBlock.GrowthStage.MEDIUM), InkColors.WHITE), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> SMALL_MOONSTONE_BUD = register(cluster(blockWithItem("small_moonstone_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.SNOW, SpectrumBlockSoundGroups.SMALL_MOONSTONE_BUD, 6), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.WHITE), ModelTemplates.CROSS));
	public static final DeferredBlock<Block> BUDDING_MOONSTONE = register(simple(blockWithItem("budding_moonstone", () -> new SpectrumBuddingBlock(gemstoneBlock(MapColor.SNOW, SpectrumBlockSoundGroups.MOONSTONE_BLOCK).pushReaction(PushReaction.DESTROY).randomTicks(), SpectrumBlocks.SMALL_MOONSTONE_BUD.get(), SpectrumBlocks.MEDIUM_MOONSTONE_BUD.get(), SpectrumBlocks.LARGE_MOONSTONE_BUD.get(), SpectrumBlocks.MOONSTONE_CLUSTER.get(), SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_CHIME), InkColors.WHITE)));
	public static final DeferredBlock<Block> MOONSTONE_BLOCK = register(blockWithItem("moonstone_block", () -> new SpectrumGemstoneBlock(gemstoneBlock(MapColor.SNOW, SpectrumBlockSoundGroups.MOONSTONE_BLOCK), SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_HIT, SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_CHIME), InkColors.WHITE).withBlockModel(SpectrumModelHelper::simpleMirroredBlockModel));

	public static final DeferredBlock<Block> TOPAZ_POWDER_BLOCK = register(simple(blockWithItem("topaz_powder_block", () -> new ColoredFallingBlock(new ColorRGBA(DyeColor.CYAN.getFireworkColor()), BlockBehaviour.Properties.ofFullCopy(SAND).mapColor(MapColor.COLOR_CYAN)), InkColors.CYAN)));
	public static final DeferredBlock<Block> AMETHYST_POWDER_BLOCK = register(simple(blockWithItem("amethyst_powder_block", () -> new ColoredFallingBlock(new ColorRGBA(DyeColor.MAGENTA.getFireworkColor()), BlockBehaviour.Properties.ofFullCopy(SAND).mapColor(MapColor.COLOR_MAGENTA)), InkColors.MAGENTA)));
	public static final DeferredBlock<Block> CITRINE_POWDER_BLOCK = register(simple(blockWithItem("citrine_powder_block", () -> new ColoredFallingBlock(new ColorRGBA(DyeColor.YELLOW.getFireworkColor()), BlockBehaviour.Properties.ofFullCopy(SAND).mapColor(MapColor.COLOR_YELLOW)), InkColors.YELLOW)));
	public static final DeferredBlock<Block> ONYX_POWDER_BLOCK = register(simple(blockWithItem("onyx_powder_block", () -> new ColoredFallingBlock(new ColorRGBA(DyeColor.BLACK.getFireworkColor()), BlockBehaviour.Properties.ofFullCopy(SAND).mapColor(MapColor.COLOR_BLACK)), InkColors.BLACK)));
	public static final DeferredBlock<Block> MOONSTONE_POWDER_BLOCK = register(simple(blockWithItem("moonstone_powder_block", () -> new ColoredFallingBlock(new ColorRGBA(DyeColor.WHITE.getFireworkColor()), BlockBehaviour.Properties.ofFullCopy(SAND).mapColor(MapColor.SNOW)), InkColors.WHITE)));

	public static final DeferredBlock<Block> VEGETAL_BLOCK = register(translucent(singleton(burnable(blockWithItem("vegetal_block", () -> new Block(settings(MapColor.GRASS, SoundType.FUNGUS, 2.0F).noOcclusion()), InkColors.GREEN), 8000), TexturedModel.createDefault(TextureMapping::defaultTexture, SpectrumModels.TRANSLUCENT_OUTER1))));
	public static final DeferredBlock<Block> NEOLITH_BLOCK = register(blockWithItem("neolith_block", () -> new SpectrumFacingBlock(settings(MapColor.COLOR_PURPLE, SoundType.COPPER, 6.0F).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM).lightLevel(state -> 13).hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always)), InkColors.PINK).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block, SpectrumModelHelper.createModelVariant(TexturedModel.CUBE_TOP_BOTTOM.create(block, ctx.modelOutput))).with(SpectrumModelHelper.createUpDefaultFacingVariantMap())));
	public static final DeferredBlock<Block> BEDROCK_DUST_BLOCK = register(simple(blockWithItem("bedrock_dust_block", () -> new BlockWithTooltip(settings(MapColor.STONE, SoundType.STONE, 100.0F, 3600.0F).pushReaction(PushReaction.BLOCK).requiresCorrectToolForDrops().instrument(NoteBlockInstrument.BASEDRUM), Component.translatable("pastel.tooltip.dragon_and_wither_immune")), IS.of(Rarity.UNCOMMON), InkColors.BLACK)));

	public static final DeferredBlock<Block> BISMUTH_CLUSTER = register(cluster(blockWithItem("bismuth_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.WARPED_STEM, SoundType.CHAIN, 8), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_BISMUTH_BUD = register(cluster(blockWithItem("large_bismuth_bud", () -> new BismuthBudBlock(gemstone(MapColor.WARPED_STEM, SoundType.CHAIN, 6).randomTicks(), SpectrumClusterBlock.GrowthStage.LARGE, SpectrumBlocks.BISMUTH_CLUSTER.get()), IS.of(Rarity.UNCOMMON), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_BISMUTH_BUD = register(cluster(blockWithItem("small_bismuth_bud", () -> new BismuthBudBlock(gemstone(MapColor.WARPED_STEM, SoundType.CHAIN, 4).randomTicks(), SpectrumClusterBlock.GrowthStage.SMALL, SpectrumBlocks.LARGE_BISMUTH_BUD.get()), IS.of(Rarity.UNCOMMON), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> BISMUTH_BLOCK = register(simple(blockWithItem("bismuth_block", () -> new Block(gemstoneBlock(MapColor.WARPED_STEM, SoundType.CHAIN)), InkColors.CYAN)));

	// DD BLOCKS
	private static final float BLACKSLAG_HARDNESS = 5.0F;
	private static final float BLACKSLAG_RESISTANCE = 7.0F;

	private static Properties blackslag(SoundType blockSoundGroup) {
		return settings(MapColor.COLOR_GRAY, blockSoundGroup, SpectrumBlocks.BLACKSLAG_HARDNESS, SpectrumBlocks.BLACKSLAG_RESISTANCE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops();
	}

	public static final DeferredBlock<Block> BLACKSLAG = register(blockWithItem("blackslag", () -> new BlackslagBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK).withBlockModel((ctx, block) -> SpectrumModelHelper.createMirroredVariantsSupplier(block, TexturedModel.COLUMN_ALT, SpectrumTexturedModels.CUBE_COLUMN_MIRRORED, ctx.modelOutput).with(SpectrumModelHelper.createAxisRotatedVariantMap())));
	public static final DeferredBlock<Block> BLACKSLAG_STAIRS = register(blockWithItem("blackslag_stairs", () -> new StairBlock(SpectrumBlocks.BLACKSLAG.get().defaultBlockState(), blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_SLAB = register(blockWithItem("blackslag_slab", () -> new SlabBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_WALL = register(blockWithItem("blackslag_wall", () -> new WallBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
	public static final BlockFamily BLACKSLAG_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(SpectrumBlocks.BLACKSLAG.get()).stairs(SpectrumBlocks.BLACKSLAG_STAIRS.get()).slab(SpectrumBlocks.BLACKSLAG_SLAB.get()).wall(SpectrumBlocks.BLACKSLAG_WALL.get()).getFamily(), SpectrumTexturedModels.cubeBottomTopWall(b -> b, "", b -> b, "_top", b -> b, "_top", b -> b, ""));

	public static final DeferredBlock<Block> INFESTED_BLACKSLAG = register(parented(blockWithItem("infested_blackslag", () -> new InfestedBlock(SpectrumBlocks.BLACKSLAG.get(), blackslag(SoundType.DEEPSLATE)), InkColors.BLACK), b -> SpectrumBlocks.BLACKSLAG.get()));

	public static final DeferredBlock<Block> COBBLED_BLACKSLAG = register(blockWithItem("cobbled_blackslag", () -> new Block(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
	public static final DeferredBlock<Block> COBBLED_BLACKSLAG_STAIRS = register(blockWithItem("cobbled_blackslag_stairs", () -> new StairBlock(SpectrumBlocks.COBBLED_BLACKSLAG.get().defaultBlockState(), blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
	public static final DeferredBlock<Block> COBBLED_BLACKSLAG_SLAB = register(blockWithItem("cobbled_blackslag_slab", () -> new SlabBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
	public static final DeferredBlock<Block> COBBLED_BLACKSLAG_WALL = register(blockWithItem("cobbled_blackslag_wall", () -> new WallBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
	public static final BlockFamily COBBLED_BLACKSLAG_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.COBBLED_BLACKSLAG.get()).stairs(SpectrumBlocks.COBBLED_BLACKSLAG_STAIRS.get()).slab(SpectrumBlocks.COBBLED_BLACKSLAG_SLAB.get()).wall(SpectrumBlocks.COBBLED_BLACKSLAG_WALL.get()).getFamily());

	public static final DeferredBlock<Block> BLACKSLAG_TILES = register(blockWithItem("blackslag_tiles", () -> new Block(blackslag(SoundType.DEEPSLATE_TILES)), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_TILE_STAIRS = register(blockWithItem("blackslag_tile_stairs", () -> new StairBlock(SpectrumBlocks.BLACKSLAG_TILES.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_TILES.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_TILE_SLAB = register(blockWithItem("blackslag_tile_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_TILES.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_TILE_WALL = register(blockWithItem("blackslag_tile_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_TILES.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> CRACKED_BLACKSLAG_TILES = register(blockWithItem("cracked_blackslag_tiles", () -> new Block(Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_TILES.get())), InkColors.BLACK));
	public static final BlockFamily BLACKSLAG_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BLACKSLAG_TILES.get()).stairs(SpectrumBlocks.BLACKSLAG_TILE_STAIRS.get()).slab(SpectrumBlocks.BLACKSLAG_TILE_SLAB.get()).wall(SpectrumBlocks.BLACKSLAG_TILE_WALL.get()).cracked(SpectrumBlocks.CRACKED_BLACKSLAG_TILES.get()).getFamily());

	public static final DeferredBlock<Block> BLACKSLAG_BRICKS = register(blockWithItem("blackslag_bricks", () -> new Block(blackslag(SoundType.DEEPSLATE_BRICKS)), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_BRICK_STAIRS = register(blockWithItem("blackslag_brick_stairs", () -> new StairBlock(SpectrumBlocks.BLACKSLAG_BRICKS.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_BRICKS.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_BRICK_SLAB = register(blockWithItem("blackslag_brick_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_BRICKS.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> BLACKSLAG_BRICK_WALL = register(blockWithItem("blackslag_brick_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_BRICKS.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> CRACKED_BLACKSLAG_BRICKS = register(blockWithItem("cracked_blackslag_bricks", () -> new Block(Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_BRICKS.get())), InkColors.BLACK));
	public static final BlockFamily BLACKSLAG_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BLACKSLAG_BRICKS.get()).stairs(SpectrumBlocks.BLACKSLAG_BRICK_STAIRS.get()).slab(SpectrumBlocks.BLACKSLAG_BRICK_SLAB.get()).wall(SpectrumBlocks.BLACKSLAG_BRICK_WALL.get()).cracked(SpectrumBlocks.CRACKED_BLACKSLAG_BRICKS.get()).getFamily());

	public static final DeferredBlock<Block> POLISHED_BLACKSLAG = register(blockWithItem("polished_blackslag", () -> new Block(blackslag(SoundType.POLISHED_DEEPSLATE)), InkColors.BLACK));
	public static final DeferredBlock<Block> POLISHED_BLACKSLAG_STAIRS = register(blockWithItem("polished_blackslag_stairs", () -> new StairBlock(SpectrumBlocks.POLISHED_BLACKSLAG.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.POLISHED_BLACKSLAG.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> POLISHED_BLACKSLAG_SLAB = register(blockWithItem("polished_blackslag_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.POLISHED_BLACKSLAG.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> POLISHED_BLACKSLAG_WALL = register(blockWithItem("polished_blackslag_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.POLISHED_BLACKSLAG.get())), InkColors.BLACK));
	public static final DeferredBlock<Block> POLISHED_BLACKSLAG_BUTTON = register(blockWithItem("polished_blackslag_button", () -> new ButtonBlock(SpectrumBlockSetTypes.POLISHED_BLACKSLAG, 5, Properties.of().pushReaction(PushReaction.DESTROY).noCollission().strength(0.5F)), InkColors.BLACK));
	public static final DeferredBlock<Block> POLISHED_BLACKSLAG_PRESSURE_PLATE = register(blockWithItem("polished_blackslag_pressure_plate", () -> new PressurePlateBlock(SpectrumBlockSetTypes.POLISHED_BLACKSLAG, Properties.of().mapColor(MapColor.COLOR_BLACK).requiresCorrectToolForDrops().noCollission().strength(0.5F)), InkColors.BLACK));
	public static final DeferredBlock<Block> CHISELED_POLISHED_BLACKSLAG = register(blockWithItem("chiseled_polished_blackslag", () -> new Block(blackslag(SoundType.DEEPSLATE_BRICKS)), InkColors.BLACK));
	public static final BlockFamily POLISHED_BLACKSLAG_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.POLISHED_BLACKSLAG.get()).stairs(SpectrumBlocks.POLISHED_BLACKSLAG_STAIRS.get()).slab(SpectrumBlocks.POLISHED_BLACKSLAG_SLAB.get()).wall(SpectrumBlocks.POLISHED_BLACKSLAG_WALL.get()).button(SpectrumBlocks.POLISHED_BLACKSLAG_BUTTON.get()).pressurePlate(SpectrumBlocks.POLISHED_BLACKSLAG_PRESSURE_PLATE.get()).chiseled(SpectrumBlocks.CHISELED_POLISHED_BLACKSLAG.get()).getFamily());

	public static final DeferredBlock<Block> POLISHED_BLACKSLAG_PILLAR = register(axisRotated(blockWithItem("polished_blackslag_pillar", () -> new RotatedPillarBlock(Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG_BRICKS.get())), InkColors.BLACK), TexturedModel.createDefault(b -> SpectrumTextureMaps.sideEnd(b, "", SpectrumBlocks.CHISELED_POLISHED_BLACKSLAG.get(), ""), ModelTemplates.CUBE_COLUMN)));
	public static final DeferredBlock<Block> ANCIENT_CHISELED_POLISHED_BLACKSLAG = register(simple(blockWithItem("ancient_chiseled_polished_blackslag", () -> new Block(blackslag(SoundType.DEEPSLATE_BRICKS)), InkColors.BLACK)));

	public static final DeferredBlock<Block> SHALE_CLAY = register(singleton(blockWithItem("shale_clay", () -> new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, blackslag(SoundType.MUD_BRICKS)), InkColors.BROWN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> TILLED_SHALE_CLAY = register(singleton(blockWithItem("tilled_shale_clay", () -> new TilledShaleClayBlock(Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get()), SpectrumBlocks.SHALE_CLAY.get().defaultBlockState()), InkColors.BROWN), SpectrumTexturedModels.farmland(b -> SpectrumBlocks.SHALE_CLAY.get(), "_side", b -> b, "")));

	public static final DeferredBlock<Block> POLISHED_SHALE_CLAY = register(blockWithItem("polished_shale_clay", () -> new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem("polished_shale_clay_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.UNAFFECTED, SpectrumBlocks.POLISHED_SHALE_CLAY.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_SHALE_CLAY_SLAB = register(blockWithItem("polished_shale_clay_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily POLISHED_SHALE_CLAY_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.POLISHED_SHALE_CLAY.get()).stairs(SpectrumBlocks.POLISHED_SHALE_CLAY_STAIRS.get()).slab(SpectrumBlocks.POLISHED_SHALE_CLAY_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> EXPOSED_POLISHED_SHALE_CLAY = register(blockWithItem("exposed_polished_shale_clay", () -> new WeatheringBlock(Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> EXPOSED_POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem("exposed_polished_shale_clay_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.EXPOSED, SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> EXPOSED_POLISHED_SHALE_CLAY_SLAB = register(blockWithItem("exposed_polished_shale_clay_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily EXPOSED_POLISHED_SHALE_CLAY_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY.get()).stairs(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY_STAIRS.get()).slab(SpectrumBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> WEATHERED_POLISHED_SHALE_CLAY = register(blockWithItem("weathered_polished_shale_clay", () -> new WeatheringBlock(Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> WEATHERED_POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem("weathered_polished_shale_clay_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.WEATHERED, SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> WEATHERED_POLISHED_SHALE_CLAY_SLAB = register(blockWithItem("weathered_polished_shale_clay_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily WEATHERED_POLISHED_SHALE_CLAY_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY.get()).stairs(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY_STAIRS.get()).slab(SpectrumBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> SHALE_CLAY_BRICKS = register(blockWithItem("shale_clay_bricks", () -> new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> SHALE_CLAY_BRICK_STAIRS = register(blockWithItem("shale_clay_brick_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.UNAFFECTED, SpectrumBlocks.SHALE_CLAY_BRICKS.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> SHALE_CLAY_BRICK_SLAB = register(blockWithItem("shale_clay_brick_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily SHALE_CLAY_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.SHALE_CLAY_BRICKS.get()).stairs(SpectrumBlocks.SHALE_CLAY_BRICK_STAIRS.get()).slab(SpectrumBlocks.SHALE_CLAY_BRICK_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_BRICKS = register(blockWithItem("exposed_shale_clay_bricks", () -> new WeatheringBlock(Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_BRICK_STAIRS = register(blockWithItem("exposed_shale_clay_brick_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.EXPOSED, SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICKS.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_BRICK_SLAB = register(blockWithItem("exposed_shale_clay_brick_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily EXPOSED_SHALE_CLAY_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICKS.get()).stairs(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS.get()).slab(SpectrumBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_BRICKS = register(blockWithItem("weathered_shale_clay_bricks", () -> new WeatheringBlock(Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_BRICK_STAIRS = register(blockWithItem("weathered_shale_clay_brick_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.WEATHERED, SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICKS.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_BRICK_SLAB = register(blockWithItem("weathered_shale_clay_brick_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily WEATHERED_SHALE_CLAY_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICKS.get()).stairs(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICK_STAIRS.get()).slab(SpectrumBlocks.WEATHERED_SHALE_CLAY_BRICK_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> SHALE_CLAY_TILES = register(blockWithItem("shale_clay_tiles", () -> new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> SHALE_CLAY_TILE_STAIRS = register(blockWithItem("shale_clay_tile_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.UNAFFECTED, SpectrumBlocks.SHALE_CLAY_TILES.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> SHALE_CLAY_TILE_SLAB = register(blockWithItem("shale_clay_tile_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily SHALE_CLAY_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.SHALE_CLAY_TILES.get()).stairs(SpectrumBlocks.SHALE_CLAY_TILE_STAIRS.get()).slab(SpectrumBlocks.SHALE_CLAY_TILE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_TILES = register(blockWithItem("exposed_shale_clay_tiles", () -> new WeatheringBlock(Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_TILE_STAIRS = register(blockWithItem("exposed_shale_clay_tile_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.EXPOSED, SpectrumBlocks.EXPOSED_SHALE_CLAY_TILES.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_TILE_SLAB = register(blockWithItem("exposed_shale_clay_tile_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily EXPOSED_SHALE_CLAY_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILES.get()).stairs(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS.get()).slab(SpectrumBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_TILES = register(blockWithItem("weathered_shale_clay_tiles", () -> new WeatheringBlock(Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_TILE_STAIRS = register(blockWithItem("weathered_shale_clay_tile_stairs", () -> new WeatheringStairsBlock(Weathering.WeatheringLevel.WEATHERED, SpectrumBlocks.WEATHERED_SHALE_CLAY_TILES.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_TILE_SLAB = register(blockWithItem("weathered_shale_clay_tile_slab", () -> new WeatheringSlabBlock(Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(SpectrumBlocks.SHALE_CLAY.get())), InkColors.BROWN));
	public static final BlockFamily WEATHERED_SHALE_CLAY_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILES.get()).stairs(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS.get()).slab(SpectrumBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> ROCK_CRYSTAL = register(simple(blockWithItem("rock_crystal", () -> new Block(settings(MapColor.QUARTZ, SoundType.NETHER_BRICKS, 200F).requiresCorrectToolForDrops()), InkColors.BROWN)));

	public static final DeferredBlock<Block> PYRITE = register(axisRotated(blockWithItem("pyrite", () -> new RotatedPillarBlock(settings(MapColor.TERRACOTTA_YELLOW, SoundType.CHAIN, 50.0F).requiresCorrectToolForDrops()), InkColors.BROWN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> PYRITE_SLAB = register(blockWithItem("pyrite_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PYRITE_STAIRS = register(blockWithItem("pyrite_stairs", () -> new StairBlock(SpectrumBlocks.PYRITE.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PYRITE_WALL = register(blockWithItem("pyrite_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN));
	public static final BlockFamily PYRITE_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(SpectrumBlocks.PYRITE.get()).stairs(SpectrumBlocks.PYRITE_STAIRS.get()).slab(SpectrumBlocks.PYRITE_SLAB.get()).wall(SpectrumBlocks.PYRITE_WALL.get()).getFamily(), TexturedModel.createDefault(b -> SpectrumTextureMaps.sideTopBottomWall(b, "_side", b, "_top", b, "_top", b, "_side"), ModelTemplates.CUBE_ALL));

	public static final DeferredBlock<Block> PYRITE_PILE = register(axisRotated(blockWithItem("pyrite_pile", () -> new RotatedPillarBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> PYRITE_PLATING = register(simple(blockWithItem("pyrite_plating", () -> new Block(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN)));
	public static final DeferredBlock<Block> PYRITE_TUBING = register(axisRotated(blockWithItem("pyrite_tubing", () -> new RotatedPillarBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> PYRITE_RELIEF = register(axisRotated(blockWithItem("pyrite_relief", () -> new RotatedPillarBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN), SpectrumTexturedModels.cubeColumn(b -> b, "_side", b -> SpectrumBlocks.PYRITE_TUBING.get(), "_top")));
	public static final DeferredBlock<Block> PYRITE_STACK = register(simple(blockWithItem("pyrite_stack", () -> new Block(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN)));
	public static final DeferredBlock<Block> PYRITE_PANELING = register(singleton(blockWithItem("pyrite_paneling", () -> new Block(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN), SpectrumTexturedModels.cubeColumn(b -> b, "", b -> SpectrumBlocks.PYRITE_PLATING.get(), "")));
	public static final DeferredBlock<Block> PYRITE_VENT = register(singleton(blockWithItem("pyrite_vent", () -> new Block(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN), SpectrumTexturedModels.cubeColumn(b -> b, "", b -> SpectrumBlocks.PYRITE_PLATING.get(), "")));
	public static final DeferredBlock<Block> PYRITE_RIPPER = register(mippedCutout(blockWithItem("pyrite_ripper", () -> new PyriteRipperBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get()).noOcclusion().isValidSpawn(SpectrumBlocks::never).isViewBlocking(SpectrumBlocks::never)), InkColors.BROWN)).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.FACING, PyriteRipperBlock.MIRRORED)
			.select(Direction.EAST, false, SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
			.select(Direction.NORTH, false, SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
			.select(Direction.SOUTH, false, SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
			.select(Direction.WEST, false, SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
			.select(Direction.UP, false, SpectrumModelHelper.createModelVariant(block, ""))
			.select(Direction.DOWN, false, SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
			.select(Direction.EAST, true, SpectrumModelHelper.createModelVariant(block, "_mirrored").with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
			.select(Direction.NORTH, true, SpectrumModelHelper.createModelVariant(block, "_mirrored").with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
			.select(Direction.SOUTH, true, SpectrumModelHelper.createModelVariant(block, "_mirrored").with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
			.select(Direction.WEST, true, SpectrumModelHelper.createModelVariant(block, "_mirrored"))
			.select(Direction.UP, true, SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
			.select(Direction.DOWN, true, SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.X_ROT, VariantProperties.Rotation.R180).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)))));
	public static final DeferredBlock<Block> PYRITE_PROJECTOR = register(singletonWithSoup(blockWithItem("pyrite_projector", () -> new ProjectorBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get()), "pyrite_projector_projection", 16, 14, 1.375F, 1F, 16F), InkColors.BROWN), (block -> ModelLocationUtils.getModelLocation(block))));

	//TODO naming convention suggests that it should be 'pyrite_tile_slab', etc.
	public static final DeferredBlock<Block> PYRITE_TILES = register(simple(blockWithItem("pyrite_tiles", () -> new Block(Properties.ofFullCopy(SpectrumBlocks.PYRITE.get())), InkColors.BROWN)));
	public static final DeferredBlock<Block> PYRITE_TILES_SLAB = register(blockWithItem("pyrite_tiles_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PYRITE_TILES_STAIRS = register(blockWithItem("pyrite_tiles_stairs", () -> new StairBlock(SpectrumBlocks.PYRITE_TILES.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.PYRITE_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PYRITE_TILES_WALL = register(blockWithItem("pyrite_tiles_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.PYRITE_TILES.get())), InkColors.BROWN));
	public static final BlockFamily PYRITE_TILE_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(SpectrumBlocks.PYRITE_TILES.get()).stairs(SpectrumBlocks.PYRITE_TILES_STAIRS.get()).slab(SpectrumBlocks.PYRITE_TILES_SLAB.get()).wall(SpectrumBlocks.PYRITE_TILES_WALL.get()).getFamily(), TexturedModel.createDefault(b -> TextureMapping.cube(SpectrumBlocks.PYRITE_PLATING.get()), ModelTemplates.CUBE_ALL));

	public static final DeferredBlock<Block> DRAGONBONE = register(axisRotated(blockWithItem("dragonbone", () -> new DragonboneBlock(Properties.ofFullCopy(BONE_BLOCK).strength(-1.0F, 22.0F).pushReaction(PushReaction.BLOCK)), InkColors.GREEN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> CRACKED_DRAGONBONE = register(axisRotated(blockWithItem("cracked_dragonbone", () -> new RotatedPillarBlock(Properties.ofFullCopy(BONE_BLOCK).strength(100.0F, 1200.0F).pushReaction(PushReaction.BLOCK)), InkColors.GREEN), TexturedModel.COLUMN));

	public static final DeferredBlock<Block> POLISHED_BONE_ASH = register(blockWithItem("polished_bone_ash", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CRACKED_DRAGONBONE.get()).destroyTime(1500.0F).mapColor(MapColor.SNOW)), InkColors.CYAN));
	public static final DeferredBlock<Block> POLISHED_BONE_ASH_STAIRS = register(blockWithItem("polished_bone_ash_stairs", () -> new StairBlock(SpectrumBlocks.POLISHED_BONE_ASH.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.POLISHED_BONE_ASH.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> POLISHED_BONE_ASH_SLAB = register(blockWithItem("polished_bone_ash_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.POLISHED_BONE_ASH.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> POLISHED_BONE_ASH_WALL = register(blockWithItem("polished_bone_ash_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.POLISHED_BONE_ASH.get())), InkColors.CYAN));
	public static final BlockFamily POLISHED_BONE_ASH_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.POLISHED_BONE_ASH.get()).stairs(SpectrumBlocks.POLISHED_BONE_ASH_STAIRS.get()).slab(SpectrumBlocks.POLISHED_BONE_ASH_SLAB.get()).wall(SpectrumBlocks.POLISHED_BONE_ASH_WALL.get()).getFamily());

	public static final DeferredBlock<Block> POLISHED_BONE_ASH_PILLAR = register(axisRotated(blockWithItem("polished_bone_ash_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BONE_ASH.get())), InkColors.CYAN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> BONE_ASH_SHINGLES = register(blockWithItem("bone_ash_shingles", () -> new ShinglesBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BONE_ASH.get()).noOcclusion()), InkColors.CYAN).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, ModelLocationUtils.getModelLocation(block)).with(SpectrumModelHelper.createEastDefaultHorizontalFacingVariantMap())));

	public static final DeferredBlock<Block> BONE_ASH_BRICKS = register(blockWithItem("bone_ash_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BONE_ASH.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> BONE_ASH_BRICK_STAIRS = register(blockWithItem("bone_ash_brick_stairs", () -> new StairBlock(SpectrumBlocks.BONE_ASH_BRICKS.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.BONE_ASH_BRICKS.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> BONE_ASH_BRICK_SLAB = register(blockWithItem("bone_ash_brick_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.BONE_ASH_BRICKS.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> BONE_ASH_BRICK_WALL = register(blockWithItem("bone_ash_brick_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.BONE_ASH_BRICKS.get())), InkColors.CYAN));
	public static final BlockFamily BONE_ASH_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BONE_ASH_BRICKS.get()).stairs(SpectrumBlocks.BONE_ASH_BRICK_STAIRS.get()).slab(SpectrumBlocks.BONE_ASH_BRICK_SLAB.get()).wall(SpectrumBlocks.BONE_ASH_BRICK_WALL.get()).getFamily());

	public static final DeferredBlock<Block> BONE_ASH_TILES = register(blockWithItem("bone_ash_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BONE_ASH.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> BONE_ASH_TILE_STAIRS = register(blockWithItem("bone_ash_tile_stairs", () -> new StairBlock(SpectrumBlocks.BONE_ASH_TILES.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.BONE_ASH_TILES.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> BONE_ASH_TILE_SLAB = register(blockWithItem("bone_ash_tile_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.BONE_ASH_TILES.get())), InkColors.CYAN));
	public static final DeferredBlock<Block> BONE_ASH_TILE_WALL = register(blockWithItem("bone_ash_tile_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.BONE_ASH_TILES.get())), InkColors.CYAN));
	public static final BlockFamily BONE_ASH_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BONE_ASH_TILES.get()).stairs(SpectrumBlocks.BONE_ASH_TILE_STAIRS.get()).slab(SpectrumBlocks.BONE_ASH_TILE_SLAB.get()).wall(SpectrumBlocks.BONE_ASH_TILE_WALL.get()).getFamily());

	public static final DeferredBlock<Block> SLUSH = register(simple(blockWithItem("slush", () -> new RotatedPillarBlock(blackslag(SoundType.MUDDY_MANGROVE_ROOTS)), InkColors.BROWN)));
	public static final DeferredBlock<Block> OVERGROWN_SLUSH = register(snowy(blockWithItem("overgrown_slush", () -> new SlushVegetationBlock(blackslag(SoundType.MUDDY_MANGROVE_ROOTS)), InkColors.BROWN), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.SLUSH.get(), "", b -> b, "_top"), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_snow_side", b -> b, "_snow_top", b -> SpectrumBlocks.SLUSH.get(), "", b -> b, "_snow_top")));
	public static final DeferredBlock<Block> TILLED_SLUSH = register(singleton(blockWithItem("tilled_slush", () -> new TilledSlushBlock(Properties.ofFullCopy(SpectrumBlocks.SLUSH.get()), SpectrumBlocks.SLUSH.get().defaultBlockState()), InkColors.BROWN), SpectrumTexturedModels.farmland(b -> SpectrumBlocks.SLUSH.get(), "", b -> b, "")));

	public static final DeferredBlock<Block> BLACK_MATERIA = register(simple(blockWithItem("black_materia", () -> new BlackMateriaBlock(settings(MapColor.TERRACOTTA_BLACK, SoundType.SAND, 0.0F).instrument(NoteBlockInstrument.SNARE).randomTicks()), InkColors.GRAY)));
	public static final DeferredBlock<Block> HORNSLAKE = register(simple(blockWithItem("hornslake", () -> new Block(settings(MapColor.TERRACOTTA_BLACK, SoundType.SAND, 0.5F).instrument(NoteBlockInstrument.SNARE)), InkColors.GRAY)));
	public static final DeferredBlock<Block> SAG_LEAF = register(cross(block("sag_leaf", () -> new BlackSludgePlantBlock(BlockBehaviour.Properties.ofFullCopy(SHORT_GRASS).mapColor(MapColor.TERRACOTTA_BLACK)))));
	public static final DeferredBlock<Block> SAG_BUBBLE = register(cross(block("sag_bubble", () -> new BlackSludgePlantBlock(BlockBehaviour.Properties.ofFullCopy(SHORT_GRASS).mapColor(MapColor.TERRACOTTA_BLACK)))));
	public static final DeferredBlock<Block> SMALL_SAG_BUBBLE = register(cross(block("small_sag_bubble", () -> new BlackSludgePlantBlock(BlockBehaviour.Properties.ofFullCopy(SHORT_GRASS).mapColor(MapColor.TERRACOTTA_BLACK)))));

	public static final DeferredBlock<Block> PRIMORDIAL_FIRE = register(cutout(block("primordial_fire", () -> new PrimordialFireBlock(Properties.ofFullCopy(FIRE).mapColor(MapColor.COLOR_PURPLE).lightLevel((state) -> 10)))).withBlockModel((ctx, block) -> {
		Condition noSides = Condition.condition().term(PrimordialFireBlock.UP, false).term(PrimordialFireBlock.NORTH, false).term(PrimordialFireBlock.SOUTH, false).term(PrimordialFireBlock.WEST, false).term(PrimordialFireBlock.EAST, false);
		TextureMapping fire0 = new TextureMapping().put(TextureSlot.FIRE, TextureMapping.getBlockTexture(block, "_0"));
		TextureMapping fire1 = new TextureMapping().put(TextureSlot.FIRE, TextureMapping.getBlockTexture(block, "_1"));
		ResourceLocation side0 = SpectrumModels.FIRE_SIDE.createWithSuffix(block, "_side0", fire0, ctx.modelOutput);
		ResourceLocation side1 = SpectrumModels.FIRE_SIDE.createWithSuffix(block, "_side1", fire1, ctx.modelOutput);
		ResourceLocation sideAlt0 = SpectrumModels.FIRE_SIDE_ALT.createWithSuffix(block, "_side_alt0", fire0, ctx.modelOutput);
		ResourceLocation sideAlt1 = SpectrumModels.FIRE_SIDE_ALT.createWithSuffix(block, "_side_alt1", fire1, ctx.modelOutput);
		return MultiPartGenerator.multiPart(block)
				.with(noSides, SpectrumModelHelper.createModelVariant(SpectrumModels.FIRE_FLOOR.createWithSuffix(block, "_floor0", fire0, ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumModels.FIRE_FLOOR.createWithSuffix(block, "_floor1", fire1, ctx.modelOutput)))
				.with(Condition.condition().term(PrimordialFireBlock.UP, true), SpectrumModelHelper.createModelVariant(SpectrumModels.FIRE_UP.createWithSuffix(block, "_up0", fire0, ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumModels.FIRE_UP.createWithSuffix(block, "_up1", fire1, ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumModels.FIRE_UP_ALT.createWithSuffix(block, "_up_alt0", fire0, ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumModels.FIRE_UP_ALT.createWithSuffix(block, "_up_alt1", fire1, ctx.modelOutput)))
				.with(Condition.or(noSides, Condition.condition().term(PrimordialFireBlock.NORTH, true)), SpectrumModelHelper.createModelVariant(side0), SpectrumModelHelper.createModelVariant(side1), SpectrumModelHelper.createModelVariant(sideAlt0), SpectrumModelHelper.createModelVariant(sideAlt1))
				.with(Condition.or(noSides, Condition.condition().term(PrimordialFireBlock.SOUTH, true)), SpectrumModelHelper.createModelVariant(side0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), SpectrumModelHelper.createModelVariant(side1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), SpectrumModelHelper.createModelVariant(sideAlt0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180), SpectrumModelHelper.createModelVariant(sideAlt1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
				.with(Condition.or(noSides, Condition.condition().term(PrimordialFireBlock.WEST, true)), SpectrumModelHelper.createModelVariant(side0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270), SpectrumModelHelper.createModelVariant(side1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270), SpectrumModelHelper.createModelVariant(sideAlt0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270), SpectrumModelHelper.createModelVariant(sideAlt1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
				.with(Condition.or(noSides, Condition.condition().term(PrimordialFireBlock.EAST, true)), SpectrumModelHelper.createModelVariant(side0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), SpectrumModelHelper.createModelVariant(side1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), SpectrumModelHelper.createModelVariant(sideAlt0).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90), SpectrumModelHelper.createModelVariant(sideAlt1).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
	}));
	public static final DeferredBlock<Block> PRIMORDIAL_WALL_TORCH = register(cutout(defaultEastHorizontalFacing(block("primordial_wall_torch", () -> new WallTorchBlock(SpectrumParticleTypes.PRIMORDIAL_FLAME, Properties.ofFullCopy(SOUL_WALL_TORCH).lightLevel(s -> 13))), ModelLocationUtils::getModelLocation)));
	public static final DeferredBlock<Block> PRIMORDIAL_TORCH = register(cutout(singletonWithSoup(blockWithItem("primordial_torch", () -> new TorchBlock(SpectrumParticleTypes.PRIMORDIAL_FLAME, Properties.ofFullCopy(SOUL_TORCH).lightLevel(s -> 13)), block -> new StandingAndWallBlockItem(block, SpectrumBlocks.PRIMORDIAL_WALL_TORCH.get(), IS.of(), Direction.DOWN), InkColors.ORANGE), ModelLocationUtils::getModelLocation).withItemModel(SpectrumModelHelper::registerItemModel)));

	public static <T extends Block> BlockRegistrar<T> moonstoneChiseled(BlockRegistrar<T> registrar, ResourceLocation capTexture) {
		return registrar.withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_down")).withBlockModel((ctx, block) -> {
			TextureMapping textureMap = SpectrumTextureMaps.sideLine(capTexture, TextureMapping.getBlockTexture(block));
			ResourceLocation base = SpectrumModels.MOONSTONE_CHISELED.create(block, textureMap, ctx.modelOutput);
			ResourceLocation down = SpectrumModels.MOONSTONE_CHISELED_DOWN.createWithSuffix(block, "_down", textureMap, ctx.modelOutput);
			return MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createDownDefaultFacingVariantMap(ModelLocationUtils.getModelLocation(block), ModelLocationUtils.getModelLocation(block, "_down")));
		});
	}

	public static final DeferredBlock<Block> SMOOTH_BASALT_STAIRS = register(blockWithItem("smooth_basalt_stairs", () -> new StairBlock(BASALT.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BASALT)), InkColors.BROWN));
	public static final DeferredBlock<Block> SMOOTH_BASALT_SLAB = register(blockWithItem("smooth_basalt_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BASALT)), InkColors.BROWN));
	public static final DeferredBlock<Block> SMOOTH_BASALT_WALL = register(blockWithItem("smooth_basalt_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(BASALT)), InkColors.BROWN));
	public static final BlockFamily SMOOTH_BASALT_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(SMOOTH_BASALT).stairs(SpectrumBlocks.SMOOTH_BASALT_STAIRS.get()).slab(SpectrumBlocks.SMOOTH_BASALT_SLAB.get()).wall(SpectrumBlocks.SMOOTH_BASALT_WALL.get()).getFamily(), TexturedModel.CUBE);

	public static final DeferredBlock<Block> POLISHED_BASALT = register(blockWithItem("polished_basalt", () -> new Block(settings(MapColor.COLOR_BLACK, SoundType.BASALT, 2.0F, 5.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_BASALT_STAIRS = register(blockWithItem("polished_basalt_stairs", () -> new StairBlock(SpectrumBlocks.POLISHED_BASALT.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_BASALT_SLAB = register(blockWithItem("polished_basalt_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_BASALT_WALL = register(blockWithItem("polished_basalt_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_BASALT_BUTTON = register(blockWithItem("polished_basalt_button", () -> new ButtonBlock(SpectrumBlockSetTypes.POLISHED_BASALT, 5, Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_BASALT_PRESSURE_PLATE = register(blockWithItem("polished_basalt_pressure_plate", () -> new PressurePlateBlock(SpectrumBlockSetTypes.POLISHED_BASALT, Properties.of().mapColor(MapColor.COLOR_BLACK).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)), InkColors.BROWN));
	public static final DeferredBlock<Block> CHISELED_POLISHED_BASALT = register(blockWithItem("chiseled_polished_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN));
	public static final BlockFamily POLISHED_BASALT_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.POLISHED_BASALT.get()).stairs(SpectrumBlocks.POLISHED_BASALT_STAIRS.get()).slab(SpectrumBlocks.POLISHED_BASALT_SLAB.get()).wall(SpectrumBlocks.POLISHED_BASALT_WALL.get()).button(SpectrumBlocks.POLISHED_BASALT_BUTTON.get()).pressurePlate(SpectrumBlocks.POLISHED_BASALT_PRESSURE_PLATE.get()).chiseled(SpectrumBlocks.CHISELED_POLISHED_BASALT.get()).getFamily());

	public static final DeferredBlock<Block> POLISHED_BASALT_PILLAR = register(axisRotated(blockWithItem("polished_basalt_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> POLISHED_BASALT_CREST = register(blockWithItem("polished_basalt_crest", () -> new CardinalFacingBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(ctx, block, TexturedModel.COLUMN).with(SpectrumModelHelper.createCardinalFacingVariantMap())));
	public static final DeferredBlock<Block> NOTCHED_POLISHED_BASALT = register(singleton(blockWithItem("notched_polished_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN), TexturedModel.COLUMN));

	public static final DeferredBlock<Block> BASALT_BRICKS = register(blockWithItem("basalt_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASALT_BRICK_STAIRS = register(blockWithItem("basalt_brick_stairs", () -> new StairBlock(SpectrumBlocks.BASALT_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASALT_BRICK_SLAB = register(blockWithItem("basalt_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASALT_BRICK_WALL = register(blockWithItem("basalt_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CRACKED_BASALT_BRICKS = register(blockWithItem("cracked_basalt_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get())), InkColors.BROWN));
	public static final BlockFamily BASALT_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BASALT_BRICKS.get()).stairs(SpectrumBlocks.BASALT_BRICK_STAIRS.get()).slab(SpectrumBlocks.BASALT_BRICK_SLAB.get()).wall(SpectrumBlocks.BASALT_BRICK_WALL.get()).cracked(SpectrumBlocks.CRACKED_BASALT_BRICKS.get()).getFamily());

	public static final DeferredBlock<Block> BASALT_TILES = register(blockWithItem("basalt_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASALT_TILE_STAIRS = register(blockWithItem("basalt_tile_stairs", () -> new StairBlock(SpectrumBlocks.BASALT_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASALT_TILE_SLAB = register(blockWithItem("basalt_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASALT_TILE_WALL = register(blockWithItem("basalt_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CRACKED_BASALT_TILES = register(blockWithItem("cracked_basalt_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_TILES.get())), InkColors.BROWN));
	public static final BlockFamily BASALT_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BASALT_TILES.get()).stairs(SpectrumBlocks.BASALT_TILE_STAIRS.get()).slab(SpectrumBlocks.BASALT_TILE_SLAB.get()).wall(SpectrumBlocks.BASALT_TILE_WALL.get()).cracked(SpectrumBlocks.CRACKED_BASALT_TILES.get()).getFamily());

	public static final DeferredBlock<Block> PLANED_BASALT = register(blockWithItem("planed_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PLANED_BASALT_STAIRS = register(blockWithItem("planed_basalt_stairs", () -> new StairBlock(SpectrumBlocks.PLANED_BASALT.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PLANED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PLANED_BASALT_SLAB = register(blockWithItem("planed_basalt_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PLANED_BASALT.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PLANED_BASALT_WALL = register(blockWithItem("planed_basalt_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PLANED_BASALT.get())), InkColors.BROWN));
	public static final BlockFamily PLANED_BASALT_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.PLANED_BASALT.get()).stairs(SpectrumBlocks.PLANED_BASALT_STAIRS.get()).slab(SpectrumBlocks.PLANED_BASALT_SLAB.get()).wall(SpectrumBlocks.PLANED_BASALT_WALL.get()).getFamily());

	public static final DeferredBlock<Block> TOPAZ_CHISELED_BASALT = register(simple(blockWithItem("topaz_chiseled_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get()).lightLevel(s -> 6)), InkColors.CYAN)));
	public static final DeferredBlock<Block> AMETHYST_CHISELED_BASALT = register(simple(blockWithItem("amethyst_chiseled_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get()).lightLevel(s -> 5)), InkColors.MAGENTA)));
	public static final DeferredBlock<Block> CITRINE_CHISELED_BASALT = register(simple(blockWithItem("citrine_chiseled_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get()).lightLevel(s -> 7)), InkColors.YELLOW)));
	public static final DeferredBlock<Block> ONYX_CHISELED_BASALT = register(simple(blockWithItem("onyx_chiseled_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get()).lightLevel(s -> 3)), InkColors.BLACK)));
	public static final DeferredBlock<Block> MOONSTONE_CHISELED_BASALT = register(moonstoneChiseled(blockWithItem("moonstone_chiseled_basalt", () -> new SpectrumLineFacingBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BASALT_BRICKS.get()).lightLevel(s -> 12)), InkColors.WHITE), SpectrumTextures.BASALT_CAP));

	public static final DeferredBlock<Block> CALCITE_STAIRS = register(blockWithItem("calcite_stairs", () -> new StairBlock(CALCITE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CALCITE)), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_SLAB = register(blockWithItem("calcite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE)), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_WALL = register(blockWithItem("calcite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE)), InkColors.BROWN));
	public static final BlockFamily CALCITE_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(CALCITE).stairs(SpectrumBlocks.CALCITE_STAIRS.get()).slab(SpectrumBlocks.CALCITE_SLAB.get()).wall(SpectrumBlocks.CALCITE_WALL.get()).getFamily(), TexturedModel.CUBE);

	public static final DeferredBlock<Block> POLISHED_CALCITE = register(blockWithItem("polished_calcite", () -> new Block(settings(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE, 2.0F, 5.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops()), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_CALCITE_STAIRS = register(blockWithItem("polished_calcite_stairs", () -> new StairBlock(SpectrumBlocks.POLISHED_CALCITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_CALCITE_SLAB = register(blockWithItem("polished_calcite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_CALCITE_WALL = register(blockWithItem("polished_calcite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_CALCITE_BUTTON = register(blockWithItem("polished_calcite_button", () -> new ButtonBlock(SpectrumBlockSetTypes.POLISHED_CALCITE, 5, Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_CALCITE_PRESSURE_PLATE = register(blockWithItem("polished_calcite_pressure_plate", () -> new PressurePlateBlock(SpectrumBlockSetTypes.POLISHED_CALCITE, Properties.of().mapColor(MapColor.TERRACOTTA_WHITE).forceSolidOn().instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY)), InkColors.BROWN));
	public static final DeferredBlock<Block> CHISELED_POLISHED_CALCITE = register(blockWithItem("chiseled_polished_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN));
	public static final BlockFamily POLISHED_CALCITE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.POLISHED_CALCITE.get()).stairs(SpectrumBlocks.POLISHED_CALCITE_STAIRS.get()).slab(SpectrumBlocks.POLISHED_CALCITE_SLAB.get()).wall(SpectrumBlocks.POLISHED_CALCITE_WALL.get()).button(SpectrumBlocks.POLISHED_CALCITE_BUTTON.get()).pressurePlate(SpectrumBlocks.POLISHED_CALCITE_PRESSURE_PLATE.get()).chiseled(SpectrumBlocks.CHISELED_POLISHED_CALCITE.get()).getFamily());

	public static final DeferredBlock<Block> POLISHED_CALCITE_PILLAR = register(axisRotated(blockWithItem("polished_calcite_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> POLISHED_CALCITE_CREST = register(blockWithItem("polished_calcite_crest", () -> new CardinalFacingBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(ctx, block, TexturedModel.COLUMN).with(SpectrumModelHelper.createCardinalFacingVariantMap())));
	public static final DeferredBlock<Block> NOTCHED_POLISHED_CALCITE = register(singleton(blockWithItem("notched_polished_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN), TexturedModel.COLUMN));

	public static final DeferredBlock<Block> CALCITE_BRICKS = register(blockWithItem("calcite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_BRICK_STAIRS = register(blockWithItem("calcite_brick_stairs", () -> new StairBlock(SpectrumBlocks.CALCITE_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_BRICK_SLAB = register(blockWithItem("calcite_brick_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_BRICK_WALL = register(blockWithItem("calcite_brick_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CRACKED_CALCITE_BRICKS = register(blockWithItem("cracked_calcite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get())), InkColors.BROWN));
	public static final BlockFamily CALCITE_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.CALCITE_BRICKS.get()).stairs(SpectrumBlocks.CALCITE_BRICK_STAIRS.get()).slab(SpectrumBlocks.CALCITE_BRICK_SLAB.get()).wall(SpectrumBlocks.CALCITE_BRICK_WALL.get()).cracked(SpectrumBlocks.CRACKED_CALCITE_BRICKS.get()).getFamily());

	public static final DeferredBlock<Block> CALCITE_TILES = register(blockWithItem("calcite_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_TILE_STAIRS = register(blockWithItem("calcite_tile_stairs", () -> new StairBlock(SpectrumBlocks.CALCITE_TILES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_TILE_SLAB = register(blockWithItem("calcite_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CALCITE_TILE_WALL = register(blockWithItem("calcite_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> CRACKED_CALCITE_TILES = register(blockWithItem("cracked_calcite_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_TILES.get())), InkColors.BROWN));
	public static final BlockFamily CALCITE_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.CALCITE_TILES.get()).stairs(SpectrumBlocks.CALCITE_TILE_STAIRS.get()).slab(SpectrumBlocks.CALCITE_TILE_SLAB.get()).wall(SpectrumBlocks.CALCITE_TILE_WALL.get()).cracked(SpectrumBlocks.CRACKED_CALCITE_TILES.get()).getFamily());

	public static final DeferredBlock<Block> PLANED_CALCITE = register(blockWithItem("planed_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PLANED_CALCITE_STAIRS = register(blockWithItem("planed_calcite_stairs", () -> new StairBlock(SpectrumBlocks.PLANED_CALCITE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PLANED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PLANED_CALCITE_SLAB = register(blockWithItem("planed_calcite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PLANED_CALCITE.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> PLANED_CALCITE_WALL = register(blockWithItem("planed_calcite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PLANED_CALCITE.get())), InkColors.BROWN));
	public static final BlockFamily PLANED_CALCITE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.PLANED_CALCITE.get()).stairs(SpectrumBlocks.PLANED_CALCITE_STAIRS.get()).slab(SpectrumBlocks.PLANED_CALCITE_SLAB.get()).wall(SpectrumBlocks.PLANED_CALCITE_WALL.get()).getFamily());

	public static final DeferredBlock<Block> TOPAZ_CHISELED_CALCITE = register(simple(blockWithItem("topaz_chiseled_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get()).lightLevel(s -> 6)), InkColors.CYAN)));
	public static final DeferredBlock<Block> AMETHYST_CHISELED_CALCITE = register(simple(blockWithItem("amethyst_chiseled_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get()).lightLevel(s -> 5)), InkColors.MAGENTA)));
	public static final DeferredBlock<Block> CITRINE_CHISELED_CALCITE = register(simple(blockWithItem("citrine_chiseled_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get()).lightLevel(s -> 7)), InkColors.YELLOW)));
	public static final DeferredBlock<Block> ONYX_CHISELED_CALCITE = register(simple(blockWithItem("onyx_chiseled_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get()).lightLevel(s -> 3)), InkColors.BLACK)));
	public static final DeferredBlock<Block> MOONSTONE_CHISELED_CALCITE = register(moonstoneChiseled(blockWithItem("moonstone_chiseled_calcite", () -> new SpectrumLineFacingBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CALCITE_BRICKS.get()).lightLevel(s -> 12)), InkColors.WHITE), SpectrumTextures.CALCITE_CAP));

	public static DeferredBlock<Block>  registerGemstoneLight(String name, DeferredBlock<Block> gemBlock, DeferredBlock<Block> baseBlock, ResourceLocation capTexture, InkColor color) {
		return register(translucent(axisRotated(blockWithItem(name, () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(baseBlock.get()).lightLevel(s -> 15).noOcclusion().forceSolidOn()), color), TexturedModel.createDefault(block -> SpectrumTextureMaps.sideTopInside(TextureMapping.getBlockTexture(block), capTexture, TextureMapping.getBlockTexture(gemBlock.get())), SpectrumModels.MULTILAYER_LIGHT))));
	}

	public static DeferredBlock<Block>  registerGemstoneLight(String name, Block gemBlock, DeferredBlock<Block> baseBlock, ResourceLocation capTexture, InkColor color) {
		return register(translucent(axisRotated(blockWithItem(name, () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(baseBlock.get()).lightLevel(s -> 15).noOcclusion().forceSolidOn()), color), TexturedModel.createDefault(block -> SpectrumTextureMaps.sideTopInside(TextureMapping.getBlockTexture(block), capTexture, TextureMapping.getBlockTexture(gemBlock)), SpectrumModels.MULTILAYER_LIGHT))));
	}

	public static final DeferredBlock<Block> TOPAZ_BASALT_LIGHT = registerGemstoneLight("topaz_basalt_light", SpectrumBlocks.TOPAZ_BLOCK, SpectrumBlocks.POLISHED_BASALT, SpectrumTextures.BASALT_CAP, InkColors.CYAN);
	public static final DeferredBlock<Block> AMETHYST_BASALT_LIGHT = registerGemstoneLight("amethyst_basalt_light", AMETHYST_BLOCK, SpectrumBlocks.POLISHED_BASALT, SpectrumTextures.BASALT_CAP, InkColors.MAGENTA);
	public static final DeferredBlock<Block> CITRINE_BASALT_LIGHT = registerGemstoneLight("citrine_basalt_light", SpectrumBlocks.CITRINE_BLOCK, SpectrumBlocks.POLISHED_BASALT, SpectrumTextures.BASALT_CAP, InkColors.YELLOW);
	public static final DeferredBlock<Block> ONYX_BASALT_LIGHT = registerGemstoneLight("onyx_basalt_light", SpectrumBlocks.ONYX_BLOCK, SpectrumBlocks.POLISHED_BASALT, SpectrumTextures.BASALT_CAP, InkColors.BLACK);
	public static final DeferredBlock<Block> MOONSTONE_BASALT_LIGHT = registerGemstoneLight("moonstone_basalt_light", SpectrumBlocks.MOONSTONE_BLOCK, SpectrumBlocks.POLISHED_BASALT, SpectrumTextures.BASALT_CAP, InkColors.WHITE);
	public static final DeferredBlock<Block> TOPAZ_CALCITE_LIGHT = registerGemstoneLight("topaz_calcite_light", SpectrumBlocks.TOPAZ_BLOCK, SpectrumBlocks.POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, InkColors.CYAN);
	public static final DeferredBlock<Block> AMETHYST_CALCITE_LIGHT = registerGemstoneLight("amethyst_calcite_light", AMETHYST_BLOCK, SpectrumBlocks.POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, InkColors.MAGENTA);
	public static final DeferredBlock<Block> CITRINE_CALCITE_LIGHT = registerGemstoneLight("citrine_calcite_light", SpectrumBlocks.CITRINE_BLOCK, SpectrumBlocks.POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, InkColors.YELLOW);
	public static final DeferredBlock<Block> ONYX_CALCITE_LIGHT = registerGemstoneLight("onyx_calcite_light", SpectrumBlocks.ONYX_BLOCK, SpectrumBlocks.POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, InkColors.BLACK);
	public static final DeferredBlock<Block> MOONSTONE_CALCITE_LIGHT = registerGemstoneLight("moonstone_calcite_light", SpectrumBlocks.MOONSTONE_BLOCK, SpectrumBlocks.POLISHED_CALCITE, SpectrumTextures.CALCITE_CAP, InkColors.WHITE);

	// GLASS
	private static Properties gemstoneGlass(SoundType soundGroup, MapColor mapColor) {
		return BlockBehaviour.Properties.ofFullCopy(GLASS).sound(soundGroup).mapColor(mapColor);
	}

	public static <T extends Block> BlockRegistrar<T> glassPane(BlockRegistrar<T> registrar, DeferredBlock<Block> glassBlock) {
		return translucent(registrar).withBlockModel((ctx, block) -> SpectrumModelHelper.glassPaneBlockModel(ctx, block, glassBlock.get()));
	}

	public static final DeferredBlock<Block> TOPAZ_GLASS = register(translucent(simple(blockWithItem("topaz_glass", () -> new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.TOPAZ_CLUSTER, MapColor.COLOR_CYAN), BuiltinGemstoneColor.CYAN), InkColors.CYAN))));
	public static final DeferredBlock<Block> AMETHYST_GLASS = register(translucent(simple(blockWithItem("amethyst_glass", () -> new GemstoneGlassBlock(gemstoneGlass(SoundType.AMETHYST_CLUSTER, MapColor.COLOR_MAGENTA), BuiltinGemstoneColor.MAGENTA), InkColors.MAGENTA))));
	public static final DeferredBlock<Block> CITRINE_GLASS = register(translucent(simple(blockWithItem("citrine_glass", () -> new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.CITRINE_CLUSTER, MapColor.COLOR_YELLOW), BuiltinGemstoneColor.YELLOW), InkColors.YELLOW))));
	public static final DeferredBlock<Block> ONYX_GLASS = register(translucent(simple(blockWithItem("onyx_glass", () -> new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.ONYX_CLUSTER, MapColor.COLOR_BLACK), BuiltinGemstoneColor.BLACK), InkColors.BLACK))));
	public static final DeferredBlock<Block> MOONSTONE_GLASS = register(translucent(simple(blockWithItem("moonstone_glass", () -> new GemstoneGlassBlock(gemstoneGlass(SpectrumBlockSoundGroups.MOONSTONE_CLUSTER, MapColor.SNOW), BuiltinGemstoneColor.WHITE), InkColors.WHITE))));
	public static final DeferredBlock<Block> RADIANT_GLASS = register(translucent(simple(blockWithItem("radiant_glass", () -> new RadiantGlassBlock(gemstoneGlass(SoundType.GLASS, MapColor.SAND).lightLevel(value -> 12)), InkColors.WHITE))));

	public static final DeferredBlock<Block> TOPAZ_GLASS_PANE = register(glassPane(blockWithItem("topaz_glass_pane", () -> new IronBarsBlock(gemstoneGlass(SpectrumBlockSoundGroups.TOPAZ_CLUSTER, MapColor.COLOR_CYAN)), InkColors.CYAN), SpectrumBlocks.TOPAZ_GLASS));
	public static final DeferredBlock<Block> AMETHYST_GLASS_PANE = register(glassPane(blockWithItem("amethyst_glass_pane", () -> new IronBarsBlock(gemstoneGlass(SoundType.AMETHYST_CLUSTER, MapColor.COLOR_MAGENTA)), InkColors.MAGENTA), SpectrumBlocks.AMETHYST_GLASS));
	public static final DeferredBlock<Block> CITRINE_GLASS_PANE = register(glassPane(blockWithItem("citrine_glass_pane", () -> new IronBarsBlock(gemstoneGlass(SpectrumBlockSoundGroups.CITRINE_CLUSTER, MapColor.COLOR_YELLOW)), InkColors.YELLOW), SpectrumBlocks.CITRINE_GLASS));
	public static final DeferredBlock<Block> ONYX_GLASS_PANE = register(glassPane(blockWithItem("onyx_glass_pane", () -> new IronBarsBlock(gemstoneGlass(SpectrumBlockSoundGroups.ONYX_CLUSTER, MapColor.COLOR_BLACK)), InkColors.BLACK), SpectrumBlocks.ONYX_GLASS));
	public static final DeferredBlock<Block> MOONSTONE_GLASS_PANE = register(glassPane(blockWithItem("moonstone_glass_pane", () -> new IronBarsBlock(gemstoneGlass(SpectrumBlockSoundGroups.MOONSTONE_CLUSTER, MapColor.SNOW)), InkColors.WHITE), SpectrumBlocks.MOONSTONE_GLASS));
	public static final DeferredBlock<Block> RADIANT_GLASS_PANE = register(glassPane(blockWithItem("radiant_glass_pane", () -> new IronBarsBlock(gemstoneGlass(SoundType.GLASS, MapColor.SAND).lightLevel(value -> 12)), InkColors.WHITE), SpectrumBlocks.RADIANT_GLASS));

	public static final DeferredBlock<Block> ETHEREAL_PLATFORM = register(translucent(simple(blockWithItem("ethereal_platform", () -> new EtherealPlatformBlock(gemstoneGlass(SoundType.AMETHYST, MapColor.NONE).pushReaction(PushReaction.NORMAL)), InkColors.LIGHT_GRAY))));
	public static final DeferredBlock<Block> UNIVERSE_SPYHOLE = register(translucent(simple(blockWithItem("universe_spyhole", () -> new TransparentBlock(settings(MapColor.NONE, SpectrumBlockSoundGroups.CITRINE_BLOCK, 1.5F).requiresCorrectToolForDrops().isViewBlocking(SpectrumBlocks::never)), InkColors.LIGHT_GRAY))));

	private static Properties chime(BlockBehaviour block) {
		return BlockBehaviour.Properties.ofFullCopy(block).pushReaction(PushReaction.DESTROY).destroyTime(1.0F).noOcclusion();
	}

	public static final DeferredBlock<Block> TOPAZ_CHIME = register(translucent(singleton(blockWithItem("topaz_chime", () -> new GemstoneChimeBlock(chime(SpectrumBlocks.TOPAZ_CLUSTER.get()), SpectrumSoundEvents.BLOCK_TOPAZ_BLOCK_CHIME, ColoredSparkleRisingParticleEffect.CYAN), InkColors.CYAN), SpectrumTexturedModels.CHIME)));
	public static final DeferredBlock<Block> AMETHYST_CHIME = register(translucent(singleton(blockWithItem("amethyst_chime", () -> new GemstoneChimeBlock(chime(AMETHYST_CLUSTER), SoundEvents.AMETHYST_BLOCK_CHIME, ColoredSparkleRisingParticleEffect.MAGENTA), InkColors.MAGENTA), SpectrumTexturedModels.CHIME)));
	public static final DeferredBlock<Block> CITRINE_CHIME = register(translucent(singleton(blockWithItem("citrine_chime", () -> new GemstoneChimeBlock(chime(SpectrumBlocks.CITRINE_CLUSTER.get()), SpectrumSoundEvents.BLOCK_CITRINE_BLOCK_CHIME, ColoredSparkleRisingParticleEffect.YELLOW), InkColors.YELLOW), SpectrumTexturedModels.CHIME)));
	public static final DeferredBlock<Block> ONYX_CHIME = register(translucent(singleton(blockWithItem("onyx_chime", () -> new GemstoneChimeBlock(chime(SpectrumBlocks.ONYX_CLUSTER.get()), SpectrumSoundEvents.BLOCK_ONYX_BLOCK_CHIME, ColoredSparkleRisingParticleEffect.BLACK), InkColors.BLACK), SpectrumTexturedModels.CHIME)));
	public static final DeferredBlock<Block> MOONSTONE_CHIME = register(translucent(singleton(blockWithItem("moonstone_chime", () -> new GemstoneChimeBlock(chime(SpectrumBlocks.MOONSTONE_CLUSTER.get()), SpectrumSoundEvents.BLOCK_MOONSTONE_BLOCK_CHIME, ColoredSparkleRisingParticleEffect.WHITE), InkColors.WHITE), SpectrumTexturedModels.CHIME)));

	private static Properties pylon(BlockBehaviour block) {
		return BlockBehaviour.Properties.ofFullCopy(block).noOcclusion();
	}

	public static final DeferredBlock<Block> TOPAZ_PYLON = register(pylon(blockWithItem("topaz_pylon", () -> new PylonBlock(pylon(SpectrumBlocks.TOPAZ_BLOCK.get())), InkColors.CYAN)));
	public static final DeferredBlock<Block> AMETHYST_PYLON = register(pylon(blockWithItem("amethyst_pylon", () -> new PylonBlock(pylon(AMETHYST_BLOCK)), InkColors.MAGENTA)));
	public static final DeferredBlock<Block> CITRINE_PYLON = register(pylon(blockWithItem("citrine_pylon", () -> new PylonBlock(pylon(SpectrumBlocks.CITRINE_BLOCK.get())), InkColors.YELLOW)));
	public static final DeferredBlock<Block> ONYX_PYLON = register(pylon(blockWithItem("onyx_pylon", () -> new PylonBlock(pylon(SpectrumBlocks.ONYX_BLOCK.get())), InkColors.BLACK)));
	public static final DeferredBlock<Block> MOONSTONE_PYLON = register(pylon(blockWithItem("moonstone_pylon", () -> new PylonBlock(pylon(SpectrumBlocks.MOONSTONE_BLOCK.get())), InkColors.WHITE)));

	public static final DeferredBlock<Block> SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("semi_permeable_glass", () -> new AlternatePlayerOnlyGlassBlock(BlockBehaviour.Properties.ofFullCopy(GLASS), GLASS, false), InkColors.WHITE), b -> GLASS)));
	public static final DeferredBlock<Block> TINTED_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("tinted_semi_permeable_glass", () -> new AlternatePlayerOnlyGlassBlock(BlockBehaviour.Properties.ofFullCopy(TINTED_GLASS), TINTED_GLASS, true), InkColors.BLACK), b -> TINTED_GLASS)));
	public static final DeferredBlock<Block> RADIANT_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("radiant_semi_permeable_glass", () -> new AlternatePlayerOnlyGlassBlock(Properties.ofFullCopy(SpectrumBlocks.RADIANT_GLASS.get()), SpectrumBlocks.RADIANT_GLASS.get(), false), InkColors.YELLOW), b -> SpectrumBlocks.RADIANT_GLASS.get())));
	public static final DeferredBlock<Block> TOPAZ_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("topaz_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.TOPAZ_GLASS.get()), BuiltinGemstoneColor.CYAN), InkColors.CYAN), b -> SpectrumBlocks.TOPAZ_GLASS.get())));
	public static final DeferredBlock<Block> AMETHYST_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("amethyst_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.AMETHYST_GLASS.get()), BuiltinGemstoneColor.MAGENTA), InkColors.MAGENTA), b -> SpectrumBlocks.AMETHYST_GLASS.get())));
	public static final DeferredBlock<Block> CITRINE_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("citrine_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.CITRINE_GLASS.get()), BuiltinGemstoneColor.YELLOW), InkColors.YELLOW), b -> SpectrumBlocks.CITRINE_GLASS.get())));
	public static final DeferredBlock<Block> ONYX_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("onyx_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.ONYX_GLASS.get()), BuiltinGemstoneColor.BLACK), InkColors.BLACK), b -> SpectrumBlocks.ONYX_GLASS.get())));
	public static final DeferredBlock<Block> MOONSTONE_SEMI_PERMEABLE_GLASS = register(translucent(parented(blockWithItem("moonstone_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.MOONSTONE_GLASS.get()), BuiltinGemstoneColor.WHITE), InkColors.WHITE), b -> SpectrumBlocks.MOONSTONE_GLASS.get())));

	// TODO: what
	public static final ResourceKey<Block> GLISTERING_MELON = singleton(new BlockRegistrar<>("glistering_melon").withBlock(() -> new Block(BlockBehaviour.Properties.ofFullCopy(MELON))).withItem(block -> new BlockItem(block, IS.of()), InkColors.LIME), TexturedModel.COLUMN).blockKey();
	public static final ResourceKey<Block> ATTACHED_GLISTERING_MELON_STEM = cutout(new BlockRegistrar<>("attached_glistering_melon_stem").withBlock(() -> new AttachedStemBlock(ResourceKey.create(Registries.BLOCK, locate("glistering_melon_stem")), SpectrumBlocks.GLISTERING_MELON, SpectrumItems.GLISTERING_MELON_SEEDS, Properties.ofFullCopy(ATTACHED_MELON_STEM)))).blockKey();
	public static final ResourceKey<Block> GLISTERING_MELON_STEM = cutout(new BlockRegistrar<>("glistering_melon_stem").withBlock(() -> new StemBlock(SpectrumBlocks.GLISTERING_MELON, SpectrumBlocks.ATTACHED_GLISTERING_MELON_STEM, SpectrumItems.GLISTERING_MELON_SEEDS, Properties.ofFullCopy(MELON_STEM))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.AGE_7).generate(age -> SpectrumModelHelper.createModelVariant(ModelTemplates.STEMS[age].create(block, TextureMapping.stem(block), ctx.modelOutput))))).withBlockModel((ctx, block) -> {
		var attached = BuiltInRegistries.BLOCK.get(SpectrumBlocks.ATTACHED_GLISTERING_MELON_STEM);
		ctx.skipAutoItemBlock(block); // Needed b/c vanilla auto-generates an incorrect seeds model for some reason
		return SpectrumModelHelper.createVariantsSupplier(attached, ModelTemplates.ATTACHED_STEM.create(attached, TextureMapping.attachedStem(block, attached), ctx.modelOutput)).with(SpectrumModelHelper.createWestDefaultHorizontalFacingVariantMap());
	})).blockKey();

	public static final DeferredBlock<Block> PRESENT = register(cutout(blockWithItem("present", () -> new PresentBlock(BlockBehaviour.Properties.ofFullCopy(WHITE_WOOL)), block -> new PresentBlockItem(block, IS.of(1)), InkColors.LIGHT_GRAY)).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(PresentBlock.VARIANT).generate(variant -> SpectrumModelHelper.createModelVariant(SpectrumModels.PRESENT.createWithSuffix(block, "_" + variant.getSerializedName(), new TextureMapping().put(TextureSlot.TEXTURE, TextureMapping.getBlockTexture(block, "_" + variant.getSerializedName())).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(variant.woolBase)), ctx.modelOutput))))).withPredefinedItemModel());
	public static final DeferredBlock<Block> TITRATION_BARREL = register(blockWithItem("titration_barrel", () -> new TitrationBarrelBlock(BlockBehaviour.Properties.ofFullCopy(OAK_PLANKS).mapColor(MapColor.COLOR_RED)), InkColors.MAGENTA).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createUpDefaultHorizontalFacingVariantMap()).with(PropertyDispatch.property(TitrationBarrelBlock.BARREL_STATE).generate(state -> SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeBottomTop(b -> b, "_side", b -> b, "_top_" + state.getSerializedName(), b -> b, "_bottom").createWithSuffix(block, state == TitrationBarrelBlock.BarrelState.EMPTY ? "" : "_" + state.getSerializedName(), ctx.modelOutput))))));

	public static final DeferredBlock<Block> BLOCK_FLOODER = register(simple(block("block_flooder", () -> new BlockFlooderBlock(settings(MapColor.CLAY, SoundType.ROOTED_DIRT, 0.0F)))));
	public static final DeferredBlock<Block> BOTTOMLESS_BUNDLE = register(cutout(blockWithItem("bottomless_bundle", () -> new BottomlessBundleBlock(settings(MapColor.ICE, SoundType.WOOL, 1.0F).noOcclusion().pushReaction(PushReaction.DESTROY)), block -> new BottomlessBundleItem(block, IS.of(1)), InkColors.LIGHT_GRAY))
			.withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(BottomlessBundleBlock.LOCKED, ModelLocationUtils.getModelLocation(block, "_locked"), ModelLocationUtils.getModelLocation(block, "_unlocked"))))
			.withPredefinedItemModel());

	//TODO these names don't match
	public static final DeferredBlock<Block> WAND_LIGHT_BLOCK = register(singleton(block("wand_light", () -> new WandLightBlock(BlockBehaviour.Properties.ofFullCopy(LIGHT).sound(SpectrumBlockSoundGroups.WAND_LIGHT).instabreak())), SpectrumTexturedModels.particle(SpectrumTextures.SHIMMERSTONE_LIGHT)));
	public static final DeferredBlock<Block> DECAYING_LIGHT_BLOCK = register(parented(block("decaying_light", () -> new DecayingLightBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.WAND_LIGHT_BLOCK.get()).randomTicks())), b -> SpectrumBlocks.WAND_LIGHT_BLOCK.get()));

	private static Properties decay(MapColor mapColor, SoundType soundGroup, float strength, float resistance, PushReaction pistonBehavior) {
		return settings(mapColor, soundGroup, strength, resistance).pushReaction(pistonBehavior).randomTicks().isValidSpawn((state, world, pos, type) -> false);
	}

	public static <T extends Block> BlockRegistrar<T> decay(BlockRegistrar<T> registrar) {
		return registrar.withBlockModel((ctx, block) -> {
			ResourceLocation none = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_none", SpectrumTextureMaps.all(block, "_none"), ctx.modelOutput);
			ResourceLocation def = ModelTemplates.CUBE_ALL.createWithSuffix(block, "_default", SpectrumTextureMaps.all(block, "_default"), ctx.modelOutput);
			return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(DecayBlock.CONVERSION).select(DecayBlock.Conversion.NONE, SpectrumModelHelper.createModelVariant(none)).select(DecayBlock.Conversion.DEFAULT, SpectrumModelHelper.createModelVariant(def)).select(DecayBlock.Conversion.SPECIAL, SpectrumModelHelper.createModelVariant(def)));
		});
	}

	public static final DeferredBlock<Block> FADING = register(decay(block("fading", () -> new FadingBlock(decay(MapColor.PLANT, SoundType.GRASS, 0.5F, 0.5F, PushReaction.DESTROY)))));
	public static final DeferredBlock<Block> FAILING = register(decay(block("failing", () -> new FailingBlock(decay(MapColor.COLOR_BLACK, SoundType.STONE, 20.0F, 50.0F, PushReaction.BLOCK)))));
	public static final DeferredBlock<Block> RUIN = register(decay(block("ruin", () -> new RuinBlock(decay(MapColor.COLOR_BLACK, SoundType.STONE, 100.0F, 3600000.0F, PushReaction.BLOCK)))));
	public static final DeferredBlock<Block> FORFEITURE = register(decay(block("forfeiture", () -> new ForfeitureBlock(decay(MapColor.COLOR_BLACK, SoundType.STONE, 100.0F, 3600000.0F, PushReaction.BLOCK)))));
	public static final DeferredBlock<Block> DECAY_AWAY = register(simple(block("decay_away", () -> new DecayAwayBlock(BlockBehaviour.Properties.ofFullCopy(DIRT).pushReaction(PushReaction.DESTROY)))));

	// PASTEL NETWORK
	private static Properties pastelNode(SoundType soundGroup) {
		return settings(MapColor.NONE, soundGroup, 1.5F).pushReaction(PushReaction.DESTROY).noOcclusion().requiresCorrectToolForDrops();
	}

	public static final DeferredBlock<Block> CONNECTION_NODE = register(cutout(defaultUpFacing(blockWithItem("connection_node", () -> new PastelNodeBlock(pastelNode(SoundType.AMETHYST_CLUSTER), PastelNodeType.CONNECTION), IS.of(16), InkColors.LIGHT_GRAY), SpectrumTexturedModels.parented(SpectrumModels.PASTEL_GENERIC_NODE))).withPredefinedItemModel());
	public static final DeferredBlock<Block> PROVIDER_NODE = register(cutout(defaultUpFacing(blockWithItem("provider_node", () -> new PastelNodeBlock(pastelNode(SoundType.AMETHYST_CLUSTER), PastelNodeType.PROVIDER), IS.of(16), InkColors.MAGENTA), SpectrumTexturedModels.parented(SpectrumModels.PASTEL_PUSH_NODE))).withPredefinedItemModel());
	public static final DeferredBlock<Block> STORAGE_NODE = register(cutout(defaultUpFacing(blockWithItem("storage_node", () -> new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.TOPAZ_CLUSTER), PastelNodeType.STORAGE), IS.of(16), InkColors.CYAN), SpectrumTexturedModels.parented(SpectrumModels.PASTEL_STORE_NODE))).withPredefinedItemModel());
	public static final DeferredBlock<Block> BUFFER_NODE = register(cutout(defaultUpFacing(blockWithItem("buffer_node", () -> new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.TOPAZ_CLUSTER), PastelNodeType.BUFFER), IS.of(16), InkColors.GREEN), SpectrumTexturedModels.parented(SpectrumModels.PASTEL_STORE_NODE))).withPredefinedItemModel());
	public static final DeferredBlock<Block> SENDER_NODE = register(cutout(defaultUpFacing(blockWithItem("sender_node", () -> new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.CITRINE_CLUSTER), PastelNodeType.SENDER), IS.of(16), InkColors.YELLOW), SpectrumTexturedModels.parented(SpectrumModels.PASTEL_PUSH_NODE))).withPredefinedItemModel());
	public static final DeferredBlock<Block> GATHER_NODE = register(cutout(defaultUpFacing(blockWithItem("gather_node", () -> new PastelNodeBlock(pastelNode(SpectrumBlockSoundGroups.ONYX_CLUSTER), PastelNodeType.GATHER), IS.of(16), InkColors.BLACK), SpectrumTexturedModels.parented(SpectrumModels.PASTEL_PULL_NODE))).withPredefinedItemModel());

	// COLORED BLOCK FAMILIES

	public static DeferredBlock<Block> registerColoredPlanks(String name, InkColor color) {
		return register(blockWithItem(name, () -> new ColoredPlankBlock(copyWithMapColor(OAK_PLANKS, color.getDyeColor().orElse(DyeColor.LIME).getMapColor()), color), color));
	}

	public static DeferredBlock<Block> registerColoredStairs(String name, DeferredBlock<Block> baseBlock, InkColor color) {
		return register(blockWithItem(name, () -> new ColoredStairsBlock(baseBlock.get().defaultBlockState(), copyWithMapColor(OAK_STAIRS, baseBlock.get().defaultMapColor()), color), color));
	}

	public static DeferredBlock<Block> registerColoredPressurePlate(String name, DeferredBlock<Block> baseBlock, InkColor color) {
		return register(blockWithItem(name, () -> new ColoredPressurePlateBlock(copyWithMapColor(OAK_PRESSURE_PLATE, baseBlock.get().defaultMapColor()), color), color));
	}

	public static DeferredBlock<Block> registerColoredFence(String name, DeferredBlock<Block> baseBlock, InkColor color) {
		return register(burnable(blockWithItem(name, () -> new ColoredFenceBlock(copyWithMapColor(OAK_FENCE, baseBlock.get().defaultMapColor()), color), color), 300));
	}

	public static DeferredBlock<Block> registerColoredFenceGate(String name, DeferredBlock<Block> baseBlock, InkColor color) {
		return register(burnable(blockWithItem(name, () -> new ColoredFenceGateBlock(copyWithMapColor(OAK_FENCE_GATE, baseBlock.get().defaultMapColor()), color), color), 300));
	}

	public static DeferredBlock<Block> registerColoredButton(String name, DeferredBlock<Block> baseBlock, InkColor color) {
		return register(blockWithItem(name, () -> new ColoredWoodenButtonBlock(copyWithMapColor(OAK_BUTTON, baseBlock.get().defaultMapColor()), color), color));
	}

	public static DeferredBlock<Block> registerColoredSlab(String name, DeferredBlock<Block> block, InkColor color) {
		return register(blockWithItem(name, () -> new ColoredSlabBlock(copyWithMapColor(OAK_SLAB, block.get().defaultMapColor()), color), color));
	}

	public static final DeferredBlock<Block> BLACK_PLANKS = registerColoredPlanks("black_planks", InkColors.BLACK);
	public static final DeferredBlock<Block> BLACK_STAIRS = registerColoredStairs("black_stairs", SpectrumBlocks.BLACK_PLANKS, InkColors.BLACK);
	public static final DeferredBlock<Block> BLACK_PRESSURE_PLATE = registerColoredPressurePlate("black_pressure_plate", SpectrumBlocks.BLACK_PLANKS, InkColors.BLACK);
	public static final DeferredBlock<Block> BLACK_FENCE = registerColoredFence("black_fence", SpectrumBlocks.BLACK_PLANKS, InkColors.BLACK);
	public static final DeferredBlock<Block> BLACK_FENCE_GATE = registerColoredFenceGate("black_fence_gate", SpectrumBlocks.BLACK_PLANKS, InkColors.BLACK);
	public static final DeferredBlock<Block> BLACK_BUTTON = registerColoredButton("black_button", SpectrumBlocks.BLACK_PLANKS, InkColors.BLACK);
	public static final DeferredBlock<Block> BLACK_SLAB = registerColoredSlab("black_slab", SpectrumBlocks.BLACK_PLANKS, InkColors.BLACK);
	public static final BlockFamily BLACK_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BLACK_PLANKS.get()).stairs(SpectrumBlocks.BLACK_STAIRS.get()).pressurePlate(SpectrumBlocks.BLACK_PRESSURE_PLATE.get()).fence(SpectrumBlocks.BLACK_FENCE.get()).fenceGate(SpectrumBlocks.BLACK_FENCE_GATE.get()).button(SpectrumBlocks.BLACK_BUTTON.get()).slab(SpectrumBlocks.BLACK_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> BLUE_PLANKS = registerColoredPlanks("blue_planks", InkColors.BLUE);
	public static final DeferredBlock<Block> BLUE_STAIRS = registerColoredStairs("blue_stairs", SpectrumBlocks.BLUE_PLANKS, InkColors.BLUE);
	public static final DeferredBlock<Block> BLUE_PRESSURE_PLATE = registerColoredPressurePlate("blue_pressure_plate", SpectrumBlocks.BLUE_PLANKS, InkColors.BLUE);
	public static final DeferredBlock<Block> BLUE_FENCE = registerColoredFence("blue_fence", SpectrumBlocks.BLUE_PLANKS, InkColors.BLUE);
	public static final DeferredBlock<Block> BLUE_FENCE_GATE = registerColoredFenceGate("blue_fence_gate", SpectrumBlocks.BLUE_PLANKS, InkColors.BLUE);
	public static final DeferredBlock<Block> BLUE_BUTTON = registerColoredButton("blue_button", SpectrumBlocks.BLUE_PLANKS, InkColors.BLUE);
	public static final DeferredBlock<Block> BLUE_SLAB = registerColoredSlab("blue_slab", SpectrumBlocks.BLUE_PLANKS, InkColors.BLUE);
	public static final BlockFamily BLUE_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BLUE_PLANKS.get()).stairs(SpectrumBlocks.BLUE_STAIRS.get()).pressurePlate(SpectrumBlocks.BLUE_PRESSURE_PLATE.get()).fence(SpectrumBlocks.BLUE_FENCE.get()).fenceGate(SpectrumBlocks.BLUE_FENCE_GATE.get()).button(SpectrumBlocks.BLUE_BUTTON.get()).slab(SpectrumBlocks.BLUE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> BROWN_PLANKS = registerColoredPlanks("brown_planks", InkColors.BROWN);
	public static final DeferredBlock<Block> BROWN_STAIRS = registerColoredStairs("brown_stairs", SpectrumBlocks.BROWN_PLANKS, InkColors.BROWN);
	public static final DeferredBlock<Block> BROWN_PRESSURE_PLATE = registerColoredPressurePlate("brown_pressure_plate", SpectrumBlocks.BROWN_PLANKS, InkColors.BROWN);
	public static final DeferredBlock<Block> BROWN_FENCE = registerColoredFence("brown_fence", SpectrumBlocks.BROWN_PLANKS, InkColors.BROWN);
	public static final DeferredBlock<Block> BROWN_FENCE_GATE = registerColoredFenceGate("brown_fence_gate", SpectrumBlocks.BROWN_PLANKS, InkColors.BROWN);
	public static final DeferredBlock<Block> BROWN_BUTTON = registerColoredButton("brown_button", SpectrumBlocks.BROWN_PLANKS, InkColors.BROWN);
	public static final DeferredBlock<Block> BROWN_SLAB = registerColoredSlab("brown_slab", SpectrumBlocks.BROWN_PLANKS, InkColors.BROWN);
	public static final BlockFamily BROWN_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BROWN_PLANKS.get()).stairs(SpectrumBlocks.BROWN_STAIRS.get()).pressurePlate(SpectrumBlocks.BROWN_PRESSURE_PLATE.get()).fence(SpectrumBlocks.BROWN_FENCE.get()).fenceGate(SpectrumBlocks.BROWN_FENCE_GATE.get()).button(SpectrumBlocks.BROWN_BUTTON.get()).slab(SpectrumBlocks.BROWN_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> CYAN_PLANKS = registerColoredPlanks("cyan_planks", InkColors.CYAN);
	public static final DeferredBlock<Block> CYAN_STAIRS = registerColoredStairs("cyan_stairs", SpectrumBlocks.CYAN_PLANKS, InkColors.CYAN);
	public static final DeferredBlock<Block> CYAN_PRESSURE_PLATE = registerColoredPressurePlate("cyan_pressure_plate", SpectrumBlocks.CYAN_PLANKS, InkColors.CYAN);
	public static final DeferredBlock<Block> CYAN_FENCE = registerColoredFence("cyan_fence", SpectrumBlocks.CYAN_PLANKS, InkColors.CYAN);
	public static final DeferredBlock<Block> CYAN_FENCE_GATE = registerColoredFenceGate("cyan_fence_gate", SpectrumBlocks.CYAN_PLANKS, InkColors.CYAN);
	public static final DeferredBlock<Block> CYAN_BUTTON = registerColoredButton("cyan_button", SpectrumBlocks.CYAN_PLANKS, InkColors.CYAN);
	public static final DeferredBlock<Block> CYAN_SLAB = registerColoredSlab("cyan_slab", SpectrumBlocks.CYAN_PLANKS, InkColors.CYAN);
	public static final BlockFamily CYAN_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.CYAN_PLANKS.get()).stairs(SpectrumBlocks.CYAN_STAIRS.get()).pressurePlate(SpectrumBlocks.CYAN_PRESSURE_PLATE.get()).fence(SpectrumBlocks.CYAN_FENCE.get()).fenceGate(SpectrumBlocks.CYAN_FENCE_GATE.get()).button(SpectrumBlocks.CYAN_BUTTON.get()).slab(SpectrumBlocks.CYAN_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> GRAY_PLANKS = registerColoredPlanks("gray_planks", InkColors.GRAY);
	public static final DeferredBlock<Block> GRAY_STAIRS = registerColoredStairs("gray_stairs", SpectrumBlocks.GRAY_PLANKS, InkColors.GRAY);
	public static final DeferredBlock<Block> GRAY_PRESSURE_PLATE = registerColoredPressurePlate("gray_pressure_plate", SpectrumBlocks.GRAY_PLANKS, InkColors.GRAY);
	public static final DeferredBlock<Block> GRAY_FENCE = registerColoredFence("gray_fence", SpectrumBlocks.GRAY_PLANKS, InkColors.GRAY);
	public static final DeferredBlock<Block> GRAY_FENCE_GATE = registerColoredFenceGate("gray_fence_gate", SpectrumBlocks.GRAY_PLANKS, InkColors.GRAY);
	public static final DeferredBlock<Block> GRAY_BUTTON = registerColoredButton("gray_button", SpectrumBlocks.GRAY_PLANKS, InkColors.GRAY);
	public static final DeferredBlock<Block> GRAY_SLAB = registerColoredSlab("gray_slab", SpectrumBlocks.GRAY_PLANKS, InkColors.GRAY);
	public static final BlockFamily GRAY_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.GRAY_PLANKS.get()).stairs(SpectrumBlocks.GRAY_STAIRS.get()).pressurePlate(SpectrumBlocks.GRAY_PRESSURE_PLATE.get()).fence(SpectrumBlocks.GRAY_FENCE.get()).fenceGate(SpectrumBlocks.GRAY_FENCE_GATE.get()).button(SpectrumBlocks.GRAY_BUTTON.get()).slab(SpectrumBlocks.GRAY_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> GREEN_PLANKS = registerColoredPlanks("green_planks", InkColors.GREEN);
	public static final DeferredBlock<Block> GREEN_STAIRS = registerColoredStairs("green_stairs", SpectrumBlocks.GREEN_PLANKS, InkColors.GREEN);
	public static final DeferredBlock<Block> GREEN_PRESSURE_PLATE = registerColoredPressurePlate("green_pressure_plate", SpectrumBlocks.GREEN_PLANKS, InkColors.GREEN);
	public static final DeferredBlock<Block> GREEN_FENCE = registerColoredFence("green_fence", SpectrumBlocks.GREEN_PLANKS, InkColors.GREEN);
	public static final DeferredBlock<Block> GREEN_FENCE_GATE = registerColoredFenceGate("green_fence_gate", SpectrumBlocks.GREEN_PLANKS, InkColors.GREEN);
	public static final DeferredBlock<Block> GREEN_BUTTON = registerColoredButton("green_button", SpectrumBlocks.GREEN_PLANKS, InkColors.GREEN);
	public static final DeferredBlock<Block> GREEN_SLAB = registerColoredSlab("green_slab", SpectrumBlocks.GREEN_PLANKS, InkColors.GREEN);
	public static final BlockFamily GREEN_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.GREEN_PLANKS.get()).stairs(SpectrumBlocks.GREEN_STAIRS.get()).pressurePlate(SpectrumBlocks.GREEN_PRESSURE_PLATE.get()).fence(SpectrumBlocks.GREEN_FENCE.get()).fenceGate(SpectrumBlocks.GREEN_FENCE_GATE.get()).button(SpectrumBlocks.GREEN_BUTTON.get()).slab(SpectrumBlocks.GREEN_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> LIGHT_BLUE_PLANKS = registerColoredPlanks("light_blue_planks", InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_BLUE_STAIRS = registerColoredStairs("light_blue_stairs", SpectrumBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_BLUE_PRESSURE_PLATE = registerColoredPressurePlate("light_blue_pressure_plate", SpectrumBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_BLUE_FENCE = registerColoredFence("light_blue_fence", SpectrumBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_BLUE_FENCE_GATE = registerColoredFenceGate("light_blue_fence_gate", SpectrumBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_BLUE_BUTTON = registerColoredButton("light_blue_button", SpectrumBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_BLUE_SLAB = registerColoredSlab("light_blue_slab", SpectrumBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
	public static final BlockFamily LIGHT_BLUE_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.LIGHT_BLUE_PLANKS.get()).stairs(SpectrumBlocks.LIGHT_BLUE_STAIRS.get()).pressurePlate(SpectrumBlocks.LIGHT_BLUE_PRESSURE_PLATE.get()).fence(SpectrumBlocks.LIGHT_BLUE_FENCE.get()).fenceGate(SpectrumBlocks.LIGHT_BLUE_FENCE_GATE.get()).button(SpectrumBlocks.LIGHT_BLUE_BUTTON.get()).slab(SpectrumBlocks.LIGHT_BLUE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> LIGHT_GRAY_PLANKS = registerColoredPlanks("light_gray_planks", InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIGHT_GRAY_STAIRS = registerColoredStairs("light_gray_stairs", SpectrumBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIGHT_GRAY_PRESSURE_PLATE = registerColoredPressurePlate("light_gray_pressure_plate", SpectrumBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIGHT_GRAY_FENCE = registerColoredFence("light_gray_fence", SpectrumBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIGHT_GRAY_FENCE_GATE = registerColoredFenceGate("light_gray_fence_gate", SpectrumBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIGHT_GRAY_BUTTON = registerColoredButton("light_gray_button", SpectrumBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIGHT_GRAY_SLAB = registerColoredSlab("light_gray_slab", SpectrumBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
	public static final BlockFamily LIGHT_GRAY_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.LIGHT_GRAY_PLANKS.get()).stairs(SpectrumBlocks.LIGHT_GRAY_STAIRS.get()).pressurePlate(SpectrumBlocks.LIGHT_GRAY_PRESSURE_PLATE.get()).fence(SpectrumBlocks.LIGHT_GRAY_FENCE.get()).fenceGate(SpectrumBlocks.LIGHT_GRAY_FENCE_GATE.get()).button(SpectrumBlocks.LIGHT_GRAY_BUTTON.get()).slab(SpectrumBlocks.LIGHT_GRAY_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> LIME_PLANKS = registerColoredPlanks("lime_planks", InkColors.LIME);
	public static final DeferredBlock<Block> LIME_STAIRS = registerColoredStairs("lime_stairs", SpectrumBlocks.LIME_PLANKS, InkColors.LIME);
	public static final DeferredBlock<Block> LIME_PRESSURE_PLATE = registerColoredPressurePlate("lime_pressure_plate", SpectrumBlocks.LIME_PLANKS, InkColors.LIME);
	public static final DeferredBlock<Block> LIME_FENCE = registerColoredFence("lime_fence", SpectrumBlocks.LIME_PLANKS, InkColors.LIME);
	public static final DeferredBlock<Block> LIME_FENCE_GATE = registerColoredFenceGate("lime_fence_gate", SpectrumBlocks.LIME_PLANKS, InkColors.LIME);
	public static final DeferredBlock<Block> LIME_BUTTON = registerColoredButton("lime_button", SpectrumBlocks.LIME_PLANKS, InkColors.LIME);
	public static final DeferredBlock<Block> LIME_SLAB = registerColoredSlab("lime_slab", SpectrumBlocks.LIME_PLANKS, InkColors.LIME);
	public static final BlockFamily LIME_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.LIME_PLANKS.get()).stairs(SpectrumBlocks.LIME_STAIRS.get()).pressurePlate(SpectrumBlocks.LIME_PRESSURE_PLATE.get()).fence(SpectrumBlocks.LIME_FENCE.get()).fenceGate(SpectrumBlocks.LIME_FENCE_GATE.get()).button(SpectrumBlocks.LIME_BUTTON.get()).slab(SpectrumBlocks.LIME_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> MAGENTA_PLANKS = registerColoredPlanks("magenta_planks", InkColors.MAGENTA);
	public static final DeferredBlock<Block> MAGENTA_STAIRS = registerColoredStairs("magenta_stairs", SpectrumBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
	public static final DeferredBlock<Block> MAGENTA_PRESSURE_PLATE = registerColoredPressurePlate("magenta_pressure_plate", SpectrumBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
	public static final DeferredBlock<Block> MAGENTA_FENCE = registerColoredFence("magenta_fence", SpectrumBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
	public static final DeferredBlock<Block> MAGENTA_FENCE_GATE = registerColoredFenceGate("magenta_fence_gate", SpectrumBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
	public static final DeferredBlock<Block> MAGENTA_BUTTON = registerColoredButton("magenta_button", SpectrumBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
	public static final DeferredBlock<Block> MAGENTA_SLAB = registerColoredSlab("magenta_slab", SpectrumBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
	public static final BlockFamily MAGENTA_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.MAGENTA_PLANKS.get()).stairs(SpectrumBlocks.MAGENTA_STAIRS.get()).pressurePlate(SpectrumBlocks.MAGENTA_PRESSURE_PLATE.get()).fence(SpectrumBlocks.MAGENTA_FENCE.get()).fenceGate(SpectrumBlocks.MAGENTA_FENCE_GATE.get()).button(SpectrumBlocks.MAGENTA_BUTTON.get()).slab(SpectrumBlocks.MAGENTA_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> ORANGE_PLANKS = registerColoredPlanks("orange_planks", InkColors.ORANGE);
	public static final DeferredBlock<Block> ORANGE_STAIRS = registerColoredStairs("orange_stairs", SpectrumBlocks.ORANGE_PLANKS, InkColors.ORANGE);
	public static final DeferredBlock<Block> ORANGE_PRESSURE_PLATE = registerColoredPressurePlate("orange_pressure_plate", SpectrumBlocks.ORANGE_PLANKS, InkColors.ORANGE);
	public static final DeferredBlock<Block> ORANGE_FENCE = registerColoredFence("orange_fence", SpectrumBlocks.ORANGE_PLANKS, InkColors.ORANGE);
	public static final DeferredBlock<Block> ORANGE_FENCE_GATE = registerColoredFenceGate("orange_fence_gate", SpectrumBlocks.ORANGE_PLANKS, InkColors.ORANGE);
	public static final DeferredBlock<Block> ORANGE_BUTTON = registerColoredButton("orange_button", SpectrumBlocks.ORANGE_PLANKS, InkColors.ORANGE);
	public static final DeferredBlock<Block> ORANGE_SLAB = registerColoredSlab("orange_slab", SpectrumBlocks.ORANGE_PLANKS, InkColors.ORANGE);
	public static final BlockFamily ORANGE_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.ORANGE_PLANKS.get()).stairs(SpectrumBlocks.ORANGE_STAIRS.get()).pressurePlate(SpectrumBlocks.ORANGE_PRESSURE_PLATE.get()).fence(SpectrumBlocks.ORANGE_FENCE.get()).fenceGate(SpectrumBlocks.ORANGE_FENCE_GATE.get()).button(SpectrumBlocks.ORANGE_BUTTON.get()).slab(SpectrumBlocks.ORANGE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> PINK_PLANKS = registerColoredPlanks("pink_planks", InkColors.PINK);
	public static final DeferredBlock<Block> PINK_STAIRS = registerColoredStairs("pink_stairs", SpectrumBlocks.PINK_PLANKS, InkColors.PINK);
	public static final DeferredBlock<Block> PINK_PRESSURE_PLATE = registerColoredPressurePlate("pink_pressure_plate", SpectrumBlocks.PINK_PLANKS, InkColors.PINK);
	public static final DeferredBlock<Block> PINK_FENCE = registerColoredFence("pink_fence", SpectrumBlocks.PINK_PLANKS, InkColors.PINK);
	public static final DeferredBlock<Block> PINK_FENCE_GATE = registerColoredFenceGate("pink_fence_gate", SpectrumBlocks.PINK_PLANKS, InkColors.PINK);
	public static final DeferredBlock<Block> PINK_BUTTON = registerColoredButton("pink_button", SpectrumBlocks.PINK_PLANKS, InkColors.PINK);
	public static final DeferredBlock<Block> PINK_SLAB = registerColoredSlab("pink_slab", SpectrumBlocks.PINK_PLANKS, InkColors.PINK);
	public static final BlockFamily PINK_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.PINK_PLANKS.get()).stairs(SpectrumBlocks.PINK_STAIRS.get()).pressurePlate(SpectrumBlocks.PINK_PRESSURE_PLATE.get()).fence(SpectrumBlocks.PINK_FENCE.get()).fenceGate(SpectrumBlocks.PINK_FENCE_GATE.get()).button(SpectrumBlocks.PINK_BUTTON.get()).slab(SpectrumBlocks.PINK_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> PURPLE_PLANKS = registerColoredPlanks("purple_planks", InkColors.PURPLE);
	public static final DeferredBlock<Block> PURPLE_STAIRS = registerColoredStairs("purple_stairs", SpectrumBlocks.PURPLE_PLANKS, InkColors.PURPLE);
	public static final DeferredBlock<Block> PURPLE_PRESSURE_PLATE = registerColoredPressurePlate("purple_pressure_plate", SpectrumBlocks.PURPLE_PLANKS, InkColors.PURPLE);
	public static final DeferredBlock<Block> PURPLE_FENCE = registerColoredFence("purple_fence", SpectrumBlocks.PURPLE_PLANKS, InkColors.PURPLE);
	public static final DeferredBlock<Block> PURPLE_FENCE_GATE = registerColoredFenceGate("purple_fence_gate", SpectrumBlocks.PURPLE_PLANKS, InkColors.PURPLE);
	public static final DeferredBlock<Block> PURPLE_BUTTON = registerColoredButton("purple_button", SpectrumBlocks.PURPLE_PLANKS, InkColors.PURPLE);
	public static final DeferredBlock<Block> PURPLE_SLAB = registerColoredSlab("purple_slab", SpectrumBlocks.PURPLE_PLANKS, InkColors.PURPLE);
	public static final BlockFamily PURPLE_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.PURPLE_PLANKS.get()).stairs(SpectrumBlocks.PURPLE_STAIRS.get()).pressurePlate(SpectrumBlocks.PURPLE_PRESSURE_PLATE.get()).fence(SpectrumBlocks.PURPLE_FENCE.get()).fenceGate(SpectrumBlocks.PURPLE_FENCE_GATE.get()).button(SpectrumBlocks.PURPLE_BUTTON.get()).slab(SpectrumBlocks.PURPLE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> RED_PLANKS = registerColoredPlanks("red_planks", InkColors.RED);
	public static final DeferredBlock<Block> RED_STAIRS = registerColoredStairs("red_stairs", SpectrumBlocks.RED_PLANKS, InkColors.RED);
	public static final DeferredBlock<Block> RED_PRESSURE_PLATE = registerColoredPressurePlate("red_pressure_plate", SpectrumBlocks.RED_PLANKS, InkColors.RED);
	public static final DeferredBlock<Block> RED_FENCE = registerColoredFence("red_fence", SpectrumBlocks.RED_PLANKS, InkColors.RED);
	public static final DeferredBlock<Block> RED_FENCE_GATE = registerColoredFenceGate("red_fence_gate", SpectrumBlocks.RED_PLANKS, InkColors.RED);
	public static final DeferredBlock<Block> RED_BUTTON = registerColoredButton("red_button", SpectrumBlocks.RED_PLANKS, InkColors.RED);
	public static final DeferredBlock<Block> RED_SLAB = registerColoredSlab("red_slab", SpectrumBlocks.RED_PLANKS, InkColors.RED);
	public static final BlockFamily RED_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.RED_PLANKS.get()).stairs(SpectrumBlocks.RED_STAIRS.get()).pressurePlate(SpectrumBlocks.RED_PRESSURE_PLATE.get()).fence(SpectrumBlocks.RED_FENCE.get()).fenceGate(SpectrumBlocks.RED_FENCE_GATE.get()).button(SpectrumBlocks.RED_BUTTON.get()).slab(SpectrumBlocks.RED_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> WHITE_PLANKS = registerColoredPlanks("white_planks", InkColors.WHITE);
	public static final DeferredBlock<Block> WHITE_STAIRS = registerColoredStairs("white_stairs", SpectrumBlocks.WHITE_PLANKS, InkColors.WHITE);
	public static final DeferredBlock<Block> WHITE_PRESSURE_PLATE = registerColoredPressurePlate("white_pressure_plate", SpectrumBlocks.WHITE_PLANKS, InkColors.WHITE);
	public static final DeferredBlock<Block> WHITE_FENCE = registerColoredFence("white_fence", SpectrumBlocks.WHITE_PLANKS, InkColors.WHITE);
	public static final DeferredBlock<Block> WHITE_FENCE_GATE = registerColoredFenceGate("white_fence_gate", SpectrumBlocks.WHITE_PLANKS, InkColors.WHITE);
	public static final DeferredBlock<Block> WHITE_BUTTON = registerColoredButton("white_button", SpectrumBlocks.WHITE_PLANKS, InkColors.WHITE);
	public static final DeferredBlock<Block> WHITE_SLAB = registerColoredSlab("white_slab", SpectrumBlocks.WHITE_PLANKS, InkColors.WHITE);
	public static final BlockFamily WHITE_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.WHITE_PLANKS.get()).stairs(SpectrumBlocks.WHITE_STAIRS.get()).pressurePlate(SpectrumBlocks.WHITE_PRESSURE_PLATE.get()).fence(SpectrumBlocks.WHITE_FENCE.get()).fenceGate(SpectrumBlocks.WHITE_FENCE_GATE.get()).button(SpectrumBlocks.WHITE_BUTTON.get()).slab(SpectrumBlocks.WHITE_SLAB.get()).getFamily());

	public static final DeferredBlock<Block> YELLOW_PLANKS = registerColoredPlanks("yellow_planks", InkColors.YELLOW);
	public static final DeferredBlock<Block> YELLOW_STAIRS = registerColoredStairs("yellow_stairs", SpectrumBlocks.YELLOW_PLANKS, InkColors.YELLOW);
	public static final DeferredBlock<Block> YELLOW_PRESSURE_PLATE = registerColoredPressurePlate("yellow_pressure_plate", SpectrumBlocks.YELLOW_PLANKS, InkColors.YELLOW);
	public static final DeferredBlock<Block> YELLOW_FENCE = registerColoredFence("yellow_fence", SpectrumBlocks.YELLOW_PLANKS, InkColors.YELLOW);
	public static final DeferredBlock<Block> YELLOW_FENCE_GATE = registerColoredFenceGate("yellow_fence_gate", SpectrumBlocks.YELLOW_PLANKS, InkColors.YELLOW);
	public static final DeferredBlock<Block> YELLOW_BUTTON = registerColoredButton("yellow_button", SpectrumBlocks.YELLOW_PLANKS, InkColors.YELLOW);
	public static final DeferredBlock<Block> YELLOW_SLAB = registerColoredSlab("yellow_slab", SpectrumBlocks.YELLOW_PLANKS, InkColors.YELLOW);
	public static final BlockFamily YELLOW_COLORED_PLANKS_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.YELLOW_PLANKS.get()).stairs(SpectrumBlocks.YELLOW_STAIRS.get()).pressurePlate(SpectrumBlocks.YELLOW_PRESSURE_PLATE.get()).fence(SpectrumBlocks.YELLOW_FENCE.get()).fenceGate(SpectrumBlocks.YELLOW_FENCE_GATE.get()).button(SpectrumBlocks.YELLOW_BUTTON.get()).slab(SpectrumBlocks.YELLOW_SLAB.get()).getFamily());

	//DD FLORA
	public static Properties overgrownBlackslag(MapColor color, SoundType soundGroup) {
		return settings(color, soundGroup, SpectrumBlocks.BLACKSLAG_HARDNESS, SpectrumBlocks.BLACKSLAG_RESISTANCE).randomTicks();
	}

	public static final DeferredBlock<Block> SAWBLADE_GRASS = register(snowy(blockWithItem("sawblade_grass", () -> new BlackslagVegetationBlock(overgrownBlackslag(MapColor.SAND, SoundType.AZALEA_LEAVES)), InkColors.LIME), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top", b -> b, "_top"), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_snow_side", b -> b, "_snow_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top", b -> b, "_snow_top")));
	public static final DeferredBlock<Block> SHIMMEL = register(snowy(blockWithItem("shimmel", () -> new BlackslagVegetationBlock(overgrownBlackslag(MapColor.TERRACOTTA_GRAY, SoundType.WART_BLOCK)), InkColors.LIME), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top"), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_snow_side", b -> b, "_snow_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top")));
	public static final DeferredBlock<Block> OVERGROWN_BLACKSLAG = register(snowy(blockWithItem("overgrown_blackslag", () -> new BlackslagVegetationBlock(overgrownBlackslag(MapColor.PLANT, SoundType.VINE).speedFactor(0.925F)), InkColors.LIME), SpectrumTexturedModels.overgrown(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top", b -> b, "_fronds"), SpectrumTexturedModels.overgrown(b -> b, "_snow_side", b -> b, "_snow_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top", b -> b, "_snow_fronds")));
	public static final DeferredBlock<Block> FLAYED_EARTH = register(blockWithItem("flayed_earth", () -> new VariableHeightBlock(Properties.ofFullCopy(Blocks.MUD).mapColor(MapColor.STONE).sound(SoundType.HONEY_BLOCK).jumpFactor(0.9F).strength(5F, 15F), 14), InkColors.GRAY).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block, SpectrumModelHelper.createModelVariant(TexturedModel.CUBE.create(block, ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "_1").createWithSuffix(block, "_1", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "_2").createWithSuffix(block, "_2", ctx.modelOutput)))));
	//TODO: flayed earth should slow you down more and be shorter once Slake Paeisan is a thing again.

	public static final float ASH_STRENGTH = 2F;

	public static Properties ash(SoundType soundGroup) {
		return settings(MapColor.QUARTZ, soundGroup, SpectrumBlocks.ASH_STRENGTH, SpectrumBlocks.ASH_STRENGTH).requiresCorrectToolForDrops();
	}

	public static final DeferredBlock<Block> ASHEN_BLACKSLAG = register(singleton(blockWithItem("ashen_blackslag", () -> new BlackslagBlock(blackslag(SoundType.DEEPSLATE).mapColor(MapColor.QUARTZ)), InkColors.LIGHT_GRAY), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.BLACKSLAG.get(), "_top", b -> b, "_top")));
	public static final DeferredBlock<Block> ASH = register(blockWithItem("ash", () -> new AshBlock(ash(SoundType.POWDER_SNOW)), InkColors.GRAY).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "").createWithSuffix(block, "", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "2").createWithSuffix(block, "2", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "3").createWithSuffix(block, "3", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "4").createWithSuffix(block, "4", ctx.modelOutput)))));
	public static final DeferredBlock<Block> ASH_PILE = register(blockWithItem("ash_pile", () -> new AshPileBlock(ash(SoundType.POWDER_SNOW).replaceable().isViewBlocking((state, world, pos) -> state.getValue(SnowLayerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)), InkColors.LIGHT_GRAY).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_height2")).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.LAYERS).generateList(height -> {
		ResourceLocation ash = TextureMapping.getBlockTexture(SpectrumBlocks.ASH.get());
		ResourceLocation ash2 = TextureMapping.getBlockTexture(SpectrumBlocks.ASH.get(), "2");
		ResourceLocation ash3 = TextureMapping.getBlockTexture(SpectrumBlocks.ASH.get(), "3");
		ResourceLocation ash4 = TextureMapping.getBlockTexture(SpectrumBlocks.ASH.get(), "4");
		if (height == 8) return List.of(SpectrumModelHelper.createModelVariant(ash), SpectrumModelHelper.createModelVariant(ash2), SpectrumModelHelper.createModelVariant(ash3), SpectrumModelHelper.createModelVariant(ash4));
		ModelTemplate layerModel = new ModelTemplate(Optional.of(ModelLocationUtils.getModelLocation(SNOW, "_height" + height * 2)), Optional.empty(), TextureSlot.PARTICLE, TextureSlot.TEXTURE);
		return List.of(
				SpectrumModelHelper.createModelVariant(layerModel.create(SpectrumCommon.locate("block/ash_pile_height" + height * 2), TextureMapping.cube(ash), ctx.modelOutput)),
				SpectrumModelHelper.createModelVariant(layerModel.create(SpectrumCommon.locate("block/ash2_pile_height" + height * 2), TextureMapping.cube(ash2), ctx.modelOutput)),
				SpectrumModelHelper.createModelVariant(layerModel.create(SpectrumCommon.locate("block/ash3_pile_height" + height * 2), TextureMapping.cube(ash3), ctx.modelOutput)),
				SpectrumModelHelper.createModelVariant(layerModel.create(SpectrumCommon.locate("block/ash4_pile_height" + height * 2), TextureMapping.cube(ash4), ctx.modelOutput))
		);
	}))));

	public static final DeferredBlock<Block> VARIA_SPROUT = register(cutout(blockWithItem("varia_sprout", () -> new AshFloraBlock(settings(MapColor.SNOW, SoundType.STEM, 0F).instabreak().lightLevel(state -> 11).offsetType(OffsetType.XZ).dynamicShape().noCollission().hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always)), InkColors.WHITE)).withBlockItemModel(SpectrumModelHelper::registerBlockTexturedItemModel).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block,
			SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "").createWithSuffix(block, "", ctx.modelOutput)),
			SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_2").createWithSuffix(block, "_2", ctx.modelOutput)),
			SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_3").createWithSuffix(block, "_3", ctx.modelOutput)),
			SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_4").createWithSuffix(block, "_4", ctx.modelOutput)),
			SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_5").createWithSuffix(block, "_5", ctx.modelOutput)),
			SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_6").createWithSuffix(block, "_6", ctx.modelOutput)))));

	public static final ToIntFunction<BlockState> LANTERN_LIGHT_PROVIDER = (state -> state.getValue(RedstoneLampBlock.LIT) ? 15 : 0);

	public static DeferredBlock<Block> registerNoxshroom(String name, ResourceKey<ConfiguredFeature<?, ?>> feature, MapColor mapColor) {
		return register(cutout(blockWithItem(name, () -> new FungusBlock(feature, SpectrumBlocks.SHIMMEL.get(), settings(mapColor, SoundType.FUNGUS, 0.0F).noCollission()), InkColors.LIME)).withBlockItemModel(SpectrumModelHelper::registerBlockTexturedItemModel).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_type_1").createWithSuffix(block, "_type_1", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_type_2").createWithSuffix(block, "_type_2", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_type_3").createWithSuffix(block, "_type_3", ctx.modelOutput)))));
	}

	public static final DeferredBlock<Block> SLATE_NOXSHROOM = registerNoxshroom("slate_noxshroom", SpectrumConfiguredFeatures.SLATE_NOXFUNGUS, MapColor.COLOR_GRAY);
	public static final DeferredBlock<Block> EBONY_NOXSHROOM = registerNoxshroom("ebony_noxshroom", SpectrumConfiguredFeatures.EBONY_NOXFUNGUS, MapColor.TERRACOTTA_BLACK);
	public static final DeferredBlock<Block> IVORY_NOXSHROOM = registerNoxshroom("ivory_noxshroom", SpectrumConfiguredFeatures.IVORY_NOXFUNGUS, MapColor.QUARTZ);
	public static final DeferredBlock<Block> CHESTNUT_NOXSHROOM = registerNoxshroom("chestnut_noxshroom", SpectrumConfiguredFeatures.CHESTNUT_NOXFUNGUS, MapColor.CRIMSON_NYLIUM);

	public static final DeferredBlock<Block> POTTED_SLATE_NOXSHROOM = register(pottedPlant(block("potted_slate_noxshroom", () -> new FlowerPotBlock(SpectrumBlocks.SLATE_NOXSHROOM.get(), pottedPlant())), false));
	public static final DeferredBlock<Block> POTTED_EBONY_NOXSHROOM = register(pottedPlant(block("potted_ebony_noxshroom", () -> new FlowerPotBlock(SpectrumBlocks.EBONY_NOXSHROOM.get(), pottedPlant())), false));
	public static final DeferredBlock<Block> POTTED_IVORY_NOXSHROOM = register(pottedPlant(block("potted_ivory_noxshroom", () -> new FlowerPotBlock(SpectrumBlocks.IVORY_NOXSHROOM.get(), pottedPlant())), false));
	public static final DeferredBlock<Block> POTTED_CHESTNUT_NOXSHROOM = register(pottedPlant(block("potted_chestnut_noxshroom", () -> new FlowerPotBlock(SpectrumBlocks.CHESTNUT_NOXSHROOM.get(), pottedPlant())), false));

	public static Properties noxcap(MapColor color) {
		return settings(color, SoundType.STEM, 4.0F).instrument(NoteBlockInstrument.BASS);
	}

	public static DeferredBlock<Block>  registerNoxwoodLightBlock(String name, DeferredBlock<Block> gillsBlock, MapColor color) {
		return register(axisRotated(blockWithItem(name, () -> new RotatedPillarBlock(noxcap(color).lightLevel(state -> 15)), InkColors.LIME), TexturedModel.createDefault(b -> SpectrumTextureMaps.sideTopInside(b, "", b, "_top", gillsBlock.get(), ""), SpectrumModels.MULTILAYER_LIGHT)));
	}

	public static <T extends FlexLanternBlock> DeferredBlock<Block> registerNoxwoodLantern(String name, Supplier<T> flexLanternBlock, InkColor color) {
		return register(cutout(blockWithItem(name, flexLanternBlock, color)).withItemModel((ctx, item) -> SpectrumModelHelper.registerItemModel(ctx, item, "_item")).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.HANGING, DiagonalBlock.DIAGONAL, FlexLanternBlock.TALL).generate((hanging, diagonal, tall) -> {
			String suffix = (hanging ? "_hanging" : "") + (diagonal ? "_diagonal" : "") + (tall ? "_tall" : "_small");
			return SpectrumModelHelper.createModelVariant(SpectrumModels.noxwoodLantern(suffix).createWithSuffix(block, suffix, TextureMapping.cube(block), ctx.modelOutput));
		}))));
	}

	private static final int NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS = 30;

	public static final DeferredBlock<Block>  STRIPPED_SLATE_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_slate_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> STRIPPED_SLATE_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_slate_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block>  SLATE_NOXCAP_STEM = register(axisRotated(blockWithItem("slate_noxcap_stem", () -> new StrippingLootPillarBlock(noxcap(MapColor.COLOR_GRAY), SpectrumBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(), SpectrumLootTables.SLATE_NOXCAP_STRIPPING), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> SLATE_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("slate_noxcap_hyphae", () -> new StrippingLootPillarBlock(noxcap(MapColor.COLOR_GRAY), SpectrumBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE.get(), SpectrumLootTables.SLATE_NOXCAP_STRIPPING), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.SLATE_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.SLATE_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block> SLATE_NOXCAP_BLOCK = register(singleton(blockWithItem("slate_noxcap_block", () -> new Block(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block>  SLATE_NOXCAP_GILLS = register(axisRotated(blockWithItem("slate_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9).emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always)), InkColors.LIME), TexturedModel.COLUMN_ALT));

	public static final DeferredBlock<Block> SLATE_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("slate_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_LAMP = register(redstoneLamp(blockWithItem("slate_noxwood_lamp", () -> new RedstoneLampBlock(noxcap(MapColor.COLOR_GRAY).lightLevel(SpectrumBlocks.LANTERN_LIGHT_PROVIDER)), InkColors.LIME)));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_LIGHT = registerNoxwoodLightBlock("slate_noxwood_light", SpectrumBlocks.SLATE_NOXCAP_GILLS, MapColor.COLOR_GRAY);
	public static final DeferredBlock<Block> SLATE_NOXWOOD_AMPHORA = register(barrellike(blockWithItem("slate_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME), b -> SpectrumBlocks.SLATE_NOXWOOD_LIGHT.get(), "_top"));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_LANTERN = registerNoxwoodLantern("slate_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN).lightLevel(s -> 13).pushReaction(PushReaction.DESTROY)), InkColors.LIME);

	public static final DeferredBlock<Block> SLATE_NOXWOOD_PLANKS = register(blockWithItem("slate_noxwood_planks", () -> new Block(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_STAIRS = register(blockWithItem("slate_noxwood_stairs", () -> new StairBlock(SpectrumBlocks.SLATE_NOXWOOD_PLANKS.get().defaultBlockState(), noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_SLAB = register(blockWithItem("slate_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_FENCE = register(blockWithItem("slate_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_FENCE_GATE = register(blockWithItem("slate_noxwood_fence_gate", () -> new FenceGateBlock(SpectrumWoodTypes.SLATE_NOXWOOD, noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_DOOR = register(cutout(blockWithItem("slate_noxwood_door", () -> new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.COLOR_GRAY)), InkColors.LIME)));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("slate_noxwood_trapdoor", () -> new TrapDoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.COLOR_GRAY)), InkColors.LIME)));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_BUTTON = register(blockWithItem("slate_noxwood_button", () -> new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, SpectrumBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.COLOR_GRAY).pushReaction(PushReaction.DESTROY)), InkColors.LIME));
	public static final DeferredBlock<Block> SLATE_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("slate_noxwood_pressure_plate", () -> new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
	public static final BlockFamily SLATE_NOXWOOD_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.SLATE_NOXWOOD_PLANKS.get()).stairs(SpectrumBlocks.SLATE_NOXWOOD_STAIRS.get()).slab(SpectrumBlocks.SLATE_NOXWOOD_SLAB.get()).fence(SpectrumBlocks.SLATE_NOXWOOD_FENCE.get()).fenceGate(SpectrumBlocks.SLATE_NOXWOOD_FENCE_GATE.get()).door(SpectrumBlocks.SLATE_NOXWOOD_DOOR.get()).trapdoor(SpectrumBlocks.SLATE_NOXWOOD_TRAPDOOR.get()).button(SpectrumBlocks.SLATE_NOXWOOD_BUTTON.get()).pressurePlate(SpectrumBlocks.SLATE_NOXWOOD_PRESSURE_PLATE.get()).getFamily());

	public static final DeferredBlock<Block> STRIPPED_EBONY_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_ebony_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> STRIPPED_EBONY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_ebony_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block> EBONY_NOXCAP_STEM = register(axisRotated(blockWithItem("ebony_noxcap_stem", () -> new StrippingLootPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK), SpectrumBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(), SpectrumLootTables.EBONY_NOXCAP_STRIPPING), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> EBONY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("ebony_noxcap_hyphae", () -> new StrippingLootPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK), SpectrumBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE.get(), SpectrumLootTables.EBONY_NOXCAP_STRIPPING), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.EBONY_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.EBONY_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block> EBONY_NOXCAP_BLOCK = register(singleton(blockWithItem("ebony_noxcap_block", () -> new Block(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> EBONY_NOXCAP_GILLS = register(axisRotated(blockWithItem("ebony_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9).emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always)), InkColors.LIME), TexturedModel.COLUMN_ALT));

	public static final DeferredBlock<Block> EBONY_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("ebony_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_LAMP = register(redstoneLamp(blockWithItem("ebony_noxwood_lamp", () -> new RedstoneLampBlock(noxcap(MapColor.TERRACOTTA_BLACK).lightLevel(SpectrumBlocks.LANTERN_LIGHT_PROVIDER)), InkColors.LIME)));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_LIGHT = registerNoxwoodLightBlock("ebony_noxwood_light", SpectrumBlocks.EBONY_NOXCAP_GILLS, MapColor.TERRACOTTA_BLACK);
	public static final DeferredBlock<Block> EBONY_NOXWOOD_AMPHORA = register(barrellike(blockWithItem("ebony_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME), b -> SpectrumBlocks.EBONY_NOXWOOD_LIGHT.get(), "_top"));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_LANTERN = registerNoxwoodLantern("ebony_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN).lightLevel(s -> 13).pushReaction(PushReaction.DESTROY)), InkColors.LIME);

	public static final DeferredBlock<Block> EBONY_NOXWOOD_PLANKS = register(blockWithItem("ebony_noxwood_planks", () -> new Block(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_STAIRS = register(blockWithItem("ebony_noxwood_stairs", () -> new StairBlock(SpectrumBlocks.EBONY_NOXWOOD_PLANKS.get().defaultBlockState(), noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_SLAB = register(blockWithItem("ebony_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_FENCE = register(blockWithItem("ebony_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_FENCE_GATE = register(blockWithItem("ebony_noxwood_fence_gate", () -> new FenceGateBlock(SpectrumWoodTypes.EBONY_NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_DOOR = register(cutout(blockWithItem("ebony_noxwood_door", () -> new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME)));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("ebony_noxwood_trapdoor", () -> new TrapDoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME)));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_BUTTON = register(blockWithItem("ebony_noxwood_button", () -> new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, SpectrumBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.TERRACOTTA_BLACK).pushReaction(PushReaction.DESTROY)), InkColors.LIME));
	public static final DeferredBlock<Block> EBONY_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("ebony_noxwood_pressure_plate", () -> new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
	public static final BlockFamily EBONY_NOXWOOD_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.EBONY_NOXWOOD_PLANKS.get()).stairs(SpectrumBlocks.EBONY_NOXWOOD_STAIRS.get()).slab(SpectrumBlocks.EBONY_NOXWOOD_SLAB.get()).fence(SpectrumBlocks.EBONY_NOXWOOD_FENCE.get()).fenceGate(SpectrumBlocks.EBONY_NOXWOOD_FENCE_GATE.get()).door(SpectrumBlocks.EBONY_NOXWOOD_DOOR.get()).trapdoor(SpectrumBlocks.EBONY_NOXWOOD_TRAPDOOR.get()).button(SpectrumBlocks.EBONY_NOXWOOD_BUTTON.get()).pressurePlate(SpectrumBlocks.EBONY_NOXWOOD_PRESSURE_PLATE.get()).getFamily());

	public static final DeferredBlock<Block>  STRIPPED_IVORY_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_ivory_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> STRIPPED_IVORY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_ivory_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block>  IVORY_NOXCAP_STEM = register(axisRotated(blockWithItem("ivory_noxcap_stem", () -> new StrippingLootPillarBlock(noxcap(MapColor.QUARTZ), SpectrumBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(), SpectrumLootTables.IVORY_NOXCAP_STRIPPING), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> IVORY_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("ivory_noxcap_hyphae", () -> new StrippingLootPillarBlock(noxcap(MapColor.QUARTZ), SpectrumBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE.get(), SpectrumLootTables.IVORY_NOXCAP_STRIPPING), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.IVORY_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.IVORY_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block> IVORY_NOXCAP_BLOCK = register(singleton(blockWithItem("ivory_noxcap_block", () -> new Block(noxcap(MapColor.QUARTZ)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block>  IVORY_NOXCAP_GILLS = register(axisRotated(blockWithItem("ivory_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9).emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always)), InkColors.LIME), TexturedModel.COLUMN_ALT));

	public static final DeferredBlock<Block> IVORY_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("ivory_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_LAMP = register(redstoneLamp(blockWithItem("ivory_noxwood_lamp", () -> new RedstoneLampBlock(noxcap(MapColor.QUARTZ).lightLevel(SpectrumBlocks.LANTERN_LIGHT_PROVIDER)), InkColors.LIME)));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_LIGHT = registerNoxwoodLightBlock("ivory_noxwood_light", SpectrumBlocks.IVORY_NOXCAP_GILLS, MapColor.QUARTZ);
	public static final DeferredBlock<Block> IVORY_NOXWOOD_AMPHORA = register(barrellike(blockWithItem("ivory_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME), b -> SpectrumBlocks.IVORY_NOXWOOD_LIGHT.get(), "_top"));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_LANTERN = registerNoxwoodLantern("ivory_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN).lightLevel(s -> 13).pushReaction(PushReaction.DESTROY)), InkColors.LIME);

	public static final DeferredBlock<Block> IVORY_NOXWOOD_PLANKS = register(blockWithItem("ivory_noxwood_planks", () -> new Block(noxcap(MapColor.QUARTZ)), InkColors.LIME));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_STAIRS = register(blockWithItem("ivory_noxwood_stairs", () -> new StairBlock(SpectrumBlocks.IVORY_NOXWOOD_PLANKS.get().defaultBlockState(), noxcap(MapColor.QUARTZ)), InkColors.LIME));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_SLAB = register(blockWithItem("ivory_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_FENCE = register(blockWithItem("ivory_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_FENCE_GATE = register(blockWithItem("ivory_noxwood_fence_gate", () -> new FenceGateBlock(SpectrumWoodTypes.CHESTNUT_NOXWOOD, noxcap(MapColor.QUARTZ)), InkColors.LIME));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_DOOR = register(cutout(blockWithItem("ivory_noxwood_door", () -> new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.QUARTZ)), InkColors.LIME)));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("ivory_noxwood_trapdoor", () -> new TrapDoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.QUARTZ)), InkColors.LIME)));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_BUTTON = register(blockWithItem("ivory_noxwood_button", () -> new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, SpectrumBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.QUARTZ).pushReaction(PushReaction.DESTROY)), InkColors.LIME));
	public static final DeferredBlock<Block> IVORY_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("ivory_noxwood_pressure_plate", () -> new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.QUARTZ)), InkColors.LIME));
	public static final BlockFamily IVORY_NOXWOOD_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.IVORY_NOXWOOD_PLANKS.get()).stairs(SpectrumBlocks.IVORY_NOXWOOD_STAIRS.get()).slab(SpectrumBlocks.IVORY_NOXWOOD_SLAB.get()).fence(SpectrumBlocks.IVORY_NOXWOOD_FENCE.get()).fenceGate(SpectrumBlocks.IVORY_NOXWOOD_FENCE_GATE.get()).door(SpectrumBlocks.IVORY_NOXWOOD_DOOR.get()).trapdoor(SpectrumBlocks.IVORY_NOXWOOD_TRAPDOOR.get()).button(SpectrumBlocks.IVORY_NOXWOOD_BUTTON.get()).pressurePlate(SpectrumBlocks.IVORY_NOXWOOD_PRESSURE_PLATE.get()).getFamily());

	public static final DeferredBlock<Block>  STRIPPED_CHESTNUT_NOXCAP_STEM = register(axisRotated(blockWithItem("stripped_chestnut_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> STRIPPED_CHESTNUT_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("stripped_chestnut_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block>  CHESTNUT_NOXCAP_STEM = register(axisRotated(blockWithItem("chestnut_noxcap_stem", () -> new StrippingLootPillarBlock(noxcap(MapColor.CRIMSON_NYLIUM), SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(), SpectrumLootTables.CHESTNUT_NOXCAP_STRIPPING), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> CHESTNUT_NOXCAP_HYPHAE = register(axisRotated(blockWithItem("chestnut_noxcap_hyphae", () -> new StrippingLootPillarBlock(noxcap(MapColor.QUARTZ), SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE.get(), SpectrumLootTables.CHESTNUT_NOXCAP_STRIPPING), InkColors.LIME), SpectrumTexturedModels.cubeColumn(b -> SpectrumBlocks.CHESTNUT_NOXCAP_STEM.get(), "", b -> SpectrumBlocks.CHESTNUT_NOXCAP_STEM.get(), "")));
	public static final DeferredBlock<Block> CHESTNUT_NOXCAP_BLOCK = register(singleton(blockWithItem("chestnut_noxcap_block", () -> new Block(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block>  CHESTNUT_NOXCAP_GILLS = register(axisRotated(blockWithItem("chestnut_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9).emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always)), InkColors.LIME), TexturedModel.COLUMN_ALT));

	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_PILLAR = register(axisRotated(blockWithItem("chestnut_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_LAMP = register(redstoneLamp(blockWithItem("chestnut_noxwood_lamp", () -> new RedstoneLampBlock(noxcap(MapColor.CRIMSON_NYLIUM).lightLevel(SpectrumBlocks.LANTERN_LIGHT_PROVIDER)), InkColors.LIME)));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_LIGHT = registerNoxwoodLightBlock("chestnut_noxwood_light", SpectrumBlocks.CHESTNUT_NOXCAP_GILLS, MapColor.CRIMSON_NYLIUM);
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_AMPHORA = register(barrellike(blockWithItem("chestnut_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME), b -> SpectrumBlocks.CHESTNUT_NOXWOOD_LIGHT.get(), "_top"));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_LANTERN = registerNoxwoodLantern("chestnut_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN).lightLevel(s -> 13).pushReaction(PushReaction.DESTROY)), InkColors.LIME);

	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_PLANKS = register(blockWithItem("chestnut_noxwood_planks", () -> new Block(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_STAIRS = register(blockWithItem("chestnut_noxwood_stairs", () -> new StairBlock(SpectrumBlocks.CHESTNUT_NOXWOOD_PLANKS.get().defaultBlockState(), noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_SLAB = register(blockWithItem("chestnut_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_FENCE = register(blockWithItem("chestnut_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_FENCE_GATE = register(blockWithItem("chestnut_noxwood_fence_gate", () -> new FenceGateBlock(SpectrumWoodTypes.IVORY_NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_DOOR = register(cutout(blockWithItem("chestnut_noxwood_door", () -> new DoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME)));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_TRAPDOOR = register(cutout(blockWithItem("chestnut_noxwood_trapdoor", () -> new TrapDoorBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME)));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_BUTTON = register(blockWithItem("chestnut_noxwood_button", () -> new ButtonBlock(SpectrumBlockSetTypes.NOXWOOD, SpectrumBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS, noxcap(MapColor.CRIMSON_NYLIUM).pushReaction(PushReaction.DESTROY)), InkColors.LIME));
	public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_PRESSURE_PLATE = register(blockWithItem("chestnut_noxwood_pressure_plate", () -> new PressurePlateBlock(SpectrumBlockSetTypes.NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
	public static final BlockFamily CHESTNUT_NOXWOOD_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.CHESTNUT_NOXWOOD_PLANKS.get()).stairs(SpectrumBlocks.CHESTNUT_NOXWOOD_STAIRS.get()).slab(SpectrumBlocks.CHESTNUT_NOXWOOD_SLAB.get()).fence(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE.get()).fenceGate(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE_GATE.get()).door(SpectrumBlocks.CHESTNUT_NOXWOOD_DOOR.get()).trapdoor(SpectrumBlocks.CHESTNUT_NOXWOOD_TRAPDOOR.get()).button(SpectrumBlocks.CHESTNUT_NOXWOOD_BUTTON.get()).pressurePlate(SpectrumBlocks.CHESTNUT_NOXWOOD_PRESSURE_PLATE.get()).getFamily());

	public static Properties galaWood(MapColor color) {
		return settings(color, SoundType.CHERRY_WOOD, 30.0F).instrument(NoteBlockInstrument.BASS).ignitedByLava();
	}

	public static final DeferredBlock<Block> WEEPING_GALA_SPRIG = register(cross(blockWithItem("weeping_gala_sprig", () -> new WeepingGalaSprigBlock(copyWithMapColor(OAK_SAPLING, MapColor.WARPED_WART_BLOCK)), InkColors.LIME)).withItemModel(SpectrumModelHelper::registerItemModel));
	public static final DeferredBlock<Block> POTTED_WEEPING_GALA_SPRIG = register(pottedPlant(block("potted_weeping_gala_sprig", () -> new FlowerPotBlock(SpectrumBlocks.WEEPING_GALA_SPRIG.get(), pottedPlant())), false));

	public static final DeferredBlock<Block> WEEPING_GALA_LEAVES = register(singleton(blockWithItem("weeping_gala_leaves", () -> new LeavesBlock(copyWithMapColor(OAK_LEAVES, MapColor.WARPED_WART_BLOCK)), InkColors.LIME), TexturedModel.LEAVES));
	public static final DeferredBlock<Block> STRIPPED_WEEPING_GALA_LOG = register(burnable(log(blockWithItem("stripped_weeping_gala_log", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME)), 600));
	public static final DeferredBlock<Block> WEEPING_GALA_LOG = register(burnable(log(blockWithItem("weeping_gala_log", () -> new SpectrumLogBlock(galaWood(MapColor.COLOR_BROWN), SpectrumBlocks.STRIPPED_WEEPING_GALA_LOG.get()), InkColors.LIME)), 600));
	public static final DeferredBlock<Block> WEEPING_GALA_WOOD = register(burnable(wood(blockWithItem("weeping_gala_wood", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), SpectrumBlocks.WEEPING_GALA_LOG), 600));
	public static final DeferredBlock<Block> STRIPPED_WEEPING_GALA_WOOD = register(burnable(wood(blockWithItem("stripped_weeping_gala_wood", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), SpectrumBlocks.STRIPPED_WEEPING_GALA_LOG), 600));

	public static final DeferredBlock<Block> WEEPING_GALA_FRONDS = register(cross(block("weeping_gala_fronds", () -> new WeepingGalaFrondsBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.WEEPING_GALA_LEAVES.get()).noCollission()))));
	public static final DeferredBlock<Block> WEEPING_GALA_FRONDS_PLANT = register(cutout(block("weeping_gala_fronds_plant", () -> new WeepingGalaFrondsTipBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.WEEPING_GALA_LEAVES.get()).noCollission().lightLevel(s -> s.getValue(WeepingGalaFrondsTipBlock.FORM).getLuminance())))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(WeepingGalaFrondsTipBlock.FORM)
			.select(WeepingGalaFrondsTipBlock.Form.TIP, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> SpectrumBlocks.WEEPING_GALA_FRONDS.get(), "_tip").create(block, ctx.modelOutput)))
			.select(WeepingGalaFrondsTipBlock.Form.SPRIG, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> SpectrumBlocks.WEEPING_GALA_FRONDS.get(), "_sprig").createWithSuffix(block, "_sprig", ctx.modelOutput)))
			.select(WeepingGalaFrondsTipBlock.Form.RESIN, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> SpectrumBlocks.WEEPING_GALA_FRONDS.get(), "_sprig_resin").createWithSuffix(block, "_resin", ctx.modelOutput))))));

	public static final BlockSetType GALA_BLOCK_SET_TYPE = new BlockSetType("gala");
	public static final WoodType GALA_WOOD_TYPE = new WoodType("gala", SpectrumBlocks.GALA_BLOCK_SET_TYPE);

	public static final DeferredBlock<Block> WEEPING_GALA_PLANKS = register(burnable(blockWithItem("weeping_gala_planks", () -> new Block(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600));
	public static final DeferredBlock<Block> WEEPING_GALA_STAIRS = register(burnable(blockWithItem("weeping_gala_stairs", () -> new StairBlock(SpectrumBlocks.WEEPING_GALA_PLANKS.get().defaultBlockState(), galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600));
	public static final DeferredBlock<Block> WEEPING_GALA_SLAB = register(burnable(blockWithItem("weeping_gala_slab", () -> new SlabBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 300));
	public static final DeferredBlock<Block> WEEPING_GALA_FENCE = register(burnable(blockWithItem("weeping_gala_fence", () -> new FenceBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600));
	public static final DeferredBlock<Block> WEEPING_GALA_FENCE_GATE = register(burnable(blockWithItem("weeping_gala_fence_gate", () -> new FenceGateBlock(SpectrumBlocks.GALA_WOOD_TYPE, galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600));
	public static final DeferredBlock<Block> WEEPING_GALA_DOOR = register(burnable(blockWithItem("weeping_gala_door", () -> new DoorBlock(SpectrumBlocks.GALA_BLOCK_SET_TYPE, galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 400));
	public static final DeferredBlock<Block> WEEPING_GALA_TRAPDOOR = register(burnable(blockWithItem("weeping_gala_trapdoor", () -> new TrapDoorBlock(SpectrumBlocks.GALA_BLOCK_SET_TYPE, galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600));
	public static final DeferredBlock<Block> WEEPING_GALA_BUTTON = register(burnable(blockWithItem("weeping_gala_button", () -> woodenButton(SpectrumBlocks.GALA_BLOCK_SET_TYPE), InkColors.LIME), 200));
	public static final DeferredBlock<Block> WEEPING_GALA_PRESSURE_PLATE = register(burnable(blockWithItem("weeping_gala_pressure_plate", () -> new PressurePlateBlock(SpectrumBlocks.GALA_BLOCK_SET_TYPE, galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600));
	public static final BlockFamily WEEPING_GALA_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.WEEPING_GALA_PLANKS.get()).stairs(SpectrumBlocks.WEEPING_GALA_STAIRS.get()).slab(SpectrumBlocks.WEEPING_GALA_SLAB.get()).fence(SpectrumBlocks.WEEPING_GALA_FENCE.get()).fenceGate(SpectrumBlocks.WEEPING_GALA_FENCE_GATE.get()).door(SpectrumBlocks.WEEPING_GALA_DOOR.get()).trapdoor(SpectrumBlocks.WEEPING_GALA_TRAPDOOR.get()).button(SpectrumBlocks.WEEPING_GALA_BUTTON.get()).pressurePlate(SpectrumBlocks.WEEPING_GALA_PRESSURE_PLATE.get()).getFamily());

	public static final DeferredBlock<Block> WEEPING_GALA_PILLAR = register(axisRotated(blockWithItem("weeping_gala_pillar", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> WEEPING_GALA_BARREL = register(barrellike(blockWithItem("weeping_gala_barrel", () -> new BarrelBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), b -> b, "_bottom"));
	public static final DeferredBlock<Block> WEEPING_GALA_AMPHORA = register(barrellike(blockWithItem("weeping_gala_amphora", () -> new AmphoraBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), b -> b, "_bottom"));
	public static final DeferredBlock<Block> WEEPING_GALA_LANTERN = register(translucent(blockWithItem("weeping_gala_lantern", () -> new FlexLanternBlock(galaWood(MapColor.COLOR_BROWN).lightLevel(state -> 13).noOcclusion().pushReaction(PushReaction.DESTROY)), InkColors.LIME)).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.HANGING).select(false, net.minecraft.data.models.blockstates.Variant.variant()).select(true, net.minecraft.data.models.blockstates.Variant.variant().with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))).with(PropertyDispatch.properties(DiagonalBlock.DIAGONAL, FlexLanternBlock.TALL).generate((diagonal, tall) -> SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.baseTransLantern(diagonal, tall).createWithSuffix(block, (diagonal ? "_diagonal" : "") + (tall ? "_tall" : "_small"), ctx.modelOutput))))).withItemModel((ctx, item) -> SpectrumModelHelper.registerItemModel(ctx, item, "_item")));
	public static final DeferredBlock<Block> WEEPING_GALA_LAMP = register(redstoneLamp(blockWithItem("weeping_gala_lamp", () -> new RedstoneLampBlock(galaWood(MapColor.COLOR_BROWN).lightLevel(SpectrumBlocks.LANTERN_LIGHT_PROVIDER)), InkColors.LIME)));
	public static final DeferredBlock<Block> WEEPING_GALA_LIGHT = register(translucent(axisRotated(blockWithItem("weeping_gala_light", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN).lightLevel(state -> 15).noOcclusion()), InkColors.LIME), SpectrumTexturedModels.BASE_TRANS_LIGHT_CORE)));

	public static Properties basalMarble() {
		return settings(MapColor.COLOR_GRAY, SoundType.DRIPSTONE_BLOCK, 8.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops();
	}

	public static final DeferredBlock<Block> BASAL_MARBLE = register(axisRotated(blockWithItem("basal_marble", () -> new RotatedPillarBlock(basalMarble()), InkColors.BROWN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BASAL_MARBLE_STAIRS = register(blockWithItem("basal_marble_stairs", () -> new StairBlock(SpectrumBlocks.BASAL_MARBLE.get().defaultBlockState(), basalMarble()), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_SLAB = register(blockWithItem("basal_marble_slab", () -> new SlabBlock(basalMarble()), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_WALL = register(blockWithItem("basal_marble_wall", () -> new WallBlock(basalMarble()), InkColors.BROWN));
	public static final BlockFamily BASAL_MARBLE_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(SpectrumBlocks.BASAL_MARBLE.get()).stairs(SpectrumBlocks.BASAL_MARBLE_STAIRS.get()).slab(SpectrumBlocks.BASAL_MARBLE_SLAB.get()).wall(SpectrumBlocks.BASAL_MARBLE_WALL.get()).getFamily(), TexturedModel.CUBE);

	public static final DeferredBlock<Block> BASAL_MARBLE_PILLAR = register(axisRotated(blockWithItem("basal_marble_pillar", () -> new RotatedPillarBlock(basalMarble()), InkColors.BROWN), TexturedModel.COLUMN));

	public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE = register(defaultUpFacing(blockWithItem("polished_basal_marble", () -> new SpectrumFacingBlock(basalMarble()), InkColors.BROWN), TexturedModel.CUBE_TOP_BOTTOM));
	public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE_STAIRS = register(blockWithItem("polished_basal_marble_stairs", () -> new StairBlock(SpectrumBlocks.POLISHED_BASAL_MARBLE.get().defaultBlockState(), basalMarble()), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE_SLAB = register(blockWithItem("polished_basal_marble_slab", () -> new SlabBlock(basalMarble()), InkColors.BROWN));
	public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE_WALL = register(blockWithItem("polished_basal_marble_wall", () -> new WallBlock(basalMarble()), InkColors.BROWN));
	public static final BlockFamily POLISHED_BASAL_MARBLE_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(SpectrumBlocks.POLISHED_BASAL_MARBLE.get()).stairs(SpectrumBlocks.POLISHED_BASAL_MARBLE_STAIRS.get()).slab(SpectrumBlocks.POLISHED_BASAL_MARBLE_SLAB.get()).wall(SpectrumBlocks.POLISHED_BASAL_MARBLE_WALL.get()).getFamily(), TexturedModel.createDefault(b -> SpectrumTextureMaps.sideTopBottomWall(b, "_side", b, "_top", b, "_bottom", b, "_side"), ModelTemplates.CUBE_BOTTOM_TOP));

	public static final DeferredBlock<Block> BASAL_MARBLE_TILES = register(blockWithItem("basal_marble_tiles", () -> new Block(basalMarble()), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_TILE_STAIRS = register(blockWithItem("basal_marble_tile_stairs", () -> new StairBlock(SpectrumBlocks.BASAL_MARBLE_TILES.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.BASAL_MARBLE_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_TILE_SLAB = register(blockWithItem("basal_marble_tile_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.BASAL_MARBLE_TILES.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_TILE_WALL = register(blockWithItem("basal_marble_tile_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.BASAL_MARBLE_TILES.get())), InkColors.BROWN));
	public static final BlockFamily BASAL_MARBLE_TILE_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BASAL_MARBLE_TILES.get()).stairs(SpectrumBlocks.BASAL_MARBLE_TILE_STAIRS.get()).slab(SpectrumBlocks.BASAL_MARBLE_TILE_SLAB.get()).wall(SpectrumBlocks.BASAL_MARBLE_TILE_WALL.get()).getFamily());

	public static final DeferredBlock<Block> BASAL_MARBLE_BRICKS = register(blockWithItem("basal_marble_bricks", () -> new Block(basalMarble()), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_BRICK_STAIRS = register(blockWithItem("basal_marble_brick_stairs", () -> new StairBlock(SpectrumBlocks.BASAL_MARBLE_BRICKS.get().defaultBlockState(), Properties.ofFullCopy(SpectrumBlocks.BASAL_MARBLE_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_BRICK_SLAB = register(blockWithItem("basal_marble_brick_slab", () -> new SlabBlock(Properties.ofFullCopy(SpectrumBlocks.BASAL_MARBLE_BRICKS.get())), InkColors.BROWN));
	public static final DeferredBlock<Block> BASAL_MARBLE_BRICK_WALL = register(blockWithItem("basal_marble_brick_wall", () -> new WallBlock(Properties.ofFullCopy(SpectrumBlocks.BASAL_MARBLE_BRICKS.get())), InkColors.BROWN));
	public static final BlockFamily BASAL_MARBLE_BRICK_FAMILY = SpectrumModelHelper.registerBlockFamily(new BlockFamily.Builder(SpectrumBlocks.BASAL_MARBLE_BRICKS.get()).stairs(SpectrumBlocks.BASAL_MARBLE_BRICK_STAIRS.get()).slab(SpectrumBlocks.BASAL_MARBLE_BRICK_SLAB.get()).wall(SpectrumBlocks.BASAL_MARBLE_BRICK_WALL.get()).getFamily());

	public static final DeferredBlock<Block> LONGING_CHIMERA = register(cutout(defaultNorthHorizontalFacing(blockWithItem("longing_chimera", () -> new GrotesqueBlock(basalMarble().noOcclusion(), 12, 15, "block.pastel.longing_chimera.tooltip"), InkColors.BROWN), ModelLocationUtils::getModelLocation)));

	public static DeferredBlock<Block> registerSmallDragonjagBlock(String name, Dragonjag.Variant variant) {
		return register(cutout(singleton(blockWithItem(name, () -> new SmallDragonjagBlock(settings(variant.getMapColor(), SoundType.GRASS, 1.0F), variant), InkColors.LIME), SpectrumTexturedModels.doubleCross(b -> b, ""))).withBlockItemModel(SpectrumModelHelper::registerBlockTexturedItemModel));
	}

	public static final DeferredBlock<Block> SMALL_RED_DRAGONJAG = registerSmallDragonjagBlock("small_red_dragonjag", Dragonjag.Variant.RED);
	public static final DeferredBlock<Block> SMALL_YELLOW_DRAGONJAG = registerSmallDragonjagBlock("small_yellow_dragonjag", Dragonjag.Variant.YELLOW);
	public static final DeferredBlock<Block> SMALL_PINK_DRAGONJAG = registerSmallDragonjagBlock("small_pink_dragonjag", Dragonjag.Variant.PINK);
	public static final DeferredBlock<Block> SMALL_PURPLE_DRAGONJAG = registerSmallDragonjagBlock("small_purple_dragonjag", Dragonjag.Variant.PURPLE);
	public static final DeferredBlock<Block> SMALL_BLACK_DRAGONJAG = registerSmallDragonjagBlock("small_black_dragonjag", Dragonjag.Variant.BLACK);

	public static DeferredBlock<Block> registerTallDragonjagBlock(String name, Dragonjag.Variant variant) {
		return register(cutout(block(name, () -> new TallDragonjagBlock(settings(variant.getMapColor(), SoundType.GRASS, 1.0F), variant))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(DoublePlantBlock.HALF, TallDragonjagBlock.DEAD).generate((half, dead) -> {
			String suffix = (half == DoubleBlockHalf.UPPER ? "_top" : "_bottom") + (dead ? "_dead" : "");
			return SpectrumModelHelper.createModelVariant((half == DoubleBlockHalf.UPPER ? SpectrumTexturedModels.cross(b -> b, suffix) : SpectrumTexturedModels.doubleCross(b -> b, suffix)).createWithSuffix(block, suffix, ctx.modelOutput));
		}))));
	}

	public static final DeferredBlock<Block> TALL_YELLOW_DRAGONJAG = registerTallDragonjagBlock("tall_yellow_dragonjag", Dragonjag.Variant.YELLOW);
	public static final DeferredBlock<Block> TALL_RED_DRAGONJAG = registerTallDragonjagBlock("tall_red_dragonjag", Dragonjag.Variant.RED);
	public static final DeferredBlock<Block> TALL_PINK_DRAGONJAG = registerTallDragonjagBlock("tall_pink_dragonjag", Dragonjag.Variant.PINK);
	public static final DeferredBlock<Block> TALL_PURPLE_DRAGONJAG = registerTallDragonjagBlock("tall_purple_dragonjag", Dragonjag.Variant.PURPLE);
	public static final DeferredBlock<Block> TALL_BLACK_DRAGONJAG = registerTallDragonjagBlock("tall_black_dragonjag", Dragonjag.Variant.BLACK);

	//Flora
	public static final DeferredBlock<Block> ALOE = register(cutout(block("aloe", () -> new AloeBlock(settings(MapColor.PLANT, SoundType.GRASS, 1.0F).noCollission().randomTicks().noOcclusion()))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.AGE_4).generate(age -> SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, age.toString()).createWithSuffix(block, age.toString(), ctx.modelOutput))))));
	public static final DeferredBlock<Block> SAWBLADE_HOLLY_BUSH = register(cutout(block("sawblade_holly_bush", () -> new SawbladeHollyBushBlock(settings(MapColor.TERRACOTTA_GREEN, SoundType.GRASS, 0.0F).noCollission().randomTicks().noOcclusion().lightLevel(s -> s.getValue(SawbladeHollyBushBlock.AGE) == SawbladeHollyBushBlock.MAX_AGE ? 10 : 0)))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.AGE_7)
			.select(0, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "0").createWithSuffix(block, "_stage0", ctx.modelOutput)))
			.select(1, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "1").createWithSuffix(block, "_stage1", ctx.modelOutput)))
			.select(2, SpectrumModelHelper.createModelVariant(block, "_stage1"))
			.select(3, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "2").createWithSuffix(block, "_stage2", ctx.modelOutput)))
			.select(4, SpectrumModelHelper.createModelVariant(block, "_stage2")).select(5, SpectrumModelHelper.createModelVariant(block, "_stage2"))
			.select(6, SpectrumModelHelper.createModelVariant(block, "_stage2"))
			.select(7, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "3").createWithSuffix(block, "_stage3", ctx.modelOutput))))));
	public static final DeferredBlock<Block> BRISTLE_SPROUTS = register(cutout(blockWithItem("bristle_sprouts", () -> new BristleSproutsBlock(settings(MapColor.GRASS, SoundType.GRASS, 0.0F).noCollission().noOcclusion().offsetType(OffsetType.XZ).replaceable()), InkColors.LIME)).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerBlockTexturedItemModel(ctx, block, "_1")).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_1").createWithSuffix(block, "_1", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_2").createWithSuffix(block, "_2", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_3").createWithSuffix(block, "_3", ctx.modelOutput)), SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, "_4").createWithSuffix(block, "_4", ctx.modelOutput)))));
	public static final DeferredBlock<Block> DOOMBLOOM = register(cutout(block("doombloom", () -> new DoomBloomBlock(SpectrumStatusEffects.STIFFNESS, 8, settings(MapColor.GRASS, SoundType.GRASS, 0.0F).randomTicks().noCollission().lightLevel((state) -> state.getValue(DoomBloomBlock.AGE) * 2).noOcclusion()))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.AGE_4).generate(age -> SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, age.toString()).createWithSuffix(block, age.toString(), ctx.modelOutput))))));
	public static final DeferredBlock<Block> SNAPPING_IVY = register(cutout(blockWithItem("snapping_ivy", () -> new SnappingIvyBlock(settings(MapColor.GRASS, SoundType.GRASS, 3.0F).noCollission().noOcclusion()), InkColors.RED)).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(SnappingIvyBlock.SNAPPED, ModelLocationUtils.getModelLocation(block, "_snapped"), ModelLocationUtils.getModelLocation(block))).with(PropertyDispatch.property(BlockStateProperties.HORIZONTAL_AXIS).select(Direction.Axis.X, net.minecraft.data.models.blockstates.Variant.variant()).select(Direction.Axis.Z, net.minecraft.data.models.blockstates.Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)))));

	public static final DeferredBlock<Block> ABYSSAL_VINES = register(cutout(block("abyssal_vines", () -> new AbyssalVineBlock(settings(MapColor.PLANT, SoundType.CAVE_VINES, 2.0F).noCollission().offsetType(OffsetType.XYZ).randomTicks().noOcclusion().lightLevel(state -> state.getValue(BlockStateProperties.BERRIES) ? 13 : 0)))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(TriStateVineBlock.LIFE_STAGE, AbyssalVineBlock.BERRIES).generate((stage, berries) -> {
		String suffix = (stage == TriStateVineBlock.LifeStage.STALK ? "" : "_tip") + (berries ? "_fruiting" : "");
		if (stage == TriStateVineBlock.LifeStage.MATURE) return SpectrumModelHelper.createModelVariant(block, suffix);
		return SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, suffix).createWithSuffix(block, suffix, ctx.modelOutput));
	}))));
	public static final DeferredBlock<Block> NIGHTDEW = register(cutout(block("nightdew", () -> new NightdewBlock(settings(MapColor.WARPED_NYLIUM, SoundType.CAVE_VINES, 0.0F).noCollission().offsetType(OffsetType.XYZ).randomTicks().noOcclusion().instabreak()))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(TriStateVineBlock.LIFE_STAGE).generate(stage -> {
		String suffix = (stage == TriStateVineBlock.LifeStage.STALK ? "" : "_tip");
		if (stage == TriStateVineBlock.LifeStage.MATURE) return SpectrumModelHelper.createModelVariant(block, suffix);
		return SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, suffix).createWithSuffix(block, suffix, ctx.modelOutput));
	}))));
	public static final DeferredBlock<Block> SWEET_PEA = register(simplePlant(blockWithItem("sweet_pea", () -> new FlowerBlock(MobEffects.NIGHT_VISION, 5, settings(MapColor.COLOR_MAGENTA, SoundType.GRASS, 0.0F).offsetType(OffsetType.XZ).noCollission().noOcclusion().lightLevel(s -> 11).hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always)), InkColors.YELLOW)));
	public static final DeferredBlock<Block> APRICOTTI = register(simplePlant(blockWithItem("apricotti", () -> new FlowerBlock(MobEffects.GLOWING, 5, settings(MapColor.COLOR_ORANGE, SoundType.GRASS, 0.0F).offsetType(OffsetType.XZ).noCollission().noOcclusion().lightLevel(s -> 11).hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always)), InkColors.YELLOW)));
	public static final DeferredBlock<Block> HUMMING_BELL = register(simplePlant(blockWithItem("humming_bell", () -> new FlowerBlock(SpectrumStatusEffects.LIGHTWEIGHT, 5, settings(MapColor.COLOR_LIGHT_BLUE, SoundType.GRASS, 0.0F).offsetType(OffsetType.XZ).noCollission().noOcclusion().lightLevel(s -> 9).hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always)), InkColors.LIME)));

	public static final DeferredBlock<Block> HUMMINGSTONE_GLASS = register(translucent(simple(blockWithItem("hummingstone_glass", () -> new TransparentBlock(settings(MapColor.SAND, SoundType.GLASS, 5.0F, 100.0F).noOcclusion().requiresCorrectToolForDrops()), InkColors.LIGHT_BLUE))));
	public static final DeferredBlock<Block> HUMMINGSTONE_GLASS_PANE = register(glassPane(blockWithItem("hummingstone_glass_pane", () -> new IronBarsBlock(Properties.ofFullCopy(SpectrumBlocks.HUMMINGSTONE_GLASS.get())), InkColors.LIGHT_BLUE), SpectrumBlocks.HUMMINGSTONE_GLASS));
	public static final DeferredBlock<Block> HUMMINGSTONE = register(translucent(blockWithItem("hummingstone", () -> new HummingstoneBlock(Properties.ofFullCopy(SpectrumBlocks.HUMMINGSTONE_GLASS.get()).lightLevel((state) -> 14)), InkColors.LIGHT_BLUE)).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(HummingstoneBlock.HUMMING, SpectrumTexturedModels.cubeAll(b -> b, "_humming").createWithSuffix(block, "_humming", ctx.modelOutput), TexturedModel.CUBE.create(block, ctx.modelOutput)))));
	public static final DeferredBlock<Block> WAXED_HUMMINGSTONE = register(translucent(parented(blockWithItem("waxed_hummingstone", () -> new TransparentBlock(Properties.ofFullCopy(SpectrumBlocks.HUMMINGSTONE.get())), InkColors.LIGHT_BLUE), b -> SpectrumBlocks.HUMMINGSTONE.get())));

	public static final DeferredBlock<Block> MOSS_BALL = register(cutout(blockWithItem("moss_ball", () -> new MossBallBlock(settings(MapColor.PLANT, SoundType.WET_GRASS, 1F).noCollission().noOcclusion().offsetType(OffsetType.XYZ)), InkColors.GREEN)).withBlockModel((ctx, block) -> {
		List<Variant> variants = new ArrayList<>(SpectrumModelHelper.createHorizontalRotationVariantList(ModelLocationUtils.getModelLocation(block, "_tuft")));
		variants.add(SpectrumModelHelper.createModelVariant(block, "").with(VariantProperties.WEIGHT, 4));
		return MultiVariantGenerator.multiVariant(block, variants.toArray(Variant[]::new));
	}));
	public static final DeferredBlock<Block> GIANT_MOSS_BALL = register(blockWithItem("giant_moss_ball", () -> new GiantMossBallBlock(settings(MapColor.PLANT, SoundType.WET_GRASS, 10F).noCollission().noOcclusion().offsetType(OffsetType.XYZ)), InkColors.GREEN).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, ModelLocationUtils.getModelLocation(block))));

	public static final DeferredBlock<Block> RESPLENDENT_BLOCK = register(defaultUpFacing(blockWithItem("resplendent_block", () -> new CushionedFacingBlock(Properties.ofFullCopy(RED_WOOL)), IS.of(Rarity.UNCOMMON), InkColors.YELLOW), TexturedModel.CUBE_TOP_BOTTOM));
	public static final DeferredBlock<Block> RESPLENDENT_CUSHION = register(singleton(blockWithItem("resplendent_cushion", () -> new CushionBlock(Properties.ofFullCopy(SpectrumBlocks.RESPLENDENT_BLOCK.get()).noOcclusion().isValidSpawn(SpectrumBlocks::never)), IS.of(Rarity.UNCOMMON), InkColors.YELLOW), SpectrumTexturedModels.CUSHION));
	public static final DeferredBlock<Block> RESPLENDENT_CARPET = register(singleton(blockWithItem("resplendent_carpet", () -> new CushionedCarpetBlock(Properties.ofFullCopy(RED_CARPET)), IS.of(Rarity.UNCOMMON), InkColors.YELLOW), TexturedModel.CARPET));
	public static final DeferredBlock<Block> RESPLENDENT_BED = register(cutout(blockWithItem("resplendent_bed", () -> new SpectrumBedBlock(DyeColor.RED, Properties.ofFullCopy(RED_BED)), IS.of(1, Rarity.UNCOMMON), InkColors.YELLOW)).withPredefinedItemModel().withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createSouthDefaultHorizontalFacingVariantMap()).with(PropertyDispatch.property(BedBlock.PART).select(BedPart.HEAD, SpectrumModelHelper.createModelVariant(block, "_head")).select(BedPart.FOOT, SpectrumModelHelper.createModelVariant(block, "_foot")))));

	// JADE VINES
	public static Properties jadeVine() {
		return settings(MapColor.GRASS, SoundType.WOOL, 0.1F).noCollission().noOcclusion();
	}

	public static final DeferredBlock<Block> JADE_VINE_ROOTS = register(cutout(block("jade_vine_roots", () -> new JadeVineRootsBlock(jadeVine().randomTicks().lightLevel((state) -> state.getValue(JadeVineRootsBlock.DEAD) ? 0 : 4)))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(JadeVineBulbBlock.DEAD, SpectrumModels.JADE_VINE_ROOTS.createWithSuffix(block, "_dead", SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT_RIPE, SpectrumTextures.JADE_VINE_PLANT_RIPE_BREAKING), ctx.modelOutput), SpectrumModels.JADE_VINE_ROOTS.create(block, SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT, SpectrumTextures.JADE_VINE_PLANT_BREAKING), ctx.modelOutput)))));
	public static final DeferredBlock<Block> JADE_VINE_BULB = register(cutout(block("jade_vine_bulb", () -> new JadeVineBulbBlock(jadeVine().lightLevel((state) -> state.getValue(JadeVineBulbBlock.DEAD) ? 0 : 5)))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(JadeVineBulbBlock.DEAD, SpectrumModels.JADE_VINE_BULB.createWithSuffix(block, "_dead", SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT_RIPE_BULB, SpectrumTextures.JADE_VINE_PLANT_RIPE_BREAKING), ctx.modelOutput), SpectrumModels.JADE_VINE_BULB.create(block, SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT_BULB, SpectrumTextures.JADE_VINE_PLANT_BREAKING), ctx.modelOutput)))));
	public static final DeferredBlock<Block> JADE_VINES = register(cutout(block("jade_vines", () -> new JadeVinePlantBlock(jadeVine().lightLevel((state) -> state.getValue(JadeVinePlantBlock.AGE) == 0 ? 0 : 5)))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.AGE_7, JadeVinePlantBlock.PART).generate((age, part) -> {
		ModelTemplate model = SpectrumModels.jadeVines(part);
		String suffix = "_" + part.getSerializedName() + (age == 0 ? "_dead" : age <= 2 ? "_leaves" : age <= 6 ? "_petals" : "_bloom");
		if (age == 0) return SpectrumModelHelper.createModelVariant(model.createWithSuffix(block, suffix, SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT_RIPE, SpectrumTextures.JADE_VINE_PLANT_RIPE_BREAKING), ctx.modelOutput));
		if (age == 1) return SpectrumModelHelper.createModelVariant(model.createWithSuffix(block, suffix, SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT, SpectrumTextures.JADE_VINE_PLANT_BREAKING), ctx.modelOutput));
		if (age == 3) return SpectrumModelHelper.createModelVariant(model.createWithSuffix(block, suffix, SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT_PETALS, SpectrumTextures.JADE_VINE_PLANT_BREAKING), ctx.modelOutput));
		if (age == 7) return SpectrumModelHelper.createModelVariant(model.createWithSuffix(block, suffix, SpectrumTextureMaps.flowerParticle(SpectrumTextures.JADE_VINE_PLANT_BLOOMING, SpectrumTextures.JADE_VINE_PLANT_BREAKING), ctx.modelOutput));
		return SpectrumModelHelper.createModelVariant(block, suffix);
	}))));
	public static final DeferredBlock<Block> JADE_VINE_PETAL_BLOCK = register(cutout(simple(blockWithItem("jade_vine_petal_block", () -> new JadeVinePetalBlock(jadeVine().lightLevel(state -> 3)), InkColors.LIME))));
	public static final DeferredBlock<Block> JADE_VINE_PETAL_CARPET = register(cutout(singleton(blockWithItem("jade_vine_petal_carpet", () -> new CarpetBlock(jadeVine().lightLevel(state -> 3)), InkColors.LIME), SpectrumTexturedModels.carpet(b -> SpectrumBlocks.JADE_VINE_PETAL_BLOCK.get(), ""))));

	public static final DeferredBlock<Block> NEPHRITE_BLOSSOM_STEM = register(cutout(blockWithItem("nephrite_blossom_stem", () -> new NephriteBlossomStemBlock(settings(MapColor.COLOR_PINK, SoundType.WOOL, 2.0F).noOcclusion().noCollission()), InkColors.PINK)).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerBlockTexturedItemModel(ctx, block, "_bottom")).withBlockModel((ctx, block) -> {
		ResourceLocation bottom = SpectrumTexturedModels.cross(b -> b, "_bottom").createWithSuffix(block, "_bottom", ctx.modelOutput);
		ResourceLocation top = SpectrumTexturedModels.cross(b -> b, "_top").createWithSuffix(block, "_top", ctx.modelOutput);
		ResourceLocation fronds = ModelLocationUtils.getModelLocation(block, "_base");
		return MultiPartGenerator.multiPart(block)
				.with(Condition.condition().term(NephriteBlossomStemBlock.STEM_PART, StemComponent.STEM), SpectrumModelHelper.createModelVariant(bottom))
				.with(Condition.condition().term(NephriteBlossomStemBlock.STEM_PART, StemComponent.STEMALT), SpectrumModelHelper.createModelVariant(top))
				.with(Condition.condition().term(NephriteBlossomStemBlock.STEM_PART, StemComponent.BASE), SpectrumModelHelper.createModelVariant(fronds))
				.with(Condition.condition().term(NephriteBlossomStemBlock.STEM_PART, StemComponent.BASE), SpectrumModelHelper.createModelVariant(bottom));
	}));
	public static final DeferredBlock<Block> NEPHRITE_BLOSSOM_LEAVES = register(cutout(blockWithItem("nephrite_blossom_leaves", () -> new NephriteBlossomLeavesBlock(settings(MapColor.COLOR_PINK, SoundType.GRASS, 0.2F).noOcclusion().randomTicks().lightLevel(state -> 13)), InkColors.PINK)).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.AGE_2).generate(age -> {
		String suffix = age == 0 ? "" : age == 1 ? "_flowering" : "_fruiting";
		return SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.leaves(b -> b, suffix).createWithSuffix(block, suffix, ctx.modelOutput));
	}))));
	public static final DeferredBlock<Block> NEPHRITE_BLOSSOM_BULB = register(cross(blockWithItem("nephrite_blossom_bulb", () -> new NephriteBlossomBulbBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.NEPHRITE_BLOSSOM_STEM.get())), IS.of(16), InkColors.PINK)).withItemModel(SpectrumModelHelper::registerItemModel));

	public static Properties jadeite() {
		return settings(MapColor.WOOL, SoundType.WOOL, 0.1F).noCollission().noOcclusion().lightLevel(state -> 12).hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always);
	}

	public static final DeferredBlock<Block> JADEITE_LOTUS_STEM = register(cutout(blockWithItem("jadeite_lotus_stem", () -> new JadeiteLotusStemBlock(settings(MapColor.COLOR_BLACK, SoundType.WOOL, 2.0F).noOcclusion().noCollission()), InkColors.LIME)).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerBlockTexturedItemModel(ctx, block, "_top")).withBlockModel((ctx, block) -> {
		ResourceLocation bottom = SpectrumTexturedModels.cross(b -> b, "_bottom").createWithSuffix(block, "_bottom", ctx.modelOutput);
		ResourceLocation top = SpectrumTexturedModels.cross(b -> b, "_top").createWithSuffix(block, "_top", ctx.modelOutput);
		ResourceLocation base = ModelLocationUtils.getModelLocation(block, "_base");
		return MultiPartGenerator.multiPart(block).with(Condition.condition().term(JadeiteLotusStemBlock.STEM_PART, StemComponent.STEM), SpectrumModelHelper.createModelVariant(bottom)).with(Condition.condition().term(JadeiteLotusStemBlock.STEM_PART, StemComponent.STEMALT), SpectrumModelHelper.createModelVariant(top)).with(Condition.condition().term(JadeiteLotusStemBlock.STEM_PART, StemComponent.BASE), SpectrumModelHelper.createModelVariant(bottom)).with(Condition.condition().term(JadeiteLotusStemBlock.STEM_PART, StemComponent.BASE).term(BlockStateProperties.INVERTED, false), SpectrumModelHelper.createModelVariant(base)).with(Condition.condition().term(JadeiteLotusStemBlock.STEM_PART, StemComponent.BASE).term(BlockStateProperties.INVERTED, true), SpectrumModelHelper.createModelVariant(base).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180));
	}));
	public static final DeferredBlock<Block> JADEITE_LOTUS_FLOWER = register(cutout(defaultUpFacingGetter(blockWithItem("jadeite_lotus_flower", () -> new JadeiteLotusFlowerBlock(settings(MapColor.SNOW, SoundType.WOOL, 2.0F).lightLevel(state -> 14).hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always)), IS.of(16), InkColors.LIME), ModelLocationUtils::getModelLocation)));
	public static final DeferredBlock<Block> JADEITE_LOTUS_BULB = register(cross(blockWithItem("jadeite_lotus_bulb", () -> new JadeiteLotusBulbBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.JADEITE_LOTUS_STEM.get()).noOcclusion()), IS.of(16), InkColors.LIME)).withItemModel(SpectrumModelHelper::registerItemModel));
	public static final DeferredBlock<Block> JADEITE_PETAL_BLOCK = register(cutout(simple(blockWithItem("jadeite_petal_block", () -> new JadeVinePetalBlock(jadeite()), InkColors.LIME))));
	public static final DeferredBlock<Block> JADEITE_PETAL_CARPET = register(cutout(singleton(blockWithItem("jadeite_petal_carpet", () -> new CarpetBlock(jadeite()), InkColors.LIME), SpectrumTexturedModels.carpet(b -> SpectrumBlocks.JADEITE_PETAL_BLOCK.get(), ""))));

	private static Properties ore() {
		return BlockBehaviour.Properties.ofFullCopy(IRON_ORE);
	}

	private static Properties deepslateOre() {
		return BlockBehaviour.Properties.ofFullCopy(DEEPSLATE_IRON_ORE);
	}

	private static Properties blackslagOre() {
		return BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.BLACKSLAG.get()).strength(SpectrumBlocks.BLACKSLAG_HARDNESS * 1.5F, SpectrumBlocks.BLACKSLAG_RESISTANCE * 2F).requiresCorrectToolForDrops();
	}

	private static Properties netherrackOre() {
		return BlockBehaviour.Properties.ofFullCopy(NETHERRACK).strength(3.0F, 3.0F).sound(SoundType.NETHER_ORE).requiresCorrectToolForDrops();
	}

	private static Properties endstoneOre() {
		return BlockBehaviour.Properties.ofFullCopy(END_STONE).strength(3.0F, 3.0F).requiresCorrectToolForDrops();
	}

	public static final DeferredBlock<Block> SHIMMERSTONE_ORE = register(simple(blockWithItem("shimmerstone_ore", () -> new ShimmerstoneOreBlock(UniformInt.of(2, 4), ore().randomTicks(), SpectrumAdvancements.REVEAL_SHIMMERSTONE, STONE.defaultBlockState()), InkColors.YELLOW)));
	public static final DeferredBlock<Block> DEEPSLATE_SHIMMERSTONE_ORE = register(simple(blockWithItem("deepslate_shimmerstone_ore", () -> new ShimmerstoneOreBlock(UniformInt.of(2, 4), deepslateOre().randomTicks(), SpectrumAdvancements.REVEAL_SHIMMERSTONE, DEEPSLATE.defaultBlockState()), InkColors.YELLOW)));
	public static final DeferredBlock<Block> BLACKSLAG_SHIMMERSTONE_ORE = register(singleton(blockWithItem("blackslag_shimmerstone_ore", () -> new ShimmerstoneOreBlock(UniformInt.of(2, 4), blackslagOre().randomTicks(), SpectrumAdvancements.REVEAL_SHIMMERSTONE, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), InkColors.YELLOW), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> SHIMMERSTONE_BLOCK = register(simple(blockWithItem("shimmerstone_block", () -> new ShimmerstoneBlock(settings(MapColor.COLOR_YELLOW, SoundType.GLASS, 2.0F).lightLevel((state) -> 15)), InkColors.YELLOW)));

	public static final DeferredBlock<Block> AZURITE_ORE = register(simpleMirrored(blockWithItem("azurite_ore", () -> new AzuriteOreBlock(UniformInt.of(4, 7), ore().randomTicks(), SpectrumAdvancements.REVEAL_AZURITE, STONE.defaultBlockState()), InkColors.BLUE)));
	public static final DeferredBlock<Block> DEEPSLATE_AZURITE_ORE = register(simpleMirrored(blockWithItem("deepslate_azurite_ore", () -> new AzuriteOreBlock(UniformInt.of(4, 7), deepslateOre().randomTicks(), SpectrumAdvancements.REVEAL_AZURITE, DEEPSLATE.defaultBlockState()), InkColors.BLUE)));
	public static final DeferredBlock<Block> BLACKSLAG_AZURITE_ORE = register(simpleMirrored(blockWithItem("blackslag_azurite_ore", () -> new AzuriteOreBlock(UniformInt.of(4, 7), blackslagOre().randomTicks(), SpectrumAdvancements.REVEAL_AZURITE, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), InkColors.BLUE)));
	public static final DeferredBlock<Block> AZURITE_BLOCK = register(defaultUpFacing(blockWithItem("azurite_block", () -> new SpectrumFacingBlock(BlockBehaviour.Properties.ofFullCopy(LAPIS_BLOCK).mapColor(MapColor.COLOR_BLUE)), InkColors.BLUE), TexturedModel.CUBE_TOP_BOTTOM));
	public static final DeferredBlock<Block> AZURITE_CLUSTER = register(cluster(blockWithItem("azurite_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_BLUE, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 2), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_AZURITE_BUD = register(cluster(blockWithItem("large_azurite_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_BLUE, SpectrumBlockSoundGroups.LARGE_ONYX_BUD, 3), SpectrumClusterBlock.GrowthStage.LARGE), IS.of(Rarity.UNCOMMON), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_AZURITE_BUD = register(cluster(blockWithItem("small_azurite_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_BLUE, SpectrumBlockSoundGroups.ONYX_CLUSTER, 5), SpectrumClusterBlock.GrowthStage.SMALL), IS.of(Rarity.UNCOMMON), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));

	public static final DeferredBlock<Block> MALACHITE_ORE = register(simple(blockWithItem("malachite_ore", () -> new CloakedOreBlock(UniformInt.of(7, 11), ore(), SpectrumAdvancements.REVEAL_MALACHITE, STONE.defaultBlockState()), IS.of(Rarity.UNCOMMON), InkColors.GREEN)));
	public static final DeferredBlock<Block> DEEPSLATE_MALACHITE_ORE = register(simple(blockWithItem("deepslate_malachite_ore", () -> new CloakedOreBlock(UniformInt.of(7, 11), deepslateOre(), SpectrumAdvancements.REVEAL_MALACHITE, DEEPSLATE.defaultBlockState()), IS.of(Rarity.UNCOMMON), InkColors.GREEN)));
	public static final DeferredBlock<Block> BLACKSLAG_MALACHITE_ORE = register(singleton(blockWithItem("blackslag_malachite_ore", () -> new CloakedOreBlock(UniformInt.of(7, 11), blackslagOre(), SpectrumAdvancements.REVEAL_MALACHITE, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), IS.of(Rarity.UNCOMMON), InkColors.GREEN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> MALACHITE_BLOCK = register(defaultUpFacing(blockWithItem("malachite_block", () -> new SpectrumFacingBlock(gemstoneBlock(MapColor.EMERALD, SoundType.CHAIN)), IS.of(Rarity.UNCOMMON), InkColors.GREEN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> MALACHITE_CLUSTER = register(cluster(blockWithItem("malachite_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.EMERALD, SoundType.CHAIN, 9), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_MALACHITE_BUD = register(cluster(blockWithItem("large_malachite_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.EMERALD, SoundType.CHAIN, 7), SpectrumClusterBlock.GrowthStage.LARGE), IS.of(Rarity.UNCOMMON), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_MALACHITE_BUD = register(cluster(blockWithItem("small_malachite_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.EMERALD, SoundType.CHAIN, 5), SpectrumClusterBlock.GrowthStage.SMALL), IS.of(Rarity.UNCOMMON), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));

	public static final DeferredBlock<Block> BLOODSTONE_BLOCK = register(defaultUpFacing(blockWithItem("bloodstone_block", () -> new SpectrumFacingBlock(gemstoneBlock(MapColor.COLOR_RED, SpectrumBlockSoundGroups.ONYX_CLUSTER)), IS.of(Rarity.UNCOMMON), InkColors.RED), TexturedModel.COLUMN));
	public static final DeferredBlock<Block> BLOODSTONE_CLUSTER = register(cluster(blockWithItem("bloodstone_cluster", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_RED, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 6), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of(Rarity.UNCOMMON), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_BLOODSTONE_BUD = register(cluster(blockWithItem("large_bloodstone_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_RED, SpectrumBlockSoundGroups.SMALL_ONYX_BUD, 4), SpectrumClusterBlock.GrowthStage.LARGE), IS.of(Rarity.UNCOMMON), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_BLOODSTONE_BUD = register(cluster(blockWithItem("small_bloodstone_bud", () -> new SpectrumClusterBlock(gemstone(MapColor.COLOR_RED, SpectrumBlockSoundGroups.ONYX_CLUSTER, 3), SpectrumClusterBlock.GrowthStage.SMALL), IS.of(Rarity.UNCOMMON), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));

	public static final DeferredBlock<Block> STRATINE_ORE = register(simple(blockWithItem("stratine_ore", () -> new CloakedOreBlock(UniformInt.of(3, 5), netherrackOre(), SpectrumAdvancements.REVEAL_STRATINE, NETHERRACK.defaultBlockState()), block -> new FloatBlockItem(block, IS.of().fireResistant(), -0.01F), InkColors.RED)));
	public static final DeferredBlock<Block> PALTAERIA_ORE = register(simple(blockWithItem("paltaeria_ore", () -> new CloakedOreBlock(UniformInt.of(2, 4), endstoneOre(), SpectrumAdvancements.REVEAL_PALTAERIA, END_STONE.defaultBlockState()), block -> new FloatBlockItem(block, IS.of(), 0.01F), InkColors.CYAN)));

	private static Properties gravityBlock(MapColor mapColor) {
		return settings(mapColor, SoundType.METAL, 4.0F, 6.0F).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops();
	}

	public static final DeferredBlock<Block> PALTAERIA_FLOATBLOCK = register(singleton(blockWithItem("paltaeria_floatblock", () -> new FloatBlock(gravityBlock(MapColor.COLOR_LIGHT_BLUE), 0.2F), block -> new FloatBlockItem(block, IS.of().fireResistant(), -0.02F), InkColors.RED), SpectrumTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top", b -> b, "_bottom")));
	public static final DeferredBlock<Block> STRATINE_FLOATBLOCK = register(singleton(blockWithItem("stratine_floatblock", () -> new FloatBlock(gravityBlock(MapColor.NETHER), -0.2F), block -> new FloatBlockItem(block, IS.of(), 0.02F), InkColors.CYAN), SpectrumTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top", b -> b, "_bottom")));
	public static final DeferredBlock<Block> HOVER_BLOCK = register(singleton(blockWithItem("hover_block", () -> new FloatBlock(gravityBlock(MapColor.DIAMOND), 0.0F), block -> new FloatBlockItem(block, IS.of(), 0F) {
		@Override
		public double applyGravity(ItemStack stack, Level world, Entity entity) {
			return 0;
		}

		@Override
		public void applyGravity(ItemStack stack, Level world, ItemEntity itemEntity) {
			itemEntity.setNoGravity(true);
		}
	}, InkColors.GREEN), TexturedModel.COLUMN));

	public static final DeferredBlock<Block> BLACKSLAG_COAL_ORE = register(singleton(blockWithItem("blackslag_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), blackslagOre()), InkColors.BLACK), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_COPPER_ORE = register(singleton(blockWithItem("blackslag_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), blackslagOre()), InkColors.BLACK), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_IRON_ORE = register(singleton(blockWithItem("blackslag_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), blackslagOre()), InkColors.BROWN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_GOLD_ORE = register(singleton(blockWithItem("blackslag_gold_ore", () -> new DropExperienceBlock(ConstantInt.of(0), blackslagOre()), InkColors.YELLOW), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_LAPIS_ORE = register(singleton(blockWithItem("blackslag_lapis_ore", () -> new DropExperienceBlock(UniformInt.of(2, 5), blackslagOre()), InkColors.BLUE), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_DIAMOND_ORE = register(singleton(blockWithItem("blackslag_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), blackslagOre()), InkColors.LIGHT_BLUE), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_REDSTONE_ORE = register(singleton(blockWithItem("blackslag_redstone_ore", () -> new RedStoneOreBlock(blackslagOre().randomTicks().lightLevel(litBlockEmission(9))), InkColors.RED), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_EMERALD_ORE = register(singleton(blockWithItem("blackslag_emerald_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), blackslagOre()), InkColors.LIME), TexturedModel.COLUMN_ALT));

	// FUNCTIONAL BLOCKS
	public static final DeferredBlock<Block> HEARTBOUND_CHEST = register(defaultNorthHorizontalFacing(blockWithItem("heartbound_chest", () -> new HeartboundChestBlock(settings(MapColor.TERRACOTTA_WHITE, SoundType.STONE, -1.0F, 3600000.0F).requiresCorrectToolForDrops().noOcclusion()), InkColors.BLUE), ModelLocationUtils::getModelLocation));
	public static final DeferredBlock<Block> COMPACTING_CHEST = register(defaultNorthHorizontalFacing(blockWithItem("compacting_chest", () -> new CompactingChestBlock(settings(MapColor.TERRACOTTA_WHITE, SoundType.STONE, 4.0F, 4.0F).requiresCorrectToolForDrops().noOcclusion()), InkColors.YELLOW), ModelLocationUtils::getModelLocation));
	public static final DeferredBlock<Block> FABRICATION_CHEST = register(cutout(defaultNorthHorizontalFacing(blockWithItem("fabrication_chest", () -> new FabricationChestBlock(settings(MapColor.COLOR_ORANGE, SoundType.STONE, 4.0F, 4.0F).requiresCorrectToolForDrops().noOcclusion()), InkColors.YELLOW), ModelLocationUtils::getModelLocation)).withPredefinedItemModel());
	public static final DeferredBlock<Block> BLACK_HOLE_CHEST = register(translucent(defaultNorthHorizontalFacing(blockWithItem("black_hole_chest", () -> new BlackHoleChestBlock(settings(MapColor.COLOR_BLACK, SoundType.STONE, 4.0F, 4.0F).requiresCorrectToolForDrops().noOcclusion()), InkColors.LIGHT_GRAY), ModelLocationUtils::getModelLocation)));
	public static final DeferredBlock<Block> PARTICLE_SPAWNER = register(cutout(blockWithItem("particle_spawner", () -> new ParticleSpawnerBlock(settings(MapColor.TERRACOTTA_WHITE, SoundType.AMETHYST, 5.0F, 6.0F).requiresCorrectToolForDrops().noOcclusion()), InkColors.PINK)).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_off")).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(BlockStateProperties.POWERED, SpectrumModels.PARTICLE_SPAWNER.create(block, SpectrumTextureMaps.top(block, "_top"), ctx.modelOutput), SpectrumModels.PARTICLE_SPAWNER.createWithSuffix(block, "_off", SpectrumTextureMaps.top(block, "_top_off"), ctx.modelOutput)))));
	public static final DeferredBlock<Block> CREATIVE_PARTICLE_SPAWNER = register(cutout(singletonWithSoup(blockWithItem("creative_particle_spawner", () -> new CreativeParticleSpawnerBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PARTICLE_SPAWNER.get()).strength(-1.0F, 3600000.8F).noLootTable()), block -> new BlockItem(block, IS.of(Rarity.EPIC)), InkColors.PINK), (Function<Block, ResourceLocation>) b -> ModelLocationUtils.getModelLocation(SpectrumBlocks.PARTICLE_SPAWNER.get()))).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, SpectrumBlocks.PARTICLE_SPAWNER.get(), "_off")));
	public static final DeferredBlock<Block> BEDROCK_ANVIL = register(defaultSouthHorizontalFacing(blockWithItem("bedrock_anvil", () -> new BedrockAnvilBlock(BlockBehaviour.Properties.ofFullCopy(ANVIL).requiresCorrectToolForDrops().strength(8.0F, 8.0F).sound(SoundType.METAL)), InkColors.BLACK), ModelLocationUtils::getModelLocation));

	// SOLID LIQUID CRYSTAL
	public static final DeferredBlock<Block> FROSTBITE_CRYSTAL = register(simple(blockWithItem("frostbite_crystal", () -> new Block(BlockBehaviour.Properties.ofFullCopy(GLOWSTONE).mapColor(MapColor.CLAY)), InkColors.LIGHT_BLUE)));
	public static final DeferredBlock<Block> BLAZING_CRYSTAL = register(simple(blockWithItem("blazing_crystal", () -> new Block(BlockBehaviour.Properties.ofFullCopy(GLOWSTONE).mapColor(MapColor.COLOR_ORANGE)), IS.of().fireResistant(), InkColors.ORANGE)));

	public static final DeferredBlock<Block> QUITOXIC_REEDS = register(cross(blockWithItem("quitoxic_reeds", () -> new QuitoxicReedsBlock(settings(MapColor.NONE, SoundType.GRASS, 0.0F).noCollission().offsetType(BlockBehaviour.OffsetType.XYZ).randomTicks().lightLevel(state -> state.getValue(QuitoxicReedsBlock.LOGGED).getLuminance())), InkColors.PURPLE)).withItemModel(SpectrumModelHelper::registerItemModel));
	public static final DeferredBlock<Block> MERMAIDS_BRUSH = register(cutout(block("mermaids_brush", () -> new MermaidsBrushBlock(settings(MapColor.NONE, SoundType.WET_GRASS, 0.0F).noCollission().randomTicks().lightLevel(state -> state.getValue(MermaidsBrushBlock.LOGGED).getLuminance())))).withBlockModel((ctx, block) -> {
		ResourceLocation none = SpectrumTexturedModels.cross(b -> b, "_none").createWithSuffix(block, "_none", ctx.modelOutput);
		ResourceLocation some = SpectrumTexturedModels.cross(b -> b, "_some").createWithSuffix(block, "_some", ctx.modelOutput);
		ResourceLocation full = SpectrumTexturedModels.cross(b -> b, "_full").createWithSuffix(block, "_full", ctx.modelOutput);
		return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.AGE_7).generate(age -> SpectrumModelHelper.createModelVariant(age < 3 ? none : age < 6 ? some : full)));
	}));
	public static final DeferredBlock<Block> RADIATING_ENDER = register(simple(blockWithItem("radiating_ender", () -> new RadiatingEnderBlock(BlockBehaviour.Properties.ofFullCopy(EMERALD_BLOCK).mapColor(MapColor.COLOR_PURPLE)), InkColors.PURPLE)));
	public static final DeferredBlock<Block> AMARANTH = register(cutout(block("amaranth", () -> new AmaranthCropBlock(settings(MapColor.NONE, SoundType.CROP, 0.0F).noCollission().randomTicks()))).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.properties(BlockStateProperties.AGE_7, TallCropBlock.HALF).generate((age, half) -> {
		String suffix;
		if (half == DoubleBlockHalf.LOWER) {
			suffix = "_stage" + ((age + 1) / 2) + "_lower";
			if (age > 0 && age % 2 == 0) return SpectrumModelHelper.createModelVariant(block, suffix);
		} else {
			suffix = "_stage" + Math.max(2, ((age + 1) / 2)) + "_upper";
			if (age < 4 || age == 6) return SpectrumModelHelper.createModelVariant(block, suffix);
		}
		return SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, suffix).createWithSuffix(block, suffix, ctx.modelOutput));
	}))));

	public static final DeferredBlock<Block> MEMORY = register(translucent(singletonWithSoup(blockWithItem("memory", () -> new MemoryBlock(settings(MapColor.NONE, SoundType.AMETHYST, 0.0F).isViewBlocking(SpectrumBlocks::never).noOcclusion().randomTicks()), block -> new MemoryItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_GRAY), ModelLocationUtils::getModelLocation)).withItemModel((ctx, item) -> SpectrumModelHelper.registerLayeredItemModel(ctx, item, ModelTemplates.THREE_LAYERED_ITEM, "_base", "_overlay", "_brighten")));
	public static final DeferredBlock<Block> CRACKED_END_PORTAL_FRAME = register(blockWithItem("cracked_end_portal_frame", () -> new CrackedEndPortalFrameBlock(settings(MapColor.ICE, SoundType.GLASS, -1.0F, 3600000.0F).instrument(NoteBlockInstrument.BASEDRUM).lightLevel((state) -> 1)), IS.of().fireResistant(), InkColors.PURPLE).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_none")).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(CrackedEndPortalFrameBlock.FACING_VERTICAL).select(false, net.minecraft.data.models.blockstates.Variant.variant()).select(true, net.minecraft.data.models.blockstates.Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))).with(PropertyDispatch.property(CrackedEndPortalFrameBlock.EYE_TYPE).generate(type -> SpectrumModelHelper.createModelVariant(ModelLocationUtils.getModelLocation(block, "_" + type.getSerializedName()))))));
	public static final DeferredBlock<Block> LAVA_SPONGE = register(simple(blockWithItem("lava_sponge", () -> new LavaSpongeBlock(BlockBehaviour.Properties.ofFullCopy(SPONGE).mapColor(MapColor.COLOR_ORANGE)), IS.of().fireResistant(), InkColors.ORANGE)));
	public static final DeferredBlock<Block> WET_LAVA_SPONGE = register(simple(burnable(blockWithItem("wet_lava_sponge", () -> new WetLavaSpongeBlock(BlockBehaviour.Properties.ofFullCopy(WET_SPONGE).mapColor(MapColor.COLOR_ORANGE).lightLevel(s -> 9).emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always)), block -> new WetLavaSpongeItem(block, IS.of(1).fireResistant().craftRemainder(SpectrumBlocks.LAVA_SPONGE.get().asItem())), InkColors.ORANGE), 12800)));

	public static final DeferredBlock<Block> LIGHT_LEVEL_DETECTOR = register(detector(blockWithItem("light_level_detector", () -> new BlockLightDetectorBlock(BlockBehaviour.Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED)));
	public static final DeferredBlock<Block> WEATHER_DETECTOR = register(detector(blockWithItem("weather_detector", () -> new WeatherDetectorBlock(BlockBehaviour.Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED)));
	public static final DeferredBlock<Block> ITEM_DETECTOR = register(detector(blockWithItem("item_detector", () -> new ItemDetectorBlock(BlockBehaviour.Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED)));
	public static final DeferredBlock<Block> PLAYER_DETECTOR = register(detector(blockWithItem("player_detector", () -> new PlayerDetectorBlock(BlockBehaviour.Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED)));
	public static final DeferredBlock<Block> CREATURE_DETECTOR = register(detector(blockWithItem("creature_detector", () -> new EntityDetectorBlock(BlockBehaviour.Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED)));
	public static final DeferredBlock<Block> REDSTONE_TIMER = register(cutout(blockWithItem("redstone_timer", () -> new RedstoneTimerBlock(BlockBehaviour.Properties.ofFullCopy(REPEATER)), InkColors.RED)).withPredefinedItemModel().withBlockModel((ctx, block) -> {
		MultiPartGenerator multipart = MultiPartGenerator.multiPart(block);
		ResourceLocation on = SpectrumModels.REDSTONE_TIMER.create(block, new TextureMapping().put(SpectrumTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH)), ctx.modelOutput);
		ResourceLocation off = SpectrumModels.REDSTONE_TIMER.createWithSuffix(block, "_off", new TextureMapping().put(SpectrumTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH, "_off")), ctx.modelOutput);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			VariantProperties.Rotation rotation = SpectrumModelHelper.getSouthDefaultRotation(direction);
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(BlockStateProperties.POWERED, true), SpectrumModelHelper.createModelVariant(on).with(VariantProperties.Y_ROT, rotation));
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(BlockStateProperties.POWERED, false), SpectrumModelHelper.createModelVariant(off).with(VariantProperties.Y_ROT, rotation));
			for (RedstoneTimerBlock.TimingStep step : RedstoneTimerBlock.TimingStep.values()) {
				multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(RedstoneTimerBlock.ACTIVE_TIME, step), SpectrumModelHelper.createModelVariant(block, "_left_" + step.ordinal()).with(VariantProperties.UV_LOCK, true).with(VariantProperties.Y_ROT, rotation));
				multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(RedstoneTimerBlock.INACTIVE_TIME, step), SpectrumModelHelper.createModelVariant(block, "_right_" + step.ordinal()).with(VariantProperties.UV_LOCK, true).with(VariantProperties.Y_ROT, rotation));
			}
		}
		return multipart;
	}));
	public static final DeferredBlock<Block> REDSTONE_CALCULATOR = register(cutout(blockWithItem("redstone_calculator", () -> new RedstoneCalculatorBlock(BlockBehaviour.Properties.ofFullCopy(REPEATER)), InkColors.RED)).withPredefinedItemModel().withBlockModel((ctx, block) -> {
		MultiPartGenerator multipart = MultiPartGenerator.multiPart(block);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			VariantProperties.Rotation rotation = SpectrumModelHelper.getSouthDefaultRotation(direction);
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(BlockStateProperties.POWERED, true), SpectrumModelHelper.createModelVariant(block, "_base").with(VariantProperties.Y_ROT, rotation));
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(BlockStateProperties.POWERED, false), SpectrumModelHelper.createModelVariant(block, "_base_off").with(VariantProperties.Y_ROT, rotation));
			for (RedstoneCalculatorBlock.CalculationMode mode : RedstoneCalculatorBlock.CalculationMode.values()) {
				multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(RedstoneCalculatorBlock.CALCULATION_MODE, mode), SpectrumModelHelper.createModelVariant(block, "_" + mode.getSerializedName()).with(VariantProperties.UV_LOCK, true).with(VariantProperties.Y_ROT, rotation));
			}
		}
		return multipart;
	}));
	public static final DeferredBlock<Block> REDSTONE_TRANSCEIVER = register(cutout(blockWithItem("redstone_transceiver", () -> new RedstoneTransceiverBlock(BlockBehaviour.Properties.ofFullCopy(REPEATER)), InkColors.RED)).withPredefinedItemModel().withBlockModel((ctx, block) -> {
		MultiPartGenerator multipart = MultiPartGenerator.multiPart(block);
		ResourceLocation senderOn = SpectrumModels.REDSTONE_TRANSCEIVER_SENDER.createWithSuffix(block, "_sender", new TextureMapping().put(SpectrumTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH)), ctx.modelOutput);
		ResourceLocation senderOff = SpectrumModels.REDSTONE_TRANSCEIVER_SENDER.createWithSuffix(block, "_sender_off", new TextureMapping().put(SpectrumTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH, "_off")), ctx.modelOutput);
		ResourceLocation receiverOn = SpectrumModels.REDSTONE_TRANSCEIVER_RECEIVER.createWithSuffix(block, "_receiver", new TextureMapping().put(SpectrumTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH)), ctx.modelOutput);
		ResourceLocation receiverOff = SpectrumModels.REDSTONE_TRANSCEIVER_RECEIVER.createWithSuffix(block, "_receiver_off", new TextureMapping().put(SpectrumTextureKeys.LIGHT, TextureMapping.getBlockTexture(REDSTONE_TORCH, "_off")), ctx.modelOutput);
		for (Direction direction : Direction.Plane.HORIZONTAL) {
			VariantProperties.Rotation rotation = SpectrumModelHelper.getSouthDefaultRotation(direction);
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(RedstoneTransceiverBlock.SENDER, true).term(BlockStateProperties.POWERED, true), SpectrumModelHelper.createModelVariant(senderOn).with(VariantProperties.Y_ROT, rotation));
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(RedstoneTransceiverBlock.SENDER, true).term(BlockStateProperties.POWERED, false), SpectrumModelHelper.createModelVariant(senderOff).with(VariantProperties.Y_ROT, rotation));
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(RedstoneTransceiverBlock.SENDER, false).term(BlockStateProperties.POWERED, true), SpectrumModelHelper.createModelVariant(receiverOn).with(VariantProperties.Y_ROT, rotation));
			multipart.with(Condition.condition().term(BlockStateProperties.HORIZONTAL_FACING, direction).term(RedstoneTransceiverBlock.SENDER, false).term(BlockStateProperties.POWERED, false), SpectrumModelHelper.createModelVariant(receiverOff).with(VariantProperties.Y_ROT, rotation));
		}
		for (DyeColor color : DyeColor.values()) {
			ResourceLocation channel = SpectrumModels.REDSTONE_TRANSCEIVER_CHANNEL.createWithSuffix(block, "_channel_" + color.getSerializedName(), SpectrumTextureMaps.all(SpectrumCommon.locate("block/" + color.getSerializedName() + "_block")), ctx.modelOutput);
			multipart.with(Condition.condition().term(RedstoneTransceiverBlock.CHANNEL, color), SpectrumModelHelper.createModelVariant(channel));
		}
		return multipart;
	}));
	public static final DeferredBlock<Block> BLOCK_PLACER = register(blockWithItem("block_placer", () -> new BlockPlacerBlock(BlockBehaviour.Properties.ofFullCopy(DISPENSER)), InkColors.CYAN).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(ctx, block, SpectrumTexturedModels.complexOrientable(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.NOTCHED_POLISHED_CALCITE.get(), "_top", b -> b, "_front", b -> b, "_back", b -> b, "_side")).with(SpectrumModelHelper.createUpNorthDefaultOrientationVariantMap())));
	public static final DeferredBlock<Block> BLOCK_DETECTOR = register(blockWithItem("block_detector", () -> new BlockDetectorBlock(BlockBehaviour.Properties.ofFullCopy(DISPENSER)), InkColors.CYAN).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createUpNorthDefaultOrientationVariantMap()).with(SpectrumModelHelper.createBooleanModelMap(BlockStateProperties.TRIGGERED, SpectrumTexturedModels.complexOrientable(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.NOTCHED_POLISHED_BASALT.get(), "_top", b -> b, "_front", b -> b, "_back_active", b -> b, "_side").createWithSuffix(block, "_active", ctx.modelOutput), SpectrumTexturedModels.complexOrientable(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.NOTCHED_POLISHED_BASALT.get(), "_top", b -> b, "_front", b -> b, "_back", b -> b, "_side").create(block, ctx.modelOutput)))));
	public static final DeferredBlock<Block> BLOCK_BREAKER = register(blockWithItem("block_breaker", () -> new BlockBreakerBlock(BlockBehaviour.Properties.ofFullCopy(DISPENSER)), InkColors.CYAN).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(ctx, block, SpectrumTexturedModels.complexOrientable(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.POLISHED_BONE_ASH_PILLAR.get(), "_top", b -> b, "_front", b -> b, "_back", b -> b, "_side")).with(SpectrumModelHelper.createUpNorthDefaultOrientationVariantMap())));
	public static final DeferredBlock<Block> ENDER_DROPPER = register(orientable(blockWithItem("ender_dropper", () -> new EnderDropperBlock(BlockBehaviour.Properties.ofFullCopy(DROPPER).mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(15F, 60.0F)), InkColors.PURPLE)));
	public static final DeferredBlock<Block> ENDER_HOPPER = register(singletonWithSoup(blockWithItem("ender_hopper", () -> new EnderHopperBlock(BlockBehaviour.Properties.ofFullCopy(HOPPER).mapColor(MapColor.COLOR_GRAY).requiresCorrectToolForDrops().strength(15F, 60.0F)), InkColors.PURPLE), ModelLocationUtils::getModelLocation).withItemModel(SpectrumModelHelper::registerItemModel));

	public static final DeferredBlock<Block> OMINOUS_SAPLING = register(simplePlant(blockWithItem("ominous_sapling", () -> new OminousSaplingBlock(BlockBehaviour.Properties.ofFullCopy(OAK_SAPLING)), block -> new OminousSaplingBlockItem(block, IS.of()), InkColors.GREEN)));

	public static final DeferredBlock<Block> SPIRIT_SALLOW_LEAVES = register(singleton(blockWithItem("spirit_sallow_leaves", () -> new SpiritSallowLeavesBlock(BlockBehaviour.Properties.ofFullCopy(OAK_LEAVES).mapColor(MapColor.QUARTZ).lightLevel((state) -> 8)), InkColors.GREEN), TexturedModel.LEAVES));
	public static final DeferredBlock<Block> SPIRIT_SALLOW_LOG = register(log(blockWithItem("spirit_sallow_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(OAK_WOOD).mapColor(MapColor.COLOR_GRAY)), InkColors.GREEN)));
	public static final DeferredBlock<Block> SPIRIT_SALLOW_ROOTS = register(blockWithItem("spirit_sallow_roots", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(OAK_WOOD).mapColor(MapColor.COLOR_GRAY)), InkColors.GREEN).withBlockModel((ctx, block) -> {
		TextureMapping textureMap = SpectrumTextureMaps.sideEnd(block, "", block, "");
		ResourceLocation vertical = ModelTemplates.CUBE_COLUMN.create(block, textureMap, ctx.modelOutput);
		ResourceLocation horizontal = ModelTemplates.CUBE_COLUMN_HORIZONTAL.create(block, textureMap, ctx.modelOutput);
		return BlockModelGenerators.createRotatedPillarWithHorizontalVariant(block, vertical, horizontal);
	}));
	public static final DeferredBlock<Block> SPIRIT_SALLOW_HEART = register(singleton(blockWithItem("spirit_sallow_heart", () -> new Block(BlockBehaviour.Properties.ofFullCopy(OAK_WOOD).mapColor(MapColor.COLOR_GRAY).lightLevel(s -> 11)), InkColors.GREEN), SpectrumTexturedModels.cubeColumn(b -> b, "", b -> SpectrumBlocks.SPIRIT_SALLOW_LOG.get(), "_top")));

	public static final DeferredBlock<Block> SACRED_SOIL = register(blockWithItem("sacred_soil", () -> new ExtraTickFarmlandBlock(BlockBehaviour.Properties.ofFullCopy(FARMLAND).mapColor(MapColor.CLAY), DIRT.defaultBlockState()), InkColors.LIME).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.MOISTURE).generate(moisture -> SpectrumModelHelper.createModelVariant(block, moisture == 7 ? "_moist" : "")))));

	private static Properties spiritVines(MapColor mapColor) {
		return settings(mapColor, SoundType.CAVE_VINES, 0.0F).noCollission();
	}

	public static final DeferredBlock<Block> CYAN_SPIRIT_SALLOW_VINES_PLANT = register(spiritVines(block("cyan_spirit_sallow_vines_body", () -> new SpiritVinesPlantBlock(spiritVines(MapColor.COLOR_CYAN), BuiltinGemstoneColor.CYAN))));
	public static final DeferredBlock<Block> MAGENTA_SPIRIT_SALLOW_VINES_PLANT = register(spiritVines(block("magenta_spirit_sallow_vines_body", () -> new SpiritVinesPlantBlock(spiritVines(MapColor.COLOR_MAGENTA), BuiltinGemstoneColor.MAGENTA))));
	public static final DeferredBlock<Block> YELLOW_SPIRIT_SALLOW_VINES_PLANT = register(spiritVines(block("yellow_spirit_sallow_vines_body", () -> new SpiritVinesPlantBlock(spiritVines(MapColor.COLOR_YELLOW), BuiltinGemstoneColor.YELLOW))));
	public static final DeferredBlock<Block> BLACK_SPIRIT_SALLOW_VINES_PLANT = register(spiritVines(block("black_spirit_sallow_vines_body", () -> new SpiritVinesPlantBlock(spiritVines(MapColor.TERRACOTTA_BLACK), BuiltinGemstoneColor.BLACK))));
	public static final DeferredBlock<Block> WHITE_SPIRIT_SALLOW_VINES_PLANT = register(spiritVines(block("white_spirit_sallow_vines_body", () -> new SpiritVinesPlantBlock(spiritVines(MapColor.TERRACOTTA_WHITE), BuiltinGemstoneColor.WHITE))));

	public static final DeferredBlock<Block> CYAN_SPIRIT_SALLOW_VINES = register(spiritVines(block("cyan_spirit_sallow_vines_head", () -> new SpiritVinesPlantStemBlock(spiritVines(MapColor.COLOR_CYAN), BuiltinGemstoneColor.CYAN))));
	public static final DeferredBlock<Block> MAGENTA_SPIRIT_SALLOW_VINES = register(spiritVines(block("magenta_spirit_sallow_vines_head", () -> new SpiritVinesPlantStemBlock(spiritVines(MapColor.COLOR_MAGENTA), BuiltinGemstoneColor.MAGENTA))));
	public static final DeferredBlock<Block> YELLOW_SPIRIT_SALLOW_VINES = register(spiritVines(block("yellow_spirit_sallow_vines_head", () -> new SpiritVinesPlantStemBlock(spiritVines(MapColor.COLOR_YELLOW), BuiltinGemstoneColor.YELLOW))));
	public static final DeferredBlock<Block> BLACK_SPIRIT_SALLOW_VINES = register(spiritVines(block("black_spirit_sallow_vines_head", () -> new SpiritVinesPlantStemBlock(spiritVines(MapColor.TERRACOTTA_BLACK), BuiltinGemstoneColor.BLACK))));
	public static final DeferredBlock<Block> WHITE_SPIRIT_SALLOW_VINES = register(spiritVines(block("white_spirit_sallow_vines_head", () -> new SpiritVinesPlantStemBlock(spiritVines(MapColor.TERRACOTTA_WHITE), BuiltinGemstoneColor.WHITE))));

	public static final DeferredBlock<Block> STUCK_STORM_STONE = register(cutout(defaultWestHorizontalFacing(block("stuck_storm_stone", () -> new StuckStormStoneBlock(settings(MapColor.NONE, SoundType.SMALL_AMETHYST_BUD, 0.0F).noCollission().noOcclusion().isSuffocating(SpectrumBlocks::never).noTerrainParticles().isViewBlocking(SpectrumBlocks::never).replaceable())), ModelLocationUtils::getModelLocation)));
	public static final DeferredBlock<Block> DEEPER_DOWN_PORTAL = register(block("deeper_down_portal", () -> new DeeperDownPortalBlock(settings(MapColor.COLOR_BLACK, SoundType.EMPTY, -1.0F, 3600000.0F).pushReaction(PushReaction.BLOCK).lightLevel(state -> 8).noLootTable())).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(DeeperDownPortalBlock.FACING_UP, ModelLocationUtils.getModelLocation(block, "_up"), ModelLocationUtils.getModelLocation(block)))));

	private static Properties upgrade() {
		return BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.POLISHED_BASALT.get()).forceSolidOn();
	}

	public static final DeferredBlock<Block> UPGRADE_SPEED = register(parented(blockWithItem("upgrade_speed", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 1, InkColors.MAGENTA_COLOR), block -> new UpgradeBlockItem(block, IS.of(16), "upgrade_speed"), InkColors.LIGHT_GRAY), b -> b));
	public static final DeferredBlock<Block> UPGRADE_SPEED2 = register(parented(blockWithItem("upgrade_speed2", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 2, InkColors.MAGENTA_COLOR), block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_speed2"), InkColors.LIGHT_GRAY), b -> SpectrumBlocks.UPGRADE_SPEED.get()));
	public static final DeferredBlock<Block> UPGRADE_SPEED3 = register(parented(blockWithItem("upgrade_speed3", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 8, InkColors.MAGENTA_COLOR), block -> new UpgradeBlockItem(block, IS.of(16, Rarity.RARE), "upgrade_speed3"), InkColors.LIGHT_GRAY), b -> SpectrumBlocks.UPGRADE_SPEED.get()));
	public static final DeferredBlock<Block> UPGRADE_EFFICIENCY = register(parented(blockWithItem("upgrade_efficiency", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EFFICIENCY, 1, InkColors.YELLOW_COLOR), block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_efficiency"), InkColors.LIGHT_GRAY), b -> b));
	public static final DeferredBlock<Block> UPGRADE_EFFICIENCY2 = register(parented(blockWithItem("upgrade_efficiency2", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EFFICIENCY, 4, InkColors.YELLOW_COLOR), block -> new UpgradeBlockItem(block, IS.of(16, Rarity.RARE), "upgrade_efficiency2"), InkColors.LIGHT_GRAY), b -> SpectrumBlocks.UPGRADE_EFFICIENCY.get()));
	public static final DeferredBlock<Block> UPGRADE_YIELD = register(parented(blockWithItem("upgrade_yield", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.YIELD, 1, InkColors.CYAN_COLOR), block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_yield"), InkColors.LIGHT_GRAY), b -> b));
	public static final DeferredBlock<Block> UPGRADE_YIELD2 = register(parented(blockWithItem("upgrade_yield2", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.YIELD, 4, InkColors.CYAN_COLOR), block -> new UpgradeBlockItem(block, IS.of(16, Rarity.RARE), "upgrade_yield2"), InkColors.LIGHT_GRAY), b -> SpectrumBlocks.UPGRADE_YIELD.get()));
	public static final DeferredBlock<Block> UPGRADE_EXPERIENCE = register(parented(blockWithItem("upgrade_experience", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EXPERIENCE, 1, InkColors.PURPLE_COLOR), block -> new UpgradeBlockItem(block, IS.of(16), "upgrade_experience"), InkColors.LIGHT_GRAY), b -> b));
	public static final DeferredBlock<Block> UPGRADE_EXPERIENCE2 = register(parented(blockWithItem("upgrade_experience2", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EXPERIENCE, 4, InkColors.PURPLE_COLOR), block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_experience2"), InkColors.LIGHT_GRAY), b -> SpectrumBlocks.UPGRADE_EXPERIENCE.get()));

	public static final DeferredBlock<Block> REDSTONE_SAND = register(simple(blockWithItem("redstone_sand", () -> new RedstoneGravityBlock(BlockBehaviour.Properties.ofFullCopy(SAND).mapColor(MapColor.FIRE)), InkColors.RED)));
	public static final DeferredBlock<Block> ENDER_GLASS = register(translucent(blockWithItem("ender_glass", () -> new EnderGlassBlock(BlockBehaviour.Properties.ofFullCopy(GLASS).mapColor(MapColor.COLOR_PURPLE).noOcclusion().isRedstoneConductor(SpectrumBlocks::never).isValidSpawn((state, world, pos, entityType) -> state.getValue(EnderGlassBlock.TRANSPARENCY_STATE) == EnderGlassBlock.TransparencyState.SOLID).isSuffocating((state, world, pos) -> state.getValue(EnderGlassBlock.TRANSPARENCY_STATE) == EnderGlassBlock.TransparencyState.SOLID).isViewBlocking((state, world, pos) -> state.getValue(EnderGlassBlock.TRANSPARENCY_STATE) == EnderGlassBlock.TransparencyState.SOLID)), InkColors.PURPLE)).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_solid")).withBlockModel((ctx, block) ->
			MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(EnderGlassBlock.TRANSPARENCY_STATE)
					.generate(transparency -> SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cubeAll(b -> b, "_" + transparency.getSerializedName()).createWithSuffix(block, "_" + transparency.getSerializedName(), ctx.modelOutput))))));
	public static final DeferredBlock<Block> CLOVER = register(singletonWithSoup(cutout(blockWithItem("clover", () -> new CloverBlock(BlockBehaviour.Properties.ofFullCopy(SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XZ)), IS.of(), InkColors.LIME)), ModelLocationUtils::getModelLocation).withItemModel(SpectrumModelHelper::registerItemModel));
	public static final DeferredBlock<Block> FOUR_LEAF_CLOVER = register(singletonWithSoup(cutout(blockWithItem("four_leaf_clover", () -> new FourLeafCloverBlock(BlockBehaviour.Properties.ofFullCopy(SHORT_GRASS).offsetType(BlockBehaviour.OffsetType.XZ)), block -> new FourLeafCloverItem(block, IS.of(), SpectrumAdvancements.REVEAL_FOUR_LEAF_CLOVER, SpectrumBlocks.CLOVER.get().asItem()), InkColors.LIME)), ModelLocationUtils::getModelLocation).withItemModel(SpectrumModelHelper::registerItemModel));

	private static final UniformInt gemOreExperienceProvider = UniformInt.of(1, 4);
	public static final DeferredBlock<Block> TOPAZ_ORE = register(simple(blockWithItem("topaz_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, ore(), BuiltinGemstoneColor.CYAN, SpectrumAdvancements.COLLECT_TOPAZ, STONE.defaultBlockState()), InkColors.CYAN)));
	public static final DeferredBlock<Block> AMETHYST_ORE = register(simple(blockWithItem("amethyst_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, ore(), BuiltinGemstoneColor.MAGENTA, SpectrumAdvancements.COLLECT_AMETHYST, STONE.defaultBlockState()), InkColors.MAGENTA)));
	public static final DeferredBlock<Block> CITRINE_ORE = register(simple(blockWithItem("citrine_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, ore(), BuiltinGemstoneColor.YELLOW, SpectrumAdvancements.COLLECT_CITRINE, STONE.defaultBlockState()), InkColors.YELLOW)));
	public static final DeferredBlock<Block> ONYX_ORE = register(simple(blockWithItem("onyx_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, ore(), BuiltinGemstoneColor.BLACK, SpectrumAdvancements.CREATE_ONYX, STONE.defaultBlockState()), InkColors.BLACK)));
	public static final DeferredBlock<Block> MOONSTONE_ORE = register(simple(blockWithItem("moonstone_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, ore(), BuiltinGemstoneColor.WHITE, SpectrumAdvancements.COLLECT_MOONSTONE, STONE.defaultBlockState()), InkColors.WHITE)));

	public static final DeferredBlock<Block> DEEPSLATE_TOPAZ_ORE = register(simple(blockWithItem("deepslate_topaz_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.CYAN, SpectrumAdvancements.COLLECT_TOPAZ, DEEPSLATE.defaultBlockState()), InkColors.CYAN)));
	public static final DeferredBlock<Block> DEEPSLATE_AMETHYST_ORE = register(simple(blockWithItem("deepslate_amethyst_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.MAGENTA, SpectrumAdvancements.COLLECT_AMETHYST, DEEPSLATE.defaultBlockState()), InkColors.MAGENTA)));
	public static final DeferredBlock<Block> DEEPSLATE_CITRINE_ORE = register(simple(blockWithItem("deepslate_citrine_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.YELLOW, SpectrumAdvancements.COLLECT_CITRINE, DEEPSLATE.defaultBlockState()), InkColors.YELLOW)));
	public static final DeferredBlock<Block> DEEPSLATE_ONYX_ORE = register(simple(blockWithItem("deepslate_onyx_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.BLACK, SpectrumAdvancements.CREATE_ONYX, DEEPSLATE.defaultBlockState()), InkColors.BLACK)));
	public static final DeferredBlock<Block> DEEPSLATE_MOONSTONE_ORE = register(simple(blockWithItem("deepslate_moonstone_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, deepslateOre(), BuiltinGemstoneColor.WHITE, SpectrumAdvancements.COLLECT_MOONSTONE, DEEPSLATE.defaultBlockState()), InkColors.WHITE)));

	public static final DeferredBlock<Block> BLACKSLAG_TOPAZ_ORE = register(singleton(blockWithItem("blackslag_topaz_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.CYAN, SpectrumAdvancements.COLLECT_TOPAZ, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), InkColors.CYAN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_AMETHYST_ORE = register(singleton(blockWithItem("blackslag_amethyst_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.MAGENTA, SpectrumAdvancements.COLLECT_AMETHYST, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), InkColors.MAGENTA), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_CITRINE_ORE = register(singleton(blockWithItem("blackslag_citrine_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.YELLOW, SpectrumAdvancements.COLLECT_CITRINE, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), InkColors.YELLOW), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_ONYX_ORE = register(singleton(blockWithItem("blackslag_onyx_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.BLACK, SpectrumAdvancements.CREATE_ONYX, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), InkColors.BLACK), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLACKSLAG_MOONSTONE_ORE = register(singleton(blockWithItem("blackslag_moonstone_ore", () -> new GemstoneOreBlock(SpectrumBlocks.gemOreExperienceProvider, blackslagOre(), BuiltinGemstoneColor.WHITE, SpectrumAdvancements.COLLECT_MOONSTONE, SpectrumBlocks.BLACKSLAG.get().defaultBlockState()), InkColors.WHITE), TexturedModel.COLUMN_ALT));

	private static Properties polishedGemBlock(MapColor mapColor, SoundType soundGroup) {
		return settings(mapColor, soundGroup, 5.0F, 6.0F);
	}

	public static final DeferredBlock<Block> POLISHED_TOPAZ_BLOCK = register(singleton(blockWithItem("polished_topaz_block", () -> new Block(polishedGemBlock(MapColor.COLOR_CYAN, SpectrumBlockSoundGroups.TOPAZ_BLOCK)), InkColors.CYAN), TexturedModel.TOP_BOTTOM_WITH_WALL));
	public static final DeferredBlock<Block> POLISHED_AMETHYST_BLOCK = register(singleton(blockWithItem("polished_amethyst_block", () -> new Block(polishedGemBlock(MapColor.COLOR_MAGENTA, SoundType.AMETHYST)), InkColors.MAGENTA), TexturedModel.TOP_BOTTOM_WITH_WALL));
	public static final DeferredBlock<Block> POLISHED_CITRINE_BLOCK = register(singleton(blockWithItem("polished_citrine_block", () -> new Block(polishedGemBlock(MapColor.COLOR_YELLOW, SpectrumBlockSoundGroups.CITRINE_BLOCK)), InkColors.YELLOW), TexturedModel.TOP_BOTTOM_WITH_WALL));
	public static final DeferredBlock<Block> POLISHED_ONYX_BLOCK = register(singleton(blockWithItem("polished_onyx_block", () -> new Block(polishedGemBlock(MapColor.COLOR_BLACK, SpectrumBlockSoundGroups.ONYX_BLOCK)), InkColors.BLACK), TexturedModel.TOP_BOTTOM_WITH_WALL));
	public static final DeferredBlock<Block> POLISHED_MOONSTONE_BLOCK = register(singleton(blockWithItem("polished_moonstone_block", () -> new Block(polishedGemBlock(MapColor.SNOW, SpectrumBlockSoundGroups.MOONSTONE_BLOCK)), InkColors.WHITE), TexturedModel.TOP_BOTTOM_WITH_WALL));

	private static BlockBehaviour.Properties deferredCopyWithMapColor(DeferredBlock<Block> baseBlock, MapColor color) {
		return BlockBehaviour.Properties.ofFullCopy(baseBlock.get()).mapColor(color);
	}

	private static BlockBehaviour.Properties copyWithMapColor(Block baseBlock, MapColor color) {
		return BlockBehaviour.Properties.ofFullCopy(baseBlock).mapColor(color);
	}

	public static Properties pottedPlant() {
		return Properties.of().instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
	}

	public static final DeferredBlock<Block> AMARANTH_BUSHEL = register(cross(blockWithItem("amaranth_bushel", () -> new AmaranthBushelBlock(SpectrumStatusEffects.NOURISHING, 8, settings(MapColor.NONE, SoundType.CROP, 0.0F).noCollission()), InkColors.RED)).withItemModel(SpectrumModelHelper::registerItemModel));
	public static final DeferredBlock<Block> POTTED_AMARANTH_BUSHEL = register(pottedPlant(block("potted_amaranth_bushel", () -> new PottedAmaranthBushelBlock(SpectrumBlocks.AMARANTH_BUSHEL.get(), pottedPlant())), false));

	public static final DeferredBlock<Block> RESONANT_LILY = register(simplePlant(blockWithItem("resonant_lily", () -> new ResonantLilyBlock(MobEffects.REGENERATION, 5, BlockBehaviour.Properties.ofFullCopy(POPPY).mapColor(MapColor.SNOW)), InkColors.GREEN)));
	public static final DeferredBlock<Block> POTTED_RESONANT_LILY = register(pottedPlant(block("potted_resonant_lily", () -> new PottedResonantLilyBlock(SpectrumBlocks.RESONANT_LILY.get(), pottedPlant())), false));

	public static final DeferredBlock<Block> BLOOD_ORCHID = register(cutout(blockWithItem("blood_orchid", () -> new BloodOrchidBlock(SpectrumStatusEffects.FRENZY, 10, BlockBehaviour.Properties.ofFullCopy(POPPY).offsetType(BlockBehaviour.OffsetType.NONE).randomTicks()), InkColors.RED)).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerBlockTexturedItemModel(ctx, block, "5")).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BloodOrchidBlock.AGE).generate(stage -> SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.cross(b -> b, stage.toString()).createWithSuffix(block, stage.toString(), ctx.modelOutput))))));
	public static final DeferredBlock<Block> POTTED_BLOOD_ORCHID = register(cutout(singleton(block("potted_blood_orchid", () -> new PottedBloodOrchidBlock(SpectrumBlocks.BLOOD_ORCHID.get(), pottedPlant())), SpectrumTexturedModels.flowerPotCross(b -> SpectrumBlocks.BLOOD_ORCHID.get(), "5", false))));

	public static DeferredBlock<Block> registerColoredSapling(String name, InkColor color, TreeGrower generator) {
		return register(simplePlant(blockWithItem(name, () -> new ColoredSaplingBlock(copyWithMapColor(OAK_SAPLING, color.getDyeColor().orElse(DyeColor.LIME).getMapColor()), color, generator), color)));
	}

	public static final DeferredBlock<Block> BLACK_SAPLING = registerColoredSapling("black_sapling", InkColors.BLACK, SpectrumSaplingGenerators.BLACK_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> BLUE_SAPLING = registerColoredSapling("blue_sapling", InkColors.BLUE, SpectrumSaplingGenerators.BLUE_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> BROWN_SAPLING = registerColoredSapling("brown_sapling", InkColors.BROWN, SpectrumSaplingGenerators.BROWN_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> CYAN_SAPLING = registerColoredSapling("cyan_sapling", InkColors.CYAN, SpectrumSaplingGenerators.CYAN_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> GRAY_SAPLING = registerColoredSapling("gray_sapling", InkColors.GRAY, SpectrumSaplingGenerators.GRAY_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> GREEN_SAPLING = registerColoredSapling("green_sapling", InkColors.GREEN, SpectrumSaplingGenerators.GREEN_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> LIGHT_BLUE_SAPLING = registerColoredSapling("light_blue_sapling", InkColors.LIGHT_BLUE, SpectrumSaplingGenerators.LIGHT_BLUE_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> LIGHT_GRAY_SAPLING = registerColoredSapling("light_gray_sapling", InkColors.LIGHT_GRAY, SpectrumSaplingGenerators.LIGHT_GRAY_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> LIME_SAPLING = registerColoredSapling("lime_sapling", InkColors.LIME, SpectrumSaplingGenerators.LIME_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> MAGENTA_SAPLING = registerColoredSapling("magenta_sapling", InkColors.MAGENTA, SpectrumSaplingGenerators.MAGENTA_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> ORANGE_SAPLING = registerColoredSapling("orange_sapling", InkColors.ORANGE, SpectrumSaplingGenerators.ORANGE_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> PINK_SAPLING = registerColoredSapling("pink_sapling", InkColors.PINK, SpectrumSaplingGenerators.PINK_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> PURPLE_SAPLING = registerColoredSapling("purple_sapling", InkColors.PURPLE, SpectrumSaplingGenerators.PURPLE_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> RED_SAPLING = registerColoredSapling("red_sapling", InkColors.RED, SpectrumSaplingGenerators.RED_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> WHITE_SAPLING = registerColoredSapling("white_sapling", InkColors.WHITE, SpectrumSaplingGenerators.WHITE_COLORED_SAPLING_GENERATOR);
	public static final DeferredBlock<Block> YELLOW_SAPLING = registerColoredSapling("yellow_sapling", InkColors.YELLOW, SpectrumSaplingGenerators.YELLOW_COLORED_SAPLING_GENERATOR);

	public static DeferredBlock<Block> registerPottedColoredSapling(String name, DeferredBlock<Block> saplingBlock) {
		return register(pottedPlant(block(name, () -> new PottedColoredSaplingBlock(saplingBlock.get(), pottedPlant(), ((ColoredSaplingBlock) saplingBlock.get()).getColor())), false));
	}

	public static final DeferredBlock<Block> POTTED_BLACK_SAPLING = registerPottedColoredSapling("potted_black_sapling", SpectrumBlocks.BLACK_SAPLING);
	public static final DeferredBlock<Block> POTTED_BLUE_SAPLING = registerPottedColoredSapling("potted_blue_sapling", SpectrumBlocks.BLUE_SAPLING);
	public static final DeferredBlock<Block> POTTED_BROWN_SAPLING = registerPottedColoredSapling("potted_brown_sapling", SpectrumBlocks.BROWN_SAPLING);
	public static final DeferredBlock<Block> POTTED_CYAN_SAPLING = registerPottedColoredSapling("potted_cyan_sapling", SpectrumBlocks.CYAN_SAPLING);
	public static final DeferredBlock<Block> POTTED_GRAY_SAPLING = registerPottedColoredSapling("potted_gray_sapling", SpectrumBlocks.GRAY_SAPLING);
	public static final DeferredBlock<Block> POTTED_GREEN_SAPLING = registerPottedColoredSapling("potted_green_sapling", SpectrumBlocks.GREEN_SAPLING);
	public static final DeferredBlock<Block> POTTED_LIGHT_BLUE_SAPLING = registerPottedColoredSapling("potted_light_blue_sapling", SpectrumBlocks.LIGHT_BLUE_SAPLING);
	public static final DeferredBlock<Block> POTTED_LIGHT_GRAY_SAPLING = registerPottedColoredSapling("potted_light_gray_sapling", SpectrumBlocks.LIGHT_GRAY_SAPLING);
	public static final DeferredBlock<Block> POTTED_LIME_SAPLING = registerPottedColoredSapling("potted_lime_sapling", SpectrumBlocks.LIME_SAPLING);
	public static final DeferredBlock<Block> POTTED_MAGENTA_SAPLING = registerPottedColoredSapling("potted_magenta_sapling", SpectrumBlocks.MAGENTA_SAPLING);
	public static final DeferredBlock<Block> POTTED_ORANGE_SAPLING = registerPottedColoredSapling("potted_orange_sapling", SpectrumBlocks.ORANGE_SAPLING);
	public static final DeferredBlock<Block> POTTED_PINK_SAPLING = registerPottedColoredSapling("potted_pink_sapling", SpectrumBlocks.PINK_SAPLING);
	public static final DeferredBlock<Block> POTTED_PURPLE_SAPLING = registerPottedColoredSapling("potted_purple_sapling", SpectrumBlocks.PURPLE_SAPLING);
	public static final DeferredBlock<Block> POTTED_RED_SAPLING = registerPottedColoredSapling("potted_red_sapling", SpectrumBlocks.RED_SAPLING);
	public static final DeferredBlock<Block> POTTED_WHITE_SAPLING = registerPottedColoredSapling("potted_white_sapling", SpectrumBlocks.WHITE_SAPLING);
	public static final DeferredBlock<Block> POTTED_YELLOW_SAPLING = registerPottedColoredSapling("potted_yellow_sapling", SpectrumBlocks.YELLOW_SAPLING);

	public static DeferredBlock<Block> registerColoredStrippedLog(String name, InkColor color) {
		return register(log(blockWithItem(name, () -> new ColoredStrippedLogBlock(copyWithMapColor(STRIPPED_OAK_LOG, color.getDyeColor().orElse(DyeColor.LIME).getMapColor()), color), color)));
	}

	public static final DeferredBlock<Block> STRIPPED_BLACK_LOG = registerColoredStrippedLog("stripped_black_log", InkColors.BLACK);
	public static final DeferredBlock<Block> STRIPPED_BLUE_LOG = registerColoredStrippedLog("stripped_blue_log", InkColors.BLUE);
	public static final DeferredBlock<Block> STRIPPED_BROWN_LOG = registerColoredStrippedLog("stripped_brown_log", InkColors.BROWN);
	public static final DeferredBlock<Block> STRIPPED_CYAN_LOG = registerColoredStrippedLog("stripped_cyan_log", InkColors.CYAN);
	public static final DeferredBlock<Block> STRIPPED_GRAY_LOG = registerColoredStrippedLog("stripped_gray_log", InkColors.GRAY);
	public static final DeferredBlock<Block> STRIPPED_GREEN_LOG = registerColoredStrippedLog("stripped_green_log", InkColors.GREEN);
	public static final DeferredBlock<Block> STRIPPED_LIGHT_BLUE_LOG = registerColoredStrippedLog("stripped_light_blue_log", InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> STRIPPED_LIGHT_GRAY_LOG = registerColoredStrippedLog("stripped_light_gray_log", InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> STRIPPED_LIME_LOG = registerColoredStrippedLog("stripped_lime_log", InkColors.LIME);
	public static final DeferredBlock<Block> STRIPPED_MAGENTA_LOG = registerColoredStrippedLog("stripped_magenta_log", InkColors.MAGENTA);
	public static final DeferredBlock<Block> STRIPPED_ORANGE_LOG = registerColoredStrippedLog("stripped_orange_log", InkColors.ORANGE);
	public static final DeferredBlock<Block> STRIPPED_PINK_LOG = registerColoredStrippedLog("stripped_pink_log", InkColors.PINK);
	public static final DeferredBlock<Block> STRIPPED_PURPLE_LOG = registerColoredStrippedLog("stripped_purple_log", InkColors.PURPLE);
	public static final DeferredBlock<Block> STRIPPED_RED_LOG = registerColoredStrippedLog("stripped_red_log", InkColors.RED);
	public static final DeferredBlock<Block> STRIPPED_WHITE_LOG = registerColoredStrippedLog("stripped_white_log", InkColors.WHITE);
	public static final DeferredBlock<Block> STRIPPED_YELLOW_LOG = registerColoredStrippedLog("stripped_yellow_log", InkColors.YELLOW);

	public static DeferredBlock<Block> registerColoredLog(String name, InkColor color, DeferredBlock<Block> strippedForm) {
		return register(log(blockWithItem(name, () -> new ColoredLogBlock(copyWithMapColor(OAK_LOG, color.getDyeColor().orElse(DyeColor.LIME).getMapColor()), color, strippedForm.get()), color)));
	}

	public static final DeferredBlock<Block> BLACK_LOG = registerColoredLog("black_log", InkColors.BLACK, SpectrumBlocks.STRIPPED_BLACK_LOG);
	public static final DeferredBlock<Block> BLUE_LOG = registerColoredLog("blue_log", InkColors.BLUE, SpectrumBlocks.STRIPPED_BLUE_LOG);
	public static final DeferredBlock<Block> BROWN_LOG = registerColoredLog("brown_log", InkColors.BROWN, SpectrumBlocks.STRIPPED_BROWN_LOG);
	public static final DeferredBlock<Block> CYAN_LOG = registerColoredLog("cyan_log", InkColors.CYAN, SpectrumBlocks.STRIPPED_CYAN_LOG);
	public static final DeferredBlock<Block> GRAY_LOG = registerColoredLog("gray_log", InkColors.GRAY, SpectrumBlocks.STRIPPED_GRAY_LOG);
	public static final DeferredBlock<Block> GREEN_LOG = registerColoredLog("green_log", InkColors.GREEN, SpectrumBlocks.STRIPPED_GREEN_LOG);
	public static final DeferredBlock<Block> LIGHT_BLUE_LOG = registerColoredLog("light_blue_log", InkColors.LIGHT_BLUE, SpectrumBlocks.STRIPPED_LIGHT_BLUE_LOG);
	public static final DeferredBlock<Block> LIGHT_GRAY_LOG = registerColoredLog("light_gray_log", InkColors.LIGHT_GRAY, SpectrumBlocks.STRIPPED_LIGHT_GRAY_LOG);
	public static final DeferredBlock<Block> LIME_LOG = registerColoredLog("lime_log", InkColors.LIME, SpectrumBlocks.STRIPPED_LIME_LOG);
	public static final DeferredBlock<Block> MAGENTA_LOG = registerColoredLog("magenta_log", InkColors.MAGENTA, SpectrumBlocks.STRIPPED_MAGENTA_LOG);
	public static final DeferredBlock<Block> ORANGE_LOG = registerColoredLog("orange_log", InkColors.ORANGE, SpectrumBlocks.STRIPPED_ORANGE_LOG);
	public static final DeferredBlock<Block> PINK_LOG = registerColoredLog("pink_log", InkColors.PINK, SpectrumBlocks.STRIPPED_PINK_LOG);
	public static final DeferredBlock<Block> PURPLE_LOG = registerColoredLog("purple_log", InkColors.PURPLE, SpectrumBlocks.STRIPPED_PURPLE_LOG);
	public static final DeferredBlock<Block> RED_LOG = registerColoredLog("red_log", InkColors.RED, SpectrumBlocks.STRIPPED_RED_LOG);
	public static final DeferredBlock<Block> WHITE_LOG = registerColoredLog("white_log", InkColors.WHITE, SpectrumBlocks.STRIPPED_WHITE_LOG);
	public static final DeferredBlock<Block> YELLOW_LOG = registerColoredLog("yellow_log", InkColors.YELLOW, SpectrumBlocks.STRIPPED_YELLOW_LOG);

	public static DeferredBlock<Block> registerColoredStrippedWood(String name, DeferredBlock<Block> logBlock, InkColor color) {
		return register(wood(blockWithItem(name, () -> new ColoredStrippedWoodBlock(copyWithMapColor(STRIPPED_OAK_WOOD, logBlock.get().defaultMapColor()), color), color), logBlock));
	}

	public static DeferredBlock<Block> registerColoredWood(String name, DeferredBlock<Block> logBlock, InkColor color) {
		return register(wood(blockWithItem(name, () -> new ColoredWoodBlock(copyWithMapColor(OAK_WOOD, logBlock.get().defaultMapColor()), color), color), logBlock));
	}

	public static final DeferredBlock<Block> BLACK_WOOD = registerColoredWood("black_wood", SpectrumBlocks.BLACK_LOG, InkColors.BLACK);
	public static final DeferredBlock<Block> BLUE_WOOD = registerColoredWood("blue_wood", SpectrumBlocks.BLUE_LOG, InkColors.BLUE);
	public static final DeferredBlock<Block> BROWN_WOOD = registerColoredWood("brown_wood", SpectrumBlocks.BROWN_LOG, InkColors.BROWN);
	public static final DeferredBlock<Block> CYAN_WOOD = registerColoredWood("cyan_wood", SpectrumBlocks.CYAN_LOG, InkColors.CYAN);
	public static final DeferredBlock<Block> GRAY_WOOD = registerColoredWood("gray_wood", SpectrumBlocks.GRAY_LOG, InkColors.GRAY);
	public static final DeferredBlock<Block> GREEN_WOOD = registerColoredWood("green_wood", SpectrumBlocks.GREEN_LOG, InkColors.GREEN);
	public static final DeferredBlock<Block> LIGHT_BLUE_WOOD = registerColoredWood("light_blue_wood", SpectrumBlocks.LIGHT_BLUE_LOG, InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_GRAY_WOOD = registerColoredWood("light_gray_wood", SpectrumBlocks.LIGHT_GRAY_LOG, InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIME_WOOD = registerColoredWood("lime_wood", SpectrumBlocks.LIME_LOG, InkColors.LIME);
	public static final DeferredBlock<Block> MAGENTA_WOOD = registerColoredWood("magenta_wood", SpectrumBlocks.MAGENTA_LOG, InkColors.MAGENTA);
	public static final DeferredBlock<Block> ORANGE_WOOD = registerColoredWood("orange_wood", SpectrumBlocks.ORANGE_LOG, InkColors.ORANGE);
	public static final DeferredBlock<Block> PINK_WOOD = registerColoredWood("pink_wood", SpectrumBlocks.PINK_LOG, InkColors.PINK);
	public static final DeferredBlock<Block> PURPLE_WOOD = registerColoredWood("purple_wood", SpectrumBlocks.PURPLE_LOG, InkColors.PURPLE);
	public static final DeferredBlock<Block> RED_WOOD = registerColoredWood("red_wood", SpectrumBlocks.RED_LOG, InkColors.RED);
	public static final DeferredBlock<Block> WHITE_WOOD = registerColoredWood("white_wood", SpectrumBlocks.WHITE_LOG, InkColors.WHITE);
	public static final DeferredBlock<Block> YELLOW_WOOD = registerColoredWood("yellow_wood", SpectrumBlocks.YELLOW_LOG, InkColors.YELLOW);

	public static final DeferredBlock<Block> STRIPPED_BLACK_WOOD = registerColoredStrippedWood("stripped_black_wood", SpectrumBlocks.STRIPPED_BLACK_LOG, InkColors.BLACK);
	public static final DeferredBlock<Block> STRIPPED_BLUE_WOOD = registerColoredStrippedWood("stripped_blue_wood", SpectrumBlocks.STRIPPED_BLUE_LOG, InkColors.BLUE);
	public static final DeferredBlock<Block> STRIPPED_BROWN_WOOD = registerColoredStrippedWood("stripped_brown_wood", SpectrumBlocks.STRIPPED_BROWN_LOG, InkColors.BROWN);
	public static final DeferredBlock<Block> STRIPPED_CYAN_WOOD = registerColoredStrippedWood("stripped_cyan_wood", SpectrumBlocks.STRIPPED_CYAN_LOG, InkColors.CYAN);
	public static final DeferredBlock<Block> STRIPPED_GRAY_WOOD = registerColoredStrippedWood("stripped_gray_wood", SpectrumBlocks.STRIPPED_GRAY_LOG, InkColors.GRAY);
	public static final DeferredBlock<Block> STRIPPED_GREEN_WOOD = registerColoredStrippedWood("stripped_green_wood", SpectrumBlocks.STRIPPED_GREEN_LOG, InkColors.GREEN);
	public static final DeferredBlock<Block> STRIPPED_LIGHT_BLUE_WOOD = registerColoredStrippedWood("stripped_light_blue_wood", SpectrumBlocks.STRIPPED_LIGHT_BLUE_LOG, InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> STRIPPED_LIGHT_GRAY_WOOD = registerColoredStrippedWood("stripped_light_gray_wood", SpectrumBlocks.STRIPPED_LIGHT_GRAY_LOG, InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> STRIPPED_LIME_WOOD = registerColoredStrippedWood("stripped_lime_wood", SpectrumBlocks.STRIPPED_LIME_LOG, InkColors.LIME);
	public static final DeferredBlock<Block> STRIPPED_MAGENTA_WOOD = registerColoredStrippedWood("stripped_magenta_wood", SpectrumBlocks.STRIPPED_MAGENTA_LOG, InkColors.MAGENTA);
	public static final DeferredBlock<Block> STRIPPED_ORANGE_WOOD = registerColoredStrippedWood("stripped_orange_wood", SpectrumBlocks.STRIPPED_ORANGE_LOG, InkColors.ORANGE);
	public static final DeferredBlock<Block> STRIPPED_PINK_WOOD = registerColoredStrippedWood("stripped_pink_wood", SpectrumBlocks.STRIPPED_PINK_LOG, InkColors.PINK);
	public static final DeferredBlock<Block> STRIPPED_PURPLE_WOOD = registerColoredStrippedWood("stripped_purple_wood", SpectrumBlocks.STRIPPED_PURPLE_LOG, InkColors.PURPLE);
	public static final DeferredBlock<Block> STRIPPED_RED_WOOD = registerColoredStrippedWood("stripped_red_wood", SpectrumBlocks.STRIPPED_RED_LOG, InkColors.RED);
	public static final DeferredBlock<Block> STRIPPED_WHITE_WOOD = registerColoredStrippedWood("stripped_white_wood", SpectrumBlocks.STRIPPED_WHITE_LOG, InkColors.WHITE);
	public static final DeferredBlock<Block> STRIPPED_YELLOW_WOOD = registerColoredStrippedWood("stripped_yellow_wood", SpectrumBlocks.STRIPPED_YELLOW_LOG, InkColors.YELLOW);

	public static DeferredBlock<Block> registerColoredLeaves(String name, InkColor color) {
		return register(singleton(blockWithItem(name, () -> new ColoredLeavesBlock(copyWithMapColor(OAK_LEAVES, color.getDyeColor().orElse(DyeColor.LIME).getMapColor()), color), color), TexturedModel.LEAVES));
	}

	public static final DeferredBlock<Block> BLACK_LEAVES = registerColoredLeaves("black_leaves", InkColors.BLACK);
	public static final DeferredBlock<Block> BLUE_LEAVES = registerColoredLeaves("blue_leaves", InkColors.BLUE);
	public static final DeferredBlock<Block> BROWN_LEAVES = registerColoredLeaves("brown_leaves", InkColors.BROWN);
	public static final DeferredBlock<Block> CYAN_LEAVES = registerColoredLeaves("cyan_leaves", InkColors.CYAN);
	public static final DeferredBlock<Block> GRAY_LEAVES = registerColoredLeaves("gray_leaves", InkColors.GRAY);
	public static final DeferredBlock<Block> GREEN_LEAVES = registerColoredLeaves("green_leaves", InkColors.GREEN);
	public static final DeferredBlock<Block> LIGHT_BLUE_LEAVES = registerColoredLeaves("light_blue_leaves", InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_GRAY_LEAVES = registerColoredLeaves("light_gray_leaves", InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIME_LEAVES = registerColoredLeaves("lime_leaves", InkColors.LIME);
	public static final DeferredBlock<Block> MAGENTA_LEAVES = registerColoredLeaves("magenta_leaves", InkColors.MAGENTA);
	public static final DeferredBlock<Block> ORANGE_LEAVES = registerColoredLeaves("orange_leaves", InkColors.ORANGE);
	public static final DeferredBlock<Block> PINK_LEAVES = registerColoredLeaves("pink_leaves", InkColors.PINK);
	public static final DeferredBlock<Block> PURPLE_LEAVES = registerColoredLeaves("purple_leaves", InkColors.PURPLE);
	public static final DeferredBlock<Block> RED_LEAVES = registerColoredLeaves("red_leaves", InkColors.RED);
	public static final DeferredBlock<Block> WHITE_LEAVES = registerColoredLeaves("white_leaves", InkColors.WHITE);
	public static final DeferredBlock<Block> YELLOW_LEAVES = registerColoredLeaves("yellow_leaves", InkColors.YELLOW);

	public static DeferredBlock<Block> registerGlowBlock(String name, InkColor color) {
		return register(simple(blockWithItem(name, () -> new GlowBlock(settings(color.getDyeColor().orElse(DyeColor.LIME).getMapColor(), SoundType.BASALT, 2.5F).requiresCorrectToolForDrops().lightLevel(state -> 1).hasPostProcess(SpectrumBlocks::always).emissiveRendering(SpectrumBlocks::always), color), color)));
	}

	public static final DeferredBlock<Block> BLACK_GLOWBLOCK = registerGlowBlock("black_glowblock", InkColors.BLACK);
	public static final DeferredBlock<Block> BLUE_GLOWBLOCK = registerGlowBlock("blue_glowblock", InkColors.BLUE);
	public static final DeferredBlock<Block> BROWN_GLOWBLOCK = registerGlowBlock("brown_glowblock", InkColors.BROWN);
	public static final DeferredBlock<Block> CYAN_GLOWBLOCK = registerGlowBlock("cyan_glowblock", InkColors.CYAN);
	public static final DeferredBlock<Block> GRAY_GLOWBLOCK = registerGlowBlock("gray_glowblock", InkColors.GRAY);
	public static final DeferredBlock<Block> GREEN_GLOWBLOCK = registerGlowBlock("green_glowblock", InkColors.GREEN);
	public static final DeferredBlock<Block> LIGHT_BLUE_GLOWBLOCK = registerGlowBlock("light_blue_glowblock", InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_GRAY_GLOWBLOCK = registerGlowBlock("light_gray_glowblock", InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIME_GLOWBLOCK = registerGlowBlock("lime_glowblock", InkColors.LIME);
	public static final DeferredBlock<Block> MAGENTA_GLOWBLOCK = registerGlowBlock("magenta_glowblock", InkColors.MAGENTA);
	public static final DeferredBlock<Block> ORANGE_GLOWBLOCK = registerGlowBlock("orange_glowblock", InkColors.ORANGE);
	public static final DeferredBlock<Block> PINK_GLOWBLOCK = registerGlowBlock("pink_glowblock", InkColors.PINK);
	public static final DeferredBlock<Block> PURPLE_GLOWBLOCK = registerGlowBlock("purple_glowblock", InkColors.PURPLE);
	public static final DeferredBlock<Block> RED_GLOWBLOCK = registerGlowBlock("red_glowblock", InkColors.RED);
	public static final DeferredBlock<Block> WHITE_GLOWBLOCK = registerGlowBlock("white_glowblock", InkColors.WHITE);
	public static final DeferredBlock<Block> YELLOW_GLOWBLOCK = registerGlowBlock("yellow_glowblock", InkColors.YELLOW);

	public static DeferredBlock<Block> registerColoredLightBlock(String name, InkColor color) {
		return register(translucent(blockWithItem(name, () -> new ColoredLightBlock(BlockBehaviour.Properties.ofFullCopy(REDSTONE_LAMP).mapColor(color.getDyeColor().orElse(DyeColor.LIME).getMapColor()), color), color)).withBlockModel((ctx, block) -> {
			ResourceLocation off = TexturedModel.CUBE.create(block, ctx.modelOutput);
			ResourceLocation on = SpectrumModels.COLORED_LAMP_ON.createWithSuffix(block, "_on", SpectrumTextureMaps.innerOuter(block, "_on", block, "_outer"), ctx.modelOutput);
			return MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(BlockStateProperties.LIT, on, off));
		}));
	}

	public static final DeferredBlock<Block> BLACK_LAMP = registerColoredLightBlock("black_lamp", InkColors.BLACK);
	public static final DeferredBlock<Block> BLUE_LAMP = registerColoredLightBlock("blue_lamp", InkColors.BLUE);
	public static final DeferredBlock<Block> BROWN_LAMP = registerColoredLightBlock("brown_lamp", InkColors.BROWN);
	public static final DeferredBlock<Block> CYAN_LAMP = registerColoredLightBlock("cyan_lamp", InkColors.CYAN);
	public static final DeferredBlock<Block> GRAY_LAMP = registerColoredLightBlock("gray_lamp", InkColors.GRAY);
	public static final DeferredBlock<Block> GREEN_LAMP = registerColoredLightBlock("green_lamp", InkColors.GREEN);
	public static final DeferredBlock<Block> LIGHT_BLUE_LAMP = registerColoredLightBlock("light_blue_lamp", InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_GRAY_LAMP = registerColoredLightBlock("light_gray_lamp", InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIME_LAMP = registerColoredLightBlock("lime_lamp", InkColors.LIME);
	public static final DeferredBlock<Block> MAGENTA_LAMP = registerColoredLightBlock("magenta_lamp", InkColors.MAGENTA);
	public static final DeferredBlock<Block> ORANGE_LAMP = registerColoredLightBlock("orange_lamp", InkColors.ORANGE);
	public static final DeferredBlock<Block> PINK_LAMP = registerColoredLightBlock("pink_lamp", InkColors.PINK);
	public static final DeferredBlock<Block> PURPLE_LAMP = registerColoredLightBlock("purple_lamp", InkColors.PURPLE);
	public static final DeferredBlock<Block> RED_LAMP = registerColoredLightBlock("red_lamp", InkColors.RED);
	public static final DeferredBlock<Block> WHITE_LAMP = registerColoredLightBlock("white_lamp", InkColors.WHITE);
	public static final DeferredBlock<Block> YELLOW_LAMP = registerColoredLightBlock("yellow_lamp", InkColors.YELLOW);

	public static DeferredBlock<Block> registerPigmentBlock(String name, InkColor color) {
		return register(simple(blockWithItem(name, () -> new PigmentBlock(settings(color.getDyeColor().orElse(DyeColor.LIME).getMapColor(), SoundType.WOOL, 1.0F), color), color)));
	}

	public static final DeferredBlock<Block> BLACK_BLOCK = registerPigmentBlock("black_block", InkColors.BLACK);
	public static final DeferredBlock<Block> BLUE_BLOCK = registerPigmentBlock("blue_block", InkColors.BLUE);
	public static final DeferredBlock<Block> BROWN_BLOCK = registerPigmentBlock("brown_block", InkColors.BROWN);
	public static final DeferredBlock<Block> CYAN_BLOCK = registerPigmentBlock("cyan_block", InkColors.CYAN);
	public static final DeferredBlock<Block> GRAY_BLOCK = registerPigmentBlock("gray_block", InkColors.GRAY);
	public static final DeferredBlock<Block> GREEN_BLOCK = registerPigmentBlock("green_block", InkColors.GREEN);
	public static final DeferredBlock<Block> LIGHT_BLUE_BLOCK = registerPigmentBlock("light_blue_block", InkColors.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_GRAY_BLOCK = registerPigmentBlock("light_gray_block", InkColors.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIME_BLOCK = registerPigmentBlock("lime_block", InkColors.LIME);
	public static final DeferredBlock<Block> MAGENTA_BLOCK = registerPigmentBlock("magenta_block", InkColors.MAGENTA);
	public static final DeferredBlock<Block> ORANGE_BLOCK = registerPigmentBlock("orange_block", InkColors.ORANGE);
	public static final DeferredBlock<Block> PINK_BLOCK = registerPigmentBlock("pink_block", InkColors.PINK);
	public static final DeferredBlock<Block> PURPLE_BLOCK = registerPigmentBlock("purple_block", InkColors.PURPLE);
	public static final DeferredBlock<Block> RED_BLOCK = registerPigmentBlock("red_block", InkColors.RED);
	public static final DeferredBlock<Block> WHITE_BLOCK = registerPigmentBlock("white_block", InkColors.WHITE);
	public static final DeferredBlock<Block> YELLOW_BLOCK = registerPigmentBlock("yellow_block", InkColors.YELLOW);

	public static DeferredBlock<Block> registerColoredSporeBlossomBlock(String name, InkColor color, ColoredFallingSporeBlossomParticleEffect falling, ColoredSporeBlossomAirParticleEffect air) {
		return register(cutout(singleton(blockWithItem(name, () -> new ColoredSporeBlossomBlock(BlockBehaviour.Properties.ofFullCopy(SPORE_BLOSSOM).mapColor(color.getDyeColor().orElse(DyeColor.LIME).getMapColor()), color, falling, air), color), TexturedModel.createDefault(b -> SpectrumTextureMaps.flowerParticle(b, "", b, ""), SpectrumModels.SPORE_BLOSSOM))));
	}

	public static final DeferredBlock<Block> BLACK_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("black_spore_blossom", InkColors.BLACK, ColoredFallingSporeBlossomParticleEffect.BLACK, ColoredSporeBlossomAirParticleEffect.BLACK);
	public static final DeferredBlock<Block> BLUE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("blue_spore_blossom", InkColors.BLUE, ColoredFallingSporeBlossomParticleEffect.BLUE, ColoredSporeBlossomAirParticleEffect.BLUE);
	public static final DeferredBlock<Block> BROWN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("brown_spore_blossom", InkColors.BROWN, ColoredFallingSporeBlossomParticleEffect.BROWN, ColoredSporeBlossomAirParticleEffect.BROWN);
	public static final DeferredBlock<Block> CYAN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("cyan_spore_blossom", InkColors.CYAN, ColoredFallingSporeBlossomParticleEffect.CYAN, ColoredSporeBlossomAirParticleEffect.CYAN);
	public static final DeferredBlock<Block> GRAY_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("gray_spore_blossom", InkColors.GRAY, ColoredFallingSporeBlossomParticleEffect.GRAY, ColoredSporeBlossomAirParticleEffect.GRAY);
	public static final DeferredBlock<Block> GREEN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("green_spore_blossom", InkColors.GREEN, ColoredFallingSporeBlossomParticleEffect.GREEN, ColoredSporeBlossomAirParticleEffect.GREEN);
	public static final DeferredBlock<Block> LIGHT_BLUE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("light_blue_spore_blossom", InkColors.LIGHT_BLUE, ColoredFallingSporeBlossomParticleEffect.LIGHT_BLUE, ColoredSporeBlossomAirParticleEffect.LIGHT_BLUE);
	public static final DeferredBlock<Block> LIGHT_GRAY_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("light_gray_spore_blossom", InkColors.LIGHT_GRAY, ColoredFallingSporeBlossomParticleEffect.LIGHT_GRAY, ColoredSporeBlossomAirParticleEffect.LIGHT_GRAY);
	public static final DeferredBlock<Block> LIME_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("lime_spore_blossom", InkColors.LIME, ColoredFallingSporeBlossomParticleEffect.LIME, ColoredSporeBlossomAirParticleEffect.LIME);
	public static final DeferredBlock<Block> MAGENTA_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("magenta_spore_blossom", InkColors.MAGENTA, ColoredFallingSporeBlossomParticleEffect.MAGENTA, ColoredSporeBlossomAirParticleEffect.MAGENTA);
	public static final DeferredBlock<Block> ORANGE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("orange_spore_blossom", InkColors.ORANGE, ColoredFallingSporeBlossomParticleEffect.ORANGE, ColoredSporeBlossomAirParticleEffect.ORANGE);
	public static final DeferredBlock<Block> PINK_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("pink_spore_blossom", InkColors.PINK, ColoredFallingSporeBlossomParticleEffect.PINK, ColoredSporeBlossomAirParticleEffect.PINK);
	public static final DeferredBlock<Block> PURPLE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("purple_spore_blossom", InkColors.PURPLE, ColoredFallingSporeBlossomParticleEffect.PURPLE, ColoredSporeBlossomAirParticleEffect.PURPLE);
	public static final DeferredBlock<Block> RED_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("red_spore_blossom", InkColors.RED, ColoredFallingSporeBlossomParticleEffect.RED, ColoredSporeBlossomAirParticleEffect.RED);
	public static final DeferredBlock<Block> WHITE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("white_spore_blossom", InkColors.WHITE, ColoredFallingSporeBlossomParticleEffect.WHITE, ColoredSporeBlossomAirParticleEffect.WHITE);
	public static final DeferredBlock<Block> YELLOW_SPORE_BLOSSOM = registerColoredSporeBlossomBlock("yellow_spore_blossom", InkColors.YELLOW, ColoredFallingSporeBlossomParticleEffect.YELLOW, ColoredSporeBlossomAirParticleEffect.YELLOW);

	public static DeferredBlock<Block> registerShimmerstoneLight(String name, SoundType soundGroup, Supplier<ResourceLocation> outerSupplier) {
		return register(blockWithItem(name, () -> new ShimmerstoneLightBlock(settings(MapColor.NONE, soundGroup, 1.0F).noOcclusion().requiresCorrectToolForDrops().lightLevel(state -> 15).pushReaction(PushReaction.DESTROY)), InkColors.YELLOW).withBlockModel((ctx, block) -> {
			ResourceLocation outer = outerSupplier.get();
			ResourceLocation base = SpectrumModels.SHIMMERSTONE_LIGHT.create(block, SpectrumTextureMaps.innerOuterParticle(SpectrumTextures.SHIMMERSTONE_LIGHT, outer, outer), ctx.modelOutput);
			ResourceLocation mirrored = SpectrumModels.SHIMMERSTONE_LIGHT_MIRRORED.createWithSuffix(block, "_mirrored", SpectrumTextureMaps.innerOuterParticle(SpectrumTextures.SHIMMERSTONE_LIGHT, outer, outer), ctx.modelOutput);
			return MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createNorthDefaultFacingVariantMap()).with(SpectrumModelHelper.createBooleanModelMap(BlockStateProperties.INVERTED, mirrored, base));
		}));
	}

	public static final DeferredBlock<Block> STONE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("stone_shimmerstone_light", SoundType.STONE, () -> SpectrumTextures.STONE_FLAT_LIGHT);
	public static final DeferredBlock<Block> BASALT_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("basalt_shimmerstone_light", SoundType.BASALT, () -> SpectrumTextures.BASALT_FLAT_LIGHT);
	public static final DeferredBlock<Block> CALCITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("calcite_shimmerstone_light", SoundType.CALCITE, () -> SpectrumTextures.CALCITE_FLAT_LIGHT);
	public static final DeferredBlock<Block> DEEPSLATE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("deepslate_shimmerstone_light", SoundType.DEEPSLATE, () -> SpectrumTextures.DEEPSLATE_FLAT_LIGHT);
	public static final DeferredBlock<Block> BLACKSLAG_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("blackslag_shimmerstone_light", SoundType.DEEPSLATE, () -> SpectrumTextures.BLACKSLAG_FLAT_LIGHT);
	public static final DeferredBlock<Block> GRANITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("granite_shimmerstone_light", SoundType.STONE, () -> ModelLocationUtils.getModelLocation(POLISHED_GRANITE));
	public static final DeferredBlock<Block> DIORITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("diorite_shimmerstone_light", SoundType.STONE, () -> ModelLocationUtils.getModelLocation(POLISHED_DIORITE));
	public static final DeferredBlock<Block> ANDESITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight("andesite_shimmerstone_light", SoundType.STONE, () -> ModelLocationUtils.getModelLocation(POLISHED_ANDESITE));

	// CRYSTALLARIEUM
	private static Properties crystallarieumGrowable(Block baseBlock) {
		return BlockBehaviour.Properties.ofFullCopy(baseBlock).strength(1.5F).noOcclusion().forceSolidOn().requiresCorrectToolForDrops().pushReaction(PushReaction.DESTROY);
	}

	public static final DeferredBlock<Block> SMALL_COAL_BUD = register(cluster(blockWithItem("small_coal_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(COAL_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_COAL_BUD = register(cluster(blockWithItem("large_coal_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_COAL_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> COAL_CLUSTER = register(cluster(blockWithItem("coal_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_COAL_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_IRON_BUD = register(cluster(blockWithItem("small_iron_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(IRON_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_IRON_BUD = register(cluster(blockWithItem("large_iron_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_IRON_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> IRON_CLUSTER = register(cluster(blockWithItem("iron_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_IRON_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_GOLD_BUD = register(cluster(blockWithItem("small_gold_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(GOLD_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_GOLD_BUD = register(cluster(blockWithItem("large_gold_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_GOLD_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> GOLD_CLUSTER = register(cluster(blockWithItem("gold_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_GOLD_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_DIAMOND_BUD = register(cluster(blockWithItem("small_diamond_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(DIAMOND_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_DIAMOND_BUD = register(cluster(blockWithItem("large_diamond_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_DIAMOND_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> DIAMOND_CLUSTER = register(cluster(blockWithItem("diamond_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_DIAMOND_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_EMERALD_BUD = register(cluster(blockWithItem("small_emerald_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(EMERALD_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_EMERALD_BUD = register(cluster(blockWithItem("large_emerald_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_EMERALD_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> EMERALD_CLUSTER = register(cluster(blockWithItem("emerald_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_EMERALD_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_REDSTONE_BUD = register(cluster(blockWithItem("small_redstone_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(REDSTONE_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_REDSTONE_BUD = register(cluster(blockWithItem("large_redstone_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_REDSTONE_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> REDSTONE_CLUSTER = register(cluster(blockWithItem("redstone_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_REDSTONE_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_LAPIS_BUD = register(cluster(blockWithItem("small_lapis_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(LAPIS_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.PURPLE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_LAPIS_BUD = register(cluster(blockWithItem("large_lapis_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_LAPIS_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.PURPLE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LAPIS_CLUSTER = register(cluster(blockWithItem("lapis_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_LAPIS_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.PURPLE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_COPPER_BUD = register(cluster(blockWithItem("small_copper_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(COPPER_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_COPPER_BUD = register(cluster(blockWithItem("large_copper_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_COPPER_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> COPPER_CLUSTER = register(cluster(blockWithItem("copper_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_COPPER_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_QUARTZ_BUD = register(cluster(blockWithItem("small_quartz_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(QUARTZ_BLOCK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_QUARTZ_BUD = register(cluster(blockWithItem("large_quartz_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_QUARTZ_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> QUARTZ_CLUSTER = register(cluster(blockWithItem("quartz_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_QUARTZ_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_NETHERITE_SCRAP_BUD = register(cluster(blockWithItem("small_netherite_scrap_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(ANCIENT_DEBRIS), SpectrumClusterBlock.GrowthStage.SMALL), IS.of().fireResistant(), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_NETHERITE_SCRAP_BUD = register(cluster(blockWithItem("large_netherite_scrap_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_NETHERITE_SCRAP_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), IS.of().fireResistant(), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> NETHERITE_SCRAP_CLUSTER = register(cluster(blockWithItem("netherite_scrap_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_NETHERITE_SCRAP_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.of().fireResistant(), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_ECHO_BUD = register(cluster(blockWithItem("small_echo_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(SCULK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_ECHO_BUD = register(cluster(blockWithItem("large_echo_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_ECHO_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> ECHO_CLUSTER = register(cluster(blockWithItem("echo_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_ECHO_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_GLOWSTONE_BUD = register(cluster(blockWithItem("small_glowstone_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(GLOWSTONE).lightLevel(state -> 4), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_GLOWSTONE_BUD = register(cluster(blockWithItem("large_glowstone_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_GLOWSTONE_BUD.get()).lightLevel(state -> 8), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> GLOWSTONE_CLUSTER = register(cluster(blockWithItem("glowstone_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_GLOWSTONE_BUD.get()).lightLevel(state -> 14), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> SMALL_PRISMARINE_BUD = register(cluster(blockWithItem("small_prismarine_bud", () -> new SpectrumClusterBlock(crystallarieumGrowable(SCULK), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> LARGE_PRISMARINE_BUD = register(cluster(blockWithItem("large_prismarine_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_PRISMARINE_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static final DeferredBlock<Block> PRISMARINE_CLUSTER = register(cluster(blockWithItem("prismarine_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.SMALL_PRISMARINE_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.CYAN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));

	public static final DeferredBlock<Block> PURE_COAL_BLOCK = register(simple(burnable(blockWithItem("pure_coal_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(COAL_BLOCK)), InkColors.BROWN), 32000)));
	public static final DeferredBlock<Block> PURE_IRON_BLOCK = register(simple(blockWithItem("pure_iron_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(IRON_BLOCK)), InkColors.BROWN)));
	public static final DeferredBlock<Block> PURE_GOLD_BLOCK = register(simple(blockWithItem("pure_gold_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(GOLD_BLOCK)), InkColors.BROWN)));
	public static final DeferredBlock<Block> PURE_DIAMOND_BLOCK = register(simple(blockWithItem("pure_diamond_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(DIAMOND_BLOCK)), InkColors.CYAN)));
	public static final DeferredBlock<Block> PURE_EMERALD_BLOCK = register(simple(blockWithItem("pure_emerald_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(EMERALD_BLOCK)), InkColors.CYAN)));
	public static final DeferredBlock<Block> PURE_REDSTONE_BLOCK = register(simple(blockWithItem("pure_redstone_block", () -> new PureRedstoneBlock(BlockBehaviour.Properties.ofFullCopy(REDSTONE_BLOCK)), InkColors.RED)));
	public static final DeferredBlock<Block> PURE_LAPIS_BLOCK = register(simple(blockWithItem("pure_lapis_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(LAPIS_BLOCK)), InkColors.PURPLE)));
	public static final DeferredBlock<Block> PURE_COPPER_BLOCK = register(simple(blockWithItem("pure_copper_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(COPPER_BLOCK)), InkColors.BROWN)));
	public static final DeferredBlock<Block> PURE_QUARTZ_BLOCK = register(simple(blockWithItem("pure_quartz_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(QUARTZ_BLOCK)), InkColors.BROWN)));
	public static final DeferredBlock<Block> PURE_GLOWSTONE_BLOCK = register(simple(blockWithItem("pure_glowstone_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(GLOWSTONE)), InkColors.YELLOW)));
	public static final DeferredBlock<Block> PURE_PRISMARINE_BLOCK = register(simple(blockWithItem("pure_prismarine_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PRISMARINE)), InkColors.CYAN)));
	public static final DeferredBlock<Block> PURE_NETHERITE_SCRAP_BLOCK = register(simple(blockWithItem("pure_netherite_scrap_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(ANCIENT_DEBRIS)), IS.of().fireResistant(), InkColors.BROWN)));
	public static final DeferredBlock<Block> PURE_ECHO_BLOCK = register(simple(blockWithItem("pure_echo_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(DIAMOND_BLOCK)), InkColors.BROWN)));

	private static Properties preservationBlock() {
		return settings(MapColor.CLAY, SoundType.STONE, -1.0F, 3600000.0F).instrument(NoteBlockInstrument.BASEDRUM).noLootTable().isValidSpawn(SpectrumBlocks::never).forceSolidOn();
	}

	public static final DeferredBlock<Block> PRESERVATION_CONTROLLER = register(cutout(singletonWithSoup(blockWithItem("preservation_controller", () -> new PreservationControllerBlock(preservationBlock().lightLevel(state -> 1).emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always)), InkColors.BLUE), ModelLocationUtils::getModelLocation)).withPredefinedItemModel());
	public static final DeferredBlock<Block> DIKE_GATE = register(translucent(simple(blockWithItem("dike_gate", () -> new DikeGateBlock(preservationBlock().lightLevel(state -> 3).sound(SoundType.GLASS).noOcclusion().emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always).isRedstoneConductor(SpectrumBlocks::never).isSuffocating(SpectrumBlocks::never).isViewBlocking(SpectrumBlocks::never)), InkColors.BLUE))));
	public static final DeferredBlock<Block> DREAM_GATE = register(translucent(simple(blockWithItem("dream_gate", () -> new DreamGateBlock(preservationBlock().lightLevel(state -> 3).sound(SoundType.GLASS).noOcclusion().emissiveRendering(SpectrumBlocks::always).hasPostProcess(SpectrumBlocks::always).isRedstoneConductor(SpectrumBlocks::never).isSuffocating(SpectrumBlocks::never).isViewBlocking(SpectrumBlocks::never)), InkColors.BLUE))));
	public static final DeferredBlock<Block> INVISIBLE_WALL = register(translucent(singleton(blockWithItem("invisible_wall", () -> new InvisibleWallBlock(preservationBlock().lightLevel(state -> 3).sound(SoundType.GLASS).noOcclusion().isViewBlocking(SpectrumBlocks::never)), InkColors.BLUE), SpectrumTexturedModels.particle(b -> GLASS, ""))).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, SpectrumBlocks.ETHEREAL_PLATFORM.get())));
	public static final DeferredBlock<Block> PRESERVATION_CHEST = register(singletonWithSoup(blockWithItem("preservation_chest", () -> new TreasureChestBlock(preservationBlock()), InkColors.BLUE), ModelLocationUtils::getModelLocation));

	public static final DeferredBlock<Block> DOWNSTONE = register(simple(blockWithItem("downstone", () -> new Block(preservationBlock()), InkColors.BLUE))); // "raw" preservation stone, used in the Deeper Down bottom in place of bedrock

	public static final DeferredBlock<Block> PRESERVATION_STONE = register(blockWithItem("preservation_stone", () -> new Block(preservationBlock()), InkColors.BLUE).withBlockModel((ctx, block) -> {
		List<ResourceLocation> modelIds = new ArrayList<>();
		int[] tops = new int[]{0, 3, 1, 1, 2, 2, 0, 3, 1, 2, 3};
		modelIds.add(SpectrumTexturedModels.cubeBottomTop(b -> b, "", b -> b, "_top_" + tops[0], b -> b, "_bottom").create(block, ctx.modelOutput));
		for (int i = 1; i <= 10; i++) modelIds.add(SpectrumTexturedModels.cubeBottomTop(b -> b, "_" + i, b -> b, "_top_" + tops[i], b -> b, "_bottom").createWithSuffix(block, "_" + i, ctx.modelOutput));
		List<Variant> variants = new ArrayList<>();
		for (VariantProperties.Rotation rotation : VariantProperties.Rotation.values()) {
			variants.add(SpectrumModelHelper.createModelVariant(modelIds.getFirst()).with(VariantProperties.WEIGHT, 10));
			if (rotation != VariantProperties.Rotation.R0) variants.getLast().with(VariantProperties.Y_ROT, rotation);
			for (int i = 1; i <= 10; i++) {
				variants.add(SpectrumModelHelper.createModelVariant(modelIds.get(i)));
				if (rotation != VariantProperties.Rotation.R0) variants.getLast().with(VariantProperties.Y_ROT, rotation);
			}
		}
		return MultiVariantGenerator.multiVariant(block, variants.toArray(Variant[]::new));
	}));
	public static final DeferredBlock<Block> PRESERVATION_STAIRS = register(blockWithItem("preservation_stairs", () -> new StairBlock(SpectrumBlocks.PRESERVATION_STONE.get().defaultBlockState(), preservationBlock()), InkColors.BLUE));
	public static final DeferredBlock<Block> PRESERVATION_SLAB = register(blockWithItem("preservation_slab", () -> new SlabBlock(preservationBlock()), InkColors.BLUE));
	public static final DeferredBlock<Block> PRESERVATION_WALL = register(blockWithItem("preservation_wall", () -> new WallBlock(preservationBlock()), InkColors.BLUE));
	public static final BlockFamily PRESERVATION_STONE_FAMILY = SpectrumModelHelper.registerBlockFamilyExceptBase(new BlockFamily.Builder(SpectrumBlocks.PRESERVATION_STONE.get()).stairs(SpectrumBlocks.PRESERVATION_STAIRS.get()).slab(SpectrumBlocks.PRESERVATION_SLAB.get()).wall(SpectrumBlocks.PRESERVATION_WALL.get()).getFamily(), TexturedModel.CUBE);

	public static final DeferredBlock<Block> POWDER_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("powder_chiseled_preservation_stone", () -> new Block(preservationBlock().lightLevel(state -> 2)), InkColors.BLUE), SpectrumTexturedModels.cubeColumn(b -> b, "", b -> SpectrumBlocks.PRESERVATION_STONE.get(), "_top_generic")));
	public static final DeferredBlock<Block> DIKE_CHISELED_PRESERVATION_STONE = register(simple(blockWithItem("dike_chiseled_preservation_stone", () -> new Block(preservationBlock().lightLevel(state -> 6)), InkColors.BLUE)));
	public static final DeferredBlock<Block> DREAM_CHISELED_PRESERVATION_STONE = register(simple(blockWithItem("dream_chiseled_preservation_stone", () -> new Block(preservationBlock().lightLevel(state -> 6)), InkColors.BLUE)));
	public static final DeferredBlock<Block> DEEP_LIGHT_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("deep_light_chiseled_preservation_stone", () -> new DeepLightBlock(preservationBlock().lightLevel(state -> 2)), InkColors.BLUE), SpectrumTexturedModels.cubeColumn(b -> b, "", b -> SpectrumBlocks.PRESERVATION_STONE.get(), "_top_generic")));

	//TODO not sure which is correct, but this should probably be renamed
	public static final DeferredBlock<Block> TREASURE_ITEM_BOWL = register(cutout(singleton(blockWithItem("item_bowl_enlightenment", () -> new TreasureItemBowlBlock(preservationBlock().noOcclusion().isRedstoneConductor(SpectrumBlocks::never).isSuffocating(SpectrumBlocks::never).isViewBlocking(SpectrumBlocks::never)), InkColors.BLUE), TexturedModel.createDefault(b -> new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(b, "_side")).put(TextureSlot.TOP, TextureMapping.getBlockTexture(b, "_top")).put(TextureSlot.BOTTOM, locate("block/item_bowl_preservation_bottom")).put(SpectrumTextureKeys.INNER, locate("block/item_bowl_preservation_bottom")), SpectrumModels.BOWL))));

	public static final DeferredBlock<Block> DIKE_GATE_FOUNTAIN = register(defaultUpFacing(blockWithItem("dike_gate_fountain", () -> new SpectrumFacingBlock(preservationBlock()), InkColors.BLUE), SpectrumTexturedModels.cubeBottomTopParticle(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.PRESERVATION_STONE.get(), "", b -> SpectrumBlocks.PRESERVATION_STONE.get(), "")));
	public static final DeferredBlock<Block> PRESERVATION_BRICKS = register(simple(blockWithItem("preservation_bricks", () -> new Block(preservationBlock()), InkColors.BLUE)));
	public static final DeferredBlock<Block> SHIMMERING_PRESERVATION_BRICKS = register(simple(blockWithItem("shimmering_preservation_bricks", () -> new Block(preservationBlock().lightLevel(s -> 5)), InkColors.BLUE)));
	public static final DeferredBlock<Block> COURIER_STATUE = register(cutout(blockWithItem("courier_statue", () -> new StatueBlock(preservationBlock()), InkColors.BLUE)).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_top")).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createNorthDefaultHorizontalFacingVariantMap()).with(PropertyDispatch.property(StatueBlock.HALF).select(DoubleBlockHalf.LOWER, SpectrumModelHelper.createModelVariant(block, "_bottom")).select(DoubleBlockHalf.UPPER, SpectrumModelHelper.createModelVariant(block, "_top")))));
	public static final DeferredBlock<Block> MANXI = register(singletonWithSoup(block("manxi", () -> new ManxiBlock(preservationBlock().noOcclusion().noCollission().noLootTable())), (Function<Block, ResourceLocation>) b -> SpectrumModels.MOB_HEAD));

	public static final DeferredBlock<Block> BLACK_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("black_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.BLACK), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BLUE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("blue_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.BLUE), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> BROWN_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("brown_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.BROWN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> CYAN_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("cyan_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.CYAN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> GRAY_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("gray_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.GRAY), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> GREEN_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("green_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.GREEN), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> LIGHT_BLUE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("light_blue_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.LIGHT_BLUE), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> LIGHT_GRAY_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("light_gray_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.LIGHT_GRAY), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> LIME_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("lime_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.LIME), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> MAGENTA_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("magenta_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.MAGENTA), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> ORANGE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("orange_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.ORANGE), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> PINK_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("pink_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.PINK), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> PURPLE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("purple_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.PURPLE), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> RED_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("red_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.RED), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> WHITE_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("white_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.WHITE), TexturedModel.COLUMN_ALT));
	public static final DeferredBlock<Block> YELLOW_CHISELED_PRESERVATION_STONE = register(singleton(blockWithItem("yellow_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.YELLOW), TexturedModel.COLUMN_ALT));

	public static final DeferredBlock<Block> PRESERVATION_GLASS = register(translucent(simple(blockWithItem("preservation_glass", () -> new TransparentBlock(preservationBlock().sound(SoundType.GLASS).noOcclusion().isRedstoneConductor(SpectrumBlocks::never).isSuffocating(SpectrumBlocks::never).isViewBlocking(SpectrumBlocks::never)), InkColors.BLUE))));
	public static final DeferredBlock<Block> TINTED_PRESERVATION_GLASS = register(translucent(simple(blockWithItem("tinted_preservation_glass", () -> new TintedGlassBlock(BlockBehaviour.Properties.ofFullCopy(SpectrumBlocks.PRESERVATION_GLASS.get())), InkColors.BLUE))));
	public static final DeferredBlock<Block> PRESERVATION_ROUNDEL = register(singleton(blockWithItem("preservation_roundel", () -> new PreservationRoundelBlock(preservationBlock().noOcclusion().forceSolidOn()), InkColors.BLUE), SpectrumTexturedModels.ROUNDEL));
	public static final DeferredBlock<Block> PRESERVATION_BLOCK_DETECTOR = register(blockWithItem("preservation_block_detector", () -> new PreservationBlockDetectorBlock(preservationBlock()), InkColors.BLUE).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block, SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.complexOrientable(b -> b, "_side", b -> b, "_top", b -> SpectrumBlocks.PRESERVATION_STONE.get(), "_top_generic", b -> b, "_front", b -> b, "_back", b -> b, "_side").create(block, ctx.modelOutput))).with(SpectrumModelHelper.createNorthDefaultFacingVariantMap())));

	private static Properties shootingStar() {
		return BlockBehaviour.Properties.ofFullCopy(STONE).noOcclusion();
	}

	public static final DeferredBlock<Block> GLISTERING_SHOOTING_STAR = register(cutout(singleton(blockWithItem("glistering_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.GLISTERING), block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE), SpectrumTexturedModels.SHOOTING_STAR)));
	public static final DeferredBlock<Block> FIERY_SHOOTING_STAR = register(cutout(singleton(blockWithItem("fiery_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.FIERY), block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE), SpectrumTexturedModels.SHOOTING_STAR)));
	public static final DeferredBlock<Block> COLORFUL_SHOOTING_STAR = register(cutout(singleton(blockWithItem("colorful_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.COLORFUL), block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE), SpectrumTexturedModels.SHOOTING_STAR)));
	public static final DeferredBlock<Block> PRISTINE_SHOOTING_STAR = register(cutout(singleton(blockWithItem("pristine_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.PRISTINE), block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE), SpectrumTexturedModels.SHOOTING_STAR)));
	public static final DeferredBlock<Block> GEMSTONE_SHOOTING_STAR = register(cutout(singleton(blockWithItem("gemstone_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.GEMSTONE), block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE), SpectrumTexturedModels.SHOOTING_STAR)));
	public static final DeferredBlock<Block> STARDUST_BLOCK = register(simple(blockWithItem("stardust_block", () -> new ColoredFallingBlock(new ColorRGBA(DyeColor.PURPLE.getFireworkColor()), BlockBehaviour.Properties.ofFullCopy(SAND).mapColor(MapColor.COLOR_PURPLE)), IS.of(1, Rarity.UNCOMMON), InkColors.BLACK)));

	public static final DeferredBlock<Block> INCANDESCENT_AMALGAM = register(cutout(singletonWithSoup(blockWithItem("incandescent_amalgam", () -> new IncandescentAmalgamBlock(BlockBehaviour.Properties.of().instabreak().noOcclusion()), block -> new IncandescentAmalgamItem(block, IS.of(16).food(SpectrumFoodComponents.INCANDESCENT_AMALGAM)), InkColors.RED), ModelLocationUtils::getModelLocation)).withBlockItemModel(SpectrumModelHelper::registerBlockTexturedItemModel));

	private static Properties idol(SoundType soundGroup) {
		return settings(MapColor.TERRACOTTA_WHITE, soundGroup, 3.0F).requiresCorrectToolForDrops().noOcclusion();
	}

	public static final DeferredBlock<Block> AXOLOTL_IDOL = register(idol(blockWithItem("axolotl_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.AXOLOTL_IDOL), ParticleTypes.HEART, MobEffects.REGENERATION, 0, 100), InkColors.PINK))); // heals 2 hp / 1 hear
	public static final DeferredBlock<Block> BAT_IDOL = register(idol(blockWithItem("bat_idol", () -> new AoEStatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.BAT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.GLOWING, 0, 200, 8), InkColors.PINK)));
	public static final DeferredBlock<Block> BEE_IDOL = register(idol(blockWithItem("bee_idol", () -> new BonemealingIdolBlock(idol(SpectrumBlockSoundGroups.BEE_IDOL), ParticleTypes.DRIPPING_HONEY), InkColors.PINK)));
	public static final DeferredBlock<Block> BLAZE_IDOL = register(idol(blockWithItem("blaze_idol", () -> new FirestarterIdolBlock(idol(SpectrumBlockSoundGroups.BLAZE_IDOL), ParticleTypes.FLAME), InkColors.PINK)));
	public static final DeferredBlock<Block> CAT_IDOL = register(idol(blockWithItem("cat_idol", () -> new FallDamageNegatingIdolBlock(idol(SpectrumBlockSoundGroups.CAT_IDOL), ParticleTypes.ENCHANTED_HIT), InkColors.PINK)));
	public static final DeferredBlock<Block> CHICKEN_IDOL = register(idol(blockWithItem("chicken_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.CHICKEN_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.SLOW_FALLING, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> COW_IDOL = register(idol(blockWithItem("cow_idol", () -> new MilkingIdolBlock(idol(SpectrumBlockSoundGroups.COW_IDOL), ParticleTypes.ENCHANTED_HIT, 6), InkColors.PINK)));
	public static final DeferredBlock<Block> CREEPER_IDOL = register(idol(blockWithItem("creeper_idol", () -> new ExplosionIdolBlock(idol(SpectrumBlockSoundGroups.CREEPER_IDOL), ParticleTypes.EXPLOSION, 3, false, Explosion.BlockInteraction.DESTROY), InkColors.PINK)));
	public static final DeferredBlock<Block> ENDER_DRAGON_IDOL = register(idol(blockWithItem("ender_dragon_idol", () -> new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.ENDER_DRAGON_IDOL), ParticleTypes.DRAGON_BREATH, EntityType.DRAGON_FIREBALL, SoundEvents.ENDER_DRAGON_SHOOT, 6.0F, 1.1F) {
		@Override
		public Projectile createProjectile(ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
			LivingMarkerEntity markerEntity = new LivingMarkerEntity(SpectrumEntityTypes.LIVING_MARKER, world);
			markerEntity.setPosRaw(position.x(), position.y(), position.z());

			Vec3 targetPosition = Vec3.atCenterOf(mobBlockPos.relative(side, 50));
			var velocity = targetPosition.subtract(markerEntity.position());

			DragonFireball entity = new DragonFireball(world, markerEntity, velocity);

			markerEntity.discard();
			return entity;
		}
	}, InkColors.PINK)));
	public static final DeferredBlock<Block> ENDERMAN_IDOL = register(idol(blockWithItem("enderman_idol", () -> new RandomTeleportingIdolBlock(idol(SpectrumBlockSoundGroups.ENDERMAN_IDOL), ParticleTypes.REVERSE_PORTAL, 16, 16), InkColors.PINK)));
	public static final DeferredBlock<Block> ENDERMITE_IDOL = register(idol(blockWithItem("endermite_idol", () -> new LineTeleportingIdolBlock(idol(SpectrumBlockSoundGroups.ENDERMITE_IDOL), ParticleTypes.REVERSE_PORTAL, 16), InkColors.PINK)));
	public static final DeferredBlock<Block> EVOKER_IDOL = register(idol(blockWithItem("evoker_idol", () -> new EntitySummoningIdolBlock(idol(SpectrumBlockSoundGroups.EVOKER_IDOL), ParticleTypes.ANGRY_VILLAGER, EntityType.VEX) {
		@Override
		public void afterSummon(ServerLevel world, Entity entity) {
			((Vex) entity).setLimitedLife(20 * (30 + world.random.nextInt(90)));
		}
	}, InkColors.PINK)));
	public static final DeferredBlock<Block> FISH_IDOL = register(idol(blockWithItem("fish_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.FISH_IDOL), ParticleTypes.SPLASH, MobEffects.WATER_BREATHING, 0, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> FOX_IDOL = register(idol(blockWithItem("fox_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.FOX_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.DIG_SPEED, 0, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> GHAST_IDOL = register(idol(blockWithItem("ghast_idol", () -> new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.GHAST_IDOL), ParticleTypes.SMOKE, EntityType.FIREBALL, SoundEvents.GHAST_SHOOT, 6.0F, 1.1F) {
		@Override
		public Projectile createProjectile(ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
			LivingMarkerEntity markerEntity = new LivingMarkerEntity(SpectrumEntityTypes.LIVING_MARKER, world);
			markerEntity.setPosRaw(position.x(), position.y(), position.z());

			Vec3 targetPosition = Vec3.atCenterOf(mobBlockPos.relative(side, 50));
			var velocity = targetPosition.subtract(markerEntity.position());

			LargeFireball entity = new LargeFireball(world, markerEntity, velocity, 1);

			markerEntity.discard();
			return entity;
		}
	}, InkColors.PINK)));
	public static final DeferredBlock<Block> GLOW_SQUID_IDOL = register(idol(blockWithItem("glow_squid_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.GLOW_SQUID_IDOL), ParticleTypes.GLOW_SQUID_INK, MobEffects.GLOWING, 0, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> GOAT_IDOL = register(idol(blockWithItem("goat_idol", () -> new KnockbackIdolBlock(idol(SpectrumBlockSoundGroups.GOAT_IDOL), ParticleTypes.ENCHANTED_HIT, 5.0F, 0.5F), InkColors.PINK))); // knocks mostly sideways
	public static final DeferredBlock<Block> GUARDIAN_IDOL = register(idol(blockWithItem("guardian_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.GUARDIAN_IDOL), ParticleTypes.BUBBLE, MobEffects.DIG_SLOWDOWN, 2, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> HORSE_IDOL = register(idol(blockWithItem("horse_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.HORSE_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.DAMAGE_BOOST, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> ILLUSIONER_IDOL = register(idol(blockWithItem("illusioner_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.ILLUSIONER_IDOL), ParticleTypes.ANGRY_VILLAGER, MobEffects.INVISIBILITY, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> OCELOT_IDOL = register(idol(blockWithItem("ocelot_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.OCELOT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.NIGHT_VISION, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> PARROT_IDOL = register(idol(blockWithItem("parrot_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.PARROT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.ABSORPTION, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> PHANTOM_IDOL = register(idol(blockWithItem("phantom_idol", () -> new InsomniaIdolBlock(idol(SpectrumBlockSoundGroups.PHANTOM_IDOL), ParticleTypes.POOF, 24000), InkColors.PINK))); // +1 ingame day without sleep
	public static final DeferredBlock<Block> PIG_IDOL = register(idol(blockWithItem("pig_idol", () -> new FeedingIdolBlock(idol(SpectrumBlockSoundGroups.PIG_IDOL), ParticleTypes.INSTANT_EFFECT, 6), InkColors.PINK)));
	public static final DeferredBlock<Block> PIGLIN_IDOL = register(idol(blockWithItem("piglin_idol", () -> new PiglinTradeIdolBlock(idol(SpectrumBlockSoundGroups.PIGLIN_IDOL), ParticleTypes.HEART), InkColors.PINK)));
	public static final DeferredBlock<Block> POLAR_BEAR_IDOL = register(idol(blockWithItem("polar_bear_idol", () -> new FreezingIdolBlock(idol(SpectrumBlockSoundGroups.POLAR_BEAR_IDOL), ParticleTypes.SNOWFLAKE), InkColors.PINK)));
	public static final DeferredBlock<Block> PUFFERFISH_IDOL = register(idol(blockWithItem("pufferfish_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.PUFFERFISH_IDOL), ParticleTypes.SPLASH, MobEffects.CONFUSION, 0, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> RABBIT_IDOL = register(idol(blockWithItem("rabbit_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.RABBIT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.JUMP, 3, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> SHEEP_IDOL = register(idol(blockWithItem("sheep_idol", () -> new ShearingIdolBlock(idol(SpectrumBlockSoundGroups.SHEEP_IDOL), ParticleTypes.ENCHANTED_HIT, 6), InkColors.PINK)));
	public static final DeferredBlock<Block> SHULKER_IDOL = register(idol(blockWithItem("shulker_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.SHULKER_IDOL), ParticleTypes.END_ROD, MobEffects.LEVITATION, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> SILVERFISH_IDOL = register(idol(blockWithItem("silverfish_idol", () -> new SilverfishInsertingIdolBlock(idol(SpectrumBlockSoundGroups.SILVERFISH_IDOL), ParticleTypes.EXPLOSION), InkColors.PINK)));
	public static final DeferredBlock<Block> SKELETON_IDOL = register(idol(blockWithItem("skeleton_idol", () -> new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.SKELETON_IDOL), ParticleTypes.INSTANT_EFFECT, EntityType.ARROW, SoundEvents.ARROW_SHOOT, 6.0F, 1.1F) {
		@Override
		public Projectile createProjectile(ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
			Arrow arrowEntity = new Arrow(world, position.x(), position.y(), position.z(), ItemStack.EMPTY, null);
			arrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
			return arrowEntity;
		}
	}, InkColors.PINK)));
	public static final DeferredBlock<Block> SLIME_IDOL = register(idol(blockWithItem("slime_idol", () -> new SlimeSizingIdolBlock(idol(SpectrumBlockSoundGroups.SLIME_IDOL), ParticleTypes.ITEM_SLIME, 6, 8), InkColors.PINK)));
	public static final DeferredBlock<Block> SNOW_GOLEM_IDOL = register(idol(blockWithItem("snow_golem_idol", () -> new ProjectileIdolBlock(idol(SpectrumBlockSoundGroups.SNOW_GOLEM_IDOL), ParticleTypes.SNOWFLAKE, EntityType.SNOWBALL, SoundEvents.ARROW_SHOOT, 3.0F, 1.1F) {
		@Override
		public Projectile createProjectile(ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
			world.playSound(null, mobBlockPos.getX(), mobBlockPos.getY(), mobBlockPos.getZ(), SoundEvents.SNOW_GOLEM_SHOOT, SoundSource.BLOCKS, 1.0F, 0.4F / world.random.nextFloat() * 0.4F + 0.8F);
			return new Snowball(world, position.x(), position.y(), position.z());
		}
	}, InkColors.PINK)));
	public static final DeferredBlock<Block> SPIDER_IDOL = register(idol(blockWithItem("spider_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.SPIDER_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.POISON, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> SQUID_IDOL = register(idol(blockWithItem("squid_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.SQUID_IDOL), ParticleTypes.SQUID_INK, MobEffects.BLINDNESS, 0, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> STRAY_IDOL = register(idol(blockWithItem("stray_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.STRAY_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.MOVEMENT_SLOWDOWN, 2, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> STRIDER_IDOL = register(idol(blockWithItem("strider_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.STRIDER_IDOL), ParticleTypes.DRIPPING_LAVA, MobEffects.FIRE_RESISTANCE, 0, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> TURTLE_IDOL = register(idol(blockWithItem("turtle_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.TURTLE_IDOL), ParticleTypes.DRIPPING_WATER, MobEffects.DAMAGE_RESISTANCE, 1, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> WITCH_IDOL = register(idol(blockWithItem("witch_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.WITCH_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.WEAKNESS, 0, 200), InkColors.PINK)));
	public static final DeferredBlock<Block> WITHER_IDOL = register(idol(blockWithItem("wither_idol", () -> new ExplosionIdolBlock(idol(SpectrumBlockSoundGroups.WITHER_IDOL), ParticleTypes.EXPLOSION, 7.0F, true, Explosion.BlockInteraction.DESTROY), InkColors.PINK)));
	public static final DeferredBlock<Block> WITHER_SKELETON_IDOL = register(idol(blockWithItem("wither_skeleton_idol", () -> new StatusEffectIdolBlock(idol(SpectrumBlockSoundGroups.WITHER_SKELETON_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.WITHER, 0, 100), InkColors.PINK)));
	public static final DeferredBlock<Block> ZOMBIE_IDOL = register(idol(blockWithItem("zombie_idol", () -> new VillagerConvertingIdolBlock(idol(SpectrumBlockSoundGroups.ZOMBIE_IDOL), ParticleTypes.ENCHANTED_HIT), InkColors.PINK)));

	// FLUIDS
	private static Properties fluid(MapColor mapColor) {
		return settings(mapColor, SoundType.EMPTY, 100.0F).replaceable().noCollission().pushReaction(PushReaction.DESTROY).noLootTable().liquid();
	}

	public static final DeferredBlock<Block> LIQUID_CRYSTAL = register(singleton(block("liquid_crystal", () -> new LiquidCrystalFluidBlock(SpectrumFluids.LIQUID_CRYSTAL.get(), SpectrumBlocks.BLAZING_CRYSTAL.get().defaultBlockState(), fluid(MapColor.CRIMSON_STEM).lightLevel((state) -> LiquidCrystalFluidBlock.LUMINANCE).replaceable())), SpectrumTexturedModels.particle(b -> b, "_still")));
	public static final DeferredBlock<Block> GOO = register(singleton(block("goo", () -> new GooFluidBlock(SpectrumFluids.GOO.get(), MUD.defaultBlockState(), fluid(MapColor.TERRACOTTA_BROWN).replaceable())), SpectrumTexturedModels.particle(b -> b, "_still")));
	public static final DeferredBlock<Block> MIDNIGHT_SOLUTION = register(singleton(block("midnight_solution", () -> new MidnightSolutionFluidBlock(SpectrumFluids.MIDNIGHT_SOLUTION.get(), SpectrumBlocks.BLACK_MATERIA.get().defaultBlockState(), fluid(MapColor.WARPED_STEM).replaceable())), SpectrumTexturedModels.particle(b -> b, "_still")));
	public static final DeferredBlock<Block> DRAGONROT = register(singleton(block("dragonrot", () -> new DragonrotFluidBlock(SpectrumFluids.DRAGONROT.get(), BLACKSTONE.defaultBlockState(), fluid(MapColor.ICE).lightLevel((state) -> 15).replaceable())), SpectrumTexturedModels.particle(b -> b, "_still")));

	static boolean never(BlockState state, BlockGetter world, BlockPos pos, EntityType<?> type) {
		return false;
	}

	static boolean always(BlockState state, BlockGetter world, BlockPos pos) {
		return true;
	}

	static boolean never(BlockState state, BlockGetter world, BlockPos pos) {
		return false;
	}

	public static <T extends Block> DeferredBlock<Block> register(BlockRegistrar<T> registrar) {
		return Objects.requireNonNull(registrar.holder(), "Attempted to register a null block");
	}

	public static <T extends Block> BlockRegistrar<T> block(String name, Supplier<T> blockFactory) {
		return new BlockRegistrar<T>(name).withBlock(blockFactory);
	}

	public static <T extends Block> BlockRegistrar<T> blockWithItem(String name, Supplier<T> block, InkColor color) {
		return blockWithItem(name, block, IS.of(), color);
	}

	public static <T extends Block> BlockRegistrar<T> blockWithItem(String name, Supplier<T> block, Item.Properties settings, InkColor color) {
		return blockWithItem(name, block, b -> new BlockItem(b, settings), color);
	}

	public static <T extends Block> BlockRegistrar<T> blockWithItem(String name, Supplier<T> block, Function<T, Item> itemFactory, InkColor color) {
		return block(name, block).withItem(itemFactory, color);
	}

	public static <T extends Block> BlockRegistrar<T> cutout(BlockRegistrar<T> registrar) {
		return registrar.withCutoutRenderLayer();
	}

	public static <T extends Block> BlockRegistrar<T> mippedCutout(BlockRegistrar<T> registrar) {
		return registrar.withMippedCutoutRenderLayer();
	}

	public static <T extends Block> BlockRegistrar<T> translucent(BlockRegistrar<T> registrar) {
		return registrar.withTranslucentRenderLayer();
	}

	public static <T extends Block> BlockRegistrar<T> simple(BlockRegistrar<T> registrar) {
		return singleton(registrar, TexturedModel.CUBE);
	}

	public static <T extends Block> BlockRegistrar<T> simpleMirrored(BlockRegistrar<T> registrar) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createMirroredVariantsSupplier(block, TexturedModel.CUBE, TexturedModel.CUBE_MIRRORED, ctx.modelOutput));
	}

	public static <T extends Block> BlockRegistrar<T> singleton(BlockRegistrar<T> registrar, TexturedModel.Provider factory) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(ctx, block, factory));
	}

	public static <T extends Block> BlockRegistrar<T> singletonWithSoup(BlockRegistrar<T> registrar, Function<Block, ResourceLocation> modelIdSupplier) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, modelIdSupplier.apply(block)));
	}

	public static <T extends Block> BlockRegistrar<T> parented(BlockRegistrar<T> registrar, UnaryOperator<Block> parent) {
		return registrar.withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, parent.apply(block))).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, ModelLocationUtils.getModelLocation(parent.apply(block))));
	}

	public static <T extends Block> BlockRegistrar<T> axisRotated(BlockRegistrar<T> registrar, TexturedModel.Provider factory) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(ctx, block, factory).with(SpectrumModelHelper.createAxisRotatedVariantMap()));
	}

	public static <T extends Block> BlockRegistrar<T> defaultUpFacing(BlockRegistrar<T> registrar, TexturedModel.Provider factory) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(ctx, block, factory).with(SpectrumModelHelper.createUpDefaultFacingVariantMap()));
	}

	public static <T extends Block> BlockRegistrar<T> defaultUpFacingGetter(BlockRegistrar<T> registrar, Function<Block, ResourceLocation> modelIdGetter) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, modelIdGetter.apply(block)).with(SpectrumModelHelper.createUpDefaultFacingVariantMap()));
	}

	public static <T extends Block> BlockRegistrar<T> defaultNorthHorizontalFacing(BlockRegistrar<T> registrar, Function<Block, ResourceLocation> modelIdGetter) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, modelIdGetter.apply(block)).with(SpectrumModelHelper.createNorthDefaultHorizontalFacingVariantMap()));
	}

	public static <T extends Block> BlockRegistrar<T> defaultSouthHorizontalFacing(BlockRegistrar<T> registrar, Function<Block, ResourceLocation> modelIdGetter) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, modelIdGetter.apply(block)).with(SpectrumModelHelper.createSouthDefaultHorizontalFacingVariantMap()));
	}

	public static <T extends Block> BlockRegistrar<T> defaultWestHorizontalFacing(BlockRegistrar<T> registrar, Function<Block, ResourceLocation> modelIdGetter) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, modelIdGetter.apply(block)).with(SpectrumModelHelper.createWestDefaultHorizontalFacingVariantMap()));
	}

	public static <T extends Block> BlockRegistrar<T> defaultEastHorizontalFacing(BlockRegistrar<T> registrar, Function<Block, ResourceLocation> modelIdGetter) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, modelIdGetter.apply(block)).with(SpectrumModelHelper.createEastDefaultHorizontalFacingVariantMap()));
	}

	public static <T extends Block> BlockRegistrar<T> cross(BlockRegistrar<T> registrar) {
		return cutout(registrar).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, SpectrumTexturedModels.cross(b -> b, "").create(block, ctx.modelOutput)));
	}

	public static <T extends Block> BlockRegistrar<T> simplePlant(BlockRegistrar<T> registrar) {
		return cross(registrar).withBlockItemModel(SpectrumModelHelper::registerBlockTexturedItemModel);
	}

	public static <T extends FlowerPotBlock> BlockRegistrar<T> pottedPlant(BlockRegistrar<T> registrar, boolean tinted) {
		return cutout(registrar).withBlockModel((ctx, block) -> SpectrumModelHelper.pottedPlantBlockModel(ctx, (FlowerPotBlock) block, tinted));
	}

	public static <T extends Block> BlockRegistrar<T> log(BlockRegistrar<T> registrar) {
		return registrar.withBlockModel(SpectrumModelHelper::logBlockModel);
	}

	public static <T extends Block> BlockRegistrar<T> wood(BlockRegistrar<T> registrar, DeferredBlock<Block> logBlock) {
		return registrar.withBlockModel((ctx, block) -> SpectrumModelHelper.woodBlockModel(ctx, block, logBlock.get()));
	}

	public static <T extends Block> BlockRegistrar<T> snowy(BlockRegistrar<T> registrar, TexturedModel.Provider base, TexturedModel.Provider snowy) {
		return registrar.withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.SNOWY).select(false, SpectrumModelHelper.createHorizontalRotationVariantList(base.create(block, ctx.modelOutput))).select(true, SpectrumModelHelper.createHorizontalRotationVariantList(snowy.createWithSuffix(block, "_snow", ctx.modelOutput)))));
	}

	public static <T extends Block> BlockRegistrar<T> redstoneLamp(BlockRegistrar<T> registrar) {
		return registrar.withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_off")).withBlockModel((ctx, block) -> {
			ResourceLocation off = SpectrumTexturedModels.cubeAll(b -> b, "_off").createWithSuffix(block, "_off", ctx.modelOutput);
			ResourceLocation on = SpectrumTexturedModels.cubeAll(b -> b, "_on").createWithSuffix(block, "_on", ctx.modelOutput);
			return MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(BlockStateProperties.LIT, on, off));
		});
	}

	public static <T extends Block> BlockRegistrar<T> barrellike(BlockRegistrar<T> registrar, UnaryOperator<Block> bottomBlock, String bottomSuffix) {
		return registrar.withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createUpDefaultFacingVariantMap()).with(SpectrumModelHelper.createBooleanModelMap(BlockStateProperties.OPEN, SpectrumTexturedModels.cubeBottomTop(b -> b, "_side", b -> b, "_top_open", bottomBlock, bottomSuffix).createWithSuffix(block, "_open", ctx.modelOutput), SpectrumTexturedModels.cubeBottomTop(b -> b, "_side", b -> b, "_top", bottomBlock, bottomSuffix).create(block, ctx.modelOutput))));
	}

	public static <T extends Block> BlockRegistrar<T> spiritVines(BlockRegistrar<T> registrar) {
		return cutout(registrar).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(SpiritVine.CRYSTALS, SpectrumTexturedModels.cross(b -> b, "_crystals").createWithSuffix(block, "_crystals", ctx.modelOutput), SpectrumTexturedModels.cross(b -> b, "_none").createWithSuffix(block, "_none", ctx.modelOutput))));
	}

	public static <T extends Block> BlockRegistrar<T> idol(BlockRegistrar<T> registrar) {
		return translucent(registrar).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, SpectrumModels.MOB_BLOCK)).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(IdolBlock.COOLDOWN, SpectrumModels.MOB_BLOCK, SpectrumModels.MOB_BLOCK_COOLDOWN)));
	}

	public static <T extends Block> BlockRegistrar<T> pedestal(BlockRegistrar<T> registrar) {
		return cutout(singleton(registrar, TexturedModel.createDefault(b -> new TextureMapping().put(SpectrumTextureKeys.PEDESTAL, TextureMapping.getBlockTexture(b)).put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(b, "_breaking")), SpectrumModels.PEDESTAL)));
	}

	public static <T extends Block> BlockRegistrar<T> sugarStick(BlockRegistrar<T> registrar, UnaryOperator<Block> sugarBlock) {
		return registrar.withItemModel(SpectrumModelHelper::registerItemModel).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.AGE_2).generate(age -> SpectrumModelHelper.createModelVariant(SpectrumTexturedModels.sugarStick(age, sugarBlock).createWithSuffix(block, age.toString(), ctx.modelOutput)))));
	}

	public static <T extends Block> BlockRegistrar<T> detector(BlockRegistrar<T> registrar) {
		return burnable(registrar, 300).withBlockModel((ctx, block) -> MultiVariantGenerator.multiVariant(block).with(SpectrumModelHelper.createBooleanModelMap(BlockStateProperties.INVERTED,
				SpectrumModels.SLAB_DETECTOR.createWithSuffix(block, "_inverted", SpectrumTextureMaps.sideTop(block, "_side", block, "_inverted_top"), ctx.modelOutput),
				SpectrumModels.SLAB_DETECTOR.create(block, SpectrumTextureMaps.sideTop(block, "_side", block, "_top"), ctx.modelOutput))));
	}

	public static <T extends Block> BlockRegistrar<T> burnable(BlockRegistrar<T> registrar, int burnTicks) {
		return registrar.withBurnTime(burnTicks);
	}

	public static <T extends Block> BlockRegistrar<T> orientable(BlockRegistrar<T> registrar) {
		return registrar.withBlockModel((ctx, block) -> {
			ResourceLocation horizontal = ModelTemplates.CUBE_ORIENTABLE.create(block, new TextureMapping().put(TextureSlot.TOP, TextureMapping.getBlockTexture(block, "_top")).put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_side")).put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_front")), ctx.modelOutput);
			ResourceLocation vertical = ModelTemplates.CUBE_ORIENTABLE_VERTICAL.create(block, new TextureMapping().put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_top")).put(TextureSlot.FRONT, TextureMapping.getBlockTexture(block, "_front_vertical")), ctx.modelOutput);
			return MultiVariantGenerator.multiVariant(block).with(PropertyDispatch.property(BlockStateProperties.FACING).select(Direction.DOWN, SpectrumModelHelper.createModelVariant(vertical).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180)).select(Direction.UP, SpectrumModelHelper.createModelVariant(vertical)).select(Direction.NORTH, SpectrumModelHelper.createModelVariant(horizontal)).select(Direction.EAST, SpectrumModelHelper.createModelVariant(horizontal).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)).select(Direction.SOUTH, SpectrumModelHelper.createModelVariant(horizontal).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)).select(Direction.WEST, SpectrumModelHelper.createModelVariant(horizontal).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)));
		});
	}

	public static <T extends Block> BlockRegistrar<T> pylon(BlockRegistrar<T> registrar) {
		return registrar.withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, block, "_head")).withBlockModel((ctx, block) -> {
			ResourceLocation head = ModelLocationUtils.getModelLocation(block, "_head");
			ResourceLocation body = ModelLocationUtils.getModelLocation(block, "_body");
			ResourceLocation waist = ModelLocationUtils.getModelLocation(block, "_waist");
			ResourceLocation foot = ModelLocationUtils.getModelLocation(block, "_foot");
			ResourceLocation end = ModelLocationUtils.getModelLocation(block, "_end");
			ResourceLocation pedestal = SpectrumModels.BALCITE_PYLON_PEDESTAL;
			SpectrumModels.BASE_PYLON_BODY.create(head, SpectrumTextureMaps.sideEnd(head, end), ctx.modelOutput);
			SpectrumModels.BASE_PYLON_BODY.create(body, SpectrumTextureMaps.sideEnd(body, end), ctx.modelOutput);
			SpectrumModels.BASE_PYLON_BODY.create(waist, SpectrumTextureMaps.sideEnd(waist, end), ctx.modelOutput);
			SpectrumModels.BASE_PYLON_BODY.create(foot, SpectrumTextureMaps.sideEnd(foot, end), ctx.modelOutput);
			return MultiPartGenerator.multiPart(block)
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.DOWN).term(PylonBlock.SECTION, PylonBlock.Section.HEAD), SpectrumModelHelper.createModelVariant(head).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.DOWN).term(PylonBlock.SECTION, PylonBlock.Section.BODY), SpectrumModelHelper.createModelVariant(body).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.DOWN).term(PylonBlock.SECTION, PylonBlock.Section.WAIST), SpectrumModelHelper.createModelVariant(waist).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.DOWN).term(PylonBlock.SECTION, PylonBlock.Section.FOOT), SpectrumModelHelper.createModelVariant(foot).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.DOWN).term(PylonBlock.PEDESTAL, true), SpectrumModelHelper.createModelVariant(pedestal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R180))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.UP).term(PylonBlock.SECTION, PylonBlock.Section.HEAD), SpectrumModelHelper.createModelVariant(head))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.UP).term(PylonBlock.SECTION, PylonBlock.Section.BODY), SpectrumModelHelper.createModelVariant(body))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.UP).term(PylonBlock.SECTION, PylonBlock.Section.WAIST), SpectrumModelHelper.createModelVariant(waist))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.UP).term(PylonBlock.SECTION, PylonBlock.Section.FOOT), SpectrumModelHelper.createModelVariant(foot))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.UP).term(PylonBlock.PEDESTAL, true), SpectrumModelHelper.createModelVariant(pedestal))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.NORTH).term(PylonBlock.SECTION, PylonBlock.Section.HEAD), SpectrumModelHelper.createModelVariant(head).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.NORTH).term(PylonBlock.SECTION, PylonBlock.Section.BODY), SpectrumModelHelper.createModelVariant(body).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.NORTH).term(PylonBlock.SECTION, PylonBlock.Section.WAIST), SpectrumModelHelper.createModelVariant(waist).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.NORTH).term(PylonBlock.SECTION, PylonBlock.Section.FOOT), SpectrumModelHelper.createModelVariant(foot).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.NORTH).term(PylonBlock.PEDESTAL, true), SpectrumModelHelper.createModelVariant(pedestal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.SOUTH).term(PylonBlock.SECTION, PylonBlock.Section.HEAD), SpectrumModelHelper.createModelVariant(head).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.SOUTH).term(PylonBlock.SECTION, PylonBlock.Section.BODY), SpectrumModelHelper.createModelVariant(body).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.SOUTH).term(PylonBlock.SECTION, PylonBlock.Section.WAIST), SpectrumModelHelper.createModelVariant(waist).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.SOUTH).term(PylonBlock.SECTION, PylonBlock.Section.FOOT), SpectrumModelHelper.createModelVariant(foot).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.SOUTH).term(PylonBlock.PEDESTAL, true), SpectrumModelHelper.createModelVariant(pedestal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.WEST).term(PylonBlock.SECTION, PylonBlock.Section.HEAD), SpectrumModelHelper.createModelVariant(head).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.WEST).term(PylonBlock.SECTION, PylonBlock.Section.BODY), SpectrumModelHelper.createModelVariant(body).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.WEST).term(PylonBlock.SECTION, PylonBlock.Section.WAIST), SpectrumModelHelper.createModelVariant(waist).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.WEST).term(PylonBlock.SECTION, PylonBlock.Section.FOOT), SpectrumModelHelper.createModelVariant(foot).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.WEST).term(PylonBlock.PEDESTAL, true), SpectrumModelHelper.createModelVariant(pedestal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.EAST).term(PylonBlock.SECTION, PylonBlock.Section.HEAD), SpectrumModelHelper.createModelVariant(head).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.EAST).term(PylonBlock.SECTION, PylonBlock.Section.BODY), SpectrumModelHelper.createModelVariant(body).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.EAST).term(PylonBlock.SECTION, PylonBlock.Section.WAIST), SpectrumModelHelper.createModelVariant(waist).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.EAST).term(PylonBlock.SECTION, PylonBlock.Section.FOOT), SpectrumModelHelper.createModelVariant(foot).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
					.with(Condition.condition().term(BlockStateProperties.FACING, Direction.EAST).term(PylonBlock.PEDESTAL, true), SpectrumModelHelper.createModelVariant(pedestal).with(VariantProperties.X_ROT, VariantProperties.Rotation.R90).with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90));
		});
	}
	
	public static class BlockRegistrar<T extends Block> {

		private final ResourceLocation id;
		private boolean hasBlock = false;
		private boolean hasItem = false;
		@Nullable
		private DeferredBlock<Block> holder = null;
		@Nullable
		private Item item = null;

		public BlockRegistrar(String name) {
			this.id = locate(name);
		}

		public BlockRegistrar<T> withBlock(Supplier<T> blockFactory) {
			if (hasBlock) throw new UnsupportedOperationException("Attempted to register two blocks with id " + id);
			hasBlock = true;
			holder = SpectrumBlocks.COMMON_REGISTRAR.register(id.getPath(), blockFactory);
			return this;
		}

		public BlockRegistrar<T> withItem(Function<T, Item> callback, InkColor color) {
			if (hasItem) throw new UnsupportedOperationException("Attempted to register two items with id " + id);
			hasItem = true;

			//SpectrumItems.ITEM_REGISTRAR.register(id.getPath(), () -> {
			//	ItemColors.ITEM_COLORS.registerColorMapping(item, color);
			//	return callback.apply((T) holder.get());
			//});
			return this;
		}

		// TODO Migrate to datagen once datagen isn't cursed
		public BlockRegistrar<T> withCutoutRenderLayer() {
			SpectrumBlocks.CLIENT_REGISTRAR.defer(() -> {
				Objects.requireNonNull(holder);
				ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.cutout());
			});
			return this;
		}

		public BlockRegistrar<T> withMippedCutoutRenderLayer() {
			SpectrumBlocks.CLIENT_REGISTRAR.defer(() -> {
				Objects.requireNonNull(holder);
				ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.cutoutMipped());
			});
			return this;
		}

		public BlockRegistrar<T> withTranslucentRenderLayer() {
			SpectrumBlocks.CLIENT_REGISTRAR.defer(() -> {
				Objects.requireNonNull(holder);
				ItemBlockRenderTypes.setRenderLayer(holder.get(), RenderType.translucent());
			});
			return this;
		}

		public BlockRegistrar<T> withBlockModel(BiFunction<BlockModelGenerators, Block, BlockStateGenerator> callback) {
			SpectrumModelHelper.BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
				Objects.requireNonNull(holder);
				ctx.blockStateOutput.accept(callback.apply(ctx, holder.get()));
			});
			return this;
		}

		public BlockRegistrar<T> withBlockItemModel(BiConsumer<ItemModelGenerators, ? super T> callback) {
			SpectrumModelHelper.ITEM_MODEL_REGISTRAR.defer(ctx -> {
				if (hasItem) {
					Objects.requireNonNull(holder);
					//callback.accept(ctx, holder.get());
				}
			});
			return this;
		}

		public BlockRegistrar<T> withItemModel(BiConsumer<ItemModelGenerators, Item> callback) {
			SpectrumModelHelper.ITEM_MODEL_REGISTRAR.defer(ctx -> {
				if (hasItem) {
					Objects.requireNonNull(holder);
					callback.accept(ctx, holder.asItem());
				}
			});
			return this;
		}

		public BlockRegistrar<T> withPredefinedItemModel() {
			SpectrumModelHelper.BLOCK_STATE_MODEL_REGISTRAR.defer(ctx -> {
				if (hasItem) {
					Objects.requireNonNull(holder);
					ctx.skipAutoItemBlock(holder.get());
				}
			});
			return this;
		}

		public BlockRegistrar<T> withBurnTime(int burnTicks) {
			if (hasItem) {
				SpectrumItems.BURN_TIMES.add(new Pair<>(holder, burnTicks));
			}
			return this;
		}

		@Nullable
		public DeferredBlock<Block> holder() {
			return holder;
		}
		
		@Nullable
		public Item item() {
			return item;
		}
		
		public ResourceKey<Block> blockKey() {
			return ResourceKey.create(Registries.BLOCK, id);
		}
		
		public ResourceKey<Item> itemKey() {
			return ResourceKey.create(Registries.ITEM, id);
		}
		
	}
	
	public static void registerCommon(IEventBus bus) {
		// All the mob heads
		for (SpectrumSkullType type : SpectrumSkullType.values()) {
			BlockRegistrar<SpectrumSkullBlock> registrar = block(type.getSerializedName() + "_head", () -> new SpectrumSkullBlock(type, BlockBehaviour.Properties.ofFullCopy(SKELETON_SKULL).instrument(NoteBlockInstrument.CUSTOM_HEAD))).withBlockItemModel((ctx, block) -> SpectrumModelHelper.registerParentedItemModel(ctx, block, SpectrumModels.SKULL_ITEM)).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, SpectrumModels.MOB_HEAD));
			DeferredBlock<Block> wallHead = register(block(type.getSerializedName() + "_wall_head", () -> new SpectrumWallSkullBlock(type, BlockBehaviour.Properties.ofFullCopy(SKELETON_SKULL).dropsLike(registrar.holder().get()))).withBlockModel((ctx, block) -> SpectrumModelHelper.createVariantsSupplier(block, SpectrumModels.MOB_HEAD)));
			register(registrar.withItem(block -> new SpectrumSkullBlockItem(block, wallHead.get(), IS.of(), type), InkColors.GRAY));
		}

		SpectrumBlocks.COMMON_REGISTRAR.register(bus);
	}
	
	public static void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
	
}