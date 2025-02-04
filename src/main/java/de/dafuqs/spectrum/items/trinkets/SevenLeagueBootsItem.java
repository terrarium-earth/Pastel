package de.dafuqs.spectrum.items.trinkets;

import com.google.common.collect.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.helpers.*;
import dev.emi.trinkets.api.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;

public class SevenLeagueBootsItem extends SpectrumTrinketItem {
	
	public static final Identifier MOVEMENT_SPEED_ATTRIBUTE_ID = SpectrumCommon.locate("seven_league_boots_movement_speed");
	public static final Identifier STEP_UP_ATTRIBUTE_ID = SpectrumCommon.locate("seven_league_boots_step_up");
	
	public SevenLeagueBootsItem(Settings settings) {
		super(settings, SpectrumCommon.locate("unlocks/trinkets/seven_league_boots"));
	}
	
	@Override
	public Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> getModifiers(ItemStack stack, SlotReference slot, LivingEntity entity, Identifier slotIdentifier) {
		Multimap<RegistryEntry<EntityAttribute>, EntityAttributeModifier> modifiers = super.getModifiers(stack, slot, entity, slotIdentifier);
		
		int powerLevel = SpectrumEnchantmentHelper.getLevel(entity.getWorld().getRegistryManager(), Enchantments.POWER, stack);
		double speedBoost = 0.05 * (powerLevel + 1);
		modifiers.put(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier(MOVEMENT_SPEED_ATTRIBUTE_ID, speedBoost, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		modifiers.put(EntityAttributes.GENERIC_STEP_HEIGHT, new EntityAttributeModifier(STEP_UP_ATTRIBUTE_ID, 0.75, EntityAttributeModifier.Operation.ADD_VALUE));
		
		return modifiers;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		return stack.getCount() == 1;
	}
	
	@Override
	public int getEnchantability() {
		return 8;
	}
	
}
