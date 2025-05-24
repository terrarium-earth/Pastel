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
		add.accept(SpectrumItems.VEGETAL, ALWAYS);
		add.accept(SpectrumItems.BONE_ASH, ALWAYS);

		add.accept(SpectrumBlocks.CLOVER, MEDIUM);
		add.accept(SpectrumBlocks.FOUR_LEAF_CLOVER, MEDIUM);

		add.accept(SpectrumItems.BLOOD_ORCHID_PETAL, LOW);
		add.accept(SpectrumBlocks.BLOOD_ORCHID, HIGH);

		add.accept(SpectrumItems.HIBERNATING_JADE_VINE_BULB, HIGH);
		add.accept(SpectrumItems.GERMINATED_JADE_VINE_BULB, HIGH);
		add.accept(SpectrumItems.JADE_VINE_PETALS, HIGH);
		add.accept(SpectrumBlocks.JADE_VINE_PETAL_BLOCK, ALWAYS);
		add.accept(SpectrumBlocks.JADE_VINE_PETAL_CARPET, HIGH);

		add.accept(SpectrumBlocks.VARIA_SPROUT, MEDIUM);
		add.accept(SpectrumBlocks.WEEPING_GALA_SPRIG, LOW);
		add.accept(SpectrumBlocks.WEEPING_GALA_LEAVES, LOW);
		add.accept(SpectrumBlocks.JADEITE_LOTUS_BULB, HIGH);
		add.accept(SpectrumBlocks.JADEITE_LOTUS_FLOWER, HIGHER);
		add.accept(SpectrumItems.JADEITE_PETALS, HIGH);
		add.accept(SpectrumBlocks.JADEITE_PETAL_BLOCK, ALWAYS);
		add.accept(SpectrumBlocks.JADEITE_PETAL_CARPET, HIGH);

		add.accept(SpectrumBlocks.SWEET_PEA, HIGH);
		add.accept(SpectrumBlocks.APRICOTTI, HIGH);
		add.accept(SpectrumBlocks.HUMMING_BELL, HIGH);
		add.accept(SpectrumBlocks.RESONANT_LILY, HIGH);
		add.accept(SpectrumBlocks.MOSS_BALL, MEDIUM);
		add.accept(SpectrumBlocks.GIANT_MOSS_BALL, ALWAYS);

		add.accept(SpectrumItems.NIGHTDEW_SPROUT, HIGH);
		add.accept(SpectrumItems.NECTARDEW_BURGEON, HIGHER);
		add.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_BULB, HIGH);
		add.accept(SpectrumBlocks.NEPHRITE_BLOSSOM_LEAVES, LOW);
		add.accept(SpectrumItems.FISSURE_PLUM, HIGH);

		add.accept(SpectrumItems.ALOE_LEAF, MEDIUM);
		add.accept(SpectrumBlocks.BRISTLE_SPROUTS, MEDIUM);
		add.accept(SpectrumItems.SAWBLADE_HOLLY_BERRY, HIGH);
		
		add.accept(SpectrumBlocks.SNAPPING_IVY, HIGH);
		add.accept(SpectrumBlocks.SMALL_RED_DRAGONJAG, LOW);
		add.accept(SpectrumBlocks.SMALL_YELLOW_DRAGONJAG, LOW);
		add.accept(SpectrumBlocks.SMALL_PINK_DRAGONJAG, LOW);
		add.accept(SpectrumBlocks.SMALL_PURPLE_DRAGONJAG, LOW);
		add.accept(SpectrumBlocks.SMALL_BLACK_DRAGONJAG, LOW);
		
		add.accept(SpectrumBlocks.SLATE_NOXSHROOM, HIGH);
		add.accept(SpectrumBlocks.EBONY_NOXSHROOM, HIGH);
		add.accept(SpectrumBlocks.IVORY_NOXSHROOM, HIGH);
		add.accept(SpectrumBlocks.CHESTNUT_NOXSHROOM, HIGH);
		
		add.accept(SpectrumBlocks.SLATE_NOXCAP_BLOCK, HIGHER);
		add.accept(SpectrumBlocks.SLATE_NOXCAP_GILLS, HIGHER);
		add.accept(SpectrumBlocks.EBONY_NOXCAP_BLOCK, HIGHER);
		add.accept(SpectrumBlocks.EBONY_NOXCAP_GILLS, HIGHER);
		add.accept(SpectrumBlocks.IVORY_NOXCAP_BLOCK, HIGHER);
		add.accept(SpectrumBlocks.IVORY_NOXCAP_GILLS, HIGHER);
		add.accept(SpectrumBlocks.CHESTNUT_NOXCAP_BLOCK, HIGHER);
		add.accept(SpectrumBlocks.CHESTNUT_NOXCAP_GILLS, HIGHER);
		
		for (InkColor color : InkColors.all()) {
			add.accept(ColoredSaplingBlock.byColor(color), LOW);
			add.accept(ColoredLeavesBlock.byColor(color), LOW);
		}
	}
	
}
