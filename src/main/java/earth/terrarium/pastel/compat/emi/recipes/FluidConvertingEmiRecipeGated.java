package earth.terrarium.pastel.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.WidgetHolder;
import earth.terrarium.pastel.compat.emi.GatedSpectrumEmiRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.FluidConvertingRecipe;

public class FluidConvertingEmiRecipeGated extends GatedSpectrumEmiRecipe<FluidConvertingRecipe> {

    public FluidConvertingEmiRecipeGated(EmiRecipeCategory category, FluidConvertingRecipe recipe) {
        super(category, recipe, 78, 26);
        this.inputs = recipe
            .getIngredients()
            .stream()
            .map(EmiIngredient::of)
            .toList();
    }

    @Override
    public void addUnlockedWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 23, 4);
        widgets.addSlot(inputs.getFirst(), 0, 4);
        widgets
            .addSlot(outputs.getFirst(), 52, 0)
            .large(true)
            .recipeContext(this);
    }
}
