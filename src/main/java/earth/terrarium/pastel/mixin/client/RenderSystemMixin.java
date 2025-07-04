package earth.terrarium.pastel.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import earth.terrarium.pastel.deeper_down.Environmental;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = RenderSystem.class, priority = 1001, remap = false)
public class RenderSystemMixin {

    @Shadow @Final private static float[] shaderFogColor;

    @Inject(method = "getShaderFogColor", at = @At("RETURN"), cancellable = true)
    private static void alterFogColor(CallbackInfoReturnable<float[]> cir) {
        var mult = Environmental.getEnvData().brightMult();

        cir.setReturnValue(new float[]{
                shaderFogColor[0] * mult,
                shaderFogColor[1] * mult,
                shaderFogColor[2] * mult,
                shaderFogColor[3]});
        cir.cancel();
    }
}
