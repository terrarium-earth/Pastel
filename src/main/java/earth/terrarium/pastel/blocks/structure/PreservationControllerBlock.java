package earth.terrarium.pastel.blocks.structure;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.SpectrumBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class PreservationControllerBlock extends BaseEntityBlock {

	public static final MapCodec<PreservationControllerBlock> CODEC = simpleCodec(PreservationControllerBlock::new);

	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

	public PreservationControllerBlock(Properties settings) {
		super(settings);
	}

	@Override
	public MapCodec<? extends PreservationControllerBlock> codec() {
		return CODEC;
	}
	
	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (!world.isClientSide && player.isCreative()) { // for testing and building structures
			BlockEntity blockEntity = world.getBlockEntity(pos);
			if (blockEntity instanceof PreservationControllerBlockEntity preservationControllerBlockEntity) {
				if (player.isShiftKeyDown()) {
					preservationControllerBlockEntity.openExit();
				} else {
					preservationControllerBlockEntity.toggleParticles();
				}
			}
		}
		return super.useWithoutItem(state, world, pos, player, hit);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		Direction direction = ctx.getClickedFace().getOpposite();
		if (direction == Direction.UP || direction == Direction.DOWN) { // those do not exist in Properties.HORIZONTAL_FACING
			direction = Direction.NORTH;
		}
		return this.defaultBlockState().setValue(FACING, direction);
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new PreservationControllerBlockEntity(pos, state);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
		return world.isClientSide ? null : createTickerHelper(type, SpectrumBlockEntities.PRESERVATION_CONTROLLER.get(), PreservationControllerBlockEntity::serverTick);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
}
