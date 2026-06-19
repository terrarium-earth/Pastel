package earth.terrarium.pastel.particle.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.energy.color.PastelInkColorCollection;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public class ColoredSparkleRisingParticleEffect implements ParticleOptions {
    public static final PastelInkColorCollection<ColoredSparkleRisingParticleEffect> VALUES =
            PastelInkColorCollection.VALUES.map(color -> new ColoredSparkleRisingParticleEffect(color.getColorInt()));


    public static final ColoredSparkleRisingParticleEffect BLACK = VALUES.pick(InkColors.BLACK);

    public static final ColoredSparkleRisingParticleEffect BLUE = VALUES.pick(InkColors.BLUE);

    public static final ColoredSparkleRisingParticleEffect BROWN = VALUES.pick(InkColors.BROWN);

    public static final ColoredSparkleRisingParticleEffect CYAN = VALUES.pick(InkColors.CYAN);

    public static final ColoredSparkleRisingParticleEffect GRAY = VALUES.pick(InkColors.GRAY);

    public static final ColoredSparkleRisingParticleEffect GREEN = VALUES.pick(InkColors.GREEN);

    public static final ColoredSparkleRisingParticleEffect LIGHT_BLUE = VALUES.pick(InkColors.LIGHT_BLUE);

    public static final ColoredSparkleRisingParticleEffect LIGHT_GRAY = VALUES.pick(InkColors.LIGHT_GRAY);

    public static final ColoredSparkleRisingParticleEffect LIME = VALUES.pick(InkColors.LIME);

    public static final ColoredSparkleRisingParticleEffect MAGENTA = VALUES.pick(InkColors.MAGENTA);

    public static final ColoredSparkleRisingParticleEffect ORANGE = VALUES.pick(InkColors.ORANGE);

    public static final ColoredSparkleRisingParticleEffect PINK = VALUES.pick(InkColors.PINK);

    public static final ColoredSparkleRisingParticleEffect PURPLE = VALUES.pick(InkColors.PURPLE);

    public static final ColoredSparkleRisingParticleEffect RED = VALUES.pick(InkColors.RED);

    public static final ColoredSparkleRisingParticleEffect WHITE = VALUES.pick(InkColors.WHITE);

    public static final ColoredSparkleRisingParticleEffect YELLOW = VALUES.pick(InkColors.YELLOW);

    public static final MapCodec<ColoredSparkleRisingParticleEffect> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> instance
                .group(
                    ExtraCodecs.VECTOR3F
                        .fieldOf("color")
                        .forGetter((effect) -> effect.color)
                )
                .apply(instance, ColoredSparkleRisingParticleEffect::new)
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ColoredSparkleRisingParticleEffect> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.VECTOR3F,
            (effect) -> effect.color,
            ColoredSparkleRisingParticleEffect::new
        );

    private final Vector3f color;

    public ColoredSparkleRisingParticleEffect(int color) {
        this.color = ColorHelper.colorIntToVec(color);
    }

    public ColoredSparkleRisingParticleEffect(Vector3f color) {
        this.color = color;
    }

    public ParticleType<ColoredSparkleRisingParticleEffect> getType() {
        return PastelParticleTypes.COLORED_SPARKLE_RISING;
    }

    public Vector3f getColor() {
        return this.color;
    }

    public static ParticleOptions of(int color) {
        return new ColoredSparkleRisingParticleEffect(color);
    }

}
