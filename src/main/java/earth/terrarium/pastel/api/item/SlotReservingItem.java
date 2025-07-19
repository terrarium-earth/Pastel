package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface SlotReservingItem {

    static boolean isReservingSlot(ItemStack stack) {
        return stack.has(PastelDataComponentTypes.SLOT_RESERVER);
    }

    static UUID getReserver(ItemStack stack) {
        return stack.get(PastelDataComponentTypes.SLOT_RESERVER);
    }

    static boolean isReserver(ItemStack stack, @NotNull UUID uuid) {
        return uuid.equals(getReserver(stack));
    }

    static void reserve(ItemStack stack, UUID reserver) {
        stack.set(PastelDataComponentTypes.SLOT_RESERVER, reserver);
    }

    static void free(ItemStack stack) {
        stack.remove(PastelDataComponentTypes.SLOT_RESERVER);
    }

}
