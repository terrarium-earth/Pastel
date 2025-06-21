package earth.terrarium.pastel.blocks.pedestal;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.block.MultiblockCrafter;
import earth.terrarium.pastel.api.block.PedestalVariant;
import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.api.item.GemstoneColor;
import earth.terrarium.pastel.blocks.*;
import earth.terrarium.pastel.blocks.upgrade.Upgradeable;
import earth.terrarium.pastel.capabilities.*;
import earth.terrarium.pastel.capabilities.item.*;
import earth.terrarium.pastel.helpers.*;
import earth.terrarium.pastel.inventories.*;
import earth.terrarium.pastel.networking.s2c_payloads.PlayBlockBoundSoundInstancePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayPedestalCraftingFinishedParticlePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PlayPedestalUpgradedParticlePayload;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.progression.PastelAdvancementCriteria;
import earth.terrarium.pastel.recipe.pedestal.BuiltinGemstoneColor;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipeTier;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelMultiblocks;
import earth.terrarium.pastel.registries.PastelRecipeTypes;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.items.*;
import net.neoforged.neoforge.items.wrapper.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.UUID;

public class PedestalBlockEntity extends BaseInventoryBlockEntity implements MultiblockCrafter, SidedCapabilityProvider, StackedContentsCompatible {
	
	public static final int INVENTORY_SIZE = 16; // 9 crafting, 5 gems, 1 craftingTablet, 1 output
	public static final int CRAFTING_TABLET_SLOT_ID = 14;
	public static final int OUTPUT_SLOT_ID = 15;
	
	private static final int[] ACCESSIBLE_SLOTS_UP = {0, 9};
	private static final int[] ACCESSIBLE_SLOTS_BASIC = {9, 3};
	private static final int[] ACCESSIBLE_SLOTS_ADVANCED = {9, 4};
	private static final int[] ACCESSIBLE_SLOTS_COMPLEX = {9, 5};
	
	protected UUID ownerUUID;
	protected PedestalVariant pedestalVariant;
	protected FriendlyStackHandler inventory;
	protected boolean shouldCraft;
	protected float storedXP;
	protected PedestalRecipeTier cachedMaxPedestalTier;
	protected long cachedMaxPedestalTierTick;
	protected UpgradeHolder upgrades;
	protected boolean inventoryChanged;
	public @Nullable RecipeHolder<?> currentRecipe;
	
	protected final CraftingDelegate propertyDelegate = new CraftingDelegate();
	
	public PedestalBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(PastelBlockEntities.PEDESTAL.get(), blockPos, blockState);
		
		if (blockState.getBlock() instanceof PedestalBlock pedestalBlock) {
			this.pedestalVariant = pedestalBlock.getVariant();
		} else {
			this.pedestalVariant = BuiltinPedestalVariant.BASIC_AMETHYST;
		}
		
