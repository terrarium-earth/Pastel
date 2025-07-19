package earth.terrarium.pastel.data;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.tags.*;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;

public class DatagenProxy {
	
	public static final boolean IS_DATAGEN = System.getProperty("fabric-api.datagen") != null;
	
	public interface TagBuilderCallback<T> {
		TagsProvider.TagAppender<T> build(TagsProvider.TagAppender<T> provider);
	}
	
	public interface KeyedTagBuilderCallback<T> {
		TagsProvider.TagAppender<T> build(ResourceKey<T> key, TagsProvider.TagAppender<T> provider);
	}
	
	public interface ProvidedTagBuilderBuilder<T> {
		TagsProvider.TagAppender<T> build(TagKey<T> key);
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
