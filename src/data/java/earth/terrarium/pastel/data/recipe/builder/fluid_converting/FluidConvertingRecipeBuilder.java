package earth.terrarium.pastel.data.recipe.builder.fluid_converting;

import earth.terrarium.pastel.data.recipe.builder.GatedIORecipe;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.HumusConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;

import java.util.Optional;

public final class FluidConvertingRecipeBuilder extends GatedIORecipe<FluidConvertingRecipeBuilder> {
    private final FluidConvertingConstructor constructor;

    private FluidConvertingRecipeBuilder(Ingredient input, ItemStack result, FluidConvertingConstructor cons) {
        super(input, result);
        this.constructor = cons;
    }

    @Override
    public void save(RecipeOutput recipeOutput, ResourceLocation id) {
        var recipe = constructor.make(
                this.group,
                this.secret,
                this.getRequiredAdvancement(),
                this.input,
                this.result
        );
        saveHelper(recipeOutput, id, recipe);
    }

    public static FluidConvertingRecipeBuilder dragonrot(Ingredient input, ItemStack result) {
        return new FluidConvertingRecipeBuilder(input, result, DragonrotConvertingRecipe::new);
    }

    public static FluidConvertingRecipeBuilder humus(Ingredient input, ItemStack result) {
        return new FluidConvertingRecipeBuilder(input, result, HumusConvertingRecipe::new);
    }

    public static FluidConvertingRecipeBuilder liquidCrystal(Ingredient input, ItemStack result) {
        return new FluidConvertingRecipeBuilder(input, result, LiquidCrystalConvertingRecipe::new);
    }

    public static FluidConvertingRecipeBuilder midnightSolution(Ingredient input, ItemStack result) {
        return new FluidConvertingRecipeBuilder(input, result, MidnightSolutionConvertingRecipe::new);
    }

    public interface FluidConvertingConstructor {
        Recipe<?> make(String group, boolean secret, Optional<ResourceLocation> requiredAdvancement, Ingredient input, ItemStack output);
    }
}
