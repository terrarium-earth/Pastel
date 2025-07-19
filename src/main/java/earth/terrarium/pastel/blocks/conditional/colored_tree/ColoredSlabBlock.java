package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.SlabBlock;

import java.util.Map;

public class ColoredSlabBlock extends SlabBlock {

    public static final MapCodec<ColoredSlabBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                                                                                               propertiesCodec(),
                                                                                                               InkColor.CODEC.fieldOf("color")
                                                                                                                             .forGetter(ColoredSlabBlock::getColor)
                                                                                                           )
                                                                                                           .apply(
                                                                                                               instance,
                                                                                                               ColoredSlabBlock::new
                                                                                                           ));

    private static final Map<InkColor, ColoredSlabBlock> BLOCKS = new Object2ObjectArrayMap<>();
    protected final InkColor color;

    public ColoredSlabBlock(Properties settings, InkColor color) {
        super(settings);
        this.color = color;
        BLOCKS.put(color, this);
    }

    @Override
    public MapCodec<? extends ColoredSlabBlock> codec() {
        return CODEC;
    }

    public InkColor getColor() {
        return this.color;
    }

    public static ColoredSlabBlock byColor(InkColor color) {
        return BLOCKS.get(color);
    }

}
