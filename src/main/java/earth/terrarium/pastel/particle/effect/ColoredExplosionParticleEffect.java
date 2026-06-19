package earth.terrarium.pastel.particle.effect;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.helpers.level.collections.PastelInkColorCollection;
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

    public static final PastelInkColorCollection<ColoredExplosionParticleEffect> VALUES =
            PastelInkColorCollection.VALUES.map(color -> new ColoredExplosionParticleEffect(color.getColorInt()));

    public static final ColoredExplosionParticleEffect BLACK = VALUES.black();

    public static final ColoredExplosionParticleEffect BLUE = VALUES.blue();

    public static final ColoredExplosionParticleEffect BROWN = VALUES.brown();

    public static final ColoredExplosionParticleEffect CYAN = VALUES.cyan();

    public static final ColoredExplosionParticleEffect GRAY = VALUES.gray();

    public static final ColoredExplosionParticleEffect GREEN = VALUES.green();

    public static final ColoredExplosionParticleEffect LIGHT_BLUE = VALUES.lightBlue();

    public static final ColoredExplosionParticleEffect LIGHT_GRAY = VALUES.lightGray();

    public static final ColoredExplosionParticleEffect LIME = VALUES.lime();

    public static final ColoredExplosionParticleEffect MAGENTA = VALUES.magenta();

    public static final ColoredExplosionParticleEffect ORANGE = VALUES.orange();

    public static final ColoredExplosionParticleEffect PINK = VALUES.pink();

    public static final ColoredExplosionParticleEffect PURPLE = VALUES.purple();

    public static final ColoredExplosionParticleEffect RED = VALUES.red();

    public static final ColoredExplosionParticleEffect WHITE = VALUES.white();

    public static final ColoredExplosionParticleEffect YELLOW = VALUES.yellow();

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
