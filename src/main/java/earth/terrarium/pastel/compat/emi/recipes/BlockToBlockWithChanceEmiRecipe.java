package earth.terrarium.pastel.compat.emi.recipes;

import earth.terrarium.pastel.compat.emi.PastelEmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class BlockToBlockWithChanceEmiRecipe extends PastelEmiRecipe {

    public BlockToBlockWithChanceEmiRecipe(
        EmiRecipeCategory category, ResourceLocation id, EmiIngredient in, EmiStack out, ResourceLocation unlock) {
        super(category, unlock, id, 78, 26);
        this.inputs = List.of(in);
        this.outputs = List.of(out);
    }

    @Override
    public void addUnlockedWidgets(WidgetHolder widgets) {
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 23, 4);
        widgets.addSlot(inputs.getFirst(), 0, 4);
        widgets.addSlot(outputs.getFirst(), 52, 0)
               .large(true)
               .recipeContext(this);
    }
}
