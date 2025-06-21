package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.KindlingEntity;
import earth.terrarium.pastel.entity.models.KindlingEntityModel;
import earth.terrarium.pastel.entity.variants.KindlingVariant;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

@OnlyIn(Dist.CLIENT)
public class KindlingEntityRenderer extends MobRenderer<KindlingEntity, KindlingEntityModel> {
	
	public static final ResourceLocation SADDLE_TEXTURE = PastelCommon.locate("textures/entity/kindling/saddle.png");
	
	public KindlingEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new KindlingEntityModel(context.bakeLayer(PastelModelLayers.KINDLING)), 0.7F);
		this.addLayer(new SaddleLayer<>(this, new KindlingEntityModel(context.bakeLayer(PastelModelLayers.KINDLING_SADDLE)), SADDLE_TEXTURE));
		this.addLayer(new KindlingEntityArmorFeatureRenderer(this, context.getModelSet()));
	}
	
	@Override
	public void render(KindlingEntity entity, float yaw, float tickDelta, PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int light) {
		super.render(entity, yaw, tickDelta, poseStack, vertexConsumerProvider, light);
	}
	
	@Override
	public ResourceLocation getTextureLocation(@NotNull KindlingEntity entity) {
		KindlingVariant variant = entity.getKindlingVariant();
		boolean isClipped = entity.isClipped();
		if (entity.getRemainingPersistentAngerTime() > 0) {
			return isClipped ? variant.getAngryClippedTexture() : variant.getAngryTexture();
		}

		boolean isBlinking = (entity.getId() - entity.level().getGameTime()) % 120 == 0; // based on the entities' id, so not all blink at the same time
		if (isClipped) {
			return isBlinking ? variant.getBlinkingClippedTexture() : variant.getClippedTexture();
		}
		
		return isBlinking ? variant.getBlinkingTexture() : variant.getDefaultTexture();
	}

	@Override
	protected boolean isShaking(KindlingEntity entity) {
		return entity.getEepyTime() > 0;
	}
}
