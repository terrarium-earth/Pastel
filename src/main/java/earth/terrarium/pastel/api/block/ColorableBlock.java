package earth.terrarium.pastel.api.block;

import earth.terrarium.pastel.helpers.ColorHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * A block that can be colored by using dye, pigment, a paintbrush or other coloring actions on them
 */
public interface ColorableBlock {

    /**
     * Color the block into the specified color
     *
     * @param color the color the block should be colored in
     * @return True if coloring was successful, false if failed (like the block was this color already)
     */
	boolean color(Level world, BlockPos pos, Optional<DyeColor> color, @Nullable Entity user);
	
	Optional<DyeColor> getColor(Level world, BlockPos pos);
	
	default boolean isColor(Level world, BlockPos pos, Optional<DyeColor> color) {
		return getColor(world, pos) == color;
    }

    default boolean tryColorUsingStackInHand(ItemStack handStack, Level world, BlockPos pos, Player player, InteractionHand hand) {
		Optional<DyeColor> itemInHandColor = ColorHelper.getDyeColorOfItemStack(handStack);
        if (itemInHandColor.isPresent()) {
			if (color(world, pos, itemInHandColor, player)) {
                if(!player.isCreative()) {
                    handStack.shrink(1);
                }
                return true;
            }
        }
        return false;
    }

}
