package earth.terrarium.pastel.recipe.crafting.dynamic;

import earth.terrarium.pastel.api.energy.InkStorageItem;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ClearInkRecipe extends SingleItemCraftingRecipe {
	
	@Override
	public boolean matches(Level world, ItemStack stack) {
		return stack.getItem() instanceof InkStorageItem;
	}
	
	@Override
	public ItemStack assemble(ItemStack stack) {
		if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
			stack = stack.copy();
			stack.setCount(1);
			inkStorageItem.clearEnergyStorage(stack);
		}
		return stack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return PastelRecipeSerializers.CLEAR_INK_SERIALIZER;
	}
	
}
