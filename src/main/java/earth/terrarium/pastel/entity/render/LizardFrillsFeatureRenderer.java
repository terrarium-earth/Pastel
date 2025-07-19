package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.entity.entity.LizardEntity;
import earth.terrarium.pastel.entity.models.LizardEntityModel;
import earth.terrarium.pastel.entity.variants.LizardFrillVariant;
import earth.terrarium.pastel.registries.client.PastelRenderLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

@OnlyIn(Dist.CLIENT)
public class LizardFrillsFeatureRenderer<T extends LizardEntity> extends RenderLayer<T, LizardEntityModel<T>> {
    
    public LizardFrillsFeatureRenderer(RenderLayerParent<T, LizardEntityModel<T>> context) {
        super(context);
    }
    
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T lizard, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        LizardFrillVariant frills = lizard.getFrills();
        if (frills != LizardFrillVariant.NONE) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(PastelRenderLayers.GlowInTheDarkRenderLayer.get(frills.getTextureLocation()));
            var color = lizard.getColor().getColorInt();
            this.getParentModel().renderToBuffer(matrices, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color);
        }
    }
    
}
