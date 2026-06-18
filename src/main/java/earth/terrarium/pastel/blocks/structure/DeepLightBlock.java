package earth.terrarium.pastel.blocks.structure;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import org.jetbrains.annotations.Nullable;

public class DeepLightBlock extends HorizontalDirectionalBlock implements EntityBlock {

    public static final MapCodec<DeepLightBlock> CODEC = simpleCodec(DeepLightBlock::new);

    public DeepLightBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends DeepLightBlock> codec() {
        return CODEC;
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this
            .defaultBlockState()
            .setValue(FACING, ctx.getHorizontalDirection());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new DeepLightBlockEntity(pos, state);
    }
}
