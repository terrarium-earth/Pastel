package earth.terrarium.pastel.helpers.enchantments;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.item.ItemStack;

public class ImprovedCriticalHelper {

    public static float getAddtionalCritDamageMultiplier(int improvedCriticalLevel) {
        return PastelCommon.CONFIG.ImprovedCriticalExtraDamageMultiplierPerLevel * improvedCriticalLevel;
    }

    public static float getAddtionalCritDamageMultiplier(HolderLookup.Provider lookup, ItemStack stack) {
        return getAddtionalCritDamageMultiplier(Ench.getLevel(lookup, PastelEnchantments.IMPROVED_CRITICAL, stack));
    }

}
