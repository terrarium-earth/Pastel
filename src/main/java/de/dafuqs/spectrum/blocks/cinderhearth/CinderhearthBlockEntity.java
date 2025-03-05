package de.dafuqs.spectrum.blocks.cinderhearth;

import java.util.*;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.energy.storage.*;
import de.dafuqs.spectrum.api.item.*;
import de.dafuqs.spectrum.api.recipe.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.upgrade.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.recipe.cinderhearth.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.screenhandler.v1.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.listener.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.particle.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.text.*;
import net.minecraft.util.collection.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

public class CinderhearthBlockEntity extends LockableContainerBlockEntity implements MultiblockCrafter, SidedInventory, ExtendedScreenHandlerFactory<BlockPos>, InkStorageBlockEntity<IndividualCappedInkStorage>, RecipeInputProvider {
	
	public static final int INVENTORY_SIZE = 11;
	public static final int INPUT_SLOT_ID = 0;
	public static final int INK_PROVIDER_SLOT_ID = 1;
	public static final int EXPERIENCE_STORAGE_ITEM_SLOT_ID = 2;
	public static final int FIRST_OUTPUT_SLOT_ID = 3;
	public static final int LAST_OUTPUT_SLOT_ID = 10;
	public static final int[] OUTPUT_SLOT_IDS = new int[]{3, 4, 5, 6, 7, 8, 9, 10};
	
	protected DefaultedList<ItemStack> inventory;
	protected boolean inventoryChanged;
	
	public static final List<InkColor> USED_INK_COLORS = List.of(InkColors.ORANGE, InkColors.MAGENTA, InkColors.LIGHT_BLUE, InkColors.PURPLE, InkColors.BLACK);
	public static final long INK_STORAGE_SIZE = 8 * 64 * 100;
	public static final long INK_COST_PER_TICK = 8;
	protected IndividualCappedInkStorage inkStorage;
	
	private UUID ownerUUID;
	private UpgradeHolder upgrades;
	private RecipeEntry<?> currentRecipe; // blasting & cinderhearth
	private boolean usesEfficiency;
	protected boolean canTransferInk;
	protected boolean inkDirty;
	
	protected CinderHearthStructureType structure = CinderHearthStructureType.NONE;
	
	protected final CraftingDelegate propertyDelegate = new CraftingDelegate();
	
	@Override
	public int[] getAvailableSlots(Direction side) {
		switch (side) {
			case UP -> {
				return new int[]{INPUT_SLOT_ID};
			}
			case DOWN -> {
				return OUTPUT_SLOT_IDS;
			}
			default -> {
				return new int[]{INK_PROVIDER_SLOT_ID, EXPERIENCE_STORAGE_ITEM_SLOT_ID};
			}
		}
	}
	
