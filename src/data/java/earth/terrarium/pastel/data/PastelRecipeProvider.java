package earth.terrarium.pastel.data;

import com.mojang.datafixers.util.Either;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.data.recipe.PastelPedestalRecipes;
import earth.terrarium.pastel.recipe.RecipeScaling;
import earth.terrarium.pastel.recipe.cantrip.DegradingRecipe;
import earth.terrarium.pastel.recipe.cantrip.HealingRecipe;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumCatalyst;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static earth.terrarium.pastel.registries.PastelEnchantments.BIG_CATCH;
import static earth.terrarium.pastel.registries.PastelEnchantments.CLOVERS_FAVOR;
import static earth.terrarium.pastel.registries.PastelEnchantments.DISARMING;
import static earth.terrarium.pastel.registries.PastelEnchantments.EXUBERANCE;
import static earth.terrarium.pastel.registries.PastelEnchantments.FIRST_STRIKE;
import static earth.terrarium.pastel.registries.PastelEnchantments.IMPROVED_CRITICAL;
import static earth.terrarium.pastel.registries.PastelEnchantments.INERTIA;
import static earth.terrarium.pastel.registries.PastelEnchantments.RAZING;
import static earth.terrarium.pastel.registries.PastelEnchantments.SERENDIPITY_REEL;
import static earth.terrarium.pastel.registries.PastelEnchantments.TIGHT_GRIP;
import static earth.terrarium.pastel.registries.PastelEnchantments.TREASURE_HUNTER;
import static earth.terrarium.pastel.registries.PastelItems.*;
import static java.util.Map.entry;
import static net.minecraft.world.item.enchantment.Enchantments.BANE_OF_ARTHROPODS;
import static net.minecraft.world.item.enchantment.Enchantments.BLAST_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.BREACH;
import static net.minecraft.world.item.enchantment.Enchantments.DENSITY;
import static net.minecraft.world.item.enchantment.Enchantments.DEPTH_STRIDER;
import static net.minecraft.world.item.enchantment.Enchantments.EFFICIENCY;
import static net.minecraft.world.item.enchantment.Enchantments.FEATHER_FALLING;
import static net.minecraft.world.item.enchantment.Enchantments.FIRE_ASPECT;
import static net.minecraft.world.item.enchantment.Enchantments.FIRE_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.FORTUNE;
import static net.minecraft.world.item.enchantment.Enchantments.FROST_WALKER;
import static net.minecraft.world.item.enchantment.Enchantments.IMPALING;
import static net.minecraft.world.item.enchantment.Enchantments.KNOCKBACK;
import static net.minecraft.world.item.enchantment.Enchantments.LOOTING;
import static net.minecraft.world.item.enchantment.Enchantments.LOYALTY;
import static net.minecraft.world.item.enchantment.Enchantments.LUCK_OF_THE_SEA;
import static net.minecraft.world.item.enchantment.Enchantments.LURE;
import static net.minecraft.world.item.enchantment.Enchantments.PIERCING;
import static net.minecraft.world.item.enchantment.Enchantments.POWER;
import static net.minecraft.world.item.enchantment.Enchantments.PROJECTILE_PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.PROTECTION;
import static net.minecraft.world.item.enchantment.Enchantments.PUNCH;
import static net.minecraft.world.item.enchantment.Enchantments.QUICK_CHARGE;
import static net.minecraft.world.item.enchantment.Enchantments.RESPIRATION;
import static net.minecraft.world.item.enchantment.Enchantments.RIPTIDE;
import static net.minecraft.world.item.enchantment.Enchantments.SHARPNESS;
import static net.minecraft.world.item.enchantment.Enchantments.SMITE;
import static net.minecraft.world.item.enchantment.Enchantments.SOUL_SPEED;
import static net.minecraft.world.item.enchantment.Enchantments.SWEEPING_EDGE;
import static net.minecraft.world.item.enchantment.Enchantments.SWIFT_SNEAK;
import static net.minecraft.world.item.enchantment.Enchantments.THORNS;
import static net.minecraft.world.item.enchantment.Enchantments.UNBREAKING;
import static net.minecraft.world.item.enchantment.Enchantments.WIND_BURST;

