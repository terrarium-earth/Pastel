package earth.terrarium.pastel.data.databank;

import com.cmdpro.databank.datagen.HiddenDatagenProvider;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredLeavesBlock;
import earth.terrarium.pastel.blocks.conditional.colored_tree.ColoredTree;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class PastelHiddenProvider extends HiddenDatagenProvider {

	public PastelHiddenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
		super(output, lookupProvider, PastelCommon.MOD_ID, existingFileHelper);
	}

	@Override
	protected void gather() {
		addLeaves(PastelBlocks.BLACK_LEAVES.get());
		addLeaves(PastelBlocks.BLUE_LEAVES.get());
		addLeaves(PastelBlocks.BROWN_LEAVES.get());
		addLeaves(PastelBlocks.CYAN_LEAVES.get());
		addLeaves(PastelBlocks.GRAY_LEAVES.get());
		addLeaves(PastelBlocks.GREEN_LEAVES.get());
		addLeaves(PastelBlocks.LIGHT_BLUE_LEAVES.get());
		addLeaves(PastelBlocks.LIGHT_GRAY_LEAVES.get());
		addLeaves(PastelBlocks.LIME_LEAVES.get());
		addLeaves(PastelBlocks.MAGENTA_LEAVES.get());
		addLeaves(PastelBlocks.ORANGE_LEAVES.get());
		addLeaves(PastelBlocks.PINK_LEAVES.get());
		addLeaves(PastelBlocks.PURPLE_LEAVES.get());
		addLeaves(PastelBlocks.RED_LEAVES.get());
		addLeaves(PastelBlocks.WHITE_LEAVES.get());
		addLeaves(PastelBlocks.YELLOW_LEAVES.get());
	}
	public void addLeaves(Block block) {
		if (block instanceof ColoredLeavesBlock leaves) {
			ResourceLocation loc = BuiltInRegistries.BLOCK.getKey(block);
			ResourceLocation advancement = ColoredTree.getTreeCloakAdvancementIdentifier(ColoredTree.TreePart.LEAVES, leaves.getColor());
			createHidden(
					PastelCommon.locate("blocks/leaves/" + loc.getPath()),
					createBlockInstance(block, Blocks.OAK_LEAVES),
					createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
			);
			createHidden(
					PastelCommon.locate("items/leaves/" + loc.getPath()),
					createItemInstance(block.asItem(), Items.OAK_LEAVES),
					createAdvancementCondition(ResourceKey.create(Registries.ADVANCEMENT, advancement))
			);
		}
	}
}