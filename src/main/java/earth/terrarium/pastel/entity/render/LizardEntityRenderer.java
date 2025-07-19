package earth.terrarium.pastel.entity.render;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.LizardEntity;
import earth.terrarium.pastel.entity.models.LizardEntityModel;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class LizardEntityRenderer extends MobRenderer<LizardEntity, LizardEntityModel<LizardEntity>> {

    public static final ResourceLocation TEXTURE = PastelCommon.locate("textures/entity/lizard/lizard.png");
    public static final ResourceLocation TEXTURE_BLINKING = PastelCommon.locate(
        "textures/entity/lizard/lizard_blinking.png");

    public LizardEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new LizardEntityModel<>(context.bakeLayer(PastelModelLayers.LIZARD_SCALES)), 0.8F);
        this.addLayer(new LizardEyesFeatureRenderer<>(this));
        this.addLayer(new LizardHornsFeatureRenderer<>(this));
        this.addLayer(new LizardFrillsFeatureRenderer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(LizardEntity entity) {
        return (entity.getId() - entity.level()
                                       .getGameTime()) % 120 == 0 ? TEXTURE_BLINKING
                                                                  : TEXTURE; // based on the entities' id, so not all
        // blink at the same time
    }

}
