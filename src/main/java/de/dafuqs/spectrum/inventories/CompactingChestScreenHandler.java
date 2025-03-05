package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.chests.*;
import de.dafuqs.spectrum.networking.c2s_payloads.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.util.math.*;

public class CompactingChestScreenHandler extends ScreenHandler {
	
	private final PropertyDelegate propertyDelegate;
	private final CompactingChestBlockEntity blockEntity;
	protected final int ROWS = 3;
	
	public CompactingChestScreenHandler(int syncId, PlayerInventory playerInventory, BlockPos pos) {
		this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(pos, SpectrumBlockEntities.COMPACTING_CHEST).orElseThrow(), new ArrayPropertyDelegate(1));
	}
	
	public CompactingChestScreenHandler(int syncId, PlayerInventory playerInventory, CompactingChestBlockEntity blockEntity, PropertyDelegate propertyDelegate) {
		super(SpectrumScreenHandlerTypes.COMPACTING_CHEST, syncId);
		
		this.blockEntity = blockEntity;
		this.propertyDelegate = propertyDelegate;
		
		checkSize(blockEntity, 27);
		blockEntity.onOpen(playerInventory.player);
		
		int i = (ROWS - 4) * 18;
		
		int j;
		int k;
		for (j = 0; j < ROWS; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlot(new Slot(blockEntity, k + j * 9, 8 + k * 18, 26 + j * 18));
			}
		}
		
		for (j = 0; j < 3; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 112 + j * 18 + i));
			}
		}
		
		for (j = 0; j < 9; ++j) {
			this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 170 + i));
		}
		
		this.addProperties(propertyDelegate);
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.blockEntity.canPlayerUse(player);
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index < this.ROWS * 9) {
				if (!this.insertItem(itemStack2, this.ROWS * 9, this.slots.size(), true)) {
					if (blockEntity instanceof CompactingChestBlockEntity compactor) {
						compactor.inventoryChanged();
					}
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, this.ROWS * 9, false)) {
				if (blockEntity instanceof CompactingChestBlockEntity compactor) {
					compactor.inventoryChanged();
				}
				return ItemStack.EMPTY;
			}
			
			if (itemStack2.isEmpty()) {
				slot.setStack(ItemStack.EMPTY);
			} else {
				slot.markDirty();
			}
		}
		
		if (blockEntity instanceof CompactingChestBlockEntity compactor) {
			compactor.inventoryChanged();
		}
		return itemStack;
	}
	
	public void toggleMode() {
		this.propertyDelegate.set(0, getCraftingMode().next().ordinal());
		sendContentUpdates();
	}
	
	@Override
	public void sendContentUpdates() {
		super.sendContentUpdates();
		ClientPlayNetworking.send(new ChangeCompactingChestSettingsPayload(getCraftingMode()));
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.blockEntity.onClose(player);
	}
	
	public Inventory getInventory() {
		return blockEntity;
	}
	
	public CompactingChestBlockEntity getBlockEntity() {
		return blockEntity;
	}
	
	public AutoCraftingMode getCraftingMode() {
		return AutoCraftingMode.values()[propertyDelegate.get(0)];
	}
	
}
