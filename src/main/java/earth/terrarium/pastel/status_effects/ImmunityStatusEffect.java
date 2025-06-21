package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.items.trinkets.WhispyCircletItem;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import java.util.Set;

public class ImmunityStatusEffect extends MobEffect {
	
	public ImmunityStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % 20 == 0;
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		WhispyCircletItem.removeNegativeStatusEffects(entity);
		return super.applyEffectTick(entity, amplifier);
	}
	
	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		super.onEffectStarted(entity, amplifier);
		WhispyCircletItem.removeNegativeStatusEffects(entity);
	}
	
}