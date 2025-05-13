package de.dafuqs.spectrum.blocks.fluid;

import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.fluid.*;
import net.minecraft.particle.*;
import net.minecraft.registry.tag.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.math.random.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class DragonrotFluidBlock extends SpectrumFluidBlock {
	
	public DragonrotFluidBlock(SpectrumFluid fluid, BlockState ultrawarmReplacementBlockState, Settings settings) {
		super(fluid, ultrawarmReplacementBlockState, settings);
	}

//	@Override
//	public MapCodec<? extends DragonrotFluidBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

	@Override
	public SimpleParticleType getSplashParticle() {
		return SpectrumParticleTypes.DRAGONROT;
	}

	@Override
	public Pair<SimpleParticleType, SimpleParticleType> getFishingParticles() {
		return new Pair<>(SpectrumParticleTypes.DRAGONROT, SpectrumParticleTypes.DRAGONROT_FISHING);
	}
	
	@Override
	public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
		super.randomDisplayTick(state, world, pos, random);
		if (!world.getBlockState(pos.up()).isSolidBlock(world, pos.up()) && random.nextFloat() < 0.03F) {
			world.addParticle(SpectrumParticleTypes.DRAGONROT, pos.getX() + random.nextDouble(), pos.getY() + 1, pos.getZ() + random.nextDouble(), 0, random.nextDouble() * 0.1, 0);
		}
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}

	public @Nullable BlockState handleFluidCollision(World world, @NotNull FluidState state, @NotNull FluidState otherState) {
		if (otherState.isIn(FluidTags.WATER)) {
			return SpectrumBlocks.SLUSH.getDefaultState();
		} else if (otherState.isIn(FluidTags.LAVA)) {
			return Blocks.BLACKSTONE.getDefaultState();
		} else if (otherState.isIn(SpectrumFluidTags.GOO)) {
			return Blocks.COARSE_DIRT.getDefaultState();
		} else if (otherState.isIn(SpectrumFluidTags.LIQUID_CRYSTAL)) {
			return SpectrumBlocks.FLAYED_EARTH.getDefaultState();
		} else if (otherState.isIn(SpectrumFluidTags.MIDNIGHT_SOLUTION)) {
			return SpectrumBlocks.HORNSLAKE.getDefaultState();
		}
		return null;
	}
	
}
