package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.helpers.ParticleHelper;
import earth.terrarium.pastel.particle.SpectrumParticleTypes;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredExplosionParticleEffect;
import earth.terrarium.pastel.particle.effect.ColoredSparkleRisingParticleEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.NoRenderParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;

@OnlyIn(Dist.CLIENT)
public class MoonstoneStrikeParticle extends NoRenderParticle {
	
	private final static int MAX_AGE = 40;
	
	MoonstoneStrikeParticle(ClientLevel clientWorld, double d, double e, double f) {
		super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
	}
	
	@Override
	public void tick() {
		if (this.age == 0) {
			level.addParticle(ColoredExplosionParticleEffect.WHITE, this.x, this.y, this.z, 0, 0, 0);
		}
		var alt = random.nextBoolean();
		var particle = alt ? SpectrumParticleTypes.SHOOTING_STAR : ColoredSparkleRisingParticleEffect.WHITE;
		var velocity = alt ? 0.5F : 0.375F;
		var rotation = Math.PI / 20F * age;
		var nextRotation = Math.PI / 20F * (age + 1);
		ParticleHelper.playParticleWithRotation(this.level, new Vec3(this.x, this.y, this.z), rotation, rotation, particle, VectorPattern.EIGHT, velocity);
		ParticleHelper.playParticleWithRotation(this.level, new Vec3(this.x, this.y, this.z), nextRotation, -nextRotation, particle, VectorPattern.EIGHT, velocity);

		this.age += 2;
		if (this.age == MAX_AGE) {
			this.remove();
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<SimpleParticleType> {
		public Factory() {
		}
		
		@Override
		public Particle createParticle(SimpleParticleType defaultParticleType, ClientLevel clientWorld, double d, double e, double f, double g, double h, double i) {
			return new MoonstoneStrikeParticle(clientWorld, d, e, f);
		}
	}

}
