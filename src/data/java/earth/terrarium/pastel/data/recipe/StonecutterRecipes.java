package earth.terrarium.pastel.data.recipe;

import earth.terrarium.pastel.data.block.PastelBlockFamilies;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.StonecutterRecipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.List;

import static earth.terrarium.pastel.data.recipe.RecipeUtil.nameFromInAndOut;

public class StonecutterRecipes {
    public static void generate(RecipeOutput ctx, HolderLookup.Provider lookup) {
        var pfx = new PrefixHelper(ctx, lookup, "stonecutting");

        basalMarble(pfx.subPrefix("basal_marble"));
        basalt(pfx.subPrefix("basalt"));
        calcite(pfx.subPrefix("calcite"));
        shaleClay(pfx.subPrefix("shale_clay"));
        blackslag(pfx.subPrefix("blackslag"));
        dragonbone(pfx.subPrefix("dragonbone"));
        pyrite(pfx.subPrefix("pyrite"));
    }

    private static void basalMarble(PrefixHelper pfx) {
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.BASAL_MARBLE.get());
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.POLISHED_BASAL_MARBLE.get());
        pfx
            .generateDynamicRecipe(
                nameFromInAndOut(PastelBlocks.POLISHED_BASAL_MARBLE, PastelBlocks.BASAL_MARBLE_PILLAR),
                new StonecutterRecipe(
                    "",
                    Ingredient.of(PastelBlocks.POLISHED_BASAL_MARBLE),
                    new ItemStack(PastelBlocks.BASAL_MARBLE_PILLAR)
                )
            );

        SubFamilyGeneratorBind
            .generateFrom(
                pfx,
                PastelBlockFamilies.BASAL_MARBLE_BRICKS.get(),
                PastelBlocks.POLISHED_BASAL_MARBLE.asItem()
            );
        SubFamilyGeneratorBind
            .generateFrom(
                pfx,
                PastelBlockFamilies.BASAL_MARBLE_TILES.get(),
                PastelBlocks.POLISHED_BASAL_MARBLE.asItem()
            );
    }

    private static void generateStonecutter(
        PrefixHelper pfx,
        ItemLike input,
        BlockFamily.Variant variant,
        ItemLike block
    ) {
        var count = variant == BlockFamily.Variant.SLAB ? 2 : 1;
        generateStonecutter(pfx, input, count, block);
    }

    private static void generateStonecutter(PrefixHelper pfx, ItemLike input, int count, ItemLike block) {
        pfx
            .generateDynamicRecipe(
                nameFromInAndOut(input, block),
                new StonecutterRecipe("", Ingredient.of(input), new ItemStack(block, count))
            );
    }

    private record FamilyGeneratorBind(PrefixHelper pfx, Item base) {
        void generate(BlockFamily.Variant variant, Block block) {
            if (!validStonecutterVariant(variant)) {
                return;
            }
            generateStonecutter(
                pfx,
                base,
                variant,
                block
            );
        }

        void generateAll(BlockFamily family) {
            family.getVariants().forEach(this::generate);
        }

        static void generateFrom(PrefixHelper pfx, BlockFamily family) {
            var base = family.getBaseBlock().asItem();
            new FamilyGeneratorBind(pfx, base).generateAll(family);
        }
    }

    // Generate a subfamily that also adds its outputs to its parent
    private record SubFamilyGeneratorBind(FamilyGeneratorBind inner, Item parent) {
        SubFamilyGeneratorBind(PrefixHelper pfx, Item base, Item parent) {
            this(new FamilyGeneratorBind(pfx, base), parent);
        }

        void generate(BlockFamily.Variant variant, Block block) {
            if (!validStonecutterVariant(variant)) {
                return;
            }
            inner.generate(variant, block);

            // Cracked can't be directly stone cut from the root block
            if (variant != BlockFamily.Variant.CRACKED) {
                generateStonecutter(inner.pfx, parent, variant, block);
            }
        }

        void generateAll(BlockFamily family) {
            inner.pfx
                .generateDynamicRecipe(
                    nameFromInAndOut(parent, inner.base),
                    new StonecutterRecipe("", Ingredient.of(parent), new ItemStack(inner.base))
                );

            family.getVariants().forEach(this::generate);
        }

        static void generateFrom(PrefixHelper pfx, BlockFamily family, Item parent) {
            var base = family.getBaseBlock().asItem();
            new SubFamilyGeneratorBind(pfx, base, parent).generateAll(family);
        }

    }

    private static boolean validStonecutterVariant(BlockFamily.Variant variant) {
        return switch (variant) {
            case BUTTON, PRESSURE_PLATE -> false;
            default -> true;
        };
    }

    private static void balcite(PrefixHelper pfx, PastelBlockFamilies.BalciteMegaFamily family) {
        var base = family.polished().getBaseBlock().asItem();
        FamilyGeneratorBind.generateFrom(pfx, family.polished());
        FamilyGeneratorBind.generateFrom(pfx, family.vanilla());
        SubFamilyGeneratorBind.generateFrom(pfx, family.bricks(), base);
        SubFamilyGeneratorBind.generateFrom(pfx, family.tiles(), base);
        SubFamilyGeneratorBind.generateFrom(pfx, family.planed(), base);
        generateStonecutter(pfx, base, 1, family.notched());
        generateStonecutter(pfx, base, 1, family.pillar());
        generateStonecutter(pfx, base, 1, family.crest());

    }

    private static void basalt(PrefixHelper pfx) {
        balcite(pfx, PastelBlockFamilies.BASALT_ALL.get());
    }

    private static void calcite(PrefixHelper pfx) {
        balcite(pfx, PastelBlockFamilies.CALCITE_ALL.get());
    }

    private static void shaleClay(PrefixHelper pfx) {
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.POLISHED_SHALE_CLAY.get());
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.WEATHERED_POLISHED_SHALE_CLAY.get());
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.EXPOSED_POLISHED_SHALE_CLAY.get());

        SubFamilyGeneratorBind
            .generateFrom(pfx, PastelBlockFamilies.SHALE_CLAY_BRICKS.get(), PastelBlocks.POLISHED_SHALE_CLAY.asItem());
        SubFamilyGeneratorBind
            .generateFrom(
                pfx,
                PastelBlockFamilies.WEATHERED_SHALE_CLAY_BRICKS.get(),
                PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY.asItem()
            );
        SubFamilyGeneratorBind
            .generateFrom(
                pfx,
                PastelBlockFamilies.EXPOSED_SHALE_CLAY_BRICKS.get(),
                PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.asItem()
            );

        SubFamilyGeneratorBind
            .generateFrom(pfx, PastelBlockFamilies.SHALE_CLAY_TILES.get(), PastelBlocks.POLISHED_SHALE_CLAY.asItem());
        SubFamilyGeneratorBind
            .generateFrom(
                pfx,
                PastelBlockFamilies.WEATHERED_SHALE_CLAY_TILES.get(),
                PastelBlocks.WEATHERED_POLISHED_SHALE_CLAY.asItem()
            );
        SubFamilyGeneratorBind
            .generateFrom(
                pfx,
                PastelBlockFamilies.EXPOSED_SHALE_CLAY_TILES.get(),
                PastelBlocks.EXPOSED_POLISHED_SHALE_CLAY.asItem()
            );
    }

    private static void dragonbone(PrefixHelper pfx) {
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.POLISHED_BONE_ASH.get());

        SubFamilyGeneratorBind
            .generateFrom(pfx, PastelBlockFamilies.BONE_ASH_BRICKS.get(), PastelBlocks.POLISHED_BONE_ASH.asItem());
        SubFamilyGeneratorBind
            .generateFrom(pfx, PastelBlockFamilies.BONE_ASH_TILES.get(), PastelBlocks.POLISHED_BONE_ASH.asItem());

        generateStonecutter(
            pfx,
            PastelBlocks.POLISHED_BONE_ASH.asItem(),
            1,
            PastelBlocks.POLISHED_BONE_ASH_PILLAR.get()
        );
    }

    private static void blackslag(PrefixHelper pfx) {
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.COBBLED_BLACKSLAG.get());
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.BLACKSLAG.get());
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.POLISHED_BLACKSLAG.get());

        SubFamilyGeneratorBind
            .generateFrom(pfx, PastelBlockFamilies.BLACKSLAG_BRICKS.get(), PastelBlocks.POLISHED_BLACKSLAG.asItem());
        SubFamilyGeneratorBind
            .generateFrom(pfx, PastelBlockFamilies.BLACKSLAG_TILES.get(), PastelBlocks.POLISHED_BLACKSLAG.asItem());

        generateStonecutter(
            pfx,
            PastelBlocks.POLISHED_BLACKSLAG.asItem(),
            1,
            PastelBlocks.POLISHED_BLACKSLAG_PILLAR.get()
        );
    }

    private static void pyrite(PrefixHelper pfx) {
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.PYRITE.get());
        // NOTE: The original datagenned files didn't include recipes directly from pyrite to tile stairs,slabs, or walls
        // if you want to change that, just swap this to a `SubFamilyGeneratorBind.generateFrom` instead, and remove the following gen call
        FamilyGeneratorBind.generateFrom(pfx, PastelBlockFamilies.PYRITE_TILES.get());
        generateStonecutter(pfx, PastelBlocks.PYRITE, 1, PastelBlocks.PYRITE_TILES);

        List
            .of(
                PastelBlocks.PYRITE_PANELING,
                PastelBlocks.PYRITE_PILE,
                PastelBlocks.PYRITE_PLATING,
                PastelBlocks.PYRITE_RELIEF,
                PastelBlocks.PYRITE_STACK,
                PastelBlocks.PYRITE_TUBING,
                PastelBlocks.PYRITE_VENT
            )
            .forEach(result -> generateStonecutter(pfx, PastelBlocks.PYRITE, 1, result));

    }
}
