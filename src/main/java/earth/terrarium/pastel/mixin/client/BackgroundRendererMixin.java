package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.blaze3d.shaders.FogShape;
import earth.terrarium.pastel.deeper_down.Environmental;
import earth.terrarium.pastel.registries.PastelDimensions;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class BackgroundRendererMixin {

	@Shadow private static int previousBiomeFog;

	@Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V", remap = false, shift = At.Shift.BEFORE))
	private static void modifyFog(Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, @Local FogRenderer.FogData fogData) {
		var world = Minecraft.getInstance().level;
		
		if (world == null)
			return;
		
		var dim = world.dimension();
		var inDim = dim == PastelDimensions.DIMENSION_KEY;
		
		if (inDim) {
			var data = Environmental.getEnvData();

			fogData.shape = FogShape.SPHERE;
			fogData.end = Math.min(Math.min(viewDistance, 192F), Environmental.getFar(fogData.end));
			fogData.start = Environmental.getNear(fogData.start);
		}
	}

	@Inject(method = "setupFog", at = @At(value = "HEAD"))
	private static void makeFogThick(Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, @Local(argsOnly = true, ordinal = 0) LocalBooleanRef tfog) {
		if (!thickFog && Minecraft.getInstance().cameraEntity instanceof LivingEntity livingEntity && SleepStatusEffect.getStrongestSleepEffect(livingEntity) != null)
			tfog.set(true);
	}

	@WrapOperation(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", remap = false, ordinal = 1))
	private static void darkenBackground(float red, float green, float blue, float alpha, Operation<Void> original) {
		var envData = Environmental.getEnvData();
		var darkening = envData.brightMult();

		if (darkening > 0) {
			red *= darkening;
			green *= darkening;
			blue *= darkening;
		}
		original.call(red, green, blue, alpha);
	}
}
