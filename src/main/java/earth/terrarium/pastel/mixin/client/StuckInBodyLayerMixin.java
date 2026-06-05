package earth.terrarium.pastel.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.StuckInBodyLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StuckInBodyLayer.class)
public abstract class StuckInBodyLayerMixin {
    @Inject(method = "Lnet/minecraft/client/renderer/entity/layers/StuckInBodyLayer;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    private <T extends LivingEntity> void trueInvis(
        PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing,
        float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci
    ){
        if(livingEntity.hasEffect(PastelMobEffects.TRUE_INVISIBILITY))
            ci.cancel();
    }
}
