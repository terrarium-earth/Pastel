package de.dafuqs.spectrum.blocks.chests;

import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.c2s_payloads.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.minecraft.block.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.particle.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.screen.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;

import java.util.*;

public class CompactingChestBlockEntity extends SpectrumChestBlockEntity {
	
	private static final Map<AutoCraftingMode, Map<ItemVariant, Optional<RecipeEntry<CraftingRecipe>>>> cache = new EnumMap<>(AutoCraftingMode.class);
	private AutoCraftingMode autoCraftingMode;
	private RecipeEntry<CraftingRecipe> lastCraftingRecipe; // cache
	private ItemVariant lastItemVariant; // cache
	private boolean hasToCraft, isOpen;
	private State state = State.CLOSED;
	float pistonPos, pistonTarget, lastPistonTarget, driverPos, driverTarget, lastDriverTarget, capPos, capTarget, lastCapTarget;
	long interpTicks, interpLength = 1, activeTicks, craftingTicks;

	private final PropertyDelegate propertyDelegate = new BlockPosDelegate(pos) {
		@Override
		public int get(int index) {
			if (index == 3)
				return autoCraftingMode.ordinal();
			return super.get(index);
		}

		@Override
		public void set(int index, int value) {
            if (index == 3)
				autoCraftingMode = AutoCraftingMode.values()[value];
            else
				super.set(index, value);
		}

		@Override
		public int size() {
			return super.size() + 1;
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
    public static void tick(World world, BlockPos pos, BlockState state, CompactingChestBlockEntity chest) {
		if (!world.isClient()) {
			CompactingChestStatusUpdatePayload.sendCompactingChestStatusUpdate(chest);
		}

		if (world.isClient()) {
			if (chest.hasToCraft()) {
				chest.craftingTicks = 20;
			}
			else {
				chest.craftingTicks--;
			}

			if (chest.craftingTicks >= 0) {
				chest.activeTicks++;
			}
			else {
				chest.activeTicks = 0;
			}

			if (chest.isOpen()) {
				chest.changeState(State.OPEN);
				chest.interpLength = 5;
			}
			else if(chest.craftingTicks >= 0) {
				chest.changeState(State.CRAFTING);
				chest.interpLength = 20;
			}
			else {
				chest.changeState(State.CLOSED);
				chest.interpLength = 15;
			}
			if (chest.interpTicks < chest.interpLength) {
				chest.interpTicks++;
			}
		}
		else {
			if (chest.hasToCraft) {
				boolean couldCraft = chest.tryCraftOnce();
				if (!couldCraft) {
					chest.shouldCraft(false);
				}
				if (world.getTime() % 6 == 0) {
					chest.produceRunningEffects();
				}
			}
		}
	}

	public void produceRunningEffects() {
		if (world instanceof ServerWorld server) {
			var random = world.getRandom();
			if (random.nextFloat() < 0.125F) {
				server.playSound(null, pos, SoundEvents.BLOCK_REDSTONE_TORCH_BURNOUT, SoundCategory.BLOCKS, 0.05F + random.nextFloat() * 0.1F, 0.334F + random.nextFloat() / 2F);
				for (int i = 0; i < 4 + random.nextInt(5); i++) {
					server.spawnParticles(ParticleTypes.CLOUD, pos.getX() + random.nextFloat(), pos.getY() + 0.975 + random.nextFloat() * 0.667F, pos.getZ() + random.nextFloat(), 0, 0, random.nextFloat() / 20F + 0.02F, 0, 1);
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

	private static boolean smartAddToInventory(List<ItemStack> itemStacks, List<ItemStack> inventory, boolean test) {
		List<ItemStack> additionStacks = new ArrayList<>();
		for (ItemStack itemStack : itemStacks) {
			additionStacks.add(itemStack.copy());
		}
		
		boolean tryStackExisting = true;
		for (int i = 0; i < inventory.size(); i++) {
			ItemStack currentStack = inventory.get(i);
			for (ItemStack additionStack : additionStacks) {
				boolean doneStuff = false;
				if (additionStack.getCount() > 0) {
					if (currentStack.isEmpty() && (test || !tryStackExisting)) {
						int maxStackCount = currentStack.getMaxCount();
						int maxAcceptCount = Math.min(additionStack.getCount(), maxStackCount);
						
						if (!test) {
							ItemStack newStack = additionStack.copy();
							newStack.setCount(maxAcceptCount);
							inventory.set(i, newStack);
						}
						additionStack.setCount(additionStack.getCount() - maxAcceptCount);
						doneStuff = true;
					} else if (ItemStack.areItemsAndComponentsEqual(currentStack, additionStack)) {
						// add to stack;
						int maxStackCount = currentStack.getMaxCount();
						int canAcceptCount = maxStackCount - currentStack.getCount();
						
						if (canAcceptCount > 0) {
							if (!test) {
								inventory.get(i).increment(Math.min(additionStack.getCount(), canAcceptCount));
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
			
			if (tryStackExisting && !test && i == inventory.size() - 1) {
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
	protected Text getContainerName() {
		return Text.translatable("block.spectrum.compacting_chest");
	}
	
	@Override
	public void readNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(tag, registryLookup);
		if (tag.contains("AutoCraftingMode", NbtElement.NUMBER_TYPE)) {
			int autoCraftingModeInt = tag.getInt("AutoCraftingMode");
			this.autoCraftingMode = AutoCraftingMode.values()[autoCraftingModeInt];
		}
	}
	
	@Override
	public void writeNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(tag, registryLookup);
		tag.putInt("AutoCraftingMode", this.autoCraftingMode.ordinal());
	}
	
	@Override
	public int size() {
		return 27;
	}
	
	@Override
	public void setStack(int slot, ItemStack stack) {
		super.setStack(slot, stack);
		shouldCraft(true);
	}
	
	public void inventoryChanged() {
		shouldCraft(true);
	}
	
	private boolean tryCraftOnce() {
		Optional<RecipeEntry<CraftingRecipe>> optionalCraftingRecipe = Optional.empty();
		DefaultedList<ItemStack> inventory = this.getHeldStacks();
		
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
			if (tryCraftInInventory(inventory, optionalCraftingRecipe.get(), this.lastItemVariant)) {
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
	public boolean onSyncedBlockEvent(int type, int data) {
		if (type == 1) {
			isOpen = data > 0;
		}
		return super.onSyncedBlockEvent(type, data);
	}

	public boolean hasToCraft() {
		return hasToCraft;
	}

	public Optional<RecipeEntry<CraftingRecipe>> searchRecipeToCraft() {
		if (world == null)
			return Optional.empty();

		for (ItemStack itemStack : inventory) {
			if (itemStack.isEmpty()) {
				continue;
			}
			
			int requiredItemCount = this.autoCraftingMode.getSize();
			Pair<Integer, List<ItemStack>> stackPair = InventoryHelper.getStackCountInInventory(itemStack, inventory, requiredItemCount);
			if (stackPair.getLeft() >= requiredItemCount) {
				var currentCache = cache.computeIfAbsent(autoCraftingMode, mode -> new HashMap<>());
				ItemVariant itemVariant = ItemVariant.of(itemStack);
				
				var recipe = currentCache.get(itemVariant);
				if (recipe != null) {
					if (recipe.isEmpty()) {
						continue;
					}
					this.lastItemVariant = itemVariant;
					return recipe;
				}
				
				CraftingRecipeInput input = this.autoCraftingMode.createRecipeInput(itemVariant).input();
				var optionalCraftingRecipe = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, input, world);
				if (optionalCraftingRecipe.isEmpty() || optionalCraftingRecipe.get().value().craft(input, world.getRegistryManager()).isEmpty()) {
					optionalCraftingRecipe = Optional.empty();
					currentCache.put(itemVariant, optionalCraftingRecipe);
				} else {
					currentCache.put(itemVariant, optionalCraftingRecipe);
					
					this.lastItemVariant = itemVariant;
					return optionalCraftingRecipe;
				}
			}
		}
		
		return Optional.empty();
	}
	
	public boolean tryCraftInInventory(DefaultedList<ItemStack> inventory, RecipeEntry<CraftingRecipe> craftingRecipe, ItemVariant itemVariant) {
		if (world == null)
			return false;
		
		ItemStack inputStack = itemVariant.toStack(this.autoCraftingMode.getSize());
		List<ItemStack> remainders = InventoryHelper.removeFromInventoryWithRemainders(inputStack, this);
		
		boolean spaceInInventory;
		
		List<ItemStack> additionItemStacks = new ArrayList<>();
		additionItemStacks.add(craftingRecipe.value().getResult(world.getRegistryManager()));
		additionItemStacks.addAll(remainders);
		
		spaceInInventory = smartAddToInventory(additionItemStacks, inventory, true);
		if (spaceInInventory) {
			// craft
			smartAddToInventory(additionItemStacks, inventory, false);
			this.setHeldStacks(inventory);
			
			// cache
			return true;
		} else {
			smartAddToInventory(List.of(inputStack), inventory, false);
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
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new CompactingChestScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	public enum State{
		OPEN,
		CRAFTING,
		CLOSED
	}

}
