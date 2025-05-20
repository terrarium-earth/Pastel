package de.dafuqs.spectrum.inventories.storage;

import net.minecraft.world.item.ItemStack;
import net.fabricmc.fabric.api.transfer.v1.item.base.SingleItemStorage;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DroppedItemStorage extends SingleItemStorage {

    public DroppedItemStorage(ItemStack itemStack) {
        this.variant = ItemStack.of(itemStack);
        this.amount = itemStack.getCount();
    }

    public DroppedItemStorage(Item item, DataComponentPatch componentChanges) {
        this.variant = ItemStack.of(item, componentChanges);
        this.amount = 1;
    }

    @Override
    protected long getCapacity(ItemStack variant) {
        return 1;
    }
}