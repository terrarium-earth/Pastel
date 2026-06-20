package earth.terrarium.pastel.data.recipe;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColorMixes;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.api.recipe.IngredientStack;
import earth.terrarium.pastel.components.InfusedBeverageComponent;
import earth.terrarium.pastel.helpers.level.collections.PastelGemstoneColorCollection;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.builder.PedestalRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.builder.ShapedPedestalRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.builder.ShapelessPedestalRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.dynamic.StarCandyRecipe;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static earth.terrarium.pastel.registries.PastelItems.*;

public class PastelPedestalRecipes {
    private static void generateRecipe(RecipeOutput ctx, String id, Recipe<?> recipe) {
        ctx.accept(PastelCommon.locate(id), recipe, null);
    }

    private static void generateRecipeFromBuilder(RecipeOutput ctx, String id, RecipeBuilder builder) {
        builder.save(ctx, PastelCommon.locate(id));
    }

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
                        new Pair<>(unsafeGemstoneColorFromInkColor(entry.getKey()), (int)(entry.getValue() * total))
                ).collect(Collectors.toMap(Pair::getFirst, Pair::getSecond))
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

    private static void generatePedestalRecipeWithSavedTier(
            RecipeOutput ctx,
            String id,
            PedestalTier tier,
            PedestalRecipeBuilder<?> builder
    ) {
        String tierId = tierIdOf(tier);
        generateRecipeFromBuilder(
                ctx,
                "pedestal/" + tierId + "/" + id,
                builder
        );
    }

    private static void generateDynamicPedestalRecipe(
            RecipeOutput ctx,
            String id,
            PedestalRecipe recipe
    ) {
        generateDynamicPedestalRecipeWithSavedTier(ctx, id, recipe.getTier(), recipe);
    }

    private static void generateDynamicPedestalRecipeWithSavedTier(
            RecipeOutput ctx,
            String id,
            PedestalTier tier,
            PedestalRecipe recipe
    ) {
        generateRecipe(
                ctx,
                "pedestal/" + tierIdOf(tier) + "/" + id,
                recipe
        );
    }

    private static void generatePedestalRecipe(
            RecipeOutput ctx,
            String id,
            PedestalRecipeBuilder<?> builder
    ) {
        Objects.requireNonNull(builder.getTier(), "tier must not be null when saving recipes!");
        generatePedestalRecipeWithSavedTier(ctx, id, builder.getTier(), builder);
    }

    public static void generate(RecipeOutput ctx) {
        BasicRecipes.generate(ctx);
        SimpleRecipes.generate(ctx);
        AdvancedRecipes.generate(ctx);
        ComplexRecipes.generate(ctx);
    }


    private static PrefixHelper rootPrefixHelper(RecipeOutput ctx, PedestalTier tier) {
        String tierId = tierIdOf(tier);
        return new PrefixHelper(ctx, "pedestal/" + tierId);
    }

    private static class BasicRecipes {
        static void generate(RecipeOutput ctx) {
            var rootHelper = rootPrefixHelper(ctx, PedestalTier.BASIC);

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
            generateJadeiteRecipes(rootHelper.subPrefix("jedeite"));
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

            // NOTE: Any integration recipes aren't datagenned due to me not knowing how to add the conditions field
            // These have been moved to `recipe/mod_integration/(mod)/pedestal/compacting`

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

            // TODO: everything else LOL
            prefixHelper.generateDynamicRecipe("star_candy", new StarCandyRecipe());
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

        // TODO
        private static void generateJadeiteRecipes(PrefixHelper pfx) {

        }

        // TODO
        private static void generateJadeVinesRecipes(PrefixHelper pfx) {

        }

        // TODO
        private static void generateNoxwoodRecipes(PrefixHelper pfx) {

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

        // TODO
        private static void generateResplendentRecipes(PrefixHelper pfx) {

        }

        private static void generateRunes(PrefixHelper pfx) {
            generateRunesGroup(pfx, PastelBlocks.POLISHED_BASALT, PastelBlocks.GEMSTONE_CHISELED_BASALTS);
            generateRunesGroup(pfx, PastelBlocks.POLISHED_CALCITE, PastelBlocks.GEMSTONE_CHISELED_CALCITES);
        }

        private static void generateSaplings(PrefixHelper pfx) {
            PastelInkColorCollection.VALUES.forEach(color -> generateSapling(pfx, color));
        }

        // TODO
        private static void generateShimmerstoneLightRecipes(PrefixHelper pfx) {

        }

        // TODO
        private static void generateToolRecipes(PrefixHelper pfx) {

        }

        // TODO
        private static void generateVanillaRecipes(PrefixHelper pfx) {

        }

        // TODO
        private static void generateWeepingGalaRecipes(PrefixHelper pfx) {

        }

        // TODO
        private static void generateRootRecipes(PrefixHelper pfx) {

        }

        // basic generation utilities

        private static void generateBasicRecipe(RecipeOutput ctx, String id, PedestalRecipeBuilder<?> builder) {
            generatePedestalRecipeWithSavedTier(
                    ctx,
                    id,
                    PedestalTier.BASIC,
                    builder
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
        static void generate(RecipeOutput ctx) {
            var rootHelper = rootPrefixHelper(ctx, PedestalTier.SIMPLE);

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
        static void generate(RecipeOutput ctx) {
            var rootHelper = rootPrefixHelper(ctx, PedestalTier.ADVANCED);

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

        // TODO
        private static void generateIdols(PrefixHelper pfx) {

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

    }

    private static class ComplexRecipes {
        static void generate(RecipeOutput ctx) {
            var rootHelper = rootPrefixHelper(ctx, PedestalTier.COMPLEX);

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
