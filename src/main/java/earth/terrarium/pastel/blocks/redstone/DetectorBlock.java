package earth.terrarium.pastel.blocks.redstone;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public abstract class DetectorBlock extends Block {

	public static final IntegerProperty POWER = BlockStateProperties.POWER;
	public static final BooleanProperty INVERTED = BlockStateProperties.INVERTED;
	protected static final VoxelShape SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D);
	
	protected static final double DETECTION_DIAMETER = 20.0D;
	
	public DetectorBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(((this.stateDefinition.any()).setValue(POWER, 0)).setValue(INVERTED, false));
	}

	@Override
	public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
		if (player.mayBuild()) {
			if (world.isClientSide) {
				return InteractionResult.SUCCESS;
			} else {
				BlockState blockState = state.cycle(INVERTED);
				world.setBlock(pos, blockState, 4);
				updateState(blockState, world, pos);
				return InteractionResult.CONSUME;
			}
		} else {
			return super.useWithoutItem(state, world, pos, player, hit);
		}
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(POWER, INVERTED);
	}
	
	@Override
	public RenderShape getRenderShape(BlockState state) {
		return RenderShape.MODEL;
	}
	
	@Override
	public boolean isSignalSource(BlockState state) {
		return true;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}
	
	@Override
	public int getSignal(BlockState state, BlockGetter world, BlockPos pos, Direction direction) {
		return state.getValue(POWER);
	}
	
	@Override
	@Deprecated
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
		updateState(state, world, pos);
		world.scheduleTick(pos, state.getBlock(), getUpdateFrequencyTicks());
	}
	
	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		updateState(state, world, pos);
		world.scheduleTick(pos, state.getBlock(), getUpdateFrequencyTicks());
	}
	
	abstract void updateState(BlockState state, Level world, BlockPos pos);
	
	abstract int getUpdateFrequencyTicks();
	
	protected AABB getDetectionBox(BlockPos pos) {
		return AABB.ofSize(Vec3.atCenterOf(pos), DETECTION_DIAMETER, DETECTION_DIAMETER, DETECTION_DIAMETER);
	}
	
}
