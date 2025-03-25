package de.dafuqs.spectrum.blocks.bottomless_bundle;

import com.mojang.serialization.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.fabric.api.transfer.v1.item.*;
import net.fabricmc.fabric.api.transfer.v1.storage.base.*;
import net.fabricmc.fabric.api.transfer.v1.transaction.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.component.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.loot.context.*;
import net.minecraft.sound.*;
import net.minecraft.state.*;
import net.minecraft.state.property.*;
import net.minecraft.state.property.Properties;
import net.minecraft.text.*;
import net.minecraft.util.*;
import net.minecraft.util.hit.*;
import net.minecraft.util.math.*;
import net.minecraft.util.shape.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

public class BottomlessBundleBlock extends BlockWithEntity {

	public static final MapCodec<BottomlessBundleBlock> CODEC = createCodec(BottomlessBundleBlock::new);
	public static final IntProperty ROTATION = Properties.ROTATION;
	public static final BooleanProperty LOCKED = Properties.LOCKED;
	public static final int MAX_ROTATIONS = RotationPropertyHelper.getMax() + 1;
	
	protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
	
	public BottomlessBundleBlock(Settings settings) {
		super(settings);
		setDefaultState(getDefaultState().with(LOCKED, false));
	}

	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {
		return CODEC;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return SHAPE;
	}
	
	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new BottomlessBundleBlockEntity(pos, state);
	}
	
	@Override
	public BlockRenderType getRenderType(BlockState state) {
		return BlockRenderType.INVISIBLE;
	}
	
	@Override
	public boolean canPathfindThrough(BlockState state, NavigationType type) {
		return false;
	}
	
	@Override
	public ItemActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if (!world.isClient) {
			if (player.isSneaking()) {
				world.getBlockEntity(pos, SpectrumBlockEntities.BOTTOMLESS_BUNDLE).ifPresent((bottomlessBundleBlockEntity) -> {
					long amount = bottomlessBundleBlockEntity.storage.amount;
					ItemVariant variant = bottomlessBundleBlockEntity.storage.getResource();
					long maxStoredAmount = BottomlessBundleItem.getMaxStoredAmount(bottomlessBundleBlockEntity.powerLevel);
					if (variant.isBlank()) {
						player.sendMessage(Text.translatable("item.spectrum.bottomless_bundle.tooltip.empty"), true);
					} else {
						player.sendMessage(Text.translatable("item.spectrum.bottomless_bundle.tooltip.count_of", amount, maxStoredAmount).append(variant.getItem().getName()), true);
					}
				});
			} else {
				world.getBlockEntity(pos, SpectrumBlockEntities.BOTTOMLESS_BUNDLE).ifPresent((bottomlessBundleBlockEntity) -> {
					SingleVariantStorage<ItemVariant> storage = bottomlessBundleBlockEntity.storage;
					ItemVariant storedVariant = storage.variant;
					
					try (Transaction transaction = Transaction.openOuter()) {
						if (storedVariant.matches(stack) || storedVariant.isBlank()) {
							// insert
							if (!stack.isEmpty() && stack.getItem().canBeNested()) {
								long inserted = storage.insert(ItemVariant.of(stack), stack.getCount(), transaction);
								stack.decrement((int) inserted);
								world.playSound(null, pos, SoundEvents.ITEM_BUNDLE_INSERT, SoundCategory.BLOCKS, 0.8F, 0.8F + world.getRandom().nextFloat() * 0.4F);
							}
						} else {
							// extract
							long extractedAmount = storage.extract(storedVariant, storedVariant.getItem().getMaxCount(), transaction);
							player.getInventory().offerOrDrop(storedVariant.toStack((int) extractedAmount));
							world.playSound(null, pos, SoundEvents.ITEM_BUNDLE_REMOVE_ONE, SoundCategory.BLOCKS, 0.8F, 0.8F + world.getRandom().nextFloat() * 0.4F);
						}
						transaction.commit();
					}
					
					bottomlessBundleBlockEntity.markDirty();
				});
			}
			return ItemActionResult.CONSUME;
		}
		return ItemActionResult.SUCCESS;
	}
	
	@Override
	public ItemStack getPickStack(WorldView world, BlockPos pos, BlockState state) {
		return SpectrumBlocks.BOTTOMLESS_BUNDLE.asItem().getDefaultStack();
	}
	
	@Override
	public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
		BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);
		if (blockEntity instanceof BottomlessBundleBlockEntity bottomlessBundleBlockEntity) {
			return List.of(bottomlessBundleBlockEntity.retrieveBundle());
		} else {
			return super.getDroppedStacks(state, builder);
		}
	}
	
	@Override
    public boolean hasComparatorOutput(BlockState state) {
        return true;
    }
	
	@Override
    public int getComparatorOutput(BlockState state, World world, BlockPos pos) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof BottomlessBundleBlockEntity bottomlessBundleBlockEntity) {
			float curr = bottomlessBundleBlockEntity.storage.amount;
			float max = bottomlessBundleBlockEntity.storage.getCapacity();
			return MathHelper.floor(curr / max * 14.0f) + curr > 0 ? 1 : 0;
		}
		
		return 0;
    }
	
	@Override
	public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
		if (!state.isOf(newState.getBlock())) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BottomlessBundleBlockEntity) {
				world.updateComparators(pos, this);
			}
			super.onStateReplaced(state, world, pos, newState, moved);
		}
	}
	
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return super.getPlacementState(ctx)
				.with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw()))
				.with(LOCKED, ctx.getStack().contains(DataComponentTypes.LOCK));
	}
	
	protected BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState)state.with(ROTATION, rotation.rotate((Integer)state.get(ROTATION), MAX_ROTATIONS));
	}
	
	protected BlockState mirror(BlockState state, BlockMirror mirror) {
		return (BlockState)state.with(ROTATION, mirror.mirror((Integer)state.get(ROTATION), MAX_ROTATIONS));
	}
	
	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
		if (!world.isClient) {
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof BottomlessBundleBlockEntity bottomlessBundleBlockEntity) {
				bottomlessBundleBlockEntity.setBundle(itemStack.copy(), world.getRegistryManager());
				world.updateComparators(pos, this);
			}
		}
	}
	
	@Override
	public MutableText getName() {
		return Text.translatable("item.spectrum.bottomless_bundle");
	}
	
	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(Properties.ROTATION, LOCKED);
	}
}
