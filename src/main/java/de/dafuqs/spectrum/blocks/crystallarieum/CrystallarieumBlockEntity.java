package de.dafuqs.spectrum.blocks.crystallarieum;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.storage.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.particle.effect.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.recipe.crystallarieum.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.particle.*;
import net.minecraft.recipe.*;
import net.minecraft.recipe.input.*;
import net.minecraft.registry.*;
import net.minecraft.server.network.*;
import net.minecraft.server.world.*;
import net.minecraft.sound.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CrystallarieumBlockEntity extends InWorldInteractionBlockEntity implements PlayerOwned, InkStorageBlockEntity<IndividualCappedInkStorage> {
	
	protected final static int CATALYST_SLOT_ID = 0;
	protected final static int INK_STORAGE_STACK_SLOT_ID = 1;
	protected final static int INVENTORY_SIZE = 2;
	
	public static final long INK_STORAGE_SIZE = 64 * 64 * 100;
	
	protected IndividualCappedInkStorage inkStorage;
	protected boolean inkDirty;
	
	@Nullable
	protected UUID ownerUUID;
	
	@Nullable
	protected RecipeEntry<CrystallarieumRecipe> currentRecipe;
	protected CrystallarieumCatalyst currentCatalyst = CrystallarieumCatalyst.EMPTY;
	
	// for performance reasons, the crystallarieum only processes recipe logic all 20 ticks
	public static final int SECOND = 20;
	protected TickLooper tickLooper = new TickLooper(SECOND);
	
	protected int currentGrowthStageTicks;
	protected boolean canWork;
	
	public CrystallarieumBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.CRYSTALLARIEUM, pos, state, INVENTORY_SIZE);
		this.inkStorage = new IndividualCappedInkStorage(INK_STORAGE_SIZE);
		this.canWork = true;
	}
	
	@SuppressWarnings("unused")
    public static void clientTick(@NotNull World world, BlockPos blockPos, BlockState blockState, CrystallarieumBlockEntity crystallarieum) {
		if (crystallarieum.canWork && crystallarieum.currentRecipe != null) {
			ParticleEffect particleEffect = ColoredSparkleRisingParticleEffect.of(crystallarieum.currentRecipe.value().getInkColor().getColorInt());
			
			int amount = 1 + crystallarieum.currentRecipe.value().getInkPerSecond();
			if (Support.getIntFromDecimalWithChance(amount / 80.0, world.random) > 0) {
				double randomX = world.getRandom().nextDouble() * 0.8;
				double randomZ = world.getRandom().nextDouble() * 0.8;
				world.addImportantParticle(particleEffect, blockPos.getX() + 0.1 + randomX, blockPos.getY() + 1, blockPos.getZ() + 0.1 + randomZ, 0.0D, 0.03D, 0.0D);
			}
		}
	}

	@SuppressWarnings("unused")
	public static void serverTick(@NotNull World world, BlockPos blockPos, BlockState blockState, CrystallarieumBlockEntity crystallarieum) {
		if (crystallarieum.canWork) {
			transferInk(crystallarieum);
			
			var recipe = crystallarieum.currentRecipe;
			if (recipe != null) {
				crystallarieum.tickLooper.tick();
				if (crystallarieum.tickLooper.reachedCap()) {
					tickRecipe(world, blockPos, crystallarieum, recipe);
					crystallarieum.tickLooper.reset();
				}
			}
		}
	}
	
	/**
	 * Progress the recipe
	 * gets called 1/second
	 */
	private static void tickRecipe(@NotNull World world, BlockPos blockPos, CrystallarieumBlockEntity crystallarieum, @NotNull RecipeEntry<CrystallarieumRecipe> recipe) {
		if (crystallarieum.currentCatalyst == CrystallarieumCatalyst.EMPTY && !recipe.value().growsWithoutCatalyst()) {
			return;
		}
		
		if (crystallarieum.inkStorage.getEnergy(recipe.value().getInkColor()) == 0) {
			return;
		}
		
		// advance growing
		float consumedInkFloat = (recipe.value().getInkPerSecond() * crystallarieum.currentCatalyst.growthAccelerationMod() * crystallarieum.currentCatalyst.inkConsumptionMod());
		int consumedInt = Support.getIntFromDecimalWithChance(consumedInkFloat, world.random);
		if (crystallarieum.inkStorage.drainEnergy(recipe.value().getInkColor(), consumedInt) < consumedInt) {
			crystallarieum.canWork = false;
			crystallarieum.setInkDirty();
			crystallarieum.updateInClientWorld();
			return;
		}
		
		crystallarieum.setInkDirty();
		crystallarieum.currentGrowthStageTicks += (int) (SECOND * crystallarieum.currentCatalyst.growthAccelerationMod());
		
		// check if a catalyst should get used up
		if (world.random.nextFloat() < crystallarieum.currentCatalyst.consumeChancePerSecond()) {
			ItemStack catalystStack = crystallarieum.getStack(CATALYST_SLOT_ID);
			catalystStack.decrement(1);
			crystallarieum.updateInClientWorld();
			if (catalystStack.isEmpty()) {
				crystallarieum.currentCatalyst = CrystallarieumCatalyst.EMPTY;
				if (!recipe.value().growsWithoutCatalyst()) {
					crystallarieum.canWork = false;
				}
			}
		}
		
		// advanced enough? grow!
		if (crystallarieum.currentGrowthStageTicks >= recipe.value().getSecondsPerGrowthStage() * SECOND) {
			BlockPos topPos = blockPos.up();
			BlockState topState = world.getBlockState(topPos);
			Optional<BlockState> nextState = recipe.value().getNextState(recipe, topState);
			if (nextState.isPresent()) {
				world.setBlockState(topPos, nextState.get());
				ServerPlayerEntity owner = (ServerPlayerEntity) crystallarieum.getOwnerIfOnline();
				if (owner != null) {
					SpectrumAdvancementCriteria.CRYSTALLARIEUM_GROWING.trigger(owner, (ServerWorld) world, topPos, crystallarieum.getStack(CATALYST_SLOT_ID));
				}
			} else {
				crystallarieum.canWork = false;
			}
			crystallarieum.currentGrowthStageTicks = 0;
		}
	}
	
	private static void transferInk(CrystallarieumBlockEntity crystallarieum) {
		ItemStack inkStorageStack = crystallarieum.getStack(INK_STORAGE_STACK_SLOT_ID);
		if (inkStorageStack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
			InkStorage itemInkStorage = inkStorageItem.getEnergyStorage(inkStorageStack);
			long transferredAmount = InkStorage.transferInk(itemInkStorage, crystallarieum.inkStorage);
			if (transferredAmount > 0) {
				inkStorageItem.setEnergyStorage(inkStorageStack, itemInkStorage);
			}
		}
	}
	
	@Override
	public void inventoryChanged() {
		if (this.currentRecipe == null || world == null) {
			this.currentCatalyst = CrystallarieumCatalyst.EMPTY;
			this.canWork = false;
		} else {
			this.currentCatalyst = this.currentRecipe.value().getCatalyst(getStack(CATALYST_SLOT_ID));
			BlockState topState = this.world.getBlockState(this.pos.up());
			this.canWork = this.currentRecipe.value().getNextState(this.currentRecipe, topState).isPresent()
					&& (this.currentRecipe.value().growsWithoutCatalyst() || this.currentCatalyst != CrystallarieumCatalyst.EMPTY);
		}
		super.inventoryChanged();
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		
		CodecHelper.fromNbt(InkStorageComponent.CODEC, nbt.get("InkStorage")).ifPresent(storage ->
				this.inkStorage = new IndividualCappedInkStorage(storage.maxPerColor(), storage.storedEnergy()));
		if (nbt.contains("Looper", NbtElement.COMPOUND_TYPE)) {
			this.tickLooper = TickLooper.readNbt(nbt.getCompound("Looper"));
		}

		this.canWork = nbt.getBoolean("CanWork");
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		this.currentCatalyst = CrystallarieumCatalyst.EMPTY;
		this.currentRecipe = MultiblockCrafter.getRecipeEntryFromNbt(world, nbt, CrystallarieumRecipe.class);
		this.currentGrowthStageTicks = nbt.getInt("CurrentGrowthStageDuration");
		if (this.currentRecipe != null) {
			this.currentCatalyst = this.currentRecipe.value().getCatalyst(getStack(CATALYST_SLOT_ID));
		}
	}
	
	@Override
	public void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		
		CodecHelper.writeNbt(nbt, "InkStorage", InkStorageComponent.CODEC, new InkStorageComponent(this.inkStorage));
		nbt.put("Looper", this.tickLooper.toNbt());
		
		nbt.putBoolean("CanWork", this.canWork);
		nbt.putInt("CurrentGrowthStageDuration", this.currentGrowthStageTicks);
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
		if (this.currentRecipe != null) {
			nbt.putString("CurrentRecipe", this.currentRecipe.id().toString());
		}
	}
	
	@Override
	public @Nullable UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(PlayerEntity playerEntity) {
		this.ownerUUID = playerEntity.getUuid();
		markDirty();
	}
	
	/**
	 * Searches recipes for a valid one using itemStack and plants the first block of that recipe on top
	 *
	 * @param itemStack stack that is tried to plant on top, if a valid recipe
	 */
	public void acceptStack(ItemStack itemStack, boolean creative, @Nullable UUID player) {
		boolean changed = false;
		
		if (world == null) return;
		if (itemStack.getItem() instanceof InkStorageItem<?> inkStorageItem && inkStorageItem.getDrainability().canDrain(false)) {
			ItemStack currentInkStorageStack = getStack(INK_STORAGE_STACK_SLOT_ID);
			if (currentInkStorageStack.isEmpty()) {
				setStack(INK_STORAGE_STACK_SLOT_ID, itemStack.copy());
				if (!creative) {
					itemStack.setCount(0);
				}
				changed = true;
			}
		} else if (world.getBlockState(pos.up()).isAir()) {
			var recipe = world.getRecipeManager().getFirstMatch(SpectrumRecipeTypes.CRYSTALLARIEUM, new SingleStackRecipeInput(itemStack), world);
			if (recipe.isPresent()) {
				if (!creative) {
					itemStack.decrement(1);
				}
				BlockState placedState = recipe.get().value().getGrowthStages().getFirst();
				world.setBlockState(pos.up(), placedState);
				onTopBlockChange(placedState, recipe.get());
				changed = true;
			}
		} else if (this.currentRecipe != null) {
			ItemStack currentCatalystStack = getStack(CATALYST_SLOT_ID);
			if (currentCatalystStack.isEmpty()) {
				CrystallarieumCatalyst catalyst = this.currentRecipe.value().getCatalyst(itemStack);
				if (catalyst != CrystallarieumCatalyst.EMPTY) {
					setStack(CATALYST_SLOT_ID, itemStack.copy());
					if (!creative) {
						itemStack.setCount(0);
					}
					this.currentCatalyst = catalyst;
					changed = true;
				}
			} else if (ItemStack.areItemsAndComponentsEqual(currentCatalystStack, itemStack)) {
				InventoryHelper.combineStacks(currentCatalystStack, itemStack);
				changed = true;
			}
		}
		
		if (changed) {
			world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 0.8F, 0.8F + world.random.nextFloat() * 0.6F);
			if (player != null) {
				this.ownerUUID = player;
			}
			inventoryChanged();
		}
	}
	
	/**
	 * Triggered when the block on top of the crystallarieum has changed
	 * Sets the new recipe matching that block state
	 *
	 * @param newState the new block state on top
	 * @param recipe   optionally the matching CrystallarieumRecipe. If null is passed it will be calculated
	 */
	public void onTopBlockChange(BlockState newState, @Nullable RecipeEntry<CrystallarieumRecipe> recipe) {
		if (world == null) return;
		if (newState.isAir()) { // fast fail
			this.currentRecipe = null;
			this.canWork = false;
			markDirty();
			updateInClientWorld();
		} else {
			this.currentRecipe = recipe == null ? CrystallarieumRecipe.getRecipeForState(world, newState) : recipe;
			if (this.currentRecipe != null) {
				ItemStack catalystStack = getStack(CATALYST_SLOT_ID);
				if (!catalystStack.isEmpty()) {
					this.currentCatalyst = this.currentRecipe.value().getCatalyst(catalystStack);
					if (this.currentCatalyst == CrystallarieumCatalyst.EMPTY) {
						ItemEntity itemEntity = new ItemEntity(world, this.getPos().getX() + 0.5, this.getPos().getY() + 1, this.getPos().getZ() + 0.5, catalystStack);
						this.setStack(CATALYST_SLOT_ID, ItemStack.EMPTY);
						world.spawnEntity(itemEntity);
					}
				}
			}
			
			inventoryChanged();
		}
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
	
	@Override
	public boolean isValid(int slot, ItemStack stack) {
		if (slot == INK_STORAGE_STACK_SLOT_ID) {
			return stack.getItem() instanceof InkStorageItem;
		} else if (this.currentRecipe != null) {
			return this.currentRecipe.value().getCatalyst(stack) != CrystallarieumCatalyst.EMPTY;
		}
		return false;
	}
	
}
