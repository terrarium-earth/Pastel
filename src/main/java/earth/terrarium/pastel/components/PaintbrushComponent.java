package earth.terrarium.pastel.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import java.util.Optional;
import java.util.function.IntFunction;

public record PaintbrushComponent(
    PaintbrushMode mode,
    Optional<InkColor> color,
    boolean brown,
    Optional<BlockPos> greenPos,
    String greenDim
) {
    public static final PaintbrushComponent DEFAULT = new PaintbrushComponent(
        PaintbrushMode.INFO,
        Optional.empty(),
        false,
        Optional.empty(),
        ""
    );

    public static final Codec<PaintbrushComponent> CODEC = RecordCodecBuilder
        .create(
            i -> i
                .group(
                    PaintbrushMode.CODEC
                        .fieldOf("mode")
                        .forGetter(PaintbrushComponent::mode),
                    InkColor.CODEC
                        .optionalFieldOf("color")
                        .forGetter(PaintbrushComponent::color),
                    Codec.BOOL.fieldOf("brown").forGetter(PaintbrushComponent::brown),
                    BlockPos.CODEC.optionalFieldOf("green_pos").forGetter(PaintbrushComponent::greenPos),
                    Codec.STRING.fieldOf("green_dim").forGetter(PaintbrushComponent::greenDim)
                )
                .apply(
                    i,
                    PaintbrushComponent::new
                )
        );

    // todo do we need a stream codec
    public static final StreamCodec<ByteBuf, PaintbrushComponent> STREAM_CODEC = StreamCodec
        .composite(
            PaintbrushMode.STREAM_CODEC,
            PaintbrushComponent::mode,
            InkColor.STREAM_CODEC.apply(ByteBufCodecs::optional),
            PaintbrushComponent::color,
            ByteBufCodecs.BOOL,
            PaintbrushComponent::brown,
            BlockPos.STREAM_CODEC.apply(ByteBufCodecs::optional),
            PaintbrushComponent::greenPos,
            ByteBufCodecs.STRING_UTF8,
            PaintbrushComponent::greenDim,
            PaintbrushComponent::new
        );

    public enum PaintbrushMode implements StringRepresentable {
        INFO("info"),
        PAINT("paint"),
        SPELL("spell");

        private final String name;

        PaintbrushMode(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }

        public static final Codec<PaintbrushMode> CODEC = StringRepresentable
            .fromValues(
                PaintbrushMode::values
            );

        public static final IntFunction<PaintbrushMode> BY_ID = ByIdMap
            .continuous(
                PaintbrushMode::ordinal,
                PaintbrushMode.values(),
                ByIdMap.OutOfBoundsStrategy.ZERO
            );

        public static final StreamCodec<ByteBuf, PaintbrushMode> STREAM_CODEC = ByteBufCodecs
            .idMapper(
                BY_ID,
                PaintbrushMode::ordinal
            );

    }
}
