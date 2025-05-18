package de.dafuqs.spectrum.recipe.crafting.dynamic;

import de.dafuqs.spectrum.api.item.InkPoweredPotionFillable;
import de.dafuqs.spectrum.registries.SpectrumRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ClearPotionFillableRecipe extends SingleItemCraftingRecipe {
	
	@Override
	public boolean matches(Level world, ItemStack stack) {
		return stack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable && inkPoweredPotionFillable.isAtLeastPartiallyFilled(stack);
	}
	
	@Override
	public ItemStack assemble(ItemStack stack) {
		if (stack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable) {
			stack = stack.copy();
			stack.setCount(1);
			inkPoweredPotionFillable.clearEffects(stack);
		}
		return stack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.CLEAR_POTION_FILLABLE_SERIALIZER;
	}
	
}
