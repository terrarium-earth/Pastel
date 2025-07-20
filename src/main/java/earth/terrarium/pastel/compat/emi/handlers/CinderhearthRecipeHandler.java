package earth.terrarium.pastel.compat.emi.handlers;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.recipe.VanillaEmiRecipeCategories;
import dev.emi.emi.api.recipe.handler.StandardRecipeHandler;
import earth.terrarium.pastel.compat.emi.PastelEmiRecipeCategories;
import earth.terrarium.pastel.inventories.CinderhearthScreenHandler;
import net.minecraft.world.inventory.Slot;

import java.util.ArrayList;
import java.util.List;

public class CinderhearthRecipeHandler implements StandardRecipeHandler<CinderhearthScreenHandler> {
    @Override
    public List<Slot> getInputSources(CinderhearthScreenHandler handler) {
        List<Slot> slots = new ArrayList<>();
        slots.add(handler.getSlot(2));
        slots.addAll(handler.slots.subList(11, 47));
        return slots;
    }

    @Override
    public List<Slot> getCraftingSlots(CinderhearthScreenHandler handler) {
        return List.of(handler.getSlot(2));
    }

    @Override
    public boolean supportsRecipe(EmiRecipe recipe) {
        EmiRecipeCategory category = recipe.getCategory();
        return
            (category == PastelEmiRecipeCategories.CINDERHEARTH || category == VanillaEmiRecipeCategories.BLASTING) &&
            recipe.supportsRecipeTree();
    }
}
