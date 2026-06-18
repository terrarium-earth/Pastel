package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.RotatedPillarBlock;

import java.util.Collection;
import java.util.Map;

public class ColoredStrippedLogBlock extends RotatedPillarBlock implements ColoredTree {

    public static final MapCodec<ColoredStrippedLogBlock> CODEC = RecordCodecBuilder
        .mapCodec(
            instance -> instance
                .group(
                    propertiesCodec(),
                    InkColor.CODEC.fieldOf("color").forGetter(ColoredStrippedLogBlock::getColor)
                )
                .apply(instance, ColoredStrippedLogBlock::new)
        );

    private static final Map<InkColor, ColoredStrippedLogBlock> LOGS = new Object2ObjectArrayMap<>();

    protected final InkColor color;

    public ColoredStrippedLogBlock(Properties settings, InkColor color) {
        super(settings);
        this.color = color;
        LOGS.put(color, this);
    }

    @Override
    public MapCodec<? extends ColoredStrippedLogBlock> codec() {
        return CODEC;
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
