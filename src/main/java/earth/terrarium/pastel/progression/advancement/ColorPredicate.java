package earth.terrarium.pastel.progression.advancement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

// TODO - Review
public record ColorPredicate(Optional<InkColor> color) {

    public static final Codec<ColorPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                                                                                                        InkColor.CODEC.optionalFieldOf("color")
                                                                                                                      .forGetter(ColorPredicate::color)
                                                                                                    )
                                                                                                    .apply(
                                                                                                        instance,
                                                                                                        ColorPredicate::new
                                                                                                    ));

    public static final ColorPredicate ANY;

    static {
        ANY = new ColorPredicate(Optional.empty());
    }

    public boolean test(InkColor color) {
        if (this == ANY || color == null) {
            return true;
        }
        return this.color.isPresent() && this.color.get()
                                                   .equals(color);
    }

    public static class Builder {

        @Nullable
        private InkColor color;

        private Builder() {
            this.color = null;
        }

        public static ColorPredicate.Builder create() {
            return new ColorPredicate.Builder();
        }

        public ColorPredicate.Builder color(InkColor color) {
            this.color = color;
            return this;
        }

        public ColorPredicate build() {
            return new ColorPredicate(Optional.ofNullable(this.color));
        }
    }
}
