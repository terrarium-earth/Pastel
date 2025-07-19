package earth.terrarium.pastel.blocks.structure;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.blocks.decoration.PastelFacingBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class PreservationBlockDetectorBlock extends PastelFacingBlock implements EntityBlock, GameMasterBlock {

    public static final MapCodec<PreservationBlockDetectorBlock> CODEC = simpleCodec(
        PreservationBlockDetectorBlock::new);

    public PreservationBlockDetectorBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any()
                                                      .setValue(FACING, Direction.SOUTH));
    }

    @Override
    public MapCodec<? extends PreservationBlockDetectorBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockState updateShape(
        BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos,
        BlockPos neighborPos
    ) {
        if (state.getValue(FACING) == direction && world.getBlockEntity(
            pos) instanceof PreservationBlockDetectorBlockEntity blockEntity) {
            blockEntity.triggerForNeighbor(neighborState);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                   .setValue(
                       FACING, ctx.getNearestLookingDirection()
                                  .getOpposite()
                                  .getOpposite()
                   );
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PreservationBlockDetectorBlockEntity(pos, state);
    }

}
