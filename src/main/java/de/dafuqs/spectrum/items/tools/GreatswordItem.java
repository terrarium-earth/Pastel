package de.dafuqs.spectrum.items.tools;

import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;

import java.util.*;

/**
 * A sword with additional reach
 */
public class GreatswordItem extends SwordItem implements Preenchanted {

	public GreatswordItem(ToolMaterial material, int attackDamage, float attackSpeed, float extraReach, Settings settings) {
		super(material, settings.attributeModifiers(AttributeModifiersComponent.builder()
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, material.getAttackDamage() + attackDamage, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(SpectrumEntityAttributes.REACH_MODIFIER_ID, extraReach, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
				.build()));
	}

	@Override
	public Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments() {
		return Map.of(Enchantments.SWEEPING_EDGE, 4);
	}

}
