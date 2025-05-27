package de.dafuqs.spectrum.attachments;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.attachments.data.azure_dike.AzureDikeData;
import de.dafuqs.spectrum.attachments.data.*;
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
		REGISTER.register("lastKill", () -> LastKillData.ATTACHMENT);
		REGISTER.register("playerMisc", () -> MiscPlayerData.ATTACHMENT);
		REGISTER.register(bus);
	}
	
}
