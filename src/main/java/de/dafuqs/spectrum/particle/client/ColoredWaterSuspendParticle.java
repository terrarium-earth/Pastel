package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.*;
import net.minecraft.particle.*;
import net.minecraft.util.math.*;
import org.joml.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class ColoredWaterSuspendParticle extends WaterSuspendParticle {
	
	public ColoredWaterSuspendParticle(ClientWorld world, SpriteProvider spriteProvider, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		super(world, spriteProvider, x, y, z, velocityX, velocityY, velocityZ);
	}
	
	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<ColoredSporeBlossomAirParticleEffect> {
		
		private final SpriteProvider spriteProvider;
		
		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public SpriteBillboardParticle createParticle(ColoredSporeBlossomAirParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			Vector3f color = parameters.getColor();
			
			WaterSuspendParticle particle = new ColoredWaterSuspendParticle(world, this.spriteProvider, x, y, z, 0.0, -0.800000011920929, 0.0) {
				public Optional<ParticleGroup> getGroup() {
					return Optional.of(ParticleGroup.SPORE_BLOSSOM_AIR);
				}
			};
			particle.setMaxAge(MathHelper.nextBetween(world.random, 500, 1000));
			particle.gravityStrength = 0.01F;
			particle.setColor(color.x, color.y, color.z);
			return particle;
		}
	}
	
}
