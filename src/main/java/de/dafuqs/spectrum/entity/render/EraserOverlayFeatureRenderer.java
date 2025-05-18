package de.dafuqs.spectrum.entity.render;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.entity.entity.EraserEntity;
import de.dafuqs.spectrum.entity.models.EraserEntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;

public class EraserOverlayFeatureRenderer extends EyesLayer<EraserEntity, EraserEntityModel> {

    public static final RenderType LAYER = RenderType.eyes(SpectrumCommon.locate("textures/entity/eraser/eraser_emissive.png"));

    public EraserOverlayFeatureRenderer(RenderLayerParent<EraserEntity, EraserEntityModel> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public RenderType renderType() {
        return LAYER;
    }
}
