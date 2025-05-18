package de.dafuqs.spectrum.blocks.redstone;

import de.dafuqs.spectrum.inventories.Spectrum3x3ContainerScreenHandler;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockPlacerBlockEntity extends DispenserBlockEntity {
	
	public BlockPlacerBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.BLOCK_PLACER, pos, state);
	}
	
	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.spectrum.block_placer");
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return Spectrum3x3ContainerScreenHandler.createTier1(syncId, playerInventory, this);
	}
	
}
