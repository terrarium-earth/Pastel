package earth.terrarium.pastel.compat.ae2;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.crystallarieum.PastelClusterBlock;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.registries.PastelItems.IS;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.world.item.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.*;
import net.neoforged.neoforge.registries.*;

import static earth.terrarium.pastel.registries.PastelBlocks.blockWithItem;
import static earth.terrarium.pastel.registries.PastelBlocks.cluster;
import static earth.terrarium.pastel.registries.PastelBlocks.simple;
import static earth.terrarium.pastel.registries.PastelItems.item;
import static earth.terrarium.pastel.registries.PastelItems.simple;

public class AE2Compat extends PastelIntegrationPacks.ModIntegrationPack {

	public static DeferredBlock<Block> SMALL_FLUIX_BUD = PastelBlocks.register(cluster(blockWithItem("small_fluix_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.PURPLE_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), PastelClusterBlock.GrowthStage.SMALL), InkColors.YELLOW), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> LARGE_FLUIX_BUD = PastelBlocks.register(cluster(blockWithItem("large_fluix_bud", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_FLUIX_BUD.get()), PastelClusterBlock.GrowthStage.LARGE), InkColors.YELLOW), PastelModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> FLUIX_CLUSTER = PastelBlocks.register(cluster(blockWithItem("fluix_cluster", () -> new PastelClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_FLUIX_BUD.get()), PastelClusterBlock.GrowthStage.CLUSTER), InkColors.YELLOW), PastelModels.CRYSTALLARIEUM_FARMABLE));

	@Override
	public void register() {}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerClient() {}
}
