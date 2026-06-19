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

public class ColoredSporeBlossomAirParticleEffect implements ParticleOptions {
    public static final PastelInkColorCollection<ColoredSporeBlossomAirParticleEffect> VALUES =
        PastelInkColorCollection.VALUES.map(color -> new ColoredSporeBlossomAirParticleEffect(color.getColorInt()));

    public static final ColoredSporeBlossomAirParticleEffect BLACK = VALUES.black();

    public static final ColoredSporeBlossomAirParticleEffect BLUE = VALUES.blue();

    public static final ColoredSporeBlossomAirParticleEffect BROWN = VALUES.brown();

    public static final ColoredSporeBlossomAirParticleEffect CYAN = VALUES.cyan();

    public static final ColoredSporeBlossomAirParticleEffect GRAY = VALUES.gray();

    public static final ColoredSporeBlossomAirParticleEffect GREEN = VALUES.green();

    public static final ColoredSporeBlossomAirParticleEffect LIGHT_BLUE = VALUES.lightBlue();

    public static final ColoredSporeBlossomAirParticleEffect LIGHT_GRAY = VALUES.lightGray();

    public static final ColoredSporeBlossomAirParticleEffect LIME = VALUES.lime();

    public static final ColoredSporeBlossomAirParticleEffect MAGENTA = VALUES.magenta();

    public static final ColoredSporeBlossomAirParticleEffect ORANGE = VALUES.orange();

    public static final ColoredSporeBlossomAirParticleEffect PINK = VALUES.pink();

    public static final ColoredSporeBlossomAirParticleEffect PURPLE = VALUES.purple();

    public static final ColoredSporeBlossomAirParticleEffect RED = VALUES.red();

    public static final ColoredSporeBlossomAirParticleEffect WHITE = VALUES.white();

    public static final ColoredSporeBlossomAirParticleEffect YELLOW = VALUES.yellow();

    public static final MapCodec<ColoredSporeBlossomAirParticleEffect> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> instance
                .group(
                    ExtraCodecs.VECTOR3F
                        .fieldOf("color")
                        .forGetter((effect) -> effect.color)
                )
                .apply(instance, ColoredSporeBlossomAirParticleEffect::new)
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ColoredSporeBlossomAirParticleEffect> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.VECTOR3F,
            (effect) -> effect.color,
            ColoredSporeBlossomAirParticleEffect::new
        );

    private final Vector3f color;

    public ColoredSporeBlossomAirParticleEffect(int color) {
        this.color = ColorHelper.colorIntToVec(color);
    }

    public ColoredSporeBlossomAirParticleEffect(Vector3f color) {
        this.color = color;
    }

    public ParticleType<ColoredSporeBlossomAirParticleEffect> getType() {
        return PastelParticleTypes.COLORED_SPORE_BLOSSOM_AIR;
    }

    public Vector3f getColor() {
        return this.color;
    }

}
