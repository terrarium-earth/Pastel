package earth.terrarium.pastel.blocks;

import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;

public abstract class InWorldInteractionBlock extends BaseEntityBlock {

	protected InWorldInteractionBlock(Properties settings) {
		super(settings);
	}

	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}

	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}

	// drop all currently stored stuff
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		Containers.dropContentsOnDestroy(state, newState, world, pos);
		// happens when filling with fluid, ...
		if (!state.is(newState.getBlock()) && world.getBlockEntity(pos) instanceof InWorldInteractionBlockEntity inWorldInteractionBlockEntity) {
		}
		super.onRemove(state, world, pos, newState, moved);
	}

	@Override
	public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClientSide && entity instanceof ItemEntity itemEntity) {
			ItemStack remainingStack = inputStack(world, pos, itemEntity.getItem());
			if (remainingStack.isEmpty()) {
				itemEntity.remove(Entity.RemovalReason.DISCARDED);
			} else {
				itemEntity.setItem(remainingStack);
			}
		} else {
			super.fallOn(world, state, pos, entity, fallDistance);
		}
	}

	public static void scatterContents(Level world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof Container inventory) {
			Containers.dropContents(world, pos, inventory);
			world.updateNeighbourForOutputSignal(pos, block);
		}
	}

	public ItemStack inputStack(Level world, BlockPos pos, ItemStack itemStack) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof InWorldInteractionBlockEntity inWorldInteractionBlockEntity) {
			int previousCount = itemStack.getCount();
			ItemStack remainingStack = InventoryHelper.smartAddToInventory(itemStack, inWorldInteractionBlockEntity.inventory, null);

			if (remainingStack.getCount() != previousCount) {
				world.playSound(null, pos, SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 0.8F, 0.8F + world.random.nextFloat() * 0.6F);
			}
			return remainingStack;
		}
		return itemStack;
	}

	public boolean exchangeStack(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack handStack, InWorldInteractionBlockEntity blockEntity) {
		return exchangeStack(world, pos, player, hand, handStack, blockEntity, 0);
	}

	public boolean exchangeStack(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack handStack, InWorldInteractionBlockEntity blockEntity, int slot) {
		boolean itemsChanged = false;
		if (player.isShiftKeyDown()) {
			ItemStack retrievedStack = blockEntity.removeItemNoUpdate(slot);
			if (!retrievedStack.isEmpty()) {
				player.getInventory().placeItemBackInInventory(retrievedStack);
				itemsChanged = true;
			}
		} else {
			ItemStack currentStack = blockEntity.getItem(slot);
			if (!handStack.isEmpty() && !currentStack.isEmpty()) {
				if (ItemStack.isSameItemSameComponents(handStack, currentStack)) {
					InventoryHelper.setOrCombineStack(blockEntity.inventory, slot, handStack);
				} else {
					blockEntity.setItem(slot, handStack);
					player.setItemInHand(hand, currentStack);
				}
				itemsChanged = true;
			} else {
				if (!handStack.isEmpty()) {
					ItemStack singleStack = handStack.split(1);
					blockEntity.setItem(slot, singleStack);
					itemsChanged = true;
				}
				if (!currentStack.isEmpty()) {
					blockEntity.setItem(slot, ItemStack.EMPTY);
					player.getInventory().placeItemBackInInventory(currentStack);
					itemsChanged = true;
				}
			}
		}

		if (itemsChanged) {
			world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8F, 0.8F + world.random.nextFloat() * 0.6F);
		}
		return itemsChanged;
	}

	public boolean retrieveStack(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack handStack, InWorldInteractionBlockEntity blockEntity, int slot) {
		ItemStack retrievedStack = blockEntity.removeItemNoUpdate(slot);
		if (retrievedStack.isEmpty()) {
			return false;
		}
		if (player.getItemInHand(hand).isEmpty()) {
			player.setItemInHand(hand, retrievedStack);
		} else {
			player.getInventory().placeItemBackInInventory(retrievedStack);
		}
		world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8F, 0.8F + world.random.nextFloat() * 0.6F);
		return true;
	}

	public boolean retrieveLastStack(Level world, BlockPos pos, Player player, InteractionHand hand, ItemStack handStack, InWorldInteractionBlockEntity blockEntity) {
		for (int i = blockEntity.getContainerSize() - 1; i >= 0; i--) {
			if (retrieveStack(world, pos, player, hand, handStack, blockEntity, i)) {
				return true;
			}
		}
		return false;
	}

	public boolean inputHandStack(Level world, Player player, InteractionHand hand, ItemStack handStack, InWorldInteractionBlockEntity blockEntity) {
		int previousCount = handStack.getCount();
		ItemStack remainingStack = InventoryHelper.smartAddToInventory(handStack, blockEntity.inventory, null);
		if (remainingStack.getCount() != previousCount) {
			player.setItemInHand(hand, remainingStack);
			world.playSound(null, player.blockPosition(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.8F, 0.8F + world.random.nextFloat() * 0.6F);
			return true;
		}
		return false;
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
	}

}
