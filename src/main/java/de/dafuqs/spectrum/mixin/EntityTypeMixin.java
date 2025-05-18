package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.entity.SpectrumEntityTypes;
import net.minecraft.world.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin {
	
	@Inject(at = @At("TAIL"), method = "trackDeltas", cancellable = true)
	public void alwaysUpdateVelocity(CallbackInfoReturnable<Boolean> cir) {
		Object thisObject = this;
		if (thisObject == SpectrumEntityTypes.PHANTOM_FRAME || thisObject == SpectrumEntityTypes.GLOW_PHANTOM_FRAME || thisObject == SpectrumEntityTypes.KINDLING_COUGH) {
			cir.setReturnValue(false);
		}
	}
	
}
