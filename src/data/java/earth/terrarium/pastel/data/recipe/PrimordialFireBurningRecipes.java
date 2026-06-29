package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.SimpleGatedIORecipeBuilder;
import earth.terrarium.pastel.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import earth.terrarium.pastel.recipe.primordial_fire_burning.dynamic.EnchantedBookUnsoulingRecipe;
import earth.terrarium.pastel.recipe.primordial_fire_burning.dynamic.MemoryDementiaRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class PrimordialFireBurningRecipes {
    private static SimpleGatedIORecipeBuilder primordialFire(Ingredient input, ItemStack output) {
        return new SimpleGatedIORecipeBuilder(input, output, PrimordialFireBurningRecipe::new);
    }

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "primordial_fire_burning");

        pfx
            .generateRecipe(
                "campfire_unsouling",
                primordialFire(
                    Ingredient.of(Items.SOUL_CAMPFIRE),
                    new ItemStack(Items.CAMPFIRE)
                )
            );

        pfx
            .generateAutoNamedRecipe(
                primordialFire(
                    Ingredient.of(Items.TORCH),
                    new ItemStack(PastelBlocks.PRIMORDIAL_TORCH)
                )
                    .secret(true)
            );

        pfx
            .generateRecipe(
                "soul_lantern_unsouling",
                primordialFire(
                    Ingredient.of(Items.SOUL_LANTERN),
                    new ItemStack(Items.LANTERN)
                )
            );

        pfx
            .generateRecipe(
                "soul_sand_unsouling",
                primordialFire(
                    Ingredient.of(Items.SOUL_SAND),
                    new ItemStack(Items.SAND)
                )
            );

        pfx
            .generateRecipe(
                "soul_soil_unsouling",
                primordialFire(
                    Ingredient.of(Items.SOUL_SOIL),
                    new ItemStack(Items.DIRT)
                )
            );

        pfx
            .generateRecipe(
                "torch_unsouling",
                primordialFire(
                    Ingredient.of(Items.SOUL_TORCH),
                    new ItemStack(Items.TORCH)
                )
            );

        pfx
            .generateDynamicRecipe(
                "enchanted_book_unsouling",
                new EnchantedBookUnsoulingRecipe(lookup)
            );

        pfx
            .generateDynamicRecipe(
                "memory_dementia",
                new MemoryDementiaRecipe()
            );
    }
}
