package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.util.*;
import net.minecraft.world.*;

public class BlackHoleChestScreenHandler extends ScreenHandler {
	
	protected static final int ROWS = 3;
	
	protected final World world;
	
	protected BlackHoleChestBlockEntity blockEntity;
	protected Inventory filterInventory;
	
	public BlackHoleChestScreenHandler(int syncId, PlayerInventory playerInventory, FilterConfigurable.ExtendedDataWithPos data) {
		this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(data.pos(), SpectrumBlockEntities.BLACK_HOLE_CHEST).orElseThrow(), data.data());
	}
	
	public BlackHoleChestScreenHandler(int syncId, PlayerInventory playerInventory, BlackHoleChestBlockEntity blockEntity, FilterConfigurable.ExtendedData data) {
		super(SpectrumScreenHandlerTypes.BLACK_HOLE_CHEST, syncId);
		this.world = playerInventory.player.getWorld();
		this.filterInventory = FilterConfigurable.getFilterInventoryFromExtendedData(syncId, playerInventory, data, this);
		this.blockEntity = blockEntity;
		
		checkSize(blockEntity, BlackHoleChestBlockEntity.INVENTORY_SIZE);
		blockEntity.onOpen(playerInventory.player);
		
		int i = (ROWS - 4) * 18;
		
		// sucking chest slots
		int j;
		int k;
		for (j = 0; j < ROWS; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlot(new Slot(blockEntity, k + j * 9, 8 + k * 18, 26 + 16 + j * 18));
			}
		}
		
		// player inventory slots
		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 112 + 19 + j * 18 + i));
			}
		}
		
		// player hotbar
		for (j = 0; j < 9; ++j) {
			this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 170 + 19 + i));
		}
		
		// experience provider slot
		this.addSlot(new StackFilterSlot(blockEntity, BlackHoleChestBlockEntity.EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT, 152, 18, SpectrumItems.KNOWLEDGE_GEM));
		
		// filter slots
		for (k = 0; k < BlackHoleChestBlockEntity.ITEM_FILTER_SLOT_COUNT; ++k) {
			this.addSlot(new BlackHoleChestFilterSlot(filterInventory, k, 8 + k * 23, 18));
		}
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return blockEntity.canPlayerUse(player);
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index < ROWS * 9) {
				if (!this.insertItem(itemStack2, ROWS * 9, this.slots.size() - 6, true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, ROWS * 9, false)) {
				return ItemStack.EMPTY;
			}
			
			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		
		return itemStack;
	}
	
	public Inventory getInventory() {
		return blockEntity;
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		blockEntity.onClose(player);
	}
	
	protected class BlackHoleChestFilterSlot extends ShadowSlot {
		
		public BlackHoleChestFilterSlot(Inventory inventory, int index, int x, int y) {
			super(inventory, index, x, y);
		}
		
		@Override
		public boolean onClicked(ItemStack heldStack, ClickType type, PlayerEntity player) {
			if (blockEntity != null) {
				blockEntity.setFilterItem(getIndex(), ItemVariant.of(heldStack));
			}
			return super.onClicked(heldStack, type, player);
		}
	}
	
}