public class PastelRecipeProvider extends RecipeProvider {

    public PastelRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    public void buildRecipes(RecipeOutput recipeOutput, HolderLookup.Provider lookup) {
        generateCrystallarieumRecipes(recipeOutput);
        generateEnchantmentUpgradeRecipes(recipeOutput);
        generateHealingDegradingRecipes(recipeOutput);
        PastelPedestalRecipes.generate(recipeOutput, lookup);
    }



    private static final Map<Block, Block> HEALING_DEGRADING_PAIRS = Map
        .ofEntries(
            entry(Blocks.SAND, Blocks.GRAVEL),
            entry(Blocks.GRAVEL, Blocks.COBBLESTONE),
            entry(Blocks.COBBLESTONE, Blocks.STONE),
            entry(Blocks.STONE, Blocks.COBBLED_DEEPSLATE),
            entry(Blocks.COBBLED_DEEPSLATE, Blocks.DEEPSLATE),

            // further cobbleing
            entry(Blocks.COBBLESTONE_SLAB, Blocks.STONE_SLAB),
            entry(Blocks.COBBLESTONE_STAIRS, Blocks.STONE_STAIRS),

            // cracked blocks
            entry(Blocks.CRACKED_DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICKS),
            entry(Blocks.CRACKED_DEEPSLATE_TILES, Blocks.DEEPSLATE_TILES),
            entry(Blocks.CRACKED_NETHER_BRICKS, Blocks.NETHER_BRICKS),
            entry(Blocks.CRACKED_STONE_BRICKS, Blocks.STONE_BRICKS),
            entry(Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS, Blocks.POLISHED_BLACKSTONE_BRICKS),
            entry(Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS),
            entry(PastelBlocks.CRACKED_BASALT_BRICKS.get(), PastelBlocks.BASALT_BRICKS.get()),
            entry(PastelBlocks.CRACKED_BASALT_TILES.get(), PastelBlocks.BASALT_TILES.get()),
            entry(PastelBlocks.CRACKED_BLACKSLAG_BRICKS.get(), PastelBlocks.BLACKSLAG_BRICKS.get()),
//      entry(PastelBlocks.CRACKED_DRAGONBONE.get(), PastelBlocks.DRAGONBONE.get()), hahaha. no.
            entry(PastelBlocks.CRACKED_CALCITE_BRICKS.get(), PastelBlocks.CALCITE_BRICKS.get()),
            entry(PastelBlocks.CRACKED_CALCITE_TILES.get(), PastelBlocks.CALCITE_TILES.get())
//      entry(PastelBlocks.CRACKED_END_PORTAL_FRAME.get(), Blocks.END_PORTAL_FRAME) // nope
        );

    private void generateHealingDegradingRecipes(RecipeOutput ctx) {
        for (
            var entry : HEALING_DEGRADING_PAIRS.entrySet()
        ) {
            generateHealingRecipe(
                ctx,
                entry
                    .getKey()
                    .getDescriptionId() + "_healing",
                entry.getKey(),
                entry.getValue()
            );
            generateDegradingRecipe(
                ctx,
                entry
                    .getValue()
                    .getDescriptionId() + "_degrading",
                entry.getValue(),
                entry.getKey()
            );
        }
    }

    private void generateHealingRecipe(RecipeOutput ctx, String id, ItemLike base, ItemLike result) {
        generateRecipe(
            ctx,
            id,
            new HealingRecipe(
                "",
                false,
                Optional.empty(),
                Ingredient.of(base),
                result
                    .asItem()
                    .getDefaultInstance()
            )
        );
    }

    private void generateDegradingRecipe(RecipeOutput ctx, String id, ItemLike base, ItemLike result) {
        generateRecipe(
            ctx,
            id,
            new DegradingRecipe(
                "",
                false,
                Optional.empty(),
                Ingredient.of(base),
                result
                    .asItem()
                    .getDefaultInstance()
            )
        );
    }

