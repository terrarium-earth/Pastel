package de.dafuqs.spectrum.compat.REI;

import de.dafuqs.spectrum.api.recipe.FluidIngredient;
import de.dafuqs.spectrum.api.recipe.IngredientStack;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class REIHelper {
	
	public static List<EntryIngredient> toEntryIngredients(List<IngredientStack> ingredientStacks) {
		return ingredientStacks.stream().map(REIHelper::ofIngredientStack).collect(Collectors.toCollection(ArrayList::new));
	}
	
	public static EntryIngredient ofIngredientStack(@NotNull IngredientStack ingredientStack) {
		return EntryIngredients.ofItemStacks(ingredientStack.getMatchingStacks());
	}
	
	public static EntryIngredient ofFluidIngredient(FluidIngredient fluidIngredient) {
		if (fluidIngredient.isTag()) {
			return EntryIngredients.ofFluidTag(fluidIngredient.tag().get());
		}
		return EntryIngredients.of(fluidIngredient.fluid().get());
	}
	
}
