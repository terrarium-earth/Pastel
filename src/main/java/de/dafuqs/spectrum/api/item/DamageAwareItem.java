package de.dafuqs.spectrum.api.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.item.ItemEntity;

public interface DamageAwareItem {
	
	void onItemEntityDamaged(DamageSource source, float amount, ItemEntity itemEntity);
	
}
