package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class PastelWoodOverlayTextureProvider extends WoodOverlayTextureProvider {
    public PastelWoodOverlayTextureProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, PastelCommon.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addOverlays() {
        for (InkColor inkColor : InkColors.all()) {
            addOverlay("planks", inkColor);
            addOverlay("log", inkColor);
            addOverlay("log_top", inkColor);
            addOverlay("leaves", inkColor);
            addOverlay("stripped_log", inkColor);
            addOverlay("stripped_log_top", inkColor);
        }
    }

    @Override
    public String getName() {
        return "Wood Overlay Textures";
    }
}
