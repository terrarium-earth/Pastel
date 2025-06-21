package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.helpers.ParticleHelper;
import earth.terrarium.pastel.networking.s2c_payloads.PlayDivinityAppliedEffectsPayload;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class DivinityStatusEffect extends MobEffect {
	
	public static final int CIRCLET_AMPLIFIER = 0;
	public static final int ASCENSION_AMPLIFIER = 1;

	public DivinityStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}
	
	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		Level world = entity.level();
		if (amplifier > CIRCLET_AMPLIFIER && world.isClientSide) { // the circlet gives divinity 0, not showing effects; the ascension one does
			ParticleHelper.playParticleWithPatternAndVelocityClient(entity.level(), entity.position(), ColoredCraftingParticleEffect.RED, VectorPattern.EIGHT, 0.2);
		}
		boolean doEffects = 40 >> amplifier == 0;
		if (entity instanceof Player player) {
			if (!world.isClientSide) {
				PastelAdvancementCriteria.DIVINITY_TICK.trigger((ServerPlayer) player);
			}
			if (doEffects) {
				player.getFoodData().eat(1 + amplifier, 0.25F);
			}
		}

		if (doEffects) {
			if (entity.getHealth() < entity.getMaxHealth()) {
				entity.heal(amplifier / 2F);
			}
		}

		return super.applyEffectTick(entity, amplifier);
	}
	
	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {}

	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		super.onEffectStarted(entity, amplifier);
		if (entity instanceof Player) {
			if (entity instanceof ServerPlayer player) {
				MobEffectInstance instance = entity.getEffect(PastelStatusEffects.DIVINITY);
				if (instance != null && !instance.isAmbient()) {
					PlayDivinityAppliedEffectsPayload.playDivinityAppliedEffects(player);
				}
			}
		}
	}

	@Override
	public void removeAttributeModifiers(AttributeMap attributes) {
		super.removeAttributeModifiers(attributes);
	}

}