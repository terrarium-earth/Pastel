package earth.terrarium.pastel.compat.create;

import com.simibubi.create.api.event.PipeCollisionEvent;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.crystallarieum.PastelClusterBlock;
import earth.terrarium.pastel.blocks.fluid.PastelFluidBlock;
import earth.terrarium.pastel.compat.PastelIntegrationPacks;
import earth.terrarium.pastel.data.PastelModelHelper;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelItems.IS;
import earth.terrarium.pastel.registries.client.PastelModels;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.NotNull;

import static earth.terrarium.pastel.registries.PastelBlocks.blockWithItem;
import static earth.terrarium.pastel.registries.PastelItems.item;
import static earth.terrarium.pastel.registries.PastelItems.simple;

public class CreateCompat extends PastelIntegrationPacks.ModIntegrationPack {

    public static void generateBlockModels(BlockModelGenerators generators){
        PastelModelHelper.cluster(generators, SMALL_ZINC_BUD, ModelTemplates.CROSS);
        PastelModelHelper.cluster(generators, LARGE_ZINC_BUD, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.cluster(generators, ZINC_CLUSTER, PastelModels.CRYSTALLARIEUM_FARMABLE);
        PastelModelHelper.predefinedItemModel(generators, ZINC_CLUSTER);
        PastelModelHelper.simple(generators, PURE_ZINC_BLOCK);
    }
    public static DeferredBlock<Block> SMALL_ZINC_BUD = PastelBlocks.register(blockWithItem(
        "small_zinc_bud", () -> new PastelClusterBlock(
            BlockBehaviour.Properties.of()
                                     .pushReaction(PushReaction.DESTROY)
                                     .destroyTime(1.0f)
                                     .mapColor(Blocks.LIGHT_GRAY_CONCRETE.defaultMapColor())
                                     .requiresCorrectToolForDrops()
                                     .noOcclusion(), PastelClusterBlock.GrowthStage.SMALL
        ), InkColors.BROWN
    ));
    public static DeferredBlock<Block> LARGE_ZINC_BUD = PastelBlocks.register(blockWithItem(
        "large_zinc_bud",
        () -> new PastelClusterBlock(
            BlockBehaviour.Properties.ofFullCopy(SMALL_ZINC_BUD.get()),
            PastelClusterBlock.GrowthStage.LARGE
        ), InkColors.BROWN
    ));
    public static DeferredBlock<Block> ZINC_CLUSTER = PastelBlocks.register(blockWithItem(
        "zinc_cluster",
        () -> new PastelClusterBlock(
            BlockBehaviour.Properties.ofFullCopy(SMALL_ZINC_BUD.get()),
            PastelClusterBlock.GrowthStage.CLUSTER
        ), InkColors.BROWN
    ));
    public static DeferredBlock<Block> PURE_ZINC_BLOCK = PastelBlocks.register(blockWithItem(
        "pure_zinc_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)),
        InkColors.BROWN
    ));

    public static DeferredItem<Item> PURE_ZINC = PastelItems.register(
        simple(item("pure_zinc", () -> new Item(IS.of()), InkColors.BROWN)));

    @Override
    public void register() {
        NeoForge.EVENT_BUS.addListener(CreateCompat::onPipeSpillCollision);
        NeoForge.EVENT_BUS.addListener(CreateCompat::onPipeFlowCollision);
    }

    private static void onPipeFlowCollision(PipeCollisionEvent.Flow event) {
        var result = handleBidirectionalCollision(event.getLevel(), event.getFirstFluid(), event.getSecondFluid());
        if (result != null)
            event.setState(result);
    }

    private static void onPipeSpillCollision(PipeCollisionEvent.Spill event) {
        var result = handleBidirectionalCollision(event.getLevel(), event.getPipeFluid(), event.getWorldFluid());
        if (result != null)
            event.setState(result);
    }

    // NOTE: firstFluid and secondFluid are assumed to be not null without checking,
    // since the default Create event handlers for pipe collisions would throw a NullPointerException otherwise.
    private static BlockState handleBidirectionalCollision(
        Level world, @NotNull Fluid firstFluid, @NotNull Fluid secondFluid) {
        final FluidState firstState = firstFluid.defaultFluidState();
        final FluidState secondState = secondFluid.defaultFluidState();

        // Handle fluid 1
        final BlockState result = spectrumFluidCollision(world, firstState, secondState);
        if (result != null) return result;

        // Handle fluid 2
        return spectrumFluidCollision(world, secondState, firstState);
    }

    private static BlockState spectrumFluidCollision(Level world, FluidState state, FluidState otherState) {
        if (state.createLegacyBlock()
                 .getBlock() instanceof PastelFluidBlock spectrumFluid)
            return spectrumFluid.handleFluidCollision(world, state, otherState);
        return null;
    }

    // todo
    @Override
    @OnlyIn(Dist.CLIENT)
    public void registerClient() {
//        PastelModelHelper.cutout(SMALL_ZINC_BUD);
//        PastelModelHelper.cutout(LARGE_ZINC_BUD);
//        PastelModelHelper.cutout(ZINC_CLUSTER);
    }

}
