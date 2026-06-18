package earth.terrarium.pastel.blocks;

import earth.terrarium.pastel.particle.PastelParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EnergeticMoteBlock extends Block {

    public EnergeticMoteBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected boolean isSignalSource(BlockState state) {
        return true;
    }

    @Override
    protected int getSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 15;
    }

    @Override
    protected int getDirectSignal(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 15;
    }

    @Override
    protected void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.removeBlock(pos, false);
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        if (world.isClientSide) {
            world
                .addAlwaysVisibleParticle(
                    PastelParticleTypes.SHIMMERSTONE_SPARKLE_SMALL,
                    (double) pos.getX() + 0.2 + random.nextFloat() * 0.6,
                    (double) pos.getY() + 0.1 + random.nextFloat() * 0.6,
                    (double) pos.getZ() + 0.2 + random.nextFloat() * 0.6,
                    0.0D,
                    0.03D,
                    0.0D
                );
        }
    }
}
