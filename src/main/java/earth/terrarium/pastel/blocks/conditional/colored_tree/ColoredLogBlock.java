package earth.terrarium.pastel.blocks.conditional.colored_tree;

import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.blocks.PastelLogBlock;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.Block;

import java.util.Collection;
import java.util.Map;

public class ColoredLogBlock extends PastelLogBlock implements ColoredTree {

    private static final Map<InkColor, ColoredLogBlock> LOGS = new Object2ObjectArrayMap<>();

    protected final InkColor color;

    public ColoredLogBlock(Properties settings, InkColor color, Block sourceBlock) {
        super(settings, sourceBlock);
        this.color = color;
        LOGS.put(color, this);
    }

    @Override
    public InkColor getColor() {
        return this.color;
    }

    public static ColoredLogBlock byColor(InkColor color) {
        return LOGS.get(color);
    }

    public static Collection<ColoredLogBlock> all() {
        return LOGS.values();
    }

}
