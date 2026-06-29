package earth.terrarium.pastel.data.recipe;

import com.mojang.datafixers.util.Either;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import static earth.terrarium.pastel.data.recipe.RecipeUtil.has;
import static java.util.Map.entry;

public class CookingRecipes {
    private record CookingPrefixes(
        PrefixHelper smelting,
        PrefixHelper blasting,
        PrefixHelper smoking,
        PrefixHelper campfireCooking
    ) {
        public CookingPrefixes subPrefix(String subPrefix) {
            return new CookingPrefixes(
                smelting.subPrefix(subPrefix),
                blasting.subPrefix(subPrefix),
                smoking.subPrefix(subPrefix),
                campfireCooking.subPrefix(subPrefix)
            );
        }

        public void recipe(
            int flags,
            String name,
            Ingredient input,
            RecipeCategory category,
            ItemLike result,
            float xp,
            Consumer<SimpleCookingRecipeBuilder> appender
        ) {
            if ((flags & SMELTING) != 0) {
                var builder = SimpleCookingRecipeBuilder
                    .smelting(
                        input,
                        category,
                        result,
                        xp,
                        SMELTING_TIME
                    );
                appender.accept(builder);
                smelting
                    .generateRecipe(
                        name,
                        builder
                    );
            }

            if ((flags & BLASTING) != 0) {
                var builder = SimpleCookingRecipeBuilder
                    .blasting(
                        input,
                        category,
                        result,
                        xp,
                        FAST_SMELT_TIME
                    );
                appender.accept(builder);
                blasting
                    .generateRecipe(
                        name,
                        builder
                    );
            }

            if ((flags & SMOKING) != 0) {
                var builder = SimpleCookingRecipeBuilder
                    .smoking(
                        input,
                        category,
                        result,
                        xp,
                        FAST_SMELT_TIME
                    );
                appender.accept(builder);
                smoking
                    .generateRecipe(
                        name,
                        builder
                    );
            }

            if ((flags & CAMPFIRE) != 0) {
                var builder = SimpleCookingRecipeBuilder
                    .campfireCooking(
                        input,
                        category,
                        result,
                        xp,
                        CAMPFIRE_TIME
                    );
                appender.accept(builder);
                campfireCooking
                    .generateRecipe(
                        name,
                        builder
                    );
            }
        }

        public void recipe(
            int flags,
            Ingredient input,
            RecipeCategory category,
            ItemLike result,
            float xp,
            Consumer<SimpleCookingRecipeBuilder> appender
        ) {
            var name = BuiltInRegistries.ITEM.getKey(result.asItem()).getPath();
            recipe(flags, name, input, category, result, xp, appender);
        }

    }

    private static final int SMELTING = 0x01;

    private static final int BLASTING = 0x02;

    private static final int SMOKING = 0x04;

