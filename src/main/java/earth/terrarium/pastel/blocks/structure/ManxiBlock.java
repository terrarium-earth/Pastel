package earth.terrarium.pastel.blocks.structure;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.api.block.WardDisruptableBlock;
import earth.terrarium.pastel.registries.*;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class ManxiBlock extends HorizontalDirectionalBlock implements EntityBlock, WardDisruptableBlock {

    public static final MapCodec<ManxiBlock> CODEC = simpleCodec(ManxiBlock::new);

    public ManxiBlock(Properties settings) {
        super(settings);
    }

    @Override
    public MapCodec<? extends ManxiBlock> codec() {
        return CODEC;
    }

    @Nullable @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this
            .defaultBlockState()
            .setValue(
                FACING,
                ctx
                    .getHorizontalDirection()
                    .getOpposite()
            );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public InteractionResult useWithoutItem(
        BlockState state,
        Level world,
        BlockPos pos,
        Player player,
        BlockHitResult hit
    ) {
        var entity = world.getBlockEntity(pos);

        if (!(entity instanceof PlayerTrackerBlockEntity manxi)) return InteractionResult.PASS;

        if (manxi.hasTaken(player)) return InteractionResult.FAIL;

        world.playLocalSound(pos, SoundEvents.CHISELED_BOOKSHELF_PICKUP_ENCHANTED, SoundSource.BLOCKS, 1F, 1F, true);
        player
            .getInventory()
            .placeItemBackInInventory(
                PastelItems.POISONERS_HANDBOOK
                    .get()
                    .getDefaultInstance()
            );
        manxi.markTaken(player);

        return InteractionResult.CONSUME;
    }

    @Override
    public void attack(BlockState state, Level world, BlockPos pos, Player player) {
        if (!world.isClientSide() && !player.getAbilities().instabuild) {
            player
                .displayClientMessage(
                    Component
                        .translatable("block.pastel.manxi.nope")
                        .withStyle(s -> s.withColor(PastelMobEffects.ETERNAL_SLUMBER_COLOR)),
                    true
                );
            world.playLocalSound(pos, PastelSounds.DEEP_CRYSTAL_RING, SoundSource.BLOCKS, 1, 1.5F, true);
            player.hurt(PastelDamageTypes.sleep(world, null), 6);
            player.knockback(2, player.getX() - (pos.getX() + 0.5), player.getZ() - (pos.getZ() + 0.5));
        }
    }

    @Override
    public void onWardDisrupt(BlockPos pos, BlockState state, Level level, Entity trigger) {
        if (!level.isClientSide() && trigger instanceof Projectile projectile && projectile
            .getOwner() instanceof ServerPlayer player && level instanceof ServerLevel serverLevel) {
            var advancement = serverLevel
                .getServer()
                .getAdvancements()
                .get(PastelAdvancements.Hidden.GET_DENIED_BY_MANXI);
            if (advancement == null) return;
            player
                .getAdvancements()
                .revoke(advancement, "rejected");
            player
                .getAdvancements()
                .award(advancement, "rejected");
            player.hurt(PastelDamageTypes.sleep(level, null), 40);
        }
    }

    @Nullable @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlayerTrackerBlockEntity(pos, state);
    }
}
