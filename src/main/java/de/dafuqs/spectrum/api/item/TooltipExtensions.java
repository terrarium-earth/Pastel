package de.dafuqs.spectrum.api.item;

import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.text.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface TooltipExtensions {
	
	default void expandTooltipPostStats(ItemStack stack, @Nullable PlayerEntity player, List<Text> tooltip, Item.TooltipContext context) {}
	
	default void appendTooltipWithPlayer(ItemStack stack, @Nullable PlayerEntity player, List<Text> tooltip, Item.TooltipContext context) {}
	
}
