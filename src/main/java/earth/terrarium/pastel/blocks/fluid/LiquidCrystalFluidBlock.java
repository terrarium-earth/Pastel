package earth.terrarium.pastel.blocks.fluid;

import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiquidCrystalFluidBlock extends PastelFluidBlock {
	
	public static final int LUMINANCE = 11;
	
	public LiquidCrystalFluidBlock(PastelFluid fluid, BlockState ultrawarmReplacementBlockState, Properties settings) {
		super(fluid, ultrawarmReplacementBlockState, settings);
	}

//	@Override
//	public MapCodec<? extends LiquidCrystalFluidBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	
	@Override
	public SimpleParticleType getSplashParticle() {
		return PastelParticleTypes.LIQUID_CRYSTAL_FISHING;
	}
	
	@Override
	public Tuple<SimpleParticleType, SimpleParticleType> getFishingParticles() {
		return new Tuple<>(PastelParticleTypes.LIQUID_CRYSTAL_SPARKLE, PastelParticleTypes.LIQUID_CRYSTAL_FISHING);
	}
	
	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return true;
	}
	
	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		super.animateTick(state, world, pos, random);
		if (random.nextFloat() < 0.10F) {
			world.addParticle(PastelParticleTypes.LIQUID_CRYSTAL_SPARKLE, pos.getX() + random.nextDouble(), pos.getY() + random.nextDouble(), pos.getZ() + random.nextDouble(), 0, random.nextDouble() * 0.1, 0);
		}
	}

	public @Nullable BlockState handleFluidCollision(Level world, @NotNull FluidState state, @NotNull FluidState otherState) {
		if (otherState.is(FluidTags.WATER)) {
			return state.isSource() ? PastelBlocks.FROSTBITE_CRYSTAL.get().defaultBlockState() : Blocks.CALCITE.defaultBlockState();
		}
		else if (otherState.is(FluidTags.LAVA)) {
			return state.isSource() ? PastelBlocks.BLAZING_CRYSTAL.get().defaultBlockState() : Blocks.COBBLED_DEEPSLATE.defaultBlockState();
		} else if (otherState.is(PastelFluidTags.HUMUS)) {
			return Blocks.CLAY.defaultBlockState();
		}
		return null;
	}

	@Override
	public @Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
		return PathType.WATER;
	}

	@Override
	public @Nullable PathType getAdjacentBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, PathType originalType) {
		return PathType.WATER_BORDER;
	}
}
