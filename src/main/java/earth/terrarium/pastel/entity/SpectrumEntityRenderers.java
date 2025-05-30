package earth.terrarium.pastel.entity;

import earth.terrarium.pastel.entity.render.BedrockFishingBobberEntityRenderer;
import earth.terrarium.pastel.entity.render.BidentEntityRenderer;
import earth.terrarium.pastel.entity.render.EggLayingWoolyPigEntityRenderer;
import earth.terrarium.pastel.entity.render.EraserEntityRenderer;
import earth.terrarium.pastel.entity.render.FloatBlockEntityRenderer;
import earth.terrarium.pastel.entity.render.GlassArrowEntityRenderer;
import earth.terrarium.pastel.entity.render.KindlingCoughEntityRenderer;
import earth.terrarium.pastel.entity.render.KindlingEntityRenderer;
import earth.terrarium.pastel.entity.render.LagoonFishingBobberEntityRenderer;
import earth.terrarium.pastel.entity.render.LightMineEntityRenderer;
import earth.terrarium.pastel.entity.render.LightShardEntityRenderer;
import earth.terrarium.pastel.entity.render.LightSpearEntityRenderer;
import earth.terrarium.pastel.entity.render.LizardEntityRenderer;
import earth.terrarium.pastel.entity.render.MagicProjectileEntityRenderer;
import earth.terrarium.pastel.entity.render.MoltenFishingBobberEntityRenderer;
import earth.terrarium.pastel.entity.render.PhantomFrameEntityRenderer;
import earth.terrarium.pastel.entity.render.PreservationTurretEntityRenderer;
import earth.terrarium.pastel.entity.render.SeatEntityRenderer;
import earth.terrarium.pastel.entity.render.ShootingStarEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.fml.event.lifecycle.*;

@OnlyIn(Dist.CLIENT)
public class SpectrumEntityRenderers {
	
	public static void registerClient(FMLClientSetupEvent event) {
		register(SpectrumEntityTypes.FLOAT_BLOCK.get(), FloatBlockEntityRenderer::new);
		register(SpectrumEntityTypes.SEAT.get(), SeatEntityRenderer::new);
		register(SpectrumEntityTypes.SHOOTING_STAR.get(), ShootingStarEntityRenderer::new);
		register(SpectrumEntityTypes.PHANTOM_FRAME.get(), PhantomFrameEntityRenderer::new);
		register(SpectrumEntityTypes.GLOW_PHANTOM_FRAME.get(), PhantomFrameEntityRenderer::new);
		register(SpectrumEntityTypes.BLOCK_FLOODER_PROJECTILE.get(), ThrownItemRenderer::new);
		register(SpectrumEntityTypes.INK_PROJECTILE.get(), MagicProjectileEntityRenderer::new);
		register(SpectrumEntityTypes.LAGOON_FISHING_BOBBER.get(), LagoonFishingBobberEntityRenderer::new);
		register(SpectrumEntityTypes.MOLTEN_FISHING_BOBBER.get(), MoltenFishingBobberEntityRenderer::new);
		register(SpectrumEntityTypes.BEDROCK_FISHING_BOBBER.get(), BedrockFishingBobberEntityRenderer::new);
		register(SpectrumEntityTypes.FIREPROOF_ITEM.get(), ItemEntityRenderer::new);
		register(SpectrumEntityTypes.EGG_LAYING_WOOLY_PIG.get(), EggLayingWoolyPigEntityRenderer::new);
		register(SpectrumEntityTypes.GLASS_ARROW.get(), GlassArrowEntityRenderer::new);
		register(SpectrumEntityTypes.MINING_PROJECTILE.get(), MagicProjectileEntityRenderer::new);
		register(SpectrumEntityTypes.BIDENT.get(), BidentEntityRenderer::new);
		register(SpectrumEntityTypes.BIDENT_MIRROR_IMAGE.get(), BidentEntityRenderer::new);
		register(SpectrumEntityTypes.LIGHT_SHARD.get(), LightShardEntityRenderer::new);
		register(SpectrumEntityTypes.LIGHT_SPEAR.get(), LightSpearEntityRenderer::new);
		register(SpectrumEntityTypes.LIGHT_MINE.get(), LightMineEntityRenderer::new);
		register(SpectrumEntityTypes.PRESERVATION_TURRET.get(), PreservationTurretEntityRenderer::new);
		register(SpectrumEntityTypes.LIZARD.get(), LizardEntityRenderer::new);
		register(SpectrumEntityTypes.KINDLING.get(), KindlingEntityRenderer::new);
		register(SpectrumEntityTypes.KINDLING_COUGH.get(), KindlingCoughEntityRenderer::new);
		register(SpectrumEntityTypes.ERASER.get(), EraserEntityRenderer::new);
		register(SpectrumEntityTypes.ITEM_PROJECTILE.get(), ThrownItemRenderer::new);
		register(SpectrumEntityTypes.DRAGON_TALON.get(), (context) -> new BidentEntityRenderer(context, 1.5F, 0));
		register(SpectrumEntityTypes.DRACONIC_TWINSWORD.get(), (context) -> new BidentEntityRenderer(context, 2.15F, 0));
	}
	
	private static <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> factory) {
		EntityRenderers.register(type, factory);
	}
	
}