package de.dafuqs.spectrum.compat.gobber;

import de.dafuqs.fractal.api.ModifyItemSubGroupEntriesEvent;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.blocks.crystallarieum.SpectrumClusterBlock;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumItems.IS;
import de.dafuqs.spectrum.registries.client.SpectrumModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.registries.*;

import static de.dafuqs.spectrum.registries.SpectrumBlocks.blockWithItem;
import static de.dafuqs.spectrum.registries.SpectrumBlocks.cluster;
import static de.dafuqs.spectrum.registries.SpectrumBlocks.simple;
import static de.dafuqs.spectrum.registries.SpectrumItems.item;
import static de.dafuqs.spectrum.registries.SpectrumItems.simple;

public class GobberCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static DeferredBlock<Block> SMALL_GLOBETTE_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_globette_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.BLUE_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_GLOBETTE_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_globette_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> GLOBETTE_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("globette_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BLUE), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> SMALL_GLOBETTE_NETHER_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_globette_nether_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.RED_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_GLOBETTE_NETHER_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_globette_nether_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_NETHER_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> GLOBETTE_NETHER_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("globette_nether_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_NETHER_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.RED), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> SMALL_GLOBETTE_END_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_globette_end_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.GREEN_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_GLOBETTE_END_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_globette_end_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_END_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> GLOBETTE_END_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("globette_end_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_END_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.GREEN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	
	public static DeferredBlock<Block> PURE_GLOBETTE_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_globette_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)), InkColors.BLUE)));
	public static DeferredBlock<Block> PURE_GLOBETTE_NETHER_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_globette_nether_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)), InkColors.RED)));
	public static DeferredBlock<Block> PURE_GLOBETTE_END_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_globette_end_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)), InkColors.GREEN)));
	
	public static DeferredItem<Item> PURE_GLOBETTE = SpectrumItems.register(simple(item("pure_globette", () -> new Item(IS.of()), InkColors.BLUE)));
	public static DeferredItem<Item> PURE_GLOBETTE_NETHER = SpectrumItems.register(simple(item("pure_globette_nether", () -> new Item(IS.of()), InkColors.RED)));
	public static DeferredItem<Item> PURE_GLOBETTE_END = SpectrumItems.register(simple(item("pure_globette_end", () -> new Item(IS.of()), InkColors.GREEN)));
	
	@Override
	public void register() {
		NeoForge.EVENT_BUS.addListener(GobberCompat::addEntries);
	}

	private static void addEntries(ModifyItemSubGroupEntriesEvent event) {
		var entries = event.getEntries();
		entries.accept(PURE_GLOBETTE);
		entries.accept(SMALL_GLOBETTE_BUD);
		entries.accept(LARGE_GLOBETTE_BUD);
		entries.accept(GLOBETTE_CLUSTER);
		entries.accept(PURE_GLOBETTE_BLOCK);

		entries.accept(PURE_GLOBETTE_NETHER);
		entries.accept(SMALL_GLOBETTE_NETHER_BUD);
		entries.accept(LARGE_GLOBETTE_NETHER_BUD);
		entries.accept(GLOBETTE_NETHER_CLUSTER);
		entries.accept(PURE_GLOBETTE_NETHER_BLOCK);

		entries.accept(PURE_GLOBETTE_END);
		entries.accept(SMALL_GLOBETTE_END_BUD);
		entries.accept(LARGE_GLOBETTE_END_BUD);
		entries.accept(GLOBETTE_END_CLUSTER);
		entries.accept(PURE_GLOBETTE_END_BLOCK);
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
}
