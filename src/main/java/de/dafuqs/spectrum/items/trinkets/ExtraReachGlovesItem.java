package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.Multimap;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.energy.storage.FixedSingleInkStorage;
import dev.emi.trinkets.api.SlotReference;
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

public class ExtraReachGlovesItem extends InkDrainTrinketItem {
	
	public ExtraReachGlovesItem(Properties settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/gloves_of_dawns_grasp"), InkColors.LIGHT_BLUE);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		tooltip.add(Component.translatable("item.spectrum.gloves_of_dawns_grasp.tooltip").withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, type);
	}
	
	public static ResourceLocation BLOCK_INTERACTION_ATTRIBUTE_ID = SpectrumCommon.locate("gloves_of_dawns_grasp_block_interaction");
	public static ResourceLocation ENTITY_INTERACTION_ATTRIBUTE_ID = SpectrumCommon.locate("gloves_of_dawns_grasp_entity_interaction");
	
	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, ResourceLocation slotIdentifier) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
		
		FixedSingleInkStorage inkStorage = getEnergyStorage(stack);
		long storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
		double extraReach = getExtraReach(storedInk);
		if (extraReach != 0) {
			modifiers.put(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(BLOCK_INTERACTION_ATTRIBUTE_ID, extraReach, AttributeModifier.Operation.ADD_VALUE));
			modifiers.put(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(ENTITY_INTERACTION_ATTRIBUTE_ID, extraReach / 6, AttributeModifier.Operation.ADD_VALUE));
		}
		
		return modifiers;
	}
	
	public double getExtraReach(long storedInk) {
		if (storedInk < 100) {
			return 0;
		} else {
			return 0.5 + roundHalf(Math.log(storedInk / 100.0f) / Math.log(64));
		}
	}
	
	public static double roundHalf(double number) {
		return ((int) (number * 2)) / 2D;
	}
	
}
