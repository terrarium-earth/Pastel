package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.particle.SpectrumParticleTypes;
import earth.terrarium.pastel.registries.SpectrumFluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@OnlyIn(Dist.CLIENT)
public class SpectrumBlockLeakParticles {

	public static class LandingGooFactory implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteProvider;
		
		public LandingGooFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripLandParticle(clientWorld, d, e, f, SpectrumFluids.GOO.get());
			blockLeakParticle.setColor(SpectrumFluids.GOO_COLOR_VEC.x(), SpectrumFluids.GOO_COLOR_VEC.y(), SpectrumFluids.GOO_COLOR_VEC.z());
			blockLeakParticle.pickSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}
	
	public static class FallingGooFactory implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteProvider;
		
		public FallingGooFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(clientWorld, d, e, f, SpectrumFluids.GOO.get(), SpectrumParticleTypes.LANDING_GOO);
			blockLeakParticle.setColor(SpectrumFluids.GOO_COLOR_VEC.x(), SpectrumFluids.GOO_COLOR_VEC.y(), SpectrumFluids.GOO_COLOR_VEC.z());
			blockLeakParticle.pickSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}
	
	public static class DrippingGooFactory implements ParticleProvider<SimpleParticleType> {
		protected final SpriteSet spriteProvider;
		
		public DrippingGooFactory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripHangParticle(clientWorld, d, e, f, SpectrumFluids.GOO.get(), SpectrumParticleTypes.FALLING_GOO);
			blockLeakParticle.setColor(SpectrumFluids.GOO_COLOR_VEC.x(), SpectrumFluids.GOO_COLOR_VEC.y(), SpectrumFluids.GOO_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripLandParticle(clientWorld, d, e, f, SpectrumFluids.LIQUID_CRYSTAL.get());
			blockLeakParticle.setColor(SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.x(), SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.y(), SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(clientWorld, d, e, f, SpectrumFluids.LIQUID_CRYSTAL.get(), SpectrumParticleTypes.LANDING_LIQUID_CRYSTAL);
			blockLeakParticle.setColor(SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.x(), SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.y(), SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripHangParticle(clientWorld, d, e, f, SpectrumFluids.LIQUID_CRYSTAL.get(), SpectrumParticleTypes.FALLING_LIQUID_CRYSTAL);
			blockLeakParticle.setColor(SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.x(), SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.y(), SpectrumFluids.LIQUID_CRYSTAL_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripLandParticle(clientWorld, d, e, f, SpectrumFluids.MIDNIGHT_SOLUTION.get());
			blockLeakParticle.setColor(SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.x(), SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.y(), SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(clientWorld, d, e, f, SpectrumFluids.MIDNIGHT_SOLUTION.get(), SpectrumParticleTypes.LANDING_MIDNIGHT_SOLUTION);
			blockLeakParticle.setColor(SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.x(), SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.y(), SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripHangParticle(clientWorld, d, e, f, SpectrumFluids.MIDNIGHT_SOLUTION.get(), SpectrumParticleTypes.FALLING_MIDNIGHT_SOLUTION);
			blockLeakParticle.setColor(SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.x(), SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.y(), SpectrumFluids.MIDNIGHT_SOLUTION_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripLandParticle(clientWorld, d, e, f, SpectrumFluids.DRAGONROT.get());
			blockLeakParticle.setColor(SpectrumFluids.DRAGONROT_COLOR_VEC.x(), SpectrumFluids.DRAGONROT_COLOR_VEC.y(), SpectrumFluids.DRAGONROT_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.FallAndLandParticle(clientWorld, d, e, f, SpectrumFluids.DRAGONROT.get(), SpectrumParticleTypes.LANDING_DRAGONROT);
			blockLeakParticle.setColor(SpectrumFluids.DRAGONROT_COLOR_VEC.x(), SpectrumFluids.DRAGONROT_COLOR_VEC.y(), SpectrumFluids.DRAGONROT_COLOR_VEC.z());
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
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			DripParticle blockLeakParticle = new DripParticle.DripHangParticle(clientWorld, d, e, f, SpectrumFluids.DRAGONROT.get(), SpectrumParticleTypes.FALLING_DRAGONROT);
			blockLeakParticle.setColor(SpectrumFluids.DRAGONROT_COLOR_VEC.x(), SpectrumFluids.DRAGONROT_COLOR_VEC.y(), SpectrumFluids.DRAGONROT_COLOR_VEC.z());
			blockLeakParticle.pickSprite(this.spriteProvider);
			return blockLeakParticle;
		}
	}

}
