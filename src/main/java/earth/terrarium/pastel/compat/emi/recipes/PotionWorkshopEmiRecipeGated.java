package earth.terrarium.pastel.compat.emi.recipes;

import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.compat.emi.GatedSpectrumEmiRecipe;
import earth.terrarium.pastel.recipe.potion_workshop.PotionWorkshopRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class PotionWorkshopEmiRecipeGated extends GatedSpectrumEmiRecipe<PotionWorkshopRecipe> {
    private final static ResourceLocation BACKGROUND_TEXTURE = PastelCommon.locate(
        "textures/gui/container/potion_workshop_3_slots.png");

    public PotionWorkshopEmiRecipeGated(EmiRecipeCategory category, PotionWorkshopRecipe recipe) {
        super(category, recipe, 112, 66);

        this.inputs = recipe.getIngredientStacks()
                            .stream()
                            .map(s -> EmiIngredient.of(s.getItems()
                                                        .map(EmiStack::of)
                                                        .toList()))
                            .toList();
    }

    @Override
    public void addUnlockedWidgets(WidgetHolder widgets) {
        widgets.addSlot(inputs.get(0), 18, 48);
        widgets.addSlot(inputs.get(1), 65, 4);
        widgets.addSlot(inputs.get(2), 18, 0);
        widgets.addSlot(inputs.get(3), 0, 24);
        widgets.addSlot(inputs.get(4), 36, 24);

        widgets.addSlot(outputs.getFirst(), 94, 24)
               .recipeContext(this);

        // bubbles
        widgets.addTexture(BACKGROUND_TEXTURE, 21, 20, 11, 27, 176, 0);
        widgets.addFillingArrow(62, 25, recipe.getCraftingTime() * 50);

        // description text
        Component text = Component.translatable("container.pastel.rei.crafting_time", (recipe.getCraftingTime() / 20));
        widgets.addText(text, 40, 53, 0x3f3f3f, false);
    }
}
