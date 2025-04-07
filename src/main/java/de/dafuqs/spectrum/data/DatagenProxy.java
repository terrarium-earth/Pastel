package de.dafuqs.spectrum.data;

import net.fabricmc.fabric.api.datagen.v1.provider.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.*;

public class DatagenProxy {
	
	public static final boolean IS_DATAGEN = System.getProperty("fabric-api.datagen") != null;
	
	public interface TagBuilderCallback<T> {
		FabricTagProvider<T>.FabricTagBuilder build(FabricTagProvider<T>.FabricTagBuilder provider);
	}
	
	public interface KeyedTagBuilderCallback<T> {
		FabricTagProvider<T>.FabricTagBuilder build(RegistryKey<T> key, FabricTagProvider<T>.FabricTagBuilder provider);
	}
	
	public interface ProvidedTagBuilderBuilder<T> {
		FabricTagProvider<T>.FabricTagBuilder build(TagKey<T> key);
	}
	
	public record BootstrapContext<T>(
			Registerable<T> registerable,
			RegistryEntryLookup<Item> items,
			RegistryEntryLookup<Block> blocks,
			RegistryEntryLookup<Enchantment> enchantments
	) {
		public BootstrapContext(Registerable<T> registerable) {
			this(
					registerable,
					registerable.getRegistryLookup(RegistryKeys.ITEM),
					registerable.getRegistryLookup(RegistryKeys.BLOCK),
					registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT)
			);
		}
	}
	
}
