package earth.terrarium.pastel.compat.emi.recipes;

import earth.terrarium.pastel.compat.emi.GatedSpectrumEmiRecipe;
import earth.terrarium.pastel.compat.emi.SpectrumEmiRecipeCategories;
import earth.terrarium.pastel.recipe.cinderhearth.CinderhearthRecipe;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.TextWidget.Alignment;
import dev.emi.emi.api.widget.WidgetHolder;

public class CinderhearthEmiRecipeGated extends GatedSpectrumEmiRecipe<CinderhearthRecipe> {
	
	public CinderhearthEmiRecipeGated(CinderhearthRecipe recipe) {
		super(SpectrumEmiRecipeCategories.CINDERHEARTH, recipe, 136, 48);
		this.inputs = recipe.getIngredientStacks().stream().map(s -> EmiIngredient.of(s.getItems().map(EmiStack::of).toList())).toList();
		this.outputs = recipe.getResultsWithChance().stream().map(p -> EmiStack.of(p.getA()).setChance(p.getB())).toList();
	}
	
	@Override
	public void addUnlockedWidgets(WidgetHolder widgets) {
		int xOff = 5;
		widgets.addSlot(inputs.getFirst(), xOff, 0);

		widgets.addFillingArrow(22 + xOff, 9, recipe.getCraftingTime() * 50);

		widgets.addTexture(EmiTexture.FULL_FLAME, 1 + xOff, 20);
		widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1 + xOff, 20, 10000, false, true, false);
		
		for (int i = 0; i < 3; i++) {
			if (i >= outputs.size()) {
				widgets.addSlot(EmiStack.EMPTY, 50 + i * 26 + xOff, 5).large(true);
			} else {
				widgets.addSlot(outputs.get(i), 50 + i * 26 + xOff, 5).large(true).recipeContext(this);
			}
		}
		
		widgets.addText(getCraftingTimeText(recipe.getCraftingTime(), recipe.getExperience()), width / 2, 37, 0x3f3f3f, false).horizontalAlign(Alignment.CENTER);
	}
}
