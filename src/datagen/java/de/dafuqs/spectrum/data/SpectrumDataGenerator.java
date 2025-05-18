package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import de.dafuqs.spectrum.registries.SpectrumRegistryKeys;
import de.dafuqs.spectrum.registries.SpectrumResonanceProcessors;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jetbrains.annotations.Nullable;

public class SpectrumDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public @Nullable String getEffectiveModId() {
		return SpectrumCommon.MOD_ID;
	}
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpectrumItemTagProvider::new);
		pack.addProvider(SpectrumBlockTagProvider::new);
		pack.addProvider(SpectrumEnchantmentTagProvider::new);
		pack.addProvider(SpectrumDynamicRegistryProvider::new);
		pack.addProvider(SpectrumModelProvider::new);
		pack.addProvider(SpectrumRecipeProvider::new);
	}
	
	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.ENCHANTMENT, registerable -> SpectrumEnchantments.provideEnchantments(new DatagenProxy.BootstrapContext<>(registerable)));
		registryBuilder.add(SpectrumRegistryKeys.RESONANCE_PROCESSOR, registerable -> SpectrumResonanceProcessors.provideResonanceProcessors(new DatagenProxy.BootstrapContext<>(registerable)));
	}
	
}
