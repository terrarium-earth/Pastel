package earth.terrarium.pastel.blocks.deeper_down.flora;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.helpers.level.BlockReference;
import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelBlocks;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

public class AbyssalVineBlock extends TriStateVineBlock {

    public static final MapCodec<AbyssalVineBlock> CODEC = simpleCodec(AbyssalVineBlock::new);

    public static final BooleanProperty BERRIES = BlockStateProperties.BERRIES;

    public AbyssalVineBlock(Properties settings) {
        super(settings, 5, 0.3F, 0.4F, 0.667F);
        registerDefaultState(defaultBlockState().setValue(BERRIES, false));
    }

    @Override
    public MapCodec<? extends AbyssalVineBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return !state.getValue(BERRIES);
    }

    @Override
    public InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        var reference = BlockReference.of(state, pos);
        var superSucc = super.useWithoutItem(state, world, pos, player, hit);

        if (superSucc.indicateItemUse()) {
            return superSucc;
        }

        if (!reference.getProperty(BERRIES))
            return InteractionResult.FAIL;

        reference.setProperty(BERRIES, false);
        reference.update(world);
        world.playSound(null, pos, SoundEvents.CAVE_VINES_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, Mth.randomBetween(world.random, 0.8F, 1.2F));
        player.getInventory().placeItemBackInInventory(PastelItems.FISSURE_PLUM.get().getDefaultInstance());

        world.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(player, reference.getState()));
        return InteractionResult.SUCCESS;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        var reference = BlockReference.of(state, pos);
        var growthChance = 0.8F;

        for (int offset = 0; true; offset++) {
            var ref = BlockReference.of(world, pos.offset(0, offset, 0));

            if (ref.isOf(PastelBlocks.SHALE_CLAY.get()))
                return;

            if (ref.isIn(PastelBlockTags.GROWTH_ACCELERATORS)) {
                growthChance = 0.5F;
            }

            if (!ref.isOf(this))
                break;
        }

        if (random.nextFloat() < growthChance)
            return;

        if (!reference.getProperty(BERRIES))
            tryGrowBerries(reference, world);
        reference.update(world);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return super.isRandomlyTicking(state) || !state.getValue(BERRIES);
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state) {
        return PastelItems.FISSURE_PLUM.get().getDefaultInstance();
    }

    @Override
    boolean hasGrowthActions() {
        return true;
    }

    public void tryGrowBerries(BlockReference reference, Level world) {
        int berryCount = 0;

        for (int i = 0; i < 3; i++) {
            var uRef = BlockReference.of(world, reference.pos.offset(0, i, 0));
            var dRef = BlockReference.of(world, reference.pos.offset(0, -i, 0));

            berryCount += checkForBerries(uRef);
            berryCount += checkForBerries(dRef);

            if (i == 1 && (reference.pos.getY() % 5 == 0 && berryCount == 2) || (reference.pos.getY() % 7 == 0 && berryCount == 1))
                return;
        }

        if (berryCount >= 3)
            return;

        reference.setProperty(BERRIES, true);
    }

    private int checkForBerries(BlockReference ref) {
        if (ref.isOf(this) && ref.getProperty(BERRIES)) {
            return 1;
        }
        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BERRIES);
    }
}
