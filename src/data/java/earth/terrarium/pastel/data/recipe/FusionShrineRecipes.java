package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.api.item.Preenchanted;
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
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import static earth.terrarium.pastel.registries.PastelItems.*;
import static earth.terrarium.pastel.registries.PastelFusionShrineWorldEffects.*;

public class FusionShrineRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "fusion_shrine");

        weather(pfx.subPrefix("weather"));
        vanilla(pfx.subPrefix("vanilla"));
        malachite(pfx.subPrefix("malachite"));
        bedrock(pfx.subPrefix("bedrock"));
        trinkets(pfx.subPrefix("trinkets"));

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

    private static void weather(PrefixHelper pfx) {
        pfx.generateRecipe(
                "clear",
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        ItemStack.EMPTY
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Weather.CLEAR)
                        .craftingTime(160)
                        .experience(0.0f)
                        .worldCondition()
                        .weather(WeatherPredicate.RAIN)
                        .submit()
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .finishCrafting(WEATHER_CLEAR)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.weather_clear")
                        .requires(Items.SUNFLOWER)
        );

        pfx.generateRecipe(
                "rain",
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        ItemStack.EMPTY
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Weather.RAIN)
                        .craftingTime(1600)
                        .experience(0.0f)
                        .worldCondition()
                        .weather(WeatherPredicate.CLEAR_SKY)
                        .submit()
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .finishCrafting(WEATHER_RAIN)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.weather_rain")
                        .requires(MERMAIDS_GEM)
        );

        // where there's rain there's thunder (shits violently)
        pfx.generateRecipe(
                "thunder",
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        ItemStack.EMPTY
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Weather.THUNDER)
                        .craftingTime(160)
                        .experience(0.0f)
                        .worldCondition()
                        .weather(WeatherPredicate.NOT_THUNDER)
                        .submit()
                        .startCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .duringCrafting(LIGHTNING_ON_SHRINE)
                        .duringCrafting(LIGHTNING_AROUND_SHRINE)
                        .finishCrafting(WEATHER_THUNDER)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.weather_thunder")
                        .requires(STORM_STONE)
        );
    }

    private static void vanilla(PrefixHelper pfx) {
        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        new ItemStack(Items.DIAMOND_HORSE_ARMOR)
                )
                        .group("horse_armor")
                        .requiredAdvancement(PastelAdvancements.CREATE_ONYX_SHARD)
                        .craftingTime(200)
                        .experience(1.0f)
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.DIAMOND, 6)
                        .requires(Items.LEATHER, 6)
                        .requires(ItemTags.WOOL, 2)
                        .requires(TOPAZ_SHARD, 3)

        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        Fluids.WATER,
                        new ItemStack(Items.GOLDEN_HORSE_ARMOR)
                )
                        .group("horse_armor")
                        .requiredAdvancement(PastelAdvancements.CREATE_ONYX_SHARD)
                        .craftingTime(200)
                        .experience(1.0f)
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.GOLD_INGOT, 6)
                        .requires(Items.LEATHER, 6)
                        .requires(ItemTags.WOOL, 2)
                        .requires(CITRINE_SHARD, 3)

        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        Fluids.LAVA,
                        new ItemStack(Items.GILDED_BLACKSTONE)
                )
                        .craftingTime(200)
                        .experience(0.25f)
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.BLACKSTONE)
                        .requires(Items.GOLD_INGOT)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        Fluids.LAVA,
                        new ItemStack(Items.NETHERITE_INGOT)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Resources.NETHERITE_INGOT)
                        .craftingTime(1200)
                        .experience(4.0f)
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .translateDescription("pastel.recipe.fusion_shrine.explanation.netherite_ingot")
                        .requires(Items.NETHERITE_SCRAP)
                        .requires(Items.GOLD_INGOT)
                        .requires(MIDNIGHT_ABERRATION)
        );

        pfx.generateRecipe(
                "pure_netherite_ingot",
                new FusionShrineRecipeBuilder(
                        PastelFluids.DRAGONROT.get(),
                        new ItemStack(Items.NETHERITE_INGOT)
                )
                        .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE)
                        .craftingTime(1200)
                        .experience(1.0f)
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .padCraftEffect()
                        .duringCrafting(VISUAL_EXPLOSIONS_ON_SHRINE)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(PURE_NETHERITE_SCRAP)
                        .requires(PURE_GOLD)
        );
    }

    private static void malachite(PrefixHelper pfx) {
        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), FEROCIOUS_GLASS_CREST_BIDENT.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.FEROCIOUS_GLASS_CREST_BIDENT)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(MALACHITE_BIDENT)
                        .requires(MOONSTONE_CORE)
                        .requires(PURE_PRISMARINE, 8)
                        .requires(STORM_STONE, 4)
                        .requires(MERMAIDS_GEM, 4)
                        .requires(WHITE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), FRACTAL_GLASS_CREST_BIDENT.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.FRACTAL_GLASS_CREST_BIDENT)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(MALACHITE_BIDENT)
                        .requires(MOONSTONE_CORE)
                        .requires(PURE_PRISMARINE, 8)
                        .requires(Items.ECHO_SHARD, 4)
                        .requires(MERMAIDS_GEM, 4)
                        .requires(WHITE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), GLASS_CREST_CROSSBOW.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.GLASS_CREST_CROSSBOW)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(MALACHITE_CROSSBOW)
                        .requires(MOONSTONE_CORE)
                        .requires(PURE_REDSTONE, 8)
                        .requires(STAR_FRAGMENT, 4)
                        .requires(WHITE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), GLASS_CREST_ULTRA_GREATSWORD.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.GLASS_CREST_ULTRA_GREATSWORD)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(MALACHITE_ULTRA_GREATSWORD)
                        .requires(MOONSTONE_CORE)
                        .requires(PURE_NETHERITE_SCRAP, 8)
                        .requires(Items.NETHER_STAR)
                        .requires(WHITE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), GLASS_CREST_WORKSTAFF.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.GLASS_CREST_WORKSTAFF)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(MALACHITE_WORKSTAFF)
                        .requires(MOONSTONE_CORE)
                        .requires(PURE_DIAMOND, 8)
                        .requires(RESONANCE_SHARD, 4)
                        .requires(WHITE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), MALACHITE_BIDENT.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.MALACHITE_TOOLS)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.TRIDENT)
                        .requires(PURE_MALACHITE, 8)
                        .requires(PURE_QUARTZ, 8)
                        .requires(PALTAERIA_FRAGMENTS, 4)
                        .requires(MOONSTONE_POWDER, 8)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), MALACHITE_CROSSBOW.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.MALACHITE_TOOLS)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.CROSSBOW)
                        .requires(PURE_MALACHITE, 8)
                        .requires(PURE_QUARTZ, 8)
                        .requires(PALTAERIA_FRAGMENTS, 4)
                        .requires(MOONSTONE_POWDER, 8)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), MALACHITE_ULTRA_GREATSWORD.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.MALACHITE_TOOLS)
                        .craftingTime(1200)
                        .experience(16.0f)
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.DIAMOND_SWORD)
                        .requires(PURE_MALACHITE, 8)
                        .requires(PURE_QUARTZ, 8)
                        .requires(PALTAERIA_FRAGMENTS, 4)
                        .requires(MOONSTONE_POWDER, 8)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), MALACHITE_WORKSTAFF.get())
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.MALACHITE_TOOLS)
                        .craftingTime(1200)
                        .experience(16.0f)
                        // pray tell copy from what
                        .copyComponents()
                        .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.DIAMOND_PICKAXE)
                        .requires(Items.DIAMOND_AXE)
                        .requires(Items.DIAMOND_SHOVEL)
                        .requires(PURE_MALACHITE, 8)
                        .requires(PURE_QUARTZ, 8)
                        .requires(PALTAERIA_FRAGMENTS, 4)
                        .requires(MOONSTONE_POWDER, 8)
        );
    }

    private static void trinkets(PrefixHelper pfx) {
        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        ARTISTS_PALETTE.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.ARTISTS_PALETTE)
                        .craftingTime(2400)
                        .experience(16.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(ItemTags.PLANKS, 4)
                        .requires(PastelItemTags.PIGMENTS, 16)
                        .requires(SPECTRAL_SHARD)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        PIGMENT_PALETTE.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.PIGMENT_PALETTE)
                        .craftingTime(1800)
                        .experience(8.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(ItemTags.PLANKS, 4)
                        .requires(RAW_AZURITE, 4)
                        .requires(PastelItemTags.PIGMENTS, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        ASHEN_CIRCLET.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.ASHEN_CIRCLET)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_CIRCLET)
                        .requires(STRATINE_GEM)
                        .requires(NEOLITH, 4)
                        .requires(ORANGE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        AZURE_DIKE_BELT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.AZURE_DIKE_BELT)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_BELT)
                        .requires(RAW_AZURITE, 8)
                        .requires(BLUE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        AZURE_DIKE_RING.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.AZURE_DIKE_RING)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_TUFF_RING)
                        .requires(RAW_AZURITE, 8)
                        .requires(BLUE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        AZURESQUE_DIKE_CORE.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.AZURESQUE_DIKE_CORE)
                        .craftingTime(1200)
                        .experience(20.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(DOWNSTONE_FRAGMENTS, 4)
                        .requires(AETHER_VESTIGES)
                        .requires(MOONSTONE_CORE)
                        .requires(PURE_AZURITE, 16)
                        .requires(BLUE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        CIRCLET_OF_ARROGANCE.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.CIRCLET_OF_ARROGANCE)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_CIRCLET)
                        .requires(PURE_MALACHITE, 8)
                        .requires(BLOOD_ORCHID_PETAL, 4)
                        .requires(MOONSTONE_POWDER, 8)
                        .requires(RED_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        COTTON_CLOUD_BOOTS.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.COTTON_CLOUD_BOOTS)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.LEATHER_BOOTS)
                        .requires(STARDUST, 16)
                        .requires(Items.PHANTOM_MEMBRANE, 4)
                        .requires(BROWN_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        GLEAMING_PIN.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.GLEAMING_PIN)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(PastelBlocks.POLISHED_CALCITE, 4)
                        .requires(STAR_FRAGMENT)
                        .requires(STARDUST, 16)
                        .requires(YELLOW_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        GLOVES_OF_DAWNS_GRASP.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.GLOVES_OF_DAWNS_GRASP)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_GLOVES)
                        .requires(NEOLITH, 8)
                        .requires(LIGHT_BLUE_PIGMENT, 16)
                        .requires(GREEN_PIGMENT, 8)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        GREATER_POTION_PENDANT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.GREATER_POTION_PENDANT)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_PENDANT)
                        .requires(PALTAERIA_GEM, 3)
                        .requires(Items.GLASS, 4)
                        .requires(PINK_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        LESSER_POTION_PENDANT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.LESSER_POTION_PENDANT)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_PENDANT)
                        .requires(STRATINE_GEM)
                        .requires(Items.GLASS, 4)
                        .requires(PINK_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        HEARTSINGERS_REWARD.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.HEARTSINGERS_REWARD)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_TUFF_RING)
                        .requires(PastelBlocks.FOUR_LEAF_CLOVER, 2)
                        .requires(PINK_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        JEOPARDANT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.JEOPARDANT)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_TUFF_RING)
                        .requires(Items.LAPIS_LAZULI, 6)
                        .requires(RED_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        LAURELS_OF_SERENITY.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.LAURELS_OF_SERENITY)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_PENDANT)
                        .requires(NECTARDEW_BURGEON)
                        .requires(NIGHTDEW_SPROUT, 3)
                        .requires(STAR_FRAGMENT, 2)
                        .requires(PURE_QUARTZ, 2)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        NEAT_RING.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.NEAT_RING)
                        .craftingTime(12000)
                        .experience(8.0f)
                        .startCrafting(PLAY_GLASS_BREAKING_SOUND)
                        .padCraftEffect()
                        .duringCrafting(LIGHTNING_ON_SHRINE)
                        .padCraftEffect()
                        .duringCrafting(EXPLOSIONS_AND_LIGHTNING_AROUND_SHRINE)
                        .duringCrafting(EXPLOSIONS_AND_LIGHTNING_AROUND_SHRINE)
                        .duringCrafting(EXPLOSIONS_AND_LIGHTNING_AROUND_SHRINE)
                        .padCraftEffect()
                        .finishCrafting(RIDICULOUSLY_SQUEAKY_FART)
                        .dontPlayFinishEffects()
                        .requires(FANCIFUL_BISMUTH_RING)
                        .requires(PastelBlocks.PURE_GOLD_BLOCK, 64)
                        .requires(MOONSTONE_SHARD, 64)
                        .requires(PastelBlocks.RESONANT_LILY)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        PUFF_CIRCLET.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.PUFF_CIRCLET)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_CIRCLET)
                        .requires(PALTAERIA_GEM)
                        .requires(Items.WIND_CHARGE, 8)
                        .requires(PURE_AZURITE, 8)
                        .requires(MOONSTONE_SHARD, 16)
                        .requires(WHITE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        RADIANCE_PIN.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.RADIANCE_PIN)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(PastelBlocks.POLISHED_CALCITE, 4)
                        .requires(STAR_FRAGMENT)
                        .requires(SHIMMERSTONE_GEM, 16)
                        .requires(BLUE_PIGMENT, 8)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        RING_OF_AETHERIAL_GRACE.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.RING_OF_AETHERIAL_GRACE)
                        .craftingTime(1200)
                        .experience(4.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_BISMUTH_RING)
                        .requires(Items.POPPED_CHORUS_FRUIT, 8)
                        .requires(PALTAERIA_FRAGMENTS, 8)
                        .requires(PALTAERIA_GEM)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        RING_OF_CONSUMPTION.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.RING_OF_CONSUMPTION)
                        .craftingTime(1200)
                        .experience(4.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_BISMUTH_RING)
                        .requires(MIDNIGHT_CHIP, 4)
                        .requires(BLOOD_ORCHID_PETAL, 4)
                        .requires(NIGHTDEW_SPROUT, 4)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        RING_OF_DENSER_STEPS.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.RING_OF_DENSER_STEPS)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_TUFF_RING)
                        .requires(Items.QUARTZ, 8)
                        .requires(STRATINE_FRAGMENTS, 8)
                        .requires(STRATINE_GEM)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        RING_OF_PURSUIT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.RING_OF_PURSUIT)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_BISMUTH_RING)
                        .requires(INCANDESCENT_ESSENCE, 8)
                        .requires(PYRITE_CHUNK, 4)
                        .requires(MAGENTA_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        SEVEN_LEAGUE_BOOTS.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.SEVEN_LEAGUE_BOOTS)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(Items.LEATHER_BOOTS)
                        .requires(PastelBlocks.FOUR_LEAF_CLOVER)
                        .requires(Items.PHANTOM_MEMBRANE)
                        .requires(PURPLE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        SHIELDGRASP_AMULET.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.SHIELDGRASP_AMULET)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_PENDANT)
                        .requires(RAW_AZURITE, 8)
                        .requires(BLUE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        TAKEOFF_BELT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.TAKEOFF_BELT)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_BELT)
                        .requires(Items.RABBIT_FOOT)
                        .requires(STORM_STONE, 2)
                        .requires(YELLOW_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        TOTEM_PENDANT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.TOTEM_PENDANT)
                        .craftingTime(600)
                        .experience(4.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_PENDANT)
                        .requires(Items.TOTEM_OF_UNDYING)
                        .requires(MOONSTRUCK_NECTAR)
                        .requires(NEOLITH, 2)
                        .requires(BLUE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        WEEPING_CIRCLET.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.WEEPING_CIRCLET)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_CIRCLET)
                        .requires(Items.HEART_OF_THE_SEA)
                        .requires(MERMAIDS_GEM, 8)
                        .requires(Items.COPPER_INGOT, 4)
                        .requires(LIGHT_BLUE_PIGMENT, 16)
        );

        pfx.generateAutoNamedRecipe(
                new FusionShrineRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        WHISPY_CIRCLET.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.WHISPY_CIRCLET)
                        .craftingTime(600)
                        .experience(2.0f)
                        .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE)
                        .requires(FANCIFUL_CIRCLET)
                        .requires(NIGHTDEW_SPROUT, 2)
                        .requires(Items.PHANTOM_MEMBRANE, 4)
                        .requires(BROWN_PIGMENT, 16)
        );


    }

    private static void bedrock(PrefixHelper pfx) {
        pfx.generateRecipe(
                "anvil",
                startBedrock(PastelBlocks.BEDROCK_ANVIL.toStack(), false)
                        .requires(Items.ANVIL)
                        .requires(BEDROCK_DUST, 27)
                        .requires(BLACK_PIGMENT, 3)
                        .requires(ONYX_POWDER, 3)
        );

        bedrockTool(
                pfx,
                Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_AXE.get()),
                Items.GOLDEN_AXE,
                3
        );

        bedrockTool(
                pfx,
                Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_HOE.get()),
                Items.GOLDEN_HOE,
                2
        );

        bedrockTool(
                pfx,
                Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_PICKAXE.get()),
                Items.GOLDEN_PICKAXE,
                3
        );

        bedrockTool(
                pfx,
                Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_SHOVEL.get()),
                Items.GOLDEN_SHOVEL,
                1
        );

        bedrockTool(
                pfx,
                Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_SWORD.get()),
                Items.GOLDEN_SWORD,
                2
        );

        pfx.generateRecipe(
                "boots",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_BOOTS.get()),
                        Items.GOLDEN_BOOTS, true, 4
                )
                        .requires(STRATINE_FRAGMENTS, 2)
        );

        pfx.generateRecipe(
                "chestplate",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_CHESTPLATE.get()),
                        Items.GOLDEN_CHESTPLATE, true, 8
                )
                        .requires(STRATINE_GEM, 1)
                        .requires(Items.ROSE_BUSH, 1)
        );

        pfx.generateRecipe(
                "helmet",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_HELMET.get()),
                        Items.GOLDEN_HELMET, true, 5
                )
                        .requires(STRATINE_FRAGMENTS, 2)
        );

        pfx.generateRecipe(
                "leggings",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_LEGGINGS.get()),
                        Items.GOLDEN_LEGGINGS, true, 7
                )
                        .requires(STRATINE_FRAGMENTS, 4)
        );

        pfx.generateRecipe(
                "bow",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_BOW.get()),
                        Items.BOW, false, 4
                )
                        .requires(STRATINE_FRAGMENTS, 2)
                        .requires(Items.GOLD_INGOT, 3)
        );

        pfx.generateRecipe(
                "crossbow",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_CROSSBOW.get()),
                        Items.CROSSBOW, false, 5
                )
                        .requires(STRATINE_FRAGMENTS, 3)
                        .requires(Items.GOLD_INGOT, 3)
        );

        pfx.generateRecipe(
                "fishing_rod",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_FISHING_ROD.get()),
                        Items.FISHING_ROD, false, 3
                )
                        .requires(STRATINE_FRAGMENTS, 1)
                        .requires(Items.GOLD_INGOT, 3)
        );

        pfx.generateRecipe(
                "shears",
                bedrockShared(
                        Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), BEDROCK_SHEARS.get()),
                        Items.SHEARS, false, 2
                )
                        .requires(STRATINE_FRAGMENTS, 2)
                        .requires(Items.GOLD_INGOT, 2)
        );
    }
    // this seems to be the only "flavor" of bedrock stuff that has a pattern that allows fully saving without
    // any hiccups
    private static void bedrockTool(PrefixHelper pfx, ItemStack result, ItemLike base, int count) {
        var name = result.getItemHolder().getKey().location().getPath().replace("bedrock_", "");
        pfx.generateRecipe(
                name,
                bedrockShared(result, base, false, count)
                        .requires(STRATINE_GEM, 1)
                        .requires(STRATINE_FRAGMENTS, count)
        );
    }

    private static FusionShrineRecipeBuilder bedrockShared(ItemStack result, ItemLike base, boolean isArmor, int count) {
        return startBedrock(result, isArmor)
                .requires(base)
                .requires(BEDROCK_DUST, count)
                .requires(BLACK_PIGMENT, count)
                .requires(ONYX_POWDER, count);
    }


    private static FusionShrineRecipeBuilder startBedrock(ItemStack result, boolean isArmor) {
        var group = isArmor ? "bedrock_armor" : "bedrock_tools";
        return new FusionShrineRecipeBuilder(
                PastelFluids.MIDNIGHT_SOLUTION.get(),
                result
        )
                .group(group)
                .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.BEDROCK_TOOLS)
                .craftingTime(1200)
                .experience(16.0f)
                .copyComponents()
                .startCrafting(FusionShrineRecipeWorldEffect.NOTHING)
                .finishCrafting(SINGLE_VISUAL_EXPLOSION_ON_SHRINE);
    }
}
