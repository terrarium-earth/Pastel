package earth.terrarium.pastel.blocks.conditional.colored_tree;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class ColoredStairsBlock extends StairBlock {

    public static final MapCodec<ColoredStairsBlock> CODEC = RecordCodecBuilder
        .mapCodec(
            instance -> instance
                .group(
                    BlockState.CODEC
                        .fieldOf("base_state")
                        .forGetter(b -> b.baseState),
                    propertiesCodec(),
                    InkColor.CODEC
                        .fieldOf("color")
                        .forGetter(ColoredStairsBlock::getColor)
                )
                .apply(
                    instance,
                    ColoredStairsBlock::new
                )
        );

    private static final Map<InkColor, ColoredStairsBlock> BLOCKS = new Object2ObjectArrayMap<>();

    protected final InkColor color;

    public ColoredStairsBlock(BlockState baseBlockState, Properties settings, InkColor color) {
        super(baseBlockState, settings);
        this.color = color;
        BLOCKS.put(color, this);
    }

    @Override
    public MapCodec<? extends ColoredStairsBlock> codec() {
        return CODEC;
    }

    public InkColor getColor() {
        return this.color;
    }

    public static ColoredStairsBlock byColor(InkColor color) {
        return BLOCKS.get(color);
    }

}
