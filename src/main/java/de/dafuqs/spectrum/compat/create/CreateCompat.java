package de.dafuqs.spectrum.compat.create;

import com.simibubi.create.api.event.PipeCollisionEvent;
import de.dafuqs.fractal.api.ModifyItemSubGroupEntriesEvent;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.blocks.crystallarieum.SpectrumClusterBlock;
import de.dafuqs.spectrum.blocks.fluid.SpectrumFluidBlock;
import de.dafuqs.spectrum.compat.SpectrumIntegrationPacks;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumItems.IS;
import de.dafuqs.spectrum.registries.client.SpectrumModels;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
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
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.registries.*;
import org.jetbrains.annotations.NotNull;

import static de.dafuqs.spectrum.registries.SpectrumBlocks.blockWithItem;
import static de.dafuqs.spectrum.registries.SpectrumBlocks.cluster;
import static de.dafuqs.spectrum.registries.SpectrumBlocks.simple;
import static de.dafuqs.spectrum.registries.SpectrumItems.item;
import static de.dafuqs.spectrum.registries.SpectrumItems.simple;

public class CreateCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static DeferredBlock<Block> SMALL_ZINC_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_zinc_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.of().pushReaction(PushReaction.DESTROY).destroyTime(1.0f).mapColor(Blocks.LIGHT_GRAY_CONCRETE.defaultMapColor()).requiresCorrectToolForDrops().noOcclusion(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), ModelTemplates.CROSS));
	public static DeferredBlock<Block> LARGE_ZINC_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_zinc_bud", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_ZINC_BUD.get()), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> ZINC_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("zinc_cluster", () -> new SpectrumClusterBlock(BlockBehaviour.Properties.ofFullCopy(SMALL_ZINC_BUD.get()), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static DeferredBlock<Block> PURE_ZINC_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_zinc_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.IRON_BLOCK)), InkColors.BROWN)));
	
	public static Item PURE_ZINC = SpectrumItems.register(simple(item("pure_zinc", new Item(IS.of()), InkColors.BROWN)));
	
	@Override
	public void register() {
		NeoForge.EVENT_BUS.addListener(CreateCompat::addEntries);
		NeoForge.EVENT_BUS.addListener(CreateCompat::onPipeSpillCollision);
		NeoForge.EVENT_BUS.addListener(CreateCompat::onPipeFlowCollision);
	}

	private static void addEntries(ModifyItemSubGroupEntriesEvent event) {
		var entries = event.getEntries();
		entries.accept(PURE_ZINC);
		entries.accept(SMALL_ZINC_BUD);
		entries.accept(LARGE_ZINC_BUD);
		entries.accept(ZINC_CLUSTER);
		entries.accept(PURE_ZINC_BLOCK);
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
	private static BlockState handleBidirectionalCollision(Level world, @NotNull Fluid firstFluid, @NotNull Fluid secondFluid) {
		final FluidState firstState = firstFluid.defaultFluidState();
		final FluidState secondState = secondFluid.defaultFluidState();
		
		// Handle fluid 1
		final BlockState result = spectrumFluidCollision(world, firstState, secondState);
		if (result != null) return result;
		
		// Handle fluid 2
		return spectrumFluidCollision(world, secondState, firstState);
	}
	
	private static BlockState spectrumFluidCollision(Level world, FluidState state, FluidState otherState) {
		if (state.createLegacyBlock().getBlock() instanceof SpectrumFluidBlock spectrumFluid)
			return spectrumFluid.handleFluidCollision(world, state, otherState);
		return null;
	}
	
	@Override
	@OnlyIn(Dist.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
	
}
