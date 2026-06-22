package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.InkConvertingRecipeBuilder;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
// import vazkii.botania.common.item.BotaniaItems;

public class InkConversionRecipes {
    /*
    private static final PastelInkColorCollection<Item> PETALS =
        new PastelInkColorCollection<>(
                BotaniaItems.cyanPetal,
                BotaniaItems.lightBluePetal,
                BotaniaItems.bluePetal,
                BotaniaItems.purplePetal,
                BotaniaItems.magentaPetal,
                BotaniaItems.pinkPetal,
                BotaniaItems.redPetal,
                BotaniaItems.orangePetal,
                BotaniaItems.yellowPetal,
                BotaniaItems.limePetal,
                BotaniaItems.greenPetal,
                BotaniaItems.brownPetal,
                BotaniaItems.blackPetal,
                BotaniaItems.grayPetal,
                BotaniaItems.lightGrayPetal,
                BotaniaItems.whitePetal
        );

     */

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "ink_converting");
        var botaniaPrefix = pfx.modLoaded("botania");
        PastelInkColorCollection.VALUES.forEach(color -> {
            var dye = PastelInkColorCollection.DYE_ITEMS.pick(color);
            var pigment = PastelItems.PIGMENTS.pick(color);
            var pigmentBlock = PastelBlocks.PIGMENT_BLOCKS.pick(color);
            // var petal = PETALS.pick(color);
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

            /*
            botaniaPrefix.generateRecipe(
                    "petal/" + name,
                    new InkConvertingRecipeBuilder(color, Ingredient.of(petal), 25)
            );

             */
        });

    }

}
