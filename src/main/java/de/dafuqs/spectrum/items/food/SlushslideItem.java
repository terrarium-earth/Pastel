package de.dafuqs.spectrum.items.food;

import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class SlushslideItem extends DrinkItem {
	
	public SlushslideItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		tooltip.add(Text.translatable("item.spectrum.slushslide.tooltip").formatted(Formatting.GRAY));
		super.appendTooltip(stack, context, tooltip, type);
	}
}
