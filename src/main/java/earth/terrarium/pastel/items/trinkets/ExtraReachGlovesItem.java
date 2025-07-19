package earth.terrarium.pastel.items.trinkets;

import com.google.common.collect.Multimap;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.energy.storage.FixedSingleInkStorage;
import top.theillusivec4.curios.api.SlotContext;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class ExtraReachGlovesItem extends InkDrainTrinketItem {
	
	public ExtraReachGlovesItem(Properties settings) {
		super(settings, PastelCommon.locate("unlocks/trinkets/gloves_of_dawns_grasp"), InkColors.LIGHT_BLUE);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
		tooltip.add(Component.translatable("item.pastel.gloves_of_dawns_grasp.tooltip").withStyle(ChatFormatting.GRAY));
		super.appendHoverText(stack, context, tooltip, type);
	}
	
	public static ResourceLocation BLOCK_INTERACTION_ATTRIBUTE_ID = PastelCommon.locate("gloves_of_dawns_grasp_block_interaction");
	public static ResourceLocation ENTITY_INTERACTION_ATTRIBUTE_ID = PastelCommon.locate("gloves_of_dawns_grasp_entity_interaction");

	@Override
	public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
		Multimap<Holder<Attribute>, AttributeModifier> modifiers = super.getAttributeModifiers(slotContext, id, stack);
		
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
