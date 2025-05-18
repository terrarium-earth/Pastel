package de.dafuqs.spectrum.data;

import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class DatagenProxy {
	
	public static final boolean IS_DATAGEN = System.getProperty("fabric-api.datagen") != null;
	
	public interface TagBuilderCallback<T> {
		FabricTagProvider<T>.FabricTagBuilder build(FabricTagProvider<T>.FabricTagBuilder provider);
	}
	
	public interface KeyedTagBuilderCallback<T> {
		FabricTagProvider<T>.FabricTagBuilder build(ResourceKey<T> key, FabricTagProvider<T>.FabricTagBuilder provider);
	}
	
	public interface ProvidedTagBuilderBuilder<T> {
		FabricTagProvider<T>.FabricTagBuilder build(TagKey<T> key);
	}
	
	public record BootstrapContext<T>(
			net.minecraft.data.worldgen.BootstrapContext<T> registerable,
			HolderGetter<Item> items,
			HolderGetter<Block> blocks,
			HolderGetter<Enchantment> enchantments
	) {
		public BootstrapContext(net.minecraft.data.worldgen.BootstrapContext<T> registerable) {
			this(
					registerable,
					registerable.lookup(Registries.ITEM),
					registerable.lookup(Registries.BLOCK),
					registerable.lookup(Registries.ENCHANTMENT)
			);
		}
	}
	
}
