package de.dafuqs.spectrum.recipe;

import it.unimi.dsi.fastutil.objects.*;
import net.minecraft.item.*;
import net.minecraft.recipe.input.*;
import net.minecraft.util.*;

import java.util.*;

public abstract class GatedStackSpectrumRecipe<C extends RecipeInput> extends GatedSpectrumRecipe<C> {
	
	protected GatedStackSpectrumRecipe(String group, boolean secret, Identifier requiredAdvancementIdentifier) {
		super(group, secret, requiredAdvancementIdentifier);
	}
	
	public abstract List<IngredientStack> getIngredientStacks();
	
	protected boolean matchIngredientStacksExclusively(RecipeInput recipeInput, List<IngredientStack> ingredientStacks) {
		// does the recipe fit into that inventory in the first place?
		if (recipeInput.getSize() < ingredientStacks.size()) {
			return false;
		}
		
		// collect all non-empty stacks
		List<ItemStack> nonEmptyStacks = new ArrayList<>();
		for (int i = 0; i < recipeInput.getSize(); i++) {
			ItemStack stack = recipeInput.getStackInSlot(i);
			if (!stack.isEmpty()) {
				nonEmptyStacks.add(stack);
			}
		}
		// do we have an exact count match?
		if (nonEmptyStacks.size() != ingredientStacks.size()) {
			return false;
		}
		
		// match each IngredientStack exclusively
		ObjectArraySet<IngredientStack> ingredients = ObjectArraySet.of(ingredientStacks.toArray(new IngredientStack[0]));
		for (ItemStack stack : nonEmptyStacks) {
			IngredientStack foundStack = null;
			for (IngredientStack ingredientStack : ingredients) {
				if (ingredientStack.test(stack)) {
					foundStack = ingredientStack;
					break;
				}
			}
			if (foundStack == null) {
				return false;
			}
			ingredients.remove(foundStack);
			if (ingredients.isEmpty()) {
				break;
			}
		}
		
		return true;
	}
	
	protected boolean matchIngredientStacksExclusively(RecipeInput recipeInput, List<IngredientStack> ingredients, int[] slots) {
		int inputStackCount = 0;
		for (int slot : slots) {
			if (!recipeInput.getStackInSlot(slot).isEmpty()) {
				inputStackCount++;
			}
		}
		if (inputStackCount != ingredients.size()) {
			return false;
		}
		
		for (IngredientStack ingredient : ingredients) {
			boolean found = false;
			for (int slot : slots) {
				if (ingredient.test(recipeInput.getStackInSlot(slot))) {
					found = true;
					break;
				}
			}
			if (!found) {
				return false;
			}
		}
		
		return true;
	}
	
}
