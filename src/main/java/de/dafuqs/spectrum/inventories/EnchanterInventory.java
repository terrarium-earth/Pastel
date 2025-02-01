package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.recipe.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.recipe.input.*;

public class EnchanterInventory extends SimpleInventory {
	
	public EnchanterInventory() {
		super(10);
	}
	
	public EnchanterInventory(ItemStack... items) {
		super(items);
	}
	
	public RecipeInput createInput() {
		return new SimpleRecipeInput(this.heldStacks);
	}
	
	public void rotate() {
		setStack(0, getStack(0));
		setStack(1, getStack(1));
		setStack(2, getStack(4));
		setStack(3, getStack(5));
		setStack(4, getStack(6));
		setStack(5, getStack(7));
		setStack(6, getStack(8));
		setStack(7, getStack(9));
		setStack(8, getStack(2));
		setStack(9, getStack(3));
	}
	
	public void mirror() {
		setStack(0, getStack(0));
		setStack(1, getStack(1));
		setStack(2, getStack(3));
		setStack(3, getStack(2));
		setStack(4, getStack(5));
		setStack(5, getStack(4));
		setStack(6, getStack(7));
		setStack(7, getStack(6));
		setStack(8, getStack(9));
		setStack(9, getStack(8));
	}
	
}
