package earth.terrarium.pastel.api.item;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface Preenchanted {
	
	Map<ResourceKey<Enchantment>, Integer> getDefaultEnchantments();
	
	static ItemEnchantments buildDefaultEnchantments(HolderLookup.Provider lookup, Preenchanted item) {
		ItemEnchantments.Mutable builder = new ItemEnchantments.Mutable(ItemEnchantments.EMPTY);
		for (Map.Entry<ResourceKey<Enchantment>, Integer> entry : item.getDefaultEnchantments().entrySet()) {
			builder.set(lookup.lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(entry.getKey()), entry.getValue());
		}
		return builder.toImmutable();
	}
	
	static @NotNull <T extends Item & Preenchanted> ItemStack getDefaultEnchantedStack(HolderLookup.Provider lookup, T item) {
		ItemStack stack = new ItemStack(item);
		stack.set(DataComponents.ENCHANTMENTS, buildDefaultEnchantments(lookup, item));
		return stack;
	}
	
	/**
	 * Checks a stack if it only has enchantments that are lower or equal its DefaultEnchantments,
	 * meaning enchantments had been added on top of the original ones.
	 */
	default boolean onlyHasPreEnchantments(ItemStack stack) {
		var currentEnchants = stack.getOrDefault(DataComponents.ENCHANTMENTS, ItemEnchantments.EMPTY);
		var defaultEnchants = getDefaultEnchantments();
		return currentEnchants.equals(defaultEnchants);
	}
	
}
