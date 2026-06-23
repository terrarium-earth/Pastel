package earth.terrarium.pastel.data.recipe.builder.cantrip;

import com.mojang.datafixers.util.Function5;
import earth.terrarium.pastel.data.recipe.builder.GatedIORecipe;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.data.recipe.builder.SimpleRecipeBuilder;
import earth.terrarium.pastel.recipe.cantrip.DegradingRecipe;
import earth.terrarium.pastel.recipe.cantrip.HealingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public final class CantripRecipeBuilder extends GatedIORecipe<CantripRecipeBuilder> {
    private final CantripConstructor constructor;

    private CantripRecipeBuilder(Ingredient input, ItemStack result, CantripConstructor cons) {
        super(input, result);
        this.constructor = cons;
    }

    public static CantripRecipeBuilder healing(Ingredient input, ItemStack result) {
        return new CantripRecipeBuilder(input, result, HealingRecipe::new);
    }

    public static CantripRecipeBuilder degrading(Ingredient input, ItemStack result) {
        return new CantripRecipeBuilder(input, result, DegradingRecipe::new);
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe = constructor.make(
                this.group,
                this.secret,
                this.getRequiredAdvancement(),
                this.input,
                this.result);

        saveHelper(recipeOutput, id, recipe);
    }

    public interface CantripConstructor {
        Recipe<?> make(
                String group,
                boolean secret,
                Optional<ResourceLocation> requiredAdvancement,
                @NotNull Ingredient input,
                ItemStack stack);
    }

}
