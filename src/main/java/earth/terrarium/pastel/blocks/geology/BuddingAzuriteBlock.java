package earth.terrarium.pastel.blocks.geology;

import earth.terrarium.pastel.api.block.WardDisruptableBlock;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class BuddingAzuriteBlock extends Block implements WardDisruptableBlock {

    public BuddingAzuriteBlock(
        Properties settings
    ) {
        super(settings);
    }

    @Override
    public float defaultDestroyTime() {
        return -1;
    }

    @Override
    protected float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
        return 0;
    }

    // we do a little :STASIS:
    @Override
    public void onWardDisrupt(BlockPos pos, BlockState state, Level level, Entity trigger) {
        if (level.isClientSide()) return;
        level.setBlockAndUpdate(pos, Blocks.DEEPSLATE.defaultBlockState());
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = UPDATE_SHAPE_ORDER[random.nextInt(UPDATE_SHAPE_ORDER.length)];
            BlockPos blockPos = pos.relative(direction);
            BlockState blockState = level.getBlockState(blockPos);
            if (level.getBlockState(blockPos)
                     .canBeReplaced()) {
                level.setBlockAndUpdate(
                    blockPos, PastelBlocks.AZURE_OUTCROP.get()
                                                        .defaultBlockState()
                                                        .setValue(AmethystClusterBlock.FACING, direction)
                );
            } else if (blockState.is(PastelBlocks.AZURE_OUTCROP)) {
                var stage = AzuriteOutcropBlock.SpirePart.closerToBase(
                    blockState.getValue(AzuriteOutcropBlock.SPIRE_PART));
                if (stage != null) {
                    level.setBlock(
                        blockPos, blockState.setValue(AzuriteOutcropBlock.SPIRE_PART, stage),
                        Block.UPDATE_SUPPRESS_DROPS |
                        Block.UPDATE_ALL
                    );
                    level.updateNeighborsAt(pos, this);
                }
            }
        }
    }
}
