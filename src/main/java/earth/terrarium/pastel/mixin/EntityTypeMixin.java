package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.entity.PastelEntityTypes;
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
        if (thisObject == PastelEntityTypes.PHANTOM_FRAME.get() ||
            thisObject == PastelEntityTypes.GLOW_PHANTOM_FRAME.get() ||
            thisObject == PastelEntityTypes.KINDLING_COUGH.get()) {
            cir.setReturnValue(false);
        }
    }

}
