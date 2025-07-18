package earth.terrarium.pastel.blocks.deeper_down.groundcover;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class AshBlock extends Block {

    public static final MapCodec<AshBlock> CODEC = simpleCodec(AshBlock::new);

    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 14, 16);

    public AshBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends AshBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance, 0.2F, world.damageSources()
                                                        .fall()
        );
    }

}
