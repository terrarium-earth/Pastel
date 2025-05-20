package de.dafuqs.spectrum.blocks;

import de.dafuqs.spectrum.helpers.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.*;

public abstract class InWorldInteractionBlockEntity extends BlockEntity implements RandomizableContainer {
	
	private final int inventorySize;
	protected FriendlyStackHandler inventory;
	@Nullable protected ResourceKey<LootTable> lootTable;
	protected long lootTableSeed;
	
	public InWorldInteractionBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, int inventorySize) {
		super(type, pos, state);
		this.inventorySize = inventorySize;
		this.inventory = new FriendlyStackHandler(inventorySize);
	}
	
	// interaction methods
	public void updateInClientWorld() {
		if (level instanceof ServerLevel serverWorld)
			serverWorld.getChunkSource().blockChanged(worldPosition);
	}

	public FriendlyStackHandler getInventory() {
		return inventory;
	}

	// Called when the chunk is first loaded to initialize this be
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		CompoundTag nbtCompound = new CompoundTag();
		this.saveAdditional(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		this.inventory = new FriendlyStackHandler(inventorySize);
		if (!this.tryLoadLootTable(nbt)) {
			inventory.deserializeNBT(registryLookup, nbt.getCompound("inventory"));
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		if (!this.trySaveLootTable(nbt)) {
			nbt.put("inventory", inventory.serializeNBT(registryLookup));
		}
	}

	@Override
	public void unpackLootTable(@Nullable Player player) {
		if (lootTableSeed == 0 && level != null)
			lootTableSeed = level.getRandom().nextLong();
		RandomizableContainer.super.unpackLootTable(player);
		this.setChanged();
	}
	
	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	@Override
	public @Nullable ResourceKey<LootTable> getLootTable() {
		return lootTable;
	}
	
	@Override
	public void setLootTable(@Nullable ResourceKey<LootTable> lootTable) {
		this.lootTable = lootTable;
	}
	
	@Override
	public long getLootTableSeed() {
		return lootTableSeed;
	}
	
	@Override
	public void setLootTableSeed(long lootTableSeed) {
		this.lootTableSeed = lootTableSeed;
	}
	
	@Override
	protected void applyImplicitComponents(BlockEntity.DataComponentInput components) {
		super.applyImplicitComponents(components);
		SeededContainerLoot containerLootComponent = components.get(DataComponents.CONTAINER_LOOT);
		if (containerLootComponent != null) {
			this.lootTable = containerLootComponent.lootTable();
			this.lootTableSeed = containerLootComponent.seed();
		}
	}
	
	@Override
	protected void collectImplicitComponents(DataComponentMap.Builder componentMapBuilder) {
		super.collectImplicitComponents(componentMapBuilder);
		if (this.lootTable != null) {
			componentMapBuilder.set(DataComponents.CONTAINER_LOOT, new SeededContainerLoot(this.lootTable, this.lootTableSeed));
		}
	}

	@Override
	public int getContainerSize() {
		return inventorySize;
	}

	@Override
	public @NotNull ItemStack getItem(int slot) {
		return inventory.getStackInSlot(slot);
	}

	@Override
	public @NotNull ItemStack removeItem(int slot, int amount) {
		return inventory.extractItem(slot, amount, false);
	}

	@Override
	public boolean isEmpty() {
		if (inventory.getInternalList().isEmpty())
			return true;

		for (ItemStack stack : inventory.getInternalList()) {
			if (!stack.isEmpty())
				return false;
		}
		return true;
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		return inventory.removeStackInSlot(slot);
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		inventory.setStackInSlot(slot, stack);
	}

	@Override
	public boolean stillValid(Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		inventory.getInternalList().clear();
	}
}
