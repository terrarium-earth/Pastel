package earth.terrarium.pastel.data.recipe.builder.cantrip;

import earth.terrarium.pastel.recipe.cantrip.HealingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class HealingRecipeBuilder extends CantripRecipeBuilder<HealingRecipeBuilder> {

    public HealingRecipeBuilder(Ingredient input, ItemStack result) {
        super(input, result);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe =
                new HealingRecipe(
                        this.group,
                        this.secret,
                        this.getRequiredAdvancement(),
                        this.input,
                        this.result
                );
        saveHelper(recipeOutput, id, recipe);
    }
}
