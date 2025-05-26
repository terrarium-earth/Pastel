package de.dafuqs.spectrum.items.food;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class MoonstruckNectarItem extends DrinkItem {
	
	public MoonstruckNectarItem(Properties settings) {
		super(settings);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		tooltip.add(Component.translatable("item.pastel.moonstruck_nectar.tooltip").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.translatable("item.pastel.moonstruck_nectar.tooltip2").withStyle(ChatFormatting.GRAY));
	}
	
}
