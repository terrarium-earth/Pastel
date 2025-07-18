package earth.terrarium.pastel.recipe;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.function.Supplier;

/**
 * A copy of the old SpecialRecipeSerializer, which simply ignores any meaningful recipe serialization.
 * <p>Recipes that use this serializer do not transport any data over the network, besides their ID.
 */
public class EmptyRecipeSerializer<T extends Recipe<?>> implements RecipeSerializer<T> {
    private final Supplier<T> instance;

    public EmptyRecipeSerializer(Supplier<T> factory) {
        this.instance = Suppliers.memoize(factory::get);
    }

    @Override
    public MapCodec<T> codec() {
        return MapCodec.unit(instance);
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return StreamCodec.unit(instance.get());
    }

}
