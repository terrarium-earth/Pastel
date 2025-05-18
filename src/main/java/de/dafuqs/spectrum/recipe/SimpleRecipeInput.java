package de.dafuqs.spectrum.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;

import java.util.List;

public class SimpleRecipeInput implements RecipeInput {
	private final List<ItemStack> stacks;
	
	public SimpleRecipeInput(List<ItemStack> stacks) {
		this.stacks = stacks;
	}
	
	@Override
	public ItemStack getItem(int slot) {
		return stacks.get(slot);
	}
	
	@Override
	public int size() {
		return stacks.size();
	}
	
	@Override
	public boolean isEmpty() {
		return stacks.isEmpty();
	}
	
}
