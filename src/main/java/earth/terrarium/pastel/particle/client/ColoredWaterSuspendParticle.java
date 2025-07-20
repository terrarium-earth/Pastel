package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.particle.effect.ColoredSporeBlossomAirParticleEffect;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.SuspendedParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ColoredWaterSuspendParticle extends SuspendedParticle {

    public ColoredWaterSuspendParticle(
        ClientLevel world, SpriteSet spriteProvider, double x, double y, double z, double velocityX, double velocityY,
        double velocityZ
    ) {
        super(world, spriteProvider, x, y, z, velocityX, velocityY, velocityZ);
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<ColoredSporeBlossomAirParticleEffect> {

        private final SpriteSet spriteProvider;

        public Factory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public TextureSheetParticle createParticle(
            ColoredSporeBlossomAirParticleEffect parameters, ClientLevel world, double x, double y, double z,
            double velocityX, double velocityY, double velocityZ
        ) {
            Vector3f color = parameters.getColor();

            SuspendedParticle particle = new ColoredWaterSuspendParticle(
                world, this.spriteProvider, x, y, z, 0.0, -0.800000011920929, 0.0) {
                public Optional<ParticleGroup> getParticleGroup() {
                    return Optional.of(ParticleGroup.SPORE_BLOSSOM);
                }
            };
            particle.setLifetime(Mth.randomBetweenInclusive(world.random, 500, 1000));
            particle.gravity = 0.01F;
            particle.setColor(color.x, color.y, color.z);
            return particle;
        }
    }

}
