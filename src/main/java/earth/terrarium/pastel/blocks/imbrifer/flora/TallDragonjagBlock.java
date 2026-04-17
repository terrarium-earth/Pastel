package earth.terrarium.pastel.blocks.imbrifer.flora;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import earth.terrarium.pastel.api.interaction.NaturesStaffTriggered;
import earth.terrarium.pastel.registries.PastelConfiguredFeatures;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class TallDragonjagBlock extends DoublePlantBlock
    implements Dragonjag, BonemealableBlock, NaturesStaffTriggered {

    public static final MapCodec<TallDragonjagBlock> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                                                                                                   propertiesCodec(),
                                                                                                   Dragonjag.Variant.CODEC.fieldOf("variant")
                                                                                                                          .forGetter(TallDragonjagBlock::getVariant)
                                                                                               )
                                                                                               .apply(
                                                                                                   i,
                                                                                                   TallDragonjagBlock::new
                                                                                               ));

    protected static final VoxelShape SHAPE_UPPER = Block.box(2.0, 0.0, 2.0, 14.0, 7.0, 14.0);
    protected static final VoxelShape SHAPE_UPPER_DEAD = Block.box(2.0, 0.0, 2.0, 10.0, 3.0, 14.0);
    protected static final VoxelShape SHAPE_LOWER = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);

    public static final BooleanProperty DEAD = BooleanProperty.create("dead");
    protected static final Map<Dragonjag.Variant, TallDragonjagBlock> VARIANTS = new HashMap<>();
    protected final Dragonjag.Variant variant;

    public TallDragonjagBlock(Properties settings, Dragonjag.Variant variant) {
        super(settings);
        this.variant = variant;
        VARIANTS.put(variant, this);
        this.registerDefaultState(this.stateDefinition.any()
                                                      .setValue(HALF, DoubleBlockHalf.LOWER)
                                                      .setValue(DEAD, false));
    }

    @Override
    public MapCodec<? extends TallDragonjagBlock> codec() {
        return CODEC;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER) {
            return state.getValue(DEAD) ? SHAPE_UPPER_DEAD : SHAPE_UPPER;
        }
        return SHAPE_LOWER;
    }

    @Override
    protected boolean mayPlaceOn(BlockState floor, BlockGetter world, BlockPos pos) {
        return Dragonjag.canPlantOnTop(floor, world, pos);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return SmallDragonjagBlock.getBlockForVariant(this.variant)
                                  .getCloneItemStack(world, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, DEAD);
    }

    @Override
    public Dragonjag.Variant getVariant() {
        return variant;
    }

    public static TallDragonjagBlock getBlockForVariant(Variant variant) {
        return VARIANTS.get(variant);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return !state.getValue(DEAD);
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return !state.getValue(DEAD);
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        boolean success = world.registryAccess()
                               .registryOrThrow(Registries.CONFIGURED_FEATURE)
                               .get(PastelConfiguredFeatures.DRAGONJAGS.get(variant))
                               .place(world, world.getChunkSource()
                                                  .getGenerator(), random, pos
                               );

        if (success) {
            setDead(world, pos, state, true);
        }
    }

    private void setDead(Level world, BlockPos pos, BlockState state, boolean dead) {
        BlockState posState = world.getBlockState(pos);
        if (posState.is(this)) {
            world.setBlockAndUpdate(pos, posState.setValue(DEAD, dead));
        }
        if (state.getValue(HALF) == DoubleBlockHalf.LOWER) {
            posState = world.getBlockState(pos.above());
            if (posState.is(this)) {
                world.setBlockAndUpdate(pos.above(), posState.setValue(DEAD, dead));
            }
        } else {
            posState = world.getBlockState(pos.below());
            if (posState.is(this)) {
                world.setBlockAndUpdate(pos.below(), posState.setValue(DEAD, dead));
            }
        }
    }

    @Override
    public boolean canUseNaturesStaff(Level world, BlockPos pos, BlockState state) {
        return state.getValue(DEAD);
    }

    @Override
    public boolean onNaturesStaffUse(Level world, BlockPos pos, BlockState state, Player player) {
        setDead(world, pos, state, false);
        return true;
    }

}
