package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.block.PastelBlockFamilies;
import earth.terrarium.pastel.helpers.level.collections.PastelGemstoneColorCollection;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearCraftingTabletRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearEnderSpliceRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearInkRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ClearPotionFillableRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.ColorEverpromiseRibbonRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.RepairAnythingRecipe;
import earth.terrarium.pastel.recipe.crafting.dynamic.WrapPresentRecipe;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;

public class CraftingTableRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "crafting_table");

        special(pfx.subPrefix("special"));
        pigmentToDye(pfx.subPrefix("pigment_to_dye"));
        flora(pfx.subPrefix("flora"));
        bannerPatterns(pfx.subPrefix("banner_patterns"));
        basalMarble(pfx.subPrefix("basal_marble"));
        blackslag(pfx.subPrefix("blackslag"));
        pyrite(pfx.subPrefix("pyrite"));

        balcite(pfx.subPrefix("calcite"), PastelBlockFamilies.CALCITE_ALL.get());
        balcite(pfx.subPrefix("basalt"), PastelBlockFamilies.BASALT_ALL.get());

        weepingGala(pfx.subPrefix("weeping_gala"));
        coloredWood(pfx.subPrefix("colored_wood"));
        ash(pfx.subPrefix("ash"));
        noxwood(pfx.subPrefix("noxwood"));
        gemBlocks(pfx.subPrefix("gem_blocks"));
        shaleClay(pfx.subPrefix("shale_clay"));

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.MISC, PastelItems.GUIDEBOOK)
                    .pattern(" S ")
                    .pattern("SBS")
                    .pattern(" S ")
                    .define('S', PastelItemTags.GEMSTONE_SHARDS)
                    .define('B', Items.BOOK)
                    .unlockedBy("started_progression", RecipeUtil.hasAdvancement(PastelAdvancements.PASTEL))
            );

        pfx
            .generateAutoNamedRecipe(
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.FOOD, Items.MUSHROOM_STEW)
                    .requires(Ingredient.of(PastelItemTags.NOXSHROOMS), 2)
                    .requires(Items.BOWL)
                    .unlockedBy("collected_noxshroom", RecipeUtil.has(PastelItemTags.NOXSHROOMS))
            );

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.FOOD, PastelBlocks.GLISTERING_MELON)
                    .pattern("MMM")
                    .pattern("MMM")
                    .pattern("MMM")
                    .define('M', Items.GLISTERING_MELON_SLICE)
                    .unlockedBy("got_vegetal", RecipeUtil.hasAdvancement(PastelAdvancements.COLLECT_VEGETAL))
            );

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.TOOLS, PastelItems.PAINTBRUSH)
                    .pattern("  R")
                    .pattern(" F ")
                    .pattern("E  ")
                    .define('E', Items.STICK)
                    .define('F', Items.COPPER_INGOT)
                    .define('R', ItemTags.WOOL)
                    .unlockedBy("placed_pedestal", RecipeUtil.hasAdvancement(PastelAdvancements.PLACE_PEDESTAL))
            );

        basicPedestalVariant(
            pfx,
            Items.AMETHYST_SHARD,
            PastelBlocks.PEDESTAL_BASIC_AMETHYST,
            PastelAdvancements.Hidden.CollectShards.AMETHYST
        );
        basicPedestalVariant(
            pfx,
            PastelItems.CITRINE_SHARD,
            PastelBlocks.PEDESTAL_BASIC_CITRINE,
            PastelAdvancements.Hidden.CollectShards.CITRINE
        );
        basicPedestalVariant(
            pfx,
            PastelItems.TOPAZ_SHARD,
            PastelBlocks.PEDESTAL_BASIC_TOPAZ,
            PastelAdvancements.Hidden.CollectShards.TOPAZ
        );
    }

    private static void basicPedestalVariant(
        PrefixHelper pfx,
        ItemLike input,
        ItemLike result,
        ResourceLocation unlock
    ) {
        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, result)
                    .pattern("SSS")
                    .pattern("WTW")
                    .pattern("WTW")
                    .define('S', input)
                    .define('W', Items.TUFF)
                    .define('T', ItemTags.PLANKS)
                    .unlockedBy("got_shard", RecipeUtil.hasAdvancement(unlock))
            );
    }

    private static void special(PrefixHelper pfx) {
        pfx
            .generateDynamicRecipe(
                "clear_crafting_tablet",
                new ClearCraftingTabletRecipe()
            );

        pfx
            .generateDynamicRecipe(
                "clear_ender_splice",
                new ClearEnderSpliceRecipe()
            );

        pfx
            .generateDynamicRecipe(
                "clear_ink",
                new ClearInkRecipe()
            );

        pfx
            .generateDynamicRecipe(
                "clear_potion_fillable",
                new ClearPotionFillableRecipe()
            );

        pfx
            .generateDynamicRecipe(
                "color_everpromise_ribbon",
                new ColorEverpromiseRibbonRecipe()
            );

        pfx
            .generateDynamicRecipe(
                "repair_anything",
                new RepairAnythingRecipe()
            );

        pfx
            .generateDynamicRecipe(
                "wrap_present",
                new WrapPresentRecipe()
            );
    }

    private static void pigmentToDye(PrefixHelper pfx) {
        PastelInkColorCollection.VALUES.forEach(color -> {
            var dye = PastelInkColorCollection.DYE_ITEMS.pick(color);
            var pigment = PastelItems.PIGMENTS.pick(color);
            var unlock = PastelAdvancements.Hidden.CollectPigment.VALUES.pick(color);
            var name = RecipeUtil.nameToInAndOut(pigment, dye);
            pfx
                .generateRecipe(
                    name,
                    ShapelessRecipeBuilder
                        .shapeless(RecipeCategory.MISC, dye)
                        .requires(pigment)
                        .unlockedBy("unlocked_pigment", RecipeUtil.hasAdvancement(unlock))
                        .group("pigment_to_dye")
                );
        });
    }

    private static void flora(PrefixHelper pfx) {
        pfx
            .generateRecipe(
                "glow_dye_from_humming_bell",
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, Items.GLOW_INK_SAC, 2)
                    .requires(PastelBlocks.HUMMING_BELL)
                    // dunno the actual advancement so :shrug:
                    .unlockedBy("unlocked_humming_bell", RecipeUtil.has(PastelBlocks.HUMMING_BELL))
                    .group("flora_to_dye")
            );

        pfx
            .generateRecipe(
                "orange_dye_from_apricotti",
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, Items.ORANGE_DYE, 2)
                    .requires(PastelBlocks.APRICOTTI)
                    .unlockedBy("unlocked_apricotti", RecipeUtil.has(PastelBlocks.APRICOTTI))
                    .group("flora_to_dye")
            );

        pfx
            .generateRecipe(
                "pink_dye_from_sweet_pea",
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, Items.PINK_DYE, 2)
                    .requires(PastelBlocks.SWEET_PEA)
                    .unlockedBy("unlocked_sweet_pea", RecipeUtil.has(PastelBlocks.SWEET_PEA))
                    .group("flora_to_dye")
            );
    }

    private static void bannerPatterns(PrefixHelper pfx) {
        pfx
            .generateAutoNamedRecipe(
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, PastelItems.AMETHYST_CLUSTER_BANNER_PATTERN)
                    .requires(Items.PAPER)
                    .requires(Items.AMETHYST_CLUSTER)
                    .unlockedBy(
                        "has_amethyst",
                        RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.CollectShards.AMETHYST)
                    )
            );

        pfx
            .generateAutoNamedRecipe(
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, PastelItems.AMETHYST_SHARD_BANNER_PATTERN)
                    .requires(Items.PAPER)
                    .requires(Items.AMETHYST_SHARD)
                    .unlockedBy(
                        "has_amethyst",
                        RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.CollectShards.AMETHYST)
                    )
            );
    }

    private static void basalMarble(PrefixHelper pfx) {
        var unlock = RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.COLLECT_BASAL_MARBLE);
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.BASAL_MARBLE.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.BASAL_MARBLE_BRICKS.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.BASAL_MARBLE_TILES.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.POLISHED_BASAL_MARBLE.get());
        generatePillar(pfx, unlock, PastelBlocks.POLISHED_BASAL_MARBLE, PastelBlocks.BASAL_MARBLE_PILLAR);

        generateConverting(pfx, unlock, PastelBlocks.POLISHED_BASAL_MARBLE, PastelBlocks.BASAL_MARBLE_BRICKS);
        generateConverting(pfx, unlock, PastelBlocks.BASAL_MARBLE_BRICKS, PastelBlocks.BASAL_MARBLE_TILES);
    }

    private static void blackslag(PrefixHelper pfx) {
        var unlock = RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.COLLECT_BLACKSLAG);
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.BLACKSLAG_BRICKS.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.BLACKSLAG_TILES.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.COBBLED_BLACKSLAG.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.POLISHED_BLACKSLAG.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.BLACKSLAG.get());
        generatePillar(pfx, unlock, PastelBlocks.POLISHED_BLACKSLAG, PastelBlocks.POLISHED_BLACKSLAG_PILLAR);

        generateConverting(pfx, unlock, PastelBlocks.POLISHED_BLACKSLAG, PastelBlocks.BLACKSLAG_BRICKS);
        generateConverting(pfx, unlock, PastelBlocks.BLACKSLAG_BRICKS, PastelBlocks.BLACKSLAG_TILES);
    }

    private static void pyrite(PrefixHelper pfx) {
        var unlock = RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.COLLECT_PYRITE);
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.PYRITE.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.PYRITE_TILES.get());

        pfx
            .generateRecipe(
                "pyrite_chunks_to_pyrite",
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, PastelBlocks.PYRITE)
                    .pattern("##")
                    .pattern("##")
                    .define('#', PastelItems.PYRITE_CHUNK)
                    .unlockedBy("has_input", unlock)
            );

        // ???
        // generateConverting(pfx, PastelBlocks.PYRITE, Pastel);
    }

    private static void balcite(PrefixHelper pfx, PastelBlockFamilies.BalciteMegaFamily family) {
        var base = family.polished().getBaseBlock();
        var unlock = RecipeUtil.has(base);
        generateBasicFamily(pfx, unlock, family.planed());
        generateBasicFamily(pfx, unlock, family.tiles());
        generateBasicFamily(pfx, unlock, family.bricks());
        generateBasicFamily(pfx, unlock, family.vanilla());
        generateBasicFamily(pfx, unlock, family.polished());

        generatePillar(pfx, unlock, base, family.pillar());

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, family.notched(), 4)
                    .pattern(" # ")
                    .pattern("# #")
                    .pattern(" # ")
                    .define('#', base)
                    .unlockedBy("has_input", unlock)
            );

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, family.crest(), 3)
                    .pattern(" # ")
                    .pattern("# #")
                    .define('#', base)
                    .unlockedBy("has_input", unlock)
            );

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, family.planed().getBaseBlock(), 9)
                    .pattern("###")
                    .pattern("###")
                    .pattern("###")
                    .define('#', base)
                    .unlockedBy("has_unlock", unlock)
            );

        generateConverting(pfx, unlock, base, family.bricks().getBaseBlock());
        generateConverting(pfx, unlock, family.bricks().getBaseBlock(), family.tiles().getBaseBlock());
    }

    private static void weepingGala(PrefixHelper pfx) {
        var unlock = RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.COLLECT_WEEPING_GALA);
        generateWood(pfx, unlock, PastelBlockFamilies.WEEPING_GALA.get());
    }

    private static void coloredWood(PrefixHelper pfx) {
        PastelInkColorCollection
            .zipApply(
                PastelAdvancements.Hidden.CollectPigment.VALUES,
                PastelBlockFamilies.COLORED_WOODS.get(),
                (unlockId, family) -> {
                    var unlock = RecipeUtil.hasAdvancement(unlockId);
                    generateWood(pfx, unlock, family);
                }
            );
    }

    private static void ash(PrefixHelper pfx) {
        var unlock = RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.COLLECT_ASH);
        pfx
            .generateRecipe(
                "ash_from_flakes",
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.MISC, PastelItems.ASH_FLAKES, 4)
                    .requires(PastelBlocks.ASH)
                    .group("compacting")
                    .unlockedBy("has_unlock", unlock)
            );

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.DECORATIONS, PastelBlocks.ASH_PILE, 6)
                    .pattern("WWW")
                    .define('W', PastelBlocks.ASH)
                    .unlockedBy("has_unlock", unlock)
            );

        pfx
            .generateRecipe(
                "flakes_from_ash",
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.MISC, PastelBlocks.ASH)
                    .pattern("WW")
                    .pattern("WW")
                    .define('W', PastelItems.ASH_FLAKES)
                    .unlockedBy("has_unlock", unlock)
            );
    }

    private static void noxwood(PrefixHelper pfx) {
        var chestnut = PastelBlockFamilies.CHESTNUT_NOXWOOD.get();
        var chestnutUnlock = RecipeUtil.has(chestnut.logs());
        generateWood(pfx, chestnutUnlock, chestnut);

        var ebony = PastelBlockFamilies.EBONY_NOXWOOD.get();
        var ebonyUnlock = RecipeUtil.has(ebony.logs());
        generateWood(pfx, ebonyUnlock, ebony);

        var ivory = PastelBlockFamilies.IVORY_NOXWOOD.get();
        var ivoryUnlock = RecipeUtil.has(ivory.logs());
        generateWood(pfx, ivoryUnlock, ivory);

        var slate = PastelBlockFamilies.SLATE_NOXWOOD.get();
        var slateUnlock = RecipeUtil.has(slate.logs());
        generateWood(pfx, slateUnlock, slate);
    }

    private static void gemBlocks(PrefixHelper pfx) {
        var bind = new BindGemBlock(pfx);

        PastelGemstoneColorCollection
            .zipApply4(
                PastelItems.GEMSTONE_SHARDS,
                PastelBlocks.GEMSTONE_BLOCKS,
                PastelBlocks.POLISHED_GEMSTONE_BLOCKS,
                PastelAdvancements.Hidden.CollectShards.VALUES,
                bind::generate
            );
    }

    private record BindGemBlock(PrefixHelper pfx) {
        void generate(
            Holder<Item> shard,
            DeferredBlock<Block> gemBlock,
            DeferredBlock<Block> polished,
            ResourceLocation unlockId
        ) {
            var unlock = RecipeUtil.hasAdvancement(unlockId);
            pfx
                .generateRecipe(
                    RecipeUtil.nameFromInAndOut(polished, shard.value()),
                    ShapelessRecipeBuilder
                        .shapeless(RecipeCategory.MISC, shard.value(), 9)
                        .requires(polished)
                        .group("compacting")
                        .unlockedBy("has_unlock", unlock)
                );

            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.MISC, polished)
                        .pattern("###")
                        .pattern("###")
                        .pattern("###")
                        .define('#', shard.value())
                        .group("compacting")
                        .unlockedBy("has_unlock", unlock)
                );

            // jank
            if (gemBlock.get() != Blocks.AMETHYST_BLOCK) {
                pfx
                    .generateAutoNamedRecipe(
                        ShapedRecipeBuilder
                            .shaped(RecipeCategory.MISC, gemBlock)
                            .pattern("##")
                            .pattern("##")
                            .define('#', shard.value())
                            .unlockedBy("has_unlock", unlock)
                    );
            }
        }
    }

    private static void shaleClay(PrefixHelper pfx) {
        var unlock = RecipeUtil.hasAdvancement(PastelAdvancements.Hidden.COLLECT_SHALE_CLAY);

        generateBasicFamily(pfx, unlock, PastelBlockFamilies.POLISHED_SHALE_CLAY.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.SHALE_CLAY_BRICKS.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.SHALE_CLAY_TILES.get());

        generateConverting(pfx, unlock, PastelBlocks.POLISHED_SHALE_CLAY, PastelBlocks.SHALE_CLAY_BRICKS);
        generateConverting(pfx, unlock, PastelBlocks.SHALE_CLAY_BRICKS, PastelBlocks.SHALE_CLAY_TILES);

        generateBasicFamily(pfx, unlock, PastelBlockFamilies.EXPOSED_POLISHED_SHALE_CLAY.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.EXPOSED_SHALE_CLAY_BRICKS.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.EXPOSED_SHALE_CLAY_TILES.get());

        generateConverting(
            pfx,
            unlock,
            PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY,
            PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS
        );
        generateConverting(pfx, unlock, PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS, PastelBlocks.EXPOSED_SHALE_CLAY_TILES);

        generateBasicFamily(pfx, unlock, PastelBlockFamilies.WEATHERED_POLISHED_SHALE_CLAY.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.WEATHERED_SHALE_CLAY_BRICKS.get());
        generateBasicFamily(pfx, unlock, PastelBlockFamilies.WEATHERED_SHALE_CLAY_TILES.get());

        generateConverting(
            pfx,
            unlock,
            PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY,
            PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS
        );
        generateConverting(
            pfx,
            unlock,
            PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS,
            PastelBlocks.WEATHERED_SHALE_CLAY_TILES
        );
    }

    private static void generateWood(PrefixHelper pfx, Criterion<?> unlock, PastelBlockFamilies.WoodFamily woodFamily) {
        generateBasicFamily(pfx, unlock, woodFamily.plankFamily());

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, woodFamily.wood(), 3)
                    .pattern("##")
                    .pattern("##")
                    .define('#', woodFamily.log())
                    .unlockedBy("has_unlock", unlock)
                    .group("bark")
            );

        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, woodFamily.strippedWood(), 3)
                    .pattern("##")
                    .pattern("##")
                    .define('#', woodFamily.strippedLog())
                    .group("bark")
                    .unlockedBy("has_unlock", unlock)
            );

        pfx
            .generateAutoNamedRecipe(
                ShapelessRecipeBuilder
                    .shapeless(RecipeCategory.BUILDING_BLOCKS, woodFamily.planks(), 4)
                    .requires(woodFamily.logs())
                    .unlockedBy("has_unlock", unlock)
            );
    }

    private static void generateConverting(PrefixHelper pfx, ItemLike base, ItemLike next) {
        generateConverting(pfx, base, base, next);
    }

    private static void generateConverting(PrefixHelper pfx, ItemLike root, ItemLike base, ItemLike next) {
        generateConverting(pfx, RecipeUtil.has(root), base, next);
    }

    private static void generateConverting(PrefixHelper pfx, Criterion<?> unlock, ItemLike base, ItemLike next) {
        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, next, 4)
                    .pattern("##")
                    .pattern("##")
                    .define('#', base)
                    .unlockedBy("has_input", unlock)
            );
    }

    private static void generatePillar(PrefixHelper pfx, Criterion<?> unlock, ItemLike base, ItemLike pillar) {
        pfx
            .generateAutoNamedRecipe(
                ShapedRecipeBuilder
                    .shaped(RecipeCategory.BUILDING_BLOCKS, pillar, 2)
                    .pattern("#")
                    .pattern("#")
                    .define('#', base)
                    .unlockedBy("has_input", unlock)
            );
    }

    private static void generatePillar(PrefixHelper pfx, ItemLike base, ItemLike pillar) {
        generatePillar(pfx, RecipeUtil.has(base), base, pillar);
    }

    private static void generateBasicFamily(PrefixHelper pfx, TagKey<Item> root, BlockFamily family) {
        generateBasicFamily(pfx, RecipeUtil.has(root), family);
    }

    private static void generateBasicFamily(PrefixHelper pfx, ItemLike root, BlockFamily family) {
        generateBasicFamily(pfx, RecipeUtil.has(root), family);
    }

    private static void generateBasicFamily(PrefixHelper pfx, Criterion<?> unlock, BlockFamily family) {
        var base = family.getBaseBlock();

        var variants = family.getVariants();
        var stairs = variants.getOrDefault(BlockFamily.Variant.STAIRS, null);
        var slab = variants.getOrDefault(BlockFamily.Variant.SLAB, null);
        var wall = variants.getOrDefault(BlockFamily.Variant.WALL, null);
        var chiseled = variants.getOrDefault(BlockFamily.Variant.CHISELED, null);
        var pressurePlate = variants.getOrDefault(BlockFamily.Variant.PRESSURE_PLATE, null);
        var button = variants.getOrDefault(BlockFamily.Variant.BUTTON, null);
        var door = variants.getOrDefault(BlockFamily.Variant.DOOR, null);
        var trapdoor = variants.getOrDefault(BlockFamily.Variant.TRAPDOOR, null);
        var fence = variants.getOrDefault(BlockFamily.Variant.FENCE, null);
        var fenceGate = variants.getOrDefault(BlockFamily.Variant.FENCE_GATE, null);

        if (stairs != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.BUILDING_BLOCKS, stairs, 6)
                        .pattern("W  ")
                        .pattern("WW ")
                        .pattern("WWW")
                        .define('W', base)
                        .unlockedBy("has_input", unlock)
                );
        }

        if (slab != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.BUILDING_BLOCKS, slab, 6)
                        .pattern("WWW")
                        .define('W', base)
                        .unlockedBy("has_input", unlock)
                );
        }

        if (wall != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.BUILDING_BLOCKS, wall, 6)
                        .pattern("WWW")
                        .pattern("WWW")
                        .define('W', base)
                        .unlockedBy("has_input", unlock)
                );
        }

        if (chiseled != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.BUILDING_BLOCKS, chiseled, 5)
                        .pattern(" # ")
                        .pattern("###")
                        .pattern(" # ")
                        .define('#', base)
                        .unlockedBy("has_input", unlock)
                );
        }

        if (pressurePlate != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.REDSTONE, pressurePlate)
                        .pattern("##")
                        .define('#', base)
                        .unlockedBy("has_input", unlock)
                );
        }

        if (button != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapelessRecipeBuilder
                        .shapeless(RecipeCategory.REDSTONE, button)
                        .requires(base)
                        .unlockedBy("has_input", unlock)
                );
        }

        if (door != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.REDSTONE, door, 3)
                        .pattern("##")
                        .pattern("##")
                        .pattern("##")
                        .define('#', base)
                        .unlockedBy("has_unlock", unlock)
                        .group("wooden_door")
                );
        }

        if (trapdoor != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.REDSTONE, trapdoor, 2)
                        .pattern("###")
                        .pattern("###")
                        .define('#', base)
                        .unlockedBy("has_unlock", unlock)
                        .group("wooden_trapdoor")
                );
        }

        if (fence != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.BUILDING_BLOCKS, fence, 3)
                        .group("wooden_fence")
                        .pattern("W#W")
                        .pattern("W#W")
                        .define('#', Items.STICK)
                        .define('W', base)
                        .unlockedBy("has_unlock", unlock)
                );
        }

        if (fenceGate != null) {
            pfx
                .generateAutoNamedRecipe(
                    ShapedRecipeBuilder
                        .shaped(RecipeCategory.REDSTONE, fenceGate)
                        .group("wooden_fence_gate")
                        .pattern("#W#")
                        .pattern("#W#")
                        .define('#', Items.STICK)
                        .define('W', base)
                        .unlockedBy("has_unlock", unlock)
                );
        }
    }
}
