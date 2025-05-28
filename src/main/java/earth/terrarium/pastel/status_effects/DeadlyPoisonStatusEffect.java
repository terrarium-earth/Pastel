package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.registries.SpectrumDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DeadlyPoisonStatusEffect extends MobEffect {
	
	public DeadlyPoisonStatusEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		entity.hurt(SpectrumDamageTypes.deadlyPoison(entity.level()), 1.0F);
		return super.applyEffectTick(entity, amplifier);
	}
	
	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		int i = 25 >> amplifier;
		if (i > 0) {
			return duration % i == 0;
		} else {
			return true;
		}
	}
	
}
