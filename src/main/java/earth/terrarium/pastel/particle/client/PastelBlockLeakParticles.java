package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelFluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@OnlyIn(Dist.CLIENT)
public class PastelBlockLeakParticles {

    public static class LandingHumusFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public LandingHumusFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripLandParticle(
                clientWorld, d, e, f, PastelFluids.HUMUS.get());
            blockLeakParticle.setColor(
                PastelFluids.HUMUS_COLOR_VEC.x(), PastelFluids.HUMUS_COLOR_VEC.y(), PastelFluids.HUMUS_COLOR_VEC.z());
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class FallingHumusFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public FallingHumusFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(
                clientWorld, d, e, f, PastelFluids.HUMUS.get(), PastelParticleTypes.LANDING_HUMUS);
            blockLeakParticle.setColor(
                PastelFluids.HUMUS_COLOR_VEC.x(), PastelFluids.HUMUS_COLOR_VEC.y(), PastelFluids.HUMUS_COLOR_VEC.z());
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class DrippingHumusFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public DrippingHumusFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripHangParticle(
                clientWorld, d, e, f, PastelFluids.HUMUS.get(), PastelParticleTypes.FALLING_HUMUS);
            blockLeakParticle.setColor(
                PastelFluids.HUMUS_COLOR_VEC.x(), PastelFluids.HUMUS_COLOR_VEC.y(), PastelFluids.HUMUS_COLOR_VEC.z());
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class LandingLiquidCrystalFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public LandingLiquidCrystalFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripLandParticle(
                clientWorld, d, e, f, PastelFluids.LIQUID_CRYSTAL.get());
            blockLeakParticle.setColor(
                PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.x(), PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.y(),
                PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class FallingLiquidCrystalFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public FallingLiquidCrystalFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(
                clientWorld, d, e, f, PastelFluids.LIQUID_CRYSTAL.get(), PastelParticleTypes.LANDING_LIQUID_CRYSTAL);
            blockLeakParticle.setColor(
                PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.x(), PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.y(),
                PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class DrippingLiquidCrystalFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public DrippingLiquidCrystalFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripHangParticle(
                clientWorld, d, e, f, PastelFluids.LIQUID_CRYSTAL.get(), PastelParticleTypes.FALLING_LIQUID_CRYSTAL);
            blockLeakParticle.setColor(
                PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.x(), PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.y(),
                PastelFluids.LIQUID_CRYSTAL_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class LandingMidnightSolutionFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public LandingMidnightSolutionFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripLandParticle(
                clientWorld, d, e, f, PastelFluids.MIDNIGHT_SOLUTION.get());
            blockLeakParticle.setColor(
                PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.x(), PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.y(),
                PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class FallingMidnightSolutionFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public FallingMidnightSolutionFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(
                clientWorld, d, e, f, PastelFluids.MIDNIGHT_SOLUTION.get(),
                PastelParticleTypes.LANDING_MIDNIGHT_SOLUTION
            );
            blockLeakParticle.setColor(
                PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.x(), PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.y(),
                PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class DrippingMidnightSolutionFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public DrippingMidnightSolutionFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripHangParticle(
                clientWorld, d, e, f, PastelFluids.MIDNIGHT_SOLUTION.get(),
                PastelParticleTypes.FALLING_MIDNIGHT_SOLUTION
            );
            blockLeakParticle.setColor(
                PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.x(), PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.y(),
                PastelFluids.MIDNIGHT_SOLUTION_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class LandingDragonrotFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public LandingDragonrotFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripLandParticle(
                clientWorld, d, e, f, PastelFluids.DRAGONROT.get());
            blockLeakParticle.setColor(
                PastelFluids.DRAGONROT_COLOR_VEC.x(), PastelFluids.DRAGONROT_COLOR_VEC.y(),
                PastelFluids.DRAGONROT_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class FallingDragonrotFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public FallingDragonrotFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(
                clientWorld, d, e, f, PastelFluids.DRAGONROT.get(), PastelParticleTypes.LANDING_DRAGONROT);
            blockLeakParticle.setColor(
                PastelFluids.DRAGONROT_COLOR_VEC.x(), PastelFluids.DRAGONROT_COLOR_VEC.y(),
                PastelFluids.DRAGONROT_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    public static class DrippingDragonrotFactory implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet spriteProvider;

        public DrippingDragonrotFactory(SpriteSet spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(
            SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g,
            double h, double i
        ) {
            DripParticle blockLeakParticle = new DripParticle.DripHangParticle(
                clientWorld, d, e, f, PastelFluids.DRAGONROT.get(), PastelParticleTypes.FALLING_DRAGONROT);
            blockLeakParticle.setColor(
                PastelFluids.DRAGONROT_COLOR_VEC.x(), PastelFluids.DRAGONROT_COLOR_VEC.y(),
                PastelFluids.DRAGONROT_COLOR_VEC.z()
            );
            blockLeakParticle.pickSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

}
