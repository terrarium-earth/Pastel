package earth.terrarium.pastel.compat.REI.plugins;

import earth.terrarium.pastel.compat.REI.PastelPlugins;
import earth.terrarium.pastel.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;

public class LiquidCrystalConvertingDisplay extends FluidConvertingDisplay {
	
	public LiquidCrystalConvertingDisplay(RecipeHolder<LiquidCrystalConvertingRecipe> recipe) {
		super(recipe);
	}
	
	@Override
	public CategoryIdentifier<?> getCategoryIdentifier() {
		return PastelPlugins.LIQUID_CRYSTAL_CONVERTING;
	}
	
	@Override
	public ResourceLocation getUnlockIdentifier() {
		return LiquidCrystalConvertingRecipe.UNLOCK_IDENTIFIER;
	}
	
}