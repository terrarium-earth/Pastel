package earth.terrarium.pastel.blocks.geology;

import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class AzuriteOutcropBlock extends Block {
    public static final EnumProperty<SpirePart> SPIRE_PART = EnumProperty.create("spire_part", SpirePart.class);
    public static final DirectionProperty FACING = AmethystClusterBlock.FACING;

    public AzuriteOutcropBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                                            .setValue(FACING, Direction.UP)
                                            .setValue(SPIRE_PART, SpirePart.TIP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(SPIRE_PART, FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return stateDefinition.any()
                              .setValue(SPIRE_PART, SpirePart.TIP)
                              .setValue(FACING, context.getClickedFace());
    }

    @Override
    protected void neighborChanged(
        BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        var offset = neighborPos.subtract(pos);
        var dir = Direction.fromDelta(offset.getX(), offset.getY(), offset.getZ());
        if (dir == null || !dir.equals(state.getValue(FACING)) && !dir.equals(state.getValue(FACING)
                                                                                   .getOpposite())) return;
        var neighborState = level.getBlockState(neighborPos);
        neighborBlock = neighborState.getBlock();
        var downstream = dir.equals(state.getValue(FACING));
        if (!downstream) {
            if (!state.is(neighborBlock) && !neighborState.is(PastelBlocks.BUDDING_AZURITE)) {
                // the block supporting us has just become something other than us
                level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            } else {
                var supported = neighborState.is(this) ? SpirePart.closerToTip(level.getBlockState(neighborPos)
                                                                                    .getValue(SPIRE_PART)) : null;
                if (supported == null && !neighborState.is(PastelBlocks.BUDDING_AZURITE)) {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                } else if (supported != state.getValue(SPIRE_PART)) {
                    if (supported != null) {
                        level.setBlock(
                            pos, state.setValue(SPIRE_PART, supported), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
                    }
                }
            }
        } else {
            if (!state.is(neighborBlock) && state.getValue(SPIRE_PART)
                                                 .ordinal() < 2) {
                // we're not a tip, and the block we're supporting is no longer one of us
                level.setBlock(
                    pos, state.setValue(SPIRE_PART, SpirePart.TIP), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL);
            } else if (state.is(neighborBlock)) {
                // the block we're supporting has updated while still being one of us
                var part = SpirePart.closerToBase(level.getBlockState(neighborPos)
                                                       .getValue(SPIRE_PART));
                if (part != null) {
                    level.setBlock(
                        pos, state.setValue(SPIRE_PART, part), Block.UPDATE_SUPPRESS_DROPS | Block.UPDATE_ALL
                    );
                    var nextPos = pos.relative(state.getValue(FACING));
                    var nextState = level.getBlockState(nextPos);
                    if (nextState.canBeReplaced()) {
                        level.setBlockAndUpdate(nextPos, state);
                        level.updateNeighborsAt(pos, this);
                    }
                } else {
                    level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
                }
            }
        }
        if (level.getBlockState(pos)
                 .getValue(SPIRE_PART) != SpirePart.TIP && level.getBlockState(pos.relative(state.getValue(FACING)))
                                                                .canBeReplaced()) {
            // we've just done all the update code, and at the end of it we're not a tip but we don't have one—make one!
            var nextPos = pos.relative(state.getValue(FACING));
            var blockState = level.getBlockState(pos);
            var part = SpirePart.closerToTip(blockState
                                                 .getValue(SPIRE_PART));
            if (part != null) {
                level.setBlockAndUpdate(
                    nextPos, defaultBlockState().setValue(FACING, blockState.getValue(FACING))
                                                .setValue(SPIRE_PART, part)
                );
                level.updateNeighborsAt(pos, this);
            }

        }
    }


    public enum SpirePart implements StringRepresentable {
        BASE("base"),
        BODY("body"),
        TIP("tip");

        private final String name;

        SpirePart(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }

        public static SpirePart closerToBase(SpirePart part) {
            switch (part) {
                case TIP -> {
                    return BODY;
                }
                case BODY -> {
                    return BASE;
                }
                case null, default -> {
                    return null;
                }
            }
        }

        public static SpirePart closerToTip(SpirePart part) {
            switch (part) {
                case BASE -> {
                    return BODY;
                }
                case BODY -> {
                    return TIP;
                }
                case null, default -> {
                    return null;
                }
            }
        }
    }
}
