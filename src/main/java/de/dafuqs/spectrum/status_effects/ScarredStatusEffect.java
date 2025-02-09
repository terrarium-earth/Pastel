package de.dafuqs.spectrum.status_effects;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;

public class ScarredStatusEffect extends StatusEffect {
	
	public ScarredStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public void onApplied(LivingEntity entity, int amplifier) {
		super.onApplied(entity, amplifier);
		if (entity.isSprinting()) {
			entity.setSprinting(false);
		}
	}

}
