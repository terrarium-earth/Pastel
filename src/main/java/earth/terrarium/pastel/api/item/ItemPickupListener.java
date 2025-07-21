package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.capabilities.PastelCapabilities;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.IItemHandlerModifiable;

import java.util.Optional;

/**
 * Implementation detail: While not currently the case in base Pastel. The listener shall be empty if not on an item.
 */
public interface ItemPickupListener {

    boolean accepts(Optional<ItemStack> listener, ItemStack proposal);

    /**
     * @return The remainder
     */
    ItemStack receive(Optional<ItemStack> listener, ItemStack stack, Optional<Entity> taker);

    static ItemStack receiveRecursive(IItemHandler inventory, int depthLimit, int depth,
                                      ItemStack stack, Optional<Entity> taker) {

        var tries = Math.min(inventory.getSlots(), 128);

        for (int slot = 0; slot < tries; slot++) {
            if (stack.isEmpty())
                return ItemStack.EMPTY;

            var proposal = inventory.getStackInSlot(slot);
            var receiver = proposal.getCapability(PastelCapabilities.Pickup.ITEM);
            var prop = Optional.of(proposal);

            if (receiver != null && receiver.accepts(prop, stack) && inventory instanceof IItemHandlerModifiable mod) {
                stack = receiver.receive(prop, stack, taker);
                mod.setStackInSlot(slot, proposal);
                continue;
            }

            if (depth == depthLimit)
                continue;

            var depthInv = proposal.getCapability(Capabilities.ItemHandler.ITEM);
            if (depthInv != null) {
                stack = receiveRecursive(depthInv, depthLimit, depth + 1, stack, taker);
            }
        }

        return stack;
    }
}
