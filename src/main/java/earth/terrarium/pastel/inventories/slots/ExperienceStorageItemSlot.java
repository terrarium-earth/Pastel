package earth.terrarium.pastel.inventories.slots;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.capabilities.ExperienceHandler;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class ExperienceStorageItemSlot extends Slot {
	
	public ExperienceStorageItemSlot(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public boolean mayPlace(ItemStack stack) {
		if (!super.mayPlace(stack))
			return false;

		return stack.getCapability(PastelCapabilities.Misc.XP, PastelCommon.getRegistryAccess()) != null;
	}
	
}
