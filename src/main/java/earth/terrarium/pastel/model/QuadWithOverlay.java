package earth.terrarium.pastel.model;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public class QuadWithOverlay extends BakedQuad {

    private final TextureAtlasSprite overlay;

    public QuadWithOverlay(BakedQuad sacrificial, TextureAtlasSprite overlay) {
        super(sacrificial.getVertices(), sacrificial.getTintIndex(), sacrificial.getDirection(),
                sacrificial.getSprite(), sacrificial.isShade(), sacrificial.hasAmbientOcclusion());
        this.overlay = overlay;
    }

    public TextureAtlasSprite getOverlay() {
        return overlay;
    }
}
