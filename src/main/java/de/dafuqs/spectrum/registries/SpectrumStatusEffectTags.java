package de.dafuqs.spectrum.registries;

import de.dafuqs.spectrum.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.registry.tag.*;

public class SpectrumStatusEffectTags {
	
	public static TagKey<StatusEffect> INCURABLE = of("uncurable");
	public static TagKey<StatusEffect> NO_DURATION_EXTENSION = of("no_duration_extension");
	public static TagKey<StatusEffect> SOPORIFIC = of("soporific");
	public static TagKey<StatusEffect> NIGHT_ALCHEMY = of("night_alchemy");
	public static TagKey<StatusEffect> STACKING = of("stacking");
	
	private static TagKey<StatusEffect> of(String id) {
		return TagKey.of(RegistryKeys.STATUS_EFFECT, SpectrumCommon.locate(id));
	}
	
	public static boolean isIncurable(RegistryEntry<StatusEffect> effect) {
		return effect.isIn(SpectrumStatusEffectTags.INCURABLE);
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
