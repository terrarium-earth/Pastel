package de.dafuqs.spectrum.status_effects;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.particle.effect.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.server.network.*;

public class AscensionStatusEffect extends StatusEffect {
	
	public static final int MUSIC_DURATION_TICKS = 288 * 20;
	public static final int MUSIC_INTRO_TICKS = 56 * 20; // 56 seconds
	
	private static boolean applyDivinity = false;
	
	public AscensionStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}
	
	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (entity.getWorld().isClient) {
			ParticleHelper.playParticleWithPatternAndVelocityClient(entity.getWorld(), entity.getPos(), ColoredSparkleRisingParticleEffect.WHITE, VectorPattern.EIGHT, 0.2);
		} else if (applyDivinity) {
			entity.addStatusEffect(new StatusEffectInstance(SpectrumStatusEffects.DIVINITY, MUSIC_DURATION_TICKS - MUSIC_INTRO_TICKS, DivinityStatusEffect.ASCENSION_AMPLIFIER));
			return false;
		}
		return super.applyUpdateEffect(entity, amplifier);
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		applyDivinity = duration == 1;
		return applyDivinity || duration % 4 == 0;
	}

	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		super.onApplied(entity, amplifier);
		if (entity instanceof ServerPlayerEntity player) {
			PlayAscensionAppliedEffectsPayload.playAscensionAppliedEffects(player);
		}
	}
	
}