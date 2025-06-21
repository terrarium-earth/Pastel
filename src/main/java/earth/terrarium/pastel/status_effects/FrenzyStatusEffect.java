package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.attachments.data.LastKillData;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.neoforge.common.EffectCure;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class FrenzyStatusEffect extends MobEffect {
	
	public static final long REQUIRE_KILL_EVERY_X_TICKS = 200;
	
	public FrenzyStatusEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
	
	// prevent the resetting of frenzy-granted attributes
	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		if (!PastelStatusEffects.effectsAreGettingStacked && !entity.hasEffect(PastelStatusEffects.FRENZY)) {
			super.onEffectStarted(entity, amplifier);
		}
	}
	
	@Override
	public void removeAttributeModifiers(AttributeMap attributes) {
		if (!PastelStatusEffects.effectsAreGettingStacked) {
			super.removeAttributeModifiers(attributes);
		}
	}
	
	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return true;
	}
	
	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		long lastKillTick = LastKillData.getLastKillTick(entity);
		long worldTime = entity.level().getGameTime();
		long lastKillTickDifference = worldTime - lastKillTick;
		boolean scoredKillInTime = lastKillTick >= 0 && lastKillTickDifference < REQUIRE_KILL_EVERY_X_TICKS;
		
		if (!scoredKillInTime && lastKillTickDifference % REQUIRE_KILL_EVERY_X_TICKS == 0) {
			updateAttributes(entity, amplifier, -1);
		}
		
		var potency = (SleepStatusEffect.getSleepScaling(entity) * (amplifier + 1) / 3) / 20;
		if (potency > 0 && entity.getHealth() > potency) {
			entity.hurt(PastelDamageTypes.sleep(entity.level(), null), potency);
		}

		return true;
	}
	
	public void onKill(LivingEntity livingEntity, int amplifier) {
		updateAttributes(livingEntity, amplifier, 1);
	}
	
	public void updateAttributes(@NotNull LivingEntity entity, int amplifier, int increase) {
		AttributeMap attributes = entity.getAttributes();
		if (attributes != null) {
			createModifiers(amplifier, (entry, modifier) -> {
				AttributeInstance entityInstance = attributes.getInstance(entry);
				if (entityInstance != null) {
                    AttributeModifier appliedModifier = entityInstance.getModifier(modifier.id());
					double newBaseValue = appliedModifier == null ? modifier.amount() : appliedModifier.amount();
					double newValue = this.adjustModifierAmount(newBaseValue, modifier.amount(), amplifier, increase);
					entityInstance.removeModifier(modifier);
					entityInstance.addPermanentModifier(new AttributeModifier(modifier.id(), newValue, modifier.operation()));
					entityInstance.getValue();
				}
			});
		}
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
		cures.add(PastelStatusEffects.Cures.SEDATIVES);
	}

	public double adjustModifierAmount(double existingValue, double additionalValue, int amplifier, int increase) {
		if (increase > 0) {
			return existingValue + additionalValue * (amplifier + increase);
		} else {
			return existingValue - additionalValue * (amplifier - increase);
		}
	}
	
}
