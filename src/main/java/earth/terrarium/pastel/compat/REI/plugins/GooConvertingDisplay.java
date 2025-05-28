package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.SpectrumPlugins;
import earth.terrarium.pastel.recipe.fluid_converting.GooConvertingRecipe;
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