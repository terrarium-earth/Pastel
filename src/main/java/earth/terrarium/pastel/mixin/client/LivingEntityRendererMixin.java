package earth.terrarium.pastel.mixin.client;

import earth.terrarium.pastel.entity.entity.CanvasWorkaroundPlayerEntity;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
    LivingEntityRenderer.class
)
public class LivingEntityRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {
    @Inject(
        method = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At(
            "HEAD"
        ), cancellable = true
    )
    private void doNotRenderNamesForThoseThatDwellInInk(T entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof CanvasWorkaroundPlayerEntity)
            cir.setReturnValue(false);
    }
}
