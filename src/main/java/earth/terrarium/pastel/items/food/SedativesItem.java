package earth.terrarium.pastel.items.food;

import earth.terrarium.pastel.items.ItemWithTooltip;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SedativesItem extends ItemWithTooltip {
	
	public SedativesItem(Properties settings, String tooltip) {
		super(settings, tooltip);
	}
	
	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity user) {
		if (!world.isClientSide) { // TODO: do we need this? Frenzy is self-stacking; this also removed all hidden status effects that are not max potency! // Dafuqs: Mildly concerning indeed
			var frenzy = user.getEffect(PastelStatusEffects.FRENZY);
			
			if (frenzy != null) {
				var level = frenzy.getAmplifier();
				var duration = frenzy.getDuration();
				
				if (world.getRandom().nextInt((int) (frenzy.getAmplifier() + Math.round(duration / 30.0) + 1)) == 0) {
					user.removeEffect(PastelStatusEffects.FRENZY);
					if (frenzy.getAmplifier() > 0) {
						user.addEffect(new MobEffectInstance(PastelStatusEffects.FRENZY, duration, level - 1, frenzy.isAmbient(), frenzy.isVisible(), frenzy.showIcon()));
					}
				}
			}
			
			// TODO - Reenable compat when up-to-date
			//if (PastelIntegrationPacks.isIntegrationPackActive(PastelIntegrationPacks.NEEPMEAT_ID)) {
			//	NEEPMeatCompat.sedateEnlightenment(user);
			//}
		}
		
		return super.finishUsingItem(stack, world, user);
	}
	
}
