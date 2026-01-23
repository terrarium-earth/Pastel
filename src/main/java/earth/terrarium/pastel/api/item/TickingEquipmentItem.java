package earth.terrarium.pastel.api.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface TickingEquipmentItem {
    void tick(LivingEntity entity, ItemStack stack);
}
