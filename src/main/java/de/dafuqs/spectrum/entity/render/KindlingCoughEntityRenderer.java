package de.dafuqs.spectrum.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.entity.entity.KindlingCoughEntity;
import de.dafuqs.spectrum.entity.models.KindlingCoughEntityModel;
import de.dafuqs.spectrum.registries.client.SpectrumModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

@Environment(EnvType.CLIENT)
public class KindlingCoughEntityRenderer extends EntityRenderer<KindlingCoughEntity> {
	
	private static final ResourceLocation TEXTURE = SpectrumCommon.locate("textures/entity/kindling/cough.png");
	private final KindlingCoughEntityModel model;
	
	public KindlingCoughEntityRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new KindlingCoughEntityModel(context.bakeLayer(SpectrumModelLayers.KINDLING_COUGH));
	}
	
	public void render(KindlingCoughEntity kindlingCoughEntity, float f, float g, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int i) {
		poseStack.pushPose();
		poseStack.translate(0.0, 0.15000000596046448, 0.0);
		poseStack.mulPose(Axis.YP.rotationDegrees(Mth.lerp(g, kindlingCoughEntity.yRotO, kindlingCoughEntity.getYRot()) - 90.0F));
		poseStack.mulPose(Axis.ZP.rotationDegrees(Mth.lerp(g, kindlingCoughEntity.xRotO, kindlingCoughEntity.getXRot())));
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
