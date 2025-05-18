package de.dafuqs.spectrum.helpers.enchantments;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import net.minecraft.world.entity.player.Player;

public class ExuberanceHelper {
	
	public static float getExuberanceMod(Player breakingPlayer) {
		if (breakingPlayer != null) {
			var drm = breakingPlayer.level().registryAccess();
			int exuberanceLevel = SpectrumEnchantmentHelper.getEquipmentLevel(drm, SpectrumEnchantments.EXUBERANCE, breakingPlayer);
			return getExuberanceMod(exuberanceLevel);
		} else {
			return 1.0F;
		}
	}
	
	public static float getExuberanceMod(int level) {
		return 1.0F + level * SpectrumCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
	}
	
}