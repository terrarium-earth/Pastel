package de.dafuqs.spectrum.api.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import java.util.List;

public interface CreativeOnlyItem {
	
	Component DESCRIPTION = Component.translatable("item.spectrum.creative_only").withStyle(ChatFormatting.DARK_PURPLE);
	
	static void appendTooltip(List<Component> tooltip) {
		tooltip.add(DESCRIPTION);
	}
	
}
