package earth.terrarium.pastel.data;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.data.databank.PastelHiddenProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = PastelCommon.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class PastelDataGenerator {

	@SubscribeEvent
	public static void onInitializeDataGenerator(GatherDataEvent event) {
		PackOutput packOutput = event.getGenerator().getPackOutput();
		CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
		ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

		PastelBlockTagsProvider blockTagsProvider = new PastelBlockTagsProvider(packOutput, lookupProvider, existingFileHelper);

		event.addProvider(blockTagsProvider);
		event.addProvider(new PastelItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), existingFileHelper));
		event.addProvider(new PastelEnchantmentTagsProvider(packOutput, lookupProvider, existingFileHelper));
		//event.addProvider(new SpectrumModelProvider(packOutput, lookupProvider));
		event.addProvider(new PastelRecipeProvider(packOutput, lookupProvider));
		event.addProvider(new PastelCompostableDataMapProvider(packOutput, lookupProvider));
		event.addProvider(new PastelWaxableDataMapProvider(packOutput, lookupProvider));
		event.addProvider(new PastelBurnTimeDataMapProvider(packOutput, lookupProvider));
		event.addProvider(new PastelHiddenProvider(packOutput, lookupProvider, existingFileHelper));

		event.createDatapackRegistryObjects(PastelDynamicRegistryProvider.createRegistryBuilders());
	}
}
