package earth.terrarium.pastel.helpers;

import earth.terrarium.pastel.capabilities.item.*;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;

public interface ContainerWrapper extends Container {

    FriendlyStackHandler getHandlerForScreens();

    @Override
    default int getContainerSize() {
        return getHandlerForScreens().getSlots();
    }

    @Override
    default boolean isEmpty() {
        return getHandlerForScreens().isEmpty();
    }

    default ItemStack getItem(int slot) {
        return getHandlerForScreens().getStackInSlot(slot);
    }

    @Override
    default ItemStack removeItem(int slot, int amount) {
        return getHandlerForScreens().extractItem(slot, amount, false);
    }

    @Override
    default void setChanged() {
        getHandlerForScreens().onContentsChanged(0);
    }

    @Override
    default ItemStack removeItemNoUpdate(int slot) {
        return getHandlerForScreens().removeStackInSlot(slot);
    }

    @Override
    default void setItem(int slot, ItemStack stack) {
        getHandlerForScreens().setStackInSlot(slot, stack);
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default void clearContent() {
        getHandlerForScreens().clear();
    }
}
