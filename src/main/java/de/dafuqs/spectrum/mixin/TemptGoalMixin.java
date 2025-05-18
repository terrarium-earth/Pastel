package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.items.magic_items.NaturesStaffItem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TemptGoal.class)
public class TemptGoalMixin {
	
	@Inject(at = @At("HEAD"), method = "shouldFollow", cancellable = true)
	private void isTemptedBy(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		if (entity.isUsingItem() && entity.getUseItem().getItem() instanceof NaturesStaffItem) {
			cir.setReturnValue(true);
		}
	}
	
}
