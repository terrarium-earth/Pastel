package earth.terrarium.pastel.blocks.redstone;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.api.block.ColorableBlock;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class RedstoneTransceiverBlock extends DiodeBlock implements EntityBlock, ColorableBlock {

	public static final MapCodec<RedstoneTransceiverBlock> CODEC = simpleCodec(RedstoneTransceiverBlock::new);

	public static final BooleanProperty SENDER = BooleanProperty.create("sender");
	public static final EnumProperty<DyeColor> CHANNEL = EnumProperty.create("channel", DyeColor.class);

	public RedstoneTransceiverBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(SENDER, true).setValue(CHANNEL, DyeColor.RED));
	}

	@Override
	public MapCodec<? extends RedstoneTransceiverBlock> codec() {
		return CODEC;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new RedstoneTransceiverBlockEntity(pos, state);
	}
	
	@Override
	protected int getDelay(BlockState state) {
		return 0;
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack handStack, BlockState state, @NotNull Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (world.isClientSide) {
			return ItemInteractionResult.SUCCESS;
		} else {
			if (!tryColorUsingStackInHand(handStack, world, pos, player, hand)) {
				toggleSendingMode(world, pos, state);
			}
			return ItemInteractionResult.CONSUME;
		}
	}
	
	public void toggleSendingMode(@NotNull Level world, BlockPos blockPos, @NotNull BlockState state) {
		BlockState newState = state.setValue(SENDER, !state.getValue(SENDER));
		world.setBlock(blockPos, newState, Block.UPDATE_CLIENTS);
		
		if (newState.getValue(SENDER)) {
			world.playSound(null, blockPos, PastelSoundEvents.REDSTONE_MECHANISM_TRIGGER, SoundSource.BLOCKS, 0.3F, 0.9F);
		} else {
			world.playSound(null, blockPos, PastelSoundEvents.REDSTONE_MECHANISM_TRIGGER, SoundSource.BLOCKS, 0.3F, 1.1F);
		}
		checkTickOnNeighbor(world, blockPos, newState);
	}
	
	// Hmmm... my feelings tell me that using channels and sender/receiver as property might be a bit much (16 dye colors) * 2 states plus the already existing states
	// Better move it to the block entity and use a dynamic renderer?
	// ram usage <=> rendering impact
	@Override
	protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
		builder.add(FACING, POWERED, SENDER, CHANNEL);
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> GameEventListener getListener(ServerLevel world, T blockEntity) {
		return blockEntity instanceof RedstoneTransceiverBlockEntity ? ((RedstoneTransceiverBlockEntity) blockEntity).getEventListener() : null;
	}
	
	@Override
	public void checkTickOnNeighbor(Level world, BlockPos pos, BlockState state) {
		int newSignal = world.getBestNeighborSignal(pos);
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof RedstoneTransceiverBlockEntity RedstoneTransceiverBlockEntity) {
			if (state.getValue(SENDER)) {
				int lastSignal = RedstoneTransceiverBlockEntity.getCurrentSignalStrength();
				if (newSignal != lastSignal) {
					RedstoneTransceiverBlockEntity.setSignalStrength(newSignal);
				}
				
				if (newSignal == 0) {
					world.setBlock(pos, state.setValue(POWERED, false), Block.UPDATE_CLIENTS);
				} else {
					world.setBlock(pos, state.setValue(POWERED, true), Block.UPDATE_CLIENTS);
				}
			}
		}
	}
	
	/**
	 * The block entity caches the output signal for performance
	 */
	@Override
	protected int getOutputSignal(@NotNull BlockGetter world, BlockPos pos, BlockState state) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		return blockEntity instanceof RedstoneTransceiverBlockEntity ? ((RedstoneTransceiverBlockEntity) blockEntity).getCurrentSignal() : 0;
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		super.setPlacedBy(world, pos, state, placer, itemStack);
		if (!world.isClientSide) {
			checkTickOnNeighbor(world, pos, state);
		}
	}
	
	@Override
	@Nullable
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return world.isClientSide ? null : Support.checkType(type, PastelBlockEntities.REDSTONE_TRANSCEIVER.get(), RedstoneTransceiverBlockEntity::serverTick);
	}

	@Override
	public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
		if (state.getValue(POWERED)) {
			double x = (double) pos.getX() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
			double y = (double) pos.getY() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
			double z = (double) pos.getZ() + 0.5D + (random.nextDouble() - 0.5D) * 0.2D;
			world.addParticle(DustParticleOptions.REDSTONE, x, y, z, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public boolean color(Level world, BlockPos pos, Optional<DyeColor> color, @Nullable Entity user) {
		if (color.isEmpty()) {
			return false;
		}
		if (getColor(world, pos) == color) {
			return false;
		}
		world.setBlockAndUpdate(pos, world.getBlockState(pos).setValue(CHANNEL, color.get()));
		return true;
	}

	@Override
	public Optional<DyeColor> getColor(Level world, BlockPos pos) {
		return Optional.of(world.getBlockState(pos).getValue(CHANNEL));
	}

}
