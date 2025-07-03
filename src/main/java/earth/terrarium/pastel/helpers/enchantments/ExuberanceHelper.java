package earth.terrarium.pastel.helpers.enchantments;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.Ench;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class ExuberanceHelper {
	
	public static float getExuberanceMod(RegistryAccess access, ItemStack tool) {
		var level = Ench.getLevel(access, PastelEnchantments.EXUBERANCE, tool);
		return getExuberanceMod(level);
	}
	
	public static float getExuberanceMod(int level) {
		return 1.0F + level * PastelCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
	}
	
}