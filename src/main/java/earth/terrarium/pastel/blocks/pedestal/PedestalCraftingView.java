package earth.terrarium.pastel.blocks.pedestal;

import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import earth.terrarium.pastel.capabilities.item.StackHandlerView;
import net.minecraft.world.item.ItemStack;

public class PedestalCraftingView extends StackHandlerView {

    private final PedestalBlockEntity pedestal;

    public PedestalCraftingView(PedestalBlockEntity pedestal, FriendlyStackHandler delegator) {
        super(delegator, 0, 9);
        this.pedestal = pedestal;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        var ing = pedestal.getTabletIngredients();

        if (ing.isEmpty())
            return super.insertItem(slot, stack, simulate);

        if (slot >= ing.size() || !ing.get(slot).test(stack))
            return stack;

        delegator.setTempLimit(getSlotLimit(slot));
        var remnant = super.insertItem(slot, stack, simulate);
        delegator.setTempLimit(-1);

        return remnant;
    }

    @Override
    public int getSlotLimit(int slot) {
        var ing = pedestal.getTabletIngredients();

        if (ing.isEmpty())
            return super.getSlotLimit(slot);

        if (slot > ing.size() || ing.get(slot).isEmpty())
            return 0;
        return 4;
    }
}
