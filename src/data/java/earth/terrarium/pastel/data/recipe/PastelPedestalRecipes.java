package earth.terrarium.pastel.data.recipe;

import com.mojang.datafixers.util.Pair;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColorMixes;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.GemstoneColor;
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
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
        generateRecipe(
                ctx,
                "pedestal/" + tierIdOf(recipe.getTier()) + "/" + id,
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


    private static class BasicRecipes {
        static void generate(RecipeOutput ctx) {
            generateArrowRecipes(ctx);
            generateColoredLampRecipes(ctx);
            generateCompactingRecipes(ctx);
            generateCrystalArmorRecipes(ctx);
            generateCushionRecipes(ctx);
            generateDetectorRecipes(ctx);
            generateDragonboneRecipes(ctx);
            generateFoodRecipes(ctx);
            generateGemstoneLightRecipes(ctx);
            generateBasicGlasses(ctx);
            generateJadeiteRecipes(ctx);
            generateJadeVinesRecipes(ctx);
            generateNoxwoodRecipes(ctx);
            generatePigmentBlocks(ctx);
            generatePylons(ctx);
            generateResplendentRecipes(ctx);
            generateRunes(ctx);
            generateSaplings(ctx);
            generateShimmerstoneLightRecipes(ctx);
            generateToolRecipes(ctx);
            generateVanillaRecipes(ctx);
            generateWeepingGalaRecipes(ctx);
            generateRootRecipes(ctx);
        }

        // basic generation groups

        private static void generateArrowRecipes(RecipeOutput ctx) {
            generatePedestalRecipe(
                    ctx,
                    "arrows/malachite",
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
                generateGemstoneArrowRecipe(ctx, color);
            }
        }

        private static void generateColoredLampRecipes(RecipeOutput ctx) {
            InkColors.BUILTIN_COLORS.forEach(color -> generateColoredLamp(ctx, color));
        }

        private static void generateCompactingRecipes(RecipeOutput ctx) {

            for (PastelGemstoneColor color : PastelGemstoneColor.values()) {
                var unpacked = GEMSTONE_POWDERS.pick(color);
                var packed = PastelBlocks.GEMSTONE_POWDER_BLOCKS.pick(color);
                var unlock = BASE_SHARD_UNLOCKS.pick(color);
                generateCompactingPair(ctx, unlock, unpacked, packed);
            }

            // NOTE: Any integration recipes aren't datagenned due to me not knowing how to add the conditions field
            // These have been moved to `recipe/mod_integration/(mod)/pedestal/compacting`

            generateCompactingPair(ctx, PastelAdvancements.Midgame.COLLECT_AZURITE, PURE_AZURITE, PastelBlocks.AZURITE_BLOCK);
            generateCompactingPair(ctx, "bedrock_dust_block_uncrafting", PastelAdvancements.Midgame.BREAK_DECAYED_BEDROCK, BEDROCK_DUST, PastelBlocks.BEDROCK_DUST_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_BISMUTH_CRYSTAL, BISMUTH_CRYSTAL, PastelBlocks.BISMUTH_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.GROW_BLOODSTONE_IN_CRYSTALLARIEUM, PURE_BLOODSTONE, PastelBlocks.BLOODSTONE_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.GROW_MALACHITE_IN_CRYSTALLARIEUM, PURE_MALACHITE, PastelBlocks.MALACHITE_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Midgame.COLLECT_NEOLITH, NEOLITH, PastelBlocks.NEOLITH_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.CARRY_TOO_MANY_LOW_GRAVITY_BLOCKS, PALTAERIA_FRAGMENTS, PastelBlocks.PALTAERIA_FLOATBLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_COAL, PastelBlocks.PURE_COAL_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_COPPER, PastelBlocks.PURE_COPPER_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_DIAMOND, PastelBlocks.PURE_DIAMOND_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_ECHO, PastelBlocks.PURE_ECHO_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_EMERALD, PastelBlocks.PURE_EMERALD_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_GLOWSTONE, PastelBlocks.PURE_GLOWSTONE_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_GOLD, PastelBlocks.PURE_GOLD_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_IRON, PastelBlocks.PURE_IRON_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_LAPIS, PastelBlocks.PURE_LAPIS_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_NETHERITE_SCRAP, PastelBlocks.PURE_NETHERITE_SCRAP_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_PRISMARINE, PastelBlocks.PURE_PRISMARINE_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_QUARTZ, PastelBlocks.PURE_QUARTZ_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE, PURE_REDSTONE, PastelBlocks.PURE_REDSTONE_BLOCK);
            // for some reason this one AND ONLY THIS ONE is plural
            generateCompactingPair(ctx, "shimmerstone_gems_from_shimmerstone_block", PastelAdvancements.COLLECT_SHIMMERSTONE, SHIMMERSTONE_GEM, PastelBlocks.SHIMMERSTONE_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Hidden.COLLECT_STARDUST, STARDUST, PastelBlocks.STARDUST_BLOCK);
            generateCompactingPair(ctx, PastelAdvancements.Midgame.CARRY_TOO_MANY_HEAVY_GRAVITY_BLOCKS, STRATINE_FRAGMENTS, PastelBlocks.STRATINE_FLOATBLOCK);
            generateCompactingPair(ctx, PastelAdvancements.COLLECT_VEGETAL, VEGETAL, PastelBlocks.VEGETAL_BLOCK);
        }

        private static void generateCrystalArmorRecipes(RecipeOutput ctx) {
            generatePedestalRecipe(
                    ctx,
                    "crystal_armor/amethyst_chestplate",
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

            generatePedestalRecipe(
                    ctx,
                    "crystal_armor/citrine_boots",
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

            generatePedestalRecipe(
                    ctx,
                    "crystal_armor/topaz_leggings",
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

        private static void generateCushionRecipes(RecipeOutput ctx) {
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
                        generatePedestalRecipe(
                                ctx,
                                "cushions/" + cushion.getId().getPath(),
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

        private static void generateDetectorRecipes(RecipeOutput ctx) {
            generateDetectorRecipe(ctx, PastelGemstoneColor.CYAN, PastelAdvancements.Unlocks.Redstone.ITEM_DETECTOR, PastelBlocks.ITEM_DETECTOR);
            generateDetectorRecipe(ctx, PastelGemstoneColor.MAGENTA, PastelAdvancements.Unlocks.Redstone.BLOCK_LIGHT_DETECTOR, PastelBlocks.BLOCK_LIGHT_DETECTOR);
            generateDetectorRecipe(ctx, PastelGemstoneColor.YELLOW, PastelAdvancements.Unlocks.Redstone.WEATHER_DETECTOR, PastelBlocks.WEATHER_DETECTOR);
            generateDetectorRecipe(ctx, PastelGemstoneColor.BLACK, PastelAdvancements.Unlocks.Redstone.PLAYER_DETECTOR, PastelBlocks.PLAYER_DETECTOR);
            generateDetectorRecipe(ctx, PastelGemstoneColor.WHITE, PastelAdvancements.Unlocks.Redstone.CREATURE_DETECTOR, PastelBlocks.CREATURE_DETECTOR);
        }

        private static void generateDragonboneRecipes(RecipeOutput ctx) {
            // polished bone ash
            generateBasicRecipe(
                    ctx,
                    "dragonbone/polished_bone_ash",
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
            generateBasicRecipe(
                    ctx,
                    "dragonbone/polished_bone_ash_pillar",
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
            generateDragonboneSlab(ctx, PastelBlocks.POLISHED_BONE_ASH_SLAB, PastelBlocks.POLISHED_BONE_ASH);
            generateDragonboneStairs(ctx, PastelBlocks.POLISHED_BONE_ASH_STAIRS, PastelBlocks.POLISHED_BONE_ASH);
            generateDragonboneWall(ctx, PastelBlocks.POLISHED_BONE_ASH_WALL, PastelBlocks.POLISHED_BONE_ASH);

            // bone ash bricks
            generateBasicRecipe(
                    ctx,
                    "dragonbone/bone_ash_bricks",
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
            generateDragonboneSlab(ctx, PastelBlocks.BONE_ASH_BRICK_SLAB, PastelBlocks.BONE_ASH_BRICKS);
            generateDragonboneStairs(ctx, PastelBlocks.BONE_ASH_BRICK_STAIRS, PastelBlocks.BONE_ASH_BRICKS);
            generateDragonboneWall(ctx, PastelBlocks.BONE_ASH_BRICK_WALL, PastelBlocks.BONE_ASH_BRICKS);

            // bone ash tiles
            generateBasicRecipe(
                    ctx,
                    "dragonbone/bone_ash_tiles",
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
            generateDragonboneSlab(ctx, PastelBlocks.BONE_ASH_TILE_SLAB, PastelBlocks.BONE_ASH_TILES);
            generateDragonboneStairs(ctx, PastelBlocks.BONE_ASH_TILE_STAIRS, PastelBlocks.BONE_ASH_TILES);
            generateDragonboneWall(ctx, PastelBlocks.BONE_ASH_TILE_WALL, PastelBlocks.BONE_ASH_TILES);

            // shingles

            generateBasicRecipe(
                    ctx,
                    "dragonbone/bone_ash_shingles",
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

        private static void generateFoodRecipes(RecipeOutput ctx) {
            // TODO: everything else LOL
            generateDynamicPedestalRecipe(ctx, "food/star_candy", new StarCandyRecipe());
        }

        private static void generateGemstoneLightRecipes(RecipeOutput ctx) {
            generateGemstoneLightsGroup(ctx, PastelBlocks.POLISHED_BASALT, PastelBlocks.BASALT_GEMSTONE_LIGHTS);
            generateGemstoneLightsGroup(ctx, PastelBlocks.POLISHED_CALCITE, PastelBlocks.CALCITE_GEMSTONE_LIGHTS);
        }

        private static void generateBasicGlasses(RecipeOutput ctx) {
            PastelGemstoneColorCollection.zipApply(PastelGemstoneColorCollection.VALUES, PastelGemstoneColorCollection.GEMSTONE_NAMES, (color, name) -> {
                generateBasicRecipe(
                        ctx,
                        "glass/" + name + "_glass",
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
                        ctx,
                        "gemstone_glass",
                        BASE_SHARD_UNLOCKS.pick(color),
                        20,
                        PastelGemstoneColorCollection.MINIMUM_TIER.pick(color),
                        PastelBlocks.GEMSTONE_GLASSES.pick(color),
                        PastelBlocks.GEMSTONE_GLASS_PANES.pick(color));
            });

            generatePedestalRecipe(
                    ctx,
                    "glass/radiant_glass",
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
            generateBasicGlassPane(ctx, "radiant_glass", PastelAdvancements.Unlocks.Blocks.RADIANT_GLASS, 80, PedestalTier.BASIC, PastelBlocks.RADIANT_GLASS, PastelBlocks.RADIANT_GLASS_PANE);

            generateBasicGlassPane(ctx, null, PastelAdvancements.Lategame.COLLECT_HUMMINGSTONE, 20, PedestalTier.BASIC, PastelBlocks.HUMMINGSTONE_GLASS, PastelBlocks.HUMMINGSTONE_GLASS_PANE);
        }

        // TODO
        private static void generateJadeiteRecipes(RecipeOutput ctx) {

        }

        // TODO
        private static void generateJadeVinesRecipes(RecipeOutput ctx) {

        }

        // TODO
        private static void generateNoxwoodRecipes(RecipeOutput ctx) {

        }

        private static void generatePigmentBlocks(RecipeOutput ctx) {
            PastelInkColorCollection.VALUES.forEach(color -> {
                var pigmentBlock = PastelBlocks.PIGMENT_BLOCKS.pick(color);
                var pigment = PIGMENTS.pick(color);
                var unlock = PastelAdvancements.Hidden.CollectPigment.VALUES.pick(color);

                generateCompactingPairWithGroup(ctx, "pigment_compacting", "pigment_blocks", pigmentBlock.getId().getPath() + "_to_" + pigment.getId().getPath(), unlock, pigment, pigmentBlock);
            });
        }

        private static void generatePylons(RecipeOutput ctx) {
            PastelGemstoneColorCollection.VALUES.forEach(color -> {
                var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color);
                var item = GEMSTONE_SHARDS.pick(color);
                var pylon = PastelBlocks.PYLONS.pick(color);
                var unlock = PastelAdvancements.Unlocks.Pylons.VALUES.pick(color);

                generateBasicRecipe(
                        ctx,
                        "pylons/" + PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color),
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
        private static void generateResplendentRecipes(RecipeOutput ctx) {

        }

        private static void generateRunes(RecipeOutput ctx) {
            generateRunesGroup(ctx, PastelBlocks.POLISHED_BASALT, PastelBlocks.GEMSTONE_CHISELED_BASALTS);
            generateRunesGroup(ctx, PastelBlocks.POLISHED_CALCITE, PastelBlocks.GEMSTONE_CHISELED_CALCITES);
        }

        private static void generateSaplings(RecipeOutput ctx) {
            PastelInkColorCollection.VALUES.forEach(color -> generateSapling(ctx, color));
        }

        // TODO
        private static void generateShimmerstoneLightRecipes(RecipeOutput ctx) {

        }

        // TODO
        private static void generateToolRecipes(RecipeOutput ctx) {

        }

        // TODO
        private static void generateVanillaRecipes(RecipeOutput ctx) {

        }

        // TODO
        private static void generateWeepingGalaRecipes(RecipeOutput ctx) {

        }

        // TODO
        private static void generateRootRecipes(RecipeOutput ctx) {

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

        private static void generateCompactingPairWithGroup(RecipeOutput ctx, @Nullable String group, String subpath, String unpackName, ResourceLocation unlock, DeferredItem<?> unpacked, DeferredBlock<?> packed) {
            generatePedestalRecipe(
                    ctx,
                    subpath + "/" + packed.getId().getPath(),
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

            generatePedestalRecipe(
                    ctx,
                    subpath + "/" + unpackName,
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

        private static void generateCompactingPair(RecipeOutput ctx, String unpackedName, ResourceLocation unlock, DeferredItem<?> unpacked, DeferredBlock<?> packed) {
            generateCompactingPairWithGroup(ctx, "compacting", "compacting", unpackedName, unlock, unpacked, packed);
        }
        private static <I extends Item, B extends Block> void generateCompactingPair(RecipeOutput ctx, ResourceLocation unlock, DeferredItem<I> unpacked, DeferredBlock<B> packed) {
            generateCompactingPairWithGroup(ctx, "compacting", "compacting", unpacked.getId().getPath() + "_from_" + packed.getId().getPath(), unlock, unpacked, packed);
        }

        private static void generateDetectorRecipe(RecipeOutput ctx, PastelGemstoneColor color, ResourceLocation unlock, DeferredBlock<?> output) {

            generateBasicRecipe(
                    ctx,
                    "detectors/" + output.getId().getPath(),
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

        private static void generateGemstoneLightsGroup(RecipeOutput ctx, DeferredBlock<Block> baseBlock, PastelGemstoneColorCollection<DeferredBlock<Block>> blocks) {
            PastelGemstoneColorCollection.zipApply(blocks, PastelGemstoneColorCollection.VALUES, (block, color) -> {
                generateBasicRecipe(
                        ctx,
                        "gemstone_lights/" + block.getId().getPath(),
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

        private static void generateRunesGroup(RecipeOutput ctx, DeferredBlock<Block> baseBlock, PastelGemstoneColorCollection<DeferredBlock<Block>> blocks) {
            PastelGemstoneColorCollection.VALUES.forEach(color -> {
                var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color);
                var result = blocks.pick(color);
                var unlock = color == PastelGemstoneColor.WHITE ? PastelAdvancements.Lategame.COLLECT_MOONSTONE : PastelAdvancements.CREATE_ONYX_SHARD;
                var cluster = PastelBlocks.GEMSTONE_CLUSTERS.pick(color);
                var shard = GEMSTONE_SHARDS.pick(color);

                generateBasicRecipe(
                        ctx,
                        "runes/" + result.getId().getPath() + "_from_cluster",
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

                generateBasicRecipe(
                        ctx,
                        "runes/" + result.getId().getPath() + "_from_shards",
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
        private static void generateColoredLamp(RecipeOutput ctx, InkColor color) {
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
            generateBasicRecipe(
                    ctx,
                    "colored_lamps/" + color.getID().getPath(),
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

        private static void generateGemstoneArrowRecipe(RecipeOutput ctx, PastelGemstoneColor color) {
            generatePedestalRecipe(
                    ctx,
                    "arrows/" + PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color),
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

        private static void generateSapling(RecipeOutput ctx, InkColor color) {
            var tier = MINIMUM_COLOR_TIER.pick(color);
            var mix = POWDER_MIXES_6.pick(color);
            var dyeItem = PastelInkColorCollection.DYE_ITEMS.pick(color);
            var sapling = PastelBlocks.COLORED_SAPLINGS.pick(color);
            var unlock = PastelAdvancements.Unlocks.ColoredSaplings.VALUES.pick(color);

            generateBasicRecipe(
                    ctx,
                    "saplings/" + PastelInkColorCollection.NAMES.pick(color),
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

        private static void generateBasicGlassPane(RecipeOutput ctx, @Nullable String group, ResourceLocation unlock, int craftingTime, PedestalTier tier, DeferredBlock<Block> glass, DeferredBlock<Block> pane) {
            generateBasicRecipe(
                    ctx,
                    "glass/" + pane.getId().getPath(),
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


        private static void generateDragonboneSlab(RecipeOutput ctx, DeferredBlock<?> slab, DeferredBlock<?> baseBlock) {
            generateBasicRecipe(
                    ctx,
                    "dragonbone/" + slab.getId().getPath(),
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

        private static void generateDragonboneStairs(RecipeOutput ctx, DeferredBlock<?> stairs, DeferredBlock<?> baseBlock) {
            generateBasicRecipe(
                    ctx,
                    "dragonbone/" + stairs.getId().getPath(),
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

        private static void generateDragonboneWall(RecipeOutput ctx, DeferredBlock<?> wall, DeferredBlock<?> baseBlock) {
            generateBasicRecipe(
                    ctx,
                    "dragonbone/" + wall.getId().getPath(),
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
            generateColoredSporeBlossoms(ctx);
            generateVanilla(ctx);
            generateRoot(ctx);
        }

        private static void generateColoredSporeBlossoms(RecipeOutput ctx) {
            PastelInkColorCollection.VALUES.forEach(color -> {
                var name = PastelInkColorCollection.NAMES.pick(color);
                var tier = MINIMUM_COLOR_TIER.pick(color).withMinimumTier(PedestalTier.SIMPLE);
                var unlock = PastelAdvancements.Hidden.CollectPigment.VALUES.pick(color);
                var pigment = PIGMENTS.pick(color);
                var coloredSporeBlossom = PastelBlocks.COLORED_SPORE_BLOSSOMS.pick(color);
                var mix = POWDER_MIXES_6.pick(color);
                generateSimpleRecipe(
                        ctx,
                        "colored_spore_blossoms/" + name,
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

        private static void generateVanilla(RecipeOutput ctx) {
            generateSimpleRecipe(
                    ctx,
                    "vanilla/bell",
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

            generateSimpleRecipe(
                    ctx,
                    "vanilla/name_tag",
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
        private static void generateRoot(RecipeOutput ctx) {

        }

        private static void generateSimpleRecipe(RecipeOutput ctx, String id, PedestalRecipeBuilder<?> builder) {
            generatePedestalRecipeWithSavedTier(ctx, id, PedestalTier.SIMPLE, builder);
        }
    }

    private static class AdvancedRecipes {
        static void generate(RecipeOutput ctx) {
            generateChimes(ctx);
            generateGlowblocks(ctx);
            generateIdols(ctx);
            generatePastelNetwork(ctx);
            generateSemiPermeableGlasses(ctx);
            generateTrinkets(ctx);
            generateRoot(ctx);
        }

        private static void generateChimes(RecipeOutput ctx) {
            PastelGemstoneColorCollection.VALUES.forEach(color -> {
                var shard = GEMSTONE_SHARDS.pick(color);
                var chime = PastelBlocks.CHIMES.pick(color);
                var name = PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color);
                var unlock = PastelAdvancements.Unlocks.GemstoneChimes.VALUES.pick(color);
                var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color).withMinimumTier(PedestalTier.ADVANCED);
                generateAdvancedRecipe(
                        ctx,
                        "gemstone_chimes/" + name,
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

        private static void generateGlowblocks(RecipeOutput ctx) {
            PastelInkColorCollection.VALUES.forEach(color -> {
                var pigment = PIGMENTS.pick(color);
                var glowBlock = PastelBlocks.GLOWBLOCKS.pick(color);
                var unlock = PastelAdvancements.Unlocks.Glowblocks.VALUES.pick(color);
                var mix = POWDER_MIXES_6.pick(color);
                var tier = MINIMUM_COLOR_TIER.pick(color).withMinimumTier(PedestalTier.ADVANCED);
                var name = PastelInkColorCollection.NAMES.pick(color);
                generateAdvancedRecipe(
                        ctx,
                        "glowblocks/" + name,
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
        private static void generateIdols(RecipeOutput ctx) {

        }

        // TODO
        private static void generatePastelNetwork(RecipeOutput ctx) {

        }

        private static void generateSemiPermeableGlasses(RecipeOutput ctx) {
            generateAdvancedRecipe(
                    ctx,
                    "semi_permeable_glass/glass",

                    sharedSemiPermeableGlass(Items.GLASS, Items.GLASS, PastelBlocks.SEMI_PERMEABLE_GLASS)
                            .powderInput(PastelGemstoneColor.CYAN, 1)
                            .powderInput(PastelGemstoneColor.MAGENTA, 1)
                            .powderInput(PastelGemstoneColor.YELLOW, 1)
            );

            generateAdvancedRecipe(
                    ctx,
                    "semi_permeable_glass/tinted",
                    sharedSemiPermeableGlass(ONYX_SHARD.asItem(), Items.TINTED_GLASS, PastelBlocks.TINTED_SEMI_PERMEABLE_GLASS)
                            .powderInput(PastelGemstoneColor.BLACK, 2)
            );

            generateAdvancedRecipe(
                    ctx,
                    "semi_permeable_glass/radiant",
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
               generateAdvancedRecipe(
                       ctx,
                       "semi_permeable_glass/" + name,
                       sharedSemiPermeableGlass(shard, Items.GLASS, result)
                               .powderInput(color, 8)
                               .tier(tier)
                               .requiredAdvancement(unlock)
               );
            });
        }

        // TODO
        private static void generateTrinkets(RecipeOutput ctx) {

        }

        // TODO
        private static void generateRoot(RecipeOutput ctx) {

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

        private static void generateAdvancedRecipe(RecipeOutput ctx, String id, PedestalRecipeBuilder<?> builder) {
            generatePedestalRecipeWithSavedTier(ctx, id, PedestalTier.ADVANCED, builder);
        }
    }

    private static class ComplexRecipes {
        static void generate(RecipeOutput ctx) {
            generateVanilla(ctx);
            generateRoot(ctx);
        }

        private static void generateVanilla(RecipeOutput ctx) {
            generateComplexRecipe(
                    ctx,
                    "vanilla/elytra",
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
            generateComplexRecipe(
                    ctx,
                    "vanilla/heavy_core",
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

            generateComplexRecipe(
                    ctx,
                    "vanilla/trident",
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
        private static void generateRoot(RecipeOutput ctx) {

        }


        private static void generateComplexRecipe(RecipeOutput ctx, String id, PedestalRecipeBuilder<?> builder) {
            generatePedestalRecipeWithSavedTier(ctx, id, PedestalTier.COMPLEX, builder);
        }
    }


}
