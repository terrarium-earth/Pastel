package earth.terrarium.pastel.blocks;

import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public interface Containerlike extends WorldlyContainer {

    @Override
    default int[] getSlotsForFace(Direction side) {
        return new int[] {
            0
        };
    }

    @Override
    default boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return direction != Direction.DOWN;
    }

    @Override
    default boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction == Direction.DOWN;
    }

    @Override
    default int getContainerSize() {
        return getInventory().getSlots();
    }

    @Override
    default boolean isEmpty() {
        return getInventory().isEmpty();
    }

    @Override
    default ItemStack getItem(int slot) {
        return getInventory().getStackInSlot(slot);
    }

    @Override
    default ItemStack removeItem(int slot, int amount) {
        return getInventory().extractItem(slot, amount, false);
    }

    @Override
    default ItemStack removeItemNoUpdate(int slot) {
        var internal = getInventory().getInternalList();
        return internal.set(slot, ItemStack.EMPTY);
    }

    @Override
    default void setItem(int slot, ItemStack stack) {
        getInventory().setStackInSlot(slot, stack);
    }

    default void containerChanged() {

    }

    @Override
    default void setChanged() {
        containerChanged();
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default void clearContent() {
        throw new UnsupportedOperationException("No.");
    }

    FriendlyStackHandler getInventory();
}
