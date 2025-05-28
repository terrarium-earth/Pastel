package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.api.block.InkColorSelectedPacketReceiver;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.blocks.energy.ColorPickerBlockEntity;
import earth.terrarium.pastel.inventories.slots.ColorPickerInputSlot;
import earth.terrarium.pastel.inventories.slots.InkStorageSlot;
import earth.terrarium.pastel.networking.s2c_payloads.UpdateBlockEntityInkPayload;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import earth.terrarium.pastel.registries.SpectrumRegistryKeys;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ColorPickerScreenHandler extends AbstractContainerMenu implements InkColorSelectedPacketReceiver {
	
	public record ScreenOpeningData(BlockPos pos, Optional<Holder<InkColor>> inkColor) {
		public static final StreamCodec<RegistryFriendlyByteBuf, ScreenOpeningData> STREAM_CODEC = StreamCodec.composite(
				BlockPos.STREAM_CODEC, ScreenOpeningData::pos,
				ByteBufCodecs.optional(ByteBufCodecs.holderRegistry(SpectrumRegistryKeys.INK_COLOR)), ScreenOpeningData::inkColor,
				ScreenOpeningData::new
		);
	}
	
	public static final int PLAYER_INVENTORY_START_X = 8;
	public static final int PLAYER_INVENTORY_START_Y = 84;
	
	protected final Level world;
	public final ServerPlayer player;
	protected ColorPickerBlockEntity blockEntity;
	
	@Override
	public void broadcastChanges() {
		super.broadcastChanges();
		
		if (this.player != null && this.blockEntity.getInkDirty()) {
			UpdateBlockEntityInkPayload.updateBlockEntityInk(blockEntity.getBlockPos(), blockEntity.getEnergyStorage(), player);
		}
	}

	public ColorPickerScreenHandler(int syncId, Inventory playerInventory, RegistryFriendlyByteBuf buf) {
		this(syncId, playerInventory, ScreenOpeningData.STREAM_CODEC.decode(buf));
	}

	public ColorPickerScreenHandler(int syncId, Inventory playerInventory, ScreenOpeningData data) {
		this(syncId, playerInventory, playerInventory.player.level().getBlockEntity(data.pos(), SpectrumBlockEntities.COLOR_PICKER).orElseThrow(), data.inkColor());
	}
	
	public ColorPickerScreenHandler(int syncId, Inventory playerInventory, ColorPickerBlockEntity blockEntity, Optional<Holder<InkColor>> selectedColor) {
		super(SpectrumScreenHandlerTypes.COLOR_PICKER, syncId);
		
		this.player = playerInventory.player instanceof ServerPlayer serverPlayerEntity ? serverPlayerEntity : null;
		this.world = playerInventory.player.level();
		this.blockEntity = blockEntity;
		
		this.blockEntity.setSelectedColor(selectedColor);
		
		checkContainerSize(blockEntity, ColorPickerBlockEntity.INVENTORY_SIZE);
		blockEntity.startOpen(playerInventory.player);
		
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
			UpdateBlockEntityInkPayload.updateBlockEntityInk(blockEntity.getBlockPos(), this.blockEntity.getEnergyStorage(), player);
		}
	}
	
	@Override
	public ColorPickerBlockEntity getBlockEntity() {
		return this.blockEntity;
	}
	
	@Override
	public boolean stillValid(Player player) {
		return this.blockEntity.stillValid(player);
	}
	
	@Override
	public void removed(Player player) {
		super.removed(player);
		this.blockEntity.stopOpen(player);
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if (slot.hasItem()) {
			ItemStack itemStack2 = slot.getItem();
			itemStack = itemStack2.copy();
			if (index < ColorPickerBlockEntity.INVENTORY_SIZE) {
				if (!this.moveItemStackTo(itemStack2, ColorPickerBlockEntity.INVENTORY_SIZE, this.slots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!this.moveItemStackTo(itemStack2, 0, ColorPickerBlockEntity.INVENTORY_SIZE, false)) {
				return ItemStack.EMPTY;
			}
			
			if (itemStack2.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
		}
		
		return itemStack;
	}
	
	@Override
	public void onInkColorSelectedPacket(Optional<Holder<InkColor>> inkColor) {
		this.blockEntity.setSelectedColor(inkColor);
	}
	
}
