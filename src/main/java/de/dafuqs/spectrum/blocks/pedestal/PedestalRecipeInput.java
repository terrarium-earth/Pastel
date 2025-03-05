package de.dafuqs.spectrum.blocks.pedestal;

import java.util.*;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.recipe.input.*;
import org.jetbrains.annotations.*;

public class PedestalRecipeInput implements RecipeInput {
	
	private static final List<ItemStack> FULL_GEMSTONE_POWDER_STACKS = List.of(
			new ItemStack(SpectrumItems.TOPAZ_POWDER, 64),
			new ItemStack(SpectrumItems.AMETHYST_POWDER, 64),
			new ItemStack(SpectrumItems.CITRINE_POWDER, 64),
			new ItemStack(SpectrumItems.ONYX_POWDER, 64),
			new ItemStack(SpectrumItems.MOONSTONE_POWDER, 64)
	);
	
	@Nullable
	private final PlayerEntity player;
	private final CraftingRecipeInput craftingGridInput;
	private final List<ItemStack> gemstonePowderStacks;
	
	public PedestalRecipeInput(CraftingRecipeInput craftingGridInput, List<ItemStack> gemstonePowderStacks, @Nullable PlayerEntity player) {
		this.player = player;
		this.craftingGridInput = craftingGridInput;
		this.gemstonePowderStacks = gemstonePowderStacks;
	}
	
	public CraftingRecipeInput getCraftingGridInput() {
		return craftingGridInput;
	}
	
	public static PedestalRecipeInput create(List<ItemStack> stacks, @Nullable PlayerEntity player) {
		return new PedestalRecipeInput(CraftingRecipeInput.create(3, 3, stacks.subList(0, 9)), stacks.subList(9, 14), player);
	}
	
	public static PedestalRecipeInput createWithFullGemstonePowder(List<ItemStack> stacks, @Nullable PlayerEntity player) {
		return new PedestalRecipeInput(CraftingRecipeInput.create(3, 3, stacks), FULL_GEMSTONE_POWDER_STACKS, player);
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
