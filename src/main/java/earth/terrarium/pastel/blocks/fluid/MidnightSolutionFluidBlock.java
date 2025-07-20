package earth.terrarium.pastel.blocks.fluid;

import earth.terrarium.pastel.blocks.decay.BlackMateriaBlock;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.FullChunkStatus;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MidnightSolutionFluidBlock extends PastelFluidBlock {

    public static final BlockState SPREAD_BLOCKSTATE = PastelBlocks.BLACK_MATERIA.get()
                                                                                 .defaultBlockState()
                                                                                 .setValue(BlackMateriaBlock.AGE, 0);

    public MidnightSolutionFluidBlock(
        PastelFluid fluid, BlockState ultrawarmReplacementBlockState, Properties settings) {
        super(fluid, ultrawarmReplacementBlockState, settings);
    }

//	@Override
//	public MapCodec<? extends MidnightSolutionFluidBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

    @Override
    public SimpleParticleType getSplashParticle() {
        return PastelParticleTypes.MIDNIGHT_SOLUTION_SPLASH;
    }

    @Override
    public Tuple<SimpleParticleType, SimpleParticleType> getFishingParticles() {
        return new Tuple<>(PastelParticleTypes.MIDNIGHT_SOLUTION_SPLASH, PastelParticleTypes.MIDNIGHT_SOLUTION_FISHING);
    }

    public static boolean tryConvertNeighbor(@NotNull Level world, BlockPos fromPos) {
        FluidState fluidState = world.getFluidState(fromPos);
        if (!fluidState.isEmpty() && fluidState.is(PastelFluidTags.MIDNIGHT_SOLUTION_CONVERTED)) {
            world.setBlockAndUpdate(
                fromPos, PastelBlocks.MIDNIGHT_SOLUTION.get()
                                                       .defaultBlockState()
            );
            fizz(world, fromPos);
            return true;
        }
        return false;
    }

    public static void fizz(@NotNull LevelAccessor world, BlockPos pos) {
        world.levelEvent(LevelEvent.LAVA_FIZZ, pos, 0);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        if (!world.getBlockState(pos.above())
                  .isRedstoneConductor(world, pos.above()) && random.nextFloat() < 0.03F) {
            world.addParticle(
                PastelParticleTypes.VOID_FOG, pos.getX() + random.nextDouble(), pos.getY() + 1,
                pos.getZ() + random.nextDouble(), 0, random.nextDouble() * 0.1, 0
            );
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public boolean shouldSpreadLiquid(Level world, BlockPos pos, BlockState state) {
        // Shouldn't happen but check anyway
        // If it IS true then do nothing, since no interaction can take place at this position
        final FluidState fluidState = state.getFluidState();
        if (fluidState == null || fluidState.isEmpty()) return true;

        for (Direction direction : Direction.values()) {
            BlockPos neighborPos = pos.relative(direction);
            FluidState neighborFluidState = world.getFluidState(neighborPos);

            // Do nothing if neighbor fluid state is empty. [matters for both collision and spread]
            if (neighborFluidState == null || neighborFluidState.isEmpty()) continue;

            // Fluid collision interaction
            final BlockState setState = handleFluidCollision(world, fluidState, neighborFluidState);
            if (setState != null) {
                fireExtinguishEvent(world, pos);
                world.setBlockAndUpdate(pos, setState);
                return false;
            }

            // World interaction
            boolean isNeighborFluidBlock = world.getBlockState(neighborPos)
                                                .getBlock() instanceof LiquidBlock;
            // spread to the fluid
            boolean doesTickEntities = world.getChunkAt(pos)
                                            .getFullStatus()
                                            .isOrAfter(FullChunkStatus.ENTITY_TICKING);
            if (!neighborFluidState.isEmpty() && doesTickEntities) {
                if (!isNeighborFluidBlock) {
                    world.setBlockAndUpdate(pos, SPREAD_BLOCKSTATE);
                    fireExtinguishEvent(world, pos);
                } else {
                    if (!neighborFluidState.is(this.fluid) && !neighborFluidState.is(
                        PastelFluidTags.MIDNIGHT_SOLUTION_CONVERTED) && !world.getBlockState(neighborPos)
                                                                              .is(this)) {
                        world.setBlockAndUpdate(pos, SPREAD_BLOCKSTATE);
                        fireExtinguishEvent(world, neighborPos);
                    }
                }
            }
        }
        return true;
    }

    public @Nullable BlockState handleFluidCollision(
        Level world, @NotNull FluidState state, @NotNull FluidState otherState) {
        if (otherState.is(FluidTags.LAVA)) return Blocks.TERRACOTTA.defaultBlockState();
        return null;
    }

    @Override
    public @Nullable PathType getBlockPathType(BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob) {
        return PathType.DAMAGE_OTHER;
    }

    @Override
    public @Nullable PathType getAdjacentBlockPathType(
        BlockState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, PathType originalType) {
        return PathType.DAMAGE_OTHER;
    }
}
