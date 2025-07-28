package earth.terrarium.pastel.entity.render;

import com.cmdpro.databank.rendering.RenderHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.entity.entity.LightShardEntity;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
public class LightShardEntityRenderer extends EntityRenderer<LightShardEntity> {

    public LightShardEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(
        LightShardEntity shard, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers,
        int light
    ) {
        renderTrail(shard, tickDelta, matrices);
        matrices.pushPose();

        var age = shard.tickCount;
        var alpha = Mth.clamp(
            1 - Mth.lerp(tickDelta, shard.getVanishingProgress(age - 1), shard.getVanishingProgress(age)), 0F, 1F);
        var scaleFactor = Mth.sin((age + tickDelta) / 16F) / 10F + shard.getScaleOffset();

        var size = shard.getBoundingBox().getSize() / 2;
        matrices.translate(0, size, 0);
        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.scale(scaleFactor, scaleFactor, scaleFactor);

        VertexConsumer consumer = vertexConsumers.getBuffer(
            RenderType.entityTranslucentCull(getTextureLocation(shard)));
        PoseStack.Pose matrix = matrices.last();
        Matrix4f positions = matrix.pose();

        consumer.addVertex(positions, -0.5F, -0.5F, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(0, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);
        consumer.addVertex(positions, 0.5F, -0.5F, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(1, 1)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);
        consumer.addVertex(positions, 0.5F, 0.5F, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(1, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);
        consumer.addVertex(positions, -0.5F, 0.5F, 0)
                .setColor(1f, 1f, 1f, alpha)
                .setUv(0, 0)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(LightTexture.FULL_BRIGHT)
                .setNormal(matrix, 0, 1, 0);

        matrices.popPose();

        super.render(shard, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    private static void renderTrail(LightShardEntity shard, float tickDelta, PoseStack matrices) {
        var trail = shard.getTrail();

        if (trail == null)
            return;

        matrices.pushPose();
        double d0 = Mth.lerp(tickDelta, shard.xOld, shard.getX());
        double d1 = Mth.lerp(tickDelta, shard.yOld, shard.getY());
        double d2 = Mth.lerp(tickDelta, shard.zOld, shard.getZ());
        Vec3 posOffset = new Vec3(d0, d1, d2).subtract(shard.position());
        Vec3 pos = shard.position()
                        .add(posOffset);
        matrices.translate(-pos.x, -pos.y, -pos.z);
        ;
        trail.position = shard.getBoundingBox()
                              .getCenter()
                              .add(posOffset);
        trail.render(
            matrices, RenderHandler.createBufferSource(), LightTexture.FULL_BRIGHT,
            shard.getGradient()
        );
        matrices.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(LightShardEntity entity) {
        return entity.getTextureLocation();
    }
}
