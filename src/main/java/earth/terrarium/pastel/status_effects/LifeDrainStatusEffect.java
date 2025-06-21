package earth.terrarium.pastel.status_effects;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelEntityTypeTags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class LifeDrainStatusEffect extends MobEffect {
	
	public static final ResourceLocation ATTRIBUTE_ID = PastelCommon.locate("effect.life_drain");
	
	public LifeDrainStatusEffect(MobEffectCategory category, int color) {
		super(category, color);
	}
	
	@Override
	public boolean applyEffectTick(LivingEntity entity, int amplifier) {
		if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
			return true;
		}
		
		AttributeInstance instance = entity.getAttribute(Attributes.MAX_HEALTH);
		if (instance != null) {
			var dragon = entity.getType().is(PastelEntityTypeTags.DRACONIC);
			AttributeModifier currentMod = instance.getModifier(ATTRIBUTE_ID);
			if (currentMod != null) {
				instance.removeModifier(currentMod);
				AttributeModifier newModifier = new AttributeModifier(ATTRIBUTE_ID, currentMod.amount() - (dragon ? 2 : 1), AttributeModifier.Operation.ADD_VALUE);
				instance.addPermanentModifier(newModifier);
				instance.getValue(); // recalculate final value
				if (entity.getHealth() > entity.getMaxHealth()) {
					entity.setHealth(entity.getMaxHealth());
				}
			}
		}
		
		return true;
	}
	
	@Override
	public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
		return duration % Math.max(1, 40 - amplifier * 2) == 0;
	}
	
}
