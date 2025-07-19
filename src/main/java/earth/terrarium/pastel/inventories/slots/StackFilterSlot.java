package earth.terrarium.pastel.inventories.slots;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StackFilterSlot extends Slot {

    private final Item acceptedItem;

    public StackFilterSlot(Container inventory, int index, int x, int y, Item acceptedItem) {
        super(inventory, index, x, y);
        this.acceptedItem = acceptedItem;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(acceptedItem);
    }

}
