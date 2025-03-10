package de.dafuqs.spectrum.api.block;

import java.util.*;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.networking.c2s_payloads.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jetbrains.annotations.*;

public interface FilterConfigurable {
	
	List<ItemVariant> getItemFilters();
	
	void setFilterItem(int slot, ItemVariant item);
	
	default int getFilterRows() {
		return 1;
	}
	
	default int getSlotsPerRow() {
		return 5;
	}
	
	default int getDrawnSlots() {
		return getItemFilters().size();
	}
	
	static void writeFilterNbt(NbtCompound tag, List<ItemVariant> filterItems) {
		for (int i = 0; i < filterItems.size(); i++) {
			if (!filterItems.get(i).isBlank()) {
				CodecHelper.writeNbt(tag, "FilterStack" + i, ItemVariant.CODEC, filterItems.get(i));
			}
		}
	}
	
	static void readFilterNbt(NbtCompound tag, List<ItemVariant> filterItems) {
		for (int i = 0; i < filterItems.size(); i++) {
			if (tag.contains("FilterStack" + i))
				filterItems.set(i, CodecHelper.fromNbt(ItemVariant.CODEC, tag.get("FilterStack" + i), null));
		}
	}
	
	static Inventory getFilterInventoryFromDataClicker(ExtendedData data, ShadowSlotClicker clicker) {
		var size = data.filterItems().size();
		Inventory inventory = new FilterInventory(clicker, size);
		for (int i = 0; i < size; i++) {
			inventory.setStack(i, data.filterItems().get(i).toStack());
		}
		return inventory;
	}
	
	static Inventory getFilterInventoryFromExtendedData(int syncId, @NotNull PlayerInventory playerInventory, ExtendedData data, @NotNull ScreenHandler handler) {
		final var clicker = new ShadowSlotClicker.FromHandler(handler, playerInventory.player, syncId);
		return getFilterInventoryFromDataClicker(data, clicker);
	}
	
	static Inventory getFilterInventoryFromItemsClicker(List<ItemVariant> items, ShadowSlotClicker clicker) {
		Inventory inventory = new FilterInventory(clicker, items.size());
		for (int i = 0; i < items.size(); i++) {
			inventory.setStack(i, items.get(i).toStack());
		}
		return inventory;
	}
	
	static Inventory getFilterInventoryFromItemsHandler(int syncId, @NotNull PlayerInventory playerInventory, List<ItemVariant> items, @NotNull ScreenHandler thisHandler) {
		final var clicker = new ShadowSlotClicker.FromHandler(thisHandler, playerInventory.player, syncId);
		return getFilterInventoryFromItemsClicker(items, clicker);
	}
	
	// Ensures execution of ShadowSlot.onClicked both on the server and client.
	// Do not use if not required.
	interface ShadowSlotClicker {
		default void clickShadowSlot(int syncId, Slot slot, ItemStack shadowStack) {
			clickShadowSlot(syncId, slot.id, shadowStack);
		}
		
		void clickShadowSlot(int syncId, int id, ItemStack shadowStack);
		
		class FromHandler implements ShadowSlotClicker {
			public final @NotNull ScreenHandler handler;
			public final @NotNull PlayerEntity player;
			public final int syncId;
			
			public FromHandler(@NotNull ScreenHandler screenHandler, @NotNull PlayerEntity player, int syncId) {
				this.handler = screenHandler;
				this.player = player;
				this.syncId = syncId;
			}
			
			@Override
			public void clickShadowSlot(int syncId, @Nullable Slot slot, ItemStack shadowStack) {
				if (this.syncId != syncId || !(slot instanceof ShadowSlot shadowSlot)) return;
				if (!shadowSlot.onClicked(shadowStack, ClickType.LEFT, player)) return;
				
				// Sync with server
				if (player.getWorld().isClient()) {
					ClientPlayNetworking.send(new SetShadowSlotPayload(syncId, slot.id, shadowStack));
				}
			}
			
			@Override
			public void clickShadowSlot(int syncId, int id, ItemStack shadowStack) {
				this.clickShadowSlot(syncId, handler.getSlot(id), shadowStack);
			}
		}
	}
	
	// Contains the slot clicker.
	class FilterInventory extends SimpleInventory {
		private final @NotNull FilterConfigurable.ShadowSlotClicker clicker;
		
		public FilterInventory(@NotNull FilterConfigurable.ShadowSlotClicker slotClicker, int size) {
			super(size);
			this.clicker = slotClicker;
		}
		
		public @NotNull FilterConfigurable.ShadowSlotClicker getClicker() {
			return clicker;
		}
	}
	
	static void writeScreenOpeningData(RegistryByteBuf buf, FilterConfigurable configurable) {
		writeScreenOpeningData(buf, configurable.getItemFilters(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots());
	}
	
	static void writeScreenOpeningData(RegistryByteBuf buf, List<ItemVariant> filterItems, int rows, int slotsPerRow, int drawnSlots) {
		buf.writeInt(filterItems.size());
		for (ItemVariant filterItem : filterItems) {
			// The difference between just using filterItem.toNbt() is that ItemVariant nbt uses "item" while ItemStack uses "id"
			ItemStack.PACKET_CODEC.encode(buf, filterItem.toStack());
		}
		buf.writeInt(rows);
		buf.writeInt(slotsPerRow);
		buf.writeInt(drawnSlots);
	}
	
	default boolean hasEmptyFilter() {
		return getItemFilters().stream().allMatch(ItemVariant::isBlank);
	}
	
	record ExtendedData(List<ItemVariant> filterItems, int rows, int slotsPerRow, int drawnSlots) {
		
		public ExtendedData(FilterConfigurable configurable) {
			this(configurable.getItemFilters(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots());
		}
		
		public static final PacketCodec<RegistryByteBuf, ExtendedData> PACKET_CODEC = PacketCodec.tuple(
				ItemVariant.PACKET_CODEC.collect(PacketCodecs.toList()), ExtendedData::filterItems,
				PacketCodecs.VAR_INT, ExtendedData::rows,
				PacketCodecs.VAR_INT, ExtendedData::slotsPerRow,
				PacketCodecs.VAR_INT, ExtendedData::drawnSlots,
				ExtendedData::new
		);
		
	}
	
	record ExtendedDataWithPos(BlockPos pos, ExtendedData data) {
		
		public ExtendedDataWithPos(BlockPos pos, FilterConfigurable configurable) {
			this(pos, new ExtendedData(configurable.getItemFilters(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots()));
		}
		
		public ExtendedDataWithPos(BlockPos pos, List<ItemVariant> filterItems, int rows, int slotsPerRow, int drawnSlots) {
			this(pos, new ExtendedData(filterItems, rows, slotsPerRow, drawnSlots));
		}
		
		public static final PacketCodec<RegistryByteBuf, ExtendedDataWithPos> PACKET_CODEC = PacketCodec.tuple(
				BlockPos.PACKET_CODEC, c -> c.pos,
				ExtendedData.PACKET_CODEC, c -> c.data,
				ExtendedDataWithPos::new
		);
		
	}
	
}
