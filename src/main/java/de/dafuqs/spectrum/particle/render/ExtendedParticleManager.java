package de.dafuqs.spectrum.particle.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;

@OnlyIn(Dist.CLIENT)
public interface ExtendedParticleManager {
    void render(PoseStack matrices, MultiBufferSource vertexConsumers, Camera camera, float tickDelta);
}