package de.dafuqs.spectrum.status_effects;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.world.*;

public class AscensionStatusEffect extends SpectrumStatusEffect {
	
	public static final int MUSIC_DURATION_TICKS = 288 * 20;
	public static final int MUSIC_INTRO_TICKS = 56 * 20; // 56 seconds
	
	public AscensionStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}
	
	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		World world = entity.getWorld();
		if (world.isClient) {
			ParticleHelper.playParticleWithPatternAndVelocityClient(entity.getWorld(), entity.getPos(), SpectrumParticleTypes.WHITE_SPARKLE_RISING, VectorPattern.EIGHT, 0.2);
		}
		return super.applyUpdateEffect(entity, amplifier);
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return duration % 4 == 0;
	}

	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		super.onApplied(entity, amplifier);
		if (entity instanceof ServerPlayerEntity player) {
			PlayAscensionAppliedEffectsPayload.playAscensionAppliedEffects(player);
		}
	}
	
	// TODO: `onEntityRemoval()` triggers on entity removal, not status effect removal, making this divinity never kick in
	@Override
	public void onEntityRemoval(LivingEntity entity, int amplifier, Entity.RemovalReason reason) {
		super.onEntityRemoval(entity, amplifier, reason);
		
		// only apply divinity if ascension ran out
		// does not apply when curing the effect by other means, such as drinking milk
		// which would trigger a ConcurrentModificationException
		StatusEffectInstance instance = entity.getStatusEffect(entity.getWorld().getRegistryManager().get(RegistryKeys.STATUS_EFFECT).getEntry(this));
		if (instance == null) { // null if the effect ran out; non-null for milk and stuff
			entity.addStatusEffect(new StatusEffectInstance(SpectrumStatusEffects.DIVINITY, MUSIC_DURATION_TICKS - MUSIC_INTRO_TICKS, DivinityStatusEffect.ASCENSION_AMPLIFIER));
		}
	}
	
}