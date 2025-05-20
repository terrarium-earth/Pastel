package de.dafuqs.spectrum.blocks.chests;

import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.AutoCraftingMode;
import de.dafuqs.spectrum.inventories.CompactingChestScreenHandler;
import de.dafuqs.spectrum.networking.c2s_payloads.ChangeCompactingChestSettingsPayload;
import de.dafuqs.spectrum.networking.s2c_payloads.CompactingChestStatusUpdatePayload;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CompactingChestBlockEntity extends SpectrumChestBlockEntity implements ExtendedScreenHandlerFactory<BlockPos> {
	
	private static final Map<AutoCraftingMode, Map<ItemStack, Optional<RecipeHolder<CraftingRecipe>>>> cache = new EnumMap<>(AutoCraftingMode.class);
	private AutoCraftingMode autoCraftingMode;
	private RecipeHolder<CraftingRecipe> lastCraftingRecipe; // cache
	private ItemStack lastItemVariant; // cache
	private boolean hasToCraft, isOpen;
	private State state = State.CLOSED;
	float pistonPos, pistonTarget, lastPistonTarget, driverPos, driverTarget, lastDriverTarget, capPos, capTarget, lastCapTarget;
	long interpTicks, interpLength = 1, activeTicks, craftingTicks;
	
	private final ContainerData propertyDelegate = new ContainerData() {
		@Override
		public int get(int index) {
			if (index == 0) return autoCraftingMode.ordinal();
			return 0;
		}
		
		@Override
		public void set(int index, int value) {
			if (index == 0) autoCraftingMode = AutoCraftingMode.values()[value];
		}
		
		@Override
		public int getCount() {
			return 1;
		}
	};
	
	public CompactingChestBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.COMPACTING_CHEST, blockPos, blockState);
		this.autoCraftingMode = AutoCraftingMode.ThreeXThree;
		this.lastItemVariant = null;
		this.lastCraftingRecipe = null;
		this.hasToCraft = false;
	}
	
	@SuppressWarnings("unused")
	public static void tick(Level world, BlockPos pos, BlockState state, CompactingChestBlockEntity chest) {
		if (!world.isClientSide()) {
			CompactingChestStatusUpdatePayload.sendCompactingChestStatusUpdate(chest);
		}
		
		if (world.isClientSide()) {
			if (chest.hasToCraft()) {
				chest.craftingTicks = 20;
			} else {
				chest.craftingTicks--;
			}
			
			if (chest.craftingTicks >= 0) {
				chest.activeTicks++;
			} else {
				chest.activeTicks = 0;
			}
			
			if (chest.isOpen()) {
				chest.changeState(State.OPEN);
				chest.interpLength = 5;
			} else if (chest.craftingTicks >= 0) {
				chest.changeState(State.CRAFTING);
				chest.interpLength = 20;
			} else {
				chest.changeState(State.CLOSED);
				chest.interpLength = 15;
			}
			if (chest.interpTicks < chest.interpLength) {
				chest.interpTicks++;
			}
		} else {
			if (chest.hasToCraft) {
				boolean couldCraft = chest.tryCraftOnce();
				if (!couldCraft) {
					chest.shouldCraft(false);
				}
				if (world.getGameTime() % 6 == 0) {
					chest.produceRunningEffects();
				}
			}
		}
	}
	
	public void produceRunningEffects() {
		if (level instanceof ServerLevel server) {
			var random = level.getRandom();
			if (random.nextFloat() < 0.125F) {
				server.playSound(null, worldPosition, SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 0.05F + random.nextFloat() * 0.1F, 0.334F + random.nextFloat() / 2F);
				for (int i = 0; i < 4 + random.nextInt(5); i++) {
					server.sendParticles(ParticleTypes.CLOUD, worldPosition.getX() + random.nextFloat(), worldPosition.getY() + 0.975 + random.nextFloat() * 0.667F, worldPosition.getZ() + random.nextFloat(), 0, 0, random.nextFloat() / 20F + 0.02F, 0, 1);
				}
			}
		}
	}
	
	public void changeState(State state) {
		if (this.state != state) {
			this.state = state;
			lastPistonTarget = pistonPos;
			lastDriverTarget = driverPos;
			lastCapTarget = capPos;
			interpTicks = 0;
		}
	}
	
	public State getState() {
		return state;
	}
	
	private static boolean smartAddToInventory(List<ItemStack> itemStacks, FriendlyStackHandler inventory, boolean test) {
		List<ItemStack> additionStacks = new ArrayList<>();
		for (ItemStack itemStack : itemStacks) {
			additionStacks.add(itemStack.copy());
		}
		
		boolean tryStackExisting = true;
		for (int i = 0; i < inventory.getSlots(); i++) {
			ItemStack currentStack = inventory.getStackInSlot(i);
			for (ItemStack additionStack : additionStacks) {
				boolean doneStuff = false;
				if (additionStack.getCount() > 0) {
					if (currentStack.isEmpty() && (test || !tryStackExisting)) {
						int maxStackCount = currentStack.getMaxStackSize();
						int maxAcceptCount = Math.min(additionStack.getCount(), maxStackCount);
						
						if (!test) {
							ItemStack newStack = additionStack.copy();
							newStack.setCount(maxAcceptCount);
							inventory.setStackInSlot(i, newStack);
						}
						additionStack.setCount(additionStack.getCount() - maxAcceptCount);
						doneStuff = true;
					} else if (ItemStack.isSameItemSameComponents(currentStack, additionStack)) {
						// add to stack;
						int maxStackCount = currentStack.getMaxStackSize();
						int canAcceptCount = maxStackCount - currentStack.getCount();
						
						if (canAcceptCount > 0) {
							if (!test) {
								inventory.getStackInSlot(i).grow(Math.min(additionStack.getCount(), canAcceptCount));
							}
							if (canAcceptCount >= additionStack.getCount()) {
								additionStack.setCount(0);
							} else {
								additionStack.setCount(additionStack.getCount() - canAcceptCount);
							}
							doneStuff = true;
						}
					}
					
					// if there were changes: check if all stacks have count 0
					if (doneStuff) {
						boolean allEmpty = true;
						for (ItemStack itemStack : additionStacks) {
							if (itemStack.getCount() > 0) {
								allEmpty = false;
								break;
							}
						}
						if (allEmpty) {
							return true;
						}
					}
				}
			}
			
			if (tryStackExisting && !test && i == inventory.getSlots() - 1) {
				tryStackExisting = false;
				i = -1;
			}
		}
		return false;
	}
	
	public static void clearCache() {
		cache.clear();
	}
	
	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.spectrum.compacting_chest");
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.loadAdditional(tag, registryLookup);
		if (tag.contains("AutoCraftingMode", Tag.TAG_ANY_NUMERIC)) {
			int autoCraftingModeInt = tag.getInt("AutoCraftingMode");
			this.autoCraftingMode = AutoCraftingMode.values()[autoCraftingModeInt];
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.saveAdditional(tag, registryLookup);
		tag.putInt("AutoCraftingMode", this.autoCraftingMode.ordinal());
	}
	
	@Override
	public int getContainerSize() {
		return 27;
	}
	
	@Override
	public void setItem(int slot, ItemStack stack) {
		super.setItem(slot, stack);
		shouldCraft(true);
	}
	
	public void inventoryChanged() {
		shouldCraft(true);
	}
	
	private boolean tryCraftOnce() {
		Optional<RecipeHolder<CraftingRecipe>> optionalCraftingRecipe = Optional.empty();
		
		// try last recipe
		if (lastCraftingRecipe != null) {
			int requiredItemCount = this.autoCraftingMode.getSize();
			if (InventoryHelper.isItemCountInInventory(inventory, lastItemVariant, requiredItemCount)) {
				optionalCraftingRecipe = Optional.ofNullable(lastCraftingRecipe);
			} else {
				lastCraftingRecipe = null;
				lastItemVariant = null;
			}
		}
		// search for other recipes
		if (optionalCraftingRecipe.isEmpty()) {
			optionalCraftingRecipe = searchRecipeToCraft();
		}
		
		if (optionalCraftingRecipe.isPresent() && this.lastItemVariant != null) {
			if (tryCraftInInventory(optionalCraftingRecipe.get(), this.lastItemVariant)) {
				this.lastCraftingRecipe = optionalCraftingRecipe.get();
				return true;
			}
		}
		return false;
	}
	
	public boolean isOpen() {
		return isOpen;
	}
	
	public void shouldCraft(boolean hasToCraft) {
		this.hasToCraft = hasToCraft;
	}
	
	@Override
	public boolean triggerEvent(int type, int data) {
		if (type == 1) {
			isOpen = data > 0;
		}
		return super.triggerEvent(type, data);
	}
	
	public boolean hasToCraft() {
		return hasToCraft;
	}
	
	public Optional<RecipeHolder<CraftingRecipe>> searchRecipeToCraft() {
		if (level == null)
			return Optional.empty();
		
		for (ItemStack itemStack : inventory.getInternalList()) {
			if (itemStack.isEmpty()) {
				continue;
			}
			
			int requiredItemCount = this.autoCraftingMode.getSize();
			Tuple<Integer, List<ItemStack>> stackPair = InventoryHelper.getStackCountInInventory(itemStack, inventory, requiredItemCount);
			if (stackPair.getA() >= requiredItemCount) {
				var currentCache = cache.computeIfAbsent(autoCraftingMode, mode -> new HashMap<>());
				var recipe = currentCache.get(itemStack);
				if (recipe != null) {
					if (recipe.isEmpty()) {
						continue;
					}
					this.lastItemVariant = itemStack;
					return recipe;
				}
				
				CraftingInput input = this.autoCraftingMode.createRecipeInput(itemStack).input();
				var optionalCraftingRecipe = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);
				if (optionalCraftingRecipe.isEmpty() || optionalCraftingRecipe.get().value().assemble(input, level.registryAccess()).isEmpty()) {
					optionalCraftingRecipe = Optional.empty();
					currentCache.put(itemStack, optionalCraftingRecipe);
				} else {
					currentCache.put(itemStack, optionalCraftingRecipe);
					
					this.lastItemVariant = itemStack;
					return optionalCraftingRecipe;
				}
			}
		}
		
		return Optional.empty();
	}

	public boolean tryCraftInInventory(RecipeHolder<CraftingRecipe> craftingRecipe, ItemStack itemStack) {
		if (level == null)
			return false;

        List<ItemStack> remainders = InventoryHelper.removeFromInventoryWithRemainders(itemStack, this);
		
		boolean spaceInInventory;
		
		List<ItemStack> additionItemStacks = new ArrayList<>();
		additionItemStacks.add(craftingRecipe.value().getResultItem(level.registryAccess()));
		additionItemStacks.addAll(remainders);
		
		spaceInInventory = smartAddToInventory(additionItemStacks, inventory, true);
		if (spaceInInventory) {
			// craft
			smartAddToInventory(additionItemStacks, inventory, false);
			
			// cache
			return true;
		} else {
			smartAddToInventory(List.of(itemStack), inventory, false);
			return false;
		}
	}
	
	@Override
	public SoundEvent getOpenSound() {
		return SpectrumSoundEvents.COMPACTING_CHEST_OPEN;
	}
	
	@Override
	public SoundEvent getCloseSound() {
		return SpectrumSoundEvents.COMPACTING_CHEST_CLOSE;
	}
	
	public void applySettings(ChangeCompactingChestSettingsPayload packet) {
		this.autoCraftingMode = packet.mode();
		this.lastCraftingRecipe = null;
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return new CompactingChestScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}
	
	@Override
	public BlockPos getScreenOpeningData(ServerPlayer serverPlayerEntity) {
		return worldPosition;
	}
	
	public enum State {
		OPEN,
		CRAFTING,
		CLOSED
	}
	
}
