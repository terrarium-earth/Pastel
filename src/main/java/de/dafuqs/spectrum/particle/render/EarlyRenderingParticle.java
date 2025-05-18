package de.dafuqs.spectrum.particle.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;

@Environment(EnvType.CLIENT)
public interface EarlyRenderingParticle {
    void renderAsEntity(final PoseStack matrices, final MultiBufferSource vertexConsumers, final Camera camera, final float tickDelta);
}