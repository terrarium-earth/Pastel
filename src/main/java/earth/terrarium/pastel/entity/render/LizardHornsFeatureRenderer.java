package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.entity.entity.LizardEntity;
import earth.terrarium.pastel.entity.models.LizardEntityModel;
import earth.terrarium.pastel.entity.variants.LizardHornVariant;
import earth.terrarium.pastel.registries.client.PastelRenderLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LizardHornsFeatureRenderer<T extends LizardEntity> extends RenderLayer<T, LizardEntityModel<T>> {

    public LizardHornsFeatureRenderer(RenderLayerParent<T, LizardEntityModel<T>> context) {
        super(context);
    }

    @Override
    public void render(
        PoseStack matrices, MultiBufferSource vertexConsumers, int light, T lizard, float limbAngle, float limbDistance,
        float tickDelta, float animationProgress, float headYaw, float headPitch
    ) {

        LizardHornVariant horns = lizard.getHorns();
        if (horns != LizardHornVariant.ONLY_LIKES_YOU_AS_A_FRIEND) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(
                PastelRenderLayers.GlowInTheDarkRenderLayer.get(horns.getTextureLocation()));
            var color = lizard.getColor()
                              .getColorInt();
            this.getParentModel()
                .renderToBuffer(matrices, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color);
        }
    }

}
