package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.cantrip.CantripRecipeBuilder;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

import static java.util.Map.entry;

public class HealingDegradingRecipes {

    private static final Map<Block, Block> HEALING_DEGRADING_PAIRS = Map
        .ofEntries(
            entry(Blocks.SAND, Blocks.GRAVEL),
            entry(Blocks.GRAVEL, Blocks.COBBLESTONE),
            entry(Blocks.COBBLESTONE, Blocks.STONE),
            entry(Blocks.STONE, Blocks.COBBLED_DEEPSLATE),
            entry(Blocks.COBBLED_DEEPSLATE, Blocks.DEEPSLATE),

            // further cobbleing
            entry(Blocks.COBBLESTONE_SLAB, Blocks.STONE_SLAB),
            entry(Blocks.COBBLESTONE_STAIRS, Blocks.STONE_STAIRS),

            // cracked blocks
            entry(Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICKS),
            entry(Blocks.CRACKED_DEEPSLATE_TILES, Blocks.DEEPSLATE_TILES),
            entry(Blocks.CRACKED_NETHER_BRICKS, Blocks.NETHER_BRICKS),
            entry(Blocks.CRACKED_STONE_BRICKS, Blocks.STONE_BRICKS),
            entry(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS),
            entry(Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS),
            entry(PastelBlocks.CRACKED_BASALT_BRICKS.get(), PastelBlocks.BASALT_BRICKS.get()),
            entry(PastelBlocks.CRACKED_BASALT_TILES.get(), PastelBlocks.BASALT_TILES.get()),
            entry(PastelBlocks.CRACKED_BLACKSLAG_BRICKS.get(), PastelBlocks.BLACKSLAG_BRICKS.get()),
//      entry(PastelBlocks.CRACKED_DRAGONBONE.get(), PastelBlocks.DRAGONBONE.get()), hahaha. no.
            entry(PastelBlocks.CRACKED_CALCITE_BRICKS.get(), PastelBlocks.CALCITE_BRICKS.get()),
            entry(PastelBlocks.CRACKED_CALCITE_TILES.get(), PastelBlocks.CALCITE_TILES.get())
//      entry(PastelBlocks.CRACKED_END_PORTAL_FRAME.get(), Blocks.END_PORTAL_FRAME) // nope
        );

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "cantrip");
        var degradePfx = pfx.subPrefix("degrading");
        var healingPfx = pfx.subPrefix("healing");

        HEALING_DEGRADING_PAIRS.forEach((bad, good) -> {
            var badName = BuiltInRegistries.BLOCK.getKey(bad).getPath();
            var goodName = BuiltInRegistries.BLOCK.getKey(good).getPath();
            degradePfx
                .generateRecipe(
                    badName + "_from_" + goodName,
                    CantripRecipeBuilder
                        .degrading(
                            Ingredient.of(good),
                            new ItemStack(bad, 1)
                        )
                );

            healingPfx
                .generateRecipe(
                    goodName + "_from_" + badName,
                    CantripRecipeBuilder
                        .healing(
                            Ingredient.of(bad),
                            new ItemStack(good, 1)
                        )
                );
        });
    }

}
