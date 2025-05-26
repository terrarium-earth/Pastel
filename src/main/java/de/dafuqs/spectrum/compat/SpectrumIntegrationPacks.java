package de.dafuqs.spectrum.compat;

import de.dafuqs.fractal.api.*;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.compat.ae2.AE2Compat;
import de.dafuqs.spectrum.compat.botania.BotaniaCompat;
import de.dafuqs.spectrum.compat.create.CreateCompat;
import de.dafuqs.spectrum.compat.exclusions_lib.ExclusionsLibCompat;
import de.dafuqs.spectrum.compat.gobber.GobberCompat;
import de.dafuqs.spectrum.compat.modonomicon.ModonomiconCompat;
import de.dafuqs.spectrum.compat.travelersbackpack.TravelersBackpackCompat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SpectrumIntegrationPacks {
	
	protected static final Map<String, ModIntegrationPack> INTEGRATION_PACKS = new HashMap<>();
	
	
	public abstract static class ModIntegrationPack {
		public abstract void register();
		
		public abstract void registerClient();
	}
	
	protected static void registerIntegrationPack(String modId, Supplier<ModIntegrationPack> container) {
		if (!SpectrumCommon.CONFIG.IntegrationPacksToSkipLoading.contains(modId) && ModList.get().isLoaded(modId)) {
			var pack = container.get();

			INTEGRATION_PACKS.put(modId, pack);
			pack.register();
		}
	}

	public static final String AE2_ID = "ae2";
	public static final String GOBBER_ID = "gobber2";
	public static final String TRAVELERS_BACKPACK_ID = "travelersbackpack";
	public static final String BOTANIA_ID = "botania";
	public static final String MODONOMICON_ID = "modonomicon";
	public static final String CREATE_ID = "create";
	public static final String FARMERSDELIGHT_ID = "farmersdelight";
	public static final String EXCLUSIONS_LIB_ID = "exclusions_lib";

	@SuppressWarnings("Convert2MethodRef")
	public static void register() {
		registerIntegrationPack(MODONOMICON_ID, () -> new ModonomiconCompat());
		
		if (!ModList.get().isLoaded(EXCLUSIONS_LIB_ID)) {
			ExclusionsLibCompat.registerNotPresent();
		}

		registerIntegrationPack(AE2_ID, () -> new AE2Compat());
		registerIntegrationPack(GOBBER_ID, () -> new GobberCompat());
		registerIntegrationPack(TRAVELERS_BACKPACK_ID, () -> new TravelersBackpackCompat());
		registerIntegrationPack(BOTANIA_ID, () -> new BotaniaCompat());
		//registerIntegrationPack(FARMERSDELIGHT_ID, () -> new FDCompat());
		registerIntegrationPack(CREATE_ID, () -> new CreateCompat());
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void registerClient() {
		for (ModIntegrationPack container : INTEGRATION_PACKS.values()) {
			container.registerClient();
		}
	}
	
	public static boolean isIntegrationPackActive(String modId) {
		return INTEGRATION_PACKS.containsKey(modId);
	}
	
}
