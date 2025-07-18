package earth.terrarium.pastel.blocks.conditional.colored_tree;

import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.FenceBlock;

import java.util.Map;

public class ColoredFenceBlock extends FenceBlock {

    private static final Map<InkColor, ColoredFenceBlock> BLOCKS = new Object2ObjectArrayMap<>();
    protected final InkColor color;

    public ColoredFenceBlock(Properties settings, InkColor color) {
        super(settings);
        this.color = color;
        BLOCKS.put(color, this);
    }

//	@Override
//	public MapCodec<? extends ColoredFenceBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

    public InkColor getColor() {
        return this.color;
    }

    public static ColoredFenceBlock byColor(InkColor color) {
        return BLOCKS.get(color);
    }

}
