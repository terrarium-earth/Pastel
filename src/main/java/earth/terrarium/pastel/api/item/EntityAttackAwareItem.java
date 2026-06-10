package earth.terrarium.pastel.api.item;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface EntityAttackAwareItem {
    void onEntityDamage(ItemStack stack, LivingEntity target, LivingEntity attacker, DamageSource source, float amount);
}
