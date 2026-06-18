package earth.terrarium.pastel.blocks.statues;

import com.mojang.serialization.MapCodec;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GrotesqueBlock extends HorizontalDirectionalBlock {

    private final VoxelShape shape;

    protected final Component tooltipText;

    public GrotesqueBlock(Properties settings, double width, double height, String tooltipKey) {
        super(settings);
        tooltipText = Component
            .translatable(tooltipKey)
            .withStyle(ChatFormatting.GRAY);
        var min = (16 - width) / 2;
        var max = width + min;
        shape = Block.box(min, 0, min, max, height, max);
    }

    @Override
    public MapCodec<? extends GrotesqueBlock> codec() {
        //TODO: Make the codec
        return null;
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this
            .defaultBlockState()
            .setValue(
                FACING,
                ctx
                    .getHorizontalDirection()
                    .getOpposite()
            );
    }

    @Override
    public void appendHoverText(
        ItemStack stack,
        Item.TooltipContext context,
        List<Component> tooltip,
        TooltipFlag type
    ) {
        super.appendHoverText(stack, context, tooltip, type);
        tooltip.add(tooltipText);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
