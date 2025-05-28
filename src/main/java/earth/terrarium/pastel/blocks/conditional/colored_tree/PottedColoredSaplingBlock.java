package earth.terrarium.pastel.blocks.conditional.colored_tree;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.api.energy.color.InkColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Hashtable;
import java.util.Map;

public class PottedColoredSaplingBlock extends FlowerPotBlock implements RevelationAware, ColoredTree {
	
	protected final InkColor color;
	
	public PottedColoredSaplingBlock(Block content, Properties settings, InkColor color) {
		super(content, settings);
		this.color = color;
		RevelationAware.register(this);
	}

//	@Override
//	public MapCodec<? extends PottedColoredSaplingBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}
	
	@Override
	public ResourceLocation getCloakAdvancementIdentifier() {
		return ColoredTree.getTreeCloakAdvancementIdentifier(TreePart.SAPLING, this.color);
	}
	
	@Override
	public Map<BlockState, BlockState> getBlockStateCloaks() {
		Map<BlockState, BlockState> map = new Hashtable<>();
		map.put(this.defaultBlockState(), Blocks.POTTED_OAK_SAPLING.defaultBlockState());
		return map;
	}
	
	@Override
	public @Nullable Tuple<Item, Item> getItemCloak() {
		return null; // does not exist in item form
	}
	
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
}
