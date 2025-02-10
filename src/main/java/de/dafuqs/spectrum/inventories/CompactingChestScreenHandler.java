package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.blocks.*;
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
	
	private final Inventory inventory;
	private final PropertyDelegate propertyDelegate;
	protected final int ROWS = 3;
	protected CompactingChestBlockEntity compactingChestBlockEntity;
	
	public CompactingChestScreenHandler(int syncId, PlayerInventory playerInventory) {
		this(syncId, playerInventory, new SimpleInventory(27), new ArrayPropertyDelegate(4));
	}
	
	public CompactingChestScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
		super(SpectrumScreenHandlerTypes.COMPACTING_CHEST, syncId);
		
		this.inventory = inventory;
		this.propertyDelegate = propertyDelegate;
		
		this.compactingChestBlockEntity = playerInventory.player.getWorld()
				.getBlockEntity(getBlockPos(), SpectrumBlockEntities.COMPACTING_CHEST)
				.orElse(null);
		
		checkSize(inventory, 27);
		inventory.onOpen(playerInventory.player);
		
		int i = (ROWS - 4) * 18;
		
		int j;
		int k;
		for (j = 0; j < ROWS; ++j) {
			for (k = 0; k < 9; ++k) {
				this.addSlot(new Slot(inventory, k + j * 9, 8 + k * 18, 26 + j * 18));
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
		return this.inventory.canPlayerUse(player);
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
					if (inventory instanceof CompactingChestBlockEntity compactor) {
						compactor.inventoryChanged();
					}
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, this.ROWS * 9, false)) {
				if (inventory instanceof CompactingChestBlockEntity compactor) {
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
		
		if (inventory instanceof CompactingChestBlockEntity compactor) {
			compactor.inventoryChanged();
		}
		return itemStack;
	}
	
	public void toggleMode() {
		this.propertyDelegate.set(3, getCraftingMode().next().ordinal());
		sendContentUpdates();
	}
	
	@Override
	public void sendContentUpdates() {
		super.sendContentUpdates();
		ClientPlayNetworking.send(new ChangeCompactingChestSettingsPayload(getCraftingMode()));
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.inventory.onClose(player);
	}
	
	public BlockPos getBlockPos() {
		return BlockPosDelegate.getBlockPos(propertyDelegate);
	}
	
	public CompactingChestBlockEntity getBlockEntity() {
		return this.compactingChestBlockEntity;
	}
	
	public AutoCraftingMode getCraftingMode() {
		return AutoCraftingMode.values()[propertyDelegate.get(3)];
	}
	
}
