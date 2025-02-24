package de.dafuqs.spectrum.status_effects;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.particle.effect.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.server.network.*;
import net.minecraft.world.*;

public class DivinityStatusEffect extends StatusEffect {
	
	public static final int CIRCLET_AMPLIFIER = 0;
	public static final int ASCENSION_AMPLIFIER = 1;

	public DivinityStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}
	
	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		World world = entity.getWorld();
		if (amplifier > CIRCLET_AMPLIFIER && world.isClient) { // the circlet gives divinity 0, not showing effects; the ascension one does
			ParticleHelper.playParticleWithPatternAndVelocityClient(entity.getWorld(), entity.getPos(), ColoredCraftingParticleEffect.RED, VectorPattern.EIGHT, 0.2);
		}
		boolean doEffects = 40 >> amplifier == 0;
		if (entity instanceof PlayerEntity player) {
			if (!world.isClient) {
				SpectrumAdvancementCriteria.DIVINITY_TICK.trigger((ServerPlayerEntity) player);
			}
			if (doEffects) {
				player.getHungerManager().add(1 + amplifier, 0.25F);
			}
		}

		if (doEffects) {
			if (entity.getHealth() < entity.getMaxHealth()) {
				entity.heal(amplifier / 2F);
			}
		}

		return super.applyUpdateEffect(entity, amplifier);
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}

	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		super.onApplied(entity, amplifier);
		if (entity instanceof PlayerEntity) {
			if (entity instanceof ServerPlayerEntity player) {
				StatusEffectInstance instance = entity.getStatusEffect(SpectrumStatusEffects.DIVINITY);
				if (instance != null && !instance.isAmbient()) {
					PlayDivinityAppliedEffectsPayload.playDivinityAppliedEffects(player);
				}
			}
		}
	}

	@Override
	public void onRemoved(AttributeContainer attributes) {
		super.onRemoved(attributes);
	}

}