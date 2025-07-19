package earth.terrarium.pastel.data.databank;

import com.cmdpro.databank.datagen.HiddenDatagenProvider;
import com.cmdpro.databank.hidden.types.BlockHiddenType;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.FluidLogging;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSaplingBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredTree;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.PottedColoredSaplingBlock;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class PastelHiddenProvider extends HiddenDatagenProvider {

	public PastelHiddenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, PastelCommon.MOD_ID, existingFileHelper);
	}

	@Override
	protected void gather() {
		allColors((color) -> addLog(ColoredLogBlock.byColor(color)));
		allColors((color) -> addLeaves(ColoredLeavesBlock.byColor(color)));
		allColors((color) -> addSapling(ColoredSaplingBlock.byColor(color)));
		allColors((color) -> addWood(ColoredWoodBlock.byColor(color)));
		allColors((color) -> addStrippedLog(ColoredStrippedLogBlock.byColor(color)));
		allColors((color) -> addStrippedWood(ColoredStrippedWoodBlock.byColor(color)));
		allColors((color) -> addPottedSapling(PottedColoredSaplingBlock.byColor(color)));
		
		hideBlockWithItem(PastelBlocks.RADIATING_ENDER.get(), Blocks.COBBLESTONE, PastelAdvancements.REVEAL_RADIATING_ENDER);
		hideBlockWithItem(PastelBlocks.FOUR_LEAF_CLOVER.get(), PastelBlocks.CLOVER.get(), PastelAdvancements.REVEAL_FOUR_LEAF_CLOVER);
		hideBlockWithItem(PastelBlocks.AMARANTH_BUSHEL.get(), Blocks.FERN, PastelAdvancements.REVEAL_AMARANTH);
		
		hideBlock(PastelBlocks.BLOOD_ORCHID.get(), Blocks.RED_TULIP, PastelCommon.locate("midgame/collect_blood_orchid_petal"));
		hideBlock(PastelBlocks.POTTED_BLOOD_ORCHID.get(), Blocks.POTTED_RED_TULIP, PastelCommon.locate("midgame/collect_blood_orchid_petal"));
		hideBlock(PastelBlocks.MERMAIDS_BRUSH.get(), Blocks.SEAGRASS, PastelAdvancements.REVEAL_MERMAIDS_BRUSH);
		hideBlock(PastelBlocks.POTTED_AMARANTH_BUSHEL.get(), Blocks.POTTED_FERN, PastelAdvancements.REVEAL_AMARANTH);
		hideBlock(PastelBlocks.STUCK_STORM_STONE.get(), Blocks.AIR, PastelAdvancements.REVEAL_STORM_STONES);
		
		hideItem(PastelItems.AMARANTH_GRAINS.asItem(), Items.LARGE_FERN, PastelAdvancements.REVEAL_AMARANTH);
		hideItem(PastelItems.MERMAIDS_GEM.asItem(), Items.KELP, PastelCommon.locate("place_pedestal"));
		hideItem(PastelItems.STORM_STONE.asItem(), Items.YELLOW_DYE, PastelAdvancements.REVEAL_STORM_STONES);
		
		createQuitoxicReeds(PastelBlocks.QUITOXIC_REEDS.get(), Blocks.SUGAR_CANE.asItem(), PastelAdvancements.REVEAL_QUITOXIC_REEDS);
	}
	public String getName(Block block) {
		return BuiltInRegistries.BLOCK.getKey(block).getPath();
	}
	public String getName(Item item) {
		return BuiltInRegistries.ITEM.getKey(item).getPath();
	}
	//Maybe rename this into something more general? Not sure what to change it to though
	private void createQuitoxicReeds(Block block, Item itemHiddenAs, ResourceLocation advancement) {
		BlockHiddenType.BlockHiddenOverride waterOverride = createOverride(StatePropertiesPredicate.Builder.properties().hasProperty(FluidLogging.ANY_INCLUDING_NONE, "water").build().orElseThrow(), Blocks.WATER);
		BlockHiddenType.BlockHiddenOverride crystalOverride = createOverride(StatePropertiesPredicate.Builder.properties().hasProperty(FluidLogging.ANY_INCLUDING_NONE, "liquid_crystal").build().orElseThrow(), PastelBlocks.LIQUID_CRYSTAL.get());
		createHidden(
				PastelCommon.locate("blocks/" + getName(block)),
				addOverride(addOverride(createBlockInstance(block, Blocks.AIR), waterOverride), crystalOverride),
				createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
		);
		hideItem(block.asItem(), itemHiddenAs, advancement);
	}
	public void hideBlockWithItem(Block block, Block hiddenAs, ResourceLocation advancement) {
		hideBlock(block, hiddenAs, advancement);
		hideItem(block.asItem(), hiddenAs.asItem(), advancement);
	}
	public void hideBlock(Block block, Block hiddenAs, ResourceLocation advancement) {
		createHidden(PastelCommon.locate("blocks/" + getName(block)), createBlockInstance(block, hiddenAs), createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement)));
	}
	public void hideItem(Item item, Item hiddenAs, ResourceLocation advancement) {
		createHidden(PastelCommon.locate("items/" + getName(item)), createItemInstance(item, hiddenAs), createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement)));
	}
	public void allColors(Consumer<InkColor> consumer) {
		for (InkColor i : InkColors.all()) {
			consumer.accept(i);
		}
	}
	public void addTreePart(Block block, Block hiddenAs, String directory, ColoredTree.TreePart part, boolean item) {
		if (block instanceof ColoredTree tree) {
			String name = getName(block);
			ResourceLocation advancement = ColoredTree.getTreeCloakAdvancementIdentifier(part, tree.getColor());
			createHidden(
					PastelCommon.locate("blocks/" + directory + "/" + name),
					createBlockInstance(block, hiddenAs),
					createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
			);
			createHidden(
					PastelCommon.locate("items/" + directory + "/" + name),
					createItemInstance(block.asItem(), hiddenAs.asItem()),
					createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
			);
		}
	}
	public void addTreePart(Block block, Block hiddenAs, String directory, ColoredTree.TreePart part) {
		addTreePart(block, hiddenAs, directory, part, true);
	}
	public void addLog(Block block) {
		addTreePart(block, Blocks.OAK_LOG, "logs", ColoredTree.TreePart.LOG);
	}
	public void addLeaves(Block block) {
		addTreePart(block, Blocks.OAK_LEAVES, "leaves", ColoredTree.TreePart.LEAVES);
	}
	public void addSapling(Block block) {
		addTreePart(block, Blocks.OAK_SAPLING, "saplings", ColoredTree.TreePart.SAPLING);
	}
	public void addWood(Block block) {
		addTreePart(block, Blocks.OAK_WOOD, "woods", ColoredTree.TreePart.WOOD);
	}
	public void addStrippedWood(Block block) {
		addTreePart(block, Blocks.STRIPPED_OAK_WOOD, "stripped_woods", ColoredTree.TreePart.STRIPPED_WOOD);
	}
	public void addStrippedLog(Block block) {
		addTreePart(block, Blocks.STRIPPED_OAK_LOG, "stripped_logs", ColoredTree.TreePart.STRIPPED_LOG);
	}
	public void addPottedSapling(Block block) {
		addTreePart(block, Blocks.POTTED_OAK_SAPLING, "potted_saplings", ColoredTree.TreePart.SAPLING, false);
	}
}