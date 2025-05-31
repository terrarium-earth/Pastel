package earth.terrarium.pastel.compat.ae2;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.crystallarieum.SpectrumClusterBlock;
import earth.terrarium.pastel.compat.SpectrumIntegrationPacks;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.registries.SpectrumItems.IS;
import earth.terrarium.pastel.registries.client.SpectrumModels;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.registries.*;

import static earth.terrarium.pastel.registries.SpectrumBlocks.blockWithItem;
import static earth.terrarium.pastel.registries.SpectrumBlocks.cluster;
import static earth.terrarium.pastel.registries.SpectrumBlocks.simple;
import static earth.terrarium.pastel.registries.SpectrumItems.item;
import static earth.terrarium.pastel.registries.SpectrumItems.simple;

public class AE2Compat extends SpectrumIntegrationPacks.ModIntegrationPack {

	public static DeferredBlock<Block> SMALL_CERTUS_QUARTZ_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_certus_quartz_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(MapColor.TERRACOTTA_WHITE).requiresCorrectToolForDrops().noOcclusion(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_CERTUS_QUARTZ_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_certus_quartz_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_CERTUS_QUARTZ_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> CERTUS_QUARTZ_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("certus_quartz_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_CERTUS_QUARTZ_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> SMALL_FLUIX_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_fluix_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.PURPLE_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_FLUIX_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_fluix_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_FLUIX_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> FLUIX_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("fluix_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_FLUIX_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.YELLOW), SpectrumModels.CRYSTALLARIEUM_FARMABLE));

	public static DeferredBlock<Block> PURE_CERTUS_QUARTZ_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_certus_quartz_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.3F).sound(SoundType.GLASS)), InkColors.YELLOW)));
	public static DeferredBlock<Block> PURE_FLUIX_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_fluix_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SAND).strength(0.3F).sound(SoundType.GLASS)), InkColors.YELLOW)));

	public static DeferredItem<Item> PURE_CERTUS_QUARTZ = SpectrumItems.register(simple(item("pure_certus_quartz", () -> new Item(IS.of()), InkColors.YELLOW)));
	public static DeferredItem<Item> PURE_FLUIX = SpectrumItems.register(simple(item("pure_fluix", () -> new Item(IS.of()), InkColors.YELLOW)));

	@Override
	public void register() {
		//NeoForge.EVENT_BUS.addListener(AE2Compat::addEntries); this may cause a crash
	}

	private static void addEntries(BuildCreativeModeTabContentsEvent event) {


		if (!event.getTabKey().location().equals(SpectrumItemGroups.RESOURCES_ID))
			return;

		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), PURE_CERTUS_QUARTZ.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), SMALL_CERTUS_QUARTZ_BUD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), LARGE_CERTUS_QUARTZ_BUD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), CERTUS_QUARTZ_CLUSTER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), PURE_CERTUS_QUARTZ_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), PURE_FLUIX.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), SMALL_FLUIX_BUD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), LARGE_FLUIX_BUD.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), FLUIX_CLUSTER.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
		event.insertAfter(SpectrumBlocks.PURE_QUARTZ_BLOCK.toStack(), PURE_FLUIX_BLOCK.toStack(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}

}
