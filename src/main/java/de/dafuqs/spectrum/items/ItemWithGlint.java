package de.dafuqs.spectrum.items;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemWithGlint extends Item {
	
	public ItemWithGlint(Properties settings) {
		super(settings);
	}
	
	@Override
	public boolean isFoil(ItemStack stack) {
		return true;
	}
	
}
