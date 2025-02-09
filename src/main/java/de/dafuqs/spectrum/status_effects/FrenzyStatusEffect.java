package de.dafuqs.spectrum.status_effects;

import de.dafuqs.spectrum.api.status_effect.*;
import de.dafuqs.spectrum.cca.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import org.jetbrains.annotations.*;

public class FrenzyStatusEffect extends StatusEffect implements StackableStatusEffect {
	
	public static final long REQUIRE_KILL_EVERY_X_TICKS = 200;
	
	public FrenzyStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		if (!SpectrumStatusEffects.effectsAreGettingStacked && !entity.hasStatusEffect(SpectrumStatusEffects.FRENZY)) {
			super.onApplied(entity, amplifier);
		}
	}
	
	@Override
	public void onRemoved(AttributeContainer attributes) {
		if (!SpectrumStatusEffects.effectsAreGettingStacked) {
			super.onRemoved(attributes);
		}
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return true;
	}
	
	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		long lastKillTick = LastKillComponent.getLastKillTick(entity);
		long worldTime = entity.getWorld().getTime();
		long lastKillTickDifference = worldTime - lastKillTick;
		boolean scoredKillInTime = lastKillTick >= 0 && lastKillTickDifference < REQUIRE_KILL_EVERY_X_TICKS;
		
		if (!scoredKillInTime && lastKillTickDifference % REQUIRE_KILL_EVERY_X_TICKS == 0) {
			updateAttributes(entity, amplifier, -1);
		}
		
		var potency = (SleepStatusEffect.getSleepScaling(entity) * (amplifier + 1) / 3) / 20;
		if (potency > 0 && entity.getHealth() > potency) {
			entity.damage(SpectrumDamageTypes.sleep(entity.getWorld(), null), potency);
		}

		return true;
	}
	
	public void onKill(LivingEntity livingEntity, int amplifier) {
		updateAttributes(livingEntity, amplifier, 1);
	}
	
	public void updateAttributes(@NotNull LivingEntity entity, int amplifier, int increase) {
		AttributeContainer attributes = entity.getAttributes();
		if (attributes != null) {
			forEachAttributeModifier(amplifier, (entry, modifier) -> {
				EntityAttributeInstance entityInstance = attributes.getCustomInstance(entry);
				if (entityInstance != null) {
                    EntityAttributeModifier appliedModifier = entityInstance.getModifier(modifier.id());
					double newBaseValue = appliedModifier == null ? modifier.value() : appliedModifier.value();
					double newValue = this.adjustModifierAmount(newBaseValue, modifier.value(), amplifier, increase);
					entityInstance.removeModifier(modifier);
					entityInstance.addPersistentModifier(new EntityAttributeModifier(modifier.id(), newValue, modifier.operation()));
					entityInstance.getValue();
				}
			});
		}
	}
	
	public double adjustModifierAmount(double existingValue, double additionalValue, int amplifier, int increase) {
		if (increase > 0) {
			return existingValue + additionalValue * (amplifier + increase);
		} else {
			return existingValue - additionalValue * (amplifier - increase);
		}
	}
	
}
