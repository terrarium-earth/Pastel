package earth.terrarium.pastel.items.tools;

import com.cmdpro.databank.misc.ColorGradient;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.GlassArrowEntity;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.apache.commons.lang3.function.TriConsumer;

import java.awt.*;

public class GlassArrowVariant {

    private static final Attributes MAL =
        new Attributes(1.5F, 2F, 0F, false, false, DamageTypes.MAGIC, 0x0bd2a0, 0x005d60);
    private static final Attributes TOP =
        new Attributes(1F, 1F, 5F, false, false, PastelDamageTypes.IMPALING, 0xf0fffd, 0x1c819a);
    private static final Attributes AME =
        new Attributes(0.5F, 0.334F, 0F, false, true, DamageTypes.MAGIC, 0xffd6ff, 0xcb14c8);
    private static final Attributes CIT =
        new Attributes(1F, 2F, 0F, true, false, DamageTypes.MAGIC, 0xfffbf2, 0xffa21f);
    private static final Attributes ONY =
        new Attributes(1F, 2F, 0F, false, false, DamageTypes.MAGIC, 0x66ffff, 0x020915,
                       (arrow, v, level) -> {
            level.getEntities(arrow, arrow.getBoundingBox().inflate(12)).forEach(target -> {
                if (v.getType() == HitResult.Type.ENTITY && ((EntityHitResult) v).getEntity() == target) {
                    var puller = arrow.getOwner() != null ? arrow.getOwner() : arrow;
                    GlassArrowEntity.pull(puller, target, 2F);
                    return;
                }

                if (arrow.isFirstHit())
                    GlassArrowEntity.pull(arrow, target, Math.max(1 - arrow.distanceTo(target) / 24, 0));
            });
                       }
            );
    private static final Attributes MOO =
        new Attributes(1.0F, 3F, 0F, false, false, PastelDamageTypes.IRRADIANCE, 0xFFFFFF, 0x767eec);

    public static final GlassArrowVariant MALACHITE = register("malachite", MAL);
    public static final GlassArrowVariant TOPAZ = register("topaz", TOP);
    public static final GlassArrowVariant AMETHYST = register("amethyst", AME);
    public static final GlassArrowVariant CITRINE = register("citrine", CIT);
    public static final GlassArrowVariant ONYX = register("onyx", ONY);
    public static final GlassArrowVariant MOONSTONE = register("moonstone", MOO);

    public GlassArrowVariant(ResourceLocation texture, Attributes attributes) {
        this.texture = texture;
        this.attributes = attributes;
    }

    private static GlassArrowVariant register(String id, Attributes attributes) {
        return Registry.register(PastelRegistries.GLASS_ARROW_VARIANT, id, new GlassArrowVariant(
            PastelCommon.locate("textures/entity/projectile/arrow/" + id + "_glass_arrow.png"),
            attributes));
    }

    protected ArrowItem arrow;
    protected ParticleOptions particleEffect;
    protected final ResourceLocation texture;
    protected final Attributes attributes;

    public static void init() {
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public ColorGradient getGradient() {
        return attributes.gradient;
    }

    public Attributes getAttributes() {
        return attributes;
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

    public record Attributes(float damage, float speed, float kb, boolean piercing, boolean homing, ResourceKey<DamageType> type,
                             ColorGradient gradient, TriConsumer<GlassArrowEntity, HitResult, Level> postHit) {

        public Attributes(float damage, float speed, float kb, boolean piercing, boolean homing, ResourceKey<DamageType> type,
                          int gStart, int gEnd, TriConsumer<GlassArrowEntity, HitResult, Level> postHit) {
            this(damage, speed, kb, piercing, homing, type, new ColorGradient(new Color(gStart), new Color(gEnd))
                .fadeAlpha(1F, 0.1F).fadeAlpha(0, 0, 1, 0.05f), postHit);
        }

        public Attributes(float damage, float speed, float kb, boolean piercing, boolean homing,
                          ResourceKey<DamageType> type, int gStart, int gEnd) {
            this(damage, speed, kb, piercing, homing, type, gStart, gEnd, (a, b, c) -> {});
        }
    }
}
