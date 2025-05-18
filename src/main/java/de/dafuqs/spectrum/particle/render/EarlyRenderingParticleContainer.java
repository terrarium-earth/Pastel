package de.dafuqs.spectrum.particle.render;

import com.google.common.collect.EvictingQueue;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.objects.Object2ReferenceOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.renderer.MultiBufferSource;

import java.util.Map;
import java.util.Queue;

@Environment(EnvType.CLIENT)
public class EarlyRenderingParticleContainer {
    
    private static final int MAX_PARTICLES = 16384;
    private final static Map<ParticleRenderType, Queue<EarlyRenderingParticle>> particles = new Object2ReferenceOpenHashMap<>();

    public void add(final Particle particle) {
        if (particle instanceof EarlyRenderingParticle earlyRenderingParticle) {
            particles.computeIfAbsent(particle.getRenderType(), sheet -> EvictingQueue.create(MAX_PARTICLES)).add(earlyRenderingParticle);
        }
    }
    
    public void removeDead() {
        for (final Queue<EarlyRenderingParticle> particles : particles.values()) {
            particles.removeIf(particle -> !((Particle) particle).isAlive());
        }
    }
    
    public static void clear() {
        particles.clear();
    }
    
    public void render(final PoseStack matrices, final MultiBufferSource vertexConsumers, final Camera camera, final float tickDelta) {
        for (final Queue<EarlyRenderingParticle> particles : particles.values()) {
            for (final EarlyRenderingParticle particle : particles) {
                particle.renderAsEntity(matrices, vertexConsumers, camera, tickDelta);
            }
        }
    }
}