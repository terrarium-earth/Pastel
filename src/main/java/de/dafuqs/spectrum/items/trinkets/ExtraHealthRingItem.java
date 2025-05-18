package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.Multimap;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.energy.storage.FixedSingleInkStorage;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ExtraHealthRingItem extends InkDrainTrinketItem {
	
	public ExtraHealthRingItem(Properties settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/heartsingers_reward"), InkColors.PINK);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		tooltip.add(Component.translatable("item.spectrum.heartsingers_reward.tooltip").withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, type);
	}
	
	public static ResourceLocation HEALTH_ATTRIBUTE_ID = SpectrumCommon.locate("heartsingers_reward_health");

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);;
		
		FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
		long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
		int extraHearts = getExtraHearts(storedInk);
		if (extraHearts != 0) {
			modifiers.put(Attributes.MAX_HEALTH, new AttributeModifier(HEALTH_ATTRIBUTE_ID, extraHearts, AttributeModifier.Operation.ADD_VALUE));
		}
		
		return modifiers;
	}
	
	public int getExtraHearts(long storedInk) {
		if (storedInk < 100) {
			return 0;
		} else {
			return 2 + 2 * (int) (Math.log(storedInk / 100.0f) / Math.log(8));
		}
	}
	
}
