package de.dafuqs.spectrum.helpers;

import net.minecraft.core.*;
import net.minecraft.nbt.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.items.*;

import java.util.*;
import java.util.function.*;

public class FriendlyStackHandler extends ItemStackHandler {

    public FriendlyStackHandler(int size) {
        super(size);
    }

    public FriendlyStackHandler(NonNullList<ItemStack> stacks) {
        super(stacks);
    }

    public ItemStack removeStackInSlot(int slot) {
        ItemStack removed;

        this.validateSlotIndex(slot);
        removed = this.stacks.set(slot, ItemStack.EMPTY);
        this.onContentsChanged(slot);
        return removed;
    }

    public NonNullList<ItemStack> getInternalList() {
        return stacks;
    }

    public void setInternalList(NonNullList<ItemStack> newStacks) {
        if (newStacks.size() != stacks.size())
            throw new RuntimeException("Attempted to replace stack handler list with one of an unequal size!");
        stacks = newStacks;
        for (int i = 0; i < getSlots(); i++) {
            onContentsChanged(i);
        }
    }

    public void save(CompoundTag tag, HolderLookup.Provider provider) {
        tag.put("inventory", serializeNBT(provider));
    }

    public void load(CompoundTag tag, HolderLookup.Provider provider) {
        deserializeNBT(provider, tag.getCompound("inventory"));
    }

    public boolean isEmpty() {
        for (ItemStack stack : stacks) {
            if (!stack.isEmpty())
                return false;
        }
        return true;
    }

    public void clear() {
        stacks.clear();
        for (int i = 0; i < getSlots(); i++) {
            onContentsChanged(i);
        }
    }

    public boolean hasAnyOf(Set<Item> set) {
        return this.hasAnyMatching(item -> !item.isEmpty() && set.contains(item.getItem()));
    }

    public boolean hasAnyMatching(Predicate<ItemStack> predicate) {
        for (int i = 0; i < this.getSlots(); i++) {
            var stack = this.getStackInSlot(i);
            if (predicate.test(stack)) {
                return true;
            }
        }

        return false;
    }
}
