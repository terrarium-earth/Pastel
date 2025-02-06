package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

import java.util.concurrent.*;

public class SpectrumDataGenerator implements DataGeneratorEntrypoint {
	
	public static final boolean IS_DATAGEN = System.getProperty("fabric-api.datagen") != null;
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpectrumItemTagProvider::new);
		pack.addProvider(SpectrumEnchantmentTagProvider::new);
		pack.addProvider(SpectrumDynamicRegistryProvider::new);
	}
	
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, SpectrumEnchantments::provideEnchantments);
	}
	
	public interface ProvidedTagBuilderCallback<T> {
		void build(FabricTagProvider<T>.FabricTagBuilder provider);
	}
	
	public interface KeyedTagBuilderCallback<T> {
		void build(RegistryKey<T> key, FabricTagProvider<T>.FabricTagBuilder provider);
	}
	
	public interface ProvidedTagBuilderBuilder<T> {
		FabricTagProvider<T>.FabricTagBuilder build(TagKey<T> key);
	}
	
	public record BootstrapContext<T>(
			Registerable<T> registerable,
			RegistryEntryLookup<Item> items,
			RegistryEntryLookup<Enchantment> enchantments
	) {
		public BootstrapContext(Registerable<T> registerable) {
			this(
					registerable,
					registerable.getRegistryLookup(RegistryKeys.ITEM),
					registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT)
			);
		}
	}
	
	public static class SpectrumDynamicRegistryProvider extends FabricDynamicRegistryProvider {
		
		public SpectrumDynamicRegistryProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}
		
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup, Entries entries) {
			entries.addAll(wrapperLookup.getWrapperOrThrow(RegistryKeys.ENCHANTMENT));
		}
		
		@Override
		public String getName() {
			return "Spectrum Registries";
		}
		
	}
	
}
