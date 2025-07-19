package earth.terrarium.pastel.items.food;

import earth.terrarium.pastel.items.ItemWithTooltip;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClottedCreamItem extends ItemWithTooltip {
	
	public ClottedCreamItem(Properties settings, String[] tooltips) {
		super(settings, tooltips);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		if (!world.isClientSide) {
			user.removeAllEffects();
		}
		
		return super.finishUsingItem(stack, world, user);
	}
	
}
