package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.helpers.ComponentHelper;
import earth.terrarium.pastel.registries.SpectrumDataComponentTypes;
import net.minecraft.world.item.ItemStack;

public interface ActivatableItem {
	
	static void setActivated(ItemStack stack, boolean activated) {
		ComponentHelper.setOrRemove(stack, SpectrumDataComponentTypes.ACTIVATED, activated);
	}
	
	static boolean isActivated(ItemStack stack) {
		return stack.has(SpectrumDataComponentTypes.ACTIVATED);
	}
	
}
