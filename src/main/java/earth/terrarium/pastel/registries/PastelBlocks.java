package earth.terrarium.pastel.registries;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.color.ItemColors;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.*;
import earth.terrarium.pastel.blocks.amalgam.IncandescentAmalgamBlock;
import earth.terrarium.pastel.blocks.amalgam.IncandescentAmalgamItem;
import earth.terrarium.pastel.blocks.amphora.AmphoraBlock;
import earth.terrarium.pastel.blocks.block_flooder.BlockFlooderBlock;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleBlock;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleItem;
import earth.terrarium.pastel.blocks.chests.BlackHoleChestBlock;
import earth.terrarium.pastel.blocks.chests.CompactingChestBlock;
import earth.terrarium.pastel.blocks.chests.FabricationChestBlock;
import earth.terrarium.pastel.blocks.chests.HeartboundChestBlock;
import earth.terrarium.pastel.blocks.cinderhearth.CinderhearthBlock;
import earth.terrarium.pastel.blocks.conditional.BloodOrchidBlock;
import earth.terrarium.pastel.blocks.conditional.CloakedOreBlock;
import earth.terrarium.pastel.blocks.conditional.GemstoneOreBlock;
import earth.terrarium.pastel.blocks.conditional.MermaidsBrushBlock;
import earth.terrarium.pastel.blocks.conditional.QuitoxicReedsBlock;
import earth.terrarium.pastel.blocks.conditional.RadiatingEnderBlock;
import earth.terrarium.pastel.blocks.conditional.ResonantLilyBlock;
import earth.terrarium.pastel.blocks.conditional.StuckStormStoneBlock;
import earth.terrarium.pastel.blocks.conditional.amaranth.AmaranthBushelBlock;
import earth.terrarium.pastel.blocks.conditional.amaranth.AmaranthCropBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredFenceBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredFenceGateBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLightBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredPlankBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredPressurePlateBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSaplingBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSlabBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSporeBlossomBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStairsBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredWoodenButtonBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.PottedColoredSaplingBlock;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlock;
import earth.terrarium.pastel.blocks.crystallarieum.PastelClusterBlock;
import earth.terrarium.pastel.blocks.decay.BlackMateriaBlock;
import earth.terrarium.pastel.blocks.decay.DecayAwayBlock;
import earth.terrarium.pastel.blocks.decay.FadingBlock;
import earth.terrarium.pastel.blocks.decay.FailingBlock;
import earth.terrarium.pastel.blocks.decay.ForfeitureBlock;
import earth.terrarium.pastel.blocks.decay.RuinBlock;
import earth.terrarium.pastel.blocks.decoration.AlternatePlayerOnlyGlassBlock;
import earth.terrarium.pastel.blocks.decoration.CardinalFacingBlock;
import earth.terrarium.pastel.blocks.decoration.CloverBlock;
import earth.terrarium.pastel.blocks.decoration.CushionBlock;
import earth.terrarium.pastel.blocks.decoration.CushionedCarpetBlock;
import earth.terrarium.pastel.blocks.decoration.CushionedFacingBlock;
import earth.terrarium.pastel.blocks.decoration.DecayingLightBlock;
import earth.terrarium.pastel.blocks.decoration.EtherealPlatformBlock;
import earth.terrarium.pastel.blocks.decoration.FlexLanternBlock;
import earth.terrarium.pastel.blocks.decoration.GemstoneChimeBlock;
import earth.terrarium.pastel.blocks.decoration.GemstoneGlassBlock;
import earth.terrarium.pastel.blocks.decoration.GemstonePlayerOnlyGlassBlock;
import earth.terrarium.pastel.blocks.decoration.GlowBlock;
import earth.terrarium.pastel.blocks.decoration.PastelBedBlock;
import earth.terrarium.pastel.blocks.decoration.PastelFacingBlock;
import earth.terrarium.pastel.blocks.decoration.PastelLineFacingBlock;
import earth.terrarium.pastel.blocks.decoration.PigmentBlock;
import earth.terrarium.pastel.blocks.decoration.ProjectorBlock;
import earth.terrarium.pastel.blocks.decoration.PylonBlock;
import earth.terrarium.pastel.blocks.decoration.RadiantGlassBlock;
import earth.terrarium.pastel.blocks.decoration.ShimmerstoneBlock;
import earth.terrarium.pastel.blocks.decoration.ShimmerstoneLightBlock;
import earth.terrarium.pastel.blocks.decoration.ShinglesBlock;
import earth.terrarium.pastel.blocks.decoration.WandLightBlock;
import earth.terrarium.pastel.blocks.imbrifer.BlackSludgePlantBlock;
import earth.terrarium.pastel.blocks.imbrifer.DragonboneBlock;
import earth.terrarium.pastel.blocks.imbrifer.HummingstoneBlock;
import earth.terrarium.pastel.blocks.imbrifer.PyriteRipperBlock;
import earth.terrarium.pastel.blocks.imbrifer.StrippingLootPillarBlock;
import earth.terrarium.pastel.blocks.imbrifer.WeepingGalaFrondsTipBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.AbyssalVineBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.AloeBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.AshFloraBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.BristleSproutsBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.DoomBloomBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.Dragonjag;
import earth.terrarium.pastel.blocks.imbrifer.flora.GiantMossBallBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.MossBallBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.NightdewBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.SawbladeHollyBushBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.SmallDragonjagBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.SnappingIvyBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.TallDragonjagBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.WeepingGalaFrondsBlock;
import earth.terrarium.pastel.blocks.imbrifer.flora.WeepingGalaSprigBlock;
import earth.terrarium.pastel.blocks.imbrifer.groundcover.AshBlock;
import earth.terrarium.pastel.blocks.imbrifer.groundcover.AshPileBlock;
import earth.terrarium.pastel.blocks.imbrifer.groundcover.BlackslagBlock;
import earth.terrarium.pastel.blocks.imbrifer.groundcover.BlackslagVegetationBlock;
import earth.terrarium.pastel.blocks.imbrifer.groundcover.SlushVegetationBlock;
import earth.terrarium.pastel.blocks.imbrifer.groundcover.VariableHeightBlock;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlock;
import earth.terrarium.pastel.blocks.ender.EnderDropperBlock;
import earth.terrarium.pastel.blocks.ender.EnderHopperBlock;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlock;
import earth.terrarium.pastel.blocks.energy.CrystalApothecaryBlock;
import earth.terrarium.pastel.blocks.farming.TilledShaleClayBlock;
import earth.terrarium.pastel.blocks.farming.TilledSlushBlock;
import earth.terrarium.pastel.blocks.fluid.DragonrotFluidBlock;
import earth.terrarium.pastel.blocks.fluid.HumusFluidBlock;
import earth.terrarium.pastel.blocks.fluid.LiquidCrystalFluidBlock;
import earth.terrarium.pastel.blocks.fluid.MidnightSolutionFluidBlock;
import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelBuddingBlock;
import earth.terrarium.pastel.blocks.gemstone.PastelGemstoneBlock;
import earth.terrarium.pastel.blocks.geology.*;
import earth.terrarium.pastel.blocks.gravity.FloatBlock;
import earth.terrarium.pastel.blocks.gravity.FloatBlockItem;
import earth.terrarium.pastel.blocks.idols.AoEStatusEffectIdolBlock;
import earth.terrarium.pastel.blocks.idols.BonemealingIdolBlock;
import earth.terrarium.pastel.blocks.idols.EntitySummoningIdolBlock;
import earth.terrarium.pastel.blocks.idols.ExplosionIdolBlock;
import earth.terrarium.pastel.blocks.idols.FallDamageNegatingIdolBlock;
import earth.terrarium.pastel.blocks.idols.FeedingIdolBlock;
import earth.terrarium.pastel.blocks.idols.FirestarterIdolBlock;
import earth.terrarium.pastel.blocks.idols.FreezingIdolBlock;
import earth.terrarium.pastel.blocks.idols.InsomniaIdolBlock;
import earth.terrarium.pastel.blocks.idols.KnockbackIdolBlock;
import earth.terrarium.pastel.blocks.idols.LineTeleportingIdolBlock;
import earth.terrarium.pastel.blocks.idols.MilkingIdolBlock;
import earth.terrarium.pastel.blocks.idols.PiglinTradeIdolBlock;
import earth.terrarium.pastel.blocks.idols.ProjectileIdolBlock;
import earth.terrarium.pastel.blocks.idols.RandomTeleportingIdolBlock;
import earth.terrarium.pastel.blocks.idols.ShearingIdolBlock;
import earth.terrarium.pastel.blocks.idols.SilverfishInsertingIdolBlock;
import earth.terrarium.pastel.blocks.idols.SlimeSizingIdolBlock;
import earth.terrarium.pastel.blocks.idols.StatusEffectIdolBlock;
import earth.terrarium.pastel.blocks.idols.VillagerConvertingIdolBlock;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlock;
import earth.terrarium.pastel.blocks.item_roundel.ItemRoundelBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeVineBulbBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeVinePetalBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeVinePlantBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeVineRootsBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeiteLotusBulbBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeiteLotusFlowerBlock;
import earth.terrarium.pastel.blocks.jade_vines.JadeiteLotusStemBlock;
import earth.terrarium.pastel.blocks.jade_vines.NephriteBlossomBulbBlock;
import earth.terrarium.pastel.blocks.jade_vines.NephriteBlossomLeavesBlock;
import earth.terrarium.pastel.blocks.jade_vines.NephriteBlossomStemBlock;
import earth.terrarium.pastel.blocks.lava_sponge.LavaSpongeBlock;
import earth.terrarium.pastel.blocks.lava_sponge.WetLavaSpongeBlock;
import earth.terrarium.pastel.blocks.lava_sponge.WetLavaSpongeItem;
import earth.terrarium.pastel.blocks.memory.MemoryBlock;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.blocks.mob_head.PastelWallSkullBlock;
import earth.terrarium.pastel.blocks.particle_spawner.CreativeParticleSpawnerBlock;
import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerBlock;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlock;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeType;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlock;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockItem;
import earth.terrarium.pastel.blocks.pedestal.PedestalVariants;
import earth.terrarium.pastel.blocks.potion_workshop.PotionWorkshopBlock;
import earth.terrarium.pastel.blocks.present.PresentBlock;
import earth.terrarium.pastel.blocks.present.PresentBlockItem;
import earth.terrarium.pastel.blocks.redstone.BlockBreakerBlock;
import earth.terrarium.pastel.blocks.redstone.BlockDetectorBlock;
import earth.terrarium.pastel.blocks.redstone.BlockLightDetectorBlock;
import earth.terrarium.pastel.blocks.redstone.BlockPlacerBlock;
import earth.terrarium.pastel.blocks.redstone.EnderGlassBlock;
import earth.terrarium.pastel.blocks.redstone.EntityDetectorBlock;
import earth.terrarium.pastel.blocks.redstone.ItemDetectorBlock;
import earth.terrarium.pastel.blocks.redstone.PlayerDetectorBlock;
import earth.terrarium.pastel.blocks.redstone.RedstoneCalculatorBlock;
import earth.terrarium.pastel.blocks.redstone.RedstoneGravityBlock;
import earth.terrarium.pastel.blocks.redstone.RedstoneTimerBlock;
import earth.terrarium.pastel.blocks.redstone.RedstoneTransceiverBlock;
import earth.terrarium.pastel.blocks.redstone.WeatherDetectorBlock;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStar;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStarBlock;
import earth.terrarium.pastel.blocks.shooting_star.ShootingStarItem;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlock;
import earth.terrarium.pastel.blocks.statues.GrotesqueBlock;
import earth.terrarium.pastel.blocks.statues.StatueBlock;
import earth.terrarium.pastel.blocks.structure.DeepLightBlock;
import earth.terrarium.pastel.blocks.structure.DikeGateBlock;
import earth.terrarium.pastel.blocks.structure.DreamGateBlock;
import earth.terrarium.pastel.blocks.structure.InvisibleWallBlock;
import earth.terrarium.pastel.blocks.structure.ManxiBlock;
import earth.terrarium.pastel.blocks.structure.PreservationBlockDetectorBlock;
import earth.terrarium.pastel.blocks.structure.PreservationControllerBlock;
import earth.terrarium.pastel.blocks.structure.PreservationRoundelBlock;
import earth.terrarium.pastel.blocks.structure.TreasureChestBlock;
import earth.terrarium.pastel.blocks.structure.TreasureItemBowlBlock;
import earth.terrarium.pastel.blocks.titration_barrel.TitrationBarrelBlock;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlock;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlockItem;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.blocks.weathering.Weathering;
import earth.terrarium.pastel.blocks.weathering.WeatheringBlock;
import earth.terrarium.pastel.blocks.weathering.WeatheringSlabBlock;
import earth.terrarium.pastel.blocks.weathering.WeatheringStairsBlock;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.compat.ae2.AE2Compat;
import earth.terrarium.pastel.compat.create.CreateCompat;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.entity.PastelEntityTypes;
import earth.terrarium.pastel.entity.entity.LivingMarkerEntity;
import earth.terrarium.pastel.items.conditional.FourLeafCloverItem;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.particle.effect.ColoredFallingSporeBlossomParticleEffect;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.particle.effect.ColoredSporeBlossomAirParticleEffect;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.registries.PastelItems.IS;
import earth.terrarium.pastel.registries.client.PastelTextures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.models.model.ModelLocationUtils;
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
import net.minecraft.world.item.component.BundleContents;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static earth.terrarium.pastel.PastelCommon.locate;
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
import static net.minecraft.world.level.block.Blocks.DEEPSLATE_IRON_ORE;
import static net.minecraft.world.level.block.Blocks.DIAMOND_BLOCK;
import static net.minecraft.world.level.block.Blocks.DIRT;
import static net.minecraft.world.level.block.Blocks.DISPENSER;
import static net.minecraft.world.level.block.Blocks.DROPPER;
import static net.minecraft.world.level.block.Blocks.EMERALD_BLOCK;
import static net.minecraft.world.level.block.Blocks.END_STONE;
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
import static net.minecraft.world.level.block.Blocks.RED_BED;
import static net.minecraft.world.level.block.Blocks.RED_CARPET;
import static net.minecraft.world.level.block.Blocks.RED_WOOL;
import static net.minecraft.world.level.block.Blocks.REPEATER;
import static net.minecraft.world.level.block.Blocks.SAND;
import static net.minecraft.world.level.block.Blocks.SCULK;
import static net.minecraft.world.level.block.Blocks.SHORT_GRASS;
import static net.minecraft.world.level.block.Blocks.SKELETON_SKULL;
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
import static net.minecraft.world.level.block.Blocks.ORANGE_WOOL;
import static net.minecraft.world.level.block.Blocks.MAGENTA_WOOL;
import static net.minecraft.world.level.block.Blocks.YELLOW_WOOL;
import static net.minecraft.world.level.block.Blocks.BLUE_WOOL;
import static net.minecraft.world.level.block.Blocks.LIGHT_BLUE_WOOL;
import static net.minecraft.world.level.block.Blocks.LIGHT_GRAY_WOOL;
import static net.minecraft.world.level.block.Blocks.LIME_WOOL;
import static net.minecraft.world.level.block.Blocks.PURPLE_WOOL;
import static net.minecraft.world.level.block.Blocks.GRAY_WOOL;
import static net.minecraft.world.level.block.Blocks.GREEN_WOOL;
import static net.minecraft.world.level.block.Blocks.PINK_WOOL;
import static net.minecraft.world.level.block.Blocks.BLACK_WOOL;
import static net.minecraft.world.level.block.Blocks.CYAN_WOOL;
import static net.minecraft.world.level.block.Blocks.BROWN_WOOL;
import static net.minecraft.world.level.block.Blocks.litBlockEmission;
import static net.minecraft.world.level.block.Blocks.woodenButton;

@SuppressWarnings({"unused"})
public class PastelBlocks {

    private static Properties settings(MapColor mapColor, SoundType blockSoundGroup, float strength) {
        return BlockBehaviour.Properties.of()
                                        .mapColor(mapColor)
                                        .sound(blockSoundGroup)
                                        .strength(strength);
    }

    private static Properties settings(MapColor mapColor, SoundType blockSoundGroup, float strength, float resistance) {
        return settings(mapColor, blockSoundGroup, strength).explosionResistance(resistance);
    }

    private static Properties craftingBlock(MapColor mapColor, SoundType blockSoundGroup) {
        return settings(mapColor, blockSoundGroup, 5.0F, 8.0F).isRedstoneConductor(PastelBlocks::never)
                                                              .isViewBlocking(PastelBlocks::never)
                                                              .noOcclusion()
                                                              .requiresCorrectToolForDrops();
    }

    public static final DeferredRegister.Blocks COMMON_REGISTRAR = DeferredRegister.createBlocks(PastelCommon.MOD_ID);
//	public static final DeferredRegistrar CLIENT_REGISTRAR = new DeferredRegistrar();

