package earth.terrarium.pastel.blocks.imbrifer.groundcover;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;

public class SlushBlock extends Block implements BonemealableBlock {

    public static final MapCodec<SlushBlock> CODEC = Block.simpleCodec(SlushBlock::new);

    public SlushBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends SlushBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        if (!world.getBlockState(pos.above()).propagatesSkylightDown(world, pos)) {
            return false;
        }
        for (BlockPos targetPos : BlockPos.betweenClosed(pos.offset(-1, -1, -1), pos.offset(1, 1, 1))) {
            if (world.getBlockState(targetPos).is(PastelBlocks.OVERGROWN_SLUSH.get())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        world.setBlock(pos, PastelBlocks.OVERGROWN_SLUSH.get().defaultBlockState(), Block.UPDATE_ALL);
    }
}