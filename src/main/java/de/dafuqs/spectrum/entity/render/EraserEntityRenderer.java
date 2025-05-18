package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.entity.entity.EraserEntity;
import de.dafuqs.spectrum.entity.models.EraserEntityModel;
import de.dafuqs.spectrum.registries.client.SpectrumModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class EraserEntityRenderer extends MobRenderer<EraserEntity, EraserEntityModel> {
	
	public static final ResourceLocation TEXTURE = SpectrumCommon.locate("textures/entity/eraser/eraser_base.png");
	
	public EraserEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new EraserEntityModel(context.bakeLayer(SpectrumModelLayers.ERASER)), 0.175F);
		this.addLayer(new EraserOverlayFeatureRenderer(this));
	}
	
	@Override
	public ResourceLocation getTextureLocation(EraserEntity entity) {
		return TEXTURE;
	}
	
}
