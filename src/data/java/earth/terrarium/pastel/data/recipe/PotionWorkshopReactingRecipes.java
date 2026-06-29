package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.data.recipe.builder.potion_workshop.PotionWorkshopReactingBuilder;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Items;

public class PotionWorkshopReactingRecipes {

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "potion_workshop_reacting");

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.AMETHYST_POWDER.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.AMETHYST)
                    .modifier()
                    .flatDurationBonusTicks(1200)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.ASH_FLAKES.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_ASH)
                    .modifier()
                    .flatDurationBonusNegativeEffects(-2400)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.BEDROCK_DUST.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.BREAK_DECAYED_BEDROCK)
                    .modifier()
                    .durationMultiplier(0.25f)
                    .potencyMultiplier(1.5f)
                    .yield(-1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.BISMUTH_FLAKE.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_BISMUTH)
                    .modifier()
                    .flatPotencyBonusPositiveEffects(1.0f)
                    .flags()
                    .randomColor()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.BITTER_OILS.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.POISONERS_HANDBOOK)
                    .modifier()
                    .yield(-4.0f)
                    .flags()
                    .incurable()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.BONE_ASH.asItem())
                    .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                    .modifier()
                    .lastEffectDurationMultiplier(2.0f)
                    .lastEffectPotencyMultiplier(2.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.CITRINE_POWDER.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.CITRINE)
                    .modifier()
                    .potencyMultiplier(1.25f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(Items.DRAGON_BREATH)
                    .modifier()
                    .flags()
                    .makeLingering()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(Items.ECHO_SHARD)
                    .modifier()
                    .chanceToAddLastEffect(0.5f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.FISSURE_PLUM.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_FISSURE_PLUM)
                    .modifier()
                    .flatPotencyBonusPositiveEffects(0.5f)
                    .flatDurationBonusPositiveEffects(600)
                    .flatPotencyBonusNegativeEffects(-0.5f)
                    .flatDurationBonusNegativeEffects(-600)
                    .yield(0.5f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelBlocks.FOUR_LEAF_CLOVER.asItem())
                    .requiredAdvancement(PastelAdvancements.COLLECT_FOUR_LEAF_CLOVER)
                    .modifier()
                    .flatDurationBonusTicks(1200)
                    .submit()
                    .modifier()
                    .flatPotencyBonus(1.0f)
                    .submit()
                    .modifier()
                    .durationMultiplier(3.0f)
                    .submit()
                    .modifier()
                    .potencyMultiplier(2.0f)
                    .submit()
                    .modifier()
                    .yield(3.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(Items.GLOWSTONE_DUST)
                    .modifier()
                    .flatPotencyBonus(1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(Items.GUNPOWDER)
                    .modifier()
                    .flags()
                    .makeSplashing()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.JADEITE_PETALS.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_JADEITE)
                    .modifier()
                    .flatDurationBonusPositiveEffects(2400)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(Items.LAPIS_LAZULI)
                    .modifier()
                    .yield(0.5f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.MIDNIGHT_CHIP.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_MIDNIGHT_CHIP)
                    .modifier()
                    .flatDurationBonusTicks(600)
                    .flatPotencyBonus(1.0f)
                    .yield(-2.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.MOONSTONE_POWDER.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_MOONSTONE)
                    .modifier()
                    .flags()
                    .makeEffectsPositive()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.MOONSTRUCK_NECTAR.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.HARVEST_MOONSTRUCK_NECTAR)
                    .modifier()
                    .additionalDrinkDurationTicks(-28)
                    .submit()
            );

        // THE BIG ONE
        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.NECTARDEW_BURGEON.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_NECTARDEW)
                    .modifier()
                    .potencyMultiplier(2.0f)
                    .durationMultiplier(2.0f)
                    .yield(-3.0f)
                    .flags()
                    .additionalEffect(PastelMobEffects.SOMNOLENCE, 0.334f, InkColors.PURPLE, 8)
                    .baseDurationTicks(800)
                    .potencyModifier(0.0f)
                    .submit()
                    .additionalEffect(PastelMobEffects.ETERNAL_SLUMBER, 0.125f, InkColors.PURPLE, 32)
                    .baseDurationTicks(1600)
                    .potencyModifier(0.0f)
                    .submit()
                    .additionalEffect(PastelMobEffects.FATAL_SLUMBER, 0.01f, InkColors.PURPLE, 256)
                    .baseDurationTicks(100)
                    .potencyModifier(0.0f)
                    .submit()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.NEOLITH.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_NEOLITH)
                    .modifier()
                    .flags()
                    .unidentifiable()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.NIGHTDEW_SPROUT.asItem())
                    .requiredAdvancement(PastelAdvancements.COLLECT_NIGHTDEW)
                    .modifier()
                    .flatPotencyBonus(1.0f)
                    .durationMultiplier(1.5f)
                    .yield(-1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.ONYX_POWDER.asItem())
                    .requiredAdvancement(PastelAdvancements.CREATE_ONYX_SHARD)
                    .modifier()
                    .yield(2.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.PALTAERIA_FRAGMENTS.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_PALTAERIA)
                    .modifier()
                    .additionalRandomPositiveEffectCount(1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.PRICKLY_BAYLEAF.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PRICKLY_BAYLEAF)
                    .modifier()
                    .flatPotencyBonusNegativeEffects(-2.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.PURE_ECHO.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE)
                    .modifier()
                    .chanceToAddLastEffect(1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.PURE_GLOWSTONE.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE)
                    .modifier()
                    .flatPotencyBonus(2.0f)
                    .durationMultiplier(0.5f)
                    .yield(-1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.PURE_LAPIS.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE)
                    .modifier()
                    .yield(1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.PURE_REDSTONE.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE)
                    .modifier()
                    .durationMultiplier(3.0f)
                    .potencyMultiplier(0.5f)
                    .yield(-1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.QUITOXIC_POWDER.asItem())
                    .requiredAdvancement(PastelAdvancements.COLLECT_QUITOXIC_REEDS)
                    .modifier()
                    .flatPotencyBonusNegativeEffects(1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.RAW_AZURITE.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_AZURITE)
                    .modifier()
                    .flags()
                    .negateDecreasingDuration()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.RAW_BLOODSTONE.asItem())
                    .requiredAdvancement(PastelAdvancements.PLUCK_RESPLENDENT_FEATHER)
                    .modifier()
                    .potencyMultiplier(2.0f)
                    .additionalDrinkDurationTicks(54)
                    .yield(-2.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.RAW_MALACHITE.asItem())
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_MALACHITE)
                    .modifier()
                    .durationMultiplier(2.0f)
                    .additionalDrinkDurationTicks(54)
                    .yield(-1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(Items.REDSTONE)
                    .modifier()
                    .durationMultiplier(2.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.SHIMMERSTONE_GEM.asItem())
                    .requiredAdvancement(PastelAdvancements.COLLECT_SHIMMERSTONE)
                    .modifier()
                    .durationMultiplier(1.2f)
                    .potencyMultiplier(1.2f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.STARDUST.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_STARDUST)
                    .modifier()
                    .lastEffectDurationMultiplier(1.5f)
                    .lastEffectPotencyMultiplier(1.5f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.STAR_FRAGMENT.asItem())
                    .requiredAdvancement(PastelAdvancements.COLLECT_STAR_FRAGMENT)
                    .modifier()
                    .chanceToAddLastEffect(0.5f)
                    .lastEffectDurationMultiplier(0.667f)
                    .lastEffectPotencyMultiplier(2.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.STORM_STONE.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_STORM_STONE)
                    .modifier()
                    .flags()
                    .potentDecreasingEffect()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.STRATINE_FRAGMENTS.asItem())
                    .requiredAdvancement(PastelAdvancements.Midgame.COLLECT_STRATINE)
                    .modifier()
                    .flatPotencyBonus(1.0f)
                    .additionalRandomNegativeEffectCount(1.0f)
                    .yield(-1.0f)
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.TOPAZ_POWDER.asItem())
                    .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.TOPAZ)
                    .modifier()
                    .flags()
                    .noParticles()
                    .submit()
                    .submit()
            );

        pfx
            .generateAutoNamedRecipe(
                new PotionWorkshopReactingBuilder(PastelItems.VEGETAL.asItem())
                    .requiredAdvancement(PastelAdvancements.COLLECT_VEGETAL)
                    .modifier()
                    .durationMultiplier(3.0f)
                    .potencyMultiplier(0.75f)
                    .submit()
            );
    }
}
