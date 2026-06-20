package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColorMixes;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.item.Preenchanted;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.blocks.mob_head.PastelSkullType;
import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.compat.create.CreateCompat;
import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.helpers.level.collections.PastelGemstoneColorCollection;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.items.tools.MoltenRodItem;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.builder.ShapedPedestalRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.builder.ShapelessPedestalRecipeBuilder;
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
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;
import oshi.util.tuples.Pair;
import oshi.util.tuples.Triplet;

import java.util.List;
import java.util.Map;
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
                            .key('M', RAW_MALACHITE.get())
                            .key('S', Items.STICK)
                            .key('F', PastelItemTags.RESPLENDENT_FEATHERS)
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
                            .powderInput(PastelGemstoneColor.MAGENTA, 4)
                            .experience(2.0f)
                            .pattern("A A")
                            .pattern("ATA")
                            .pattern("AAA")
                            .key('A', Items.AMETHYST_SHARD)
                            .key('T', Items.GHAST_TEAR)
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.AMETHYST)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CITRINE_BOOTS.get()))
                            .tier(PedestalTier.BASIC)
                            .craftingTime(400)
                            .powderInput(PastelGemstoneColor.YELLOW, 4)
                            .experience(2.0f)
                            .pattern("S S")
                            .pattern("C C")
                            .pattern("C C")
                            .key('S', Items.SUGAR)
                            .key('C', CITRINE_SHARD.get())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectShards.CITRINE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(TOPAZ_LEGGINGS.get()))
                            .tier(PedestalTier.BASIC)
                            .craftingTime(400)
                            .powderInput(PastelGemstoneColor.CYAN, 4)
                            .experience(2.0f)
                            // is that a deepslate in your pants or are you just happy to see me?
                            .pattern("TTT")
                            .pattern("TDT")
                            .pattern("T T")
                            .key('T', TOPAZ_SHARD.get())
                            .key('D', Items.DEEPSLATE)
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
                                        .key('C', carpets.pick(color))
                                        .key('F', Tags.Items.FEATHERS)
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
                            .key('#', BONE_ASH.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
            );
            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.POLISHED_BONE_ASH_PILLAR, 2))
                            .group("bone_ash_blocks")
                            .craftingTime(200)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("#")
                            .pattern("#")
                            .key('#', PastelBlocks.POLISHED_BONE_ASH.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
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
                            .key('W', PastelBlocks.POLISHED_BONE_ASH.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
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
                            .key('W', PastelBlocks.BONE_ASH_BRICKS.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
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
                            .key('#', PastelBlocks.POLISHED_BONE_ASH.asItem())
                            .key('B', DRAGONBONE_CHUNK.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
            );
        }

        private static void generateFoodRecipes(PrefixHelper prefixHelper) {

            // Tarts
            prefixHelper.generateAutoNamedRecipe(
                    tartBase(ASHEN_TART.asItem(), Items.SWEET_BERRIES)
                            .powderInput(PastelGemstoneColor.CYAN, 2)
                            .powderInput(PastelGemstoneColor.YELLOW, 4)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(JARAMEL_TART.asItem(), Items.AIR)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
            );
            prefixHelper.generateAutoNamedRecipe(
                    tartBase(PUFF_TART.asItem(), Items.DRAGON_BREATH)
                            .powderInput(PastelGemstoneColor.CYAN, 2)
                            .powderInput(PastelGemstoneColor.MAGENTA, 2)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(SALTED_JARAMEL_TART.asItem(), Items.GHAST_TEAR)
                            .secret(true)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(WEEPING_TART.asItem(), Items.KELP)
                            .powderInput(PastelGemstoneColor.CYAN, 4)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    tartBase(WHISPY_TART.asItem(), NIGHTDEW_SPROUT.asItem())
                            .powderInput(PastelGemstoneColor.MAGENTA, 4)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
            );

            // Trifles
            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(DEMON_TRIFLE.asItem(), BLOODBOIL_SYRUP.asItem())
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.DEMON_TRIFLE)
            );

            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(JARAMEL_TRIFLE.asItem(), Items.AIR)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(MONSTER_TRIFLE.asItem(), QUITOXIC_POWDER.asItem())
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    trifleBase(SALTED_JARAMEL_TRIFLE.asItem(), Items.GHAST_TEAR)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
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
                            .key('E', LIZARD_MEAT.asItem())
                            .key('A', Items.BEETROOT)
                            .key('M', MYCEYLON.asItem())
                            .key('H', Items.HONEY_BOTTLE)
                            .key('J', infusedBeverageIngredient(InfusedBeverageComponent.MALT_BEER))
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(BAGNUN.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("EBE")
                            .pattern("JMJ")
                            .pattern("AAA")
                            .key('A', CRAWFISH.asItem())
                            .key('B', CLOTTED_CREAM.asItem())
                            .key('J', Tags.Items.MUSHROOMS)
                            .key('M', JADE_WINE.asItem())
                            .key('E', MYCEYLON.asItem())
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
                            .key('A',CRAWFISH.asItem())
                            .key('J', Items.SWEET_BERRIES)
                            .key('E', Tags.Items.MUSHROOMS)
                            .key('B', PRICKLY_BAYLEAF.asItem())
                            .key('M', infusedBeverageIngredient(InfusedBeverageComponent.RUM))
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
                            .key('X', FRESH_CHOCOLATE.asItem())
                            .key('E', AMARANTH_GRAINS.asItem())
                            .key('C', CLOTTED_CREAM.asItem())
                            .key('S', infusedBeverageIngredient(InfusedBeverageComponent.SAWBLADE_HOLLY_LIQUOR))
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
                            .key('H', Items.HONEY_BOTTLE)
                            .key('B', Items.SWEET_BERRIES)
                            .key('M', CLOTTED_CREAM.asItem())
                            .key('C', FRESH_CHOCOLATE.asItem())
                            .key('L', infusedBeverageIngredient(InfusedBeverageComponent.BERRY_LIQUOR))
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CHAUVE_SOURIS_AU_VIN.asItem()))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("EEE")
                            .pattern("APA")
                            .pattern("FVF")
                            .key('V', JADE_WINE.asItem())
                            .key('F', LIZARD_MEAT.asItem())
                            .key('A', GLASS_PEACH.asItem())
                            .key('E', JADEITE_PETALS.asItem())
                            .key('P', FRESH_CHOCOLATE.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.IMPERIAL_COOKBOOK)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(CRAWFISH_COCKTAIL.asItem(), 3))
                            .craftingTime(160)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .pattern("AAA")
                            .pattern("BEJ")
                            .key('A', CRAWFISH.asItem())
                            .key('J', MYCEYLON.asItem())
                            .key('E', INCANDESCENT_ESSENCE.asItem())
                            .key('B', GLASS_PEACH.asItem())
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
                            .key('S', Items.HONEY_BOTTLE)
                            .key('E', AMARANTH_GRAINS.asItem())
                            .key('C', CLOTTED_CREAM.asItem())
                            .key('X', Tags.Items.EGGS)
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
                            .key('A', CRAWFISH.asItem())
                            .key('K', KOI.asItem())
                            .key('F', Tags.Items.FOODS_RAW_FISH)
                            .key('E', MYCEYLON.asItem())
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
                            .key('A', AMARANTH_GRAINS.asItem())
                            .key('H', Items.HONEY_BOTTLE)
                            .key('E', Tags.Items.EGGS)
                            .key('S', Items.SUGAR)
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
                            .key('A', AMARANTH_GRAINS.asItem())
                            .key('C', PastelBlocks.FOUR_LEAF_CLOVER.asItem())
                            .key('M', Tags.Items.BUCKETS_MILK)
                            .key('S', Items.SUGAR)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Food.LUCKY_ROLL)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(MEATLOAF_SANDWICH.asItem(), 4))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.2f)
                            .ingredient(MEATLOAF.asItem())
                            .ingredient(Items.BREAD)
                            .ingredient(CLOTTED_CREAM.asItem())
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
                            .key('A', GERMINATED_JADE_VINE_BULB.asItem())
                            .key('B', PastelBlocks.JADEITE_LOTUS_BULB.asItem())
                            .key('C', PastelBlocks.NEPHRITE_BLOSSOM_BULB.asItem())
                            .key('J', FROSTBITE_ESSENCE.asItem())
                            .key('E', MOONSTRUCK_NECTAR.asItem())
                            .key('R', AQUA_REGIA.asItem())
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
                            .key('Y', MYCEYLON.asItem())
                            .key('A', AMARANTH_GRAINS.asItem())
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
                            .key('V', NECTERED_VIOGNIER.asItem())
                            .key('F', MYCEYLON.asItem())
                            .key('K', INCANDESCENT_ESSENCE.asItem())
                            .key('A', PEACH_JAM.asItem())
                            .key('E', CLOTTED_CREAM.asItem())
                            .key('P', GLASS_PEACH.asItem())
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
                            .key('S', MYCEYLON.asItem())
                            .key('E', AMARANTH_GRAINS.asItem())
                            .key('C', CLOTTED_CREAM.asItem())
                            .key('X', JADEITE_PETALS.asItem())
                            .key('M', JADE_WINE.asItem())
                            .requiredAdvancement(PastelAdvancements.Hidden.CollectCookbooks.MELOCHITES_COOKBOOK_VOL_2)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(SCONE.asItem(), 2))
                            .craftingTime(50)
                            .tier(PedestalTier.BASIC)
                            .experience(1.0f)
                            .ingredient(AMARANTH_GRAINS.asItem())
                            .ingredient(Tags.Items.EGGS)
                            .ingredient(Items.SWEET_BERRIES)
                            .ingredient(CLOTTED_CREAM.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_AMARANTH_BUSHEL)
            );

            prefixHelper.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(SLUSHSLIDE.asItem(), 2))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .experience(0.2f)
                            .ingredient(FRESH_CHOCOLATE.asItem())
                            .ingredient(MYCEYLON.asItem())
                            .ingredient(CLOTTED_CREAM.asItem())
                            .ingredient(infusedBeverageIngredient(InfusedBeverageComponent.SAWBLADE_HOLLY_LIQUOR))
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
                            .key('A', AMARANTH_GRAINS.asItem())
                            .key('C', CLOTTED_CREAM.asItem())
                            .key('M', PastelItemTags.COMMON_MEATS)
                            .key('F', PastelItemTags.WATER_MEATS)
                            .key('L', PastelItemTags.LEAN_MEATS)
                            .key('E', Tags.Items.EGGS)
                            .key('W', PastelItemTags.DRINKABLE_SPIRITS)
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
                            .key('P', PRICKLY_BAYLEAF.asItem())
                            .key('C', CLOTTED_CREAM.asItem())
                            .key('M', PastelItemTags.COMMON_MEATS)
                            .key('F', PastelItemTags.WATER_MEATS)
                            .key('L', PastelItemTags.LEAN_MEATS)
                            .key('W', PastelItemTags.DRINKABLE_SPIRITS)
                            .key('B', Items.BOWL)
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
                            .key('Y', PRICKLY_BAYLEAF.asItem())
                            .key('D', DRAGONBONE_CHUNK.asItem())
                            .key('V', Tags.Items.FOODS_FRUIT)
                            .key('B', Items.BOWL)
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
                                .powderInput(color, 4)
                                .experience(1.0f)
                                .pattern("GPG")
                                .pattern("PPP")
                                .pattern("GPG")
                                .key('P', GEMSTONE_SHARDS.pick(color).value())
                                .key('G', Items.GLASS)
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
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
                            .experience(0.5f)
                            .craftingTime(80)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.RADIANT_GLASS)
                            .pattern(" S ")
                            .pattern("SGS")
                            .pattern(" S ")
                            .key('G', Items.GLASS)
                            .key('S', SHIMMERSTONE_GEM.get())
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
                                .powderInput(color, 2)
                                .experience(1.0f)
                                .pattern(" S ")
                                .pattern("SSS")
                                .pattern("XYX")
                                .key('S', item.value())
                                .key('X', PastelBlocks.POLISHED_BASALT.asItem())
                                .key('Y', PastelBlocks.POLISHED_CALCITE.asItem())
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
                            .key('F', block.asItem())
                            .key('#', ItemTags.PLANKS)
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
                            .key('G', RESPLENDENT_FEATHER.asItem())
                            .requiredAdvancement(unlock)
                            .ignoreYieldUpgrades(true)
            );

            pfx.generateRecipe(
                    "feathers_from_block",
                    new ShapelessPedestalRecipeBuilder(new ItemStack(RESPLENDENT_FEATHER.asItem(), 4))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .ingredient(block.asItem())
                            .requiredAdvancement(unlock)
                            .ignoreYieldUpgrades(true)
            );

            pfx.generateRecipe(
                    "carpet_from_blocks",
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.RESPLENDENT_CARPET.asItem(), 4))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("BB")
                            .key('B', block.asItem())
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
                            .key('F', RESPLENDENT_FEATHER.asItem())
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
                            .key('F', RESPLENDENT_FEATHER.asItem())
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
                            .powderInput(PastelGemstoneColor.CYAN, 1)
                            .experience(0.5f)
                            .pattern("  B")
                            .pattern(" BS")
                            .pattern("B M")
                            .key('B', Items.SMOOTH_BASALT)
                            .key('S', Items.STRING)
                            .key('M', MERMAIDS_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.LAGOON_ROD)
            );
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, LUCKY_PICKAXE.get()))
                            .craftingTime(600)
                            .tier(PedestalTier.BASIC)
                            .powderInput(PastelGemstoneColor.YELLOW, 8)
                            .experience(2.0f)
                            .pattern("CGC")
                            .pattern(" S ")
                            .pattern(" S ")
                            .key('S', Items.SMOOTH_BASALT)
                            .key('C', CITRINE_SHARD.asItem())
                            .key('G', SHIMMERSTONE_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.LUCKY_PICKAXE)
            );
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, MOLTEN_ROD.get()))
                            .craftingTime(800)
                            .tier(PedestalTier.BASIC)
                            .powderInput(PastelGemstoneColor.MAGENTA, 2)
                            .powderInput(PastelGemstoneColor.YELLOW, 4)
                            .experience(4.0f)
                            .pattern(" PB")
                            .pattern("PBS") // pbs kids! with shows such as sid the science kid!
                            .pattern("B S")
                            .key('P', ORANGE_PIGMENT.asItem())
                            .key('B', Items.BLAZE_ROD)
                            .key('S', Items.STRING)
                            .requiredAdvancement(MoltenRodItem.UNLOCK_IDENTIFIER)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, RAZOR_FALCHION.get()))
                            .craftingTime(600)
                            .tier(PedestalTier.BASIC)
                            .powderInput(PastelGemstoneColor.MAGENTA, 8)
                            .experience(2.0f)
                            // The pattern was padded in the json but it didnt NEED to be so im just... not
                            .pattern("A")
                            .pattern("A")
                            .pattern("S")
                            .key('S', Items.CALCITE)
                            .key('A', Items.AMETHYST_SHARD)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.RAZOR_FALCHION)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(Preenchanted.getDefaultEnchantedStack(lookup, TENDER_PICKAXE.get()))
                            .craftingTime(600)
                            .tier(PedestalTier.BASIC)
                            .powderInput(PastelGemstoneColor.CYAN, 8)
                            .experience(2.0f)
                            .pattern("TMT")
                            .pattern(" S ")
                            .pattern(" S ")
                            .key('S', Items.SMOOTH_BASALT)
                            .key('T', TOPAZ_SHARD.asItem())
                            .key('M', MERMAIDS_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Equipment.TENDER_PICKAXE)
            );
        }

        private static void generateVanillaRecipes(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.BUNDLE))
                            .craftingTime(80)
                            .tier(PedestalTier.BASIC)
                            .powderInput(PastelGemstoneColor.CYAN, 4)
                            .experience(2.0f)
                            .pattern("S")
                            .pattern("L")
                            .key('S', Items.STRING)
                            .key('L', Items.LEATHER)
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
                            .key('S', Items.STRING)
                            .key('A', Items.SPIDER_EYE)
                            .key('Q', QUITOXIC_POWDER.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_QUITOXIC_REEDS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(Items.PACKED_MUD))
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .ingredient(Items.MUD)
                            .ingredient(PastelBlocks.AMARANTH_BUSHEL.asItem())
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
                            .key('A', Items.ARROW)
                            .key('S', SHIMMERSTONE_GEM.asItem())
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
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
                            .experience(2.0f)
                            .pattern("QSQ")
                            .pattern("SQS")
                            .pattern("QSQ")
                            .key('Q', QUITOXIC_POWDER.asItem())
                            // could be replaced with the sand tag?
                            .key('S', Items.SAND)
                            .requiredAdvancement(PastelAdvancements.COLLECT_QUITOXIC_REEDS)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(Items.WRITABLE_BOOK))
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.1f)
                            .ingredient(Items.BOOK)
                            .ingredient(PastelItemTags.PIGMENTS)
                            .ingredient(Items.FEATHER)
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
                            .key('S', PastelBlocks.WEEPING_GALA_SLAB.asItem())
                            .key('P', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
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
                            .key('S', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
                            .key('G', Items.REDSTONE)
                            .key('X', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
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
                            .key('S', SHIMMERSTONE_GEM.asItem())
                            .key('P', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
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
                            .key('P', PastelBlocks.WEEPING_GALA_PLANKS.asItem())
                            .key('S', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
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
                            .key('#', PastelItemTags.WEEPING_GALA_LOGS)
                            .requiredAdvancement(unlock)
            );
        }

        // TODO
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
                            .key('O', Items.OBSIDIAN)
                            .key('E', Items.ENDER_EYE)
                            .key('T', PastelBlocks.RADIATING_ENDER)
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
                            .key('R', Items.REDSTONE)
                            .key('S', STORM_STONE)
                            .key('B', PastelBlocks.POLISHED_BASALT)
                            .key('C', Items.COPPER_INGOT)
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
                            .key('R', Items.REDSTONE)
                            .key('T', TOPAZ_SHARD)
                            .key('B', PastelBlocks.POLISHED_CALCITE)
                            .key('C', Items.COPPER_INGOT)
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
                            .key('S', SHIMMERSTONE_GEM)
                            .key('F', Items.FERMENTED_SPIDER_EYE)
                            .key('C', Items.BLAZE_POWDER)
                            .key('B', Items.HONEY_BOTTLE)
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
                            .key('C', Items.COPPER_INGOT)
                            .key('B', Items.COPPER_BLOCK)
                            .key('T', PastelBlocks.POLISHED_BASALT)
                            .key('I', Items.IRON_INGOT)
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
                                .key('A', first)
                                .key('B', second)
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
                            .key('E', PastelBlocks.RADIATING_ENDER)
                            .key('G', Items.GLASS)
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
                            .key('G', Items.GOLD_INGOT)
                            .key('M', Items.MELON_SEEDS)
                            .key('V', VEGETAL)
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
                            .key('L', Items.LEATHER)
                            .key('S', Items.STICK)
                            .key('C', SHIMMERSTONE_GEM)
                            .key('F', Items.PHANTOM_MEMBRANE)
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
                            .key('L', Items.LEATHER)
                            .key('S', Items.STICK)
                            .key('C', SHIMMERSTONE_GEM)
                            .key('F', Items.PHANTOM_MEMBRANE)
                            .key('G', Items.GLOW_INK_SAC)
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
                            .key('P', Items.PAPER)
                            .key('W', ItemTags.PLANKS)
                            .key('G', Items.GOLD_INGOT)
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
                            .key('D', Items.DIRT)
                            .key('C', Items.MUD)
                            .key('B', Items.BUCKET)
                            .key('M', MERMAIDS_GEM)
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
                            .key('B', Items.LAVA_BUCKET)
                            .key('M', Items.MAGMA_BLOCK)
                            .key('C', Items.MAGMA_CREAM)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.LAVA_SPONGE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(PastelBlocks.PEDESTAL_ALL_BASIC))
                            .craftingTime(200)
                            .basic()
                            .experience(4.0f)
                            .pattern("CMY")
                            .pattern("RRR")
                            .key('C', TOPAZ_SHARD)
                            .key('M', Items.AMETHYST_SHARD)
                            .key('Y', CITRINE_SHARD)
                            .key('R', PastelBlocks.POLISHED_CALCITE)
                            .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.CMY_PEDESTAL)
                            .ignoreYieldUpgrades(true)
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
                            .key('P', Items.PAPER)
                            .key('S', SHIMMERSTONE_GEM)
                            .key('G', Items.GUNPOWDER)
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
                            .key('S', Items.GLOW_INK_SAC)
                            .key('R', Items.LEATHER)
                            .key('G', Items.GOLD_NUGGET)
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
                            .key('C', CITRINE_SHARD)
                            .key('R', Items.REDSTONE)
                            .key('T', Items.REDSTONE_TORCH)
                            .key('S', Items.STONE)
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
                            .key('A', Items.AMETHYST_SHARD)
                            .key('R', Items.REDSTONE)
                            .key('T', Items.REDSTONE_TORCH)
                            .key('I', Items.COPPER_INGOT)
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
                            .key('R', Items.REDSTONE)
                            // replace with tag?
                            .key('S', Items.SAND)
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
                            .key('P', ItemTags.PLANKS)
                            .key('S', ItemTags.WOODEN_SLABS)
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
                            .key('C', PastelItemTags.COLORED_PLANKS)
                            .key('P', PastelItemTags.PIGMENTS)
                            .key('I', Items.IRON_INGOT)
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
                            .powderInput(PastelGemstoneColor.YELLOW, 1)
                            .experience(0.5f)
                            .pattern("TST")
                            .pattern("SSS")
                            .pattern("TST")
                            .key('S', SHIMMERSTONE_GEM.asItem())
                            .key('T', stone)
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
                    .key('A', AMARANTH_GRAINS.asItem())
                    .key('J', JARAMEL.asItem())
                    .key('E', Tags.Items.EGGS)
                    .key('M', Tags.Items.BUCKETS_MILK)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.TARTS);

            return topping == Items.AIR ? base : base.key('T', topping);
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
                    .key('A', AMARANTH_GRAINS.asItem())
                    .key('J', JARAMEL.asItem())
                    .key('B', Items.BONE_MEAL)
                    .key('E', Tags.Items.EGGS)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.TRIFLES);

            return topping == Items.AIR ? base : base.key('T', topping);
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
                            .key('Y', MYCEYLON.asItem())
                            .key('M', Tags.Items.BUCKETS_MILK)
                            .key('S', Items.SUGAR)
                            .key('P', thingie)
                            .key('A', AMARANTH_GRAINS.asItem())
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
                            .key('E', Tags.Items.MUSHROOMS)
                            .key('J', CLOTTED_CREAM.asItem())
                            .key('A', Items.POTATO)
                            .key('M', infusedBeverageIngredient(InfusedBeverageComponent.BEER))
                            .key('H', Items.COOKED_RABBIT)
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
                            .ignoreYieldUpgrades(true)
                            .experience(0.0f)
                            .requiredAdvancement(unlock)
                            .pattern("AAA")
                            .pattern("AAA")
                            .pattern("AAA")
                            .key('A', unpacked.get())
            );

            pfx.generateRecipe(
                    unpackName,
                    new ShapelessPedestalRecipeBuilder(new ItemStack(unpacked.get(), 9))
                            .group(group)
                            .craftingTime(20)
                            .tier(PedestalTier.BASIC)
                            .ignoreYieldUpgrades(true)
                            .experience(0.0f)
                            .requiredAdvancement(unlock)
                            .ingredient(packed.asItem())
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
                            .powderInput(color, 4)
                            .experience(2.0f)
                            .pattern("BBB")
                            .pattern("CQC")
                            .pattern("SSS")
                            .key('B', Items.GLASS)
                            .key('C', GEMSTONE_SHARDS.pick(color).value())
                            .key('Q', Items.QUARTZ)
                            .key('S', ItemTags.WOODEN_SLABS)
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
                                .powderInput(color, 4)
                                .experience(0.3f)
                                .pattern("TTT")
                                .pattern("BSB")
                                .pattern("TTT")
                                .key('T', baseBlock.asItem())
                                .key('B', PastelBlocks.GEMSTONE_BLOCKS.pick(color).asItem())
                                .key('S', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
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
                                .powderInput(color, 4)
                                .experience(1.0f)
                                .requiredAdvancement(unlock)
                                .pattern("WWW")
                                .pattern("WXW")
                                .pattern("WWW")
                                .key('W', baseBlock.asItem())
                                .key('X', cluster.asItem())
                );

                pfx.generateRecipe(
                        result.getId().getPath() + "_from_shards",
                        new ShapedPedestalRecipeBuilder(new ItemStack(result.asItem(), 1))
                                .group("gemstone_chiseled_blocks")
                                .craftingTime(80)
                                .tier(tier)
                                .powderInput(color, 1)
                                .experience(1.0f)
                                .requiredAdvancement(unlock)
                                .pattern("WXW")
                                .pattern("XXX")
                                .pattern("WXW")
                                .key('W', baseBlock.asItem())
                                .key('X', shard.value())
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
                            .key('R', Items.REDSTONE)
                            .key('S', SHIMMERSTONE_GEM.get())
                            .key('P', PigmentItem.byColor(color))
                            .replacePowderInputsWith(colorMix)
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
                            .powderInput(color, 1)
                            .experience(1.0f)
                            .pattern("AA")
                            .pattern("GB")
                            .key('G', GEMSTONE_SHARDS.pick(color).value())
                            .key('A', MALACHITE_GLASS_ARROW.get())
                            .key('B', BISMUTH_FLAKE.get())
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
                            .replacePowderInputsWith(mix)
                            .experience(1.0f)
                            .requiredAdvancement(unlock)
                            .pattern("DDD")
                            .pattern("VSV")
                            .pattern("DDD")
                            .key('D', dyeItem)
                            .key('V', VEGETAL.get())
                            .key('S', ItemTags.SAPLINGS)
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
                            .key('X', petals.asItem())
                            .requiredAdvancement(unlock)
                            .ignoreYieldUpgrades(true)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(carpet.asItem(), 3))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .pattern("XX")
                            .key('X', block.asItem())
                            .requiredAdvancement(unlock)
            );

            pfx.generateRecipe(
                    petals.getId().getPath() + "_from_petal_block",
                    new ShapelessPedestalRecipeBuilder(new ItemStack(JADEITE_PETALS.asItem(), 4))
                            .group(group)
                            .craftingTime(40)
                            .tier(PedestalTier.BASIC)
                            .experience(0.0f)
                            .ingredient(block.asItem())
                            .requiredAdvancement(unlock)
                            .ignoreYieldUpgrades(true)
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
                            .key('S', slab.asItem())
                            .key('P', planks.asItem())
                            .key('I', ingot)
                            .key('A', Items.PAPER)
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
                            .key('S', stem.asItem())
                            .key('G', gills.asItem())
                            .key('I', ingot)
                            .key('X', SHIMMERSTONE_GEM.asItem())
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
                            .key('S', slab.asItem())
                            .key('G', gills.asItem())
                            .key('C', block.asItem())
                            .key('I', ingot)
                            .key('H', SHIMMERSTONE_GEM.asItem())
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
                            .key('S', stem.asItem())
                            .key('G', gills.asItem())
                            .key('X', PastelBlocks.SHIMMERSTONE_BLOCK.asItem())
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
                            .key('S', stems)
                            .key('C', block.asItem())
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
                            .key('G', glass.asItem())
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
                            .key('W', baseBlock.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
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
                            .key('W', baseBlock.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
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
                            .key('W', baseBlock.asItem())
                            .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                            .ignoreYieldUpgrades(true)
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
                                .replacePowderInputsWith(mix)
                                .experience(0.5f)
                                .pattern("PPP")
                                .pattern("PSP")
                                .pattern("PPP")
                                .key('P', pigment.asItem())
                                .key('S', Items.SPORE_BLOSSOM)
                                .requiredAdvancement(unlock)
                );
            });
        }

        private static void generateVanilla(PrefixHelper pfx) {
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.BELL, 1))
                            .craftingTime(120)
                            .tier(PedestalTier.SIMPLE)
                            .powderInput(PastelGemstoneColor.YELLOW, 4)
                            .experience(1.0f)
                            .pattern("SSS")
                            .pattern("GGG")
                            .pattern("G G")
                            .key('G', Items.GOLD_INGOT)
                            .key('S', Items.STICK)
                            .requiredAdvancement(PastelAdvancements.BUILD_BASIC_PEDESTAL_STRUCTURE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapelessPedestalRecipeBuilder(new ItemStack(Items.NAME_TAG, 1))
                            .craftingTime(120)
                            .tier(PedestalTier.SIMPLE)
                            .powderInput(PastelGemstoneColor.MAGENTA, 2)
                            .powderInput(PastelGemstoneColor.CYAN, 2)
                            .experience(1.0f)
                            .ingredient(Items.STRING)
                            .ingredient(Items.PAPER)
                            .ingredient(Items.PAPER)
                            .ingredient(PastelBlocks.FOUR_LEAF_CLOVER.asItem())
                            .requiredAdvancement(PastelAdvancements.COLLECT_FOUR_LEAF_CLOVER)
            );
        }

        // TODO
        private static void generateRoot(PrefixHelper pfx) {

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
                                .powderInput(color, 1)
                                .experience(1.0f)
                                .pattern(" Y ")
                                .pattern("SGS")
                                .pattern("PXP")
                                .key('X', PastelBlocks.POLISHED_BASALT.asItem())
                                .key('Y', PastelBlocks.POLISHED_CALCITE.asItem())
                                .key('S', shard.value())
                                .key('G', Items.STRING)
                                .key('P', SHIMMERSTONE_GEM.asItem())
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
                                .replacePowderInputsWith(mix)
                                .experience(1.0f)
                                .pattern("PQP")
                                .pattern("QPQ")
                                .pattern("PQP")
                                .key('P', pigment.asItem())
                                .key('Q', QUITOXIC_POWDER.asItem())
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
                                .key('H', head)
                                .key('V', VEGETAL)
                                .key('Q', QUITOXIC_POWDER)
                                .key('S', STARDUST)
                                .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.IDOLS)
                );
            });
        }

        // TODO
        private static void generatePastelNetwork(PrefixHelper pfx) {

        }

        private static void generateSemiPermeableGlasses(PrefixHelper pfx) {
            pfx.generateRecipe(
                    "glass",
                    sharedSemiPermeableGlass(Items.GLASS, Items.GLASS, PastelBlocks.SEMI_PERMEABLE_GLASS)
                            .powderInput(PastelGemstoneColor.CYAN, 1)
                            .powderInput(PastelGemstoneColor.MAGENTA, 1)
                            .powderInput(PastelGemstoneColor.YELLOW, 1)
            );

            pfx.generateRecipe(
                    "tinted",
                    sharedSemiPermeableGlass(ONYX_SHARD.asItem(), Items.TINTED_GLASS, PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS)
                            .powderInput(PastelGemstoneColor.BLACK, 2)
            );

            pfx.generateRecipe(
                    "radiant",
                    sharedSemiPermeableGlass(SHIMMERSTONE_GEM.asItem(), Items.GLASS, PastelBlocks.RADIANT_SEMI_PERMEABLE_GLASS)
                            .powderInput(PastelGemstoneColor.MAGENTA, 4)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)

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
                               .powderInput(color, 8)
                               .tier(tier)
                               .requiredAdvancement(unlock)
               );
            });
        }

        // TODO
        private static void generateTrinkets(PrefixHelper pfx) {

        }

        // TODO
        private static void generateRoot(PrefixHelper pfx) {

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
                    .key('S', center)
                    .key('P', PURPLE_PIGMENT.asItem())
                    .key('G', glass)
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
                            .powderInput(PastelGemstoneColor.CYAN, 2)
                            .powderInput(PastelGemstoneColor.WHITE, 8)
                            .experience(4.0f)
                            .pattern("CGC")
                            .pattern("MPM")
                            .pattern("MPM")
                            .key('G', PALTAERIA_GEM.asItem())
                            .key('P', PALTAERIA_FRAGMENTS.asItem())
                            .key('M', Items.PHANTOM_MEMBRANE)
                            .key('C', Items.POPPED_CHORUS_FRUIT)
                            .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_PALTAERIA_GEM)
            );

            // "Heavy core is, in fact, another name for my ass" - Azzyy
            // ^ these need to be preserved SOMEHOW
            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.HEAVY_CORE))
                            .craftingTime(1200)
                            .tier(PedestalTier.COMPLEX)
                            .powderInput(PastelGemstoneColor.CYAN, 12)
                            .powderInput(PastelGemstoneColor.WHITE, 4)
                            .experience(3.0f)
                            .pattern("MBM")
                            .pattern("MSM")
                            .pattern("MBM")
                            .key('M', PastelBlocks.BASAL_MARBLE.asItem())
                            .key('B', BISMUTH_CRYSTAL.asItem())
                            .key('S', STRATINE_GEM.asItem())
                            .requiredAdvancement(PastelAdvancements.Unlocks.Items.HEAVY_CORE)
            );

            pfx.generateAutoNamedRecipe(
                    new ShapedPedestalRecipeBuilder(new ItemStack(Items.TRIDENT))
                            .craftingTime(1200)
                            .tier(PedestalTier.COMPLEX)
                            .powderInput(PastelGemstoneColor.YELLOW, 2)
                            .powderInput(PastelGemstoneColor.CYAN, 4)
                            .powderInput(PastelGemstoneColor.WHITE, 4)
                            .experience(2.0f)
                            .pattern(" PP")
                            .pattern(" MP")
                            .pattern("S  ")
                            .key('M', MERMAIDS_GEM.asItem())
                            .key('P', Items.PRISMARINE_SHARD)
                            .key('S', Items.STICK)
                            .requiredAdvancement(PastelAdvancements.Lategame.BUILD_COMPLEX_PEDESTAL_STRUCTURE)
            );
        }

        // TODO
        private static void generateRoot(PrefixHelper pfx) {

        }


    }


}
