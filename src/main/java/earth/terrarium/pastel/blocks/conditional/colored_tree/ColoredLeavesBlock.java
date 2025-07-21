package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.registries.client.PastelColorProviders;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class ColoredLeavesBlock extends LeavesBlock implements ColoredTree {

	public static final MapCodec<ColoredLeavesBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			propertiesCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(ColoredLeavesBlock::getColor)
	).apply(instance, ColoredLeavesBlock::new));

	private static final Map<InkColor, ColoredLeavesBlock> LEAVES = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public ColoredLeavesBlock(Properties settings, InkColor color) {
		super(settings.lightLevel(s -> 9).hasPostProcess((state, level, pos) -> true).emissiveRendering((state, level, pos) -> true));
		this.color = color;
		LEAVES.put(color, this);
		registerDefaultState(defaultBlockState().setValue(NATURAL, false));
	}

	@Override
	public MapCodec<? extends ColoredLeavesBlock> codec() {
		return CODEC;
	}
	
	//@Override
	//public Map<BlockState, BlockState> getBlockStateCloaks() {
	//	Map<BlockState, BlockState> map = new Hashtable<>();
	//	for (int distance = 1; distance < 8; distance++) {
	//		BlockState leaves = this.defaultBlockState().setValue(LeavesBlock.DISTANCE, distance)
	//				.setValue(NATURAL, true);
//
	//		map.put(leaves.setValue(LeavesBlock.PERSISTENT, false).setValue(WATERLOGGED, false), Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.DISTANCE, distance).setValue(LeavesBlock.PERSISTENT, false).setValue(WATERLOGGED, false));
	//		map.put(leaves.setValue(LeavesBlock.PERSISTENT, false).setValue(WATERLOGGED, true), Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.DISTANCE, distance).setValue(LeavesBlock.PERSISTENT, false).setValue(WATERLOGGED, true));
	//
	//		map.put(leaves.setValue(LeavesBlock.PERSISTENT, true).setValue(WATERLOGGED, false), Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.DISTANCE, distance).setValue(LeavesBlock.PERSISTENT, true).setValue(WATERLOGGED, false));
	//		map.put(leaves.setValue(LeavesBlock.PERSISTENT, true).setValue(WATERLOGGED, true), Blocks.OAK_LEAVES.defaultBlockState().setValue(LeavesBlock.DISTANCE, distance).setValue(LeavesBlock.PERSISTENT, true).setValue(WATERLOGGED, true));
	//	}
	//	return map;
	//}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(NATURAL);
	}

	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredLeavesBlock byColor(InkColor color) {
		return LEAVES.get(color);
	}
	
	public static Collection<ColoredLeavesBlock> all() {
		return LEAVES.values();
	}
	
}
