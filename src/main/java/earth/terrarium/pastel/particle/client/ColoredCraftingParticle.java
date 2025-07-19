package earth.terrarium.pastel.particle.client;

import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class ColoredCraftingParticle extends TextureSheetParticle {
	
	protected ColoredCraftingParticle(ClientLevel clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float red, float green, float blue) {
		super(clientWorld, x, y, z, velocityX, velocityY, velocityZ);
		this.gravity = 0.0F;
		this.speedUpWhenYMotionIsBlocked = true;
		this.quadSize *= 0.75F;
		this.hasPhysics = false;
		this.xd = velocityX;
		this.yd = velocityY;
		this.zd = velocityZ;
		
		this.rCol = red;
		this.gCol = green;
		this.bCol = blue;
	}

	@Override
	public ParticleRenderType getRenderType() {
		return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}
	
	@Override
	public int getLightColor(float tint) {
		float f = ((float) this.age + tint) / (float) this.lifetime;
		f = Mth.clamp(f, 0.0F, 1.0F);
		int i = super.getLightColor(tint);
		int j = i & 255;
		int k = i >> 16 & 255;
		j += (int) (f * 15.0F * 16.0F);
		if (j > 240) {
			j = 240;
		}
		
		return j | k << 16;
	}

	@OnlyIn(Dist.CLIENT)
	public static class Factory implements ParticleProvider<ColoredCraftingParticleEffect> {

		private final SpriteSet spriteProvider;

		public Factory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}
		
		@Override
		public @Nullable Particle createParticle(ColoredCraftingParticleEffect parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
			Vector3f color = parameters.getColor();
			ColoredCraftingParticle coloredCraftingParticle = new ColoredCraftingParticle(world, x, y, z, velocityX, velocityY, velocityZ, color.x, color.y, color.z);
			coloredCraftingParticle.setLifetime((int) (8.0D / (world.random.nextDouble() * 0.8D + 0.2D)));
			coloredCraftingParticle.pickSprite(this.spriteProvider);
			return coloredCraftingParticle;
		}
	}
	
}
