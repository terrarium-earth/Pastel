package earth.terrarium.pastel.data.recipe.builder;

import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

public final class SimpleGatedIORecipeBuilder extends GatedIORecipe<SimpleGatedIORecipeBuilder> {
    private final Constructor constructor;

    public SimpleGatedIORecipeBuilder(Ingredient input, ItemStack output, Constructor constructor) {
        super(input, output);
        this.constructor = constructor;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        saveHelperGated(
            recipeOutput,
            id,
            daId -> constructor.make(this.group, this.secret, daId, this.input, this.result)
        );
    }

    public interface Constructor {
        Recipe<?> make(
            String group,
            boolean secret,
            Optional<ResourceLocation> requiredAdvancement,
            Ingredient input,
            ItemStack output
        );
    }
}
