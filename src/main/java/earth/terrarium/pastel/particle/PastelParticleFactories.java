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
import earth.terrarium.pastel.particle.client.PastelBlockLeakParticles;
import earth.terrarium.pastel.particle.client.PastelTransmissionParticle;
import earth.terrarium.pastel.particle.client.PrimordialSmokeParticle;
import earth.terrarium.pastel.particle.client.QuartzFluffParticle;
import earth.terrarium.pastel.particle.client.RainRippleParticle;
import earth.terrarium.pastel.particle.client.RaindropParticle;
import earth.terrarium.pastel.particle.client.TranslucentSplashParticle;
import earth.terrarium.pastel.particle.client.TransmissionParticle;
import earth.terrarium.pastel.particle.client.VoidFogParticle;
import earth.terrarium.pastel.particle.client.ZigZagParticle;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

// See ParticleManager for vanilla
@OnlyIn(Dist.CLIENT)
public class PastelParticleFactories {

    public static void register(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(PastelParticleTypes.COLORED_CRAFTING, ColoredCraftingParticle.Factory::new);
        event.registerSpriteSet(
            PastelParticleTypes.COLORED_FLUID_RISING, FixedVelocityParticle.ColoredFluidRisingFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.COLORED_SPARKLE_RISING, FixedVelocityParticle.ColoredSparkleRisingFactory::new);
        event.registerSpriteSet(PastelParticleTypes.COLORED_EXPLOSION, ColoredExplosionParticle.Factory::new);
        event.registerSpriteSet(
            PastelParticleTypes.COLORED_FALLING_SPORE_BLOSSOM, ColoredBlockLeakParticle.Factory::new);
        event.registerSpriteSet(
            PastelParticleTypes.COLORED_SPORE_BLOSSOM_AIR, ColoredWaterSuspendParticle.Factory::new);

        event.registerSpriteSet(
            PastelParticleTypes.ITEM_TRANSMISSION,
            provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                TransmissionParticle particle = new TransmissionParticle(
                    world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
                particle.pickSprite(provider);
                return particle;
            }
        );

        event.registerSpriteSet(
            PastelParticleTypes.EXPERIENCE_TRANSMISSION,
            provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                TransmissionParticle particle = new TransmissionParticle(
                    world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
                particle.pickSprite(provider);
                return particle;
            }
        );

        event.registerSpriteSet(
            PastelParticleTypes.WIRELESS_REDSTONE_TRANSMISSION,
            provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                TransmissionParticle particle = new TransmissionParticle(
                    world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
                particle.pickSprite(provider);
                return particle;
            }
        );

        event.registerSpriteSet(
            PastelParticleTypes.COLORED_TRANSMISSION,
            provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                ColoredTransmissionParticle particle = new ColoredTransmissionParticle(
                    world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks(), parameters.getColor());
                particle.pickSprite(provider);
                return particle;
            }
        );

