package de.dafuqs.spectrum.mixin.accessors;

import net.minecraft.entity.*;
import net.minecraft.entity.damage.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.*;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {
	@Accessor
	DamageSource getLastDamageSource();
	
	@Accessor
	void setLastDamageSource(DamageSource damageSource);
	
	@Accessor()
	Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();
}
