package earth.terrarium.pastel.blocks.jade_vines;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.api.interaction.NaturesStaffTriggered;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelLootTables;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class JadeVinePlantBlock extends Block implements JadeVine, NaturesStaffTriggered {

    public static final MapCodec<JadeVinePlantBlock> CODEC = simpleCodec(JadeVinePlantBlock::new);

    public static final EnumProperty<JadeVinesPlantPart> PART = EnumProperty.create("part", JadeVinesPlantPart.class);
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    public JadeVinePlantBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(PART, JadeVinesPlantPart.BASE)
                                                              .setValue(AGE, 1));
    }

    @Override
    public MapCodec<? extends JadeVinePlantBlock> codec() {
        return CODEC;
    }

    public static List<ItemStack> getHarvestedStacks(
        BlockState state, ServerLevel world, BlockPos pos, @Nullable BlockEntity blockEntity, @Nullable Entity entity,
        ItemStack stack, ResourceKey<LootTable> lootTableIdentifier
    ) {
        var builder = (new LootParams.Builder(world))
            .withParameter(LootContextParams.BLOCK_STATE, state)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(pos))
            .withParameter(LootContextParams.TOOL, stack)
            .withOptionalParameter(LootContextParams.THIS_ENTITY, entity)
            .withOptionalParameter(LootContextParams.BLOCK_ENTITY, blockEntity);

        LootTable lootTable = world.getServer()
                                   .reloadableRegistries()
                                   .getLootTable(lootTableIdentifier);
        return lootTable.getRandomItems(builder.create(LootContextParamSets.BLOCK));
    }

    static void setHarvested(@NotNull BlockState blockState, @NotNull ServerLevel world, @NotNull BlockPos blockPos) {
        BlockPos rootsPos = blockState.getValue(PART)
                                      .getLowestRootsPos(blockPos);
        if (world.getBlockState(rootsPos)
                 .getBlock() instanceof JadeVineRootsBlock jadeVineRootsBlock) {
            jadeVineRootsBlock.setPlantToAge(world, rootsPos, 1);
        }
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);

        int age = state.getValue(AGE);
        if (age == BlockStateProperties.MAX_AGE_7) {
            if (random.nextFloat() < 0.3) {
                JadeVine.spawnBloomParticlesClient(world, pos);
            }
        } else if (age != 0) {
            JadeVine.spawnParticlesClient(world, pos);
        }
    }

    @Override
    public BlockState updateShape(
        BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos,
        BlockPos neighborPos
    ) {
        if (!state.canSurvive(world, pos) || missingBottom(state, world.getBlockState(pos.below()))) {
            world.scheduleTick(pos, this, 1);
        }
        return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!state.canSurvive(world, pos) || missingBottom(state, world.getBlockState(pos.below()))) {
            world.destroyBlock(pos, false);
        }
    }

    private boolean missingBottom(BlockState state, BlockState belowState) {
        JadeVinesPlantPart part = state.getValue(PART);
        if (part == JadeVinesPlantPart.TIP) {
            return false;
        } else {
            return !(belowState.getBlock() instanceof JadeVinePlantBlock);
        }
    }

    @Override
    public ItemInteractionResult useItemOn(
        ItemStack handStack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
        BlockHitResult hit
    ) {
        JadeVinesGrowthStage growthStage = JadeVinesGrowthStage.fromAge(state.getValue(AGE));

        if (growthStage.isFullyGrown()) {
            boolean harvested = false;

            if (handStack.is(Items.GLASS_BOTTLE)) {
                if (world.isClientSide) {
                    return ItemInteractionResult.SUCCESS;
                } else {
                    if (player instanceof ServerPlayer serverPlayerEntity) {
                        CriteriaTriggers.ITEM_USED_ON_BLOCK.trigger(serverPlayerEntity, pos, handStack);
                    }

                    handStack.shrink(1);
                    setHarvested(state, (ServerLevel) world, pos);

                    List<ItemStack> harvestedStacks = getHarvestedStacks(
                        state, (ServerLevel) world, pos, world.getBlockEntity(pos), player, handStack,
                        PastelLootTables.JADE_VINE_HARVESTING_NECTAR
                    );
                    for (ItemStack harvestedStack : harvestedStacks) {
                        player.getInventory()
                              .placeItemBackInInventory(harvestedStack);
                    }
                    harvested = true;
                }
            }

            if (!harvested) {
                player.displayClientMessage(
                    Component.translatable("message.pastel.needs_item_to_harvest")
                             .append(Items.GLASS_BOTTLE.getDescription()), true
                );
            }

            return ItemInteractionResult.sidedSuccess(world.isClientSide);
        } else if (growthStage.canHarvestPetals()) {
            if (!world.isClientSide) {
                setHarvested(state, (ServerLevel) world, pos);

                List<ItemStack> harvestedStacks = getHarvestedStacks(
                    state, (ServerLevel) world, pos, world.getBlockEntity(pos), player, player.getMainHandItem(),
                    PastelLootTables.JADE_VINE_HARVESTING_PETALS
                );
                for (ItemStack harvestedStack : harvestedStacks) {
                    player.getInventory()
                          .placeItemBackInInventory(harvestedStack);
                }
            }
            return ItemInteractionResult.sidedSuccess(world.isClientSide);
        }

        return super.useItemOn(handStack, state, world, pos, player, hand, hit);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return PastelItems.GERMINATED_JADE_VINE_BULB.get()
                                                    .getDefaultInstance();
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(PART) == JadeVinesPlantPart.TIP ? TIP_SHAPE : SHAPE;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader world, BlockPos pos) {
        BlockState upState = world.getBlockState(pos.above());
        Block upBlock = upState.getBlock();
        JadeVinesPlantPart part = state.getValue(PART);
        if (part == JadeVinesPlantPart.BASE) {
            return upBlock instanceof JadeVineRootsBlock;
        } else if (part == JadeVinesPlantPart.MIDDLE) {
            return upBlock instanceof JadeVinePlantBlock && upState.getValue(PART) == JadeVinesPlantPart.BASE;
        } else {
            return upBlock instanceof JadeVinePlantBlock && upState.getValue(PART) == JadeVinesPlantPart.MIDDLE;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        builder.add(PART, AGE);
    }

    @Override
    public boolean setToAge(Level world, BlockPos blockPos, int age) {
        BlockState currentState = world.getBlockState(blockPos);
        if (currentState.getBlock() instanceof JadeVinePlantBlock) {
            int currentAge = currentState.getValue(AGE);
            if (age != currentAge) {
                world.setBlockAndUpdate(blockPos, currentState.setValue(AGE, age));
                return true;
            }
        }
        return false;
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide) {
            if (!player.isCreative()) {
                dropResources(state, world, pos, null, player, player.getMainHandItem());
            }
        }

        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public void playerDestroy(
        Level world, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity,
        ItemStack stack
    ) {
        super.playerDestroy(world, player, pos, Blocks.AIR.defaultBlockState(), blockEntity, stack);
    }

    @Override
    public boolean canUseNaturesStaff(Level world, BlockPos pos, BlockState state) {
        return state.getValue(AGE) == 0;
    }

    @Override
    public boolean onNaturesStaffUse(Level world, BlockPos pos, BlockState state, Player player) {
        BlockPos rootsPos = state.getValue(PART)
                                 .getLowestRootsPos(pos);
        BlockState rootsState = world.getBlockState(rootsPos);
        if (rootsState.getBlock() instanceof JadeVineRootsBlock jadeVineRootsBlock) {
            jadeVineRootsBlock.onNaturesStaffUse(world, rootsPos, rootsState, player);
        }
        JadeVine.spawnParticlesServer((ServerLevel) world, pos, 16);
        return false;
    }

    public enum JadeVinesPlantPart implements StringRepresentable {
        BASE,
        MIDDLE,
        TIP;

        @Contract(pure = true)
        public @NotNull String toString() {
            return this.getSerializedName();
        }

        @Override
        @Contract(pure = true)
        public @NotNull String getSerializedName() {
            return this == BASE ? "base" : this == MIDDLE ? "middle" : "tip";
        }

        public BlockPos getLowestRootsPos(BlockPos blockPos) {
            if (this == BASE) {
                return blockPos.above();
            } else if (this == MIDDLE) {
                return blockPos.above(2);
            } else {
                return blockPos.above(3);
            }
        }

    }

    public enum JadeVinesGrowthStage {
        DEAD,
        LEAVES,
        PETALS,
        BLOOM;

        public static JadeVinesGrowthStage fromAge(int age) {
            if (age == 0) {
                return DEAD;
            } else if (age == BlockStateProperties.MAX_AGE_7) {
                return BLOOM;
            } else if (age > 2) {
                return PETALS;
            } else {
                return LEAVES;
            }
        }

        public static boolean isFullyGrown(int age) {
            return age == BlockStateProperties.MAX_AGE_7;
        }

        public boolean isFullyGrown() {
            return this == BLOOM;
        }

        public boolean canHarvestPetals() {
            return this == PETALS || this == BLOOM;
        }

    }

}
