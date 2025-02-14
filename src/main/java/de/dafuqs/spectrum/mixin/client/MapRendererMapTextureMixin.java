package de.dafuqs.spectrum.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.*;
import net.minecraft.item.map.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(MapRenderer.MapTexture.class)
public class MapRendererMapTextureMixin {
	
	@WrapOperation(method = "draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ZI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/math/MatrixStack;scale(FFF)V"))
	public void scaleDecorations(MatrixStack instance, float x, float y, float z, Operation<Void> original, @Local MapDecoration decoration) {
		float scale = (float) ((int) decoration.spectrum$getScale() + 128) / 128;
		original.call(instance, 4f * scale, 4f * scale, 3f * scale);
	}
	
}
