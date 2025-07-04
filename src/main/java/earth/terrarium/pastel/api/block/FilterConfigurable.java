package earth.terrarium.pastel.api.block;

import earth.terrarium.pastel.api.item.ItemReference;
import earth.terrarium.pastel.inventories.slots.ShadowSlot;
import earth.terrarium.pastel.networking.c2s_payloads.SetShadowSlotPayload;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.*;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface FilterConfigurable {

	String FILTER_KEY = "Filter";
	
	NonNullList<ItemReference> getItemFilters();

	void setFilterItem(int slot, ItemStack item);

	default int getFilterRows() {
		return 1;
	}
	
	default int getSlotsPerRow() {
		return 5;
	}
	
	default int getDrawnSlots() {
		return getItemFilters().size();
	}
	
	static void writeFilterNbt(CompoundTag tag, List<ItemReference> filterItems, HolderLookup.Provider lookup) {
		var innerTag = new CompoundTag();
		var list = new ListTag();

		for (int i = 0; i < filterItems.size(); i++) {
			var ref = filterItems.get(i);
			if (ref.isEmpty())
				continue;

			var refTag = new CompoundTag();
			refTag.putInt("Slot", i);
			refTag.put("Ref", ItemReference.CODEC.encodeStart(lookup.createSerializationContext(NbtOps.INSTANCE), ref).getOrThrow());
			list.add(refTag);
		}

		innerTag.put("Filters", list);
		tag.put(FILTER_KEY, innerTag);
	}
	
	static void readFilterNbt(CompoundTag tag, List<ItemReference> filterItems, HolderLookup.Provider lookup) {
		filterItems.clear();

		if (!tag.contains(FILTER_KEY))
			return;

		var innerTag = tag.getCompound(FILTER_KEY);
		var list = innerTag.getList("Filters", Tag.TAG_COMPOUND);
		for (int i = 0; i < list.size(); i++) {
			var refTag = list.getCompound(i);
			var slot = refTag.getInt("Slot");
			if (slot <= filterItems.size())
				filterItems.set(slot, ItemReference.CODEC.decode(lookup.createSerializationContext(NbtOps.INSTANCE), refTag.getCompound("Ref")).getOrThrow().getFirst());
		}
	}
	
	static Container getFilterInventoryFromDataClicker(ExtendedData data, ShadowSlotClicker clicker) {
		var size = data.filterItems().size();
		Container inventory = new FilterInventory(clicker, size);
		for (int i = 0; i < size; i++) {
			inventory.setItem(i, data.filterItems().get(i).asStack());
		}
		return inventory;
	}
	
	static Container getFilterInventoryFromExtendedData(int syncId, @NotNull Inventory playerInventory, ExtendedData data, @NotNull AbstractContainerMenu handler) {
		final var clicker = new ShadowSlotClicker.FromHandler(handler, playerInventory.player, syncId);
		return getFilterInventoryFromDataClicker(data, clicker);
	}
	
	static Container getFilterInventoryFromItemsClicker(List<ItemReference> items, ShadowSlotClicker clicker) {
		Container inventory = new FilterInventory(clicker, items.size());
		for (int i = 0; i < items.size(); i++) {
			inventory.setItem(i, items.get(i).asStack());
		}
		return inventory;
	}
	
	static Container getFilterInventoryFromItemsHandler(int syncId, @NotNull Inventory playerInventory, List<ItemReference> items, @NotNull AbstractContainerMenu thisHandler) {
		final var clicker = new ShadowSlotClicker.FromHandler(thisHandler, playerInventory.player, syncId);
		return getFilterInventoryFromItemsClicker(items, clicker);
	}
	
	// Ensures execution of ShadowSlot.onClicked both on the server and client.
	// Do not use if not required.
	interface ShadowSlotClicker {
		default void clickShadowSlot(int syncId, Slot slot, ItemStack shadowStack) {
			clickShadowSlot(syncId, slot.index, shadowStack);
		}
		
		void clickShadowSlot(int syncId, int id, ItemStack shadowStack);
		
		class FromHandler implements ShadowSlotClicker {
			public final @NotNull AbstractContainerMenu handler;
			public final @NotNull Player player;
			public final int syncId;
			
			public FromHandler(@NotNull AbstractContainerMenu screenHandler, @NotNull Player player, int syncId) {
				this.handler = screenHandler;
				this.player = player;
				this.syncId = syncId;
			}
			
			@Override
			public void clickShadowSlot(int syncId, @Nullable Slot slot, ItemStack shadowStack) {
				if (this.syncId != syncId || !(slot instanceof ShadowSlot shadowSlot)) return;
				if (!shadowSlot.onClicked(shadowStack, ClickAction.PRIMARY, player)) return;
				
				// Sync with server
				if (player.level().isClientSide()) {
					PacketDistributor.sendToServer(new SetShadowSlotPayload(syncId, slot.index, shadowStack));
				}
			}
			
			@Override
			public void clickShadowSlot(int syncId, int id, ItemStack shadowStack) {
				this.clickShadowSlot(syncId, handler.getSlot(id), shadowStack);
			}
		}
	}
	
	// Contains the slot clicker.
	class FilterInventory extends SimpleContainer {
		private final @NotNull FilterConfigurable.ShadowSlotClicker clicker;
		
		public FilterInventory(@NotNull FilterConfigurable.ShadowSlotClicker slotClicker, int size) {
			super(size);
			this.clicker = slotClicker;
		}
		
		public @NotNull FilterConfigurable.ShadowSlotClicker getClicker() {
			return clicker;
		}
	}
	
	static void writeScreenOpeningData(RegistryFriendlyByteBuf buf, FilterConfigurable configurable) {
		writeScreenOpeningData(buf, configurable.getItemFilters(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots());
	}
	
	static void writeScreenOpeningData(RegistryFriendlyByteBuf buf, List<ItemReference> filterItems, int rows, int slotsPerRow, int drawnSlots) {
		buf.writeInt(filterItems.size());
		var nbt = new CompoundTag();
		writeFilterNbt(nbt, filterItems, buf.registryAccess());
		buf.writeNbt(nbt);
		buf.writeInt(rows);
		buf.writeInt(slotsPerRow);
		buf.writeInt(drawnSlots);
	}
	
	default boolean hasEmptyFilter() {
		var filters = getItemFilters();
        for (ItemReference filter : filters) {
            if (!filter.isEmpty())
                return false;
        }
		return true;
	}
	
	record ExtendedData(List<ItemReference> filterItems, int rows, int slotsPerRow, int drawnSlots) {
		public ExtendedData(FilterConfigurable configurable) {
			this(configurable.getItemFilters(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots());
		}
		
		public static final StreamCodec<RegistryFriendlyByteBuf, ExtendedData> STREAM_CODEC = StreamCodec.composite(
				ItemReference.STREAM_CODEC.apply(ByteBufCodecs.list()), ExtendedData::filterItems,
				ByteBufCodecs.VAR_INT, ExtendedData::rows,
				ByteBufCodecs.VAR_INT, ExtendedData::slotsPerRow,
				ByteBufCodecs.VAR_INT, ExtendedData::drawnSlots,
				ExtendedData::new
		);
		
	}
	
	record ExtendedDataWithPos(BlockPos pos, ExtendedData data) {
		
		public ExtendedDataWithPos(BlockPos pos, FilterConfigurable configurable) {
			this(pos, new ExtendedData(configurable.getItemFilters(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots()));
		}
		
		public static final StreamCodec<RegistryFriendlyByteBuf, ExtendedDataWithPos> STREAM_CODEC = StreamCodec.composite(
				BlockPos.STREAM_CODEC, c -> c.pos,
				ExtendedData.STREAM_CODEC, c -> c.data,
				ExtendedDataWithPos::new
		);
		
	}
	
}
