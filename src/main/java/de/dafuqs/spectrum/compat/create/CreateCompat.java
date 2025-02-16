package de.dafuqs.spectrum.compat.create;

import com.simibubi.create.api.event.*;
import de.dafuqs.spectrum.blocks.crystallarieum.*;
import de.dafuqs.spectrum.blocks.fluid.*;
import de.dafuqs.spectrum.compat.*;
import de.dafuqs.spectrum.registries.*;
import de.dafuqs.spectrum.registries.SpectrumItems.*;
import de.dafuqs.spectrum.registries.client.*;
import net.fabricmc.api.*;
import net.minecraft.block.*;
import net.minecraft.block.piston.*;
import net.minecraft.data.client.*;
import net.minecraft.fluid.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class CreateCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Block SMALL_ZINC_BUD = SpectrumBlocks.registerClusterBlock("small_zinc_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.LIGHT_GRAY_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), IS.DEFAULT, Models.CROSS, DyeColor.BROWN);
	public static Block LARGE_ZINC_BUD = SpectrumBlocks.registerClusterBlock("large_zinc_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_ZINC_BUD), SpectrumClusterBlock.GrowthStage.LARGE), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.BROWN);
	public static Block ZINC_CLUSTER = SpectrumBlocks.registerClusterBlock("zinc_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_ZINC_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), IS.DEFAULT, SpectrumModels.CRYSTALLARIEUM_FARMABLE, DyeColor.BROWN);
	public static Block PURE_ZINC_BLOCK;
	
	public static Item PURE_ZINC = SpectrumItems.registerDeferred("pure_zinc", new Item(IS.of()), DyeColor.BROWN);
	
	@Override
	public void register() {
		PURE_ZINC_BLOCK = new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK));
		SpectrumBlocks.registerBlockWithItem("pure_zinc_block", PURE_ZINC_BLOCK, IS.of(), DyeColor.BROWN);
		
		SpectrumItems.REGISTRAR.flush();
		SpectrumBlocks.COMMON_REGISTRAR.flush();
		
		PipeCollisionEvent.FLOW.register(event -> {
			final BlockState result = handleBidirectionalCollision(event.getLevel(), event.getFirstFluid(), event.getSecondFluid());
			if (result != null) event.setState(result);
		});
		
		PipeCollisionEvent.SPILL.register(event -> {
			final BlockState result = handleBidirectionalCollision(event.getLevel(), event.getPipeFluid(), event.getWorldFluid());
			if (result != null) event.setState(result);
		});
	}
	
	// NOTE: firstFluid and secondFluid are assumed to be not null without checking,
	// since the default Create event handlers for pipe collisions would throw a NullPointerException otherwise.
	private BlockState handleBidirectionalCollision(World world, @NotNull Fluid firstFluid, @NotNull Fluid secondFluid) {
		final FluidState firstState = firstFluid.getDefaultState();
		final FluidState secondState = secondFluid.getDefaultState();
		
		// Handle fluid 1
		final BlockState result = spectrumFluidCollision(world, firstState, secondState);
		if (result != null) return result;
		
		// Handle fluid 2
		return spectrumFluidCollision(world, secondState, firstState);
	}
	
	private BlockState spectrumFluidCollision(World world, FluidState state, FluidState otherState) {
		if (state.getBlockState().getBlock() instanceof SpectrumFluidBlock spectrumFluid)
			return spectrumFluid.handleFluidCollision(world, state, otherState);
		return null;
	}
	
	@Override
	@Environment(EnvType.CLIENT)
	public void registerClient() {
		SpectrumBlocks.CLIENT_REGISTRAR.flush();
	}
	
}
