package de.dafuqs.spectrum.compat.farmersdelight;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.compat.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.loot.v3.*;
import net.minecraft.loot.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.util.*;

import java.util.*;

public class FDCompat extends SpectrumIntegrationPacks.ModIntegrationPack {
	
	private static final Identifier AMARANTH_LOOT_TABLE_ID = SpectrumCommon.locate("blocks/amaranth");
	private static final RegistryKey<LootTable> FD_AMARANTH_LOOT_KEY = RegistryKey.of(RegistryKeys.LOOT_TABLE, SpectrumCommon.locate("mod_integration/farmers_delight/amaranth"));
	
	public void register() {
		LootTableEvents.REPLACE.register((registryKey, lootTable, lootTableSource, wrapperLookup) -> {
			if (AMARANTH_LOOT_TABLE_ID.equals(registryKey.getValue())) {
				Optional<RegistryEntry.Reference<LootTable>> wrapper = wrapperLookup
					.createRegistryLookup()
					.getOptional(RegistryKeys.LOOT_TABLE)
					.get()
					.getOptional(FD_AMARANTH_LOOT_KEY);
				return wrapper.orElseThrow().value();
			}
			return null;
		});
	}
	
	@Environment(EnvType.CLIENT)
	@Override
	public void registerClient() {
	
	}
	
}
