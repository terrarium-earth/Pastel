package de.dafuqs.spectrum.particle.client;

import de.dafuqs.spectrum.particle.effect.*;
import net.fabricmc.api.*;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.*;
import net.minecraft.fluid.*;
import net.minecraft.util.math.*;
import org.joml.*;

@Environment(EnvType.CLIENT)
public class ColoredBlockLeakParticle extends BlockLeakParticle {
	
	public ColoredBlockLeakParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
		super(world, x, y, z, fluid);
	}
	
	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<ColoredFallingSporeBlossomParticleEffect> {
		
		private final SpriteProvider spriteProvider;
		
		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public SpriteBillboardParticle createParticle(ColoredFallingSporeBlossomParticleEffect parameters, ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			Vector3f color = parameters.getColor();
			int i = (int) (64.0F / MathHelper.nextBetween(world.getRandom(), 0.1F, 0.9F));
			BlockLeakParticle particle = new BlockLeakParticle.Falling(world, x, y, z, Fluids.EMPTY, i);
			particle.gravityStrength = 0.005F;
			particle.setSprite(this.spriteProvider);
			particle.setColor(color.x, color.y, color.z);
			return particle;
		}
	}
	
}
