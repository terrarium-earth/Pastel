package earth.terrarium.pastel.entity;

import earth.terrarium.pastel.entity.render.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
public class PastelEntityRenderers {

    public static void registerClient(FMLClientSetupEvent event) {
        register(PastelEntityTypes.FLOAT_BLOCK.get(), FloatBlockEntityRenderer::new);
        register(PastelEntityTypes.SEAT.get(), SeatEntityRenderer::new);
        register(PastelEntityTypes.SHOOTING_STAR.get(), ShootingStarEntityRenderer::new);
        register(PastelEntityTypes.PHANTOM_FRAME.get(), PhantomFrameEntityRenderer::new);
        register(PastelEntityTypes.GLOW_PHANTOM_FRAME.get(), PhantomFrameEntityRenderer::new);
        register(PastelEntityTypes.BLOCK_FLOODER_PROJECTILE.get(), ThrownItemRenderer::new);
        register(PastelEntityTypes.INK_PROJECTILE.get(), MagicProjectileEntityRenderer::new);
        register(PastelEntityTypes.WIRE_HOOK.get(), WireHookEntityRenderer::new);
        register(PastelEntityTypes.LAGOON_FISHING_BOBBER.get(), LagoonFishingBobberEntityRenderer::new);
        register(PastelEntityTypes.MOLTEN_FISHING_BOBBER.get(), MoltenFishingBobberEntityRenderer::new);
        register(PastelEntityTypes.BEDROCK_FISHING_BOBBER.get(), BedrockFishingBobberEntityRenderer::new);
        register(PastelEntityTypes.FIREPROOF_ITEM.get(), ItemEntityRenderer::new);
        register(PastelEntityTypes.EGG_LAYING_WOOLY_PIG.get(), EggLayingWoolyPigEntityRenderer::new);
        register(PastelEntityTypes.GLASS_ARROW.get(), GlassArrowEntityRenderer::new);
        register(PastelEntityTypes.MINING_PROJECTILE.get(), MagicProjectileEntityRenderer::new);
        register(PastelEntityTypes.BIDENT.get(), BidentEntityRenderer::new);
        register(PastelEntityTypes.BIDENT_MIRROR_IMAGE.get(), BidentEntityRenderer::new);
        register(PastelEntityTypes.LIGHT_SHARD.get(), LightShardEntityRenderer::new);
        register(PastelEntityTypes.LIGHT_SPEAR.get(), LightSpearEntityRenderer::new);
        register(PastelEntityTypes.LIGHT_MINE.get(), LightMineEntityRenderer::new);
        register(PastelEntityTypes.PRESERVATION_TURRET.get(), PreservationTurretEntityRenderer::new);
        register(PastelEntityTypes.LIZARD.get(), LizardEntityRenderer::new);
        register(PastelEntityTypes.KINDLING.get(), KindlingEntityRenderer::new);
        register(PastelEntityTypes.KINDLING_COUGH.get(), KindlingCoughEntityRenderer::new);
        register(PastelEntityTypes.ERASER.get(), EraserEntityRenderer::new);
        register(PastelEntityTypes.ITEM_PROJECTILE.get(), ThrownItemRenderer::new);
        register(PastelEntityTypes.DRAGON_TALON.get(), (context) -> new BidentEntityRenderer(context, 1.5F, 0));
        register(PastelEntityTypes.DRACONIC_TWINSWORD.get(), (context) -> new BidentEntityRenderer(context, 2.15F, 0));
        register(PastelEntityTypes.ENDER_CANVAS.get(), EnderCanvasRenderer::new);
    }

    private static <T extends Entity> void register(EntityType<? extends T> type, EntityRendererProvider<T> factory) {
        EntityRenderers.register(type, factory);
    }

}
