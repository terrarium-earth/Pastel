package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.recipe.RecipeScaling;
import net.minecraft.core.Registry;

public class SpectrumRecipeScalings {
	
	public static void init() {
		register(RecipeScaling.LINEAR);
		register(RecipeScaling.DOUBLING);
		register(RecipeScaling.EXPONENTIAL);
		register(RecipeScaling.INDEXED);
	}
	
	private static void register(RecipeScaling scaling) {
		Registry.register(SpectrumRegistries.RECIPE_SCALING, scaling.getId(), scaling);
	}
}
