package de.dafuqs.spectrum.blocks.item_roundel;

import de.dafuqs.spectrum.blocks.InWorldInteractionBlockEntity;
import de.dafuqs.spectrum.capabilities.*;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.*;

public class ItemRoundelBlockEntity extends InWorldInteractionBlockEntity implements SidedCapabilityProvider {
	
	protected static final int INVENTORY_SIZE = 6;
	
	public ItemRoundelBlockEntity(BlockPos pos, BlockState state) {
		this(SpectrumBlockEntities.ITEM_ROUNDEL, pos, state, INVENTORY_SIZE);
	}
	
	public ItemRoundelBlockEntity(BlockEntityType<? extends ItemRoundelBlockEntity> blockEntityType, BlockPos pos, BlockState state, int inventorySize) {
		super(blockEntityType, pos, state, inventorySize);
	}
	
	public boolean renderStacksAsIndividualItems() {
		return false;
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registryLookup) {
		this.unpackLootTable(null);
		return super.getUpdateTag(registryLookup);
	}

	@Override
	public IItemHandler exposeItemHandlers(Direction dir) {
		return inventory;
	}
}
