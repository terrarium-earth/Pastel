package earth.terrarium.pastel.items.food;

import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BloodOrchidDrinkItem extends DrinkItem{

    public BloodOrchidDrinkItem(Properties settings) {
        super(settings);
    }

    public BloodOrchidDrinkItem(Properties settings, String tooltip) {
        super(settings, tooltip);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
        user.removeEffectsCuredBy(PastelStatusEffects.Cures.BLOOD_ORCHID);
        return super.finishUsingItem(stack, world, user);
    }
}
