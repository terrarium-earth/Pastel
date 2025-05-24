package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import de.dafuqs.spectrum.registries.SpectrumRegistryKeys;
import de.dafuqs.spectrum.registries.SpectrumResonanceProcessors;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = SpectrumCommon.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class SpectrumDataGenerator {

	@SubscribeEvent
	public static void onInitializeDataGenerator(GatherDataEvent event) {
		PackOutput packOutput = event.getGenerator().getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		SpectrumBlockTagsProvider blockTagsProvider = new SpectrumBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

		event.addProvider(blockTagsProvider);
		event.addProvider(new SpectrumItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
		event.addProvider(new SpectrumEnchantmentTagsProvider(packOutput, lookupProvider, existingFileHelper));
		event.addProvider(new SpectrumDynamicRegistryProvider(packOutput, lookupProvider));
		event.addProvider(new SpectrumModelProvider(packOutput, lookupProvider));
		event.addProvider(new SpectrumRecipeProvider(packOutput, lookupProvider));
		event.addProvider(new SpectrumCompostableDataMapProvider(packOutput, lookupProvider));
	}
	
	// TODO
	@Override
	public void buildRegistry(RegistrySetBuilder registryBuilder) {
		registryBuilder.add(Registries.ENCHANTMENT, registerable -> SpectrumEnchantments.provideEnchantments(new DatagenProxy.BootstrapContext<>(registerable)));
		registryBuilder.add(SpectrumRegistryKeys.RESONANCE_PROCESSOR, registerable -> SpectrumResonanceProcessors.provideResonanceProcessors(new DatagenProxy.BootstrapContext<>(registerable)));
	}
	
}
