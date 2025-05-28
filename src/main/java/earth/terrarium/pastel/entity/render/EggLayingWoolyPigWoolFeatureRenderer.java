package earth.terrarium.pastel.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import earth.terrarium.pastel.entity.entity.EggLayingWoolyPigEntity;
import earth.terrarium.pastel.entity.models.EggLayingWoolyPigEntityModel;
import earth.terrarium.pastel.entity.models.EggLayingWoolyPigHatEntityModel;
import earth.terrarium.pastel.entity.models.EggLayingWoolyPigWoolEntityModel;
import earth.terrarium.pastel.registries.client.SpectrumModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class EggLayingWoolyPigWoolFeatureRenderer extends RenderLayer<EggLayingWoolyPigEntity, EggLayingWoolyPigEntityModel> {
	
	private final EggLayingWoolyPigHatEntityModel hat;
	private final EggLayingWoolyPigWoolEntityModel wool;
	
	public EggLayingWoolyPigWoolFeatureRenderer(EggLayingWoolyPigEntityRenderer context, EntityModelSet loader) {
		super(context);
		this.hat = new EggLayingWoolyPigHatEntityModel(loader.bakeLayer(SpectrumModelLayers.WOOLY_PIG_HAT));
		this.wool = new EggLayingWoolyPigWoolEntityModel(loader.bakeLayer(SpectrumModelLayers.WOOLY_PIG_WOOL));
	}
	
	@Override
	public void render(PoseStack poseStack, MultiBufferSource vertexConsumerProvider, int i, EggLayingWoolyPigEntity entity, float f, float g, float h, float j, float k, float l) {
		if (entity.isInvisible()) {
			Minecraft minecraftClient = Minecraft.getInstance();
			boolean hasOutline = minecraftClient.shouldEntityAppearGlowing(entity);
			if (hasOutline) {
				int rgbColor = EggLayingWoolyPigEntity.getRgbColor(entity.getColor());
				VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(RenderType.outline(EggLayingWoolyPigEntityRenderer.TEXTURE));
				if (!entity.isHatless()) {
					this.getParentModel().copyPropertiesTo(this.hat);
					this.hat.prepareMobModel(entity, f, g, h);
					this.hat.setupAnim(entity, f, g, j, k, l);
					this.hat.renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), rgbColor);
				}
				if (!entity.isSheared()) {
					this.getParentModel().copyPropertiesTo(this.wool);
					this.wool.prepareMobModel(entity, f, g, h);
					this.wool.setupAnim(entity, f, g, j, k, l);
					this.wool.renderToBuffer(poseStack, vertexConsumer, i, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), rgbColor);
				}
			}
		} else {
			int rgbColor = EggLayingWoolyPigEntity.getRgbColor(entity.getColor());
			if (!entity.isHatless()) {
				this.getParentModel().copyPropertiesTo(this.hat);
				this.hat.prepareMobModel(entity, f, g, h);
				this.hat.setupAnim(entity, f, g, j, k, l);
				coloredCutoutModelCopyLayerRender(this.getParentModel(), this.hat, getTextureLocation(entity), poseStack, vertexConsumerProvider, i, entity, f, g, j, k, l, h, rgbColor);
			}
			if (!entity.isSheared()) {
				coloredCutoutModelCopyLayerRender(this.getParentModel(), this.wool, getTextureLocation(entity), poseStack, vertexConsumerProvider, i, entity, f, g, j, k, l, h, rgbColor);
			}
		}
	}
	
	@Override
	public ResourceLocation getTextureLocation(EggLayingWoolyPigEntity entity) {
		return EggLayingWoolyPigEntityRenderer.TEXTURE;
	}
	
}
