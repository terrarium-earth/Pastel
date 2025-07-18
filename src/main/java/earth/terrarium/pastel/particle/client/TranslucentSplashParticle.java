package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.helpers.data.ColorHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.WaterDropParticle;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;

@OnlyIn(Dist.CLIENT)

public class TranslucentSplashParticle extends WaterDropParticle {

    protected TranslucentSplashParticle(ClientLevel clientWorld, double d, double e, double f) {
        super(clientWorld, d, e, f);
        var waterColor = ColorHelper.colorIntToVec(
            BiomeColors.getAverageWaterColor(level, BlockPos.containing(x, y, z)));
        rCol = waterColor.x;
        gCol = waterColor.y;
        bCol = waterColor.z;
        quadSize *= 0.667F;
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
            WaterDropParticle rainSplashParticle = new TranslucentSplashParticle(clientWorld, d, e, f);
            rainSplashParticle.pickSprite(this.spriteProvider);
            return rainSplashParticle;
        }
    }
}
