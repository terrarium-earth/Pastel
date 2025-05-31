package earth.terrarium.pastel.compat.gobber;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.crystallarieum.SpectrumClusterBlock;
import earth.terrarium.pastel.compat.SpectrumIntegrationPacks;
import earth.terrarium.pastel.registries.SpectrumBlocks;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumItems.IS;
import earth.terrarium.pastel.registries.client.SpectrumModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.registries.*;

import static earth.terrarium.pastel.registries.SpectrumBlocks.blockWithItem;
import static earth.terrarium.pastel.registries.SpectrumBlocks.cluster;
import static earth.terrarium.pastel.registries.SpectrumBlocks.simple;
import static earth.terrarium.pastel.registries.SpectrumItems.item;
import static earth.terrarium.pastel.registries.SpectrumItems.simple;

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
	public void register() {}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
}
