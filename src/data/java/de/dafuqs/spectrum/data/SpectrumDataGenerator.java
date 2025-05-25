package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.SpectrumCommon;
import net.minecraft.core.HolderLookup;
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
		event.addProvider(new SpectrumModelProvider(packOutput, lookupProvider));
		event.addProvider(new SpectrumRecipeProvider(packOutput, lookupProvider));
		event.addProvider(new SpectrumCompostableDataMapProvider(packOutput, lookupProvider));
		event.addProvider(new SpectrumWaxableDataMapProvider(packOutput, lookupProvider));

		event.createDatapackRegistryObjects(SpectrumDynamicRegistryProvider.createRegistryBuilders());
	}
}
