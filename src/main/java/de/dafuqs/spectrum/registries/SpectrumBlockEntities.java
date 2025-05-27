package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.blocks.PlacedItemBlockEntity;
import de.dafuqs.spectrum.blocks.amphora.AmphoraBlockEntity;
import de.dafuqs.spectrum.blocks.block_flooder.BlockFlooderBlockEntity;
import de.dafuqs.spectrum.blocks.bottomless_bundle.BottomlessBundleBlockEntity;
import de.dafuqs.spectrum.blocks.bottomless_bundle.BottomlessBundleBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.chests.BlackHoleChestBlockEntity;
import de.dafuqs.spectrum.blocks.chests.BlackHoleChestBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.chests.CompactingChestBlockEntity;
import de.dafuqs.spectrum.blocks.chests.CompactingChestBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.chests.FabricationChestBlockEntity;
import de.dafuqs.spectrum.blocks.chests.FabricationChestBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.chests.HeartboundChestBlockEntity;
import de.dafuqs.spectrum.blocks.chests.HeartboundChestBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.chests.SpectrumChestBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.cinderhearth.CinderhearthBlockEntity;
import de.dafuqs.spectrum.blocks.crystallarieum.CrystallarieumBlockEntity;
import de.dafuqs.spectrum.blocks.crystallarieum.CrystallarieumBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.decoration.ProjectorBlockEntity;
import de.dafuqs.spectrum.blocks.decoration.ProjectorBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.deeper_down.HummingstoneBlockEntity;
import de.dafuqs.spectrum.blocks.enchanter.EnchanterBlockEntity;
import de.dafuqs.spectrum.blocks.enchanter.EnchanterBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.ender.EnderDropperBlockEntity;
import de.dafuqs.spectrum.blocks.ender.EnderHopperBlockEntity;
import de.dafuqs.spectrum.blocks.energy.ColorPickerBlockEntity;
import de.dafuqs.spectrum.blocks.energy.ColorPickerBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.energy.CrystalApothecaryBlockEntity;
import de.dafuqs.spectrum.blocks.fusion_shrine.FusionShrineBlockEntity;
import de.dafuqs.spectrum.blocks.fusion_shrine.FusionShrineBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.item_bowl.ItemBowlBlockEntity;
import de.dafuqs.spectrum.blocks.item_bowl.ItemBowlBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.item_roundel.ItemRoundelBlockEntity;
import de.dafuqs.spectrum.blocks.item_roundel.ItemRoundelBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.jade_vines.JadeVineRootsBlockEntity;
import de.dafuqs.spectrum.blocks.jade_vines.JadeVineRootsBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.memory.MemoryBlockEntity;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlock;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumSkullBlockEntity;
import de.dafuqs.spectrum.blocks.mob_head.SpectrumWallSkullBlock;
import de.dafuqs.spectrum.blocks.mob_head.client.SpectrumSkullBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.particle_spawner.ParticleSpawnerBlockEntity;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.PastelNodeBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.pedestal.PedestalBlockEntity;
import de.dafuqs.spectrum.blocks.pedestal.PedestalBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.potion_workshop.PotionWorkshopBlockEntity;
import de.dafuqs.spectrum.blocks.present.PresentBlockEntity;
import de.dafuqs.spectrum.blocks.redstone.BlockBreakerBlockEntity;
import de.dafuqs.spectrum.blocks.redstone.BlockPlacerBlockEntity;
import de.dafuqs.spectrum.blocks.redstone.PlayerDetectorBlockEntity;
import de.dafuqs.spectrum.blocks.redstone.RedstoneCalculatorBlockEntity;
import de.dafuqs.spectrum.blocks.redstone.RedstoneTransceiverBlockEntity;
import de.dafuqs.spectrum.blocks.spirit_instiller.SpiritInstillerBlockEntity;
import de.dafuqs.spectrum.blocks.spirit_instiller.SpiritInstillerBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.spirit_sallow.OminousSaplingBlockEntity;
import de.dafuqs.spectrum.blocks.structure.DeepLightBlockEntity;
import de.dafuqs.spectrum.blocks.structure.DeepLightBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.structure.PlayerTrackerBlockEntity;
import de.dafuqs.spectrum.blocks.structure.PlayerTrackingBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.structure.PreservationBlockDetectorBlockEntity;
import de.dafuqs.spectrum.blocks.structure.PreservationControllerBlockEntity;
import de.dafuqs.spectrum.blocks.structure.PreservationControllerBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.structure.PreservationRoundelBlockEntity;
import de.dafuqs.spectrum.blocks.structure.TreasureChestBlockEntity;
import de.dafuqs.spectrum.blocks.titration_barrel.TitrationBarrelBlockEntity;
import de.dafuqs.spectrum.blocks.upgrade.UpgradeBlock;
import de.dafuqs.spectrum.blocks.upgrade.UpgradeBlockBlockEntityRenderer;
import de.dafuqs.spectrum.blocks.upgrade.UpgradeBlockEntity;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.registries.*;

