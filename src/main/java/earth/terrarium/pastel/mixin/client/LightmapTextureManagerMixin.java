package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.deeper_down.Environmental;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LightTexture.class, priority = 9999)

public class LightmapTextureManagerMixin {

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

    @ModifyExpressionValue(method = "updateLightTexture",
                           at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
    private float decreaseGamma(float gamma) {
        var state = Environmental.isActive();
        var mod = state.force() ? PastelCommon.CONFIG.DimensionBrightnessMod : 0.25F;

        if (state.force() && minecraft.getCameraEntity() instanceof LivingEntity living) {
            gamma -= living.hasEffect(MobEffects.NIGHT_VISION) ? 0.275F : 0F;
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
