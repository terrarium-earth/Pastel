package earth.terrarium.pastel.helpers.level.collections;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.*;

public interface GroupedCollection {
    @FunctionalInterface
    interface QuadFunction<T, U, V, W, R> {
        R apply(T t, U u, V v, W w);
   }

   @FunctionalInterface
   interface BlockFactory<K, Id> extends QuadFunction<K, Id, Function<BlockBehaviour.Properties, Block>, Supplier<BlockBehaviour.Properties>, DeferredBlock<Block>> {}

}
