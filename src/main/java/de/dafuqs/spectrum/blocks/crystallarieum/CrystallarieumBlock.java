package de.dafuqs.spectrum.blocks.crystallarieum;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.api.energy.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.blocks.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.transfer.v1.context.*;
import net.fabricmc.fabric.api.transfer.v1.fluid.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.item.tooltip.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class CrystallarieumBlock extends InWorldInteractionBlock {

	public static final MapCodec<CrystallarieumBlock> CODEC = createCodec(CrystallarieumBlock::new);

	public CrystallarieumBlock(Settings settings) {
		super(settings);
		this.setDefaultState((this.stateManager.getDefaultState()));
	}

	@Override
	public MapCodec<? extends CrystallarieumBlock> getCodec() {
		return CODEC;
	}
	
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new CrystallarieumBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
		return validateTicker(type, SpectrumBlockEntities.CRYSTALLARIEUM, world.isClient ? CrystallarieumBlockEntity::clientTick : CrystallarieumBlockEntity::serverTick);
	}
	
	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		if (!world.isClient() && direction == Direction.UP) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CrystallarieumBlockEntity crystallarieumBlockEntity) {
				crystallarieumBlockEntity.onTopBlockChange(neighborState, null);
			}
		}
		return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}
	
	@Override
	public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
		if (!world.isClient && entity instanceof ItemEntity itemEntity) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CrystallarieumBlockEntity crystallarieumBlockEntity) {
				ItemStack stack = itemEntity.getStack();
				crystallarieumBlockEntity.acceptStack(stack, false, itemEntity.getOwner() != null ? itemEntity.getOwner().getUuid() : null);
			}
		} else {
			super.onLandedUpon(world, state, pos, entity, fallDistance);
		}
	}
	
	@Override
	public ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			// if the structure is valid the player can put / retrieve blocks into the shrine
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof CrystallarieumBlockEntity crystallarieumBlockEntity) {
				
				if (player.isSneaking() || stack.isEmpty()) {
					// sneaking or empty hand: remove items
					if (retrieveStack(world, pos, player, hand, stack, crystallarieumBlockEntity, 1) || retrieveStack(world, pos, player, hand, stack, crystallarieumBlockEntity, 0)) {
						crystallarieumBlockEntity.inventoryChanged();
						crystallarieumBlockEntity.setOwner(player);
					}
					return ItemActionResult.CONSUME;
				} else {
					if (ContainerItemContext.forPlayerInteraction(player, hand).find(FluidStorage.ITEM) != null) {
						FluidStorageUtil.interactWithFluidStorage(crystallarieumBlockEntity.fluidStorage, player, hand);
					}
					
					// hand is full and inventory is empty: add
					// hand is full and inventory already contains item: exchange them
					else if (stack.getItem() instanceof InkStorageItem<?> inkStorageItem) {
						if (inkStorageItem.getDrainability().canDrain(false) && exchangeStack(world, pos, player, hand, stack, crystallarieumBlockEntity, CrystallarieumBlockEntity.INK_STORAGE_STACK_SLOT_ID)) {
							crystallarieumBlockEntity.inventoryChanged();
							crystallarieumBlockEntity.setOwner(player);
						}
					} else {
						if (exchangeStack(world, pos, player, hand, stack, crystallarieumBlockEntity, CrystallarieumBlockEntity.CATALYST_SLOT_ID)) {
							crystallarieumBlockEntity.inventoryChanged();
							crystallarieumBlockEntity.setOwner(player);
						}
					}
				}
			}
			return ItemActionResult.CONSUME;
		}
		return ItemActionResult.SUCCESS;
	}
	
	public ItemStack asStackWithColor(InkColor color) {
		ItemStack stack = asItem().getDefaultStack();
		stack.set(SpectrumDataComponentTypes.INK_COLOR, color);
		return stack;
	}

	@Override
	public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
		super.appendTooltip(stack, context, tooltip, type);
		var color = stack.get(SpectrumDataComponentTypes.INK_COLOR);
		if (color != null)
			tooltip.add(color.getColoredInkName());
	}
	
}
