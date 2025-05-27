package de.dafuqs.spectrum.blocks.bottomless_bundle;

import com.mojang.serialization.MapCodec;
import de.dafuqs.spectrum.registries.SpectrumBlockEntities;
import de.dafuqs.spectrum.registries.SpectrumBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BottomlessBundleBlock extends BaseEntityBlock {

	public static final MapCodec<BottomlessBundleBlock> CODEC = simpleCodec(BottomlessBundleBlock::new);
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	public static final BooleanProperty LOCKED = BlockStateProperties.LOCKED;
	public static final int MAX_ROTATIONS = RotationSegment.getMaxSegmentIndex() + 1;
	
	protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	
	public BottomlessBundleBlock(Properties settings) {
		super(settings);
		registerDefaultState(defaultBlockState().setValue(LOCKED, false));
	}

	@Override
	protected MapCodec<? extends BaseEntityBlock> codec() {
		return CODEC;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new BottomlessBundleBlockEntity(pos, state);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.INVISIBLE;
	}
	
	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}
	
	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (!world.isClientSide) {
			if (player.isShiftKeyDown()) {
				world.getBlockEntity(pos, SpectrumBlockEntities.BOTTOMLESS_BUNDLE).ifPresent((bottomlessBundleBlockEntity) -> {
					long amount = bottomlessBundleBlockEntity.storage.getStackInSlot(0).getCount();
					ItemStack ref = bottomlessBundleBlockEntity.storage.getStackInSlot(0);
					long maxStoredAmount = BottomlessBundleItem.getMaxStoredAmount(bottomlessBundleBlockEntity.powerLevel);
					if (ref.isEmpty()) {
						player.displayClientMessage(Component.translatable("item.pastel.bottomless_bundle.tooltip.empty"), true);
					} else {
						player.displayClientMessage(Component.translatable("item.pastel.bottomless_bundle.tooltip.count_of", amount, maxStoredAmount).append(ref.getItem().getDescription()), true);
					}
				});
			} else {
				world.getBlockEntity(pos, SpectrumBlockEntities.BOTTOMLESS_BUNDLE).ifPresent((bottomlessBundleBlockEntity) -> {
					ItemStackHandler storage = bottomlessBundleBlockEntity.storage;
					ItemStack ref = storage.getStackInSlot(0);

					if (ItemStack.isSameItemSameComponents(ref, stack) || ref.isEmpty()) {
						// insert
						if (!stack.isEmpty() && stack.getItem().canFitInsideContainerItems()) {
							stack.setCount(storage.insertItem(0, stack, false).getCount());
							world.playSound(null, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 0.8F, 0.8F + world.getRandom().nextFloat() * 0.4F);
						}
					} else {
						// extract
						var extracted = storage.extractItem(0, ref.getItem().getMaxStackSize(ref), false);
						player.getInventory().placeItemBackInInventory(extracted);
						world.playSound(null, pos, SoundEvents.BUNDLE_REMOVE_ONE, SoundSource.BLOCKS, 0.8F, 0.8F + world.getRandom().nextFloat() * 0.4F);
					}
					
					bottomlessBundleBlockEntity.setChanged();
				});
			}
			return ItemInteractionResult.CONSUME;
		}
		return ItemInteractionResult.SUCCESS;
	}
	
	@Override
	public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
		return SpectrumBlocks.BOTTOMLESS_BUNDLE.get().asItem().getDefaultInstance();
	}
	
	@Override
	public List<ItemStack> getDrops(BlockState state, LootParams.Builder builder) {
		BlockEntity blockEntity = builder.getParameter(LootContextParams.BLOCK_ENTITY);
		if (blockEntity instanceof BottomlessBundleBlockEntity bottomlessBundleBlockEntity) {
			return List.of(bottomlessBundleBlockEntity.retrieveBundle());
		} else {
			return super.getDrops(state, builder);
		}
	}
	
	@Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
	
	@Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof BottomlessBundleBlockEntity bundle) {
			float curr = bundle.storage.getStackInSlot(0).getCount();
			float max = BottomlessBundleItem.getMaxStoredAmount(bundle.powerLevel);
			return Mth.floor(curr / max * 14.0f) + curr > 0 ? 1 : 0;
		}
		
		return 0;
    }
	
	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.is(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BottomlessBundleBlockEntity) {
				world.updateNeighbourForOutputSignal(pos, this);
			}
			super.onRemove(state, world, pos, newState, moved);
		}
	}
	
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return super.getStateForPlacement(ctx)
				.setValue(ROTATION, RotationSegment.convertToSegment(ctx.getRotation()))
				.setValue(LOCKED, ctx.getItemInHand().has(DataComponents.LOCK));
	}
	
	protected BlockState rotate(BlockState state, Rotation rotation) {
		return (BlockState)state.setValue(ROTATION, rotation.rotate((Integer)state.getValue(ROTATION), MAX_ROTATIONS));
	}
	
	protected BlockState mirror(BlockState state, Mirror mirror) {
		return (BlockState)state.setValue(ROTATION, mirror.mirror((Integer)state.getValue(ROTATION), MAX_ROTATIONS));
	}
	
	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (!world.isClientSide) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BottomlessBundleBlockEntity bottomlessBundleBlockEntity) {
				bottomlessBundleBlockEntity.setBundle(itemStack.copy(), world.registryAccess());
				world.updateNeighbourForOutputSignal(pos, this);
			}
		}
	}
	
	@Override
	public MutableComponent getName() {
		return Component.translatable("item.pastel.bottomless_bundle");
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.ROTATION_16, LOCKED);
	}
}
