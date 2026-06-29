package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.components.WithMilkComponent;
import earth.terrarium.pastel.data.recipe.builder.potion_workshop.PotionWorkshopCraftingBuilder;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.crafting.SizedIngredient;

public class PotionWorkshopCraftingRecipes {

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "potion_workshop_crafting");

        var milkies = DataComponentPatch
            .builder()
            .set(PastelDataComponentTypes.WITH_MILK, new WithMilkComponent())
            .build();

        // TODO: convert color numbers to hex
        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.AZALEA_TEA.asItem())
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.AZALEA_TEA)
                    .group("azalea_tea")
                    .ingredient1(SizedIngredient.of(Items.FLOWERING_AZALEA, 1))
                    .ingredient2(SizedIngredient.of(PastelItems.NIGHTDEW_SPROUT, 1))
                    .color(9997393)
            );

        pfx
            .generateRecipe(
                "azalea_tea_milk",
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.AZALEA_TEA, 1, milkies)
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.AZALEA_TEA)
                    .group("azalea_tea")
                    .secret(true)
                    .ingredient1(Items.FLOWERING_AZALEA)
                    .ingredient2(PastelItems.NIGHTDEW_SPROUT)
                    .ingredient3(Tags.Items.BUCKETS_MILK)
                    .color(9997393)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.BLOODBOIL_SYRUP.asItem())
                )
                    .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_BLOOD_ORCHID_PETAL)
                    .color(9568256)
                    .ingredient1(PastelItems.BLOOD_ORCHID_PETAL)
                    .ingredient2(PastelItems.QUITOXIC_POWDER)
                    .ingredient3(Items.SUGAR)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(PastelItems.VEGETAL, 1),
                    new ItemStack(Items.BONE_MEAL, 24)
                )
                    .dontConsumeBaseIngredient()
                    .color(15852761)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Resources.FERTILIZER)
                    .ingredient1(PastelItems.ASH_FLAKES, 2)
                    .ingredient2(PastelItems.FISSURE_PLUM)
                    .ingredient3(PastelItems.MILKY_RESIN)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.DEMON_TEA.asItem())
                )
                    .color(10027008)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.DEMON_TEA)
                    .ingredient1(PastelItems.JADE_PETALS)
                    .ingredient2(PastelItems.BLOODBOIL_SYRUP)
            );

        pfx
            .generateRecipe(
                "demon_tea_milk",
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.DEMON_TEA, 1, milkies)
                )
                    .color(10027008)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.DEMON_TEA)
                    .ingredient1(PastelItems.JADE_PETALS)
                    .ingredient2(PastelItems.BLOODBOIL_SYRUP)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 3),
                    new ItemStack(Items.DRAGON_BREATH, 3)
                )
                    .requiredExperience(300)
                    .color(13333666)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Items.DRAGON_BREATH)
                    .ingredient1(PastelItems.KNOWLEDGE_GEM)
                    .ingredient2(Items.POPPED_CHORUS_FRUIT)
                    .ingredient3(PastelItems.RAW_BLOODSTONE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.BUCKET, 1),
                    PastelItems.DRAGONROT_BUCKET.toStack()
                )
                    .color(14907183)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.DRAGONROT_BUCKET)
                    .ingredient1(PastelItems.LIGHT_GRAY_PIGMENT)
                    .ingredient2(PastelItems.GRAY_PIGMENT)
                    .ingredient3(PastelItems.BONE_ASH)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(Items.EXPERIENCE_BOTTLE)
                )
                    .color(11665152)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Items.EXPERIENCE_BOTTLE)
                    .requiredExperience(10)
                    .ingredient1(PastelItems.KNOWLEDGE_GEM)
                    .ingredient2(PastelItems.PURPLE_PIGMENT)
                    .ingredient3(PastelItems.LIME_PIGMENT)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    PastelItems.GLISTERING_JELLY_TEA.toStack()
                )
                    .color(13803816)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.GLISTERING_JELLY_TEA)
                    .ingredient1(PastelItems.JADE_JELLY)
                    .ingredient2(Items.GLISTERING_MELON_SLICE)
                    .group("glistering_jelly_tea")
            );

        pfx
            .generateRecipe(
                "glistering_jelly_tea_milk",
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.GLISTERING_JELLY_TEA, 1, milkies)
                )
                    .color(13803816)
                    .secret(true)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.GLISTERING_JELLY_TEA)
                    .ingredient1(PastelItems.JADE_JELLY)
                    .ingredient2(Items.GLISTERING_MELON_SLICE)
                    .ingredient3(Tags.Items.BUCKETS_MILK)
                    .group("glistering_jelly_tea")
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    PastelItems.GOLDEN_BRISTLE_TEA.toStack()
                )
                    .color(15853164)
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.BREWERS_HANDBOOK)
                    .ingredient1(PastelItems.SAWBLADE_HOLLY_BERRY)
                    .ingredient2(Items.HONEY_BOTTLE)
                    // deserts
                    .group("deserts")
            );

        pfx
            .generateRecipe(
                "golden_bristle_tea_milk",
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.GOLDEN_BRISTLE_TEA, 1, milkies)
                )
                    .color(15853164)
                    .secret(true)
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.BREWERS_HANDBOOK)
                    .ingredient1(PastelItems.SAWBLADE_HOLLY_BERRY)
                    .ingredient2(Items.HONEY_BOTTLE)
                    .ingredient3(Tags.Items.BUCKETS_MILK)
                    // deserts
                    .group("deserts")
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(Items.HONEY_BOTTLE)
                )
                    .color(16697227)
                    .ingredient1(Items.HONEYCOMB)
                    .ingredient2(PastelItems.ORANGE_PIGMENT)
                    .ingredient3(PastelItems.GREEN_PIGMENT)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    PastelItems.HOT_CHOCOLATE.toStack()
                )
                    .color(9128969)
                    .ingredient1(Tags.Items.BUCKETS_MILK)
                    .ingredient2(PastelItems.FRESH_CHOCOLATE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.BUCKET, 1),
                    PastelItems.HUMUS_BUCKET.toStack()
                )
                    .color(4268288)
                    .ingredient1(Items.DIRT)
                    .ingredient2(Items.MUD)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(PastelItems.PEACH_JAM, 1),
                    PastelItems.JUNKET.toStack()
                )
                    // deserts
                    .group("deserts")
                    .color(16754073)
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMPERIAL_COOKBOOK)
                    .ingredient1(PastelItems.CLOTTED_CREAM)
                    .ingredient2(PastelItems.JADE_PETALS)
                    .ingredient3(PastelItems.JADEITE_PETALS)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 2),
                    PastelItems.KARAK_CHAI.toStack(2)
                )
                    .color(16758914)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.KARAK_CHAI)
                    .ingredient1(PastelItems.MILKY_RESIN)
                    .ingredient2(PastelItems.NIGHTDEW_SPROUT)
                    .ingredient3(Items.BEETROOT_SEEDS)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(PastelBlocks.WET_LAVA_SPONGE, 1),
                    new ItemStack(Items.LAVA_BUCKET)
                )
                    .dontConsumeBaseIngredient()
                    .color(13387304)
                    .ingredient1(Items.BUCKET)
                    .ingredient2(Items.MAGMA_BLOCK)
                    .ingredient3(PastelItems.ORANGE_PIGMENT)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.BUCKET, 1),
                    PastelItems.LIQUID_CRYSTAL_BUCKET.toStack()
                )
                    .color(11972050)
                    .ingredient1(PastelItems.PINK_PIGMENT)
                    .ingredient2(PastelItems.YELLOW_PIGMENT)
                    .ingredient3(PastelItems.SHIMMERSTONE_GEM)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.BUCKET, 1),
                    PastelItems.MIDNIGHT_SOLUTION_BUCKET.toStack()
                )
                    .color(526373)
                    .ingredient1(PastelItems.MIDNIGHT_CHIP)
                    .ingredient2(PastelItems.QUITOXIC_POWDER)
                    .ingredient3(PastelItems.BLACK_PIGMENT)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.MIDNIGHT_SOLUTION)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.BUCKET, 1),
                    new ItemStack(Items.MILK_BUCKET)
                )
                    .color(16774110)
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_MILKY_RESIN)
                    .ingredient1(PastelItems.MILKY_RESIN)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.SUGAR, 1),
                    PastelItems.PEACH_CREAM.toStack()
                )
                    .color(16754073)
                    // deserts
                    .group("deserts")
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.BREWERS_HANDBOOK)
                    .ingredient1(PastelItems.CLOTTED_CREAM)
                    .ingredient2(PastelItems.GLASS_PEACH)
                    .ingredient3(PastelItems.MYCEYLON)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.BUCKET, 1),
                    new ItemStack(Items.POWDER_SNOW_BUCKET)
                )
                    .color(14217456)
                    .ingredient1(Items.SNOW_BLOCK)
                    .ingredient2(PastelItems.CYAN_PIGMENT)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    PastelItems.RESTORATION_TEA.toStack()
                )
                    .group("restoration_tea")
                    .color(5807774)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.RESTORATION_TEA)
                    .ingredient1(PastelItems.JADE_PETALS)
                    .ingredient2(Items.COCOA_BEANS)
            );

        pfx
            .generateRecipe(
                "restoration_tea_milk",
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.GLASS_BOTTLE, 1),
                    new ItemStack(PastelItems.RESTORATION_TEA, 1, milkies)
                )
                    .group("restoration_tea")
                    .secret(true)
                    .color(5807774)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.RESTORATION_TEA)
                    .ingredient1(PastelItems.JADE_PETALS)
                    .ingredient2(Items.COCOA_BEANS)
                    .ingredient3(Tags.Items.BUCKETS_MILK)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(PastelBlocks.JADEITE_LOTUS_FLOWER, 1),
                    PastelItems.SEDATIVES.toStack()
                )
                    // deserts
                    .group("deserts")
                    .color(9753087)
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.BREWERS_HANDBOOK)
                    .ingredient1(PastelItems.ALOE_LEAF)
                    .ingredient2(PastelItems.FROSTBITE_ESSENCE, 2)
                    .ingredient3(PastelItems.NIGHTDEW_SPROUT)
                    .dontConsumeBaseIngredient()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopCraftingBuilder(
                    SizedIngredient.of(Items.BUCKET, 1),
                    new ItemStack(Items.WATER_BUCKET)
                )
                    .color(2451682)
                    .ingredient1(PastelItems.LIGHT_BLUE_PIGMENT, 4)
            );

    }
}
