package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.particle.effect.ColoredExplosionParticleEffect;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.HugeExplosionParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@OnlyIn(
    Dist.CLIENT
)
public class ColoredExplosionParticle extends HugeExplosionParticle {

    protected ColoredExplosionParticle(
        ClientLevel world,
        double x,
        double y,
        double z,
        double d,
        SpriteSet spriteProvider,
        float red,
        float green,
        float blue
    ) {
        super(world, x, y, z, d, spriteProvider);

        this.rCol = red;
        this.gCol = green;
        this.bCol = blue;
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    @OnlyIn(
        Dist.CLIENT
    )
    public static class Factory implements ParticleProvider<ColoredExplosionParticleEffect> {

        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public @Nullable Particle createParticle(
            ColoredExplosionParticleEffect parameters,
            ClientLevel world,
            double x,
            double y,
            double z,
            double velocityX,
            double velocityY,
            double velocityZ
        ) {
            Vector3f color = parameters.getColor();
            return new ColoredExplosionParticle(
                world,
                x,
                y,
                z,
                velocityX,
                this.spriteProvider,
                color.x,
                color.y,
                color.z
            );
        }
    }

}
