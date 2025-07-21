package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;

public class ColoredStrippedLogBlock extends RotatedPillarBlock implements ColoredTree {

	public static final MapCodec<ColoredStrippedLogBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			propertiesCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(ColoredStrippedLogBlock::getColor)
	).apply(instance, ColoredStrippedLogBlock::new));
	
	private static final Map<InkColor, ColoredStrippedLogBlock> LOGS = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public ColoredStrippedLogBlock(Properties settings, InkColor color) {
		super(settings);
		this.color = color;
		LOGS.put(color, this);
		registerDefaultState(defaultBlockState().setValue(NATURAL, false));
	}

	@Override
	public MapCodec<? extends ColoredStrippedLogBlock> codec() {
		return CODEC;
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(NATURAL);
	}
	
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredStrippedLogBlock byColor(InkColor color) {
		return LOGS.get(color);
	}
	
	public static Collection<ColoredStrippedLogBlock> all() {
		return LOGS.values();
	}
	
}
