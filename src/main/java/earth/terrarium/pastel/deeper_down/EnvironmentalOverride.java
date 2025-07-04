package earth.terrarium.pastel.deeper_down;

import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record EnvironmentalOverride(Predicate<Entity> predicate, ColorData color, EnvironmentalData dataOverride, int priority) {
    private static final List<EnvironmentalOverride> OVERRIDES = new ArrayList<>();

    public static final ColorData BLANK = new ColorData(new Vector3f(), 0);
    public static final EnvironmentalOverride INACTIVE = new EnvironmentalOverride(null, BLANK, EnvironmentalData.NOOP, -999);
    private static final EnvironmentalData NOOP = new EnvironmentalData(0F, 0, 0, 0);

    public EnvironmentalOverride(Predicate<Entity> predicate, ColorData data, int priority) {
        this(predicate, data, EnvironmentalData.NOOP, priority);
    }

    public EnvironmentalOverride(Predicate<Entity> predicate, EnvironmentalData dataOverride, int priority) {
        this(predicate, BLANK, dataOverride, priority);
    }

    public static EnvironmentalOverride fromArray(float[] override) {
        return new EnvironmentalOverride(
                null,
                new ColorData(
                        new Vector3f(
                                override[1],
                                override[2],
                                override[3]
                        ),
                        override[0]
                ),
                new EnvironmentalData(
                        override[4],
                        override[5],
                        override[6],
                        override[7]
                ),
                -999
        );
    }

    public float[] asArray() {
        var color = color().colorMod;

        return ArrayUtils.addAll(new float[] {
                color().blend,
                color.x,
                color.y,
                color.z
        }, dataOverride.asArray());
    }

    public static EnvironmentalOverride get(Entity camera) {
        EnvironmentalOverride effect = INACTIVE;

        for (EnvironmentalOverride override : OVERRIDES) {
            if (!override.predicate.test(camera))
                continue;

            if (effect ==INACTIVE) {
                effect = override;
                continue;
            }

            if (override.priority > effect.priority)
                effect = override;
        }

        return effect;
    }

    public static void register(EnvironmentalOverride override) {
        OVERRIDES.add(override);
    }

    private static Predicate<Entity> has(Holder<MobEffect> effect) {
        return entity -> entity instanceof LivingEntity l && l.hasEffect(effect);
    }

    public record ColorData(Vector3f colorMod, float blend) {
        public boolean isBlank() {
            return this == BLANK;
        }
    }

    static {
        register(
                new EnvironmentalOverride(has(PastelMobEffects.SOMNOLENCE),
                new ColorData(ColorHelper.colorIntToVec(PastelMobEffects.ETERNAL_SLUMBER_COLOR), 0.575F),
                new EnvironmentalData(-0.25F, 2F, 0.125F, 0.25F),
                0)
        );

        register(
                new EnvironmentalOverride(has(PastelMobEffects.ETERNAL_SLUMBER),
                        new ColorData(ColorHelper.colorIntToVec(PastelMobEffects.ETERNAL_SLUMBER_COLOR), 0.65F),
                        new EnvironmentalData(NOOP.darkening(), NOOP.brightMult(), -1.0F, -0.334F),
                        2)
        );

        register(
                new EnvironmentalOverride(has(PastelMobEffects.FATAL_SLUMBER),
                        new ColorData(ColorHelper.colorIntToVec(0x8136c2), 0.65F),
                        new EnvironmentalData(0.5F, -0.5F, -6F, -0.5F),
                        10)
        );

        register(
                new EnvironmentalOverride(has(PastelMobEffects.FRENZY),
                        new ColorData(ColorHelper.colorIntToVec(0xdf420d), 0.1F),
                        new EnvironmentalData(0.1F, -0.1F, NOOP.fogNear(), NOOP.fogFar()),
                        1)
        );

        register(
                new EnvironmentalOverride(entity -> entity instanceof LivingEntity l &&
                        l.hasEffect(PastelMobEffects.FRENZY) &&
                        (l.hasEffect(PastelMobEffects.ETERNAL_SLUMBER) || l.hasEffect(PastelMobEffects.FATAL_SLUMBER)),
                        new ColorData(ColorHelper.colorIntToVec(0xdf2449), 0.55F),
                        new EnvironmentalData(0.25F, -0.25F, -2.0F, -0.334F),
                        11)
        );
    }
}