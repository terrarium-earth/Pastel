package de.dafuqs.spectrum.api.item;

import net.minecraft.network.chat.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface TooltipExtensions {
	
	default void expandTooltipPostStats(ItemStack stack, @Nullable Player player, List<Component> tooltip, Item.TooltipContext context) {}
	
	default void appendTooltipWithPlayer(ItemStack stack, @Nullable Player player, List<Component> tooltip, Item.TooltipContext context) {}
	
}
