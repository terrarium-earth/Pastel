package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class MidnightSolutionConvertingDisplay extends FluidConvertingDisplay {
	
	public MidnightSolutionConvertingDisplay(RecipeHolder<MidnightSolutionConvertingRecipe> recipe) {
		super(recipe);
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.MIDNIGHT_SOLUTION_CONVERTING;
	}
	
	@Override
	public ResourceLocation getUnlockIdentifier() {
		return MidnightSolutionConvertingRecipe.UNLOCK_IDENTIFIER;
	}
	
}