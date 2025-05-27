package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredLeavesBlock;
import de.dafuqs.spectrum.blocks.conditional.colored_tree.ColoredSaplingBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;

import java.util.function.BiConsumer;

public class SpectrumCompostableBlocks {
	
	private static final float LOW = 0.3F;
	private static final float MEDIUM = 0.5F;
	private static final float HIGH = 0.65F;
	private static final float HIGHER = 0.85F;
	private static final float ALWAYS = 1.0F;
	
	public static void register(DataMapProvider.Builder<Compostable, Item> builder) {
		register((item, chance) -> builder.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false));
	}

	private static void register(BiConsumer<ItemLike, Float> add) {
		add.accept(SpectrumItems.VEGETAL.get(), ALWAYS);
		add.accept(SpectrumItems.BONE_ASH.get(), ALWAYS);

		add.accept(SpectrumBlocks.CLOVER.get(), MEDIUM);
		add.accept(SpectrumBlocks.FOUR_LEAF_CLOVER.get(), MEDIUM);

		add.accept(SpectrumItems.BLOOD_ORCHID_PETAL.get(), LOW);
		add.accept(SpectrumBlocks.BLOOD_ORCHID.get(), HIGH);

		add.accept(SpectrumItems.HIBERNATING_JADE_VINE_BULB.get(), HIGH);
		add.accept(SpectrumItems.GERMINATED_JADE_VINE_BULB.get(), HIGH);
		add.accept(SpectrumItems.JADE_VINE_PETALS.get(), HIGH);
		add.accept(SpectrumBlocks.JADE_VINE_PETAL_BLOCK.get(), ALWAYS);
		add.accept(SpectrumBlocks.JADE_VINE_PETAL_CARPET.get(), HIGH);

		add.accept(SpectrumBlocks.VARIA_SPROUT.get(), MEDIUM);
		add.accept(SpectrumBlocks.WEEPING_GALA_SPRIG.get(), LOW);
		add.accept(SpectrumBlocks.WEEPING_GALA_LEAVES.get(), LOW);
		add.accept(SpectrumBlocks.JADEITE_LOTUS_BULB.get(), HIGH);
		add.accept(SpectrumBlocks.JADEITE_LOTUS_FLOWER.get(), HIGHER);
		add.accept(SpectrumItems.JADEITE_PETALS.get(), HIGH);
		add.accept(SpectrumBlocks.JADEITE_PETAL_BLOCK.get(), ALWAYS);
		add.accept(SpectrumBlocks.JADEITE_PETAL_CARPET.get(), HIGH);

		add.accept(SpectrumBlocks.SWEET_PEA.get(), HIGH);
		add.accept(SpectrumBlocks.APRICOTTI.get(), HIGH);
		add.accept(SpectrumBlocks.HUMMING_BELL.get(), HIGH);
		add.accept(SpectrumBlocks.RESONANT_LILY.get(), HIGH);
		add.accept(SpectrumBlocks.MOSS_BALL.get(), MEDIUM);
		add.accept(SpectrumBlocks.GIANT_MOSS_BALL.get(), ALWAYS);

		add.accept(SpectrumItems.NIGHTDEW_SPROUT.get(), HIGH);
		add.accept(SpectrumItems.NECTARDEW_BURGEON.get(), HIGHER);
		add.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_BULB.get(), HIGH);
		add.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_LEAVES.get(), LOW);
		add.accept(SpectrumItems.FISSURE_PLUM.get(), HIGH);

		add.accept(SpectrumItems.ALOE_LEAF.get(), MEDIUM);
		add.accept(SpectrumBlocks.BRISTLE_SPROUTS.get(), MEDIUM);
		add.accept(SpectrumItems.SAWBLADE_HOLLY_BERRY.get(), HIGH);
		
		add.accept(SpectrumBlocks.SNAPPING_IVY.get(), HIGH);
		add.accept(SpectrumBlocks.SMALL_RED_DRAGONJAG.get(), LOW);
		add.accept(SpectrumBlocks.SMALL_YELLOW_DRAGONJAG.get(), LOW);
		add.accept(SpectrumBlocks.SMALL_PINK_DRAGONJAG.get(), LOW);
		add.accept(SpectrumBlocks.SMALL_PURPLE_DRAGONJAG.get(), LOW);
		add.accept(SpectrumBlocks.SMALL_BLACK_DRAGONJAG.get(), LOW);
		
		add.accept(SpectrumBlocks.SLATE_NOXSHROOM.get(), HIGH);
		add.accept(SpectrumBlocks.EBONY_NOXSHROOM.get(), HIGH);
		add.accept(SpectrumBlocks.IVORY_NOXSHROOM.get(), HIGH);
		add.accept(SpectrumBlocks.CHESTNUT_NOXSHROOM.get(), HIGH);
		
		add.accept(SpectrumBlocks.SLATE_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(SpectrumBlocks.SLATE_NOXCAP_GILLS.get(), HIGHER);
		add.accept(SpectrumBlocks.EBONY_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(SpectrumBlocks.EBONY_NOXCAP_GILLS.get(), HIGHER);
		add.accept(SpectrumBlocks.IVORY_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(SpectrumBlocks.IVORY_NOXCAP_GILLS.get(), HIGHER);
		add.accept(SpectrumBlocks.CHESTNUT_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(SpectrumBlocks.CHESTNUT_NOXCAP_GILLS.get(), HIGHER);
		
		for (InkColor color : InkColors.all()) {
			add.accept(ColoredSaplingBlock.byColor(color), LOW);
			add.accept(ColoredLeavesBlock.byColor(color), LOW);
		}
	}
	
}
