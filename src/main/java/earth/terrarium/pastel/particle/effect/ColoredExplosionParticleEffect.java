package earth.terrarium.pastel.particle.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.joml.Vector3f;

public class ColoredExplosionParticleEffect implements ParticleOptions {

    public static final ColoredExplosionParticleEffect BLACK = new ColoredExplosionParticleEffect(
        InkColors.BLACK_COLOR
    );

    public static final ColoredExplosionParticleEffect BLUE = new ColoredExplosionParticleEffect(InkColors.BLUE_COLOR);

    public static final ColoredExplosionParticleEffect BROWN = new ColoredExplosionParticleEffect(
        InkColors.BROWN_COLOR
    );

    public static final ColoredExplosionParticleEffect CYAN = new ColoredExplosionParticleEffect(InkColors.CYAN_COLOR);

    public static final ColoredExplosionParticleEffect GRAY = new ColoredExplosionParticleEffect(InkColors.GRAY_COLOR);

    public static final ColoredExplosionParticleEffect GREEN = new ColoredExplosionParticleEffect(
        InkColors.GREEN_COLOR
    );

    public static final ColoredExplosionParticleEffect LIGHT_BLUE = new ColoredExplosionParticleEffect(
        InkColors.LIGHT_BLUE_COLOR
    );

    public static final ColoredExplosionParticleEffect LIGHT_GRAY = new ColoredExplosionParticleEffect(
        InkColors.LIGHT_GRAY_COLOR
    );

    public static final ColoredExplosionParticleEffect LIME = new ColoredExplosionParticleEffect(InkColors.LIME_COLOR);

    public static final ColoredExplosionParticleEffect MAGENTA = new ColoredExplosionParticleEffect(
        InkColors.MAGENTA_COLOR
    );

    public static final ColoredExplosionParticleEffect ORANGE = new ColoredExplosionParticleEffect(
        InkColors.ORANGE_COLOR
    );

    public static final ColoredExplosionParticleEffect PINK = new ColoredExplosionParticleEffect(InkColors.PINK_COLOR);

    public static final ColoredExplosionParticleEffect PURPLE = new ColoredExplosionParticleEffect(
        InkColors.PURPLE_COLOR
    );

    public static final ColoredExplosionParticleEffect RED = new ColoredExplosionParticleEffect(InkColors.RED_COLOR);

    public static final ColoredExplosionParticleEffect WHITE = new ColoredExplosionParticleEffect(
        InkColors.WHITE_COLOR
    );

    public static final ColoredExplosionParticleEffect YELLOW = new ColoredExplosionParticleEffect(
        InkColors.YELLOW_COLOR
    );

    public static final MapCodec<ColoredExplosionParticleEffect> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> instance
                .group(
                    ExtraCodecs.VECTOR3F
                        .fieldOf("color")
                        .forGetter((effect) -> effect.color)
                )
                .apply(instance, ColoredExplosionParticleEffect::new)
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ColoredExplosionParticleEffect> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.VECTOR3F,
            (effect) -> effect.color,
            ColoredExplosionParticleEffect::new
        );

    private final Vector3f color;

    public ColoredExplosionParticleEffect(int color) {
        this.color = ColorHelper.colorIntToVec(color);
    }

    public ColoredExplosionParticleEffect(Vector3f color) {
        this.color = color;
    }

    public ParticleType<ColoredExplosionParticleEffect> getType() {
        return PastelParticleTypes.COLORED_EXPLOSION;
    }

    public Vector3f getColor() {
        return this.color;
    }

    public static ParticleOptions of(int color) {
        return new ColoredExplosionParticleEffect(color);
    }

}
