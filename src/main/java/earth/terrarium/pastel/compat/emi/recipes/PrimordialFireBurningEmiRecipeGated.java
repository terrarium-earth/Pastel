package earth.terrarium.pastel.compat.emi.recipes;

import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.widget.WidgetHolder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.emi.GatedSpectrumEmiRecipe;
import earth.terrarium.pastel.compat.emi.PastelEmiRecipeCategories;
import earth.terrarium.pastel.compat.emi.widgets.AnimatedTexturedWidget;
import earth.terrarium.pastel.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import net.minecraft.resources.ResourceLocation;

public class PrimordialFireBurningEmiRecipeGated extends GatedSpectrumEmiRecipe<PrimordialFireBurningRecipe> {

    private final static ResourceLocation FIRE_TEXTURE = PastelCommon.locate("textures/block/primordial_fire_0.png");

    public PrimordialFireBurningEmiRecipeGated(PrimordialFireBurningRecipe recipe) {
        super(PastelEmiRecipeCategories.PRIMORDIAL_FIRE_BURNING, recipe, 80, 35);
        this.inputs = recipe.getIngredients()
                            .stream()
                            .map(EmiIngredient::of)
                            .toList();
    }

    @Override
    public void addUnlockedWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.getFirst(), 0, 0);
        widgets.add(new AnimatedTexturedWidget(FIRE_TEXTURE, 1, 19, 16, 176, 1000));
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 24, 9);
        widgets.addSlot(outputs.getFirst(), 54, 4)
               .large(true)
               .recipeContext(this);
    }

}
