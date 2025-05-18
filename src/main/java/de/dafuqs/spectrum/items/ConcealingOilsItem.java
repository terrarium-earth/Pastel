package de.dafuqs.spectrum.items;

import de.dafuqs.spectrum.api.energy.InkPowered;
import de.dafuqs.spectrum.api.item.InkPoweredPotionFillable;
import de.dafuqs.spectrum.items.food.DrinkItem;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ResolvableProfile;

import java.util.List;

public class ConcealingOilsItem extends DrinkItem implements InkPoweredPotionFillable {
	
	public static final int POISONED_COLOUR = 0x3d1125;
	
	public ConcealingOilsItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		if (!InkPoweredPotionFillable.getEffects(stack).isEmpty())
			tooltip.add(Component.translatable("item.spectrum.concealing_oils.tooltip").withStyle(s -> s.applyFormat(ChatFormatting.GRAY).withItalic(true)));
		appendPotionFillableTooltip(stack, tooltip, Component.translatable("item.spectrum.concealing_oils.when_poisoned"), true, context.tickRate());
	}
	
	@Override
	public boolean overrideStackedOnOther(ItemStack oilsStack, Slot slot, ClickAction clickType, Player player) {
		if (clickType != ClickAction.SECONDARY)
			return false;
		
		var stackToApplyTo = slot.getItem();
		
		if (!stackToApplyTo.has(DataComponents.FOOD))
			return false;
		
		if (!isFull(oilsStack))
			return false;
		
		if (tryApplyOil(oilsStack, stackToApplyTo, player)) {
			if (!player.getAbilities().instabuild)
				oilsStack.shrink(1);
			player.playSound(SoundEvents.BOTTLE_EMPTY, 1, 1);
			return true;
		}
		
		return false;
	}
	
	private boolean tryApplyOil(ItemStack oilsStack, ItemStack foodStack, Player user) {
		if (foodStack.getItem() instanceof DrinkItem)
			return false;
		if (foodStack.has(SpectrumDataComponentTypes.CONCEALED_EFFECT))
			return false;
		
		var effect = InkPoweredPotionFillable.getEffects(oilsStack).getFirst();
		if (!InkPowered.tryDrainEnergy(user, effect.getInkCost().color(), effect.getInkCost().cost()))
			return false;
		
		var foodComponent = foodStack.get(DataComponents.FOOD);
		if (foodComponent != null &&
				foodComponent
						.effects()
						.stream()
						.map(FoodProperties.PossibleEffect::effect)
						.anyMatch(e -> e.is(effect.getStatusEffectInstance().getEffect())))
			return false;
		
		foodStack.set(DataComponents.PROFILE, new ResolvableProfile(user.getGameProfile()));
		foodStack.set(SpectrumDataComponentTypes.CONCEALED_EFFECT, effect.getStatusEffectInstance());
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
