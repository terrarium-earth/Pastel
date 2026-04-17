package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.imbrifer.ImbriferDimensionEffects;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

public class PastelDimensionsClient {
    public static final ResourceLocation EFFECTS_ID = PastelCommon.locate("imbrifer");

    public static void registerClient(RegisterDimensionSpecialEffectsEvent event) {
        event.register(EFFECTS_ID, new ImbriferDimensionEffects());
    }
}
