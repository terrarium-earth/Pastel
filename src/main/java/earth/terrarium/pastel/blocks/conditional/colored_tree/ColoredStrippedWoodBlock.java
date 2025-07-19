package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.Map;

public class ColoredStrippedWoodBlock extends RotatedPillarBlock implements ColoredTree {

	public static final MapCodec<ColoredStrippedWoodBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
			propertiesCodec(),
			InkColor.CODEC.fieldOf("color").forGetter(ColoredStrippedWoodBlock::getColor)
	).apply(instance, ColoredStrippedWoodBlock::new));
	
	private static final Map<InkColor, ColoredStrippedWoodBlock> WOOD = new Object2ObjectArrayMap<>();
	protected final InkColor color;
	
	public ColoredStrippedWoodBlock(Properties settings, InkColor color) {
		super(settings);
		this.color = color;
		WOOD.put(color, this);
	}

	@Override
	public MapCodec<? extends ColoredStrippedWoodBlock> codec() {
		return CODEC;
	}
	
	@Override
	public InkColor getColor() {
		return this.color;
	}
	
	public static ColoredStrippedWoodBlock byColor(InkColor color) {
		return WOOD.get(color);
	}
	
	public static Collection<ColoredStrippedWoodBlock> all() {
		return WOOD.values();
	}
	
}
