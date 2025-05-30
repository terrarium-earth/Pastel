package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.blocks.PlacedItemBlockEntity;
import earth.terrarium.pastel.blocks.amphora.AmphoraBlockEntity;
import earth.terrarium.pastel.blocks.block_flooder.BlockFlooderBlockEntity;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleBlockEntity;
import earth.terrarium.pastel.blocks.bottomless_bundle.BottomlessBundleBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.BlackHoleChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.BlackHoleChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.CompactingChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.CompactingChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.FabricationChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.FabricationChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.HeartboundChestBlockEntity;
import earth.terrarium.pastel.blocks.chests.HeartboundChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.chests.SpectrumChestBlockEntityRenderer;
import earth.terrarium.pastel.blocks.cinderhearth.CinderhearthBlockEntity;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlockEntity;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlockEntityRenderer;
import earth.terrarium.pastel.blocks.decoration.ProjectorBlockEntity;
import earth.terrarium.pastel.blocks.decoration.ProjectorBlockEntityRenderer;
import earth.terrarium.pastel.blocks.deeper_down.HummingstoneBlockEntity;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntity;
import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntityRenderer;
import earth.terrarium.pastel.blocks.ender.EnderDropperBlockEntity;
import earth.terrarium.pastel.blocks.ender.EnderHopperBlockEntity;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlockEntity;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlockEntityRenderer;
import earth.terrarium.pastel.blocks.energy.CrystalApothecaryBlockEntity;
import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlockEntity;
import earth.terrarium.pastel.blocks.fusion_shrine.FusionShrineBlockEntityRenderer;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlockEntity;
import earth.terrarium.pastel.blocks.item_bowl.ItemBowlBlockEntityRenderer;
import earth.terrarium.pastel.blocks.item_roundel.ItemRoundelBlockEntity;
import earth.terrarium.pastel.blocks.item_roundel.ItemRoundelBlockEntityRenderer;
import earth.terrarium.pastel.blocks.jade_vines.JadeVineRootsBlockEntity;
import earth.terrarium.pastel.blocks.jade_vines.JadeVineRootsBlockEntityRenderer;
import earth.terrarium.pastel.blocks.memory.MemoryBlockEntity;
import earth.terrarium.pastel.blocks.mob_head.SpectrumSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.SpectrumSkullBlockEntity;
import earth.terrarium.pastel.blocks.mob_head.SpectrumWallSkullBlock;
import earth.terrarium.pastel.blocks.mob_head.client.SpectrumSkullBlockEntityRenderer;
import earth.terrarium.pastel.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntityRenderer;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntityRenderer;
import earth.terrarium.pastel.blocks.potion_workshop.PotionWorkshopBlockEntity;
import earth.terrarium.pastel.blocks.present.PresentBlockEntity;
import earth.terrarium.pastel.blocks.redstone.BlockBreakerBlockEntity;
import earth.terrarium.pastel.blocks.redstone.BlockPlacerBlockEntity;
import earth.terrarium.pastel.blocks.redstone.PlayerDetectorBlockEntity;
import earth.terrarium.pastel.blocks.redstone.RedstoneCalculatorBlockEntity;
import earth.terrarium.pastel.blocks.redstone.RedstoneTransceiverBlockEntity;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import earth.terrarium.pastel.blocks.spirit_instiller.SpiritInstillerBlockEntityRenderer;
import earth.terrarium.pastel.blocks.spirit_sallow.OminousSaplingBlockEntity;
import earth.terrarium.pastel.blocks.structure.DeepLightBlockEntity;
import earth.terrarium.pastel.blocks.structure.DeepLightBlockEntityRenderer;
import earth.terrarium.pastel.blocks.structure.PlayerTrackerBlockEntity;
import earth.terrarium.pastel.blocks.structure.PlayerTrackingBlockEntityRenderer;
import earth.terrarium.pastel.blocks.structure.PreservationBlockDetectorBlockEntity;
import earth.terrarium.pastel.blocks.structure.PreservationControllerBlockEntity;
import earth.terrarium.pastel.blocks.structure.PreservationControllerBlockEntityRenderer;
import earth.terrarium.pastel.blocks.structure.PreservationRoundelBlockEntity;
import earth.terrarium.pastel.blocks.structure.TreasureChestBlockEntity;
import earth.terrarium.pastel.blocks.titration_barrel.TitrationBarrelBlockEntity;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlock;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlockBlockEntityRenderer;
import earth.terrarium.pastel.blocks.upgrade.UpgradeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.*;
import net.neoforged.fml.event.lifecycle.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.registries.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class SpectrumBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SpectrumCommon.MOD_ID);

	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<OminousSaplingBlockEntity>> OMINOUS_SAPLING;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PedestalBlockEntity>> PEDESTAL;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<FusionShrineBlockEntity>> FUSION_SHRINE;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<EnchanterBlockEntity>> ENCHANTER;

	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemBowlBlockEntity>> ITEM_BOWL;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ItemRoundelBlockEntity>> ITEM_ROUNDEL;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderDropperBlockEntity>> ENDER_DROPPER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<EnderHopperBlockEntity>> ENDER_HOPPER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ParticleSpawnerBlockEntity>> PARTICLE_SPAWNER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<UpgradeBlockEntity>> UPGRADE_BLOCK;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<SpectrumSkullBlockEntity>> SKULL;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BottomlessBundleBlockEntity>> BOTTOMLESS_BUNDLE;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PotionWorkshopBlockEntity>> POTION_WORKSHOP;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystallarieumBlockEntity>> CRYSTALLARIEUM;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CinderhearthBlockEntity>> CINDERHEARTH;
	
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CrystalApothecaryBlockEntity>> CRYSTAL_APOTHECARY;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ColorPickerBlockEntity>> COLOR_PICKER;
	
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<CompactingChestBlockEntity>> COMPACTING_CHEST;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<FabricationChestBlockEntity>> FABRICATION_CHEST;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<HeartboundChestBlockEntity>> HEARTBOUND_CHEST;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlackHoleChestBlockEntity>> BLACK_HOLE_CHEST;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TreasureChestBlockEntity>> PRESERVATION_CHEST;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<AmphoraBlockEntity>> AMPHORA;

	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<ProjectorBlockEntity>> PROJECTOR;

	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PlayerDetectorBlockEntity>> PLAYER_DETECTOR;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<RedstoneCalculatorBlockEntity>> REDSTONE_CALCULATOR;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<RedstoneTransceiverBlockEntity>> REDSTONE_TRANSCEIVER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockPlacerBlockEntity>> BLOCK_PLACER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockBreakerBlockEntity>> BLOCK_BREAKER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<BlockFlooderBlockEntity>> BLOCK_FLOODER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<SpiritInstillerBlockEntity>> SPIRIT_INSTILLER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<MemoryBlockEntity>> MEMORY;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<JadeVineRootsBlockEntity>> JADE_VINE_ROOTS;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PresentBlockEntity>> PRESENT;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<TitrationBarrelBlockEntity>> TITRATION_BARREL;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PastelNodeBlockEntity>> PASTEL_NODE;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<HummingstoneBlockEntity>> HUMMINGSTONE;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PlacedItemBlockEntity>> PLACED_ITEM;

	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PreservationControllerBlockEntity>> PRESERVATION_CONTROLLER;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PreservationRoundelBlockEntity>> PRESERVATION_ROUNDEL;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PreservationBlockDetectorBlockEntity>> PRESERVATION_BLOCK_DETECTOR;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<DeepLightBlockEntity>> DEEP_LIGHT;
	public static DeferredHolder<BlockEntityType<?>, BlockEntityType<PlayerTrackerBlockEntity>> PLAYER_TRACKING;
	
	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<? extends  Block>... blocks) {
		return register(id, factory, () -> Arrays.asList(blocks).stream().map(Supplier::get).toList());
	}

	private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String id, BlockEntityType.BlockEntitySupplier<T> factory, Supplier<List<? extends  Block>> blocks) {
		return REGISTER.register(id, () -> BlockEntityType.Builder.of(factory, blocks.get().toArray(new Block[0])).build(null));
	}
	
	public static void register(IEventBus bus) {
		OMINOUS_SAPLING = register("ominous_sapling_block_entity", OminousSaplingBlockEntity::new, SpectrumBlocks.OMINOUS_SAPLING);
		PEDESTAL = register("pedestal_block_entity", PedestalBlockEntity::new, SpectrumBlocks.PEDESTAL_BASIC_AMETHYST, SpectrumBlocks.PEDESTAL_BASIC_TOPAZ, SpectrumBlocks.PEDESTAL_BASIC_CITRINE, SpectrumBlocks.PEDESTAL_ALL_BASIC, SpectrumBlocks.PEDESTAL_ONYX, SpectrumBlocks.PEDESTAL_MOONSTONE);
		FUSION_SHRINE = register("fusion_shrine_block_entity", FusionShrineBlockEntity::new, SpectrumBlocks.FUSION_SHRINE_BASALT, SpectrumBlocks.FUSION_SHRINE_CALCITE);
		ENCHANTER = register("enchanter_block_entity", EnchanterBlockEntity::new, SpectrumBlocks.ENCHANTER);
		ITEM_BOWL = register("item_bowl_block_entity", ItemBowlBlockEntity::new, SpectrumBlocks.ITEM_BOWL_BASALT, SpectrumBlocks.ITEM_BOWL_CALCITE);
		ITEM_ROUNDEL = register("item_roundel", ItemRoundelBlockEntity::new, SpectrumBlocks.ITEM_ROUNDEL);
		ENDER_DROPPER = register("ender_dropper", EnderDropperBlockEntity::new, SpectrumBlocks.ENDER_DROPPER);
		ENDER_HOPPER = register("ender_hopper", EnderHopperBlockEntity::new, SpectrumBlocks.ENDER_HOPPER);
		PARTICLE_SPAWNER = register("particle_spawner", ParticleSpawnerBlockEntity::new, SpectrumBlocks.PARTICLE_SPAWNER, SpectrumBlocks.CREATIVE_PARTICLE_SPAWNER);
		COMPACTING_CHEST = register("compacting_chest", CompactingChestBlockEntity::new, SpectrumBlocks.COMPACTING_CHEST);
		FABRICATION_CHEST = register("fabrication_chest", FabricationChestBlockEntity::new, SpectrumBlocks.FABRICATION_CHEST);
		HEARTBOUND_CHEST = register("heartbound_chest", HeartboundChestBlockEntity::new, SpectrumBlocks.HEARTBOUND_CHEST);
		BLACK_HOLE_CHEST = register("black_hole_chest", BlackHoleChestBlockEntity::new, SpectrumBlocks.BLACK_HOLE_CHEST);
		PRESERVATION_CHEST = register("preservation_chest", TreasureChestBlockEntity::new, SpectrumBlocks.PRESERVATION_CHEST);
		AMPHORA = register("amphora", AmphoraBlockEntity::new, SpectrumBlocks.CHESTNUT_NOXWOOD_AMPHORA, SpectrumBlocks.EBONY_NOXWOOD_AMPHORA, SpectrumBlocks.SLATE_NOXWOOD_AMPHORA, SpectrumBlocks.IVORY_NOXWOOD_AMPHORA, SpectrumBlocks.WEEPING_GALA_AMPHORA);
		PROJECTOR = register("projector", ProjectorBlockEntity::new, SpectrumBlocks.PYRITE_PROJECTOR);
		PLAYER_DETECTOR = register("player_detector", PlayerDetectorBlockEntity::new, SpectrumBlocks.PLAYER_DETECTOR);
		REDSTONE_CALCULATOR = register("redstone_calculator", RedstoneCalculatorBlockEntity::new, SpectrumBlocks.REDSTONE_CALCULATOR);
		REDSTONE_TRANSCEIVER = register("redstone_transceiver", RedstoneTransceiverBlockEntity::new, SpectrumBlocks.REDSTONE_TRANSCEIVER);
		BLOCK_PLACER = register("block_placer", BlockPlacerBlockEntity::new, SpectrumBlocks.BLOCK_PLACER);
		BLOCK_BREAKER = register("block_breaker", BlockBreakerBlockEntity::new, SpectrumBlocks.BLOCK_BREAKER);
		BLOCK_FLOODER = register("block_flooder", BlockFlooderBlockEntity::new, SpectrumBlocks.BLOCK_FLOODER);
		BOTTOMLESS_BUNDLE = register("bottomless_bundle", BottomlessBundleBlockEntity::new, SpectrumBlocks.BOTTOMLESS_BUNDLE);
		POTION_WORKSHOP = register("potion_workshop", PotionWorkshopBlockEntity::new, SpectrumBlocks.POTION_WORKSHOP);
		SPIRIT_INSTILLER = register("spirit_instiller", SpiritInstillerBlockEntity::new, SpectrumBlocks.SPIRIT_INSTILLER);
		MEMORY = register("memory", MemoryBlockEntity::new, SpectrumBlocks.MEMORY);
		JADE_VINE_ROOTS = register("jade_vine_roots", JadeVineRootsBlockEntity::new, SpectrumBlocks.JADE_VINE_ROOTS);
		CRYSTALLARIEUM = register("crystallarieum", CrystallarieumBlockEntity::new, SpectrumBlocks.CRYSTALLARIEUM);
		CRYSTAL_APOTHECARY = register("crystal_apothecary", CrystalApothecaryBlockEntity::new, SpectrumBlocks.CRYSTAL_APOTHECARY);
		COLOR_PICKER = register("color_picker", ColorPickerBlockEntity::new, SpectrumBlocks.COLOR_PICKER);
		CINDERHEARTH = register("cinderhearth", CinderhearthBlockEntity::new, SpectrumBlocks.CINDERHEARTH);
		PRESENT = register("present", PresentBlockEntity::new, SpectrumBlocks.PRESENT);
		TITRATION_BARREL = register("titration_barrel", TitrationBarrelBlockEntity::new, SpectrumBlocks.TITRATION_BARREL);
		PASTEL_NODE = register("pastel_node", PastelNodeBlockEntity::new, SpectrumBlocks.CONNECTION_NODE, SpectrumBlocks.PROVIDER_NODE, SpectrumBlocks.STORAGE_NODE, SpectrumBlocks.SENDER_NODE, SpectrumBlocks.GATHER_NODE, SpectrumBlocks.BUFFER_NODE);
		HUMMINGSTONE = register("hummingstone", HummingstoneBlockEntity::new, SpectrumBlocks.HUMMINGSTONE);
		PLACED_ITEM = register("placed_item", PlacedItemBlockEntity::new, SpectrumBlocks.INCANDESCENT_AMALGAM,
				SpectrumBlocks.COLORFUL_SHOOTING_STAR, SpectrumBlocks.FIERY_SHOOTING_STAR, SpectrumBlocks.GEMSTONE_SHOOTING_STAR, SpectrumBlocks.GLISTERING_SHOOTING_STAR, SpectrumBlocks.PRISTINE_SHOOTING_STAR);
		PRESERVATION_CONTROLLER = register("preservation_controller", PreservationControllerBlockEntity::new, SpectrumBlocks.PRESERVATION_CONTROLLER);
		PRESERVATION_ROUNDEL = register("preservation_roundel", PreservationRoundelBlockEntity::new, SpectrumBlocks.PRESERVATION_ROUNDEL);
		PRESERVATION_BLOCK_DETECTOR = register("preservation_block_detector", PreservationBlockDetectorBlockEntity::new, SpectrumBlocks.PRESERVATION_BLOCK_DETECTOR);
		DEEP_LIGHT = register("deep_light", DeepLightBlockEntity::new, SpectrumBlocks.DEEP_LIGHT_CHISELED_PRESERVATION_STONE);
		PLAYER_TRACKING = register("player_tracking", PlayerTrackerBlockEntity::new, SpectrumBlocks.MANXI, SpectrumBlocks.TREASURE_ITEM_BOWL);

		// All the upgrades
		List<Supplier<? extends Block>> upgradeBlocksList = List.of(SpectrumBlocks.UPGRADE_SPEED,
			SpectrumBlocks.UPGRADE_SPEED2,
			SpectrumBlocks.UPGRADE_SPEED3,
			SpectrumBlocks.UPGRADE_EFFICIENCY,
			SpectrumBlocks.UPGRADE_EFFICIENCY2,
			SpectrumBlocks.UPGRADE_YIELD,
			SpectrumBlocks.UPGRADE_YIELD2,
			SpectrumBlocks.UPGRADE_EXPERIENCE,
			SpectrumBlocks.UPGRADE_EXPERIENCE2
		);
		UPGRADE_BLOCK = register("upgrade_block", UpgradeBlockEntity::new, upgradeBlocksList.toArray(new Supplier[0]));
		
		// All the skulls
		SKULL = register("skull", SpectrumSkullBlockEntity::new, () -> {
			List<Block> skullBlocksList = new ArrayList<>();
			// TODO Avoid this pattern, blocks shouldn't register themselves to a list
			skullBlocksList.addAll(SpectrumSkullBlock.getMobHeads());
			skullBlocksList.addAll(SpectrumWallSkullBlock.getMobWallHeads());
			return skullBlocksList;
		});

		REGISTER.register(bus);
	}

	public static void registerAdditionalTypes(BlockEntityTypeAddBlocksEvent event) {
		event.modify(BlockEntityType.BARREL, SpectrumBlocks.WEEPING_GALA_BARREL.get());
	}
	
	public static void registerClient(FMLClientSetupEvent event) {
		BlockEntityRenderers.register(SpectrumBlockEntities.PEDESTAL.get(), PedestalBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.BOTTOMLESS_BUNDLE.get(), BottomlessBundleBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.HEARTBOUND_CHEST.get(), HeartboundChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.COMPACTING_CHEST.get(), CompactingChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.FABRICATION_CHEST.get(), FabricationChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PRESERVATION_CHEST.get(), SpectrumChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.BLACK_HOLE_CHEST.get(), BlackHoleChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.UPGRADE_BLOCK.get(), UpgradeBlockBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.FUSION_SHRINE.get(), FusionShrineBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.ENCHANTER.get(), EnchanterBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.ITEM_BOWL.get(), ItemBowlBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.ITEM_ROUNDEL.get(), ItemRoundelBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PRESERVATION_ROUNDEL.get(), ItemRoundelBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.SKULL.get(), SpectrumSkullBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.SPIRIT_INSTILLER.get(), SpiritInstillerBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.JADE_VINE_ROOTS.get(), JadeVineRootsBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.CRYSTALLARIEUM.get(), CrystallarieumBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.COLOR_PICKER.get(), ColorPickerBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PRESERVATION_CONTROLLER.get(), PreservationControllerBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PROJECTOR.get(), ProjectorBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.DEEP_LIGHT.get(), DeepLightBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PLAYER_TRACKING.get(), PlayerTrackingBlockEntityRenderer::new);

		BlockEntityRenderers.register(SpectrumBlockEntities.PASTEL_NODE.get(), PastelNodeBlockEntityRenderer::new);
	}
	
}
