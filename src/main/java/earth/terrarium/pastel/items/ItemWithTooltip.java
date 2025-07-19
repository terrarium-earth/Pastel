package earth.terrarium.pastel.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemWithTooltip extends Item {

	private final List<MutableComponent> tooltipTexts = new ArrayList<>();
	
	public ItemWithTooltip(Properties settings, String tooltip) {
		super(settings);
		this.tooltipTexts.add(Component.translatable(tooltip));
	}
	
	public ItemWithTooltip(Properties settings, String[] tooltips) {
		super(settings);
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
