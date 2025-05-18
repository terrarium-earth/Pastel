package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.entity.entity.EggLayingWoolyPigEntity;
import de.dafuqs.spectrum.entity.models.EggLayingWoolyPigEntityModel;
import de.dafuqs.spectrum.registries.client.SpectrumModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class EggLayingWoolyPigEntityRenderer extends MobRenderer<EggLayingWoolyPigEntity, EggLayingWoolyPigEntityModel> {
	
	public static final ResourceLocation TEXTURE = SpectrumCommon.locate("textures/entity/egg_laying_wooly_pig/egg_laying_wooly_pig.png");
	public static final ResourceLocation TEXTURE_BLINKING = SpectrumCommon.locate("textures/entity/egg_laying_wooly_pig/egg_laying_wooly_pig_blink.png");
	
	public EggLayingWoolyPigEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new EggLayingWoolyPigEntityModel(context.bakeLayer(SpectrumModelLayers.WOOLY_PIG)), 0.6F);
		this.addLayer(new EggLayingWoolyPigWoolFeatureRenderer(this, context.getModelSet()));
	}
	
	@Override
	public ResourceLocation getTextureLocation(EggLayingWoolyPigEntity entity) {
		return (entity.getId() - entity.level().getGameTime()) % 120 == 0 ? TEXTURE_BLINKING : TEXTURE; // based on the entities' id, so not all blink at the same time
	}
	
}