package earth.terrarium.pastel.blocks.decoration;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.particle.PastelParticleTypes;
import earth.terrarium.pastel.registries.PastelBlocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.TransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class EtherealPlatformBlock extends TransparentBlock {

    public static final MapCodec<EtherealPlatformBlock> CODEC = simpleCodec(EtherealPlatformBlock::new);

    public static final int MAX_AGE = 5;
    public static final BooleanProperty EXTEND = BooleanProperty.create("extend");
    public static final IntegerProperty AGE = BlockStateProperties.AGE_5;
    protected static final VoxelShape SHAPE = Block.box(0.0D, 15.0D, 0.0D, 16.0D, 16.0D, 16.0D);

    public EtherealPlatformBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends EtherealPlatformBlock> codec() {
        return CODEC;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        return state.getValue(AGE) == 0 || !(direction == Direction.UP);
    }

    @Override
    public void stepOn(Level world, BlockPos pos, BlockState state, Entity entity) {
        if (state.getValue(AGE) != MAX_AGE) {
            world.setBlock(pos, state.setValue(AGE, MAX_AGE), 3);
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos offsetPos = pos.relative(direction);
                BlockState directionState = world.getBlockState(offsetPos);
                if (directionState.getBlock() instanceof EtherealPlatformBlock) {
                    world.setBlock(offsetPos, directionState.setValue(AGE, MAX_AGE - 1)
                                                            .setValue(EXTEND, true), Block.UPDATE_CLIENTS
                    );
                    world.scheduleTick(offsetPos, this, 2);
                }
            }
        }
        super.stepOn(world, pos, state, entity);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);
        boolean extend = state.getValue(EXTEND);
        if (extend && age > 1) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos offsetPos = pos.relative(direction);
                BlockState directionState = world.getBlockState(offsetPos);
                if (directionState.getBlock() instanceof EtherealPlatformBlock && age > directionState.getValue(AGE)) {
                    world.setBlock(offsetPos, directionState.setValue(AGE, age - 1)
                                                            .setValue(EXTEND, true), Block.UPDATE_CLIENTS
                    );
                    world.scheduleTick(offsetPos, this, 2);
                }
            }
            world.setBlockAndUpdate(pos, state.setValue(EXTEND, false));
        } else if (!extend && this.increaseAge(state, world, pos)) {
            BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

            for (Direction direction : Direction.values()) {
                mutable.setWithOffset(pos, direction);
                BlockState blockState = world.getBlockState(mutable);
                if (blockState.is(this) && !this.increaseAge(blockState, world, mutable)) {
                    world.scheduleTick(mutable, this, 4);
                }
            }
        }
        if (age > 0) {
            world.scheduleTick(pos, this, 10);
        }
    }

    private boolean increaseAge(BlockState state, Level world, BlockPos pos) {
        int i = state.getValue(AGE);
        if (i > 0) {
            world.setBlock(pos, state.setValue(AGE, i - 1)
                                     .setValue(EXTEND, false), Block.UPDATE_CLIENTS
            );
            return false;
        } else {
            return true;
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    @Deprecated
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (context instanceof EntityCollisionContext entityShapeContext) {
            Entity entity = entityShapeContext.getEntity();
            if (entity instanceof LivingEntity) {
                return state.getShape(world, pos);
            }
        }
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, EXTEND);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        int age = state.getValue((AGE));
        if ((world.isClientSide && Minecraft.getInstance().player.getMainHandItem()
                                                                 .is(PastelBlocks.ETHEREAL_PLATFORM.get()
                                                                                                   .asItem()))) {
            age = Math.max(age, 3);
        }
        if (age > 0) {
            for (int i = 0; i < age; i++) {
                double d = pos.getX() + random.nextFloat();
                double e = pos.getY() + 1.01;
                double f = pos.getZ() + random.nextFloat();
                world.addParticle(PastelParticleTypes.LIQUID_CRYSTAL_SPARKLE, d, e, f, 0.0D, 0.03D, 0.0D);
            }
        }
    }

}
