package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.fluid_converting.FluidConvertingRecipeBuilder;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;

public class FluidConvertingRecipes {

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "");

        dragonrot(pfx.subPrefix("dragonrot_converting"));
        humus(pfx.subPrefix("humus_converting"));
        liquidCrystal(pfx.subPrefix("liquid_crystal_converting"));
    }

    private static void dragonrot(PrefixHelper pfx) {
        // it's ash.
        pfx.generateRecipe(
                "ash_from_bone_ash",
                FluidConvertingRecipeBuilder.dragonrot(Ingredient.of(PastelItems.BONE_ASH), new ItemStack(PastelItems.ASH_FLAKES.asItem()))
                        .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
        );

        pfx.generateRecipe(
                "blackslag_from_deepslate",
                FluidConvertingRecipeBuilder.dragonrot(Ingredient.of(Items.DEEPSLATE), new ItemStack(PastelBlocks.BLACKSLAG))
        );

        pfx.generateRecipe(
                "blackstone_from_stone",
                FluidConvertingRecipeBuilder.dragonrot(Ingredient.of(Items.STONE), new ItemStack(Items.BLACKSTONE))
        );

        pfx.generateRecipe(
                "lilypad_from_dripleaf",
                FluidConvertingRecipeBuilder.dragonrot(Ingredient.of(Items.BIG_DRIPLEAF), new ItemStack(Items.LILY_PAD))
        );

        // TIME TO DEEVILIZE!
        pfx.generateRecipe(
                "meat_rotting",
                FluidConvertingRecipeBuilder.dragonrot(
                        DifferenceIngredient.of(Ingredient.of(ItemTags.MEAT), Ingredient.of(Items.ROTTEN_FLESH)),
                        new ItemStack(Items.ROTTEN_FLESH)
                )
        );

        pfx.generateRecipe(
                "mud_from_sand",
                FluidConvertingRecipeBuilder.dragonrot(
                        Ingredient.of(Items.SAND),
                        new ItemStack(Items.MUD)
                )
        );
    }

    private static void humus(PrefixHelper pfx) {
        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.humus(
                        Ingredient.of(Items.ROTTEN_FLESH),
                        new ItemStack(Items.RABBIT_HIDE)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.humus(
                        Ingredient.of(PastelBlocks.ASH.asItem()),
                        new ItemStack(PastelBlocks.SLUSH.asItem())
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_ASH_AND_SLUSH)
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.humus(
                        Ingredient.of(Items.WHEAT),
                        new ItemStack(Items.STRING, 2)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.humus(
                        Ingredient.of(PastelBlocks.FOUR_LEAF_CLOVER),
                        new ItemStack(Items.TURTLE_SCUTE)
                )
                        .requiredAdvancement(PastelAdvancements.COLLECT_FOUR_LEAF_CLOVER)
        );
    }

    private static void liquidCrystal(PrefixHelper pfx) {
        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.liquidCrystal(
                        Ingredient.of(PastelItemTags.RESPLENDENT_FEATHERS),
                        new ItemStack(PastelItems.RAW_BLOODSTONE.asItem())
                )
                        .requiredAdvancement(PastelAdvancements.PLUCK_RESPLENDENT_FEATHER)
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.liquidCrystal(
                        Ingredient.of(ItemTags.FLOWERS),
                        new ItemStack(PastelBlocks.RESONANT_LILY)
                )
                        .secret(true)
                        .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_RESONANT_LILY)
        );
    }
}
