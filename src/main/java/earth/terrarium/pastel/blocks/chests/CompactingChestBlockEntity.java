package earth.terrarium.pastel.blocks.chests;

import earth.terrarium.pastel.api.item.ItemReference;
import earth.terrarium.pastel.api.item.ItemStorage;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.inventories.AutoCraftingMode;
import earth.terrarium.pastel.inventories.CompactingChestScreenHandler;
import earth.terrarium.pastel.networking.s2c_payloads.CompactingChestStatusUpdatePayload;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import earth.terrarium.pastel.render.animation.FlowAnimator;
import earth.terrarium.pastel.render.animation.FlowData;
import earth.terrarium.pastel.render.animation.FlowHandlers;
import earth.terrarium.pastel.render.animation.FlowStates;
import earth.terrarium.pastel.render.animation.Interpolation;
import earth.terrarium.pastel.render.animation.KeyFrame;
import net.minecraft.network.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class CompactingChestBlockEntity extends PastelChestBlockEntity {

	private static final FlowAnimator.Factory<CompactingChestBlockEntity> FACTORY;

	@NotNull
	private AutoCraftingMode mode = AutoCraftingMode.X3;
	@NotNull
	private Optional<RecipeHolder<CraftingRecipe>> cachedRecipe = Optional.empty();
	private boolean isOpen;
	public long craftingTimeStamp;

	protected FlowAnimator animator;
	protected FlowData<Float> _piston = FlowData.NULL(), _driver = FlowData.NULL(), _cap = FlowData.NULL();


	private final ContainerData propertyDelegate = new ContainerData() {
		@Override
		public int get(int index) {
			if (index == 0) return mode.ordinal();
			return 0;
		}

		@Override
		public void set(int index, int value) {
			if (index == 0) mode = AutoCraftingMode.values()[value];
		}

		@Override
		public int getCount() {
			return 1;
		}
	};
	
	public CompactingChestBlockEntity(BlockPos blockPos, BlockState blockState) {
		super(PastelBlockEntities.COMPACTING_CHEST.get(), blockPos, blockState);
		inventory.addListener(i -> setChanged());
	}
	
	@SuppressWarnings("unused")
	public static void tick(Level world, BlockPos pos, BlockState state, CompactingChestBlockEntity chest) {
		if (world.isClientSide())
			chest.updateAnimator();
		else
			chest.process();
	}

	private void updateAnimator() {
		if (animator == null)
			animator = FACTORY.create(FlowStates.CLOSED, this);

		animator.tick();

		if (isOpen) {
			animator.swapState(FlowStates.OPEN);
		}
		else if (level.getGameTime() - craftingTimeStamp < 20) {
			animator.swapState(FlowStates.ACTIVE);
		}
		else {
			animator.swapState(FlowStates.CLOSED);
		}
	}

	private void process() {
		var available = InventoryHelper.getAvailableItems(inventory);

		if (cachedRecipe.isEmpty()) {
			findRecipe(available);
			return;
		}

		var recipe = cachedRecipe.get();
		var ref = AutoCraftingMode.getCache(mode).getOrDefault(recipe.id(), ItemReference.empty());

		if (ref.isEmpty())
			return;

		if (!hasEnough(ref, available)) {
			cachedRecipe = Optional.empty();
			return;
		}

		var result = recipe.value().getResultItem(level.registryAccess()).copy();

		if (!ItemHandlerHelper.insertItemStacked(inventory, result, true).isEmpty())
			return;

		ItemHandlerHelper.insertItemStacked(inventory, result, false);
		InventoryHelper.extractFromInventory(inventory, ref, mode.getSize());
		craftingTimeStamp = level.getGameTime();
		produceRunningEffects();

		if (level.getGameTime() % 5 == 0) {
			CompactingChestStatusUpdatePayload.sendCompactingChestStatusUpdate(this);
			((ServerLevel) level).getChunkSource().blockChanged(worldPosition);
		}
	}

	private boolean hasEnough(ItemReference ref, List<ItemStorage> available) {
		if (available.isEmpty())
			return false;

		for (ItemStorage itemStorage : available) {
			if (!itemStorage.getReference().equals(ref))
				continue;

			if (itemStorage.getCount() >= mode.getSize())
				return true;
		}
		return false;
	}

	private void findRecipe(List<ItemStorage> available) {
		CraftingInput input = null;
		ItemReference target = null;

		for (ItemStorage storage : available) {
			if (storage.getCount() >= mode.getSize()) {
				input = mode.createRecipeInput(storage.stack(1)).input();
				target = storage.getReference();

				cachedRecipe = level.getRecipeManager().getRecipeFor(RecipeType.CRAFTING, input, level);
				if (cachedRecipe.isPresent())
					break;
			}
		}

		if (input == null)
			return;

		ItemReference finalTarget = target;
		cachedRecipe.ifPresent(r -> {
			var recipes = AutoCraftingMode.getCache(mode);
			recipes.putIfAbsent(r.id(), finalTarget);
			craftingTimeStamp = level.getGameTime();
			CompactingChestStatusUpdatePayload.sendCompactingChestStatusUpdate(this);
		});
	}
	
	public void produceRunningEffects() {
		var random = level.getRandom();
		if (random.nextFloat() < 0.04F) {
			level.playSound(null, worldPosition, SoundEvents.REDSTONE_TORCH_BURNOUT, SoundSource.BLOCKS, 0.05F + random.nextFloat() * 0.1F, 0.334F + random.nextFloat() / 2F);
			for (int i = 0; i < 4 + random.nextInt(5); i++) {
				((ServerLevel) level).sendParticles(ParticleTypes.CLOUD, worldPosition.getX() + random.nextFloat(), worldPosition.getY() + 1 + random.nextFloat() * 0.667F, worldPosition.getZ() + random.nextFloat(), 0, 0, random.nextFloat() / 20F + 0.02F, 0, 1);
			}
		}
	}
	
	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.pastel.compacting_chest");
	}
	
	@Override
	public void loadAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.loadAdditional(tag, registryLookup);
		if (tag.contains("AutoCraftingMode", Tag.TAG_ANY_NUMERIC)) {
			int autoCraftingModeInt = tag.getInt("AutoCraftingMode");
			this.mode = AutoCraftingMode.values()[autoCraftingModeInt];
		}
	}
	
	@Override
	public void saveAdditional(CompoundTag tag, HolderLookup.Provider registryLookup) {
		super.saveAdditional(tag, registryLookup);
		tag.putInt("AutoCraftingMode", this.mode.ordinal());
	}

	@Override
	public boolean triggerEvent(int type, int data) {
		if (type == 1) {
			isOpen = data > 0;
		}
		return super.triggerEvent(type, data);
	}
	
	@Override
	public SoundEvent getOpenSound() {
		return PastelSoundEvents.COMPACTING_CHEST_OPEN;
	}
	
	@Override
	public SoundEvent getCloseSound() {
		return PastelSoundEvents.COMPACTING_CHEST_CLOSE;
	}
	
	public void applySettings(AutoCraftingMode mode) {
		if (this.mode == mode)
			return;

		this.mode = mode;
		this.cachedRecipe = Optional.empty();
		setChanged();
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return new CompactingChestScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
	}

	@Override
	public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
		BlockPos.STREAM_CODEC.encode(buffer, worldPosition);
	}

	@Override
	public int getContainerSize() {
		return 27;
	}

	static {
		var builder = new FlowAnimator.Builder<>(CompactingChestBlockEntity.class);
		builder.stateInfo(FlowStates.OPEN, 5);
		builder.stateInfo(FlowStates.ACTIVE, 20);
		builder.stateInfo(FlowStates.CLOSED, 14);

		builder.handle("piston", FlowHandlers.FLOAT)
				.initial(0F)
				.loopback(FlowStates.CLOSED)
				.forStates(14F, FlowStates.OPEN)
				.forStates(KeyFrame.sine(0.1F, 5, 4), FlowStates.ACTIVE)
				.interpolate(Interpolation.CUBIC_IN)
				.push();

		builder.handle("driver", FlowHandlers.FLOAT)
				.initial(0F)
				.loopback(FlowStates.CLOSED)
				.forStates(6F, FlowStates.OPEN)
				.forStates(KeyFrame.sine(0.1F, 5, 5, 13), FlowStates.ACTIVE)
				.interpolate(Interpolation.CUBIC_IN)
				.push();

		builder.handle("cap", FlowHandlers.FLOAT)
				.initial(0F)
				.loopback(FlowStates.CLOSED, FlowStates.ACTIVE)
				.forStates(5F, FlowStates.OPEN)
				.interpolate(Interpolation.CUBIC_IN)
				.push();

		FACTORY = builder.build();
	}
}