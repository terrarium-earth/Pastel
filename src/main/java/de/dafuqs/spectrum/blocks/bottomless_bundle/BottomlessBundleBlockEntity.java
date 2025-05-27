package de.dafuqs.spectrum.blocks.bottomless_bundle;

import de.dafuqs.spectrum.capabilities.*;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import de.dafuqs.spectrum.registries.SpectrumEnchantmentTags;
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

	// Cached to prevent incessant enchantment calls.
	// No need to write that back into the bundle stack.
	private boolean isVoiding;
	protected int powerLevel;

	public ItemStackHandler storage = new ItemStackHandler(1) {
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			if (!(stacks.getFirst().isEmpty() || ItemStack.isSameItemSameComponents(stack, stacks.getFirst())
					&& stack.getItem().canFitInsideContainerItems()))
				return stack;

			var remainder = insertItem(0, stack, simulate);

			if (!simulate)
				setChanged();

			return isVoiding ? ItemStack.EMPTY : remainder;
		}

		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			var result = super.extractItem(slot, amount, simulate);
			if (!simulate)
				setChanged();

			return result;
		}

		@Override
		public int getSlotLimit(int slot) {
			return (int) BottomlessBundleItem.getMaxStoredAmount(powerLevel);
		}
	};

	public BottomlessBundleBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.BOTTOMLESS_BUNDLE, pos, state);
		this.bundle = SpectrumBlocks.BOTTOMLESS_BUNDLE.get().asItem().getDefaultInstance();
	}

	@Override
	public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
		super.loadAdditional(nbt, registryLookup);
		this.setBundleUnsynced(ItemStack.parse(registryLookup, nbt.getCompound("Bundle"))
				.orElse(SpectrumBlocks.BOTTOMLESS_BUNDLE.get().asItem().getDefaultInstance()), registryLookup);
		syncStorageWithBundle();
	}

	// Trivial sync methods. Call whenever bundle/storage contents need to be synced with each other [(de)serialization, bundle stack set, bundle block break loot]
	private void syncBundleWithStorage() {
		var stack = storage.getStackInSlot(0);

        assert this.level != null;
        var builder = BottomlessBundleItem.BottomlessStack.Builder.of(this.level, this.bundle);
		builder.set(stack.copyWithCount(1), stack.getCount());
		builder.buildAndSet(this.bundle);
	}

	private void syncStorageWithBundle() {
		var ref = BottomlessBundleItem.getTemplateVariant(bundle);
		var count = BottomlessBundleItem.getStoredAmount(bundle);
		storage.setStackInSlot(0, ref.copyWithCount((int) count));
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
			this.isVoiding = EnchantmentHelper.hasTag(bundle, SpectrumEnchantmentTags.DELETES_OVERFLOW);
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
			return SpectrumBlocks.BOTTOMLESS_BUNDLE.get().asItem().getDefaultInstance();
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
