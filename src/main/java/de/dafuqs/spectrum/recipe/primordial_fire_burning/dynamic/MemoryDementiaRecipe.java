package de.dafuqs.spectrum.recipe.primordial_fire_burning.dynamic;

import de.dafuqs.spectrum.blocks.memory.*;
import de.dafuqs.spectrum.recipe.primordial_fire_burning.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.world.*;

import java.util.*;

public class MemoryDementiaRecipe extends PrimordialFireBurningRecipe {
	
	public MemoryDementiaRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER),
				Ingredient.ofStacks(MemoryItem.getForEntityType(EntityType.BEE, 1), MemoryItem.getForEntityType(EntityType.FOX, 10), MemoryItem.getForEntityType(EntityType.SKELETON, 5), MemoryItem.getForEntityType(EntityType.HUSK, 50), MemoryItem.getForEntityType(EntityType.BLAZE, -1)),
				SpectrumBlocks.MEMORY.asItem().getDefaultStack());
	}
	
	@Override
	public boolean matches(RecipeInput inv, World world) {
		return inv.getStackInSlot(0).contains(SpectrumDataComponentTypes.MEMORY);
	}
	
	@Override
	public ItemStack craft(RecipeInput inv, RegistryWrapper.WrapperLookup drm) {
		ItemStack stack = inv.getStackInSlot(0);
		stack.remove(SpectrumDataComponentTypes.MEMORY);
		return stack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.MEMORY_DEMENTIA;
	}
	
}
