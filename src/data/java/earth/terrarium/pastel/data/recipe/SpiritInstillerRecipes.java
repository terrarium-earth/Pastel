package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.SpiritInstillerRecipeBuilder;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.HardcorePlayerRevivalRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.MemoryToHeadRecipe;
import earth.terrarium.pastel.recipe.spirit_instiller.dynamic.spawner_manipulation.*;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class SpiritInstillerRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "spirit_instiller");

        root(pfx);
        spawner(pfx.subPrefix("spawner"));
        secret(pfx.subPrefix("secret"));
    }

    private static void secret(PrefixHelper pfx) {
        // NOTE: this has a hint that isn't actually used due to the codec
        // not accepting it
        // lang string
        // recipe.pastel.spirit_instiller.germinated_jade_vine_crossbreeding.hint
        pfx.generateRecipe(
                "germiniated_jade_vine_crossbreeding",
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.VEGETAL, 8),
                        SizedIngredient.of(PastelBlocks.NEPHRITE_BLOSSOM_BULB, 1),
                        SizedIngredient.of(PastelBlocks.JADEITE_LOTUS_BULB, 1),
                        new ItemStack(PastelItems.GERMINATED_JADE_VINE_BULB.asItem())
                )
                        .craftingTime(200)
                        .experience(2.0f)
                        .secret(true)
        );

        // Also has a unused hint:
        // recipe.pastel.spirit_instiller.chorus_flower.hint
        pfx.generateRecipe(
            "chorus_flower",
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.VEGETAL, 8),
                        SizedIngredient.of(Items.ECHO_SHARD, 1),
                        SizedIngredient.of(PastelBlocks.RESONANT_LILY.asItem(), 1),
                        new ItemStack(Items.CHORUS_FLOWER)
                )
                        .craftingTime(1200)
                        .experience(10.0f)
                        .secret(true)
        );
    }

    private static void spawner(PrefixHelper pfx) {
        pfx.generateDynamicRecipe(
                "spawner_creature_change",
                new SpawnerCreatureChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_max_nearby_entities_change",
                new SpawnerMaxNearbyEntitiesChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_required_player_range_change",
                new SpawnerRequiredPlayerRangeChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_spawn_count_change",
                new SpawnerSpawnCountChangeRecipe()
        );

        pfx.generateDynamicRecipe(
                "spawner_spawn_delay_change",
                new SpawnerSpawnDelayChangeRecipe()
        );
    }

    private static void root(PrefixHelper pfx) {
        pfx.generateDynamicRecipe(
                "hardcore_player_revival",
                new HardcorePlayerRevivalRecipe()
        );

        pfx.generateDynamicRecipe(
                "memory_to_head",
                new MemoryToHeadRecipe()
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.VEGETAL, 8),
                        SizedIngredient.of(PastelBlocks.RESONANT_LILY, 1),
                        SizedIngredient.of(PastelItems.BLOOD_ORCHID_PETAL, 1),
                        new ItemStack(PastelBlocks.BLOOD_ORCHID)
                )
                        .craftingTime(400)
                        .experience(4.0f)
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.BLOOD_ORCHID)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelBlocks.ONYX_BLOCK, 1),
                        SizedIngredient.of(PastelItems.MIDNIGHT_ABERRATION, 1),
                        SizedIngredient.of(PastelItems.MOONSTRUCK_NECTAR, 1),
                        new ItemStack(PastelBlocks.BUDDING_ONYX, 1)
                )
                        .craftingTime(800)
                        .experience(4.0f)
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.BUDDING_ONYX)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.HIBERNATING_JADE_VINE_BULB, 1),
                        SizedIngredient.of(PastelItems.LIQUID_CRYSTAL_BUCKET, 1),
                        SizedIngredient.of(PastelItems.VEGETAL, 16),
                        new ItemStack(PastelItems.GERMINATED_JADE_VINE_BULB.asItem())
                )
                        .craftingTime(600)
                        .experience(20.0f)
                        .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_HIBERNATING_JADE_VINE_BULB)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(Items.NAUTILUS_SHELL, 1),
                        SizedIngredient.of(PastelItems.MOONSTRUCK_NECTAR, 1),
                        SizedIngredient.of(PastelItems.MERMAIDS_GEM, 1),
                        new ItemStack(Items.HEART_OF_THE_SEA)
                )
                        .craftingTime(200)
                        .experience(2.0f)
        );

        pfx.generateAutoNamedRecipe(
                new SpiritInstillerRecipeBuilder(
                        SizedIngredient.of(PastelItems.KNOTTED_SWORD, 1),
                        SizedIngredient.of(PastelItems.EVERNECTAR, 1),
                        SizedIngredient.of(PastelItems.AETHER_VESTIGES, 1),
                        new ItemStack(PastelItems.NECTAR_LANCE.asItem())
                )
                        .craftingTime(1200)
                        .experience(16.0f)
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.NECTAR_LANCE)
        );
    }
}
