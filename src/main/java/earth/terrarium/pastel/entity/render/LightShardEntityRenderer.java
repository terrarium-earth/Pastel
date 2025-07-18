package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.entity.entity.LightShardEntity;
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
import org.joml.Matrix4f;

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
        matrices.pushPose();

        var age = shard.tickCount;
        var alpha = Mth.clamp(
            1 - Mth.lerp(tickDelta, shard.getVanishingProgress(age - 1), shard.getVanishingProgress(age)), 0F, 1F);
        var scaleFactor = Mth.sin((age + tickDelta) / 16F) / 10F + shard.getScaleOffset();

        matrices.mulPose(this.entityRenderDispatcher.cameraOrientation());
        matrices.scale(scaleFactor, scaleFactor, scaleFactor);

        VertexConsumer consumer = vertexConsumers.getBuffer(
            RenderType.entityTranslucentCull(getTextureLocation(shard)));
        PoseStack.Pose matrix = matrices.last();
        Matrix4f positions = matrix.pose();

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
    public ResourceLocation getTextureLocation(LightShardEntity entity) {
        return entity.getTextureLocation();
    }
}
