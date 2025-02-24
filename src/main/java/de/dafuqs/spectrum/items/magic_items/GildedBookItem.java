package de.dafuqs.spectrum.items.magic_items;

import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class GildedBookItem extends BookItem {
	
	public GildedBookItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return false;
	}
	
	@Override
	public int getEnchantability() {
		return Items.GOLDEN_PICKAXE.getEnchantability();
	}

	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		tooltip.add(Text.translatable("item.spectrum.gilded_book.tooltip.enchantability").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.gilded_book.tooltip.copy_enchantments").formatted(Formatting.GRAY));
		tooltip.add(Text.translatable("item.spectrum.gilded_book.tooltip.copy_enchantments2").formatted(Formatting.GRAY));
	}
	
}
