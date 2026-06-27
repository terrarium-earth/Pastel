package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.api.predicate.location.MoonPhasePredicate;
import earth.terrarium.pastel.api.predicate.location.WeatherPredicate;
import earth.terrarium.pastel.api.recipe.FusionShrineRecipeWorldEffect;
import earth.terrarium.pastel.blocks.fluid.PastelFluid;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.data.recipe.builder.fusion_shrine.FusionShrineRecipeBuilder;
import earth.terrarium.pastel.helpers.interaction.TimeHelper;
import earth.terrarium.pastel.recipe.fusion_shrine.dynamic.ShootingStarHardeningRecipe;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import static earth.terrarium.pastel.registries.PastelItems.*;
import static earth.terrarium.pastel.registries.PastelFusionShrineWorldEffects.*;

public class FusionShrineRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "fusion_shrine");

        pfx.generateDynamicRecipe(
                "shooting_star_hardening",
                new ShootingStarHardeningRecipe()
        );

        pfx.generateRecipe(
                "no_rgb",
                new FusionShrineRecipeBuilder(
                        FluidIngredient.of(PastelFluids.LIQUID_CRYSTAL.get()),
                        new ItemStack(Items.RAW_COPPER)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_EVERY_PURE_CMY_RESOURCE)
                        .craftingTime(200)
                        .experience(0.0f)
                        .secret(true)
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .duringCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.no_rgb")
                        .requires(PURE_MALACHITE)
                        .requires(PURE_AZURITE)
                        .requires(PURE_BLOODSTONE)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        FluidIngredient.of(PastelFluids.LIQUID_CRYSTAL.get()),
                        DRACONIC_TWINSWORD.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.DRACONIC_TWINSWORD)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(PastelFusionShrineWorldEffects.SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .duringCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .duringCrafting(PastelFusionShrineWorldEffects.VISUAL_EXPLOSIONS_ON_SHRINE)
                        .duringCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(PastelFusionShrineWorldEffects.LEGENDARY_TOOL_CRAFT)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.draconic_twinsword")
                        .requires(Items.NETHERITE_SWORD)
                        .requires(Items.NETHERITE_SWORD)
                        .requires(RAW_BLOODSTONE, 4)
                        .requires(Items.GOLD_INGOT, 8)
                        .requires(BLOOD_ORCHID_PETAL, 8)
                        .requires(YELLOW_PIGMENT, 16)
        );

        pfx.generateRecipe(
                "draconic_twinsword_reclaim",
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        DRACONIC_TWINSWORD.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.DRACONIC_TWINSWORD)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(PastelFusionShrineWorldEffects.SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(PastelFusionShrineWorldEffects.VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(PastelFusionShrineWorldEffects.LEGENDARY_TOOL_CRAFT)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.draconic_twinsword_reclaim")
                        .requires(DRAGON_TALON)
                        .requires(Items.NETHERITE_SWORD)
                        .requires(Items.GOLD_INGOT, 4)
                        .requires(Items.QUARTZ, 4)
                        .requires(MOONSTRUCK_NECTAR, 8)
                        .requires(YELLOW_PIGMENT, 2)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.MIDNIGHT_SOLUTION.get(),
                        DREAMFLAYER.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.DREAMFLAYER)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(PastelFusionShrineWorldEffects.SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(PastelFusionShrineWorldEffects.VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(PastelFusionShrineWorldEffects.LEGENDARY_TOOL_CRAFT)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.dreamflayer")
                        .worldCondition()
                        .timeOfDay(TimeHelper.TimeOfDay.NIGHT)
                        .moonPhase(MoonPhasePredicate.FULL_MOON)
                        .submit()
                        .requires(Items.NETHERITE_SWORD)
                        .requires(BISMUTH_CRYSTAL, 4)
                        .requires(MOONSTRUCK_NECTAR)
                        .requires(RED_PIGMENT, 4)
                        .requires(LIGHT_BLUE_PIGMENT, 4)
                        .requires(LIGHT_GRAY_PIGMENT, 4)
                        .requires(BLACK_PIGMENT, 4)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.MIDNIGHT_SOLUTION.get(),
                        PastelBlocks.MOB_HEADS.get(PastelSkullType.EGG_LAYING_WOOLY_PIG).toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.EGG_LAYING_WOOLY_PIG_HEAD)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .startCrafting(PastelFusionShrineWorldEffects.SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(PastelFusionShrineWorldEffects.VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(PastelFusionShrineWorldEffects.SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.egg_laying_wooly_pig_head")
                        // fortunately the only usage of this EVIL category of blocks
                        .requires(PastelBlocks.MOB_HEADS.get(PastelSkullType.COW))
                        .requires(PastelBlocks.MOB_HEADS.get(PastelSkullType.SHEEP))
                        .requires(PastelBlocks.MOB_HEADS.get(PastelSkullType.PIG))
                        .requires(PastelBlocks.MOB_HEADS.get(PastelSkullType.CHICKEN))
                        .requires(VEGETAL, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        FOX_O_NINE_TAILS.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.FOX_O_NINE_TAILS)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(LEGENDARY_TOOL_CRAFT)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.fox_o_nine_tails")
                        .requires(VERDIGRIS_LASH)
                        .requires(Items.COPPER_INGOT, 16)
                        .requires(Items.RAW_GOLD, 16)
                        .requires(SHIMMERSTONE_GEM, 16)
                        .requires(AETHER_VESTIGES)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        KNOTTED_SWORD.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.KNOTTED_SWORD)
                        .craftingTime(400)
                        .experience(12.0f)
                        .copyComponents()
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(LEGENDARY_TOOL_CRAFT)
                        .requires(Items.IRON_SWORD)
                        .requires(Items.DIAMOND)
                        .requires(NIGHTDEW_SPROUT, 3)
                        .requires(VEGETAL, 8)
                        .requires(QUITOXIC_POWDER, 8)
                        .requires(PURPLE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        MIDNIGHT_ABERRATION.toStack()
                )
                        // my favorite block
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.MIDNIGHT_ABERRATION)
                        .craftingTime(800)
                        .experience(8.0f)
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(LIGHTNING_ON_SHRINE)
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .duringCrafting(LIGHTNING_AROUND_SHRINE)
                        .duringCrafting(MAYBE_PLACE_MIDNIGHT_SOLUTION)
                        .finishCrafting(PLACE_MIDNIGHT_SOLUTION)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.midnight_aberration")
                        .worldCondition()
                        .timeOfDay(TimeHelper.TimeOfDay.DAY)
                        .weather(WeatherPredicate.CLEAR_SKY)
                        .submit()
                        .requires(TOPAZ_SHARD)
                        .requires(Items.AMETHYST_SHARD)
                        .requires(CITRINE_SHARD)
                        .requires(ONYX_SHARD)
                        .requires(NEOLITH)
        );

        pfx.generateRecipe(
                "mysterious_locket_socketing",
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        new ItemStack(MYSTERIOUS_LOCKET, 1, DataComponentPatch.builder().set(PastelDataComponentTypes.SOCKETED, Unit.INSTANCE).build())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.MYSTERIOUS_LOCKET_SOCKETING)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.MIDNIGHT_SOLUTION.get(),
                        NIGHTFALLS_BLADE.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.NIGHTFALLS_BLADE)
                        .craftingTime(1200)
                        .experience(16.0f)
                        // not present in original, but it HAS to be a mistake, right?
                        .copyComponents()
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(LIGHTNING_ON_SHRINE)
                        .requires(Items.IRON_SWORD)
                        .requires(PYRITE_CHUNK, 4)
                        .requires(PURE_QUARTZ, 2)
                        .requires(FROSTBITE_ESSENCE, 8)
                        .requires(RED_PIGMENT, 4)
                        .requires(PURPLE_PIGMENT, 4)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        Fluids.LAVA,
                        ONYX_SHARD.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.FUSION_SHRINE)
                        .craftingTime(480)
                        .experience(2.0f)
                        .startCrafting(WEATHER_THUNDER_SHORT)
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .finishCrafting(LIGHTNING_ON_SHRINE)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.onyx_shard")
                        .worldCondition()
                        // The torture device:
                        .timeOfDay(TimeHelper.TimeOfDay.MIDNIGHT)
                        .moonPhase(MoonPhasePredicate.NEW_MOON)
                        .submit()
                        .requires(TOPAZ_SHARD)
                        .requires(Items.AMETHYST_SHARD)
                        .requires(CITRINE_SHARD)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        SOOTHING_BOUQUET.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.SOOTHING_BOUQUET)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .worldCondition()
                        .timeOfDay(TimeHelper.TimeOfDay.MIDNIGHT)
                        .submit()
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.soothing_bouquet")
                        .requires(NECTARDEW_BURGEON)
                        .requires(Items.POPPY, 8)
                        .requires(Items.PHANTOM_MEMBRANE, 8)
                        .requires(MILKY_RESIN, 2)
                        .requires(PURE_QUARTZ, 3)
                        .requires(PURPLE_PIGMENT, 3)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        VERDIGRIS_LASH.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.VERDIGRIS_LASH)
                        .craftingTime(400)
                        .experience(12.0f)
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(LEGENDARY_TOOL_CRAFT)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.verdigris_lash")
                        .requires(Items.LEAD)
                        .requires(NIGHTDEW_SPROUT, 3)
                        .requires(VEGETAL, 8)
                        .requires(STARDUST, 16)
                        .requires(FROSTBITE_ESSENCE, 8)
        );
    }
}
