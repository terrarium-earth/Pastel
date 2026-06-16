package earth.terrarium.pastel.api.item;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface EquipAwareItem {
    void onEquipChange(LivingEntity entity, ItemStack stack, EquipmentSlot slot, boolean unequip);
}
