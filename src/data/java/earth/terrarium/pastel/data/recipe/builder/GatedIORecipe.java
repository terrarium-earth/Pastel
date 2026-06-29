package earth.terrarium.pastel.data.recipe.builder;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class GatedIORecipe<C extends GatedIORecipe<C>> extends GatedRecipeBuilder<C> {
    protected final Ingredient input;

    public GatedIORecipe(Ingredient input, ItemStack result) {
        super(result);
        this.input = input;
    }
}
