package earth.terrarium.pastel.capabilities.item;

import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class FriendlyStackHandler extends ItemStackHandler {

    private List<Consumer<Integer>> listeners;
    private final Int2IntMap limits = new Int2IntOpenHashMap();
    private int tempLimit = -1;

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

    public ItemStack removeStackInSlotNoUpdate(int slot) {
        ItemStack removed;

        this.validateSlotIndex(slot);
        removed = this.stacks.set(slot, ItemStack.EMPTY);
        return removed;
    }

    public NonNullList<ItemStack> getInternalList() {
        return stacks;
    }

    //TODO: blow this up with hammers
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

    public void addListener(Consumer<Integer> listener) {
        if (listeners == null)
            listeners = new ArrayList<>();
        listeners.add(listener);
    }

    @Override
    public void onContentsChanged(int slot) {
        if (listeners == null)
            return;

        listeners.forEach(l -> l.accept(slot));
    }

    public boolean hasAnyOf(Set<Item> set) {
        return this.hasAnyMatching(item -> !item.isEmpty() && set.contains(item.getItem()));
    }

    @Override
    protected void validateSlotIndex(int slot) {
        super.validateSlotIndex(slot);
    }

    @Override
    public int getSlotLimit(int slot) {
        if (tempLimit != -1)
            return tempLimit;

        if (limits.containsKey(slot))
            return limits.get(slot);

        return super.getSlotLimit(slot);
    }

    public FriendlyStackHandler addLimit(int slot, int limit) {
        limits.put(slot, limit);
        return this;
    }

    public void clearLimits() {
        limits.clear();
    }

    @ApiStatus.Internal
    public void setTempLimit(int tempLimit) {
        this.tempLimit = tempLimit;
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
