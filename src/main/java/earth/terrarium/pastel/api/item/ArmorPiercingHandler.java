package earth.terrarium.pastel.api.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

/**
 * Make sure to wrap your damage source with stack tracking or this won't work!
 */
public interface ArmorPiercingHandler extends SplitDamageHandler {
	
	float getDefenseMultiplier(LivingEntity target, ItemStack stack);
	
	float getProtReduction(LivingEntity target, ItemStack stack);
}
