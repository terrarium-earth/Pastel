package earth.terrarium.pastel.api.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Implement this to cause Dark Stakes—and any future sources of ward disruption—to affect your block in custom ways
 * If the desired effect is a simple blockstate change, use the block tag instead.
 */
public interface WardDisruptableBlock {
    /**
     * Triggered when a Dark Stake is activated near a block
     *
     * @param pos     The position of the affected block
     * @param state   The blockstate of the affected block
     * @param level   The level of the affected block
     * @param trigger The entity that triggered the ward disruption (i.e., the Dark Stake). If you want the player that
     *                threw it, use getOwner
     */
    default void onWardDisrupt(BlockPos pos, BlockState state, Level level, Entity trigger) {
    }
}
