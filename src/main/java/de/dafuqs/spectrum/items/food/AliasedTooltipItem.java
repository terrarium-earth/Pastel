package de.dafuqs.spectrum.items.food;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AliasedTooltipItem extends ItemNameBlockItem {

	private final List<MutableComponent> tooltipTexts = new ArrayList<>();

	public AliasedTooltipItem(Block block, Properties settings, String tooltip) {
		super(block, settings);
		this.tooltipTexts.add(Component.translatable(tooltip));
	}

	public AliasedTooltipItem(Block block, Properties settings, String[] tooltips) {
		super(block, settings);
		Arrays.stream(tooltips)
				.map(Component::translatable)
				.forEach(tooltipTexts::add);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		for (MutableComponent text : this.tooltipTexts) {
			tooltip.add(text.withStyle(ChatFormatting.GRAY));
		}
	}

}
