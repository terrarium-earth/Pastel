package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.shaders.FogShape;
import earth.terrarium.pastel.deeper_down.Environmental;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class BackgroundRendererMixin {

	@Inject(method = "setupFog", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;setShaderFogStart(F)V", remap = false, shift = At.Shift.BEFORE))
	private static void modifyFog(Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci, @Local FogRenderer.FogData fogData) {
		var state = Environmental.isActive();

		if (state.overrides) {
			fogData.end = Environmental.getFar(fogData.end);
			fogData.start = Environmental.getNear(fogData.start, !state.force());
		}

		if (state.force()) {
			fogData.shape = FogShape.SPHERE;
			fogData.end = Math.min(fogData.end, Math.min(viewDistance, 192F));
		}
	}

	@WrapOperation(method = "setupColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", remap = false, ordinal = 1))
	private static void darkenBackground(float red, float green, float blue, float alpha, Operation<Void> original) {
		if (!Environmental.isActive().overrides) {
			original.call(red, green, blue, alpha);
			return;
		}

		var envData = Environmental.getEnvData();
		var darkening = envData.brightMult();

		if (darkening < 1) {
			red *= darkening;
			green *= darkening;
			blue *= darkening;
		}

		var colors = new float[] {red, green, blue};
		Environmental.applyColor(colors);
		original.call(colors[0], colors[1], colors[2], alpha);
	}
}
