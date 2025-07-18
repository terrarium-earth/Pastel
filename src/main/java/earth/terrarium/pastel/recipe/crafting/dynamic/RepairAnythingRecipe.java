package earth.terrarium.pastel.recipe.crafting.dynamic;

import earth.terrarium.pastel.registries.PastelItemTags;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelRecipeSerializers;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class RepairAnythingRecipe extends CustomRecipe {

    public RepairAnythingRecipe() {
        super(CraftingBookCategory.MISC);
    }

    @Override
    public boolean matches(CraftingInput input, Level world) {
        boolean nectarFound = false;
        boolean itemFound = false;

        for (int j = 0; j < input.size(); ++j) {
            ItemStack itemStack = input.getItem(j);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() == PastelItems.MOONSTRUCK_NECTAR.get()) {
                    if (nectarFound) {
                        return false;
                    }
                    nectarFound = true;
                } else if (itemStack.isDamageableItem() && itemStack.isDamaged() && !itemStack.is(
                    PastelItemTags.INDESTRUCTIBLE_BLACKLISTED)) {
                    if (itemFound) {
                        return false;
                    }
                    itemFound = true;
                }
            }
        }

        return nectarFound && itemFound;
    }

    @Override
    public ItemStack assemble(CraftingInput input, HolderLookup.Provider registryLookup) {
        ItemStack itemStack = ItemStack.EMPTY;
        for (int j = 0; j < input.size(); ++j) {
            itemStack = input.getItem(j);
            if (!itemStack.isEmpty() && itemStack.getItem() != PastelItems.MOONSTRUCK_NECTAR.get()) {
                break;
            }
        }

        if (itemStack.isDamageableItem() && itemStack.isDamaged() && !itemStack.is(
            PastelItemTags.INDESTRUCTIBLE_BLACKLISTED)) {
            ItemStack returnStack = itemStack.copy();
            int damage = returnStack.getDamageValue();
            int maxDamage = returnStack.getMaxDamage();

            int newDamage = Math.max(0, damage - maxDamage / 3);
            returnStack.setDamageValue(newDamage);
            return returnStack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PastelRecipeSerializers.REPAIR_ANYTHING_SERIALIZER;
    }

}
