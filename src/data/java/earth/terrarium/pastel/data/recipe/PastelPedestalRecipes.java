package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColorMixes;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.item.Preenchanted;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.compat.create.CreateCompat;
import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.helpers.level.collections.PastelGemstoneColorCollection;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.items.tools.MoltenRodItem;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.data.recipe.builder.pedestal.ShapedPedestalRecipeBuilder;
import earth.terrarium.pastel.data.recipe.builder.pedestal.ShapelessPedestalRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.dynamic.EnderCanvasLargeRecipe;
import earth.terrarium.pastel.recipe.pedestal.dynamic.EnderCanvasRecipe;
import earth.terrarium.pastel.recipe.pedestal.dynamic.StarCandyRecipe;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.BlockItemStateProperties;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Triplet;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static earth.terrarium.pastel.registries.PastelItems.*;

public class PastelPedestalRecipes {

    private static final PastelGemstoneColorCollection<ResourceLocation> BASE_SHARD_UNLOCKS =
            new PastelGemstoneColorCollection<>(
                    PastelAdvancements.Hidden.CollectShards.TOPAZ,
                    PastelAdvancements.Hidden.CollectShards.AMETHYST,
                    PastelAdvancements.Hidden.CollectShards.CITRINE,
                    PastelAdvancements.CREATE_ONYX_SHARD,
                    PastelAdvancements.Lategame.COLLECT_MOONSTONE
            );

    private static ResourceLocation blockUnlock(DeferredBlock<?> block) {
        return PastelCommon.locate("unlocks/blocks/" + block.getId().getPath());
    }

    private static ResourceLocation itemUnlock(DeferredItem<?> item) {
        return PastelCommon.locate("unlocks/items/" + item.getId().getPath());
    }

    private static GemstoneColor unsafeGemstoneColorFromInkColor(InkColor color) {
        if (InkColors.CYAN == color) {
            return PastelGemstoneColor.CYAN;
        } else if (InkColors.MAGENTA == color) {
            return PastelGemstoneColor.MAGENTA;
        } else if (InkColors.YELLOW == color) {
            return PastelGemstoneColor.YELLOW;
        } else if (InkColors.BLACK == color) {
            return PastelGemstoneColor.BLACK;
        } else if (InkColors.WHITE == color) {
            return PastelGemstoneColor.WHITE;
        }
        throw new IllegalArgumentException("Tried to get gemstone color from non-primary ink color");
    }

    private static Map<GemstoneColor, Integer> getPowderMix(InkColor color, int total) {
        return InkColorMixes.getColorsToMix(color).map(it ->
                it.entrySet().stream().map(entry ->
                        Map.entry(unsafeGemstoneColorFromInkColor(entry.getKey()), (int)(entry.getValue() * total))
                ).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
        ).orElseGet(() -> Map.of(unsafeGemstoneColorFromInkColor(color), total));
    }

    private static final PastelInkColorCollection<Map<GemstoneColor, Integer>> POWDER_MIXES_6 =
            PastelInkColorCollection.VALUES.map(it -> getPowderMix(it, 6));

    private static final PastelInkColorCollection<PedestalTier> MINIMUM_COLOR_TIER =
            new PastelInkColorCollection<>(
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.BASIC,
                    PedestalTier.ADVANCED,
                    PedestalTier.ADVANCED,
                    PedestalTier.COMPLEX,
                    PedestalTier.COMPLEX,
                    PedestalTier.COMPLEX
            );

    private static String tierIdOf(PedestalTier tier) {
        return switch (tier) {
            case BASIC -> "tier1";
            case SIMPLE -> "tier2";
            case ADVANCED -> "tier3";
            case COMPLEX -> "tier4";
        };
    }




