package earth.terrarium.pastel.items.tools;

import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.item.ArrowItem;

public class GlassArrowVariant {

    public static final GlassArrowVariant MALACHITE = register("malachite");
    public static final GlassArrowVariant TOPAZ = register("topaz");
    public static final GlassArrowVariant AMETHYST = register("amethyst");
    public static final GlassArrowVariant CITRINE = register("citrine");
    public static final GlassArrowVariant ONYX = register("onyx");
    public static final GlassArrowVariant MOONSTONE = register("moonstone");

    private static GlassArrowVariant register(String id) {
        return Registry.register(PastelRegistries.GLASS_ARROW_VARIANT, id, new GlassArrowVariant());
    }

    protected ArrowItem arrow;
    protected ParticleOptions particleEffect;

    public static void init() {
    }

    // A bit of a load order hack because arrows are not registered at the time the variant registry is needed
    void setData(ArrowItem arrowItem, ParticleOptions particleEffect) {
        this.arrow = arrowItem;
        this.particleEffect = particleEffect;
    }

    public ArrowItem getArrow() {
        return arrow;
    }

    public ParticleOptions getParticleEffect() {
        return particleEffect;
    }

}
