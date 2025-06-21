package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.helpers.MobEffectHelper;
import earth.terrarium.pastel.items.trinkets.WhispyCircletItem;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.common.EffectCure;

import java.util.ArrayList;
import java.util.Set;

public class ImmunityStatusEffect extends MobEffect {
	
	public ImmunityStatusEffect(MobEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % 5 == 0;
	}

	@Override
	public void fillEffectCures(Set<EffectCure> cures, MobEffectInstance effectInstance) {}

	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		MobEffectHelper.actionImmunity(entity, true);
		return true;
	}
	
	@Override
	public void onEffectStarted(LivingEntity entity, int amplifier) {
		MobEffectHelper.actionImmunity(entity, false);
	}


}