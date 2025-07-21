package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.particle.render.EarlyRenderingParticleContainer;
import earth.terrarium.pastel.particle.render.ExtendedParticleManager;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public class MixinParticleManager implements ExtendedParticleManager {

    @Unique
    private final EarlyRenderingParticleContainer earlyRenderingParticleContainer
        = new EarlyRenderingParticleContainer();

    @Inject(method = "tick", at = @At(value = "INVOKE",
                                      target = "Ljava/util/Map;computeIfAbsent(Ljava/lang/Object;" +
                                               "Ljava/util/function/Function;)Ljava/lang/Object;"))
    private void earlyRenderingHook(final CallbackInfo ci, @Local final Particle particle) {
        earlyRenderingParticleContainer.add(particle);
    }

    @Inject(method = "tick", at = @At("RETURN"))
    private void removeDeadHook(final CallbackInfo ci) {
        earlyRenderingParticleContainer.removeDead();
    }

    @Override
    public void render(
        final PoseStack matrices, final MultiBufferSource vertexConsumers, final Camera camera, final float tickDelta) {
        earlyRenderingParticleContainer.render(matrices, vertexConsumers, camera, tickDelta);
    }

}
