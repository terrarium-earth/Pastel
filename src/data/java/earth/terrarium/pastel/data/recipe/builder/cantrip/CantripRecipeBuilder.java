package earth.terrarium.pastel.data.recipe.builder.cantrip;

import com.mojang.datafixers.util.Function5;
import earth.terrarium.pastel.data.recipe.builder.GatedIORecipe;
import earth.terrarium.pastel.data.recipe.builder.GatedRecipeBuilder;
import earth.terrarium.pastel.data.recipe.builder.SimpleGatedIORecipeBuilder;
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

public final class CantripRecipeBuilder {

    public static SimpleGatedIORecipeBuilder healing(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, HealingRecipe::new);
    }

    public static SimpleGatedIORecipeBuilder degrading(Ingredient input, ItemStack result) {
        return new SimpleGatedIORecipeBuilder(input, result, DegradingRecipe::new);
    }

}
