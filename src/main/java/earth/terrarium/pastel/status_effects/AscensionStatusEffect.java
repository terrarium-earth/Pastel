package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.helpers.render.ParticleHelper;
import earth.terrarium.pastel.networking.s2c_payloads.PlayAscensionAppliedEffectsPayload;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class AscensionStatusEffect extends MobEffect {

    public static final int MUSIC_DURATION_TICKS = 288 * 20;
    public static final int MUSIC_INTRO_TICKS = 56 * 20; // 56 seconds

    private static boolean applyDivinity = false;

    public AscensionStatusEffect(MobEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) {
            ParticleHelper.playParticleWithPatternAndVelocityClient(
                entity.level(), entity.position(), ColoredSparkleRisingParticleEffect.WHITE, VectorPattern.EIGHT, 0.2);
        } else if (applyDivinity) {
            entity.addEffect(new MobEffectInstance(
                PastelMobEffects.DIVINITY, MUSIC_DURATION_TICKS - MUSIC_INTRO_TICKS,
                                                   DivinityStatusEffect.ASCENSION_AMPLIFIER
            ));
            return false;
        }
        return super.applyEffectTick(entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        applyDivinity = duration == 1;
        return applyDivinity || duration % 4 == 0;
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
        super.onEffectStarted(entity, amplifier);
        if (entity instanceof ServerPlayer player) {
            PlayAscensionAppliedEffectsPayload.playAscensionAppliedEffects(player);
        }
    }

}
