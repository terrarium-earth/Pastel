package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.*;
import org.jetbrains.annotations.*;
import org.joml.*;

@Environment(EnvType.CLIENT)
public class ColoredExplosionParticle extends ExplosionLargeParticle {
	
	protected ColoredExplosionParticle(ClientWorld world, double x, double y, double z, double d, SpriteProvider spriteProvider, float red, float green, float blue) {
		super(world, x, y, z, d, spriteProvider);
		
		this.red = red;
		this.green = green;
		this.blue = blue;
	}
	
	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<ColoredExplosionParticleEffect> {
		
		private final SpriteProvider spriteProvider;
		
		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public @Nullable Particle createParticle(ColoredExplosionParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			Vector3f color = parameters.getColor();
			return new ColoredExplosionParticle(world, x, y, z, velocityX, this.spriteProvider, color.x, color.y, color.z);
		}
	}
	
}
