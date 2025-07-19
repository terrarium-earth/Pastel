package earth.terrarium.pastel.blocks.deeper_down.groundcover;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VariableHeightBlock extends MudBlock {

    private final VoxelShape shape;

    public VariableHeightBlock(Properties settings, int height) {
        super(settings);
        shape = Block.box(0, 0, 0, 16, height, 16);
    }

//    @Override
//    public MapCodec<? extends VariableHeightBlock> getCodec() {
//        //TODO: Make the codec
//        return CODEC;
//    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return shape;
    }

}
