package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import de.dafuqs.spectrum.compat.REI.REIHelper;
import de.dafuqs.spectrum.recipe.potion_workshop.PotionWorkshopRecipe;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.crafting.RecipeHolder;

import java.util.Collections;

public abstract class PotionWorkshopRecipeDisplay extends GatedSpectrumDisplay {
	
	protected final int craftingTime;
	
	/**
	 * When using the REI recipe functionality
	 *
	 * @param recipe The recipe
	 */
	public PotionWorkshopRecipeDisplay(RecipeHolder<? extends PotionWorkshopRecipe> recipe) {
		super(recipe, REIHelper.toEntryIngredients(recipe.value().getIngredientStacks()), Collections.singletonList(EntryIngredients.of(recipe.value().getResultItem(BasicDisplay.registryAccess()))));
		this.craftingTime = recipe.value().getCraftingTime();
	}
	
}