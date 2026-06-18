package earth.terrarium.pastel.blocks.gravity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.entity.entity.FloatBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;

public class FloatBlock extends FallingBlock {

    public static final MapCodec<FloatBlock> CODEC = RecordCodecBuilder
        .mapCodec(
            i -> i
                .group(
                    propertiesCodec(),
                    Codec.FLOAT
                        .fieldOf(
                            "gravity_mod"
                        )
                        .forGetter(FloatBlock::getGravityMod)
                )
                .apply(i, FloatBlock::new)
        );

    private final float gravityMod;

    public FloatBlock(Properties settings, float gravityMod) {
        super(settings);
        this.gravityMod = gravityMod;
    }

    @Override
    public MapCodec<? extends FloatBlock> codec() {
        return CODEC;
    }

    public float getGravityMod() {
        return gravityMod;
    }

    @Override
    public void onPlace(BlockState state, Level world, BlockPos blockPos, BlockState oldState, boolean notify) {
        world.scheduleTick(blockPos, this, this.getDelayAfterPlace());
    }

    @Override
    public BlockState updateShape(
        BlockState state,
        Direction direction,
        BlockState facingState,
        LevelAccessor world,
        BlockPos blockPos,
        BlockPos facingPos
    ) {
        world.scheduleTick(blockPos, this, this.getDelayAfterPlace());
        return super.updateShape(state, direction, facingState, world, blockPos, facingPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.checkForLaunch(world, pos);
    }

    private void checkForLaunch(Level world, BlockPos pos) {
        if (!world.isClientSide) {
            if (gravityMod == 0) {
                launch(world, pos);
                return;
            }

            BlockPos collisionBlockPos;
            if (gravityMod > 0) {
                collisionBlockPos = pos.above();
            } else {
                collisionBlockPos = pos.below();
            }

            if (world.isEmptyBlock(collisionBlockPos) || isFree(world.getBlockState(collisionBlockPos))) {
                launch(world, pos);
            }
        }
    }

    private static void launch(Level world, BlockPos pos) {
        FloatBlockEntity blockEntity = new FloatBlockEntity(world, pos, world.getBlockState(pos));
        world.addFreshEntity(blockEntity);
    }

}
