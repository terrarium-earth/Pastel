package earth.terrarium.pastel.api.item;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.Iterator;

/**
 * A gross abuse of data components
 */
public class ItemStorage {

    public static final Codec<ItemStorage> CODEC = RecordCodecBuilder.create(i -> i.group(
            ItemReference.CODEC.fieldOf("reference").forGetter(ItemStorage::getReference),
            Codec.LONG.fieldOf("count").forGetter(ItemStorage::getCount),
            Codec.LONG.optionalFieldOf("limit", -1L).forGetter(ItemStorage::getLimit)
    ).apply(i, ItemStorage::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, ItemStorage> STREAM_CODEC = StreamCodec.composite(
            ItemReference.STREAM_CODEC, ItemStorage::getReference,
            ByteBufCodecs.VAR_LONG, ItemStorage::getCount,
            ByteBufCodecs.VAR_LONG, ItemStorage::getLimit,
            ItemStorage::new//
    );

    private ItemReference reference;
    private long count;
    private long limit = -1;

    public ItemStorage(ItemReference reference, long count, long limit) {
        this(reference, count);
        this.limit = limit;
    }

    public ItemStorage(ItemReference reference, long count) {
        this.reference = reference;
        this.count = count;
    }

    public ItemStorage(ItemReference reference) {
        this(reference, 0);
    }

    public static ItemStorage blank() {
        return new ItemStorage(ItemReference.empty(), 0);
    }

    public static ItemStorage limited(long limit) {
        var storage = blank();
        storage.setLimit(limit);
        return storage;
    }

    public long getCount() {
        return count;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public ItemReference getReference() {
        return reference;
    }

    public int stackSize() {
        return reference.asItem().getMaxStackSize(stack(1));
    }

    public void increment(long increment) {
        if (isLimited()) {
            this.count = Math.min(limit, this.count + increment);
        }
        else {
            this.count += increment;
        }
    }

    /**
     * @return The amount of items inserted
     */
    public int insert(ItemStack stack) {
        if (this.isEmpty()) {
            reference = ItemReference.of(stack);
        }

        int offer = stack.getCount();

        if (!reference.permits(stack))
            return 0;

        var old = count;
        increment(offer);

        return (int) (count - old);
    }

    public ItemStack extract(long extract) {
        return stack((int) extractPure(extract));
    }

    public long extractPure(long extract) {
        if (extract == 0 || count == 0)
            return 0;

        var decrement = Math.min(count, extract);
        this.increment(-decrement);

        return decrement;
    }

    public void setCount(long count) {
        if (count < 0)
            count = 0;

        this.count = count;
    }

    public void setReference(ItemReference reference) {
        this.reference = reference;
    }

    public ItemStorage copy() {
        return new ItemStorage(reference, count, limit);
    }

    public ItemStack stack(int count) {
        if (count == 0)
            return ItemStack.EMPTY;

        return new ItemStack(reference.asItem().builtInRegistryHolder(), count, reference.asPatch());
    }

    public ItemStack unsafeStack() {
        return stack((int) count);
    }

    public boolean isLimited() {
        return limit >= 0;
    }

    public boolean isEmpty() {
        return count == 0 ;
    }

    public static ItemStorage load(ItemStack holder) {
        var component = holder.getOrDefault(PastelDataComponentTypes.ITEM_STORAGE, Component.DEFAULT);
        var storage = new ItemStorage(component.reference, component.count);

        if (holder.getItem() instanceof LimitCallback callback)
            storage.setLimit(callback.updateLimit(holder));

        return storage;
    }

    public void save(ItemStack holder) {
        save(holder, true);
    }

    public void save(ItemStack holder, boolean blankOnEmpty) {
        if (blankOnEmpty && isEmpty() && !reference.isEmpty())
            setReference(ItemReference.empty());

        holder.set(PastelDataComponentTypes.ITEM_STORAGE, new Component(reference, count));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ItemStorage other))
            return false;

        if (this == other)
            return true;

        return this.count == other.count && this.reference.equals(other.reference);
    }

    @Override
    public int hashCode() {
        return 31 * (31 + reference.asItem().hashCode() + reference.getComponents().hashCode());
    }

    public interface LimitCallback {
        long updateLimit(ItemStack holder);
    }

    public static class IterableView implements Iterator<ItemStack> {

        private final ItemStorage storage;

        public IterableView(ItemStorage storage) {
            this.storage = storage;
        }

        @Override
        public boolean hasNext() {
            return !storage.isEmpty();
        }

        @Override
        public ItemStack next() {
            return storage.extract(storage.stackSize());
        }
    }

    public record Component(ItemReference reference, long count) {
        public static final Component DEFAULT = new Component(ItemReference.empty(), 0);

        public static final Codec<Component> CODEC = RecordCodecBuilder.create(i -> i.group(
                ItemReference.CODEC.fieldOf("reference").forGetter(Component::reference),
                Codec.LONG.fieldOf("count").forGetter(Component::count)
        ).apply(i, Component::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, Component> STREAM_CODEC = StreamCodec.composite(
                ItemReference.STREAM_CODEC, Component::reference,
                ByteBufCodecs.VAR_LONG, Component::count,
                Component::new
        );
    }
}
