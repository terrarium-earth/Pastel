package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.anvil_crushing.AnvilCrushingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public class AnvilCrushingDisplay extends GatedSpectrumDisplay {
	public final float experience;
	public final float crushedItemsPerPointOfDamage;
	
	public AnvilCrushingDisplay(RecipeHolder<AnvilCrushingRecipe> recipe) {
		super(recipe, recipe.value().getIngredients().stream().map(EntryIngredients::ofIngredient).toList(), Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(BasicDisplay.registryAccess()))));
		this.experience = recipe.value().getExperience();
		this.crushedItemsPerPointOfDamage = recipe.value().getCrushedItemsPerPointOfDamage();
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.ANVIL_CRUSHING;
	}
	
}