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

public class ColoredCraftingParticleEffect implements ParticleOptions {

    public static final PastelInkColorCollection<ColoredCraftingParticleEffect> VALUES =
            PastelInkColorCollection.VALUES.map(color -> new ColoredCraftingParticleEffect(color.getColorInt()));

    public static final ColoredCraftingParticleEffect BLACK = VALUES.pick(InkColors.BLACK);

    public static final ColoredCraftingParticleEffect BLUE = VALUES.pick(InkColors.BLUE);

    public static final ColoredCraftingParticleEffect BROWN = VALUES.pick(InkColors.BROWN);

    public static final ColoredCraftingParticleEffect CYAN = VALUES.pick(InkColors.CYAN);

    public static final ColoredCraftingParticleEffect GRAY = VALUES.pick(InkColors.GRAY);

    public static final ColoredCraftingParticleEffect GREEN = VALUES.pick(InkColors.GREEN);

    public static final ColoredCraftingParticleEffect LIGHT_BLUE = VALUES.pick(InkColors.LIGHT_BLUE);

    public static final ColoredCraftingParticleEffect LIGHT_GRAY = VALUES.pick(InkColors.LIGHT_GRAY);

    public static final ColoredCraftingParticleEffect LIME = VALUES.pick(InkColors.LIME);

    public static final ColoredCraftingParticleEffect MAGENTA = VALUES.pick(InkColors.MAGENTA);

    public static final ColoredCraftingParticleEffect ORANGE = VALUES.pick(InkColors.ORANGE);

    public static final ColoredCraftingParticleEffect PINK = VALUES.pick(InkColors.PINK);

    public static final ColoredCraftingParticleEffect PURPLE = VALUES.pick(InkColors.PURPLE);

    public static final ColoredCraftingParticleEffect RED = VALUES.pick(InkColors.RED);

    public static final ColoredCraftingParticleEffect WHITE = VALUES.pick(InkColors.WHITE);

    public static final ColoredCraftingParticleEffect YELLOW = VALUES.pick(InkColors.YELLOW);

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
