package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.blocks.fluid.PastelFluid;
import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.data.recipe.builder.titration_barrel.TitrationBarrelRecipeBuilder;
import earth.terrarium.pastel.recipe.titration_barrel.dynamic.*;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static earth.terrarium.pastel.registries.PastelItems.*;

public class TitrationBarrelRecipes {
    private static final String INFUSED_BEVERAGES = "infused_beverages";

    private static final TagKey<Item> RICE = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", "foods/rices"));

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "titration_barrel");

        var sakePfx = new PrefixHelper(ctx, lookup, "mod_integration");

        sakePfx.generateAutoNamedRecipe(
                TitrationBarrelRecipeBuilder.infusedBeverage(
                        FluidIngredient.empty(),
                        InfusedBeverageComponent.SAKE,
                        1
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.TITRATION_BARREL)
                        .neoCondition(
                                // ???
                                new PastelLoadConditions.PastelTagsPopulatedResourceCondition(Registries.ITEM.location(),
                                        List.of(RICE.location())
                                )
                        )
                        .group(INFUSED_BEVERAGES)
                        .minFermentationTimeHours(24)
                        .tappingItem(Items.GLASS_BOTTLE)
                        .fermentationSpeedMod(0.25f)
                        .statusEffect(PastelMobEffects.LAVA_GLIDING, 9600)
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
                        .statusEffect(MobEffects.MOVEMENT_SPEED, 9600)
                            .simplePotencyEntry(0)
                            .potencyAlc(1, 20)
                            .potencyAlc(2, 25)
                            .potencyAlc(3, 30)
                        .submit()
                        .statusEffect(MobEffects.DAMAGE_BOOST, 9600)
                            .potencyFull(0, 25, 1.5f)
                            .potencyFull(1, 25, 2.0f)
                            .potencyFull(2, 25, 2.5f)
                        .submit()
                        .statusEffect(MobEffects.MOVEMENT_SLOWDOWN, 9600)
                            .potencyThickness(0, 2)
                            .potencyThickness(1, 3)
                            .potencyThickness(2, 4)
                        .submit()
                        .statusEffect(MobEffects.BLINDNESS, 600)
                            .potencyThickness(0, 3)
                            .potencyAlc(0, 30)
                        .submit()
                        .requires(RICE, 8)
        );

        special(pfx.subPrefix("special"));
        infusedBeverages(pfx.subPrefix("infused_beverages"));

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

    // THE FINAL BOSS OF DATAGEN BECKONS...
    private static void infusedBeverages(PrefixHelper pfx) {
        liquors(pfx);
        ciders(pfx);

        pfx.generateAutoNamedRecipe(
                infusedCommon(Fluids.WATER, InfusedBeverageComponent.ADVOCAAT)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.25f)
                        .statusEffect(PastelMobEffects.NOURISHING, 9600)
                            .simplePotencyEntry(0)
                            .potencyAlc(1, 20)
                            .potencyAlc(2, 25)
                            .potencyAlc(3, 30)
                        .submit()
                        .statusEffect(MobEffects.FIRE_RESISTANCE, 9600)
                            .potencyFull(0, 25, 1.25f)
                        .submit()
                        // ???
                        .statusEffect(MobEffects.SATURATION, 8)
                            .potencyAlc(0, 28)
                        .submit()
                        .statusEffect(MobEffects.MOVEMENT_SLOWDOWN, 9600)
                            .potencyThickness(0, 2)
                            .potencyThickness(1, 3)
                            .potencyThickness(2, 4)
                        .submit()
                        .statusEffect(MobEffects.BLINDNESS, 600)
                            .potencyThickness(0, 3)
                            .potencyAlc(0, 30)
                        .submit()
                        .requires(Items.WHEAT, 4)
                        .requires(CLOTTED_CREAM)
                        .requires(Tags.Items.EGGS, 2)
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.ALE
                )
                        .minFermentationTimeHours(4)
                        .fermentationSpeedMod(1.25f)
                        .statusEffect(MobEffects.HEALTH_BOOST, 9600)
                            .simplePotencyEntry(0)
                            .potencyAlc(1, 5)
                            .potencyAlc(2, 8)
                        .submit()
                        .statusEffect(PastelMobEffects.NOURISHING, 9600)
                            .potencyAlc(0, 3)
                            .potencyAlc(1, 6)
                        .submit()
                        // ???
                        .statusEffect(MobEffects.SATURATION, 120)
                            .potencyAlc(0, 7)
                            .potencyAlc(1, 9)
                            .potencyAlc(2, 11)
                        .submit()
                        // If experiencing stiffness for 4 or more hours, seek medical help immediately
                        .statusEffect(PastelMobEffects.STIFFNESS, 600)
                            .potencyThickness(0, 2)
                            .potencyThickness(1, 3)
                            .potencyThickness(2, 4)
                        .submit()
                        .statusEffect(MobEffects.CONFUSION, 600)
                            .potencyAlc(0, 9)
                            .potencyThickness(0, 3)
                        .submit()
                        .requires(Items.WHEAT, 4)
                        .requires(Items.SWEET_BERRIES, 1)
                        .requires(Items.SUGAR_CANE, 1)
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.APPLE_LIQUOR
                )
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.25f)
                        .statusEffect(MobEffects.MOVEMENT_SPEED, 9600)
                            .simplePotencyEntry(0)
                            .potencyAlc(1, 16)
                            .potencyAlc(2, 20)
                            .potencyAlc(3, 24)
                            .potencyAlc(4, 28)
                            .potencyAlc(5, 32)
                        .submit()
                        .statusEffect(MobEffects.WEAKNESS, 9600)
                            .potencyThickness(0, 2)
                            .potencyThickness(1, 3)
                            .potencyThickness(2, 4)
                        .submit()
                        .statusEffect(MobEffects.CONFUSION, 600)
                            .potencyThickness(0, 3)
                            .potencyAlc(0, 30)
                        .submit()
                        .requires(Items.APPLE, 6)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.ARTEMISA
                )
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.25f)
                        .statusEffect(MobEffects.REGENERATION, 800)
                            .simplePotencyEntry(0)
                            .potencyAlc(1, 20)
                            .potencyAlc(2, 30)
                        .submit()
                        .statusEffect(MobEffects.MOVEMENT_SPEED, 9600)
                            .potencyFull(0, 25, 1.5f)
                            .potencyFull(1, 25, 2.0f)
                            .potencyFull(2, 25, 2.5f)
                        .submit()
                        .statusEffect(MobEffects.LUCK, 9600)
                            .potencyAlc(0, 28)
                        .submit()
                        // ???
                        .statusEffect(MobEffects.MOVEMENT_SLOWDOWN, 9600)
                            .potencyThickness(0, 2)
                            .potencyThickness(1, 3)
                            .potencyThickness(2, 4)
                        .submit()
                        .statusEffect(MobEffects.BLINDNESS, 600)
                            .potencyThickness(0, 3)
                            .potencyAlc(0, 9)
                        .submit()
                        .requires(Items.SHORT_GRASS, 6)
                        .requires(Items.FERN, 2)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.BEER
                )
                        .minFermentationTimeHours(4)
                        .fermentationSpeedMod(1.25f)
                        .statusEffect(MobEffects.DIG_SPEED, 9600)
                            .simplePotencyEntry(0)
                            .potencyAlc(1, 5)
                            .potencyAlc(2, 8)
                        .submit()
                        .statusEffect(PastelMobEffects.NOURISHING, 9600)
                            .potencyAlc(0, 3)
                            .potencyAlc(1, 6)
                        .submit()
                        .statusEffect(MobEffects.SATURATION, 120)
                            .potencyAlc(0, 7)
                            .potencyAlc(1, 9)
                            .potencyAlc(2, 11)
                        .submit()
                        .statusEffect(PastelMobEffects.STIFFNESS, 600)
                            .potencyThickness(0, 2)
                            .potencyThickness(1, 3)
                            .potencyThickness(2, 4)
                        .submit()
                        .statusEffect(MobEffects.CONFUSION, 600)
                            .potencyThickness(0, 3)
                            .potencyAlc(0, 9)
                        .submit()
                        .requires(Items.WHEAT, 6)
        );





        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.CAMOMILLESQUE
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.CAMOMILLESQUE)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.667f)
                        .statusEffect(PastelMobEffects.CALMING, 12000)
                            .scaleOnAlc(5, 0, 5)
                        .submit()
                        .statusEffect(MobEffects.MOVEMENT_SPEED, 12000)
                            .potencyFull(0, 25, 1.25f)
                        .submit()
                        .statusEffect(MobEffects.WEAKNESS, 12000)
                            .scaleOnThickness(2, 2, 1)
                        .submit()
                        .requires(Items.FLOWERING_AZALEA_LEAVES, 4)
                        .requires(Items.HONEYCOMB)
                        .requires(NIGHTDEW_SPROUT)
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.DAMASSINE
                )
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.125f)
                        .statusEffect(MobEffects.REGENERATION, 600)
                            .simplePotencyEntry(0)
                            .potencyEntry(1)
                                .minAlc(35)
                            .scaledUntil(4, 10, 0)
                        .submit()
                        .statusEffect(PastelMobEffects.SWIFTNESS, 9600)
                            .potencyEntry(0)
                                .minAlc(40)
                                .minThickness(1.5f)
                            .scaledUntil(2, 0, 0.5f)
                        .submit()
                        .statusEffect(MobEffects.FIRE_RESISTANCE, 9600)
                            .potencyAlc(0, 60)
                        .submit()
                        .statusEffect(MobEffects.WEAKNESS, 9600)
                            .scaleOnThickness(2, 2, 1)
                        .submit()
                        .statusEffect(MobEffects.POISON, 600)
                            .potencyAlc(0, 60)
                            .potencyThickness(0, 3)
                        .submit()
                        .requires(Items.BEETROOT, 8)
                        .requires(CLOTTED_CREAM)
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.ENCHANTED_APPLE_CIDER
                )
                        .minFermentationTimeHours(72)
                        .fermentationSpeedMod(0.075f)
                        .statusEffect(MobEffects.ABSORPTION, 3600)
                            .simplePotencyEntry(2)
                            .potencyEntry(3)
                                .minAlc(80)
                            .scaledUntil(6, 5, 0)
                        .submit()
                        .statusEffect(MobEffects.REGENERATION, 400)
                            .simplePotencyEntry(0)
                            .potencyEntry(1)
                                .minAlc(50)
                            .scaledUntil(5, 10, 0)
                        .submit()
                        .statusEffect(MobEffects.FIRE_RESISTANCE, 9600)
                            .simplePotencyEntry(0)
                        .submit()
                        .statusEffect(MobEffects.DAMAGE_RESISTANCE, 9600)
                            .simplePotencyEntry(0)
                            .potencyEntry(1)
                                .minAlc(75)
                            .scaledUntil(3, 10, 0)
                        .submit()
                        .requires(Items.ENCHANTED_GOLDEN_APPLE, 4)
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.FRUIT_SHNAPS
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.GLASS_PEACH_TITRATION)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.125f)
                        .statusEffect(MobEffects.MOVEMENT_SPEED, 9600)
                            .simplePotencyEntry(0)
                            .potencyEntry(1)
                                .minAlc(35)
                            .scaledUntil(4, 10, 0)
                        .submit()
                        .statusEffect(MobEffects.HEALTH_BOOST, 9600)
                            .potencyEntry(0)
                                .minAlc(40)
                                .minThickness(1.5f)
                            .scaledUntil(2, 0, 0.5f)
                        .submit()
                        .statusEffect(PastelMobEffects.TOUGHNESS, 9600)
                            .potencyEntry(0)
                                .minAlc(40)
                                .minThickness(1.5f)
                            .scaledUntil(2, 0, 0.5f)
                        .submit()
                        .statusEffect(MobEffects.WEAKNESS, 9600)
                            .scaleOnThickness(2, 2, 1)
                        .submit()
                        .statusEffect(MobEffects.POISON, 600)
                            .potencyThickness(0, 3)
                            .potencyAlc(0, 60)
                        .submit()
                        .requires(Items.APPLE, 4)
                        .requires(GLASS_PEACH, 4)
                        .requires(Items.SWEET_BERRIES, 2)
        );

        var gatorWineRecipe =
                infusedCommon(
                        PastelFluids.LIQUID_CRYSTAL.get(),
                        InfusedBeverageComponent.GATORWINE
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.GATORWINE)
                        .minFermentationTimeHours(4)
                        .fermentationSpeedMod(0.1f)
                        .requires(CITRINE_POWDER, 16)
                        .requires(Items.SUGAR, 16)
                        .requires(STORM_STONE)
                        .requires(SHIMMERSTONE_GEM)
                        .requires(FISSURE_PLUM, 4)
                ;


        var swiftnessEffect =
                gatorWineRecipe.statusEffect(PastelMobEffects.SWIFTNESS, 9600)
                        .simplePotencyEntry(0)
                ;
        var speedEffect =
                gatorWineRecipe.statusEffect(MobEffects.MOVEMENT_SPEED, 9600);

        // VERY funny guys, making this god awful complex setup
        // scoping ftw
        {
            float alc = 10;
            for (int potency = 1; potency <= 11; potency += 2, alc += 10) {
                swiftnessEffect.potencyAlc(potency, alc);
                speedEffect.potencyAlc(potency, alc);
            }
        }
        swiftnessEffect.submit();
        speedEffect.submit();


        pfx.generateAutoNamedRecipe(
                gatorWineRecipe
                        .statusEffect(MobEffects.WEAKNESS, 9600)
                            .simplePotencyEntry(0)
                            .potencyThickness(1, 3)
                            .potencyThickness(2, 4)
                        .submit()
                        // ???
                        .statusEffect(MobEffects.CONFUSION, 36000)
                            .simplePotencyEntry(0)
                        .submit()
                        .statusEffect(PastelMobEffects.DEADLY_POISON, 9600)
                            .potencyEntry(0)
                                .minAlc(10)
                            .scaledUntil(3, 15, 0)
                        .submit()
        );

        pfx.generateAutoNamedRecipe(
                infusedCommon(
                        Fluids.WATER,
                        InfusedBeverageComponent.GIN
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.GIN)
                        .minFermentationTimeHours(24)
                        .fermentationSpeedMod(0.125f)
                        .statusEffect(MobEffects.LUCK, 9600)
                            .simplePotencyEntry(0)
                            .potencyEntry(1)
                                .minAlc(35)
                            .scaledUntil(4, 10, 0)
                        .submit()
                        .statusEffect(PastelMobEffects.SWIFTNESS, 9600)
                            .potencyEntry(0)
                                .minAlc(40)
                                .minThickness(1.5f)
                            .scaledUntil(2, 0, 0.5f)
                        .submit()
                        .statusEffect(MobEffects.FIRE_RESISTANCE, 9600)
                            .potencyAlc(0, 60)
                        .submit()
                        .statusEffect(MobEffects.WEAKNESS, 9600)
                            .scaleOnThickness(2, 2, 1)
                        .submit()
                        .statusEffect(MobEffects.POISON, 600)
                            .potencyThickness(0, 3)
                            .potencyAlc(0, 60)
                        .submit()
                        .requires(AMARANTH_GRAINS, 12)
        );


    }

    private static void ciders(PrefixHelper pfx) {
        pfx.generateAutoNamedRecipe(
                cider(
                        Fluids.WATER,
                        InfusedBeverageComponent.APPLE_CIDER,
                        MobEffects.MOVEMENT_SPEED,
                        MobEffects.DAMAGE_BOOST,
                        MobEffects.WEAKNESS
                )
                        .requires(Items.APPLE, 8)
        );

        pfx.generateAutoNamedRecipe(
                cider(
                        Fluids.WATER,
                        InfusedBeverageComponent.BERRY_CIDER,
                        MobEffects.HEALTH_BOOST,
                        MobEffects.DAMAGE_BOOST,
                        MobEffects.WEAKNESS
                )
                        .requires(Items.SWEET_BERRIES, 8)
        );

        pfx.generateAutoNamedRecipe(
                cider(
                        Fluids.WATER,
                        InfusedBeverageComponent.GLASS_PEACH_CIDER,
                        PastelMobEffects.PROJECTILE_REBOUND,
                        MobEffects.DAMAGE_BOOST,
                        MobEffects.WEAKNESS
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.GLASS_PEACH_TITRATION)
                        .requires(GLASS_PEACH, 8)
        );

        pfx.generateAutoNamedRecipe(
                cider(
                        Fluids.WATER,
                        InfusedBeverageComponent.GLOW_BERRY_CIDER,
                        PastelMobEffects.SWIFTNESS,
                        MobEffects.DAMAGE_BOOST,
                        MobEffects.WEAKNESS
                )
                        .statusEffect(MobEffects.GLOWING, 4800)
                            .simplePotencyEntry(0)
                        .submit()
                        .requires(Items.GLOW_BERRIES, 8)
        );

        pfx.generateAutoNamedRecipe(
                cider(
                        Fluids.WATER,
                        InfusedBeverageComponent.PLUM_CIDER,
                        MobEffects.MOVEMENT_SPEED,
                        PastelMobEffects.SWIFTNESS,
                        PastelMobEffects.VULNERABILITY
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.FISSURE_PLUM_TITRATION)
                        .requires(FISSURE_PLUM, 8)
        );

        pfx.generateAutoNamedRecipe(
                cider(
                        Fluids.WATER,
                        InfusedBeverageComponent.SAWBLADE_HOLLY_CIDER,
                        PastelMobEffects.TOUGHNESS,
                        MobEffects.DAMAGE_BOOST,
                        MobEffects.WEAKNESS
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.SAWBLADE_HOLLY_TITRATION)
                        .requires(SAWBLADE_HOLLY_BERRY, 8)
        );
    }

    private static void liquors(PrefixHelper pfx) {

        pfx.generateAutoNamedRecipe(
                liquor(
                        Fluids.WATER,
                        InfusedBeverageComponent.BERRY_LIQUOR,
                        MobEffects.HEALTH_BOOST,
                        MobEffects.MOVEMENT_SPEED,
                        MobEffects.MOVEMENT_SLOWDOWN
                )
                        .requires(Items.SWEET_BERRIES, 6)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateAutoNamedRecipe(
                liquor(
                        Fluids.WATER,
                        InfusedBeverageComponent.GLOW_BERRY_LIQUOR,
                        PastelMobEffects.SWIFTNESS,
                        MobEffects.MOVEMENT_SPEED,
                        MobEffects.MOVEMENT_SLOWDOWN
                )
                        .statusEffect(MobEffects.GLOWING, 4800)
                            .simplePotencyEntry(0)
                        .submit()
                        .requires(Items.GLOW_BERRIES, 6)
                        .requires(Items.SUGAR, 4)
        );


        pfx.generateAutoNamedRecipe(
                liquor(
                        Fluids.WATER,
                        InfusedBeverageComponent.GLASS_PEACH_LIQUOR,
                        PastelMobEffects.PROJECTILE_REBOUND,
                        MobEffects.MOVEMENT_SPEED,
                        MobEffects.MOVEMENT_SLOWDOWN
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.GLASS_PEACH_TITRATION)
                        .requires(GLASS_PEACH, 6)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateAutoNamedRecipe(
                liquor(
                        Fluids.WATER,
                        InfusedBeverageComponent.MYCEYLON_LIQUOR,
                        MobEffects.ABSORPTION,
                        MobEffects.MOVEMENT_SPEED,
                        MobEffects.MOVEMENT_SLOWDOWN
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.MYCEYLON_LIQUOR)
                        .requires(MYCEYLON, 6)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateAutoNamedRecipe(
                liquor(
                        Fluids.WATER,
                        InfusedBeverageComponent.PLUM_LIQUOR,
                        MobEffects.MOVEMENT_SPEED,
                        PastelMobEffects.SWIFTNESS,
                        PastelMobEffects.VULNERABILITY
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.FISSURE_PLUM_TITRATION)
                        .requires(FISSURE_PLUM, 6)
                        .requires(Items.SUGAR, 4)
        );

        pfx.generateAutoNamedRecipe(
                liquor(
                        Fluids.WATER,
                        InfusedBeverageComponent.SAWBLADE_HOLLY_LIQUOR,
                        PastelMobEffects.TOUGHNESS,
                        MobEffects.MOVEMENT_SPEED,
                        MobEffects.MOVEMENT_SLOWDOWN
                )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Food.SAWBLADE_HOLLY_TITRATION)
                        .requires(SAWBLADE_HOLLY_BERRY, 6)
                        .requires(Items.SUGAR, 4)
        );


    }

    // for liquors that arent apple liquor LOL
    private static TitrationBarrelRecipeBuilder liquor(
            Fluid fluid,
            InfusedBeverageComponent component,
            Holder<MobEffect> primaryEffect,
            Holder<MobEffect> secondaryEffect,
            Holder<MobEffect> negativeEffect) {
        return infusedCommon(fluid, component)
                .minFermentationTimeHours(24)
                .fermentationSpeedMod(0.25f)
                .statusEffect(primaryEffect, 9600)
                    .simplePotencyEntry(0)
                    .potencyEntry(1)
                        .minAlc(20)
                    .scaledUntil(3, 8, 0)
                .submit()
                .statusEffect(secondaryEffect, 9600)
                    .potencyAlc(0, 25)
                    .potencyEntry(1)
                        .minAlc(25)
                    .scaledUntil(3, 5, 0)
                .submit()
                .statusEffect(negativeEffect, 9600)
                    .scaleOnThickness(2, 2, 1)
                .submit()
                .statusEffect(MobEffects.CONFUSION, 600)
                    .potencyThickness(0, 3)
                    .potencyAlc(0, 30)
                .submit()
                ;
    }

    private static TitrationBarrelRecipeBuilder cider(
            Fluid fluid,
            InfusedBeverageComponent component,
            Holder<MobEffect> primaryEffect,
            Holder<MobEffect> secondaryEffect,
            Holder<MobEffect> negativeEffect
    ) {
        return infusedCommon(fluid, component)
                .minFermentationTimeHours(12)
                .fermentationSpeedMod(1.25f)
                .statusEffect(primaryEffect, 9600)
                    .simplePotencyEntry(0)
                    .potencyAlc(1, 5)
                    .potencyAlc(2, 8)
                .submit()
                .statusEffect(secondaryEffect, 9600)
                    .potencyAlc(0, 3)
                    .potencyAlc(1, 6)
                .submit()
                .statusEffect(negativeEffect, 9600)
                    .scaleOnThickness(2, 2, 1)
                .submit()
                .statusEffect(MobEffects.CONFUSION, 600)
                    .potencyAlc(0, 9)
                    .potencyThickness(0, 3)
                .submit()
                ;
    }

    private static TitrationBarrelRecipeBuilder infusedCommon(@Nullable Fluid fluid, InfusedBeverageComponent component) {
        var ingredient = fluid == null ? FluidIngredient.empty() : FluidIngredient.of(fluid);
        return TitrationBarrelRecipeBuilder.infusedBeverage(
                ingredient,
                component,
                4
        )
                .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.TITRATION_BARREL)
                .group(INFUSED_BEVERAGES)
                .tappingItem(Items.GLASS_BOTTLE);
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
