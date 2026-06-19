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
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

public class ColoredFluidRisingParticleEffect implements ParticleOptions {
    public static final PastelInkColorCollection<ColoredFluidRisingParticleEffect> VALUES =
            PastelInkColorCollection.VALUES.map(color -> new ColoredFluidRisingParticleEffect(color.getColorInt()));

    public static final ColoredFluidRisingParticleEffect BLACK = VALUES.black();

    public static final ColoredFluidRisingParticleEffect BLUE = VALUES.blue();

    public static final ColoredFluidRisingParticleEffect BROWN = VALUES.brown();

    public static final ColoredFluidRisingParticleEffect CYAN = VALUES.cyan();

    public static final ColoredFluidRisingParticleEffect GRAY = VALUES.gray();

    public static final ColoredFluidRisingParticleEffect GREEN = VALUES.green();

    public static final ColoredFluidRisingParticleEffect LIGHT_BLUE = VALUES.lightBlue();

    public static final ColoredFluidRisingParticleEffect LIGHT_GRAY = VALUES.lightGray();

    public static final ColoredFluidRisingParticleEffect LIME = VALUES.lime();

    public static final ColoredFluidRisingParticleEffect MAGENTA = VALUES.magenta();

    public static final ColoredFluidRisingParticleEffect ORANGE = VALUES.orange();

    public static final ColoredFluidRisingParticleEffect PINK = VALUES.pink();

    public static final ColoredFluidRisingParticleEffect PURPLE = VALUES.purple();

    public static final ColoredFluidRisingParticleEffect RED = VALUES.red();

    public static final ColoredFluidRisingParticleEffect WHITE = VALUES.white();

    public static final ColoredFluidRisingParticleEffect YELLOW = VALUES.yellow();

    public static final MapCodec<ColoredFluidRisingParticleEffect> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> instance
                .group(
                    ExtraCodecs.VECTOR3F
                        .fieldOf("color")
                        .forGetter((effect) -> effect.color)
                )
                .apply(instance, ColoredFluidRisingParticleEffect::new)
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ColoredFluidRisingParticleEffect> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.VECTOR3F,
            (effect) -> effect.color,
            ColoredFluidRisingParticleEffect::new
        );

    private final Vector3f color;

    public ColoredFluidRisingParticleEffect(int color) {
        this.color = ColorHelper.colorIntToVec(color);
    }

    public ColoredFluidRisingParticleEffect(Vector3f color) {
        this.color = color;
    }

    public ParticleType<ColoredFluidRisingParticleEffect> getType() {
        return PastelParticleTypes.COLORED_FLUID_RISING;
    }

    public Vector3f getColor() {
        return this.color;
    }

    public static @NotNull ParticleOptions of(int color) {
        return new ColoredFluidRisingParticleEffect(color);
    }

}
