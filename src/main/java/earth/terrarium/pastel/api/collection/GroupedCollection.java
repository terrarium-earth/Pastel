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
import java.util.function.*;

public interface GroupedCollection {
    @FunctionalInterface
    interface QuadFunction<T, U, V, W, R> {
        R apply(T t, U u, V v, W w);
   }

   @FunctionalInterface
   interface BlockFactory<K, Id> extends QuadFunction<K, Id, Function<BlockBehaviour.Properties, Block>, Supplier<BlockBehaviour.Properties>, DeferredBlock<Block>> {}

}
