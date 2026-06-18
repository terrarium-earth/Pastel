package earth.terrarium.pastel.compat.emi.handlers;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import earth.terrarium.pastel.compat.emi.PastelEmiRecipeCategories;
import earth.terrarium.pastel.inventories.PotionWorkshopScreenHandler;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class PotionWorkshopRecipeHandler implements StandardRecipeHandler<PotionWorkshopScreenHandler> {
    @Override
    public List<Slot> getInputSources(PotionWorkshopScreenHandler handler) {
        List<Slot> slots = new ArrayList<>();
        slots.addAll(handler.slots.subList(0, 9));
        slots.addAll(handler.slots.subList(21, 57));
        return slots;
    }

    @Override
    public List<Slot> getCraftingSlots(PotionWorkshopScreenHandler handler) {
        return handler.slots.subList(0, 9);
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        EmiRecipeCategory category = recipe.getCategory();
        return (category == PastelEmiRecipeCategories.POTION_WORKSHOP_BREWING || category == PastelEmiRecipeCategories.POTION_WORKSHOP_CRAFTING) && recipe
            .supportsRecipeTree();
    }
}
