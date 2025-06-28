package earth.terrarium.pastel.recipe.enchanter;

import earth.terrarium.pastel.blocks.enchanter.EnchanterBlockEntity;
import earth.terrarium.pastel.recipe.GatedPastelRecipe;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.Optional;

public abstract class EnchanterRecipe extends GatedPastelRecipe<RecipeInput> {

    protected EnchanterRecipe(String group, boolean secret, Optional<ResourceLocation> requiredAdvancementIdentifier) {
        super(group, secret, requiredAdvancementIdentifier);
    }

    public abstract int getCraftingTime(double scaling);

    public abstract boolean noDiscounts();

    public abstract void consumeIngredients(EnchanterBlockEntity inv, HolderLookup.Provider lookup, double scaling);
}
