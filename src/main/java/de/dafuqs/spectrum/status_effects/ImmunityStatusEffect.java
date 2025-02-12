package de.dafuqs.spectrum.status_effects;

import de.dafuqs.spectrum.items.trinkets.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;

public class ImmunityStatusEffect extends StatusEffect {
	
	public ImmunityStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return duration % 20 == 0;
	}
	
	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		WhispyCircletItem.removeNegativeStatusEffects(entity);
		return super.applyUpdateEffect(entity, amplifier);
	}
	
	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		super.onApplied(entity, amplifier);
		WhispyCircletItem.removeNegativeStatusEffects(entity);
	}
	
}