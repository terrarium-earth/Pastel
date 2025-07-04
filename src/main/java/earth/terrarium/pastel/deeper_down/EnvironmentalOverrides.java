package earth.terrarium.pastel.deeper_down;

import earth.terrarium.pastel.helpers.data.ColorHelper;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.function.Predicate;

import static earth.terrarium.pastel.deeper_down.EnvironmentalOverride.NOOP;

public class EnvironmentalOverrides {

    public static void init() {
        EnvironmentalOverride.register(
                new EnvironmentalOverride(has(PastelMobEffects.SOMNOLENCE),
                        new EnvironmentalOverride.ColorData(ColorHelper.colorIntToVec(PastelMobEffects.ETERNAL_SLUMBER_COLOR), 0.575F),
                        new EnvironmentalData(-0.25F, 2F, 0.125F, 0.25F),
                        0)
        );

        EnvironmentalOverride.register(
                new EnvironmentalOverride(has(PastelMobEffects.ETERNAL_SLUMBER),
                        new EnvironmentalOverride.ColorData(ColorHelper.colorIntToVec(PastelMobEffects.ETERNAL_SLUMBER_COLOR), 0.65F),
                        new EnvironmentalData(NOOP.darkening(), NOOP.brightMult(), -1.0F, -0.334F),
                        2)
        );

        EnvironmentalOverride.register(
                new EnvironmentalOverride(has(PastelMobEffects.FATAL_SLUMBER),
                        new EnvironmentalOverride.ColorData(ColorHelper.colorIntToVec(0x8136c2), 0.65F),
                        new EnvironmentalData(0.5F, -0.5F, -6F, -0.5F),
                        10)
        );

        EnvironmentalOverride.register(
                new EnvironmentalOverride(has(PastelMobEffects.FRENZY),
                        new EnvironmentalOverride.ColorData(ColorHelper.colorIntToVec(0xdf420d), 0.1F),
                        new EnvironmentalData(0.1F, -0.1F, NOOP.fogNear(), NOOP.fogFar()),
                        1)
        );

        EnvironmentalOverride.register(
                new EnvironmentalOverride(entity -> entity instanceof LivingEntity l &&
                        l.hasEffect(PastelMobEffects.FRENZY) &&
                        (l.hasEffect(PastelMobEffects.ETERNAL_SLUMBER) || l.hasEffect(PastelMobEffects.FATAL_SLUMBER)),
                        new EnvironmentalOverride.ColorData(ColorHelper.colorIntToVec(0xdf2449), 0.55F),
                        new EnvironmentalData(0.25F, -0.25F, -2.0F, -0.334F),
                        11)
        );
    }

    public static Predicate<Entity> has(Holder<MobEffect> effect) {
        return entity -> entity instanceof LivingEntity l && l.hasEffect(effect);
    }
}
