package de.dafuqs.spectrum.inventories;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.energy.*;
import de.dafuqs.spectrum.inventories.slots.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.registry.entry.*;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.*;
import net.minecraft.server.network.*;
import net.minecraft.world.*;

import java.util.*;

public class ColorPickerScreenHandler extends ScreenHandler implements InkColorSelectedPacketReceiver {
	
	public static final int PLAYER_INVENTORY_START_X = 8;
	public static final int PLAYER_INVENTORY_START_Y = 84;
	
	protected final World world;
	public final ServerPlayerEntity player;
	protected ColorPickerBlockEntity blockEntity;
	
	@Override
	public void sendContentUpdates() {
		super.sendContentUpdates();
		
		if (this.player != null && this.blockEntity.getInkDirty()) {
			UpdateBlockEntityInkPayload.updateBlockEntityInk(blockEntity.getPos(), blockEntity.getEnergyStorage(), player);
		}
	}
	
	public ColorPickerScreenHandler(int syncId, PlayerInventory playerInventory, ColorPickerBlockEntity.ColorPickerScreenData data) {
		this(syncId, playerInventory, playerInventory.player.getWorld().getBlockEntity(data.pos(), SpectrumBlockEntities.COLOR_PICKER).orElseThrow(), data.inkColor());
	}
	
	public ColorPickerScreenHandler(int syncId, PlayerInventory playerInventory, ColorPickerBlockEntity blockEntity, Optional<RegistryEntry<InkColor>> selectedColor) {
		super(SpectrumScreenHandlerTypes.COLOR_PICKER, syncId);
		
		this.player = playerInventory.player instanceof ServerPlayerEntity serverPlayerEntity ? serverPlayerEntity : null;
		this.world = playerInventory.player.getWorld();
		this.blockEntity = blockEntity;
		
		this.blockEntity.setSelectedColor(selectedColor);
		
		checkSize(blockEntity, ColorPickerBlockEntity.INVENTORY_SIZE);
		blockEntity.onOpen(playerInventory.player);
		
		// color picker slots
		this.addSlot(new ColorPickerInputSlot(blockEntity, 0, 26, 33));
		this.addSlot(new InkStorageSlot(blockEntity, 1, 133, 33));
		
		// player inventory
		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlot(new Slot(playerInventory, k + j * 9 + 9, PLAYER_INVENTORY_START_X + k * 18, PLAYER_INVENTORY_START_Y + j * 18));
			}
		}
		
		// player hotbar
		for (int j = 0; j < 9; ++j) {
			this.addSlot(new Slot(playerInventory, j, PLAYER_INVENTORY_START_X + j * 18, PLAYER_INVENTORY_START_Y + 58));
		}
		
		if (this.player != null) {
			UpdateBlockEntityInkPayload.updateBlockEntityInk(blockEntity.getPos(), this.blockEntity.getEnergyStorage(), player);
		}
	}
	
	@Override
	public ColorPickerBlockEntity getBlockEntity() {
		return this.blockEntity;
	}
	
	@Override
	public boolean canUse(PlayerEntity player) {
		return this.blockEntity.canPlayerUse(player);
	}
	
	@Override
	public void onClosed(PlayerEntity player) {
		super.onClosed(player);
		this.blockEntity.onClose(player);
	}
	
	@Override
	public ItemStack quickMove(PlayerEntity player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasStack()) {
			ItemStack itemStack2 = slot.getStack();
			itemStack = itemStack2.copy();
			if (index < ColorPickerBlockEntity.INVENTORY_SIZE) {
				if (!this.insertItem(itemStack2, ColorPickerBlockEntity.INVENTORY_SIZE, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.insertItem(itemStack2, 0, ColorPickerBlockEntity.INVENTORY_SIZE, false)) {
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
	
	@Override
	public void onInkColorSelectedPacket(Optional<RegistryEntry<InkColor>> inkColor) {
		this.blockEntity.setSelectedColor(inkColor);
	}
	
}
