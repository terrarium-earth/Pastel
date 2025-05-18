package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.entity.entity.LizardEntity;
import de.dafuqs.spectrum.entity.models.LizardEntityModel;
import de.dafuqs.spectrum.registries.client.SpectrumModelLayers;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@Environment(EnvType.CLIENT)
public class LizardEntityRenderer extends MobRenderer<LizardEntity, LizardEntityModel<LizardEntity>> {
	
	public static final ResourceLocation TEXTURE = SpectrumCommon.locate("textures/entity/lizard/lizard.png");
	public static final ResourceLocation TEXTURE_BLINKING = SpectrumCommon.locate("textures/entity/lizard/lizard_blinking.png");

	public LizardEntityRenderer(EntityRendererProvider.Context context) {
		super(context, new LizardEntityModel<>(context.bakeLayer(SpectrumModelLayers.LIZARD_SCALES)), 0.8F);
		this.addLayer(new LizardEyesFeatureRenderer<>(this));
		this.addLayer(new LizardHornsFeatureRenderer<>(this));
		this.addLayer(new LizardFrillsFeatureRenderer<>(this));
	}
	
	@Override
	public ResourceLocation getTextureLocation(LizardEntity entity) {
		return (entity.getId() - entity.level().getGameTime()) % 120 == 0 ? TEXTURE_BLINKING : TEXTURE; // based on the entities' id, so not all blink at the same time
 	}
	
}
