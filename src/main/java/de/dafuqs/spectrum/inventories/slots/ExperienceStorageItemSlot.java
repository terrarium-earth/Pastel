package de.dafuqs.spectrum.inventories.slots;

import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ExperienceStorageItemSlot extends Slot {
	
	public ExperienceStorageItemSlot(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		return super.mayPlace(stack) && stack.getItem() instanceof ExperienceStorageItem;
	}
	
}
