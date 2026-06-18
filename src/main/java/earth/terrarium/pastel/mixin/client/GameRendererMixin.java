package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.attachments.data.ConsumptionRingData;
import earth.terrarium.pastel.imbrifer.Environmental;
import earth.terrarium.pastel.registries.client.PastelShaders;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    value = GameRenderer.class, priority = 9999
)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @Inject(
        method = "bobHurt", at = @At(
            "HEAD"
        )
    )
    private void fuckYou(PoseStack poseStack, float partialTicks, CallbackInfo ci) {
        if (minecraft.getCameraEntity() instanceof LivingEntity entity && entity
            .getData(
                ConsumptionRingData.ATTACHMENT
            )) entity.hurtTime = 0;
    }

    @ModifyReturnValue(
        method = "getNightVisionScale", at = @At(
            "RETURN"
        )
    )
    private static float modifyNightVision(float original, LivingEntity entity, float tickDelta) {
        if (Environmental
            .isActive()
            .force()) {
            original /= 6F;
        }
        original *= 1F - Environmental
            .getEnvData()
            .darkening();

        return original;
    }

    @Inject(
        method = "render", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;getMainRenderTarget()" + "Lcom/mojang/blaze3d/pipeline/RenderTarget;", shift = At.Shift.BEFORE
        )
    )
    private void applyPostProcessShaders(DeltaTracker tickCounter, boolean tick, CallbackInfo ci) {
        RenderSystem.disableBlend();
        RenderSystem.disableDepthTest();
        RenderSystem.resetTextureMatrix();
        PastelShaders.colorGradingPostProcess.ifPresent(pps -> pps.process(tickCounter.getGameTimeDeltaTicks()));
    }

    @Inject(
        method = "close", at = @At(
            "TAIL"
        )
    )
    private void closeShaders(CallbackInfo ci) {
        PastelShaders.clearDimensionShaders();
    }

    @Inject(
        method = "resize", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;resize(II)V"
        )
    )
    private void resizeShaders(int width, int height, CallbackInfo ci) {
        PastelShaders.resizeShaders(width, height);
    }
}
