package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.GatedSpectrumDisplay;
import de.dafuqs.spectrum.recipe.GatedSpectrumRecipe;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class EnchanterDisplay extends GatedSpectrumDisplay {
	
	// first input is the center, all others around clockwise
	public EnchanterDisplay(@NotNull RecipeHolder<? extends GatedSpectrumRecipe<?>> recipe, List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
		super(recipe, inputs, outputs);
	}
	
}