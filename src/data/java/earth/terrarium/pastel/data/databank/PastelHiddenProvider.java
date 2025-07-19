package earth.terrarium.pastel.data.databank;

import com.cmdpro.databank.datagen.HiddenDatagenProvider;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredSaplingBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedLogBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredStrippedWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredTree;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredWoodBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.PottedColoredSaplingBlock;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
	}
	public void allColors(Consumer<InkColor> consumer) {
		for (InkColor i : InkColors.all()) {
			consumer.accept(i);
		}
	}
	public void addTreePart(Block block, Block hiddenAs, String directory, ColoredTree.TreePart part, boolean item) {
		if (block instanceof ColoredTree tree) {
			ResourceLocation loc = BuiltInRegistries.BLOCK.getKey(block);
			ResourceLocation advancement = ColoredTree.getTreeCloakAdvancementIdentifier(part, tree.getColor());
			createHidden(
					PastelCommon.locate("blocks/" + directory + "/" + loc.getPath()),
					createBlockInstance(block, hiddenAs),
					createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
			);
			createHidden(
					PastelCommon.locate("items/" + directory + "/" + loc.getPath()),
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