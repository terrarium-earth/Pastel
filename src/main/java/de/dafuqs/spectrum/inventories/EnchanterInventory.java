package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.capabilities.item.*;
import de.dafuqs.spectrum.recipe.SimpleRecipeInput;
import net.minecraft.core.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

public class EnchanterInventory extends FriendlyStackHandler {
	
	public EnchanterInventory() {
		super(10);
	}
	
	public EnchanterInventory(ItemStack... items) {
		super(NonNullList.of(ItemStack.EMPTY, items));
	}
	
	public RecipeInput createInput() {
		return new SimpleRecipeInput(stacks);
	}
	
	public void rotate() {
		ItemStack stack2 = getStackInSlot(2);
		ItemStack stack3 = getStackInSlot(3);

		setStackInSlot(2, getStackInSlot(4));
		setStackInSlot(3, getStackInSlot(5));
		setStackInSlot(4, getStackInSlot(6));
		setStackInSlot(5, getStackInSlot(7));
		setStackInSlot(6, getStackInSlot(8));
		setStackInSlot(7, getStackInSlot(9));
		setStackInSlot(8, stack2);
		setStackInSlot(9, stack3);
	}
	
	public void mirror() {
		ItemStack stack2 = getStackInSlot(2);
		ItemStack stack4 = getStackInSlot(4);
		ItemStack stack6 = getStackInSlot(6);
		ItemStack stack8 = getStackInSlot(8);

		setStackInSlot(2, getStackInSlot(3));
		setStackInSlot(4, getStackInSlot(5));
		setStackInSlot(6, getStackInSlot(7));
		setStackInSlot(8, getStackInSlot(9));

		setStackInSlot(3, stack2);
		setStackInSlot(5, stack4);
		setStackInSlot(7, stack6);
		setStackInSlot(9, stack8);
	}
	
}
