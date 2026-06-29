package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.data.recipe.builder.potion_workshop.PotionWorkshopBrewingBuilder;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;

public class PotionWorkshopBrewingRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "potion_workshop_brewing");

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.ABSORPTION,
                    InkColors.BLUE,
                    4
                )
                    .requiredAdvancement(PastelAdvancements.Midgame.BREW_POTION_IN_POTION_WORKSHOP)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.ONYX_SHARD)
                    .baseDurationTicks(1800)
                    .potencyModifier(1.0f)
                    .notApplicableToPotionFillables()
            );

        // caught with a bad omen
        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.BAD_OMEN,
                    InkColors.BLACK,
                    64
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.BAD_OMEN)
                    .baseDurationTicks(36000)
                    .potencyModifier(0.5f)
                    .notApplicableToTippedArrows()
                    .baseYield(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.BLOODBOIL_SYRUP)
                    .ingredient3(PastelItems.MIDNIGHT_ABERRATION)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.CALMING,
                    InkColors.PURPLE,
                    4
                )
                    .group("sleep_potions")
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.WEAK_SLEEP_EFFECTS)
                    .baseDurationTicks(3600)
                    .potencyModifier(1.5f)
                    .ingredient1(PastelItems.NIGHTDEW_SPROUT)
                    .ingredient2(PastelItems.MOONSTRUCK_NECTAR)
                    .ingredient3(PastelItems.FROSTBITE_ESSENCE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.DARKNESS,
                    InkColors.BLACK,
                    8
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.DARKNESS)
                    .baseDurationTicks(600)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.ECHO_SHARD)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.DEADLY_POISON,
                    InkColors.GREEN,
                    4
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.DEADLY_POISON)
                    .baseDurationTicks(600)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.BONE_ASH)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.ETERNAL_SLUMBER,
                    InkColors.PURPLE,
                    32
                )
                    .group("sleep_potions")
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.STRONG_SLEEP_EFFECTS)
                    .baseDurationTicks(1200)
                    .potencyModifier(0.1f)
                    .potencyHardCap(1)
                    .baseYield(1.0f)
                    .notApplicableToTippedArrows()
                    .ingredient1(PastelItems.NECTARDEW_BURGEON)
                    .ingredient2(PastelItems.JADEITE_PETALS)
                    .ingredient3(PastelItems.RESPLENDENT_FEATHER)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.FATAL_SLUMBER,
                    InkColors.PURPLE,
                    // WOWZA!
                    640
                )
                    .group("sleep_potions")
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.STRONG_SLEEP_EFFECTS)
                    .baseDurationTicks(200)
                    .potencyModifier(0.1f)
                    .notApplicableToPotions()
                    .notApplicableToTippedArrows()
                    .potencyHardCap(1)
                    .baseYield(1.0f)
                    .ingredient1(PastelItems.NECTARDEW_BURGEON)
                    .ingredient2(PastelItems.EVERNECTAR)
                    .ingredient3(PastelItems.MOONSTONE_POWDER)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.FIRE_RESISTANCE,
                    InkColors.ORANGE,
                    4
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(3600)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.MAGMA_CREAM)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.GLOWING,
                    InkColors.GREEN,
                    1
                )
                    .requiredAdvancement(PastelAdvancements.Midgame.BREW_POTION_IN_POTION_WORKSHOP)
                    .baseDurationTicks(1800)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.SHIMMERSTONE_GEM)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.DIG_SPEED,
                    InkColors.MAGENTA,
                    4
                )
                    .requiredAdvancement(PastelAdvancements.Midgame.BREW_POTION_IN_POTION_WORKSHOP)
                    .baseDurationTicks(3600)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.AMETHYST_SHARD)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.HUNGER,
                    InkColors.LIME,
                    2
                )
                    .requiredAdvancement(PastelAdvancements.Midgame.BREW_POTION_IN_POTION_WORKSHOP)
                    .baseDurationTicks(900)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.CITRINE_SHARD)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.IMMUNITY,
                    InkColors.PINK,
                    64
                )
                    // ???
                    .group("sleep_potions")
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.POISONERS_HANDBOOK)
                    .baseDurationTicks(300)
                    .potencyModifier(0.1f)
                    .potencyHardCap(1)
                    .baseYield(1)
                    .ingredient1(PastelItems.NECTARDEW_BURGEON)
                    .ingredient2(PastelItems.MOONSTRUCK_NECTAR)
                    .ingredient3(PastelItems.JADE_JELLY)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.INFESTED,
                    InkColors.LIME,
                    16
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.STONE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.HARM,
                    InkColors.BLACK,
                    16
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(0)
                    .potencyModifier(0.5f)
                    .notApplicableToPotionFillables()
                    .potencyHardCap(4)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.SPIDER_EYE)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateRecipe(
                "instant_damage2",
                new PotionWorkshopBrewingBuilder(
                    MobEffects.HARM,
                    InkColors.BLACK,
                    16
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(0)
                    .potencyModifier(0.5f)
                    .notApplicableToPotionFillables()
                    .potencyHardCap(4)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.GLISTERING_MELON_SLICE)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.HEAL,
                    InkColors.LIME,
                    16
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(0)
                    .potencyModifier(1.0f)
                    .notApplicableToPotionFillables()
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.GLISTERING_MELON_SLICE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.INVISIBILITY,
                    InkColors.GREEN,
                    8
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(3600)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.GOLDEN_CARROT)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.JUMP,
                    InkColors.YELLOW,
                    2
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(3600)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.RABBIT_FOOT)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.LEVITATION,
                    InkColors.BROWN,
                    8
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.LEVITATION)
                    .baseDurationTicks(200)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.PALTAERIA_FRAGMENTS)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.LIFE_DRAIN,
                    InkColors.GRAY,
                    16
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.LIFE_DRAIN)
                    .baseDurationTicks(300)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.DOWNSTONE_FRAGMENTS)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.LUCK,
                    InkColors.LIGHT_BLUE,
                    2
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.LUCK)
                    .baseDurationTicks(6000)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelBlocks.FOUR_LEAF_CLOVER)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.DIG_SLOWDOWN,
                    InkColors.MAGENTA,
                    4
                )
                    .requiredAdvancement(PastelAdvancements.Midgame.BREW_POTION_IN_POTION_WORKSHOP)
                    .baseDurationTicks(3600)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.AMETHYST_SHARD)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.NIGHT_VISION,
                    InkColors.YELLOW,
                    4
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(3600)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.GOLDEN_CARROT)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.OOZING,
                    InkColors.BLUE,
                    16
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.SLIME_BLOCK)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.POISON,
                    InkColors.PINK,
                    2
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(900)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.REGENERATION,
                    InkColors.PINK,
                    8
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(900)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.GHAST_TEAR)
            );

        pfx
            .generateRecipe(
                "regeneration_strong",
                new PotionWorkshopBrewingBuilder(
                    MobEffects.REGENERATION,
                    InkColors.PINK,
                    16
                )
                    .group("vanilla_potions")
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_MILKY_RESIN)
                    .baseDurationTicks(300)
                    .potencyModifier(2.0f)
                    .baseYield(2.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.MILKY_RESIN)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.DAMAGE_RESISTANCE,
                    InkColors.BLUE,
                    8
                )
                    .group("resistance_potions")
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.RESISTANCE)
                    .baseDurationTicks(1800)
                    .potencyModifier(0.25f)
                    .baseYield(1.0f)
                    // NO INVINCE!
                    .potencyHardCap(3)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.MOONSTONE_SHARD)
            );

        pfx
            .generateRecipe(
                // lie
                "saturation",
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.NOURISHING,
                    InkColors.LIME,
                    8
                )
                    .requiredAdvancement(PastelAdvancements.Midgame.BREW_POTION_IN_POTION_WORKSHOP)
                    .baseDurationTicks(900)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.CITRINE_SHARD)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.SCARRED,
                    InkColors.GRAY,
                    4
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.SCARRED)
                    .baseDurationTicks(6000)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.BONE_ASH)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.SLOW_FALLING,
                    InkColors.BROWN,
                    2
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.PHANTOM_MEMBRANE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    InkColors.MAGENTA,
                    2
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(1.5f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.SUGAR)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateRecipe(
                "slowness2",
                new PotionWorkshopBrewingBuilder(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    InkColors.MAGENTA,
                    2
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(2.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.RABBIT_FOOT)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.SOMNOLENCE,
                    InkColors.PURPLE,
                    8
                )
                    .group("sleep_potions")
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.WEAK_SLEEP_EFFECTS)
                    .baseDurationTicks(1200)
                    .potencyModifier(0.75f)
                    .ingredient1(PastelItems.NIGHTDEW_SPROUT)
                    .ingredient2(PastelItems.NEOLITH)
                    .ingredient3(PastelItems.ONYX_SHARD)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.MOVEMENT_SPEED,
                    InkColors.MAGENTA,
                    2
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(3600)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.SUGAR)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.DAMAGE_BOOST,
                    InkColors.RED,
                    4
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(3600)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.BLAZE_POWDER)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    PastelMobEffects.TOUGHNESS,
                    InkColors.PINK,
                    8
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.TOUGHNESS)
                    .baseDurationTicks(1200)
                    .potencyModifier(0.5f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.GLASS_PEACH)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.UNLUCK,
                    InkColors.LIGHT_BLUE,
                    2
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.LUCK)
                    .baseDurationTicks(6000)
                    .potencyModifier(1.5f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelBlocks.FOUR_LEAF_CLOVER)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.WATER_BREATHING,
                    InkColors.PINK,
                    2
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(3600)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.PUFFERFISH)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.WEAKNESS,
                    InkColors.RED,
                    4
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(1.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.FERMENTED_SPIDER_EYE)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.WEAVING,
                    InkColors.BLUE,
                    8
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.COBWEB)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.WIND_CHARGED,
                    InkColors.YELLOW,
                    8
                )
                    .group("vanilla_potions")
                    .baseDurationTicks(1800)
                    .potencyModifier(0.0f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(Items.BREEZE_ROD)
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopBrewingBuilder(
                    MobEffects.WITHER,
                    InkColors.BLACK,
                    16
                )
                    .requiredAdvancement(PastelAdvancements.Unlocks.Potions.WITHER)
                    .baseDurationTicks(600)
                    .potencyModifier(0.5f)
                    .ingredient1(Items.NETHER_WART)
                    .ingredient2(PastelItems.MIDNIGHT_CHIP)
                    .ingredient3(Items.FERMENTED_SPIDER_EYE)
            );
    }
}
