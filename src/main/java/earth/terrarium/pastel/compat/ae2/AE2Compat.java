package earth.terrarium.pastel.compat.ae2;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.crystallarieum.PastelClusterBlock;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.registries.DeferredBlock;

import static earth.terrarium.pastel.registries.PastelBlocks.blockWithItem;

public class AE2Compat extends PastelIntegrationPacks.ModIntegrationPack {

    public static void generateBlockModels(BlockModelGenerators generators) {
        PastelModelHelper.BLOCK.cluster(generators, SMALL_FLUIX_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, LARGE_FLUIX_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.cluster(generators, FLUIX_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.BLOCK.predefinedItemModel(generators, FLUIX_CLUSTER);
    }

    public static void generateItemModels(ItemModelGenerators generators){
        PastelModelHelper.ITEM.cluster(generators, SMALL_FLUIX_BUD, PastelClusterBlock.GrowthStage.SMALL);
        PastelModelHelper.ITEM.cluster(generators, LARGE_FLUIX_BUD, PastelClusterBlock.GrowthStage.LARGE);
        PastelModelHelper.ITEM.cluster(generators, FLUIX_CLUSTER, PastelClusterBlock.GrowthStage.CLUSTER);
    }

    public static DeferredBlock<Block> SMALL_FLUIX_BUD = PastelBlocks.register(blockWithItem(
        "small_fluix_bud", () -> new PastelClusterBlock(
            BlockBehaviour.Properties.of()
                                     .pushReaction(PushReaction.DESTROY)
                                     .destroyTime(1.0f)
                                     .mapColor(Blocks.PURPLE_CONCRETE.defaultMapColor())
                                     .requiresCorrectToolForDrops()
                                     .noOcclusion(), PastelClusterBlock.GrowthStage.SMALL
        ), InkColors.YELLOW
    ));
    public static DeferredBlock<Block> LARGE_FLUIX_BUD = PastelBlocks.register(blockWithItem(
        "large_fluix_bud",
        () -> new PastelClusterBlock(
            BlockBehaviour.Properties.ofFullCopy(SMALL_FLUIX_BUD.get()),
            PastelClusterBlock.GrowthStage.LARGE
        ), InkColors.YELLOW
    ));
    public static DeferredBlock<Block> FLUIX_CLUSTER = PastelBlocks.register(blockWithItem(
        "fluix_cluster",
        () -> new PastelClusterBlock(
            BlockBehaviour.Properties.ofFullCopy(SMALL_FLUIX_BUD.get()),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), InkColors.YELLOW
    ));

    @Override
    public void register() {
    }

    // todo
    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient() {
    }
}
