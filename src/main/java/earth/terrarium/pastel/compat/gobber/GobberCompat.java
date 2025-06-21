package earth.terrarium.pastel.compat.gobber;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.crystallarieum.PastelClusterBlock;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelItems.IS;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.*;

import static earth.terrarium.pastel.registries.PastelBlocks.blockWithItem;
import static earth.terrarium.pastel.registries.PastelBlocks.cluster;
import static earth.terrarium.pastel.registries.PastelBlocks.simple;
import static earth.terrarium.pastel.registries.PastelItems.item;
import static earth.terrarium.pastel.registries.PastelItems.simple;

public class GobberCompat extends PastelIntegrationPacks.ModIntegrationPack {
	
	public static DeferredBlock<Block> SMALL_GLOBETTE_BUD = PastelBlocks.register(cluster(blockWithItem("small_globette_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.BLUE_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), PastelClusterBlock.GrowthStage.SMALL), InkColors.BLUE), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_GLOBETTE_BUD = PastelBlocks.register(cluster(blockWithItem("large_globette_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_BUD.get()), PastelClusterBlock.GrowthStage.LARGE), InkColors.BLUE), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> GLOBETTE_CLUSTER = PastelBlocks.register(cluster(blockWithItem("globette_cluster", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER), InkColors.BLUE), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> SMALL_GLOBETTE_NETHER_BUD = PastelBlocks.register(cluster(blockWithItem("small_globette_nether_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.RED_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), PastelClusterBlock.GrowthStage.SMALL), InkColors.RED), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_GLOBETTE_NETHER_BUD = PastelBlocks.register(cluster(blockWithItem("large_globette_nether_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_NETHER_BUD.get()), PastelClusterBlock.GrowthStage.LARGE), InkColors.RED), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> GLOBETTE_NETHER_CLUSTER = PastelBlocks.register(cluster(blockWithItem("globette_nether_cluster", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_NETHER_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER), InkColors.RED), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> SMALL_GLOBETTE_END_BUD = PastelBlocks.register(cluster(blockWithItem("small_globette_end_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.GREEN_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), PastelClusterBlock.GrowthStage.SMALL), InkColors.GREEN), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_GLOBETTE_END_BUD = PastelBlocks.register(cluster(blockWithItem("large_globette_end_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_END_BUD.get()), PastelClusterBlock.GrowthStage.LARGE), InkColors.GREEN), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> GLOBETTE_END_CLUSTER = PastelBlocks.register(cluster(blockWithItem("globette_end_cluster", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_GLOBETTE_END_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER), InkColors.GREEN), PastelModels.CRYSTALLARIEUM_FARMABLE));
	
	public static DeferredBlock<Block> PURE_GLOBETTE_BLOCK = PastelBlocks.register(simple(blockWithItem("pure_globette_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)), InkColors.BLUE)));
	public static DeferredBlock<Block> PURE_GLOBETTE_NETHER_BLOCK = PastelBlocks.register(simple(blockWithItem("pure_globette_nether_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)), InkColors.RED)));
	public static DeferredBlock<Block> PURE_GLOBETTE_END_BLOCK = PastelBlocks.register(simple(blockWithItem("pure_globette_end_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK)), InkColors.GREEN)));
	
	public static DeferredItem<Item> PURE_GLOBETTE = PastelItems.register(simple(item("pure_globette", () -> new Item(IS.of()), InkColors.BLUE)));
	public static DeferredItem<Item> PURE_GLOBETTE_NETHER = PastelItems.register(simple(item("pure_globette_nether", () -> new Item(IS.of()), InkColors.RED)));
	public static DeferredItem<Item> PURE_GLOBETTE_END = PastelItems.register(simple(item("pure_globette_end", () -> new Item(IS.of()), InkColors.GREEN)));
	
	@Override
	public void register() {}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerClient() {
		PastelBlocks.CLIENT_REGISTRAR.flush();
	}
}
