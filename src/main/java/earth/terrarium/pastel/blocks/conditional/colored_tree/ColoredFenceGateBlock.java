package earth.terrarium.pastel.blocks.conditional.colored_tree;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.registries.PastelWoodTypes;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.FenceGateBlock;

import java.util.Map;

public class ColoredFenceGateBlock extends FenceGateBlock {

    private static final Map<InkColor, ColoredFenceGateBlock> BLOCKS = new Object2ObjectArrayMap<>();

    protected final InkColor color;

    public ColoredFenceGateBlock(Properties settings, InkColor color) {
        super(PastelWoodTypes.COLORED_WOOD, settings);
        this.color = color;
        BLOCKS.put(color, this);
    }

//	@Override
//	public MapCodec<? extends ColoredFenceGateBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

    public InkColor getColor() {
        return this.color;
    }

    public static ColoredFenceGateBlock byColor(InkColor color) {
        return BLOCKS.get(color);
    }

}
