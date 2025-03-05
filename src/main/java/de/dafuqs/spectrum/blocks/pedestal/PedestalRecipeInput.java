package de.dafuqs.spectrum.blocks.pedestal;

import java.util.*;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.item.*;
import net.minecraft.recipe.input.*;

public class PedestalRecipeInput implements RecipeInput {
	
	private static final List<ItemStack> FULL_GEMSTONE_POWDER_STACKS = List.of(
			new ItemStack(SpectrumItems.TOPAZ_POWDER, 64),
			new ItemStack(SpectrumItems.AMETHYST_POWDER, 64),
			new ItemStack(SpectrumItems.CITRINE_POWDER, 64),
			new ItemStack(SpectrumItems.ONYX_POWDER, 64),
			new ItemStack(SpectrumItems.MOONSTONE_POWDER, 64)
	);
	
	CraftingRecipeInput craftingGridInput;
	private final List<ItemStack> gemstonePowderStacks;
	
	public PedestalRecipeInput(CraftingRecipeInput craftingGridInput, List<ItemStack> gemstonePowderStacks) {
		this.craftingGridInput = craftingGridInput;
		this.gemstonePowderStacks = gemstonePowderStacks;
	}
	
	public CraftingRecipeInput getCraftingGridInput() {
		return craftingGridInput;
	}
	
	public static PedestalRecipeInput create(List<ItemStack> stacks) {
		return new PedestalRecipeInput(CraftingRecipeInput.create(3, 3, stacks.subList(0, 9)), stacks.subList(9, 14));
	}
	
	public static PedestalRecipeInput createWithFullGemstonePowder(List<ItemStack> stacks) {
		return new PedestalRecipeInput(CraftingRecipeInput.create(3, 3, stacks), FULL_GEMSTONE_POWDER_STACKS);
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < 9) {
			return slot < craftingGridInput.getSize() ? craftingGridInput.getStackInSlot(slot) : ItemStack.EMPTY;
		}
		slot -= 9;
		return slot < gemstonePowderStacks.size() ? gemstonePowderStacks.get(slot) : ItemStack.EMPTY;
	}
	
	@Override
	public int getSize() {
		return 14;
	}
}
