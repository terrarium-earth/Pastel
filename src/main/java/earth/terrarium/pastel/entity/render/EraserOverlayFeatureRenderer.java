package earth.terrarium.pastel.entity.render;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.EraserEntity;
import earth.terrarium.pastel.entity.models.EraserEntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

public class EraserOverlayFeatureRenderer extends EyesLayer<EraserEntity, EraserEntityModel> {

    public static final RenderType LAYER = RenderType
        .eyes(
            PastelCommon.locate("textures/entity/eraser/eraser_emissive.png")
        );

    public EraserOverlayFeatureRenderer(RenderLayerParent<EraserEntity, EraserEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderType renderType() {
        return LAYER;
    }
}
