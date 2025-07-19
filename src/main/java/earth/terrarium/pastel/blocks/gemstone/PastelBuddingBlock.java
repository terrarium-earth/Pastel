package earth.terrarium.pastel.blocks.gemstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;

public class PastelBuddingBlock extends PastelGemstoneBlock {

    private static final Direction[] DIRECTIONS = Direction.values();
    private final Block smallBlock;
    private final Block mediumBlock;
    private final Block largeBlock;
    private final Block clusterBlock;

    public PastelBuddingBlock(
        Properties settings, Block smallBlock, Block mediumBlock, Block largeBlock, Block clusterBlock,
        SoundEvent hitSoundEvent, SoundEvent chimeSoundEvent
    ) {
        super(settings, hitSoundEvent, chimeSoundEvent);

        this.smallBlock = smallBlock;
        this.mediumBlock = mediumBlock;
        this.largeBlock = largeBlock;
        this.clusterBlock = clusterBlock;
    }

    @Override
    public MapCodec<? extends PastelBuddingBlock> codec() {
        //TODO: Make the codec
        return null;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = UPDATE_SHAPE_ORDER[random.nextInt(UPDATE_SHAPE_ORDER.length)];
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (BuddingAmethystBlock.canClusterGrowAtState(blockState)) {
                block = smallBlock;
            } else if (blockState.is(smallBlock) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = mediumBlock;
            } else if (blockState.is(mediumBlock) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = largeBlock;
            } else if (blockState.is(largeBlock) && blockState.getValue(AmethystClusterBlock.FACING) == direction) {
                block = clusterBlock;
            }

            if (block != null) {
                world.setBlockAndUpdate(
                    blockPos, block.defaultBlockState()
                                   .setValue(AmethystClusterBlock.FACING, direction)
                                   .setValue(
                                       AmethystClusterBlock.WATERLOGGED, blockState.getFluidState()
                                                                                   .getType() == Fluids.WATER
                                   )
                );
            }
        }
    }

}
