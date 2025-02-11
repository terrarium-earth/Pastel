package de.dafuqs.spectrum.items.food;

import net.minecraft.item.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

import java.util.*;

public class SlushslideItem extends DrinkItem {
	
	public SlushslideItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
		tooltip.add(Text.translatable("item.spectrum.slushslide.tooltip").formatted(Formatting.GRAY));
		super.appendTooltip(itemStack, world, tooltip, tooltipContext);
	}
	
}
