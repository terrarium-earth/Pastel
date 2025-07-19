package earth.terrarium.pastel.inventories.slots;

import earth.terrarium.pastel.recipe.InkConvertingRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ColorPickerInputSlot extends Slot {

    public ColorPickerInputSlot(Container inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return super.mayPlace(stack) && InkConvertingRecipe.isInput(stack.getItem());
    }

}
