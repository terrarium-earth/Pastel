package earth.terrarium.pastel.blocks.conditional.colored_tree;

import de.dafuqs.revelationary.api.revelations.RevelationAware;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.blocks.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Collection;
import java.util.Hashtable;
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
