package earth.terrarium.pastel.entity.render;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.EggLayingWoolyPigEntity;
import earth.terrarium.pastel.entity.models.EggLayingWoolyPigEntityModel;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EggLayingWoolyPigEntityRenderer
    extends MobRenderer<EggLayingWoolyPigEntity, EggLayingWoolyPigEntityModel> {

    public static final ResourceLocation TEXTURE = PastelCommon.locate(
        "textures/entity/egg_laying_wooly_pig/egg_laying_wooly_pig.png");
    public static final ResourceLocation TEXTURE_BLINKING = PastelCommon.locate(
        "textures/entity/egg_laying_wooly_pig/egg_laying_wooly_pig_blink.png");

    public EggLayingWoolyPigEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new EggLayingWoolyPigEntityModel(context.bakeLayer(PastelModelLayers.WOOLY_PIG)), 0.6F);
        this.addLayer(new EggLayingWoolyPigWoolFeatureRenderer(this, context.getModelSet()));
    }

    @Override
    public ResourceLocation getTextureLocation(EggLayingWoolyPigEntity entity) {
        return (entity.getId() - entity.level()
                                       .getGameTime()) % 120 == 0 ? TEXTURE_BLINKING
                                                                  : TEXTURE; // based on the entities' id, so not all
        // blink at the same time
    }

}
