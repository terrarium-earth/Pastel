package earth.terrarium.pastel.logistics.api;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;

public interface Payload<T, C> {

    ResourceLocation id();
    BlockCapability<C, Direction> blockCapability();

    ItemCapability<C, Void> itemCapability();

    T insert(C destination, T input, int count, boolean simulate);

    T extract(C source, T request, int count, boolean simulate);

    int getCount(T stack);

    MapCodec<Wrapper> wrapperCodec();

    StreamCodec<RegistryFriendlyByteBuf, Wrapper> wrapperStream();

    Wrapper<T, C> wrap(T reference);

    abstract class Wrapper<T, C> {
        public abstract Payload<T, C> getType();

        public abstract T getWrapped();
    }
}
