package de.dafuqs.spectrum.blocks.cinderhearth;

import de.dafuqs.spectrum.api.block.MultiblockCrafter;
import de.dafuqs.spectrum.api.block.PlayerOwned;
import de.dafuqs.spectrum.api.energy.InkStorage;
import de.dafuqs.spectrum.api.energy.InkStorageBlockEntity;
import de.dafuqs.spectrum.api.energy.InkStorageItem;
import de.dafuqs.spectrum.api.energy.color.InkColor;
import de.dafuqs.spectrum.api.energy.color.InkColors;
import de.dafuqs.spectrum.api.energy.storage.IndividualCappedInkStorage;
import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import de.dafuqs.spectrum.api.recipe.GatedRecipe;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.blocks.upgrade.Upgradeable;
import de.dafuqs.spectrum.components.InkStorageComponent;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.CinderhearthScreenHandler;
import de.dafuqs.spectrum.networking.s2c_payloads.PlayParticleWithRandomOffsetAndVelocityPayload;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.recipe.cinderhearth.CinderhearthRecipe;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumItemTags;
import de.dafuqs.spectrum.registries.SpectrumRecipeTypes;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CinderhearthBlockEntity extends BaseInventoryBlockEntity implements Container, MultiblockCrafter, InkStorageBlockEntity<IndividualCappedInkStorage>, StackedContentsCompatible {
	
	public static final int INVENTORY_SIZE = 11;
	public static final int INPUT_SLOT_ID = 0;
	public static final int INK_PROVIDER_SLOT_ID = 1;
	public static final int EXPERIENCE_STORAGE_ITEM_SLOT_ID = 2;
	public static final int FIRST_OUTPUT_SLOT_ID = 3;
	public static final int LAST_OUTPUT_SLOT_ID = 10;
	public static final int[] OUTPUT_SLOT_IDS = new int[]{3, 4, 5, 6, 7, 8, 9, 10};
	
	protected FriendlyStackHandler inventory;
	protected boolean inventoryChanged;
	
	public static final List<InkColor> USED_INK_COLORS = List.of(InkColors.ORANGE, InkColors.MAGENTA, InkColors.LIGHT_BLUE, InkColors.PURPLE, InkColors.BLACK);
	public static final long INK_STORAGE_SIZE = 8 * 64 * 100;
	public static final long INK_COST_PER_TICK = 8;
	protected IndividualCappedInkStorage inkStorage;
	
	private UUID ownerUUID;
	private UpgradeHolder upgrades;
	private RecipeHolder<?> currentRecipe; // blasting & cinderhearth
	private boolean usesEfficiency;
	protected boolean canTransferInk;
	protected boolean inkDirty;
	
	protected CinderHearthStructureType structure = CinderHearthStructureType.NONE;
	
	protected final CraftingDelegate propertyDelegate = new CraftingDelegate();

	public enum CinderHearthStructureType {
		NONE,
		WITH_LAVA,
		WITHOUT_LAVA
	}
	
	public CinderhearthBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.CINDERHEARTH, pos, state);
		this.inventory = new FriendlyStackHandler(INVENTORY_SIZE);
		this.inkStorage = new IndividualCappedInkStorage(INK_STORAGE_SIZE, USED_INK_COLORS);
	}

	@Override
	protected FriendlyStackHandler getHandler() {
		return inventory;
	}

	@Override
	public void resetUpgrades() {
		this.upgrades = null;
		this.setChanged();
	}
	
	@Override
	public void calculateUpgrades() {
		if (level == null) return;
		this.upgrades = Upgradeable.calculateUpgradeMods2(level, worldPosition, Support.rotationFromDirection(level.getBlockState(worldPosition).getValue(CinderhearthBlock.FACING)), 2, 1, 1, this.ownerUUID);
		this.updateInClientWorld();
		this.setChanged();
	}
	
	public void updateInClientWorld() {
		if (level instanceof ServerLevel serverWorld)
			serverWorld.getChunkSource().blockChanged(worldPosition);
	}
	
	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	// Called when the chunk is first loaded to initialize this be
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		CompoundTag nbtCompound = new CompoundTag();
		this.saveAdditional(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(Player playerEntity) {
		this.ownerUUID = playerEntity.getUUID();
		this.setChanged();
	}
	
	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.spectrum.cinderhearth");
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return new CinderhearthScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
		BlockPos.STREAM_CODEC.encode(buffer, worldPosition);
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);

		inventory.deserializeNBT(registryLookup, nbt.getCompound("inventory"));
		
		CodecHelper.fromNbt(InkStorageComponent.CODEC, nbt.get("InkStorage")).ifPresent(storage ->
				this.inkStorage = new IndividualCappedInkStorage(storage.maxPerColor(), storage.storedEnergy()));
		this.propertyDelegate.craftingTime = nbt.getShort("CraftingTime");
		this.propertyDelegate.craftingTimeTotal = nbt.getShort("CraftingTimeTotal");
		this.usesEfficiency = nbt.getBoolean("UsesEfficiency");
		this.canTransferInk = nbt.getBoolean("Paused");
		this.inventoryChanged = nbt.getBoolean("InventoryChanged");
		if (nbt.contains("Structure", Tag.TAG_ANY_NUMERIC)) {
			this.structure = CinderHearthStructureType.values()[nbt.getInt("Structure")];
		} else {
			this.structure = CinderHearthStructureType.NONE;
		}
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		this.currentRecipe = MultiblockCrafter.getRecipeEntryFromNbt(level, nbt);
		if (nbt.contains("Upgrades", Tag.TAG_LIST)) {
			this.upgrades = UpgradeHolder.fromNbt(nbt.getList("Upgrades", Tag.TAG_COMPOUND));
		} else {
			this.upgrades = new UpgradeHolder();
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		nbt.put("inventory", inventory.serializeNBT(registryLookup));
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
	public static void serverTick(Level world, BlockPos blockPos, BlockState blockState, CinderhearthBlockEntity cinder) {
		if (cinder.upgrades == null) {
			cinder.calculateUpgrades();
		}
		cinder.inkDirty = false;
		
		if (cinder.canTransferInk) {
			boolean didSomething = false;
			ItemStack stack = cinder.inventory.getStackInSlot(INK_PROVIDER_SLOT_ID);
			if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
				InkStorage itemStorage = inkStorageItem.getEnergyStorage(stack);
				didSomething = InkStorage.transferInk(itemStorage, cinder.inkStorage) != 0;
				if (didSomething) {
					inkStorageItem.setEnergyStorage(stack, itemStorage);
				}
			}
			if (didSomething) {
				cinder.setChanged();
				cinder.setInkDirty();
			} else {
				cinder.canTransferInk = false;
			}
		}
		
		if (cinder.inventoryChanged) {
			calculateRecipe(world, cinder);
			cinder.inventoryChanged = false;
			cinder.updateInClientWorld();
		}
		
		if (cinder.currentRecipe != null) {
			if (!canContinue(world, blockPos, cinder)) {
				cinder.currentRecipe = null;
				cinder.propertyDelegate.craftingTime = 0;
				cinder.propertyDelegate.craftingTimeTotal = 0;
				cinder.setChanged();
				return;
			}
			
			if (cinder.propertyDelegate.craftingTime == 0) {
				var recipe = cinder.currentRecipe;
				
				cinder.usesEfficiency = cinder.drainInkForUpgradesRequired(cinder, UpgradeType.EFFICIENCY, InkColors.BLACK, false);
				
				int baseTime = recipe.value() instanceof AbstractCookingRecipe abstractCookingRecipe ? abstractCookingRecipe.getCookingTime()
						: recipe.value() instanceof CinderhearthRecipe cinderhearthRecipe ? cinderhearthRecipe.getCraftingTime()
						: -1;
				if (baseTime >= 0) {
					float speedModifier = cinder.drainInkForUpgrades(cinder, UpgradeType.SPEED, InkColors.MAGENTA, cinder.usesEfficiency);
					cinder.propertyDelegate.craftingTimeTotal = (int) Math.ceil(baseTime / speedModifier);
				}
			}
			
			cinder.propertyDelegate.craftingTime++;
			
			if (cinder.propertyDelegate.craftingTime == cinder.propertyDelegate.craftingTimeTotal) {
				if (cinder.getCurrentRecipe() instanceof CinderhearthRecipe cinderhearthRecipe) {
					craftCinderhearthRecipe(world, cinder, cinderhearthRecipe);
				} else if (cinder.getCurrentRecipe() instanceof BlastingRecipe blastingRecipe) {
					craftBlastingRecipe(world, cinder, blastingRecipe);
				}
			}
			
			cinder.setChanged();
		}
	}
	
	private static boolean canContinue(Level world, BlockPos blockPos, CinderhearthBlockEntity cinder) {
		if (!canAcceptRecipeOutput(world, cinder.currentRecipe, cinder.inventory)) {
			return false;
		}
		
		if (cinder.propertyDelegate.craftingTime % 20 == 0) {
			if (!checkRecipeRequirements(world, blockPos, cinder)) {
				return false;
			}
			// consume orange ink
			return cinder.drainInkForUpgradesRequired(cinder, InkColors.ORANGE, INK_COST_PER_TICK, cinder.usesEfficiency);
		}
		
		return true;
	}
	
	protected static boolean canAcceptRecipeOutput(Level world, RecipeHolder<?> recipe, FriendlyStackHandler inventory) {
		if (recipe != null) {
			ItemStack outputStack = recipe.value().getResultItem(world.registryAccess());
			if (outputStack.isEmpty()) {
				return true;
			} else {
				int outputSpaceFound = 0;
				for (int slot : OUTPUT_SLOT_IDS) {
					ItemStack slotStack = inventory.getStackInSlot(slot);
					if (slotStack.isEmpty()) {
						return true;
					} else if (ItemStack.isSameItemSameComponents(slotStack, outputStack)) {
						outputSpaceFound += outputStack.getMaxStackSize() - slotStack.getCount();
						if (outputSpaceFound >= outputStack.getCount()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	
	private static void calculateRecipe(@NotNull Level world, @NotNull CinderhearthBlockEntity cinderhearthBlockEntity) {
		var input = new SingleRecipeInput(cinderhearthBlockEntity.inventory.getStackInSlot(0));
		
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
		ItemStack inputStack = cinderhearthBlockEntity.inventory.getStackInSlot(0);
		if (!inputStack.isEmpty()) {
			world.getRecipeManager().getRecipeFor(SpectrumRecipeTypes.CINDERHEARTH, input, world).ifPresentOrElse(
					r -> cinderhearthBlockEntity.currentRecipe = r,
					() -> world.getRecipeManager().getRecipeFor(RecipeType.BLASTING, input, world).ifPresent(
							r -> cinderhearthBlockEntity.currentRecipe = r));
		}
	}
	
	private static boolean checkRecipeRequirements(Level world, BlockPos blockPos, @NotNull CinderhearthBlockEntity cinderhearthBlockEntity) {
		Player lastInteractedPlayer = PlayerOwned.getPlayerEntityIfOnline(cinderhearthBlockEntity.ownerUUID);
		if (lastInteractedPlayer == null) {
			return false;
		}
		
		cinderhearthBlockEntity.structure = CinderhearthBlock.verifyStructure(world, blockPos, null);
		if (cinderhearthBlockEntity.structure == CinderHearthStructureType.NONE) {
			world.playSound(null, cinderhearthBlockEntity.getBlockPos(), SpectrumSoundEvents.CRAFTING_ABORTED, SoundSource.BLOCKS, 0.9F + world.random.nextFloat() * 0.2F, 0.9F + world.random.nextFloat() * 0.2F);
			return false;
		}
		
		if (cinderhearthBlockEntity.getCurrentRecipe() instanceof GatedRecipe<?> gatedRecipe) {
			return gatedRecipe.canPlayerCraft(lastInteractedPlayer);
		}
		return true;
	}
	
	public static void craftBlastingRecipe(Level world, @NotNull CinderhearthBlockEntity cinderhearth, @NotNull BlastingRecipe blastingRecipe) {
		// calculate outputs
		ItemStack inputStack = cinderhearth.inventory.getStackInSlot(INPUT_SLOT_ID);
		float yieldMod = inputStack.is(SpectrumItemTags.NO_CINDERHEARTH_DOUBLING) ? 1.0F : cinderhearth.drainInkForUpgrades(cinderhearth, UpgradeType.YIELD, InkColors.LIGHT_BLUE, cinderhearth.usesEfficiency);
		ItemStack output = blastingRecipe.getResultItem(world.registryAccess()).copy();
		List<ItemStack> outputs = new ArrayList<>();
		if (yieldMod > 1) {
			int outputCount = Support.getIntFromDecimalWithChance(output.getCount() * yieldMod, world.random);
			while (outputCount > 0) { // if the rolled count exceeds the max stack size we need to split them (unstackable items, counts > 64, ...)
				int count = Math.min(outputCount, output.getMaxStackSize());
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
	
	public static void craftCinderhearthRecipe(Level world, @NotNull CinderhearthBlockEntity cinderhearth, @NotNull CinderhearthRecipe cinderhearthRecipe) {
		// calculate outputs
		ItemStack inputStack = cinderhearth.inventory.getStackInSlot(INPUT_SLOT_ID);
		float yieldMod = inputStack.is(SpectrumItemTags.NO_CINDERHEARTH_DOUBLING) ? 1.0F : cinderhearth.drainInkForUpgrades(cinderhearth, UpgradeType.YIELD, InkColors.LIGHT_BLUE, cinderhearth.usesEfficiency);
		List<ItemStack> outputs = cinderhearthRecipe.getRolledOutputs(world.random, yieldMod);
		
		// craft
		craftRecipe(cinderhearth, inputStack, outputs, cinderhearthRecipe.getExperience());
	}
	
	private static void craftRecipe(@NotNull CinderhearthBlockEntity cinderhearth, ItemStack inputStack, List<ItemStack> outputs, float experience) {
		var world = cinderhearth.level;
		if (world == null) return;
		
		var backupInventory = new FriendlyStackHandler(INVENTORY_SIZE);
		for (int i = 0; i < cinderhearth.inventory.getSlots(); i++) {
			backupInventory.setStackInSlot(i, cinderhearth.inventory.getStackInSlot(i));
		}

		boolean couldAdd = InventoryHelper.addToInventory(cinderhearth.inventory, outputs, FIRST_OUTPUT_SLOT_ID, LAST_OUTPUT_SLOT_ID + 1);
		if (couldAdd) {
			ItemStack remainder = inputStack.getCraftingRemainingItem();

			// use up input ingredient
			ItemStack inputStackCopy = inputStack.copy();
			int amountToDecrementInput = cinderhearth.getCurrentRecipe() instanceof CinderhearthRecipe cinderhearthRecipe ? cinderhearthRecipe.getIngredientStacks().getFirst().getCount() : 1;
			inputStack.shrink(amountToDecrementInput);

			if (remainder.isEmpty()) {
				boolean remainderAdded = InventoryHelper.addToInventory(cinderhearth.inventory, remainder, FIRST_OUTPUT_SLOT_ID, LAST_OUTPUT_SLOT_ID + 1);
				if (!remainderAdded) {
					cinderhearth.inventory.setStackInSlot(CinderhearthBlockEntity.INPUT_SLOT_ID, remainder);
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
			ExperienceStorageItem.addStoredExperience(world.registryAccess(), cinderhearth.inventory.getStackInSlot(EXPERIENCE_STORAGE_ITEM_SLOT_ID), finalExperience);
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
		ServerPlayer serverPlayerEntity = (ServerPlayer) getOwnerIfOnline();
		if (serverPlayerEntity != null) {
			SpectrumAdvancementCriteria.CINDERHEARTH_SMELTING.trigger(serverPlayerEntity, input, outputs, experience, this.upgrades);
		}
	}

	public static void playCraftingFinishedEffects(@NotNull CinderhearthBlockEntity cinderhearthBlockEntity) {
		Direction.Axis axis = null;
		Direction direction = Direction.UP;
		if (!(cinderhearthBlockEntity.level instanceof ServerLevel world)) return;

		for (Map.Entry<UpgradeType, Integer> entry : cinderhearthBlockEntity.upgrades.entrySet()) {
			if (entry.getValue() > 1) {
				if (axis == null) {
					BlockState state = world.getBlockState(cinderhearthBlockEntity.worldPosition);
					direction = state.getValue(CinderhearthBlock.FACING);
					axis = direction.getAxis();
				}

				double d = (double) cinderhearthBlockEntity.worldPosition.getX() + 0.5D;
				double f = (double) cinderhearthBlockEntity.worldPosition.getZ() + 0.5D;
				double g2 = -3D / 16D;
				double h2 = 4D / 16D;
				double i2 = axis == Direction.Axis.X ? (double) direction.getStepX() * g2 : h2;
				double k2 = axis == Direction.Axis.Z ? (double) direction.getStepZ() * g2 : h2;
				PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world,
						new Vec3(d + i2, cinderhearthBlockEntity.worldPosition.getY() + 1.1, f + k2),
						ParticleTypes.CAMPFIRE_COSY_SMOKE,
						3,
						new Vec3(0.05D, 0.00D, 0.05D),
						new Vec3(0.0D, 0.3D, 0.0D));
			}
		}
	}
	
	public void inventoryChanged() {
		this.inventoryChanged = true;
		this.canTransferInk = true;
		this.setChanged();
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
	
	public RecipeHolder<?> getCurrentRecipeEntry() {
		return currentRecipe;
	}
	
	public Recipe<?> getCurrentRecipe() {
		return currentRecipe == null ? null : currentRecipe.value();
	}
	
	@Override
	public void fillStackedContents(StackedContents finder) {
		this.inventory.getInternalList().forEach(finder::accountStack);
	}
	
}
