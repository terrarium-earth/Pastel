package de.dafuqs.spectrum.api.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ArmorWithHitEffect {
	
	void onHit(ItemStack itemStack, DamageSource source, LivingEntity targetEntity, float amount);
	
}
