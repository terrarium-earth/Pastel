package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredFenceBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredFenceGateBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredPlankBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSlabBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSporeBlossomBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStairsBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredWoodBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class PastelFlammableBlocks {

    private static void add(Block block, int igniteOdds, int burnOdds) {
        ((FireBlock) Blocks.FIRE).setFlammable(block, igniteOdds, burnOdds);
    }

    public static void register() {

        // ignite odds, burn odds
        add(PastelBlocks.CLOVER.get(), 60, 100);

        for (InkColor color : InkColors.all()) {
            add(ColoredLogBlock.byColor(color), 5, 5);
            add(ColoredWoodBlock.byColor(color), 5, 5);
            add(ColoredLeavesBlock.byColor(color), 30, 60);
            add(ColoredPlankBlock.byColor(color), 5, 20);
            add(ColoredSlabBlock.byColor(color), 5, 20);
            add(ColoredFenceBlock.byColor(color), 5, 20);
            add(ColoredFenceGateBlock.byColor(color), 5, 20);
            add(ColoredStairsBlock.byColor(color), 5, 20);
            add(ColoredSporeBlossomBlock.byColor(color), 60, 100);
            add(ColoredStrippedLogBlock.byColor(color), 5, 20);
            add(ColoredStrippedWoodBlock.byColor(color), 5, 20);
        }

        add(PastelBlocks.VEGETAL_BLOCK.get(), 30, 60);

        add(PastelBlocks.SWEET_PEA.get(), 60, 100);
        add(PastelBlocks.APRICOTTI.get(), 60, 100);
        add(PastelBlocks.HUMMING_BELL.get(), 60, 100);
        add(PastelBlocks.BLOOD_ORCHID.get(), 60, 100);
        add(PastelBlocks.RESONANT_LILY.get(), 60, 100);

        add(PastelBlocks.NEPHRITE_BLOSSOM_STEM.get(), 5, 5);
        add(PastelBlocks.NEPHRITE_BLOSSOM_LEAVES.get(), 30, 60);
        add(PastelBlocks.JADE_VINES.get(), 5, 5);
        add(PastelBlocks.JADEITE_LOTUS_STEM.get(), 5, 5);
        add(PastelBlocks.JADEITE_LOTUS_FLOWER.get(), 30, 60);
        add(PastelBlocks.JADE_PETAL_BLOCK.get(), 30, 60);
        add(PastelBlocks.JADE_PETAL_CARPET.get(), 60, 20);
        add(PastelBlocks.JADEITE_PETAL_BLOCK.get(), 30, 60);
        add(PastelBlocks.JADEITE_PETAL_CARPET.get(), 60, 20);

        add(PastelBlocks.NIGHTDEW.get(), 15, 60);
        add(PastelBlocks.ABYSSAL_VINES.get(), 15, 60);

        add(PastelBlocks.SLATE_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.SLATE_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.SLATE_NOXCAP_BLOCK.get(), 5, 5);
        add(PastelBlocks.SLATE_NOXCAP_GILLS.get(), 5, 5);
        add(PastelBlocks.SLATE_NOXWOOD_PLANKS.get(), 5, 20);
        add(PastelBlocks.SLATE_NOXWOOD_SLAB.get(), 5, 20);
        add(PastelBlocks.SLATE_NOXWOOD_FENCE.get(), 5, 20);
        add(PastelBlocks.SLATE_NOXWOOD_FENCE_GATE.get(), 5, 20);
        add(PastelBlocks.SLATE_NOXWOOD_STAIRS.get(), 5, 20);
        add(PastelBlocks.SLATE_NOXWOOD_PILLAR.get(), 5, 20);
        add(PastelBlocks.SLATE_NOXWOOD_LIGHT.get(), 5, 20);


        add(PastelBlocks.EBONY_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.EBONY_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.EBONY_NOXCAP_BLOCK.get(), 5, 5);
        add(PastelBlocks.EBONY_NOXCAP_GILLS.get(), 5, 5);
        add(PastelBlocks.EBONY_NOXWOOD_PLANKS.get(), 5, 20);
        add(PastelBlocks.EBONY_NOXWOOD_SLAB.get(), 5, 20);
        add(PastelBlocks.EBONY_NOXWOOD_FENCE.get(), 5, 20);
        add(PastelBlocks.EBONY_NOXWOOD_FENCE_GATE.get(), 5, 20);
        add(PastelBlocks.EBONY_NOXWOOD_STAIRS.get(), 5, 20);
        add(PastelBlocks.EBONY_NOXWOOD_PILLAR.get(), 5, 20);
        add(PastelBlocks.EBONY_NOXWOOD_LIGHT.get(), 5, 20);

        add(PastelBlocks.IVORY_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.IVORY_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.IVORY_NOXCAP_BLOCK.get(), 5, 5);
        add(PastelBlocks.IVORY_NOXCAP_GILLS.get(), 5, 5);
        add(PastelBlocks.IVORY_NOXWOOD_PLANKS.get(), 5, 20);
        add(PastelBlocks.IVORY_NOXWOOD_SLAB.get(), 5, 20);
        add(PastelBlocks.IVORY_NOXWOOD_FENCE.get(), 5, 20);
        add(PastelBlocks.IVORY_NOXWOOD_FENCE_GATE.get(), 5, 20);
        add(PastelBlocks.IVORY_NOXWOOD_STAIRS.get(), 5, 20);
        add(PastelBlocks.IVORY_NOXWOOD_PILLAR.get(), 5, 20);
        add(PastelBlocks.IVORY_NOXWOOD_LIGHT.get(), 5, 20);

        add(PastelBlocks.CHESTNUT_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(), 5, 5);
        add(PastelBlocks.CHESTNUT_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE.get(), 5, 5);
        add(PastelBlocks.CHESTNUT_NOXCAP_BLOCK.get(), 5, 5);
        add(PastelBlocks.CHESTNUT_NOXCAP_GILLS.get(), 5, 5);
        add(PastelBlocks.CHESTNUT_NOXWOOD_PLANKS.get(), 5, 20);
        add(PastelBlocks.CHESTNUT_NOXWOOD_SLAB.get(), 5, 20);
        add(PastelBlocks.CHESTNUT_NOXWOOD_FENCE.get(), 5, 20);
        add(PastelBlocks.CHESTNUT_NOXWOOD_FENCE_GATE.get(), 5, 20);
        add(PastelBlocks.CHESTNUT_NOXWOOD_STAIRS.get(), 5, 20);
        add(PastelBlocks.CHESTNUT_NOXWOOD_PILLAR.get(), 5, 20);
        add(PastelBlocks.CHESTNUT_NOXWOOD_LIGHT.get(), 5, 20);

        add(PastelBlocks.MOSS_BALL.get(), 30, 60);
        add(PastelBlocks.GIANT_MOSS_BALL.get(), 30, 60);
    }

}
