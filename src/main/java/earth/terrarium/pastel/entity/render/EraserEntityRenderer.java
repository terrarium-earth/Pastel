package earth.terrarium.pastel.entity.render;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.EraserEntity;
import earth.terrarium.pastel.entity.models.EraserEntityModel;
import earth.terrarium.pastel.registries.client.PastelModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(
    Dist.CLIENT
)
public class EraserEntityRenderer extends MobRenderer<EraserEntity, EraserEntityModel> {

    public static final ResourceLocation TEXTURE = PastelCommon.locate("textures/entity/eraser/eraser_base.png");

    public EraserEntityRenderer(EntityRendererProvider.Context context) {
        super(context, new EraserEntityModel(context.bakeLayer(PastelModelLayers.ERASER)), 0.175F);
        this.addLayer(new EraserOverlayFeatureRenderer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(EraserEntity entity) {
        return TEXTURE;
    }

}
