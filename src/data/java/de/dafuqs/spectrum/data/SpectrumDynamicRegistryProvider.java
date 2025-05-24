package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.SpectrumRegistryKeys;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;

import java.util.concurrent.CompletableFuture;

public class SpectrumDynamicRegistryProvider extends FabricDynamicRegistryProvider {
	
	public SpectrumDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	protected void configure(HolderLookup.Provider wrapperLookup, Entries entries) {
		entries.addAll(wrapperLookup.lookupOrThrow(Registries.ENCHANTMENT));
		entries.addAll(wrapperLookup.lookupOrThrow(SpectrumRegistryKeys.RESONANCE_PROCESSOR));
	}
	
	@Override
	public String getName() {
		return "Spectrum Registries";
	}
	
}
