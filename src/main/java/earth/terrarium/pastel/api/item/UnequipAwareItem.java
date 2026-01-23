package earth.terrarium.pastel.api.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface UnequipAwareItem {
    void onUnequip(LivingEntity entity, ItemStack stack, EquipmentSlot slot);
}
