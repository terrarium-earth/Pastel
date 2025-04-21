package de.dafuqs.spectrum.items;

import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.items.food.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.screen.slot.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;

import java.util.*;

public class ConcealingOilsItem extends DrinkItem implements InkPoweredPotionFillable {
	
	public static final int POISONED_COLOUR = 0x3d1125;
	
	public ConcealingOilsItem(Settings settings) {
		super(settings);
	}
	
	@Override
	public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
		if (!InkPoweredPotionFillable.getEffects(stack).isEmpty())
			tooltip.add(Text.translatable("item.spectrum.concealing_oils.tooltip").styled(s -> s.withFormatting(Formatting.GRAY).withItalic(true)));
		appendPotionFillableTooltip(stack, tooltip, Text.translatable("item.spectrum.concealing_oils.when_poisoned"), true, context.getUpdateTickRate());
	}
	
	@Override
	public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
		if (clickType != ClickType.RIGHT)
			return false;
		
		var appliedStack = slot.getStack();
		
		if (!appliedStack.contains(DataComponentTypes.FOOD))
			return false;
		
		if (!isFull(stack))
			return false;
		
		if (tryApplyOil(stack, appliedStack, player)) {
			if (!player.getAbilities().creativeMode)
				stack.decrement(1);
			player.playSound(SoundEvents.ITEM_BOTTLE_EMPTY, 1, 1);
			return true;
		}
		
		return false;
	}
	
	private boolean tryApplyOil(ItemStack oil, ItemStack food, PlayerEntity user) {
		if (food.getItem() instanceof DrinkItem)
			return false;
		if (food.contains(SpectrumDataComponentTypes.CONCEALED_EFFECT))
			return false;
		
		var effect = InkPoweredPotionFillable.getEffects(oil).getFirst();
		if (!InkPowered.tryDrainEnergy(user, effect.getInkCost().color(), effect.getInkCost().cost()))
			return false;
		
		var foodComponent = food.get(DataComponentTypes.FOOD);
		if (foodComponent != null &&
				foodComponent
						.effects()
						.stream()
						.map(FoodComponent.StatusEffectEntry::effect)
						.anyMatch(e -> e.equals(effect.getStatusEffectInstance().getEffectType())))
			return false;
		
		food.set(DataComponentTypes.PROFILE, new ProfileComponent(user.getGameProfile()));
		food.set(SpectrumDataComponentTypes.CONCEALED_EFFECT, effect.getStatusEffectInstance());
		return true;
	}
	
	@Override
	public int maxEffectCount() {
		return 1;
	}
	
	@Override
	public int maxEffectAmplifier() {
		return 3;
	}
}
