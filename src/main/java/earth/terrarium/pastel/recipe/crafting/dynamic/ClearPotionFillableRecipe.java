package earth.terrarium.pastel.recipe.crafting.dynamic;

import earth.terrarium.pastel.api.item.InkPoweredPotionFillable;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class ClearPotionFillableRecipe extends SingleItemCraftingRecipe {

    @Override
    public boolean matches(Level world, ItemStack stack) {
        return stack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable && inkPoweredPotionFillable
            .isAtLeastPartiallyFilled(stack);
    }

    @Override
    public ItemStack assemble(ItemStack stack) {
        if (stack.getItem() instanceof InkPoweredPotionFillable inkPoweredPotionFillable) {
            stack = stack.copy();
            stack.setCount(1);
            inkPoweredPotionFillable.clearEffects(stack);
        }
        return stack;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.CLEAR_POTION_FILLABLE_SERIALIZER;
    }

}
