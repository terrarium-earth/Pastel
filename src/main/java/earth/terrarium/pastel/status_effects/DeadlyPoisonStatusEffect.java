package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.registries.PastelDamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;
import net.neoforged.neoforge.common.EffectCures;

import java.util.Set;

public class DeadlyPoisonStatusEffect extends MobEffect {
	
	public DeadlyPoisonStatusEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		entity.hurt(PastelDamageTypes.deadlyPoison(entity.level()), 1.0F);
		return super.applyEffectTick(entity, amplifier);
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {
		super.fillEffectCures(cures, effectInstance);
		cures.add(EffectCures.HONEY);
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
