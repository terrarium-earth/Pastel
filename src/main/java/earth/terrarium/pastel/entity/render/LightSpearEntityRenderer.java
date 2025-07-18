package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import earth.terrarium.pastel.entity.entity.LightShardBaseEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@OnlyIn(Dist.CLIENT)
public class LightSpearEntityRenderer extends EntityRenderer<LightShardBaseEntity> {

    public LightSpearEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(
        LightShardBaseEntity shard, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
        int light
    ) {
        matrices.pushPose();

        var age = shard.tickCount;
        var alpha = Mth.clamp(
            1 - Mth.lerp(tickDelta, shard.getVanishingProgress(age - 1), shard.getVanishingProgress(age)), 0F, 1F);
        var scaleFactor = Mth.sin((age + tickDelta) / 8F) / 6F + shard.getScaleOffset();

        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.mulPose(Axis.YP.rotationDegrees(Mth.lerp(tickDelta, shard.yRotO, shard.getYRot()) - 45.0F));
        matrices.mulPose(Axis.ZP.rotationDegrees(0 + Mth.lerp(tickDelta, shard.xRotO, shard.getXRot())));

        matrices.scale(scaleFactor, scaleFactor, 1);
        matrices.translate(-0.5F, -0.5F, 0);

        var consumer = vertexConsumers.getBuffer(RenderType.entityTranslucentCull(getTextureLocation(shard)));
        var matrix = matrices.last();
        var positions = matrix.pose();

        consumer.addVertex(positions, 0, 0, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(0, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);
        consumer.addVertex(positions, 1, 0, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(1, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);
        consumer.addVertex(positions, 1, 1, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(1, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);
        consumer.addVertex(positions, 0, 1, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(0, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);

        matrices.popPose();

        super.render(shard, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    @Override
    public ResourceLocation getTextureLocation(LightShardBaseEntity entity) {
        return entity.getTextureLocation();
    }
}
