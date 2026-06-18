package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import org.apache.commons.lang3.NotImplementedException;

import java.util.Collection;
import java.util.Map;

public class ColoredSaplingBlock extends SaplingBlock implements ColoredTree {

    /*public static final MapCodec<ColoredSaplingBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
    		createSettingsCodec(),
    		InkColor.CODEC.fieldOf("color").forGetter(ColoredSaplingBlock::getColor)
    ).apply(instance, ColoredSaplingBlock::new));*/

    private static final Map<InkColor, ColoredSaplingBlock> SAPLINGS = new Object2ObjectArrayMap<>();

    protected final InkColor color;

    public ColoredSaplingBlock(Properties settings, InkColor color, TreeGrower saplingGenerator) {
        super(saplingGenerator, settings);
        this.color = color;
        SAPLINGS.put(color, this);
    }

    @Override
    public MapCodec<? extends ColoredSaplingBlock> codec() {
        throw new NotImplementedException();
    }

    @Override
    public InkColor getColor() {
        return this.color;
    }

    public static ColoredSaplingBlock byColor(InkColor color) {
        return SAPLINGS.get(color);
    }

    public static Collection<ColoredSaplingBlock> all() {
        return SAPLINGS.values();
    }

}
