package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.recipe.builder.anvil_crushing.AnvilCrushingRecipeBuilder;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
import earth.terrarium.pastel.helpers.tuples.Tuple4;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import oshi.util.tuples.Triplet;

import javax.annotation.Nullable;
import java.util.Map;
import static java.util.Map.entry;

public class AnvilCrushingRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "anvil_crushing");

        coloredLeaves(pfx.subPrefix("colored_leaves"));
        dye(pfx.subPrefix("dye"));
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
