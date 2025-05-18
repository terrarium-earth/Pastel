package de.dafuqs.spectrum.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.entity.models.LizardEntityModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class LizardEyesFeatureRenderer<T extends LivingEntity> extends EyesLayer<T, LizardEntityModel<T>> {
	
	private static final RenderType TEXTURE = RenderType.eyes(SpectrumCommon.locate("textures/entity/lizard/lizard_eyes.png"));
	
	public LizardEyesFeatureRenderer(RenderLayerParent<T, LizardEntityModel<T>> featureRendererContext) {
		super(featureRendererContext);
	}
	
	@Override
	public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if ((entity.getId() - entity.level().getGameTime() % 120) != 0) {
			super.render(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
		}
	}

	@Override
	public RenderType renderType() {
		return TEXTURE;
	}
	
}
