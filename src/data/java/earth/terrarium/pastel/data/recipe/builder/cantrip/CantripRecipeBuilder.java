package earth.terrarium.pastel.data.recipe.builder.cantrip;

import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.data.recipe.builder.SimpleRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public abstract class CantripRecipeBuilder<C extends CantripRecipeBuilder<C>> extends GatedRecipeBuilder<C> {
    protected final Ingredient input;


    public CantripRecipeBuilder(Ingredient input, ItemStack result) {
        super(result);
        this.input = input;
    }

}
