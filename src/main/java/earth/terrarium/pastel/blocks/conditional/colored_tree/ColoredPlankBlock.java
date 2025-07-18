package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class ColoredPlankBlock extends Block {

    public static final MapCodec<ColoredPlankBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                                                                                                                propertiesCodec(),
                                                                                                                InkColor.CODEC.fieldOf("color")
                                                                                                                              .forGetter(ColoredPlankBlock::getColor)
                                                                                                            )
                                                                                                            .apply(
                                                                                                                instance,
                                                                                                                ColoredPlankBlock::new
                                                                                                            ));

    private static final Map<InkColor, ColoredPlankBlock> BLOCKS = new Object2ObjectArrayMap<>();
    protected final InkColor color;

    public ColoredPlankBlock(Properties settings, InkColor color) {
        super(settings);
        this.color = color;
        BLOCKS.put(color, this);
    }

    @Override
    public MapCodec<? extends ColoredPlankBlock> codec() {
        return CODEC;
    }

    public InkColor getColor() {
        return this.color;
    }

    public static ColoredPlankBlock byColor(InkColor color) {
        return BLOCKS.get(color);
    }

}
