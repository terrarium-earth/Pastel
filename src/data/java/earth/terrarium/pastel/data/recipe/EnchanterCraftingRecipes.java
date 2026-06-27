package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.enchanter.EnchanterCraftingRecipeBuilder;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelResourceConditions;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.enchantment.Enchantments;

import static earth.terrarium.pastel.registries.PastelItems.*;

public class EnchanterCraftingRecipes {

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "enchanter");

        vanillaBooks(pfx.subPrefix("vanilla_books"));

        pfx.generateAutoNamedRecipe(
            new EnchanterCraftingRecipeBuilder(
                    Ingredient.of(Items.APPLE),
                    new ItemStack(Items.ENCHANTED_GOLDEN_APPLE)
            )
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE)
                    .craftingTime(600)
                    .requiredExperience(100)
                    .requires(PastelBlocks.SHIMMERSTONE_BLOCK)
                    .requires(PastelBlocks.SHIMMERSTONE_BLOCK)
                    .requires(PastelBlocks.PURE_GOLD_BLOCK)
                    .requires(PastelBlocks.PURE_GOLD_BLOCK)
        );

        pfx.generateAutoNamedRecipe(
                new EnchanterCraftingRecipeBuilder(
                        Ingredient.of(Items.CARROT),
                        PastelItems.ENCHANTED_GOLDEN_CARROT.toStack()
                )
                        .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE)
                        .craftingTime(600)
                        .requiredExperience(50)
                        .requires(PastelItems.SHIMMERSTONE_GEM)
                        .requires(PastelItems.SHIMMERSTONE_GEM)
                        .requires(PastelItems.PURE_GOLD)
                        .requires(PastelItems.PURE_GOLD)
        );
    }

    private static void vanillaBooks(PrefixHelper pfx) {
        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.AQUA_AFFINITY
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PastelItems.BLUE_PIGMENT)
                        .requires(PastelItems.BLUE_PIGMENT)
                        .requires(Items.NAUTILUS_SHELL)
                        .requires(PastelItems.MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.BANE_OF_ARTHROPODS
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PastelItems.BLACK_PIGMENT)
                        .requires(PastelItems.BLACK_PIGMENT)
                        .requires(Items.STRING)
                        .requires(PastelItems.STORM_STONE)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.BINDING_CURSE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_CURSES)
                        .craftingTime(600)
                        .requiredExperience(250)
                        .requires(PastelItems.GRAY_PIGMENT)
                        .requires(PastelItems.GRAY_PIGMENT)
                        .requires(Items.HONEYCOMB)
                        .requires(PastelItems.STRATINE_FRAGMENTS)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.BLAST_PROTECTION
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PastelItems.PINK_PIGMENT)
                        .requires(PastelItems.PINK_PIGMENT)
                        .requires(Items.GUNPOWDER)
                        .requires(PastelItems.BEDROCK_DUST)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.BREACH
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL_BREACHING)
                        .craftingTime(600)
                        .requiredExperience(200)
                        .requires(PastelItems.RED_PIGMENT)
                        .requires(PastelItems.RED_PIGMENT)
                        .requires(PastelItems.BLOOD_ORCHID_PETAL)
                        .requires(PastelItems.DRAGONBONE_CHUNK)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.CHANNELING
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT_CHANNELING)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PastelItems.BROWN_PIGMENT)
                        .requires(PastelItems.BROWN_PIGMENT)
                        .requires(PastelItems.STORM_STONE)
                        .requires(PastelItems.MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.DENSITY
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL)
                        .craftingTime(600)
                        .requiredExperience(200)
                        .requires(PastelItems.CYAN_PIGMENT)
                        .requires(PastelItems.CYAN_PIGMENT)
                        .requires(Items.TUFF) // ts is tuff.....
                        .requires(PastelItems.STRATINE_FRAGMENTS)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.DEPTH_STRIDER
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BLUE_PIGMENT)
                        .requires(BLUE_PIGMENT)
                        .requires(Items.WATER_BUCKET)
                        .requires(MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.EFFICIENCY
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_QUITOXIC)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(YELLOW_PIGMENT)
                        .requires(YELLOW_PIGMENT)
                        .requires(Items.SUGAR)
                        .requires(QUITOXIC_POWDER)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.FEATHER_FALLING
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_QUITOXIC)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BLUE_PIGMENT)
                        .requires(BLUE_PIGMENT)
                        .requires(Items.FEATHER)
                        .requires(QUITOXIC_POWDER)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.FIRE_ASPECT
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.FIRE_CHARGE)
                        .requires(STORM_STONE)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.FIRE_PROTECTION
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PINK_PIGMENT)
                        .requires(PINK_PIGMENT)
                        .requires(Items.MAGMA_CREAM)
                        .requires(BEDROCK_DUST)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.FLAME
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.FIRE_CHARGE)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.FORTUNE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_LUCK)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(Items.DIAMOND)
                        .requires(PastelBlocks.FOUR_LEAF_CLOVER)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.FROST_WALKER
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TREASURE)
                        .craftingTime(1200)
                        .requiredExperience(500)
                        .requires(LIGHT_GRAY_PIGMENT)
                        .requires(LIGHT_GRAY_PIGMENT)
                        .requires(Items.BLUE_ICE)
                        .requires(PALTAERIA_FRAGMENTS)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.IMPALING
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BROWN_PIGMENT)
                        .requires(BROWN_PIGMENT)
                        .requires(Items.FLINT)
                        .requires(MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.INFINITY
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE_INFINITY)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(QUITOXIC_POWDER)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.KNOCKBACK
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BLACK_PIGMENT)
                        .requires(BLACK_PIGMENT)
                        .requires(Items.GUNPOWDER)
                        .requires(STORM_STONE)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.LOOTING
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_LUCK)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(Items.FLINT)
                        .requires(PastelBlocks.FOUR_LEAF_CLOVER)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.LOYALTY
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BROWN_PIGMENT)
                        .requires(BROWN_PIGMENT)
                        .requires(Items.STRING)
                        .requires(MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.LUCK_OF_THE_SEA
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER_LUCK)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(PastelBlocks.FOUR_LEAF_CLOVER)
                        .requires(MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.LURE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BLUE_PIGMENT)
                        .requires(BLUE_PIGMENT)
                        .requires(Items.FISHING_ROD)
                        .requires(MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.MENDING
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TREASURE)
                        .craftingTime(1200)
                        .requiredExperience(500)
                        .requires(LIGHT_GRAY_PIGMENT)
                        .requires(LIGHT_GRAY_PIGMENT)
                        .requires(Items.EXPERIENCE_BOTTLE)
                        .requires(PALTAERIA_FRAGMENTS)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.MULTISHOT
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.ARROW)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.PIERCING
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.FLINT)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.POWER
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.DIAMOND)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.PROJECTILE_PROTECTION
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PINK_PIGMENT)
                        .requires(PINK_PIGMENT)
                        .requires(Items.ARROW)
                        .requires(BEDROCK_DUST)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.PROTECTION
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PINK_PIGMENT)
                        .requires(PINK_PIGMENT)
                        .requires(Items.TURTLE_SCUTE)
                        .requires(BEDROCK_DUST)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.PUNCH
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.GUNPOWDER)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.QUICK_CHARGE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.SUGAR)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.RESPIRATION
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BLUE_PIGMENT)
                        .requires(BLUE_PIGMENT)
                        .requires(Items.PUFFERFISH)
                        .requires(MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.RIPTIDE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BROWN_PIGMENT)
                        .requires(BROWN_PIGMENT)
                        .requires(Items.WET_SPONGE)
                        .requires(MERMAIDS_GEM)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.SHARPNESS
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BLACK_PIGMENT)
                        .requires(BLACK_PIGMENT)
                        .requires(Items.FLINT)
                        .requires(STORM_STONE)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.SILK_TOUCH
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_SILK_TOUCH)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(CYAN_PIGMENT)
                        .requires(CYAN_PIGMENT)
                        .requires(Items.DIAMOND)
                        .requires(RAW_AZURITE)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.SMITE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(BLACK_PIGMENT)
                        .requires(BLACK_PIGMENT)
                        .requires(Items.ROTTEN_FLESH)
                        .requires(STORM_STONE)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.SOUL_SPEED
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TREASURE)
                        .craftingTime(1200)
                        .requiredExperience(500)
                        .requires(LIGHT_GRAY_PIGMENT)
                        .requires(LIGHT_GRAY_PIGMENT)
                        .requires(Items.SOUL_SOIL)
                        .requires(PALTAERIA_FRAGMENTS)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.SWEEPING_EDGE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(RED_PIGMENT)
                        .requires(RED_PIGMENT)
                        .requires(Items.FLINT)
                        .requires(STORM_STONE)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.SWIFT_SNEAK
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_SWIFT_SNEAK)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(LIGHT_BLUE_PIGMENT)
                        .requires(Items.SCULK)
                        .requires(NEOLITH)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.THORNS
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(PINK_PIGMENT)
                        .requires(PINK_PIGMENT)
                        .requires(Items.BLAZE_POWDER)
                        .requires(BEDROCK_DUST)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.UNBREAKING
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_UNBREAKING)
                        .craftingTime(300)
                        .requiredExperience(100)
                        .requires(CYAN_PIGMENT)
                        .requires(CYAN_PIGMENT)
                        .requires(Items.IRON_INGOT)
                        .requires(BEDROCK_DUST)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.VANISHING_CURSE
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_CURSES)
                        .craftingTime(600)
                        .requiredExperience(250)
                        .requires(GRAY_PIGMENT)
                        .requires(GRAY_PIGMENT)
                        .requires(Items.COAL)
                        .requires(STRATINE_FRAGMENTS)
        );

        pfx.generateAutoNamedRecipe(
                EnchanterCraftingRecipeBuilder.forEnchantment(
                        pfx.getLookup(),
                        Enchantments.WIND_BURST
                )
                        .group("vanilla")
                        .requiredAdvancement(PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL_TREASURE)
                        .craftingTime(1200)
                        .requiredExperience(500)
                        .requires(YELLOW_PIGMENT)
                        .requires(YELLOW_PIGMENT)
                        .requires(Items.WIND_CHARGE)
                        .requires(PALTAERIA_FRAGMENTS)
        );
    }
}