	@Override
	public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
		switch (slot) {
			case INK_PROVIDER_SLOT_ID -> {
				return stack.getItem() instanceof InkStorageItem<?> inkStorageItem && (inkStorageItem.getDrainability().canDrain(false));
			}
			case EXPERIENCE_STORAGE_ITEM_SLOT_ID -> {
				return stack.getItem() instanceof ExperienceStorageItem;
			}
			default -> {
				return true;
			}
		}
	}
	
	@Override
	public boolean canExtract(int slot, ItemStack stack, Direction dir) {
		return slot >= FIRST_OUTPUT_SLOT_ID;
	}
	
	public enum CinderHearthStructureType {
		NONE,
		WITH_LAVA,
		WITHOUT_LAVA
	}
	
	public CinderhearthBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.CINDERHEARTH, pos, state);
		this.inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
		this.inkStorage = new IndividualCappedInkStorage(INK_STORAGE_SIZE, USED_INK_COLORS);
	}
	
	@Override
	public void resetUpgrades() {
		this.upgrades = null;
		this.markDirty();
	}
	
	@Override
	public void calculateUpgrades() {
		if (world == null) return;
		this.upgrades = Upgradeable.calculateUpgradeMods2(world, pos, Support.rotationFromDirection(world.getBlockState(pos).get(CinderhearthBlock.FACING)), 2, 1, 1, this.ownerUUID);
		this.updateInClientWorld();
		this.markDirty();
	}
	
	public void updateInClientWorld() {
		if (world instanceof ServerWorld serverWorld)
			serverWorld.getChunkManager().markForUpdate(pos);
	}
	
	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	// Called when the chunk is first loaded to initialize this be
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound nbtCompound = new NbtCompound();
		this.writeNbt(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(PlayerEntity playerEntity) {
		this.ownerUUID = playerEntity.getUuid();
		this.markDirty();
	}
	
	@Override
	protected Text getContainerName() {
		return Text.translatable("block.spectrum.cinderhearth");
	}
	
	@Override
	protected DefaultedList<ItemStack> getHeldStacks() {
		return inventory;
	}
	
	@Override
	protected void setHeldStacks(DefaultedList<ItemStack> inventory) {
		this.inventory = inventory;
	}
	
	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new CinderhearthScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}
	
	@Override
	public BlockPos getScreenOpeningData(ServerPlayerEntity serverPlayerEntity) {
		return pos;
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		
		Inventories.readNbt(nbt, this.inventory, registryLookup);
		
		CodecHelper.fromNbt(InkStorageComponent.CODEC, nbt.get("InkStorage")).ifPresent(storage ->
				this.inkStorage = new IndividualCappedInkStorage(storage.maxEnergyTotal(), storage.storedEnergy()));
		this.propertyDelegate.craftingTime = nbt.getShort("CraftingTime");
		this.propertyDelegate.craftingTimeTotal = nbt.getShort("CraftingTimeTotal");
		this.usesEfficiency = nbt.getBoolean("UsesEfficiency");
		this.canTransferInk = nbt.getBoolean("Paused");
		this.inventoryChanged = nbt.getBoolean("InventoryChanged");
		if (nbt.contains("Structure", NbtElement.NUMBER_TYPE)) {
			this.structure = CinderHearthStructureType.values()[nbt.getInt("Structure")];
		} else {
			this.structure = CinderHearthStructureType.NONE;
		}
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		this.currentRecipe = MultiblockCrafter.getRecipeEntryFromNbt(world, nbt);
		if (nbt.contains("Upgrades", NbtElement.LIST_TYPE)) {
			this.upgrades = UpgradeHolder.fromNbt(nbt.getList("Upgrades", NbtElement.COMPOUND_TYPE));
		} else {
			this.upgrades = new UpgradeHolder();
		}
	}
	
	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		Inventories.writeNbt(nbt, this.inventory, registryLookup);
		CodecHelper.writeNbt(nbt, "InkStorage", InkStorageComponent.CODEC, new InkStorageComponent(this.inkStorage));
		nbt.putShort("CraftingTime", (short) this.propertyDelegate.craftingTime);
		nbt.putShort("CraftingTimeTotal", (short) this.propertyDelegate.craftingTimeTotal);
		nbt.putBoolean("UsesEfficiency", this.usesEfficiency);
		nbt.putBoolean("Paused", this.canTransferInk);
		nbt.putBoolean("InventoryChanged", this.inventoryChanged);
		nbt.putInt("Structure", this.structure.ordinal());
		if (this.upgrades != null) {
			nbt.put("Upgrades", this.upgrades.toNbt());
		}
		
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
		if (this.currentRecipe != null) {
			nbt.putString("CurrentRecipe", this.currentRecipe.id().toString());
		}
	}
	
	@SuppressWarnings("unused")
	public static void serverTick(World world, BlockPos blockPos, BlockState blockState, CinderhearthBlockEntity cinderhearthBlockEntity) {
		if (cinderhearthBlockEntity.upgrades == null) {
			cinderhearthBlockEntity.calculateUpgrades();
		}
		cinderhearthBlockEntity.inkDirty = false;
		
		if (cinderhearthBlockEntity.canTransferInk) {
			boolean didSomething = false;
			ItemStack stack = cinderhearthBlockEntity.getStack(INK_PROVIDER_SLOT_ID);
			if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
				InkStorage itemStorage = inkStorageItem.getEnergyStorage(stack);
				didSomething = InkStorage.transferInk(itemStorage, cinderhearthBlockEntity.inkStorage) != 0;
				if (didSomething) {
					inkStorageItem.setEnergyStorage(stack, itemStorage);
				}
			}
			if (didSomething) {
				cinderhearthBlockEntity.markDirty();
				cinderhearthBlockEntity.setInkDirty();
			} else {
				cinderhearthBlockEntity.canTransferInk = false;
			}
		}
		
		if (cinderhearthBlockEntity.inventoryChanged) {
			calculateRecipe(world, cinderhearthBlockEntity);
			cinderhearthBlockEntity.inventoryChanged = false;
			cinderhearthBlockEntity.updateInClientWorld();
		}
		
		if (cinderhearthBlockEntity.currentRecipe != null) {
			if (!canContinue(world, blockPos, cinderhearthBlockEntity)) {
				cinderhearthBlockEntity.currentRecipe = null;
				cinderhearthBlockEntity.propertyDelegate.craftingTime = 0;
				cinderhearthBlockEntity.propertyDelegate.craftingTimeTotal = 0;
				cinderhearthBlockEntity.markDirty();
				return;
			}
			
			if (cinderhearthBlockEntity.propertyDelegate.craftingTime == 0) {
				var recipe = cinderhearthBlockEntity.currentRecipe;
				
				cinderhearthBlockEntity.usesEfficiency = cinderhearthBlockEntity.drainInkForUpgradesRequired(cinderhearthBlockEntity, UpgradeType.EFFICIENCY, InkColors.BLACK, false);
				
				int baseTime = recipe.value() instanceof AbstractCookingRecipe abstractCookingRecipe ? abstractCookingRecipe.getCookingTime()
						: recipe.value() instanceof CinderhearthRecipe cinderhearthRecipe ? cinderhearthRecipe.getCraftingTime()
						: -1;
				if (baseTime >= 0) {
					float speedModifier = cinderhearthBlockEntity.drainInkForUpgrades(cinderhearthBlockEntity, UpgradeType.SPEED, InkColors.MAGENTA, cinderhearthBlockEntity.usesEfficiency);
					cinderhearthBlockEntity.propertyDelegate.craftingTimeTotal = (int) Math.ceil(baseTime / speedModifier);
				}
			}
			
			cinderhearthBlockEntity.propertyDelegate.craftingTime++;
			
			if (cinderhearthBlockEntity.propertyDelegate.craftingTime == cinderhearthBlockEntity.propertyDelegate.craftingTimeTotal) {
				if (cinderhearthBlockEntity.getCurrentRecipe() instanceof CinderhearthRecipe cinderhearthRecipe) {
					craftCinderhearthRecipe(world, cinderhearthBlockEntity, cinderhearthRecipe);
				} else if (cinderhearthBlockEntity.getCurrentRecipe() instanceof BlastingRecipe blastingRecipe) {
					craftBlastingRecipe(world, cinderhearthBlockEntity, blastingRecipe);
				}
			}
			
			cinderhearthBlockEntity.markDirty();
		}
	}
	
	private static boolean canContinue(World world, BlockPos blockPos, CinderhearthBlockEntity cinderhearthBlockEntity) {
		if (!canAcceptRecipeOutput(world, cinderhearthBlockEntity.currentRecipe, cinderhearthBlockEntity)) {
			return false;
		}
		
		if (cinderhearthBlockEntity.propertyDelegate.craftingTime % 20 == 0) {
			if (!checkRecipeRequirements(world, blockPos, cinderhearthBlockEntity)) {
				return false;
			}
			// consume orange ink
			return cinderhearthBlockEntity.drainInkForUpgradesRequired(cinderhearthBlockEntity, InkColors.ORANGE, INK_COST_PER_TICK, cinderhearthBlockEntity.usesEfficiency);
		}
		
		return true;
	}
	
	protected static boolean canAcceptRecipeOutput(World world, RecipeEntry<?> recipe, Inventory inventory) {
		if (recipe != null) {
			ItemStack outputStack = recipe.value().getResult(world.getRegistryManager());
			if (outputStack.isEmpty()) {
				return true;
			} else {
				int outputSpaceFound = 0;
				for (int slot : OUTPUT_SLOT_IDS) {
					ItemStack slotStack = inventory.getStack(slot);
					if (slotStack.isEmpty()) {
						return true;
					} else if (ItemStack.areItemsAndComponentsEqual(slotStack, outputStack)) {
						outputSpaceFound += outputStack.getMaxCount() - slotStack.getCount();
						if (outputSpaceFound >= outputStack.getCount()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private static void calculateRecipe(@NotNull World world, @NotNull CinderhearthBlockEntity cinderhearthBlockEntity) {
		var input = new SingleStackRecipeInput(cinderhearthBlockEntity.getStack(0));
		
		// test the cached recipe => faster
		if (cinderhearthBlockEntity.getCurrentRecipe() instanceof CinderhearthRecipe recipe) {
			if (recipe.matches(input, world))
				return;
		} else if (cinderhearthBlockEntity.getCurrentRecipe() instanceof BlastingRecipe recipe) {
			if (recipe.matches(input, world))
				return;
		}
		
		cinderhearthBlockEntity.currentRecipe = null;
		cinderhearthBlockEntity.propertyDelegate.craftingTime = 0;
		cinderhearthBlockEntity.propertyDelegate.craftingTimeTotal = -1;
		
		// cached recipe did not match => calculate new
		ItemStack inputStack = cinderhearthBlockEntity.getStack(0);
		if (!inputStack.isEmpty()) {
			world.getRecipeManager().getFirstMatch(SpectrumRecipeTypes.CINDERHEARTH, input, world).ifPresentOrElse(
					r -> cinderhearthBlockEntity.currentRecipe = r,
					() -> world.getRecipeManager().getFirstMatch(RecipeType.BLASTING, input, world).ifPresent(
							r -> cinderhearthBlockEntity.currentRecipe = r));
		}
	}
	
	private static boolean checkRecipeRequirements(World world, BlockPos blockPos, @NotNull CinderhearthBlockEntity cinderhearthBlockEntity) {
		PlayerEntity lastInteractedPlayer = PlayerOwned.getPlayerEntityIfOnline(cinderhearthBlockEntity.ownerUUID);
		if (lastInteractedPlayer == null) {
			return false;
		}
		
		cinderhearthBlockEntity.structure = CinderhearthBlock.verifyStructure(world, blockPos, null);
		if (cinderhearthBlockEntity.structure == CinderHearthStructureType.NONE) {
			world.playSound(null, cinderhearthBlockEntity.getPos(), SpectrumSoundEvents.CRAFTING_ABORTED, SoundCategory.BLOCKS, 0.9F + world.random.nextFloat() * 0.2F, 0.9F + world.random.nextFloat() * 0.2F);
			return false;
		}
		
		if (cinderhearthBlockEntity.getCurrentRecipe() instanceof GatedRecipe<?> gatedRecipe) {
			return gatedRecipe.canPlayerCraft(lastInteractedPlayer);
		}
		return true;
	}
	
	public static void craftBlastingRecipe(World world, @NotNull CinderhearthBlockEntity cinderhearth, @NotNull BlastingRecipe blastingRecipe) {
		// calculate outputs
		ItemStack inputStack = cinderhearth.getStack(INPUT_SLOT_ID);
		float yieldMod = inputStack.isIn(SpectrumItemTags.NO_CINDERHEARTH_DOUBLING) ? 1.0F : cinderhearth.drainInkForUpgrades(cinderhearth, UpgradeType.YIELD, InkColors.LIGHT_BLUE, cinderhearth.usesEfficiency);
		ItemStack output = blastingRecipe.getResult(world.getRegistryManager()).copy();
		List<ItemStack> outputs = new ArrayList<>();
		if (yieldMod > 1) {
			int outputCount = Support.getIntFromDecimalWithChance(output.getCount() * yieldMod, world.random);
			while (outputCount > 0) { // if the rolled count exceeds the max stack size we need to split them (unstackable items, counts > 64, ...)
				int count = Math.min(outputCount, output.getMaxCount());
				ItemStack outputStack = output.copy();
				outputStack.setCount(count);
				outputs.add(outputStack);
				outputCount -= count;
			}
		} else {
			outputs.add(output.copy());
		}
		
		// craft
		craftRecipe(cinderhearth, inputStack, outputs, blastingRecipe.getExperience());
	}
	
	public static void craftCinderhearthRecipe(World world, @NotNull CinderhearthBlockEntity cinderhearth, @NotNull CinderhearthRecipe cinderhearthRecipe) {
		// calculate outputs
		ItemStack inputStack = cinderhearth.getStack(INPUT_SLOT_ID);
		float yieldMod = inputStack.isIn(SpectrumItemTags.NO_CINDERHEARTH_DOUBLING) ? 1.0F : cinderhearth.drainInkForUpgrades(cinderhearth, UpgradeType.YIELD, InkColors.LIGHT_BLUE, cinderhearth.usesEfficiency);
		List<ItemStack> outputs = cinderhearthRecipe.getRolledOutputs(world.random, yieldMod);
		
		// craft
		craftRecipe(cinderhearth, inputStack, outputs, cinderhearthRecipe.getExperience());
	}
	
	private static void craftRecipe(@NotNull CinderhearthBlockEntity cinderhearth, ItemStack inputStack, List<ItemStack> outputs, float experience) {
		var world = cinderhearth.world;
		if (world == null) return;
		
		DefaultedList<ItemStack> backupInventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
		for (int i = 0; i < cinderhearth.inventory.size(); i++) {
			backupInventory.set(i, cinderhearth.inventory.get(i));
		}
		
		boolean couldAdd = InventoryHelper.addToInventory(cinderhearth, outputs, FIRST_OUTPUT_SLOT_ID, LAST_OUTPUT_SLOT_ID + 1);
		if (couldAdd) {
			ItemStack remainder = inputStack.getRecipeRemainder();
			
			// use up input ingredient
			ItemStack inputStackCopy = inputStack.copy();
			int amountToDecrementInput = cinderhearth.getCurrentRecipe() instanceof CinderhearthRecipe cinderhearthRecipe ? cinderhearthRecipe.getIngredientStacks().getFirst().getCount() : 1;
			inputStack.decrement(amountToDecrementInput);
			
			if (remainder.isEmpty()) {
				boolean remainderAdded = InventoryHelper.addToInventory(cinderhearth, remainder, FIRST_OUTPUT_SLOT_ID, LAST_OUTPUT_SLOT_ID + 1);
				if (!remainderAdded) {
					cinderhearth.setStack(CinderhearthBlockEntity.INPUT_SLOT_ID, remainder);
				}
			}
			
			// effects
			playCraftingFinishedEffects(cinderhearth);
			
			// reset
			cinderhearth.propertyDelegate.craftingTime = 0;
			cinderhearth.inventoryChanged();
			
			// grant experience & advancements
			float experienceMod = cinderhearth.drainInkForUpgrades(cinderhearth, UpgradeType.EXPERIENCE, InkColors.PURPLE, cinderhearth.usesEfficiency);
			int finalExperience = Support.getIntFromDecimalWithChance(experience * experienceMod, world.random);
			ExperienceStorageItem.addStoredExperience(world.getRegistryManager(), cinderhearth.getStack(EXPERIENCE_STORAGE_ITEM_SLOT_ID), finalExperience);
			cinderhearth.grantPlayerCinderhearthSmeltingAdvancement(inputStackCopy, outputs, finalExperience);
		} else {
			cinderhearth.inventory = backupInventory;
			
			// prevents trying to craft more until the inventory is freed up
			cinderhearth.propertyDelegate.craftingTimeTotal = -1;
			cinderhearth.currentRecipe = null;
			cinderhearth.inventoryChanged = false;
		}
	}
	
	public void grantPlayerCinderhearthSmeltingAdvancement(ItemStack input, List<ItemStack> outputs, int experience) {
		ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) getOwnerIfOnline();
		if (serverPlayerEntity != null) {
			SpectrumAdvancementCriteria.CINDERHEARTH_SMELTING.trigger(serverPlayerEntity, input, outputs, experience, this.upgrades);
		}
	}
	
	public static void playCraftingFinishedEffects(@NotNull CinderhearthBlockEntity cinderhearthBlockEntity) {
		Direction.Axis axis = null;
		Direction direction = Direction.UP;
		if (!(cinderhearthBlockEntity.world instanceof ServerWorld world)) return;
		
		for (Map.Entry<UpgradeType, Integer> entry : cinderhearthBlockEntity.upgrades.entrySet()) {
			if (entry.getValue() > 1) {
				if (axis == null) {
					BlockState state = world.getBlockState(cinderhearthBlockEntity.pos);
					direction = state.get(CinderhearthBlock.FACING);
					axis = direction.getAxis();
				}
				
				double d = (double) cinderhearthBlockEntity.pos.getX() + 0.5D;
				double f = (double) cinderhearthBlockEntity.pos.getZ() + 0.5D;
				double g2 = -3D / 16D;
				double h2 = 4D / 16D;
				double i2 = axis == Direction.Axis.X ? (double) direction.getOffsetX() * g2 : h2;
				double k2 = axis == Direction.Axis.Z ? (double) direction.getOffsetZ() * g2 : h2;
				PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world,
						new Vec3d(d + i2, cinderhearthBlockEntity.pos.getY() + 1.1, f + k2),
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						3,
						new Vec3d(0.05D, 0.00D, 0.05D),
						new Vec3d(0.0D, 0.3D, 0.0D));
			}
		}
	}
	
	@Override
	public int size() {
		return INVENTORY_SIZE;
	}
	
	@Override
	public boolean isEmpty() {
		return this.inventory.isEmpty();
	}
	
	@Override
	public ItemStack getStack(int slot) {
		return inventory.get(slot);
	}
	
	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack removedStack = Inventories.splitStack(this.inventory, slot, amount);
		this.inventoryChanged();
		return removedStack;
	}
	
	@Override
	public ItemStack removeStack(int slot) {
		ItemStack removedStack = Inventories.removeStack(this.inventory, slot);
		this.inventoryChanged();
		return removedStack;
	}
	
	@Override
	public void setStack(int slot, @NotNull ItemStack stack) {
		this.inventory.set(slot, stack);
		if (stack.getCount() > this.getMaxCountPerStack()) {
			stack.setCount(this.getMaxCountPerStack());
		}
		this.inventoryChanged();
	}
	
	@Override
	public boolean canPlayerUse(PlayerEntity player) {
		if (world == null || world.getBlockEntity(this.pos) != this) {
			return false;
		} else {
			return player.squaredDistanceTo((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
		}
	}
	
	public void inventoryChanged() {
		this.inventoryChanged = true;
		this.canTransferInk = true;
		this.markDirty();
	}
	
	@Override
	public void clear() {
		this.inventory.clear();
		this.inventoryChanged();
	}
	
	@Override
	public UpgradeHolder getUpgradeHolder() {
		return this.upgrades;
	}
	
	@Override
	public IndividualCappedInkStorage getEnergyStorage() {
		return this.inkStorage;
	}
	
	@Override
	public void setInkDirty() {
		this.inkDirty = true;
	}
	
	@Override
	public boolean getInkDirty() {
		return this.inkDirty;
	}
	
	public RecipeEntry<?> getCurrentRecipeEntry() {
		return currentRecipe;
	}
	
	public Recipe<?> getCurrentRecipe() {
		return currentRecipe == null ? null : currentRecipe.value();
	}
	
	@Override
	public void provideRecipeInputs(RecipeMatcher finder) {
		this.inventory.forEach(finder::addInput);
	}
	
}
