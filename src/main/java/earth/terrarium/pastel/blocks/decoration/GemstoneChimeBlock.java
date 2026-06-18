package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GemstoneChimeBlock extends Block {

    protected static final VoxelShape SHAPE = Shapes
        .or(
            Block.box(5.0D, 3.0D, 5.0D, 11.0D, 13.0D, 11.0D),
            Block.box(7.0D, 13.0D, 7.0D, 9.0D, 16.0D, 9.0D)
        );

    protected final SoundEvent soundEvent;

    protected final ParticleOptions particleEffect;

    public GemstoneChimeBlock(Properties settings, SoundEvent soundEvent, ParticleOptions particleEffect) {
        super(settings);
        this.soundEvent = soundEvent;
        this.particleEffect = particleEffect;
    }

    @Override
    public MapCodec<? extends GemstoneChimeBlock> codec() {
        //TODO: Make the codec
        return null;
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        world
            .addParticle(
                particleEffect,
                pos.getX() + 0.25 + random.nextDouble() * 0.5,
                pos.getY() + 0.15 + random.nextDouble() * 0.5,
                pos.getZ() + 0.25 + random.nextDouble() * 0.5,
                0,
                -0.02 - random.nextDouble() * 0.025,
                0
            );

        if (random.nextFloat() < 0.2) {
            world
                .playLocalSound(
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    soundEvent,
                    SoundSource.BLOCKS,
                    0.7F + random.nextFloat() * 0.4F,
                    0.75F + random.nextFloat() * 0.5F,
                    true
                );
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = Direction.UP;
        return Block.canSupportCenter(world, pos.relative(direction), direction.getOpposite());
    }

    @Override
    public BlockState updateShape(
        BlockState state,
        Direction direction,
        BlockState neighborState,
        LevelAccessor world,
        BlockPos pos,
        BlockPos neighborPos
    ) {
        return direction == Direction.UP && !state.canSurvive(world, pos)
            ? Blocks.AIR.defaultBlockState()
            : super.updateShape(
                state,
                direction,
                neighborState,
                world,
                pos,
                neighborPos
            );
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    public ParticleOptions getParticleEffect() {
        return this.particleEffect;
    }

}
