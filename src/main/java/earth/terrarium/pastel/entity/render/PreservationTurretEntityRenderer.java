package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.PreservationTurretEntity;
import earth.terrarium.pastel.entity.models.PreservationTurretEntityModel;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PreservationTurretEntityRenderer
    extends MobRenderer<PreservationTurretEntity, PreservationTurretEntityModel<PreservationTurretEntity>> {

    public static final ResourceLocation TEXTURE = PastelCommon.locate(
        "textures/entity/preservation_turret/preservation_turret.png");

    public PreservationTurretEntityRenderer(EntityRendererProvider.Context context) {
        super(
            context, new PreservationTurretEntityModel<>(context.bakeLayer(PastelModelLayers.PRESERVATION_TURRET)),
            0.0F
        );
    }

    @Override
    public ResourceLocation getTextureLocation(PreservationTurretEntity turretEntity) {
        return TEXTURE;
    }

    @Override
    protected void setupRotations(
        PreservationTurretEntity turretEntity, PoseStack matrices, float animationProgress, float bodyYaw,
        float tickDelta, float scale
    ) {
        super.setupRotations(turretEntity, matrices, animationProgress, bodyYaw + 180.0F, tickDelta, scale);
        matrices.translate(0.0, 0.5, 0.0);
        matrices.mulPose(turretEntity.getAttachedFace()
                                     .getOpposite()
                                     .getRotation());
        matrices.translate(0.0, -0.5, 0.0);
    }

}
