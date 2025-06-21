package earth.terrarium.pastel.blocks.bottomless_bundle;

import earth.terrarium.pastel.api.item.ItemStorage;
import earth.terrarium.pastel.capabilities.*;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelEnchantmentTags;
import net.minecraft.core.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.NotNull;

public class BottomlessBundleBlockEntity extends BlockEntity implements SidedCapabilityProvider {

	// Do not modify without syncing storage too!
	// Contents are synced from/into storage whenever needed [i.e. (de)serialization or setting/fetching bundle item]
	private ItemStack bundle;
	private ItemStorage innerStorage;

	// Cached to prevent incessant enchantment calls.
	// No need to write that back into the bundle stack.
	private boolean isVoiding;
	protected int powerLevel;

	public BottomlessBundleBlockEntity(BlockPos pos, BlockState state) {
		super(PastelBlockEntities.BOTTOMLESS_BUNDLE.get(), pos, state);
		this.bundle = PastelBlocks.BOTTOMLESS_BUNDLE.get().asItem().getDefaultInstance();
		innerStorage = ItemStorage.load(bundle).copy();
	}

	public IItemHandler storage = new IItemHandler() {
		@Override
		public int getSlots() {
			return 1;
		}

		@Override
		public ItemStack getStackInSlot(int i) {
			return innerStorage.stack((int) Math.min(innerStorage.getCount(), innerStorage.stackSize()));
		}

		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (slot != 0)
				return stack;

			var change = innerStorage.insert(stack);
			var remainder = stack.copyWithCount(stack.getCount() - change);

			if (simulate) {
				innerStorage.extractPure(change);
				// Erm technically, the shimulationch here will schfail if the inchertion goes over Integer.MAX_VALUE - SHUT THE FUCK UP
				return remainder;
			}

			setChanged();
			return isVoiding ? ItemStack.EMPTY : remainder;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			if (slot != 0)
				return ItemStack.EMPTY;

			var result = innerStorage.extract(amount);

			if (simulate) {
				innerStorage.increment(result.getCount());
				return result;
			}

			setChanged();
			return result;
		}

		@Override
		public int getSlotLimit(int slot) {
			return (int) BottomlessBundleItem.getMaxStoredAmount(powerLevel);
		}

		@Override
		public boolean isItemValid(int i, ItemStack itemStack) {
			return false;
		}
	};

	public int getStoredAmount() {
		return (int) innerStorage.getCount();
	}

	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		this.setBundleUnsynced(ItemStack.parse(registryLookup, nbt.getCompound("Bundle"))
				.orElse(PastelBlocks.BOTTOMLESS_BUNDLE.get().asItem().getDefaultInstance()), registryLookup);
		syncStorageWithBundle();
	}

	// Trivial sync methods. Call whenever bundle/storage contents need to be synced with each other [(de)serialization, bundle stack set, bundle block break loot]
	private void syncBundleWithStorage() {
        assert this.level != null;
		innerStorage.copy().save(bundle);
	}

	private void syncStorageWithBundle() {
		innerStorage = ItemStorage.load(bundle).copy();
	}
	
	@Override
	protected void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.saveAdditional(nbt, registryLookup);
		syncBundleWithStorage();
		nbt.put("Bundle", this.bundle.saveOptional(registryLookup));
	}

	private boolean setBundleUnsynced(ItemStack itemStack, HolderLookup.Provider registryLookup) {
		if (itemStack.getItem() instanceof BottomlessBundleItem) {
			this.bundle = itemStack;
			// cache once, use many times
			this.isVoiding = EnchantmentHelper.hasTag(bundle, PastelEnchantmentTags.DELETES_OVERFLOW);
			this.powerLevel = EnchantmentHelper.getItemEnchantmentLevel(registryLookup.lookup(Registries.ENCHANTMENT).flatMap(impl -> impl.get(Enchantments.POWER)).orElse(null), itemStack);
			return true;
		}
		return false;
	}

	public void setBundle(@NotNull ItemStack itemStack, HolderLookup.Provider registryLookup) {
		if (setBundleUnsynced(itemStack, registryLookup)) syncStorageWithBundle();
	}

	public ItemStack retrieveBundle() {
		if (this.bundle.isEmpty()) {
			return PastelBlocks.BOTTOMLESS_BUNDLE.get().asItem().getDefaultInstance();
		} else {
			syncBundleWithStorage();
			return this.bundle;
		}
	}

	@Override
	public IItemHandler exposeItemHandlers(Direction dir) {
		return storage;
	}
}
