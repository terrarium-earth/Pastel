package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Map;

public class GlowBlock extends Block {

    public static final MapCodec<GlowBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                                                                                          propertiesCodec(),
                                                                                          InkColor.CODEC.fieldOf(
                                                                                              "color")
                                                                                                        .forGetter(GlowBlock::getColor)
                                                                                      )
                                                                                      .apply(i, GlowBlock::new));

    private static final Map<InkColor, GlowBlock> GLOWBLOCKS = new Object2ObjectArrayMap<>();
    protected final InkColor color;

    public GlowBlock(Properties settings, InkColor color) {
        super(settings);
        this.color = color;
        GLOWBLOCKS.put(color, this);
    }

    @Override
    public MapCodec<? extends GlowBlock> codec() {
        return CODEC;
    }

    public InkColor getColor() {
        return this.color;
    }

    public static GlowBlock byColor(InkColor color) {
        return GLOWBLOCKS.get(color);
    }

    @Override
    public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }

}