    private static final int CAMPFIRE = 0x08;

    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "");
        var smelting = pfx.subPrefix("smelting");
        var blasting = pfx.subPrefix("blasting");
        var smoking = pfx.subPrefix("smoking");
        var campfireCooking = pfx.subPrefix("campfire_cooking");
        var prefixes = new CookingPrefixes(smelting, blasting, smoking, campfireCooking);

        ore(prefixes.subPrefix("ore"));
        blackslagOres(prefixes.subPrefix("blackslag_ores"));
        pureResources(prefixes.subPrefix("pure_resources"));

        // hi guys
        var newFunkyPrefix = new CookingPrefixes(smelting.subPrefix("food"), blasting, smoking, campfireCooking);
        food(newFunkyPrefix);

        root(prefixes);
    }

    private static void food(CookingPrefixes pfxs) {
        pfxs
            .recipe(
                SMELTING | SMOKING | CAMPFIRE,
                Ingredient.of(PastelItems.LIZARD_MEAT),
                RecipeCategory.FOOD,
                PastelItems.COOKED_LIZARD_MEAT,
                0.35f,
                it -> it.unlockedBy("has_input", RecipeUtil.has(PastelItems.LIZARD_MEAT))
            );
    }

    private record OreRecipe(
        String name,
        Either<TagKey<Item>, Item> input,
        Item result,
        float xp
    ) {
        OreRecipe(String name, TagKey<Item> input, ItemLike result, float xp) {
            this(name, Either.left(input), result.asItem(), xp);
        }

        OreRecipe(String name, ItemLike input, ItemLike result, float xp) {
            this(name, Either.right(input.asItem()), result.asItem(), xp);
        }

        Ingredient ingredient() {
            return input.map(Ingredient::of, Ingredient::of);
        }

        Criterion<InventoryChangeTrigger.TriggerInstance> unlock() {
            return input.map(RecipeUtil::has, RecipeUtil::has);
        }
    }

    private static final int SMELTING_TIME = 200;

    private static final int FAST_SMELT_TIME = 100;

    private static final int CAMPFIRE_TIME = 600;

    private static final List<OreRecipe> ORES = List
        .of(
            new OreRecipe("amethyst_ores", PastelItemTags.AMETHYST_ORES, Items.AMETHYST_SHARD, 0.25f),
            new OreRecipe("azurite_ores", PastelItemTags.AZURITE_ORES, PastelItems.RAW_AZURITE.asItem(), 2f),
            new OreRecipe("citrine_ores", PastelItemTags.CITRINE_ORES, PastelItems.CITRINE_SHARD.asItem(), 0.25f),
            new OreRecipe("malachite_ores", PastelBlocks.VIRIDIAN_CRYSTAL, PastelItems.RAW_MALACHITE.asItem(), 2f),
            new OreRecipe("moonstone_ores", PastelItemTags.MOONSTONE_ORES, PastelItems.MOONSTONE_SHARD.asItem(), 0.25f),
            new OreRecipe("onyx_ores", PastelItemTags.ONYX_ORES, PastelItems.ONYX_SHARD.asItem(), 0.25f),
            new OreRecipe("paltaeria_ore", PastelBlocks.PALTAERIA_ORE, PastelItems.PALTAERIA_FRAGMENTS.asItem(), 0.2f),
            new OreRecipe(
                "shimmerstone_ores",
                PastelItemTags.SHIMMERSTONE_ORES,
                PastelItems.SHIMMERSTONE_GEM.asItem(),
                0.1f
            ),
            new OreRecipe("stratine_ore", PastelBlocks.STRATINE_ORE, PastelItems.STRATINE_FRAGMENTS.asItem(), 0.2f),
            new OreRecipe("topaz_ores", PastelItemTags.TOPAZ_ORES, PastelItems.TOPAZ_SHARD.asItem(), 0.25f)
        );

    private static void ore(CookingPrefixes pfxs) {
        ORES.forEach(recipe -> {
            var ingredient = recipe.ingredient();
            var unlock = recipe.unlock();
            pfxs
                .recipe(
                    SMELTING | BLASTING,
                    recipe.name,
                    ingredient,
                    RecipeCategory.MISC,
                    recipe.result,
                    recipe.xp,
                    it -> it.unlockedBy("has_inputs", unlock)
                );
        });
    }

    private static final List<OreRecipe> BLACKSLAG_ORE = List
        .of(
            new OreRecipe("coal", PastelBlocks.BLACKSLAG_COAL_ORE, Items.COAL, 0.1f),
            new OreRecipe("copper", PastelBlocks.BLACKSLAG_COPPER_ORE, Items.COPPER_INGOT, 0.7f),
            new OreRecipe("diamond", PastelBlocks.BLACKSLAG_DIAMOND_ORE, Items.DIAMOND, 1.0f),
            new OreRecipe("emerald", PastelBlocks.BLACKSLAG_EMERALD_ORE, Items.EMERALD, 1.0f),
            new OreRecipe("gold", PastelBlocks.BLACKSLAG_GOLD_ORE, Items.GOLD_INGOT, 1.0f),
            new OreRecipe("iron", PastelBlocks.BLACKSLAG_IRON_ORE, Items.IRON_INGOT, 0.7f),
            new OreRecipe("lapis_lazuli", PastelBlocks.BLACKSLAG_LAPIS_ORE, Items.LAPIS_LAZULI, 0.2f),
            new OreRecipe("redstone", PastelBlocks.BLACKSLAG_REDSTONE_ORE, Items.REDSTONE, 0.7f)
        );

    private static void blackslagOres(CookingPrefixes pfxs) {
        BLACKSLAG_ORE.forEach(recipe -> {
            var group = BuiltInRegistries.ITEM.getKey(recipe.result).getPath();
            pfxs
                .recipe(
                    SMELTING | BLASTING,
                    recipe.name,
                    recipe.ingredient(),
                    RecipeCategory.MISC,
                    recipe.result,
                    recipe.xp,
                    it -> it.unlockedBy("has_inputs", recipe.unlock()).group(group)
                );
        });
    }

    private static final Map<DeferredItem<?>, ItemLike> PURE_RESOURCES = Map
        .ofEntries(
            entry(PastelItems.PURE_COAL, Items.COAL),
            entry(PastelItems.PURE_COPPER, Items.COPPER_INGOT),
            entry(PastelItems.PURE_DIAMOND, Items.DIAMOND),
            entry(PastelItems.PURE_ECHO, Items.ECHO_SHARD),
            entry(PastelItems.PURE_EMERALD, Items.EMERALD),
            entry(PastelItems.PURE_GLOWSTONE, Items.GLOWSTONE_DUST),
            entry(PastelItems.PURE_GOLD, Items.GOLD_INGOT),
            entry(PastelItems.PURE_IRON, Items.IRON_INGOT),
            entry(PastelItems.PURE_LAPIS, Items.LAPIS_LAZULI),
            entry(PastelItems.PURE_NETHERITE_SCRAP, Items.NETHERITE_SCRAP),
            entry(PastelItems.PURE_PRISMARINE, Items.PRISMARINE_CRYSTALS),
            entry(PastelItems.PURE_QUARTZ, Items.QUARTZ),
            entry(PastelItems.PURE_REDSTONE, Items.REDSTONE)
        );

    private static void pureResources(CookingPrefixes pfxs) {
        // in a way we all are impure
        PURE_RESOURCES.forEach((pure, impure) -> {
            var name = pure.getId().getPath().replace("pure_", "");
            pfxs
                .recipe(
                    BLASTING,
                    name,
                    Ingredient.of(pure),
                    RecipeCategory.MISC,
                    impure,
                    0.5f,
                    it -> it.unlockedBy("has_input", RecipeUtil.has(pure))
                );
        });
    }

    private static void root(CookingPrefixes pfxs) {
        // the single recipe that is smelt only
        pfxs
            .recipe(
                SMELTING,
                "glistering_melon_to_gold_nugget",
                Ingredient.of(Items.GLISTERING_MELON_SLICE),
                RecipeCategory.MISC,
                Items.GOLD_NUGGET,
                0.1f,
                it -> it.unlockedBy("has_input", RecipeUtil.has(Items.GLISTERING_MELON_SLICE))
            );

        // the rest are smelt/blast
        polishConversion(pfxs, PastelBlocks.BASAL_MARBLE, PastelBlocks.POLISHED_BASAL_MARBLE);
        polishConversion(pfxs, PastelBlocks.BLACKSLAG, PastelBlocks.POLISHED_BLACKSLAG);
        polishConversion(pfxs, Items.CALCITE, PastelBlocks.POLISHED_CALCITE);
        // TODO: is this fine to place in the "building blocks" category?
        polishConversion(pfxs, PastelBlocks.COBBLED_BLACKSLAG, PastelBlocks.BLACKSLAG);
        polishConversion(pfxs, PastelBlocks.SHALE_CLAY, PastelBlocks.POLISHED_SHALE_CLAY);
        polishConversion(pfxs, Items.SMOOTH_BASALT, PastelBlocks.POLISHED_BASALT);

        unsmeltConversion(
            pfxs,
            PastelBlocks.POLISHED_BASAL_MARBLE,
            PastelBlocks.BASAL_MARBLE_BRICK_STAIRS,
            PastelBlocks.BASAL_MARBLE_BRICK_WALL,
            PastelBlocks.BASAL_MARBLE_BRICKS,
            PastelBlocks.BASAL_MARBLE_TILE_STAIRS,
            PastelBlocks.BASAL_MARBLE_TILE_WALL,
            PastelBlocks.BASAL_MARBLE_TILES,
            PastelBlocks.BASAL_MARBLE_PILLAR,
            PastelBlocks.POLISHED_BASAL_MARBLE_STAIRS,
            PastelBlocks.POLISHED_BASAL_MARBLE_WALL
        );

        unsmeltConversion(
            pfxs,
            PastelBlocks.POLISHED_BASALT,
            PastelBlocks.BASALT_BRICK_STAIRS,
            PastelBlocks.BASALT_BRICK_WALL,
            PastelBlocks.BASALT_BRICKS,
            PastelBlocks.BASALT_TILE_STAIRS,
            PastelBlocks.BASALT_TILE_WALL,
            PastelBlocks.BASALT_TILES,
            PastelBlocks.CHISELED_POLISHED_BASALT,
            PastelBlocks.CRACKED_BASALT_TILES,
            PastelBlocks.NOTCHED_POLISHED_BASALT,
            PastelBlocks.POLISHED_BASALT_BUTTON,
            PastelBlocks.POLISHED_BASALT_CREST,
            PastelBlocks.POLISHED_BASALT_PILLAR,
            PastelBlocks.POLISHED_BASALT_PRESSURE_PLATE,
            PastelBlocks.POLISHED_BASALT_STAIRS,
            PastelBlocks.POLISHED_BASALT_WALL,
            PastelBlocks.PLANED_BASALT,
            PastelBlocks.PLANED_BASALT_WALL,
            PastelBlocks.PLANED_BASALT_STAIRS
        );

        unsmeltConversion(
            pfxs,
            PastelBlocks.POLISHED_CALCITE,
            PastelBlocks.CALCITE_BRICK_STAIRS,
            PastelBlocks.CALCITE_BRICK_WALL,
            PastelBlocks.CALCITE_BRICKS,
            PastelBlocks.CALCITE_TILE_STAIRS,
            PastelBlocks.CALCITE_TILE_WALL,
            PastelBlocks.CALCITE_TILES,
            PastelBlocks.CHISELED_POLISHED_CALCITE,
            PastelBlocks.CRACKED_CALCITE_TILES,
            PastelBlocks.NOTCHED_POLISHED_CALCITE,
            PastelBlocks.POLISHED_CALCITE_BUTTON,
            PastelBlocks.POLISHED_CALCITE_CREST,
            PastelBlocks.POLISHED_CALCITE_PILLAR,
            PastelBlocks.POLISHED_CALCITE_PRESSURE_PLATE,
            PastelBlocks.POLISHED_CALCITE_STAIRS,
            PastelBlocks.POLISHED_CALCITE_WALL,
            PastelBlocks.PLANED_CALCITE,
            PastelBlocks.PLANED_CALCITE_WALL,
            PastelBlocks.PLANED_CALCITE_STAIRS
        );

        unsmeltConversion(
            pfxs,
            PastelBlocks.POLISHED_BLACKSLAG,
            PastelBlocks.BLACKSLAG_BRICK_STAIRS,
            PastelBlocks.BLACKSLAG_BRICK_WALL,
            PastelBlocks.BLACKSLAG_BRICKS,
            PastelBlocks.BLACKSLAG_TILE_STAIRS,
            PastelBlocks.BLACKSLAG_TILE_WALL,
            PastelBlocks.BLACKSLAG_TILES,
            PastelBlocks.CHISELED_POLISHED_BLACKSLAG,
            PastelBlocks.COBBLED_BLACKSLAG_STAIRS,
            PastelBlocks.COBBLED_BLACKSLAG_WALL,
            PastelBlocks.CRACKED_BLACKSLAG_TILES,
            PastelBlocks.POLISHED_BLACKSLAG_BUTTON,
            PastelBlocks.POLISHED_BLACKSLAG_PILLAR,
            PastelBlocks.POLISHED_BLACKSLAG_PRESSURE_PLATE,
            PastelBlocks.POLISHED_BLACKSLAG_STAIRS,
            PastelBlocks.POLISHED_BLACKSLAG_WALL
        );

        unsmeltConversion(
            pfxs,
            PastelBlocks.POLISHED_SHALE_CLAY,
            PastelBlocks.SHALE_CLAY_BRICK_STAIRS,
            PastelBlocks.SHALE_CLAY_BRICKS,
            PastelBlocks.SHALE_CLAY_TILE_STAIRS,
            PastelBlocks.SHALE_CLAY_TILES,
            PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_STAIRS,
            PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS,
            PastelBlocks.EXPOSED_SHALE_CLAY_TILE_STAIRS,
            PastelBlocks.EXPOSED_SHALE_CLAY_TILES,
            PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_STAIRS,
            PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS,
            PastelBlocks.WEATHERED_SHALE_CLAY_TILE_STAIRS,
            PastelBlocks.WEATHERED_SHALE_CLAY_TILES
        );

    }

    private static void simpleConversion(
        CookingPrefixes pfxs,
        String name,
        Either<TagKey<Item>, Item> input,
        ItemLike output
    ) {
        var ingredient = input.map(Ingredient::of, Ingredient::of);
        var appender = input.map(CookingRecipes::inUnlock, CookingRecipes::inUnlock);
        pfxs
            .recipe(
                SMELTING | BLASTING,
                name,
                ingredient,
                RecipeCategory.BUILDING_BLOCKS,
                output,
                0.1f,
                appender
            );
    }

    private static void unsmeltConversion(CookingPrefixes pfxs, DeferredBlock<?> result, ItemLike... items) {
        var ingredient = Ingredient.of(items);
        // :shrug:
        var unlock = has(result);
        pfxs
            .recipe(
                SMELTING | BLASTING,
                result.getId().getPath() + "_unsmelting",
                ingredient,
                RecipeCategory.BUILDING_BLOCKS,
                result,
                0.1f,
                it -> it.unlockedBy("has_result", unlock)
            );
    }

    private static void polishConversion(CookingPrefixes pfxs, ItemLike base, DeferredBlock<?> polished) {
        var item = base.asItem();
        var baseName = BuiltInRegistries.ITEM.getKey(item).getPath();
        simpleConversion(
            pfxs,
            baseName + "_to_" + polished.getId().getPath(),
            Either.right(item),
            polished
        );
    }

    private static Consumer<SimpleCookingRecipeBuilder> inUnlock(ItemLike item) {
        return it -> it.unlockedBy("has_input", has(item));
    }

    private static Consumer<SimpleCookingRecipeBuilder> inUnlock(TagKey<Item> tag) {
        return it -> it.unlockedBy("has_input", has(tag));
    }
}
