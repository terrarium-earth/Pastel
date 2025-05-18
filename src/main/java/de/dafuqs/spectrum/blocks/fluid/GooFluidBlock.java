package de.dafuqs.spectrum.blocks.fluid;

import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class GooFluidBlock extends SpectrumFluidBlock {
	
	public GooFluidBlock(SpectrumFluid fluid, BlockState ultrawarmReplacementBlockState, Properties settings) {
		super(fluid, ultrawarmReplacementBlockState, settings);
	}

//	@Override
//	public MapCodec<? extends MudFluidBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	
	@Override
	public SimpleParticleType getSplashParticle() {
		return SpectrumParticleTypes.GOO_SPLASH;
	}
	
	@Override
	public Tuple<SimpleParticleType, SimpleParticleType> getFishingParticles() {
		return new Tuple<>(SpectrumParticleTypes.GOO_POP, SpectrumParticleTypes.GOO_FISHING);
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);
		if (!world.getBlockState(pos.above()).isRedstoneConductor(world, pos.above()) && random.nextFloat() < 0.03F) {
			world.addParticle(SpectrumParticleTypes.GOO_POP, pos.getX() + random.nextDouble(), pos.getY() + 1, pos.getZ() + random.nextDouble(), 0, random.nextDouble() * 0.1, 0);
		}
	}
	
	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return true;
	}

	public @Nullable BlockState handleFluidCollision(Level world, @NotNull FluidState state, @NotNull FluidState otherState) {
		if (otherState.is(FluidTags.WATER)) {
			return Blocks.DIRT.defaultBlockState();
		}
		if (otherState.is(FluidTags.LAVA)) {
			return Blocks.MUD.defaultBlockState();
		}
		return null;
	}
	
}
