package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.deeper_down.DeeperDownDimensionEffects;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

public class PastelDimensionsClient {
	public static final ResourceLocation EFFECTS_ID = SpectrumCommon.locate("deeper_down");
	
	public static void registerClient(RegisterDimensionSpecialEffectsEvent event) {
		event.register(EFFECTS_ID, new DeeperDownDimensionEffects());
	}
}
