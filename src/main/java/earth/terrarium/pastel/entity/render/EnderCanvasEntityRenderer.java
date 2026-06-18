package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.CanvasWorkaroundPlayerEntity;
import earth.terrarium.pastel.entity.entity.EnderCanvasEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.RemotePlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.commands.arguments.EntityAnchorArgument;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.AttachmentType;

public class EnderCanvasEntityRenderer extends EntityRenderer<EnderCanvasEntity> {
    private final EntityRendererProvider.Context context;

    public EnderCanvasEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public ResourceLocation getTextureLocation(EnderCanvasEntity entity) {
        return ResourceLocation.withDefaultNamespace("textures/painting/tides.png");
    }

    @Override
    public void render(
        EnderCanvasEntity entity,
        float entityYaw,
        float partialTick,
        PoseStack poseStack,
        MultiBufferSource bufferSource,
        int packedLight
    ) {
        var buffer = bufferSource.getBuffer(RenderType.entitySolid(getTextureLocation(entity)));
        var width = entity.getVariant() == EnderCanvasEntity.EnderCanvasVariant.LANDSCAPELARGE ? 2 : 1;
        var skyLight = (packedLight >> 16) & 0xFFFF;
        var blockLight = packedLight & 0xFFFF;

        poseStack.pushPose();
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        var pose = poseStack.last();

        buffer
            .addVertex(pose, -width / 2f, 1, 0)
            .setColor(1f, 1f, 1f, 1f)
            .setUv(1, 0)
            .setUv1(0, 10)
            .setUv2(skyLight, blockLight)
            .setNormal(0, 0, -1);
        buffer
            .addVertex(pose, width / 2f, 1, 0)
            .setColor(1f, 1f, 1f, 1f)
            .setUv(0, 0)
            .setUv1(0, 10)
            .setUv2(skyLight, blockLight)
            .setNormal(0, 0, -1);
        buffer
            .addVertex(pose, width / 2f, -1, 0)
            .setColor(1f, 1f, 1f, 1f)
            .setUv(0, 1)
            .setUv1(0, 10)
            .setUv2(skyLight, blockLight)
            .setNormal(0, 0, -1);
        buffer
            .addVertex(pose, -width / 2f, -1, 0)
            .setColor(1f, 1f, 1f, 1f)
            .setUv(1, 1)
            .setUv1(0, 10)
            .setUv2(skyLight, blockLight)
            .setNormal(0, 0, -1);

        if (entity.getVariant() == EnderCanvasEntity.EnderCanvasVariant.PORTRAIT && entity
            .getSpliceData()
            .targetGameProfile()
            .isPresent() && Minecraft.getInstance().level != null) {
            CanvasWorkaroundPlayerEntity toRender;
            if (entity.cachedPlayer == null) {
                toRender = new CanvasWorkaroundPlayerEntity(
                    Minecraft.getInstance().level,
                    entity
                        .getSpliceData()
                        .targetGameProfile()
                        .get()
                );
                toRender.setCustomNameVisible(false);

                toRender
                    .getEntityData()
                    .set(Player.DATA_PLAYER_MODE_CUSTOMISATION, (byte) 0b1111111);
                entity.cachedPlayer = toRender;
            } else {
                toRender = entity.cachedPlayer;
            }

            toRender
                .lookAt(
                    EntityAnchorArgument.Anchor.EYES,
                    toRender
                        .getEyePosition()
                        .relative(entity.getDirection(), 1.0)
                );

            poseStack.pushPose();
            poseStack
                .mulPose(
                    entity
                        .getDirection()
                        .getAxis() == Direction.Axis.Z
                            ? Axis.YP.rotationDegrees(180f - entityYaw)
                            : Axis.YP.rotationDegrees(-entityYaw)
                );
            poseStack.translate(0, -1, -0.005);
            poseStack
                .scale(
                    entity
                        .getDirection()
                        .getAxis() == Direction.Axis.Z ? 1f : 0.0625f,
                    1,
                    entity
                        .getDirection()
                        .getAxis() == Direction.Axis.X
                            ? 1f
                            : 0.0625f
                );
            context
                .getEntityRenderDispatcher()
                .render(toRender, 0, 0, 0, 0, partialTick, poseStack, bufferSource, packedLight);
            poseStack.popPose();
        }
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