    public static final DeferredBlock<Block> PEDESTAL_BASIC_TOPAZ = register(blockWithItem(
        "pedestal_basic_topaz", () -> new PedestalBlock(
            craftingBlock(MapColor.DIAMOND, PastelBlockSoundGroups.TOPAZ_BLOCK), PedestalVariants.BASIC_TOPAZ),
        block -> new PedestalBlockItem(
            block, IS.of(1), PedestalVariants.BASIC_TOPAZ, "item.pastel.pedestal.tooltip.basic_topaz"), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> PEDESTAL_BASIC_AMETHYST = register(blockWithItem(
        "pedestal_basic_amethyst", () -> new PedestalBlock(
            craftingBlock(MapColor.COLOR_PURPLE, SoundType.AMETHYST), PedestalVariants.BASIC_AMETHYST),
        block -> new PedestalBlockItem(
            block, IS.of(1), PedestalVariants.BASIC_AMETHYST, "item.pastel.pedestal.tooltip.basic_amethyst"),
        InkColors.WHITE
    ));
    public static final DeferredBlock<Block> PEDESTAL_BASIC_CITRINE = register(blockWithItem(
        "pedestal_basic_citrine", () -> new PedestalBlock(
            craftingBlock(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.CITRINE_BLOCK), PedestalVariants.BASIC_CITRINE),
        block -> new PedestalBlockItem(
            block, IS.of(1), PedestalVariants.BASIC_CITRINE, "item.pastel.pedestal.tooltip.basic_citrine"),
        InkColors.WHITE
    ));
    public static final DeferredBlock<Block> PEDESTAL_ALL_BASIC = register(blockWithItem(
        "pedestal_all_basic",
        () -> new PedestalBlock(craftingBlock(MapColor.COLOR_PURPLE, SoundType.AMETHYST), PedestalVariants.CMY),
        block -> new PedestalBlockItem(block, IS.of(1), PedestalVariants.CMY, "item.pastel.pedestal.tooltip.all_basic"),
        InkColors.WHITE
    ));
    public static final DeferredBlock<Block> PEDESTAL_ONYX = register(blockWithItem(
        "pedestal_onyx", () -> new PedestalBlock(
            craftingBlock(MapColor.COLOR_BLACK, PastelBlockSoundGroups.ONYX_BLOCK), PedestalVariants.ONYX),
        block -> new PedestalBlockItem(block, IS.of(1), PedestalVariants.ONYX, "item.pastel.pedestal.tooltip.onyx"),
        InkColors.WHITE
    ));
    public static final DeferredBlock<Block> PEDESTAL_MOONSTONE = register(blockWithItem(
        "pedestal_moonstone", () -> new PedestalBlock(
            craftingBlock(MapColor.SNOW, PastelBlockSoundGroups.MOONSTONE_BLOCK), PedestalVariants.MOONSTONE),
        block -> new PedestalBlockItem(
            block, IS.of(1), PedestalVariants.MOONSTONE, "item.pastel.pedestal.tooltip.moonstone"), InkColors.WHITE
    ));

    public static final DeferredBlock<Block> FUSION_SHRINE_BASALT = register(blockWithItem(
        "fusion_shrine_basalt", () -> new FusionShrineBlock(
            craftingBlock(MapColor.COLOR_BLACK, SoundType.BASALT).lightLevel(
                value -> value.getValue(FusionShrineBlock.LIGHT_LEVEL))), IS.of(1), InkColors.GRAY
    ));
    public static final DeferredBlock<Block> FUSION_SHRINE_CALCITE = register(blockWithItem(
        "fusion_shrine_calcite", () -> new FusionShrineBlock(
            craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE).lightLevel(
                value -> value.getValue(FusionShrineBlock.LIGHT_LEVEL))), IS.of(1), InkColors.GRAY
    ));

    public static final DeferredBlock<Block> ENCHANTER = register(blockWithItem(
        "enchanter", () -> new EnchanterBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(1),
        InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> ITEM_BOWL_BASALT = register(blockWithItem(
        "item_bowl_basalt", () -> new ItemBowlBlock(craftingBlock(MapColor.COLOR_BLACK, SoundType.BASALT)), IS.of(16),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> ITEM_BOWL_CALCITE = register(blockWithItem(
        "item_bowl_calcite", () -> new ItemBowlBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)),
        IS.of(16), InkColors.PINK
    ));
    public static final DeferredBlock<Block> ITEM_ROUNDEL = register(blockWithItem(
        "item_roundel", () -> new ItemRoundelBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)),
        IS.of(16), InkColors.PINK
    ));
    public static final DeferredBlock<Block> POTION_WORKSHOP = register(blockWithItem(
        "potion_workshop", () -> new PotionWorkshopBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)),
        IS.of(1), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> SPIRIT_INSTILLER = register(blockWithItem(
        "spirit_instiller", () -> new SpiritInstillerBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)),
        IS.of(1), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> CRYSTALLARIEUM = register(
        blockWithItem(
            "crystallarieum",
            () -> new CrystallarieumBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(1),
            InkColors.BROWN
        ));
    public static final DeferredBlock<Block> CINDERHEARTH = register(blockWithItem(
        "cinderhearth", () -> new CinderhearthBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)),
        IS.of(1)
          .fireResistant(), InkColors.ORANGE
    ));

    public static final DeferredBlock<Block> COLOR_PICKER = register(blockWithItem(
        "color_picker", () -> new ColorPickerBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)),
        IS.of(8), InkColors.GREEN
    ));
    public static final DeferredBlock<Block> CRYSTAL_APOTHECARY = register(blockWithItem(
        "crystal_apothecary",
        () -> new CrystalApothecaryBlock(craftingBlock(MapColor.TERRACOTTA_WHITE, SoundType.CALCITE)), IS.of(8),
        InkColors.GREEN
    ));

    private static Properties gemstone(MapColor mapColor, SoundType blockSoundGroup, int luminance) {
        return settings(mapColor, blockSoundGroup, 1.5F).forceSolidOn()
                                                        .noOcclusion()
                                                        .lightLevel((state) -> luminance)
                                                        .pushReaction(PushReaction.DESTROY);
    }

    private static Properties gemstoneBlock(MapColor mapColor, SoundType blockSoundGroup) {
        return settings(mapColor, blockSoundGroup, 1.5F).requiresCorrectToolForDrops();
    }

    public static final DeferredBlock<Block> TOPAZ_CLUSTER = register(blockWithItem(
        "topaz_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_CYAN, PastelBlockSoundGroups.TOPAZ_CLUSTER, 8),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> LARGE_TOPAZ_BUD = register(blockWithItem(
        "large_topaz_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_CYAN, PastelBlockSoundGroups.LARGE_TOPAZ_BUD, 6),
            PastelClusterBlock.GrowthStage.LARGE
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> MEDIUM_TOPAZ_BUD = register(blockWithItem(
        "medium_topaz_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_CYAN, PastelBlockSoundGroups.MEDIUM_TOPAZ_BUD, 4),
            PastelClusterBlock.GrowthStage.MEDIUM
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> SMALL_TOPAZ_BUD = register(blockWithItem(
        "small_topaz_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_CYAN, PastelBlockSoundGroups.SMALL_TOPAZ_BUD, 2),
            PastelClusterBlock.GrowthStage.SMALL
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BUDDING_TOPAZ = register(blockWithItem(
        "budding_topaz", () -> new PastelBuddingBlock(
            gemstoneBlock(MapColor.COLOR_CYAN, PastelBlockSoundGroups.TOPAZ_BLOCK).pushReaction(PushReaction.DESTROY)
                                                                                  .randomTicks(),
            PastelBlocks.SMALL_TOPAZ_BUD.get(), PastelBlocks.MEDIUM_TOPAZ_BUD.get(), PastelBlocks.LARGE_TOPAZ_BUD.get(),
            PastelBlocks.TOPAZ_CLUSTER.get(), PastelSounds.BLOCK_TOPAZ_BLOCK_HIT, PastelSounds.BLOCK_TOPAZ_BLOCK_CHIME
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> TOPAZ_BLOCK = register(blockWithItem(
        "topaz_block", () -> new PastelGemstoneBlock(
            gemstoneBlock(MapColor.COLOR_CYAN, PastelBlockSoundGroups.TOPAZ_BLOCK), PastelSounds.BLOCK_TOPAZ_BLOCK_HIT,
            PastelSounds.BLOCK_TOPAZ_BLOCK_CHIME
        ), InkColors.CYAN
    ));

    public static final DeferredBlock<Block> CITRINE_CLUSTER = register(blockWithItem(
        "citrine_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.CITRINE_CLUSTER, 9),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> LARGE_CITRINE_BUD = register(blockWithItem(
        "large_citrine_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.LARGE_CITRINE_BUD, 7),
            PastelClusterBlock.GrowthStage.LARGE
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> MEDIUM_CITRINE_BUD = register(blockWithItem(
        "medium_citrine_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.MEDIUM_CITRINE_BUD, 5),
            PastelClusterBlock.GrowthStage.MEDIUM
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> SMALL_CITRINE_BUD = register(blockWithItem(
        "small_citrine_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.SMALL_CITRINE_BUD, 3),
            PastelClusterBlock.GrowthStage.SMALL
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> BUDDING_CITRINE = register(blockWithItem(
        "budding_citrine", () -> new PastelBuddingBlock(
            gemstoneBlock(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.CITRINE_BLOCK).pushReaction(
                                                                                          PushReaction.DESTROY)
                                                                                      .randomTicks(),
            PastelBlocks.SMALL_CITRINE_BUD.get(), PastelBlocks.MEDIUM_CITRINE_BUD.get(),
            PastelBlocks.LARGE_CITRINE_BUD.get(), PastelBlocks.CITRINE_CLUSTER.get(),
            PastelSounds.BLOCK_CITRINE_BLOCK_HIT, PastelSounds.BLOCK_CITRINE_BLOCK_CHIME
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> CITRINE_BLOCK = register(blockWithItem(
        "citrine_block", () -> new PastelGemstoneBlock(
            gemstoneBlock(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.CITRINE_BLOCK),
            PastelSounds.BLOCK_CITRINE_BLOCK_HIT, PastelSounds.BLOCK_CITRINE_BLOCK_CHIME
        ), InkColors.YELLOW
    ));

    public static final DeferredBlock<Block> ONYX_CLUSTER = register(blockWithItem(
        "onyx_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_BLACK, PastelBlockSoundGroups.ONYX_CLUSTER, 6),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> LARGE_ONYX_BUD = register(blockWithItem(
        "large_onyx_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_BLACK, PastelBlockSoundGroups.LARGE_ONYX_BUD, 5),
            PastelClusterBlock.GrowthStage.LARGE
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MEDIUM_ONYX_BUD = register(blockWithItem(
        "medium_onyx_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_BLACK, PastelBlockSoundGroups.MEDIUM_ONYX_BUD, 3),
            PastelClusterBlock.GrowthStage.MEDIUM
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> SMALL_ONYX_BUD = register(blockWithItem(
        "small_onyx_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_BLACK, PastelBlockSoundGroups.SMALL_ONYX_BUD, 1),
            PastelClusterBlock.GrowthStage.SMALL
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> BUDDING_ONYX = register(blockWithItem(
        "budding_onyx", () -> new PastelBuddingBlock(
            gemstoneBlock(MapColor.COLOR_BLACK, PastelBlockSoundGroups.ONYX_BLOCK).pushReaction(PushReaction.DESTROY)
                                                                                  .randomTicks(),
            PastelBlocks.SMALL_ONYX_BUD.get(), PastelBlocks.MEDIUM_ONYX_BUD.get(), PastelBlocks.LARGE_ONYX_BUD.get(),
            PastelBlocks.ONYX_CLUSTER.get(), PastelSounds.BLOCK_ONYX_BLOCK_HIT, PastelSounds.BLOCK_ONYX_BLOCK_CHIME
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> ONYX_BLOCK = register(blockWithItem(
        "onyx_block", () -> new PastelGemstoneBlock(
            gemstoneBlock(MapColor.COLOR_BLACK, PastelBlockSoundGroups.ONYX_BLOCK), PastelSounds.BLOCK_ONYX_BLOCK_HIT,
            PastelSounds.BLOCK_ONYX_BLOCK_CHIME
        ), InkColors.BLACK
    ));

    public static final DeferredBlock<Block> MOONSTONE_CLUSTER = register(blockWithItem(
        "moonstone_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.SNOW, PastelBlockSoundGroups.MOONSTONE_CLUSTER, 15),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> LARGE_MOONSTONE_BUD = register(blockWithItem(
        "large_moonstone_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.SNOW, PastelBlockSoundGroups.LARGE_MOONSTONE_BUD, 12),
            PastelClusterBlock.GrowthStage.LARGE
        ), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> MEDIUM_MOONSTONE_BUD = register(blockWithItem(
        "medium_moonstone_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.SNOW, PastelBlockSoundGroups.MEDIUM_MOONSTONE_BUD, 9),
            PastelClusterBlock.GrowthStage.MEDIUM
        ), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> SMALL_MOONSTONE_BUD = register(blockWithItem(
        "small_moonstone_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.SNOW, PastelBlockSoundGroups.SMALL_MOONSTONE_BUD, 6),
            PastelClusterBlock.GrowthStage.SMALL
        ), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> BUDDING_MOONSTONE = register(blockWithItem(
        "budding_moonstone", () -> new PastelBuddingBlock(
            gemstoneBlock(MapColor.SNOW, PastelBlockSoundGroups.MOONSTONE_BLOCK).pushReaction(PushReaction.DESTROY)
                                                                                .randomTicks(),
            PastelBlocks.SMALL_MOONSTONE_BUD.get(), PastelBlocks.MEDIUM_MOONSTONE_BUD.get(),
            PastelBlocks.LARGE_MOONSTONE_BUD.get(), PastelBlocks.MOONSTONE_CLUSTER.get(),
            PastelSounds.BLOCK_MOONSTONE_BLOCK_HIT, PastelSounds.BLOCK_MOONSTONE_BLOCK_CHIME
        ), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> MOONSTONE_BLOCK = register(blockWithItem(
        "moonstone_block", () -> new PastelGemstoneBlock(
            gemstoneBlock(MapColor.SNOW, PastelBlockSoundGroups.MOONSTONE_BLOCK),
            PastelSounds.BLOCK_MOONSTONE_BLOCK_HIT, PastelSounds.BLOCK_MOONSTONE_BLOCK_CHIME
        ), InkColors.WHITE
    ));

    public static final DeferredBlock<Block> TOPAZ_POWDER_BLOCK = register(blockWithItem(
        "topaz_powder_block", () -> new ColoredFallingBlock(
            new ColorRGBA(DyeColor.CYAN.getFireworkColor()), Properties.ofFullCopy(SAND)
                                                                       .mapColor(MapColor.COLOR_CYAN)
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_POWDER_BLOCK = register(blockWithItem(
        "amethyst_powder_block", () -> new ColoredFallingBlock(
            new ColorRGBA(DyeColor.MAGENTA.getFireworkColor()), Properties.ofFullCopy(SAND)
                                                                          .mapColor(MapColor.COLOR_MAGENTA)
        ), InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> CITRINE_POWDER_BLOCK = register(blockWithItem(
        "citrine_powder_block", () -> new ColoredFallingBlock(
            new ColorRGBA(DyeColor.YELLOW.getFireworkColor()), Properties.ofFullCopy(SAND)
                                                                         .mapColor(MapColor.COLOR_YELLOW)
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_POWDER_BLOCK = register(blockWithItem(
        "onyx_powder_block", () -> new ColoredFallingBlock(
            new ColorRGBA(DyeColor.BLACK.getFireworkColor()), Properties.ofFullCopy(SAND)
                                                                        .mapColor(MapColor.COLOR_BLACK)
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_POWDER_BLOCK = register(blockWithItem(
        "moonstone_powder_block", () -> new ColoredFallingBlock(
            new ColorRGBA(DyeColor.WHITE.getFireworkColor()), Properties.ofFullCopy(SAND)
                                                                        .mapColor(MapColor.SNOW)
        ), InkColors.WHITE
    ));

    public static final DeferredBlock<Block> VEGETAL_BLOCK = register(burnable(
        blockWithItem(
            "vegetal_block", () -> new Block(settings(MapColor.GRASS, SoundType.FUNGUS, 2.0F).noOcclusion()),
            InkColors.GREEN
        ), 8000
    ));
    public static final DeferredBlock<Block> NEOLITH_BLOCK = register(blockWithItem(
        "neolith_block", () -> new PastelFacingBlock(settings(
            MapColor.COLOR_PURPLE, SoundType.COPPER, 6.0F).requiresCorrectToolForDrops()
                                                          .instrument(NoteBlockInstrument.BASEDRUM)
                                                          .lightLevel(state -> 13)
                                                          .hasPostProcess(PastelBlocks::always)
                                                          .emissiveRendering(PastelBlocks::always)), InkColors.PINK
    ));
    public static final DeferredBlock<Block> BEDROCK_DUST_BLOCK = register(blockWithItem(
        "bedrock_dust_block", () -> new BlockWithTooltip(
            settings(MapColor.STONE, SoundType.STONE, 100.0F, 3600.0F).pushReaction(PushReaction.BLOCK)
                                                                      .requiresCorrectToolForDrops()
                                                                      .instrument(NoteBlockInstrument.BASEDRUM),
            Component.translatable("pastel.tooltip.dragon_and_wither_immune")
        ), IS.of(Rarity.UNCOMMON), InkColors.BLACK
    ));

    public static final DeferredBlock<Block> BISMUTH_CLUSTER = register(blockWithItem(
        "bismuth_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.WARPED_STEM, SoundType.CHAIN, 8), PastelClusterBlock.GrowthStage.CLUSTER),
        IS.of(Rarity.UNCOMMON), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> LARGE_BISMUTH_BUD = register(blockWithItem(
        "large_bismuth_bud", () -> new BismuthBudBlock(
            gemstone(MapColor.WARPED_STEM, SoundType.CHAIN, 6).randomTicks(), PastelClusterBlock.GrowthStage.LARGE,
            PastelBlocks.BISMUTH_CLUSTER.get()
        ), IS.of(Rarity.UNCOMMON), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> SMALL_BISMUTH_BUD = register(blockWithItem(
        "small_bismuth_bud", () -> new BismuthBudBlock(
            gemstone(MapColor.WARPED_STEM, SoundType.CHAIN, 4).randomTicks(), PastelClusterBlock.GrowthStage.SMALL,
            PastelBlocks.LARGE_BISMUTH_BUD.get()
        ), IS.of(Rarity.UNCOMMON), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BISMUTH_BLOCK = register(blockWithItem(
        "bismuth_block", () -> new Block(gemstoneBlock(MapColor.WARPED_STEM, SoundType.CHAIN)), InkColors.CYAN));
    // Once we unfuck the datagen remember to ensure this still has its mirrored form

    // DD BLOCKS
    private static final float BLACKSLAG_HARDNESS = 5.0F;
    private static final float BLACKSLAG_RESISTANCE = 7.0F;

    private static Properties blackslag(SoundType blockSoundGroup) {
        return settings(
            MapColor.COLOR_GRAY, blockSoundGroup, PastelBlocks.BLACKSLAG_HARDNESS,
            PastelBlocks.BLACKSLAG_RESISTANCE
        ).instrument(NoteBlockInstrument.BASEDRUM)
         .requiresCorrectToolForDrops();
    }

    public static final DeferredBlock<Block> BLACKSLAG = register(
        blockWithItem("blackslag", () -> new BlackslagBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
    public static final DeferredBlock<Block> BLACKSLAG_STAIRS = register(blockWithItem(
        "blackslag_stairs", () -> new StairBlock(
            PastelBlocks.BLACKSLAG.get()
                                  .defaultBlockState(), blackslag(SoundType.DEEPSLATE)
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> BLACKSLAG_SLAB = register(
        blockWithItem("blackslag_slab", () -> new SlabBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
    public static final DeferredBlock<Block> BLACKSLAG_WALL = register(
        blockWithItem("blackslag_wall", () -> new WallBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));

    public static final DeferredBlock<Block> INFESTED_BLACKSLAG = register(blockWithItem(
        "infested_blackslag", () -> new InfestedBlock(PastelBlocks.BLACKSLAG.get(), blackslag(SoundType.DEEPSLATE)),
        InkColors.BLACK
    ));

    public static final DeferredBlock<Block> COBBLED_BLACKSLAG = register(
        blockWithItem("cobbled_blackslag", () -> new Block(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
    public static final DeferredBlock<Block> COBBLED_BLACKSLAG_STAIRS = register(blockWithItem(
        "cobbled_blackslag_stairs", () -> new StairBlock(
            PastelBlocks.COBBLED_BLACKSLAG.get()
                                          .defaultBlockState(), blackslag(SoundType.DEEPSLATE)
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> COBBLED_BLACKSLAG_SLAB = register(
        blockWithItem("cobbled_blackslag_slab", () -> new SlabBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));
    public static final DeferredBlock<Block> COBBLED_BLACKSLAG_WALL = register(
        blockWithItem("cobbled_blackslag_wall", () -> new WallBlock(blackslag(SoundType.DEEPSLATE)), InkColors.BLACK));

    public static final DeferredBlock<Block> BLACKSLAG_TILES = register(
        blockWithItem("blackslag_tiles", () -> new Block(blackslag(SoundType.DEEPSLATE_TILES)), InkColors.BLACK));
    public static final DeferredBlock<Block> BLACKSLAG_TILE_STAIRS = register(blockWithItem(
        "blackslag_tile_stairs", () -> new StairBlock(
            PastelBlocks.BLACKSLAG_TILES.get()
                                        .defaultBlockState(), Properties.ofFullCopy(PastelBlocks.BLACKSLAG_TILES.get())
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> BLACKSLAG_TILE_SLAB = register(blockWithItem(
        "blackslag_tile_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.BLACKSLAG_TILES.get())),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> BLACKSLAG_TILE_WALL = register(blockWithItem(
        "blackslag_tile_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.BLACKSLAG_TILES.get())),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> CRACKED_BLACKSLAG_TILES = register(blockWithItem(
        "cracked_blackslag_tiles", () -> new Block(Properties.ofFullCopy(PastelBlocks.BLACKSLAG_TILES.get())),
        InkColors.BLACK
    ));

    public static final DeferredBlock<Block> BLACKSLAG_BRICKS = register(
        blockWithItem("blackslag_bricks", () -> new Block(blackslag(SoundType.DEEPSLATE_BRICKS)), InkColors.BLACK));
    public static final DeferredBlock<Block> BLACKSLAG_BRICK_STAIRS = register(blockWithItem(
        "blackslag_brick_stairs", () -> new StairBlock(
            PastelBlocks.BLACKSLAG_BRICKS.get()
                                         .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.BLACKSLAG_BRICKS.get())
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> BLACKSLAG_BRICK_SLAB = register(blockWithItem(
        "blackslag_brick_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.BLACKSLAG_BRICKS.get())),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> BLACKSLAG_BRICK_WALL = register(blockWithItem(
        "blackslag_brick_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.BLACKSLAG_BRICKS.get())),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> CRACKED_BLACKSLAG_BRICKS = register(blockWithItem(
        "cracked_blackslag_bricks", () -> new Block(Properties.ofFullCopy(PastelBlocks.BLACKSLAG_BRICKS.get())),
        InkColors.BLACK
    ));

    public static final DeferredBlock<Block> POLISHED_BLACKSLAG = register(
        blockWithItem("polished_blackslag", () -> new Block(blackslag(SoundType.POLISHED_DEEPSLATE)), InkColors.BLACK));
    public static final DeferredBlock<Block> POLISHED_BLACKSLAG_STAIRS = register(blockWithItem(
        "polished_blackslag_stairs", () -> new StairBlock(
            PastelBlocks.POLISHED_BLACKSLAG.get()
                                           .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.POLISHED_BLACKSLAG.get())
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> POLISHED_BLACKSLAG_SLAB = register(blockWithItem(
        "polished_blackslag_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.POLISHED_BLACKSLAG.get())),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> POLISHED_BLACKSLAG_WALL = register(blockWithItem(
        "polished_blackslag_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.POLISHED_BLACKSLAG.get())),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> POLISHED_BLACKSLAG_BUTTON = register(blockWithItem(
        "polished_blackslag_button", () -> new ButtonBlock(
            PastelBlockSetTypes.POLISHED_BLACKSLAG, 5, Properties.of()
                                                                 .pushReaction(PushReaction.DESTROY)
                                                                 .noCollission()
                                                                 .strength(0.5F)
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> POLISHED_BLACKSLAG_PRESSURE_PLATE = register(blockWithItem(
        "polished_blackslag_pressure_plate", () -> new PressurePlateBlock(
            PastelBlockSetTypes.POLISHED_BLACKSLAG, Properties.of()
                                                              .mapColor(MapColor.COLOR_BLACK)
                                                              .requiresCorrectToolForDrops()
                                                              .noCollission()
                                                              .strength(0.5F)
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> CHISELED_POLISHED_BLACKSLAG = register(blockWithItem(
        "chiseled_polished_blackslag", () -> new Block(blackslag(SoundType.DEEPSLATE_BRICKS)), InkColors.BLACK));

    public static final DeferredBlock<Block> POLISHED_BLACKSLAG_PILLAR = register(blockWithItem(
        "polished_blackslag_pillar",
        () -> new RotatedPillarBlock(Properties.ofFullCopy(PastelBlocks.BLACKSLAG_BRICKS.get())), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> ANCIENT_CHISELED_POLISHED_BLACKSLAG = register(blockWithItem(
        "ancient_chiseled_polished_blackslag", () -> new Block(blackslag(SoundType.DEEPSLATE_BRICKS)),
        InkColors.BLACK
    ));

    public static final DeferredBlock<Block> SHALE_CLAY = register(blockWithItem(
        "shale_clay", () -> new WeatheringBlock(Weathering.WeatheringLevel.UNAFFECTED, blackslag(SoundType.MUD_BRICKS)),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> TILLED_SHALE_CLAY = register(blockWithItem(
        "tilled_shale_clay", () -> new TilledShaleClayBlock(
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get()), PastelBlocks.SHALE_CLAY.get()
                                                                                         .defaultBlockState()
        ), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> POLISHED_SHALE_CLAY = register(blockWithItem(
        "polished_shale_clay", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem(
        "polished_shale_clay_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.UNAFFECTED, PastelBlocks.POLISHED_SHALE_CLAY.get()
                                                                                   .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_SHALE_CLAY_SLAB = register(blockWithItem(
        "polished_shale_clay_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> EXPOSED_POLISHED_SHALE_CLAY = register(blockWithItem(
        "exposed_polished_shale_clay", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> EXPOSED_POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem(
        "exposed_polished_shale_clay_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.EXPOSED, PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.get()
                                                                                        .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> EXPOSED_POLISHED_SHALE_CLAY_SLAB = register(blockWithItem(
        "exposed_polished_shale_clay_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> WEATHERED_POLISHED_SHALE_CLAY = register(blockWithItem(
        "weathered_polished_shale_clay", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> WEATHERED_POLISHED_SHALE_CLAY_STAIRS = register(blockWithItem(
        "weathered_polished_shale_clay_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.WEATHERED, PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY.get()
                                                                                            .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> WEATHERED_POLISHED_SHALE_CLAY_SLAB = register(blockWithItem(
        "weathered_polished_shale_clay_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SHALE_CLAY_BRICKS = register(blockWithItem(
        "shale_clay_bricks", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SHALE_CLAY_BRICK_STAIRS = register(blockWithItem(
        "shale_clay_brick_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.UNAFFECTED, PastelBlocks.SHALE_CLAY_BRICKS.get()
                                                                                 .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SHALE_CLAY_BRICK_SLAB = register(blockWithItem(
        "shale_clay_brick_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_BRICKS = register(blockWithItem(
        "exposed_shale_clay_bricks", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_BRICK_STAIRS = register(blockWithItem(
        "exposed_shale_clay_brick_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.EXPOSED, PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS.get()
                                                                                      .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_BRICK_SLAB = register(blockWithItem(
        "exposed_shale_clay_brick_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_BRICKS = register(blockWithItem(
        "weathered_shale_clay_bricks", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_BRICK_STAIRS = register(blockWithItem(
        "weathered_shale_clay_brick_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.WEATHERED, PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS.get()
                                                                                          .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_BRICK_SLAB = register(blockWithItem(
        "weathered_shale_clay_brick_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> SHALE_CLAY_TILES = register(blockWithItem(
        "shale_clay_tiles", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SHALE_CLAY_TILE_STAIRS = register(blockWithItem(
        "shale_clay_tile_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.UNAFFECTED, PastelBlocks.SHALE_CLAY_TILES.get()
                                                                                .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SHALE_CLAY_TILE_SLAB = register(blockWithItem(
        "shale_clay_tile_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.UNAFFECTED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_TILES = register(blockWithItem(
        "exposed_shale_clay_tiles", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_TILE_STAIRS = register(blockWithItem(
        "exposed_shale_clay_tile_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.EXPOSED, PastelBlocks.EXPOSED_SHALE_CLAY_TILES.get()
                                                                                     .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> EXPOSED_SHALE_CLAY_TILE_SLAB = register(blockWithItem(
        "exposed_shale_clay_tile_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.EXPOSED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_TILES = register(blockWithItem(
        "weathered_shale_clay_tiles", () -> new WeatheringBlock(
            Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_TILE_STAIRS = register(blockWithItem(
        "weathered_shale_clay_tile_stairs", () -> new WeatheringStairsBlock(
            Weathering.WeatheringLevel.WEATHERED, PastelBlocks.WEATHERED_SHALE_CLAY_TILES.get()
                                                                                         .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> WEATHERED_SHALE_CLAY_TILE_SLAB = register(blockWithItem(
        "weathered_shale_clay_tile_slab", () -> new WeatheringSlabBlock(
            Weathering.WeatheringLevel.WEATHERED, Properties.ofFullCopy(PastelBlocks.SHALE_CLAY.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> ROCK_CRYSTAL = register(blockWithItem(
        "rock_crystal",
        () -> new Block(settings(MapColor.QUARTZ, SoundType.NETHER_BRICKS, 200F).requiresCorrectToolForDrops()),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> PYRITE = register(blockWithItem(
        "pyrite", () -> new RotatedPillarBlock(
            settings(MapColor.TERRACOTTA_YELLOW, SoundType.CHAIN, 50.0F).requiresCorrectToolForDrops()), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_SLAB = register(blockWithItem(
        "pyrite_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.PYRITE.get())), InkColors.BROWN));
    public static final DeferredBlock<Block> PYRITE_STAIRS = register(blockWithItem(
        "pyrite_stairs", () -> new StairBlock(
            PastelBlocks.PYRITE.get()
                               .defaultBlockState(), Properties.ofFullCopy(PastelBlocks.PYRITE.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_WALL = register(blockWithItem(
        "pyrite_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.PYRITE.get())), InkColors.BROWN));

    public static final DeferredBlock<Block> PYRITE_PILE = register(blockWithItem(
        "pyrite_pile", () -> new RotatedPillarBlock(Properties.ofFullCopy(PastelBlocks.PYRITE.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_PLATING = register(blockWithItem(
        "pyrite_plating", () -> new Block(Properties.ofFullCopy(PastelBlocks.PYRITE.get())), InkColors.BROWN));
    public static final DeferredBlock<Block> PYRITE_TUBING = register(blockWithItem(
        "pyrite_tubing", () -> new RotatedPillarBlock(Properties.ofFullCopy(PastelBlocks.PYRITE.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_RELIEF = register(blockWithItem(
        "pyrite_relief", () -> new RotatedPillarBlock(Properties.ofFullCopy(PastelBlocks.PYRITE.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_STACK = register(blockWithItem(
        "pyrite_stack", () -> new Block(Properties.ofFullCopy(PastelBlocks.PYRITE.get())), InkColors.BROWN));
    public static final DeferredBlock<Block> PYRITE_PANELING = register(blockWithItem(
        "pyrite_paneling", () -> new Block(Properties.ofFullCopy(PastelBlocks.PYRITE.get())), InkColors.BROWN));
    public static final DeferredBlock<Block> PYRITE_VENT = register(blockWithItem(
        "pyrite_vent", () -> new Block(Properties.ofFullCopy(PastelBlocks.PYRITE.get())), InkColors.BROWN));
    public static final DeferredBlock<Block> PYRITE_RIPPER = register(blockWithItem(
        "pyrite_ripper", () -> new PyriteRipperBlock(Properties.ofFullCopy(PastelBlocks.PYRITE.get())
                                                               .noOcclusion()
                                                               .isValidSpawn(PastelBlocks::never)
                                                               .isViewBlocking(PastelBlocks::never)), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_PROJECTOR = register(blockWithItem(
        "pyrite_projector", () -> new ProjectorBlock(
            Properties.ofFullCopy(PastelBlocks.PYRITE.get()), "pyrite_projector_projection", 16, 14, 1.375F, 1F, 16F),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> PYRITE_TILES = register(blockWithItem(
        "pyrite_tiles", () -> new Block(Properties.ofFullCopy(PastelBlocks.PYRITE.get())), InkColors.BROWN));
    public static final DeferredBlock<Block> PYRITE_TILE_SLAB = register(blockWithItem(
        "pyrite_tile_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.PYRITE_TILES.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_TILE_STAIRS = register(blockWithItem(
        "pyrite_tile_stairs", () -> new StairBlock(
            PastelBlocks.PYRITE_TILES.get()
                                     .defaultBlockState(), Properties.ofFullCopy(PastelBlocks.PYRITE_TILES.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PYRITE_TILE_WALL = register(blockWithItem(
        "pyrite_tile_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.PYRITE_TILES.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> DRAGONBONE = register(blockWithItem(
        "dragonbone", () -> new DragonboneBlock(Properties.ofFullCopy(BONE_BLOCK)
                                                          .strength(-1.0F, 22.0F)
                                                          .pushReaction(PushReaction.BLOCK)), InkColors.GREEN
    ));
    public static final DeferredBlock<Block> CRACKED_DRAGONBONE = register(blockWithItem(
        "cracked_dragonbone", () -> new RotatedPillarBlock(Properties.ofFullCopy(BONE_BLOCK)
                                                                     .strength(100.0F, 1200.0F)
                                                                     .pushReaction(PushReaction.BLOCK)), InkColors.GREEN
    ));

    public static final DeferredBlock<Block> POLISHED_BONE_ASH = register(blockWithItem(
        "polished_bone_ash", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CRACKED_DRAGONBONE.get())
                                                                      .destroyTime(1500.0F)
                                                                      .mapColor(MapColor.SNOW)), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> POLISHED_BONE_ASH_STAIRS = register(blockWithItem(
        "polished_bone_ash_stairs", () -> new StairBlock(
            PastelBlocks.POLISHED_BONE_ASH.get()
                                          .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.POLISHED_BONE_ASH.get())
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> POLISHED_BONE_ASH_SLAB = register(blockWithItem(
        "polished_bone_ash_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.POLISHED_BONE_ASH.get())),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> POLISHED_BONE_ASH_WALL = register(blockWithItem(
        "polished_bone_ash_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.POLISHED_BONE_ASH.get())),
        InkColors.CYAN
    ));

    public static final DeferredBlock<Block> POLISHED_BONE_ASH_PILLAR = register(blockWithItem(
        "polished_bone_ash_pillar",
        () -> new RotatedPillarBlock(Properties.ofFullCopy(PastelBlocks.POLISHED_BONE_ASH.get())), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BONE_ASH_SHINGLES = register(blockWithItem(
        "bone_ash_shingles", () -> new ShinglesBlock(
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BONE_ASH.get())
                                     .noOcclusion()), InkColors.CYAN
    ));

    public static final DeferredBlock<Block> BONE_ASH_BRICKS = register(blockWithItem(
        "bone_ash_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BONE_ASH.get())),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BONE_ASH_BRICK_STAIRS = register(blockWithItem(
        "bone_ash_brick_stairs", () -> new StairBlock(
            PastelBlocks.BONE_ASH_BRICKS.get()
                                        .defaultBlockState(), Properties.ofFullCopy(PastelBlocks.BONE_ASH_BRICKS.get())
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BONE_ASH_BRICK_SLAB = register(blockWithItem(
        "bone_ash_brick_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.BONE_ASH_BRICKS.get())),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BONE_ASH_BRICK_WALL = register(blockWithItem(
        "bone_ash_brick_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.BONE_ASH_BRICKS.get())),
        InkColors.CYAN
    ));

    public static final DeferredBlock<Block> BONE_ASH_TILES = register(blockWithItem(
        "bone_ash_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BONE_ASH.get())),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BONE_ASH_TILE_STAIRS = register(blockWithItem(
        "bone_ash_tile_stairs", () -> new StairBlock(
            PastelBlocks.BONE_ASH_TILES.get()
                                       .defaultBlockState(), Properties.ofFullCopy(PastelBlocks.BONE_ASH_TILES.get())
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BONE_ASH_TILE_SLAB = register(blockWithItem(
        "bone_ash_tile_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.BONE_ASH_TILES.get())),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BONE_ASH_TILE_WALL = register(blockWithItem(
        "bone_ash_tile_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.BONE_ASH_TILES.get())),
        InkColors.CYAN
    ));

    public static final DeferredBlock<Block> SLUSH = register(blockWithItem(
        "slush", () -> new RotatedPillarBlock(blackslag(SoundType.MUDDY_MANGROVE_ROOTS)), InkColors.BROWN));
    public static final DeferredBlock<Block> OVERGROWN_SLUSH = register(blockWithItem(
        "overgrown_slush", () -> new SlushVegetationBlock(blackslag(SoundType.MUDDY_MANGROVE_ROOTS)), InkColors.BROWN));
    public static final DeferredBlock<Block> TILLED_SLUSH = register(blockWithItem(
        "tilled_slush", () -> new TilledSlushBlock(
            Properties.ofFullCopy(PastelBlocks.SLUSH.get()), PastelBlocks.SLUSH.get()
                                                                               .defaultBlockState()
        ), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> BLACK_MATERIA = register(blockWithItem(
        "black_materia", () -> new BlackMateriaBlock(settings(
            MapColor.TERRACOTTA_BLACK, SoundType.SAND, 0.0F).instrument(NoteBlockInstrument.SNARE)
                                                            .randomTicks()), InkColors.GRAY
    ));
    public static final DeferredBlock<Block> HORNSLAKE = register(blockWithItem(
        "hornslake", () -> new Block(
            settings(MapColor.TERRACOTTA_BLACK, SoundType.SAND, 0.5F).instrument(NoteBlockInstrument.SNARE)),
        InkColors.GRAY
    ));
    public static final DeferredBlock<Block> SAG_LEAF = register(block(
        "sag_leaf", () -> new BlackSludgePlantBlock(Properties.ofFullCopy(SHORT_GRASS)
                                                              .mapColor(MapColor.TERRACOTTA_BLACK))
    ));
    public static final DeferredBlock<Block> SAG_BUBBLE = register(block(
        "sag_bubble", () -> new BlackSludgePlantBlock(Properties.ofFullCopy(SHORT_GRASS)
                                                                .mapColor(MapColor.TERRACOTTA_BLACK))
    ));
    public static final DeferredBlock<Block> SMALL_SAG_BUBBLE = register(block(
        "small_sag_bubble", () -> new BlackSludgePlantBlock(Properties.ofFullCopy(SHORT_GRASS)
                                                                      .mapColor(MapColor.TERRACOTTA_BLACK))
    ));

    public static final DeferredBlock<Block> PRIMORDIAL_FIRE = register(block(
        "primordial_fire", () -> new PrimordialFireBlock(Properties.ofFullCopy(FIRE)
                                                                   .mapColor(MapColor.COLOR_PURPLE)
                                                                   .lightLevel((state) -> 10))
    ));
    public static final DeferredBlock<Block> PRIMORDIAL_WALL_TORCH = register(block(
        "primordial_wall_torch", () -> new WallTorchBlock(
            PastelParticleTypes.PRIMORDIAL_FLAME, Properties.ofFullCopy(SOUL_WALL_TORCH)
                                                            .lightLevel(s -> 13)
        )
    ));
    public static final DeferredBlock<Block> PRIMORDIAL_TORCH = register(blockWithItem(
        "primordial_torch", () -> new TorchBlock(
            PastelParticleTypes.PRIMORDIAL_FLAME, Properties.ofFullCopy(SOUL_TORCH)
                                                            .lightLevel(s -> 13)
        ),
        block -> new StandingAndWallBlockItem(block, PastelBlocks.PRIMORDIAL_WALL_TORCH.get(), IS.of(), Direction.DOWN),
        InkColors.ORANGE
    ));

    public static final DeferredBlock<Block> SMOOTH_BASALT_STAIRS = register(blockWithItem(
        "smooth_basalt_stairs",
        () -> new StairBlock(BASALT.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(BASALT)), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMOOTH_BASALT_SLAB = register(blockWithItem(
        "smooth_basalt_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(BASALT)), InkColors.BROWN));
    public static final DeferredBlock<Block> SMOOTH_BASALT_WALL = register(blockWithItem(
        "smooth_basalt_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(BASALT)), InkColors.BROWN));

    public static final DeferredBlock<Block> POLISHED_BASALT = register(blockWithItem(
        "polished_basalt", () -> new Block(settings(MapColor.COLOR_BLACK, SoundType.BASALT, 2.0F, 5.0F).instrument(
                                                                                                           NoteBlockInstrument.BASEDRUM)
                                                                                                       .requiresCorrectToolForDrops()),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_BASALT_STAIRS = register(blockWithItem(
        "polished_basalt_stairs", () -> new StairBlock(
            PastelBlocks.POLISHED_BASALT.get()
                                        .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_BASALT_SLAB = register(blockWithItem(
        "polished_basalt_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_BASALT_WALL = register(blockWithItem(
        "polished_basalt_wall",
        () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_BASALT_BUTTON = register(blockWithItem(
        "polished_basalt_button", () -> new ButtonBlock(
            PastelBlockSetTypes.POLISHED_BASALT, 5, Properties.of()
                                                              .noCollission()
                                                              .strength(0.5F)
                                                              .pushReaction(PushReaction.DESTROY)
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_BASALT_PRESSURE_PLATE = register(blockWithItem(
        "polished_basalt_pressure_plate", () -> new PressurePlateBlock(
            PastelBlockSetTypes.POLISHED_BASALT, Properties.of()
                                                           .mapColor(MapColor.COLOR_BLACK)
                                                           .forceSolidOn()
                                                           .instrument(NoteBlockInstrument.BASEDRUM)
                                                           .requiresCorrectToolForDrops()
                                                           .noCollission()
                                                           .strength(0.5F)
                                                           .pushReaction(PushReaction.DESTROY)
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CHISELED_POLISHED_BASALT = register(blockWithItem(
        "chiseled_polished_basalt",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> POLISHED_BASALT_PILLAR = register(blockWithItem(
        "polished_basalt_pillar",
        () -> new RotatedPillarBlock(Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_BASALT_CREST = register(blockWithItem(
        "polished_basalt_crest",
        () -> new CardinalFacingBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> NOTCHED_POLISHED_BASALT = register(blockWithItem(
        "notched_polished_basalt", () -> new Block(Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> BASALT_BRICKS = register(blockWithItem(
        "basalt_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASALT_BRICK_STAIRS = register(blockWithItem(
        "basalt_brick_stairs", () -> new StairBlock(
            PastelBlocks.BASALT_BRICKS.get()
                                      .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASALT_BRICK_SLAB = register(blockWithItem(
        "basalt_brick_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASALT_BRICK_WALL = register(blockWithItem(
        "basalt_brick_wall",
        () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CRACKED_BASALT_BRICKS = register(blockWithItem(
        "cracked_basalt_bricks",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> BASALT_TILES = register(blockWithItem(
        "basalt_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASALT_TILE_STAIRS = register(blockWithItem(
        "basalt_tile_stairs", () -> new StairBlock(
            PastelBlocks.BASALT_TILES.get()
                                     .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_TILES.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASALT_TILE_SLAB = register(blockWithItem(
        "basalt_tile_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_TILES.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASALT_TILE_WALL = register(blockWithItem(
        "basalt_tile_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_TILES.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CRACKED_BASALT_TILES = register(blockWithItem(
        "cracked_basalt_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BASALT_TILES.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> PLANED_BASALT = register(blockWithItem(
        "planed_basalt", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PLANED_BASALT_STAIRS = register(blockWithItem(
        "planed_basalt_stairs", () -> new StairBlock(
            PastelBlocks.PLANED_BASALT.get()
                                      .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.PLANED_BASALT.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PLANED_BASALT_SLAB = register(blockWithItem(
        "planed_basalt_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.PLANED_BASALT.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PLANED_BASALT_WALL = register(blockWithItem(
        "planed_basalt_wall",
        () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.PLANED_BASALT.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> TOPAZ_CHISELED_BASALT = register(blockWithItem(
        "topaz_chiseled_basalt", () -> new Block(Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())
                                                           .lightLevel(s -> 6)), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_CHISELED_BASALT = register(blockWithItem(
        "amethyst_chiseled_basalt", () -> new Block(Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())
                                                              .lightLevel(s -> 5)), InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> CITRINE_CHISELED_BASALT = register(blockWithItem(
        "citrine_chiseled_basalt", () -> new Block(Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())
                                                             .lightLevel(s -> 7)), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_CHISELED_BASALT = register(blockWithItem(
        "onyx_chiseled_basalt", () -> new Block(Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())
                                                          .lightLevel(s -> 3)), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_CHISELED_BASALT = register(blockWithItem(
        "moonstone_chiseled_basalt", () -> new PastelLineFacingBlock(
            Properties.ofFullCopy(PastelBlocks.BASALT_BRICKS.get())
                      .lightLevel(s -> 12)), InkColors.WHITE
    ));

    public static final DeferredBlock<Block> CALCITE_STAIRS = register(blockWithItem(
        "calcite_stairs",
        () -> new StairBlock(CALCITE.defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(CALCITE)),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CALCITE_SLAB = register(blockWithItem(
        "calcite_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE)), InkColors.BROWN));
    public static final DeferredBlock<Block> CALCITE_WALL = register(blockWithItem(
        "calcite_wall", () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(CALCITE)), InkColors.BROWN));

    public static final DeferredBlock<Block> POLISHED_CALCITE = register(blockWithItem(
        "polished_calcite", () -> new Block(settings(
            MapColor.TERRACOTTA_WHITE, SoundType.CALCITE, 2.0F, 5.0F).instrument(NoteBlockInstrument.BASEDRUM)
                                                                     .requiresCorrectToolForDrops()), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_CALCITE_STAIRS = register(blockWithItem(
        "polished_calcite_stairs", () -> new StairBlock(
            PastelBlocks.POLISHED_CALCITE.get()
                                         .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_CALCITE_SLAB = register(blockWithItem(
        "polished_calcite_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_CALCITE_WALL = register(blockWithItem(
        "polished_calcite_wall",
        () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_CALCITE_BUTTON = register(blockWithItem(
        "polished_calcite_button", () -> new ButtonBlock(
            PastelBlockSetTypes.POLISHED_CALCITE, 5, Properties.of()
                                                               .noCollission()
                                                               .strength(0.5F)
                                                               .pushReaction(PushReaction.DESTROY)
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_CALCITE_PRESSURE_PLATE = register(blockWithItem(
        "polished_calcite_pressure_plate", () -> new PressurePlateBlock(
            PastelBlockSetTypes.POLISHED_CALCITE, Properties.of()
                                                            .mapColor(MapColor.TERRACOTTA_WHITE)
                                                            .forceSolidOn()
                                                            .instrument(NoteBlockInstrument.BASEDRUM)
                                                            .requiresCorrectToolForDrops()
                                                            .noCollission()
                                                            .strength(0.5F)
                                                            .pushReaction(PushReaction.DESTROY)
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CHISELED_POLISHED_CALCITE = register(blockWithItem(
        "chiseled_polished_calcite",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> POLISHED_CALCITE_PILLAR = register(blockWithItem(
        "polished_calcite_pillar",
        () -> new RotatedPillarBlock(Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_CALCITE_CREST = register(blockWithItem(
        "polished_calcite_crest",
        () -> new CardinalFacingBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> NOTCHED_POLISHED_CALCITE = register(blockWithItem(
        "notched_polished_calcite", () -> new Block(Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> CALCITE_BRICKS = register(blockWithItem(
        "calcite_bricks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CALCITE_BRICK_STAIRS = register(blockWithItem(
        "calcite_brick_stairs", () -> new StairBlock(
            PastelBlocks.CALCITE_BRICKS.get()
                                       .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CALCITE_BRICK_SLAB = register(blockWithItem(
        "calcite_brick_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CALCITE_BRICK_WALL = register(blockWithItem(
        "calcite_brick_wall",
        () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CRACKED_CALCITE_BRICKS = register(blockWithItem(
        "cracked_calcite_bricks",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> CALCITE_TILES = register(blockWithItem(
        "calcite_tiles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CALCITE_TILE_STAIRS = register(blockWithItem(
        "calcite_tile_stairs", () -> new StairBlock(
            PastelBlocks.CALCITE_TILES.get()
                                      .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_TILES.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CALCITE_TILE_SLAB = register(blockWithItem(
        "calcite_tile_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_TILES.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CALCITE_TILE_WALL = register(blockWithItem(
        "calcite_tile_wall",
        () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_TILES.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> CRACKED_CALCITE_TILES = register(blockWithItem(
        "cracked_calcite_tiles",
        () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.CALCITE_TILES.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> PLANED_CALCITE = register(blockWithItem(
        "planed_calcite", () -> new Block(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_CALCITE.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PLANED_CALCITE_STAIRS = register(blockWithItem(
        "planed_calcite_stairs", () -> new StairBlock(
            PastelBlocks.PLANED_CALCITE.get()
                                       .defaultBlockState(),
            BlockBehaviour.Properties.ofFullCopy(PastelBlocks.PLANED_CALCITE.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PLANED_CALCITE_SLAB = register(blockWithItem(
        "planed_calcite_slab",
        () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.PLANED_CALCITE.get())), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PLANED_CALCITE_WALL = register(blockWithItem(
        "planed_calcite_wall",
        () -> new WallBlock(BlockBehaviour.Properties.ofFullCopy(PastelBlocks.PLANED_CALCITE.get())), InkColors.BROWN
    ));

    public static final DeferredBlock<Block> TOPAZ_CHISELED_CALCITE = register(blockWithItem(
        "topaz_chiseled_calcite", () -> new Block(Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())
                                                            .lightLevel(s -> 6)), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_CHISELED_CALCITE = register(blockWithItem(
        "amethyst_chiseled_calcite", () -> new Block(Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())
                                                               .lightLevel(s -> 5)), InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> CITRINE_CHISELED_CALCITE = register(blockWithItem(
        "citrine_chiseled_calcite", () -> new Block(Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())
                                                              .lightLevel(s -> 7)), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_CHISELED_CALCITE = register(blockWithItem(
        "onyx_chiseled_calcite", () -> new Block(Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())
                                                           .lightLevel(s -> 3)), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_CHISELED_CALCITE = register(blockWithItem(
        "moonstone_chiseled_calcite", () -> new PastelLineFacingBlock(
            Properties.ofFullCopy(PastelBlocks.CALCITE_BRICKS.get())
                      .lightLevel(s -> 12)), InkColors.WHITE
    ));

    public static DeferredBlock<Block> registerGemstoneLight(
        String name, DeferredBlock<Block> gemBlock, DeferredBlock<Block> baseBlock, InkColor color) {
        return register(blockWithItem(
            name, () -> new RotatedPillarBlock(Properties.ofFullCopy(baseBlock.get())
                                                         .lightLevel(s -> 15)
                                                         .noOcclusion()
                                                         .forceSolidOn()), color
        ));
    }

    public static DeferredBlock<Block> registerGemstoneLight(
        String name, Block gemBlock, DeferredBlock<Block> baseBlock, InkColor color) {
        return register(blockWithItem(
            name, () -> new RotatedPillarBlock(Properties.ofFullCopy(baseBlock.get())
                                                         .lightLevel(s -> 15)
                                                         .noOcclusion()
                                                         .forceSolidOn()), color
        ));
    }

    public static final DeferredBlock<Block> TOPAZ_BASALT_LIGHT = registerGemstoneLight(
        "topaz_basalt_light", PastelBlocks.TOPAZ_BLOCK, PastelBlocks.POLISHED_BASALT, InkColors.CYAN);
    public static final DeferredBlock<Block> AMETHYST_BASALT_LIGHT = registerGemstoneLight(
        "amethyst_basalt_light", AMETHYST_BLOCK, PastelBlocks.POLISHED_BASALT, InkColors.MAGENTA);
    public static final DeferredBlock<Block> CITRINE_BASALT_LIGHT = registerGemstoneLight(
        "citrine_basalt_light", PastelBlocks.CITRINE_BLOCK, PastelBlocks.POLISHED_BASALT, InkColors.YELLOW);
    public static final DeferredBlock<Block> ONYX_BASALT_LIGHT = registerGemstoneLight(
        "onyx_basalt_light", PastelBlocks.ONYX_BLOCK, PastelBlocks.POLISHED_BASALT, InkColors.BLACK);
    public static final DeferredBlock<Block> MOONSTONE_BASALT_LIGHT = registerGemstoneLight(
        "moonstone_basalt_light", PastelBlocks.MOONSTONE_BLOCK, PastelBlocks.POLISHED_BASALT, InkColors.WHITE);
    public static final DeferredBlock<Block> TOPAZ_CALCITE_LIGHT = registerGemstoneLight(
        "topaz_calcite_light", PastelBlocks.TOPAZ_BLOCK, PastelBlocks.POLISHED_CALCITE, InkColors.CYAN);
    public static final DeferredBlock<Block> AMETHYST_CALCITE_LIGHT = registerGemstoneLight(
        "amethyst_calcite_light", AMETHYST_BLOCK, PastelBlocks.POLISHED_CALCITE, InkColors.MAGENTA);
    public static final DeferredBlock<Block> CITRINE_CALCITE_LIGHT = registerGemstoneLight(
        "citrine_calcite_light", PastelBlocks.CITRINE_BLOCK, PastelBlocks.POLISHED_CALCITE, InkColors.YELLOW);
    public static final DeferredBlock<Block> ONYX_CALCITE_LIGHT = registerGemstoneLight(
        "onyx_calcite_light", PastelBlocks.ONYX_BLOCK, PastelBlocks.POLISHED_CALCITE, InkColors.BLACK);
    public static final DeferredBlock<Block> MOONSTONE_CALCITE_LIGHT = registerGemstoneLight(
        "moonstone_calcite_light", PastelBlocks.MOONSTONE_BLOCK, PastelBlocks.POLISHED_CALCITE, InkColors.WHITE);

    // GLASS
    private static Properties gemstoneGlass(SoundType soundGroup, MapColor mapColor) {
        return BlockBehaviour.Properties.ofFullCopy(GLASS)
                                        .sound(soundGroup)
                                        .mapColor(mapColor);
    }

    public static final DeferredBlock<Block> TOPAZ_GLASS = register(blockWithItem(
        "topaz_glass", () -> new GemstoneGlassBlock(
            gemstoneGlass(PastelBlockSoundGroups.TOPAZ_CLUSTER, MapColor.COLOR_CYAN), PastelGemstoneColor.CYAN),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_GLASS = register(blockWithItem(
        "amethyst_glass", () -> new GemstoneGlassBlock(
            gemstoneGlass(SoundType.AMETHYST_CLUSTER, MapColor.COLOR_MAGENTA), PastelGemstoneColor.MAGENTA),
        InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> CITRINE_GLASS = register(blockWithItem(
        "citrine_glass", () -> new GemstoneGlassBlock(
            gemstoneGlass(PastelBlockSoundGroups.CITRINE_CLUSTER, MapColor.COLOR_YELLOW), PastelGemstoneColor.YELLOW),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_GLASS = register(blockWithItem(
        "onyx_glass", () -> new GemstoneGlassBlock(
            gemstoneGlass(PastelBlockSoundGroups.ONYX_CLUSTER, MapColor.COLOR_BLACK), PastelGemstoneColor.BLACK),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_GLASS = register(blockWithItem(
        "moonstone_glass", () -> new GemstoneGlassBlock(
            gemstoneGlass(PastelBlockSoundGroups.MOONSTONE_CLUSTER, MapColor.SNOW), PastelGemstoneColor.WHITE),
        InkColors.WHITE
    ));
    public static final DeferredBlock<Block> RADIANT_GLASS = register(blockWithItem(
        "radiant_glass",
        () -> new RadiantGlassBlock(gemstoneGlass(SoundType.GLASS, MapColor.SAND).lightLevel(value -> 12)),
        InkColors.WHITE
    ));

    public static final DeferredBlock<Block> TOPAZ_GLASS_PANE = register(blockWithItem(
        "topaz_glass_pane",
        () -> new IronBarsBlock(gemstoneGlass(PastelBlockSoundGroups.TOPAZ_CLUSTER, MapColor.COLOR_CYAN)),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_GLASS_PANE = register(
        blockWithItem(
            "amethyst_glass_pane",
            () -> new IronBarsBlock(gemstoneGlass(SoundType.AMETHYST_CLUSTER, MapColor.COLOR_MAGENTA)),
            InkColors.MAGENTA
        ));
    public static final DeferredBlock<Block> CITRINE_GLASS_PANE = register(blockWithItem(
        "citrine_glass_pane",
        () -> new IronBarsBlock(gemstoneGlass(PastelBlockSoundGroups.CITRINE_CLUSTER, MapColor.COLOR_YELLOW)),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_GLASS_PANE = register(blockWithItem(
        "onyx_glass_pane",
        () -> new IronBarsBlock(gemstoneGlass(PastelBlockSoundGroups.ONYX_CLUSTER, MapColor.COLOR_BLACK)),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_GLASS_PANE = register(blockWithItem(
        "moonstone_glass_pane",
        () -> new IronBarsBlock(gemstoneGlass(PastelBlockSoundGroups.MOONSTONE_CLUSTER, MapColor.SNOW)), InkColors.WHITE
    ));
    public static final DeferredBlock<Block> RADIANT_GLASS_PANE = register(blockWithItem(
        "radiant_glass_pane",
        () -> new IronBarsBlock(gemstoneGlass(SoundType.GLASS, MapColor.SAND).lightLevel(value -> 12)), InkColors.WHITE
    ));

    public static final DeferredBlock<Block> ETHEREAL_PLATFORM = register(blockWithItem(
        "ethereal_platform", () -> new EtherealPlatformBlock(
            gemstoneGlass(SoundType.AMETHYST, MapColor.NONE).pushReaction(PushReaction.NORMAL)), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UNIVERSE_SPYHOLE = register(blockWithItem(
        "universe_spyhole", () -> new TransparentBlock(settings(
            MapColor.NONE, PastelBlockSoundGroups.CITRINE_BLOCK, 1.5F).requiresCorrectToolForDrops()
                                                                      .isViewBlocking(PastelBlocks::never)),
        InkColors.LIGHT_GRAY
    ));

    private static Properties chime(BlockBehaviour block) {
        return BlockBehaviour.Properties.ofFullCopy(block)
                                        .pushReaction(PushReaction.DESTROY)
                                        .destroyTime(1.0F)
                                        .noOcclusion();
    }

    public static final DeferredBlock<Block> TOPAZ_CHIME = register(blockWithItem(
        "topaz_chime", () -> new GemstoneChimeBlock(
            chime(PastelBlocks.TOPAZ_CLUSTER.get()), PastelSounds.BLOCK_TOPAZ_BLOCK_CHIME,
            ColoredSparkleRisingParticleEffect.CYAN
        ), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_CHIME = register(blockWithItem(
        "amethyst_chime", () -> new GemstoneChimeBlock(
            chime(AMETHYST_CLUSTER), SoundEvents.AMETHYST_BLOCK_CHIME, ColoredSparkleRisingParticleEffect.MAGENTA),
        InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> CITRINE_CHIME = register(blockWithItem(
        "citrine_chime", () -> new GemstoneChimeBlock(
            chime(PastelBlocks.CITRINE_CLUSTER.get()), PastelSounds.BLOCK_CITRINE_BLOCK_CHIME,
            ColoredSparkleRisingParticleEffect.YELLOW
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_CHIME = register(blockWithItem(
        "onyx_chime", () -> new GemstoneChimeBlock(
            chime(PastelBlocks.ONYX_CLUSTER.get()), PastelSounds.BLOCK_ONYX_BLOCK_CHIME,
            ColoredSparkleRisingParticleEffect.BLACK
        ), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_CHIME = register(blockWithItem(
        "moonstone_chime", () -> new GemstoneChimeBlock(
            chime(PastelBlocks.MOONSTONE_CLUSTER.get()), PastelSounds.BLOCK_MOONSTONE_BLOCK_CHIME,
            ColoredSparkleRisingParticleEffect.WHITE
        ), InkColors.WHITE
    ));

    private static Properties pylon(BlockBehaviour block) {
        return BlockBehaviour.Properties.ofFullCopy(block)
                                        .noOcclusion();
    }

    public static final DeferredBlock<Block> TOPAZ_PYLON = register(
        blockWithItem("topaz_pylon", () -> new PylonBlock(pylon(PastelBlocks.TOPAZ_BLOCK.get())), InkColors.CYAN));
    public static final DeferredBlock<Block> AMETHYST_PYLON = register(
        blockWithItem("amethyst_pylon", () -> new PylonBlock(pylon(AMETHYST_BLOCK)), InkColors.MAGENTA));
    public static final DeferredBlock<Block> CITRINE_PYLON = register(blockWithItem(
        "citrine_pylon", () -> new PylonBlock(pylon(PastelBlocks.CITRINE_BLOCK.get())), InkColors.YELLOW));
    public static final DeferredBlock<Block> ONYX_PYLON = register(
        blockWithItem("onyx_pylon", () -> new PylonBlock(pylon(PastelBlocks.ONYX_BLOCK.get())), InkColors.BLACK));
    public static final DeferredBlock<Block> MOONSTONE_PYLON = register(blockWithItem(
        "moonstone_pylon", () -> new PylonBlock(pylon(PastelBlocks.MOONSTONE_BLOCK.get())), InkColors.WHITE));

    public static final DeferredBlock<Block> SEMI_PERMEABLE_GLASS = register(blockWithItem(
        "semi_permeable_glass", () -> new AlternatePlayerOnlyGlassBlock(Properties.ofFullCopy(GLASS), GLASS, false),
        InkColors.WHITE
    ));
    public static final DeferredBlock<Block> TINTED_SEMI_PERMEABLE_GLASS = register(blockWithItem(
        "tinted_semi_permeable_glass",
        () -> new AlternatePlayerOnlyGlassBlock(Properties.ofFullCopy(TINTED_GLASS), TINTED_GLASS, true),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> RADIANT_SEMI_PERMEABLE_GLASS = register(blockWithItem(
        "radiant_semi_permeable_glass", () -> new AlternatePlayerOnlyGlassBlock(
            Properties.ofFullCopy(PastelBlocks.RADIANT_GLASS.get()), PastelBlocks.RADIANT_GLASS.get(), false),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> TOPAZ_SEMI_PERMEABLE_GLASS = register(blockWithItem(
        "topaz_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(
            Properties.ofFullCopy(PastelBlocks.TOPAZ_GLASS.get()), PastelGemstoneColor.CYAN), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_SEMI_PERMEABLE_GLASS = register(
        blockWithItem(
            "amethyst_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(
                Properties.ofFullCopy(PastelBlocks.AMETHYST_GLASS.get()), PastelGemstoneColor.MAGENTA),
            InkColors.MAGENTA
        ));
    public static final DeferredBlock<Block> CITRINE_SEMI_PERMEABLE_GLASS = register(blockWithItem(
        "citrine_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(
            Properties.ofFullCopy(PastelBlocks.CITRINE_GLASS.get()), PastelGemstoneColor.YELLOW), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_SEMI_PERMEABLE_GLASS = register(blockWithItem(
        "onyx_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(
            Properties.ofFullCopy(PastelBlocks.ONYX_GLASS.get()), PastelGemstoneColor.BLACK), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_SEMI_PERMEABLE_GLASS = register(blockWithItem(
        "moonstone_semi_permeable_glass", () -> new GemstonePlayerOnlyGlassBlock(
            Properties.ofFullCopy(PastelBlocks.MOONSTONE_GLASS.get()), PastelGemstoneColor.WHITE), InkColors.WHITE
    ));

    public static final DeferredBlock<Block> GLISTERING_MELON = register(
        block("glistering_melon", () -> new Block(BlockBehaviour.Properties.ofFullCopy(MELON))).withItem(
            block -> new BlockItem(block, IS.of()), InkColors.LIME));
    public static final DeferredBlock<Block> ATTACHED_GLISTERING_MELON_STEM = register(block(
        "attached_glistering_melon_stem", () -> new AttachedStemBlock(
            ResourceKey.create(Registries.BLOCK, locate("glistering_melon_stem")),
            PastelBlocks.GLISTERING_MELON.getKey(), PastelItems.GLISTERING_MELON_SEEDS.getKey(),
            Properties.ofFullCopy(ATTACHED_MELON_STEM)
        )
    ));
    public static final DeferredBlock<Block> GLISTERING_MELON_STEM = register(block(
        "glistering_melon_stem", () -> new StemBlock(
            PastelBlocks.GLISTERING_MELON.getKey(), PastelBlocks.ATTACHED_GLISTERING_MELON_STEM.getKey(),
            PastelItems.GLISTERING_MELON_SEEDS.getKey(), Properties.ofFullCopy(MELON_STEM)
        )
    ));

    public static final DeferredBlock<Block> PRESENT = register(blockWithItem(
        "present", () -> new PresentBlock(Properties.ofFullCopy(WHITE_WOOL)), block -> new PresentBlockItem(
            block, IS.of(1)
                     .component(DataComponents.BUNDLE_CONTENTS, BundleContents.EMPTY)
        ), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> TITRATION_BARREL = register(blockWithItem(
        "titration_barrel", () -> new TitrationBarrelBlock(BlockBehaviour.Properties.ofFullCopy(OAK_PLANKS)
                                                                                    .mapColor(MapColor.COLOR_RED)),
        InkColors.MAGENTA
    ));

    public static final DeferredBlock<Block> BLOCK_FLOODER = register(
        block("block_flooder", () -> new BlockFlooderBlock(settings(MapColor.CLAY, SoundType.ROOTED_DIRT, 0.0F))));
    public static final DeferredBlock<Block> BOTTOMLESS_BUNDLE = register(blockWithItem(
                                                                              "bottomless_bundle",
        () -> new BottomlessBundleBlock(settings(MapColor.ICE, SoundType.WOOL, 1.0F).noOcclusion()
                                                                                                                                                                               .pushReaction(
                                                                                                                                                                                   PushReaction.DESTROY)),
                                                                              block -> new BottomlessBundleItem(block
                                                                                  , IS.of(1)), InkColors.LIGHT_GRAY
                                                                          )

    );

    public static final DeferredBlock<Block> SHIMMERSTONE_LIGHT = register(block(
        "shimmerstone_light", () -> new WandLightBlock(Properties.ofFullCopy(LIGHT)
                                                                 .sound(PastelBlockSoundGroups.SHIMMERSTONE_LIGHT)
                                                                 .instabreak())
    ));
    public static final DeferredBlock<Block> TEMPORAL_SHIMMERSTONE_LIGHT = register(block(
        "temporal_shimmerstone_light", () -> new DecayingLightBlock(
            Properties.ofFullCopy(PastelBlocks.SHIMMERSTONE_LIGHT.get())
                      .randomTicks())
    ));
    public static final DeferredBlock<Block> ENERGETIC_MOTE = register(block(
        "energetic_mote", () -> new EnergeticMoteBlock(BlockBehaviour.Properties.of()
                                                                                .replaceable()
                                                                                .strength(-1.0F, 3600000.8F)
                                                                                .mapColor(MapColor.NONE)
                                                                                .noLootTable()
                                                                                .noOcclusion()
                                                                                .sound(
                                                                                    PastelBlockSoundGroups.SHIMMERSTONE_LIGHT)
                                                                                .instabreak())
    ));

    public static final DeferredBlock<Block> TEMPORARY_PLATFORM = register(block(
        "temporary_platform", () -> new TemporaryPlatformBlock(
            Properties.ofFullCopy(PastelBlocks.ETHEREAL_PLATFORM.get())
                      .instabreak())
    ));

    private static Properties decay(
        MapColor mapColor, SoundType soundGroup, float strength, float resistance, PushReaction pistonBehavior) {
        return settings(mapColor, soundGroup, strength, resistance).pushReaction(pistonBehavior)
                                                                   .randomTicks()
                                                                   .isValidSpawn((state, world, pos, type) -> false);
    }


    public static final DeferredBlock<Block> FADING = register(block(
        "fading", () -> new FadingBlock(decay(MapColor.PLANT, SoundType.GRASS, 0.5F, 0.5F, PushReaction.DESTROY))));
    public static final DeferredBlock<Block> FAILING = register(block(
        "failing",
        () -> new FailingBlock(decay(MapColor.COLOR_BLACK, SoundType.STONE, 20.0F, 50.0F, PushReaction.BLOCK))
    ));
    public static final DeferredBlock<Block> RUIN = register(block(
        "ruin",
        () -> new RuinBlock(decay(MapColor.COLOR_BLACK, SoundType.STONE, 100.0F, 3600000.0F, PushReaction.BLOCK))
    ));
    public static final DeferredBlock<Block> FORFEITURE = register(block(
        "forfeiture", () -> new ForfeitureBlock(
            decay(MapColor.COLOR_BLACK, SoundType.STONE, 100.0F, 3600000.0F, PushReaction.BLOCK))
    ));
    public static final DeferredBlock<Block> DECAY_AWAY = register(block(
        "decay_away", () -> new DecayAwayBlock(Properties.ofFullCopy(DIRT)
                                                         .pushReaction(PushReaction.DESTROY))
    ));

    // PASTEL NETWORK
    private static Properties pastelNode(SoundType soundGroup) {
        return settings(MapColor.NONE, soundGroup, 1.5F).pushReaction(PushReaction.DESTROY)
                                                        .noOcclusion()
                                                        .requiresCorrectToolForDrops();
    }

    public static final DeferredBlock<Block> CONNECTION_NODE = register(blockWithItem(
        "connection_node", () -> new PastelNodeBlock(pastelNode(SoundType.AMETHYST_CLUSTER), PastelNodeType.CONNECTION),
        IS.of(16), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> PROVIDER_NODE = register(blockWithItem(
        "provider_node", () -> new PastelNodeBlock(pastelNode(SoundType.AMETHYST_CLUSTER), PastelNodeType.PROVIDER),
        IS.of(16), InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> STORAGE_NODE = register(blockWithItem(
        "storage_node",
        () -> new PastelNodeBlock(pastelNode(PastelBlockSoundGroups.TOPAZ_CLUSTER), PastelNodeType.STORAGE), IS.of(16),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BUFFER_NODE = register(blockWithItem(
        "buffer_node",
        () -> new PastelNodeBlock(pastelNode(PastelBlockSoundGroups.TOPAZ_CLUSTER), PastelNodeType.BUFFER), IS.of(16),
        InkColors.GREEN
    ));
    public static final DeferredBlock<Block> SENDER_NODE = register(blockWithItem(
        "sender_node",
        () -> new PastelNodeBlock(pastelNode(PastelBlockSoundGroups.CITRINE_CLUSTER), PastelNodeType.SENDER), IS.of(16),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> GATHER_NODE = register(blockWithItem(
        "gather_node",
        () -> new PastelNodeBlock(pastelNode(PastelBlockSoundGroups.ONYX_CLUSTER), PastelNodeType.GATHER), IS.of(16),
        InkColors.BLACK
    ));

    // COLORED BLOCK FAMILIES

    public static DeferredBlock<Block> registerColoredPlanks(String name, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredPlankBlock(
                copyWithMapColor(
                    OAK_PLANKS, color.getDyeColor()
                                     .orElse(DyeColor.LIME)
                                     .getMapColor()
                ), color
            ), color
        ));
    }

    public static DeferredBlock<Block> registerColoredStairs(
        String name, DeferredBlock<Block> baseBlock, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredStairsBlock(
                baseBlock.get()
                         .defaultBlockState(), copyWithMapColor(
                OAK_STAIRS, baseBlock.get()
                                     .defaultMapColor()
            ), color
            ), color
        ));
    }

    public static DeferredBlock<Block> registerColoredPressurePlate(
        String name, DeferredBlock<Block> baseBlock, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredPressurePlateBlock(
                copyWithMapColor(
                    OAK_PRESSURE_PLATE, baseBlock.get()
                                                 .defaultMapColor()
                ), color
            ), color
        ));
    }

    public static DeferredBlock<Block> registerColoredFence(
        String name, DeferredBlock<Block> baseBlock, InkColor color) {
        return register(burnable(
            blockWithItem(
                name, () -> new ColoredFenceBlock(
                    copyWithMapColor(
                        OAK_FENCE, baseBlock.get()
                                            .defaultMapColor()
                    ), color
                ), color
            ), 300
        ));
    }

    public static DeferredBlock<Block> registerColoredFenceGate(
        String name, DeferredBlock<Block> baseBlock, InkColor color) {
        return register(burnable(
            blockWithItem(
                name, () -> new ColoredFenceGateBlock(
                    copyWithMapColor(
                        OAK_FENCE_GATE, baseBlock.get()
                                                 .defaultMapColor()
                    ), color
                ), color
            ), 300
        ));
    }

    public static DeferredBlock<Block> registerColoredButton(
        String name, DeferredBlock<Block> baseBlock, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredWoodenButtonBlock(
                copyWithMapColor(
                    OAK_BUTTON, baseBlock.get()
                                         .defaultMapColor()
                ), color
            ), color
        ));
    }

    public static DeferredBlock<Block> registerColoredSlab(String name, DeferredBlock<Block> block, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredSlabBlock(
                copyWithMapColor(
                    OAK_SLAB, block.get()
                                   .defaultMapColor()
                ), color
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_PLANKS = registerColoredPlanks("black_planks", InkColors.BLACK);
    public static final DeferredBlock<Block> BLACK_STAIRS = registerColoredStairs(
        "black_stairs", PastelBlocks.BLACK_PLANKS, InkColors.BLACK);
    public static final DeferredBlock<Block> BLACK_PRESSURE_PLATE = registerColoredPressurePlate(
        "black_pressure_plate", PastelBlocks.BLACK_PLANKS, InkColors.BLACK);
    public static final DeferredBlock<Block> BLACK_FENCE = registerColoredFence(
        "black_fence", PastelBlocks.BLACK_PLANKS, InkColors.BLACK);
    public static final DeferredBlock<Block> BLACK_FENCE_GATE = registerColoredFenceGate(
        "black_fence_gate", PastelBlocks.BLACK_PLANKS, InkColors.BLACK);
    public static final DeferredBlock<Block> BLACK_BUTTON = registerColoredButton(
        "black_button", PastelBlocks.BLACK_PLANKS, InkColors.BLACK);
    public static final DeferredBlock<Block> BLACK_SLAB = registerColoredSlab(
        "black_slab", PastelBlocks.BLACK_PLANKS, InkColors.BLACK);

    public static final DeferredBlock<Block> BLUE_PLANKS = registerColoredPlanks("blue_planks", InkColors.BLUE);
    public static final DeferredBlock<Block> BLUE_STAIRS = registerColoredStairs(
        "blue_stairs", PastelBlocks.BLUE_PLANKS, InkColors.BLUE);
    public static final DeferredBlock<Block> BLUE_PRESSURE_PLATE = registerColoredPressurePlate(
        "blue_pressure_plate", PastelBlocks.BLUE_PLANKS, InkColors.BLUE);
    public static final DeferredBlock<Block> BLUE_FENCE = registerColoredFence(
        "blue_fence", PastelBlocks.BLUE_PLANKS, InkColors.BLUE);
    public static final DeferredBlock<Block> BLUE_FENCE_GATE = registerColoredFenceGate(
        "blue_fence_gate", PastelBlocks.BLUE_PLANKS, InkColors.BLUE);
    public static final DeferredBlock<Block> BLUE_BUTTON = registerColoredButton(
        "blue_button", PastelBlocks.BLUE_PLANKS, InkColors.BLUE);
    public static final DeferredBlock<Block> BLUE_SLAB = registerColoredSlab(
        "blue_slab", PastelBlocks.BLUE_PLANKS, InkColors.BLUE);

    public static final DeferredBlock<Block> BROWN_PLANKS = registerColoredPlanks("brown_planks", InkColors.BROWN);
    public static final DeferredBlock<Block> BROWN_STAIRS = registerColoredStairs(
        "brown_stairs", PastelBlocks.BROWN_PLANKS, InkColors.BROWN);
    public static final DeferredBlock<Block> BROWN_PRESSURE_PLATE = registerColoredPressurePlate(
        "brown_pressure_plate", PastelBlocks.BROWN_PLANKS, InkColors.BROWN);
    public static final DeferredBlock<Block> BROWN_FENCE = registerColoredFence(
        "brown_fence", PastelBlocks.BROWN_PLANKS, InkColors.BROWN);
    public static final DeferredBlock<Block> BROWN_FENCE_GATE = registerColoredFenceGate(
        "brown_fence_gate", PastelBlocks.BROWN_PLANKS, InkColors.BROWN);
    public static final DeferredBlock<Block> BROWN_BUTTON = registerColoredButton(
        "brown_button", PastelBlocks.BROWN_PLANKS, InkColors.BROWN);
    public static final DeferredBlock<Block> BROWN_SLAB = registerColoredSlab(
        "brown_slab", PastelBlocks.BROWN_PLANKS, InkColors.BROWN);

    public static final DeferredBlock<Block> CYAN_PLANKS = registerColoredPlanks("cyan_planks", InkColors.CYAN);
    public static final DeferredBlock<Block> CYAN_STAIRS = registerColoredStairs(
        "cyan_stairs", PastelBlocks.CYAN_PLANKS, InkColors.CYAN);
    public static final DeferredBlock<Block> CYAN_PRESSURE_PLATE = registerColoredPressurePlate(
        "cyan_pressure_plate", PastelBlocks.CYAN_PLANKS, InkColors.CYAN);
    public static final DeferredBlock<Block> CYAN_FENCE = registerColoredFence(
        "cyan_fence", PastelBlocks.CYAN_PLANKS, InkColors.CYAN);
    public static final DeferredBlock<Block> CYAN_FENCE_GATE = registerColoredFenceGate(
        "cyan_fence_gate", PastelBlocks.CYAN_PLANKS, InkColors.CYAN);
    public static final DeferredBlock<Block> CYAN_BUTTON = registerColoredButton(
        "cyan_button", PastelBlocks.CYAN_PLANKS, InkColors.CYAN);
    public static final DeferredBlock<Block> CYAN_SLAB = registerColoredSlab(
        "cyan_slab", PastelBlocks.CYAN_PLANKS, InkColors.CYAN);

    public static final DeferredBlock<Block> GRAY_PLANKS = registerColoredPlanks("gray_planks", InkColors.GRAY);
    public static final DeferredBlock<Block> GRAY_STAIRS = registerColoredStairs(
        "gray_stairs", PastelBlocks.GRAY_PLANKS, InkColors.GRAY);
    public static final DeferredBlock<Block> GRAY_PRESSURE_PLATE = registerColoredPressurePlate(
        "gray_pressure_plate", PastelBlocks.GRAY_PLANKS, InkColors.GRAY);
    public static final DeferredBlock<Block> GRAY_FENCE = registerColoredFence(
        "gray_fence", PastelBlocks.GRAY_PLANKS, InkColors.GRAY);
    public static final DeferredBlock<Block> GRAY_FENCE_GATE = registerColoredFenceGate(
        "gray_fence_gate", PastelBlocks.GRAY_PLANKS, InkColors.GRAY);
    public static final DeferredBlock<Block> GRAY_BUTTON = registerColoredButton(
        "gray_button", PastelBlocks.GRAY_PLANKS, InkColors.GRAY);
    public static final DeferredBlock<Block> GRAY_SLAB = registerColoredSlab(
        "gray_slab", PastelBlocks.GRAY_PLANKS, InkColors.GRAY);

    public static final DeferredBlock<Block> GREEN_PLANKS = registerColoredPlanks("green_planks", InkColors.GREEN);
    public static final DeferredBlock<Block> GREEN_STAIRS = registerColoredStairs(
        "green_stairs", PastelBlocks.GREEN_PLANKS, InkColors.GREEN);
    public static final DeferredBlock<Block> GREEN_PRESSURE_PLATE = registerColoredPressurePlate(
        "green_pressure_plate", PastelBlocks.GREEN_PLANKS, InkColors.GREEN);
    public static final DeferredBlock<Block> GREEN_FENCE = registerColoredFence(
        "green_fence", PastelBlocks.GREEN_PLANKS, InkColors.GREEN);
    public static final DeferredBlock<Block> GREEN_FENCE_GATE = registerColoredFenceGate(
        "green_fence_gate", PastelBlocks.GREEN_PLANKS, InkColors.GREEN);
    public static final DeferredBlock<Block> GREEN_BUTTON = registerColoredButton(
        "green_button", PastelBlocks.GREEN_PLANKS, InkColors.GREEN);
    public static final DeferredBlock<Block> GREEN_SLAB = registerColoredSlab(
        "green_slab", PastelBlocks.GREEN_PLANKS, InkColors.GREEN);

    public static final DeferredBlock<Block> LIGHT_BLUE_PLANKS = registerColoredPlanks(
        "light_blue_planks", InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_BLUE_STAIRS = registerColoredStairs(
        "light_blue_stairs", PastelBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_BLUE_PRESSURE_PLATE = registerColoredPressurePlate(
        "light_blue_pressure_plate", PastelBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_BLUE_FENCE = registerColoredFence(
        "light_blue_fence", PastelBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_BLUE_FENCE_GATE = registerColoredFenceGate(
        "light_blue_fence_gate", PastelBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_BLUE_BUTTON = registerColoredButton(
        "light_blue_button", PastelBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_BLUE_SLAB = registerColoredSlab(
        "light_blue_slab", PastelBlocks.LIGHT_BLUE_PLANKS, InkColors.LIGHT_BLUE);

    public static final DeferredBlock<Block> LIGHT_GRAY_PLANKS = registerColoredPlanks(
        "light_gray_planks", InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIGHT_GRAY_STAIRS = registerColoredStairs(
        "light_gray_stairs", PastelBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIGHT_GRAY_PRESSURE_PLATE = registerColoredPressurePlate(
        "light_gray_pressure_plate", PastelBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIGHT_GRAY_FENCE = registerColoredFence(
        "light_gray_fence", PastelBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIGHT_GRAY_FENCE_GATE = registerColoredFenceGate(
        "light_gray_fence_gate", PastelBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIGHT_GRAY_BUTTON = registerColoredButton(
        "light_gray_button", PastelBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIGHT_GRAY_SLAB = registerColoredSlab(
        "light_gray_slab", PastelBlocks.LIGHT_GRAY_PLANKS, InkColors.LIGHT_GRAY);

    public static final DeferredBlock<Block> LIME_PLANKS = registerColoredPlanks("lime_planks", InkColors.LIME);
    public static final DeferredBlock<Block> LIME_STAIRS = registerColoredStairs(
        "lime_stairs", PastelBlocks.LIME_PLANKS, InkColors.LIME);
    public static final DeferredBlock<Block> LIME_PRESSURE_PLATE = registerColoredPressurePlate(
        "lime_pressure_plate", PastelBlocks.LIME_PLANKS, InkColors.LIME);
    public static final DeferredBlock<Block> LIME_FENCE = registerColoredFence(
        "lime_fence", PastelBlocks.LIME_PLANKS, InkColors.LIME);
    public static final DeferredBlock<Block> LIME_FENCE_GATE = registerColoredFenceGate(
        "lime_fence_gate", PastelBlocks.LIME_PLANKS, InkColors.LIME);
    public static final DeferredBlock<Block> LIME_BUTTON = registerColoredButton(
        "lime_button", PastelBlocks.LIME_PLANKS, InkColors.LIME);
    public static final DeferredBlock<Block> LIME_SLAB = registerColoredSlab(
        "lime_slab", PastelBlocks.LIME_PLANKS, InkColors.LIME);

    public static final DeferredBlock<Block> MAGENTA_PLANKS = registerColoredPlanks(
        "magenta_planks", InkColors.MAGENTA);
    public static final DeferredBlock<Block> MAGENTA_STAIRS = registerColoredStairs(
        "magenta_stairs", PastelBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
    public static final DeferredBlock<Block> MAGENTA_PRESSURE_PLATE = registerColoredPressurePlate(
        "magenta_pressure_plate", PastelBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
    public static final DeferredBlock<Block> MAGENTA_FENCE = registerColoredFence(
        "magenta_fence", PastelBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
    public static final DeferredBlock<Block> MAGENTA_FENCE_GATE = registerColoredFenceGate(
        "magenta_fence_gate", PastelBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
    public static final DeferredBlock<Block> MAGENTA_BUTTON = registerColoredButton(
        "magenta_button", PastelBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);
    public static final DeferredBlock<Block> MAGENTA_SLAB = registerColoredSlab(
        "magenta_slab", PastelBlocks.MAGENTA_PLANKS, InkColors.MAGENTA);

    public static final DeferredBlock<Block> ORANGE_PLANKS = registerColoredPlanks("orange_planks", InkColors.ORANGE);
    public static final DeferredBlock<Block> ORANGE_STAIRS = registerColoredStairs(
        "orange_stairs", PastelBlocks.ORANGE_PLANKS, InkColors.ORANGE);
    public static final DeferredBlock<Block> ORANGE_PRESSURE_PLATE = registerColoredPressurePlate(
        "orange_pressure_plate", PastelBlocks.ORANGE_PLANKS, InkColors.ORANGE);
    public static final DeferredBlock<Block> ORANGE_FENCE = registerColoredFence(
        "orange_fence", PastelBlocks.ORANGE_PLANKS, InkColors.ORANGE);
    public static final DeferredBlock<Block> ORANGE_FENCE_GATE = registerColoredFenceGate(
        "orange_fence_gate", PastelBlocks.ORANGE_PLANKS, InkColors.ORANGE);
    public static final DeferredBlock<Block> ORANGE_BUTTON = registerColoredButton(
        "orange_button", PastelBlocks.ORANGE_PLANKS, InkColors.ORANGE);
    public static final DeferredBlock<Block> ORANGE_SLAB = registerColoredSlab(
        "orange_slab", PastelBlocks.ORANGE_PLANKS, InkColors.ORANGE);

    public static final DeferredBlock<Block> PINK_PLANKS = registerColoredPlanks("pink_planks", InkColors.PINK);
    public static final DeferredBlock<Block> PINK_STAIRS = registerColoredStairs(
        "pink_stairs", PastelBlocks.PINK_PLANKS, InkColors.PINK);
    public static final DeferredBlock<Block> PINK_PRESSURE_PLATE = registerColoredPressurePlate(
        "pink_pressure_plate", PastelBlocks.PINK_PLANKS, InkColors.PINK);
    public static final DeferredBlock<Block> PINK_FENCE = registerColoredFence(
        "pink_fence", PastelBlocks.PINK_PLANKS, InkColors.PINK);
    public static final DeferredBlock<Block> PINK_FENCE_GATE = registerColoredFenceGate(
        "pink_fence_gate", PastelBlocks.PINK_PLANKS, InkColors.PINK);
    public static final DeferredBlock<Block> PINK_BUTTON = registerColoredButton(
        "pink_button", PastelBlocks.PINK_PLANKS, InkColors.PINK);
    public static final DeferredBlock<Block> PINK_SLAB = registerColoredSlab(
        "pink_slab", PastelBlocks.PINK_PLANKS, InkColors.PINK);

    public static final DeferredBlock<Block> PURPLE_PLANKS = registerColoredPlanks("purple_planks", InkColors.PURPLE);
    public static final DeferredBlock<Block> PURPLE_STAIRS = registerColoredStairs(
        "purple_stairs", PastelBlocks.PURPLE_PLANKS, InkColors.PURPLE);
    public static final DeferredBlock<Block> PURPLE_PRESSURE_PLATE = registerColoredPressurePlate(
        "purple_pressure_plate", PastelBlocks.PURPLE_PLANKS, InkColors.PURPLE);
    public static final DeferredBlock<Block> PURPLE_FENCE = registerColoredFence(
        "purple_fence", PastelBlocks.PURPLE_PLANKS, InkColors.PURPLE);
    public static final DeferredBlock<Block> PURPLE_FENCE_GATE = registerColoredFenceGate(
        "purple_fence_gate", PastelBlocks.PURPLE_PLANKS, InkColors.PURPLE);
    public static final DeferredBlock<Block> PURPLE_BUTTON = registerColoredButton(
        "purple_button", PastelBlocks.PURPLE_PLANKS, InkColors.PURPLE);
    public static final DeferredBlock<Block> PURPLE_SLAB = registerColoredSlab(
        "purple_slab", PastelBlocks.PURPLE_PLANKS, InkColors.PURPLE);

    public static final DeferredBlock<Block> RED_PLANKS = registerColoredPlanks("red_planks", InkColors.RED);
    public static final DeferredBlock<Block> RED_STAIRS = registerColoredStairs(
        "red_stairs", PastelBlocks.RED_PLANKS, InkColors.RED);
    public static final DeferredBlock<Block> RED_PRESSURE_PLATE = registerColoredPressurePlate(
        "red_pressure_plate", PastelBlocks.RED_PLANKS, InkColors.RED);
    public static final DeferredBlock<Block> RED_FENCE = registerColoredFence(
        "red_fence", PastelBlocks.RED_PLANKS, InkColors.RED);
    public static final DeferredBlock<Block> RED_FENCE_GATE = registerColoredFenceGate(
        "red_fence_gate", PastelBlocks.RED_PLANKS, InkColors.RED);
    public static final DeferredBlock<Block> RED_BUTTON = registerColoredButton(
        "red_button", PastelBlocks.RED_PLANKS, InkColors.RED);
    public static final DeferredBlock<Block> RED_SLAB = registerColoredSlab(
        "red_slab", PastelBlocks.RED_PLANKS, InkColors.RED);

    public static final DeferredBlock<Block> WHITE_PLANKS = registerColoredPlanks("white_planks", InkColors.WHITE);
    public static final DeferredBlock<Block> WHITE_STAIRS = registerColoredStairs(
        "white_stairs", PastelBlocks.WHITE_PLANKS, InkColors.WHITE);
    public static final DeferredBlock<Block> WHITE_PRESSURE_PLATE = registerColoredPressurePlate(
        "white_pressure_plate", PastelBlocks.WHITE_PLANKS, InkColors.WHITE);
    public static final DeferredBlock<Block> WHITE_FENCE = registerColoredFence(
        "white_fence", PastelBlocks.WHITE_PLANKS, InkColors.WHITE);
    public static final DeferredBlock<Block> WHITE_FENCE_GATE = registerColoredFenceGate(
        "white_fence_gate", PastelBlocks.WHITE_PLANKS, InkColors.WHITE);
    public static final DeferredBlock<Block> WHITE_BUTTON = registerColoredButton(
        "white_button", PastelBlocks.WHITE_PLANKS, InkColors.WHITE);
    public static final DeferredBlock<Block> WHITE_SLAB = registerColoredSlab(
        "white_slab", PastelBlocks.WHITE_PLANKS, InkColors.WHITE);

    public static final DeferredBlock<Block> YELLOW_PLANKS = registerColoredPlanks("yellow_planks", InkColors.YELLOW);
    public static final DeferredBlock<Block> YELLOW_STAIRS = registerColoredStairs(
        "yellow_stairs", PastelBlocks.YELLOW_PLANKS, InkColors.YELLOW);
    public static final DeferredBlock<Block> YELLOW_PRESSURE_PLATE = registerColoredPressurePlate(
        "yellow_pressure_plate", PastelBlocks.YELLOW_PLANKS, InkColors.YELLOW);
    public static final DeferredBlock<Block> YELLOW_FENCE = registerColoredFence(
        "yellow_fence", PastelBlocks.YELLOW_PLANKS, InkColors.YELLOW);
    public static final DeferredBlock<Block> YELLOW_FENCE_GATE = registerColoredFenceGate(
        "yellow_fence_gate", PastelBlocks.YELLOW_PLANKS, InkColors.YELLOW);
    public static final DeferredBlock<Block> YELLOW_BUTTON = registerColoredButton(
        "yellow_button", PastelBlocks.YELLOW_PLANKS, InkColors.YELLOW);
    public static final DeferredBlock<Block> YELLOW_SLAB = registerColoredSlab(
        "yellow_slab", PastelBlocks.YELLOW_PLANKS, InkColors.YELLOW);

    //DD FLORA
    public static Properties overgrownBlackslag(MapColor color, SoundType soundGroup) {
        return settings(
            color, soundGroup, PastelBlocks.BLACKSLAG_HARDNESS, PastelBlocks.BLACKSLAG_RESISTANCE).randomTicks();
    }

    public static final DeferredBlock<Block> SAWBLADE_GRASS = register(
        blockWithItem(
            "sawblade_grass",
            () -> new BlackslagVegetationBlock(overgrownBlackslag(MapColor.SAND, SoundType.AZALEA_LEAVES)),
            InkColors.LIME
        ));
    public static final DeferredBlock<Block> SHIMMEL = register(blockWithItem(
        "shimmel",
        () -> new BlackslagVegetationBlock(overgrownBlackslag(MapColor.TERRACOTTA_GRAY, SoundType.WART_BLOCK)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> OVERGROWN_BLACKSLAG = register(blockWithItem(
        "overgrown_blackslag",
        () -> new BlackslagVegetationBlock(overgrownBlackslag(MapColor.PLANT, SoundType.VINE).speedFactor(0.925F)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> FLAYED_EARTH = register(blockWithItem(
        "flayed_earth", () -> new VariableHeightBlock(
            Properties.ofFullCopy(Blocks.MUD)
                      .mapColor(MapColor.STONE)
                      .sound(SoundType.HONEY_BLOCK)
                      .jumpFactor(0.9F)
                      .strength(5F, 15F), 14
        ), InkColors.GRAY
    ));
    //TODO: flayed earth should slow you down more and be shorter once Slake Paeisan is a thing again.

    public static final float ASH_STRENGTH = 2F;

    public static Properties ash(SoundType soundGroup) {
        return settings(
            MapColor.QUARTZ, soundGroup, PastelBlocks.ASH_STRENGTH,
            PastelBlocks.ASH_STRENGTH
        ).requiresCorrectToolForDrops();
    }

    public static final DeferredBlock<Block> ASHEN_BLACKSLAG = register(blockWithItem(
        "ashen_blackslag", () -> new BlackslagBlock(blackslag(SoundType.DEEPSLATE).mapColor(MapColor.QUARTZ)),
        InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> ASH = register(
        blockWithItem("ash", () -> new AshBlock(ash(SoundType.POWDER_SNOW)), InkColors.GRAY));
    public static final DeferredBlock<Block> ASH_PILE = register(blockWithItem(
        "ash_pile", () -> new AshPileBlock(ash(SoundType.POWDER_SNOW).replaceable()
                                                                     .isViewBlocking((state, world, pos) ->
                                                                                         state.getValue(
                                                                                             SnowLayerBlock.LAYERS) >=
                                                                                         8)
                                                                     .pushReaction(PushReaction.DESTROY)),
        InkColors.LIGHT_GRAY
    ));

    public static final DeferredBlock<Block> VARIA_SPROUT = register(blockWithItem(
        "varia_sprout", () -> new AshFloraBlock(settings(MapColor.SNOW, SoundType.STEM, 0F).instabreak()
                                                                                           .lightLevel(state -> 11)
                                                                                           .offsetType(OffsetType.XZ)
                                                                                           .dynamicShape()
                                                                                           .noCollission()
                                                                                           .hasPostProcess(
                                                                                               PastelBlocks::always)
                                                                                           .emissiveRendering(
                                                                                               PastelBlocks::always)),
        InkColors.WHITE
    ));

    public static final ToIntFunction<BlockState> LANTERN_LIGHT_PROVIDER = state -> state.getValue(
        RedstoneLampBlock.LIT) ? 15 : 0;

    public static DeferredBlock<Block> registerNoxshroom(
        String name, ResourceKey<ConfiguredFeature<?, ?>> feature, MapColor mapColor) {
        return register(blockWithItem(
            name, () -> new FungusBlock(
                feature, PastelBlocks.SHIMMEL.get(), settings(mapColor, SoundType.FUNGUS, 0.0F).noCollission()),
            InkColors.LIME
        ));
    }

    public static final DeferredBlock<Block> SLATE_NOXSHROOM = registerNoxshroom(
        "slate_noxshroom", PastelConfiguredFeatures.SLATE_NOXFUNGUS, MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> EBONY_NOXSHROOM = registerNoxshroom(
        "ebony_noxshroom", PastelConfiguredFeatures.EBONY_NOXFUNGUS, MapColor.TERRACOTTA_BLACK);
    public static final DeferredBlock<Block> IVORY_NOXSHROOM = registerNoxshroom(
        "ivory_noxshroom", PastelConfiguredFeatures.IVORY_NOXFUNGUS, MapColor.QUARTZ);
    public static final DeferredBlock<Block> CHESTNUT_NOXSHROOM = registerNoxshroom(
        "chestnut_noxshroom", PastelConfiguredFeatures.CHESTNUT_NOXFUNGUS, MapColor.CRIMSON_NYLIUM);

    public static final DeferredBlock<Block> POTTED_SLATE_NOXSHROOM = register(
        block("potted_slate_noxshroom", () -> new FlowerPotBlock(PastelBlocks.SLATE_NOXSHROOM.get(), pottedPlant())));
    public static final DeferredBlock<Block> POTTED_EBONY_NOXSHROOM = register(
        block("potted_ebony_noxshroom", () -> new FlowerPotBlock(PastelBlocks.EBONY_NOXSHROOM.get(), pottedPlant())));
    public static final DeferredBlock<Block> POTTED_IVORY_NOXSHROOM = register(
        block("potted_ivory_noxshroom", () -> new FlowerPotBlock(PastelBlocks.IVORY_NOXSHROOM.get(), pottedPlant())));
    public static final DeferredBlock<Block> POTTED_CHESTNUT_NOXSHROOM = register(block(
        "potted_chestnut_noxshroom", () -> new FlowerPotBlock(
            PastelBlocks.CHESTNUT_NOXSHROOM.get(), pottedPlant())
    ));

    public static Properties noxcap(MapColor color) {
        return settings(color, SoundType.STEM, 4.0F).instrument(NoteBlockInstrument.BASS);
    }

    public static DeferredBlock<Block> registerNoxwoodLightBlock(
        String name, DeferredBlock<Block> gillsBlock, MapColor color) {
        return register(
            blockWithItem(name, () -> new RotatedPillarBlock(noxcap(color).lightLevel(state -> 15)), InkColors.LIME));
    }

    public static <T extends FlexLanternBlock> DeferredBlock<Block> registerNoxwoodLantern(
        String name, Supplier<T> flexLanternBlock, InkColor color) {
        return register(blockWithItem(name, flexLanternBlock, color));
    }

    private static final int NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS = 30;

    public static final DeferredBlock<Block> STRIPPED_SLATE_NOXCAP_STEM = register(blockWithItem(
        "stripped_slate_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> STRIPPED_SLATE_NOXCAP_HYPHAE = register(blockWithItem(
        "stripped_slate_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> SLATE_NOXCAP_STEM = register(blockWithItem(
        "slate_noxcap_stem", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.COLOR_GRAY), PastelBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(),
            PastelLootTables.SLATE_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXCAP_HYPHAE = register(blockWithItem(
        "slate_noxcap_hyphae", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.COLOR_GRAY), PastelBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE.get(),
            PastelLootTables.SLATE_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXCAP_BLOCK = register(
        blockWithItem("slate_noxcap_block", () -> new Block(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> SLATE_NOXCAP_GILLS = register(blockWithItem(
        "slate_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9)
                                                                                   .emissiveRendering(
                                                                                       PastelBlocks::always)
                                                                                   .hasPostProcess(
                                                                                       PastelBlocks::always)),
        InkColors.LIME
    ));

    public static final DeferredBlock<Block> SLATE_NOXWOOD_PILLAR = register(blockWithItem(
        "slate_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_LAMP = register(blockWithItem(
        "slate_noxwood_lamp",
        () -> new RedstoneLampBlock(noxcap(MapColor.COLOR_GRAY).lightLevel(PastelBlocks.LANTERN_LIGHT_PROVIDER)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_LIGHT = registerNoxwoodLightBlock(
        "slate_noxwood_light", PastelBlocks.SLATE_NOXCAP_GILLS, MapColor.COLOR_GRAY);
    public static final DeferredBlock<Block> SLATE_NOXWOOD_AMPHORA = register(
        blockWithItem("slate_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_LANTERN = registerNoxwoodLantern(
        "slate_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN)
                                                                                     .lightLevel(s -> 13)
                                                                                     .pushReaction(
                                                                                         PushReaction.DESTROY)),
        InkColors.LIME
    );

    public static final DeferredBlock<Block> SLATE_NOXWOOD_PLANKS = register(
        blockWithItem("slate_noxwood_planks", () -> new Block(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_STAIRS = register(blockWithItem(
        "slate_noxwood_stairs", () -> new StairBlock(
            PastelBlocks.SLATE_NOXWOOD_PLANKS.get()
                                             .defaultBlockState(), noxcap(MapColor.COLOR_GRAY)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_SLAB = register(
        blockWithItem("slate_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_FENCE = register(
        blockWithItem("slate_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.COLOR_GRAY)), InkColors.LIME));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_FENCE_GATE = register(blockWithItem(
        "slate_noxwood_fence_gate",
        () -> new FenceGateBlock(PastelWoodTypes.SLATE_NOXWOOD, noxcap(MapColor.COLOR_GRAY)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_DOOR = register(blockWithItem(
        "slate_noxwood_door", () -> new DoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.COLOR_GRAY)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_TRAPDOOR = register(blockWithItem(
        "slate_noxwood_trapdoor", () -> new TrapDoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.COLOR_GRAY)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_BUTTON = register(blockWithItem(
        "slate_noxwood_button", () -> new ButtonBlock(
            PastelBlockSetTypes.NOXWOOD, PastelBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS,
            noxcap(MapColor.COLOR_GRAY).pushReaction(PushReaction.DESTROY)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> SLATE_NOXWOOD_PRESSURE_PLATE = register(blockWithItem(
        "slate_noxwood_pressure_plate",
        () -> new PressurePlateBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.COLOR_GRAY)), InkColors.LIME
    ));

    public static final DeferredBlock<Block> STRIPPED_EBONY_NOXCAP_STEM = register(blockWithItem(
        "stripped_ebony_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
    public static final DeferredBlock<Block> STRIPPED_EBONY_NOXCAP_HYPHAE = register(blockWithItem(
        "stripped_ebony_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXCAP_STEM = register(blockWithItem(
        "ebony_noxcap_stem", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.TERRACOTTA_BLACK), PastelBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(),
            PastelLootTables.EBONY_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXCAP_HYPHAE = register(blockWithItem(
        "ebony_noxcap_hyphae", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.TERRACOTTA_BLACK), PastelBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE.get(),
            PastelLootTables.EBONY_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXCAP_BLOCK = register(
        blockWithItem("ebony_noxcap_block", () -> new Block(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
    public static final DeferredBlock<Block> EBONY_NOXCAP_GILLS = register(blockWithItem(
        "ebony_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9)
                                                                                   .emissiveRendering(
                                                                                       PastelBlocks::always)
                                                                                   .hasPostProcess(
                                                                                       PastelBlocks::always)),
        InkColors.LIME
    ));

    public static final DeferredBlock<Block> EBONY_NOXWOOD_PILLAR = register(blockWithItem(
        "ebony_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_LAMP = register(blockWithItem(
        "ebony_noxwood_lamp", () -> new RedstoneLampBlock(
            noxcap(MapColor.TERRACOTTA_BLACK).lightLevel(PastelBlocks.LANTERN_LIGHT_PROVIDER)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_LIGHT = registerNoxwoodLightBlock(
        "ebony_noxwood_light", PastelBlocks.EBONY_NOXCAP_GILLS, MapColor.TERRACOTTA_BLACK);
    public static final DeferredBlock<Block> EBONY_NOXWOOD_AMPHORA = register(blockWithItem(
        "ebony_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_LANTERN = registerNoxwoodLantern(
        "ebony_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN)
                                                                                     .lightLevel(s -> 13)
                                                                                     .pushReaction(
                                                                                         PushReaction.DESTROY)),
        InkColors.LIME
    );

    public static final DeferredBlock<Block> EBONY_NOXWOOD_PLANKS = register(
        blockWithItem("ebony_noxwood_planks", () -> new Block(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_STAIRS = register(blockWithItem(
        "ebony_noxwood_stairs", () -> new StairBlock(
            PastelBlocks.EBONY_NOXWOOD_PLANKS.get()
                                             .defaultBlockState(), noxcap(MapColor.TERRACOTTA_BLACK)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_SLAB = register(
        blockWithItem("ebony_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_FENCE = register(
        blockWithItem("ebony_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_FENCE_GATE = register(blockWithItem(
        "ebony_noxwood_fence_gate",
        () -> new FenceGateBlock(PastelWoodTypes.EBONY_NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_DOOR = register(blockWithItem(
        "ebony_noxwood_door", () -> new DoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_TRAPDOOR = register(blockWithItem(
        "ebony_noxwood_trapdoor",
        () -> new TrapDoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_BUTTON = register(blockWithItem(
        "ebony_noxwood_button", () -> new ButtonBlock(
            PastelBlockSetTypes.NOXWOOD, PastelBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS,
            noxcap(MapColor.TERRACOTTA_BLACK).pushReaction(PushReaction.DESTROY)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> EBONY_NOXWOOD_PRESSURE_PLATE = register(blockWithItem(
        "ebony_noxwood_pressure_plate",
        () -> new PressurePlateBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.TERRACOTTA_BLACK)), InkColors.LIME
    ));

    public static final DeferredBlock<Block> STRIPPED_IVORY_NOXCAP_STEM = register(blockWithItem(
        "stripped_ivory_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> STRIPPED_IVORY_NOXCAP_HYPHAE = register(blockWithItem(
        "stripped_ivory_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> IVORY_NOXCAP_STEM = register(blockWithItem(
        "ivory_noxcap_stem", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.QUARTZ), PastelBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(),
            PastelLootTables.IVORY_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXCAP_HYPHAE = register(blockWithItem(
        "ivory_noxcap_hyphae", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.QUARTZ), PastelBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE.get(),
            PastelLootTables.IVORY_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXCAP_BLOCK = register(
        blockWithItem("ivory_noxcap_block", () -> new Block(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> IVORY_NOXCAP_GILLS = register(blockWithItem(
        "ivory_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9)
                                                                                   .emissiveRendering(
                                                                                       PastelBlocks::always)
                                                                                   .hasPostProcess(
                                                                                       PastelBlocks::always)),
        InkColors.LIME
    ));

    public static final DeferredBlock<Block> IVORY_NOXWOOD_PILLAR = register(
        blockWithItem("ivory_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_LAMP = register(blockWithItem(
        "ivory_noxwood_lamp",
        () -> new RedstoneLampBlock(noxcap(MapColor.QUARTZ).lightLevel(PastelBlocks.LANTERN_LIGHT_PROVIDER)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_LIGHT = registerNoxwoodLightBlock(
        "ivory_noxwood_light", PastelBlocks.IVORY_NOXCAP_GILLS, MapColor.QUARTZ);
    public static final DeferredBlock<Block> IVORY_NOXWOOD_AMPHORA = register(
        blockWithItem("ivory_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_LANTERN = registerNoxwoodLantern(
        "ivory_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN)
                                                                                     .lightLevel(s -> 13)
                                                                                     .pushReaction(
                                                                                         PushReaction.DESTROY)),
        InkColors.LIME
    );

    public static final DeferredBlock<Block> IVORY_NOXWOOD_PLANKS = register(
        blockWithItem("ivory_noxwood_planks", () -> new Block(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_STAIRS = register(blockWithItem(
        "ivory_noxwood_stairs", () -> new StairBlock(
            PastelBlocks.IVORY_NOXWOOD_PLANKS.get()
                                             .defaultBlockState(), noxcap(MapColor.QUARTZ)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_SLAB = register(
        blockWithItem("ivory_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_FENCE = register(
        blockWithItem("ivory_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_FENCE_GATE = register(blockWithItem(
        "ivory_noxwood_fence_gate", () -> new FenceGateBlock(PastelWoodTypes.CHESTNUT_NOXWOOD, noxcap(MapColor.QUARTZ)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_DOOR = register(blockWithItem(
        "ivory_noxwood_door", () -> new DoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.QUARTZ)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_TRAPDOOR = register(blockWithItem(
        "ivory_noxwood_trapdoor", () -> new TrapDoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.QUARTZ)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_BUTTON = register(blockWithItem(
        "ivory_noxwood_button", () -> new ButtonBlock(
            PastelBlockSetTypes.NOXWOOD, PastelBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS,
            noxcap(MapColor.QUARTZ).pushReaction(PushReaction.DESTROY)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> IVORY_NOXWOOD_PRESSURE_PLATE = register(blockWithItem(
        "ivory_noxwood_pressure_plate",
        () -> new PressurePlateBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.QUARTZ)), InkColors.LIME
    ));

    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_NOXCAP_STEM = register(blockWithItem(
        "stripped_chestnut_noxcap_stem", () -> new RotatedPillarBlock(noxcap(MapColor.CRIMSON_NYLIUM)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> STRIPPED_CHESTNUT_NOXCAP_HYPHAE = register(blockWithItem(
        "stripped_chestnut_noxcap_hyphae", () -> new RotatedPillarBlock(noxcap(MapColor.QUARTZ)), InkColors.LIME));
    public static final DeferredBlock<Block> CHESTNUT_NOXCAP_STEM = register(blockWithItem(
        "chestnut_noxcap_stem", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.CRIMSON_NYLIUM), PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(),
            PastelLootTables.CHESTNUT_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXCAP_HYPHAE = register(blockWithItem(
        "chestnut_noxcap_hyphae", () -> new StrippingLootPillarBlock(
            noxcap(MapColor.QUARTZ), PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE.get(),
            PastelLootTables.CHESTNUT_NOXCAP_STRIPPING
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXCAP_BLOCK = register(
        blockWithItem("chestnut_noxcap_block", () -> new Block(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
    public static final DeferredBlock<Block> CHESTNUT_NOXCAP_GILLS = register(blockWithItem(
        "chestnut_noxcap_gills", () -> new RotatedPillarBlock(noxcap(MapColor.DIAMOND).lightLevel(state -> 9)
                                                                                      .emissiveRendering(
                                                                                          PastelBlocks::always)
                                                                                      .hasPostProcess(
                                                                                          PastelBlocks::always)),
        InkColors.LIME
    ));

    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_PILLAR = register(blockWithItem(
        "chestnut_noxwood_pillar", () -> new RotatedPillarBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_LAMP = register(blockWithItem(
        "chestnut_noxwood_lamp", () -> new RedstoneLampBlock(
            noxcap(MapColor.CRIMSON_NYLIUM).lightLevel(PastelBlocks.LANTERN_LIGHT_PROVIDER)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_LIGHT = registerNoxwoodLightBlock(
        "chestnut_noxwood_light", PastelBlocks.CHESTNUT_NOXCAP_GILLS, MapColor.CRIMSON_NYLIUM);
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_AMPHORA = register(blockWithItem(
        "chestnut_noxwood_amphora", () -> new AmphoraBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_LANTERN = registerNoxwoodLantern(
        "chestnut_noxwood_lantern", () -> new FlexLanternBlock(BlockBehaviour.Properties.ofFullCopy(LANTERN)
                                                                                        .lightLevel(s -> 13)
                                                                                        .pushReaction(
                                                                                            PushReaction.DESTROY)),
        InkColors.LIME
    );

    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_PLANKS = register(
        blockWithItem("chestnut_noxwood_planks", () -> new Block(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_STAIRS = register(blockWithItem(
        "chestnut_noxwood_stairs", () -> new StairBlock(
            PastelBlocks.CHESTNUT_NOXWOOD_PLANKS.get()
                                                .defaultBlockState(), noxcap(MapColor.CRIMSON_NYLIUM)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_SLAB = register(
        blockWithItem("chestnut_noxwood_slab", () -> new SlabBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_FENCE = register(
        blockWithItem("chestnut_noxwood_fence", () -> new FenceBlock(noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_FENCE_GATE = register(blockWithItem(
        "chestnut_noxwood_fence_gate",
        () -> new FenceGateBlock(PastelWoodTypes.IVORY_NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_DOOR = register(blockWithItem(
        "chestnut_noxwood_door", () -> new DoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_TRAPDOOR = register(blockWithItem(
        "chestnut_noxwood_trapdoor",
        () -> new TrapDoorBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_BUTTON = register(blockWithItem(
        "chestnut_noxwood_button", () -> new ButtonBlock(
            PastelBlockSetTypes.NOXWOOD, PastelBlocks.NOXCAP_BUTTON_BLOCK_PRESS_TIME_TICKS,
            noxcap(MapColor.CRIMSON_NYLIUM).pushReaction(PushReaction.DESTROY)
        ), InkColors.LIME
    ));
    public static final DeferredBlock<Block> CHESTNUT_NOXWOOD_PRESSURE_PLATE = register(blockWithItem(
        "chestnut_noxwood_pressure_plate",
        () -> new PressurePlateBlock(PastelBlockSetTypes.NOXWOOD, noxcap(MapColor.CRIMSON_NYLIUM)), InkColors.LIME
    ));

    public static Properties galaWood(MapColor color) {
        return settings(color, SoundType.CHERRY_WOOD, 30.0F).instrument(NoteBlockInstrument.BASS)
                                                            .ignitedByLava();
    }

    public static final DeferredBlock<Block> WEEPING_GALA_SPRIG = register(blockWithItem(
        "weeping_gala_sprig",
        () -> new WeepingGalaSprigBlock(copyWithMapColor(OAK_SAPLING, MapColor.WARPED_WART_BLOCK)), InkColors.LIME
    ));
    public static final DeferredBlock<Block> POTTED_WEEPING_GALA_SPRIG = register(block(
        "potted_weeping_gala_sprig", () -> new FlowerPotBlock(
            PastelBlocks.WEEPING_GALA_SPRIG.get(), pottedPlant())
    ));

    public static final DeferredBlock<Block> WEEPING_GALA_LEAVES = register(blockWithItem(
        "weeping_gala_leaves", () -> new LeavesBlock(copyWithMapColor(OAK_LEAVES, MapColor.WARPED_WART_BLOCK)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> STRIPPED_WEEPING_GALA_LOG = register(burnable(
        blockWithItem(
            "stripped_weeping_gala_log", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME),
        600
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_LOG = register(burnable(
        blockWithItem(
            "weeping_gala_log", () -> new PastelLogBlock(
                galaWood(MapColor.COLOR_BROWN),
                PastelBlocks.STRIPPED_WEEPING_GALA_LOG.get()
            ), InkColors.LIME
        ), 600
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_WOOD = register(burnable(
        blockWithItem(
            "weeping_gala_wood", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600
    ));
    public static final DeferredBlock<Block> STRIPPED_WEEPING_GALA_WOOD = register(burnable(
        blockWithItem(
            "stripped_weeping_gala_wood", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME),
        600
    ));

    public static final DeferredBlock<Block> WEEPING_GALA_FRONDS = register(block(
        "weeping_gala_fronds", () -> new WeepingGalaFrondsBlock(
            Properties.ofFullCopy(PastelBlocks.WEEPING_GALA_LEAVES.get())
                      .noCollission())
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_FRONDS_PLANT = register(block(
        "weeping_gala_fronds_plant", () -> new WeepingGalaFrondsTipBlock(Properties.ofFullCopy(
                                                                                       PastelBlocks.WEEPING_GALA_LEAVES.get())
                                                                                   .noCollission()
                                                                                   .lightLevel(s -> s.getValue(
                                                                                                         WeepingGalaFrondsTipBlock.FORM)
                                                                                                     .getLuminance()))
    ));

    public static final BlockSetType GALA_BLOCK_SET_TYPE = new BlockSetType("gala");
    public static final WoodType GALA_WOOD_TYPE = new WoodType("gala", PastelBlocks.GALA_BLOCK_SET_TYPE);

    public static final DeferredBlock<Block> WEEPING_GALA_PLANKS = register(burnable(
        blockWithItem("weeping_gala_planks", () -> new Block(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 600));
    public static final DeferredBlock<Block> WEEPING_GALA_STAIRS = register(burnable(
        blockWithItem(
            "weeping_gala_stairs", () -> new StairBlock(
                PastelBlocks.WEEPING_GALA_PLANKS.get()
                                                .defaultBlockState(), galaWood(MapColor.COLOR_BROWN)
            ), InkColors.LIME
        ), 600
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_SLAB = register(burnable(
        blockWithItem("weeping_gala_slab", () -> new SlabBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME), 300));
    public static final DeferredBlock<Block> WEEPING_GALA_FENCE = register(burnable(
        blockWithItem("weeping_gala_fence", () -> new FenceBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME),
        600
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_FENCE_GATE = register(burnable(
        blockWithItem(
            "weeping_gala_fence_gate",
            () -> new FenceGateBlock(PastelBlocks.GALA_WOOD_TYPE, galaWood(MapColor.COLOR_BROWN)), InkColors.LIME
        ), 600
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_DOOR = register(burnable(
        blockWithItem(
            "weeping_gala_door", () -> new DoorBlock(PastelBlocks.GALA_BLOCK_SET_TYPE, galaWood(MapColor.COLOR_BROWN)),
            InkColors.LIME
        ), 400
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_TRAPDOOR = register(burnable(
        blockWithItem(
            "weeping_gala_trapdoor",
            () -> new TrapDoorBlock(PastelBlocks.GALA_BLOCK_SET_TYPE, galaWood(MapColor.COLOR_BROWN)), InkColors.LIME
        ), 600
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_BUTTON = register(burnable(
        blockWithItem("weeping_gala_button", () -> woodenButton(PastelBlocks.GALA_BLOCK_SET_TYPE), InkColors.LIME),
        200
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_PRESSURE_PLATE = register(burnable(
        blockWithItem(
            "weeping_gala_pressure_plate",
            () -> new PressurePlateBlock(PastelBlocks.GALA_BLOCK_SET_TYPE, galaWood(MapColor.COLOR_BROWN)),
            InkColors.LIME
        ), 600
    ));

    public static final DeferredBlock<Block> WEEPING_GALA_PILLAR = register(blockWithItem(
        "weeping_gala_pillar", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME));
    public static final DeferredBlock<Block> WEEPING_GALA_BARREL = register(
        blockWithItem("weeping_gala_barrel", () -> new BarrelBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME));
    public static final DeferredBlock<Block> WEEPING_GALA_AMPHORA = register(
        blockWithItem("weeping_gala_amphora", () -> new AmphoraBlock(galaWood(MapColor.COLOR_BROWN)), InkColors.LIME));
    public static final DeferredBlock<Block> WEEPING_GALA_LANTERN = register(blockWithItem(
        "weeping_gala_lantern", () -> new FlexLanternBlock(galaWood(MapColor.COLOR_BROWN).lightLevel(state -> 13)
                                                                                         .noOcclusion()
                                                                                         .pushReaction(
                                                                                             PushReaction.DESTROY)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_LAMP = register(blockWithItem(
        "weeping_gala_lamp",
        () -> new RedstoneLampBlock(galaWood(MapColor.COLOR_BROWN).lightLevel(PastelBlocks.LANTERN_LIGHT_PROVIDER)),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> WEEPING_GALA_LIGHT = register(blockWithItem(
        "weeping_gala_light", () -> new RotatedPillarBlock(galaWood(MapColor.COLOR_BROWN).lightLevel(state -> 15)
                                                                                         .noOcclusion()), InkColors.LIME
    ));

    public static final DeferredBlock<Block> TEA_TABLE = register(
        blockWithItem("tea_table", () -> new TeaTable(galaWood(MapColor.COLOR_BROWN).noOcclusion()), InkColors.LIME));
    public static final DeferredBlock<Block> WHITE_CUSHION = register(blockWithItem(
        "cushion_white", () -> new CushionBlock(
            Properties.ofFullCopy(WHITE_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.WHITE
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> ORANGE_CUSHION = register(blockWithItem(
        "cushion_orange", () -> new CushionBlock(
            Properties.ofFullCopy(ORANGE_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.ORANGE
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> MAGENTA_CUSHION = register(blockWithItem(
        "cushion_magenta", () -> new CushionBlock(
            Properties.ofFullCopy(MAGENTA_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.MAGENTA
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> LIGHT_BLUE_CUSHION = register(blockWithItem(
        "cushion_light_blue", () -> new CushionBlock(
            Properties.ofFullCopy(LIGHT_BLUE_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.LIGHT_BLUE
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> YELLOW_CUSHION = register(blockWithItem(
        "cushion_yellow", () -> new CushionBlock(
            Properties.ofFullCopy(YELLOW_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.YELLOW
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> LIME_CUSHION = register(blockWithItem(
        "cushion_lime", () -> new CushionBlock(
            Properties.ofFullCopy(LIME_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.LIME
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> PINK_CUSHION = register(blockWithItem(
        "cushion_pink", () -> new CushionBlock(
            Properties.ofFullCopy(PINK_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.PINK
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> GRAY_CUSHION = register(blockWithItem(
        "cushion_gray", () -> new CushionBlock(
            Properties.ofFullCopy(GRAY_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.GRAY
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> LIGHT_GRAY_CUSHION = register(blockWithItem(
        "cushion_light_gray", () -> new CushionBlock(
            Properties.ofFullCopy(LIGHT_GRAY_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.LIGHT_GRAY
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> CYAN_CUSHION = register(blockWithItem(
        "cushion_cyan", () -> new CushionBlock(
            Properties.ofFullCopy(CYAN_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.CYAN
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> PURPLE_CUSHION = register(blockWithItem(
        "cushion_purple", () -> new CushionBlock(
            Properties.ofFullCopy(PURPLE_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.PURPLE
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> BLUE_CUSHION = register(blockWithItem(
        "cushion_blue", () -> new CushionBlock(
            Properties.ofFullCopy(BLUE_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.BLUE
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> BROWN_CUSHION = register(blockWithItem(
        "cushion_brown", () -> new CushionBlock(
            Properties.ofFullCopy(BROWN_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.BROWN
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> GREEN_CUSHION = register(blockWithItem(
        "cushion_green", () -> new CushionBlock(
            Properties.ofFullCopy(GREEN_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.GREEN
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> RED_CUSHION = register(blockWithItem(
        "cushion_red", () -> new CushionBlock(
            Properties.ofFullCopy(RED_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.RED
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));
    public static final DeferredBlock<Block> BLACK_CUSHION = register(blockWithItem(
        "cushion_black", () -> new CushionBlock(
            Properties.ofFullCopy(BLACK_WOOL)
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), DyeColor.BLACK
        ), IS.of(Rarity.COMMON), InkColors.PINK
    ));

    public static Properties basalMarble() {
        return settings(MapColor.COLOR_GRAY, SoundType.DRIPSTONE_BLOCK, 8.0F).instrument(NoteBlockInstrument.BASEDRUM)
                                                                             .requiresCorrectToolForDrops();
    }

    public static final DeferredBlock<Block> BASAL_MARBLE = register(
        blockWithItem("basal_marble", () -> new RotatedPillarBlock(basalMarble()), InkColors.BROWN));
    public static final DeferredBlock<Block> BASAL_MARBLE_STAIRS = register(blockWithItem(
        "basal_marble_stairs", () -> new StairBlock(
            PastelBlocks.BASAL_MARBLE.get()
                                     .defaultBlockState(), basalMarble()
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASAL_MARBLE_SLAB = register(
        blockWithItem("basal_marble_slab", () -> new SlabBlock(basalMarble()), InkColors.BROWN));
    public static final DeferredBlock<Block> BASAL_MARBLE_WALL = register(
        blockWithItem("basal_marble_wall", () -> new WallBlock(basalMarble()), InkColors.BROWN));

    public static final DeferredBlock<Block> BASAL_MARBLE_PILLAR = register(
        blockWithItem("basal_marble_pillar", () -> new RotatedPillarBlock(basalMarble()), InkColors.BROWN));

    public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE = register(
        blockWithItem("polished_basal_marble", () -> new PastelFacingBlock(basalMarble()), InkColors.BROWN));
    public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE_STAIRS = register(blockWithItem(
        "polished_basal_marble_stairs", () -> new StairBlock(
            PastelBlocks.POLISHED_BASAL_MARBLE.get()
                                              .defaultBlockState(), basalMarble()
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE_SLAB = register(
        blockWithItem("polished_basal_marble_slab", () -> new SlabBlock(basalMarble()), InkColors.BROWN));
    public static final DeferredBlock<Block> POLISHED_BASAL_MARBLE_WALL = register(
        blockWithItem("polished_basal_marble_wall", () -> new WallBlock(basalMarble()), InkColors.BROWN));

    public static final DeferredBlock<Block> BASAL_MARBLE_TILES = register(
        blockWithItem("basal_marble_tiles", () -> new Block(basalMarble()), InkColors.BROWN));
    public static final DeferredBlock<Block> BASAL_MARBLE_TILE_STAIRS = register(blockWithItem(
        "basal_marble_tile_stairs", () -> new StairBlock(
            PastelBlocks.BASAL_MARBLE_TILES.get()
                                           .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.BASAL_MARBLE_TILES.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASAL_MARBLE_TILE_SLAB = register(blockWithItem(
        "basal_marble_tile_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.BASAL_MARBLE_TILES.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASAL_MARBLE_TILE_WALL = register(blockWithItem(
        "basal_marble_tile_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.BASAL_MARBLE_TILES.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> BASAL_MARBLE_BRICKS = register(
        blockWithItem("basal_marble_bricks", () -> new Block(basalMarble()), InkColors.BROWN));
    public static final DeferredBlock<Block> BASAL_MARBLE_BRICK_STAIRS = register(blockWithItem(
        "basal_marble_brick_stairs", () -> new StairBlock(
            PastelBlocks.BASAL_MARBLE_BRICKS.get()
                                            .defaultBlockState(),
            Properties.ofFullCopy(PastelBlocks.BASAL_MARBLE_BRICKS.get())
        ), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASAL_MARBLE_BRICK_SLAB = register(blockWithItem(
        "basal_marble_brick_slab", () -> new SlabBlock(Properties.ofFullCopy(PastelBlocks.BASAL_MARBLE_BRICKS.get())),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> BASAL_MARBLE_BRICK_WALL = register(blockWithItem(
        "basal_marble_brick_wall", () -> new WallBlock(Properties.ofFullCopy(PastelBlocks.BASAL_MARBLE_BRICKS.get())),
        InkColors.BROWN
    ));

    public static final DeferredBlock<Block> LONGING_CHIMERA = register(blockWithItem(
        "longing_chimera",
        () -> new GrotesqueBlock(basalMarble().noOcclusion(), 12, 15, "block.pastel.longing_chimera.tooltip"),
        InkColors.BROWN
    ));

    public static DeferredBlock<Block> registerSmallDragonjagBlock(String name, Dragonjag.Variant variant) {
        return register(blockWithItem(
            name, () -> new SmallDragonjagBlock(settings(variant.getMapColor(), SoundType.GRASS, 1.0F), variant),
            InkColors.LIME
        ));
    }

    public static final DeferredBlock<Block> SMALL_RED_DRAGONJAG = registerSmallDragonjagBlock(
        "small_red_dragonjag", Dragonjag.Variant.RED);
    public static final DeferredBlock<Block> SMALL_YELLOW_DRAGONJAG = registerSmallDragonjagBlock(
        "small_yellow_dragonjag", Dragonjag.Variant.YELLOW);
    public static final DeferredBlock<Block> SMALL_PINK_DRAGONJAG = registerSmallDragonjagBlock(
        "small_pink_dragonjag", Dragonjag.Variant.PINK);
    public static final DeferredBlock<Block> SMALL_PURPLE_DRAGONJAG = registerSmallDragonjagBlock(
        "small_purple_dragonjag", Dragonjag.Variant.PURPLE);
    public static final DeferredBlock<Block> SMALL_BLACK_DRAGONJAG = registerSmallDragonjagBlock(
        "small_black_dragonjag", Dragonjag.Variant.BLACK);

    public static DeferredBlock<Block> registerTallDragonjagBlock(String name, Dragonjag.Variant variant) {
        return register(
            block(name, () -> new TallDragonjagBlock(settings(variant.getMapColor(), SoundType.GRASS, 1.0F), variant)));
    }

    public static final DeferredBlock<Block> TALL_YELLOW_DRAGONJAG = registerTallDragonjagBlock(
        "tall_yellow_dragonjag", Dragonjag.Variant.YELLOW);
    public static final DeferredBlock<Block> TALL_RED_DRAGONJAG = registerTallDragonjagBlock(
        "tall_red_dragonjag", Dragonjag.Variant.RED);
    public static final DeferredBlock<Block> TALL_PINK_DRAGONJAG = registerTallDragonjagBlock(
        "tall_pink_dragonjag", Dragonjag.Variant.PINK);
    public static final DeferredBlock<Block> TALL_PURPLE_DRAGONJAG = registerTallDragonjagBlock(
        "tall_purple_dragonjag", Dragonjag.Variant.PURPLE);
    public static final DeferredBlock<Block> TALL_BLACK_DRAGONJAG = registerTallDragonjagBlock(
        "tall_black_dragonjag", Dragonjag.Variant.BLACK);

    //Flora
    public static final DeferredBlock<Block> ALOE = register(block(
        "aloe", () -> new AloeBlock(settings(MapColor.PLANT, SoundType.GRASS, 1.0F).noCollission()
                                                                                   .randomTicks()
                                                                                   .noOcclusion())
    ));
    public static final DeferredBlock<Block> SAWBLADE_HOLLY_BUSH = register(block(
        "sawblade_holly_bush", () -> new SawbladeHollyBushBlock(settings(
            MapColor.TERRACOTTA_GREEN, SoundType.GRASS, 0.0F).noCollission()
                                                             .randomTicks()
                                                             .noOcclusion()
                                                             .lightLevel(s -> s.getValue(SawbladeHollyBushBlock.AGE) ==
                                                                              SawbladeHollyBushBlock.MAX_AGE ? 10 : 0))
    ));
    public static final DeferredBlock<Block> BRISTLE_SPROUTS = register(blockWithItem(
        "bristle_sprouts", () -> new BristleSproutsBlock(settings(MapColor.GRASS, SoundType.GRASS, 0.0F).noCollission()
                                                                                                        .noOcclusion()
                                                                                                        .offsetType(
                                                                                                            OffsetType.XZ)
                                                                                                        .replaceable()),
        InkColors.LIME
    ));
    public static final DeferredBlock<Block> DOOMBLOOM = register(block(
        "doombloom", () -> new DoomBloomBlock(
            PastelMobEffects.STIFFNESS, 8, settings(MapColor.GRASS, SoundType.GRASS, 0.0F).randomTicks()
                                                                                          .noCollission()
                                                                                          .lightLevel((state) ->
                                                                                                          state.getValue(
                                                                                                              DoomBloomBlock.AGE) *
                                                                                                          2)
                                                                                          .noOcclusion()
        )
    ));
    public static final DeferredBlock<Block> SNAPPING_IVY = register(blockWithItem(
        "snapping_ivy", () -> new SnappingIvyBlock(settings(MapColor.GRASS, SoundType.GRASS, 3.0F).noCollission()
                                                                                                  .noOcclusion()),
        InkColors.RED
    ));

    public static final DeferredBlock<Block> ABYSSAL_VINES = register(block(
        "abyssal_vines", () -> new AbyssalVineBlock(settings(MapColor.PLANT, SoundType.CAVE_VINES, 2.0F).noCollission()
                                                                                                        .offsetType(
                                                                                                            OffsetType.XYZ)
                                                                                                        .randomTicks()
                                                                                                        .noOcclusion()
                                                                                                        .lightLevel(
                                                                                                            state ->
                                                                                                                state.getValue(
                                                                                                                    BlockStateProperties.BERRIES)
                                                                                                                ? 13
                                                                                                                : 0))
    ));
    public static final DeferredBlock<Block> NIGHTDEW = register(block(
        "nightdew", () -> new NightdewBlock(settings(MapColor.WARPED_NYLIUM, SoundType.CAVE_VINES, 0.0F).noCollission()
                                                                                                        .offsetType(
                                                                                                            OffsetType.XYZ)
                                                                                                        .randomTicks()
                                                                                                        .noOcclusion()
                                                                                                        .instabreak())
    ));
    public static final DeferredBlock<Block> SWEET_PEA = register(blockWithItem(
        "sweet_pea", () -> new FlowerBlock(
            MobEffects.NIGHT_VISION, 5, settings(MapColor.COLOR_MAGENTA, SoundType.GRASS, 0.0F).offsetType(
                                                                                                   OffsetType.XZ)
                                                                                               .noCollission()
                                                                                               .noOcclusion()
                                                                                               .lightLevel(s -> 11)
                                                                                               .hasPostProcess(
                                                                                                   PastelBlocks::always)
                                                                                               .emissiveRendering(
                                                                                                   PastelBlocks::always)
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> APRICOTTI = register(blockWithItem(
        "apricotti", () -> new FlowerBlock(
            MobEffects.GLOWING, 5, settings(MapColor.COLOR_ORANGE, SoundType.GRASS, 0.0F).offsetType(OffsetType.XZ)
                                                                                         .noCollission()
                                                                                         .noOcclusion()
                                                                                         .lightLevel(s -> 11)
                                                                                         .hasPostProcess(
                                                                                             PastelBlocks::always)
                                                                                         .emissiveRendering(
                                                                                             PastelBlocks::always)
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> HUMMING_BELL = register(blockWithItem(
        "humming_bell", () -> new FlowerBlock(
            PastelMobEffects.LIGHTWEIGHT, 5, settings(MapColor.COLOR_LIGHT_BLUE, SoundType.GRASS, 0.0F).offsetType(
                                                                                                           OffsetType.XZ)
                                                                                                       .noCollission()
                                                                                                       .noOcclusion()
                                                                                                       .lightLevel(
                                                                                                           s -> 9)
                                                                                                       .hasPostProcess(
                                                                                                           PastelBlocks::always)
                                                                                                       .emissiveRendering(
                                                                                                           PastelBlocks::always)
        ), InkColors.LIME
    ));

    public static final DeferredBlock<Block> HUMMINGSTONE_GLASS = register(blockWithItem(
        "hummingstone_glass", () -> new TransparentBlock(settings(
            MapColor.SAND, SoundType.GLASS, 5.0F, 100.0F).noOcclusion()
                                                         .requiresCorrectToolForDrops()), InkColors.LIGHT_BLUE
    ));
    public static final DeferredBlock<Block> HUMMINGSTONE_GLASS_PANE = register(blockWithItem(
        "hummingstone_glass_pane",
        () -> new IronBarsBlock(Properties.ofFullCopy(PastelBlocks.HUMMINGSTONE_GLASS.get())), InkColors.LIGHT_BLUE
    ));
    public static final DeferredBlock<Block> HUMMINGSTONE = register(blockWithItem(
        "hummingstone", () -> new HummingstoneBlock(Properties.ofFullCopy(PastelBlocks.HUMMINGSTONE_GLASS.get())
                                                              .lightLevel((state) -> 14)), InkColors.LIGHT_BLUE
    ));
    public static final DeferredBlock<Block> WAXED_HUMMINGSTONE = register(blockWithItem(
        "waxed_hummingstone", () -> new TransparentBlock(Properties.ofFullCopy(PastelBlocks.HUMMINGSTONE.get())),
        InkColors.LIGHT_BLUE
    ));

    public static final DeferredBlock<Block> MOSS_BALL = register(blockWithItem(
        "moss_ball", () -> new MossBallBlock(settings(MapColor.PLANT, SoundType.WET_GRASS, 1F).noCollission()
                                                                                              .noOcclusion()
                                                                                              .offsetType(
                                                                                                  OffsetType.XYZ)),
        InkColors.GREEN
    ));
    public static final DeferredBlock<Block> GIANT_MOSS_BALL = register(blockWithItem(
        "giant_moss_ball", () -> new GiantMossBallBlock(settings(
            MapColor.PLANT, SoundType.WET_GRASS, 10F).noCollission()
                                                     .noOcclusion()
                                                     .offsetType(OffsetType.XYZ)), InkColors.GREEN
    ));

    public static final DeferredBlock<Block> RESPLENDENT_BLOCK = register(blockWithItem(
        "resplendent_block", () -> new CushionedFacingBlock(Properties.ofFullCopy(RED_WOOL)), IS.of(Rarity.UNCOMMON),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> RESPLENDENT_CUSHION = register(blockWithItem(
        "resplendent_cushion", () -> new CushionBlock(
            Properties.ofFullCopy(PastelBlocks.RESPLENDENT_BLOCK.get())
                      .noOcclusion()
                      .isValidSpawn(PastelBlocks::never), null
        ), IS.of(Rarity.UNCOMMON), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> RESPLENDENT_CARPET = register(blockWithItem(
        "resplendent_carpet", () -> new CushionedCarpetBlock(Properties.ofFullCopy(RED_CARPET)), IS.of(Rarity.UNCOMMON),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> RESPLENDENT_BED = register(blockWithItem(
        "resplendent_bed", () -> new PastelBedBlock(DyeColor.RED, Properties.ofFullCopy(RED_BED)),
        IS.of(1, Rarity.UNCOMMON), InkColors.YELLOW
    ));

    // JADE VINES
    public static Properties jadeVine() {
        return settings(MapColor.GRASS, SoundType.WOOL, 0.1F).noCollission()
                                                             .noOcclusion();
    }

    public static final DeferredBlock<Block> JADE_VINE_ROOTS = register(block(
        "jade_vine_roots", () -> new JadeVineRootsBlock(jadeVine().randomTicks()
                                                                  .lightLevel(
                                                                      (state) -> state.getValue(JadeVineRootsBlock.DEAD)
                                                                                 ? 0 : 4))
    ));
    public static final DeferredBlock<Block> JADE_VINE_BULB = register(block(
        "jade_vine_bulb", () -> new JadeVineBulbBlock(
            jadeVine().lightLevel((state) -> state.getValue(JadeVineBulbBlock.DEAD) ? 0 : 5))
    ));
    public static final DeferredBlock<Block> JADE_VINES = register(block(
        "jade_vines", () -> new JadeVinePlantBlock(
            jadeVine().lightLevel((state) -> state.getValue(JadeVinePlantBlock.AGE) == 0 ? 0 : 5))
    ));
    public static final DeferredBlock<Block> JADE_PETAL_BLOCK = register(blockWithItem(
        "jade_petal_block", () -> new JadeVinePetalBlock(jadeVine().lightLevel(state -> 3)), InkColors.LIME));
    public static final DeferredBlock<Block> JADE_PETAL_CARPET = register(
        blockWithItem("jade_petal_carpet", () -> new CarpetBlock(jadeVine().lightLevel(state -> 3)), InkColors.LIME));

    public static final DeferredBlock<Block> NEPHRITE_BLOSSOM_STEM = register(blockWithItem(
        "nephrite_blossom_stem", () -> new NephriteBlossomStemBlock(settings(
            MapColor.COLOR_PINK, SoundType.WOOL, 2.0F).noOcclusion()
                                                      .noCollission()), InkColors.PINK
    ));
    public static final DeferredBlock<Block> NEPHRITE_BLOSSOM_LEAVES = register(blockWithItem(
        "nephrite_blossom_leaves", () -> new NephriteBlossomLeavesBlock(settings(
            MapColor.COLOR_PINK, SoundType.GRASS, 0.2F).noOcclusion()
                                                       .randomTicks()
                                                       .lightLevel(state -> 13)), InkColors.PINK
    ));
    public static final DeferredBlock<Block> NEPHRITE_BLOSSOM_BULB = register(blockWithItem(
        "nephrite_blossom_bulb",
        () -> new NephriteBlossomBulbBlock(Properties.ofFullCopy(PastelBlocks.NEPHRITE_BLOSSOM_STEM.get())), IS.of(16),
        InkColors.PINK
    ));

    public static Properties jadeite() {
        return settings(MapColor.WOOL, SoundType.WOOL, 0.1F).noCollission()
                                                            .noOcclusion()
                                                            .lightLevel(state -> 12)
                                                            .hasPostProcess(PastelBlocks::always)
                                                            .emissiveRendering(PastelBlocks::always);
    }

    public static final DeferredBlock<Block> JADEITE_LOTUS_STEM = register(blockWithItem(
        "jadeite_lotus_stem", () -> new JadeiteLotusStemBlock(settings(
            MapColor.COLOR_BLACK, SoundType.WOOL, 2.0F).noOcclusion()
                                                       .noCollission()), InkColors.LIME
    ));
    public static final DeferredBlock<Block> JADEITE_LOTUS_FLOWER = register(blockWithItem(
        "jadeite_lotus_flower", () -> new JadeiteLotusFlowerBlock(settings(
            MapColor.SNOW, SoundType.WOOL, 2.0F).lightLevel(state -> 14)
                                                .hasPostProcess(PastelBlocks::always)
                                                .emissiveRendering(PastelBlocks::always)), IS.of(16), InkColors.LIME
    ));
    public static final DeferredBlock<Block> JADEITE_LOTUS_BULB = register(blockWithItem(
        "jadeite_lotus_bulb", () -> new JadeiteLotusBulbBlock(
            Properties.ofFullCopy(PastelBlocks.JADEITE_LOTUS_STEM.get())
                      .noOcclusion()), IS.of(16), InkColors.LIME
    ));
    public static final DeferredBlock<Block> JADEITE_PETAL_BLOCK = register(
        blockWithItem("jadeite_petal_block", () -> new JadeVinePetalBlock(jadeite()), InkColors.LIME));
    public static final DeferredBlock<Block> JADEITE_PETAL_CARPET = register(
        blockWithItem("jadeite_petal_carpet", () -> new CarpetBlock(jadeite()), InkColors.LIME));

    private static Properties ore() {
        return BlockBehaviour.Properties.ofFullCopy(IRON_ORE);
    }

    private static Properties deepslateOre() {
        return BlockBehaviour.Properties.ofFullCopy(DEEPSLATE_IRON_ORE);
    }

    private static Properties blackslagOre() {
        return BlockBehaviour.Properties.ofFullCopy(PastelBlocks.BLACKSLAG.get())
                                        .strength(
                                            PastelBlocks.BLACKSLAG_HARDNESS * 1.5F,
                                            PastelBlocks.BLACKSLAG_RESISTANCE * 2F
                                        )
                                        .requiresCorrectToolForDrops();
    }

    private static Properties netherrackOre() {
        return BlockBehaviour.Properties.ofFullCopy(NETHERRACK)
                                        .strength(3.0F, 3.0F)
                                        .sound(SoundType.NETHER_ORE)
                                        .requiresCorrectToolForDrops();
    }

    private static Properties endstoneOre() {
        return BlockBehaviour.Properties.ofFullCopy(END_STONE)
                                        .strength(3.0F, 3.0F)
                                        .requiresCorrectToolForDrops();
    }

    public static final DeferredBlock<Block> SHIMMERSTONE_ORE = register(blockWithItem(
        "shimmerstone_ore", () -> new ShimmerstoneOreBlock(UniformInt.of(2, 4), ore().randomTicks()),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> DEEPSLATE_SHIMMERSTONE_ORE = register(blockWithItem(
        "deepslate_shimmerstone_ore", () -> new ShimmerstoneOreBlock(UniformInt.of(2, 4), deepslateOre().randomTicks()),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> SHIMMERSTONE_BLOCK = register(blockWithItem(
        "shimmerstone_block", () -> new ShimmerstoneBlock(
            settings(MapColor.COLOR_YELLOW, SoundType.GLASS, 2.0F).lightLevel((state) -> 15)), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> AZURE_CRYSTAL = register(blockWithItem(
        "azure_crystal", () -> new AzureCrystalBlock(
            UniformInt.of(4, 7), deepslateOre().randomTicks()
                                               .noOcclusion()
                                               .lightLevel(state -> state.getValue(AzureCrystalBlock.WARDED) ? 5 : 0)
        ), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> AZURE_OUTCROP = register(block(
        "azure_outcrop",
        () -> new AzuriteOutcropBlock(gemstone(MapColor.COLOR_BLUE, PastelBlockSoundGroups.ONYX_CLUSTER, 5))
    ));
    public static final DeferredBlock<Block> BUDDING_AZURITE = register(block(
        "budding_azurite", () -> new BuddingAzuriteBlock(
            gemstone(MapColor.COLOR_BLUE, PastelBlockSoundGroups.ONYX_CLUSTER, 5).randomTicks())
    ));
    public static final DeferredBlock<Block> AZURITE_BLOCK = register(blockWithItem(
        "azurite_block", () -> new PastelFacingBlock(Properties.ofFullCopy(LAPIS_BLOCK)
                                                               .mapColor(MapColor.COLOR_BLUE)), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> AZURITE_CLUSTER = register(blockWithItem(
        "azurite_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_BLUE, PastelBlockSoundGroups.SMALL_ONYX_BUD, 2),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), IS.of(Rarity.UNCOMMON), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> LARGE_AZURITE_BUD = register(blockWithItem(
        "large_azurite_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_BLUE, PastelBlockSoundGroups.LARGE_ONYX_BUD, 3),
            PastelClusterBlock.GrowthStage.LARGE
        ), IS.of(Rarity.UNCOMMON), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> SMALL_AZURITE_BUD = register(blockWithItem(
        "small_azurite_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_BLUE, PastelBlockSoundGroups.ONYX_CLUSTER, 5),
            PastelClusterBlock.GrowthStage.SMALL
        ), IS.of(Rarity.UNCOMMON), InkColors.BLUE
    ));

    public static final DeferredBlock<Block> VIRIDIAN_CRYSTAL = register(blockWithItem(
        "viridian_crystal", () -> new CloakedOreBlock(UniformInt.of(7, 11), blackslagOre().noOcclusion()),
        IS.of(Rarity.UNCOMMON), InkColors.GREEN
    ));
    public static final DeferredBlock<Block> MALACHITE_BLOCK = register(blockWithItem(
        "malachite_block", () -> new PastelFacingBlock(gemstoneBlock(MapColor.EMERALD, SoundType.CHAIN)),
        IS.of(Rarity.UNCOMMON), InkColors.GREEN
    ));
    public static final DeferredBlock<Block> MALACHITE_CLUSTER = register(blockWithItem(
        "malachite_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.EMERALD, SoundType.CHAIN, 9), PastelClusterBlock.GrowthStage.CLUSTER),
        IS.of(Rarity.UNCOMMON), InkColors.GREEN
    ));
    public static final DeferredBlock<Block> LARGE_MALACHITE_BUD = register(blockWithItem(
        "large_malachite_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.EMERALD, SoundType.CHAIN, 7), PastelClusterBlock.GrowthStage.LARGE),
        IS.of(Rarity.UNCOMMON), InkColors.GREEN
    ));
    public static final DeferredBlock<Block> SMALL_MALACHITE_BUD = register(blockWithItem(
        "small_malachite_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.EMERALD, SoundType.CHAIN, 5), PastelClusterBlock.GrowthStage.SMALL),
        IS.of(Rarity.UNCOMMON), InkColors.GREEN
    ));

    public static final DeferredBlock<Block> BLOODSTONE_BLOCK = register(blockWithItem(
        "bloodstone_block",
        () -> new PastelFacingBlock(gemstoneBlock(MapColor.COLOR_RED, PastelBlockSoundGroups.ONYX_CLUSTER)),
        IS.of(Rarity.UNCOMMON), InkColors.RED
    ));
    public static final DeferredBlock<Block> BLOODSTONE_CLUSTER = register(blockWithItem(
        "bloodstone_cluster", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_RED, PastelBlockSoundGroups.SMALL_ONYX_BUD, 6),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), IS.of(Rarity.UNCOMMON), InkColors.RED
    ));
    public static final DeferredBlock<Block> LARGE_BLOODSTONE_BUD = register(blockWithItem(
        "large_bloodstone_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_RED, PastelBlockSoundGroups.SMALL_ONYX_BUD, 4),
            PastelClusterBlock.GrowthStage.LARGE
        ), IS.of(Rarity.UNCOMMON), InkColors.RED
    ));
    public static final DeferredBlock<Block> SMALL_BLOODSTONE_BUD = register(blockWithItem(
        "small_bloodstone_bud", () -> new PastelClusterBlock(
            gemstone(MapColor.COLOR_RED, PastelBlockSoundGroups.ONYX_CLUSTER, 3), PastelClusterBlock.GrowthStage.SMALL),
        IS.of(Rarity.UNCOMMON), InkColors.RED
    ));

    public static final DeferredBlock<Block> STRATINE_ORE = register(blockWithItem(
        "stratine_ore", () -> new CloakedOreBlock(UniformInt.of(3, 5), netherrackOre()), block -> new FloatBlockItem(
            block, IS.of()
                     .fireResistant(), -0.01F
        ), InkColors.RED
    ));
    public static final DeferredBlock<Block> PALTAERIA_ORE = register(blockWithItem(
        "paltaeria_ore", () -> new CloakedOreBlock(UniformInt.of(2, 4), endstoneOre()),
        block -> new FloatBlockItem(block, IS.of(), 0.01F), InkColors.CYAN
    ));

    private static Properties gravityBlock(MapColor mapColor) {
        return settings(mapColor, SoundType.METAL, 4.0F, 6.0F).instrument(NoteBlockInstrument.BASEDRUM)
                                                              .requiresCorrectToolForDrops();
    }

    public static final DeferredBlock<Block> PALTAERIA_FLOATBLOCK = register(blockWithItem(
        "paltaeria_floatblock", () -> new FloatBlock(gravityBlock(MapColor.COLOR_LIGHT_BLUE), 0.2F),
        block -> new FloatBlockItem(
            block, IS.of()
                     .fireResistant(), 0.02F
        ), InkColors.RED
    ));
    public static final DeferredBlock<Block> STRATINE_FLOATBLOCK = register(blockWithItem(
        "stratine_floatblock", () -> new FloatBlock(gravityBlock(MapColor.NETHER), -0.2F),
        block -> new FloatBlockItem(block, IS.of(), -0.02F), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> HOVERBLOCK = register(blockWithItem(
        "hoverblock", () -> new FloatBlock(gravityBlock(MapColor.DIAMOND), 0.0F),
        block -> new FloatBlockItem(block, IS.of(), 0F) {
            @Override
            public double applyGravity(ItemStack stack, Level world, Entity entity) {
                return 0;
            }

            @Override
            public void applyGravity(ItemStack stack, Level world, ItemEntity itemEntity) {
                itemEntity.setNoGravity(true);
            }
        }, InkColors.GREEN
    ));

    public static final DeferredBlock<Block> BLACKSLAG_COAL_ORE = register(blockWithItem(
        "blackslag_coal_ore", () -> new DropExperienceBlock(UniformInt.of(0, 2), blackslagOre()), InkColors.BLACK));
    public static final DeferredBlock<Block> BLACKSLAG_COPPER_ORE = register(blockWithItem(
        "blackslag_copper_ore", () -> new DropExperienceBlock(ConstantInt.of(0), blackslagOre()), InkColors.BLACK));
    public static final DeferredBlock<Block> BLACKSLAG_IRON_ORE = register(blockWithItem(
        "blackslag_iron_ore", () -> new DropExperienceBlock(ConstantInt.of(0), blackslagOre()), InkColors.BROWN));
    public static final DeferredBlock<Block> BLACKSLAG_GOLD_ORE = register(blockWithItem(
        "blackslag_gold_ore", () -> new DropExperienceBlock(ConstantInt.of(0), blackslagOre()), InkColors.YELLOW));
    public static final DeferredBlock<Block> BLACKSLAG_LAPIS_ORE = register(blockWithItem(
        "blackslag_lapis_ore", () -> new DropExperienceBlock(UniformInt.of(2, 5), blackslagOre()), InkColors.BLUE));
    public static final DeferredBlock<Block> BLACKSLAG_DIAMOND_ORE = register(blockWithItem(
        "blackslag_diamond_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), blackslagOre()),
        InkColors.LIGHT_BLUE
    ));
    public static final DeferredBlock<Block> BLACKSLAG_REDSTONE_ORE = register(blockWithItem(
        "blackslag_redstone_ore", () -> new RedStoneOreBlock(blackslagOre().randomTicks()
                                                                           .lightLevel(litBlockEmission(9))),
        InkColors.RED
    ));
    public static final DeferredBlock<Block> BLACKSLAG_EMERALD_ORE = register(blockWithItem(
        "blackslag_emerald_ore", () -> new DropExperienceBlock(UniformInt.of(3, 7), blackslagOre()), InkColors.LIME));

    // FUNCTIONAL BLOCKS
    public static final DeferredBlock<Block> HEARTBOUND_CHEST = register(blockWithItem(
        "heartbound_chest", () -> new HeartboundChestBlock(settings(
            MapColor.TERRACOTTA_WHITE, SoundType.STONE, -1.0F, 3600000.0F).requiresCorrectToolForDrops()
                                                                          .noOcclusion()), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> COMPACTING_CHEST = register(blockWithItem(
        "compacting_chest", () -> new CompactingChestBlock(settings(
            MapColor.TERRACOTTA_WHITE, SoundType.STONE, 4.0F, 4.0F).requiresCorrectToolForDrops()
                                                                   .noOcclusion()), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> FABRICATION_CHEST = register(blockWithItem(
        "fabrication_chest", () -> new FabricationChestBlock(settings(
            MapColor.COLOR_ORANGE, SoundType.STONE, 4.0F, 4.0F).requiresCorrectToolForDrops()
                                                               .noOcclusion()), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> BLACK_HOLE_CHEST = register(blockWithItem(
        "black_hole_chest", () -> new BlackHoleChestBlock(settings(
            MapColor.COLOR_BLACK, SoundType.STONE, 4.0F, 4.0F).requiresCorrectToolForDrops()
                                                              .noOcclusion()), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> PARTICLE_SPAWNER = register(blockWithItem(
        "particle_spawner", () -> new ParticleSpawnerBlock(settings(
            MapColor.TERRACOTTA_WHITE, SoundType.AMETHYST, 5.0F, 6.0F).requiresCorrectToolForDrops()
                                                                      .noOcclusion()), InkColors.PINK
    ));
    public static final DeferredBlock<Block> CREATIVE_PARTICLE_SPAWNER = register(blockWithItem(
        "creative_particle_spawner", () -> new CreativeParticleSpawnerBlock(Properties.ofFullCopy(
                                                                                          PastelBlocks.PARTICLE_SPAWNER.get())
                                                                                      .strength(-1.0F, 3600000.8F)
                                                                                      .noLootTable()),
        block -> new BlockItem(block, IS.of(Rarity.EPIC)), InkColors.PINK
    ));
    public static final DeferredBlock<Block> BEDROCK_ANVIL = register(blockWithItem(
        "bedrock_anvil", () -> new BedrockAnvilBlock(Properties.ofFullCopy(ANVIL)
                                                               .requiresCorrectToolForDrops()
                                                               .strength(8.0F, 8.0F)
                                                               .sound(SoundType.METAL)), InkColors.BLACK
    ));

    // SOLID LIQUID CRYSTAL
    public static final DeferredBlock<Block> FROSTBITE_CRYSTAL = register(blockWithItem(
        "frostbite_crystal", () -> new Block(Properties.ofFullCopy(GLOWSTONE)
                                                       .mapColor(MapColor.CLAY)), InkColors.LIGHT_BLUE
    ));
    public static final DeferredBlock<Block> BLAZING_CRYSTAL = register(blockWithItem(
        "blazing_crystal", () -> new Block(Properties.ofFullCopy(GLOWSTONE)
                                                     .mapColor(MapColor.COLOR_ORANGE)), IS.of()
                                                                                          .fireResistant(),
        InkColors.ORANGE
    ));

    public static final DeferredBlock<Block> QUITOXIC_REEDS = register(blockWithItem(
        "quitoxic_reeds", () -> new QuitoxicReedsBlock(settings(MapColor.NONE, SoundType.GRASS, 0.0F).noCollission()
                                                                                                     .offsetType(
                                                                                                         OffsetType.XYZ)
                                                                                                     .randomTicks()
                                                                                                     .lightLevel(
                                                                                                         state -> state.getValue(
                                                                                                                           QuitoxicReedsBlock.LOGGED)
                                                                                                                       .getLuminance())),
        InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> MERMAIDS_BRUSH = register(block(
        "mermaids_brush", () -> new MermaidsBrushBlock(settings(MapColor.NONE, SoundType.WET_GRASS, 0.0F).noCollission()
                                                                                                         .randomTicks()
                                                                                                         .lightLevel(
                                                                                                             state -> state.getValue(
                                                                                                                               MermaidsBrushBlock.LOGGED)
                                                                                                                           .getLuminance()))
    ));
    public static final DeferredBlock<Block> RADIATING_ENDER = register(blockWithItem(
        "radiating_ender", () -> new RadiatingEnderBlock(Properties.ofFullCopy(EMERALD_BLOCK)
                                                                   .mapColor(MapColor.COLOR_PURPLE)), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> AMARANTH = register(block(
        "amaranth", () -> new AmaranthCropBlock(settings(MapColor.NONE, SoundType.CROP, 0.0F).noCollission()
                                                                                             .randomTicks())
    ));

    public static final DeferredBlock<Block> MEMORY = register(blockWithItem(
        "memory", () -> new MemoryBlock(settings(MapColor.NONE, SoundType.AMETHYST, 0.0F).isViewBlocking(
                                                                                             PastelBlocks::never)
                                                                                         .noOcclusion()
                                                                                         .randomTicks()),
        block -> new MemoryItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> CRACKED_END_PORTAL_FRAME = register(blockWithItem(
        "cracked_end_portal_frame", () -> new CrackedEndPortalFrameBlock(settings(
            MapColor.ICE, SoundType.GLASS, -1.0F, 3600000.0F).instrument(NoteBlockInstrument.BASEDRUM)
                                                             .lightLevel((state) -> 1)), IS.of()
                                                                                           .fireResistant(),
        InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> LAVA_SPONGE = register(blockWithItem(
        "lava_sponge", () -> new LavaSpongeBlock(Properties.ofFullCopy(SPONGE)
                                                           .mapColor(MapColor.COLOR_ORANGE)), IS.of()
                                                                                                .fireResistant(),
        InkColors.ORANGE
    ));
    public static final DeferredBlock<Block> WET_LAVA_SPONGE = register(burnable(
        blockWithItem(
            "wet_lava_sponge", () -> new WetLavaSpongeBlock(Properties.ofFullCopy(WET_SPONGE)
                                                                      .mapColor(MapColor.COLOR_ORANGE)
                                                                      .lightLevel(s -> 9)
                                                                      .emissiveRendering(PastelBlocks::always)
                                                                      .hasPostProcess(PastelBlocks::always)),
            block -> new WetLavaSpongeItem(
                block, IS.of(1)
                         .fireResistant()
                         .craftRemainder(PastelBlocks.LAVA_SPONGE.get()
                                                                 .asItem())
            ), InkColors.ORANGE
        ), 12800
    ));

    public static final DeferredBlock<Block> BLOCK_LIGHT_DETECTOR = register(blockWithItem(
        "block_light_detector", () -> new BlockLightDetectorBlock(Properties.ofFullCopy(DAYLIGHT_DETECTOR)),
        InkColors.RED
    ));
    public static final DeferredBlock<Block> WEATHER_DETECTOR = register(blockWithItem(
        "weather_detector", () -> new WeatherDetectorBlock(Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED));
    public static final DeferredBlock<Block> ITEM_DETECTOR = register(blockWithItem(
        "item_detector", () -> new ItemDetectorBlock(Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED));
    public static final DeferredBlock<Block> PLAYER_DETECTOR = register(blockWithItem(
        "player_detector", () -> new PlayerDetectorBlock(Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED));
    public static final DeferredBlock<Block> CREATURE_DETECTOR = register(blockWithItem(
        "creature_detector", () -> new EntityDetectorBlock(Properties.ofFullCopy(DAYLIGHT_DETECTOR)), InkColors.RED));
    public static final DeferredBlock<Block> REDSTONE_TIMER = register(
        blockWithItem("redstone_timer", () -> new RedstoneTimerBlock(Properties.ofFullCopy(REPEATER)), InkColors.RED));
    public static final DeferredBlock<Block> REDSTONE_CALCULATOR = register(blockWithItem(
        "redstone_calculator", () -> new RedstoneCalculatorBlock(Properties.ofFullCopy(REPEATER)), InkColors.RED));
    public static final DeferredBlock<Block> REDSTONE_TRANSCEIVER = register(blockWithItem(
        "redstone_transceiver", () -> new RedstoneTransceiverBlock(Properties.ofFullCopy(REPEATER)), InkColors.RED));
    public static final DeferredBlock<Block> BLOCK_PLACER = register(blockWithItem(
        "block_placer", () -> new BlockPlacerBlock(BlockBehaviour.Properties.ofFullCopy(DISPENSER)), InkColors.CYAN));
    public static final DeferredBlock<Block> BLOCK_DETECTOR = register(blockWithItem(
        "block_detector", () -> new BlockDetectorBlock(BlockBehaviour.Properties.ofFullCopy(DISPENSER)),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BLOCK_BREAKER = register(blockWithItem(
        "block_breaker", () -> new BlockBreakerBlock(BlockBehaviour.Properties.ofFullCopy(DISPENSER)), InkColors.CYAN));
    public static final DeferredBlock<Block> ENDER_DROPPER = register(blockWithItem(
        "ender_dropper", () -> new EnderDropperBlock(Properties.ofFullCopy(DROPPER)
                                                               .mapColor(MapColor.COLOR_GRAY)
                                                               .requiresCorrectToolForDrops()
                                                               .strength(15F, 60.0F)), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> ENDER_HOPPER = register(blockWithItem(
        "ender_hopper", () -> new EnderHopperBlock(Properties.ofFullCopy(HOPPER)
                                                             .mapColor(MapColor.COLOR_GRAY)
                                                             .requiresCorrectToolForDrops()
                                                             .strength(15F, 60.0F)), InkColors.PURPLE
    ));

    public static final DeferredBlock<Block> STUCK_STORM_STONE = register(block(
        "stuck_storm_stone", () -> new StuckStormStoneBlock(settings(
            MapColor.NONE, SoundType.SMALL_AMETHYST_BUD, 0.0F).noCollission()
                                                              .noOcclusion()
                                                              .isSuffocating(PastelBlocks::never)
                                                              .noTerrainParticles()
                                                              .isViewBlocking(PastelBlocks::never)
                                                              .replaceable())
    ));
    public static final DeferredBlock<Block> IMBRIFER_PORTAL = register(block(
        "fissure", () -> new DeeperDownPortalBlock(settings(
            MapColor.COLOR_BLACK, SoundType.EMPTY, -1.0F, 3600000.0F).pushReaction(PushReaction.BLOCK)
                                                                     .lightLevel(state -> 8)
                                                                     .noLootTable())
    ));

    private static Properties upgrade() {
        return BlockBehaviour.Properties.ofFullCopy(PastelBlocks.POLISHED_BASALT.get())
                                        .forceSolidOn();
    }

    public static final DeferredBlock<Block> UPGRADE_SPEED = register(blockWithItem(
        "upgrade_speed", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 1, InkColors.MAGENTA_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16), "upgrade_speed"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_SPEED2 = register(blockWithItem(
        "upgrade_speed2", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 2, InkColors.MAGENTA_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_speed2"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_SPEED3 = register(blockWithItem(
        "upgrade_speed3", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.SPEED, 8, InkColors.MAGENTA_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16, Rarity.RARE), "upgrade_speed3"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_EFFICIENCY = register(blockWithItem(
        "upgrade_efficiency",
        () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EFFICIENCY, 1, InkColors.YELLOW_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_efficiency"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_EFFICIENCY2 = register(blockWithItem(
        "upgrade_efficiency2",
        () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EFFICIENCY, 4, InkColors.YELLOW_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16, Rarity.RARE), "upgrade_efficiency2"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_YIELD = register(blockWithItem(
        "upgrade_yield", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.YIELD, 1, InkColors.CYAN_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_yield"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_YIELD2 = register(blockWithItem(
        "upgrade_yield2", () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.YIELD, 4, InkColors.CYAN_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16, Rarity.RARE), "upgrade_yield2"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_EXPERIENCE = register(blockWithItem(
        "upgrade_experience",
        () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EXPERIENCE, 1, InkColors.PURPLE_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16), "upgrade_experience"), InkColors.LIGHT_GRAY
    ));
    public static final DeferredBlock<Block> UPGRADE_EXPERIENCE2 = register(blockWithItem(
        "upgrade_experience2",
        () -> new UpgradeBlock(upgrade(), Upgradeable.UpgradeType.EXPERIENCE, 4, InkColors.PURPLE_COLOR),
        block -> new UpgradeBlockItem(block, IS.of(16, Rarity.UNCOMMON), "upgrade_experience2"), InkColors.LIGHT_GRAY
    ));

    public static final DeferredBlock<Block> REDSTONE_SAND = register(blockWithItem(
        "redstone_sand", () -> new RedstoneGravityBlock(Properties.ofFullCopy(SAND)
                                                                  .mapColor(MapColor.FIRE)), InkColors.RED
    ));
    public static final DeferredBlock<Block> ENDER_GLASS = register(blockWithItem(
        "ender_glass", () -> new EnderGlassBlock(Properties.ofFullCopy(GLASS)
                                                           .mapColor(MapColor.COLOR_PURPLE)
                                                           .noOcclusion()
                                                           .isRedstoneConductor(PastelBlocks::never)
                                                           .isValidSpawn((state, world, pos, entityType) ->
                                                                             state.getValue(
                                                                                 EnderGlassBlock.TRANSPARENCY_STATE) ==
                                                                             EnderGlassBlock.TransparencyState.SOLID)
                                                           .isSuffocating((state, world, pos) -> state.getValue(
                                                               EnderGlassBlock.TRANSPARENCY_STATE) ==
                                                                                                 EnderGlassBlock.TransparencyState.SOLID)
                                                           .isViewBlocking((state, world, pos) -> state.getValue(
                                                               EnderGlassBlock.TRANSPARENCY_STATE) ==
                                                                                                  EnderGlassBlock.TransparencyState.SOLID)),
        InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> CLOVER = register(blockWithItem(
        "clover", () -> new CloverBlock(Properties.ofFullCopy(SHORT_GRASS)
                                                  .offsetType(OffsetType.XZ)), IS.of(), InkColors.LIME
    ));
    public static final DeferredBlock<Block> FOUR_LEAF_CLOVER = register(blockWithItem(
        "four_leaf_clover", () -> new CloverBlock(Properties.ofFullCopy(SHORT_GRASS)
                                                            .offsetType(OffsetType.XZ)),
        block -> new FourLeafCloverItem(block, IS.of()), InkColors.LIME
    ));

    private static final UniformInt gemOreExperienceProvider = UniformInt.of(1, 4);
    public static final DeferredBlock<Block> TOPAZ_ORE = register(blockWithItem(
        "topaz_ore", () -> new GemstoneOreBlock(PastelBlocks.gemOreExperienceProvider, ore(), PastelGemstoneColor.CYAN),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> AMETHYST_ORE = register(blockWithItem(
        "amethyst_ore",
        () -> new GemstoneOreBlock(PastelBlocks.gemOreExperienceProvider, ore(), PastelGemstoneColor.MAGENTA),
        InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> CITRINE_ORE = register(blockWithItem(
        "citrine_ore",
        () -> new GemstoneOreBlock(PastelBlocks.gemOreExperienceProvider, ore(), PastelGemstoneColor.YELLOW),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> ONYX_ORE = register(blockWithItem(
        "onyx_ore", () -> new GemstoneOreBlock(PastelBlocks.gemOreExperienceProvider, ore(), PastelGemstoneColor.BLACK),
        InkColors.BLACK
    ));
    public static final DeferredBlock<Block> MOONSTONE_ORE = register(blockWithItem(
        "moonstone_ore",
        () -> new GemstoneOreBlock(PastelBlocks.gemOreExperienceProvider, ore(), PastelGemstoneColor.WHITE),
        InkColors.WHITE
    ));

    public static final DeferredBlock<Block> FROSTED_DEEPSLATE = register(
        blockWithItem("frosted_deepslate", () -> new Block(Properties.ofFullCopy(Blocks.DEEPSLATE)), InkColors.BLUE));
    public static final DeferredBlock<Block> SNOWGRAVE = register(
        block("snowgrave", () -> new SnowgraveBlock(Properties.ofFullCopy(Blocks.ICE))));

    public static final DeferredBlock<Block> DEEPSLATE_TOPAZ_ORE = register(blockWithItem(
        "deepslate_topaz_ore",
        () -> new GemstoneOreBlock(PastelBlocks.gemOreExperienceProvider, deepslateOre(), PastelGemstoneColor.CYAN),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> DEEPSLATE_AMETHYST_ORE = register(
        blockWithItem(
            "deepslate_amethyst_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, deepslateOre(),
                PastelGemstoneColor.MAGENTA
            ), InkColors.MAGENTA
        ));
    public static final DeferredBlock<Block> DEEPSLATE_CITRINE_ORE = register(
        blockWithItem(
            "deepslate_citrine_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, deepslateOre(),
                PastelGemstoneColor.YELLOW
            ), InkColors.YELLOW
        ));
    public static final DeferredBlock<Block> DEEPSLATE_ONYX_ORE = register(
        blockWithItem(
            "deepslate_onyx_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, deepslateOre(),
                PastelGemstoneColor.BLACK
            ), InkColors.BLACK
        ));
    public static final DeferredBlock<Block> DEEPSLATE_MOONSTONE_ORE = register(
        blockWithItem(
            "deepslate_moonstone_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, deepslateOre(),
                PastelGemstoneColor.WHITE
            ), InkColors.WHITE
        ));

    public static final DeferredBlock<Block> BLACKSLAG_TOPAZ_ORE = register(blockWithItem(
        "blackslag_topaz_ore",
        () -> new GemstoneOreBlock(PastelBlocks.gemOreExperienceProvider, blackslagOre(), PastelGemstoneColor.CYAN),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> BLACKSLAG_AMETHYST_ORE = register(
        blockWithItem(
            "blackslag_amethyst_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, blackslagOre(),
                PastelGemstoneColor.MAGENTA
            ), InkColors.MAGENTA
        ));
    public static final DeferredBlock<Block> BLACKSLAG_CITRINE_ORE = register(
        blockWithItem(
            "blackslag_citrine_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, blackslagOre(),
                PastelGemstoneColor.YELLOW
            ), InkColors.YELLOW
        ));
    public static final DeferredBlock<Block> BLACKSLAG_ONYX_ORE = register(
        blockWithItem(
            "blackslag_onyx_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, blackslagOre(),
                PastelGemstoneColor.BLACK
            ), InkColors.BLACK
        ));
    public static final DeferredBlock<Block> BLACKSLAG_MOONSTONE_ORE = register(
        blockWithItem(
            "blackslag_moonstone_ore", () -> new GemstoneOreBlock(
                PastelBlocks.gemOreExperienceProvider, blackslagOre(),
                PastelGemstoneColor.WHITE
            ), InkColors.WHITE
        ));

    private static Properties polishedGemBlock(MapColor mapColor, SoundType soundGroup) {
        return settings(mapColor, soundGroup, 5.0F, 6.0F);
    }

    public static final DeferredBlock<Block> POLISHED_TOPAZ_BLOCK = register(blockWithItem(
        "polished_topaz_block",
        () -> new Block(polishedGemBlock(MapColor.COLOR_CYAN, PastelBlockSoundGroups.TOPAZ_BLOCK)), InkColors.CYAN
    ));
    public static final DeferredBlock<Block> POLISHED_AMETHYST_BLOCK = register(blockWithItem(
        "polished_amethyst_block", () -> new Block(polishedGemBlock(MapColor.COLOR_MAGENTA, SoundType.AMETHYST)),
        InkColors.MAGENTA
    ));
    public static final DeferredBlock<Block> POLISHED_CITRINE_BLOCK = register(blockWithItem(
        "polished_citrine_block",
        () -> new Block(polishedGemBlock(MapColor.COLOR_YELLOW, PastelBlockSoundGroups.CITRINE_BLOCK)), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> POLISHED_ONYX_BLOCK = register(blockWithItem(
        "polished_onyx_block",
        () -> new Block(polishedGemBlock(MapColor.COLOR_BLACK, PastelBlockSoundGroups.ONYX_BLOCK)), InkColors.BLACK
    ));
    public static final DeferredBlock<Block> POLISHED_MOONSTONE_BLOCK = register(blockWithItem(
        "polished_moonstone_block",
        () -> new Block(polishedGemBlock(MapColor.SNOW, PastelBlockSoundGroups.MOONSTONE_BLOCK)), InkColors.WHITE
    ));

    private static BlockBehaviour.Properties deferredCopyWithMapColor(DeferredBlock<Block> baseBlock, MapColor color) {
        return BlockBehaviour.Properties.ofFullCopy(baseBlock.get())
                                        .mapColor(color);
    }

    private static BlockBehaviour.Properties copyWithMapColor(Block baseBlock, MapColor color) {
        return BlockBehaviour.Properties.ofFullCopy(baseBlock)
                                        .mapColor(color);
    }

    public static Properties pottedPlant() {
        return Properties.of()
                         .instabreak()
                         .noOcclusion()
                         .pushReaction(PushReaction.DESTROY);
    }

    public static final DeferredBlock<Block> AMARANTH_BUSHEL = register(
        blockWithItem(
            "amaranth_bushel", () -> new AmaranthBushelBlock(
                PastelMobEffects.NOURISHING, 8, settings(MapColor.NONE, SoundType.CROP, 0.0F).noCollission()),
            InkColors.RED
        ));
    public static final DeferredBlock<Block> POTTED_AMARANTH_BUSHEL = register(
        block("potted_amaranth_bushel", () -> new FlowerPotBlock(PastelBlocks.AMARANTH_BUSHEL.get(), pottedPlant())));

    public static final DeferredBlock<Block> RESONANT_LILY = register(blockWithItem(
        "resonant_lily", () -> new ResonantLilyBlock(
            MobEffects.REGENERATION, 5, BlockBehaviour.Properties.ofFullCopy(POPPY)
                                                                 .mapColor(MapColor.SNOW)
        ), InkColors.GREEN
    ));
    public static final DeferredBlock<Block> POTTED_RESONANT_LILY = register(
        block("potted_resonant_lily", () -> new FlowerPotBlock(PastelBlocks.RESONANT_LILY.get(), pottedPlant())));

    public static final DeferredBlock<Block> BLOOD_ORCHID = register(blockWithItem(
        "blood_orchid", () -> new BloodOrchidBlock(
            PastelMobEffects.FRENZY, 10, Properties.ofFullCopy(POPPY)
                                                   .offsetType(OffsetType.NONE)
                                                   .randomTicks()
        ), InkColors.RED
    ));
    public static final DeferredBlock<Block> POTTED_BLOOD_ORCHID = register(
        block("potted_blood_orchid", () -> new FlowerPotBlock(PastelBlocks.BLOOD_ORCHID.get(), pottedPlant())));

    public static DeferredBlock<Block> registerColoredSapling(String name, InkColor color, TreeGrower generator) {
        return register(blockWithItem(
            name, () -> new ColoredSaplingBlock(
                copyWithMapColor(
                    OAK_SAPLING, color.getDyeColor()
                                      .orElse(DyeColor.LIME)
                                      .getMapColor()
                ), color, generator
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_SAPLING = registerColoredSapling(
        "black_sapling", InkColors.BLACK, PastelSaplingGenerators.BLACK_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> BLUE_SAPLING = registerColoredSapling(
        "blue_sapling", InkColors.BLUE, PastelSaplingGenerators.BLUE_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> BROWN_SAPLING = registerColoredSapling(
        "brown_sapling", InkColors.BROWN, PastelSaplingGenerators.BROWN_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> CYAN_SAPLING = registerColoredSapling(
        "cyan_sapling", InkColors.CYAN, PastelSaplingGenerators.CYAN_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> GRAY_SAPLING = registerColoredSapling(
        "gray_sapling", InkColors.GRAY, PastelSaplingGenerators.GRAY_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> GREEN_SAPLING = registerColoredSapling(
        "green_sapling", InkColors.GREEN, PastelSaplingGenerators.GREEN_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> LIGHT_BLUE_SAPLING = registerColoredSapling(
        "light_blue_sapling", InkColors.LIGHT_BLUE, PastelSaplingGenerators.LIGHT_BLUE_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> LIGHT_GRAY_SAPLING = registerColoredSapling(
        "light_gray_sapling", InkColors.LIGHT_GRAY, PastelSaplingGenerators.LIGHT_GRAY_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> LIME_SAPLING = registerColoredSapling(
        "lime_sapling", InkColors.LIME, PastelSaplingGenerators.LIME_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> MAGENTA_SAPLING = registerColoredSapling(
        "magenta_sapling", InkColors.MAGENTA, PastelSaplingGenerators.MAGENTA_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> ORANGE_SAPLING = registerColoredSapling(
        "orange_sapling", InkColors.ORANGE, PastelSaplingGenerators.ORANGE_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> PINK_SAPLING = registerColoredSapling(
        "pink_sapling", InkColors.PINK, PastelSaplingGenerators.PINK_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> PURPLE_SAPLING = registerColoredSapling(
        "purple_sapling", InkColors.PURPLE, PastelSaplingGenerators.PURPLE_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> RED_SAPLING = registerColoredSapling(
        "red_sapling", InkColors.RED, PastelSaplingGenerators.RED_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> WHITE_SAPLING = registerColoredSapling(
        "white_sapling", InkColors.WHITE, PastelSaplingGenerators.WHITE_COLORED_SAPLING_GENERATOR);
    public static final DeferredBlock<Block> YELLOW_SAPLING = registerColoredSapling(
        "yellow_sapling", InkColors.YELLOW, PastelSaplingGenerators.YELLOW_COLORED_SAPLING_GENERATOR);

    public static DeferredBlock<Block> registerPottedColoredSapling(String name, DeferredBlock<Block> saplingBlock) {
        return register(block(
            name, () -> new PottedColoredSaplingBlock(
                saplingBlock.get(), pottedPlant(), ((ColoredSaplingBlock) saplingBlock.get()).getColor())
        ));
    }

    public static final DeferredBlock<Block> POTTED_BLACK_SAPLING = registerPottedColoredSapling(
        "potted_black_sapling", PastelBlocks.BLACK_SAPLING);
    public static final DeferredBlock<Block> POTTED_BLUE_SAPLING = registerPottedColoredSapling(
        "potted_blue_sapling", PastelBlocks.BLUE_SAPLING);
    public static final DeferredBlock<Block> POTTED_BROWN_SAPLING = registerPottedColoredSapling(
        "potted_brown_sapling", PastelBlocks.BROWN_SAPLING);
    public static final DeferredBlock<Block> POTTED_CYAN_SAPLING = registerPottedColoredSapling(
        "potted_cyan_sapling", PastelBlocks.CYAN_SAPLING);
    public static final DeferredBlock<Block> POTTED_GRAY_SAPLING = registerPottedColoredSapling(
        "potted_gray_sapling", PastelBlocks.GRAY_SAPLING);
    public static final DeferredBlock<Block> POTTED_GREEN_SAPLING = registerPottedColoredSapling(
        "potted_green_sapling", PastelBlocks.GREEN_SAPLING);
    public static final DeferredBlock<Block> POTTED_LIGHT_BLUE_SAPLING = registerPottedColoredSapling(
        "potted_light_blue_sapling", PastelBlocks.LIGHT_BLUE_SAPLING);
    public static final DeferredBlock<Block> POTTED_LIGHT_GRAY_SAPLING = registerPottedColoredSapling(
        "potted_light_gray_sapling", PastelBlocks.LIGHT_GRAY_SAPLING);
    public static final DeferredBlock<Block> POTTED_LIME_SAPLING = registerPottedColoredSapling(
        "potted_lime_sapling", PastelBlocks.LIME_SAPLING);
    public static final DeferredBlock<Block> POTTED_MAGENTA_SAPLING = registerPottedColoredSapling(
        "potted_magenta_sapling", PastelBlocks.MAGENTA_SAPLING);
    public static final DeferredBlock<Block> POTTED_ORANGE_SAPLING = registerPottedColoredSapling(
        "potted_orange_sapling", PastelBlocks.ORANGE_SAPLING);
    public static final DeferredBlock<Block> POTTED_PINK_SAPLING = registerPottedColoredSapling(
        "potted_pink_sapling", PastelBlocks.PINK_SAPLING);
    public static final DeferredBlock<Block> POTTED_PURPLE_SAPLING = registerPottedColoredSapling(
        "potted_purple_sapling", PastelBlocks.PURPLE_SAPLING);
    public static final DeferredBlock<Block> POTTED_RED_SAPLING = registerPottedColoredSapling(
        "potted_red_sapling", PastelBlocks.RED_SAPLING);
    public static final DeferredBlock<Block> POTTED_WHITE_SAPLING = registerPottedColoredSapling(
        "potted_white_sapling", PastelBlocks.WHITE_SAPLING);
    public static final DeferredBlock<Block> POTTED_YELLOW_SAPLING = registerPottedColoredSapling(
        "potted_yellow_sapling", PastelBlocks.YELLOW_SAPLING);

    public static DeferredBlock<Block> registerColoredStrippedLog(String name, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredStrippedLogBlock(
                copyWithMapColor(
                    STRIPPED_OAK_LOG, color.getDyeColor()
                                           .orElse(DyeColor.LIME)
                                           .getMapColor()
                ), color
            ), color
        ));
    }

    public static final DeferredBlock<Block> STRIPPED_BLACK_LOG = registerColoredStrippedLog(
        "stripped_black_log", InkColors.BLACK);
    public static final DeferredBlock<Block> STRIPPED_BLUE_LOG = registerColoredStrippedLog(
        "stripped_blue_log", InkColors.BLUE);
    public static final DeferredBlock<Block> STRIPPED_BROWN_LOG = registerColoredStrippedLog(
        "stripped_brown_log", InkColors.BROWN);
    public static final DeferredBlock<Block> STRIPPED_CYAN_LOG = registerColoredStrippedLog(
        "stripped_cyan_log", InkColors.CYAN);
    public static final DeferredBlock<Block> STRIPPED_GRAY_LOG = registerColoredStrippedLog(
        "stripped_gray_log", InkColors.GRAY);
    public static final DeferredBlock<Block> STRIPPED_GREEN_LOG = registerColoredStrippedLog(
        "stripped_green_log", InkColors.GREEN);
    public static final DeferredBlock<Block> STRIPPED_LIGHT_BLUE_LOG = registerColoredStrippedLog(
        "stripped_light_blue_log", InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> STRIPPED_LIGHT_GRAY_LOG = registerColoredStrippedLog(
        "stripped_light_gray_log", InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> STRIPPED_LIME_LOG = registerColoredStrippedLog(
        "stripped_lime_log", InkColors.LIME);
    public static final DeferredBlock<Block> STRIPPED_MAGENTA_LOG = registerColoredStrippedLog(
        "stripped_magenta_log", InkColors.MAGENTA);
    public static final DeferredBlock<Block> STRIPPED_ORANGE_LOG = registerColoredStrippedLog(
        "stripped_orange_log", InkColors.ORANGE);
    public static final DeferredBlock<Block> STRIPPED_PINK_LOG = registerColoredStrippedLog(
        "stripped_pink_log", InkColors.PINK);
    public static final DeferredBlock<Block> STRIPPED_PURPLE_LOG = registerColoredStrippedLog(
        "stripped_purple_log", InkColors.PURPLE);
    public static final DeferredBlock<Block> STRIPPED_RED_LOG = registerColoredStrippedLog(
        "stripped_red_log", InkColors.RED);
    public static final DeferredBlock<Block> STRIPPED_WHITE_LOG = registerColoredStrippedLog(
        "stripped_white_log", InkColors.WHITE);
    public static final DeferredBlock<Block> STRIPPED_YELLOW_LOG = registerColoredStrippedLog(
        "stripped_yellow_log", InkColors.YELLOW);

    public static DeferredBlock<Block> registerColoredLog(
        String name, InkColor color, DeferredBlock<Block> strippedForm) {
        return register(blockWithItem(
            name, () -> new ColoredLogBlock(
                copyWithMapColor(
                    OAK_LOG, color.getDyeColor()
                                  .orElse(DyeColor.LIME)
                                  .getMapColor()
                ), color, strippedForm.get()
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_LOG = registerColoredLog(
        "black_log", InkColors.BLACK, PastelBlocks.STRIPPED_BLACK_LOG);
    public static final DeferredBlock<Block> BLUE_LOG = registerColoredLog(
        "blue_log", InkColors.BLUE, PastelBlocks.STRIPPED_BLUE_LOG);
    public static final DeferredBlock<Block> BROWN_LOG = registerColoredLog(
        "brown_log", InkColors.BROWN, PastelBlocks.STRIPPED_BROWN_LOG);
    public static final DeferredBlock<Block> CYAN_LOG = registerColoredLog(
        "cyan_log", InkColors.CYAN, PastelBlocks.STRIPPED_CYAN_LOG);
    public static final DeferredBlock<Block> GRAY_LOG = registerColoredLog(
        "gray_log", InkColors.GRAY, PastelBlocks.STRIPPED_GRAY_LOG);
    public static final DeferredBlock<Block> GREEN_LOG = registerColoredLog(
        "green_log", InkColors.GREEN, PastelBlocks.STRIPPED_GREEN_LOG);
    public static final DeferredBlock<Block> LIGHT_BLUE_LOG = registerColoredLog(
        "light_blue_log", InkColors.LIGHT_BLUE, PastelBlocks.STRIPPED_LIGHT_BLUE_LOG);
    public static final DeferredBlock<Block> LIGHT_GRAY_LOG = registerColoredLog(
        "light_gray_log", InkColors.LIGHT_GRAY, PastelBlocks.STRIPPED_LIGHT_GRAY_LOG);
    public static final DeferredBlock<Block> LIME_LOG = registerColoredLog(
        "lime_log", InkColors.LIME, PastelBlocks.STRIPPED_LIME_LOG);
    public static final DeferredBlock<Block> MAGENTA_LOG = registerColoredLog(
        "magenta_log", InkColors.MAGENTA, PastelBlocks.STRIPPED_MAGENTA_LOG);
    public static final DeferredBlock<Block> ORANGE_LOG = registerColoredLog(
        "orange_log", InkColors.ORANGE, PastelBlocks.STRIPPED_ORANGE_LOG);
    public static final DeferredBlock<Block> PINK_LOG = registerColoredLog(
        "pink_log", InkColors.PINK, PastelBlocks.STRIPPED_PINK_LOG);
    public static final DeferredBlock<Block> PURPLE_LOG = registerColoredLog(
        "purple_log", InkColors.PURPLE, PastelBlocks.STRIPPED_PURPLE_LOG);
    public static final DeferredBlock<Block> RED_LOG = registerColoredLog(
        "red_log", InkColors.RED, PastelBlocks.STRIPPED_RED_LOG);
    public static final DeferredBlock<Block> WHITE_LOG = registerColoredLog(
        "white_log", InkColors.WHITE, PastelBlocks.STRIPPED_WHITE_LOG);
    public static final DeferredBlock<Block> YELLOW_LOG = registerColoredLog(
        "yellow_log", InkColors.YELLOW, PastelBlocks.STRIPPED_YELLOW_LOG);

    public static DeferredBlock<Block> registerColoredStrippedWood(
        String name, DeferredBlock<Block> logBlock, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredStrippedWoodBlock(
                copyWithMapColor(
                    STRIPPED_OAK_WOOD, logBlock.get()
                                               .defaultMapColor()
                ), color
            ), color
        ));
    }

    public static DeferredBlock<Block> registerColoredWood(String name, DeferredBlock<Block> logBlock, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredWoodBlock(
                copyWithMapColor(
                    OAK_WOOD, logBlock.get()
                                      .defaultMapColor()
                ), color
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_WOOD = registerColoredWood(
        "black_wood", PastelBlocks.BLACK_LOG, InkColors.BLACK);
    public static final DeferredBlock<Block> BLUE_WOOD = registerColoredWood(
        "blue_wood", PastelBlocks.BLUE_LOG, InkColors.BLUE);
    public static final DeferredBlock<Block> BROWN_WOOD = registerColoredWood(
        "brown_wood", PastelBlocks.BROWN_LOG, InkColors.BROWN);
    public static final DeferredBlock<Block> CYAN_WOOD = registerColoredWood(
        "cyan_wood", PastelBlocks.CYAN_LOG, InkColors.CYAN);
    public static final DeferredBlock<Block> GRAY_WOOD = registerColoredWood(
        "gray_wood", PastelBlocks.GRAY_LOG, InkColors.GRAY);
    public static final DeferredBlock<Block> GREEN_WOOD = registerColoredWood(
        "green_wood", PastelBlocks.GREEN_LOG, InkColors.GREEN);
    public static final DeferredBlock<Block> LIGHT_BLUE_WOOD = registerColoredWood(
        "light_blue_wood", PastelBlocks.LIGHT_BLUE_LOG, InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_GRAY_WOOD = registerColoredWood(
        "light_gray_wood", PastelBlocks.LIGHT_GRAY_LOG, InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIME_WOOD = registerColoredWood(
        "lime_wood", PastelBlocks.LIME_LOG, InkColors.LIME);
    public static final DeferredBlock<Block> MAGENTA_WOOD = registerColoredWood(
        "magenta_wood", PastelBlocks.MAGENTA_LOG, InkColors.MAGENTA);
    public static final DeferredBlock<Block> ORANGE_WOOD = registerColoredWood(
        "orange_wood", PastelBlocks.ORANGE_LOG, InkColors.ORANGE);
    public static final DeferredBlock<Block> PINK_WOOD = registerColoredWood(
        "pink_wood", PastelBlocks.PINK_LOG, InkColors.PINK);
    public static final DeferredBlock<Block> PURPLE_WOOD = registerColoredWood(
        "purple_wood", PastelBlocks.PURPLE_LOG, InkColors.PURPLE);
    public static final DeferredBlock<Block> RED_WOOD = registerColoredWood(
        "red_wood", PastelBlocks.RED_LOG, InkColors.RED);
    public static final DeferredBlock<Block> WHITE_WOOD = registerColoredWood(
        "white_wood", PastelBlocks.WHITE_LOG, InkColors.WHITE);
    public static final DeferredBlock<Block> YELLOW_WOOD = registerColoredWood(
        "yellow_wood", PastelBlocks.YELLOW_LOG, InkColors.YELLOW);

    public static final DeferredBlock<Block> STRIPPED_BLACK_WOOD = registerColoredStrippedWood(
        "stripped_black_wood", PastelBlocks.STRIPPED_BLACK_LOG, InkColors.BLACK);
    public static final DeferredBlock<Block> STRIPPED_BLUE_WOOD = registerColoredStrippedWood(
        "stripped_blue_wood", PastelBlocks.STRIPPED_BLUE_LOG, InkColors.BLUE);
    public static final DeferredBlock<Block> STRIPPED_BROWN_WOOD = registerColoredStrippedWood(
        "stripped_brown_wood", PastelBlocks.STRIPPED_BROWN_LOG, InkColors.BROWN);
    public static final DeferredBlock<Block> STRIPPED_CYAN_WOOD = registerColoredStrippedWood(
        "stripped_cyan_wood", PastelBlocks.STRIPPED_CYAN_LOG, InkColors.CYAN);
    public static final DeferredBlock<Block> STRIPPED_GRAY_WOOD = registerColoredStrippedWood(
        "stripped_gray_wood", PastelBlocks.STRIPPED_GRAY_LOG, InkColors.GRAY);
    public static final DeferredBlock<Block> STRIPPED_GREEN_WOOD = registerColoredStrippedWood(
        "stripped_green_wood", PastelBlocks.STRIPPED_GREEN_LOG, InkColors.GREEN);
    public static final DeferredBlock<Block> STRIPPED_LIGHT_BLUE_WOOD = registerColoredStrippedWood(
        "stripped_light_blue_wood", PastelBlocks.STRIPPED_LIGHT_BLUE_LOG, InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> STRIPPED_LIGHT_GRAY_WOOD = registerColoredStrippedWood(
        "stripped_light_gray_wood", PastelBlocks.STRIPPED_LIGHT_GRAY_LOG, InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> STRIPPED_LIME_WOOD = registerColoredStrippedWood(
        "stripped_lime_wood", PastelBlocks.STRIPPED_LIME_LOG, InkColors.LIME);
    public static final DeferredBlock<Block> STRIPPED_MAGENTA_WOOD = registerColoredStrippedWood(
        "stripped_magenta_wood", PastelBlocks.STRIPPED_MAGENTA_LOG, InkColors.MAGENTA);
    public static final DeferredBlock<Block> STRIPPED_ORANGE_WOOD = registerColoredStrippedWood(
        "stripped_orange_wood", PastelBlocks.STRIPPED_ORANGE_LOG, InkColors.ORANGE);
    public static final DeferredBlock<Block> STRIPPED_PINK_WOOD = registerColoredStrippedWood(
        "stripped_pink_wood", PastelBlocks.STRIPPED_PINK_LOG, InkColors.PINK);
    public static final DeferredBlock<Block> STRIPPED_PURPLE_WOOD = registerColoredStrippedWood(
        "stripped_purple_wood", PastelBlocks.STRIPPED_PURPLE_LOG, InkColors.PURPLE);
    public static final DeferredBlock<Block> STRIPPED_RED_WOOD = registerColoredStrippedWood(
        "stripped_red_wood", PastelBlocks.STRIPPED_RED_LOG, InkColors.RED);
    public static final DeferredBlock<Block> STRIPPED_WHITE_WOOD = registerColoredStrippedWood(
        "stripped_white_wood", PastelBlocks.STRIPPED_WHITE_LOG, InkColors.WHITE);
    public static final DeferredBlock<Block> STRIPPED_YELLOW_WOOD = registerColoredStrippedWood(
        "stripped_yellow_wood", PastelBlocks.STRIPPED_YELLOW_LOG, InkColors.YELLOW);

    public static DeferredBlock<Block> registerColoredLeaves(String name, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredLeavesBlock(
                copyWithMapColor(
                    OAK_LEAVES, color.getDyeColor()
                                     .orElse(DyeColor.LIME)
                                     .getMapColor()
                ), color
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_LEAVES = registerColoredLeaves("black_leaves", InkColors.BLACK);
    public static final DeferredBlock<Block> BLUE_LEAVES = registerColoredLeaves("blue_leaves", InkColors.BLUE);
    public static final DeferredBlock<Block> BROWN_LEAVES = registerColoredLeaves("brown_leaves", InkColors.BROWN);
    public static final DeferredBlock<Block> CYAN_LEAVES = registerColoredLeaves("cyan_leaves", InkColors.CYAN);
    public static final DeferredBlock<Block> GRAY_LEAVES = registerColoredLeaves("gray_leaves", InkColors.GRAY);
    public static final DeferredBlock<Block> GREEN_LEAVES = registerColoredLeaves("green_leaves", InkColors.GREEN);
    public static final DeferredBlock<Block> LIGHT_BLUE_LEAVES = registerColoredLeaves(
        "light_blue_leaves", InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_GRAY_LEAVES = registerColoredLeaves(
        "light_gray_leaves", InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIME_LEAVES = registerColoredLeaves("lime_leaves", InkColors.LIME);
    public static final DeferredBlock<Block> MAGENTA_LEAVES = registerColoredLeaves(
        "magenta_leaves", InkColors.MAGENTA);
    public static final DeferredBlock<Block> ORANGE_LEAVES = registerColoredLeaves("orange_leaves", InkColors.ORANGE);
    public static final DeferredBlock<Block> PINK_LEAVES = registerColoredLeaves("pink_leaves", InkColors.PINK);
    public static final DeferredBlock<Block> PURPLE_LEAVES = registerColoredLeaves("purple_leaves", InkColors.PURPLE);
    public static final DeferredBlock<Block> RED_LEAVES = registerColoredLeaves("red_leaves", InkColors.RED);
    public static final DeferredBlock<Block> WHITE_LEAVES = registerColoredLeaves("white_leaves", InkColors.WHITE);
    public static final DeferredBlock<Block> YELLOW_LEAVES = registerColoredLeaves("yellow_leaves", InkColors.YELLOW);

    public static DeferredBlock<Block> registerGlowBlock(String name, InkColor color) {
        return register(blockWithItem(
            name, () -> new GlowBlock(
                settings(
                    color.getDyeColor()
                         .orElse(DyeColor.LIME)
                         .getMapColor(), SoundType.BASALT, 2.5F
                ).requiresCorrectToolForDrops()
                 .lightLevel(state -> 1)
                 .hasPostProcess(PastelBlocks::always)
                 .emissiveRendering(PastelBlocks::always), color
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_GLOWBLOCK = registerGlowBlock("black_glowblock", InkColors.BLACK);
    public static final DeferredBlock<Block> BLUE_GLOWBLOCK = registerGlowBlock("blue_glowblock", InkColors.BLUE);
    public static final DeferredBlock<Block> BROWN_GLOWBLOCK = registerGlowBlock("brown_glowblock", InkColors.BROWN);
    public static final DeferredBlock<Block> CYAN_GLOWBLOCK = registerGlowBlock("cyan_glowblock", InkColors.CYAN);
    public static final DeferredBlock<Block> GRAY_GLOWBLOCK = registerGlowBlock("gray_glowblock", InkColors.GRAY);
    public static final DeferredBlock<Block> GREEN_GLOWBLOCK = registerGlowBlock("green_glowblock", InkColors.GREEN);
    public static final DeferredBlock<Block> LIGHT_BLUE_GLOWBLOCK = registerGlowBlock(
        "light_blue_glowblock", InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_GRAY_GLOWBLOCK = registerGlowBlock(
        "light_gray_glowblock", InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIME_GLOWBLOCK = registerGlowBlock("lime_glowblock", InkColors.LIME);
    public static final DeferredBlock<Block> MAGENTA_GLOWBLOCK = registerGlowBlock(
        "magenta_glowblock", InkColors.MAGENTA);
    public static final DeferredBlock<Block> ORANGE_GLOWBLOCK = registerGlowBlock("orange_glowblock", InkColors.ORANGE);
    public static final DeferredBlock<Block> PINK_GLOWBLOCK = registerGlowBlock("pink_glowblock", InkColors.PINK);
    public static final DeferredBlock<Block> PURPLE_GLOWBLOCK = registerGlowBlock("purple_glowblock", InkColors.PURPLE);
    public static final DeferredBlock<Block> RED_GLOWBLOCK = registerGlowBlock("red_glowblock", InkColors.RED);
    public static final DeferredBlock<Block> WHITE_GLOWBLOCK = registerGlowBlock("white_glowblock", InkColors.WHITE);
    public static final DeferredBlock<Block> YELLOW_GLOWBLOCK = registerGlowBlock("yellow_glowblock", InkColors.YELLOW);

    public static DeferredBlock<Block> registerColoredLightBlock(String name, InkColor color) {
        return register(blockWithItem(
            name, () -> new ColoredLightBlock(
                Properties.ofFullCopy(REDSTONE_LAMP)
                          .mapColor(color.getDyeColor()
                                         .orElse(DyeColor.LIME)
                                         .getMapColor()), color
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_LAMP = registerColoredLightBlock("black_lamp", InkColors.BLACK);
    public static final DeferredBlock<Block> BLUE_LAMP = registerColoredLightBlock("blue_lamp", InkColors.BLUE);
    public static final DeferredBlock<Block> BROWN_LAMP = registerColoredLightBlock("brown_lamp", InkColors.BROWN);
    public static final DeferredBlock<Block> CYAN_LAMP = registerColoredLightBlock("cyan_lamp", InkColors.CYAN);
    public static final DeferredBlock<Block> GRAY_LAMP = registerColoredLightBlock("gray_lamp", InkColors.GRAY);
    public static final DeferredBlock<Block> GREEN_LAMP = registerColoredLightBlock("green_lamp", InkColors.GREEN);
    public static final DeferredBlock<Block> LIGHT_BLUE_LAMP = registerColoredLightBlock(
        "light_blue_lamp", InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_GRAY_LAMP = registerColoredLightBlock(
        "light_gray_lamp", InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIME_LAMP = registerColoredLightBlock("lime_lamp", InkColors.LIME);
    public static final DeferredBlock<Block> MAGENTA_LAMP = registerColoredLightBlock(
        "magenta_lamp", InkColors.MAGENTA);
    public static final DeferredBlock<Block> ORANGE_LAMP = registerColoredLightBlock("orange_lamp", InkColors.ORANGE);
    public static final DeferredBlock<Block> PINK_LAMP = registerColoredLightBlock("pink_lamp", InkColors.PINK);
    public static final DeferredBlock<Block> PURPLE_LAMP = registerColoredLightBlock("purple_lamp", InkColors.PURPLE);
    public static final DeferredBlock<Block> RED_LAMP = registerColoredLightBlock("red_lamp", InkColors.RED);
    public static final DeferredBlock<Block> WHITE_LAMP = registerColoredLightBlock("white_lamp", InkColors.WHITE);
    public static final DeferredBlock<Block> YELLOW_LAMP = registerColoredLightBlock("yellow_lamp", InkColors.YELLOW);

    public static DeferredBlock<Block> registerPigmentBlock(String name, InkColor color) {
        return register(blockWithItem(
            name, () -> new PigmentBlock(
                settings(
                    color.getDyeColor()
                         .orElse(DyeColor.LIME)
                         .getMapColor(), SoundType.WOOL, 1.0F
                ), color
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_BLOCK = registerPigmentBlock("black_block", InkColors.BLACK);
    public static final DeferredBlock<Block> BLUE_BLOCK = registerPigmentBlock("blue_block", InkColors.BLUE);
    public static final DeferredBlock<Block> BROWN_BLOCK = registerPigmentBlock("brown_block", InkColors.BROWN);
    public static final DeferredBlock<Block> CYAN_BLOCK = registerPigmentBlock("cyan_block", InkColors.CYAN);
    public static final DeferredBlock<Block> GRAY_BLOCK = registerPigmentBlock("gray_block", InkColors.GRAY);
    public static final DeferredBlock<Block> GREEN_BLOCK = registerPigmentBlock("green_block", InkColors.GREEN);
    public static final DeferredBlock<Block> LIGHT_BLUE_BLOCK = registerPigmentBlock(
        "light_blue_block", InkColors.LIGHT_BLUE);
    public static final DeferredBlock<Block> LIGHT_GRAY_BLOCK = registerPigmentBlock(
        "light_gray_block", InkColors.LIGHT_GRAY);
    public static final DeferredBlock<Block> LIME_BLOCK = registerPigmentBlock("lime_block", InkColors.LIME);
    public static final DeferredBlock<Block> MAGENTA_BLOCK = registerPigmentBlock("magenta_block", InkColors.MAGENTA);
    public static final DeferredBlock<Block> ORANGE_BLOCK = registerPigmentBlock("orange_block", InkColors.ORANGE);
    public static final DeferredBlock<Block> PINK_BLOCK = registerPigmentBlock("pink_block", InkColors.PINK);
    public static final DeferredBlock<Block> PURPLE_BLOCK = registerPigmentBlock("purple_block", InkColors.PURPLE);
    public static final DeferredBlock<Block> RED_BLOCK = registerPigmentBlock("red_block", InkColors.RED);
    public static final DeferredBlock<Block> WHITE_BLOCK = registerPigmentBlock("white_block", InkColors.WHITE);
    public static final DeferredBlock<Block> YELLOW_BLOCK = registerPigmentBlock("yellow_block", InkColors.YELLOW);

    public static DeferredBlock<Block> registerColoredSporeBlossomBlock(
        String name, InkColor color, ColoredFallingSporeBlossomParticleEffect falling,
        ColoredSporeBlossomAirParticleEffect air
    ) {
        return register(blockWithItem(
            name, () -> new ColoredSporeBlossomBlock(
                Properties.ofFullCopy(SPORE_BLOSSOM)
                          .mapColor(color.getDyeColor()
                                         .orElse(DyeColor.LIME)
                                         .getMapColor()), color, falling, air
            ), color
        ));
    }

    public static final DeferredBlock<Block> BLACK_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "black_spore_blossom", InkColors.BLACK, ColoredFallingSporeBlossomParticleEffect.BLACK,
        ColoredSporeBlossomAirParticleEffect.BLACK
    );
    public static final DeferredBlock<Block> BLUE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "blue_spore_blossom", InkColors.BLUE, ColoredFallingSporeBlossomParticleEffect.BLUE,
        ColoredSporeBlossomAirParticleEffect.BLUE
    );
    public static final DeferredBlock<Block> BROWN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "brown_spore_blossom", InkColors.BROWN, ColoredFallingSporeBlossomParticleEffect.BROWN,
        ColoredSporeBlossomAirParticleEffect.BROWN
    );
    public static final DeferredBlock<Block> CYAN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "cyan_spore_blossom", InkColors.CYAN, ColoredFallingSporeBlossomParticleEffect.CYAN,
        ColoredSporeBlossomAirParticleEffect.CYAN
    );
    public static final DeferredBlock<Block> GRAY_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "gray_spore_blossom", InkColors.GRAY, ColoredFallingSporeBlossomParticleEffect.GRAY,
        ColoredSporeBlossomAirParticleEffect.GRAY
    );
    public static final DeferredBlock<Block> GREEN_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "green_spore_blossom", InkColors.GREEN, ColoredFallingSporeBlossomParticleEffect.GREEN,
        ColoredSporeBlossomAirParticleEffect.GREEN
    );
    public static final DeferredBlock<Block> LIGHT_BLUE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "light_blue_spore_blossom", InkColors.LIGHT_BLUE, ColoredFallingSporeBlossomParticleEffect.LIGHT_BLUE,
        ColoredSporeBlossomAirParticleEffect.LIGHT_BLUE
    );
    public static final DeferredBlock<Block> LIGHT_GRAY_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "light_gray_spore_blossom", InkColors.LIGHT_GRAY, ColoredFallingSporeBlossomParticleEffect.LIGHT_GRAY,
        ColoredSporeBlossomAirParticleEffect.LIGHT_GRAY
    );
    public static final DeferredBlock<Block> LIME_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "lime_spore_blossom", InkColors.LIME, ColoredFallingSporeBlossomParticleEffect.LIME,
        ColoredSporeBlossomAirParticleEffect.LIME
    );
    public static final DeferredBlock<Block> MAGENTA_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "magenta_spore_blossom", InkColors.MAGENTA, ColoredFallingSporeBlossomParticleEffect.MAGENTA,
        ColoredSporeBlossomAirParticleEffect.MAGENTA
    );
    public static final DeferredBlock<Block> ORANGE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "orange_spore_blossom", InkColors.ORANGE, ColoredFallingSporeBlossomParticleEffect.ORANGE,
        ColoredSporeBlossomAirParticleEffect.ORANGE
    );
    public static final DeferredBlock<Block> PINK_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "pink_spore_blossom", InkColors.PINK, ColoredFallingSporeBlossomParticleEffect.PINK,
        ColoredSporeBlossomAirParticleEffect.PINK
    );
    public static final DeferredBlock<Block> PURPLE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "purple_spore_blossom", InkColors.PURPLE, ColoredFallingSporeBlossomParticleEffect.PURPLE,
        ColoredSporeBlossomAirParticleEffect.PURPLE
    );
    public static final DeferredBlock<Block> RED_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "red_spore_blossom", InkColors.RED, ColoredFallingSporeBlossomParticleEffect.RED,
        ColoredSporeBlossomAirParticleEffect.RED
    );
    public static final DeferredBlock<Block> WHITE_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "white_spore_blossom", InkColors.WHITE, ColoredFallingSporeBlossomParticleEffect.WHITE,
        ColoredSporeBlossomAirParticleEffect.WHITE
    );
    public static final DeferredBlock<Block> YELLOW_SPORE_BLOSSOM = registerColoredSporeBlossomBlock(
        "yellow_spore_blossom", InkColors.YELLOW, ColoredFallingSporeBlossomParticleEffect.YELLOW,
        ColoredSporeBlossomAirParticleEffect.YELLOW
    );

    public static DeferredBlock<Block> registerShimmerstoneLight(
        String name, SoundType soundGroup, Supplier<ResourceLocation> outerSupplier) {
        return register(blockWithItem(
            name, () -> new ShimmerstoneLightBlock(settings(MapColor.NONE, soundGroup, 1.0F).noOcclusion()
                                                                                            .requiresCorrectToolForDrops()
                                                                                            .lightLevel(state -> 15)
                                                                                            .pushReaction(
                                                                                                PushReaction.DESTROY)),
            InkColors.YELLOW
        ));
    }

    public static final DeferredBlock<Block> STONE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "stone_shimmerstone_light", SoundType.STONE, () -> PastelTextures.STONE_FLAT_LIGHT);
    public static final DeferredBlock<Block> BASALT_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "basalt_shimmerstone_light", SoundType.BASALT, () -> PastelTextures.BASALT_FLAT_LIGHT);
    public static final DeferredBlock<Block> CALCITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "calcite_shimmerstone_light", SoundType.CALCITE, () -> PastelTextures.CALCITE_FLAT_LIGHT);
    public static final DeferredBlock<Block> DEEPSLATE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "deepslate_shimmerstone_light", SoundType.DEEPSLATE, () -> PastelTextures.DEEPSLATE_FLAT_LIGHT);
    public static final DeferredBlock<Block> BLACKSLAG_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "blackslag_shimmerstone_light", SoundType.DEEPSLATE, () -> PastelTextures.BLACKSLAG_FLAT_LIGHT);
    public static final DeferredBlock<Block> GRANITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "granite_shimmerstone_light", SoundType.STONE, () -> ModelLocationUtils.getModelLocation(POLISHED_GRANITE));
    public static final DeferredBlock<Block> DIORITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "diorite_shimmerstone_light", SoundType.STONE, () -> ModelLocationUtils.getModelLocation(POLISHED_DIORITE));
    public static final DeferredBlock<Block> ANDESITE_SHIMMERSTONE_LIGHT = registerShimmerstoneLight(
        "andesite_shimmerstone_light", SoundType.STONE, () -> ModelLocationUtils.getModelLocation(POLISHED_ANDESITE));

    // CRYSTALLARIEUM
    private static Properties crystallarieumGrowable(Block baseBlock) {
        return BlockBehaviour.Properties.ofFullCopy(baseBlock)
                                        .strength(1.5F)
                                        .noOcclusion()
                                        .forceSolidOn()
                                        .requiresCorrectToolForDrops()
                                        .pushReaction(PushReaction.DESTROY);
    }

    public static final DeferredBlock<Block> SMALL_COAL_BUD = register(blockWithItem(
        "small_coal_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(COAL_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> LARGE_COAL_BUD = register(blockWithItem(
        "large_coal_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_COAL_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> COAL_CLUSTER = register(blockWithItem(
        "coal_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_COAL_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMALL_IRON_BUD = register(blockWithItem(
        "small_iron_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(IRON_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> LARGE_IRON_BUD = register(blockWithItem(
        "large_iron_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_IRON_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> IRON_CLUSTER = register(blockWithItem(
        "iron_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_IRON_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMALL_GOLD_BUD = register(blockWithItem(
        "small_gold_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(GOLD_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> LARGE_GOLD_BUD = register(blockWithItem(
        "large_gold_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_GOLD_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> GOLD_CLUSTER = register(blockWithItem(
        "gold_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_GOLD_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMALL_DIAMOND_BUD = register(blockWithItem(
        "small_diamond_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(DIAMOND_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> LARGE_DIAMOND_BUD = register(blockWithItem(
        "large_diamond_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_DIAMOND_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> DIAMOND_CLUSTER = register(blockWithItem(
        "diamond_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_DIAMOND_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> SMALL_EMERALD_BUD = register(blockWithItem(
        "small_emerald_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(EMERALD_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> LARGE_EMERALD_BUD = register(blockWithItem(
        "large_emerald_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_EMERALD_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> EMERALD_CLUSTER = register(blockWithItem(
        "emerald_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_EMERALD_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> SMALL_REDSTONE_BUD = register(blockWithItem(
        "small_redstone_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(REDSTONE_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.RED
    ));
    public static final DeferredBlock<Block> LARGE_REDSTONE_BUD = register(blockWithItem(
        "large_redstone_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_REDSTONE_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.RED
    ));
    public static final DeferredBlock<Block> REDSTONE_CLUSTER = register(blockWithItem(
        "redstone_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_REDSTONE_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.RED
    ));
    public static final DeferredBlock<Block> SMALL_LAPIS_BUD = register(blockWithItem(
        "small_lapis_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(LAPIS_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> LARGE_LAPIS_BUD = register(blockWithItem(
        "large_lapis_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_LAPIS_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> LAPIS_CLUSTER = register(blockWithItem(
        "lapis_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_LAPIS_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> SMALL_COPPER_BUD = register(blockWithItem(
        "small_copper_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(COPPER_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> LARGE_COPPER_BUD = register(blockWithItem(
        "large_copper_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_COPPER_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> COPPER_CLUSTER = register(blockWithItem(
        "copper_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_COPPER_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMALL_QUARTZ_BUD = register(blockWithItem(
        "small_quartz_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(QUARTZ_BLOCK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> LARGE_QUARTZ_BUD = register(blockWithItem(
        "large_quartz_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_QUARTZ_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> QUARTZ_CLUSTER = register(blockWithItem(
        "quartz_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_QUARTZ_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMALL_NETHERITE_SCRAP_BUD = register(blockWithItem(
        "small_netherite_scrap_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(ANCIENT_DEBRIS), PastelClusterBlock.GrowthStage.SMALL),
        IS.of()
          .fireResistant(), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> LARGE_NETHERITE_SCRAP_BUD = register(blockWithItem(
        "large_netherite_scrap_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_NETHERITE_SCRAP_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        IS.of()
          .fireResistant(), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> NETHERITE_SCRAP_CLUSTER = register(blockWithItem(
        "netherite_scrap_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_NETHERITE_SCRAP_BUD.get()),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), IS.of()
             .fireResistant(), InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMALL_ECHO_BUD = register(blockWithItem(
        "small_echo_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(SCULK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> LARGE_ECHO_BUD = register(blockWithItem(
        "large_echo_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_ECHO_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> ECHO_CLUSTER = register(blockWithItem(
        "echo_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_ECHO_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> SMALL_GLOWSTONE_BUD = register(blockWithItem(
        "small_glowstone_bud", () -> new PastelClusterBlock(
            crystallarieumGrowable(GLOWSTONE).lightLevel(state -> 4), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> LARGE_GLOWSTONE_BUD = register(blockWithItem(
        "large_glowstone_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_GLOWSTONE_BUD.get())
                      .lightLevel(state -> 8), PastelClusterBlock.GrowthStage.LARGE
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> GLOWSTONE_CLUSTER = register(blockWithItem(
        "glowstone_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_GLOWSTONE_BUD.get())
                      .lightLevel(state -> 14), PastelClusterBlock.GrowthStage.CLUSTER
        ), InkColors.YELLOW
    ));
    public static final DeferredBlock<Block> SMALL_PRISMARINE_BUD = register(blockWithItem(
        "small_prismarine_bud",
        () -> new PastelClusterBlock(crystallarieumGrowable(SCULK), PastelClusterBlock.GrowthStage.SMALL),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> LARGE_PRISMARINE_BUD = register(blockWithItem(
        "large_prismarine_bud", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_PRISMARINE_BUD.get()), PastelClusterBlock.GrowthStage.LARGE),
        InkColors.CYAN
    ));
    public static final DeferredBlock<Block> PRISMARINE_CLUSTER = register(blockWithItem(
        "prismarine_cluster", () -> new PastelClusterBlock(
            Properties.ofFullCopy(PastelBlocks.SMALL_PRISMARINE_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER),
        InkColors.CYAN
    ));

    public static final DeferredBlock<Block> PURE_COAL_BLOCK = register(burnable(
        blockWithItem("pure_coal_block", () -> new Block(Properties.ofFullCopy(COAL_BLOCK)), InkColors.BROWN), 32000));
    public static final DeferredBlock<Block> PURE_IRON_BLOCK = register(
        blockWithItem("pure_iron_block", () -> new Block(Properties.ofFullCopy(IRON_BLOCK)), InkColors.BROWN));
    public static final DeferredBlock<Block> PURE_GOLD_BLOCK = register(
        blockWithItem("pure_gold_block", () -> new Block(Properties.ofFullCopy(GOLD_BLOCK)), InkColors.BROWN));
    public static final DeferredBlock<Block> PURE_DIAMOND_BLOCK = register(
        blockWithItem("pure_diamond_block", () -> new Block(Properties.ofFullCopy(DIAMOND_BLOCK)), InkColors.CYAN));
    public static final DeferredBlock<Block> PURE_EMERALD_BLOCK = register(
        blockWithItem("pure_emerald_block", () -> new Block(Properties.ofFullCopy(EMERALD_BLOCK)), InkColors.CYAN));
    public static final DeferredBlock<Block> PURE_REDSTONE_BLOCK = register(blockWithItem(
        "pure_redstone_block", () -> new PureRedstoneBlock(Properties.ofFullCopy(REDSTONE_BLOCK)), InkColors.RED));
    public static final DeferredBlock<Block> PURE_LAPIS_BLOCK = register(
        blockWithItem("pure_lapis_block", () -> new Block(Properties.ofFullCopy(LAPIS_BLOCK)), InkColors.PURPLE));
    public static final DeferredBlock<Block> PURE_COPPER_BLOCK = register(
        blockWithItem("pure_copper_block", () -> new Block(Properties.ofFullCopy(COPPER_BLOCK)), InkColors.BROWN));
    public static final DeferredBlock<Block> PURE_QUARTZ_BLOCK = register(
        blockWithItem("pure_quartz_block", () -> new Block(Properties.ofFullCopy(QUARTZ_BLOCK)), InkColors.BROWN));
    public static final DeferredBlock<Block> PURE_GLOWSTONE_BLOCK = register(
        blockWithItem("pure_glowstone_block", () -> new Block(Properties.ofFullCopy(GLOWSTONE)), InkColors.YELLOW));
    public static final DeferredBlock<Block> PURE_PRISMARINE_BLOCK = register(
        blockWithItem("pure_prismarine_block", () -> new Block(Properties.ofFullCopy(PRISMARINE)), InkColors.CYAN));
    public static final DeferredBlock<Block> PURE_NETHERITE_SCRAP_BLOCK = register(blockWithItem(
        "pure_netherite_scrap_block", () -> new Block(Properties.ofFullCopy(ANCIENT_DEBRIS)), IS.of()
                                                                                                .fireResistant(),
        InkColors.BROWN
    ));
    public static final DeferredBlock<Block> PURE_ECHO_BLOCK = register(
        blockWithItem("pure_echo_block", () -> new Block(Properties.ofFullCopy(DIAMOND_BLOCK)), InkColors.BROWN));

    private static Properties preservationBlock() {
        return settings(MapColor.CLAY, SoundType.STONE, -1.0F, 3600000.0F).instrument(NoteBlockInstrument.BASEDRUM)
                                                                          .noLootTable()
                                                                          .isValidSpawn(PastelBlocks::never)
                                                                          .forceSolidOn();
    }

    public static final DeferredBlock<Block> PRESERVATION_CONTROLLER = register(blockWithItem(
        "preservation_controller", () -> new PreservationControllerBlock(preservationBlock().lightLevel(state -> 1)
                                                                                            .emissiveRendering(
                                                                                                PastelBlocks::always)
                                                                                            .hasPostProcess(
                                                                                                PastelBlocks::always)),
        InkColors.BLUE
    ));
    public static final DeferredBlock<Block> DIKE_GATE = register(blockWithItem(
        "dike_gate", () -> new DikeGateBlock(preservationBlock().lightLevel(state -> 3)
                                                                .sound(SoundType.GLASS)
                                                                .noOcclusion()
                                                                .emissiveRendering(PastelBlocks::always)
                                                                .hasPostProcess(PastelBlocks::always)
                                                                .isRedstoneConductor(PastelBlocks::never)
                                                                .isSuffocating(PastelBlocks::never)
                                                                .isViewBlocking(PastelBlocks::never)), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> DREAM_GATE = register(blockWithItem(
        "dream_gate", () -> new DreamGateBlock(preservationBlock().lightLevel(state -> 3)
                                                                  .sound(SoundType.GLASS)
                                                                  .noOcclusion()
                                                                  .emissiveRendering(PastelBlocks::always)
                                                                  .hasPostProcess(PastelBlocks::always)
                                                                  .isRedstoneConductor(PastelBlocks::never)
                                                                  .isSuffocating(PastelBlocks::never)
                                                                  .isViewBlocking(PastelBlocks::never)), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> INVISIBLE_WALL = register(blockWithItem(
        "invisible_wall", () -> new InvisibleWallBlock(preservationBlock().lightLevel(state -> 3)
                                                                          .sound(SoundType.GLASS)
                                                                          .noOcclusion()
                                                                          .isViewBlocking(PastelBlocks::never)),
        InkColors.BLUE
    ));
    public static final DeferredBlock<Block> PRESERVATION_CHEST = register(
        blockWithItem("preservation_chest", () -> new TreasureChestBlock(preservationBlock()), InkColors.BLUE));

    public static final DeferredBlock<Block> DOWNSTONE = register(
        blockWithItem("downstone", () -> new Block(preservationBlock()), InkColors.BLUE));

    public static final DeferredBlock<Block> PRESERVATION_STONE = register(
        blockWithItem("preservation_stone", () -> new Block(preservationBlock()), InkColors.BLUE));
    public static final DeferredBlock<Block> PRESERVATION_STAIRS = register(blockWithItem(
        "preservation_stairs", () -> new StairBlock(
            PastelBlocks.PRESERVATION_STONE.get()
                                           .defaultBlockState(), preservationBlock()
        ), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> PRESERVATION_SLAB = register(
        blockWithItem("preservation_slab", () -> new SlabBlock(preservationBlock()), InkColors.BLUE));
    public static final DeferredBlock<Block> PRESERVATION_WALL = register(
        blockWithItem("preservation_wall", () -> new WallBlock(preservationBlock()), InkColors.BLUE));

    public static final DeferredBlock<Block> POWDER_CHISELED_PRESERVATION_STONE = register(blockWithItem(
        "powder_chiseled_preservation_stone", () -> new Block(preservationBlock().lightLevel(state -> 2)),
        InkColors.BLUE
    ));
    public static final DeferredBlock<Block> DIKE_CHISELED_PRESERVATION_STONE = register(blockWithItem(
        "dike_chiseled_preservation_stone", () -> new Block(preservationBlock().lightLevel(state -> 6)),
        InkColors.BLUE
    ));
    public static final DeferredBlock<Block> DREAM_CHISELED_PRESERVATION_STONE = register(blockWithItem(
        "dream_chiseled_preservation_stone", () -> new Block(preservationBlock().lightLevel(state -> 6)),
        InkColors.BLUE
    ));
    public static final DeferredBlock<Block> DEEP_LIGHT_CHISELED_PRESERVATION_STONE = register(blockWithItem(
        "deep_light_chiseled_preservation_stone", () -> new DeepLightBlock(preservationBlock().lightLevel(state -> 2)),
        InkColors.BLUE
    ));

    public static final DeferredBlock<Block> ENLIGHTENMENT_ITEM_BOWL = register(blockWithItem(
        "enlightenment_item_bowl", () -> new TreasureItemBowlBlock(preservationBlock().noOcclusion()
                                                                                      .isRedstoneConductor(
                                                                                          PastelBlocks::never)
                                                                                      .isSuffocating(
                                                                                          PastelBlocks::never)
                                                                                      .isViewBlocking(
                                                                                          PastelBlocks::never)),
        InkColors.BLUE
    ));

    public static final DeferredBlock<Block> DIKE_GATE_FOUNTAIN = register(
        blockWithItem("dike_gate_fountain", () -> new PastelFacingBlock(preservationBlock()), InkColors.BLUE));
    public static final DeferredBlock<Block> PRESERVATION_BRICKS = register(
        blockWithItem("preservation_bricks", () -> new Block(preservationBlock()), InkColors.BLUE));
    public static final DeferredBlock<Block> SHIMMERING_PRESERVATION_BRICKS = register(blockWithItem(
        "shimmering_preservation_bricks", () -> new Block(preservationBlock().lightLevel(s -> 5)), InkColors.BLUE));
    public static final DeferredBlock<Block> COURIER_STATUE = register(
        blockWithItem("courier_statue", () -> new StatueBlock(preservationBlock()), InkColors.BLUE));
    public static final DeferredBlock<Block> MANXI = register(block(
        "manxi", () -> new ManxiBlock(preservationBlock().noOcclusion()
                                                         .noCollission()
                                                         .noLootTable())
    ));

    public static final DeferredBlock<Block> BLACK_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("black_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.BLACK));
    public static final DeferredBlock<Block> BLUE_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("blue_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.BLUE));
    public static final DeferredBlock<Block> BROWN_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("brown_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.BROWN));
    public static final DeferredBlock<Block> CYAN_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("cyan_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.CYAN));
    public static final DeferredBlock<Block> GRAY_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("gray_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.GRAY));
    public static final DeferredBlock<Block> GREEN_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("green_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.GREEN));
    public static final DeferredBlock<Block> LIGHT_BLUE_CHISELED_PRESERVATION_STONE = register(blockWithItem(
        "light_blue_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.LIGHT_BLUE));
    public static final DeferredBlock<Block> LIGHT_GRAY_CHISELED_PRESERVATION_STONE = register(blockWithItem(
        "light_gray_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.LIGHT_GRAY));
    public static final DeferredBlock<Block> LIME_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("lime_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.LIME));
    public static final DeferredBlock<Block> MAGENTA_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("magenta_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.MAGENTA));
    public static final DeferredBlock<Block> ORANGE_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("orange_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.ORANGE));
    public static final DeferredBlock<Block> PINK_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("pink_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.PINK));
    public static final DeferredBlock<Block> PURPLE_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("purple_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.PURPLE));
    public static final DeferredBlock<Block> RED_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("red_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.RED));
    public static final DeferredBlock<Block> WHITE_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("white_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.WHITE));
    public static final DeferredBlock<Block> YELLOW_CHISELED_PRESERVATION_STONE = register(
        blockWithItem("yellow_chiseled_preservation_stone", () -> new Block(preservationBlock()), InkColors.YELLOW));

    public static final DeferredBlock<Block> PRESERVATION_GLASS = register(blockWithItem(
        "preservation_glass", () -> new TransparentBlock(preservationBlock().sound(SoundType.GLASS)
                                                                            .noOcclusion()
                                                                            .isRedstoneConductor(PastelBlocks::never)
                                                                            .isSuffocating(PastelBlocks::never)
                                                                            .isViewBlocking(PastelBlocks::never)),
        InkColors.BLUE
    ));
    public static final DeferredBlock<Block> TINTED_PRESERVATION_GLASS = register(blockWithItem(
        "tinted_preservation_glass",
        () -> new TintedGlassBlock(Properties.ofFullCopy(PastelBlocks.PRESERVATION_GLASS.get())), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> PRESERVATION_ROUNDEL = register(blockWithItem(
        "preservation_roundel", () -> new PreservationRoundelBlock(preservationBlock().noOcclusion()
                                                                                      .forceSolidOn()), InkColors.BLUE
    ));
    public static final DeferredBlock<Block> PRESERVATION_BLOCK_DETECTOR = register(blockWithItem(
        "preservation_block_detector", () -> new PreservationBlockDetectorBlock(preservationBlock()), InkColors.BLUE));

    private static Properties shootingStar() {
        return BlockBehaviour.Properties.ofFullCopy(STONE)
                                        .noOcclusion();
    }

    public static final DeferredBlock<Block> GLISTERING_SHOOTING_STAR = register(blockWithItem(
        "glistering_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.GLISTERING),
        block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> FIERY_SHOOTING_STAR = register(blockWithItem(
        "fiery_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.FIERY),
        block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> COLORFUL_SHOOTING_STAR = register(blockWithItem(
        "colorful_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.COLORFUL),
        block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> PRISTINE_SHOOTING_STAR = register(blockWithItem(
        "pristine_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.PRISTINE),
        block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> GEMSTONE_SHOOTING_STAR = register(blockWithItem(
        "gemstone_shooting_star", () -> new ShootingStarBlock(shootingStar(), ShootingStar.Variant.GEMSTONE),
        block -> new ShootingStarItem(block, IS.of(1, Rarity.UNCOMMON)), InkColors.PURPLE
    ));
    public static final DeferredBlock<Block> STARDUST_BLOCK = register(blockWithItem(
        "stardust_block", () -> new ColoredFallingBlock(
            new ColorRGBA(DyeColor.PURPLE.getFireworkColor()), Properties.ofFullCopy(SAND)
                                                                         .mapColor(MapColor.COLOR_PURPLE)
        ), IS.of(1, Rarity.UNCOMMON), InkColors.BLACK
    ));

    public static final DeferredBlock<Block> INCANDESCENT_AMALGAM = register(blockWithItem(
        "incandescent_amalgam", () -> new IncandescentAmalgamBlock(Properties.of()
                                                                             .instabreak()
                                                                             .noOcclusion()),
        block -> new IncandescentAmalgamItem(
            block, IS.of(16)
                     .food(PastelFoodComponents.INCANDESCENT_AMALGAM)
        ), InkColors.RED
    ));

    private static Properties idol(SoundType soundGroup) {
        return settings(MapColor.TERRACOTTA_WHITE, soundGroup, 3.0F).requiresCorrectToolForDrops()
                                                                    .noOcclusion();
    }

    public static final DeferredBlock<Block> AXOLOTL_IDOL = register(blockWithItem(
        "axolotl_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.AXOLOTL_IDOL), ParticleTypes.HEART, MobEffects.REGENERATION, 0, 100),
        InkColors.PINK
    )); // heals 2 hp / 1 heart
    public static final DeferredBlock<Block> BAT_IDOL = register(blockWithItem(
        "bat_idol", () -> new AoEStatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.BAT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.GLOWING, 0, 200, 8),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> BEE_IDOL = register(blockWithItem(
        "bee_idol", () -> new BonemealingIdolBlock(idol(PastelBlockSoundGroups.BEE_IDOL), ParticleTypes.DRIPPING_HONEY),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> BLAZE_IDOL = register(blockWithItem(
        "blaze_idol", () -> new FirestarterIdolBlock(idol(PastelBlockSoundGroups.BLAZE_IDOL), ParticleTypes.FLAME),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> CAT_IDOL = register(blockWithItem(
        "cat_idol",
        () -> new FallDamageNegatingIdolBlock(idol(PastelBlockSoundGroups.CAT_IDOL), ParticleTypes.ENCHANTED_HIT),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> CHICKEN_IDOL = register(blockWithItem(
        "chicken_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.CHICKEN_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.SLOW_FALLING, 0, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> COW_IDOL = register(blockWithItem(
        "cow_idol", () -> new MilkingIdolBlock(idol(PastelBlockSoundGroups.COW_IDOL), ParticleTypes.ENCHANTED_HIT, 6),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> CREEPER_IDOL = register(blockWithItem(
        "creeper_idol", () -> new ExplosionIdolBlock(
            idol(PastelBlockSoundGroups.CREEPER_IDOL), ParticleTypes.EXPLOSION, 3, false,
            Explosion.BlockInteraction.DESTROY
        ), InkColors.PINK
    ));
    public static final DeferredBlock<Block> ENDER_DRAGON_IDOL = register(blockWithItem(
        "ender_dragon_idol", () -> new ProjectileIdolBlock(
            idol(PastelBlockSoundGroups.ENDER_DRAGON_IDOL), ParticleTypes.DRAGON_BREATH, EntityType.DRAGON_FIREBALL,
            SoundEvents.ENDER_DRAGON_SHOOT, 6.0F, 1.1F
        ) {
            @Override
            public Projectile createProjectile(
                ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
                LivingMarkerEntity markerEntity = new LivingMarkerEntity(PastelEntityTypes.LIVING_MARKER.get(), world);
                markerEntity.setPosRaw(position.x(), position.y(), position.z());

                Vec3 targetPosition = Vec3.atCenterOf(mobBlockPos.relative(side, 50));
                var velocity = targetPosition.subtract(markerEntity.position());

                DragonFireball entity = new DragonFireball(world, markerEntity, velocity);

                markerEntity.discard();
                return entity;
            }
        }, InkColors.PINK
    ));
    public static final DeferredBlock<Block> ENDERMAN_IDOL = register(blockWithItem(
        "enderman_idol", () -> new RandomTeleportingIdolBlock(
            idol(PastelBlockSoundGroups.ENDERMAN_IDOL), ParticleTypes.REVERSE_PORTAL, 16, 16), InkColors.PINK
    ));
    public static final DeferredBlock<Block> ENDERMITE_IDOL = register(blockWithItem(
        "endermite_idol", () -> new LineTeleportingIdolBlock(
            idol(PastelBlockSoundGroups.ENDERMITE_IDOL), ParticleTypes.REVERSE_PORTAL, 16), InkColors.PINK
    ));
    public static final DeferredBlock<Block> EVOKER_IDOL = register(blockWithItem(
        "evoker_idol", () -> new EntitySummoningIdolBlock(
            idol(PastelBlockSoundGroups.EVOKER_IDOL), ParticleTypes.ANGRY_VILLAGER, EntityType.VEX) {
            @Override
            public void afterSummon(ServerLevel world, Entity entity) {
                ((Vex) entity).setLimitedLife(20 * (30 + world.random.nextInt(90)));
            }
        }, InkColors.PINK
    ));
    public static final DeferredBlock<Block> FISH_IDOL = register(blockWithItem(
        "fish_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.FISH_IDOL), ParticleTypes.SPLASH, MobEffects.WATER_BREATHING, 0, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> FOX_IDOL = register(blockWithItem(
        "fox_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.FOX_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.DIG_SPEED, 0, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> GHAST_IDOL = register(blockWithItem(
        "ghast_idol", () -> new ProjectileIdolBlock(
            idol(PastelBlockSoundGroups.GHAST_IDOL), ParticleTypes.SMOKE, EntityType.FIREBALL, SoundEvents.GHAST_SHOOT,
            6.0F, 1.1F
        ) {
            @Override
            public Projectile createProjectile(
                ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
                LivingMarkerEntity markerEntity = new LivingMarkerEntity(PastelEntityTypes.LIVING_MARKER.get(), world);
                markerEntity.setPosRaw(position.x(), position.y(), position.z());

                Vec3 targetPosition = Vec3.atCenterOf(mobBlockPos.relative(side, 50));
                var velocity = targetPosition.subtract(markerEntity.position());

                LargeFireball entity = new LargeFireball(world, markerEntity, velocity, 1);

                markerEntity.discard();
                return entity;
            }
        }, InkColors.PINK
    ));
    public static final DeferredBlock<Block> GLOW_SQUID_IDOL = register(blockWithItem(
        "glow_squid_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.GLOW_SQUID_IDOL), ParticleTypes.GLOW_SQUID_INK, MobEffects.GLOWING, 0, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> GOAT_IDOL = register(blockWithItem(
        "goat_idol",
        () -> new KnockbackIdolBlock(idol(PastelBlockSoundGroups.GOAT_IDOL), ParticleTypes.ENCHANTED_HIT, 5.0F, 0.5F),
        InkColors.PINK
    )); // knocks mostly sideways
    public static final DeferredBlock<Block> GUARDIAN_IDOL = register(blockWithItem(
        "guardian_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.GUARDIAN_IDOL), ParticleTypes.BUBBLE, MobEffects.DIG_SLOWDOWN, 2, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> HORSE_IDOL = register(blockWithItem(
        "horse_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.HORSE_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.DAMAGE_BOOST, 0, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> ILLUSIONER_IDOL = register(blockWithItem(
        "illusioner_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.ILLUSIONER_IDOL), ParticleTypes.ANGRY_VILLAGER, MobEffects.INVISIBILITY, 0,
            100
        ), InkColors.PINK
    ));
    public static final DeferredBlock<Block> OCELOT_IDOL = register(blockWithItem(
        "ocelot_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.OCELOT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.NIGHT_VISION, 0, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> PARROT_IDOL = register(blockWithItem(
        "parrot_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.PARROT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.ABSORPTION, 0, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> PHANTOM_IDOL = register(blockWithItem(
        "phantom_idol",
        () -> new InsomniaIdolBlock(idol(PastelBlockSoundGroups.PHANTOM_IDOL), ParticleTypes.POOF, 24000),
        InkColors.PINK
    )); // +1 ingame day without sleep
    public static final DeferredBlock<Block> PIG_IDOL = register(blockWithItem(
        "pig_idol", () -> new FeedingIdolBlock(idol(PastelBlockSoundGroups.PIG_IDOL), ParticleTypes.INSTANT_EFFECT, 6),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> PIGLIN_IDOL = register(blockWithItem(
        "piglin_idol", () -> new PiglinTradeIdolBlock(idol(PastelBlockSoundGroups.PIGLIN_IDOL), ParticleTypes.HEART),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> POLAR_BEAR_IDOL = register(blockWithItem(
        "polar_bear_idol",
        () -> new FreezingIdolBlock(idol(PastelBlockSoundGroups.POLAR_BEAR_IDOL), ParticleTypes.SNOWFLAKE),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> PUFFERFISH_IDOL = register(blockWithItem(
        "pufferfish_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.PUFFERFISH_IDOL), ParticleTypes.SPLASH, MobEffects.CONFUSION, 0, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> RABBIT_IDOL = register(blockWithItem(
        "rabbit_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.RABBIT_IDOL), ParticleTypes.INSTANT_EFFECT, MobEffects.JUMP, 3, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> SHEEP_IDOL = register(blockWithItem(
        "sheep_idol",
        () -> new ShearingIdolBlock(idol(PastelBlockSoundGroups.SHEEP_IDOL), ParticleTypes.ENCHANTED_HIT, 6),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> SHULKER_IDOL = register(blockWithItem(
        "shulker_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.SHULKER_IDOL), ParticleTypes.END_ROD, MobEffects.LEVITATION, 0, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> SILVERFISH_IDOL = register(
        blockWithItem(
            "silverfish_idol", () -> new SilverfishInsertingIdolBlock(
                idol(PastelBlockSoundGroups.SILVERFISH_IDOL),
                ParticleTypes.EXPLOSION
            ), InkColors.PINK
        ));
    public static final DeferredBlock<Block> SKELETON_IDOL = register(blockWithItem(
        "skeleton_idol", () -> new ProjectileIdolBlock(
            idol(PastelBlockSoundGroups.SKELETON_IDOL), ParticleTypes.INSTANT_EFFECT, EntityType.ARROW,
            SoundEvents.ARROW_SHOOT, 6.0F, 1.1F
        ) {
            @Override
            public Projectile createProjectile(
                ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
                Arrow arrowEntity = new Arrow(world, position.x(), position.y(), position.z(), ItemStack.EMPTY, null);
                arrowEntity.pickup = AbstractArrow.Pickup.DISALLOWED;
                return arrowEntity;
            }
        }, InkColors.PINK
    ));
    public static final DeferredBlock<Block> SLIME_IDOL = register(blockWithItem(
        "slime_idol",
        () -> new SlimeSizingIdolBlock(idol(PastelBlockSoundGroups.SLIME_IDOL), ParticleTypes.ITEM_SLIME, 6, 8),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> SNOW_GOLEM_IDOL = register(blockWithItem(
        "snow_golem_idol", () -> new ProjectileIdolBlock(
            idol(PastelBlockSoundGroups.SNOW_GOLEM_IDOL), ParticleTypes.SNOWFLAKE, EntityType.SNOWBALL,
            SoundEvents.ARROW_SHOOT, 3.0F, 1.1F
        ) {
            @Override
            public Projectile createProjectile(
                ServerLevel world, BlockPos mobBlockPos, Position position, Direction side) {
                world.playSound(
                    null, mobBlockPos.getX(), mobBlockPos.getY(), mobBlockPos.getZ(), SoundEvents.SNOW_GOLEM_SHOOT,
                    SoundSource.BLOCKS, 1.0F, 0.4F / world.random.nextFloat() * 0.4F + 0.8F
                );
                return new Snowball(world, position.x(), position.y(), position.z());
            }
        }, InkColors.PINK
    ));
    public static final DeferredBlock<Block> SPIDER_IDOL = register(blockWithItem(
        "spider_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.SPIDER_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.POISON, 0, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> SQUID_IDOL = register(blockWithItem(
        "squid_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.SQUID_IDOL), ParticleTypes.SQUID_INK, MobEffects.BLINDNESS, 0, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> STRAY_IDOL = register(blockWithItem(
        "stray_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.STRAY_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.MOVEMENT_SLOWDOWN, 2, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> STRIDER_IDOL = register(blockWithItem(
        "strider_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.STRIDER_IDOL), ParticleTypes.DRIPPING_LAVA, MobEffects.FIRE_RESISTANCE, 0, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> TURTLE_IDOL = register(blockWithItem(
        "turtle_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.TURTLE_IDOL), ParticleTypes.DRIPPING_WATER, MobEffects.DAMAGE_RESISTANCE, 1,
            200
        ), InkColors.PINK
    ));
    public static final DeferredBlock<Block> WITCH_IDOL = register(blockWithItem(
        "witch_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.WITCH_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.WEAKNESS, 0, 200),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> WITHER_IDOL = register(blockWithItem(
        "wither_idol", () -> new ExplosionIdolBlock(
            idol(PastelBlockSoundGroups.WITHER_IDOL), ParticleTypes.EXPLOSION, 7.0F, true,
            Explosion.BlockInteraction.DESTROY
        ), InkColors.PINK
    ));
    public static final DeferredBlock<Block> WITHER_SKELETON_IDOL = register(blockWithItem(
        "wither_skeleton_idol", () -> new StatusEffectIdolBlock(
            idol(PastelBlockSoundGroups.WITHER_SKELETON_IDOL), ParticleTypes.ENCHANTED_HIT, MobEffects.WITHER, 0, 100),
        InkColors.PINK
    ));
    public static final DeferredBlock<Block> ZOMBIE_IDOL = register(
        blockWithItem(
            "zombie_idol", () -> new VillagerConvertingIdolBlock(
                idol(PastelBlockSoundGroups.ZOMBIE_IDOL),
                ParticleTypes.ENCHANTED_HIT
            ), InkColors.PINK
        ));

    // FLUIDS
    private static Properties fluid(MapColor mapColor) {
        return settings(mapColor, SoundType.EMPTY, 100.0F).replaceable()
                                                          .noCollission()
                                                          .pushReaction(PushReaction.DESTROY)
                                                          .noLootTable()
                                                          .liquid();
    }

    public static final DeferredBlock<Block> LIQUID_CRYSTAL = register(block(
        "liquid_crystal", () -> new LiquidCrystalFluidBlock(
            PastelFluids.LIQUID_CRYSTAL.get(), PastelBlocks.BLAZING_CRYSTAL.get()
                                                                           .defaultBlockState(), fluid(
            MapColor.CRIMSON_STEM).lightLevel((state) -> LiquidCrystalFluidBlock.LUMINANCE)
                                  .replaceable()
        )
    ));
    public static final DeferredBlock<Block> HUMUS = register(block(
        "humus", () -> new HumusFluidBlock(
            PastelFluids.HUMUS.get(), MUD.defaultBlockState(), fluid(MapColor.TERRACOTTA_BROWN).replaceable())
    ));
    public static final DeferredBlock<Block> MIDNIGHT_SOLUTION = register(block(
        "midnight_solution", () -> new MidnightSolutionFluidBlock(
            PastelFluids.MIDNIGHT_SOLUTION.get(), PastelBlocks.BLACK_MATERIA.get()
                                                                            .defaultBlockState(),
            fluid(MapColor.WARPED_STEM).replaceable()
        )
    ));
    public static final DeferredBlock<Block> DRAGONROT = register(block(
        "dragonrot", () -> new DragonrotFluidBlock(
            PastelFluids.DRAGONROT.get(), BLACKSTONE.defaultBlockState(), fluid(MapColor.ICE).lightLevel((state) -> 15)
                                                                                             .replaceable()
        )
    ));

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

    public static <T extends Block> BlockRegistrar<T> blockWithItem(
        String name, Supplier<T> block, Item.Properties settings, InkColor color) {
        return blockWithItem(name, block, b -> new BlockItem(b, settings), color);
    }

    public static <T extends Block> BlockRegistrar<T> blockWithItem(
        String name, Supplier<T> block, Function<T, Item> itemFactory, InkColor color) {
        return block(name, block).withItem(itemFactory, color);
    }

    public static <T extends Block> BlockRegistrar<T> burnable(BlockRegistrar<T> registrar, int burnTicks) {
        return registrar.withBurnTime(burnTicks);
    }

    public static class BlockRegistrar<T extends Block> {

        private final ResourceLocation id;
        private boolean hasBlock = false;
        private boolean hasItem = false;
        @Nullable
        private DeferredBlock<Block> holder = null;

        public BlockRegistrar(String name) {
            this.id = locate(name);
        }

        public BlockRegistrar<T> withBlock(Supplier<T> blockFactory) {
            if (hasBlock) throw new UnsupportedOperationException("Attempted to register two blocks with id " + id);
            hasBlock = true;
            holder = PastelBlocks.COMMON_REGISTRAR.register(id.getPath(), blockFactory);
            return this;
        }

        public BlockRegistrar<T> withItem(Function<T, Item> callback, InkColor color) {
            if (hasItem) throw new UnsupportedOperationException("Attempted to register two items with id " + id);
            hasItem = true;

            PastelItems.ITEM_REGISTRAR.register(
                id.getPath(), () -> {
                    Item item = callback.apply((T) holder.get());
                    ItemColors.ITEM_COLORS.registerColorMapping(item, color);
                    return item;
                }
            );
            return this;
        }


        public BlockRegistrar<T> withBurnTime(int burnTicks) {
            if (hasItem) {
                PastelItems.BURN_TIMES.add(new Pair<>(holder, burnTicks));
            }
            return this;
        }

        @Nullable
        public DeferredBlock<Block> holder() {
            return holder;
        }

        public ResourceKey<Block> blockKey() {
            return ResourceKey.create(Registries.BLOCK, id);
        }

        public ResourceKey<Item> itemKey() {
            return ResourceKey.create(Registries.ITEM, id);
        }

    }

    public static Map<PastelSkullType, DeferredBlock<Block>> MOB_HEADS = new HashMap<>();
    public static Map<PastelSkullType, DeferredBlock<Block>> WALL_HEADS = new HashMap<>();

    public static void registerCommon(IEventBus bus) {
        // All the mob heads
        for (PastelSkullType type : PastelSkullType.values()) {
            BlockRegistrar<PastelSkullBlock> registrar = block(
                type.getSerializedName() + "_head", () -> new PastelSkullBlock(
                    type, BlockBehaviour.Properties.ofFullCopy(SKELETON_SKULL)
                                                   .instrument(NoteBlockInstrument.CUSTOM_HEAD)
                )
            );
            DeferredBlock<Block> wallHead = register(block(
                type.getSerializedName() + "_wall_head", () -> new PastelWallSkullBlock(
                    type, BlockBehaviour.Properties.ofFullCopy(SKELETON_SKULL)
                                                   .dropsLike(registrar.holder()
                                                                       .get())
                )
            ));
            MOB_HEADS.put(
                type, register(registrar.withItem(
                    block -> new StandingAndWallBlockItem(block, wallHead.get(), IS.of(), Direction.DOWN),
                    InkColors.GRAY
                ))
            );
            WALL_HEADS.put(type, wallHead);
        }

        PastelBlocks.COMMON_REGISTRAR.register(bus);
    }

    // all the render types, until we get a way to set these in the models directly. thank you neoforge
    public static void registerClient(FMLClientSetupEvent event) {
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BLACK_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BLUE_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BROWN_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CYAN_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GRAY_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GREEN_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LIGHT_BLUE_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LIGHT_GRAY_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LIME_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MAGENTA_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ORANGE_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PINK_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PURPLE_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.RED_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.WHITE_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.YELLOW_SPORE_BLOSSOM);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.BLACK_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.BLUE_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.BROWN_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CYAN_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.GRAY_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.GREEN_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.LIGHT_BLUE_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.LIGHT_GRAY_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.LIME_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MAGENTA_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ORANGE_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PINK_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PURPLE_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.RED_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.WHITE_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.YELLOW_LAMP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SNOWGRAVE);
        PastelModelHelper.BLOCK.mippedCutout(PastelBlocks.PYRITE_RIPPER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SLATE_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.EBONY_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.IVORY_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CHESTNUT_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SLATE_NOXWOOD_LANTERN);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.EBONY_NOXWOOD_LANTERN);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.IVORY_NOXWOOD_LANTERN);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CHESTNUT_NOXWOOD_LANTERN);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.WEEPING_GALA_LANTERN);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.WEEPING_GALA_FRONDS_PLANT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.VEGETAL_BLOCK);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.FUSION_SHRINE_BASALT);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.FUSION_SHRINE_CALCITE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ENCHANTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SPIRIT_INSTILLER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CRYSTALLARIEUM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.COLOR_PICKER);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.POTION_WORKSHOP);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ITEM_BOWL_BASALT);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ITEM_BOWL_CALCITE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PRIMORDIAL_WALL_TORCH);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PRIMORDIAL_TORCH);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LONGING_CHIMERA);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.RESPLENDENT_BED);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADE_PETAL_BLOCK);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADE_PETAL_CARPET);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADEITE_PETAL_BLOCK);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADEITE_PETAL_CARPET);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GLISTERING_SHOOTING_STAR);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.FIERY_SHOOTING_STAR);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.COLORFUL_SHOOTING_STAR);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PRISTINE_SHOOTING_STAR);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GEMSTONE_SHOOTING_STAR);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TOPAZ_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AMETHYST_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CITRINE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ONYX_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MOONSTONE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.RADIANT_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TOPAZ_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AMETHYST_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CITRINE_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ONYX_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MOONSTONE_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.RADIANT_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TOPAZ_CHIME);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AMETHYST_CHIME);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CITRINE_CHIME);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ONYX_CHIME);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MOONSTONE_CHIME);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TOPAZ_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AMETHYST_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CITRINE_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ONYX_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MOONSTONE_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.HUMMINGSTONE_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.HUMMINGSTONE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.WAXED_HUMMINGSTONE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TOPAZ_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AMETHYST_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CITRINE_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ONYX_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MOONSTONE_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.RADIANT_GLASS_PANE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.HUMMINGSTONE_GLASS_PANE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PRIMORDIAL_FIRE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PRESENT);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BOTTOMLESS_BUNDLE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.FABRICATION_CHEST);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PARTICLE_SPAWNER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CREATIVE_PARTICLE_SPAWNER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.REDSTONE_TIMER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.REDSTONE_CALCULATOR);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.INCANDESCENT_AMALGAM);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ETHEREAL_PLATFORM);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.UNIVERSE_SPYHOLE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.BLACK_HOLE_CHEST);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MEMORY);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ENDER_GLASS);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CONNECTION_NODE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PROVIDER_NODE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.STORAGE_NODE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BUFFER_NODE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SENDER_NODE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GATHER_NODE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_RED_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_YELLOW_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_PINK_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_PURPLE_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_BLACK_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.TALL_RED_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.TALL_YELLOW_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.TALL_PINK_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.TALL_PURPLE_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.TALL_BLACK_DRAGONJAG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ATTACHED_GLISTERING_MELON_STEM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GLISTERING_MELON_STEM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.VARIA_SPROUT);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ALOE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SAWBLADE_HOLLY_BUSH);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BRISTLE_SPROUTS);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.DOOMBLOOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SNAPPING_IVY);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ABYSSAL_VINES);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.NIGHTDEW);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MOSS_BALL);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADE_VINE_ROOTS);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADE_VINE_BULB);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADE_VINES);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.NEPHRITE_BLOSSOM_STEM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.NEPHRITE_BLOSSOM_LEAVES);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.NEPHRITE_BLOSSOM_BULB);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADEITE_LOTUS_STEM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADEITE_LOTUS_FLOWER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MERMAIDS_BRUSH);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CLOVER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.FOUR_LEAF_CLOVER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BLOOD_ORCHID);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_BLOOD_ORCHID);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.DIKE_GATE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.DREAM_GATE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.INVISIBLE_WALL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PRESERVATION_GLASS);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TINTED_PRESERVATION_GLASS);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PRESERVATION_CONTROLLER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ENLIGHTENMENT_ITEM_BOWL);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.COURIER_STATUE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PEDESTAL_BASIC_TOPAZ);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PEDESTAL_BASIC_AMETHYST);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PEDESTAL_BASIC_CITRINE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PEDESTAL_ALL_BASIC);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PEDESTAL_ONYX);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PEDESTAL_MOONSTONE);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AXOLOTL_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.BAT_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.BEE_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.BLAZE_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CAT_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CHICKEN_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.COW_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CREEPER_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ENDER_DRAGON_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ENDERMAN_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ENDERMITE_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.EVOKER_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.FISH_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.FOX_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.GHAST_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.GLOW_SQUID_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.GOAT_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.GUARDIAN_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.HORSE_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ILLUSIONER_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.OCELOT_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PARROT_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PHANTOM_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PIG_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PIGLIN_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.POLAR_BEAR_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.PUFFERFISH_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.RABBIT_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SHEEP_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SHULKER_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SILVERFISH_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SKELETON_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SLIME_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SNOW_GOLEM_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SPIDER_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.SQUID_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.STRAY_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.STRIDER_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TURTLE_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.WITCH_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.WITHER_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.WITHER_SKELETON_IDOL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ZOMBIE_IDOL);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SAG_LEAF);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SAG_BUBBLE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_SAG_BUBBLE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SWEET_PEA);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.APRICOTTI);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.HUMMING_BELL);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.NEPHRITE_BLOSSOM_BULB);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.JADEITE_LOTUS_BULB);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.QUITOXIC_REEDS);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.AMARANTH_BUSHEL);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.RESONANT_LILY);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BLACK_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BLUE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BROWN_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CYAN_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GRAY_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GREEN_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LIGHT_BLUE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LIGHT_GRAY_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LIME_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MAGENTA_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ORANGE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PINK_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PURPLE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.RED_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.WHITE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.YELLOW_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.WEEPING_GALA_SPRIG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.WEEPING_GALA_FRONDS);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_AMARANTH_BUSHEL);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_RESONANT_LILY);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_BLACK_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_BLUE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_BROWN_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_CYAN_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_GRAY_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_GREEN_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_LIGHT_BLUE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_LIGHT_GRAY_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_LIME_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_MAGENTA_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_ORANGE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_PINK_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_PURPLE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_RED_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_WHITE_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_YELLOW_SAPLING);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_SLATE_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_EBONY_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_IVORY_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_CHESTNUT_NOXSHROOM);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.POTTED_WEEPING_GALA_SPRIG);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.TOPAZ_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_TOPAZ_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MEDIUM_TOPAZ_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_TOPAZ_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.CITRINE_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_CITRINE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MEDIUM_CITRINE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_CITRINE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ONYX_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_ONYX_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MEDIUM_ONYX_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_ONYX_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MOONSTONE_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_MOONSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MEDIUM_MOONSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_MOONSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BISMUTH_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_BISMUTH_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_BISMUTH_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_COAL_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_COAL_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.COAL_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_IRON_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_IRON_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.IRON_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_GOLD_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_GOLD_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GOLD_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_DIAMOND_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_DIAMOND_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.DIAMOND_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_EMERALD_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_EMERALD_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.EMERALD_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_REDSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_REDSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.REDSTONE_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_LAPIS_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_LAPIS_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LAPIS_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_COPPER_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_COPPER_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.COPPER_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_QUARTZ_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_QUARTZ_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.QUARTZ_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_NETHERITE_SCRAP_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_NETHERITE_SCRAP_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.NETHERITE_SCRAP_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_ECHO_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_ECHO_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.ECHO_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_GLOWSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_GLOWSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.GLOWSTONE_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_PRISMARINE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_PRISMARINE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.PRISMARINE_CLUSTER);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TOPAZ_BASALT_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AMETHYST_BASALT_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CITRINE_BASALT_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ONYX_BASALT_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MOONSTONE_BASALT_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TOPAZ_CALCITE_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AMETHYST_CALCITE_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.CITRINE_CALCITE_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.ONYX_CALCITE_LIGHT);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.MOONSTONE_CALCITE_LIGHT);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.AZURITE_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_AZURITE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_AZURITE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.MALACHITE_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_MALACHITE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_MALACHITE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.BLOODSTONE_CLUSTER);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.LARGE_BLOODSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.SMALL_BLOODSTONE_BUD);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.STUCK_STORM_STONE);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.AMARANTH);
        PastelModelHelper.BLOCK.cutout(PastelBlocks.AMARANTH_BUSHEL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AZURE_CRYSTAL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.AZURE_OUTCROP);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.VIRIDIAN_CRYSTAL);
        PastelModelHelper.BLOCK.translucent(PastelBlocks.TEMPORARY_PLATFORM);

        if (PastelIntegrationPacks.isIntegrationPackActive(PastelIntegrationPacks.AE2_ID)) {
            PastelModelHelper.BLOCK.cutout(AE2Compat.SMALL_FLUIX_BUD);
            PastelModelHelper.BLOCK.cutout(AE2Compat.LARGE_FLUIX_BUD);
            PastelModelHelper.BLOCK.cutout(AE2Compat.FLUIX_CLUSTER);
        }

        if (PastelIntegrationPacks.isIntegrationPackActive(PastelIntegrationPacks.CREATE_ID)) {
            PastelModelHelper.BLOCK.cutout(CreateCompat.SMALL_ZINC_BUD);
            PastelModelHelper.BLOCK.cutout(CreateCompat.LARGE_ZINC_BUD);
            PastelModelHelper.BLOCK.cutout(CreateCompat.ZINC_CLUSTER);
        }
    }

}
