package de.dafuqs.spectrum.api.item;

import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import org.jetbrains.annotations.*;

import java.util.*;

public interface Preenchanted {
	
	Map<RegistryKey<Enchantment>, Integer> getDefaultEnchantments();
	
	static ItemEnchantmentsComponent buildDefaultEnchantments(RegistryWrapper.WrapperLookup lookup, Preenchanted item) {
		ItemEnchantmentsComponent.Builder builder = new ItemEnchantmentsComponent.Builder(ItemEnchantmentsComponent.DEFAULT);
		for (Map.Entry<RegistryKey<Enchantment>, Integer> entry : item.getDefaultEnchantments().entrySet()) {
			builder.set(lookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(entry.getKey()), entry.getValue());
		}
		return builder.build();
	}
	
	static @NotNull <T extends Item & Preenchanted> ItemStack getDefaultEnchantedStack(RegistryWrapper.WrapperLookup lookup, T item) {
		ItemStack stack = new ItemStack(item);
		stack.set(DataComponentTypes.ENCHANTMENTS, buildDefaultEnchantments(lookup, item));
		return stack;
	}
	
	/**
	 * Checks a stack if it only has enchantments that are lower or equal its DefaultEnchantments,
	 * meaning enchantments had been added on top of the original ones.
	 */
	default boolean onlyHasPreEnchantments(ItemStack stack) {
		var currentEnchants = stack.getOrDefault(DataComponentTypes.ENCHANTMENTS, ItemEnchantmentsComponent.DEFAULT);
		var defaultEnchants = getDefaultEnchantments();
		return currentEnchants.equals(defaultEnchants);
	}
	
}
