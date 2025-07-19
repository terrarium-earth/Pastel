package earth.terrarium.pastel.registries.client;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.deeper_down.DeeperDownDimensionEffects;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;

public class PastelDimensionsClient {
	public static final ResourceLocation EFFECTS_ID = PastelCommon.locate("deeper_down");
	
	public static void registerClient(RegisterDimensionSpecialEffectsEvent event) {
		event.register(EFFECTS_ID, new DeeperDownDimensionEffects());
	}
}
