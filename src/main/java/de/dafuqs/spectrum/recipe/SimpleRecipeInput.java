package de.dafuqs.spectrum.recipe;

import net.minecraft.item.*;
import net.minecraft.recipe.input.*;

import java.util.*;

public class SimpleRecipeInput implements RecipeInput {
	private final List<ItemStack> stacks;
	
	public SimpleRecipeInput(List<ItemStack> stacks) {
		this.stacks = stacks;
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		return stacks.get(slot);
	}
	
	@Override
	public int getSize() {
		return stacks.size();
	}
	
	@Override
	public boolean isEmpty() {
		return stacks.isEmpty();
	}
	
}
