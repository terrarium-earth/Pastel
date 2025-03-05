package de.dafuqs.spectrum.inventories.slots;

import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.screen.slot.*;

public class LockableCraftingResultSlot extends CraftingResultSlot {
	
	private boolean locked;
	
	public LockableCraftingResultSlot(Inventory craftingResultInventory, int index, int x, int y, PlayerEntity player, CraftingInventory input) {
		super(player, input, craftingResultInventory, index, x, y);
		this.locked = false;
	}
	
	@Override
	public boolean canTakeItems(PlayerEntity playerEntity) {
		return !locked;
	}
	
	public void lock() {
		this.locked = true;
	}
	
	public void unlock() {
		this.locked = false;
	}
	
}
