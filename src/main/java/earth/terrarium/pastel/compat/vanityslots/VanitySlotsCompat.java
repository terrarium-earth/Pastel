package earth.terrarium.pastel.compat.vanityslots;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.ModList;

public class VanitySlotsCompat {
    public static final boolean IS_VANITY_SLOTS_PRESENT = ModList
        .get()
        .isLoaded("vanityslots");

    public static ItemStack getEquippedStack(LivingEntity entity, EquipmentSlot slot) {
        if (VanitySlotsCompat.IS_VANITY_SLOTS_PRESENT) {
            return gay.nyako.vanityslots.CommonClass.getEquippedStack(entity, slot);
        } else {
            return entity.getItemBySlot(slot);
        }
    }
}
