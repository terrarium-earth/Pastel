package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.model.QuadWithOverlay;
import net.minecraft.client.renderer.block.model.BakedQuad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(VertexConsumer.class)
public interface VertexConsumerMixin {

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/util/FastColor$ARGB32;color(IIII)I"), method = "putBulkData(Lcom/mojang/blaze3d/vertex/PoseStack$Pose;Lnet/minecraft/client/renderer/block/model/BakedQuad;[FFFFF[IIZ)V")
    private static int applyQuadOverlay(int alpha, int red, int green, int blue, Operation<Integer> original, @Local(argsOnly = true) BakedQuad quad) {
        if (quad instanceof QuadWithOverlay overlay) {
            // :<
        }

        return original.call(alpha, red, green, blue);
    }
}
