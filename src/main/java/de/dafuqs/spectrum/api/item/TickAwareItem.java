package de.dafuqs.spectrum.api.item;

import net.minecraft.world.entity.item.ItemEntity;

public interface TickAwareItem {
	
	void onItemEntityTicked(ItemEntity itemEntity);
	
}
