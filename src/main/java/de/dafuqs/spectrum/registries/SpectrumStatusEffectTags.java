package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

public class SpectrumStatusEffectTags {
	
	public static TagKey<StatusEffect> BYPASSES_WHISPY_CIRCLET = of("bypasses_whispy_circlet");
	public static TagKey<StatusEffect> BYPASSES_NECTAR_GLOVES = of("bypasses_nectar_gloves");
	public static TagKey<StatusEffect> BYPASSES_IMMUNITY = of("bypasses_immunity");
	public static TagKey<StatusEffect> CANNOT_BE_INCURABLE = of("cannot_be_incurable");
	public static TagKey<StatusEffect> NO_DURATION_EXTENSION = of("no_duration_extension");
	public static TagKey<StatusEffect> SOPORIFIC = of("soporific");
	public static TagKey<StatusEffect> NIGHT_ALCHEMY = of("night_alchemy");
	public static TagKey<StatusEffect> STACKING = of("stacking");
	
	private static TagKey<StatusEffect> of(String id) {
		return TagKey.of(RegistryKeys.STATUS_EFFECT, SpectrumCommon.locate(id));
	}
	
	public static boolean hasEffectWithTag(LivingEntity livingEntity, TagKey<StatusEffect> tag) {
		for (var statusEffect : livingEntity.getActiveStatusEffects().keySet()) {
			if (statusEffect.isIn(tag)) {
				return true;
			}
		}
		return false;
	}
	
}
