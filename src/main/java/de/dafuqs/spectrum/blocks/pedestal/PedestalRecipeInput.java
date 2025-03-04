package de.dafuqs.spectrum.blocks.pedestal;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.recipe.input.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class PedestalRecipeInput implements RecipeInput {
	
	private final CraftingRecipeInput craftingGridInput;
	private final List<ItemStack> gemstonePowderStacks;
	private final @Nullable PlayerEntity player;
	
	public PedestalRecipeInput(CraftingRecipeInput craftingGridInput, List<ItemStack> gemstonePowderStacks, @Nullable PlayerEntity player) {
		this.craftingGridInput = craftingGridInput;
		this.gemstonePowderStacks = gemstonePowderStacks;
		this.player = player;
	}
	
	public CraftingRecipeInput getCraftingGridInput() {
		return craftingGridInput;
	}
	
	public static PedestalRecipeInput create(List<ItemStack> stacks, @Nullable PlayerEntity player) {
		List<ItemStack> gridStacks = new ArrayList<>(9);
		List<ItemStack> gemstonePowderStacks = new ArrayList<>(5);
		for (int i = 0; i < 9; i++) {
			gridStacks.add(stacks.get(i));
		}
		for (int i = 9; i < 14; i++) {
			gemstonePowderStacks.add(stacks.get(i));
		}
		
		return new PedestalRecipeInput(CraftingRecipeInput.create(3, 3, gridStacks), gemstonePowderStacks, player);
	}
	
	public static PedestalRecipeInput createWithFullGemstonePowder(List<ItemStack> stacks, @Nullable PlayerEntity player) {
		List<ItemStack> gridStacks = new ArrayList<>(9);
		List<ItemStack> gemstonePowderStacks = new ArrayList<>(5);
		for (int i = 0; i < 9; i++) {
			gridStacks.add(stacks.get(i));
		}
		gemstonePowderStacks.add(new ItemStack(SpectrumItems.TOPAZ_POWDER, 64));
		gemstonePowderStacks.add(new ItemStack(SpectrumItems.AMETHYST_POWDER, 64));
		gemstonePowderStacks.add(new ItemStack(SpectrumItems.CITRINE_POWDER, 64));
		gemstonePowderStacks.add(new ItemStack(SpectrumItems.ONYX_POWDER, 64));
		gemstonePowderStacks.add(new ItemStack(SpectrumItems.MOONSTONE_POWDER, 64));
		
		return new PedestalRecipeInput(CraftingRecipeInput.create(3, 3, gridStacks), gemstonePowderStacks, player);
	}
	
	@Override
	public ItemStack getStackInSlot(int slot) {
		if (slot < craftingGridInput.getSize()) {
			return craftingGridInput.getStackInSlot(slot);
		}
		return gemstonePowderStacks.get(slot - craftingGridInput.getSize());
	}
	
	@Override
	public int getSize() {
		return craftingGridInput.getSize() + gemstonePowderStacks.size();
	}
	
	public int[] getCraftingGridSlots() {
		int size = craftingGridInput.getSize();
		int[] slots = new int[size];
		for (int i = 0; i < size; i++) {
			slots[i] = i;
		}
		return slots;
	}
	
	public @Nullable PlayerEntity getPlayer() {
		return this.player;
	}
	
}
