package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSaplingBlock;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;

import java.util.function.BiConsumer;

public class PastelCompostableBlocks {
	
	private static final float LOW = 0.3F;
	private static final float MEDIUM = 0.5F;
	private static final float HIGH = 0.65F;
	private static final float HIGHER = 0.85F;
	private static final float ALWAYS = 1.0F;
	
	public static void register(DataMapProvider.Builder<Compostable, Item> builder) {
		register((item, chance) -> builder.add(item.asItem().builtInRegistryHolder(), new Compostable(chance), false));
	}

	private static void register(BiConsumer<ItemLike, Float> add) {
		add.accept(PastelItems.VEGETAL.get(), ALWAYS);
		add.accept(PastelItems.BONE_ASH.get(), ALWAYS);

		add.accept(PastelBlocks.CLOVER.get(), MEDIUM);
		add.accept(PastelBlocks.FOUR_LEAF_CLOVER.get(), MEDIUM);

		add.accept(PastelItems.BLOOD_ORCHID_PETAL.get(), LOW);
		add.accept(PastelBlocks.BLOOD_ORCHID.get(), HIGH);

		add.accept(PastelItems.HIBERNATING_JADE_VINE_BULB.get(), HIGH);
		add.accept(PastelItems.GERMINATED_JADE_VINE_BULB.get(), HIGH);
		add.accept(PastelItems.JADE_VINE_PETALS.get(), HIGH);
		add.accept(PastelBlocks.JADE_VINE_PETAL_BLOCK.get(), ALWAYS);
		add.accept(PastelBlocks.JADE_VINE_PETAL_CARPET.get(), HIGH);

		add.accept(PastelBlocks.VARIA_SPROUT.get(), MEDIUM);
		add.accept(PastelBlocks.WEEPING_GALA_SPRIG.get(), LOW);
		add.accept(PastelBlocks.WEEPING_GALA_LEAVES.get(), LOW);
		add.accept(PastelBlocks.JADEITE_LOTUS_BULB.get(), HIGH);
		add.accept(PastelBlocks.JADEITE_LOTUS_FLOWER.get(), HIGHER);
		add.accept(PastelItems.JADEITE_PETALS.get(), HIGH);
		add.accept(PastelBlocks.JADEITE_PETAL_BLOCK.get(), ALWAYS);
		add.accept(PastelBlocks.JADEITE_PETAL_CARPET.get(), HIGH);

		add.accept(PastelBlocks.SWEET_PEA.get(), HIGH);
		add.accept(PastelBlocks.APRICOTTI.get(), HIGH);
		add.accept(PastelBlocks.HUMMING_BELL.get(), HIGH);
		add.accept(PastelBlocks.RESONANT_LILY.get(), HIGH);
		add.accept(PastelBlocks.MOSS_BALL.get(), MEDIUM);
		add.accept(PastelBlocks.GIANT_MOSS_BALL.get(), ALWAYS);

		add.accept(PastelItems.NIGHTDEW_SPROUT.get(), HIGH);
		add.accept(PastelItems.NECTARDEW_BURGEON.get(), HIGHER);
		add.accept(PastelBlocks.NEPHRITE_BLOSSOM_BULB.get(), HIGH);
		add.accept(PastelBlocks.NEPHRITE_BLOSSOM_LEAVES.get(), LOW);
		add.accept(PastelItems.FISSURE_PLUM.get(), HIGH);

		add.accept(PastelItems.ALOE_LEAF.get(), MEDIUM);
		add.accept(PastelBlocks.BRISTLE_SPROUTS.get(), MEDIUM);
		add.accept(PastelItems.SAWBLADE_HOLLY_BERRY.get(), HIGH);
		
		add.accept(PastelBlocks.SNAPPING_IVY.get(), HIGH);
		add.accept(PastelBlocks.SMALL_RED_DRAGONJAG.get(), LOW);
		add.accept(PastelBlocks.SMALL_YELLOW_DRAGONJAG.get(), LOW);
		add.accept(PastelBlocks.SMALL_PINK_DRAGONJAG.get(), LOW);
		add.accept(PastelBlocks.SMALL_PURPLE_DRAGONJAG.get(), LOW);
		add.accept(PastelBlocks.SMALL_BLACK_DRAGONJAG.get(), LOW);
		
		add.accept(PastelBlocks.SLATE_NOXSHROOM.get(), HIGH);
		add.accept(PastelBlocks.EBONY_NOXSHROOM.get(), HIGH);
		add.accept(PastelBlocks.IVORY_NOXSHROOM.get(), HIGH);
		add.accept(PastelBlocks.CHESTNUT_NOXSHROOM.get(), HIGH);
		
		add.accept(PastelBlocks.SLATE_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(PastelBlocks.SLATE_NOXCAP_GILLS.get(), HIGHER);
		add.accept(PastelBlocks.EBONY_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(PastelBlocks.EBONY_NOXCAP_GILLS.get(), HIGHER);
		add.accept(PastelBlocks.IVORY_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(PastelBlocks.IVORY_NOXCAP_GILLS.get(), HIGHER);
		add.accept(PastelBlocks.CHESTNUT_NOXCAP_BLOCK.get(), HIGHER);
		add.accept(PastelBlocks.CHESTNUT_NOXCAP_GILLS.get(), HIGHER);
		
		for (InkColor color : InkColors.all()) {
			add.accept(ColoredSaplingBlock.byColor(color), LOW);
			add.accept(ColoredLeavesBlock.byColor(color), LOW);
		}
	}
	
}
