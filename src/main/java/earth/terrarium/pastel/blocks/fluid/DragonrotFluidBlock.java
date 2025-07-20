package earth.terrarium.pastel.blocks.fluid;

import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelFluidTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DragonrotFluidBlock extends PastelFluidBlock {

    public DragonrotFluidBlock(PastelFluid fluid, BlockState ultrawarmReplacementBlockState, Properties settings) {
        super(fluid, ultrawarmReplacementBlockState, settings);
    }

//	@Override
//	public MapCodec<? extends DragonrotFluidBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

    @Override
    public SimpleParticleType getSplashParticle() {
        return PastelParticleTypes.DRAGONROT;
    }

    @Override
    public Tuple<SimpleParticleType, SimpleParticleType> getFishingParticles() {
        return new Tuple<>(PastelParticleTypes.DRAGONROT, PastelParticleTypes.DRAGONROT_FISHING);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        if (!world.getBlockState(pos.above())
                  .isRedstoneConductor(world, pos.above()) && random.nextFloat() < 0.03F) {
            world.addParticle(
                PastelParticleTypes.DRAGONROT, pos.getX() + random.nextDouble(), pos.getY() + 1,
                pos.getZ() + random.nextDouble(), 0, random.nextDouble() * 0.1, 0
            );
        }
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    public @Nullable BlockState handleFluidCollision(
        Level world, @NotNull FluidState state, @NotNull FluidState otherState) {
        if (otherState.is(FluidTags.WATER)) {
            return PastelBlocks.SLUSH.get()
                                     .defaultBlockState();
        } else if (otherState.is(FluidTags.LAVA)) {
            return Blocks.BLACKSTONE.defaultBlockState();
        } else if (otherState.is(PastelFluidTags.HUMUS)) {
            return Blocks.COARSE_DIRT.defaultBlockState();
        } else if (otherState.is(PastelFluidTags.LIQUID_CRYSTAL)) {
            return PastelBlocks.FLAYED_EARTH.get()
                                            .defaultBlockState();
        } else if (otherState.is(PastelFluidTags.MIDNIGHT_SOLUTION)) {
            return PastelBlocks.HORNSLAKE.get()
                                         .defaultBlockState();
        }
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
