package earth.terrarium.pastel.data.recipe.builder.cantrip;

import earth.terrarium.pastel.data.recipe.builder.SimpleGatedIORecipeBuilder;
import earth.terrarium.pastel.recipe.cantrip.DegradingRecipe;
import earth.terrarium.pastel.recipe.cantrip.HealingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class CantripRecipeBuilder {

    public static SimpleGatedIORecipeBuilder healing(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, HealingRecipe::new);
    }

    public static SimpleGatedIORecipeBuilder degrading(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, DegradingRecipe::new);
    }

}
