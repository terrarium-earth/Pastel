package earth.terrarium.pastel.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public interface WardDisruptableBlock {
    default void onWardDisrupt(BlockPos pos, BlockState state, Level level) {}
}
