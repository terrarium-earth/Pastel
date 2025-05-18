package de.dafuqs.spectrum.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.dafuqs.spectrum.entity.entity.LizardEntity;
import de.dafuqs.spectrum.entity.models.LizardEntityModel;
import de.dafuqs.spectrum.entity.variants.LizardFrillVariant;
import de.dafuqs.spectrum.registries.client.SpectrumRenderLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Environment(EnvType.CLIENT)
public class LizardFrillsFeatureRenderer<T extends LizardEntity> extends RenderLayer<T, LizardEntityModel<T>> {
    
    public LizardFrillsFeatureRenderer(RenderLayerParent<T, LizardEntityModel<T>> context) {
        super(context);
    }
    
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T lizard, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        LizardFrillVariant frills = lizard.getFrills();
        if (frills != LizardFrillVariant.NONE) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(SpectrumRenderLayers.GlowInTheDarkRenderLayer.get(frills.getTextureLocation()));
            var color = lizard.getColor().getColorInt();
            this.getParentModel().renderToBuffer(matrices, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color);
        }
    }
    
}
