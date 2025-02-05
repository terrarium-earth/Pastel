package de.dafuqs.spectrum.data;

import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

public class SpectrumDataGenerator implements DataGeneratorEntrypoint {
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpectrumItemTagProvider::new);
	}
	
	public interface ProvidedTagBuilderCallback<T> {
		void build(FabricTagProvider<T>.FabricTagBuilder provider);
	}
	
	public interface ProvidedTagBuilderBuilder<T> {
		FabricTagProvider<T>.FabricTagBuilder build(TagKey<T> key);
	}
	
	public interface BootstrapCallback<T, D> {
		D call(RegistryKey<T> key, BootstrapContext<T> ctx);
	}
	
	public record BootstrapContext<T>(Registerable<T> registerable, RegistryEntryLookup<Item> items) {
		public BootstrapContext(Registerable<T> registerable) {
			this(registerable, registerable.getRegistryLookup(RegistryKeys.ITEM));
		}
	}
	
}
