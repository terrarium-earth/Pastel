package earth.terrarium.pastel.blocks.conditional.colored_tree;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.registries.PastelBlockSetTypes;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.ButtonBlock;

import java.util.Map;

public class ColoredWoodenButtonBlock extends ButtonBlock {

    private static final Map<InkColor, ColoredWoodenButtonBlock> BLOCKS = new Object2ObjectArrayMap<>();

    protected final InkColor color;

    public ColoredWoodenButtonBlock(Properties settings, InkColor color) {
        super(PastelBlockSetTypes.COLORED_WOOD, 30, settings);
        this.color = color;
        BLOCKS.put(color, this);
    }

//	@Override
//	public MapCodec<? extends ColoredWoodenButtonBlock> getCodec() {
//		//TODO: Make the codec
//		return null;
//	}

    public InkColor getColor() {
        return this.color;
    }

    public static ColoredWoodenButtonBlock byColor(InkColor color) {
        return BLOCKS.get(color);
    }

}
