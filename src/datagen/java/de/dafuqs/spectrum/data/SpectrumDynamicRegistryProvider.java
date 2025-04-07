package de.dafuqs.spectrum.data;

import java.util.concurrent.*;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.registry.*;

public class SpectrumDynamicRegistryProvider extends FabricDynamicRegistryProvider {
	
	public SpectrumDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
		super(output, registriesFuture);
	}
	
	@Override
	protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
		entries.addAll(wrapperLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT));
		entries.addAll(wrapperLookup.getWrapperOrThrow(SpectrumRegistryKeys.RESONANCE_PROCESSOR));
	}
	
	@Override
	public String getName() {
		return "Spectrum Registries";
	}
	
}
