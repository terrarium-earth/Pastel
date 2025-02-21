package de.dafuqs.spectrum.data;

import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.datagen.v1.*;
import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

import java.util.concurrent.*;

public class SpectrumDataGenerator implements DataGeneratorEntrypoint {
	
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		pack.addProvider(SpectrumItemTagProvider::new);
		pack.addProvider(SpectrumBlockTagProvider::new);
		pack.addProvider(SpectrumEnchantmentTagProvider::new);
		pack.addProvider(SpectrumDynamicRegistryProvider::new);
		pack.addProvider(SpectrumModelProvider::new);
	}
	
	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.ENCHANTMENT, registerable -> SpectrumEnchantments.provideEnchantments(new DatagenProxy.BootstrapContext<>(registerable)));
		registryBuilder.addRegistry(SpectrumRegistryKeys.RESONANCE_PROCESSOR, registerable -> SpectrumResonanceProcessors.provideResonanceProcessors(new DatagenProxy.BootstrapContext<>(registerable)));
	}
	
	public static class SpectrumItemTagProvider extends FabricTagProvider.ItemTagProvider {
		public SpectrumItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> lookupFuture) {
			super(output, lookupFuture);
		}
		
		@Override
		protected void configure(RegistryWrapper.WrapperLookup lookup) {
			SpectrumEnchantments.provideItemTags(this::getOrCreateTagBuilder);
			
			getOrCreateTagBuilder(SpectrumItemTags.COOKBOOKS).add(
					SpectrumItems.BREWERS_HANDBOOK,
					SpectrumItems.IMBRIFER_COOKBOOK,
					SpectrumItems.IMPERIAL_COOKBOOK,
					SpectrumItems.MELOCHITES_COOKBOOK_VOL_1,
					SpectrumItems.MELOCHITES_COOKBOOK_VOL_2,
					SpectrumItems.POISONERS_HANDBOOK);
			
			getOrCreateTagBuilder(ItemTags.BOOKSHELF_BOOKS)
					.addTag(SpectrumItemTags.COOKBOOKS)
					.add(SpectrumItems.GILDED_BOOK, SpectrumItems.GUIDEBOOK);
		}
	}
	
	public static class SpectrumBlockTagProvider extends FabricTagProvider.BlockTagProvider {
		public SpectrumBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
			super(output, registriesFuture);
		}
		
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			SpectrumBlockTags.provideTags(this::getOrCreateTagBuilder);
		}
	}
	
	public static class SpectrumEnchantmentTagProvider extends FabricTagProvider.EnchantmentTagProvider {
		public SpectrumEnchantmentTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
			super(output, completableFuture);
		}
		
		@Override
		protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
			SpectrumEnchantments.provideEnchantmentTags(this::getOrCreateTagBuilder);
		}
	}
	
	public static class SpectrumDynamicRegistryProvider extends FabricDynamicRegistryProvider {
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
	
	
}
