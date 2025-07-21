package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.KindlingCoughEntity;
import earth.terrarium.pastel.entity.models.KindlingCoughEntityModel;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class KindlingCoughEntityRenderer extends EntityRenderer<KindlingCoughEntity> {

    private static final ResourceLocation TEXTURE = PastelCommon.locate("textures/entity/kindling/cough.png");
    private final KindlingCoughEntityModel model;

    public KindlingCoughEntityRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.model = new KindlingCoughEntityModel(context.bakeLayer(PastelModelLayers.KINDLING_COUGH));
    }

    public void render(
        KindlingCoughEntity kindlingCoughEntity, float f, float g, PoseStack poseStack,
        MultiBufferSource vertexConsumerProvider, int i
    ) {
        poseStack.pushPose();
        poseStack.translate(0.0, 0.15000000596046448, 0.0);
        poseStack.mulPose(
            Axis.YP.rotationDegrees(Mth.lerp(g, kindlingCoughEntity.yRotO, kindlingCoughEntity.getYRot()) - 90.0F));
        poseStack.mulPose(
            Axis.ZP.rotationDegrees(Mth.lerp(g, kindlingCoughEntity.xRotO, kindlingCoughEntity.getXRot())));
        this.model.setupAnim(kindlingCoughEntity, g, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(this.model.renderType(TEXTURE));
        this.model.renderToBuffer(poseStack, vertexConsumer, i, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
        super.render(kindlingCoughEntity, f, g, poseStack, vertexConsumerProvider, i);
    }

    public ResourceLocation getTextureLocation(KindlingCoughEntity kindlingCoughEntity) {
        return TEXTURE;
    }
}
