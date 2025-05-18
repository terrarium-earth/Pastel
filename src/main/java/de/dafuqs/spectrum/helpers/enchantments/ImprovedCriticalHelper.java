package de.dafuqs.spectrum.helpers.enchantments;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
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