    private void generateCrystallarieumRecipes(RecipeOutput ctx) {
        generateCrystallarieumRecipe(
            ctx,
            "minecraft/coal",
            Items.COAL,
            null,
            null,
            60,
            InkColors.BROWN,
            1,
            false,
            List
                .of(
                    PastelBlocks.SMALL_COAL_BUD.get(),
                    PastelBlocks.LARGE_COAL_BUD.get(),
                    PastelBlocks.COAL_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.CHARCOAL), 2.0f, 0.4f, 0.2f),
                    new CrystallarieumCatalyst(Ingredient.of(INCANDESCENT_ESSENCE), 16.0f, 2.0f, 0.05f),
                    new CrystallarieumCatalyst(Ingredient.of(VEGETAL), 0.75f, 0.05f, 0.4f)
                ),
            List
                .of(
                    PURE_COAL
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/copper",
            Items.RAW_COPPER,
            null,
            null,
            60,
            InkColors.BROWN,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_COPPER_BUD.get(),
                    PastelBlocks.LARGE_COPPER_BUD.get(),
                    PastelBlocks.COPPER_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(RAW_MALACHITE), 4.0f, 0.5f, 0.2f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.HONEYCOMB), 8.0f, 2.0f, 0.05f),
                    new CrystallarieumCatalyst(Ingredient.of(NEOLITH), 1.5f, 0.25f, 0.02f)
                ),
            List
                .of(
                    PURE_COPPER
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/diamond",
            Items.DIAMOND,
            null,
            null,
            480,
            InkColors.CYAN,
            3,
            false,
            List
                .of(
                    PastelBlocks.SMALL_DIAMOND_BUD.get(),
                    PastelBlocks.LARGE_DIAMOND_BUD.get(),
                    PastelBlocks.DIAMOND_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.COAL), 8.0f, 0.25f, 0.2f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.COAL_BLOCK), 10.0f, 0.25f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.CHARCOAL), 16.0f, 0.25f, 1.0f)
                ),
            List
                .of(
                    PURE_DIAMOND
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/echo",
            Items.ECHO_SHARD,
            PastelFluids.MIDNIGHT_SOLUTION.get(),
            null,
            960,
            InkColors.BROWN,
            3,
            false,
            List
                .of(
                    PastelBlocks.SMALL_ECHO_BUD.get(),
                    PastelBlocks.LARGE_ECHO_BUD.get(),
                    PastelBlocks.ECHO_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(FROSTBITE_ESSENCE), 1.5f, 2.0f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.ENDER_PEARL), 1.0f, 0.25f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.EXPERIENCE_BOTTLE), 8.0f, 2.0f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.SCULK), 2.0f, 0.125f, 0.02f)
                ),
            List
                .of(
                    PURE_ECHO
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/emerald",
            Items.EMERALD,
            null,
            null,
            60,
            InkColors.CYAN,
            3,
            false,
            List
                .of(
                    PastelBlocks.SMALL_EMERALD_BUD.get(),
                    PastelBlocks.LARGE_EMERALD_BUD.get(),
                    PastelBlocks.EMERALD_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.GUNPOWDER), 4.0f, 3.0f, 0.04f),
                    new CrystallarieumCatalyst(Ingredient.of(FROSTBITE_ESSENCE), 1.0f, 0.125f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(MIDNIGHT_CHIP), 16.0f, 4.0f, 0.2f)
                ),
            List
                .of(
                    PURE_EMERALD
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/glowstone",
            Items.GLOWSTONE,
            null,
            null,
            120,
            InkColors.YELLOW,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_GLOWSTONE_BUD.get(),
                    PastelBlocks.LARGE_GLOWSTONE_BUD.get(),
                    PastelBlocks.GLOWSTONE_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(SHIMMERSTONE_GEM), 16.0f, 1.0f, 0.1f),
                    new CrystallarieumCatalyst(Ingredient.of(FROSTBITE_ESSENCE), 4.0f, 0.25f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(MOONSTONE_SHARD), 1.0f, 0.01f, 0.05f)
                ),
            List
                .of(
                    PURE_GLOWSTONE
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/gold",
            Items.RAW_GOLD,
            null,
            null,
            60,
            InkColors.BROWN,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_GOLD_BUD.get(),
                    PastelBlocks.LARGE_GOLD_BUD.get(),
                    PastelBlocks.GOLD_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.GOLD_NUGGET), 4.0f, 0.5f, 0.2f),
                    new CrystallarieumCatalyst(Ingredient.of(SHIMMERSTONE_GEM), 8.0f, 2.0f, 0.05f),
                    new CrystallarieumCatalyst(Ingredient.of(NEOLITH), 1.5f, 0.25f, 0.02f)
                ),
            List
                .of(
                    PURE_GOLD
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/iron",
            Items.RAW_IRON,
            null,
            null,
            60,
            InkColors.BROWN,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_IRON_BUD.get(),
                    PastelBlocks.LARGE_IRON_BUD.get(),
                    PastelBlocks.IRON_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.IRON_NUGGET), 4.0f, 0.5f, 0.2f),
                    new CrystallarieumCatalyst(Ingredient.of(BEDROCK_DUST), 8.0f, 2.0f, 0.05f),
                    new CrystallarieumCatalyst(Ingredient.of(NEOLITH), 1.5f, 0.25f, 0.02f)
                ),
            List
                .of(
                    PURE_IRON
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/lapis",
            Items.LAPIS_LAZULI,
            null,
            null,
            60,
            InkColors.PURPLE,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_LAPIS_BUD.get(),
                    PastelBlocks.LARGE_LAPIS_BUD.get(),
                    PastelBlocks.LAPIS_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.EXPERIENCE_BOTTLE), 8.0f, 4.0f, 0.05f),
                    new CrystallarieumCatalyst(Ingredient.of(RAW_AZURITE), 0.5f, 0.1f, 0.004f),
                    new CrystallarieumCatalyst(Ingredient.of(MIDNIGHT_CHIP), 1.2f, 1.5f, 0.2f)
                ),
            List
                .of(
                    PURE_LAPIS
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/netherite_scrap",
            Items.NETHERITE_SCRAP,
            Fluids.LAVA,
            null,
            960,
            InkColors.BROWN,
            3,
            false,
            List
                .of(
                    PastelBlocks.SMALL_NETHERITE_SCRAP_BUD.get(),
                    PastelBlocks.LARGE_NETHERITE_SCRAP_BUD.get(),
                    PastelBlocks.NETHERITE_SCRAP_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(INCANDESCENT_ESSENCE), 1.5f, 2.0f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.FIRE_CHARGE), 1.0f, 0.25f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.GOLD_INGOT), 16.0f, 2.5f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(STRATINE_FRAGMENTS), 2.0f, 0.125f, 0.02f)
                ),
            List
                .of(
                    PURE_NETHERITE_SCRAP
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/prismarine_crystal",
            Items.PRISMARINE_CRYSTALS,
            Fluids.WATER,
            null,
            60,
            InkColors.CYAN,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_PRISMARINE_BUD.get(),
                    PastelBlocks.LARGE_PRISMARINE_BUD.get(),
                    PastelBlocks.PRISMARINE_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.PRISMARINE_SHARD), 2.0f, 0.5f, 0.04f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.WET_SPONGE), 1.0f, 0.7f, 0.0002f),
                    new CrystallarieumCatalyst(Ingredient.of(MERMAIDS_GEM), 32.0f, 2.0f, 0.1f)
                ),
            List
                .of(
                    PURE_PRISMARINE
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/quartz",
            Items.QUARTZ,
            Fluids.WATER,
            null,
            180,
            InkColors.CYAN,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_QUARTZ_BUD.get(),
                    PastelBlocks.LARGE_QUARTZ_BUD.get(),
                    PastelBlocks.QUARTZ_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.SAND), 3.0f, 2.0f, 0.25f),
                    new CrystallarieumCatalyst(Ingredient.of(MIDNIGHT_CHIP), 1.0f, 0.25f, 0.01f),
                    new CrystallarieumCatalyst(Ingredient.of(PastelBlocks.ROCK_CRYSTAL.get()), 12.0f, 0.5f, 0.1f)
                ),
            List
                .of(
                    PURE_QUARTZ
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "minecraft/redstone",
            Items.REDSTONE,
            null,
            null,
            60,
            InkColors.YELLOW,
            2,
            false,
            List
                .of(
                    PastelBlocks.SMALL_REDSTONE_BUD.get(),
                    PastelBlocks.LARGE_REDSTONE_BUD.get(),
                    PastelBlocks.REDSTONE_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(STORM_STONE), 8.0f, 2.0f, 0.01f),
                    new CrystallarieumCatalyst(Ingredient.of(SHIMMERSTONE_GEM), 1.0f, 0.25f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(STARDUST), 1.5f, 0.5f, 0.005f)
                ),
            List
                .of(
                    PURE_REDSTONE
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "pastel/azurite",
            RAW_AZURITE,
            null,
            PastelAdvancements.Midgame.COLLECT_AZURITE,
            300,
            InkColors.BLUE,
            4,
            false,
            List
                .of(
                    PastelBlocks.SMALL_AZURITE_BUD.get(),
                    PastelBlocks.LARGE_AZURITE_BUD.get(),
                    PastelBlocks.AZURITE_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.RAW_COPPER), 7.5f, 10.0f, 0.05f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.COPPER_INGOT), 1.0f, 0.5f, 0.15f),
                    new CrystallarieumCatalyst(Ingredient.of(PURE_COPPER), 1.708f, 0.707f, 0.01f)
                ),
            List
                .of(
                    PURE_AZURITE
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "pastel/bismuth",
            BISMUTH_FLAKE,
            null,
            null,
            120,
            InkColors.CYAN,
            4,
            false,
            List
                .of(
                    PastelBlocks.SMALL_BISMUTH_BUD.get(),
                    PastelBlocks.LARGE_BISMUTH_BUD.get(),
                    PastelBlocks.BISMUTH_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(BISMUTH_FLAKE), 8.0f, 1.0f, 0.2f),
                    new CrystallarieumCatalyst(Ingredient.of(STARDUST), 2.0f, 0.25f, 0.02f),
                    new CrystallarieumCatalyst(Ingredient.of(STAR_FRAGMENT), 1.25f, 1.0f, 0.0002f)
                ),
            List
                .of(
                    BISMUTH_CRYSTAL
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "pastel/bloodstone",
            RAW_BLOODSTONE,
            null,
            PastelAdvancements.Unlocks.Resources.BLOODSTONE,
            300,
            InkColors.RED,
            4,
            false,
            List
                .of(
                    PastelBlocks.SMALL_BLOODSTONE_BUD.get(),
                    PastelBlocks.LARGE_BLOODSTONE_BUD.get(),
                    PastelBlocks.BLOODSTONE_CLUSTER.get()
                ),
            List
                .of(
                    new CrystallarieumCatalyst(Ingredient.of(Items.RAW_COPPER), 7.5f, 10.0f, 0.05f),
                    new CrystallarieumCatalyst(Ingredient.of(Items.COPPER_INGOT), 1.0f, 0.5f, 0.15f),
                    new CrystallarieumCatalyst(Ingredient.of(PURE_COPPER), 1.708f, 0.707f, 0.01f)
                ),
            List
                .of(
                    PURE_BLOODSTONE
                        .get()
                        .getDefaultInstance()
                )
        );

        generateCrystallarieumRecipe(
            ctx,
            "pastel/malachite",
            RAW_MALACHITE,
            null,
            PastelAdvancements.Lategame.COLLECT_MALACHITE,
            300,
            InkColors.WHITE,
            4,
            false,
            List
                .of(
                    PastelBlocks.SMALL_MALACHITE_BUD.get(),
                    PastelBlocks.LARGE_MALACHITE_BUD.get(),
                    PastelBlocks.MALACHITE_CLUSTER.get()
                ),
            List.of(new CrystallarieumCatalyst(Ingredient.of(MOONSTONE_POWDER), 1.0f, 1.0f, 0.04f)),
            List
                .of(
                    PURE_MALACHITE
                        .get()
                        .getDefaultInstance()
                )
        );
    }

    private void generateEnchantmentUpgradeRecipes(RecipeOutput ctx) {
        //TODO These could benefit from a revisit

        // Pastel
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            BIG_CATCH,
            PastelAdvancements.Unlocks.Enchantments.BIG_CATCH,
            LIGHT_BLUE_PIGMENT,
            3,
            RecipeScaling.doubling(400),
            RecipeScaling.indices(32, 128)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            CLOVERS_FAVOR,
            PastelAdvancements.Unlocks.Enchantments.CLOVERS_FAVOR,
            LIGHT_BLUE_PIGMENT,
            6,
            RecipeScaling.indices(200, 400, 2000, 10000, 40000),
            RecipeScaling.indices(8, 32, 128, 512, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            DISARMING,
            PastelAdvancements.Unlocks.Enchantments.DISARMING,
            RED_PIGMENT,
            4,
            RecipeScaling.indices(400, 2000, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            EXUBERANCE,
            PastelAdvancements.Unlocks.Enchantments.EXUBERANCE,
            PURPLE_PIGMENT,
            10,
            RecipeScaling.linear(400, 200, 1.0F),
            RecipeScaling.indices(8, 16, 32, 64, 128, 256, 512, 512, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            FIRST_STRIKE,
            PastelAdvancements.Unlocks.Enchantments.FIRST_STRIKE,
            PINK_PIGMENT,
            5,
            RecipeScaling.indices(200, 400, 2000, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            IMPROVED_CRITICAL,
            PastelAdvancements.Unlocks.Enchantments.IMPROVED_CRITICAL,
            BLACK_PIGMENT,
            4,
            RecipeScaling.indices(400, 2000, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            INERTIA,
            PastelAdvancements.Unlocks.Enchantments.INERTIA,
            BROWN_PIGMENT,
            5,
            RecipeScaling.indices(200, 400, 2000, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            RAZING,
            PastelAdvancements.Unlocks.Enchantments.RAZING_USAGE,
            GRAY_PIGMENT,
            5,
            RecipeScaling.indices(400, 2000, 10000, 10000),
            RecipeScaling.indices(32, 128, 256, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            SERENDIPITY_REEL,
            PastelAdvancements.Unlocks.Enchantments.SERENDIPITY_REEL,
            LIGHT_BLUE_PIGMENT,
            3,
            RecipeScaling.doubling(400),
            RecipeScaling.indices(32, 128)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            TIGHT_GRIP,
            PastelAdvancements.Unlocks.Enchantments.TIGHT_GRIP,
            YELLOW_PIGMENT,
            4,
            RecipeScaling.indices(400, 2000, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "",
            TREASURE_HUNTER,
            PastelAdvancements.Unlocks.Enchantments.TREASURE_HUNTER,
            LIGHT_BLUE_PIGMENT,
            5,
            RecipeScaling.indices(200, 400, 2000, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );

        // Vanilla
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            BANE_OF_ARTHROPODS,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE,
            BLACK_PIGMENT,
            8,
            RecipeScaling.doubling(100),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            BLAST_PROTECTION,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION,
            PINK_PIGMENT,
            8,
            RecipeScaling.doubling(100),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            DEPTH_STRIDER,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER,
            BLUE_PIGMENT,
            3,
            RecipeScaling.doubling(200),
            RecipeScaling.indices(8, 32)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            EFFICIENCY,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_QUITOXIC,
            YELLOW_PIGMENT,
            8,
            RecipeScaling.indices(200, 400, 600, 800, 1600, 2600, 4000),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            FEATHER_FALLING,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_QUITOXIC,
            BLUE_PIGMENT,
            6,
            RecipeScaling.doubling(250),
            RecipeScaling.indices(8, 16, 32, 64, 256)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            FIRE_ASPECT,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE,
            RED_PIGMENT,
            4,
            RecipeScaling.doubling(200, 200, 2.0F),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            FIRE_PROTECTION,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION,
            PINK_PIGMENT,
            8,
            RecipeScaling.indices(100, 200, 300, 400, 800, 1300, 2000),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            FORTUNE,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_LUCK,
            LIGHT_BLUE_PIGMENT,
            5,
            RecipeScaling.indices(100, 400, 3000, 10000),
            RecipeScaling.indices(32, 128, 256, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            FROST_WALKER,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_TREASURE,
            LIGHT_GRAY_PIGMENT,
            4,
            RecipeScaling.indices(400, 1600, 3200),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            IMPALING,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT,
            BROWN_PIGMENT,
            8,
            RecipeScaling.indices(100, 200, 300, 400, 800, 1300, 2000),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            KNOCKBACK,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE,
            BLACK_PIGMENT,
            5,
            RecipeScaling.indices(200, 1600, 3200, 6400),
            RecipeScaling.indices(8, 32, 128, 256)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            LOOTING,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_LUCK,
            LIGHT_BLUE_PIGMENT,
            6,
            RecipeScaling.indices(200, 500, 2400, 10000, 40000),
            RecipeScaling.indices(8, 32, 128, 512, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            LOYALTY,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT,
            BROWN_PIGMENT,
            4,
            RecipeScaling.doubling(0, 200, 2.0F),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            LUCK_OF_THE_SEA,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER_LUCK,
            LIGHT_BLUE_PIGMENT,
            5,
            RecipeScaling.indices(200, 400, 2000, 4000),
            RecipeScaling.indices(8, 32, 128, 256)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            LURE,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER,
            BLUE_PIGMENT,
            5,
            RecipeScaling.indices(200, 400, 2000, 4000),
            RecipeScaling.indices(8, 32, 128, 256)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            PIERCING,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE,
            RED_PIGMENT,
            8,
            RecipeScaling.indices(100, 200, 300, 400, 800, 1300, 2000),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            POWER,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE,
            RED_PIGMENT,
            8,
            RecipeScaling.doubling(200),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            PROJECTILE_PROTECTION,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION,
            PINK_PIGMENT,
            8,
            RecipeScaling.indices(100, 200, 300, 400, 800, 1300, 2000),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            PROTECTION,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION,
            PINK_PIGMENT,
            8,
            RecipeScaling.indices(200, 400, 600, 800, 1600, 4000, 8000),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            PUNCH,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE,
            RED_PIGMENT,
            5,
            RecipeScaling.indices(200, 1600, 3200, 6400),
            RecipeScaling.indices(8, 32, 128, 256)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            QUICK_CHARGE,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROJECTILE,
            RED_PIGMENT,
            5,
            RecipeScaling.indices(200, 1600, 5000, 10000),
            RecipeScaling.indices(8, 32, 512, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            RESPIRATION,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_WATER,
            BLUE_PIGMENT,
            6,
            RecipeScaling.indices(100, 200, 1600, 4800, 10000),
            RecipeScaling.indices(8, 32, 128, 256, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            RIPTIDE,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIDENT,
            BROWN_PIGMENT,
            3,
            RecipeScaling.indices(200, 2400, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            SHARPNESS,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE,
            BLACK_PIGMENT,
            8,
            RecipeScaling.doubling(75),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            SMITE,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE,
            BLACK_PIGMENT,
            8,
            RecipeScaling.indices(100, 200, 300, 400, 800, 1300, 2000),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            SOUL_SPEED,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_TREASURE,
            LIGHT_GRAY_PIGMENT,
            3,
            RecipeScaling.indices(200, 2400, 10000),
            RecipeScaling.doubling(0, 8, 2.0F)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            SWEEPING_EDGE,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE,
            RED_PIGMENT,
            7,
            RecipeScaling.indices(100, 400, 1000, 2000, 5000, 10000),
            RecipeScaling.indices(8, 32, 64, 128, 256, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            SWIFT_SNEAK,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_SWIFT_SNEAK,
            LIGHT_BLUE_PIGMENT,
            5,
            RecipeScaling.indices(200, 600, 2000, 5000),
            RecipeScaling.indices(8, 32, 128, 256)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            THORNS,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_PROTECTION,
            PINK_PIGMENT,
            6,
            RecipeScaling.indices(100, 400, 2000, 4000, 10000),
            RecipeScaling.indices(8, 32, 128, 256, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            UNBREAKING,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_UNBREAKING,
            CYAN_PIGMENT,
            6,
            RecipeScaling.indices(100, 400, 2000, 4000, 10000),
            RecipeScaling.indices(8, 32, 256, 512, 512)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            WIND_BURST,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_DAMAGE,
            YELLOW_PIGMENT,
            5,
            RecipeScaling.doubling(200),
            RecipeScaling.doubling(16)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            BREACH,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL_BREACHING,
            RED_PIGMENT,
            5,
            RecipeScaling.doubling(200),
            RecipeScaling.doubling(8)
        );
        generateEnchantmentUpgradeRecipe(
            ctx,
            "minecraft",
            DENSITY,
            PastelAdvancements.Unlocks.Enchantments.VANILLA_TRIAL,
            CYAN_PIGMENT,
            8,
            RecipeScaling.doubling(400),
            RecipeScaling.doubling(16)
        );

    }



    private void generateCrystallarieumRecipe(
        RecipeOutput ctx,
        String id,
        ItemLike base,
        @Nullable Fluid medium,
        @Nullable ResourceLocation advancement,
        int secondsPerStage,
        InkColor inkColor,
        int inkCostTier,
        boolean growsWithoutCatalyst,
        List<Block> stages,
        List<CrystallarieumCatalyst> catalysts,
        List<ItemStack> additionalResults
    ) {
        generateRecipe(
            ctx,
            "crystallarieum/" + id,
            new CrystallarieumRecipe(
                "",
                false,
                Optional.ofNullable(advancement),
                Ingredient.of(base),
                stages
                    .stream()
                    .map(
                        s -> s
                            .defaultBlockState()
                            .setValue(
                                BlockStateProperties.FACING,
                                Direction.UP
                            )
                    )
                    .toList(),
                secondsPerStage,
                inkColor,
                1 << (inkCostTier - 1),
                growsWithoutCatalyst,
                catalysts,
                Optional
                    .of(
                        FluidIngredient
                            .of(
                                new FluidStack(
                                    medium == null ? PastelFluids.LIQUID_CRYSTAL.get() : medium,
                                    FluidType.BUCKET_VOLUME
                                )
                            )
                    ),
                additionalResults
            )
        );
    }

    private void generateEnchantmentUpgradeRecipe(
        RecipeOutput ctx,
        String group,
        ResourceKey<Enchantment> enchantment,
        ResourceLocation advancement,
        DeferredItem<Item> bulkItem,
        int levelCap,
        RecipeScaling.ScalingData xpScaling,
        RecipeScaling.ScalingData itemScaling
    ) {
        ctx = ctx.withConditions(new PastelResourceConditions.EnchantmentsExistResourceCondition(List.of(enchantment)));
        String namespace = enchantment
            .location()
            .getNamespace();
        String base = "enchantment_upgrade/" + namespace + "/" + enchantment
            .location()
            .getPath()
            .replace("/", ".");
        generateRecipe(
            ctx,
            base,
            new EnchantmentUpgradeRecipe(
                group,
                false,
                Optional.of(advancement),
                Either.right(enchantment),
                levelCap,
                Ingredient.of(bulkItem),
                xpScaling,
                itemScaling
            )
        );
    }

    private void generateRecipe(RecipeOutput ctx, String id, Recipe<?> recipe) {
        ctx.accept(PastelCommon.locate(id), recipe, null);
    }

}
