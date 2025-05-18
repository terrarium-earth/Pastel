package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public class PrimordialFireBurningDisplay extends GatedSpectrumDisplay {
	
	public PrimordialFireBurningDisplay(RecipeHolder<PrimordialFireBurningRecipe> recipe) {
		super(recipe, recipe.value().getIngredients().stream().map(EntryIngredients::ofIngredient).toList(), Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(BasicDisplay.registryAccess()))));
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.PRIMORDIAL_FIRE_BURNING;
	}
	
}