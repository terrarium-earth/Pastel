package earth.terrarium.pastel.data.recipe.builder.fluid_converting;

import earth.terrarium.pastel.data.recipe.builder.SimpleGatedIORecipeBuilder;
import earth.terrarium.pastel.recipe.fluid_converting.DragonrotConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.HumusConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.LiquidCrystalConvertingRecipe;
import earth.terrarium.pastel.recipe.fluid_converting.MidnightSolutionConvertingRecipe;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public final class FluidConvertingRecipeBuilder {

    public static SimpleGatedIORecipeBuilder dragonrot(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, DragonrotConvertingRecipe::new);
    }

    public static SimpleGatedIORecipeBuilder humus(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, HumusConvertingRecipe::new);
    }

    public static SimpleGatedIORecipeBuilder liquidCrystal(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, LiquidCrystalConvertingRecipe::new);
    }

    public static SimpleGatedIORecipeBuilder midnightSolution(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, MidnightSolutionConvertingRecipe::new);
    }

}
