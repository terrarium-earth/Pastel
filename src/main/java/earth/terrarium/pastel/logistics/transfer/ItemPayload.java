package earth.terrarium.pastel.logistics.transfer;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.item.ItemReference;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.logistics.api.Payload;
import net.minecraft.core.Direction;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ItemCapability;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class ItemPayload implements Payload<ItemStack, IItemHandler> {

    private final ResourceLocation id;

    ItemPayload(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public ResourceLocation id() {
        return id;
    }

    @Override
    public BlockCapability<IItemHandler, Direction> blockCapability() {
        return Capabilities.ItemHandler.BLOCK;
    }

    @Override
    public ItemCapability<IItemHandler, Void> itemCapability() {
        return Capabilities.ItemHandler.ITEM;
    }

    @Override
    public int getCount(ItemStack stack) {
        return stack.getCount();
    }

    @Override
    public ItemStack insert(IItemHandler destination, ItemStack input, int count, boolean simulate) {
        return ItemHandlerHelper.insertItemStacked(
            destination, input.copyWithCount(count), true);
    }

    @Override
    public ItemStack extract(IItemHandler source, ItemStack request, int count, boolean simulate) {
        return InventoryHelper.extractFromInventory(
            source, ItemReference.of(request),
            count
        );
    }

    @Override
    public MapCodec<Wrapper> wrapperCodec() {
        return (MapCodec<Wrapper>) (Object) ItemWrapper.CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, Wrapper> wrapperStream() {
        return (StreamCodec<RegistryFriendlyByteBuf, Wrapper>) (Object) ItemWrapper.STREAM_CODEC;
    }

    @Override
    public Wrapper<ItemStack, IItemHandler> wrap(ItemStack stack) {
        return new ItemWrapper(stack);
    }

    public static class ItemWrapper extends Payload.Wrapper<ItemStack, IItemHandler> {

        public static final MapCodec<ItemWrapper> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ItemStack.CODEC.fieldOf("wrapped").forGetter(ItemWrapper::getWrapped)
        ).apply(i, ItemWrapper::new));

        public static final StreamCodec<RegistryFriendlyByteBuf, ItemWrapper> STREAM_CODEC = StreamCodec.composite(
            ItemStack.STREAM_CODEC, ItemWrapper::getWrapped,
            ItemWrapper::new
        );

        private final ItemStack wrapped;

        public ItemWrapper(ItemStack wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public Payload<ItemStack, IItemHandler, Payload.Sachet<ItemStack>> getType() {
            return PastelPayloads.ITEM_PAYLOAD.get();
        }

        @Override
        public ItemStack getWrapped() {
            return wrapped;
        }
    }
}
