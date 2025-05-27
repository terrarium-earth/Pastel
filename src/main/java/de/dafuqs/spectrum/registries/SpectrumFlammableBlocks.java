package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredFenceBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredFenceGateBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredLeavesBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredLogBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredPlankBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredSlabBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredSporeBlossomBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredStairsBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredStrippedLogBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredStrippedWoodBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredWoodBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;

public class SpectrumFlammableBlocks {

	private static void add(Block block, int igniteOdds, int burnOdds) {
		((FireBlock) Blocks.FIRE).setFlammable(block, igniteOdds, burnOdds);
	}

	public static void register() {

		// ignite odds, burn odds
		add(SpectrumBlocks.CLOVER.get(), 60, 100);

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

		add(SpectrumBlocks.VEGETAL_BLOCK.get(), 30, 60);

		add(SpectrumBlocks.SWEET_PEA.get(), 60, 100);
		add(SpectrumBlocks.APRICOTTI.get(), 60, 100);
		add(SpectrumBlocks.HUMMING_BELL.get(), 60, 100);
		add(SpectrumBlocks.BLOOD_ORCHID.get(), 60, 100);
		add(SpectrumBlocks.RESONANT_LILY.get(), 60, 100);

		add(SpectrumBlocks.NEPHRITE_BLOSSOM_STEM.get(), 5, 5);
		add(SpectrumBlocks.NEPHRITE_BLOSSOM_LEAVES.get(), 30, 60);
		add(SpectrumBlocks.JADE_VINES.get(), 5, 5);
		add(SpectrumBlocks.JADEITE_LOTUS_STEM.get(), 5, 5);
		add(SpectrumBlocks.JADEITE_LOTUS_FLOWER.get(), 30, 60);
		add(SpectrumBlocks.JADE_VINE_PETAL_BLOCK.get(), 30, 60);
		add(SpectrumBlocks.JADE_VINE_PETAL_CARPET.get(), 60, 20);
		add(SpectrumBlocks.JADEITE_PETAL_BLOCK.get(), 30, 60);
		add(SpectrumBlocks.JADEITE_PETAL_CARPET.get(), 60, 20);

		add(SpectrumBlocks.NIGHTDEW.get(), 15, 60);
		add(SpectrumBlocks.ABYSSAL_VINES.get(), 15, 60);

		add(SpectrumBlocks.SLATE_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_SLATE_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.SLATE_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_SLATE_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.SLATE_NOXCAP_BLOCK.get(), 5, 5);
		add(SpectrumBlocks.SLATE_NOXCAP_GILLS.get(), 5, 5);
		add(SpectrumBlocks.SLATE_NOXWOOD_PLANKS.get(), 5, 20);
		add(SpectrumBlocks.SLATE_NOXWOOD_SLAB.get(), 5, 20);
		add(SpectrumBlocks.SLATE_NOXWOOD_FENCE.get(), 5, 20);
		add(SpectrumBlocks.SLATE_NOXWOOD_FENCE_GATE.get(), 5, 20);
		add(SpectrumBlocks.SLATE_NOXWOOD_STAIRS.get(), 5, 20);
		add(SpectrumBlocks.SLATE_NOXWOOD_PILLAR.get(), 5, 20);
		add(SpectrumBlocks.SLATE_NOXWOOD_LIGHT.get(), 5, 20);


		add(SpectrumBlocks.EBONY_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_EBONY_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.EBONY_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_EBONY_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.EBONY_NOXCAP_BLOCK.get(), 5, 5);
		add(SpectrumBlocks.EBONY_NOXCAP_GILLS.get(), 5, 5);
		add(SpectrumBlocks.EBONY_NOXWOOD_PLANKS.get(), 5, 20);
		add(SpectrumBlocks.EBONY_NOXWOOD_SLAB.get(), 5, 20);
		add(SpectrumBlocks.EBONY_NOXWOOD_FENCE.get(), 5, 20);
		add(SpectrumBlocks.EBONY_NOXWOOD_FENCE_GATE.get(), 5, 20);
		add(SpectrumBlocks.EBONY_NOXWOOD_STAIRS.get(), 5, 20);
		add(SpectrumBlocks.EBONY_NOXWOOD_PILLAR.get(), 5, 20);
		add(SpectrumBlocks.EBONY_NOXWOOD_LIGHT.get(), 5, 20);

		add(SpectrumBlocks.IVORY_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_IVORY_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.IVORY_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_IVORY_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.IVORY_NOXCAP_BLOCK.get(), 5, 5);
		add(SpectrumBlocks.IVORY_NOXCAP_GILLS.get(), 5, 5);
		add(SpectrumBlocks.IVORY_NOXWOOD_PLANKS.get(), 5, 20);
		add(SpectrumBlocks.IVORY_NOXWOOD_SLAB.get(), 5, 20);
		add(SpectrumBlocks.IVORY_NOXWOOD_FENCE.get(), 5, 20);
		add(SpectrumBlocks.IVORY_NOXWOOD_FENCE_GATE.get(), 5, 20);
		add(SpectrumBlocks.IVORY_NOXWOOD_STAIRS.get(), 5, 20);
		add(SpectrumBlocks.IVORY_NOXWOOD_PILLAR.get(), 5, 20);
		add(SpectrumBlocks.IVORY_NOXWOOD_LIGHT.get(), 5, 20);

		add(SpectrumBlocks.CHESTNUT_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_STEM.get(), 5, 5);
		add(SpectrumBlocks.CHESTNUT_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.STRIPPED_CHESTNUT_NOXCAP_HYPHAE.get(), 5, 5);
		add(SpectrumBlocks.CHESTNUT_NOXCAP_BLOCK.get(), 5, 5);
		add(SpectrumBlocks.CHESTNUT_NOXCAP_GILLS.get(), 5, 5);
		add(SpectrumBlocks.CHESTNUT_NOXWOOD_PLANKS.get(), 5, 20);
		add(SpectrumBlocks.CHESTNUT_NOXWOOD_SLAB.get(), 5, 20);
		add(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE.get(), 5, 20);
		add(SpectrumBlocks.CHESTNUT_NOXWOOD_FENCE_GATE.get(), 5, 20);
		add(SpectrumBlocks.CHESTNUT_NOXWOOD_STAIRS.get(), 5, 20);
		add(SpectrumBlocks.CHESTNUT_NOXWOOD_PILLAR.get(), 5, 20);
		add(SpectrumBlocks.CHESTNUT_NOXWOOD_LIGHT.get(), 5, 20);

		add(SpectrumBlocks.WEEPING_GALA_LOG.get(), 2, 2);
		add(SpectrumBlocks.WEEPING_GALA_LEAVES.get(), 10, 20);
		add(SpectrumBlocks.WEEPING_GALA_PLANKS.get(), 2, 8);
		add(SpectrumBlocks.WEEPING_GALA_SLAB.get(), 2, 8);
		add(SpectrumBlocks.WEEPING_GALA_FENCE.get(), 2, 8);
		add(SpectrumBlocks.WEEPING_GALA_FENCE_GATE.get(), 2, 8);
		add(SpectrumBlocks.WEEPING_GALA_STAIRS.get(), 2, 8);
		add(SpectrumBlocks.STRIPPED_WEEPING_GALA_LOG.get(), 2, 8);
		add(SpectrumBlocks.STRIPPED_WEEPING_GALA_WOOD.get(), 2, 8);
		add(SpectrumBlocks.WEEPING_GALA_PILLAR.get(), 2, 8);
		add(SpectrumBlocks.WEEPING_GALA_LIGHT.get(), 2, 8);
		add(SpectrumBlocks.WEEPING_GALA_LAMP.get(), 2, 8);

		add(SpectrumBlocks.MOSS_BALL.get(), 30, 60);
		add(SpectrumBlocks.GIANT_MOSS_BALL.get(), 30, 60);
	}

}
