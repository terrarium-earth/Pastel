package earth.terrarium.pastel.blocks.imbrifer.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.blocks.SpreadableFloraBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class MossBallBlock extends SpreadableFloraBlock {

    public static final MapCodec<MossBallBlock> CODEC = simpleCodec(MossBallBlock::new);

    private static final VoxelShape SHAPE = MossBallBlock.box(3.5, 0, 3.5, 12.5, 9, 12.5);

    public MossBallBlock(Properties settings) {
        super(3, settings);
    }

//    @Override
//    public MapCodec<? extends MossBallBlock> getCodec() {
//        //TODO: Make the codec
//        return CODEC;
//    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        super.fallOn(world, state, pos, entity, fallDistance / 2F);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(world, pos);
        return SHAPE.move(vec3d.x, vec3d.y, vec3d.z);
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.2F;
    }

    @Override
    public float getMaxVerticalOffset() {
        return 0.125F;
    }

    @Override
    public boolean isPathfindable(BlockState state, PathComputationType type) {
        return true;
    }
}
