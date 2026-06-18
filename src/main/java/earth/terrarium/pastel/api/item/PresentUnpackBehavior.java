package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.blocks.present.PresentBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;

/**
 * Doing a lil trolling
 */
public interface PresentUnpackBehavior {

    /**
     * Invoked when an item is unwrapped from a present
     *
     * @return the resulting stack after unpacking. Can be the original stack, ItemStack.AIR, or a new stack altogether
     */
    ItemStack onPresentUnpack(
        ItemStack stack,
        PresentBlockEntity presentBlockEntity,
        ServerLevel world,
        BlockPos pos,
        RandomSource random
    );

}
