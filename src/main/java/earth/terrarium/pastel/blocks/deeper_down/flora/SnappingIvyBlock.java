package earth.terrarium.pastel.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelConfiguredFeatures;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class SnappingIvyBlock extends BushBlock implements BonemealableBlock {

    public static final MapCodec<SnappingIvyBlock> CODEC = simpleCodec(SnappingIvyBlock::new);

    public static final EnumProperty<Direction.Axis> AXIS = BlockStateProperties.HORIZONTAL_AXIS;
    public static final BooleanProperty SNAPPED = BooleanProperty.create("snapped");

    protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0);
    protected static final Vec3 MOVEMENT_SLOWDOWN_VECTOR = new Vec3(0.5, 0.75, 0.5);

    public SnappingIvyBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(AXIS, Direction.Axis.X).setValue(SNAPPED, false));
    }

    @Override
    public MapCodec<? extends SnappingIvyBlock> codec() {
        return CODEC;
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, SNAPPED);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(PastelBlockTags.SNAPPING_IVY_PLANTABLE);
    }
	
	@Override
	public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
		return true;
	}
    
    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }
    
    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
		world.registryAccess()
				.registryOrThrow(Registries.CONFIGURED_FEATURE)
				.get(PastelConfiguredFeatures.SNAPPING_IVY_PATCH)
				.place(world, world.getChunkSource().getGenerator(), random, pos);
    }
    
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(AXIS, state.getValue(AXIS) == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X);
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		return this.defaultBlockState().setValue(AXIS, ctx.getHorizontalDirection().getAxis());
    }
    
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(SNAPPED);
    }
    
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (state.getValue(SNAPPED)) {
            snap(state, world, pos, false);
        }
    }
    
    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        boolean snapped = state.getValue(SNAPPED);

        if (!snapped && entity instanceof ItemEntity) {
            snap(state, world, pos, true);
        }
        if (entity instanceof LivingEntity livingEntity && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.makeStuckInBlock(state, MOVEMENT_SLOWDOWN_VECTOR);
            if (!snapped) {
				entity.hurt(PastelDamageTypes.snappingIvy(world), 5.0F);
                livingEntity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 120, 1));
	
				snap(state, world, pos, true);
            }
        }
    }
	
	private static void snap(BlockState state, Level world, BlockPos pos, boolean close) {
        BlockState newState = state.setValue(SNAPPED, close);
        world.setBlock(pos, newState, Block.UPDATE_CLIENTS);
        world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(newState));
        world.playSound(null, pos, close ? SoundEvents.BIG_DRIPLEAF_TILT_DOWN : SoundEvents.BIG_DRIPLEAF_TILT_UP, SoundSource.BLOCKS, 1.0F, Mth.randomBetween(world.random, 0.8F, 1.2F));
    }
    
}
