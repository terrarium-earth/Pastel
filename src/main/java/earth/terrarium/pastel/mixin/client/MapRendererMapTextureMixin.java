package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.injectors.MapDecorationInjector;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.world.level.saveddata.maps.MapDecoration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MapRenderer.MapInstance.class)
public class MapRendererMapTextureMixin {
	
	@WrapOperation(method = "draw", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;scale(FFF)V"))
	public void scaleDecorations(PoseStack instance, float x, float y, float z, Operation<Void> original, @Local MapDecoration decoration) {
		float scale = (float) ((int) ((MapDecorationInjector) (Object) decoration).spectrum$getScale() + 128) / 128;
		original.call(instance, 4f * scale, 4f * scale, 3f * scale);
	}
	
}
