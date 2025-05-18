package de.dafuqs.spectrum.items.food.beverages;

import de.dafuqs.spectrum.api.item.FermentedItem;
import de.dafuqs.spectrum.components.BeverageComponent;
import de.dafuqs.spectrum.items.food.StatusEffectDrinkItem;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BeverageItem extends StatusEffectDrinkItem implements FermentedItem {
	
	public BeverageItem(Properties settings) {
		super(settings.component(SpectrumDataComponentTypes.BEVERAGE, BeverageComponent.DEFAULT));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		
		var infused = stack.get(SpectrumDataComponentTypes.INFUSED_BEVERAGE);
		if (infused != null) infused.addToTooltip(context, tooltip::add, type);
		
		var beverage = stack.get(SpectrumDataComponentTypes.BEVERAGE);
		if (beverage != null) beverage.addToTooltip(context, tooltip::add, type);
		
		var jade = stack.get(SpectrumDataComponentTypes.JADE_WINE);
		if (jade != null) jade.addToTooltip(context, tooltip::add, type);
		
		var effects = stack.get(DataComponents.POTION_CONTENTS);
		if (effects != null) effects.addPotionTooltip(tooltip::add, 1.0f, context.tickRate());
	}
	
}
