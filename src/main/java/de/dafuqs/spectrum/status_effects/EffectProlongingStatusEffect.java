package de.dafuqs.spectrum.status_effects;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.entry.*;

public class EffectProlongingStatusEffect extends StatusEffect {
	
	public static final float ADDITIONAL_EFFECT_DURATION_MODIFIER_PER_LEVEL = 0.25F;
	
	public EffectProlongingStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}
	
	public static boolean canBeExtended(RegistryEntry<StatusEffect> statusEffect) {
		return !statusEffect.isIn(SpectrumStatusEffectTags.NO_DURATION_EXTENSION);
	}
	
	public static int getExtendedDuration(int originalDuration, int prolongingAmplifier) {
		return (int) (originalDuration * (1 + ADDITIONAL_EFFECT_DURATION_MODIFIER_PER_LEVEL * prolongingAmplifier));
	}
	
}
