package earth.terrarium.pastel.blocks.imbrifer.flora;

import earth.terrarium.pastel.helpers.level.BlockReference;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.Nullable;

public abstract class TriStateVineBlock extends BushBlock implements BonemealableBlock {

    public static final EnumProperty<LifeStage> LIFE_STAGE = EnumProperty.create("life_stage", LifeStage.class);
    private final int minHeight;
    private final float growthTickChance, spreadChance, overgrowth;

    public TriStateVineBlock(
        Properties settings, int minHeight, float growthChance, float spreadChance, float overgrowth) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(LIFE_STAGE, LifeStage.GROWING));
        this.minHeight = minHeight;
        this.growthTickChance = growthChance;
        this.spreadChance = spreadChance;
        this.overgrowth = overgrowth;
    }

    @Override
    public ItemInteractionResult useItemOn(
        ItemStack handStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult hit
    ) {
        var reference = BlockReference.of(state, pos);
        var creative = player.getAbilities().instabuild;

        if (handStack.is(Tags.Items.TOOLS_SHEAR)) {
            if (reference.getProperty(LIFE_STAGE) != LifeStage.GROWING)
                return ItemInteractionResult.FAIL;

            if (!creative)
                handStack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));

            reference.setProperty(LIFE_STAGE, LifeStage.MATURE);
            reference.update(world);

            world.playSound(
                null, pos, SoundEvents.BEEHIVE_SHEAR, SoundSource.BLOCKS, 1.0F,
                Mth.randomBetween(world.random, 0.6F, 1.0F)
            ); // TODO: custom sound event because subtitles
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, reference.getState()));
            return ItemInteractionResult.sidedSuccess(world.isClientSide());
        } else if (handStack.is(PastelItems.MOONSTRUCK_NECTAR.get())) {
            if (reference.getProperty(LIFE_STAGE) != LifeStage.MATURE)
                return ItemInteractionResult.FAIL;

            if (!creative)
                handStack.shrink(1);

            reference.setProperty(LIFE_STAGE, LifeStage.GROWING);
            reference.update(world);

            world.playSound(
                null, pos, SoundEvents.AMETHYST_BLOCK_CHIME, SoundSource.BLOCKS, 1.0F,
                Mth.randomBetween(world.random, 0.6F, 1.0F)
            ); // TODO: custom sound event because subtitles
            world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, reference.getState()));
            return ItemInteractionResult.sidedSuccess(world.isClientSide());
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        var world = ctx.getLevel();
        var pos = ctx.getClickedPos();

        var state = defaultBlockState();
        var roof = BlockReference.of(world, pos.above());

        if (!canSurvive(world.getBlockState(pos), world, pos) || !world.isEmptyBlock(pos))
            return null;

        if (roof.isOf(this)) {
            state = state.setValue(LIFE_STAGE, roof.getProperty(LIFE_STAGE));
            roof.setProperty(LIFE_STAGE, LifeStage.STALK);
            roof.update(world);
        }

        return state;
    }

    abstract boolean hasGrowthActions();

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(LIFE_STAGE) != LifeStage.MATURE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextFloat() >= growthTickChance)
            return;

        var reference = BlockReference.of(state, pos);
        var stage = reference.getProperty(LIFE_STAGE);

        if (hasGrowthActions() && random.nextBoolean() || stage != LifeStage.GROWING) {
            performBonemeal(world, random, pos, state);
        } else {
            if (!isBonemealSuccess(world, random, pos, state) || random.nextFloat() >= spreadChance)
                return;

            reference.setProperty(LIFE_STAGE, LifeStage.STALK);
            reference.update(world);

            var sprigState = defaultBlockState();
            var height = getCurrentHeight(world, reference.pos);

            if (height >= minHeight && random.nextFloat() >= overgrowth) {
                sprigState = sprigState.setValue(LIFE_STAGE, LifeStage.MATURE);
            }

            world.setBlockAndUpdate(reference.pos.below(), sprigState);
        }
    }

    protected int getCurrentHeight(Level world, BlockPos pos) {
        var state = world.getBlockState(pos);
        var count = 0;

        while (state.is(this)) {
            count++;
            state = world.getBlockState(pos.above(count));
        }

        return count;
    }

    @Override
    public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
        var roof = BlockReference.of(world, pos.above());

        if (roof.isOf(this)) {
            roof.setProperty(LIFE_STAGE, getLowestLifeStage(world, pos.below(), state.getValue(LIFE_STAGE)));
            roof.update(world);
        }

        scheduleBreakCheck(world, pos);
    }

    public LifeStage getLowestLifeStage(LevelAccessor world, BlockPos pos, LifeStage stage) {
        var state = world.getBlockState(pos);
        var lastStage = stage;
        while (state.is(this)) {
            lastStage = state.getValue(LIFE_STAGE);
            pos = pos.below();
            state = world.getBlockState(pos);
        }
        return lastStage;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return world.isEmptyBlock(pos.below());
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (canSurvive(state, world, pos))
            return;

        scheduleBreakCheck(world, pos);
        world.destroyBlock(pos, true);
    }

    private void scheduleBreakCheck(LevelAccessor world, BlockPos pos) {
        var underside = pos.below();
        if (world.getBlockState(underside)
                 .is(this))
            world.scheduleTick(underside, this, 1);
    }

    @Override
    public BlockState updateShape(
        BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos,
        BlockPos neighborPos
    ) {
        if (!canSurvive(state, world, pos)) {
            world.scheduleTick(pos, this, 1);
        }

        return state;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        var roof = pos.above();
        var roofState = world.getBlockState(roof);

        if (roofState.is(this))
            return true;

        return mayPlaceOn(roofState, world, roof);
    }

    @Override
    public boolean mayPlaceOn(BlockState roof, BlockGetter world, BlockPos pos) {
        return roof.isFaceSturdy(world, pos, Direction.DOWN);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIFE_STAGE);
    }

    public enum LifeStage implements StringRepresentable {
        STALK("stalk"),
        GROWING("growing"),
        MATURE("mature");

        private final String name;

        LifeStage(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    @Override
    public float getMaxHorizontalOffset() {
        return 0.1F;
    }

    @Override
    public float getMaxVerticalOffset() {
        return -0.15F;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Vec3 vec3d = state.getOffset(world, pos);
        return super.getShape(state, world, pos, context)
                    .move(vec3d.x, vec3d.y, vec3d.z);
    }
}
