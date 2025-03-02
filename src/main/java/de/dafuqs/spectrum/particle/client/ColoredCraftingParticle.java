package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;
import org.joml.*;

@Environment(EnvType.CLIENT)
public class ColoredCraftingParticle extends SpriteBillboardParticle {
	
	protected ColoredCraftingParticle(ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float red, float green, float blue) {
		super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
		this.gravityStrength = 0.0F;
		this.ascending = true;
		this.scale *= 0.75F;
		this.collidesWithWorld = false;
		
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	@Override
	public ParticleTextureSheet getType() {
		return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public int getBrightness(float tint) {
		float f = ((float) this.age + tint) / (float) this.maxAge;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		int i = super.getBrightness(tint);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int) (f * 15.0F * 16.0F);
		if (j > 240) {
			j = 240;
		}
		
		return j | k << 16;
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<ColoredCraftingParticleEffect> {

		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public @Nullable Particle createParticle(ColoredCraftingParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			Vector3f color = parameters.getColor();
			ColoredCraftingParticle coloredCraftingParticle = new ColoredCraftingParticle(world, x, y, z, velocityX, velocityY, velocityZ, color.x, color.y, color.z);
			coloredCraftingParticle.setMaxAge((int) (8.0D / (world.random.nextDouble() * 0.8D + 0.2D)));
			coloredCraftingParticle.setSprite(this.spriteProvider);
			return coloredCraftingParticle;
		}
	}
	
}
