package de.dafuqs.spectrum.blocks.redstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class PlayerDetectorBlock extends DetectorBlock implements EntityBlock {

	public static final MapCodec<PlayerDetectorBlock> CODEC = simpleCodec(PlayerDetectorBlock::new);

	public PlayerDetectorBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends PlayerDetectorBlock> codec() {
		return CODEC;
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
		if (!world.isClientSide && placer instanceof Player) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof PlayerDetectorBlockEntity) {
				((PlayerDetectorBlockEntity) blockEntity).setOwner((Player) placer);
			}
		}
	}
	
	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			if (player.isShiftKeyDown()) {
				
				String ownerName = getOwnerName(world, pos);
				if (ownerName != null && !ownerName.isBlank()) {
					player.displayClientMessage(Component.translatable("block.pastel.player_detector.owner", ownerName), true);
				}
				return InteractionResult.CONSUME;
			} else {
				return super.useWithoutItem(state, world, pos, player, hit);
			}
		}
	}
	
	@Override
	protected void updateState(BlockState state, Level world, BlockPos pos) {
		List<Player> players = world.getEntities(EntityType.PLAYER, getDetectionBox(pos), player -> player.isAlive() && !player.isSpectator());
		
		int power = 0;
		
		if (!players.isEmpty()) {
			power = 8;
			UUID ownerUUID = getOwnerUUID(world, pos);
			if (ownerUUID != null) {
				for (Player playerEntity : players) {
					if (playerEntity.getUUID().equals(ownerUUID)) {
						power = 15;
						break;
					}
				}
			}
		}
		
		power = state.getValue(INVERTED) ? 15 - power : power;
		if (state.getValue(POWER) != power) {
			world.setBlock(pos, state.setValue(POWER, power), 3);
		}
	}
	
	@Override
	int getUpdateFrequencyTicks() {
		return 20;
	}
	
	private UUID getOwnerUUID(Level world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		if (blockEntity instanceof PlayerDetectorBlockEntity) {
			return ((PlayerDetectorBlockEntity) blockEntity).getOwnerUUID();
		}
		return null;
	}
	
	private String getOwnerName(Level world, BlockPos blockPos) {
		BlockEntity blockEntity = world.getBlockEntity(blockPos);
		if (blockEntity instanceof PlayerDetectorBlockEntity) {
			return ((PlayerDetectorBlockEntity) blockEntity).getOwnerName();
		}
		return null;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PlayerDetectorBlockEntity(pos, state);
	}
}
