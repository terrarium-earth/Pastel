package de.dafuqs.spectrum.api.block;

import de.dafuqs.spectrum.helpers.*;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public interface FluidStackInventory {
	
	/**
	 * Retrieves the item list of this inventory.
	 * Must return the same instance every time it's called.
	 */
	FriendlyStackHandler getInventory();
	
	SingleVariantStorage<FluidStack> getFluidStorage();
	
	/**
	 * Creates an inventory from the item list.
	 */
	static FluidStackInventory of(FriendlyStackHandler items, SingleVariantStorage<FluidStack> fluid) {
		return new FluidStackInventory() {
			@Override
			public FriendlyStackHandler getInventory() {
				return items;
			}
			
			@Override
			public SingleVariantStorage<FluidStack> getFluidStorage() {
				return fluid;
			}
		};
	}
	
}