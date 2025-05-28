package earth.terrarium.pastel.particle;

import earth.terrarium.pastel.blocks.pastel_network.PastelRenderHelper;
import earth.terrarium.pastel.particle.client.AzureAuraParticle;
import earth.terrarium.pastel.particle.client.AzureMoteParticle;
import earth.terrarium.pastel.particle.client.BloodflyParticle;
import earth.terrarium.pastel.particle.client.ColoredBlockLeakParticle;
import earth.terrarium.pastel.particle.client.ColoredCraftingParticle;
import earth.terrarium.pastel.particle.client.ColoredExplosionParticle;
import earth.terrarium.pastel.particle.client.ColoredTransmissionParticle;
import earth.terrarium.pastel.particle.client.ColoredWaterSuspendParticle;
import earth.terrarium.pastel.particle.client.DynamicParticle;
import earth.terrarium.pastel.particle.client.FallingAshParticle;
import earth.terrarium.pastel.particle.client.FireflyParticle;
import earth.terrarium.pastel.particle.client.FixedVelocityParticle;
import earth.terrarium.pastel.particle.client.HardcoreParticle;
import earth.terrarium.pastel.particle.client.LargePrimordialSmokeParticle;
import earth.terrarium.pastel.particle.client.LightTrailParticle;
import earth.terrarium.pastel.particle.client.LitParticle;
import earth.terrarium.pastel.particle.client.MoonstoneStrikeParticle;
import earth.terrarium.pastel.particle.client.PastelTransmissionParticle;
import earth.terrarium.pastel.particle.client.PrimordialSmokeParticle;
import earth.terrarium.pastel.particle.client.QuartzFluffParticle;
import earth.terrarium.pastel.particle.client.RainRippleParticle;
import earth.terrarium.pastel.particle.client.RaindropParticle;
import earth.terrarium.pastel.particle.client.SpectrumBlockLeakParticles;
import earth.terrarium.pastel.particle.client.TranslucentSplashParticle;
import earth.terrarium.pastel.particle.client.TransmissionParticle;
import earth.terrarium.pastel.particle.client.VoidFogParticle;
import earth.terrarium.pastel.particle.client.WindParticle;
import earth.terrarium.pastel.particle.client.ZigZagParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BubblePopParticle;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.particle.WakeParticle;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.neoforge.client.event.*;

// See ParticleManager for vanilla
@OnlyIn(Dist.CLIENT)
public class SpectrumParticleFactories {
	
	public static void register(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(SpectrumParticleTypes.COLORED_CRAFTING, ColoredCraftingParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.COLORED_FLUID_RISING, FixedVelocityParticle.ColoredFluidRisingFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.COLORED_SPARKLE_RISING, FixedVelocityParticle.ColoredSparkleRisingFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.COLORED_EXPLOSION, ColoredExplosionParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.COLORED_FALLING_SPORE_BLOSSOM, ColoredBlockLeakParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.COLORED_SPORE_BLOSSOM_AIR, ColoredWaterSuspendParticle.Factory::new);
		
		event.registerSpriteSet(SpectrumParticleTypes.ITEM_TRANSMISSION, provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			TransmissionParticle particle = new TransmissionParticle(world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
			particle.pickSprite(provider);
			return particle;
		});
		
		event.registerSpriteSet(SpectrumParticleTypes.EXPERIENCE_TRANSMISSION, provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			TransmissionParticle particle = new TransmissionParticle(world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
			particle.pickSprite(provider);
			return particle;
		});
		
		event.registerSpriteSet(SpectrumParticleTypes.WIRELESS_REDSTONE_TRANSMISSION, provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			TransmissionParticle particle = new TransmissionParticle(world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
			particle.pickSprite(provider);
			return particle;
		});
		
		event.registerSpriteSet(SpectrumParticleTypes.COLORED_TRANSMISSION, provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			ColoredTransmissionParticle particle = new ColoredTransmissionParticle(world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks(), parameters.getColor());
			particle.pickSprite(provider);
			return particle;
		});
		
		event.registerSpriteSet(SpectrumParticleTypes.BLOCK_POS_EVENT_TRANSMISSION, provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			TransmissionParticle particle = new TransmissionParticle(world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
			particle.pickSprite(provider);
			return particle;
		});
		
		event.registerSpriteSet(SpectrumParticleTypes.PASTEL_TRANSMISSION, provider -> (pastelTransmissionParticleEffect, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			PastelTransmissionParticle particle = new PastelTransmissionParticle(Minecraft.getInstance().getItemRenderer(), world, x, y, z, pastelTransmissionParticleEffect.nodePositions(), pastelTransmissionParticleEffect.stack(), pastelTransmissionParticleEffect.travelTime(), pastelTransmissionParticleEffect.color());
			particle.pickSprite(provider);
			float[] color = PastelRenderHelper.unpackNormalizedColor(pastelTransmissionParticleEffect.color());
			particle.setColor(color[1], color[2], color[3]);
			return particle;
		});
		
		event.registerSpriteSet(SpectrumParticleTypes.HUMMINGSTONE_TRANSMISSION, TransmissionParticle.Factory::new);
		
