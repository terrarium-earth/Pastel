package de.dafuqs.spectrum.blocks.energy;

import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.api.energy.storage.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.components.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.inventories.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import de.dafuqs.spectrum.particle.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.recipe.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.listener.*;
import net.minecraft.network.packet.*;
import net.minecraft.network.packet.s2c.play.*;
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

import java.util.*;

public class ColorPickerBlockEntity extends LootableContainerBlockEntity implements PlayerOwned, InkStorageBlockEntity<TotalCappedInkStorage> {
	
	public static final int INVENTORY_SIZE = 2; // input & output slots
	public static final int INPUT_SLOT_ID = 0;
	public static final int OUTPUT_SLOT_ID = 1;
	public static final long TICKS_PER_CONVERSION = 5;
	public static final long STORAGE_AMOUNT = 64 * 64 * 64 * 100;
	
	public DefaultedList<ItemStack> inventory;
	protected TotalCappedInkStorage inkStorage;
	protected boolean paused;
	protected boolean inkDirty;
	protected @Nullable InkConvertingRecipe cachedRecipe;
	protected Optional<InkColor> selectedColor = Optional.empty();
	private UUID ownerUUID;
	private final PropertyDelegate propertyDelegate = new BlockPosDelegate(pos) {
		@Override
		public int get(int index) {
			if (index == 3)
				return selectedColor.isEmpty() ? -1 : SpectrumRegistries.INK_COLOR.getRawId(selectedColor.get());
			return super.get(index);
		}

		@Override
		public void set(int index, int value) {
			if (index == 3)
				selectedColor = value == -1 ? Optional.empty() : Optional.of(SpectrumRegistries.INK_COLOR.get(value));
			else
				super.set(index, value);
		}

		@Override
		public int size() {
			return super.size() + 1;
		}
	};
	
	public ColorPickerBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(SpectrumBlockEntities.COLOR_PICKER, blockPos, blockState);
		
