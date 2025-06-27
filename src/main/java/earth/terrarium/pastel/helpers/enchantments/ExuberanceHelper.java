package earth.terrarium.pastel.helpers.enchantments;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.helpers.Ench;
import earth.terrarium.pastel.registries.PastelEnchantments;
import net.minecraft.world.entity.player.Player;

public class ExuberanceHelper {
	
	public static float getExuberanceMod(Player breakingPlayer) {
		if (breakingPlayer != null) {
			var drm = breakingPlayer.level().registryAccess();
			int exuberanceLevel = Ench.getEquipmentLevel(drm, PastelEnchantments.EXUBERANCE, breakingPlayer);
			return getExuberanceMod(exuberanceLevel);
		} else {
			return 1.0F;
		}
	}
	
	public static float getExuberanceMod(int level) {
		return 1.0F + level * PastelCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
	}
	
}