		event.registerSpriteSet(SpectrumParticleTypes.MOONSTONE_STRIKE, provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
			MoonstoneStrikeParticle.Factory factory = new MoonstoneStrikeParticle.Factory();
			return factory.createParticle(SpectrumParticleTypes.MOONSTONE_STRIKE, world, x, y, z, velocityX, velocityY, velocityZ);
		});
		
		event.registerSpriteSet(SpectrumParticleTypes.PRIMORDIAL_COSY_SMOKE, LargePrimordialSmokeParticle.CosySmokeFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.PRIMORDIAL_SIGNAL_SMOKE, LargePrimordialSmokeParticle.SignalSmokeFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.PRIMORDIAL_SMOKE, PrimordialSmokeParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.PRIMORDIAL_FLAME, FlameParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.PRIMORDIAL_FLAME_SMALL, FlameParticle.SmallFlameProvider::new);
		
		event.registerSpriteSet(SpectrumParticleTypes.DIVINITY, HardcoreParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.SHOOTING_STAR, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.SHIMMERSTONE_SPARKLE, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.SHIMMERSTONE_SPARKLE_SMALL, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.SHIMMERSTONE_SPARKLE_TINY, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.LIQUID_CRYSTAL_SPARKLE, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRAGONROT, BubblePopParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.VOID_FOG, VoidFogParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.GOO_POP, BubblePopParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.BLUE_BUBBLE_POP, BubblePopParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.GREEN_BUBBLE_POP, BubblePopParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.SPIRIT_SALLOW, WindParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.JADE_VINES, ZigZagParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.JADE_VINES_BLOOM, ZigZagParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.MIRROR_IMAGE, LitParticle.Factory::new);
		
		// Runes / Dike
		event.registerSpriteSet(SpectrumParticleTypes.RUNES, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.AZURE_DIKE_RUNES, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.AZURE_DIKE_RUNES_MAJOR, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRAKEBLOOD_DIKE_RUNES, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRAKEBLOOD_DIKE_RUNES_MAJOR, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.MALACHITE_DIKE_RUNES, LitParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.MALACHITE_DIKE_RUNES_MAJOR, LitParticle.Factory::new);
		
		event.registerSpriteSet(SpectrumParticleTypes.AZURE_AURA, AzureAuraParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.AZURE_MOTE, AzureMoteParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.AZURE_MOTE_SMALL, AzureMoteParticle.Factory::new);
		
		// Fluid Splash
		event.registerSpriteSet(SpectrumParticleTypes.GOO_SPLASH, SplashParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.LIQUID_CRYSTAL_SPLASH, SplashParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.MIDNIGHT_SOLUTION_SPLASH, SplashParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRAGONROT_SPLASH, SplashParticle.Provider::new);
		
		// Fluid Dripping
		event.registerSpriteSet(SpectrumParticleTypes.DRIPPING_GOO, SpectrumBlockLeakParticles.DrippingGooFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRIPPING_LIQUID_CRYSTAL, SpectrumBlockLeakParticles.DrippingLiquidCrystalFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRIPPING_MIDNIGHT_SOLUTION, SpectrumBlockLeakParticles.DrippingMidnightSolutionFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRIPPING_DRAGONROT, SpectrumBlockLeakParticles.DrippingDragonrotFactory::new);
		
		// Fluid Falling
		event.registerSpriteSet(SpectrumParticleTypes.FALLING_GOO, SpectrumBlockLeakParticles.FallingGooFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.FALLING_LIQUID_CRYSTAL, SpectrumBlockLeakParticles.FallingLiquidCrystalFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.FALLING_MIDNIGHT_SOLUTION, SpectrumBlockLeakParticles.FallingMidnightSolutionFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.FALLING_DRAGONROT, SpectrumBlockLeakParticles.FallingDragonrotFactory::new);
		
		// Fluid Landing
		event.registerSpriteSet(SpectrumParticleTypes.LANDING_GOO, SpectrumBlockLeakParticles.LandingGooFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.LANDING_LIQUID_CRYSTAL, SpectrumBlockLeakParticles.LandingLiquidCrystalFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.LANDING_MIDNIGHT_SOLUTION, SpectrumBlockLeakParticles.LandingMidnightSolutionFactory::new);
		event.registerSpriteSet(SpectrumParticleTypes.LANDING_DRAGONROT, SpectrumBlockLeakParticles.LandingDragonrotFactory::new);
		
		// Fluid Fishing
		event.registerSpriteSet(SpectrumParticleTypes.LAVA_FISHING, WakeParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.GOO_FISHING, WakeParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.LIQUID_CRYSTAL_FISHING, WakeParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.MIDNIGHT_SOLUTION_FISHING, WakeParticle.Provider::new);
		event.registerSpriteSet(SpectrumParticleTypes.DRAGONROT_FISHING, WakeParticle.Provider::new);
		
		//Azzyy sucked cock here
		//Can confirm this sucks ~Daf
		event.registerSpriteSet(SpectrumParticleTypes.LIGHT_TRAIL, LightTrailParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.FALLING_ASH, FallingAshParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.FIREFLY, FireflyParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.BLOODFLY, BloodflyParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.QUARTZ_FLUFF, QuartzFluffParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.LIGHT_RAIN, RaindropParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.HEAVY_RAIN, RaindropParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.RAIN_SPLASH, TranslucentSplashParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.RAIN_RIPPLE, RainRippleParticle.Factory::new);
		
		// Since these can reference other particle types, they should always come last
		event.registerSpriteSet(SpectrumParticleTypes.DYNAMIC, DynamicParticle.Factory::new);
		event.registerSpriteSet(SpectrumParticleTypes.DYNAMIC_ALWAYS_SHOW, DynamicParticle.Factory::new);
	}
	
	public static TextureSheetParticle createFallingSporeBlossom(SimpleParticleType type, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
		int i = (int) (64.0F / Mth.randomBetween(world.getRandom(), 0.1F, 0.9F));
		DripParticle blockLeakParticle = new DripParticle.FallingParticle(world, x, y, z, Fluids.EMPTY, i);
		blockLeakParticle.gravity = 0.005F;
		(blockLeakParticle).setColor(0.32F, 0.5F, 0.22F);
		return blockLeakParticle;
	}
	
	
}
