package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import de.dafuqs.spectrum.registries.SpectrumPlacedFeatures;
import de.dafuqs.spectrum.registries.SpectrumRegistryKeys;
import de.dafuqs.spectrum.registries.SpectrumResonanceProcessors;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class SpectrumDynamicRegistryProvider {
	public static RegistrySetBuilder createRegistryBuilders() {
		return new RegistrySetBuilder()
				.add(Registries.ENCHANTMENT, registerable -> SpectrumEnchantments.provideEnchantments(new DatagenProxy.BootstrapContext<>(registerable)))
				.add(SpectrumRegistryKeys.RESONANCE_PROCESSOR, registerable -> SpectrumResonanceProcessors.provideResonanceProcessors(new DatagenProxy.BootstrapContext<>(registerable)))
				.add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, registerable -> SpectrumPlacedFeatures.addBiomeModifications(new DatagenProxy.BootstrapContext<>(registerable)));
	}
}
