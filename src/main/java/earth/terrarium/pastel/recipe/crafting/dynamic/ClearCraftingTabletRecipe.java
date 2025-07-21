package earth.terrarium.pastel.recipe.crafting.dynamic;

import earth.terrarium.pastel.items.magic_items.CraftingTabletItem;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ClearCraftingTabletRecipe extends SingleItemCraftingRecipe {

    @Override
    public boolean matches(Level world, ItemStack stack) {
        return stack.getItem() instanceof CraftingTabletItem && CraftingTabletItem.getStoredRecipe(world, stack) !=
                                                                null;
    }

    @Override
    public ItemStack assemble(ItemStack stack) {
        CraftingTabletItem.clearStoredRecipe(stack);
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.CLEAR_CRAFTING_TABLET_SERIALIZER;
    }

}
