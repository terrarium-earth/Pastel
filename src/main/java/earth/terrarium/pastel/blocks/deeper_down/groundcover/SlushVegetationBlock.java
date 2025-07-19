package earth.terrarium.pastel.blocks.deeper_down.groundcover;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.LightEngine;

public class SlushVegetationBlock extends SnowyDirtBlock {

    public SlushVegetationBlock(Properties settings) {
        super(settings);
    }

    public static final MapCodec<SlushVegetationBlock> CODEC = simpleCodec(SlushVegetationBlock::new);

    @Override
    public MapCodec<? extends SlushVegetationBlock> codec() {
        return CODEC;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!canSurvive(state, world, pos)) {
            world.setBlockAndUpdate(
                pos, PastelBlocks.SLUSH.get()
                                       .defaultBlockState()
            );
        }
    }

    private static boolean canSurvive(BlockState state, BlockGetter world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(Blocks.SNOW) && blockState.getValue(SnowLayerBlock.LAYERS) == 1) {
            return true;
        } else if (blockState.getFluidState()
                             .getAmount() == 8) {
            return false;
        } else {
            int light = LightEngine.getLightBlockInto(
                world, state, pos, blockState, blockPos, Direction.UP, blockState.getLightBlock(world, blockPos));
            return light < world.getMaxLightLevel();
        }
    }

}
