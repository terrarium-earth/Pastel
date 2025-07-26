package earth.terrarium.pastel.blocks.cinderhearth;

import com.klikli_dev.modonomicon.api.multiblock.Multiblock;
import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.compat.modonomicon.ModonomiconHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import earth.terrarium.pastel.registries.PastelMultiblocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CinderhearthBlock extends BaseEntityBlock {

    public static final MapCodec<CinderhearthBlock> CODEC = simpleCodec(CinderhearthBlock::new);

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    public CinderhearthBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(FACING, Direction.EAST));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CinderhearthBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level world, BlockState state, BlockEntityType<T> type) {
        return world.isClientSide ? null : createTickerHelper(
            type, PastelBlockEntities.CINDERHEARTH.get(), CinderhearthBlockEntity::serverTick);
    }

    @Override
    public InteractionResult useWithoutItem(
        BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            verifyStructure(world, pos);
            return InteractionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CinderhearthBlockEntity cinderhearthBlockEntity) {
                cinderhearthBlockEntity.setOwner(player);
                if (verifyStructure(world, pos) !=
                    CinderhearthBlockEntity.CinderHearthStructureType.NONE) {
                    player.openMenu(cinderhearthBlockEntity);
                }
            }
            return InteractionResult.CONSUME;
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState()
                   .setValue(
                       FACING, ctx.getHorizontalDirection()
                                  .getOpposite()
                   );
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CinderhearthBlockEntity cinderhearthBlockEntity) {
            if (placer instanceof Player player) {
                cinderhearthBlockEntity.setOwner(player);
            }
        }
    }

    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CinderhearthBlockEntity cinderhearthBlockEntity) {
                if (world instanceof ServerLevel) {
                    Containers.dropContents(world, pos, cinderhearthBlockEntity);
                }
                world.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, world, pos, newState, moved);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(world.getBlockEntity(pos));
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

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof CinderhearthBlockEntity cinderhearthBlockEntity) {
            Direction direction = state.getValue(FACING);
            Direction.Axis axis = direction.getAxis();
            double d = (double) pos.getX() + 0.5D;
            double e = pos.getY() + 0.4;
            double f = (double) pos.getZ() + 0.5D;

            var recipe = cinderhearthBlockEntity.getCurrentRecipeEntry();
            if (recipe != null) {
                if (random.nextDouble() < 0.1D) {
                    world.playLocalSound(
                        d, e, f, SoundEvents.FURNACE_FIRE_CRACKLE, SoundSource.BLOCKS, 1.0F, 0.8F, false);
                }

                double g = 0.35D;
                double h = random.nextDouble() * 0.4D - 0.2D;
                double i = axis == Direction.Axis.X ? (double) direction.getStepX() * g : h;
                double j = random.nextDouble() * 4.0D / 16.0D;
                double k = axis == Direction.Axis.Z ? (double) direction.getStepZ() * g : h;
                world.addParticle(ParticleTypes.FLAME, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);
                world.addParticle(ParticleTypes.SMOKE, d + i, e + j, f + k, 0.0D, 0.0D, 0.0D);

                if (random.nextBoolean()) {
                    double g2 = -3D / 16D;
                    double h2 = 4D / 16D;
                    double i2 = axis == Direction.Axis.X ? (double) direction.getStepX() * g2 : h2;
                    double k2 = axis == Direction.Axis.Z ? (double) direction.getStepZ() * g2 : h2;
                    world.addParticle(ParticleTypes.CLOUD, d + i2, pos.getY() + 1.1, f + k2, 0.0D, 0.06D, 0.0D);
                }
            }
            if (cinderhearthBlockEntity.structure == CinderhearthBlockEntity.CinderHearthStructureType.WITH_LAVA) {
                for (int v = 0; v < 2; v++) {
                    double g3 = 1.5 - random.nextDouble() * 2.0;
                    double h3 = 1.5 - random.nextDouble() * 3.0;
                    double i3 = axis == Direction.Axis.X ? (double) direction.getStepX() * g3 : h3;
                    double k3 = axis == Direction.Axis.Z ? (double) direction.getStepZ() * g3 : h3;
                    world.addParticle(
                        ColoredCraftingParticleEffect.ORANGE, d + i3, pos.getY() - 1.2, f + k3, 0.0D, 0.1D, 0.0D);
                }
            }
        }
    }

    public static CinderhearthBlockEntity.CinderHearthStructureType verifyStructure(
        Level level, @NotNull BlockPos blockPos) {
        Rotation rotation = Support.rotationFromDirection(level.getBlockState(blockPos)
                                                               .getValue(FACING)
                                                               .getOpposite());

        Multiblock multiblock = PastelMultiblocks.get(PastelMultiblocks.CINDERHEARTH);
        CinderhearthBlockEntity.CinderHearthStructureType completedStructure
            = CinderhearthBlockEntity.CinderHearthStructureType.NONE;

        if (multiblock.validate(level, blockPos.below(3), rotation)) {
            completedStructure = CinderhearthBlockEntity.CinderHearthStructureType.WITH_LAVA;
        } else {
            multiblock = PastelMultiblocks.get(PastelMultiblocks.CINDERHEARTH_WITHOUT_LAVA);
            if (multiblock.validate(level, blockPos.below(3), rotation)) {
                completedStructure = CinderhearthBlockEntity.CinderHearthStructureType.WITHOUT_LAVA;
            }
        }

        boolean structureValid = completedStructure != CinderhearthBlockEntity.CinderHearthStructureType.NONE;

        if (level.isClientSide) {
            if (!structureValid) {
                ModonomiconHelper.renderMultiblock(
                    PastelMultiblocks.get(PastelMultiblocks.CINDERHEARTH), PastelMultiblocks.CINDERHEARTH_TEXT,
                    blockPos.below(4), rotation
                );
            }
        } else if (structureValid && level instanceof ServerLevel sl) {
            Support.mbCriterion(sl, blockPos, multiblock);
        }

        return completedStructure;
    }

    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        if (world.isClientSide()) {
            ModonomiconHelper.clearRenderedMultiblock(PastelMultiblocks.get(PastelMultiblocks.CINDERHEARTH));
        }
    }

}