    // Enchantments requires threading through a holder lookup
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        BasicRecipes.generate(ctx, lookup);
        SimpleRecipes.generate(ctx, lookup);
        AdvancedRecipes.generate(ctx, lookup);
        ComplexRecipes.generate(ctx, lookup);
    }


    private static PrefixHelper rootPrefixHelper(RecipeOutput ctx, HolderLookup.Provider lookup, PedestalTier tier) {
        String tierId = tierIdOf(tier);
        return new PrefixHelper(ctx, lookup, "pedestal/" + tierId);
    }

    private static class BasicRecipes {
        static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
            var rootHelper = rootPrefixHelper(ctx, lookup, PedestalTier.BASIC);

            generateArrowRecipes(rootHelper.subPrefix("arrows"));
            generateColoredLampRecipes(rootHelper.subPrefix("colored_lamps"));
            generateCompactingRecipes(rootHelper.subPrefix("compacting"));
            generateCrystalArmorRecipes(rootHelper.subPrefix("crystal_armor"));
            generateCushionRecipes(rootHelper.subPrefix("cushions"));
            generateDetectorRecipes(rootHelper.subPrefix("detectors"));
            generateDragonboneRecipes(rootHelper.subPrefix("dragonbone"));
            generateFoodRecipes(rootHelper.subPrefix("food"));
            generateGemstoneLightRecipes(rootHelper.subPrefix("gemstone_lights"));
            generateBasicGlasses(rootHelper.subPrefix("glass"));
            generateJadeiteRecipes(rootHelper.subPrefix("jadeite"));
            generateJadeVinesRecipes(rootHelper.subPrefix("jade_vines"));
            generateNoxwoodRecipes(rootHelper.subPrefix("noxwood"));
            generatePigmentBlocks(rootHelper.subPrefix("pigment_blocks"));
            generatePylons(rootHelper.subPrefix("pylons"));
            generateResplendentRecipes(rootHelper.subPrefix("resplendent"));
            generateRunes(rootHelper.subPrefix("runes"));
            generateSaplings(rootHelper.subPrefix("saplings"));
            generateShimmerstoneLightRecipes(rootHelper.subPrefix("shimmerstone_lights"));
            generateToolRecipes(rootHelper.subPrefix("tools"));
            generateVanillaRecipes(rootHelper.subPrefix("vanilla"));
            generateWeepingGalaRecipes(rootHelper.subPrefix("weeping_gala"));
            generateRootRecipes(rootHelper);
        }

        // basic generation groups

        private static void generateArrowRecipes(PrefixHelper prefixHelper) {
            prefixHelper.generateRecipe(
                    "malachite",
                    new ShapedPedestalRecipeBuilder(new ItemStack(MALACHITE_GLASS_ARROW.get(), 4))
                            .group("glass_arrows")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("M")
                            .pattern("S")
                            .pattern("F")
                            .define('M', RAW_MALACHITE.get())
                            .define('S', Items.STICK)
                            .define('F', PastelItemTags.RESPLENDENT_FEATHERS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.GLASS_ARROWS)
            );

            for (PastelGemstoneColor color : PastelGemstoneColor.values()) {
                generateGemstoneArrowRecipe(prefixHelper, color);
            }
        }

        private static void generateColoredLampRecipes(PrefixHelper ctx) {
            InkColors.BUILTIN_COLORS.forEach(color -> generateColoredLamp(ctx, color));
        }

        private static void generateCompactingRecipes(PrefixHelper pfx) {

            for (PastelGemstoneColor color : PastelGemstoneColor.values()) {
                var unpacked = GEMSTONE_POWDERS.pick(color);
                var packed = PastelBlocks.GEMSTONE_POWDER_BLOCKS.pick(color);
                var unlock = BASE_SHARD_UNLOCKS.pick(color);
                generateCompactingPair(pfx, unlock, unpacked, packed);
            }


            generateCompactingPair(pfx, PastelAdvancements.Midgame.COLLECT_AZURITE, PURE_AZURITE, PastelBlocks.AZURITE_BLOCK);
            generateCompactingPair(pfx, "bedrock_dust_block_uncrafting", PastelAdvancements.Midgame.BREAK_DECAYED_BEDROCK, BEDROCK_DUST, PastelBlocks.BEDROCK_DUST_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_BISMUTH_CRYSTAL, BISMUTH_CRYSTAL, PastelBlocks.BISMUTH_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.GROW_BLOODSTONE_IN_CRYSTALLARIEUM, PURE_BLOODSTONE, PastelBlocks.BLOODSTONE_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.GROW_MALACHITE_IN_CRYSTALLARIEUM, PURE_MALACHITE, PastelBlocks.MALACHITE_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Midgame.COLLECT_NEOLITH, NEOLITH, PastelBlocks.NEOLITH_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.CARRY_TOO_MANY_LOW_GRAVITY_BLOCKS, PALTAERIA_FRAGMENTS, PastelBlocks.PALTAERIA_FLOATBLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_COAL, PastelBlocks.PURE_COAL_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_COPPER, PastelBlocks.PURE_COPPER_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_DIAMOND, PastelBlocks.PURE_DIAMOND_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_ECHO, PastelBlocks.PURE_ECHO_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_EMERALD, PastelBlocks.PURE_EMERALD_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_GLOWSTONE, PastelBlocks.PURE_GLOWSTONE_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_GOLD, PastelBlocks.PURE_GOLD_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_IRON, PastelBlocks.PURE_IRON_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_LAPIS, PastelBlocks.PURE_LAPIS_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_NETHERITE_SCRAP, PastelBlocks.PURE_NETHERITE_SCRAP_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_PRISMARINE, PastelBlocks.PURE_PRISMARINE_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_QUARTZ, PastelBlocks.PURE_QUARTZ_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_REDSTONE, PastelBlocks.PURE_REDSTONE_BLOCK);
            // for some reason this one AND ONLY THIS ONE is plural
            generateCompactingPair(pfx, "shimmerstone_gems_from_shimmerstone_block", PastelAdvancements.COLLECT_SHIMMERSTONE, SHIMMERSTONE_GEM, PastelBlocks.SHIMMERSTONE_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Hidden.COLLECT_STARDUST, STARDUST, PastelBlocks.STARDUST_BLOCK);
            generateCompactingPair(pfx, PastelAdvancements.Midgame.CARRY_TOO_MANY_HEAVY_GRAVITY_BLOCKS, STRATINE_FRAGMENTS, PastelBlocks.STRATINE_FLOATBLOCK);
            generateCompactingPair(pfx, PastelAdvancements.COLLECT_VEGETAL, VEGETAL, PastelBlocks.VEGETAL_BLOCK);

            // CREATE INTEGRATION

            var createPfx = pfx.modIntegration("create");
            generateCompactingPair(
                    createPfx,
                    PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE,
                    CreateCompat.PURE_ZINC,
                    CreateCompat.PURE_ZINC_BLOCK
            );

        }

        private static void generateCrystalArmorRecipes(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(AMETHYST_CHESTPLATE.get()))
                            .tier(PedestalTier.BASIC)
                            .craftingTime(400)
                            .color(PastelGemstoneColor.MAGENTA, 4)
                            .experience(2.0f)
                            .pattern("A A")
                            .pattern("ATA")
                            .pattern("AAA")
                            .define('A', Items.AMETHYST_SHARD)
                            .define('T', Items.GHAST_TEAR)
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.AMETHYST)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CITRINE_BOOTS.get()))
                            .tier(PedestalTier.BASIC)
                            .craftingTime(400)
                            .color(PastelGemstoneColor.YELLOW, 4)
                            .experience(2.0f)
                            .pattern("S S")
                            .pattern("C C")
                            .pattern("C C")
                            .define('S', Items.SUGAR)
                            .define('C', CITRINE_SHARD.get())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.CITRINE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(TOPAZ_LEGGINGS.get()))
                            .tier(PedestalTier.BASIC)
                            .craftingTime(400)
                            .color(PastelGemstoneColor.CYAN, 4)
                            .experience(2.0f)
                            // is that a deepslate in your pants or are you just happy to see me?
                            .pattern("TTT")
                            .pattern("TDT")
                            .pattern("T T")
                            .define('T', TOPAZ_SHARD.get())
                            .define('D', Items.DEEPSLATE)
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.TOPAZ)
            );
        }

        private static void generateCushionRecipes(PrefixHelper pfx) {
            PastelInkColorCollection<Item> carpets =
                    new PastelInkColorCollection<>(
                            Items.CYAN_CARPET,
                            Items.LIGHT_BLUE_CARPET,
                            Items.BLUE_CARPET,
                            Items.PURPLE_CARPET,
                            Items.MAGENTA_CARPET,
                            Items.PINK_CARPET,
                            Items.RED_CARPET,
                            Items.ORANGE_CARPET,
                            Items.YELLOW_CARPET,
                            Items.LIME_CARPET,
                            Items.GREEN_CARPET,
                            Items.BROWN_CARPET,
                            Items.BLACK_CARPET,
                            Items.GRAY_CARPET,
                            Items.LIGHT_GRAY_CARPET,
                            Items.WHITE_CARPET
                    );
            PastelInkColorCollection.VALUES.forEach(
                    color -> {
                        var cushion = PastelBlocks.CUSHIONS.pick(color);
                        pfx.generateAutoNamedRecipe(
                                new ShapedPedestalRecipeBuilder(new ItemStack(cushion.asItem(), 2))
                                        .tier(PedestalTier.BASIC)
                                        .craftingTime(80)
                                        .experience(1.0f)
                                        .pattern("CCC")
                                        .pattern("FFF")
                                        .pattern("CCC")
                                        .define('C', carpets.pick(color))
                                        .define('F', Tags.Items.FEATHERS)
                                        .requiredAdvancement(PastelAdvancements.CRAFT_USING_PEDESTAL)
                        );
                    }
            );
        }

        private static void generateDetectorRecipes(PrefixHelper pfx) {
            generateDetectorRecipe(pfx, PastelGemstoneColor.CYAN, PastelAdvancements.Unlocks.Redstone.ITEM_DETECTOR, PastelBlocks.ITEM_DETECTOR);
            generateDetectorRecipe(pfx, PastelGemstoneColor.MAGENTA, PastelAdvancements.Unlocks.Redstone.BLOCK_LIGHT_DETECTOR, PastelBlocks.BLOCK_LIGHT_DETECTOR);
            generateDetectorRecipe(pfx, PastelGemstoneColor.YELLOW, PastelAdvancements.Unlocks.Redstone.WEATHER_DETECTOR, PastelBlocks.WEATHER_DETECTOR);
            generateDetectorRecipe(pfx, PastelGemstoneColor.BLACK, PastelAdvancements.Unlocks.Redstone.PLAYER_DETECTOR, PastelBlocks.PLAYER_DETECTOR);
            generateDetectorRecipe(pfx, PastelGemstoneColor.WHITE, PastelAdvancements.Unlocks.Redstone.CREATURE_DETECTOR, PastelBlocks.CREATURE_DETECTOR);
        }

        private static void generateDragonboneRecipes(PrefixHelper prefixHelper) {

            // polished bone ash
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.POLISHED_BONE_ASH))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("###")
                            .pattern("###")
                            .pattern("###")
                            .define('#', BONE_ASH.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.POLISHED_BONE_ASH_PILLAR, 2))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("#")
                            .pattern("#")
                            .define('#', PastelBlocks.POLISHED_BONE_ASH.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
            generateDragonboneSlab(prefixHelper, PastelBlocks.POLISHED_BONE_ASH_SLAB, PastelBlocks.POLISHED_BONE_ASH);
            generateDragonboneStairs(prefixHelper, PastelBlocks.POLISHED_BONE_ASH_STAIRS, PastelBlocks.POLISHED_BONE_ASH);
            generateDragonboneWall(prefixHelper, PastelBlocks.POLISHED_BONE_ASH_WALL, PastelBlocks.POLISHED_BONE_ASH);

            // bone ash bricks
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BONE_ASH_BRICKS, 4))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("WW")
                            .pattern("WW")
                            .define('W', PastelBlocks.POLISHED_BONE_ASH.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
            generateDragonboneSlab(prefixHelper, PastelBlocks.BONE_ASH_BRICK_SLAB, PastelBlocks.BONE_ASH_BRICKS);
            generateDragonboneStairs(prefixHelper, PastelBlocks.BONE_ASH_BRICK_STAIRS, PastelBlocks.BONE_ASH_BRICKS);
            generateDragonboneWall(prefixHelper, PastelBlocks.BONE_ASH_BRICK_WALL, PastelBlocks.BONE_ASH_BRICKS);

            // bone ash tiles
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BONE_ASH_TILES, 4))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("WW")
                            .pattern("WW")
                            .define('W', PastelBlocks.BONE_ASH_BRICKS.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
            generateDragonboneSlab(prefixHelper, PastelBlocks.BONE_ASH_TILE_SLAB, PastelBlocks.BONE_ASH_TILES);
            generateDragonboneStairs(prefixHelper, PastelBlocks.BONE_ASH_TILE_STAIRS, PastelBlocks.BONE_ASH_TILES);
            generateDragonboneWall(prefixHelper, PastelBlocks.BONE_ASH_TILE_WALL, PastelBlocks.BONE_ASH_TILES);

            // shingles

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BONE_ASH_SHINGLES, 6))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("#  ")
                            .pattern("#B ")
                            .pattern("##B")
                            .define('#', PastelBlocks.POLISHED_BONE_ASH.asItem())
                            .define('B', DRAGONBONE_CHUNK.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
        }

        private static void generateFoodRecipes(PrefixHelper prefixHelper) {

            // Tarts
            prefixHelper.generateAutoNamedRecipe(
                    tartBase(ASHEN_TART.asItem(), Items.SWEET_BERRIES)
                            .color(PastelGemstoneColor.CYAN, 2)
                            .color(PastelGemstoneColor.YELLOW, 4)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(JARAMEL_TART.asItem(), Items.AIR)
                            .color(PastelGemstoneColor.YELLOW, 2)
            );
            prefixHelper.generateAutoNamedRecipe(
                    tartBase(PUFF_TART.asItem(), Items.DRAGON_BREATH)
                            .color(PastelGemstoneColor.CYAN, 2)
                            .color(PastelGemstoneColor.MAGENTA, 2)
                            .color(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(SALTED_JARAMEL_TART.asItem(), Items.GHAST_TEAR)
                            .secret(true)
                            .color(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(WEEPING_TART.asItem(), Items.KELP)
                            .color(PastelGemstoneColor.CYAN, 4)
                            .color(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(WHISPY_TART.asItem(), NIGHTDEW_SPROUT.asItem())
                            .color(PastelGemstoneColor.MAGENTA, 4)
                            .color(PastelGemstoneColor.YELLOW, 2)
            );

            // Trifles
            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(DEMON_TRIFLE.asItem(), BLOODBOIL_SYRUP.asItem())
                            .color(PastelGemstoneColor.YELLOW, 2)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.DEMON_TRIFLE)
            );

            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(JARAMEL_TRIFLE.asItem(), Items.AIR)
                            .color(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(MONSTER_TRIFLE.asItem(), QUITOXIC_POWDER.asItem())
                            .color(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(SALTED_JARAMEL_TRIFLE.asItem(), Items.GHAST_TEAR)
                            .color(PastelGemstoneColor.YELLOW, 2)
                            .secret(true)
            );

            // AHHH THE MEATLOAF :sob:

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(MEATLOAF.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("AHA")
                            .pattern("EME")
                            .pattern("JEJ")
                            .define('E', LIZARD_MEAT.asItem())
                            .define('A', Items.BEETROOT)
                            .define('M', MYCEYLON.asItem())
                            .define('H', Items.HONEY_BOTTLE)
                            .define('J', infusedBeverageIngredient(InfusedBeverageComponent.MALT_BEER))
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BAGNUN.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("EBE")
                            .pattern("JMJ")
                            .pattern("AAA")
                            .define('A', CRAWFISH.asItem())
                            .define('B', CLOTTED_CREAM.asItem())
                            .define('J', Tags.Items.MUSHROOMS)
                            .define('M', JADE_WINE.asItem())
                            .define('E', MYCEYLON.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMBRIFER_COOKBOOK)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BANYASH.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("JBJ")
                            .pattern("EME")
                            .pattern("AAA")
                            .define('A',CRAWFISH.asItem())
                            .define('J', Items.SWEET_BERRIES)
                            .define('E', Tags.Items.MUSHROOMS)
                            .define('B', PRICKLY_BAYLEAF.asItem())
                            .define('M', infusedBeverageIngredient(InfusedBeverageComponent.RUM))
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_1)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BERLINER.asItem()))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.2f)
                            .pattern("CCC")
                            .pattern("XSX")
                            .pattern("EEE")
                            .define('X', FRESH_CHOCOLATE.asItem())
                            .define('E', AMARANTH_GRAINS.asItem())
                            .define('C', CLOTTED_CREAM.asItem())
                            .define('S', infusedBeverageIngredient(InfusedBeverageComponent.SAWBLADE_HOLLY_LIQUOR))
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BODACIOUS_BERRY_BAR.asItem(), 4))
                            .craftingTime(300)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("CHC")
                            .pattern("BLB") // bulby..................
                            .pattern("CMC")
                            .define('H', Items.HONEY_BOTTLE)
                            .define('B', Items.SWEET_BERRIES)
                            .define('M', CLOTTED_CREAM.asItem())
                            .define('C', FRESH_CHOCOLATE.asItem())
                            .define('L', infusedBeverageIngredient(InfusedBeverageComponent.BERRY_LIQUOR))
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CHAUVE_SOURIS_AU_VIN.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("EEE")
                            .pattern("APA")
                            .pattern("FVF")
                            .define('V', JADE_WINE.asItem())
                            .define('F', LIZARD_MEAT.asItem())
                            .define('A', GLASS_PEACH.asItem())
                            .define('E', JADEITE_PETALS.asItem())
                            .define('P', FRESH_CHOCOLATE.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMPERIAL_COOKBOOK)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CRAWFISH_COCKTAIL.asItem(), 3))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("AAA")
                            .pattern("BEJ")
                            .define('A', CRAWFISH.asItem())
                            .define('J', MYCEYLON.asItem())
                            .define('E', INCANDESCENT_ESSENCE.asItem())
                            .define('B', GLASS_PEACH.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMBRIFER_COOKBOOK)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CREAM_PASTRY.asItem(), 5))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.2f)
                            .pattern("ESE")
                            .pattern("CCC")
                            .pattern("EXE")
                            .define('S', Items.HONEY_BOTTLE)
                            .define('E', AMARANTH_GRAINS.asItem())
                            .define('C', CLOTTED_CREAM.asItem())
                            .define('X', Tags.Items.EGGS)
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FISHCAKE.asItem(), 3))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("KAK")
                            .pattern("AEA")
                            .pattern("FFF")
                            .define('A', CRAWFISH.asItem())
                            .define('K', KOI.asItem())
                            .define('F', Tags.Items.FOODS_RAW_FISH)
                            .define('E', MYCEYLON.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMBRIFER_COOKBOOK)
            );

            // hare roasts

            hareRoast(prefixHelper, "hare_roast_beer", InfusedBeverageComponent.BEER);
            hareRoast(prefixHelper, "hare_roast_malt", InfusedBeverageComponent.MALT_BEER);
            hareRoast(prefixHelper, "hare_roast_porter", InfusedBeverageComponent.PORTER);

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(HONEY_PASTRY.asItem()))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern(" H ")
                            .pattern("SES")
                            .pattern("AAA")
                            .define('A', AMARANTH_GRAINS.asItem())
                            .define('H', Items.HONEY_BOTTLE)
                            .define('E', Tags.Items.EGGS)
                            .define('S', Items.SUGAR)
                            .requiredAdvancement(PastelAdvancements.COLLECT_AMARANTH_BUSHEL)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(LUCKY_ROLL.asItem(), 2))
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.5f)
                            .pattern("SCS")
                            .pattern("AMA")
                            .pattern("AAA")
                            .define('A', AMARANTH_GRAINS.asItem())
                            .define('C', PastelBlocks.FOUR_LEAF_CLOVER.asItem())
                            .define('M', Tags.Items.BUCKETS_MILK)
                            .define('S', Items.SUGAR)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.LUCKY_ROLL)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(MEATLOAF_SANDWICH.asItem(), 4))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.2f)
                            .requires(MEATLOAF.asItem())
                            .requires(Items.BREAD)
                            .requires(CLOTTED_CREAM.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_1)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(MELLOW_SHALLOT_SOUP.asItem(), 4))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern(" R ")
                            .pattern("ABC")
                            .pattern("JEJ")
                            .define('A', GERMINATED_JADE_VINE_BULB.asItem())
                            .define('B', PastelBlocks.JADEITE_LOTUS_BULB.asItem())
                            .define('C', PastelBlocks.NEPHRITE_BLOSSOM_BULB.asItem())
                            .define('J', FROSTBITE_ESSENCE.asItem())
                            .define('E', MOONSTRUCK_NECTAR.asItem())
                            .define('R', AQUA_REGIA.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMBRIFER_COOKBOOK)
            );

            // mycelyon pies

            myceylonPie(prefixHelper, MYCEYLON_APPLE_PIE.asItem(), Items.APPLE);
            myceylonPie(prefixHelper, MYCEYLON_PUMPKIN_PIE.asItem(), Items.PUMPKIN);

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(MYCEYLON_COOKIE.asItem(), 8))
                            .craftingTime(120)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("AYA")
                            .define('Y', MYCEYLON.asItem())
                            .define('A', AMARANTH_GRAINS.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.MYCEYLON_PASTRIES)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PEACHES_FLAMBE.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("KVK")
                            .pattern("AEA")
                            .pattern("FPF")
                            .define('V', NECTERED_VIOGNIER.asItem())
                            .define('F', MYCEYLON.asItem())
                            .define('K', INCANDESCENT_ESSENCE.asItem())
                            .define('A', PEACH_JAM.asItem())
                            .define('E', CLOTTED_CREAM.asItem())
                            .define('P', GLASS_PEACH.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMPERIAL_COOKBOOK)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(RABBIT_CREAM_PIE.asItem(), 2))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.2f)
                            .pattern("SXS")
                            .pattern("CMC")
                            .pattern("EEE")
                            .define('S', MYCEYLON.asItem())
                            .define('E', AMARANTH_GRAINS.asItem())
                            .define('C', CLOTTED_CREAM.asItem())
                            .define('X', JADEITE_PETALS.asItem())
                            .define('M', JADE_WINE.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(SCONE.asItem(), 2))
                            .craftingTime(50)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .requires(AMARANTH_GRAINS.asItem())
                            .requires(Tags.Items.EGGS)
                            .requires(Items.SWEET_BERRIES)
                            .requires(CLOTTED_CREAM.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_AMARANTH_BUSHEL)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(SLUSHSLIDE.asItem(), 2))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.2f)
                            .requires(FRESH_CHOCOLATE.asItem())
                            .requires(MYCEYLON.asItem())
                            .requires(CLOTTED_CREAM.asItem())
                            .requires(infusedBeverageIngredient(InfusedBeverageComponent.SAWBLADE_HOLLY_LIQUOR))
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(TRIPLE_MEAT_POT_PIE.asItem()))
                            .craftingTime(120)
                            .tier(PedestalTier.BASIC)
                            .experience(4.0f)
                            .pattern("ECE")
                            .pattern("MFL")
                            .pattern("AWA")
                            .define('A', AMARANTH_GRAINS.asItem())
                            .define('C', CLOTTED_CREAM.asItem())
                            .define('M', PastelItemTags.COMMON_MEATS)
                            .define('F', PastelItemTags.WATER_MEATS)
                            .define('L', PastelItemTags.LEAN_MEATS)
                            .define('E', Tags.Items.EGGS)
                            .define('W', PastelItemTags.DRINKABLE_SPIRITS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.TRIPLE_MEAT_POT_PIE)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(TRIPLE_MEAT_POT_STEW.asItem()))
                            .craftingTime(120)
                            .tier(PedestalTier.BASIC)
                            .experience(4.0f)
                            .pattern("PWP")
                            .pattern("MFL")
                            .pattern("CBC")
                            .define('P', PRICKLY_BAYLEAF.asItem())
                            .define('C', CLOTTED_CREAM.asItem())
                            .define('M', PastelItemTags.COMMON_MEATS)
                            .define('F', PastelItemTags.WATER_MEATS)
                            .define('L', PastelItemTags.LEAN_MEATS)
                            .define('W', PastelItemTags.DRINKABLE_SPIRITS)
                            .define('B', Items.BOWL)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.TRIPLE_MEAT_POT_STEW)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(WYRMSCALE_JELLY.asItem()))
                            .craftingTime(120)
                            .tier(PedestalTier.BASIC)
                            .experience(2.0f)
                            .pattern("YYY")
                            .pattern("DDD")
                            .pattern("VBV")
                            .define('Y', PRICKLY_BAYLEAF.asItem())
                            .define('D', DRAGONBONE_CHUNK.asItem())
                            .define('V', Tags.Items.FOODS_FRUIT)
                            .define('B', Items.BOWL)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.WYRMSCALE_JELLY)
            );

            prefixHelper.generateAutoNamedDynamicRecipe(new StarCandyRecipe());
        }

        private static void generateGemstoneLightRecipes(PrefixHelper pfx) {
            generateGemstoneLightsGroup(pfx, PastelBlocks.POLISHED_BASALT, PastelBlocks.BASALT_GEMSTONE_LIGHTS);
            generateGemstoneLightsGroup(pfx, PastelBlocks.POLISHED_CALCITE, PastelBlocks.CALCITE_GEMSTONE_LIGHTS);
        }

        private static void generateBasicGlasses(PrefixHelper pfx) {
            PastelGemstoneColorCollection.zipApply(PastelGemstoneColorCollection.VALUES, PastelGemstoneColorCollection.GEMSTONE_NAMES, (color, name) -> {
                pfx.generateAutoNamedRecipe(
                        new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.GEMSTONE_GLASSES.pick(color), 8))
                                .tier(PastelGemstoneColorCollection.MINIMUM_TIER.pick(color))
                                .craftingTime(20)
                                .group("gemstone_glass")
                                .requiredAdvancement(BASE_SHARD_UNLOCKS.pick(color))
                                .color(color, 4)
                                .experience(1.0f)
                                .pattern("GPG")
                                .pattern("PPP")
                                .pattern("GPG")
                                .define('P', GEMSTONE_SHARDS.pick(color).value())
                                .define('G', Items.GLASS)
                );
                generateBasicGlassPane(
                        pfx,
                        "gemstone_glass",
                        BASE_SHARD_UNLOCKS.pick(color),
                        20,
                        PastelGemstoneColorCollection.MINIMUM_TIER.pick(color),
                        PastelBlocks.GEMSTONE_GLASSES.pick(color),
                        PastelBlocks.GEMSTONE_GLASS_PANES.pick(color));
            });

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.RADIANT_GLASS.asItem(), 2))
                            .tier(PedestalTier.BASIC)
                            .group("radiant_glass")
                            .color(PastelGemstoneColor.YELLOW, 2)
                            .experience(0.5f)
                            .craftingTime(80)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.RADIANT_GLASS)
                            .pattern(" S ")
                            .pattern("SGS")
                            .pattern(" S ")
                            .define('G', Items.GLASS)
                            .define('S', SHIMMERSTONE_GEM.get())
            );
            generateBasicGlassPane(pfx, "radiant_glass", PastelAdvancements.Unlocks.Blocks.RADIANT_GLASS, 80, PedestalTier.BASIC, PastelBlocks.RADIANT_GLASS, PastelBlocks.RADIANT_GLASS_PANE);

            generateBasicGlassPane(pfx, null, PastelAdvancements.Lategame.COLLECT_HUMMINGSTONE, 20, PedestalTier.BASIC, PastelBlocks.HUMMINGSTONE_GLASS, PastelBlocks.HUMMINGSTONE_GLASS_PANE);
        }

        private static void generateJadeiteRecipes(PrefixHelper pfx) {
            generateJadeLike(
                    pfx,
                    "jadeite_decorations",
                    PastelAdvancements.Hidden.COLLECT_JADEITE,
                    JADEITE_PETALS,
                    PastelBlocks.JADEITE_PETAL_BLOCK,
                    PastelBlocks.JADEITE_PETAL_CARPET
            );
        }

        private static void generateJadeVinesRecipes(PrefixHelper pfx) {
            generateJadeLike(
                    pfx,
                    "jade_decorations",
                    PastelAdvancements.Midgame.COLLECT_JADE_PETALS,
                    JADE_PETALS,
                    PastelBlocks.JADE_PETAL_BLOCK,
                    PastelBlocks.JADE_PETAL_CARPET
            );
        }

        // this is awful. maybe consider making an extended block family for noxwood items
        private static void generateNoxwoodRecipes(PrefixHelper pfx) {
            generateNoxwoodFamily(
                    pfx,
                    Items.COPPER_INGOT,
                    PastelBlocks.CHESTNUT_NOXWOOD_PLANKS,
                    PastelBlocks.CHESTNUT_NOXWOOD_SLAB,
                    PastelBlocks.CHESTNUT_NOXCAP_STEM,
                    PastelBlocks.CHESTNUT_NOXCAP_GILLS,
                    PastelBlocks.CHESTNUT_NOXCAP_BLOCK,
                    PastelItemTags.CHESTNUT_NOXCAP_STEMS,
                    PastelBlocks.CHESTNUT_NOXWOOD_AMPHORA,
                    PastelBlocks.CHESTNUT_NOXWOOD_LAMP,
                    PastelBlocks.CHESTNUT_NOXWOOD_LANTERN,
                    PastelBlocks.CHESTNUT_NOXWOOD_LIGHT,
                    PastelBlocks.CHESTNUT_NOXWOOD_PILLAR
            );
            generateNoxwoodFamily(
                    pfx,
                    Items.IRON_INGOT,
                    PastelBlocks.EBONY_NOXWOOD_PLANKS,
                    PastelBlocks.EBONY_NOXWOOD_SLAB,
                    PastelBlocks.EBONY_NOXCAP_STEM,
                    PastelBlocks.EBONY_NOXCAP_GILLS,
                    PastelBlocks.EBONY_NOXCAP_BLOCK,
                    PastelItemTags.EBONY_NOXCAP_STEMS,
                    PastelBlocks.EBONY_NOXWOOD_AMPHORA,
                    PastelBlocks.EBONY_NOXWOOD_LAMP,
                    PastelBlocks.EBONY_NOXWOOD_LANTERN,
                    PastelBlocks.EBONY_NOXWOOD_LIGHT,
                    PastelBlocks.EBONY_NOXWOOD_PILLAR
            );
            generateNoxwoodFamily(
                    pfx,
                    Items.QUARTZ,
                    PastelBlocks.IVORY_NOXWOOD_PLANKS,
                    PastelBlocks.IVORY_NOXWOOD_SLAB,
                    PastelBlocks.IVORY_NOXCAP_STEM,
                    PastelBlocks.IVORY_NOXCAP_GILLS,
                    PastelBlocks.IVORY_NOXCAP_BLOCK,
                    PastelItemTags.IVORY_NOXCAP_STEMS,
                    PastelBlocks.IVORY_NOXWOOD_AMPHORA,
                    PastelBlocks.IVORY_NOXWOOD_LAMP,
                    PastelBlocks.IVORY_NOXWOOD_LANTERN,
                    PastelBlocks.IVORY_NOXWOOD_LIGHT,
                    PastelBlocks.IVORY_NOXWOOD_PILLAR
            );
            generateNoxwoodFamily(
                    pfx,
                    Items.PRISMARINE_CRYSTALS,
                    PastelBlocks.SLATE_NOXWOOD_PLANKS,
                    PastelBlocks.SLATE_NOXWOOD_SLAB,
                    PastelBlocks.SLATE_NOXCAP_STEM,
                    PastelBlocks.SLATE_NOXCAP_GILLS,
                    PastelBlocks.SLATE_NOXCAP_BLOCK,
                    PastelItemTags.SLATE_NOXCAP_STEMS,
                    PastelBlocks.SLATE_NOXWOOD_AMPHORA,
                    PastelBlocks.SLATE_NOXWOOD_LAMP,
                    PastelBlocks.SLATE_NOXWOOD_LANTERN,
                    PastelBlocks.SLATE_NOXWOOD_LIGHT,
                    PastelBlocks.SLATE_NOXWOOD_PILLAR
            );
        }

        private static void generatePigmentBlocks(PrefixHelper pfx) {
            PastelInkColorCollection.VALUES.forEach(color -> {
                var pigmentBlock = PastelBlocks.PIGMENT_BLOCKS.pick(color);
                var pigment = PIGMENTS.pick(color);
                var unlock = PastelAdvancements.Hidden.CollectPigment.VALUES.pick(color);

                generateCompactingPairWithGroup(pfx, "pigment_compacting", pigmentBlock.getId().getPath() + "_to_" + pigment.getId().getPath(), unlock, pigment, pigmentBlock);
            });
        }

        private static void generatePylons(PrefixHelper pfx) {
            PastelGemstoneColorCollection.VALUES.forEach(color -> {
                var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color);
                var item = GEMSTONE_SHARDS.pick(color);
                var pylon = PastelBlocks.PYLONS.pick(color);
                var unlock = PastelAdvancements.Unlocks.Pylons.VALUES.pick(color);

                pfx.generateRecipe(
                        PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color),
                        new ShapedPedestalRecipeBuilder(new ItemStack(pylon.asItem()))
                                .group("pylons")
                                .craftingTime(40)
                                .tier(tier)
                                .color(color, 2)
                                .experience(1.0f)
                                .pattern(" S ")
                                .pattern("SSS")
                                .pattern("XYX")
                                .define('S', item.value())
                                .define('X', PastelBlocks.POLISHED_BASALT.asItem())
                                .define('Y', PastelBlocks.POLISHED_CALCITE.asItem())
                                .requiredAdvancement(unlock)
                );
            });
        }

        private static void generateResplendentRecipes(PrefixHelper pfx) {
            var block = PastelBlocks.RESPLENDENT_BLOCK;
            var unlock = PastelAdvancements.PLUCK_RESPLENDENT_FEATHER;
            var group = "resplendent_decorations";

            pfx.generateRecipe(
                    "bed",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.RESPLENDENT_BED.asItem()))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("FFF")
                            .pattern("###")
                            .define('F', block.asItem())
                            .define('#', ItemTags.PLANKS)
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "block",
                    new ShapedPedestalRecipeBuilder(new ItemStack(block.asItem()))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("GG")
                            .pattern("GG")
                            .define('G', RESPLENDENT_FEATHER.asItem())
                            .requiredAdvancement(unlock)
                            .disableYieldUpgrades(true)
            );

            pfx.generateRecipe(
                    "feathers_from_block",
                    new ShapelessPedestalRecipeBuilder(new ItemStack(RESPLENDENT_FEATHER.asItem(), 4))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .requires(block.asItem())
                            .requiredAdvancement(unlock)
                            .disableYieldUpgrades(true)
            );

            pfx.generateRecipe(
                    "carpet_from_blocks",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.RESPLENDENT_CARPET.asItem(), 4))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("BB")
                            .define('B', block.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "carpet_from_feathers",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.RESPLENDENT_CARPET.asItem(), 1))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("FF")
                            .define('F', RESPLENDENT_FEATHER.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "cushion",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.RESPLENDENT_CUSHION.asItem()))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("FFF")
                            .pattern("FFF")
                            .define('F', RESPLENDENT_FEATHER.asItem())
                            .requiredAdvancement(unlock)
            );


        }

        private static void generateRunes(PrefixHelper pfx) {
            generateRunesGroup(pfx, PastelBlocks.POLISHED_BASALT, PastelBlocks.GEMSTONE_CHISELED_BASALTS);
            generateRunesGroup(pfx, PastelBlocks.POLISHED_CALCITE, PastelBlocks.GEMSTONE_CHISELED_CALCITES);
        }

        private static void generateSaplings(PrefixHelper pfx) {
            PastelInkColorCollection.VALUES.forEach(color -> generateSapling(pfx, color));
        }

        private static void generateShimmerstoneLightRecipes(PrefixHelper pfx) {
            var defaultUnlock = PastelAdvancements.Unlocks.Blocks.SHIMMERSTONE_LIGHTS;

            generateShimmerstoneLight(pfx, PastelBlocks.ANDESITE_SHIMMERSTONE_LIGHT, Items.POLISHED_ANDESITE, defaultUnlock);
            generateShimmerstoneLight(pfx, PastelBlocks.BASALT_SHIMMERSTONE_LIGHT, PastelBlocks.POLISHED_BASALT.asItem(), defaultUnlock);
            generateShimmerstoneLight(pfx, PastelBlocks.CALCITE_SHIMMERSTONE_LIGHT, PastelBlocks.POLISHED_CALCITE.asItem(), defaultUnlock);
            generateShimmerstoneLight(pfx, PastelBlocks.DEEPSLATE_SHIMMERSTONE_LIGHT, Items.POLISHED_DEEPSLATE, defaultUnlock);
            generateShimmerstoneLight(pfx, PastelBlocks.DIORITE_SHIMMERSTONE_LIGHT, Items.POLISHED_DIORITE, defaultUnlock);
            generateShimmerstoneLight(pfx, PastelBlocks.GRANITE_SHIMMERSTONE_LIGHT, Items.POLISHED_GRANITE, defaultUnlock);
            generateShimmerstoneLight(pfx, PastelBlocks.STONE_SHIMMERSTONE_LIGHT, Items.SMOOTH_STONE, defaultUnlock);

            generateShimmerstoneLight(
                    pfx,
                    PastelBlocks.BLACKSLAG_SHIMMERSTONE_LIGHT,
                    PastelBlocks.POLISHED_BLACKSLAG.asItem(),
                    PastelAdvancements.Unlocks.Blocks.BLACKSLAG_SHIMMERSTONE_LIGHT
            );

        }

        private static void generateToolRecipes(PrefixHelper pfx) {
            var lookup = pfx.getLookup();
            // Actually this is a _benefit_ of datagen, no need to copy everything
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, LAGOON_ROD.get()))
                            .craftingTime(180)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.CYAN, 1)
                            .experience(0.5f)
                            .pattern("  B")
                            .pattern(" BS")
                            .pattern("B M")
                            .define('B', Items.SMOOTH_BASALT)
                            .define('S', Items.STRING)
                            .define('M', MERMAIDS_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.LAGOON_ROD)
            );
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, LUCKY_PICKAXE.get()))
                            .craftingTime(600)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.YELLOW, 8)
                            .experience(2.0f)
                            .pattern("CGC")
                            .pattern(" S ")
                            .pattern(" S ")
                            .define('S', Items.SMOOTH_BASALT)
                            .define('C', CITRINE_SHARD.asItem())
                            .define('G', SHIMMERSTONE_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.LUCKY_PICKAXE)
            );
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, MOLTEN_ROD.get()))
                            .craftingTime(800)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.MAGENTA, 2)
                            .color(PastelGemstoneColor.YELLOW, 4)
                            .experience(4.0f)
                            .pattern(" PB")
                            .pattern("PBS") // pbs kids! with shows such as sid the science kid!
                            .pattern("B S")
                            .define('P', ORANGE_PIGMENT.asItem())
                            .define('B', Items.BLAZE_ROD)
                            .define('S', Items.STRING)
                            .requiredAdvancement(MoltenRodItem.UNLOCK_IDENTIFIER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, RAZOR_FALCHION.get()))
                            .craftingTime(600)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.MAGENTA, 8)
                            .experience(2.0f)
                            // The pattern was padded in the json but it didnt NEED to be so im just... not
                            .pattern("A")
                            .pattern("A")
                            .pattern("S")
                            .define('S', Items.CALCITE)
                            .define('A', Items.AMETHYST_SHARD)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.RAZOR_FALCHION)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, TENDER_PICKAXE.get()))
                            .craftingTime(600)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.CYAN, 8)
                            .experience(2.0f)
                            .pattern("TMT")
                            .pattern(" S ")
                            .pattern(" S ")
                            .define('S', Items.SMOOTH_BASALT)
                            .define('T', TOPAZ_SHARD.asItem())
                            .define('M', MERMAIDS_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.TENDER_PICKAXE)
            );
        }

        private static void generateVanillaRecipes(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.BUNDLE))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.CYAN, 4)
                            .experience(2.0f)
                            .pattern("S")
                            .pattern("L")
                            .define('S', Items.STRING)
                            .define('L', Items.LEATHER)
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.TOPAZ)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.COBWEB, 2))
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.25f)
                            .pattern(" S ")
                            .pattern("QAQ")
                            .pattern(" S ")
                            .define('S', Items.STRING)
                            .define('A', Items.SPIDER_EYE)
                            .define('Q', QUITOXIC_POWDER.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_QUITOXIC_REEDS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(Items.PACKED_MUD))
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .requires(Items.MUD)
                            .requires(PastelBlocks.AMARANTH_BUSHEL.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_AMARANTH_BUSHEL)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.SPECTRAL_ARROW, 2))
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.25f)
                            .pattern(" S ")
                            .pattern("SAS")
                            .pattern(" S ")
                            .define('A', Items.ARROW)
                            .define('S', SHIMMERSTONE_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_SHIMMERSTONE)
            );

            var tntStack = new ItemStack(Items.TNT, 2);
            tntStack.applyComponents(
                    DataComponentPatch.builder()
                            .set(DataComponents.BLOCK_STATE, new BlockItemStateProperties(Map.of("unstable", "true")))
                            .set(DataComponents.LORE, new ItemLore(List.of(Component.literal("unstable"))))
                            .build());

            pfx.generateRecipe(
                    "unstable_tnt",
                    new ShapedPedestalRecipeBuilder(tntStack)
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.YELLOW, 2)
                            .experience(2.0f)
                            .pattern("QSQ")
                            .pattern("SQS")
                            .pattern("QSQ")
                            .define('Q', QUITOXIC_POWDER.asItem())
                            // could be replaced with the sand tag?
                            .define('S', Items.SAND)
                            .requiredAdvancement(PastelAdvancements.COLLECT_QUITOXIC_REEDS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(Items.WRITABLE_BOOK))
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .requires(Items.BOOK)
                            .requires(PastelItemTags.PIGMENTS)
                            .requires(Items.FEATHER)
                            .requiredAdvancement(PastelAdvancements.COLLECT_PIGMENT)
            );
        }

        private static void generateWeepingGalaRecipes(PrefixHelper pfx) {
            final var unlock = PastelAdvancements.Hidden.COLLECT_WEEPING_GALA;
            final var group = "weeping_gala_deco";

            generateAmphora(
                    pfx,
                    Items.IRON_INGOT,
                    "amphora",
                    group,
                    unlock,
                    PastelBlocks.WEEPING_GALA_PLANKS,
                    PastelBlocks.WEEPING_GALA_SLAB,
                    PastelBlocks.WEEPING_GALA_AMPHORA
            );

            pfx.generateRecipe(
                    "barrel",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.WEEPING_GALA_BARREL.asItem()))
                            .group(group)
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("PSP")
                            .pattern("P P")
                            .pattern("PSP")
                            .define('S', PastelBlocks.WEEPING_GALA_SLAB.asItem())
                            .define('P', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "lamp",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.WEEPING_GALA_LAMP.asItem(), 2))
                            .group(group)
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("SSS")
                            .pattern("GXG")
                            .pattern("SSS")
                            .define('S', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
                            .define('G', Items.REDSTONE)
                            .define('X', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "lantern",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.WEEPING_GALA_LANTERN))
                            .group(group)
                            .craftingTime(240)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("S")
                            .pattern("P")
                            .define('S', SHIMMERSTONE_GEM.asItem())
                            .define('P', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "light",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.WEEPING_GALA_LIGHT, 4))
                            .group(group)
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("PPP")
                            .pattern("SSS")
                            .pattern("PPP")
                            .define('P', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
                            .define('S', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    "pillar",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.WEEPING_GALA_PILLAR, 2))
                            .group(group)
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("#")
                            .pattern("#")
                            .define('#', PastelItemTags.WEEPING_GALA_LOGS)
                            .requiredAdvancement(unlock)
            );
        }

        private static void generateRootRecipes(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BAG_OF_HOLDING.asItem()))
                            .craftingTime(240)
                            .basic()
                            .magenta(4)
                            .cyan(4)
                            .experience(2.0f)
                            .pattern("OOO")
                            .pattern("TET")
                            .pattern("OOO")
                            .define('O', Items.OBSIDIAN)
                            .define('E', Items.ENDER_EYE)
                            .define('T', PastelBlocks.RADIATING_ENDER)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.BAG_OF_HOLDING)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BLOCK_DETECTOR))
                            .craftingTime(200)
                            .basic()
                            .cyan(2)
                            .yellow(2)
                            .experience(1.0f)
                            .pattern("CCC")
                            .pattern("RSB")
                            .pattern("BBB")
                            .define('R', Items.REDSTONE)
                            .define('S', STORM_STONE)
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('C', Items.COPPER_INGOT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Redstone.BLOCK_DETECTOR)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BLOCK_PLACER))
                            .craftingTime(200)
                            .basic()
                            .cyan(4)
                            .experience(1.0f)
                            .pattern("CCC")
                            .pattern("RTB")
                            .pattern("BBB")
                            .define('R', Items.REDSTONE)
                            .define('T', TOPAZ_SHARD)
                            .define('B', PastelBlocks.POLISHED_CALCITE)
                            .define('C', Items.COPPER_INGOT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Redstone.BLOCK_PLACER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BOTTLE_OF_FADING.asItem()))
                            .craftingTime(400)
                            .basic()
                            .experience(2.0f)
                            .skipRemainders(true)
                            .pattern("FSF")
                            .pattern("CBC")
                            .pattern("FSF")
                            .define('S', SHIMMERSTONE_GEM)
                            .define('F', Items.FERMENTED_SPIDER_EYE)
                            .define('C', Items.BLAZE_POWDER)
                            .define('B', Items.HONEY_BOTTLE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.BOTTLE_OF_FADING)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.COMPACTING_CHEST))
                            .craftingTime(800)
                            .basic()
                            .cyan(4)
                            .experience(2.0f)
                            .pattern("CIC")
                            .pattern("T T")
                            .pattern("TBT")
                            .define('C', Items.COPPER_INGOT)
                            .define('B', Items.COPPER_BLOCK)
                            .define('T', PastelBlocks.POLISHED_BASALT)
                            .define('I', Items.IRON_INGOT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.COMPACTING_CHEST)
            );


            List.of(
                    new Triplet<>("", PastelBlocks.POLISHED_BASALT, PastelBlocks.POLISHED_CALCITE),
                    new Triplet<>("_mirrored", PastelBlocks.POLISHED_CALCITE, PastelBlocks.POLISHED_BASALT)
            ).forEach(triplet -> {
                var name = "crafting_tablet" + triplet.getA();
                var first = triplet.getB();
                var second = triplet.getC();

                pfx.generateRecipe(
                        name,
                        new ShapedPedestalRecipeBuilder(new ItemStack(CRAFTING_TABLET.asItem()))
                                .group("crafting_tablet")
                                .craftingTime(40)
                                .basic()
                                .magenta(1)
                                .experience(0.1f)
                                .pattern("AA")
                                .pattern("BB")
                                .define('A', first)
                                .define('B', second)
                                .requiredAdvancement(PastelAdvancements.Unlocks.Items.CRAFTING_TABLET)
                );
            });

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.ENDER_GLASS, 4))
                            .craftingTime(80)
                            .basic()
                            .magenta(4)
                            .experience(0.5f)
                            .pattern(" G ")
                            .pattern("GEG")
                            .pattern(" G ")
                            .define('E', PastelBlocks.RADIATING_ENDER)
                            .define('G', Items.GLASS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.ENDER_GLASS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(GLISTERING_MELON_SEEDS.asItem()))
                            .craftingTime(200)
                            .basic()
                            .yellow(8)
                            .experience(1.0f)
                            .pattern("VGV")
                            .pattern("GMG")
                            .pattern("VGV")
                            .define('G', Items.GOLD_INGOT)
                            .define('M', Items.MELON_SEEDS)
                            .define('V', VEGETAL)
                            .requiredAdvancement(PastelAdvancements.COLLECT_VEGETAL)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PHANTOM_FRAME.asItem()))
                            .group("phantom_frames")
                            .craftingTime(80)
                            .basic()
                            .yellow(2)
                            .experience(0.5f)
                            .pattern("SSS")
                            .pattern("CLC")
                            .pattern("SFS")
                            .define('L', Items.LEATHER)
                            .define('S', Items.STICK)
                            .define('C', SHIMMERSTONE_GEM)
                            .define('F', Items.PHANTOM_MEMBRANE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.PHANTOM_FRAMES)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(GLOW_PHANTOM_FRAME.asItem()))
                            .group("phantom_frames")
                            .craftingTime(80)
                            .basic()
                            .yellow(4)
                            .experience(0.5f)
                            .pattern("SGS")
                            .pattern("CLC")
                            .pattern("SFS")
                            .define('L', Items.LEATHER)
                            .define('S', Items.STICK)
                            .define('C', SHIMMERSTONE_GEM)
                            .define('F', Items.PHANTOM_MEMBRANE)
                            .define('G', Items.GLOW_INK_SAC)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.PHANTOM_FRAMES)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.HEARTBOUND_CHEST))
                            .craftingTime(120)
                            .basic()
                            .yellow(4)
                            .experience(1.0f)
                            .pattern("PPP")
                            .pattern("WGW")
                            .pattern("WWW")
                            .define('P', Items.PAPER)
                            .define('W', ItemTags.PLANKS)
                            .define('G', Items.GOLD_INGOT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.HEARTBOUND_CHEST)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(HUMUS_BUCKET.asItem()))
                            .craftingTime(240)
                            .basic()
                            .cyan(2)
                            .experience(1.0f)
                            .pattern("DMD")
                            .pattern("CBC")
                            .pattern("DCD")
                            .define('D', Items.DIRT)
                            .define('C', Items.MUD)
                            .define('B', Items.BUCKET)
                            .define('M', MERMAIDS_GEM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.HUMUS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.LAVA_SPONGE))
                            .craftingTime(800)
                            .basic()
                            .cyan(2)
                            .yellow(4)
                            .experience(2.0f)
                            .pattern("CMC")
                            .pattern("MBM")
                            .pattern("CMC")
                            .define('B', Items.LAVA_BUCKET)
                            .define('M', Items.MAGMA_BLOCK)
                            .define('C', Items.MAGMA_CREAM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.LAVA_SPONGE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.PEDESTAL_ALL_BASIC))
                            .craftingTime(200)
                            .basic()
                            .experience(4.0f)
                            .pattern("CMY")
                            .pattern("RRR")
                            .define('C', TOPAZ_SHARD)
                            .define('M', Items.AMETHYST_SHARD)
                            .define('Y', CITRINE_SHARD)
                            .define('R', PastelBlocks.POLISHED_CALCITE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.CMY_PEDESTAL)
                            .disableYieldUpgrades(true)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.PRESENT))
                            .craftingTime(200)
                            .basic()
                            .cyan(1)
                            .yellow(1)
                            .experience(1.0f)
                            .pattern("SSS")
                            .pattern("PGP")
                            .pattern("PPP")
                            .define('P', Items.PAPER)
                            .define('S', SHIMMERSTONE_GEM)
                            .define('G', Items.GUNPOWDER)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.PRESENT)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PRISCILLENT_SPECTACLES.asItem()))
                            .craftingTime(200)
                            .basic()
                            .yellow(2)
                            .experience(2.0f)
                            .pattern("G G")
                            .pattern("SRS")
                            .define('S', Items.GLOW_INK_SAC)
                            .define('R', Items.LEATHER)
                            .define('G', Items.GOLD_NUGGET)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.PRISCILLENT_SPECTACLES)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.REDSTONE_CALCULATOR))
                            .craftingTime(200)
                            .basic()
                            .yellow(4)
                            .experience(1.0f)
                            .pattern("TCR")
                            .pattern("SSS")
                            .define('C', CITRINE_SHARD)
                            .define('R', Items.REDSTONE)
                            .define('T', Items.REDSTONE_TORCH)
                            .define('S', Items.STONE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Redstone.REDSTONE_CALCULATOR)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.REDSTONE_TIMER))
                            .craftingTime(200)
                            .basic()
                            .magenta(4)
                            .experience(1.0f)
                            .pattern("TAR")
                            .pattern("III")
                            .define('A', Items.AMETHYST_SHARD)
                            .define('R', Items.REDSTONE)
                            .define('T', Items.REDSTONE_TORCH)
                            .define('I', Items.COPPER_INGOT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Redstone.REDSTONE_TIMER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.REDSTONE_SAND))
                            .craftingTime(20)
                            .basic()
                            .yellow(1)
                            .yellow(1)
                            .experience(0.05f)
                            .pattern(" R ")
                            .pattern("RSR")
                            .pattern(" R ")
                            .define('R', Items.REDSTONE)
                            // replace with tag?
                            .define('S', Items.SAND)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Redstone.REDSTONE_SAND)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.TEA_TABLE))
                            .craftingTime(240)
                            .basic()
                            .experience(1.0f)
                            .pattern("   ")
                            .pattern("SSS")
                            .pattern("P P")
                            .define('P', ItemTags.PLANKS)
                            .define('S', ItemTags.WOODEN_SLABS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.FUSION_SHRINE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.TITRATION_BARREL))
                            .craftingTime(160)
                            .basic()
                            .magenta(4)
                            .experience(0.5f)
                            .pattern("CCC")
                            .pattern("IPI")
                            .pattern("CCC")
                            .define('C', PastelItemTags.COLORED_PLANKS)
                            .define('P', PastelItemTags.PIGMENTS)
                            .define('I', Items.IRON_INGOT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.TITRATION_BARREL)
            );


        }

        // basic generation utilities


        private static void generateShimmerstoneLight(PrefixHelper pfx, DeferredBlock<?> result, Item stone, ResourceLocation unlock) {
            var name = result.getId().getPath().replace("_shimmerstone_light", "");

            pfx.generateRecipe(
                    name,
                    new ShapedPedestalRecipeBuilder(new ItemStack(result.asItem(), 4))
                            .group("shimmerstone_lights")
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .color(PastelGemstoneColor.YELLOW, 1)
                            .experience(0.5f)
                            .pattern("TST")
                            .pattern("SSS")
                            .pattern("TST")
                            .define('S', SHIMMERSTONE_GEM.asItem())
                            .define('T', stone)
                            .requiredAdvancement(unlock)
            );
        }

        private static IngredientStack infusedBeverageIngredient(InfusedBeverageComponent component) {
            return new IngredientStack(
                    Ingredient.of(INFUSED_BEVERAGE.asItem()),
                    DataComponentPredicate.builder().expect(PastelDataComponentTypes.INFUSED_BEVERAGE, component).build(),
                    DataComponentPatch.builder().set(PastelDataComponentTypes.INFUSED_BEVERAGE, component).build(),
                    1
            );
        }

        private static ShapedPedestalRecipeBuilder tartBase(Item result, Item topping) {
            String firstPattern = topping == Items.AIR ? "J J" : "JTJ";

            var base = new ShapedPedestalRecipeBuilder(new ItemStack(result))
                    .craftingTime(160)
                    .tier(PedestalTier.BASIC)
                    .experience(1.0f)
                    .pattern(firstPattern)
                    .pattern("EME")
                    .pattern("AAA")
                    .define('A', AMARANTH_GRAINS.asItem())
                    .define('J', JARAMEL.asItem())
                    .define('E', Tags.Items.EGGS)
                    .define('M', Tags.Items.BUCKETS_MILK)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.TARTS);

            return topping == Items.AIR ? base : base.define('T', topping);
        }

        private static ShapedPedestalRecipeBuilder trifleBase(Item result, Item topping) {
            String firstPattern = topping == Items.AIR ? "J J" : "JTJ";

            var base = new ShapedPedestalRecipeBuilder(new ItemStack(result))
                    .craftingTime(160)
                    .tier(PedestalTier.BASIC)
                    .experience(1.0f)
                    .pattern(firstPattern)
                    .pattern("BEB")
                    .pattern("AAA")
                    .define('A', AMARANTH_GRAINS.asItem())
                    .define('J', JARAMEL.asItem())
                    .define('B', Items.BONE_MEAL)
                    .define('E', Tags.Items.EGGS)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.TRIFLES);

            return topping == Items.AIR ? base : base.define('T', topping);
        }

        private static void myceylonPie(PrefixHelper pfx, Item result, Item thingie) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(result))
                            .craftingTime(120)
                            .tier(PedestalTier.BASIC)
                            .experience(2.0f)
                            .pattern("YPY")
                            .pattern("SMS")
                            .pattern("AAA")
                            .define('Y', MYCEYLON.asItem())
                            .define('M', Tags.Items.BUCKETS_MILK)
                            .define('S', Items.SUGAR)
                            .define('P', thingie)
                            .define('A', AMARANTH_GRAINS.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.MYCEYLON_PASTRIES)
            );
        }

        private static void hareRoast(PrefixHelper ctx, String name, InfusedBeverageComponent beverage) {
            ctx.generateRecipe(
                    name,
                    new ShapedPedestalRecipeBuilder(new ItemStack(HARE_ROAST.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("JJJ")
                            .pattern("EME")
                            .pattern("AHA")
                            .define('E', Tags.Items.MUSHROOMS)
                            .define('J', CLOTTED_CREAM.asItem())
                            .define('A', Items.POTATO)
                            .define('M', infusedBeverageIngredient(InfusedBeverageComponent.BEER))
                            .define('H', Items.COOKED_RABBIT)
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_1)
            );
        }

        // subpath no longer required as pfx will provide it
        private static void generateCompactingPairWithGroup(PrefixHelper pfx, @Nullable String group, String unpackName, ResourceLocation unlock, DeferredItem<?> unpacked, DeferredBlock<?> packed) {
            pfx.generateRecipe(
                    packed.getId().getPath(),
                    new ShapedPedestalRecipeBuilder(new ItemStack(packed.asItem()))
                            .group(group)
                            .craftingTime(20)
                            .tier(PedestalTier.BASIC)
                            .disableYieldUpgrades(true)
                            .experience(0.0f)
                            .requiredAdvancement(unlock)
                            .pattern("AAA")
                            .pattern("AAA")
                            .pattern("AAA")
                            .define('A', unpacked.get())
            );

            pfx.generateRecipe(
                    unpackName,
                    new ShapelessPedestalRecipeBuilder(new ItemStack(unpacked.get(), 9))
                            .group(group)
                            .craftingTime(20)
                            .tier(PedestalTier.BASIC)
                            .disableYieldUpgrades(true)
                            .experience(0.0f)
                            .requiredAdvancement(unlock)
                            .requires(packed.asItem())
            );
        }

        private static void generateCompactingPair(PrefixHelper ctx, String unpackedName, ResourceLocation unlock, DeferredItem<?> unpacked, DeferredBlock<?> packed) {
            generateCompactingPairWithGroup(ctx, "compacting", unpackedName, unlock, unpacked, packed);
        }
        private static void generateCompactingPair(PrefixHelper ctx, ResourceLocation unlock, DeferredItem<?> unpacked, DeferredBlock<?> packed) {
            generateCompactingPairWithGroup(ctx, "compacting",  unpacked.getId().getPath() + "_from_" + packed.getId().getPath(), unlock, unpacked, packed);
        }

        private static void generateDetectorRecipe(PrefixHelper pfx, PastelGemstoneColor color, ResourceLocation unlock, DeferredBlock<?> output) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(output.asItem()))
                            .tier(PastelGemstoneColorCollection.MINIMUM_TIER.pick(color))
                            .craftingTime(80)
                            .color(color, 4)
                            .experience(2.0f)
                            .pattern("BBB")
                            .pattern("CQC")
                            .pattern("SSS")
                            .define('B', Items.GLASS)
                            .define('C', GEMSTONE_SHARDS.pick(color).value())
                            .define('Q', Items.QUARTZ)
                            .define('S', ItemTags.WOODEN_SLABS)
                            .requiredAdvancement(unlock)
            );
        }

        private static void generateGemstoneLightsGroup(PrefixHelper pfx, DeferredBlock<Block> baseBlock, PastelGemstoneColorCollection<DeferredBlock<Block>> blocks) {
            PastelGemstoneColorCollection.zipApply(blocks, PastelGemstoneColorCollection.VALUES, (block, color) -> {
                pfx.generateAutoNamedRecipe(
                        new ShapedPedestalRecipeBuilder(new ItemStack(block.asItem(), 4))
                                .group(PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color) + "_lights")
                                .requiredAdvancement(PastelAdvancements.Unlocks.GemstoneLights.VALUES.pick(color))
                                .craftingTime(40)
                                .tier(PastelGemstoneColorCollection.MINIMUM_TIER.pick(color))
                                .color(color, 4)
                                .experience(0.3f)
                                .pattern("TTT")
                                .pattern("BSB")
                                .pattern("TTT")
                                .define('T', baseBlock.asItem())
                                .define('B', PastelBlocks.GEMSTONE_BLOCKS.pick(color).asItem())
                                .define('S', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
                );
            });
        }

        private static void generateRunesGroup(PrefixHelper pfx, DeferredBlock<Block> baseBlock, PastelGemstoneColorCollection<DeferredBlock<Block>> blocks) {
            PastelGemstoneColorCollection.VALUES.forEach(color -> {
                var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color);
                var result = blocks.pick(color);
                var unlock = color == PastelGemstoneColor.WHITE ? PastelAdvancements.Lategame.COLLECT_MOONSTONE : PastelAdvancements.CREATE_ONYX_SHARD;
                var cluster = PastelBlocks.GEMSTONE_CLUSTERS.pick(color);
                var shard = GEMSTONE_SHARDS.pick(color);

                pfx.generateRecipe(
                        result.getId().getPath() + "_from_cluster",
                        new ShapedPedestalRecipeBuilder(new ItemStack(result.asItem(), 8))
                                .group("gemstone_chiseled_blocks")
                                .craftingTime(80)
                                .tier(tier)
                                .color(color, 4)
                                .experience(1.0f)
                                .requiredAdvancement(unlock)
                                .pattern("WWW")
                                .pattern("WXW")
                                .pattern("WWW")
                                .define('W', baseBlock.asItem())
                                .define('X', cluster.asItem())
                );

                pfx.generateRecipe(
                        result.getId().getPath() + "_from_shards",
                        new ShapedPedestalRecipeBuilder(new ItemStack(result.asItem(), 1))
                                .group("gemstone_chiseled_blocks")
                                .craftingTime(80)
                                .tier(tier)
                                .color(color, 1)
                                .experience(1.0f)
                                .requiredAdvancement(unlock)
                                .pattern("WXW")
                                .pattern("XXX")
                                .pattern("WXW")
                                .define('W', baseBlock.asItem())
                                .define('X', shard.value())
                );
            });
        }
        private static void generateColoredLamp(PrefixHelper ctx, InkColor color) {
            DeferredBlock<?> result = PastelBlocks.COLORED_LAMPS.pick(color);
            ResourceLocation unlock = PastelAdvancements.Unlocks.ColoredLamps.VALUES.pick(color);
            Map<GemstoneColor, Integer> colorMix = POWDER_MIXES_6.pick(color);

            PedestalTier tier = PedestalTier.BASIC;

            if (colorMix.containsKey(PastelGemstoneColor.WHITE)) {
                tier = PedestalTier.COMPLEX;
            } else if (colorMix.containsKey(PastelGemstoneColor.BLACK)) {
                tier = PedestalTier.ADVANCED;
            }


            // note, for some reason all these are saved under the tier1 directory even though they have different tiers
            // depending on color progression
            ctx.generateRecipe(
                    color.getID().getPath(),
                    new ShapedPedestalRecipeBuilder(new ItemStack(result.asItem(), 2))
                            .group("colored_lamps")
                            .experience(0.5f)
                            .craftingTime(40)
                            .pattern("SPS")
                            .pattern("PRP")
                            .pattern("SPS")
                            .define('R', Items.REDSTONE)
                            .define('S', SHIMMERSTONE_GEM.get())
                            .define('P', PigmentItem.byColor(color))
                            .replaceColorsWith(colorMix)
                            .tier(tier)
                            .requiredAdvancement(unlock)
            );

        }

        private static void generateGemstoneArrowRecipe(PrefixHelper prefixHelper, PastelGemstoneColor color) {
            prefixHelper.generateRecipe(
                    PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color),
                    new ShapedPedestalRecipeBuilder(new ItemStack(GEMSTONE_GLASS_ARROWS.pick(color).get(), 2))
                            .group("glass_arrows")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .color(color, 1)
                            .experience(1.0f)
                            .pattern("AA")
                            .pattern("GB")
                            .define('G', GEMSTONE_SHARDS.pick(color).value())
                            .define('A', MALACHITE_GLASS_ARROW.get())
                            .define('B', BISMUTH_FLAKE.get())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.GLASS_ARROWS)
            );
        }

        private static void generateSapling(PrefixHelper pfx, InkColor color) {
            var tier = MINIMUM_COLOR_TIER.pick(color);
            var mix = POWDER_MIXES_6.pick(color);
            var dyeItem = PastelInkColorCollection.DYE_ITEMS.pick(color);
            var sapling = PastelBlocks.COLORED_SAPLINGS.pick(color);
            var unlock = PastelAdvancements.Unlocks.ColoredSaplings.VALUES.pick(color);

            pfx.generateRecipe(
                    PastelInkColorCollection.NAMES.pick(color),
                    new ShapedPedestalRecipeBuilder(new ItemStack(sapling.asItem()))
                            .group("colored_saplings")
                            .craftingTime(160)
                            .tier(tier)
                            .replaceColorsWith(mix)
                            .experience(1.0f)
                            .requiredAdvancement(unlock)
                            .pattern("DDD")
                            .pattern("VSV")
                            .pattern("DDD")
                            .define('D', dyeItem)
                            .define('V', VEGETAL.get())
                            .define('S', ItemTags.SAPLINGS)
            );
        }

        private static void generateJadeLike(PrefixHelper pfx, String group, ResourceLocation unlock, DeferredItem<?> petals, DeferredBlock<?> block, DeferredBlock<?> carpet) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(block.asItem()))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("XX")
                            .pattern("XX")
                            .define('X', petals.asItem())
                            .requiredAdvancement(unlock)
                            .disableYieldUpgrades(true)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(carpet.asItem(), 3))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("XX")
                            .define('X', block.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    petals.getId().getPath() + "_from_petal_block",
                    new ShapelessPedestalRecipeBuilder(new ItemStack(JADEITE_PETALS.asItem(), 4))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .requires(block.asItem())
                            .requiredAdvancement(unlock)
                            .disableYieldUpgrades(true)
            );
        }

        private static void generateAmphora(
                PrefixHelper pfx,
                Item ingot,
                String name,
                String group,
                ResourceLocation unlock,
                DeferredBlock<?> planks,
                DeferredBlock<?> slab,
                DeferredBlock<?> amphora
        ) {
            pfx.generateRecipe(
                    name,
                    new ShapedPedestalRecipeBuilder(new ItemStack(amphora.asItem()))
                            .group(group)
                            .craftingTime(240)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("SAS")
                            .pattern("I I")
                            .pattern("PSP")
                            .define('S', slab.asItem())
                            .define('P', planks.asItem())
                            .define('I', ingot)
                            .define('A', Items.PAPER)
                            .requiredAdvancement(unlock)
            );
        }

        private static void generateNoxwoodFamily(
                PrefixHelper pfx,
                Item ingot,
                DeferredBlock<?> planks,
                DeferredBlock<?> slab,
                DeferredBlock<?> stem,
                DeferredBlock<?> gills,
                DeferredBlock<?> block,
                TagKey<Item> stems,
                DeferredBlock<?> amphora,
                DeferredBlock<?> lamp,
                DeferredBlock<?> lantern,
                DeferredBlock<?> light,
                DeferredBlock<?> pillar

                ) {

            String familyName = amphora.getId().getPath().replace("_noxwood_amphora", "");

            generateAmphora(
                    pfx,
                    ingot,
                   familyName + "_amphora",
                    "noxwood_amphoras",
                    PastelAdvancements.Lategame.COLLECT_NOXWOOD,
                    planks,
                    slab,
                    amphora
            );

            pfx.generateRecipe(
                    lamp.getId().getPath().replace("_noxwood", ""),
                    new ShapedPedestalRecipeBuilder(new ItemStack(lamp.asItem(), 2))
                            .group("noxwood_lamps")
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("SSS")
                            .pattern("GXG")
                            .pattern("III")
                            .define('S', stem.asItem())
                            .define('G', gills.asItem())
                            .define('I', ingot)
                            .define('X', SHIMMERSTONE_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_NOXWOOD)
            );

            pfx.generateRecipe(
                    lantern.getId().getPath().replace("_noxwood", ""),
                    new ShapedPedestalRecipeBuilder(new ItemStack(lantern.asItem()))
                            .group("noxwood_lanterns")
                            .craftingTime(240)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern(" S ")
                            .pattern("GHG")
                            .pattern("CIC")
                            .define('S', slab.asItem())
                            .define('G', gills.asItem())
                            .define('C', block.asItem())
                            .define('I', ingot)
                            .define('H', SHIMMERSTONE_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_NOXWOOD)
            );

            pfx.generateRecipe(
                    light.getId().getPath().replace("_noxwood", ""),
                    new ShapedPedestalRecipeBuilder(new ItemStack(light.asItem(), 4))
                            .group("noxwood_lights")
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("SSS")
                            .pattern("GXG")
                            .pattern("SSS")
                            .define('S', stem.asItem())
                            .define('G', gills.asItem())
                            .define('X', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
                            .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_NOXWOOD)
            );

            pfx.generateRecipe(
                    pillar.getId().getPath().replace("_noxwood", ""),
                    new ShapedPedestalRecipeBuilder(new ItemStack(pillar.asItem(), 2))
                            .group("noxwood_decorations")
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .pattern("CC")
                            .pattern("SS")
                            .pattern("CC")
                            .define('S', stems)
                            .define('C', block.asItem())
                            .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_NOXWOOD)
            );
        }

        private static void generateBasicGlassPane(PrefixHelper pfx, @Nullable String group, ResourceLocation unlock, int craftingTime, PedestalTier tier, DeferredBlock<Block> glass, DeferredBlock<Block> pane) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(pane.asItem(), 16))
                            .group(group)
                            .craftingTime(craftingTime)
                            .tier(tier)
                            .experience(0.1f)
                            .requiredAdvancement(unlock)
                            .pattern("GGG")
                            .pattern("GGG")
                            .define('G', glass.asItem())
            );
        }


        private static void generateDragonboneSlab(PrefixHelper prefixHelper, DeferredBlock<?> slab, DeferredBlock<?> baseBlock) {
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(slab.asItem(), 6))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("WWW")
                            .define('W', baseBlock.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
        }

        private static void generateDragonboneStairs(PrefixHelper prefixHelper, DeferredBlock<?> stairs, DeferredBlock<?> baseBlock) {
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(stairs.asItem(), 6))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("W  ")
                            .pattern("WW ")
                            .pattern("WWW")
                            .define('W', baseBlock.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
        }

        private static void generateDragonboneWall(PrefixHelper prefixHelper, DeferredBlock<?> wall, DeferredBlock<?> baseBlock) {
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(wall.asItem(), 6))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("WWW")
                            .pattern("WWW")
                            .define('W', baseBlock.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .disableYieldUpgrades(true)
            );
        }
    }

    private static class SimpleRecipes {
        static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
            var rootHelper = rootPrefixHelper(ctx, lookup, PedestalTier.SIMPLE);

            generateColoredSporeBlossoms(rootHelper.subPrefix("colored_spore_blossoms"));
            generateVanilla(rootHelper.subPrefix("vanilla"));
            generateRoot(rootHelper);
        }

        private static void generateColoredSporeBlossoms(PrefixHelper pfx) {
            PastelInkColorCollection.VALUES.forEach(color -> {
                var name = PastelInkColorCollection.NAMES.pick(color);
                var tier = MINIMUM_COLOR_TIER.pick(color).withMinimumTier(PedestalTier.SIMPLE);
                var unlock = PastelAdvancements.Hidden.CollectPigment.VALUES.pick(color);
                var pigment = PIGMENTS.pick(color);
                var coloredSporeBlossom = PastelBlocks.COLORED_SPORE_BLOSSOMS.pick(color);
                var mix = POWDER_MIXES_6.pick(color);
                pfx.generateRecipe(
                        name,
                        new ShapedPedestalRecipeBuilder(new ItemStack(coloredSporeBlossom, 1))
                                .group("colored_spore_blossoms")
                                .craftingTime(80)
                                .tier(tier)
                                .replaceColorsWith(mix)
                                .experience(0.5f)
                                .pattern("PPP")
                                .pattern("PSP")
                                .pattern("PPP")
                                .define('P', pigment.asItem())
                                .define('S', Items.SPORE_BLOSSOM)
                                .requiredAdvancement(unlock)
                );
            });
        }

        private static void generateVanilla(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.BELL, 1))
                            .craftingTime(120)
                            .tier(PedestalTier.SIMPLE)
                            .color(PastelGemstoneColor.YELLOW, 4)
                            .experience(1.0f)
                            .pattern("SSS")
                            .pattern("GGG")
                            .pattern("G G")
                            .define('G', Items.GOLD_INGOT)
                            .define('S', Items.STICK)
                            .requiredAdvancement(PastelAdvancements.BUILD_BASIC_PEDESTAL_STRUCTURE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(Items.NAME_TAG, 1))
                            .craftingTime(120)
                            .tier(PedestalTier.SIMPLE)
                            .color(PastelGemstoneColor.MAGENTA, 2)
                            .color(PastelGemstoneColor.CYAN, 2)
                            .experience(1.0f)
                            .requires(Items.STRING)
                            .requires(Items.PAPER)
                            .requires(Items.PAPER)
                            .requires(PastelBlocks.FOUR_LEAF_CLOVER.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_FOUR_LEAF_CLOVER)
            );
        }

        private static void generateRoot(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BLOCK_FLOODER.asItem(), 4))
                            .craftingTime(600)
                            .simple()
                            .cyan(6)
                            .magenta(2)
                            .experience(2.0f)
                            .pattern("GCG")
                            .pattern("CMC")
                            .pattern("GCG")
                            .define('M', MERMAIDS_GEM)
                            .define('C', CYAN_PIGMENT)
                            .define('G', GREEN_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.BLOCK_FLOODER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BOTTOMLESS_BUNDLE))
                            .craftingTime(240)
                            .simple()
                            .cyan(8)
                            .experience(2.0f)
                            .pattern("QRQ")
                            .pattern("RQR")
                            .pattern("RRR")
                            .define('Q', QUITOXIC_POWDER)
                            .define('R', Items.RABBIT_HIDE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.BOTTOMLESS_BUNDLE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.COLOR_PICKER))
                            .craftingTime(300)
                            .simple()
                            .cyan(4)
                            .magenta(4)
                            .yellow(4)
                            .experience(6.0f)
                            .pattern("B B")
                            .pattern("CMY")
                            .pattern("PPP")
                            .define('C', CYAN_PIGMENT)
                            .define('M', MAGENTA_PIGMENT)
                            .define('Y', YELLOW_PIGMENT)
                            .define('P', PastelBlocks.POLISHED_CALCITE)
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.COLOR_PICKER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CONSTRUCTORS_STAFF.asItem()))
                            .craftingTime(1200)
                            .simple()
                            .cyan(6)
                            .yellow(2)
                            .experience(2.0f)
                            .pattern(" LT")
                            .pattern(" SL")
                            .pattern("S  ")
                            .define('S', Items.STICK)
                            .define('L', LIGHT_BLUE_PIGMENT)
                            .define('T', STRATINE_FRAGMENTS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.CONSTRUCTORS_STAFF)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(ENCHANTMENT_CANVAS.asItem()))
                            .craftingTime(40)
                            .simple()
                            .cyan(8)
                            .magenta(2)
                            .experience(2.0f)
                            .pattern("PBP")
                            .pattern("BAB")
                            .pattern("PBP")
                            .define('A', Items.PHANTOM_MEMBRANE)
                            .define('B', Items.BAMBOO)
                            .define('P', PURPLE_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.ENCHANTMENT_CANVAS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.ENDER_DROPPER))
                            .craftingTime(1200)
                            .simple()
                            .cyan(2)
                            .magenta(4)
                            .experience(2.0f)
                            .pattern("BSB")
                            .pattern("BPB")
                            .pattern("BRB")
                            .define('P', BLUE_PIGMENT)
                            .define('S', PastelBlocks.RADIATING_ENDER)
                            .define('B', Items.POLISHED_BLACKSTONE)
                            .define('R', Items.REDSTONE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.ENDER_BLOCKS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.ENDER_HOPPER))
                            .craftingTime(1200)
                            .simple()
                            .cyan(4)
                            .magenta(2)
                            .experience(2.0f)
                            .pattern("BPB")
                            .pattern("BSB")
                            .pattern(" B ")
                            .define('P', BLUE_PIGMENT)
                            .define('S', PastelBlocks.RADIATING_ENDER)
                            .define('B', Items.POLISHED_BLACKSTONE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.ENDER_BLOCKS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FLOWING_STAFF.asItem()))
                            .craftingTime(1200)
                            .simple()
                            .cyan(6)
                            .yellow(2)
                            .experience(2.0f)
                            .pattern(" YP")
                            .pattern(" SY")
                            .pattern("S  ")
                            .define('S', Items.STICK)
                            .define('Y', YELLOW_PIGMENT)
                            .define('P', PALTAERIA_FRAGMENTS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.FLOWING_STAFF)
            );

            List.of(
                    new Triplet<>(PastelBlocks.FUSION_SHRINE_BASALT, PastelBlocks.POLISHED_BASALT, PastelBlocks.POLISHED_CALCITE),
                    new Triplet<>(PastelBlocks.FUSION_SHRINE_CALCITE, PastelBlocks.POLISHED_CALCITE, PastelBlocks.POLISHED_BASALT)
            ).forEach(triplet -> {
                var shrine = triplet.getA();
                var primary = triplet.getB();
                var secondary = triplet.getC();

                pfx.generateAutoNamedRecipe(
                        new ShapedPedestalRecipeBuilder(new ItemStack(shrine))
                                .group("fusion_shrines")
                                .craftingTime(600)
                                .simple()
                                .cyan(4)
                                .magenta(4)
                                .yellow(4)
                                .experience(2.0f)
                                .pattern("PCP")
                                .pattern("SAS")
                                .pattern("PTP")
                                .define('T', PastelBlocks.POLISHED_TOPAZ_BLOCK)
                                .define('A', PastelBlocks.POLISHED_AMETHYST_BLOCK)
                                .define('C', PastelBlocks.POLISHED_CITRINE_BLOCK)
                                .define('P', primary)
                                .define('S', secondary)
                                .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.FUSION_SHRINE)
                                .disableYieldUpgrades(true)
                );
            });

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(INK_ASSORTMENT.asItem()))
                            .craftingTime(600)
                            .simple()
                            .cyan(1)
                            .magenta(1)
                            .yellow(1)
                            .experience(4.0f)
                            .pattern("SCS")
                            .pattern("PPP")
                            .pattern("GGG")
                            .define('P', PastelItemTags.PIGMENTS)
                            .define('G', Items.GLASS)
                            .define('C', PastelBlocks.FOUR_LEAF_CLOVER)
                            .define('S', SHIMMERSTONE_GEM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.INK_ASSORTMENT)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(INK_FLASK.asItem()))
                            .craftingTime(300)
                            .simple()
                            .cyan(1)
                            .magenta(1)
                            .yellow(1)
                            .experience(1.0f)
                            .pattern("SGS")
                            .pattern("GPG")
                            .pattern("SGS")
                            .define('P', PastelItemTags.PIGMENTS)
                            .define('G', Items.GLASS)
                            .define('S', SHIMMERSTONE_GEM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.BASIC_INK_STORAGE_ITEMS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.ITEM_ROUNDEL))
                            .craftingTime(120)
                            .simple()
                            .cyan(2)
                            .experience(1.0f)
                            .pattern("BCB")
                            .pattern("SCS")
                            .pattern("BBB")
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('C', PastelBlocks.POLISHED_CALCITE)
                            .define('S', SHIMMERSTONE_GEM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.ITEM_ROUNDEL)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), MULTITOOL.get()))
                            .craftingTime(600)
                            .simple()
                            .cyan(1)
                            .magenta(1)
                            .yellow(1)
                            .experience(2.0f)
                            .pattern("III")
                            .pattern("IS ")
                            .pattern(" S ")
                            .define('S', Items.STICK)
                            .define('I', Items.IRON_INGOT)
                            .requiredAdvancement(PastelAdvancements.BUILD_BASIC_PEDESTAL_STRUCTURE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(NATURES_STAFF.asItem()))
                            .craftingTime(1200)
                            .simple()
                            .magenta(6)
                            .yellow(2)
                            .experience(2.0f)
                            .pattern(" LM")
                            .pattern(" SL")
                            .pattern("S  ")
                            .define('S', Items.STICK)
                            .define('M', Items.MOSS_BLOCK)
                            .define('L', LIME_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.NATURES_STAFF)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(pfx.getLookup(), OBLIVION_PICKAXE.get()))
                            .craftingTime(1200)
                            .simple()
                            .cyan(2)
                            .magenta(4)
                            .experience(2.0f)
                            .pattern("PQP")
                            .pattern(" S ")
                            .pattern(" S ")
                            .define('S', Items.CALCITE)
                            .define('Q', QUITOXIC_POWDER)
                            .define('P', Items.PHANTOM_MEMBRANE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.OBLIVION_PICKAXE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.PARTICLE_SPAWNER))
                            .craftingTime(300)
                            .simple()
                            .cyan(2)
                            .magenta(6)
                            .yellow(2)
                            .experience(2.0f)
                            .pattern("PSP")
                            .pattern("PXP")
                            .pattern("YRY")
                            .define('P', PastelItemTags.PIGMENTS)
                            .define('S', STAR_FRAGMENT)
                            .define('X', PastelBlocks.POLISHED_CALCITE)
                            .define('Y', PastelBlocks.POLISHED_BASALT)
                            .define('R', Items.REDSTONE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.PARTICLE_SPAWNER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.PEDESTAL_ONYX))
                            .craftingTime(300)
                            .simple()
                            .experience(8.0f)
                            .pattern("SOS")
                            .pattern("RRR")
                            .define('O', ONYX_SHARD)
                            .define('S', SHIMMERSTONE_GEM)
                            .define('R', Items.OBSIDIAN)
                            .requiredAdvancement(PastelAdvancements.CREATE_ONYX_SHARD)
                            .disableYieldUpgrades(true)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(RADIANCE_STAFF.asItem()))
                            .craftingTime(1200)
                            .simple()
                            .yellow(8)
                            .experience(2.0f)
                            .pattern(" BG")
                            .pattern(" SB")
                            .pattern("S  ")
                            .define('S', Items.STICK)
                            .define('G', SHIMMERSTONE_GEM)
                            .define('B', YELLOW_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.RADIANCE_STAFF)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.REDSTONE_TRANSCEIVER, 2))
                            .craftingTime(400)
                            .simple()
                            .cyan(2)
                            .magenta(2)
                            .yellow(2)
                            .experience(1.0f)
                            .pattern("TOR")
                            .pattern("SSS")
                            .define('O', ONYX_SHARD)
                            .define('R', Items.REDSTONE)
                            .define('T', Items.REDSTONE_TORCH)
                            .define('S', Items.STONE)
                            .requiredAdvancement(PastelAdvancements.CREATE_ONYX_SHARD)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(WIRE_HOOK.asItem()))
                            .craftingTime(1200)
                            .simple()
                            .cyan(4)
                            .yellow(4)
                            .experience(2.0f)
                            .pattern("CCG")
                            .pattern("CDC")
                            .pattern("BCC")
                            .define('C', Items.CHAIN)
                            .define('G', SHIMMERSTONE_GEM)
                            .define('B', Tags.Items.STORAGE_BLOCKS_COPPER)
                            .define('D', Tags.Items.GEMS_DIAMOND) // ?????
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.WIRE_HOOK)
            );


        }
    }

    private static class AdvancedRecipes {
        static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
            var rootHelper = rootPrefixHelper(ctx, lookup, PedestalTier.ADVANCED);

            generateChimes(rootHelper.subPrefix("gemstone_chimes"));
            generateGlowblocks(rootHelper.subPrefix("glowblocks"));
            generateIdols(rootHelper.subPrefix("idols"));
            generatePastelNetwork(rootHelper.subPrefix("pastel_network"));
            generateSemiPermeableGlasses(rootHelper.subPrefix("semi_permeable_glass"));
            generateTrinkets(rootHelper.subPrefix("trinkets"));
            generateRoot(rootHelper);
        }

        private static void generateChimes(PrefixHelper pfx) {
            PastelGemstoneColorCollection.VALUES.forEach(color -> {
                var shard = GEMSTONE_SHARDS.pick(color);
                var chime = PastelBlocks.CHIMES.pick(color);
                var name = PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color);
                var unlock = PastelAdvancements.Unlocks.GemstoneChimes.VALUES.pick(color);
                var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color).withMinimumTier(PedestalTier.ADVANCED);
                pfx.generateRecipe(
                        name,
                        new ShapedPedestalRecipeBuilder(new ItemStack(chime, 1))
                                .group("gemstone_chimes")
                                .craftingTime(60)
                                .tier(tier)
                                .color(color, 1)
                                .experience(1.0f)
                                .pattern(" Y ")
                                .pattern("SGS")
                                .pattern("PXP")
                                .define('X', PastelBlocks.POLISHED_BASALT.asItem())
                                .define('Y', PastelBlocks.POLISHED_CALCITE.asItem())
                                .define('S', shard.value())
                                .define('G', Items.STRING)
                                .define('P', SHIMMERSTONE_GEM.asItem())
                                .requiredAdvancement(unlock)
                );
            });
        }

        private static void generateGlowblocks(PrefixHelper pfx) {
            PastelInkColorCollection.VALUES.forEach(color -> {
                var pigment = PIGMENTS.pick(color);
                var glowBlock = PastelBlocks.GLOWBLOCKS.pick(color);
                var unlock = PastelAdvancements.Unlocks.Glowblocks.VALUES.pick(color);
                var mix = POWDER_MIXES_6.pick(color);
                var tier = MINIMUM_COLOR_TIER.pick(color).withMinimumTier(PedestalTier.ADVANCED);
                var name = PastelInkColorCollection.NAMES.pick(color);

                pfx.generateRecipe(
                        name,
                        new ShapedPedestalRecipeBuilder(new ItemStack(glowBlock, 4))
                                .group("glowblocks")
                                .craftingTime(200)
                                .tier(tier)
                                .replaceColorsWith(mix)
                                .experience(1.0f)
                                .pattern("PQP")
                                .pattern("QPQ")
                                .pattern("PQP")
                                .define('P', pigment.asItem())
                                .define('Q', QUITOXIC_POWDER.asItem())
                                .requiredAdvancement(unlock)
                );
            });
        }

        private static void generateIdols(PrefixHelper pfx) {
            HEAD_IDOL_PAIRS.forEach((head, idol) -> {
                var name = idol.getId().getPath().replace("_idol", "");
                pfx.generateRecipe(
                        name,
                        new ShapedPedestalRecipeBuilder(new ItemStack(idol))
                                .group("idols")
                                .craftingTime(200)
                                .advanced()
                                .magenta(4)
                                .yellow(2)
                                .black(1)
                                .experience(2.0f)
                                .pattern("SVS")
                                .pattern("VHV")
                                .pattern("QVQ")
                                .define('H', head)
                                .define('V', VEGETAL)
                                .define('Q', QUITOXIC_POWDER)
                                .define('S', STARDUST)
                                .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.IDOLS)
                );
            });
        }

        private static void generatePastelNetwork(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(TUNING_STAMP.asItem()))
                            .group("pastel_network")
                            .craftingTime(80)
                            .advanced()
                            .cyan(1)
                            .magenta(1)
                            .yellow(1)
                            .black(4)
                            .experience(2.0f)
                            .pattern(" I ")
                            .pattern("GAI") // Yes i am, how'd u know?
                            .pattern("SG ")
                            .define('S', Items.STICK)
                            .define('G', Items.GLOW_INK_SAC)
                            .define('I', Items.INK_SAC)
                            .define('A', RAW_AZURITE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.PASTEL_NETWORK)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BUFFER_NODE))
                            .group("pastel_network")
                            .craftingTime(80)
                            .advanced()
                            .black(4)
                            .experience(1.0f)
                            .pattern(" S ")
                            .pattern("SGS")
                            .pattern(" S ")
                            .define('S', Items.GLOW_INK_SAC)
                            .define('G', PastelBlocks.GATHER_NODE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.PASTEL_NETWORK)
            );

            generateNodePair(pfx, null, new ItemStack(PastelBlocks.CONNECTION_NODE, 4));
            generateNodePair(pfx, PastelGemstoneColor.BLACK, new ItemStack(PastelBlocks.GATHER_NODE));
            generateNodePair(pfx, PastelGemstoneColor.MAGENTA, new ItemStack(PastelBlocks.PROVIDER_NODE));
            generateNodePair(pfx, PastelGemstoneColor.YELLOW, new ItemStack(PastelBlocks.SENDER_NODE));
            generateNodePair(pfx, PastelGemstoneColor.CYAN, new ItemStack(PastelBlocks.STORAGE_NODE));
        }

        private static void generateSemiPermeableGlasses(PrefixHelper pfx) {
            pfx.generateRecipe(
                    "glass",
                    sharedSemiPermeableGlass(Items.GLASS, Items.GLASS, PastelBlocks.SEMI_PERMEABLE_GLASS)
                            .color(PastelGemstoneColor.CYAN, 1)
                            .color(PastelGemstoneColor.MAGENTA, 1)
                            .color(PastelGemstoneColor.YELLOW, 1)
            );

            pfx.generateRecipe(
                    "tinted",
                    sharedSemiPermeableGlass(ONYX_SHARD.asItem(), Items.TINTED_GLASS, PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS)
                            .color(PastelGemstoneColor.BLACK, 2)
            );

            pfx.generateRecipe(
                    "radiant",
                    sharedSemiPermeableGlass(SHIMMERSTONE_GEM.asItem(), Items.GLASS, PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS)
                            .color(PastelGemstoneColor.MAGENTA, 4)
                            .color(PastelGemstoneColor.YELLOW, 2)

            );

            PastelGemstoneColorCollection.VALUES.forEach(color -> {
               var result = PastelBlocks.GEMSTONE_SEMI_PERMEABLE_GLASSES.pick(color);
               var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color).withMinimumTier(PedestalTier.ADVANCED);
               var unlock =
                       color == PastelGemstoneColor.WHITE
                               ? PastelAdvancements.Unlocks.Blocks.MOONSTONE_SEMI_PERMEABLE_GLASS
                               : PastelAdvancements.Midgame.BUILD_ADVANCED_PEDESTAL_STRUCTURE;
               var shard = GEMSTONE_SHARDS.pick(color).value();
               var name = PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color);
               pfx.generateRecipe(
                       name,
                       sharedSemiPermeableGlass(shard, Items.GLASS, result)
                               .color(color, 8)
                               .tier(tier)
                               .requiredAdvancement(unlock)
               );
            });
        }

        private static void generateTrinkets(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FANCIFUL_BELT.asItem()))
                            .craftingTime(200)
                            .advanced()
                            .experience(0.5f)
                            .pattern(" L ")
                            .pattern("L L")
                            .pattern("GL ")
                            .define('L', Items.LEATHER)
                            .define('G', Items.GOLD_INGOT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.FANCIFUL_BELT)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FANCIFUL_CIRCLET.asItem()))
                            .craftingTime(200)
                            .advanced()
                            .experience(0.5f)
                            .pattern(" L ")
                            .pattern("L L")
                            .pattern("QL ")
                            .define('L', Items.TUFF)
                            .define('Q', Items.QUARTZ)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.FANCIFUL_CIRCLET)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FANCIFUL_GLOVES.asItem()))
                            .craftingTime(200)
                            .advanced()
                            .experience(0.5f)
                            .pattern(" L ")
                            .pattern("L L")
                            .pattern("T T")
                            .define('T', Items.TUFF)
                            .define('L', Items.LEATHER)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.FANCIFUL_GLOVES)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FANCIFUL_PENDANT.asItem()))
                            .craftingTime(200)
                            .advanced()
                            .experience(0.5f)
                            .pattern(" G ")
                            .pattern("G G")
                            .pattern("LG ")
                            .define('G', Items.GOLD_INGOT)
                            .define('L', Items.LAPIS_LAZULI)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.FANCIFUL_PENDANT)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FANCIFUL_TUFF_RING.asItem()))
                            .craftingTime(200)
                            .advanced()
                            .experience(0.5f)
                            .pattern(" L ")
                            .pattern("L L")
                            .pattern(" L ")
                            .define('L', Items.TUFF)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.FANCIFUL_TUFF_RING)
            );
        }

        private static void generateRoot(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(ARTISANS_ATLAS.asItem()))
                            .craftingTime(3600)
                            .advanced()
                            .cyan(8)
                            .black(2)
                            .experience(4.0f)
                            .pattern("PPP")
                            .pattern("SCS")
                            .pattern("PPP")
                            .define('C', PastelBlocks.FOUR_LEAF_CLOVER)
                            .define('P', Items.PAPER)
                            .define('S', STARDUST)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.ARTISANS_ATLAS)
            );
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BLACK_HOLE_CHEST))
                            .craftingTime(3600)
                            .advanced()
                            .cyan(4)
                            .magenta(8)
                            .yellow(4)
                            .black(2)
                            .experience(4.0f)
                            .pattern("BBB")
                            .pattern("XSX")
                            .pattern("YYY")
                            .define('B', BLUE_PIGMENT)
                            .define('Y', PastelBlocks.POLISHED_BASALT)
                            .define('X', PastelBlocks.POLISHED_CALCITE)
                            .define('S', STRATINE_GEM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.BLACK_HOLE_CHEST)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.BLOCK_BREAKER))
                            .craftingTime(200)
                            .advanced()
                            .cyan(4)
                            .experience(1.0f)
                            .pattern("CCC")
                            .pattern("RSB")
                            .pattern("BBB")
                            .define('R', Items.REDSTONE)
                            .define('S', STORM_STONE)
                            .define('B', PastelBlocks.POLISHED_BONE_ASH)
                            .define('C', PastelBlocks.PYRITE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Redstone.BLOCK_BREAKER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BOTTLE_OF_DECAY_AWAY.asItem()))
                            .craftingTime(200)
                            .advanced()
                            .cyan(1)
                            .magenta(1)
                            .yellow(1)
                            .pattern("GAG")
                            .pattern("NBN")
                            .pattern("GAG")
                            .define('B', Items.GLASS_BOTTLE)
                            .define('N', NEOLITH)
                            .define('A', RAW_AZURITE)
                            .define('G', GREEN_PIGMENT)
                            .requiredAdvancement(itemUnlock(BOTTLE_OF_DECAY_AWAY))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BOTTLE_OF_FAILING.asItem()))
                            .craftingTime(800)
                            .advanced()
                            .cyan(1)
                            .magenta(1)
                            .yellow(1)
                            .black(1)
                            .experience(2.0f)
                            .pattern("FSF")
                            .pattern("PBP")
                            .pattern("FSF")
                            .define('S', STRATINE_FRAGMENTS)
                            .define('F', Items.FERMENTED_SPIDER_EYE)
                            .define('P', Items.PRISMARINE_CRYSTALS)
                            .define('B', Items.EXPERIENCE_BOTTLE)
                            .requiredAdvancement(itemUnlock(BOTTLE_OF_FAILING))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BOTTLE_OF_RUIN.asItem()))
                            .craftingTime(1200)
                            .advanced()
                            .cyan(4)
                            .magenta(4)
                            .yellow(4)
                            .black(4)
                            .advanced()
                            .experience(2.0f)
                            .skipRemainders(true)
                            .pattern("FMF")
                            .pattern("ABA")
                            .pattern("FMF")
                            .define('A', RAW_AZURITE)
                            .define('F', Items.FERMENTED_SPIDER_EYE)
                            .define('M', MIDNIGHT_CHIP)
                            .define('B', Items.DRAGON_BREATH)
                            .requiredAdvancement(itemUnlock(BOTTLE_OF_RUIN))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.CINDERHEARTH))
                            .craftingTime(600)
                            .advanced()
                            .cyan(4)
                            .magenta(4)
                            .yellow(8)
                            .black(2)
                            .experience(8.0f)
                            .pattern(" C ")
                            .pattern("CGC")
                            .pattern("BFB")
                            .define('C', PastelBlocks.POLISHED_CALCITE)
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('G', STRATINE_GEM)
                            .define('F', STRATINE_FRAGMENTS)
                            .requiredAdvancement(blockUnlock(PastelBlocks.CINDERHEARTH))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CRESCENT_CLOCK.asItem()))
                            .craftingTime(400)
                            .advanced()
                            .magenta(4)
                            .experience(2.0f)
                            .pattern(" O ")
                            .pattern("ORO")
                            .pattern(" C ")
                            .define('O', ONYX_SHARD)
                            .define('C', Items.COPPER_INGOT)
                            .define('R', Items.REDSTONE)
                            .requiredAdvancement(itemUnlock(CRESCENT_CLOCK))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.CRYSTAL_APOTHECARY))
                            .craftingTime(800)
                            .advanced()
                            .yellow(8)
                            .experience(4.0f)
                            .pattern(" L ")
                            .pattern("LBL")
                            .pattern("BCB")
                            .define('L', STORM_STONE)
                            .define('C', PastelBlocks.POLISHED_CALCITE)
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .requiredAdvancement(blockUnlock(PastelBlocks.CRYSTAL_APOTHECARY))
            );

            pfx.generateRecipe(
                    "dark_stakes",
                    new ShapedPedestalRecipeBuilder(new ItemStack(DARK_STAKE.asItem(), 4))
                            .craftingTime(80)
                            .advanced()
                            .black(4)
                            .experience(4.0f)
                            .pattern("  C")
                            .pattern(" S ")
                            .pattern("T  ")
                            .define('C', MIDNIGHT_CHIP)
                            .define('S', Items.STRING)
                            .define('T', Items.STICK)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.DARK_STAKES)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.ENCHANTER))
                            .craftingTime(400)
                            .advanced()
                            .magenta(4)
                            .black(2)
                            .experience(2.0f)
                            .pattern("SLS")
                            .pattern("BLB")
                            .pattern("BCB")
                            .define('L', Items.LAPIS_BLOCK)
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('C', PastelBlocks.POLISHED_CALCITE)
                            .define('S', STRATINE_FRAGMENTS)
                            .requiredAdvancement(blockUnlock(PastelBlocks.ENCHANTER))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(ENDER_SPLICE.asItem(), 4))
                            .craftingTime(240)
                            .advanced()
                            .magenta(4)
                            .cyan(4)
                            .experience(2.0f)
                            .pattern("PCP")
                            .pattern("ETE")
                            .pattern("PCP")
                            .define('T', PastelBlocks.RADIATING_ENDER)
                            .define('C', Items.CHORUS_FRUIT)
                            .define('E', NEOLITH)
                            .define('P', CYAN_PIGMENT)
                            .requiredAdvancement(itemUnlock(ENDER_SPLICE))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.ETHEREAL_PLATFORM, 8))
                            .craftingTime(600)
                            .advanced()
                            .cyan(2)
                            .magenta(2)
                            .yellow(2)
                            .black(1)
                            .experience(2.0f)
                            .pattern("YGY")
                            .pattern("SSS")
                            .pattern("PSP")
                            .define('S', STARDUST)
                            .define('G', SHIMMERSTONE_GEM)
                            .define('Y', YELLOW_PIGMENT)
                            .define('P', PURPLE_PIGMENT)
                            .requiredAdvancement(blockUnlock(PastelBlocks.ETHEREAL_PLATFORM))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(EXCHANGING_STAFF.asItem()))
                            .craftingTime(1200)
                            .advanced()
                            .cyan(6)
                            .yellow(2)
                            .black(1)
                            .experience(2.0f)
                            .pattern(" BP")
                            .pattern(" TB")
                            .pattern("T  ")
                            .define('T', Items.STICK)
                            .define('B', BROWN_PIGMENT)
                            .define('P', PastelBlocks.HOVERBLOCK)
                            .requiredAdvancement(itemUnlock(EXCHANGING_STAFF))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.FABRICATION_CHEST))
                            .craftingTime(1600)
                            .advanced()
                            .cyan(4)
                            .yellow(2)
                            .black(1)
                            .experience(2.0f)
                            .pattern("CTC")
                            .pattern("PGP")
                            .pattern("BBB")
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('P', PastelBlocks.POLISHED_CALCITE)
                            .define('C', Items.COPPER_INGOT)
                            .define('G', STRATINE_FRAGMENTS)
                            .define('T', RED_PIGMENT)
                            .requiredAdvancement(blockUnlock(PastelBlocks.FABRICATION_CHEST))
            );

            Map.of(
                    PastelBlocks.ITEM_BOWL_BASALT, PastelBlocks.POLISHED_BASALT,
                    PastelBlocks.ITEM_BOWL_CALCITE, PastelBlocks.POLISHED_CALCITE
            ).forEach((bowl, block) -> {
                pfx.generateAutoNamedRecipe(
                        new ShapedPedestalRecipeBuilder(new ItemStack(bowl))
                                .group("item_bowls")
                                .craftingTime(120)
                                .advanced()
                                .black(1)
                                .experience(1.0f)
                                .pattern("S S")
                                .pattern("XSX")
                                .pattern("XXX")
                                .define('X', block)
                                .define('S', SHIMMERSTONE_GEM)
                                .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.ITEM_BOWL)
                );
            });

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(KNOWLEDGE_GEM.asItem()))
                            .craftingTime(160)
                            .advanced()
                            .magenta(8)
                            .black(2)
                            .experience(2.0f)
                            .pattern("GPG")
                            .pattern("PEP")
                            .pattern("GPG")
                            .define('E', Items.EMERALD)
                            .define('P', PURPLE_PIGMENT)
                            .define('G', GREEN_PIGMENT)
                            .requiredAdvancement(itemUnlock(KNOWLEDGE_GEM))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(LIQUID_CRYSTAL_BUCKET.asItem()))
                            .craftingTime(240)
                            .advanced()
                            .magenta(1)
                            .yellow(1)
                            .cyan(1)
                            .black(1)
                            .experience(1.0f)
                            .pattern("PMP")
                            .pattern("SBS")
                            .pattern("YMY")
                            .define('P', PINK_PIGMENT)
                            .define('Y', YELLOW_PIGMENT)
                            .define('B', Items.BUCKET)
                            .define('S', SHIMMERSTONE_GEM)
                            .define('M', MERMAIDS_GEM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.LIQUID_CRYSTAL)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(OMNI_ACCELERATOR.asItem()))
                            .craftingTime(3600)
                            .advanced()
                            .cyan(4)
                            .yellow(16)
                            .black(2)
                            .experience(4.0f)
                            .pattern("GMG")
                            .pattern("STS")
                            .pattern("MCM")
                            .define('M', PURE_MALACHITE)
                            .define('S', Items.STRING)
                            .define('T', Items.TRIPWIRE_HOOK)
                            .define('G', PURE_GOLD)
                            .define('C', MOONSTONE_CORE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.OMNI_ACCELERATOR)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(ONYX_HELMET.asItem()))
                            .craftingTime(400)
                            .advanced()
                            .cyan(4)
                            .magenta(4)
                            .yellow(4)
                            .black(4)
                            .experience(2.0f)
                            .pattern("OOO")
                            .pattern("OCO")
                            .define('O', ONYX_SHARD)
                            .define('C', LIQUID_CRYSTAL_BUCKET)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.ONYX_HELMET)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.PEDESTAL_MOONSTONE))
                            .craftingTime(200)
                            .advanced()
                            .experience(16.0f)
                            .pattern("MMM")
                            .pattern("BRB")
                            .define('M', MOONSTONE_SHARD)
                            .define('B', BISMUTH_FLAKE)
                            .define('R', PastelBlocks.POLISHED_ONYX_BLOCK)
                            .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_MOONSTONE)
                            .disableYieldUpgrades(true)
            );

            pfx.generateAutoNamedRecipe(
                    // YAYYY MY FAVORITE!!!!!
                    new ShapedPedestalRecipeBuilder(new ItemStack(PIPE_BOMB.asItem()))
                            .craftingTime(400)
                            .advanced()
                            .yellow(2)
                            .black(4)
                            .experience(1.0f)
                            .pattern("CGC")
                            .pattern("GMG")
                            .pattern("CGC")
                            .define('C', MIDNIGHT_CHIP)
                            .define('G', DOOMBLOOM_SEED)
                            .define('M', MOONSTONE_CORE)
                            .requiredAdvancement(itemUnlock(PIPE_BOMB))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.POTION_WORKSHOP))
                            .craftingTime(1200)
                            .advanced()
                            .magenta(4)
                            .yellow(4)
                            .black(1)
                            .experience(4.0f)
                            .pattern("CQC")
                            .pattern("PGP")
                            .pattern("BBB")
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('P', PastelBlocks.POLISHED_CALCITE)
                            .define('C', Items.COPPER_INGOT)
                            .define('G', STRATINE_FRAGMENTS)
                            .define('Q', QUITOXIC_POWDER)
                            .requiredAdvancement(blockUnlock(PastelBlocks.POTION_WORKSHOP))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.SPIRIT_INSTILLER))
                            .craftingTime(1200)
                            .advanced()
                            .black(1)
                            .experience(2.0f)
                            .pattern("CCC")
                            .pattern(" B ")
                            .pattern("ACA")
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('C', PastelBlocks.POLISHED_CALCITE)
                            .define('A', RAW_AZURITE)
                            .requiredAdvancement(blockUnlock(PastelBlocks.SPIRIT_INSTILLER))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(STRATINE_GEM.asItem()))
                            .craftingTime(400)
                            .advanced()
                            .black(2)
                            .experience(0.5f)
                            .pattern("###")
                            .pattern("#X#")
                            .pattern("###")
                            .define('#', STRATINE_FRAGMENTS)
                            .define('X', Items.EMERALD)
                            .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_STRATINE_GEM)
                            .disableYieldUpgrades(true)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.UNIVERSE_SPYHOLE, 4))
                            .craftingTime(600)
                            .advanced()
                            .cyan(2)
                            .magenta(2)
                            .yellow(2)
                            .black(2)
                            .experience(2.0f)
                            .pattern("PMP")
                            .pattern("MEM")
                            .pattern("PMP")
                            .define('E', PastelBlocks.RADIATING_ENDER)
                            .define('M', MIDNIGHT_CHIP)
                            .define('P', BLACK_PIGMENT)
                            .requiredAdvancement(blockUnlock(PastelBlocks.UNIVERSE_SPYHOLE))
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_EFFICIENCY))
                            .craftingTime(3600)
                            .advanced()
                            .cyan(16)
                            .magenta(16)
                            .yellow(16)
                            .black(4)
                            .experience(4.0f)
                            .pattern("GLG")
                            .pattern("TAC")
                            .pattern("YYY")
                            .define('Y', PastelBlocks.POLISHED_BASALT)
                            .define('A', PastelBlocks.POLISHED_AMETHYST_BLOCK)
                            .define('T', PastelBlocks.POLISHED_TOPAZ_BLOCK)
                            .define('C', PastelBlocks.POLISHED_CITRINE_BLOCK)
                            .define('L', RAW_AZURITE)
                            .define('G', GREEN_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_EFFICIENCY)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_EXPERIENCE))
                            .craftingTime(1200)
                            .advanced()
                            .cyan(4)
                            .magenta(8)
                            .yellow(4)
                            .experience(8.0f)
                            .pattern("PLP")
                            .pattern("XEX")
                            .pattern("YYY")
                            .define('E', Items.EMERALD_BLOCK)
                            .define('X', PastelBlocks.POLISHED_CALCITE)
                            .define('Y', PastelBlocks.POLISHED_BASALT)
                            .define('L', STORM_STONE)
                            .define('P', PURPLE_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_EXPERIENCE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_EXPERIENCE2))
                            .craftingTime(3600)
                            .advanced()
                            .cyan(4)
                            .magenta(8)
                            .yellow(4)
                            .experience(16.0f)
                            .requires(PastelBlocks.UPGRADE_EXPERIENCE)
                            .requires(MOONSTONE_SHARD)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_EXPERIENCE2)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_SPEED))
                            .craftingTime(1200)
                            .advanced()
                            .cyan(2)
                            .magenta(4)
                            .yellow(2)
                            .black(1)
                            .experience(4.0f)
                            .pattern("PAP")
                            .pattern("XTX")
                            .pattern("YCY")
                            .define('X', PastelBlocks.POLISHED_CALCITE)
                            .define('Y', PastelBlocks.POLISHED_BASALT)
                            .define('A', Items.AMETHYST_BLOCK)
                            .define('T', PastelBlocks.TOPAZ_BLOCK)
                            .define('C', PastelBlocks.CITRINE_BLOCK)
                            .define('P', MAGENTA_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_SPEED)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_SPEED2))
                            .craftingTime(3600)
                            .advanced()
                            .cyan(4)
                            .magenta(8)
                            .yellow(4)
                            .black(8)
                            .experience(4.0f)
                            .requires(STORM_STONE)
                            .requires(PastelBlocks.UPGRADE_SPEED)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_SPEED2)
            );



            pfx.generateDynamicRecipe("ender_canvas", new EnderCanvasRecipe());
            pfx.generateDynamicRecipe("ender_canvas_large", new EnderCanvasLargeRecipe());
        }

        private static void generateNodePair(PrefixHelper pfx, @Nullable PastelGemstoneColor color, ItemStack baseStack) {
            var center = color == null ? Items.QUARTZ : PastelItems.GEMSTONE_SHARDS.pick(color).value();
            var refinedStack = baseStack.copyWithCount(baseStack.getCount() * 4);
            var refinedName = Objects.requireNonNull(baseStack.getItemHolder().getKey()).location().getPath() + "_refined";
            Map<GemstoneColor, Integer> mix =
                    color == null ? Map.of() : Map.of(color, 1);

            Map<GemstoneColor, Integer> refinedMix =
                    color == null ? Map.of() : Map.of(color, 4);

            pfx.generateAutoNamedRecipe(
                    baseNode(baseStack)
                            .define('S', center)
                            .define('A', RAW_AZURITE)
                            .replaceColorsWith(mix)
                            .experience(1.0f)
                            .group("pastel_network")
            );

            pfx.generateRecipe(
                    refinedName,
                    baseNode(refinedStack)
                            .define('S', center)
                            .define('A', PURE_AZURITE)
                            .replaceColorsWith(refinedMix)
                            .experience(4.0f)
                            .group("pure_pastel_network")
            );
        }

        private static ShapedPedestalRecipeBuilder baseNode(ItemStack result) {
            return new ShapedPedestalRecipeBuilder(result)
                    .craftingTime(80)
                    .advanced()
                    .pattern(" A ")
                    .pattern("ASA")
                    .pattern("YXY")
                    .define('X', PastelBlocks.POLISHED_BASALT)
                    .define('Y', PastelBlocks.POLISHED_CALCITE)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.PASTEL_NETWORK);
        }


        private static ShapedPedestalRecipeBuilder sharedSemiPermeableGlass(Item center, Item glass, DeferredBlock<?> result) {
            return new ShapedPedestalRecipeBuilder(new ItemStack(result.asItem(), 6))
                    .group("semi_permeable_glass")
                    .craftingTime(80)
                    .experience(1.0f)
                    .tier(PedestalTier.ADVANCED)
                    .pattern("GPG")
                    .pattern("PSP")
                    .pattern("GPG")
                    .define('S', center)
                    .define('P', PURPLE_PIGMENT.asItem())
                    .define('G', glass)
                    .requiredAdvancement(PastelAdvancements.Midgame.BUILD_ADVANCED_PEDESTAL_STRUCTURE);
        }

        private static final Map<IngredientStack, DeferredBlock<?>> HEAD_IDOL_PAIRS =
                Map.ofEntries(
                        hPair(PastelItemTags.MobHeads.AXOLOTL_HEADS, PastelBlocks.AXOLOTL_IDOL),
                        hPair(PastelSkullType.BAT, PastelBlocks.BAT_IDOL),
                        hPair(PastelSkullType.BEE, PastelBlocks.BEE_IDOL),
                        hPair(PastelSkullType.BLAZE, PastelBlocks.BLAZE_IDOL),
                        hPair(PastelSkullType.CAT, PastelBlocks.CAT_IDOL),
                        hPair(PastelSkullType.CHICKEN, PastelBlocks.CHICKEN_IDOL),
                        hPair(PastelItemTags.MobHeads.BOVINE_HEADS, PastelBlocks.COW_IDOL),
                        hPair(Items.CREEPER_HEAD, PastelBlocks.CREEPER_IDOL),
                        hPair(Items.DRAGON_HEAD, PastelBlocks.ENDER_DRAGON_IDOL),
                        hPair(PastelSkullType.ENDERMAN, PastelBlocks.ENDERMAN_IDOL),
                        hPair(PastelSkullType.ENDERMITE, PastelBlocks.ENDERMITE_IDOL),
                        hPair(PastelSkullType.EVOKER, PastelBlocks.EVOKER_IDOL),
                        hPair(PastelItemTags.MobHeads.FISH_HEADS, PastelBlocks.FISH_IDOL),
                        hPair(PastelItemTags.MobHeads.FOX_HEADS, PastelBlocks.FOX_IDOL),
                        hPair(PastelSkullType.GHAST, PastelBlocks.GHAST_IDOL),
                        hPair(PastelSkullType.GLOW_SQUID, PastelBlocks.GLOW_SQUID_IDOL),
                        hPair(PastelSkullType.GOAT, PastelBlocks.GOAT_IDOL),
                        hPair(PastelItemTags.MobHeads.GUARDIAN_HEADS, PastelBlocks.GUARDIAN_IDOL),
                        hPair(PastelItemTags.MobHeads.EQUIDAE_HEADS, PastelBlocks.HORSE_IDOL),
                        hPair(PastelSkullType.ILLUSIONER, PastelBlocks.ILLUSIONER_IDOL),
                        hPair(PastelSkullType.OCELOT, PastelBlocks.OCELOT_IDOL),
                        hPair(PastelItemTags.MobHeads.PARROT_HEADS, PastelBlocks.PARROT_IDOL),
                        hPair(PastelSkullType.PHANTOM, PastelBlocks.PHANTOM_IDOL),
                        hPair(PastelSkullType.PIG, PastelBlocks.PIG_IDOL),
                        hPair(Items.PIGLIN_HEAD, PastelBlocks.PIGLIN_IDOL),
                        hPair(PastelSkullType.POLAR_BEAR, PastelBlocks.POLAR_BEAR_IDOL),
                        hPair(PastelSkullType.PUFFERFISH, PastelBlocks.PUFFERFISH_IDOL),
                        hPair(PastelSkullType.RABBIT, PastelBlocks.RABBIT_IDOL),
                        hPair(PastelSkullType.SHEEP, PastelBlocks.SHEEP_IDOL),
                        hPair(PastelItemTags.MobHeads.SHULKER_HEADS, PastelBlocks.SHULKER_IDOL),
                        hPair(PastelSkullType.SILVERFISH, PastelBlocks.SILVERFISH_IDOL),
                        hPair(Items.SKELETON_SKULL, PastelBlocks.SKELETON_IDOL),
                        hPair(PastelItemTags.MobHeads.SLIME_HEADS, PastelBlocks.SLIME_IDOL),
                        hPair(PastelSkullType.SNOW_GOLEM, PastelBlocks.SNOW_GOLEM_IDOL),
                        hPair(PastelItemTags.MobHeads.SPIDER_HEADS, PastelBlocks.SPIDER_IDOL),
                        hPair(PastelSkullType.SQUID, PastelBlocks.SQUID_IDOL),
                        hPair(PastelSkullType.STRAY, PastelBlocks.STRAY_IDOL),
                        hPair(PastelSkullType.STRIDER, PastelBlocks.STRIDER_IDOL),
                        hPair(PastelSkullType.TURTLE, PastelBlocks.TURTLE_IDOL),
                        hPair(PastelSkullType.WITCH, PastelBlocks.WITCH_IDOL),
                        hPair(PastelSkullType.WITHER, PastelBlocks.WITHER_IDOL),
                        hPair(Items.WITHER_SKELETON_SKULL, PastelBlocks.WITHER_SKELETON_IDOL),
                        hPair(PastelItemTags.MobHeads.ZOMBIE_HEADS, PastelBlocks.ZOMBIE_IDOL)
                );

        private static Map.Entry<IngredientStack, DeferredBlock<?>> hPair(PastelSkullType kind, DeferredBlock<?> block) {
            return Map.entry(head(kind), block);
        }

        private static Map.Entry<IngredientStack, DeferredBlock<?>> hPair(ItemLike item, DeferredBlock<?> block) {
            return Map.entry(IngredientStack.ofItems(item.asItem()), block);
        }

        private static Map.Entry<IngredientStack, DeferredBlock<?>> hPair(TagKey<Item> tag, DeferredBlock<?> block) {
            return Map.entry(IngredientStack.ofTag(tag), block);
        }

        private static IngredientStack head(PastelSkullType kind) {
            return IngredientStack.ofItems(PastelBlocks.MOB_HEADS.get(kind).asItem());
        }

        private static IngredientStack stack(ItemLike item) {
            return IngredientStack.ofItems(item.asItem());
        }

        private static IngredientStack stack(TagKey<Item> tag) {
            return IngredientStack.ofTag(tag);
        }


    }

    private static class ComplexRecipes {
        static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
            var rootHelper = rootPrefixHelper(ctx, lookup, PedestalTier.COMPLEX);

            generateVanilla(rootHelper.subPrefix("vanilla"));
            generateRoot(rootHelper);
        }

        private static void generateVanilla(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.ELYTRA))
                            .craftingTime(1200)
                            .tier(PedestalTier.COMPLEX)
                            .color(PastelGemstoneColor.CYAN, 2)
                            .color(PastelGemstoneColor.WHITE, 8)
                            .experience(4.0f)
                            .pattern("CGC")
                            .pattern("MPM")
                            .pattern("MPM")
                            .define('G', PALTAERIA_GEM.asItem())
                            .define('P', PALTAERIA_FRAGMENTS.asItem())
                            .define('M', Items.PHANTOM_MEMBRANE)
                            .define('C', Items.POPPED_CHORUS_FRUIT)
                            .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_PALTAERIA_GEM)
            );

            // "Heavy core is, in fact, another name for my ass" - Azzyy
            // ^ these need to be preserved SOMEHOW
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.HEAVY_CORE))
                            .craftingTime(1200)
                            .tier(PedestalTier.COMPLEX)
                            .color(PastelGemstoneColor.CYAN, 12)
                            .color(PastelGemstoneColor.WHITE, 4)
                            .experience(3.0f)
                            .pattern("MBM")
                            .pattern("MSM")
                            .pattern("MBM")
                            .define('M', PastelBlocks.BASAL_MARBLE.asItem())
                            .define('B', BISMUTH_CRYSTAL.asItem())
                            .define('S', STRATINE_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.HEAVY_CORE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.TRIDENT))
                            .craftingTime(1200)
                            .tier(PedestalTier.COMPLEX)
                            .color(PastelGemstoneColor.YELLOW, 2)
                            .color(PastelGemstoneColor.CYAN, 4)
                            .color(PastelGemstoneColor.WHITE, 4)
                            .experience(2.0f)
                            .pattern(" PP")
                            .pattern(" MP")
                            .pattern("S  ")
                            .define('M', MERMAIDS_GEM.asItem())
                            .define('P', Items.PRISMARINE_SHARD)
                            .define('S', Items.STICK)
                            .requiredAdvancement(PastelAdvancements.Lategame.BUILD_COMPLEX_PEDESTAL_STRUCTURE)
            );
        }

        private static void generateRoot(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PALTAERIA_GEM.asItem()))
                            .craftingTime(400)
                            .complex()
                            .white(4)
                            .experience(0.5f)
                            .pattern("###")
                            .pattern("#X#")
                            .pattern("###")
                            .define('#', PALTAERIA_FRAGMENTS)
                            .define('X', Items.DIAMOND)
                            .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_PALTAERIA_GEM)
                            .disableYieldUpgrades(true)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CELESTIAL_POCKETWATCH.asItem()))
                            .craftingTime(2400)
                            .complex()
                            .magenta(16)
                            .white(4)
                            .experience(4.0f)
                            .pattern("PAP")
                            .pattern("ACA")
                            .pattern("PAP")
                            .define('A', Items.AMETHYST_SHARD)
                            .define('C', PALTAERIA_GEM)
                            .define('P', MAGENTA_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.CELESTIAL_POCKETWATCH)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CONCEALING_OILS.asItem()))
                            .craftingTime(800)
                            .complex()
                            .black(8)
                            .white(8)
                            .experience(2.0f)
                            .pattern(" F ")
                            .pattern("BNB")
                            .pattern(" F ")
                            .define('B', PURE_QUARTZ)
                            .define('F', BISMUTH_FLAKE)
                            .define('N', NIGHTDEW_SPROUT)
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.POISONERS_HANDBOOK)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.CRYSTALLARIEUM))
                            .craftingTime(1200)
                            .complex()
                            .cyan(8)
                            .magenta(16)
                            .white(4)
                            .experience(8.0f)
                            .pattern("ILI")
                            .pattern("CPC")
                            .pattern("BPB")
                            .define('I', BISMUTH_CRYSTAL)
                            .define('C', PastelBlocks.POLISHED_CALCITE)
                            .define('B', PastelBlocks.POLISHED_BASALT)
                            .define('P', PastelItemTags.PIGMENT_BLOCKS)
                            .define('L', Items.BUCKET)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.CRYSTALLARIEUM)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(EVERPROMISE_RIBBON.asItem()))
                            .craftingTime(800)
                            .complex()
                            .cyan(1)
                            .magenta(1)
                            .yellow(1)
                            .white(8)
                            .experience(2.0f)
                            .pattern("PS ")
                            .pattern("PSS")
                            .pattern("MPP")
                            .define('S', Items.STRING)
                            .define('M', PURE_MALACHITE)
                            .define('P', JADEITE_PETALS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.EVERPROMISE_RIBBON_RECIPE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(FANCIFUL_BISMUTH_RING.asItem()))
                            .craftingTime(800)
                            .complex()
                            .white(8)
                            .experience(2.0f)
                            .pattern(" B ")
                            .pattern("B B")
                            .pattern(" B ")
                            .define('B', BISMUTH_CRYSTAL)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Trinkets.FANCIFUL_BISMUTH_RING)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PERTURBED_EYE.asItem(), 12))
                            .craftingTime(2400)
                            .complex()
                            .yellow(8)
                            .white(4)
                            .experience(4.0f)
                            .pattern("EPE")
                            .pattern("PRP") // PRPle guy.....
                            .pattern("EPE")
                            .define('E', Items.ENDER_EYE)
                            .define('R', PastelBlocks.RADIATING_ENDER)
                            .define('P', PALTAERIA_GEM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.PERTURBED_EYE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(STAFF_OF_REMEMBRANCE.asItem()))
                            .craftingTime(2400)
                            .complex()
                            .white(8)
                            .black(2)
                            .experience(4.0f)
                            .pattern(" JM")
                            .pattern(" DJ")
                            .pattern("D  ")
                            .define('M', MOONSTONE_CORE)
                            .define('D', DRAGONBONE_CHUNK)
                            .define('J', JADEITE_PETALS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.STAFF_OF_REMEMBRANCE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_EFFICIENCY2))
                            .craftingTime(6000)
                            .complex()
                            .white(32) // ?!?!?
                            .experience(4.0f)
                            .requires(PastelBlocks.UPGRADE_EFFICIENCY)
                            .requires(PURE_EMERALD)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_EFFICIENCY2)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_SPEED3))
                            .craftingTime(6000)
                            .complex()
                            .white(4)
                            .experience(4.0f)
                            .requires(PastelBlocks.UPGRADE_SPEED2)
                            .requires(PURE_IRON)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_SPEED3)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_YIELD))
                            .craftingTime(3600)
                            .complex()
                            .black(16)
                            .experience(4.0f)
                            .pattern("LPL")
                            .pattern("CPC")
                            .pattern("YYY")
                            .define('Y', PastelBlocks.POLISHED_BASALT)
                            .define('P', PALTAERIA_GEM)
                            .define('C', STRATINE_GEM)
                            .define('L', LIGHT_BLUE_PIGMENT)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_YIELD)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(PastelBlocks.UPGRADE_YIELD2))
                            .craftingTime(6000)
                            .complex()
                            .white(32)
                            .experience(4.0f)
                            .requires(PastelBlocks.UPGRADE_YIELD)
                            .requires(PURE_DIAMOND)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Upgrades.UPGRADE_YIELD2)
            );

            generateTools(pfx);
        }

        // groups that don't actually have a subdirectory, but are useful to group together anyway

        private static void generateTools(PrefixHelper pfx) {
            var lookup = pfx.getLookup();

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, DRAGONRENDING_PICKAXE.get()))
                            .craftingTime(600)
                            .complex()
                            .magenta(4)
                            .yellow(8)
                            .white(4)
                            .experience(2.0f)
                            .pattern("MPM")
                            .pattern(" S ")
                            .pattern(" S ")
                            .define('S', Items.SMOOTH_BASALT)
                            .define('M', DRAGONBONE_CHUNK)
                            .define('P', PastelItemTags.RESPLENDENT_FEATHERS)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.DRAGONRENDING_PICKAXE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, RESONANT_PICKAXE.get()))
                            .craftingTime(600)
                            .complex()
                            .yellow(8)
                            .white(4)
                            .experience(2.0f)
                            .pattern("MPM")
                            .pattern(" S ")
                            .pattern(" S ")
                            .define('S', Items.SMOOTH_BASALT)
                            .define('M', MOONSTONE_SHARD)
                            .define('P', RESONANCE_SHARD)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.RESONANT_PICKAXE)
            );
        }

    }


}
