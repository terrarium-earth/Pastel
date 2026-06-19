package earth.terrarium.pastel.data;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColorMixes;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.energy.color.PastelInkColorCollection;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.recipe.RecipeScaling;
import earth.terrarium.pastel.recipe.cantrip.DegradingRecipe;
import earth.terrarium.pastel.recipe.cantrip.HealingRecipe;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumCatalyst;
import earth.terrarium.pastel.recipe.crystallarieum.CrystallarieumRecipe;
import earth.terrarium.pastel.recipe.enchanter.EnchantmentUpgradeRecipe;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColorCollection;
import earth.terrarium.pastel.recipe.pedestal.PedestalTier;
import earth.terrarium.pastel.recipe.pedestal.builder.PedestalRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.builder.ShapedPedestalRecipeBuilder;
import earth.terrarium.pastel.recipe.pedestal.builder.ShapelessPedestalRecipeBuilder;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
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
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.crafting.FluidIngredient;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

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
    public void buildRecipes(RecipeOutput recipeOutput) {
        generateCrystallarieumRecipes(recipeOutput);
        generateEnchantmentUpgradeRecipes(recipeOutput);
        generateHealingDegradingRecipes(recipeOutput);
        generatePedestalRecipes(recipeOutput);
    }

    private static final PastelGemstoneColorCollection<ResourceLocation> BASE_SHARD_UNLOCKS =
            new PastelGemstoneColorCollection<>(
                    PastelAdvancements.Hidden.CollectShards.TOPAZ,
                    PastelAdvancements.Hidden.CollectShards.AMETHYST,
                    PastelAdvancements.Hidden.CollectShards.CITRINE,
                    PastelAdvancements.CREATE_ONYX_SHARD,
                    PastelAdvancements.Lategame.COLLECT_MOONSTONE
            );

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

    private void generateArrowPedestalRecipes(RecipeOutput ctx) {
        generatePedestalRecipe(
                ctx,
                "arrows/malachite",
                new ShapedPedestalRecipeBuilder(new ItemStack(PastelItems.MALACHITE_GLASS_ARROW.get(), 4))
                        .group("glass_arrows")
                        .craftingTime(200)
                        .tier(PedestalTier.BASIC)
                        .experience(1.0f)
                        .pattern("M")
                        .pattern("S")
                        .pattern("F")
                        .key('M', PastelItems.RAW_MALACHITE.get())
                        .key('S', Items.STICK)
                        .key('F', PastelItemTags.RESPLENDENT_FEATHERS)
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.GLASS_ARROWS)
        );

        for (PastelGemstoneColor color : PastelGemstoneColor.values()) {
            generateGemstoneArrowRecipe(ctx, color);
        }
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

    private void generateColoredLamp(RecipeOutput ctx, InkColor color) {
        DeferredBlock<?> result = PastelBlocks.COLORED_LAMPS.pick(color);
        ResourceLocation unlock = PastelAdvancements.Unlocks.ColoredLamps.VALUES.pick(color);
        // this MAY be the worst java code i've written
        // but IF THIS WAS SCALA this would be peak
        Map<GemstoneColor, Integer> colorMix = POWDER_MIXES_6.pick(color);

        PedestalTier tier = PedestalTier.BASIC;

        if (colorMix.containsKey(PastelGemstoneColor.WHITE)) {
            tier = PedestalTier.COMPLEX;
        } else if (colorMix.containsKey(PastelGemstoneColor.BLACK)) {
            tier = PedestalTier.ADVANCED;
        }


        // note, for some reason all these are saved under the tier1 directory even though they have different tiers
        // depending on color progression
        generatePedestalRecipeWithSavedTier(
                ctx,
                "colored_lamps/" + color.getID().getPath(),
                PedestalTier.BASIC,
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
                        .powderInput(PastelGemstoneColor.CYAN, colorMix.getOrDefault(PastelGemstoneColor.CYAN, 0))
                        .powderInput(PastelGemstoneColor.MAGENTA, colorMix.getOrDefault(PastelGemstoneColor.MAGENTA, 0))
                        .powderInput(PastelGemstoneColor.YELLOW, colorMix.getOrDefault(PastelGemstoneColor.YELLOW, 0))
                        .powderInput(PastelGemstoneColor.BLACK, colorMix.getOrDefault(PastelGemstoneColor.BLACK, 0))
                        .powderInput(PastelGemstoneColor.WHITE, colorMix.getOrDefault(PastelGemstoneColor.WHITE, 0))
                        .tier(tier)
                        .requiredAdvancement(unlock)
        );

    }

    private void generateColoredLampPedestalRecipes(RecipeOutput ctx) {
        InkColors.BUILTIN_COLORS.forEach(color -> generateColoredLamp(ctx, color));
    }

    private void generatePedestalRecipes(RecipeOutput ctx) {
        // # BASIC

        // BASIC / ARROWS
        generateArrowPedestalRecipes(ctx);

        // BASIC / COLORED LAMPS
        // - note, for some reason colored lamps are all saved in the tier1 directory even though they do have different tiers
        generateColoredLampPedestalRecipes(ctx);

        // BASIC / COMPACTING
        generateCompactingRecipes(ctx);

        // BASIC / CRYSTAL ARMOR

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

        // BASIC / CUSHIONS

        generateCushionRecipes(ctx);

        // BASIC / DETECTORS
        // these also always save in the tier1 directory
        generateDetectorRecipe(ctx, PastelGemstoneColor.CYAN, PastelAdvancements.Unlocks.Redstone.ITEM_DETECTOR, PastelBlocks.ITEM_DETECTOR);
        generateDetectorRecipe(ctx, PastelGemstoneColor.MAGENTA, PastelAdvancements.Unlocks.Redstone.BLOCK_LIGHT_DETECTOR, PastelBlocks.BLOCK_LIGHT_DETECTOR);
        generateDetectorRecipe(ctx, PastelGemstoneColor.YELLOW, PastelAdvancements.Unlocks.Redstone.WEATHER_DETECTOR, PastelBlocks.WEATHER_DETECTOR);
        generateDetectorRecipe(ctx, PastelGemstoneColor.BLACK, PastelAdvancements.Unlocks.Redstone.PLAYER_DETECTOR, PastelBlocks.PLAYER_DETECTOR);
        generateDetectorRecipe(ctx, PastelGemstoneColor.WHITE, PastelAdvancements.Unlocks.Redstone.CREATURE_DETECTOR, PastelBlocks.CREATURE_DETECTOR);

        // BASIC / DRAGONBONE (todo)

        // BASIC / FOOD (todo)

        // BASIC / GEMSTONE LIGHTS
        // also grouped under basic even tho onyx/moonstone are higher tier

        generateGemstoneLightsGroup(ctx, PastelBlocks.POLISHED_BASALT, PastelBlocks.BASALT_GEMSTONE_LIGHTS);
        generateGemstoneLightsGroup(ctx, PastelBlocks.POLISHED_CALCITE, PastelBlocks.CALCITE_GEMSTONE_LIGHTS);


        // BASIC / GLASS

        generateBasicGlasses(ctx);

        // BASIC / JADEITE (todo)

        // BASIC / JADE_VINES (todo)

        // BASIC / NOXWOOD (todo)

        // BASIC / PIGMENT BLOCKS

        PastelInkColorCollection.VALUES.forEach(color -> {
            var pigmentBlock = PastelBlocks.PIGMENT_BLOCKS.pick(color);
            var pigment = PIGMENTS.pick(color);
            var unlock = PastelAdvancements.Hidden.CollectPigment.VALUES.pick(color);

            generateCompactingPairWithGroup(ctx, "pigment_compacting", "pigment_blocks", pigmentBlock.getId().getPath() + "_to_" + pigment.getId().getPath(), unlock, pigment, pigmentBlock);
        });

        // BASIC / PYLONS

        PastelGemstoneColorCollection.VALUES.forEach(color -> {
           var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color);
           var item = GEMSTONE_SHARDS.pick(color);
           var pylon = PastelBlocks.PYLONS.pick(color);
           var unlock = PastelAdvancements.Unlocks.Pylons.VALUES.pick(color);

           generatePedestalRecipeWithSavedTier(
                   ctx,
                   "pylons/" + PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color),
                   PedestalTier.BASIC,
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

        // BASIC / RESPLENDENT (todo)

        // BASIC / RUNES (chiseled blocks)
        generateRunes(ctx);

        // BASIC / SAPLINGS

        PastelInkColorCollection.VALUES.forEach(color -> generateSapling(ctx, color));

        // BASIC / SHIMMERSTONE LIGHTS (todo)

        // BASIC / TOOLS (todo)

        // BASIC / VANILLA (todo)

        // BASIC / WEEPING GALA

        // BASIC / (root)

    }

    private void generateSapling(RecipeOutput ctx, InkColor color) {
        var tier = MINIMUM_COLOR_TIER.pick(color);
        var mix = POWDER_MIXES_6.pick(color);
        var dyeItem = PastelInkColorCollection.DYE_ITEMS.pick(color);
        var sapling = PastelBlocks.COLORED_SAPLINGS.pick(color);
        var unlock = PastelAdvancements.Unlocks.ColoredSaplings.VALUES.pick(color);

        generatePedestalRecipeWithSavedTier(
                ctx,
                "saplings/" + PastelInkColorCollection.NAMES.pick(color),
                PedestalTier.BASIC,
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

    private void generateRunes(RecipeOutput ctx) {
        generateRunesGroup(ctx, PastelBlocks.POLISHED_BASALT, PastelBlocks.GEMSTONE_CHISELED_BASALTS);
        generateRunesGroup(ctx, PastelBlocks.POLISHED_CALCITE, PastelBlocks.GEMSTONE_CHISELED_CALCITES);
    }

    private void generateRunesGroup(RecipeOutput ctx, DeferredBlock<Block> baseBlock, PastelGemstoneColorCollection<DeferredBlock<Block>> blocks) {
        PastelGemstoneColorCollection.VALUES.forEach(color -> {
            var tier = PastelGemstoneColorCollection.MINIMUM_TIER.pick(color);
            var result = blocks.pick(color);
            var unlock = color == PastelGemstoneColor.WHITE ? PastelAdvancements.Lategame.COLLECT_MOONSTONE : PastelAdvancements.CREATE_ONYX_SHARD;
            var cluster = PastelBlocks.GEMSTONE_CLUSTERS.pick(color);
            var shard = GEMSTONE_SHARDS.pick(color);

            generatePedestalRecipeWithSavedTier(
                    ctx,
                    "runes/" + result.getId().getPath() + "_from_cluster",
                    PedestalTier.BASIC,
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

            generatePedestalRecipeWithSavedTier(
                    ctx,
                    "runes/" + result.getId().getPath() + "_from_shards",
                    PedestalTier.BASIC,
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

    private void generateBasicGlasses(RecipeOutput ctx) {
        PastelGemstoneColorCollection.zipApply(PastelGemstoneColorCollection.VALUES, PastelGemstoneColorCollection.GEMSTONE_NAMES, (color, name) -> {
            generatePedestalRecipeWithSavedTier(
                    ctx,
                    "glass/" + name + "_glass",
                    PedestalTier.BASIC,
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

    // named basic to remind myself that it always saves to the basic directory
    private void generateBasicGlassPane(RecipeOutput ctx, @Nullable String group, ResourceLocation unlock, int craftingTime, PedestalTier tier, DeferredBlock<Block> glass, DeferredBlock<Block> pane) {
        generatePedestalRecipeWithSavedTier(
                ctx,
                "glass/" + pane.getId().getPath(),
                PedestalTier.BASIC,
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

    private void generateGemstoneLightsGroup(RecipeOutput ctx, DeferredBlock<Block> baseBlock, PastelGemstoneColorCollection<DeferredBlock<Block>> blocks) {
        PastelGemstoneColorCollection.zipApply(blocks, PastelGemstoneColorCollection.VALUES, (block, color) -> {
            generatePedestalRecipeWithSavedTier(
                    ctx,
                    "gemstone_lights/" + block.getId().getPath(),
                    PedestalTier.BASIC,
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

    private void generateDetectorRecipe(RecipeOutput ctx, PastelGemstoneColor color, ResourceLocation unlock, DeferredBlock<?> output) {

        generatePedestalRecipeWithSavedTier(
                ctx,
                "detectors/" + output.getId().getPath(),
                PedestalTier.BASIC,
                new ShapedPedestalRecipeBuilder(new ItemStack(output.asItem()))
                        .tier(PastelGemstoneColorCollection.MINIMUM_TIER.pick(color))
                        .craftingTime(80)
                        .powderInput(color, 4)
                        .experience(2.0f)
                        .pattern("BBB")
                        .pattern("CQC")
                        .pattern("SSS")
                        .key('B', Items.GLASS)
                        .key('C', PastelItems.GEMSTONE_SHARDS.pick(color).value())
                        .key('Q', Items.QUARTZ)
                        .key('S', ItemTags.WOODEN_SLABS)
                        .requiredAdvancement(unlock)
        );
    }

    private void generateCushionRecipes(RecipeOutput ctx) {
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

    private void generateCompactingRecipes(RecipeOutput ctx) {

        for (PastelGemstoneColor color : PastelGemstoneColor.values()) {
            var unpacked = PastelItems.GEMSTONE_POWDERS.pick(color);
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

    private void generateCompactingPairWithGroup(RecipeOutput ctx, @Nullable String group, String subpath, String unpackName, ResourceLocation unlock, DeferredItem<?> unpacked, DeferredBlock<?> packed) {
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



    private void generateCompactingPair(RecipeOutput ctx, String unpackedName, ResourceLocation unlock, DeferredItem<?> unpacked, DeferredBlock<?> packed) {
        generateCompactingPairWithGroup(ctx, "compacting", "compacting", unpackedName, unlock, unpacked, packed);
    }
    private <I extends Item, B extends Block> void generateCompactingPair(RecipeOutput ctx, ResourceLocation unlock, DeferredItem<I> unpacked, DeferredBlock<B> packed) {
        generateCompactingPairWithGroup(ctx, "compacting", "compacting", unpacked.getId().getPath() + "_from_" + packed.getId().getPath(), unlock, unpacked, packed);
    }

    private void generateGemstoneArrowRecipe(RecipeOutput ctx, PastelGemstoneColor color) {
        generatePedestalRecipe(
                ctx,
                "arrows/" + PastelGemstoneColorCollection.GEMSTONE_NAMES.pick(color), // todo, get the name from the gemstone item?
                new ShapedPedestalRecipeBuilder(new ItemStack(PastelItems.GEMSTONE_GLASS_ARROWS.pick(color).get(), 2))
                        .group("glass_arrows")
                        .craftingTime(200)
                        .tier(PedestalTier.BASIC)
                        .powderInput(color, 1)
                        .experience(1.0f)
                        .pattern("AA")
                        .pattern("GB")
                        .key('G', PastelItems.GEMSTONE_SHARDS.pick(color).value())
                        .key('A', PastelItems.MALACHITE_GLASS_ARROW.get())
                        .key('B', PastelItems.BISMUTH_FLAKE.get())
                        .requiredAdvancement(PastelAdvancements.Unlocks.Malachite.GLASS_ARROWS)
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

    private void generatePedestalRecipeWithSavedTier(
            RecipeOutput ctx,
            String id,
            PedestalTier tier,
            PedestalRecipeBuilder<?> builder
    ) {
        String tierId =
                switch (tier) {
                    case BASIC -> "tier1";
                    case SIMPLE -> "tier2";
                    case ADVANCED -> "tier3";
                    case COMPLEX -> "tier4";
                };
        generateRecipeFromBuilder(
                ctx,
                "pedestal/" + tierId + "/" + id,
                builder
        );
    }

    private void generatePedestalRecipe(
            RecipeOutput ctx,
            String id,
            PedestalRecipeBuilder<?> builder
    ) {
        Objects.requireNonNull(builder.getTier(), "tier must not be null when saving recipes!");
        generatePedestalRecipeWithSavedTier(ctx, id, builder.getTier(), builder);
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

    private void generateRecipeFromBuilder(RecipeOutput ctx, String id, RecipeBuilder builder) {
        builder.save(ctx, PastelCommon.locate(id));
    }

}
