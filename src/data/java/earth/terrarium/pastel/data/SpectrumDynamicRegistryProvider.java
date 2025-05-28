package earth.terrarium.pastel.data;

import earth.terrarium.pastel.registries.SpectrumEnchantments;
import earth.terrarium.pastel.registries.SpectrumPlacedFeatures;
import earth.terrarium.pastel.registries.SpectrumRegistryKeys;
import earth.terrarium.pastel.registries.SpectrumResonanceProcessors;
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
