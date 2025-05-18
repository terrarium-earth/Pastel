package de.dafuqs.spectrum.particle.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.MultiBufferSource;

@OnlyIn(Dist.CLIENT)
public interface EarlyRenderingParticle {
    void renderAsEntity(final PoseStack matrices, final MultiBufferSource vertexConsumers, final Camera camera, final float tickDelta);
}