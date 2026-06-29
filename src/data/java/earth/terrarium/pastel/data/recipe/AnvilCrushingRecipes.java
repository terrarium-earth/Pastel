package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.anvil_crushing.AnvilCrushingRecipeBuilder;
import earth.terrarium.pastel.helpers.level.collections.PastelGemstoneColorCollection;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.helpers.level.collections.VanillaColorCollections;
import earth.terrarium.pastel.helpers.tuples.Tuple4;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

import static earth.terrarium.pastel.data.recipe.RecipeUtil.nameFromInAndOut;
import static java.util.Map.entry;

public class AnvilCrushingRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "anvil_crushing");

        coloredLeaves(pfx.subPrefix("colored_leaves"));
        dye(pfx.subPrefix("dye"));
        gemstonePowder(pfx.subPrefix("gemstone_powder"));
        vanillaPulverizing(pfx.subPrefix("vanilla_pulverising"));
        pastelPulverizing(pfx.subPrefix("pastel_pulverising"));
        bismuth(pfx.subPrefix("bismuth"));
        cGrowables(pfx.subPrefix("crystallarieum_growables"));
        vanillaItems(pfx.subPrefix("vanilla_items"));
        root(pfx);
    }

    private static void coloredLeaves(PrefixHelper pfx) {
        var tupled = PastelInkColorCollection.Instance.INSTANCE
            .apply4(
                Tuple4::new,
                PastelBlocks.COLORED_LEAVES,
                PastelItems.PIGMENTS,
                PastelAdvancements.Hidden.CollectPigment.VALUES,
                PastelInkColorCollection.NAMES
            );

        // for comparison, this is how this would look in scala:
        // (PastelBlocks.COLORED_LEAVES,
        //  PastelItems.PIGMENTS,
        //  PastelAdvancements.Hidden.CollectPigment.VALUES,
        //  PastelInkColorCollection.NAMES).tupled.forEach { (leaves, pigment, unlock, name ) => ??? }
        PastelInkColorCollection.unbox(tupled).forEach(tup -> {
            var leaves = tup.a();
            var pigment = tup.b();
            var unlock = tup.c();
            var name = tup.d();

            pfx
                .generateRecipe(
                    name,
                    AnvilCrushingRecipeBuilder
                        .of(
                            new ItemStack(pigment.asItem()),
                            Ingredient.of(leaves),
                            1f,
                            SoundEvents.MOSS_BREAK
                        )
                        .experience(0.01f)
                        .particleEffect(ParticleTypes.ENCHANTED_HIT)
                        .particleCount(10)
                        .requiredAdvancement(unlock)
                );
        });
    }

    private static final PastelGemstoneColorCollection<ResourceLocation> SHARD_UNLOCKS = new PastelGemstoneColorCollection<>(
        PastelAdvancements.Hidden.CollectShards.TOPAZ,
        PastelAdvancements.Hidden.CollectShards.AMETHYST,
        PastelAdvancements.Hidden.CollectShards.CITRINE,
        PastelAdvancements.CREATE_ONYX_SHARD,
        PastelAdvancements.Lategame.COLLECT_MOONSTONE
    );

    private static void gemstonePowder(PrefixHelper pfx) {
        var groupProcessor = new PowderGroupProcessor(pfx);

        PastelGemstoneColorCollection.Instance.INSTANCE
            .ap13(
                PastelGemstoneColorCollection.Instance.INSTANCE.point(groupProcessor::process),
                PastelItems.GEMSTONE_POWDERS,
                PastelItems.GEMSTONE_SHARDS,
                PastelBlocks.SMALL_GEMSTONE_BUDS,
                PastelBlocks.MEDIUM_GEMSTONE_BUDS,
                PastelBlocks.LARGE_GEMSTONE_BUDS,
                PastelBlocks.GEMSTONE_CLUSTERS,
                PastelBlocks.GEMSTONE_BLOCKS,
                PastelSounds.SMALL_GEMSTONE_BUD_BREAK,
                PastelSounds.MEDIUM_GEMSTONE_BUD_BREAK,
                PastelSounds.LARGE_GEMSTONE_BUD_BREAK,
                PastelSounds.GEMSTONE_CLUSTER_BREAK,
                PastelSounds.GEMSTONE_BLOCK_BREAK,
                SHARD_UNLOCKS
            );
    }

    private static final String GEMSTONE_GROUP = "gemstone_crushing";

    private record PowderGroupProcessor(
        PrefixHelper pfx
    ) {
        Unit process(
            DeferredItem<Item> powder,
            Holder<Item> shard,
            DeferredBlock<Block> smallBud,
            DeferredBlock<Block> mediumBud,
            DeferredBlock<Block> largeBud,
            DeferredBlock<Block> cluster,
            DeferredBlock<Block> block,
            SoundEvent smallBudBreak,
            SoundEvent mediumBudBreak,
            SoundEvent largeBudBreak,
            SoundEvent clusterBreak,
            SoundEvent blockBreak,
            ResourceLocation unlock
        ) {

            var bind = new BoundPowderGenerator(pfx, powder.asItem(), unlock);

            bind
                .generateDefault(
                    2,
                    shard.value(),
                    smallBudBreak
                );

            bind
                .generateDefault(
                    16,
                    cluster,
                    clusterBreak
                );

            bind
                .generateDefault(
                    8,
                    largeBud,
                    largeBudBreak
                );

            bind
                .generateDefault(
                    6,
                    mediumBud,
                    mediumBudBreak
                );

            bind
                .generateDefault(
                    4,
                    smallBud,
                    smallBudBreak
                );

            bind
                .generate(
                    1,
                    block,
                    blockBreak,
                    0.4f,
                    0.2f
                );

            return Unit.INSTANCE;
        }
    }

    private record BoundPowderGenerator(
        PrefixHelper pfx,
        Item powder,
        ResourceLocation unlock
    ) {
        void generateDefault(int count, ItemLike input, SoundEvent soundEvent) {
            generate(count, input, soundEvent, 0.6f, 0.4f);
        }

        void generate(int count, ItemLike input, SoundEvent soundEvent, float ppd, float xp) {
            pfx
                .generateRecipe(
                    nameFromInAndOut(input, powder),
                    AnvilCrushingRecipeBuilder
                        .of(
                            new ItemStack(powder, count),
                            Ingredient.of(input),
                            ppd,
                            soundEvent
                        )
                        .group(GEMSTONE_GROUP)
                        .requiredAdvancement(unlock)
                        .particleEffect(ParticleTypes.EXPLOSION)
                        .experience(xp)
                );
        }
    }

    private static final Map<Item, Item> SMALL_DYES = Map
        .ofEntries(
            entry(Items.WITHER_ROSE, Items.BLACK_DYE),
            entry(Items.CORNFLOWER, Items.BLUE_DYE),
            entry(Items.COCOA_BEANS, Items.BROWN_DYE),
            entry(Items.CACTUS, Items.GREEN_DYE),
            entry(Items.BLUE_ORCHID, Items.LIGHT_BLUE_DYE),
            entry(Items.AZURE_BLUET, Items.LIGHT_GRAY_DYE),
            entry(Items.WHITE_TULIP, Items.LIGHT_GRAY_DYE),
            entry(Items.SEA_PICKLE, Items.LIME_DYE),
            entry(Items.ALLIUM, Items.MAGENTA_DYE),
            entry(Items.ORANGE_TULIP, Items.ORANGE_DYE),
            entry(Items.PINK_TULIP, Items.PINK_DYE),
            entry(Items.BEETROOT, Items.RED_DYE),
            entry(Items.POPPY, Items.RED_DYE),
            entry(Items.RED_TULIP, Items.RED_DYE),
            entry(Items.BONE_MEAL, Items.WHITE_DYE),
            entry(Items.LILY_OF_THE_VALLEY, Items.WHITE_DYE),
            entry(Items.DANDELION, Items.YELLOW_DYE)
        );

    private static final Map<Item, Item> LARGE_DYES = Map
        .ofEntries(
            entry(Items.LILAC, Items.MAGENTA_DYE),
            entry(PastelBlocks.APRICOTTI.asItem(), Items.ORANGE_DYE),
            entry(Items.PEONY, Items.PINK_DYE),
            entry(PastelBlocks.SWEET_PEA.asItem(), Items.PINK_DYE),
            entry(Items.ROSE_BUSH, Items.RED_DYE),
            entry(Items.SUNFLOWER, Items.YELLOW_DYE)
        );

    private static void dye(PrefixHelper pfx) {
        SMALL_DYES.forEach((input, dye) -> generateDye(pfx, input, dye, 2));
        LARGE_DYES.forEach((input, dye) -> generateDye(pfx, input, dye, 4));

        generateDye(pfx, PastelBlocks.CLOVER.asItem(), Items.GREEN_DYE, 1);
        generateDye(
            pfx,
            PastelBlocks.FOUR_LEAF_CLOVER.asItem(),
            Items.GREEN_DYE,
            1.0f,
            0.8f,
            4,
            PastelAdvancements.COLLECT_FOUR_LEAF_CLOVER
        );
        generateDye(
            pfx,
            "glow_dye_from_humming_bell",
            PastelBlocks.HUMMING_BELL.asItem(),
            Items.GLOW_INK_SAC,
            2.0f,
            0.0f,
            4,
            null
        );
    }

    private static void generateDye(
        PrefixHelper pfx,
        String name,
        Item input,
        Item dye,
        float ppd,
        float xp,
        int resultCount,
        @Nullable ResourceLocation requiredAdvancement
    ) {
        pfx
            .generateRecipe(
                name,
                AnvilCrushingRecipeBuilder
                    .of(new ItemStack(dye, resultCount), Ingredient.of(input), ppd, SoundEvents.GRASS_BREAK)
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(xp)
                    .requiredAdvancement(requiredAdvancement)
            );
    }

    private static void generateDye(
        PrefixHelper pfx,
        Item input,
        Item dye,
        float ppd,
        float xp,
        int resultCount,
        @Nullable ResourceLocation requiredAdvancement
    ) {
        var inputName = BuiltInRegistries.ITEM.getKey(input).getPath();
        var dyeName = BuiltInRegistries.ITEM.getKey(dye).getPath();
        generateDye(pfx, dyeName + "_from_" + inputName, input, dye, ppd, xp, resultCount, requiredAdvancement);
    }

    private static void generateDye(PrefixHelper pfx, Item input, Item dye, int resultCount) {
        generateDye(pfx, input, dye, 2.0f, 0.0f, resultCount, null);
    }

    private static final List<UncraftGroup> VANILLA_PULVERIZE_UNCRAFT = List
        .of(
            new UncraftGroup(
                Items.RED_SAND,
                1.0f,
                Map
                    .of(
                        4,
                        new UncraftEntry(
                            "red_sandstones",
                            List
                                .of(
                                    Items.CHISELED_RED_SANDSTONE,
                                    Items.RED_SANDSTONE,
                                    Items.RED_SANDSTONE_STAIRS,
                                    Items.RED_SANDSTONE_WALL,
                                    Items.SMOOTH_RED_SANDSTONE,
                                    Items.SMOOTH_RED_SANDSTONE_STAIRS,
                                    Items.CUT_RED_SANDSTONE
                                )
                        ),
                        2,
                        new UncraftEntry(
                            "red_sandstone_slabs",
                            List
                                .of(
                                    Items.RED_SANDSTONE_SLAB,
                                    Items.SMOOTH_RED_SANDSTONE_SLAB,
                                    Items.CUT_RED_SANDSTONE_SLAB
                                )
                        )
                    )
            ),

            new UncraftGroup(
                Items.SAND,
                1.0f,
                Map
                    .of(
                        4,
                        new UncraftEntry(
                            "sandstones",
                            List
                                .of(
                                    Items.CHISELED_SANDSTONE,
                                    Items.CUT_SANDSTONE,
                                    Items.SANDSTONE,
                                    Items.SANDSTONE_STAIRS,
                                    Items.SANDSTONE_WALL,
                                    Items.SMOOTH_SANDSTONE,
                                    Items.SMOOTH_SANDSTONE_STAIRS
                                )
                        ),
                        2,
                        new UncraftEntry(
                            "sandstone_slabs",
                            List
                                .of(
                                    // ??? mojang, you missed one
                                    Blocks.CUT_SANDSTONE_SLAB.asItem(),
                                    Items.SANDSTONE_SLAB,
                                    Items.SMOOTH_SANDSTONE_SLAB
                                )
                        )
                    )
            ),
            UncraftGroup.singleton(Items.SAND, 2.0f, SoundEvents.GRAVEL_BREAK, Items.GRAVEL, 1),
            UncraftGroup.singleton(Items.SAND, 1.5f, Items.END_STONE, 1),
            new UncraftGroup(
                Items.SAND,
                0.5f,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "end_stone_bricks",
                            List
                                .of(
                                    Items.END_STONE_BRICKS,
                                    Items.END_STONE_BRICK_STAIRS,
                                    Items.END_STONE_BRICK_WALL
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.MOSSY_COBBLESTONE,
                0.5f,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "mossy_stone_bricks",
                            List
                                .of(
                                    Items.INFESTED_MOSSY_STONE_BRICKS,
                                    Items.MOSSY_STONE_BRICKS
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.MOSSY_COBBLESTONE,
                2.0f,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "mossy_cobblestone_variants",
                            List
                                .of(
                                    Items.MOSSY_COBBLESTONE_STAIRS,
                                    Items.MOSSY_COBBLESTONE_WALL
                                )
                        )
                    )
            ),
            UncraftGroup.singleton(Items.MOSSY_COBBLESTONE_STAIRS, 2.0f, Items.MOSSY_STONE_BRICK_STAIRS, 1),
            UncraftGroup.singleton(Items.MOSSY_COBBLESTONE_WALL, 2.0f, Items.MOSSY_STONE_BRICK_WALL, 1),
            new UncraftGroup(
                Items.COBBLED_DEEPSLATE,
                0.8f,
                SoundEvents.DEEPSLATE_BREAK,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "deepslates",
                            List
                                .of(
                                    Items.DEEPSLATE,
                                    Items.INFESTED_DEEPSLATE
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.COBBLED_DEEPSLATE,
                0.8f,
                SoundEvents.POLISHED_DEEPSLATE_BREAK,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "polished_deepslates",
                            List
                                .of(
                                    Items.POLISHED_DEEPSLATE,
                                    Items.POLISHED_DEEPSLATE_STAIRS,
                                    Items.POLISHED_DEEPSLATE_WALL
                                )
                        )
                    )
            ),
            UncraftGroup
                .singleton(
                    Items.COBBLED_DEEPSLATE,
                    0.8f,
                    SoundEvents.DEEPSLATE_BRICKS_BREAK,
                    Items.CRACKED_DEEPSLATE_BRICKS,
                    1
                ),
            UncraftGroup
                .singleton(
                    Items.COBBLED_DEEPSLATE,
                    0.8f,
                    SoundEvents.DEEPSLATE_TILES_BREAK,
                    Items.CRACKED_DEEPSLATE_TILES,
                    1
                ),
            new UncraftGroup(
                Items.CRACKED_DEEPSLATE_BRICKS,
                2.0f,
                SoundEvents.DEEPSLATE_BRICKS_BREAK,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "deepslate_bricks",
                            List
                                .of(
                                    Items.DEEPSLATE_BRICKS,
                                    Items.DEEPSLATE_BRICK_STAIRS,
                                    Items.DEEPSLATE_BRICK_WALL
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.CRACKED_DEEPSLATE_TILES,
                2.0f,
                SoundEvents.DEEPSLATE_TILES_BREAK,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "deepslate_tiles",
                            List
                                .of(
                                    Items.DEEPSLATE_TILES,
                                    Items.DEEPSLATE_TILE_STAIRS,
                                    Items.DEEPSLATE_TILE_WALL
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.COBBLESTONE,
                0.5f,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "cracked_stone_bricks",
                            List
                                .of(
                                    Items.CRACKED_STONE_BRICKS,
                                    Items.INFESTED_CRACKED_STONE_BRICKS
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.COBBLESTONE,
                1.0f,
                Map
                    .of(
                        // That's how i'd die if
                        // I was in the 19th century v
                        1,
                        new UncraftEntry(
                            "stones",
                            List
                                .of(
                                    Items.INFESTED_STONE,
                                    Items.SMOOTH_STONE,
                                    Items.STONE,
                                    Items.STONE_STAIRS
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.GRAVEL,
                2.0f,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "cobblestone_variants",
                            List
                                .of(
                                    Items.COBBLESTONE,
                                    Items.COBBLESTONE_STAIRS,
                                    Items.COBBLESTONE_WALL,
                                    Items.INFESTED_COBBLESTONE,
                                    Items.MOSSY_COBBLESTONE
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.CRACKED_STONE_BRICKS,
                0.5f,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "stone_bricks",
                            List
                                .of(
                                    Items.CHISELED_STONE_BRICKS,
                                    Items.INFESTED_CHISELED_STONE_BRICKS,
                                    Items.INFESTED_STONE_BRICKS,
                                    Items.STONE_BRICKS,
                                    Items.STONE_BRICK_STAIRS,
                                    Items.STONE_BRICK_WALL
                                )
                        )
                    )
            ),
            UncraftGroup.singleton(Items.CRACKED_POLISHED_BLACKSTONE_BRICKS, 0.9f, Items.POLISHED_BLACKSTONE_BRICKS, 1),
            new UncraftGroup(
                Items.GRAVEL,
                2.0f,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "cobblestones",
                            List
                                .of(
                                    Items.COBBLESTONE,
                                    Items.MOSSY_COBBLESTONE,
                                    Items.INFESTED_COBBLESTONE,
                                    Items.COBBLESTONE_WALL,
                                    Items.COBBLESTONE_STAIRS
                                )
                        )
                    )
            ),
            new UncraftGroup(
                Items.CRACKED_NETHER_BRICKS,
                0.4f,
                SoundEvents.NETHER_BRICKS_BREAK,
                Map
                    .of(
                        1,
                        new UncraftEntry(
                            "nether_bricks",
                            List
                                .of(
                                    Items.CHISELED_NETHER_BRICKS,
                                    Items.NETHER_BRICK_FENCE,
                                    Items.NETHER_BRICKS,
                                    Items.NETHER_BRICK_STAIRS,
                                    Items.NETHER_BRICK_WALL
                                )
                        )
                    )
            ),
            UncraftGroup.singleton(Items.QUARTZ, 2.0f, Items.QUARTZ_BLOCK, 2),
            new UncraftGroup(
                Items.QUARTZ,
                0.8f,
                Map
                    .of(
                        2,
                        new UncraftEntry(
                            "quartz_variants",
                            List
                                .of(
                                    Items.QUARTZ_BRICKS,
                                    Items.QUARTZ_PILLAR,
                                    Items.QUARTZ_STAIRS,
                                    Items.SMOOTH_QUARTZ,
                                    Items.SMOOTH_QUARTZ_STAIRS,
                                    Items.CHISELED_QUARTZ_BLOCK
                                )
                        ),
                        1,
                        new UncraftEntry(
                            "quartz_slabs",
                            List
                                .of(
                                    Items.QUARTZ_SLAB,
                                    Items.SMOOTH_QUARTZ_SLAB
                                )
                        )
                    )
            ),
            UncraftGroup
                .singleton(Items.NETHER_BRICK, 0.4f, SoundEvents.NETHER_BRICKS_BREAK, Items.CRACKED_NETHER_BRICKS, 4),
            UncraftGroup.singleton(Items.MELON_SEEDS, 3.0f, SoundEvents.WOOD_BREAK, Items.MELON, 8),
            UncraftGroup.singleton(Items.PUMPKIN_SEEDS, 3.0f, SoundEvents.WOOD_BREAK, Items.PUMPKIN, 8),
            UncraftGroup.singleton(Items.HONEYCOMB, 2.0f, SoundEvents.CORAL_BLOCK_BREAK, Items.HONEYCOMB_BLOCK, 4),
            UncraftGroup
                .singleton(Items.GOLD_NUGGET, 0.9f, SoundEvents.GILDED_BLACKSTONE_BREAK, Items.GILDED_BLACKSTONE, 4),
            UncraftGroup.singleton(Items.GLOWSTONE_DUST, 2.0f, SoundEvents.GLASS_BREAK, Items.GLOWSTONE, 4),
            UncraftGroup.singleton(Items.CLAY_BALL, 2.0f, SoundEvents.GRAVEL_BREAK, Items.CLAY, 4),
            UncraftGroup.singleton(Items.BONE_MEAL, 1.5f, SoundEvents.BONE_BLOCK_BREAK, Items.BONE_BLOCK, 9),
            UncraftGroup.singleton(Items.PRISMARINE_CRYSTALS, 1.0f, SoundEvents.GLASS_BREAK, Items.SEA_LANTERN, 3),
            new UncraftGroup(
                Items.PRISMARINE_SHARD,
                1.0f,
                Map
                    .of(
                        2,
                        new UncraftEntry(
                            "prismarine_variants",
                            List
                                .of(
                                    Items.DARK_PRISMARINE,
                                    Items.DARK_PRISMARINE_STAIRS,
                                    Items.PRISMARINE_BRICKS,
                                    Items.PRISMARINE_BRICK_STAIRS
                                )
                        ),
                        // "prismarine_slabs" would be a lie
                        1,
                        new UncraftEntry(
                            "half_prismarine_variants",
                            List
                                .of(
                                    Items.PRISMARINE,
                                    Items.DARK_PRISMARINE_SLAB,
                                    Items.PRISMARINE_BRICK_SLAB,
                                    Items.PRISMARINE_SLAB,
                                    Items.PRISMARINE_STAIRS,
                                    Items.PRISMARINE_WALL
                                )
                        )
                    )
            ),
            UncraftGroup.singleton(Items.RED_SAND, 4.0f, SoundEvents.NETHERRACK_BREAK, Items.NETHERRACK, 1),
            UncraftGroup.singleton(Items.SLIME_BALL, 4.0f, SoundEvents.SLIME_BLOCK_BREAK, Items.SLIME_BLOCK, 9),
            UncraftGroup.singleton(Items.SNOWBALL, 4.0f, SoundEvents.SNOW_BREAK, Items.SNOW_BLOCK, 4)

        );

    private record UncraftEntry(String name, List<Item> items) {
    }

    private record UncraftGroup(Item dest, float ppd, SoundEvent event, Map<Integer, UncraftEntry> entries) {
        UncraftGroup(Item dest, float ppd, Map<Integer, UncraftEntry> entries) {
            this(dest, ppd, SoundEvents.STONE_BREAK, entries);
        }

        static UncraftGroup singleton(Item dest, float ppd, Item source, int count) {
            var name = BuiltInRegistries.ITEM.getKey(source).getPath();
            return new UncraftGroup(dest, ppd, Map.of(count, new UncraftEntry(name, List.of(source))));
        }

        static UncraftGroup singleton(Item dest, float ppd, SoundEvent event, Item source, int count) {
            var name = BuiltInRegistries.ITEM.getKey(source).getPath();
            return new UncraftGroup(dest, ppd, event, Map.of(count, new UncraftEntry(name, List.of(source))));
        }

    }

    private static void vanillaPulverizing(PrefixHelper pfx) {
        PastelInkColorCollection
            .zipApply(
                VanillaColorCollections.CONCRETE_ITEMS,
                VanillaColorCollections.CONCRETE_POWDER_ITEMS,
                (concrete, powder) -> {
                    pfx
                        .generateRecipe(
                            nameFromInAndOut(concrete, powder),
                            AnvilCrushingRecipeBuilder
                                .of(
                                    new ItemStack(powder, 1),
                                    Ingredient.of(concrete),
                                    1.0f,
                                    SoundEvents.STONE_BREAK
                                )
                                .particleEffect(ParticleTypes.CLOUD)
                                .experience(0.0f)
                        );
                }
            );

        VANILLA_PULVERIZE_UNCRAFT.forEach(group -> {
            var destName = BuiltInRegistries.ITEM.getKey(group.dest).getPath();
            group.entries.forEach((count, items) -> {
                pfx
                    .generateRecipe(
                        destName + "_from_" + items.name,
                        AnvilCrushingRecipeBuilder
                            .of(
                                new ItemStack(group.dest, count),
                                Ingredient.of(items.items.toArray(Item[]::new)),
                                group.ppd,
                                group.event
                            )
                            .particleEffect(ParticleTypes.CLOUD)
                            .experience(0.0f)
                    );
            });
        });
    }

    private static void pastelPulverizing(PrefixHelper pfx) {
        pfx
            .generateRecipe(
                "cobbled_blackslag_from_blackslags",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelBlocks.COBBLED_BLACKSLAG),
                        Ingredient.of(PastelBlocks.BLACKSLAG, PastelBlocks.INFESTED_BLACKSLAG),
                        0.8f,
                        SoundEvents.DEEPSLATE_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_BLACKSLAG)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelBlocks.CRACKED_BLACKSLAG_BRICKS),
                        Ingredient.of(PastelBlocks.BLACKSLAG_BRICKS),
                        1.0f,
                        SoundEvents.DEEPSLATE_BREAK
                    )
                    .group("block_cracking")
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelBlocks.CRACKED_BLACKSLAG_TILES),
                        Ingredient.of(PastelBlocks.BLACKSLAG_TILES),
                        1.0f,
                        SoundEvents.DEEPSLATE_BREAK
                    )
                    .group("block_cracking")
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelBlocks.CRACKED_BASALT_BRICKS),
                        Ingredient.of(PastelBlocks.BASALT_BRICKS),
                        1.0f,
                        SoundEvents.DEEPSLATE_BREAK
                    )
                    .group("block_cracking")
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelBlocks.CRACKED_BASALT_TILES),
                        Ingredient.of(PastelBlocks.BASALT_TILES),
                        1.0f,
                        SoundEvents.DEEPSLATE_BREAK
                    )
                    .group("block_cracking")
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelBlocks.CRACKED_CALCITE_BRICKS),
                        Ingredient.of(PastelBlocks.CALCITE_BRICKS),
                        1.0f,
                        SoundEvents.DEEPSLATE_BREAK
                    )
                    .group("block_cracking")
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelBlocks.CRACKED_CALCITE_TILES),
                        Ingredient.of(PastelBlocks.CALCITE_TILES),
                        1.0f,
                        SoundEvents.DEEPSLATE_BREAK
                    )
                    .group("block_cracking")
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
            );

        pfx
            .generateRecipe(
                "quartz_from_rock_crystal",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.QUARTZ, 7),
                        Ingredient.of(PastelBlocks.ROCK_CRYSTAL),
                        0.2f,
                        SoundEvents.GLASS_BREAK
                    )
            );
    }

    private static void bismuth(PrefixHelper pfx) {
        bismuthRecipe(
            pfx,
            1,
            Ingredient.of(PastelBlocks.SMALL_BISMUTH_BUD, PastelBlocks.LARGE_BISMUTH_BUD),
            "buds",
            0.25f
        );
        bismuthRecipe(pfx, 4, Ingredient.of(PastelBlocks.BISMUTH_CLUSTER), "cluster", 1.0f);
        bismuthRecipe(pfx, 2, Ingredient.of(PastelItems.BISMUTH_CRYSTAL), "crystal", 1.0f);
    }

    private static void bismuthRecipe(PrefixHelper pfx, int count, Ingredient input, String fromName, float xp) {
        var name = "bismuth_from_" + fromName;

        pfx
            .generateRecipe(
                name,
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.BISMUTH_FLAKE.asItem(), count),
                        input,
                        0.75f,
                        SoundEvents.CHAIN_BREAK
                    )
                    .experience(xp)
                    .particleEffect(ParticleTypes.ENCHANTED_HIT)
                    .particleCount(10)
                    .group("bismuth_crushing")
                    .requiredAdvancement(PastelAdvancements.Lategame.COLLECT_BISMUTH)
            );
    }

    // I am NOT typing out "Crystallarieum"
    private record CGrowables(
        DeferredBlock<Block> smallBud,
        DeferredBlock<Block> largeBud,
        DeferredBlock<Block> cluster,
        Item result,
        int clusterCount,
        String name
    ) {
        CGrowables(
            DeferredBlock<Block> smallBud,
            DeferredBlock<Block> largeBud,
            DeferredBlock<Block> cluster,
            Item result
        ) {
            this(smallBud, largeBud, cluster, result, 6, BuiltInRegistries.ITEM.getKey(result).getPath());
        }

        CGrowables(
            DeferredBlock<Block> smallBud,
            DeferredBlock<Block> largeBud,
            DeferredBlock<Block> cluster,
            Item result,
            String name
        ) {
            this(smallBud, largeBud, cluster, result, 6, name);
        }

        CGrowables(
            DeferredBlock<Block> smallBud,
            DeferredBlock<Block> largeBud,
            DeferredBlock<Block> cluster,
            Item result,
            int clusterCount
        ) {
            this(smallBud, largeBud, cluster, result, clusterCount, BuiltInRegistries.ITEM.getKey(result).getPath());
        }
    }

    private static final List<CGrowables> CRYSTALLARIEUM_GROWABLES = List
        .of(
            new CGrowables(
                PastelBlocks.SMALL_COAL_BUD,
                PastelBlocks.LARGE_COAL_BUD,
                PastelBlocks.COAL_CLUSTER,
                Items.COAL
            ),
            new CGrowables(
                PastelBlocks.SMALL_COPPER_BUD,
                PastelBlocks.LARGE_COPPER_BUD,
                PastelBlocks.COPPER_CLUSTER,
                Items.COPPER_INGOT
            ),
            new CGrowables(
                PastelBlocks.SMALL_DIAMOND_BUD,
                PastelBlocks.LARGE_DIAMOND_BUD,
                PastelBlocks.DIAMOND_CLUSTER,
                Items.DIAMOND
            ),
            new CGrowables(
                PastelBlocks.SMALL_ECHO_BUD,
                PastelBlocks.LARGE_ECHO_BUD,
                PastelBlocks.ECHO_CLUSTER,
                Items.ECHO_SHARD
            ),
            new CGrowables(
                PastelBlocks.SMALL_EMERALD_BUD,
                PastelBlocks.LARGE_EMERALD_BUD,
                PastelBlocks.EMERALD_CLUSTER,
                Items.EMERALD
            ),
            // someone felt SPECIAL today, didn't they????
            new CGrowables(
                PastelBlocks.SMALL_GLOWSTONE_BUD,
                PastelBlocks.LARGE_GLOWSTONE_BUD,
                PastelBlocks.GLOWSTONE_CLUSTER,
                Items.GLOWSTONE_DUST,
                12,
                "glowstone"
            ),
            new CGrowables(
                PastelBlocks.SMALL_GOLD_BUD,
                PastelBlocks.LARGE_GOLD_BUD,
                PastelBlocks.GOLD_CLUSTER,
                Items.GOLD_INGOT
            ),
            new CGrowables(
                PastelBlocks.SMALL_IRON_BUD,
                PastelBlocks.LARGE_IRON_BUD,
                PastelBlocks.IRON_CLUSTER,
                Items.IRON_INGOT
            ),
            new CGrowables(
                PastelBlocks.SMALL_LAPIS_BUD,
                PastelBlocks.LARGE_LAPIS_BUD,
                PastelBlocks.LAPIS_CLUSTER,
                Items.LAPIS_LAZULI,
                "lapis"
            ),
            new CGrowables(
                PastelBlocks.SMALL_NETHERITE_SCRAP_BUD,
                PastelBlocks.LARGE_NETHERITE_SCRAP_BUD,
                PastelBlocks.NETHERITE_SCRAP_CLUSTER,
                Items.NETHERITE_SCRAP
            ),
            new CGrowables(
                PastelBlocks.SMALL_PRISMARINE_BUD,
                PastelBlocks.LARGE_PRISMARINE_BUD,
                PastelBlocks.PRISMARINE_CLUSTER,
                Items.PRISMARINE_CRYSTALS,
                "prismarine"
            ),
            new CGrowables(
                PastelBlocks.SMALL_QUARTZ_BUD,
                PastelBlocks.LARGE_QUARTZ_BUD,
                PastelBlocks.QUARTZ_CLUSTER,
                Items.QUARTZ
            ),
            new CGrowables(
                PastelBlocks.SMALL_REDSTONE_BUD,
                PastelBlocks.LARGE_REDSTONE_BUD,
                PastelBlocks.REDSTONE_CLUSTER,
                Items.REDSTONE
            )

        );

    private static void cGrowables(PrefixHelper pfx) {
        // ATP just have a static final C = "crystallarieum"
        final var groupName = "crystallarieum_growable_crushing";

        CRYSTALLARIEUM_GROWABLES.forEach(growable -> {
            pfx
                .generateRecipe(
                    growable.name + "_from_buds",
                    AnvilCrushingRecipeBuilder
                        .of(
                            new ItemStack(growable.result),
                            Ingredient.of(growable.smallBud, growable.largeBud),
                            1.0f,
                            SoundEvents.AMETHYST_CLUSTER_BREAK
                        )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.CRYSTALLARIEUM)
                        .group(groupName)
                        .experience(2.0f)
                        .particleEffect(ParticleTypes.EXPLOSION)
                );

            pfx
                .generateRecipe(
                    growable.name + "_from_cluster",
                    AnvilCrushingRecipeBuilder
                        .of(
                            new ItemStack(growable.result, growable.clusterCount),
                            Ingredient.of(growable.cluster),
                            1.0f,
                            SoundEvents.AMETHYST_CLUSTER_BREAK
                        )
                        .requiredAdvancement(PastelAdvancements.Unlocks.Blocks.CRYSTALLARIEUM)
                        .group(groupName)
                        .experience(3.0f)
                        .particleEffect(ParticleTypes.EXPLOSION)
                );
        });
    }

    private static void vanillaItems(PrefixHelper pfx) {
        pfx
            .generateRecipe(
                "blaze_powder_from_blaze_rod",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.BLAZE_POWDER, 4),
                        Ingredient.of(Items.BLAZE_ROD),
                        2.0f,
                        SoundEvents.STONE_BREAK
                    )
                    .particleEffect(ParticleTypes.LAVA)
                    .particleCount(3)
                    .experience(0.0f)
            );

        pfx
            .generateRecipe(
                "bone_meal_from_bone",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.BONE_MEAL, 4),
                        Ingredient.of(Items.BONE),
                        2.0f,
                        SoundEvents.BONE_BLOCK_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.0f)
            );

        pfx
            .generateRecipe(
                "diamonds_from_diamond_horse_armor",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.DIAMOND, 3),
                        Ingredient.of(Items.DIAMOND_HORSE_ARMOR),
                        0.05f,
                        SoundEvents.METAL_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "gold_ingots_from_golden_horse_armor",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.GOLD_INGOT, 3),
                        Ingredient.of(Items.GOLDEN_HORSE_ARMOR),
                        0.2f,
                        SoundEvents.METAL_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "gold_nuggets_from_glistering_melon",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.GOLD_NUGGET, 3),
                        Ingredient.of(Items.GLISTERING_MELON_SLICE),
                        3.0f,
                        SoundEvents.WOOD_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "iron_ingots_from_iron_horse_armor",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.IRON_INGOT, 3),
                        Ingredient.of(Items.IRON_HORSE_ARMOR),
                        0.2f,
                        SoundEvents.METAL_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "iron_nuggets_from_chain",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.IRON_NUGGET, 6),
                        Ingredient.of(Items.CHAIN),
                        0.2f,
                        SoundEvents.METAL_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "leather_from_leather_horse_armor",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.LEATHER, 3),
                        Ingredient.of(Items.LEATHER_HORSE_ARMOR),
                        0.3f,
                        SoundEvents.METAL_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "leather_from_saddle",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.LEATHER, 3),
                        Ingredient.of(Items.SADDLE),
                        0.3f,
                        SoundEvents.METAL_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "sugar_from_sugar_cane",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.SUGAR, 2),
                        Ingredient.of(Items.SUGAR_CANE),
                        4.0f,
                        SoundEvents.GRASS_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "wind_charge_from_breeze_rod",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.WIND_CHARGE, 8),
                        Ingredient.of(Items.BREEZE_ROD),
                        2.0f,
                        SoundEvents.WIND_CHARGE_BURST.value()
                    )
                    .particleEffect(ParticleTypes.GUST_EMITTER_SMALL)
                    .particleCount(3)
            );
    }

    private static void root(PrefixHelper pfx) {
        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.AMARANTH_GRAINS.asItem(), 2),
                        Ingredient.of(PastelBlocks.AMARANTH_BUSHEL),
                        2.0f,
                        SoundEvents.MOSS_BREAK
                    )
                    .requiredAdvancement(PastelAdvancements.COLLECT_AMARANTH_BUSHEL)
                    .particleCount(10)
                    .particleEffect(ParticleTypes.ENCHANTED_HIT)
                    .experience(0.25f)
            );

        pfx
            .generateRecipe(
                "bone_meal_from_dragonbone",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.BONE_MEAL, 32),
                        Ingredient.of(PastelItems.DRAGONBONE_CHUNK),
                        0.3f,
                        SoundEvents.BONE_BLOCK_BREAK
                    )
                    .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                    .particleEffect(ParticleTypes.CLOUD)
                    .experience(0.1f)
            );

        pfx
            .generateRecipe(
                "frostbite_essence_from_frostbite_crystal",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.FROSTBITE_ESSENCE.asItem(), 16),
                        Ingredient.of(PastelBlocks.FROSTBITE_CRYSTAL),
                        1.5f,
                        SoundEvents.GLASS_BREAK
                    )
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_FROSTBITE_CRYSTAL)
                    .particleEffect(ParticleTypes.ENCHANTED_HIT)
                    .particleCount(10)
                    .experience(4.0f)
            );

        pfx
            .generateRecipe(
                "frostbite_essence_from_ice",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.FROSTBITE_ESSENCE.asItem(), 1),
                        Ingredient.of(PastelItemTags.ICES),
                        1.5f,
                        SoundEvents.GLASS_BREAK
                    )
                    .particleEffect(ParticleTypes.ENCHANTED_HIT)
                    .particleCount(10)
                    .experience(0.25f)
            );

        pfx
            .generateRecipe(
                "gold_nugget_from_glistering_melon",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(Items.GOLD_NUGGET, 12),
                        Ingredient.of(PastelBlocks.GLISTERING_MELON),
                        3.0f,
                        SoundEvents.WOOD_BREAK
                    )
                    .particleEffect(ParticleTypes.CLOUD)
            );

        pfx
            .generateRecipe(
                "incandescent_essence_from_blazing_crystal",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.INCANDESCENT_ESSENCE.asItem(), 16),
                        Ingredient.of(PastelBlocks.BLAZING_CRYSTAL),
                        1.5f,
                        SoundEvents.GLASS_BREAK
                    )
                    .particleCount(10)
                    .particleEffect(ParticleTypes.ENCHANTED_HIT)
                    .experience(4.0f)
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_BLAZING_CRYSTAL)
            );

        pfx
            .generateRecipe(
                "incandescent_esscene_from_magma_block",
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.INCANDESCENT_ESSENCE.asItem(), 1),
                        Ingredient.of(Items.MAGMA_BLOCK),
                        1.5f,
                        SoundEvents.STONE_BREAK
                    )
                    .particleCount(10)
                    .particleEffect(ParticleTypes.ENCHANTED_HIT)
                    .experience(0.25f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.PALTAERIA_FRAGMENTS.asItem(), 8),
                        Ingredient.of(PastelItems.PALTAERIA_GEM),
                        1f,
                        SoundEvents.GLASS_BREAK
                    )
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_PALTAERIA_GEM)
                    .particleEffect(ParticleTypes.ENCHANTED_HIT)
                    .particleCount(10)
                    .experience(0.5f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.STRATINE_FRAGMENTS.asItem(), 8),
                        Ingredient.of(PastelItems.STRATINE_GEM),
                        1f,
                        SoundEvents.GLASS_BREAK
                    )
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_STRATINE_GEM)
                    .particleEffect(ParticleTypes.EXPLOSION)
                    .experience(0.5f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.QUITOXIC_POWDER.asItem(), 4),
                        Ingredient.of(PastelBlocks.QUITOXIC_REEDS),
                        1.5f,
                        SoundEvents.GRASS_BREAK
                    )
                    .requiredAdvancement(PastelAdvancements.COLLECT_QUITOXIC_REEDS)
                    .particleEffect(ParticleTypes.WITCH)
                    .particleCount(10)
                    .experience(0.4f)
            );

        pfx
            .generateAutoNamedRecipe(
                AnvilCrushingRecipeBuilder
                    .of(
                        new ItemStack(PastelItems.STAR_FRAGMENT.asItem(), 2),
                        Ingredient.of(PastelItemTags.SHOOTING_STARS),
                        0.3f,
                        SoundEvents.STONE_BREAK
                    )
                    .requiredAdvancement(PastelAdvancements.COLLECT_STAR_FRAGMENT)
                    .experience(5.0f)
                    .particleEffect(ParticleTypes.EXPLOSION)
                    .particleCount(2)
            );

    }

}
