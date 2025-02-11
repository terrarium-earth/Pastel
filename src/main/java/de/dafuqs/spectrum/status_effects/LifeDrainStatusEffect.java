package de.dafuqs.spectrum.status_effects;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class LifeDrainStatusEffect extends StatusEffect {
	
	public static final Identifier ATTRIBUTE_ID = SpectrumCommon.locate("effect.life_drain");
	
	public LifeDrainStatusEffect(StatusEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
		if (entity instanceof PlayerEntity player && (player.isCreative() || player.isSpectator())) {
			return;
		}
		
		EntityAttributeInstance instance = entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
		if (instance != null) {
			var dragon = entity.getType().isIn(SpectrumEntityTypeTags.DRACONIC);
			EntityAttributeModifier currentMod = instance.getModifier(ATTRIBUTE_ID);
			if (currentMod != null) {
				instance.removeModifier(currentMod);
				EntityAttributeModifier newModifier = new EntityAttributeModifier(ATTRIBUTE_ID, currentMod.value() - (dragon ? 2 : 1), EntityAttributeModifier.Operation.ADD_VALUE);
				instance.addPersistentModifier(newModifier);
				instance.getValue(); // recalculate final value
				if (entity.getHealth() > entity.getMaxHealth()) {
					entity.setHealth(entity.getMaxHealth());
				}
			}
		}

		return true;
	}
	
	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return duration % Math.max(1, 40 - amplifier * 2) == 0;
	}
	
}
