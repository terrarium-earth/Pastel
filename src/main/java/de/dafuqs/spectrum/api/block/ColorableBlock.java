package de.dafuqs.spectrum.api.block;

import de.dafuqs.spectrum.helpers.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

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
	boolean color(World world, BlockPos pos, Optional<DyeColor> color, @Nullable Entity user);
	
	Optional<DyeColor> getColor(World world, BlockPos pos);
	
	default boolean isColor(World world, BlockPos pos, Optional<DyeColor> color) {
		return getColor(world, pos) == color;
    }

    default boolean tryColorUsingStackInHand(ItemStack handStack, World world, BlockPos pos, PlayerEntity player, Hand hand) {
		Optional<DyeColor> itemInHandColor = SpectrumColorHelper.getDyeColorOfItemStack(handStack);
        if (itemInHandColor.isPresent()) {
			if (color(world, pos, itemInHandColor, player)) {
                if(!player.isCreative()) {
                    handStack.decrement(1);
                }
                return true;
            }
        }
        return false;
    }

}
