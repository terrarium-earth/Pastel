package earth.terrarium.pastel.attachments;

import earth.terrarium.pastel.*;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeData;
import earth.terrarium.pastel.attachments.data.*;
import net.minecraft.core.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.attachment.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumStoredData {

	private static final DeferredRegister<AttachmentType<?>> REGISTER = DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, SpectrumCommon.MOD_ID);

	public static void register(IEventBus bus) {
		REGISTER.register("primfire", () -> PrimordialFireData.ATTACHMENT);
		REGISTER.register("dike", () -> AzureDikeData.ATTACHMENT);
		REGISTER.register("ribbon", () -> EverpromiseRibbonData.ATTACHMENT);
		REGISTER.register("last_kill", () -> LastKillData.ATTACHMENT);
		REGISTER.register("player_misc", () -> MiscPlayerData.ATTACHMENT);
		REGISTER.register(bus);
	}
	
}
