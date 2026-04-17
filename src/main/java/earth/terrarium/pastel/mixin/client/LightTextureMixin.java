package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.attachments.data.SpectacleData;
import earth.terrarium.pastel.imbrifer.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LightTexture.class, priority = 9999)

public class LightTextureMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyReturnValue(method = "calculateDarknessScale", at = @At("RETURN"))
    private float getDarkness(float original) {
        var lightMod = PastelCommon.CONFIG.DimensionBrightnessMod * 0.25F;

        if (Environmental.isActive().overrides) {
            var data = Environmental.getEnvData();
            return Math.max(data.darkening() - lightMod, original);
        }
        return original;
    }

    @Inject(method = "updateLightTexture", at = @At(value = "INVOKE",
                                                    target = "Lorg/joml/Vector3f;lerp(Lorg/joml/Vector3fc;F)Lorg/joml/Vector3f;", ordinal = 0))
    private void modifyNightVis(float partialTicks, CallbackInfo ci, @Local(ordinal = 7) LocalFloatRef potency) {
        var player = Minecraft.getInstance().player;
        if (player == null || !SpectacleData.isActive(player))
            return;

        var data = player.getData(SpectacleData.ATTACHMENT);

        if (potency.get() < data.getPotency())
            potency.set(data.getPotency());
    }

    @ModifyExpressionValue(method = "updateLightTexture",
                           at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
    private float decreaseGamma(float gamma) {
        var state = Environmental.isActive();
        var mod = state.force() ? PastelCommon.CONFIG.DimensionBrightnessMod : 0.25F;

        if (state.force() && minecraft.getCameraEntity() instanceof LivingEntity living) {
            gamma -= living.hasEffect(MobEffects.NIGHT_VISION) || SpectacleData.isActive(minecraft.player) ? 0.275F : 0F;
        }

        if (state.overrides) {
            gamma = Mth.lerp(
                Environmental.getEnvData()
                             .darkening(), gamma,
                gamma - 25F + mod
            );
        }

        return gamma;
    }
}
