package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.blocks.weathering.Weathering;
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
import net.minecraft.world.level.block.WeatheringCopper;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.DifferenceIngredient;

public class FluidConvertingRecipes {

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "");

        dragonrot(pfx.subPrefix("dragonrot_converting"));
        humus(pfx.subPrefix("humus_converting"));
        liquidCrystal(pfx.subPrefix("liquid_crystal_converting"));
        midnightSolution(pfx.subPrefix("midnight_solution_converting"));
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

    private static void midnightSolution(PrefixHelper pfx) {
        copperOxidizing(pfx.subPrefix("copper"));
        shaleClayOxidizing(pfx.subPrefix("shale_clay"));

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.STONE),
                        new ItemStack(Items.BLACKSTONE)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.RED_MUSHROOM),
                        new ItemStack(Items.CRIMSON_FUNGUS)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.OBSIDIAN),
                        new ItemStack(Items.CRYING_OBSIDIAN)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.GRAVEL),
                        new ItemStack(Items.FLINT)
                )
        );

        pfx.generateAutoNamedRecipe(
                // EVIL recipe
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(PastelItems.GILDED_BOOK),
                        new ItemStack(Items.BOOK)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.GLOWSTONE_DUST),
                        new ItemStack(Items.GUNPOWDER)
                )
        );

        pfx.generateRecipe(
                "golden_apple_disenchanting",
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.ENCHANTED_GOLDEN_APPLE),
                        new ItemStack(Items.GOLDEN_APPLE)
                )
        );

        pfx.generateRecipe(
                "golden_carrot_disenchanting",
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(PastelItems.ENCHANTED_GOLDEN_CARROT),
                        new ItemStack(Items.GOLDEN_CARROT)
                )
        );

        // This is a new recipe, but I saw that the enchanted star candy was named, well,
        // "enchanted star candy" and couldn't resist
        /*
        pfx.generateRecipe(
                "star_candy_disenchanting",
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(PastelItems.ENCHANTED_STAR_CANDY),
                        new ItemStack(PastelItems.STAR_CANDY.asItem())
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_ENCHANTED_STAR_CANDY)
        );

         */

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        DifferenceIngredient.of(
                                Ingredient.of(Tags.Items.MUSIC_DISCS),
                                Ingredient.of(Items.MUSIC_DISC_11)
                        ),
                        new ItemStack(Items.MUSIC_DISC_11)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.GOLD_ORE),
                        new ItemStack(Items.NETHER_GOLD_ORE)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.COBBLESTONE),
                        new ItemStack(Items.NETHERRACK)
                )
        );

        pfx.generateRecipe(
                "slimeball_from_clay_ball",
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.CLAY_BALL),
                        new ItemStack(Items.SLIME_BALL)
                )
        );

        pfx.generateRecipe(
                "slimeball_from_dried_kelp_block",
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.DRIED_KELP_BLOCK),
                        new ItemStack(Items.SLIME_BALL)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.CAMPFIRE),
                        new ItemStack(Items.SOUL_CAMPFIRE)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(ItemTags.SAND),
                        new ItemStack(Items.SOUL_SAND)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(ItemTags.DIRT),
                        new ItemStack(Items.SOUL_SOIL)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(Items.BROWN_MUSHROOM),
                        new ItemStack(Items.WARPED_FUNGUS)
                )
        );

        pfx.generateAutoNamedRecipe(
                FluidConvertingRecipeBuilder.midnightSolution(
                        Ingredient.of(ItemTags.FLOWERS),
                        new ItemStack(Items.WITHER_ROSE)
                )
        );
    }

    private static void copperOxidizing(PrefixHelper pfx) {
        // TODO: Ideally, like create, we'd do something special at runtime to make this apply to ALL oxidizable blocks
        // HOWEVER, because of our seperate classpath for datagen we can't actually do anything like that
        // (not without breaking code seperation/sanity, at least)
        // If we ever get around to doing something like, using create as a reference point is a good idea:
        // https://github.com/Creators-of-Create/Create/blob/mc1.21.1/dev/src/main/java/com/simibubi/create/foundation/data/RuntimeDataGenerator.java
        WeatheringCopper.NEXT_BY_BLOCK.get().forEach((block, nextBlock) -> {
            pfx.generateAutoNamedRecipe(
                    FluidConvertingRecipeBuilder.midnightSolution(
                            Ingredient.of(block.asItem()),
                            new ItemStack(nextBlock, 1)
                    )
            );
        });
    }

    private static void shaleClayOxidizing(PrefixHelper pfx) {
        Weathering.WEATHERING_LEVEL_INCREASES.get().forEach((block, nextBlock) ->
                pfx.generateAutoNamedRecipe(
                        FluidConvertingRecipeBuilder.midnightSolution(
                                Ingredient.of(block.asItem()),
                                new ItemStack(nextBlock, 1)
                        )
                                .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_SHALE_CLAY)
                )
        );
    }
}
