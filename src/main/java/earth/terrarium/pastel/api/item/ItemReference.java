package earth.terrarium.pastel.api.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.component.DataComponentHolder;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;


/**
 * Once Neoforge gets something equivalent we are nuking this shit
 */
public final class ItemReference implements ItemLike, DataComponentHolder {

    private static final ItemReference EMPTY = ItemReference.of(Items.AIR);

    public static final Codec<ItemReference> CODEC = RecordCodecBuilder.create(i -> i.group(
            BuiltInRegistries.ITEM.byNameCodec().fieldOf("reference").forGetter(ref -> ref.reference),
            DataComponentPatch.CODEC.fieldOf("components").forGetter(ref -> ref.components.asPatch())
            ).apply(i, ItemReference::new)
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemReference> STREAM_CODEC = new StreamCodec<>() {
        private static final StreamCodec<RegistryFriendlyByteBuf, Item> ITEM_CODEC = ByteBufCodecs.registry(Registries.ITEM);

        public ItemReference decode(RegistryFriendlyByteBuf buf) {
            var reference = ITEM_CODEC.decode(buf);
            DataComponentPatch datacomponentpatch = DataComponentPatch.STREAM_CODEC.decode(buf);
            return new ItemReference(reference, datacomponentpatch);
        }

        public void encode(RegistryFriendlyByteBuf buf, ItemReference itemReference) {
                ITEM_CODEC.encode(buf, itemReference.reference);
                DataComponentPatch.STREAM_CODEC.encode(buf, itemReference.components.asPatch());
        }
    };

    public final Item reference;
    private final PatchedDataComponentMap components;

    private ItemReference(Item reference) {
        this.reference = reference;
        this.components = new PatchedDataComponentMap(reference.components());
    }

    private ItemReference(Item reference, DataComponentPatch components) {
        this.reference = reference;
        this.components = PatchedDataComponentMap.fromPatch(reference.components(), components);
    }

    public static ItemReference of(ItemLike reference) {
        return new ItemReference(reference.asItem());
    }

    public static ItemReference of(ItemStack stack) {
        if (stack.isEmpty())
            return new ItemReference(Items.AIR, stack.getComponentsPatch());
       return new ItemReference(stack.getItem(), stack.getComponentsPatch());
    }

    public boolean permits(ItemStack stack) {
        if (this.isEmpty() && stack.isEmpty())
            return true;

        return stack.is(reference) && components.equals(stack.getComponents());
    }

    public ItemReference swap(Item item) {
        return new ItemReference(item);
    }

    public boolean is(ItemLike item) {
        return this.reference == item.asItem();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemReference other))
            return false;

        if (this == other)
            return true;

        return this.reference == other.reference && this.components.equals(other.components);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, components);
    }

    @Override
    public Item asItem() {
        return reference;
    }

    public ItemStack asStack() {
        var stack = reference.getDefaultInstance();
        stack.applyComponents(components);
        return stack;
    }

    public ItemStack asStack(int count) {
        return asStack().copyWithCount(count);
    }

    public static ItemReference empty() {
        return EMPTY;
    }

    public boolean isEmpty() {
        return reference.asItem().equals(Items.AIR);
    }

    @Override
    public DataComponentMap getComponents() {
        return components;
    }

    public DataComponentPatch asPatch() {
        return components.asPatch();
    }
}
