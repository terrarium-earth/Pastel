package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.blocks.fluid.PastelFluid;
import earth.terrarium.pastel.data.recipe.builder.titration_barrel.TitrationBarrelRecipeBuilder;
import earth.terrarium.pastel.recipe.titration_barrel.dynamic.*;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;

import static earth.terrarium.pastel.registries.PastelItems.*;

public class TitrationBarrelRecipes {
    private static final String INFUSED_BEVERAGES = "infused_beverages";

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "titration_barrel");

        special(pfx.subPrefix("special"));

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        BITTER_OILS.toStack(6)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.POISONERS_HANDBOOK)
                        .minFermentationTimeHours(4)
                        .requires(NECTARDEW_BURGEON)
                        .requires(BLOODBOIL_SYRUP)
                        .requires(BEDROCK_DUST, 3)
                        .requires(STRATINE_FRAGMENTS, 3)
                        .requires(Items.ECHO_SHARD)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        Fluids.WATER,
                        BRISTLE_MEAD.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.BREWERS_HANDBOOK)
                        .minFermentationTimeHours(24)
                        .tappingItem(Items.GLASS_BOTTLE)
                        .fermentationSpeedMod(0.25f)
                        .statusEffect(MobEffects.FIRE_RESISTANCE, 9600)
                            .simplePotencyEntry(0)
                        .submit()
                        .statusEffect(MobEffects.MOVEMENT_SPEED, 9600)
                            .simplePotencyEntry(0)
                            .potencyEntry(1)
                                .minAlc(20)
                            .submit()
                            .potencyEntry(2)
                                .minAlc(25)
                            .submit()
                            .potencyEntry(3)
                                .minAlc(30)
                            .submit()
                        .submit()
                        .statusEffect(MobEffects.DAMAGE_BOOST, 9600)
                            .potencyEntry(0)
                                .minAlc(25)
                                .minThickness(1.5f)
                            .submit()
                            .potencyEntry(1)
                                .minAlc(25)
                                .minThickness(2.0f)
                            .submit()
                            .potencyEntry(2)
                                .minAlc(25)
                                .minThickness(2.5f)
                            .submit()
                        .submit()
                        .statusEffect(MobEffects.MOVEMENT_SLOWDOWN, 9600)
                            .potencyEntry(0)
                                .minThickness(2)
                            .submit()
                            .potencyEntry(1)
                                .minThickness(3)
                            .submit()
                            .potencyEntry(2)
                                .minThickness(4)
                            .submit()
                        .submit()
                        .statusEffect(MobEffects.BLINDNESS, 600)
                            .potencyEntry(0)
                                .minThickness(3)
                            .submit()
                            .potencyEntry(0)
                                .minAlc(30)
                            .submit()
                        .submit()
                        .requires(Items.HONEY_BOTTLE, 4)
                        .requires(SAWBLADE_HOLLY_BERRY, 4)
                        .requires(PastelBlocks.BRISTLE_SPROUTS, 8)
                        .requires(JARAMEL, 2)

        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        NeoForgeMod.MILK.get(),
                        CLOTTED_CREAM.toStack(4)
                )
                        .minFermentationTimeHours(1)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateRecipe(
                "clotted_cream_resin",
                new TitrationBarrelRecipeBuilder(
                        FluidIngredient.empty(),
                        CLOTTED_CREAM.toStack(16)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_MILKY_RESIN)
                        .minFermentationTimeHours(1)
                        .requires(MILKY_RESIN)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        EVERNECTAR.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.POISONERS_HANDBOOK)
                        .minFermentationTimeHours(24)
                        .requires(NECTARDEW_BURGEON, 4)
                        .requires(PURE_COAL, 8)
                        .requires(FROSTBITE_ESSENCE, 8)
                        .requires(STAR_FRAGMENT, 2)
                        .requires(QUITOXIC_POWDER, 2)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        FADED_KOI.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMPERIAL_COOKBOOK)
                        .minFermentationTimeHours(2)
                        .tappingItem(ALOE_LEAF)
                        .requires(KOI, 8)
                        .requires(BOTTLE_OF_FADING)
        );

        pfx.generateRecipe(
                "fast_pure_alcohol",
                new TitrationBarrelRecipeBuilder(
                        Fluids.WATER,
                        PURE_ALCOHOL.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Items.INCANDESCENT_AMALGAM)
                        .minFermentationTimeHours(48)
                        .group(INFUSED_BEVERAGES)
                        .requires(PastelItemTags.DRINKABLE_SPIRITS)
                        .requires(Items.BLAZE_POWDER, 4)
                        .requires(PastelBlocks.INCANDESCENT_AMALGAM)
        );

        pfx.generateRecipe(
                "fermented_spider_eyes",
                new TitrationBarrelRecipeBuilder(
                        Fluids.WATER,
                        new ItemStack(Items.FERMENTED_SPIDER_EYE, 4)
                )
                        .minFermentationTimeHours(1)
                        .requires(Items.SPIDER_EYE, 2)
                        .requires(Items.BROWN_MUSHROOM, 2)
                        .requires(Items.SUGAR, 2)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        FREIGEIST.toStack(2)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.FREIGEIST)
                        .minFermentationTimeHours(48)
                        .tappingItem(Items.GLASS_BOTTLE)
                        .group(INFUSED_BEVERAGES)
                        .requires(AMARANTH_GRAINS, 4)
                        .requires(ONYX_POWDER, 8)
                        .requires(FROSTBITE_ESSENCE, 8)
                        .requires(BLOODBOIL_SYRUP, 2)
                        .requires(Items.NETHER_WART, 2)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        FluidIngredient.empty(),
                        FRESH_CHOCOLATE.toStack(8)
                )
                        .minFermentationTimeHours(4)
                        .requires(Items.COCOA_BEANS, 4)
                        .requires(CLOTTED_CREAM)
                        .requires(Items.SWEET_BERRIES, 2)
        );

        pfx.generateRecipe(
                // HONEY IM HOMO
                "honey",
                new TitrationBarrelRecipeBuilder(
                        Fluids.WATER,
                        new ItemStack(Items.HONEY_BOTTLE, 4)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.HONEY)
                        .minFermentationTimeHours(1)
                        .tappingItem(Items.GLASS_BOTTLE)
                        .requires(SAWBLADE_HOLLY_BERRY, 16)
                        .requires(ALOE_LEAF)
        );

        pfx.generateRecipe(
                "incandescent_amalgam_doom",
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.MIDNIGHT_SOLUTION.get(),
                        PastelBlocks.INCANDESCENT_AMALGAM.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Items.INCANDESCENT_AMALGAM_DOOM)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.8f)
                        .angelsSharePercentPerMcDay(-0.02f)
                        // YUM!
                        .group(INFUSED_BEVERAGES)
                        .requires(PastelItemTags.DRINKABLE_SPIRITS)
                        .requires(INCANDESCENT_ESSENCE, 32)
                        .requires(Items.HONEYCOMB, 2)
                        .requires(QUITOXIC_POWDER, 16)
                        .requires(DOOMBLOOM_SEED, 4)
        );

        pfx.generateRecipe(
                "incandescent_amalgam_pure",
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.MIDNIGHT_SOLUTION.get(),
                        PastelBlocks.INCANDESCENT_AMALGAM.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Items.INCANDESCENT_AMALGAM_DOOM)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.8f)
                        .angelsSharePercentPerMcDay(-0.02f)
                        .group(INFUSED_BEVERAGES)
                        .requires(PURE_ALCOHOL)
                        .requires(Items.HONEYCOMB, 2)
                        .requires(QUITOXIC_POWDER, 8)
                        .requires(DOOMBLOOM_SEED, 1)
        );

        pfx.generateRecipe(
                "incandescent_amalgam_simple",
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.MIDNIGHT_SOLUTION.get(),
                        PastelBlocks.INCANDESCENT_AMALGAM.toStack(2)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Items.INCANDESCENT_AMALGAM)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.8f)
                        .angelsSharePercentPerMcDay(-0.02f)
                        .group(INFUSED_BEVERAGES)
                        .requires(PURE_ALCOHOL)
                        .requires(Items.HONEYCOMB, 2)
                        .requires(QUITOXIC_POWDER, 8)
                        .requires(Items.GUNPOWDER, 8)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        FluidIngredient.empty(),
                        LE_FISHE_AU_CHOCOLAT.toStack(4)
                )
                        .minFermentationTimeHours(4)
                        .requires(ItemTags.FISHES, 4)
                        .requires(FRESH_CHOCOLATE, 2)
                        .requires(Items.SUGAR, 4)
                        .requires(CLOTTED_CREAM, 1)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        Fluids.WATER,
                        NIGHT_SALTS.toStack(6)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Items.NIGHT_SALTS)
                        .minFermentationTimeHours(1)
                        .requires(NIGHTDEW_SPROUT)
                        .requires(Items.QUARTZ, 6)
                        .requires(Items.HONEYCOMB, 2)
                        .requires(ONYX_SHARD)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        PEACH_JAM.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_2)
                        .minFermentationTimeHours(4)
                        .requires(Items.SUGAR, 16)
                        .requires(GLASS_PEACH, 8)
                        .requires(JADE_PETALS, 8)
                        .requires(BLOOD_ORCHID_PETAL)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        Fluids.WATER,
                        REPRISE.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.REPRISE)
                        .minFermentationTimeHours(4)
                        .fermentationSpeedMod(0.5f)
                        .tappingItem(Items.GLASS_BOTTLE)
                        .requires(Items.CHORUS_FRUIT, 4)
                        .requires(FROSTBITE_ESSENCE, 4)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        PastelFluids.MIDNIGHT_SOLUTION.get(),
                        SURSTROMMING.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_1)
                        .minFermentationTimeHours(24)
                        .requires(Tags.Items.FOODS_RAW_FISH, 8)
                        .requires(PRICKLY_BAYLEAF, 8)
                        .requires(MIDNIGHT_CHIP, 1)
                        .requires(Items.GHAST_TEAR, 1)
        );

        pfx.generateAutoNamedRecipe(
                new TitrationBarrelRecipeBuilder(
                        Fluids.WATER,
                        MORCHELLA.toStack(4)
                )
                        .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.BREWERS_HANDBOOK)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.5f)
                        .tappingItem(Items.GLASS_BOTTLE)
                        .statusEffect(PastelMobEffects.NOURISHING, 9600)
                            .simplePotencyEntry(0)
                        .submit()
                        .statusEffect(MobEffects.NIGHT_VISION, 9600)
                            .potencyEntry(0)
                                .minAlc(30)
                            .submit()
                        .submit()
                        .statusEffect(PastelMobEffects.SWIFTNESS, 9600)
                            .potencyEntry(0)
                                .minAlc(12)
                                .minThickness(1.5f)
                            .submit()
                            .potencyEntry(1)
                                .minAlc(15)
                                .minThickness(2.0f)
                            .submit()
                            .potencyEntry(2)
                                .minAlc(18)
                                .minThickness(2.5f)
                            .submit()
                        .submit()
                        .statusEffect(MobEffects.DIG_SPEED, 9600)
                            .potencyEntry(0)
                                .minAlc(13)
                                .minThickness(1.5f)
                            .submit()
                            .potencyEntry(1)
                                .minAlc(16)
                                .minThickness(2.0f)
                            .submit()
                            .potencyEntry(2)
                                .minAlc(19)
                                .minThickness(2.5f)
                            .submit()
                        .submit()
                        .statusEffect(PastelMobEffects.VULNERABILITY, 9600)
                            .potencyEntry(0)
                                .minThickness(2)
                            .submit()
                            .potencyEntry(1)
                                .minThickness(3)
                            .submit()
                            .potencyEntry(2)
                                .minThickness(4)
                            .submit()
                        .submit()
                        .statusEffect(MobEffects.CONFUSION, 600)
                            .potencyEntry(0)
                                .minThickness(3)
                            .submit()
                            .potencyEntry(0)
                                .minAlc(22)
                            .submit()
                        .submit()
                        .requires(INCANDESCENT_ESSENCE, 4)
                        .requires(MYCEYLON, 8)
                        .requires(AMARANTH_GRAINS, 4)
                        .requires(Tags.Items.MUSHROOMS, 2)
                        .requires(JARAMEL, 2)
        );
    }

    private static void special(PrefixHelper pfx) {
        pfx.generateDynamicRecipe(
                "aqua_regia",
                new AquaRegiaRecipe()
        );

        pfx.generateDynamicRecipe(
                "cheong",
                new CheongRecipe()
        );

        pfx.generateDynamicRecipe(
                "jade_wine",
                new JadeWineRecipe()
        );

        pfx.generateDynamicRecipe(
                "nectered_viognier",
                new NecteredViognierRecipe()
        );

        pfx.generateDynamicRecipe(
                "suspicious_brew",
                new SuspiciousBrewRecipe()
        );
    }
}
