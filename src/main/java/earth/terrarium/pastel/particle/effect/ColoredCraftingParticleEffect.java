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

public class ColoredCraftingParticleEffect implements ParticleOptions {

    public static final ColoredCraftingParticleEffect BLACK = new ColoredCraftingParticleEffect(InkColors.BLACK_COLOR);

    public static final ColoredCraftingParticleEffect BLUE = new ColoredCraftingParticleEffect(InkColors.BLUE_COLOR);

    public static final ColoredCraftingParticleEffect BROWN = new ColoredCraftingParticleEffect(InkColors.BROWN_COLOR);

    public static final ColoredCraftingParticleEffect CYAN = new ColoredCraftingParticleEffect(InkColors.CYAN_COLOR);

    public static final ColoredCraftingParticleEffect GRAY = new ColoredCraftingParticleEffect(InkColors.GRAY_COLOR);

    public static final ColoredCraftingParticleEffect GREEN = new ColoredCraftingParticleEffect(InkColors.GREEN_COLOR);

    public static final ColoredCraftingParticleEffect LIGHT_BLUE = new ColoredCraftingParticleEffect(
        InkColors.LIGHT_BLUE_COLOR
    );

    public static final ColoredCraftingParticleEffect LIGHT_GRAY = new ColoredCraftingParticleEffect(
        InkColors.LIGHT_GRAY_COLOR
    );

    public static final ColoredCraftingParticleEffect LIME = new ColoredCraftingParticleEffect(InkColors.LIME_COLOR);

    public static final ColoredCraftingParticleEffect MAGENTA = new ColoredCraftingParticleEffect(
        InkColors.MAGENTA_COLOR
    );

    public static final ColoredCraftingParticleEffect ORANGE = new ColoredCraftingParticleEffect(
        InkColors.ORANGE_COLOR
    );

    public static final ColoredCraftingParticleEffect PINK = new ColoredCraftingParticleEffect(InkColors.PINK_COLOR);

    public static final ColoredCraftingParticleEffect PURPLE = new ColoredCraftingParticleEffect(
        InkColors.PURPLE_COLOR
    );

    public static final ColoredCraftingParticleEffect RED = new ColoredCraftingParticleEffect(InkColors.RED_COLOR);

    public static final ColoredCraftingParticleEffect WHITE = new ColoredCraftingParticleEffect(InkColors.WHITE_COLOR);

    public static final ColoredCraftingParticleEffect YELLOW = new ColoredCraftingParticleEffect(
        InkColors.YELLOW_COLOR
    );

    public static final MapCodec<ColoredCraftingParticleEffect> CODEC = RecordCodecBuilder
        .mapCodec(
            (instance) -> instance
                .group(
                    ExtraCodecs.VECTOR3F
                        .fieldOf("color")
                        .forGetter((effect) -> effect.color)
                )
                .apply(instance, ColoredCraftingParticleEffect::new)
        );

    public static final StreamCodec<RegistryFriendlyByteBuf, ColoredCraftingParticleEffect> STREAM_CODEC = StreamCodec
        .composite(
            ByteBufCodecs.VECTOR3F,
            (effect) -> effect.color,
            ColoredCraftingParticleEffect::new
        );

    private final Vector3f color;

    public ColoredCraftingParticleEffect(int color) {
        this.color = ColorHelper.colorIntToVec(color);
    }

    public ColoredCraftingParticleEffect(Vector3f color) {
        this.color = color;
    }

    public ParticleType<ColoredCraftingParticleEffect> getType() {
        return PastelParticleTypes.COLORED_CRAFTING;
    }

    public Vector3f getColor() {
        return this.color;
    }

    public static ParticleOptions of(int color) {
        return new ColoredCraftingParticleEffect(color);
    }

}
