package earth.terrarium.pastel.capabilities.item;


import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public class StackHandlerView extends FriendlyStackHandler {

    protected final FriendlyStackHandler delegator;
    private final Int2ObjectMap<Predicate<ItemStack>> filters = new Int2ObjectArrayMap<>();
    private final int offset;
    private final int size;
    private boolean supportsInsertion = true;
    private boolean supportsExtraction = true;

    public StackHandlerView(FriendlyStackHandler delegator, int offset, int size) {
        super(null);
        this.delegator = delegator;
        this.offset = offset;
        this.size = size;
    }

    public StackHandlerView(FriendlyStackHandler delegator, int offset) {
        this(delegator, offset, 1);
    }

    public StackHandlerView(FriendlyStackHandler delegator) {
        this(delegator, 0, delegator.getSlots());
    }

    public StackHandlerView disableExtraction() {
        this.supportsExtraction = false;
        return this;
    }

    public StackHandlerView disableInsertion() {
        this.supportsInsertion = false;
        return this;
    }

    @Override
    public int getSlots() {
        return size;
    }

    public StackHandlerView addFilter(int slot, Predicate<ItemStack> filter) {
        filters.put(slot, filter);
        return this;
    }

    public StackHandlerView addFilter(int slot) {
        filters.put(slot, stack -> false);
        return this;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack) {
        delegator.setStackInSlot(slot + offset, stack);
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return delegator.getStackInSlot(slot + offset);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        if (!supportsInsertion)
            return stack;

        if (!filters.getOrDefault(slot, stck -> true)
                    .test(stack))
            return stack;

        return delegator.insertItem(slot + offset, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (!supportsExtraction)
            return ItemStack.EMPTY;

        return delegator.extractItem(slot + offset, amount, simulate);
    }

    @Override
    public ItemStack removeStackInSlot(int slot) {
        return delegator.removeStackInSlot(slot + offset);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return super.isItemValid(slot, stack);
    }

    @Override
    protected void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= size) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + size + ")");
        }
    }

    @Override
    public void setInternalList(NonNullList<ItemStack> newStacks) {
        throw new UnsupportedOperationException(
            "Attempted to change the internal list of a forwarded ItemStackHandler");
    }

    @Override
    public void setSize(int size) {
        throw new UnsupportedOperationException("Attempted to change the size of a forwarded ItemStackHandler");
    }

    @Override
    public void save(CompoundTag tag, HolderLookup.Provider provider) {
    }

    @Override
    public void load(CompoundTag tag, HolderLookup.Provider provider) {
    }
}
