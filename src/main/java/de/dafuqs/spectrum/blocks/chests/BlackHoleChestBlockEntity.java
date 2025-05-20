package de.dafuqs.spectrum.blocks.chests;

import de.dafuqs.spectrum.api.block.FilterConfigurable;
import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import de.dafuqs.spectrum.events.SpectrumGameEvents;
import de.dafuqs.spectrum.events.listeners.EventQueue;
import de.dafuqs.spectrum.events.listeners.ExperienceOrbEventQueue;
import de.dafuqs.spectrum.events.listeners.ItemAndExperienceEventQueue;
import de.dafuqs.spectrum.events.listeners.ItemEntityEventQueue;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.BlackHoleChestScreenHandler;
import de.dafuqs.spectrum.mixin.accessors.ItemEntityAccessor;
import de.dafuqs.spectrum.networking.s2c_payloads.BlackHoleChestStatusUpdatePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithExactVelocityPayload;
import de.dafuqs.spectrum.particle.SpectrumParticleTypes;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.minecraft.core.*;
import net.minecraft.network.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.BlockPositionSource;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;

public class BlackHoleChestBlockEntity extends SpectrumChestBlockEntity implements FilterConfigurable, WorldlyContainer, EventQueue.Callback<Object> {
	
	public static final int INVENTORY_SIZE = 28;
	public static final int ITEM_FILTER_SLOT_COUNT = 5;
	public static final int EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT = 27;
	private static final int RANGE = 12;
	private final ItemAndExperienceEventQueue itemAndExperienceEventQueue;
	private final FriendlyStackHandler filterItems;
	private State state = State.CLOSED_INACTIVE;
	private boolean isOpen, isFull, hasXPStorage;
	float storageTarget, storagePos, lastStorageTarget, capTarget, capPos, lastCapTarget, orbTarget, orbPos, lastOrbTarget, yawTarget, orbYaw, lastYawTarget;
	long interpTicks, interpLength = 1, age, storedXP, maxStoredXP;
	
