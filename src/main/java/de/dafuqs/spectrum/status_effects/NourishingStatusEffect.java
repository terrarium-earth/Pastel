package de.dafuqs.spectrum.status_effects;

import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;

public class NourishingStatusEffect extends StatusEffect {
	
	public NourishingStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}
	
	@Override
	public String getTranslationKey() {
		return StatusEffects.SATURATION.value().getTranslationKey();
	}
	
	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		World world = entity.getWorld();
		if (!world.isClient && entity instanceof PlayerEntity playerEntity) {
			playerEntity.getHungerManager().add(1, 0.25F);
		}
		return super.applyUpdateEffect(entity, amplifier);
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		int i = 200 >> amplifier;
		if (i > 0) {
			return duration % i == 0;
		}
		return true;
	}
	
}