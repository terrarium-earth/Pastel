package earth.terrarium.pastel.helpers.enchantments;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.helpers.SpectrumEnchantmentHelper;
import earth.terrarium.pastel.registries.SpectrumEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public class ImprovedCriticalHelper {
	
	public static float getAddtionalCritDamageMultiplier(int improvedCriticalLevel) {
		return SpectrumCommon.CONFIG.ImprovedCriticalExtraDamageMultiplierPerLevel * improvedCriticalLevel;
	}
	
	public static float getAddtionalCritDamageMultiplier(HolderLookup.Provider lookup, ItemStack stack) {
		return getAddtionalCritDamageMultiplier(SpectrumEnchantmentHelper.getLevel(lookup, SpectrumEnchantments.IMPROVED_CRITICAL, stack));
	}
	
}