		this.inventory = new FriendlyStackHandler(INVENTORY_SIZE);
	}
	
	public void updateInClientWorld() {
		if (level instanceof ServerLevel serverWorld)
			serverWorld.getChunkSource().blockChanged(worldPosition);
	}

	@Override
	protected FriendlyStackHandler getHandler() {
		return inventory;
	}
	
	@SuppressWarnings("unused")
	public static void clientTick(@NotNull Level world, BlockPos blockPos, BlockState blockState, PedestalBlockEntity pedestalBlockEntity) {
		Recipe<?> currentRecipe = pedestalBlockEntity.getCurrentRecipe();
		if (currentRecipe instanceof PedestalRecipe pedestalRecipe) {
			Map<GemstoneColor, Integer> gemstonePowderInputs = pedestalRecipe.getPowderInputs();
			
			for (Map.Entry<GemstoneColor, Integer> entry : gemstonePowderInputs.entrySet()) {
				int amount = entry.getValue();
				if (amount > 0) {
					ParticleOptions particleEffect = ColoredCraftingParticleEffect.of(entry.getKey().getColor());
					
					float particleAmount = Support.getIntFromDecimalWithChance(amount * 0.125, world.random);
					for (int i = 0; i < particleAmount; i++) {
						float randomX = 2.0F - world.getRandom().nextFloat() * 5;
						float randomZ = 2.0F - world.getRandom().nextFloat() * 5;
						world.addParticle(particleEffect, blockPos.getX() + randomX, blockPos.getY(), blockPos.getZ() + randomZ, 0.0D, 0.03D, 0.0D);
					}
				}
			}
		}
	}
	
	public static void spawnCraftingStartParticles(@NotNull Level world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		if (blockEntity instanceof PedestalBlockEntity pedestalBlockEntity) {
			Recipe<?> currentRecipe = pedestalBlockEntity.getCurrentRecipe();
			if (currentRecipe instanceof PedestalRecipe pedestalRecipe) {
				Map<GemstoneColor, Integer> gemstonePowderInputs = pedestalRecipe.getPowderInputs();
				
				for (Map.Entry<GemstoneColor, Integer> entry : gemstonePowderInputs.entrySet()) {
					int amount = entry.getValue();
					if (amount > 0) {
						ParticleOptions particleEffect = ColoredCraftingParticleEffect.of(entry.getKey().getColor());
						
						amount = amount * 4;
						for (int i = 0; i < amount; i++) {
							Direction direction = Direction.getRandom(world.random);
							if (direction != Direction.DOWN) {
								BlockPos offsetPos = blockPos.relative(direction);
								BlockState offsetState = world.getBlockState(offsetPos);
								if (!offsetState.isFaceSturdy(world, offsetPos, direction.getOpposite())) {
									double d = direction.getStepX() == 0 ? world.random.nextDouble() : 0.5D + (double) direction.getStepX() * 0.6D;
									double e = direction.getStepY() == 0 ? world.random.nextDouble() : 0.5D + (double) direction.getStepY() * 0.6D;
									double f = direction.getStepZ() == 0 ? world.random.nextDouble() : 0.5D + (double) direction.getStepZ() * 0.6D;
									world.addParticle(particleEffect, (double) blockPos.getX() + d, (double) blockPos.getY() + e, (double) blockPos.getZ() + f, 0.0D, 0.03D, 0.0D);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public static void serverTick(@NotNull Level world, BlockPos blockPos, BlockState blockState, PedestalBlockEntity pedestalBlockEntity) {
		if (pedestalBlockEntity.upgrades == null) {
			pedestalBlockEntity.calculateUpgrades();
		}
		
		// check recipe crafted last tick => performance
		boolean shouldMarkDirty = false;
		
		var calculatedEntry = calculateRecipe(world, pedestalBlockEntity);
		var calculatedRecipe = calculatedEntry == null ? null : calculatedEntry.value();
		pedestalBlockEntity.inventoryChanged = false;
		if (pedestalBlockEntity.currentRecipe != calculatedEntry) {
			pedestalBlockEntity.shouldCraft = false;
			pedestalBlockEntity.currentRecipe = calculatedEntry;
			pedestalBlockEntity.propertyDelegate.craftingTime = 0;
			if (calculatedRecipe instanceof PedestalRecipe calculatedPedestalRecipe) {
				pedestalBlockEntity.propertyDelegate.craftingTimeTotal = (int) Math.ceil(calculatedPedestalRecipe.getCraftingTime() / pedestalBlockEntity.upgrades.getEffectiveValue(UpgradeType.SPEED));
				
				Player player = pedestalBlockEntity.getOwnerIfOnline();
				if (player instanceof ServerPlayer serverPlayerEntity) {
					PastelAdvancementCriteria.PEDESTAL_RECIPE_CALCULATED.trigger(serverPlayerEntity, calculatedPedestalRecipe.assemble(pedestalBlockEntity.createRecipeInput(), world.registryAccess()), (int) calculatedPedestalRecipe.getExperience(), pedestalBlockEntity.propertyDelegate.craftingTimeTotal);
				}
			} else {
				pedestalBlockEntity.propertyDelegate.craftingTimeTotal = (int) Math.ceil(PastelCommon.CONFIG.VanillaRecipeCraftingTimeTicks / pedestalBlockEntity.upgrades.getEffectiveValue(UpgradeType.SPEED));
			}
			pedestalBlockEntity.setChanged();
			PlayBlockBoundSoundInstancePayload.sendCancelBlockBoundSoundInstance((ServerLevel) pedestalBlockEntity.getLevel(), pedestalBlockEntity.getBlockPos());
			pedestalBlockEntity.updateInClientWorld();
		}
		
		// only craft when there is redstone power
		if (pedestalBlockEntity.propertyDelegate.craftingTime == 0 && !pedestalBlockEntity.shouldCraft && !(blockState.getBlock() instanceof PedestalBlock && blockState.getValue(BlockStateProperties.POWERED))) {
			return;
		}
		
		int maxCountPerStack = pedestalBlockEntity.getMaxStackSize();
		// Pedestal crafting
		boolean craftingFinished = false;
		if (calculatedRecipe instanceof PedestalRecipe pedestalRecipe && pedestalBlockEntity.canAcceptRecipeOutput(calculatedRecipe, pedestalBlockEntity.createRecipeInput(), maxCountPerStack)) {
			pedestalBlockEntity.propertyDelegate.craftingTime++;
			if (pedestalBlockEntity.propertyDelegate.craftingTime == pedestalBlockEntity.propertyDelegate.craftingTimeTotal) {
				pedestalBlockEntity.propertyDelegate.craftingTime = 0;
				craftingFinished = craftPedestalRecipe(pedestalBlockEntity, pedestalRecipe, pedestalBlockEntity, maxCountPerStack);
				if (craftingFinished) {
					pedestalBlockEntity.inventoryChanged = true;
				}
				shouldMarkDirty = true;
			}
			// Vanilla crafting
		} else if (calculatedRecipe instanceof CraftingRecipe vanillaCraftingRecipe && pedestalBlockEntity.canAcceptRecipeOutput(calculatedRecipe, pedestalBlockEntity.createRecipeInput(), maxCountPerStack)) {
			pedestalBlockEntity.propertyDelegate.craftingTime++;
			if (pedestalBlockEntity.propertyDelegate.craftingTime == pedestalBlockEntity.propertyDelegate.craftingTimeTotal) {
				pedestalBlockEntity.propertyDelegate.craftingTime = 0;
				craftingFinished = pedestalBlockEntity.craftVanillaRecipe(vanillaCraftingRecipe, pedestalBlockEntity, maxCountPerStack);
				if (craftingFinished) {
					playCraftingFinishedSoundEvent(pedestalBlockEntity, calculatedRecipe);
					pedestalBlockEntity.inventoryChanged = true;
				}
				shouldMarkDirty = true;
			}
		}
		
		if (pedestalBlockEntity.propertyDelegate.craftingTime == 1 && pedestalBlockEntity.propertyDelegate.craftingTimeTotal > 1) {
			PlayBlockBoundSoundInstancePayload.sendPlayBlockBoundSoundInstance(PastelSoundEvents.PEDESTAL_CRAFTING, (ServerLevel) pedestalBlockEntity.getLevel(), pedestalBlockEntity.getBlockPos(), pedestalBlockEntity.propertyDelegate.craftingTimeTotal - pedestalBlockEntity.propertyDelegate.craftingTime);
		}
		
		// try to output the currently stored output stack
		ItemStack outputItemStack = pedestalBlockEntity.inventory.getStackInSlot(OUTPUT_SLOT_ID);
		if (outputItemStack != ItemStack.EMPTY) {
			if (world.getBlockState(blockPos.above()).getCollisionShape(world, blockPos.above()).isEmpty()) {
				spawnOutputAsItemEntity((ServerLevel) world, blockPos, pedestalBlockEntity, outputItemStack);
			} else {
				int remaining = 0;
				remaining += ItemHandlerHelper.insertItemStacked(world.getCapability(Capabilities.ItemHandler.BLOCK, blockPos.below(), Direction.UP), outputItemStack, false).getCount();

				if (remaining > 0) {
					remaining = 0;
					remaining += ItemHandlerHelper.insertItemStacked(world.getCapability(Capabilities.ItemHandler.BLOCK, blockPos.below(), Direction.DOWN), outputItemStack, false).getCount();
				}

				if (remaining == 0) {
					shouldMarkDirty = true;
				} else {
					// play sound when the entity can not put its output anywhere
					if (craftingFinished) {
						pedestalBlockEntity.playSound(SoundEvents.LAVA_EXTINGUISH);
					}
				}
			}
		}
		
		if (shouldMarkDirty) {
			setChanged(world, blockPos, blockState);
		}
	}
	
	@Contract(pure = true)
	public static PedestalVariant getVariant(@NotNull PedestalBlockEntity pedestalBlockEntity) {
		return pedestalBlockEntity.pedestalVariant;
	}
	
	public static void spawnOutputAsItemEntity(ServerLevel world, BlockPos blockPos, @NotNull PedestalBlockEntity pedestalBlockEntity, ItemStack outputItemStack) {
		// spawn crafting output
		MultiblockCrafter.spawnItemStackAsEntitySplitViaMaxCount(world, pedestalBlockEntity.worldPosition, outputItemStack, outputItemStack.getCount(), new Vec3(0, 0.1, 0));
		pedestalBlockEntity.inventory.setStackInSlot(OUTPUT_SLOT_ID, ItemStack.EMPTY);
		
		// spawn XP
		MultiblockCrafter.spawnExperience(world, pedestalBlockEntity.worldPosition, pedestalBlockEntity.storedXP, world.random);
		pedestalBlockEntity.storedXP = 0;
		
		// only triggered on server side. Therefore, has to be sent to client via S2C packet
		PlayPedestalCraftingFinishedParticlePayload.sendPlayPedestalCraftingFinishedParticle(world, blockPos, outputItemStack);
	}
	
	public static boolean tryPutIntoInventory(PedestalBlockEntity pedestalBlockEntity, Container targetInventory, ItemStack outputItemStack) {
		ItemStack remainingStack = InventoryHelper.smartAddToInventory(outputItemStack, new InvWrapper(targetInventory), Direction.DOWN);
		if (remainingStack.isEmpty()) {
			pedestalBlockEntity.inventory.setStackInSlot(OUTPUT_SLOT_ID, ItemStack.EMPTY);
			return true;
		} else {
			pedestalBlockEntity.inventory.setStackInSlot(OUTPUT_SLOT_ID, remainingStack);
			return false;
		}
	}
	
	public static void playCraftingFinishedSoundEvent(PedestalBlockEntity pedestalBlockEntity, Recipe<?> craftingRecipe) {
		Level world = pedestalBlockEntity.getLevel();
		if (world != null && craftingRecipe instanceof PedestalRecipe pedestalRecipe) {
			pedestalBlockEntity.playSound(pedestalRecipe.getSoundEvent(world.random));
		} else {
			pedestalBlockEntity.playSound(PastelSoundEvents.PEDESTAL_CRAFTING_FINISHED_GENERIC);
		}
	}
	
	public static @Nullable RecipeHolder<?> calculateRecipe(Level world, @NotNull PedestalBlockEntity pedestalBlockEntity) {
		var currentRecipe = pedestalBlockEntity.getCurrentRecipe();
		
		if (!pedestalBlockEntity.inventoryChanged) {
			return pedestalBlockEntity.currentRecipe;
		}
		
		// unchanged pedestal recipe?
		if (currentRecipe instanceof PedestalRecipe pedestalRecipe && pedestalRecipe.matches(pedestalBlockEntity.createRecipeInput(), world)) {
			return pedestalBlockEntity.currentRecipe;
		}
		
		// unchanged vanilla recipe?
		if (PastelCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
			if (currentRecipe instanceof CraftingRecipe craftingRecipe && craftingRecipe.matches(pedestalBlockEntity.createRecipeInput().getCraftingGridInput(), world)) {
				return pedestalBlockEntity.currentRecipe;
			}
		}
		
		// current recipe does not match last recipe
		// => search valid recipe
		var pedestalRecipe = world.getRecipeManager().getRecipeFor(PastelRecipeTypes.PEDESTAL, pedestalBlockEntity.createRecipeInput(), world).orElse(null);
		if (pedestalRecipe == null) {
			if (PastelCommon.CONFIG.canPedestalCraftVanillaRecipes()) {
				return world.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, pedestalBlockEntity.createRecipeInput().getCraftingGridInput(), world).orElse(null);
			}
			return null;
		}
		
		if (!pedestalRecipe.value().canCraft(pedestalBlockEntity)) {
			return null;
		}
		return pedestalRecipe;
	}
	
	private boolean canAcceptRecipeOutput(@Nullable Recipe<?> recipe, PedestalRecipeInput input, int maxCountPerStack) {
		if (recipe != null) {
			ItemStack output;
			if (recipe instanceof PedestalRecipe pedestalRecipe) {
				output = pedestalRecipe.assemble(input, null);
			} else if (recipe instanceof CraftingRecipe craftingRecipe) {
				output = craftingRecipe.assemble(input.getCraftingGridInput(), null);
			} else {
				output = ItemStack.EMPTY;
			}
			
			if (output.isEmpty()) {
				return false;
			}
			
			ItemStack existingOutput = this.getItem(OUTPUT_SLOT_ID);
			if (existingOutput.isEmpty()) {
				return true;
			}
			
			if (!ItemStack.isSameItemSameComponents(existingOutput, output)) {
				return false;
			}
			
			if (existingOutput.getCount() < maxCountPerStack && existingOutput.getCount() < existingOutput.getMaxStackSize()) {
				return true;
			}
			
			return existingOutput.getCount() < output.getMaxStackSize();
		}
		
		return false;
	}
	
	private static boolean craftPedestalRecipe(PedestalBlockEntity pedestalBlockEntity, @Nullable PedestalRecipe recipe, Container inventory, int maxCountPerStack) {
		var world = pedestalBlockEntity.level;
		if (world == null || !pedestalBlockEntity.canAcceptRecipeOutput(recipe, pedestalBlockEntity.createRecipeInput(), maxCountPerStack)) {
			return false;
		}
		
		ItemStack outputStack = recipe.assemble(pedestalBlockEntity.createRecipeInput(), world.registryAccess());
		recipe.consumeIngredients(pedestalBlockEntity);
		
		if (!recipe.areYieldUpgradesDisabled()) {
			double yieldModifier = pedestalBlockEntity.upgrades.getEffectiveValue(UpgradeType.YIELD);
			if (yieldModifier != 1.0) {
				int modifiedCount = Support.getIntFromDecimalWithChance(outputStack.getCount() * yieldModifier, world.random);
				outputStack.setCount(Math.min(outputStack.getMaxStackSize(), modifiedCount));
			}
		}
		
		// Add the XP for this recipe to the pedestal
		float experience = recipe.getExperience() * pedestalBlockEntity.upgrades.getEffectiveValue(UpgradeType.EXPERIENCE);
		pedestalBlockEntity.storedXP += experience;
		
		Player player = pedestalBlockEntity.getOwnerIfOnline();
		//TODO revisit onCraftByCrafter (see OfflineDataLookup)
		if (player != null) {
			outputStack.onCraftedBy(world, player, outputStack.getCount());
		}
		
		// trigger advancements
		pedestalBlockEntity.grantPlayerPedestalCraftingAdvancement(outputStack, (int) experience, pedestalBlockEntity.propertyDelegate.craftingTimeTotal);
		
		// if it was a recipe to upgrade the pedestal itself
		// => upgrade
		PedestalVariant newPedestalVariant = PedestalRecipe.getUpgradedPedestalVariantForOutput(outputStack);
		if (newPedestalVariant != null && newPedestalVariant.isBetterThan(getVariant(pedestalBlockEntity))) {
			// It is an upgrade recipe (output is a pedestal block item)
			// => Upgrade
			pedestalBlockEntity.playSound(PastelSoundEvents.PEDESTAL_UPGRADE);
			PedestalBlock.upgradeToVariant(world, pedestalBlockEntity.getBlockPos(), newPedestalVariant);
			PlayPedestalUpgradedParticlePayload.spawnPedestalUpgradeParticles(world, pedestalBlockEntity.worldPosition, newPedestalVariant);
			
			pedestalBlockEntity.pedestalVariant = newPedestalVariant;
			pedestalBlockEntity.currentRecipe = null; // reset the recipe, otherwise pedestal would remember crafting the update
		} else {
			// Not an upgrade recipe => Add output to output slot
			ItemStack existingOutput = inventory.getItem(OUTPUT_SLOT_ID);
			if (!existingOutput.isEmpty()) {
				// merge existing & newly crafted stacks
				// protection against stacks > max stack size
				outputStack.setCount(Math.min(existingOutput.getMaxStackSize(), existingOutput.getCount() + outputStack.getCount()));
			}
			inventory.setItem(OUTPUT_SLOT_ID, outputStack);
		}
		
		pedestalBlockEntity.setChanged();
		pedestalBlockEntity.inventoryChanged = true;
		pedestalBlockEntity.updateInClientWorld();
		
		return true;
	}
	
	public static Item getGemstonePowderItemForSlot(int slot) {
		return switch (slot) {
			case 9 -> PastelItems.TOPAZ_POWDER.get();
			case 10 -> PastelItems.AMETHYST_POWDER.get();
			case 11 -> PastelItems.CITRINE_POWDER.get();
			case 12 -> PastelItems.ONYX_POWDER.get();
			case 13 -> PastelItems.MOONSTONE_POWDER.get();
			default -> Items.AIR;
		};
	}
	
	public static int getSlotForGemstonePowder(GemstoneColor gemstoneColor) {
		return switch (gemstoneColor) {
			case BuiltinGemstoneColor.CYAN -> 9;
			case BuiltinGemstoneColor.MAGENTA -> 10;
			case BuiltinGemstoneColor.YELLOW -> 11;
			case BuiltinGemstoneColor.BLACK -> 12;
			case BuiltinGemstoneColor.WHITE -> 13;
			default -> -1;
		};
	}
	
	public void setVariant(PedestalVariant pedestalVariant) {
		this.pedestalVariant = pedestalVariant;
	}
	
	public PedestalVariant getVariant() {
		return pedestalVariant;
	}
	
	@Override
	public Component getDefaultName() {
		return Component.translatable("block.pastel.pedestal");
	}
	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return new PedestalScreenHandler(syncId, playerInventory, this, this.propertyDelegate, this.getPedestalTier(), this.getHighestAvailableRecipeTier());
	}

	@Override
	public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
		PedestalScreenHandler.ScreenOpeningData.STREAM_CODEC.encode(buffer, new PedestalScreenHandler.ScreenOpeningData(this.getBlockPos(), this.getPedestalTier(), this.getHighestAvailableRecipeTier()));
	}
	
	@Override
	public boolean stillValid(Player player) {
		if (level == null || level.getBlockEntity(worldPosition) != this) {
			return false;
		} else {
			return player.distanceToSqr((double) worldPosition.getX() + 0.5D, (double) worldPosition.getY() + 0.5D, (double) worldPosition.getZ() + 0.5D) <= 64.0D;
		}
	}
	
	@Override
	public void fillStackedContents(StackedContents recipeMatcher) {
		for (ItemStack itemStack : this.inventory.getInternalList()) {
			recipeMatcher.accountStack(itemStack);
		}
	}
	
	@Nullable
	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}
	
	// Called when the chunk is first loaded to initialize this be or manually synced via updateInClientWorld()
	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		CompoundTag nbtCompound = new CompoundTag();
		this.saveAdditional(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		
		inventory.load(nbt, registryLookup);
		
		if (nbt.contains("StoredXP")) {
			this.storedXP = nbt.getFloat("StoredXP");
		}
		if (nbt.contains("CraftingTime")) {
			this.propertyDelegate.craftingTime = nbt.getShort("CraftingTime");
		}
		if (nbt.contains("CraftingTimeTotal")) {
			this.propertyDelegate.craftingTimeTotal = nbt.getShort("CraftingTimeTotal");
		}
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		if (nbt.contains("Upgrades", Tag.TAG_LIST)) {
			this.upgrades = UpgradeHolder.fromNbt(nbt.getList("Upgrades", Tag.TAG_COMPOUND));
		} else {
			this.upgrades = new UpgradeHolder();
		}
		if (nbt.contains("inventory_changed")) {
			this.inventoryChanged = nbt.getBoolean("inventory_changed");
		}
		
		this.currentRecipe = null;
		this.currentRecipe = MultiblockCrafter.getRecipeEntryFromNbt(level, nbt);
	}
	
	@Override
	public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		nbt.putFloat("StoredXP", this.storedXP);
		nbt.putShort("CraftingTime", (short) this.propertyDelegate.craftingTime);
		nbt.putShort("CraftingTimeTotal", (short) this.propertyDelegate.craftingTimeTotal);
		nbt.putBoolean("inventory_changed", this.inventoryChanged);
		
		if (this.upgrades != null) {
			nbt.put("Upgrades", this.upgrades.toNbt());
		}
		if (this.currentRecipe != null) {
			nbt.putString("CurrentRecipe", this.currentRecipe.id().toString());
		}
		
		inventory.save(nbt, registryLookup);
	}
	
	public boolean isCrafting() {
		return this.propertyDelegate.craftingTime > 0;
	}
	
	private void playSound(SoundEvent soundEvent) {
		if (level == null) return;
		level.playSound(null, worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), soundEvent, SoundSource.BLOCKS, 0.9F + level.random.nextFloat() * 0.2F, 0.9F + level.random.nextFloat() * 0.15F);
	}
	
	private boolean craftVanillaRecipe(@Nullable CraftingRecipe recipe, PedestalBlockEntity pedestal, int maxCountPerStack) {
		if (canAcceptRecipeOutput(recipe, createRecipeInput(), maxCountPerStack)) {
			ItemStack recipeOutput = recipe.assemble(createRecipeInput().getCraftingGridInput(), null);
			Player player = getOwnerIfOnline();
			//TODO revise for non-player crafting
			if (player == null) {
				recipeOutput.onCraftedBySystem(this.getLevel());
			} else {
				recipeOutput.onCraftedBy(this.getLevel(), player, recipeOutput.getCount());
			}
			
			// -1 for all crafting inputs
			decrementInputStacks(pedestal);
			
			ItemStack existingOutput = pedestal.getItem(OUTPUT_SLOT_ID);
			if (existingOutput.isEmpty()) {
				pedestal.setItem(OUTPUT_SLOT_ID, recipeOutput.copy());
			} else {
				existingOutput.grow(recipeOutput.getCount());
				pedestal.setItem(OUTPUT_SLOT_ID, existingOutput);
			}
			
			return true;
		} else {
			return false;
		}
	}
	
	private void decrementInputStacks(Container inventory) {
		if (level == null) return;
		for (int i = 0; i < 9; i++) {
			ItemStack itemStack = inventory.getItem(i);
			if (!itemStack.isEmpty()) {
				ItemStack remainder = itemStack.getCraftingRemainingItem();
				if (remainder.isEmpty()) {
					itemStack.shrink(1);
				} else {
					if (this.inventory.getStackInSlot(i).getCount() == 1) {
						this.inventory.setStackInSlot(i, remainder);
					} else {
						this.inventory.getStackInSlot(i).shrink(1);
						ItemEntity itemEntity = new ItemEntity(level, worldPosition.getX() + 0.5, worldPosition.getY() + 1, worldPosition.getZ() + 0.5, remainder);
						itemEntity.push(0, 0.05, 0);
						level.addFreshEntity(itemEntity);
					}
				}
			}
		}
	}
	
	private void grantPlayerPedestalCraftingAdvancement(ItemStack output, int experience, int duration) {
		ServerPlayer serverPlayerEntity = (ServerPlayer) getOwnerIfOnline();
		if (serverPlayerEntity != null) {
			PastelAdvancementCriteria.PEDESTAL_CRAFTING.trigger(serverPlayerEntity, output, experience, duration);
		}
	}
	
	@Override
	public boolean canPlaceItem(int slot, ItemStack stack) {
		if (slot < 9) {
			return true;
		} else if (slot == CRAFTING_TABLET_SLOT_ID && stack.is(PastelItems.CRAFTING_TABLET.get())) {
			return true;
		} else {
			return stack.is(getGemstonePowderItemForSlot(slot));
		}
	}

	public int[] getSlotsForFace(Direction side) {
		if (side == Direction.DOWN) {
			return new int[]{OUTPUT_SLOT_ID, 1};
		} else if (side == Direction.UP) {
			return ACCESSIBLE_SLOTS_UP;
		} else {
			switch (this.pedestalVariant.getRecipeTier()) {
				case COMPLEX -> {
					return ACCESSIBLE_SLOTS_COMPLEX;
				}
				case ADVANCED -> {
					return ACCESSIBLE_SLOTS_ADVANCED;
				}
				default -> {
					return ACCESSIBLE_SLOTS_BASIC;
				}
			}
		}
	}
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	public @Nullable Recipe<?> getCurrentRecipe() {
		return currentRecipe == null ? null : currentRecipe.value();
	}
	
	public PedestalRecipeInput createRecipeInput() {
		return PedestalRecipeInput.create(this.inventory.getInternalList(), getOwnerIfOnline());
	}
	
	public CraftingInput.Positioned createPositionedInput() {
		return CraftingInput.ofPositioned(3, 3, inventory.getInternalList());
	}
	
	public ItemStack getCurrentCraftingRecipeOutput() {
		var currentRecipe = getCurrentRecipe();
		if (level == null || currentRecipe == null) {
			return ItemStack.EMPTY;
		}
		
		if (currentRecipe instanceof PedestalRecipe pedestalRecipe) {
			return pedestalRecipe.assemble(createRecipeInput(), level.registryAccess());
		}
		
		if (currentRecipe instanceof CraftingRecipe craftingRecipe) {
			return craftingRecipe.assemble(createRecipeInput().getCraftingGridInput(), null);
		}
		
		return ItemStack.EMPTY;
	}
	
	public PedestalRecipeTier getHighestAvailableRecipeTier() {
		if (level == null || level.getGameTime() <= cachedMaxPedestalTierTick + 20) {
			return cachedMaxPedestalTier;
		} else {
			PedestalRecipeTier pedestalTier = getPedestalTier();
			PedestalRecipeTier structureTier = getStructureTier();
			
			PedestalRecipeTier denominator = PedestalRecipeTier.values()[Math.min(pedestalTier.ordinal(), structureTier.ordinal())];
			cachedMaxPedestalTier = denominator;
			cachedMaxPedestalTierTick = level.getGameTime();
			
			return denominator;
		}
	}
	
	private PedestalRecipeTier getPedestalTier() {
		return this.pedestalVariant.getRecipeTier();
	}
	
	
	@NotNull
	private PedestalRecipeTier getStructureTier() {
		Multiblock multiblock;
		
		multiblock = PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_COMPLEX);
		if (multiblock.validate(level, worldPosition.below(), Rotation.NONE)) {
			PastelAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger((ServerPlayer) this.getOwnerIfOnline(), multiblock);
			return PedestalRecipeTier.COMPLEX;
		}
		
		multiblock = PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_COMPLEX_WITHOUT_MOONSTONE);
		if (multiblock.validate(level, worldPosition.below(), Rotation.NONE)) {
			PastelAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger((ServerPlayer) this.getOwnerIfOnline(), multiblock);
			return PedestalRecipeTier.ADVANCED;
		}
		
		multiblock = PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_ADVANCED);
		if (multiblock.validate(level, worldPosition.below(), Rotation.NONE)) {
			PastelAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger((ServerPlayer) this.getOwnerIfOnline(), multiblock);
			return PedestalRecipeTier.ADVANCED;
		}
		
		multiblock = PastelMultiblocks.get(PastelMultiblocks.PEDESTAL_SIMPLE);
		if (multiblock.validate(level, worldPosition.below(), Rotation.NONE)) {
			PastelAdvancementCriteria.COMPLETED_MULTIBLOCK.trigger((ServerPlayer) this.getOwnerIfOnline(), multiblock);
			return PedestalRecipeTier.SIMPLE;
		}
		
		return PedestalRecipeTier.BASIC;
	}
	
	@Override
	public void resetUpgrades() {
		this.upgrades = null;
		this.setChanged();
	}
	
	/**
	 * Search for upgrades at valid positions and apply
	 */
	@Override
	public void calculateUpgrades() {
		this.upgrades = Upgradeable.calculateUpgradeMods4(level, worldPosition, 3, 2, this.ownerUUID);
		this.setChanged();
	}
	
	@Override
	public UpgradeHolder getUpgradeHolder() {
		return this.upgrades;
	}
	
	@Override
	public void setOwner(Player playerEntity) {
		this.ownerUUID = playerEntity.getUUID();
		setChanged();
	}

	@Override
	protected void notifyInventoryUpdate() {
		setInventoryChanged();
	}

	public void setInventoryChanged() {
		this.inventoryChanged = true;
	}

	@Override
	public IItemHandler exposeItemHandlers(Direction dir) {
		var slots = getSlotsForFace(dir);
		var view = new StackHandlerView(inventory, slots[0], slots[1]);

		if (dir == Direction.DOWN)
			return view.disableInsertion();
		else
			return view.disableExtraction();
	}
}
