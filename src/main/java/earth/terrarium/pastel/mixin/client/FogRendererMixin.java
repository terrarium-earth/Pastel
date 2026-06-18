package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import earth.terrarium.pastel.attachments.data.SpectacleData;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(
    FogRenderer.class
)
public class FogRendererMixin {

    @Inject(
        method = "setupColor", at = @At(
            value = "INVOKE", target = "Ljava/lang/Math;min(FF)F", ordinal = 0
        )
    )
    private static void modifyNightVisionEffect(
        Camera activeRenderInfo,
        float partialTicks,
        ClientLevel level,
        int renderDistanceChunks,
        float bossColorModifier,
        CallbackInfo ci,
        @Local(
            name = "f7"
        )
        LocalFloatRef potency
    ) {

        var player = Minecraft.getInstance().player;
        if (player == null || !SpectacleData.isActive(player))
            return;

        var data = player.getData(SpectacleData.ATTACHMENT);

        if (potency.get() < data.getPotency())
            potency.set(data.getPotency());
    }
}
