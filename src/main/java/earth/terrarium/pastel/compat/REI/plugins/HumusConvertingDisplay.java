package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.fluid_converting.HumusConvertingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class HumusConvertingDisplay extends FluidConvertingDisplay {
	
	public HumusConvertingDisplay(RecipeHolder<HumusConvertingRecipe> recipe) {
		super(recipe);
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return PastelPlugins.HUMUS_CONVERTING;
	}
	
	@Override
	public ResourceLocation getUnlockIdentifier() {
		return HumusConvertingRecipe.UNLOCK_IDENTIFIER;
	}
	
}