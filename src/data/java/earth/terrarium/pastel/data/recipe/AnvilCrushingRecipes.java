package earth.terrarium.pastel.data.recipe;

import com.mojang.datafixers.util.Function13;
import earth.terrarium.pastel.data.recipe.builder.anvil_crushing.AnvilCrushingRecipeBuilder;
import earth.terrarium.pastel.helpers.level.collections.PastelGemstoneColorCollection;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.helpers.tuples.Tuple4;
import earth.terrarium.pastel.recipe.pedestal.PastelGemstoneColor;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
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
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import oshi.util.tuples.Triplet;

import javax.annotation.Nullable;
import java.util.Map;
import static java.util.Map.entry;

public class AnvilCrushingRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "anvil_crushing");

        coloredLeaves(pfx.subPrefix("colored_leaves"));
        dye(pfx.subPrefix("dye"));
        gemstonePowder(pfx.subPrefix("gemstone_powder"));
    }


    private static void coloredLeaves(PrefixHelper pfx) {
        var tupled =
                PastelInkColorCollection.Instance.INSTANCE.apply4(Tuple4::new,
                        PastelBlocks.COLORED_LEAVES,
                        PastelItems.PIGMENTS,
                        PastelAdvancements.Hidden.CollectPigment.VALUES,
                        PastelInkColorCollection.NAMES);

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

            pfx.generateRecipe(
                    name,
                    AnvilCrushingRecipeBuilder.of(
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

    private static final PastelGemstoneColorCollection<ResourceLocation> SHARD_UNLOCKS =
            new PastelGemstoneColorCollection<>(
                    PastelAdvancements.Hidden.CollectShards.TOPAZ,
                    PastelAdvancements.Hidden.CollectShards.AMETHYST,
                    PastelAdvancements.Hidden.CollectShards.CITRINE,
                    PastelAdvancements.CREATE_ONYX_SHARD,
                    PastelAdvancements.Lategame.COLLECT_MOONSTONE
            );

    private static void gemstonePowder(PrefixHelper pfx) {
        var groupProcessor = new PowderGroupProcessor(pfx);

        PastelGemstoneColorCollection.Instance.INSTANCE.ap13(
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

            bind.generateDefault(
                    2,
                    shard.value(),
                    smallBudBreak
            );

            bind.generateDefault(
                    16,
                    cluster,
                    clusterBreak
            );

            bind.generateDefault(
                    8,
                    largeBud,
                    largeBudBreak
            );

            bind.generateDefault(
                    6,
                    mediumBud,
                    mediumBudBreak
            );

            bind.generateDefault(
                    4,
                    smallBud,
                    smallBudBreak
            );

            bind.generate(
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
            pfx.generateRecipe(
                    nameFromInAndOut(input, powder),
                    AnvilCrushingRecipeBuilder.of(
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


    private static String nameFromInAndOut(ItemLike input, ItemLike output) {
        var inName = BuiltInRegistries.ITEM.getKey(input.asItem()).getPath();
        var outName = BuiltInRegistries.ITEM.getKey(output.asItem()).getPath();
        return outName + "_from_" + inName;
    }

    private static final Map<Item, Item> SMALL_DYES =
            Map.ofEntries(
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

    private static final Map<Item, Item> LARGE_DYES =
            Map.ofEntries(
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
        generateDye(pfx, PastelBlocks.FOUR_LEAF_CLOVER.asItem(), Items.GREEN_DYE, 1.0f, 0.8f, 4, PastelAdvancements.COLLECT_FOUR_LEAF_CLOVER);
        generateDye(pfx, "glow_dye_from_humming_bell", PastelBlocks.HUMMING_BELL.asItem(), Items.GLOW_INK_SAC, 2.0f, 0.0f, 4, null);
    }

    private static void generateDye(PrefixHelper pfx, String name, Item input, Item dye, float ppd, float xp, int resultCount, @Nullable ResourceLocation requiredAdvancement) {
        pfx.generateRecipe(
                name,
                AnvilCrushingRecipeBuilder.of(new ItemStack(dye, resultCount), Ingredient.of(input), ppd, SoundEvents.GRASS_BREAK)
                        .particleEffect(ParticleTypes.CLOUD)
                        .experience(xp)
                        .requiredAdvancement(requiredAdvancement)
        );
    }

    private static void generateDye(PrefixHelper pfx, Item input, Item dye, float ppd, float xp, int resultCount, @Nullable ResourceLocation requiredAdvancement) {
        var inputName = BuiltInRegistries.ITEM.getKey(input).getPath();
        var dyeName = BuiltInRegistries.ITEM.getKey(dye).getPath();
        generateDye(pfx, dyeName + "_from_" + inputName, input, dye, ppd, xp, resultCount, requiredAdvancement);
    }

    private static void generateDye(PrefixHelper pfx, Item input, Item dye, int resultCount) {
        generateDye(pfx, input, dye, 2.0f, 0.0f, resultCount, null);
    }

}
