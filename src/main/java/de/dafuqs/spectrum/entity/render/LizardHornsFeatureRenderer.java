package de.dafuqs.spectrum.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.dafuqs.spectrum.entity.entity.LizardEntity;
import de.dafuqs.spectrum.entity.models.LizardEntityModel;
import de.dafuqs.spectrum.entity.variants.LizardHornVariant;
import de.dafuqs.spectrum.registries.client.SpectrumRenderLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

@Environment(EnvType.CLIENT)
public class LizardHornsFeatureRenderer<T extends LizardEntity> extends RenderLayer<T, LizardEntityModel<T>> {
    
    public LizardHornsFeatureRenderer(RenderLayerParent<T, LizardEntityModel<T>> context) {
        super(context);
    }
    
    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T lizard, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        
        LizardHornVariant horns = lizard.getHorns();
        if (horns != LizardHornVariant.ONLY_LIKES_YOU_AS_A_FRIEND) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(SpectrumRenderLayers.GlowInTheDarkRenderLayer.get(horns.getTextureLocation()));
			var color = lizard.getColor().getColorInt();
            this.getParentModel().renderToBuffer(matrices, vertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, color);
        }
    }
    
}
