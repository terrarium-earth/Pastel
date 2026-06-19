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

public class ColoredFallingSporeBlossomParticleEffect implements ParticleOptions {
    public static final PastelInkColorCollection<ColoredFallingSporeBlossomParticleEffect> VALUES =
        PastelInkColorCollection.VALUES.map(color -> new ColoredFallingSporeBlossomParticleEffect(color.getColorInt()));

    public static final ColoredFallingSporeBlossomParticleEffect BLACK = VALUES.black();

    public static final ColoredFallingSporeBlossomParticleEffect BLUE = VALUES.blue();

    public static final ColoredFallingSporeBlossomParticleEffect BROWN = VALUES.brown();

    public static final ColoredFallingSporeBlossomParticleEffect CYAN = VALUES.cyan();

    public static final ColoredFallingSporeBlossomParticleEffect GRAY = VALUES.gray();

    public static final ColoredFallingSporeBlossomParticleEffect GREEN = VALUES.green();

    public static final ColoredFallingSporeBlossomParticleEffect LIGHT_BLUE = VALUES.lightBlue();

    public static final ColoredFallingSporeBlossomParticleEffect LIGHT_GRAY = VALUES.lightGray();

    public static final ColoredFallingSporeBlossomParticleEffect LIME = VALUES.lime();

    public static final ColoredFallingSporeBlossomParticleEffect MAGENTA = VALUES.magenta();

    public static final ColoredFallingSporeBlossomParticleEffect ORANGE = VALUES.orange();

    public static final ColoredFallingSporeBlossomParticleEffect PINK = VALUES.pink();

    public static final ColoredFallingSporeBlossomParticleEffect PURPLE = VALUES.pink();

    public static final ColoredFallingSporeBlossomParticleEffect RED = VALUES.red();

    public static final ColoredFallingSporeBlossomParticleEffect WHITE = VALUES.white();

    public static final ColoredFallingSporeBlossomParticleEffect YELLOW = VALUES.yellow();

    public static final MapCodec<ColoredFallingSporeBlossomParticleEffect> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> instance
                .group(
                    ExtraCodecs.VECTOR3F
                        .fieldOf("color")
                        .forGetter((effect) -> effect.color)
                )
                .apply(instance, ColoredFallingSporeBlossomParticleEffect::new)
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ColoredFallingSporeBlossomParticleEffect> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.VECTOR3F,
            (effect) -> effect.color,
            ColoredFallingSporeBlossomParticleEffect::new
        );

    private final Vector3f color;

    public ColoredFallingSporeBlossomParticleEffect(int color) {
        this.color = ColorHelper.colorIntToVec(color);
    }

    public ColoredFallingSporeBlossomParticleEffect(Vector3f color) {
        this.color = color;
    }

    public ParticleType<ColoredFallingSporeBlossomParticleEffect> getType() {
        return PastelParticleTypes.COLORED_FALLING_SPORE_BLOSSOM;
    }

    public Vector3f getColor() {
        return this.color;
    }

}
