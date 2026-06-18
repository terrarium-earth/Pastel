package earth.terrarium.pastel.blocks.chests;

import com.mojang.serialization.MapCodec;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEventListener;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class BlackHoleChestBlock extends PastelChestBlock {

    public static final MapCodec<BlackHoleChestBlock> CODEC = simpleCodec(BlackHoleChestBlock::new);

    protected static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 9.0D, 15.0D);

    public BlackHoleChestBlock(Properties settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    @Nullable public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new BlackHoleChestBlockEntity(pos, state);
    }

    @Override
    @Nullable public <T extends BlockEntity> BlockEntityTicker<T> getTicker(
        Level world,
        BlockState state,
        BlockEntityType<T> type
    ) {
        return createTickerHelper(type, PastelBlockEntities.BLACK_HOLE_CHEST.get(), BlackHoleChestBlockEntity::tick);
    }

    @Override
    public void openScreen(Level world, BlockPos pos, Player player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof BlackHoleChestBlockEntity blackHoleChestBlockEntity) {
            if (!isChestBlocked(world, pos)) {
                player.openMenu(blackHoleChestBlockEntity);
            }
        }
    }

    @Override
    @Nullable public <T extends BlockEntity> GameEventListener getListener(ServerLevel world, T blockEntity) {
        return blockEntity instanceof BlackHoleChestBlockEntity blackHoleChestBlockEntity
            ? blackHoleChestBlockEntity.getEventListener()
            : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
