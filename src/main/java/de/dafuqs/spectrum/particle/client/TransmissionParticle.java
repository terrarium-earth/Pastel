package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.*;
import net.minecraft.util.math.*;
import net.minecraft.world.event.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class TransmissionParticle extends SpriteBillboardParticle {
	
	private final PositionSource positionSource;
	
	public TransmissionParticle(ClientWorld world, double x, double y, double z, PositionSource positionSource, int arrivalInTicks) {
		super(world, x, y, z);
		this.scale = 0.3F;
		this.alpha = 0.7F;
		
		this.maxAge = arrivalInTicks;
		this.positionSource = positionSource;
	}
	
	@Override
	public void tick() {
		this.prevPosX = this.x;
		this.prevPosY = this.y;
		this.prevPosZ = this.z;
		if (this.age++ >= this.maxAge) {
			this.markDead();
		} else {
			Optional<Vec3d> optional = this.positionSource.getPos(this.world);
			if (optional.isEmpty()) {
				this.markDead();
			} else {
				int i = this.maxAge - this.age;
				double d = 1.0 / (double) i;
				Vec3d vec3d = optional.get();
				this.x = MathHelper.lerp(d, this.x, vec3d.getX());
				this.y = MathHelper.lerp(d, this.y, vec3d.getY());
				this.z = MathHelper.lerp(d, this.z, vec3d.getZ());
			}
		}
	}
	
	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<TransmissionParticleEffect> {
		private final SpriteProvider spriteProvider;
		
		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		public Particle createParticle(TransmissionParticleEffect particleEffect, ClientWorld world, double x, double y, double z, double g, double h, double i) {
			TransmissionParticle particle = new TransmissionParticle(world, x, y, z, particleEffect.getDestination(), particleEffect.getArrivalInTicks());
			particle.setSprite(this.spriteProvider);
			return particle;
		}
	}
	
}