	public BlackHoleChestBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.BLACK_HOLE_CHEST, blockPos, blockState);
		this.itemAndExperienceEventQueue = new ItemAndExperienceEventQueue(new BlockPositionSource(this.worldPosition), RANGE, this);
		this.filterItems = new FriendlyStackHandler(ITEM_FILTER_SLOT_COUNT);
	}
	
	@SuppressWarnings("unused")
	public static void tick(@NotNull Level world, BlockPos pos, BlockState state, BlackHoleChestBlockEntity chest) {
		chest.age++;
		
		if (chest.isOpen) {
			if (chest.canFunction()) {
				chest.changeState(State.OPEN_ACTIVE);
				chest.interpLength = 7;
			} else {
				chest.changeState(State.OPEN_INACTIVE);
				chest.interpLength = 5;
			}
		} else {
			if (chest.isFull) {
				chest.changeState(State.FULL);
				chest.interpLength = 12;
			} else if (chest.canFunction()) {
				chest.changeState(State.CLOSED_ACTIVE);
				chest.interpLength = 15;
			} else {
				chest.changeState(State.CLOSED_INACTIVE);
				chest.interpLength = 10;
			}
		}
		
		if (chest.interpTicks < chest.interpLength) {
			chest.interpTicks++;
		}
		
		if (world.isClientSide) {
			chest.lidAnimator.tickLid();
		} else {
			chest.itemAndExperienceEventQueue.tick(world);
			if (world.getGameTime() % 80 == 0 && !SpectrumChestBlock.isChestBlocked(world, pos)) {
				searchForNearbyEntities(chest);
			}
		}
	}
	
	public long getRenderTime() {
		return age % 50000;
	}
	
	public void changeState(State state) {
		if (this.state != state) {
			this.state = state;
			lastCapTarget = capPos;
			lastOrbTarget = orbPos;
			lastStorageTarget = storagePos;
			lastYawTarget = orbYaw;
			interpTicks = 0;
		}
	}
	
	public void updateFullState(boolean force) {
		if (level != null && !level.isClientSide()) {
			var wasFull = isFull;
			isFull = isFull();
			if (force || wasFull != isFull) {
				BlackHoleChestStatusUpdatePayload.sendBlackHoleChestUpdate(this);
			}
		}
	}
	
	public void setXPData(long xp, long max) {
		this.storedXP = xp;
		this.maxStoredXP = max;
	}
	
	public State getState() {
		return state;
	}
	
	public boolean canFunction() {
		return level != null && !SpectrumChestBlock.isChestBlocked(level, this.worldPosition) && !isFull;
	}
	
	public boolean isFull() {
		for (int i = 0; i < inventory.getSlots() - 1; i++) {
			var stack = inventory.getStackInSlot(i);
			if (stack.getCount() < stack.getMaxStackSize()) {
				return false;
			}
		}
		
		if (canStoreExperience() && level != null) {
			var experienceStack = inventory.getStackInSlot(EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT);
			var experienceStorage = (ExperienceStorageItem) experienceStack.getItem();
			return ExperienceStorageItem.getStoredExperience(experienceStack) >= experienceStorage.getMaxStoredExperience(level.registryAccess(), experienceStack);
		}
		
		return true;
	}
	
	public boolean canStoreExperience() {
		return inventory.getStackInSlot(EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT).getItem() instanceof ExperienceStorageItem;
	}
	
	public boolean isFullServer() {
		return isFull;
	}
	
	public void setFull(boolean full) {
		isFull = full;
	}
	
	public void setHasXPStorage(boolean hasXPStorage) {
		this.hasXPStorage = hasXPStorage;
	}
	
	public boolean hasXPStorage() {
		return hasXPStorage;
	}
	
	private static void searchForNearbyEntities(@NotNull BlackHoleChestBlockEntity blockEntity) {
		var world = blockEntity.getLevel();
		if (world == null)
			return;
		
		List<ItemEntity> itemEntities = world.getEntities(EntityType.ITEM, getBoxWithRadius(blockEntity.worldPosition, RANGE), Entity::isAlive);
		for (ItemEntity itemEntity : itemEntities) {
			if (itemEntity.isAlive() && !itemEntity.getItem().isEmpty()) {
				itemEntity.gameEvent(SpectrumGameEvents.ENTITY_SPAWNED);
			}
		}
		
		List<ExperienceOrb> experienceOrbEntities = world.getEntities(EntityType.EXPERIENCE_ORB, getBoxWithRadius(blockEntity.worldPosition, RANGE), Entity::isAlive);
		for (ExperienceOrb experienceOrbEntity : experienceOrbEntities) {
			if (experienceOrbEntity.isAlive()) {
				experienceOrbEntity.gameEvent(SpectrumGameEvents.ENTITY_SPAWNED);
			}
		}
	}
	
	@Contract("_, _ -> new")
	protected static @NotNull AABB getBoxWithRadius(BlockPos blockPos, int radius) {
		return AABB.ofSize(Vec3.atCenterOf(blockPos), radius, radius, radius);
	}
	
	@Override
	public boolean triggerEvent(int type, int data) {
		if (type == 1) {
			isOpen = data > 0;
		}
		return super.triggerEvent(type, data);
	}
	
	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.spectrum.black_hole_chest");
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return new BlackHoleChestScreenHandler(syncId, playerInventory, this, new ExtendedData(this));
	}
	
	@Override
	protected void onInvOpenOrClose(Level world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
		super.onInvOpenOrClose(world, pos, state, oldViewerCount, newViewerCount);
		updateFullState(true);
	}
	
	@Override
	public void saveAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.saveAdditional(tag, registryLookup);
		FilterConfigurable.writeFilterNbt(tag, filterItems, registryLookup);
		tag.putLong("age", age);
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.loadAdditional(tag, registryLookup);
		FilterConfigurable.readFilterNbt(tag, filterItems, registryLookup);
		age = tag.getLong("age");
	}
	
	@Override
	public int getContainerSize() {
		return 27 + 1; // 3 rows, 1 knowledge gem, 5 item filters (they are not real slots, though)
	}
	
	public ItemAndExperienceEventQueue getEventListener() {
		return this.itemAndExperienceEventQueue;
	}
	
	@Override
	public boolean canAcceptEvent(Level world, GameEventListener listener, GameEvent.ListenerInfo event, Vec3 sourcePos) {
		if (SpectrumChestBlock.isChestBlocked(world, this.worldPosition)) {
			return false;
		}
		Entity entity = event.context().sourceEntity();
		if (entity instanceof ItemEntity) {
			return true;
		}
		return entity instanceof ExperienceOrb && hasExperienceStorageItem();
	}
	
	@Override
	public void triggerEvent(Level world, GameEventListener listener, Object entry) {
		if (SpectrumChestBlock.isChestBlocked(world, worldPosition)) {
			return;
		}
		
		if (entry instanceof ExperienceOrbEventQueue.EventEntry experienceEntry) {
			ExperienceOrb experienceOrbEntity = experienceEntry.experienceOrbEntity;
			if (experienceOrbEntity != null && experienceOrbEntity.isAlive() && hasExperienceStorageItem()) {
				ExperienceStorageItem.addStoredExperience(world.registryAccess(), this.inventory.getStackInSlot(EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT), experienceOrbEntity.getValue()); // overflow experience is void, to not lag the world on large farms
				
				sendPlayExperienceOrbEntityAbsorbedParticle((ServerLevel) world, experienceOrbEntity);
				world.playSound(null, experienceOrbEntity.blockPosition(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.9F + world.random.nextFloat() * 0.2F, 0.9F + world.random.nextFloat() * 0.2F);
				experienceOrbEntity.remove(Entity.RemovalReason.DISCARDED);
			}
		} else if (entry instanceof ItemEntityEventQueue.EventEntry itemEntry) {
			ItemEntity itemEntity = itemEntry.itemEntity;
			if (itemEntity != null && itemEntity.isAlive() && ((ItemEntityAccessor) itemEntity).getPickupDelay() != 32767 && acceptsItemStack(itemEntity.getItem())) {
				int previousAmount = itemEntity.getItem().getCount();
				ItemStack remainingStack = InventoryHelper.smartAddToInventory(itemEntity.getItem(), this.inventory, Direction.UP);
				
				if (remainingStack.isEmpty()) {
					sendPlayItemEntityAbsorbedParticle((ServerLevel) world, itemEntity);
					world.playSound(null, itemEntity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.9F + world.random.nextFloat() * 0.2F, 0.9F + world.random.nextFloat() * 0.2F);
					itemEntity.setItem(ItemStack.EMPTY);
					itemEntity.discard();
				} else if (remainingStack.getCount() != previousAmount) {
					sendPlayItemEntityAbsorbedParticle((ServerLevel) world, itemEntity);
					world.playSound(null, itemEntity.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.9F + world.random.nextFloat() * 0.2F, 0.9F + world.random.nextFloat() * 0.2F);
					itemEntity.setItem(remainingStack);
				}
			}
		}
	}
	
	public static void sendPlayItemEntityAbsorbedParticle(ServerLevel world, @NotNull ItemEntity itemEntity) {
		PlayParticleWithExactVelocityPayload.playParticleWithExactVelocity(world, itemEntity.position(),
				SpectrumParticleTypes.BLUE_BUBBLE_POP,
				1, Vec3.ZERO);
	}
	
	public static void sendPlayExperienceOrbEntityAbsorbedParticle(ServerLevel world, @NotNull ExperienceOrb experienceOrbEntity) {
		PlayParticleWithExactVelocityPayload.playParticleWithExactVelocity(world, experienceOrbEntity.position(),
				SpectrumParticleTypes.GREEN_BUBBLE_POP,
				1, Vec3.ZERO);
	}
	
	@Override
	public SoundEvent getOpenSound() {
		return SpectrumSoundEvents.BLACK_HOLE_CHEST_OPEN;
	}
	
	@Override
	public SoundEvent getCloseSound() {
		return SpectrumSoundEvents.BLACK_HOLE_CHEST_CLOSE;
	}

	@Override
	public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
		ExtendedDataWithPos.STREAM_CODEC.encode(buffer, new ExtendedDataWithPos(worldPosition, this));
	}
	
	@Override
	public FriendlyStackHandler getItemFilters() {
		return this.filterItems;
	}
	
	@Override
	public int getSlotsPerRow() {
		return ITEM_FILTER_SLOT_COUNT;
	}
	
	@Override
	public int getDrawnSlots() {
		return ITEM_FILTER_SLOT_COUNT;
	}

	public void setFilterItem(int slot, ItemStack item) {
		this.filterItems.setStackInSlot(slot, item);
		this.setChanged();
	}
	
	public boolean acceptsItemStack(ItemStack itemStack) {
		if (itemStack.isEmpty()) {
			return false;
		}
		
		boolean allAir = true;
		for (int i = 0; i < ITEM_FILTER_SLOT_COUNT; i++) {
			ItemStack filterItem = this.filterItems.getStackInSlot(i);
			if (itemStack.is(filterItem.getItem())) {
				return true;
			} else if (!filterItem.isEmpty()) {
				allAir = false;
			}
		}
		return allAir;
	}

	public boolean hasExperienceStorageItem() {
		return this.inventory.getStackInSlot(EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT).getItem() instanceof ExperienceStorageItem;
	}
	
	@Override
	public int[] getSlotsForFace(Direction side) {
		return IntStream.rangeClosed(0, EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT - 1).toArray();
	}
	
	@Override
	public boolean canPlaceItemThroughFace(int slot, ItemStack stack, @Nullable Direction dir) {
		return true;
	}
	
	@Override
	public boolean canTakeItemThroughFace(int slot, ItemStack stack, Direction dir) {
		return true;
	}
	
	@Override
	public void setItem(int slot, ItemStack stack) {
		super.setItem(slot, stack);
		updateFullState(false);
	}
	
	@Override
	public ItemStack removeItem(int slot, int amount) {
		var stack = super.removeItem(slot, amount);
		if (!stack.isEmpty())
			updateFullState(false);
		return stack;
	}
	
	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		var stack = super.removeItemNoUpdate(slot);
		if (!stack.isEmpty())
			updateFullState(false);
		return stack;
	}
	
	public enum State {
		OPEN_INACTIVE,
		OPEN_ACTIVE,
		CLOSED_ACTIVE,
		CLOSED_INACTIVE,
		FULL
	}
	
}
