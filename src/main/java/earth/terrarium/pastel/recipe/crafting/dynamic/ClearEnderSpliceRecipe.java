package earth.terrarium.pastel.recipe.crafting.dynamic;

import earth.terrarium.pastel.items.magic_items.EnderSpliceItem;
import earth.terrarium.pastel.registries.SpectrumRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ClearEnderSpliceRecipe extends SingleItemCraftingRecipe {
	
	@Override
	public boolean matches(Level world, ItemStack stack) {
		return stack.getItem() instanceof EnderSpliceItem && EnderSpliceItem.hasTeleportTarget(stack);
	}
	
	@Override
	public ItemStack assemble(ItemStack stack) {
		ItemStack returnStack = stack.copy();
		returnStack.setCount(1);
		EnderSpliceItem.clearTeleportTarget(returnStack);
		return returnStack;
	}
	
	@Override
	public RecipeSerializer<?> getSerializer() {
		return SpectrumRecipeSerializers.CLEAR_ENDER_SPLICE_SERIALIZER;
	}
	
}
