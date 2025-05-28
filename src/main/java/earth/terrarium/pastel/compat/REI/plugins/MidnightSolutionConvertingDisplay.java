package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.SpectrumPlugins;
import earth.terrarium.pastel.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
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