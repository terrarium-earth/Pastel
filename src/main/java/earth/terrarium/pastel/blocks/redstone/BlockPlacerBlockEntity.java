package earth.terrarium.pastel.blocks.redstone;

import earth.terrarium.pastel.capabilities.*;
import earth.terrarium.pastel.inventories.Spectrum3x3ContainerScreenHandler;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.*;

public class BlockPlacerBlockEntity extends DispenserBlockEntity implements SidedCapabilityProvider {
	
	public BlockPlacerBlockEntity(BlockPos pos, BlockState state) {
		super(SpectrumBlockEntities.BLOCK_PLACER.get(), pos, state);
	}
	
	@Override
	protected Component getDefaultName() {
		return Component.translatable("block.pastel.block_placer");
	}
	
	@Override
	protected AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
		return Spectrum3x3ContainerScreenHandler.createTier1(syncId, playerInventory, this);
	}

	@Override
	public IItemHandler exposeItemHandlers(Direction dir) {
		return new ItemStackHandler(getItems());
	}
}
