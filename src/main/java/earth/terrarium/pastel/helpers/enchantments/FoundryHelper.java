package earth.terrarium.pastel.helpers.enchantments;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FoundryHelper {

    @Nullable
    public static ItemStack getSmeltedItemStack(ItemStack inputItemStack, Level world) {
        var drm = world.registryAccess();
        var input = new SingleRecipeInput(inputItemStack);
        return world.getRecipeManager()
                    .getRecipeFor(RecipeType.SMELTING, input, world)
                    .map(recipe -> {
                        var recipeOutputStack = recipe.value()
                                                      .getResultItem(drm)
                                                      .copy();
                        recipeOutputStack.setCount(recipeOutputStack.getCount() * inputItemStack.getCount());
                        return recipeOutputStack;
                    })
                    .orElse(null);
    }

    @NotNull
    public static List<ItemStack> applyFoundry(Level world, List<ItemStack> originalStacks) {
        List<ItemStack> returnItemStacks = new ArrayList<>();

        for (ItemStack is : originalStacks) {
            ItemStack smeltedStack = FoundryHelper.getSmeltedItemStack(is, world);
            if (smeltedStack == null) {
                returnItemStacks.add(is);
            } else {
                while (!smeltedStack.isEmpty()) {
                    int currentAmount = Math.min(smeltedStack.getCount(), smeltedStack.getItem()
                                                                                      .getDefaultMaxStackSize()
                    );
                    ItemStack currentStack = smeltedStack.copyWithCount(currentAmount);
                    returnItemStacks.add(currentStack);
                    smeltedStack.setCount(smeltedStack.getCount() - currentAmount);
                }
            }
        }
        return returnItemStacks;
    }

}