        event.registerSpriteSet(
            PastelParticleTypes.BLOCK_POS_EVENT_TRANSMISSION,
            provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                TransmissionParticle particle = new TransmissionParticle(
                    world, x, y, z, parameters.getDestination(), parameters.getArrivalInTicks());
                particle.pickSprite(provider);
                return particle;
            }
        );

        event.registerSpriteSet(
            PastelParticleTypes.PASTEL_TRANSMISSION,
            provider -> (pastelTransmissionParticleEffect, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                PastelTransmissionParticle particle = new PastelTransmissionParticle(
                    Minecraft.getInstance()
                             .getItemRenderer(), world, x, y, z, pastelTransmissionParticleEffect.nodePositions(),
                    pastelTransmissionParticleEffect.stack(), pastelTransmissionParticleEffect.travelTime(),
                    pastelTransmissionParticleEffect.color()
                );
                particle.pickSprite(provider);
                float[] color = PastelRenderHelper.unpackNormalizedColor(pastelTransmissionParticleEffect.color());
                particle.setColor(color[1], color[2], color[3]);
                return particle;
            }
        );

        event.registerSpriteSet(PastelParticleTypes.HUMMINGSTONE_TRANSMISSION, TransmissionParticle.Factory::new);

        event.registerSpriteSet(
            PastelParticleTypes.MOONSTONE_STRIKE,
            provider -> (parameters, world, x, y, z, velocityX, velocityY, velocityZ) -> {
                MoonstoneStrikeParticle.Factory factory = new MoonstoneStrikeParticle.Factory();
                return factory.createParticle(
                    PastelParticleTypes.MOONSTONE_STRIKE, world, x, y, z, velocityX, velocityY, velocityZ);
            }
        );

        event.registerSpriteSet(
            PastelParticleTypes.PRIMORDIAL_COSY_SMOKE, LargePrimordialSmokeParticle.CosySmokeFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.PRIMORDIAL_SIGNAL_SMOKE, LargePrimordialSmokeParticle.SignalSmokeFactory::new);
        event.registerSpriteSet(PastelParticleTypes.PRIMORDIAL_SMOKE, PrimordialSmokeParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.PRIMORDIAL_FLAME, FlameParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.PRIMORDIAL_FLAME_SMALL, FlameParticle.SmallFlameProvider::new);

        event.registerSpriteSet(PastelParticleTypes.DIVINITY, HardcoreParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.SHOOTING_STAR, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.SHIMMERSTONE_SPARKLE, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.SHIMMERSTONE_SPARKLE_SMALL, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.SHIMMERSTONE_SPARKLE_TINY, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.LIQUID_CRYSTAL_SPARKLE, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.DRAGONROT, BubblePopParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.VOID_FOG, VoidFogParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.HUMUS_POP, BubblePopParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.BLUE_BUBBLE_POP, BubblePopParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.GREEN_BUBBLE_POP, BubblePopParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.JADE_VINES, ZigZagParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.JADE_VINES_BLOOM, ZigZagParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.MIRROR_IMAGE, LitParticle.Factory::new);

        // Runes / Dike
        event.registerSpriteSet(PastelParticleTypes.RUNES, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.AZURE_DIKE_RUNES, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.AZURE_DIKE_RUNES_MAJOR, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.DRAKEBLOOD_DIKE_RUNES, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.DRAKEBLOOD_DIKE_RUNES_MAJOR, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.MALACHITE_DIKE_RUNES, LitParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.MALACHITE_DIKE_RUNES_MAJOR, LitParticle.Factory::new);

        event.registerSpriteSet(PastelParticleTypes.AZURE_AURA, AzureAuraParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.AZURE_MOTE, AzureMoteParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.AZURE_MOTE_SMALL, AzureMoteParticle.Factory::new);

        // Fluid Splash
        event.registerSpriteSet(PastelParticleTypes.HUMUS_SPLASH, SplashParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.LIQUID_CRYSTAL_SPLASH, SplashParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.MIDNIGHT_SOLUTION_SPLASH, SplashParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.DRAGONROT_SPLASH, SplashParticle.Provider::new);

        // Fluid Dripping
        event.registerSpriteSet(PastelParticleTypes.DRIPPING_HUMUS, PastelBlockLeakParticles.DrippingHumusFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.DRIPPING_LIQUID_CRYSTAL, PastelBlockLeakParticles.DrippingLiquidCrystalFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.DRIPPING_MIDNIGHT_SOLUTION,
            PastelBlockLeakParticles.DrippingMidnightSolutionFactory::new
        );
        event.registerSpriteSet(
            PastelParticleTypes.DRIPPING_DRAGONROT, PastelBlockLeakParticles.DrippingDragonrotFactory::new);

        // Fluid Falling
        event.registerSpriteSet(PastelParticleTypes.FALLING_HUMUS, PastelBlockLeakParticles.FallingHumusFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.FALLING_LIQUID_CRYSTAL, PastelBlockLeakParticles.FallingLiquidCrystalFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.FALLING_MIDNIGHT_SOLUTION,
            PastelBlockLeakParticles.FallingMidnightSolutionFactory::new
        );
        event.registerSpriteSet(
            PastelParticleTypes.FALLING_DRAGONROT, PastelBlockLeakParticles.FallingDragonrotFactory::new);

        // Fluid Landing
        event.registerSpriteSet(PastelParticleTypes.LANDING_HUMUS, PastelBlockLeakParticles.LandingHumusFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.LANDING_LIQUID_CRYSTAL, PastelBlockLeakParticles.LandingLiquidCrystalFactory::new);
        event.registerSpriteSet(
            PastelParticleTypes.LANDING_MIDNIGHT_SOLUTION,
            PastelBlockLeakParticles.LandingMidnightSolutionFactory::new
        );
        event.registerSpriteSet(
            PastelParticleTypes.LANDING_DRAGONROT, PastelBlockLeakParticles.LandingDragonrotFactory::new);

        // Fluid Fishing
        event.registerSpriteSet(PastelParticleTypes.LAVA_FISHING, WakeParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.HUMUS_FISHING, WakeParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.LIQUID_CRYSTAL_FISHING, WakeParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.MIDNIGHT_SOLUTION_FISHING, WakeParticle.Provider::new);
        event.registerSpriteSet(PastelParticleTypes.DRAGONROT_FISHING, WakeParticle.Provider::new);

        //Azzyy sucked cock here
        //Can confirm this sucks ~Daf
        event.registerSpriteSet(PastelParticleTypes.LIGHT_TRAIL, LightTrailParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.FALLING_ASH, FallingAshParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.FIREFLY, FireflyParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.BLOODFLY, BloodflyParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.QUARTZ_FLUFF, QuartzFluffParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.LIGHT_RAIN, RaindropParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.HEAVY_RAIN, RaindropParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.RAIN_SPLASH, TranslucentSplashParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.RAIN_RIPPLE, RainRippleParticle.Factory::new);

        // Since these can reference other particle types, they should always come last
        event.registerSpriteSet(PastelParticleTypes.DYNAMIC, DynamicParticle.Factory::new);
        event.registerSpriteSet(PastelParticleTypes.DYNAMIC_ALWAYS_SHOW, DynamicParticle.Factory::new);
    }

    public static TextureSheetParticle createFallingSporeBlossom(
        SimpleParticleType type, ClientLevel world, double x, double y, double z, double velocityX, double velocityY,
        double velocityZ
    ) {
        int i = (int) (64.0F / Mth.randomBetween(world.getRandom(), 0.1F, 0.9F));
        DripParticle blockLeakParticle = new DripParticle.FallingParticle(world, x, y, z, Fluids.EMPTY, i);
        blockLeakParticle.gravity = 0.005F;
        (blockLeakParticle).setColor(0.32F, 0.5F, 0.22F);
        return blockLeakParticle;
    }


}
