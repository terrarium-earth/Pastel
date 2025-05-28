package earth.terrarium.pastel.api.item;

import earth.terrarium.pastel.registries.SpectrumDataComponentTypes;
import net.minecraft.util.Unit;
import net.minecraft.world.item.ItemStack;

public interface FermentedItem {
	
	static boolean isPreviewStack(ItemStack stack) {
		return stack.has(SpectrumDataComponentTypes.IS_PREVIEW_ITEM);
	}
	
	static void setPreviewStack(ItemStack stack) {
		stack.set(SpectrumDataComponentTypes.IS_PREVIEW_ITEM, Unit.INSTANCE);
	}
	
}