import java.util.ArrayList;
import java.util.List;

public class SpectrumBlockEntities {

	public static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, SpectrumCommon.MOD_ID);

	public static BlockEntityType<OminousSaplingBlockEntity> OMINOUS_SAPLING;
	public static BlockEntityType<PedestalBlockEntity> PEDESTAL;
	public static BlockEntityType<FusionShrineBlockEntity> FUSION_SHRINE;
	public static BlockEntityType<EnchanterBlockEntity> ENCHANTER;

	public static BlockEntityType<ItemBowlBlockEntity> ITEM_BOWL;
	public static BlockEntityType<ItemRoundelBlockEntity> ITEM_ROUNDEL;
	public static BlockEntityType<EnderDropperBlockEntity> ENDER_DROPPER;
	public static BlockEntityType<EnderHopperBlockEntity> ENDER_HOPPER;
	public static BlockEntityType<ParticleSpawnerBlockEntity> PARTICLE_SPAWNER;
	public static BlockEntityType<UpgradeBlockEntity> UPGRADE_BLOCK;
	public static BlockEntityType<SpectrumSkullBlockEntity> SKULL;
	public static BlockEntityType<BottomlessBundleBlockEntity> BOTTOMLESS_BUNDLE;
	public static BlockEntityType<PotionWorkshopBlockEntity> POTION_WORKSHOP;
	public static BlockEntityType<CrystallarieumBlockEntity> CRYSTALLARIEUM;
	public static BlockEntityType<CinderhearthBlockEntity> CINDERHEARTH;
	
	public static BlockEntityType<CrystalApothecaryBlockEntity> CRYSTAL_APOTHECARY;
	public static BlockEntityType<ColorPickerBlockEntity> COLOR_PICKER;
	
	public static BlockEntityType<CompactingChestBlockEntity> COMPACTING_CHEST;
	public static BlockEntityType<FabricationChestBlockEntity> FABRICATION_CHEST;
	public static BlockEntityType<HeartboundChestBlockEntity> HEARTBOUND_CHEST;
	public static BlockEntityType<BlackHoleChestBlockEntity> BLACK_HOLE_CHEST;
	public static BlockEntityType<TreasureChestBlockEntity> PRESERVATION_CHEST;
	public static BlockEntityType<AmphoraBlockEntity> AMPHORA;

	public static BlockEntityType<ProjectorBlockEntity> PROJECTOR;

	public static BlockEntityType<PlayerDetectorBlockEntity> PLAYER_DETECTOR;
	public static BlockEntityType<RedstoneCalculatorBlockEntity> REDSTONE_CALCULATOR;
	public static BlockEntityType<RedstoneTransceiverBlockEntity> REDSTONE_TRANSCEIVER;
	public static BlockEntityType<BlockPlacerBlockEntity> BLOCK_PLACER;
	public static BlockEntityType<BlockBreakerBlockEntity> BLOCK_BREAKER;
	public static BlockEntityType<BlockFlooderBlockEntity> BLOCK_FLOODER;
	public static BlockEntityType<SpiritInstillerBlockEntity> SPIRIT_INSTILLER;
	public static BlockEntityType<MemoryBlockEntity> MEMORY;
	public static BlockEntityType<JadeVineRootsBlockEntity> JADE_VINE_ROOTS;
	public static BlockEntityType<PresentBlockEntity> PRESENT;
	public static BlockEntityType<TitrationBarrelBlockEntity> TITRATION_BARREL;
	public static BlockEntityType<PastelNodeBlockEntity> PASTEL_NODE;
	public static BlockEntityType<HummingstoneBlockEntity> HUMMINGSTONE;
	public static BlockEntityType<PlacedItemBlockEntity> PLACED_ITEM;

	public static BlockEntityType<PreservationControllerBlockEntity> PRESERVATION_CONTROLLER;
	public static BlockEntityType<PreservationRoundelBlockEntity> PRESERVATION_ROUNDEL;
	public static BlockEntityType<PreservationBlockDetectorBlockEntity> PRESERVATION_BLOCK_DETECTOR;
	public static BlockEntityType<DeepLightBlockEntity> DEEP_LIGHT;
	public static BlockEntityType<PlayerTrackerBlockEntity> PLAYER_TRACKING;
	
	private static <T extends BlockEntity> BlockEntityType<T> register(String id, BlockEntityType.BlockEntitySupplier<T> factory, Block... blocks) {
		var type = BlockEntityType.Builder.of(factory, blocks).build(null);
		REGISTER.register(id, () -> type);
		return type;
	}
	
	public static void register(IEventBus bus) {
		OMINOUS_SAPLING = register("ominous_sapling_block_entity", OminousSaplingBlockEntity::new, SpectrumBlocks.OMINOUS_SAPLING.get());
		PEDESTAL = register("pedestal_block_entity", PedestalBlockEntity::new, SpectrumBlocks.PEDESTAL_BASIC_AMETHYST.get(), SpectrumBlocks.PEDESTAL_BASIC_TOPAZ.get(), SpectrumBlocks.PEDESTAL_BASIC_CITRINE.get(), SpectrumBlocks.PEDESTAL_ALL_BASIC.get(), SpectrumBlocks.PEDESTAL_ONYX.get(), SpectrumBlocks.PEDESTAL_MOONSTONE.get());
		FUSION_SHRINE = register("fusion_shrine_block_entity", FusionShrineBlockEntity::new, SpectrumBlocks.FUSION_SHRINE_BASALT.get(), SpectrumBlocks.FUSION_SHRINE_CALCITE.get());
		ENCHANTER = register("enchanter_block_entity", EnchanterBlockEntity::new, SpectrumBlocks.ENCHANTER.get());
		ITEM_BOWL = register("item_bowl_block_entity", ItemBowlBlockEntity::new, SpectrumBlocks.ITEM_BOWL_BASALT.get(), SpectrumBlocks.ITEM_BOWL_CALCITE.get());
		ITEM_ROUNDEL = register("item_roundel", ItemRoundelBlockEntity::new, SpectrumBlocks.ITEM_ROUNDEL.get());
		ENDER_DROPPER = register("ender_dropper", EnderDropperBlockEntity::new, SpectrumBlocks.ENDER_DROPPER.get());
		ENDER_HOPPER = register("ender_hopper", EnderHopperBlockEntity::new, SpectrumBlocks.ENDER_HOPPER.get());
		PARTICLE_SPAWNER = register("particle_spawner", ParticleSpawnerBlockEntity::new, SpectrumBlocks.PARTICLE_SPAWNER.get(), SpectrumBlocks.CREATIVE_PARTICLE_SPAWNER.get());
		COMPACTING_CHEST = register("compacting_chest", CompactingChestBlockEntity::new, SpectrumBlocks.COMPACTING_CHEST.get());
		FABRICATION_CHEST = register("fabrication_chest", FabricationChestBlockEntity::new, SpectrumBlocks.FABRICATION_CHEST.get());
		HEARTBOUND_CHEST = register("heartbound_chest", HeartboundChestBlockEntity::new, SpectrumBlocks.HEARTBOUND_CHEST.get());
		BLACK_HOLE_CHEST = register("black_hole_chest", BlackHoleChestBlockEntity::new, SpectrumBlocks.BLACK_HOLE_CHEST.get());
		PRESERVATION_CHEST = register("preservation_chest", TreasureChestBlockEntity::new, SpectrumBlocks.PRESERVATION_CHEST.get());
		AMPHORA = register("amphora", AmphoraBlockEntity::new, SpectrumBlocks.CHESTNUT_NOXWOOD_AMPHORA.get(), SpectrumBlocks.EBONY_NOXWOOD_AMPHORA.get(), SpectrumBlocks.SLATE_NOXWOOD_AMPHORA.get(), SpectrumBlocks.IVORY_NOXWOOD_AMPHORA.get(), SpectrumBlocks.WEEPING_GALA_AMPHORA.get());
		PROJECTOR = register("projector", ProjectorBlockEntity::new, SpectrumBlocks.PYRITE_PROJECTOR.get());
		PLAYER_DETECTOR = register("player_detector", PlayerDetectorBlockEntity::new, SpectrumBlocks.PLAYER_DETECTOR.get());
		REDSTONE_CALCULATOR = register("redstone_calculator", RedstoneCalculatorBlockEntity::new, SpectrumBlocks.REDSTONE_CALCULATOR.get());
		REDSTONE_TRANSCEIVER = register("redstone_transceiver", RedstoneTransceiverBlockEntity::new, SpectrumBlocks.REDSTONE_TRANSCEIVER.get());
		BLOCK_PLACER = register("block_placer", BlockPlacerBlockEntity::new, SpectrumBlocks.BLOCK_PLACER.get());
		BLOCK_BREAKER = register("block_breaker", BlockBreakerBlockEntity::new, SpectrumBlocks.BLOCK_BREAKER.get());
		BLOCK_FLOODER = register("block_flooder", BlockFlooderBlockEntity::new, SpectrumBlocks.BLOCK_FLOODER.get());
		BOTTOMLESS_BUNDLE = register("bottomless_bundle", BottomlessBundleBlockEntity::new, SpectrumBlocks.BOTTOMLESS_BUNDLE.get());
		POTION_WORKSHOP = register("potion_workshop", PotionWorkshopBlockEntity::new, SpectrumBlocks.POTION_WORKSHOP.get());
		SPIRIT_INSTILLER = register("spirit_instiller", SpiritInstillerBlockEntity::new, SpectrumBlocks.SPIRIT_INSTILLER.get());
		MEMORY = register("memory", MemoryBlockEntity::new, SpectrumBlocks.MEMORY.get());
		JADE_VINE_ROOTS = register("jade_vine_roots", JadeVineRootsBlockEntity::new, SpectrumBlocks.JADE_VINE_ROOTS.get());
		CRYSTALLARIEUM = register("crystallarieum", CrystallarieumBlockEntity::new, SpectrumBlocks.CRYSTALLARIEUM.get());
		CRYSTAL_APOTHECARY = register("crystal_apothecary", CrystalApothecaryBlockEntity::new, SpectrumBlocks.CRYSTAL_APOTHECARY.get());
		COLOR_PICKER = register("color_picker", ColorPickerBlockEntity::new, SpectrumBlocks.COLOR_PICKER.get());
		CINDERHEARTH = register("cinderhearth", CinderhearthBlockEntity::new, SpectrumBlocks.CINDERHEARTH.get());
		PRESENT = register("present", PresentBlockEntity::new, SpectrumBlocks.PRESENT.get());
		TITRATION_BARREL = register("titration_barrel", TitrationBarrelBlockEntity::new, SpectrumBlocks.TITRATION_BARREL.get());
		PASTEL_NODE = register("pastel_node", PastelNodeBlockEntity::new, SpectrumBlocks.CONNECTION_NODE.get(), SpectrumBlocks.PROVIDER_NODE.get(), SpectrumBlocks.STORAGE_NODE.get(), SpectrumBlocks.SENDER_NODE.get(), SpectrumBlocks.GATHER_NODE.get(), SpectrumBlocks.BUFFER_NODE.get());
		HUMMINGSTONE = register("hummingstone", HummingstoneBlockEntity::new, SpectrumBlocks.HUMMINGSTONE.get());
		PLACED_ITEM = register("placed_item", PlacedItemBlockEntity::new, SpectrumBlocks.INCANDESCENT_AMALGAM.get(),
				SpectrumBlocks.COLORFUL_SHOOTING_STAR.get(), SpectrumBlocks.FIERY_SHOOTING_STAR.get(), SpectrumBlocks.GEMSTONE_SHOOTING_STAR.get(), SpectrumBlocks.GLISTERING_SHOOTING_STAR.get(), SpectrumBlocks.PRISTINE_SHOOTING_STAR.get());
		PRESERVATION_CONTROLLER = register("preservation_controller", PreservationControllerBlockEntity::new, SpectrumBlocks.PRESERVATION_CONTROLLER.get());
		PRESERVATION_ROUNDEL = register("preservation_roundel", PreservationRoundelBlockEntity::new, SpectrumBlocks.PRESERVATION_ROUNDEL.get());
		PRESERVATION_BLOCK_DETECTOR = register("preservation_block_detector", PreservationBlockDetectorBlockEntity::new, SpectrumBlocks.PRESERVATION_BLOCK_DETECTOR.get());
		DEEP_LIGHT = register("deep_light", DeepLightBlockEntity::new, SpectrumBlocks.DEEP_LIGHT_CHISELED_PRESERVATION_STONE.get());
		PLAYER_TRACKING = register("player_tracking", PlayerTrackerBlockEntity::new, SpectrumBlocks.MANXI.get(), SpectrumBlocks.TREASURE_ITEM_BOWL.get());

		// All the upgrades
		List<Block> upgradeBlocksList = UpgradeBlock.getUpgradeBlocks();
		Block[] upgradeBlocksArray = new Block[upgradeBlocksList.size()];
		upgradeBlocksArray = upgradeBlocksList.toArray(upgradeBlocksArray);
		UPGRADE_BLOCK = register("upgrade_block", UpgradeBlockEntity::new, upgradeBlocksArray);
		
		// All the skulls
		List<Block> skullBlocksList = new ArrayList<>();
		skullBlocksList.addAll(SpectrumSkullBlock.getMobHeads());
		skullBlocksList.addAll(SpectrumWallSkullBlock.getMobWallHeads());
		
		Block[] skullBlocksArray = new Block[skullBlocksList.size()];
		skullBlocksArray = skullBlocksList.toArray(skullBlocksArray);
		SKULL = register("skull", SpectrumSkullBlockEntity::new, skullBlocksArray);

		REGISTER.register(bus);
	}

	public static void registerAdditionalTypes(BlockEntityTypeAddBlocksEvent event) {
		event.modify(BlockEntityType.BARREL, SpectrumBlocks.WEEPING_GALA_BARREL.get());
	}
	
	public static void registerClient() {
		BlockEntityRenderers.register(SpectrumBlockEntities.PEDESTAL, PedestalBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.BOTTOMLESS_BUNDLE, BottomlessBundleBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.HEARTBOUND_CHEST, HeartboundChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.COMPACTING_CHEST, CompactingChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.FABRICATION_CHEST, FabricationChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PRESERVATION_CHEST, SpectrumChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.BLACK_HOLE_CHEST, BlackHoleChestBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.UPGRADE_BLOCK, UpgradeBlockBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.FUSION_SHRINE, FusionShrineBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.ENCHANTER, EnchanterBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.ITEM_BOWL, ItemBowlBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.ITEM_ROUNDEL, ItemRoundelBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PRESERVATION_ROUNDEL, ItemRoundelBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.SKULL, SpectrumSkullBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.SPIRIT_INSTILLER, SpiritInstillerBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.JADE_VINE_ROOTS, JadeVineRootsBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.CRYSTALLARIEUM, CrystallarieumBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.COLOR_PICKER, ColorPickerBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PRESERVATION_CONTROLLER, PreservationControllerBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PROJECTOR, ProjectorBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.DEEP_LIGHT, DeepLightBlockEntityRenderer::new);
		BlockEntityRenderers.register(SpectrumBlockEntities.PLAYER_TRACKING, PlayerTrackingBlockEntityRenderer::new);

		BlockEntityRenderers.register(SpectrumBlockEntities.PASTEL_NODE, PastelNodeBlockEntityRenderer::new);
	}
	
}
