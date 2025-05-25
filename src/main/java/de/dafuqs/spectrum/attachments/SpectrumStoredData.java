package de.dafuqs.spectrum.attachments;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.attachments.data.azure_dike.AzureDikeData;
import de.dafuqs.spectrum.attachments.data.*;
import net.minecraft.core.*;
import net.neoforged.neoforge.registries.*;

public class SpectrumStoredData {

	public static void register() {
		Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, SpectrumCommon.locate("primfire"), PrimordialFireData.ATTACHMENT);
		Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, SpectrumCommon.locate("dike"), AzureDikeData.ATTACHMENT);
		Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, SpectrumCommon.locate("ribbon"), EverpromiseRibbonData.ATTACHMENT);
		Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, SpectrumCommon.locate("lastKill"), LastKillData.ATTACHMENT);
		Registry.register(NeoForgeRegistries.ATTACHMENT_TYPES, SpectrumCommon.locate("playerMisc"), MiscPlayerData.ATTACHMENT);
	}
	
}
