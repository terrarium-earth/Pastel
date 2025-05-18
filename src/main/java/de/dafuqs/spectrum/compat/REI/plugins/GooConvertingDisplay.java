package de.dafuqs.spectrum.compat.REI.plugins;

import de.dafuqs.spectrum.compat.REI.SpectrumPlugins;
import de.dafuqs.spectrum.recipe.fluid_converting.GooConvertingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class GooConvertingDisplay extends FluidConvertingDisplay {
	
	public GooConvertingDisplay(RecipeHolder<GooConvertingRecipe> recipe) {
		super(recipe);
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return SpectrumPlugins.GOO_CONVERTING;
	}
	
	@Override
	public ResourceLocation getUnlockIdentifier() {
		return GooConvertingRecipe.UNLOCK_IDENTIFIER;
	}
	
}