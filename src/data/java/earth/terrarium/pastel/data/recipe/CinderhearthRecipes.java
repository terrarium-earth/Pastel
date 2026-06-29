package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.cinderhearth.CinderhearthRecipeBuilder;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

public class CinderhearthRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "cinderhearth");

        root(pfx);
        vanilla(pfx.subPrefix("vanilla"));
        vanillaOres(pfx.subPrefix("vanilla_ores"));
        slabFusing(pfx.subPrefix("slab_fusing"));
        pureResources(pfx.subPrefix("pure_resources"));
    }

    private static void vanilla(PrefixHelper pfx) {
        pfx
            .generateRecipe(
                "bread_from_amaranth",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItems.AMARANTH_GRAINS), 2, 200)
                    .experience(0.6f)
                    .result(new ItemStack(Items.BREAD))
                    .requiredAdvancement(PastelAdvancements.COLLECT_AMARANTH_BUSHEL)

            );

        pfx
            .generateAutoNamedRecipe(
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(Items.CLAY_BALL), 100)
                    .experience(0.6f)
                    .result(new ItemStack(Items.BRICK, 2))
            );

        pfx
            .generateAutoNamedRecipe(
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(ItemTags.LOGS_THAT_BURN), 100)
                    .experience(0.3f)
                    .result(new ItemStack(Items.CHARCOAL, 2))
            );

        pfx
            .generateAutoNamedRecipe(
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(Items.GLASS), 100)
                    .experience(0.2f)
                    .result(new ItemStack(Items.GLASS, 2))
            );

        pfx
            .generateRecipe(
                "sugar_from_fissure_plum",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItems.FISSURE_PLUM), 2, 50)
                    .experience(1.0f)
                    .result(new ItemStack(Items.SUGAR, 3))
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_FISSURE_PLUM)
            );

    }

    private static void vanillaOres(PrefixHelper pfx) {
        pfx
            .generateRecipe(
                "coal",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(ItemTags.COAL_ORES), 100)
                    .experience(0.2f)
                    .result(new ItemStack(Items.COAL, 4))
                    .result(new ItemStack(Items.DIAMOND), 0.02f)
            );

        pfx
            .generateRecipe(
                "netherite_scrap",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(Items.ANCIENT_DEBRIS), 100)
                    .experience(0.2f)
                    .result(new ItemStack(Items.NETHERITE_SCRAP))
                    .result(new ItemStack(Items.NETHERITE_SCRAP), 0.5f)
            );

        pfx
            .generateRecipe(
                "redstone",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(ItemTags.REDSTONE_ORES), 100)
                    .experience(0.2f)
                    .result(new ItemStack(Items.REDSTONE, 4))
                    .result(new ItemStack(Items.REDSTONE), 0.5f)
                    .result(new ItemStack(Items.GLOWSTONE_DUST), 0.1f)
            );
    }

    private static final Map<DeferredBlock<?>, ItemLike> SLABS = Map
        .ofEntries(
            entry(PastelBlocks.BASALT_BRICK_SLAB, PastelBlocks.BASALT_BRICKS),
            entry(PastelBlocks.BASALT_TILE_SLAB, PastelBlocks.BASALT_TILES),
            entry(PastelBlocks.BLACKSLAG_SLAB, PastelBlocks.BLACKSLAG),
            entry(PastelBlocks.BLACKSLAG_BRICK_SLAB, PastelBlocks.BLACKSLAG_BRICKS),
            entry(PastelBlocks.BLACKSLAG_TILE_SLAB, PastelBlocks.BLACKSLAG_TILES),
            entry(PastelBlocks.BONE_ASH_BRICK_SLAB, PastelBlocks.BONE_ASH_BRICKS),
            entry(PastelBlocks.BONE_ASH_TILE_SLAB, PastelBlocks.BONE_ASH_TILES),
            entry(PastelBlocks.CALCITE_SLAB, Items.CALCITE),
            entry(PastelBlocks.CALCITE_BRICK_SLAB, PastelBlocks.CALCITE_BRICKS),
            entry(PastelBlocks.CALCITE_TILE_SLAB, PastelBlocks.CALCITE_TILES),
            entry(PastelBlocks.COBBLED_BLACKSLAG_SLAB, PastelBlocks.COBBLED_BLACKSLAG),
            entry(PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY_SLAB, PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY),
            entry(PastelBlocks.EXPOSED_SHALE_CLAY_BRICK_SLAB, PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS),
            entry(PastelBlocks.EXPOSED_SHALE_CLAY_TILE_SLAB, PastelBlocks.EXPOSED_SHALE_CLAY_TILES),
            entry(PastelBlocks.PLANED_BASALT_SLAB, PastelBlocks.PLANED_BASALT),
            entry(PastelBlocks.PLANED_CALCITE_SLAB, PastelBlocks.PLANED_CALCITE),
            entry(PastelBlocks.POLISHED_BASALT_SLAB, PastelBlocks.POLISHED_BASALT),
            entry(PastelBlocks.POLISHED_CALCITE_SLAB, PastelBlocks.POLISHED_CALCITE),
            entry(PastelBlocks.POLISHED_BLACKSLAG_SLAB, PastelBlocks.POLISHED_BLACKSLAG),
            entry(PastelBlocks.POLISHED_BONE_ASH_SLAB, PastelBlocks.POLISHED_BONE_ASH),
            entry(PastelBlocks.POLISHED_SHALE_CLAY_SLAB, PastelBlocks.POLISHED_SHALE_CLAY),
            entry(PastelBlocks.SHALE_CLAY_BRICK_SLAB, PastelBlocks.SHALE_CLAY_BRICKS),
            entry(PastelBlocks.SHALE_CLAY_TILE_SLAB, PastelBlocks.SHALE_CLAY_TILES),
            entry(PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY_SLAB, PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY),
            entry(PastelBlocks.WEATHERED_SHALE_CLAY_BRICK_SLAB, PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS),
            entry(PastelBlocks.WEATHERED_SHALE_CLAY_TILE_SLAB, PastelBlocks.WEATHERED_SHALE_CLAY_TILES),
            entry(PastelBlocks.SMOOTH_BASALT_SLAB, Items.SMOOTH_BASALT),
            entry(PastelBlocks.PYRITE_SLAB, PastelBlocks.PYRITE),
            entry(PastelBlocks.PYRITE_TILE_SLAB, PastelBlocks.PYRITE_TILES)
        );

    private static final Map<ResourceLocation, List<ItemLike>> SLAB_ADVANCEMENT = Map
        .of(
            PastelAdvancements.Midgame.ENTER_DIMENSION,
            List
                .of(
                    PastelBlocks.BLACKSLAG,
                    PastelBlocks.BLACKSLAG_BRICKS,
                    PastelBlocks.BLACKSLAG_TILES,
                    PastelBlocks.COBBLED_BLACKSLAG
                ),
            PastelAdvancements.BREAK_CRACKED_DRAGONBONE,
            List
                .of(
                    PastelBlocks.BONE_ASH_BRICKS,
                    PastelBlocks.BONE_ASH_TILES,
                    PastelBlocks.POLISHED_BONE_ASH
                ),
            PastelAdvancements.Hidden.COLLECT_PYRITE,
            List
                .of(
                    PastelBlocks.PYRITE,
                    PastelBlocks.PYRITE_TILES
                ),
            PastelAdvancements.Hidden.COLLECT_SHALE_CLAY,
            List
                .of(
                    PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY,
                    PastelBlocks.EXPOSED_SHALE_CLAY_BRICKS,
                    PastelBlocks.EXPOSED_SHALE_CLAY_TILES,
                    PastelBlocks.POLISHED_SHALE_CLAY,
                    PastelBlocks.SHALE_CLAY_BRICKS,
                    PastelBlocks.SHALE_CLAY_TILES,
                    PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY,
                    PastelBlocks.WEATHERED_SHALE_CLAY_BRICKS,
                    PastelBlocks.WEATHERED_SHALE_CLAY_TILES
                )
        );

    private static void slabFusing(PrefixHelper pfx) {
        SLABS.forEach((slab, block) -> {
            var advancement = SLAB_ADVANCEMENT
                .entrySet()
                .stream()
                .filter(it -> it.getValue().contains(block))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(null);
            pfx
                .generateAutoNamedRecipe(
                    CinderhearthRecipeBuilder
                        .of(Ingredient.of(slab.asItem()), 2, 100)
                        .experience(0.1f)
                        .result(new ItemStack(block))
                        .requiredAdvancement(advancement)
                );
        });

        vanillaSlabFusing(pfx.subPrefix("vanilla"));

    }

    private static final Map<ItemLike, ItemLike> VANILLA_SLABS = Map
        .ofEntries(
            entry(Items.ANDESITE_SLAB, Items.ANDESITE),
            entry(Items.BLACKSTONE_SLAB, Items.BLACKSTONE),
            entry(Items.COBBLED_DEEPSLATE_SLAB, Items.COBBLED_DEEPSLATE),
            entry(Items.COBBLESTONE_SLAB, Items.COBBLESTONE),
            entry(Items.DEEPSLATE_BRICK_SLAB, Items.DEEPSLATE_BRICKS),
            entry(Items.DEEPSLATE_TILE_SLAB, Items.DEEPSLATE_TILES),
            entry(Items.DIORITE_SLAB, Items.DIORITE),
            entry(Items.END_STONE_BRICK_SLAB, Items.END_STONE_BRICKS),
            entry(Items.GRANITE_SLAB, Items.GRANITE),
            entry(Items.MOSSY_COBBLESTONE_SLAB, Items.MOSSY_COBBLESTONE),
            entry(Items.MOSSY_STONE_BRICK_SLAB, Items.MOSSY_STONE_BRICKS),
            entry(Items.POLISHED_ANDESITE_SLAB, Items.POLISHED_ANDESITE),
            entry(Items.POLISHED_BLACKSTONE_SLAB, Items.POLISHED_BLACKSTONE),
            entry(Items.POLISHED_BLACKSTONE_BRICK_SLAB, Items.POLISHED_BLACKSTONE_BRICKS),
            entry(Items.POLISHED_DEEPSLATE_SLAB, Items.POLISHED_DEEPSLATE),
            entry(Items.POLISHED_DIORITE_SLAB, Items.POLISHED_DIORITE),
            entry(Items.POLISHED_GRANITE_SLAB, Items.POLISHED_GRANITE),
            entry(Items.POLISHED_TUFF_SLAB, Items.POLISHED_TUFF),
            entry(Items.QUARTZ_SLAB, Items.QUARTZ_BLOCK),
            entry(Items.SMOOTH_STONE_SLAB, Items.SMOOTH_STONE),
            entry(Items.STONE_SLAB, Items.STONE),
            entry(Items.STONE_BRICK_SLAB, Items.STONE_BRICKS),
            entry(Items.TUFF_SLAB, Items.TUFF),
            entry(Items.TUFF_BRICK_SLAB, Items.TUFF_BRICKS)
        );

    private static void vanillaSlabFusing(PrefixHelper pfx) {
        VANILLA_SLABS.forEach((slab, block) -> {
            pfx
                .generateAutoNamedRecipe(
                    CinderhearthRecipeBuilder
                        .of(Ingredient.of(slab), 2, 100)
                        .experience(0.1f)
                        .result(new ItemStack(block))
                );
        });
    }

    private static final Map<DeferredItem<?>, ItemLike> STANDARD_PURE = Map
        .of(
            PastelItems.PURE_COAL,
            Items.COAL,
            PastelItems.PURE_COPPER,
            Items.COPPER_INGOT,
            PastelItems.PURE_DIAMOND,
            Items.DIAMOND,
            PastelItems.PURE_EMERALD,
            Items.EMERALD,
            PastelItems.PURE_GOLD,
            Items.GOLD_INGOT,
            PastelItems.PURE_IRON,
            Items.IRON_INGOT,
            PastelItems.PURE_NETHERITE_SCRAP,
            Items.NETHERITE_SCRAP
        );

    private static void pureResources(PrefixHelper pfx) {
        var unlock = PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE;
        STANDARD_PURE.forEach((pure, item) -> {
            var name = pure.getId().getPath().replace("pure_", "");
            pfx
                .generateRecipe(
                    name,
                    CinderhearthRecipeBuilder
                        .of(Ingredient.of(pure.asItem()), 200)
                        .experience(0.5f)
                        .requiredAdvancement(unlock)
                        .result(new ItemStack(item, 2))
                );
        });

        pfx
            .generateRecipe(
                "echo",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItems.PURE_ECHO), 200)
                    .experience(0.5f)
                    .requiredAdvancement(unlock)
                    .result(new ItemStack(Items.ECHO_SHARD, 2))
                    .result(new ItemStack(Items.ECHO_SHARD), 0.25f)
            );

        pfx
            .generateRecipe(
                "lapis",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItems.PURE_LAPIS), 200)
                    .experience(0.5f)
                    .requiredAdvancement(unlock)
                    .result(new ItemStack(Items.LAPIS_LAZULI, 4))
                    .result(new ItemStack(Items.LAPIS_LAZULI), 0.5f)
            );

        generateDust(pfx, PastelItems.PURE_GLOWSTONE, Items.GLOWSTONE_DUST);
        generateDust(pfx, PastelItems.PURE_REDSTONE, Items.REDSTONE);
        generateDust(pfx, PastelItems.PURE_PRISMARINE, Items.PRISMARINE_CRYSTALS);
        generateDust(pfx, PastelItems.PURE_QUARTZ, Items.QUARTZ);

        /*
        pfx
            .modIntegration("create")
            .generateRecipe(
                "pure_zinc",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(CreateCompat.PURE_ZINC), 200)
                    .experience(0.5f)
                    .requiredAdvancement(unlock)
                    .result(new ItemStack(AllItems.ZINC_INGOT.asItem(), 2))
            );
        */
    }

    private static void root(PrefixHelper pfx) {
        pfx
            .generateAutoNamedRecipe(
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelBlocks.SLUSH), 20)
                    .experience(4.0f)
                    .requiredAdvancement(PastelAdvancements.Hidden.COLLECT_ASH_AND_SLUSH)
                    .result(new ItemStack(PastelItems.ASH_FLAKES.asItem(), 2))
            );

        pfx
            .generateAutoNamedRecipe(
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItems.DRAGONBONE_CHUNK), 6000)
                    .experience(4.0f)
                    .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                    .result(new ItemStack(PastelItems.BONE_ASH.asItem(), 45))
            );

        pfx
            .generateRecipe(
                "bone_ash_unsmelting",
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItemTags.SMELTS_TO_BONE_ASH), 600)
                    .experience(0.0f)
                    .requiredAdvancement(PastelAdvancements.BREAK_CRACKED_DRAGONBONE)
                    .result(new ItemStack(PastelBlocks.POLISHED_BONE_ASH))
            );

        pfx
            .generateAutoNamedRecipe(
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItems.JADE_JELLY), 100)
                    .experience(0.2f)
                    .requiredAdvancement(PastelAdvancements.Unlocks.Food.JARAMEL)
                    .result(new ItemStack(PastelItems.JARAMEL.asItem(), 4))
            );

        pfx
            .generateAutoNamedRecipe(
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(PastelItems.MERMAIDS_GEM), 16, 200)
                    .experience(0.1f)
                    .requiredAdvancement(PastelAdvancements.COLLECT_MERMAIDS_GEM)
                    .result(new ItemStack(PastelItems.MERMAIDS_POPCORN.asItem()))
            );

        /*
         var ae2 = pfx.modLoaded("ae2");
         ae2.generateRecipe(
                 "silicon_from_rock_crystal",
                 AE2Compat
         );
        */
    }

    private static void generateDust(PrefixHelper pfx, DeferredItem<?> pure, Item dust) {
        var unlock = PastelAdvancements.Lategame.COLLECT_PURE_RESOURCE;

        pfx
            .generateRecipe(
                pure.getId().getPath().replace("pure_", ""),
                CinderhearthRecipeBuilder
                    .of(Ingredient.of(pure), 200)
                    .experience(0.5f)
                    .requiredAdvancement(unlock)
                    .result(new ItemStack(dust, 4))
                    .result(new ItemStack(dust), 0.5f)
            );

    }

}
