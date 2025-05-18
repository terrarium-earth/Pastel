package de.dafuqs.spectrum.api.block;

import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;

public interface FluidStackInventory extends ImplementedInventory {
	
	/**
	 * Retrieves the item list of this inventory.
	 * Must return the same instance every time it's called.
	 */
	NonNullList<ItemStack> getItems();
	
	SingleVariantStorage<FluidStack> getFluidStorage();
	
	/**
	 * Creates an inventory from the item list.
	 */
	static FluidStackInventory of(NonNullList<ItemStack> items, SingleVariantStorage<FluidStack> fluid) {
		return new FluidStackInventory() {
			@Override
			public NonNullList<ItemStack> getItems() {
				return items;
			}
			
			@Override
			public SingleVariantStorage<FluidStack> getFluidStorage() {
				return fluid;
			}
		};
	}
	
}