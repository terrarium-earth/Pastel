package de.dafuqs.spectrum.particle.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;

@Environment(EnvType.CLIENT)
public interface ExtendedParticleManager {
    void render(PoseStack matrices, MultiBufferSource vertexConsumers, Camera camera, float tickDelta);
}