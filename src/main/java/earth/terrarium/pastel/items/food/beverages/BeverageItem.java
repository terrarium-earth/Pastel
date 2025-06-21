package earth.terrarium.pastel.items.food.beverages;

import earth.terrarium.pastel.api.item.FermentedItem;
import earth.terrarium.pastel.components.BeverageComponent;
import earth.terrarium.pastel.items.food.StatusEffectDrinkItem;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class BeverageItem extends StatusEffectDrinkItem implements FermentedItem {
	
	public BeverageItem(Properties settings) {
		super(settings.component(PastelDataComponentTypes.BEVERAGE, BeverageComponent.DEFAULT));
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		super.appendHoverText(stack, context, tooltip, type);
		
		var infused = stack.get(PastelDataComponentTypes.INFUSED_BEVERAGE);
		if (infused != null) infused.addToTooltip(context, tooltip::add, type);
		
		var beverage = stack.get(PastelDataComponentTypes.BEVERAGE);
		if (beverage != null) beverage.addToTooltip(context, tooltip::add, type);
		
		var jade = stack.get(PastelDataComponentTypes.JADE_WINE);
		if (jade != null) jade.addToTooltip(context, tooltip::add, type);
		
		var effects = stack.get(DataComponents.POTION_CONTENTS);
		if (effects != null) effects.addPotionTooltip(tooltip::add, 1.0f, context.tickRate());
	}
	
}
