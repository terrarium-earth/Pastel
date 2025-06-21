package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.deeper_down.DimensionRenderEffects;
import earth.terrarium.pastel.registries.PastelDimensions;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = LightTexture.class, priority = 9999)

public class LightmapTextureManagerMixin {

	@Shadow @Final private Minecraft minecraft;

	@ModifyReturnValue(method = "calculateDarknessScale", at = @At("RETURN"))
	private float getDarkness(float original) {
		var lightMod = PastelCommon.CONFIG.DimensionBrightnessMod * 0.25F;

		if (isInDim()) {
			var darkening = Mth.lerp(DimensionRenderEffects.getDarknessInterpolation(), 0.11F - lightMod, 0.2125F - lightMod);
			return Math.max(darkening, original);

		}
		return original;
	}
	
	@ModifyExpressionValue(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
	private float decreaseGamma(float gamma) {
		if (isInDim()) {
			if (minecraft.getCameraEntity() instanceof LivingEntity living) {
				gamma -= living.hasEffect(MobEffects.NIGHT_VISION) ? 0.275F : 0F;
			}

			if (DimensionRenderEffects.darkenTicks > 0) {
				gamma = Mth.lerp(DimensionRenderEffects.getDarknessInterpolation(), gamma, gamma - 25F + PastelCommon.CONFIG.DimensionBrightnessMod);
			}
		}

		return gamma;
	}

	@Unique
	private static boolean isInDim() {
		Minecraft client = Minecraft.getInstance();
		return PastelDimensions.DIMENSION_KEY.equals(client.player.level().dimension());
	}
}
