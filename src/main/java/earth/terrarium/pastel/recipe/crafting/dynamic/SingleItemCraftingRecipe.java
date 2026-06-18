package earth.terrarium.pastel.recipe.crafting.dynamic;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.level.Level;

public abstract class SingleItemCraftingRecipe extends CustomRecipe {

    public SingleItemCraftingRecipe() {
        super(CraftingBookCategory.MISC);
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        boolean matchingItemFound = false;

        for (
            int slot = 0;
            slot < input.size();
            ++slot
        ) {
            ItemStack itemStack = input.getItem(slot);
            if (itemStack.isEmpty()) {
                continue;
            }

            if (!matchingItemFound && matches(world, itemStack)) {
                matchingItemFound = true;
            } else {
                return false;
            }
        }

        return matchingItemFound;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registryLookup) {
        ItemStack stack;
        for (
            int slot = 0;
            slot < input.size();
            ++slot
        ) {
            stack = input.getItem(slot);
            if (!stack.isEmpty()) {
                return assemble(stack.copy());
            }
        }

        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height > 0;
    }

    public abstract boolean matches(Level world, ItemStack stack);

    public abstract ItemStack assemble(ItemStack stack);

}
