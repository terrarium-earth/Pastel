package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class DragonrotConvertingDisplay extends FluidConvertingDisplay {
	
	public DragonrotConvertingDisplay(RecipeHolder<DragonrotConvertingRecipe> recipe) {
		super(recipe);
	}
	
	@Override
	public ResourceLocation getUnlockIdentifier() {
		return DragonrotConvertingRecipe.UNLOCK_IDENTIFIER;
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return PastelPlugins.DRAGONROT_CONVERTING;
	}
	
}