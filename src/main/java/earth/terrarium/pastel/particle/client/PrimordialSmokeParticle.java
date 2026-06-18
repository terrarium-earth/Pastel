package earth.terrarium.pastel.particle.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BaseAshSmokeParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PrimordialSmokeParticle extends BaseAshSmokeParticle {
    protected PrimordialSmokeParticle(
        ClientLevel world,
        double x,
        double y,
        double z,
        double velocityX,
        double velocityY,
        double velocityZ,
        float scaleMultiplier,
        SpriteSet spriteProvider
    ) {
        super(
            world,
            x,
            y,
            z,
            0.1F,
            0.1F,
            0.1F,
            velocityX,
            velocityY,
            velocityZ,
            scaleMultiplier,
            spriteProvider,
            1F,
            10,
            -0.1F,
            true
        );
        rCol = 1;
        gCol = 1;
        bCol = 1;
    }

    @OnlyIn(
        Dist.CLIENT
    )
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(
            SimpleParticleType defaultParticleType,
            ClientLevel clientWorld,
            double d,
            double e,
            double f,
            double g,
            double h,
            double i
        ) {
            return new PrimordialSmokeParticle(clientWorld, d, e, f, g, h, i, 1.1F, this.spriteProvider);
        }
    }
}
