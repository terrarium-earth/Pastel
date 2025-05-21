package de.dafuqs.spectrum.api.block;

import de.dafuqs.spectrum.helpers.*;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.templates.*;

public interface FluidTankInventory {
	
	/**
	 * Retrieves the item list of this inventory.
	 * Must return the same instance every time it's called.
	 */
	FriendlyStackHandler getInventory();
	
	FluidTank getTank();
	
	/**
	 * Creates an inventory from the item list.
	 */
	static FluidTankInventory of(FriendlyStackHandler items, FluidTank tank) {
		return new FluidTankInventory() {
			@Override
			public FriendlyStackHandler getInventory() {
				return items;
			}
			
			@Override
			public FluidTank getTank() {
				return tank;
			}
		};
	}
	
}