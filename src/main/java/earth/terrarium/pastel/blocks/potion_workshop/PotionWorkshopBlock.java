package earth.terrarium.pastel.blocks.potion_workshop;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class PotionWorkshopBlock extends HorizontalDirectionalBlock implements EntityBlock {

	public static final MapCodec<PotionWorkshopBlock> CODEC = simpleCodec(PotionWorkshopBlock::new);

	public static final ResourceLocation UNLOCK_IDENTIFIER = SpectrumCommon.locate("unlocks/blocks/potion_workshop");

	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

	public PotionWorkshopBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends PotionWorkshopBlock> codec() {
		return CODEC;
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PotionWorkshopBlockEntity(pos, state);
	}
	
	@Override
	public boolean isPathfindable(BlockState state, PathComputationType type) {
		return false;
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return world.isClientSide ? null : Support.checkType(type, SpectrumBlockEntities.POTION_WORKSHOP.get(), PotionWorkshopBlockEntity::tick);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
	
	@Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
		return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
    }

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
		Containers.dropContentsOnDestroy(state, newState, world, pos);
		super.onRemove(state, world, pos, newState, moved);
	}
	
	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (world.isClientSide) {
			return InteractionResult.SUCCESS;
		} else {
			this.openScreen(world, pos, player);
			return InteractionResult.CONSUME;
		}
	}
	
	protected void openScreen(Level world, BlockPos pos, Player player) {
		BlockEntity blockEntity = world.getBlockEntity(pos);
		if (blockEntity instanceof PotionWorkshopBlockEntity potionWorkshopBlockEntity) {
			potionWorkshopBlockEntity.setOwner(player);
			player.openMenu((MenuProvider) blockEntity);
		}
	}

}
