package de.dafuqs.spectrum.api.block;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.slots.ShadowSlot;
import de.dafuqs.spectrum.networking.c2s_payloads.SetShadowSlotPayload;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
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
import net.neoforged.neoforge.items.*;
import net.neoforged.neoforge.network.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public interface FilterConfigurable {
	
	String FILTER_KEY = "Filter";
	
	FriendlyStackHandler getItemFilters();

	void setFilterItem(int slot, ItemStack item);

	default int getFilterRows() {
		return 1;
	}
	
	default int getSlotsPerRow() {
		return 5;
	}
	
	default int getDrawnSlots() {
		return getItemFilters().getSlots();
	}
	
	static void writeFilterNbt(CompoundTag tag, ItemStackHandler filterItems, HolderLookup.Provider lookup) {
		tag.put(FILTER_KEY, filterItems.serializeNBT(lookup));
	}
	
	static void readFilterNbt(CompoundTag tag, ItemStackHandler filterItems, HolderLookup.Provider lookup) {
		filterItems.deserializeNBT(lookup, tag.getCompound(FILTER_KEY));
	}
	
	static Container getFilterInventoryFromDataClicker(ExtendedData data, ShadowSlotClicker clicker) {
		var size = data.filterItems().size();
		Container inventory = new FilterInventory(clicker, size);
		for (int i = 0; i < size; i++) {
			inventory.setItem(i, data.filterItems().get(i));
		}
		return inventory;
	}
	
	static Container getFilterInventoryFromExtendedData(int syncId, @NotNull Inventory playerInventory, ExtendedData data, @NotNull AbstractContainerMenu handler) {
		final var clicker = new ShadowSlotClicker.FromHandler(handler, playerInventory.player, syncId);
		return getFilterInventoryFromDataClicker(data, clicker);
	}
	
	static Container getFilterInventoryFromItemsClicker(ItemStackHandler items, ShadowSlotClicker clicker) {
		Container inventory = new FilterInventory(clicker, items.getSlots());
		for (int i = 0; i < items.getSlots(); i++) {
			inventory.setItem(i, items.getStackInSlot(i));
		}
		return inventory;
	}
	
	static Container getFilterInventoryFromItemsHandler(int syncId, @NotNull Inventory playerInventory, ItemStackHandler items, @NotNull AbstractContainerMenu thisHandler) {
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
	
	static void writeScreenOpeningData(RegistryFriendlyByteBuf buf, ItemStackHandler filterItems, int rows, int slotsPerRow, int drawnSlots) {
		buf.writeInt(filterItems.getSlots());
		filterItems.serializeNBT(buf.registryAccess());
		buf.writeInt(rows);
		buf.writeInt(slotsPerRow);
		buf.writeInt(drawnSlots);
	}
	
	default boolean hasEmptyFilter() {
		var filters = getItemFilters();
		for (int i = 0; i < filters.getSlots(); i++) {
			if (filters.getStackInSlot(i).isEmpty())
				return true;
		}
		return false;
	}
	
	HolderGetter.Provider getLookup();
	
	record ExtendedData(List<ItemStack> filterItems, int rows, int slotsPerRow, int drawnSlots) {
		public ExtendedData(FilterConfigurable configurable) {
			this(configurable.getItemFilters().getInternalList(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots());
		}
		
		public static final StreamCodec<RegistryFriendlyByteBuf, ExtendedData> STREAM_CODEC = StreamCodec.composite(
				ItemStack.STREAM_CODEC.apply(ByteBufCodecs.list()), ExtendedData::filterItems,
				ByteBufCodecs.VAR_INT, ExtendedData::rows,
				ByteBufCodecs.VAR_INT, ExtendedData::slotsPerRow,
				ByteBufCodecs.VAR_INT, ExtendedData::drawnSlots,
				ExtendedData::new
		);
		
	}
	
	record ExtendedDataWithPos(BlockPos pos, ExtendedData data) {
		
		public ExtendedDataWithPos(BlockPos pos, FilterConfigurable configurable) {
			this(pos, new ExtendedData(configurable.getItemFilters().getInternalList(), configurable.getFilterRows(), configurable.getSlotsPerRow(), configurable.getDrawnSlots()));
		}
		
		public static final StreamCodec<RegistryFriendlyByteBuf, ExtendedDataWithPos> STREAM_CODEC = StreamCodec.composite(
				BlockPos.STREAM_CODEC, c -> c.pos,
				ExtendedData.STREAM_CODEC, c -> c.data,
				ExtendedDataWithPos::new
		);
		
	}
	
}
