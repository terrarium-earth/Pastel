package earth.terrarium.pastel.api.collection;

import earth.terrarium.pastel.api.energy.color.InkColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import org.apache.commons.lang3.function.TriFunction;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class GroupedCollection<K, V> {
    protected final Map<K, V> values;

    public GroupedCollection(Map<K, V> values) {
        this.values = Map.copyOf(values);
    }

    @FunctionalInterface
    public interface QuadFunction<T, U, V, W, R> {
        R apply(T t, U u, V v, W w);
   }

   @FunctionalInterface
   public interface BlockFactory<K, Id> extends QuadFunction<K, Id, Function<BlockBehaviour.Properties, Block>, BlockBehaviour.Properties, DeferredBlock<Block>> {}


    public static <B extends Block, Id, K> Map<K, DeferredBlock<Block>> registerBlocks(
            Map<K, K> values,
            Map<K, Id> ids,
            QuadFunction<K, Id, Function<BlockBehaviour.Properties, Block>, BlockBehaviour.Properties, DeferredBlock<Block>> register,
            BiFunction<K, BlockBehaviour.Properties, B> blockFactory,
            Function<K, BlockBehaviour.Properties> propertiesSupplier
    ) {
        return zipMap(
                values,
                ids,
                (key, id) -> register.apply(key, id, p -> blockFactory.apply(key, p), propertiesSupplier.apply(key))
        );
    }

    public static <Id, K> Map<K, DeferredItem<Item>> registerBlockItems(
            Map<K, K> values,
            Map<K, Id> ids,
            Map<K, DeferredBlock<Block>> blocks,
            TriFunction<Id, DeferredBlock<Block>, K, DeferredItem<Item>> itemFactory
    ) {
        return zipMap(
                values,
                ids,
                (key, id) -> itemFactory.apply(id, blocks.get(key), key)
        );
    }

    // Java has no form of higher kinded types, so I can't really abstract over
    // this any better
    public static <Id, K> Map<K, DeferredItem<Item>> registerItems(
            Map<K, K> values,
            Map<K, Id> ids,
            BiFunction<Id, K, DeferredItem<Item>> itemFactory
    ) {
        return zipMap(
                ids,
                values,
                itemFactory
        );
    }



    public abstract <U> GroupedCollection<K, U> map(Function<V, U> mapper);



    public static <K, V, U> Map<K, U> map(Map<K, V> values, Function<V, U> mapper) {
        var newMap = new HashMap<K, U>();
        values.forEach((k, v) -> newMap.put(k, mapper.apply(v)));
        return Collections.unmodifiableMap(newMap);
    }

    public static <K, V, U, R> Map<K, R> zipMap(Map<K, V> first, Map<K, U> second, BiFunction<V, U, R> operation) {
        var newMap = new HashMap<K, R>();
        first.keySet().forEach(
                key ->
                {
                    if (second.containsKey(key)) {
                        var firstV = first.get(key);
                        var secondV = second.get(key);
                        newMap.put(key, operation.apply(firstV, secondV));
                    }
                }
        );

        return Collections.unmodifiableMap(newMap);
    }

    public V pick(K key) {
        return this.values.get(key);
    }

    public void forEach(Consumer<V> consumer) {
        this.values.values().forEach(consumer);
    }

    public void forEachEntry(BiConsumer<K, V> action) {
        this.values.forEach(action);
    }
}
