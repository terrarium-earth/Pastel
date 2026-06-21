package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.InkConvertingRecipeBuilder;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.crafting.Ingredient;

public class InkConversionRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "ink_converting");
        PastelInkColorCollection.VALUES.forEach(color -> {
            var dye = PastelInkColorCollection.DYE_ITEMS.pick(color);
            var pigment = PastelItems.PIGMENTS.pick(color);
            var pigmentBlock = PastelBlocks.PIGMENT_BLOCKS.pick(color);
            var name = PastelInkColorCollection.NAMES.pick(color);
            var unlock = PastelAdvancements.Hidden.CollectPigment.VALUES.pick(color);


            pfx.generateRecipe(
                    "dye/" + name,
                    new InkConvertingRecipeBuilder(color, Ingredient.of(dye), 5)
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "pigment/" + name,
                    new InkConvertingRecipeBuilder(color, Ingredient.of(pigment), 100)
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "pigment_blocks/" + name,
                    new InkConvertingRecipeBuilder(color, Ingredient.of(pigmentBlock), 900)
                            .requiredAdvancement(unlock)
            );
        });

    }

}
