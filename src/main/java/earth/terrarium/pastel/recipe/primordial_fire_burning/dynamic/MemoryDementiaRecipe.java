package earth.terrarium.pastel.recipe.primordial_fire_burning.dynamic;

import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.recipe.primordial_fire_burning.PrimordialFireBurningRecipe;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class MemoryDementiaRecipe extends PrimordialFireBurningRecipe {
	
	public MemoryDementiaRecipe() {
		super("", false, Optional.of(UNLOCK_IDENTIFIER),
				Ingredient.of(MemoryItem.getForEntityType(EntityType.BEE, 1), MemoryItem.getForEntityType(EntityType.FOX, 10), MemoryItem.getForEntityType(EntityType.SKELETON, 5), MemoryItem.getForEntityType(EntityType.HUSK, 50), MemoryItem.getForEntityType(EntityType.BLAZE, -1)),
                PastelBlocks.MEMORY.get().asItem().getDefaultInstance());
	}
	
	@Override
	public boolean matches(RecipeInput inv, Level world) {
		return inv.getItem(0).has(PastelDataComponentTypes.MEMORY);
	}
	
	@Override
	public ItemStack assemble(RecipeInput inv, HolderLookup.Provider drm) {
		ItemStack stack = inv.getItem(0);
		stack.remove(PastelDataComponentTypes.MEMORY);
		return stack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.MEMORY_DEMENTIA;
	}
	
}
