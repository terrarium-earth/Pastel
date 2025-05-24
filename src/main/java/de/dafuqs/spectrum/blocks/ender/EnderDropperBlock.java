package de.dafuqs.spectrum.blocks.ender;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.inventories.GenericSpectrumContainerScreenHandler;
import de.dafuqs.spectrum.inventories.ScreenBackgroundVariant;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.capabilities.*;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.Nullable;

public class EnderDropperBlock extends DispenserBlock {

	public static final MapCodec<EnderDropperBlock> CODEC = simpleCodec(EnderDropperBlock::new);

	private static final DispenseItemBehavior BEHAVIOR = new DefaultDispenseItemBehavior();

	public EnderDropperBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends EnderDropperBlock> codec() {
		return CODEC;
	}
	
	@Override
	protected DispenseItemBehavior getDispenseMethod(Level world, ItemStack stack) {
		return BEHAVIOR;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new EnderDropperBlockEntity(pos, state);
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (placer instanceof ServerPlayer serverPlayer) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EnderDropperBlockEntity dropperEntity) {
				dropperEntity.setOwner(serverPlayer);
				blockEntity.setChanged();
			}
		}
	}
	
	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof EnderDropperBlockEntity enderDropperBlockEntity) {
				
				if (!enderDropperBlockEntity.hasOwner()) {
					enderDropperBlockEntity.setOwner(player);
				}
				
				if (enderDropperBlockEntity.isOwner(player)) {
					PlayerEnderChestContainer enderChestInventory = player.getEnderChestInventory();
					
					player.openMenu(new SimpleMenuProvider((i, playerInventory, playerEntity) -> GenericSpectrumContainerScreenHandler.createGeneric9x3(i, playerInventory, enderChestInventory, ScreenBackgroundVariant.EARLYGAME), enderDropperBlockEntity.getDefaultName()));
					
					PiglinAi.angerNearbyPiglins(player, true);
				} else {
					player.displayClientMessage(Component.translatable("block.spectrum.ender_dropper_with_owner", enderDropperBlockEntity.getOwnerName()), true);
				}
			}
			return InteractionResult.CONSUME;
		}
	}
	
	@Override
	protected void dispenseFrom(ServerLevel level, BlockState state, BlockPos pos) {
		EnderDropperBlockEntity dropper = level.getBlockEntity(pos, SpectrumBlockEntities.ENDER_DROPPER).orElse(null);
		if (dropper == null) {
			return;
		}

		BlockSource blockPointer = new BlockSource(level, pos, state, dropper);
		int i = dropper.getRandomSlot(level.random);
		if (i < 0) {
			level.levelEvent(LevelEvent.SOUND_DISPENSER_FAIL, pos, 0); // no items in inv
		} else {
			ItemStack itemStack = dropper.getItem(i);
			if (!itemStack.isEmpty()) {
				Direction direction = level.getBlockState(pos).getValue(FACING);
				if (level.getBlockState(pos.relative(direction)).isAir()) {
					ItemStack itemStack3 = BEHAVIOR.dispense(blockPointer, itemStack);
					dropper.setItem(i, itemStack3);
				} else {
					var handler = level.getCapability(Capabilities.ItemHandler.BLOCK, pos.relative(direction), direction.getOpposite());
					if (handler != null) {

						assert dropper.getOwnerIfOnline() != null;
						var moved = ItemHandlerHelper.insertItemStacked(handler, itemStack.copyWithCount(1), false);
						// return without triggering fail event if successfully moved
						if (moved.isEmpty()) {
							itemStack.shrink(1);
							return;
						}
					}
					level.levelEvent(LevelEvent.SOUND_DISPENSER_FAIL, pos, 0); // no room to dispense to
				}
			}
		}
	}

}