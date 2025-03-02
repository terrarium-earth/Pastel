package de.dafuqs.spectrum.compat.create;

import com.simibubi.create.api.event.*;
import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.item_group.*;
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
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import static de.dafuqs.spectrum.registries.SpectrumBlocks.*;

public class CreateCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	public static Block SMALL_ZINC_BUD = SpectrumBlocks.register(cluster(blockWithItem("small_zinc_bud", new SpectrumClusterBlock(AbstractBlock.Settings.create().pistonBehavior(PistonBehavior.DESTROY).hardness(1.0f).mapColor(Blocks.LIGHT_GRAY_CONCRETE.getDefaultMapColor()).requiresTool().nonOpaque(), SpectrumClusterBlock.GrowthStage.SMALL), InkColors.BROWN), Models.CROSS));
	public static Block LARGE_ZINC_BUD = SpectrumBlocks.register(cluster(blockWithItem("large_zinc_bud", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_ZINC_BUD), SpectrumClusterBlock.GrowthStage.LARGE), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block ZINC_CLUSTER = SpectrumBlocks.register(cluster(blockWithItem("zinc_cluster", new SpectrumClusterBlock(AbstractBlock.Settings.copy(SMALL_ZINC_BUD), SpectrumClusterBlock.GrowthStage.CLUSTER), InkColors.BROWN), SpectrumModels.CRYSTALLARIEUM_FARMABLE));
	public static Block PURE_ZINC_BLOCK = SpectrumBlocks.register(simple(blockWithItem("pure_zinc_block", new Block(AbstractBlock.Settings.copy(Blocks.IRON_BLOCK)), InkColors.BROWN)));
	
	public static Item PURE_ZINC = SpectrumItems.registerDeferred("pure_zinc", new Item(IS.of()), InkColors.BROWN);
	
	@Override
	public void register() {
		SpectrumItems.ITEM_REGISTRAR.flush();
		SpectrumBlocks.COMMON_REGISTRAR.flush();
		
		ItemSubGroupEvents.modifyEntriesEvent(ItemGroupIDs.SUBTAB_PURE_RESOURCES).register(entries -> {
			entries.add(PURE_ZINC);
			entries.add(SMALL_ZINC_BUD);
			entries.add(LARGE_ZINC_BUD);
			entries.add(ZINC_CLUSTER);
			entries.add(PURE_ZINC_BLOCK);
		});
		
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
