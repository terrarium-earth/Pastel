package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.Collection;
import java.util.Map;

public class ColoredWoodBlock extends RotatedPillarBlock implements ColoredTree {

	public static final MapCodec<ColoredWoodBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			propertiesCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(ColoredWoodBlock::getColor)
	).apply(instance, ColoredWoodBlock::new));
	
	private static final Map<InkColor, ColoredWoodBlock> WOOD = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public ColoredWoodBlock(Properties settings, InkColor color) {
		super(settings);
		this.color = color;
		WOOD.put(color, this);
	}

	@Override
	public MapCodec<? extends ColoredWoodBlock> codec() {
		return CODEC;
	}
	
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredWoodBlock byColor(InkColor color) {
		return WOOD.get(color);
	}
	
	public static Collection<ColoredWoodBlock> all() {
		return WOOD.values();
	}
	
}