		this.inventory = DefaultedList.ofSize(INVENTORY_SIZE, ItemStack.EMPTY);
		this.inkStorage = new TotalCappedInkStorage(STORAGE_AMOUNT, Map.of());
	}
	
	@SuppressWarnings("unused")
    public static void tick(World world, BlockPos pos, BlockState state, ColorPickerBlockEntity blockEntity) {
		if (!world.isClient) {
			blockEntity.inkDirty = false;
			if (!blockEntity.paused) {
				boolean convertedPigment = false;
				boolean shouldPause = true;
				if (world.getTime() % TICKS_PER_CONVERSION == 0) {
					convertedPigment = blockEntity.tryConvertPigmentToEnergy((ServerWorld) world);
				} else {
					shouldPause = false;
				}
				boolean filledContainer = blockEntity.tryFillInkContainer(); // that's an OR
				
				if (convertedPigment || filledContainer) {
					blockEntity.updateInClientWorld();
					blockEntity.setInkDirty();
					blockEntity.markDirty();
				} else if (shouldPause) {
					blockEntity.paused = true;
				}
			}
		}
	}
	
	@Override
	public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.readNbt(nbt, registryLookup);
		this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
		if (!this.readLootTable(nbt)) {
			Inventories.readNbt(nbt, this.inventory, registryLookup);
		}
		CodecHelper.fromNbt(InkStorageComponent.CODEC, nbt.get("InkStorage")).ifPresent(storage ->
				this.inkStorage = new TotalCappedInkStorage(storage.maxEnergyTotal(), storage.storedEnergy()));
		this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
		if (nbt.contains("SelectedColor", NbtElement.STRING_TYPE)) {
			this.selectedColor = InkColor.ofIdString(nbt.getString("SelectedColor"));
		}
	}
	
	@Override
	protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		super.writeNbt(nbt, registryLookup);
		if (!this.writeLootTable(nbt)) {
			Inventories.writeNbt(nbt, this.inventory, registryLookup);
		}
		CodecHelper.writeNbt(nbt, "InkStorage", InkStorageComponent.CODEC, new InkStorageComponent(this.inkStorage));
		PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
		if (this.selectedColor.isPresent()) {
			nbt.putString("SelectedColor", this.selectedColor.get().getID().toString());
		}
	}
	
	@Override
	protected Text getContainerName() {
		return Text.translatable("block.spectrum.color_picker");
	}
	
	@Override
	protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
		return new ColorPickerScreenHandler(syncId, playerInventory, this.propertyDelegate);
	}
	
	@Override
	public UUID getOwnerUUID() {
		return this.ownerUUID;
	}
	
	@Override
	public void setOwner(PlayerEntity playerEntity) {
		this.ownerUUID = playerEntity.getUuid();
		markDirty();
	}
	
	@Override
	public TotalCappedInkStorage getEnergyStorage() {
		return inkStorage;
	}
	
	@Override
	public void setInkDirty() {
		this.inkDirty = true;
	}
	
	@Override
	public boolean getInkDirty() {
		return inkDirty;
	}
	
	@Override
	protected DefaultedList<ItemStack> getHeldStacks() {
		return this.inventory;
	}
	
	@Override
	protected void setHeldStacks(DefaultedList<ItemStack> list) {
		this.inventory = list;
		this.paused = false;
		updateInClientWorld();
	}
	
	@Override
	public ItemStack removeStack(int slot, int amount) {
		ItemStack itemStack = super.removeStack(slot, amount);
		this.paused = false;
		updateInClientWorld();
		return itemStack;
	}
	
	@Override
	public ItemStack removeStack(int slot) {
		ItemStack itemStack = super.removeStack(slot);
		this.paused = false;
		updateInClientWorld();
		return itemStack;
	}
	
	@Override
	public void setStack(int slot, ItemStack stack) {
		super.setStack(slot, stack);
		this.paused = false;
		updateInClientWorld();
	}
	
	@Override
	public int size() {
		return INVENTORY_SIZE;
	}

	protected boolean tryConvertPigmentToEnergy(ServerWorld world) {
		InkConvertingRecipe recipe = getInkConvertingRecipe(world);
		if (recipe != null) {
			InkColor color = recipe.getInkColor();
			long amount = recipe.getInkAmount();
			if (amount <= this.inkStorage.getRoom(color)) {
				inventory.get(INPUT_SLOT_ID).decrement(1);
				this.inkStorage.addEnergy(color, amount);
				
				if (SpectrumCommon.CONFIG.BlockSoundVolume > 0) {
					world.playSound(null, pos, SpectrumSoundEvents.COLOR_PICKER_PROCESSING, SoundCategory.BLOCKS, SpectrumCommon.CONFIG.BlockSoundVolume / 3, 1.0F);
				}
				PlayParticleWithRandomOffsetAndVelocityPayload.playParticleWithRandomOffsetAndVelocity(world,
						new Vec3d(pos.getX() + 0.5, pos.getY() + 0.7, pos.getZ() + 0.5),
						SpectrumParticleTypes.getFluidRisingParticle(color.getDyeColor()),
						5,
						new Vec3d(0.22, 0.0, 0.22),
						new Vec3d(0.0, 0.1, 0.0)
				);
				
				return true;
			}
		}
		return false;
	}
	
	protected @Nullable InkConvertingRecipe getInkConvertingRecipe(World world) {
		// is the current stack empty?
		ItemStack inputStack = inventory.get(INPUT_SLOT_ID);
		if (inputStack.isEmpty()) {
			this.cachedRecipe = null;
			return null;
		}
		
		// does the cached recipe match?
		if (this.cachedRecipe != null) {
			if (this.cachedRecipe.getIngredients().get(INPUT_SLOT_ID).test(inputStack)) {
				return this.cachedRecipe;
			}
		}
		
		// search matching recipe
		Optional<RecipeEntry<InkConvertingRecipe>> recipe = world.getRecipeManager().getFirstMatch(SpectrumRecipeTypes.INK_CONVERTING, new SingleStackRecipeInput(inventory.get(INPUT_SLOT_ID)), world);
		if (recipe.isPresent()) {
			this.cachedRecipe = recipe.get().value();
			return this.cachedRecipe;
		} else {
			this.cachedRecipe = null;
			return null;
		}
	}
	
	protected boolean tryFillInkContainer() {
		long transferredAmount = 0;
		
		ItemStack stack = inventory.get(OUTPUT_SLOT_ID);
		if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
			InkStorage itemStorage = inkStorageItem.getEnergyStorage(stack);

			ServerPlayerEntity owner = null;
			if (getOwnerIfOnline() instanceof ServerPlayerEntity serverPlayerEntity) {
				owner = serverPlayerEntity;
			}
			
			if (this.selectedColor.isEmpty()) {
				for (InkColor color : InkColors.all()) {
					transferredAmount += tryTransferInk(owner, stack, itemStorage, color);
				}
			} else {
				transferredAmount = tryTransferInk(owner, stack, itemStorage, this.selectedColor.get());
			}
			
			if (transferredAmount > 0) {
				inkStorageItem.setEnergyStorage(stack, itemStorage);
			}
		}
		
		return transferredAmount > 0;
	}

	private long tryTransferInk(ServerPlayerEntity owner, ItemStack stack, InkStorage itemStorage, InkColor color) {
		long amount = InkStorage.transferInk(this.inkStorage, itemStorage, color);
		if (amount > 0 && owner != null) {
			SpectrumAdvancementCriteria.INK_CONTAINER_INTERACTION.trigger(owner, stack, itemStorage, color, amount);
		}
		return amount;
	}
	
	public void setSelectedColor(@Nullable InkColor inkColor) {
		this.selectedColor = Optional.ofNullable(inkColor);
		this.paused = false;
		this.markDirty();
	}
	
	public Optional<InkColor> getSelectedColor() {
		return this.selectedColor;
	}
	
	// Called when the chunk is first loaded to initialize this be
	@Override
	public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
		NbtCompound nbtCompound = new NbtCompound();
		this.writeNbt(nbtCompound, registryLookup);
		return nbtCompound;
	}
	
	@Nullable
	@Override
	public Packet<ClientPlayPacketListener> toUpdatePacket() {
		return BlockEntityUpdateS2CPacket.create(this);
	}
	
	public void updateInClientWorld() {
		if (world != null) {
			world.updateListeners(pos, world.getBlockState(pos), world.getBlockState(pos), Block.NO_REDRAW);
		}
	}
	
	@Override
	public boolean isValid(int slot, ItemStack stack) {
		if (slot == INPUT_SLOT_ID) {
			return InkConvertingRecipe.isInput(stack.getItem());
		}
		if (slot == OUTPUT_SLOT_ID) {
			return stack.getItem() instanceof InkStorageItem<?>;
		}
		return true;
	}
	
}
