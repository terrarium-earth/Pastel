package earth.terrarium.pastel.particle.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.core.particles.SimpleParticleType;

public class AzureMoteParticle extends BloodflyParticle {

    protected AzureMoteParticle(
        ClientLevel clientWorld, double d, double e, double f, double velocityX, double velocityY, double velocityZ,
        float scaleMultiplier, SpriteSet spriteProvider
    ) {
        super(clientWorld, d, e, f, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider);
        this.bCol = 1F;
        this.rCol = 0.15F * random.nextFloat();
        this.gCol = 0.3F + random.nextFloat() * 0.55F;
        this.lifetime = 40 + random.nextInt(61);
    }

    @Override
    public int getLightColor(float tint) {
        return LightTexture.FULL_BRIGHT;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            return new AzureMoteParticle(clientWorld, d, e, f, 0.0, 0.0, 0.0, 1.0F, this.spriteProvider);
        }
    }
}
