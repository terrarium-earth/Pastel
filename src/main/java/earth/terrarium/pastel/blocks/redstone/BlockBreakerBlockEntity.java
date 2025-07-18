package earth.terrarium.pastel.blocks.redstone;

import earth.terrarium.pastel.api.block.PlayerOwned;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.UUID;

public class BlockBreakerBlockEntity extends BlockEntity implements PlayerOwned {

    private UUID ownerUUID;

    public BlockBreakerBlockEntity(BlockPos pos, BlockState state) {
        super(PastelBlockEntities.BLOCK_BREAKER.get(), pos, state);
    }

    @Override
    public UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    @Override
    public void setOwner(Player playerEntity) {
        this.ownerUUID = playerEntity.getUUID();
        this.setChanged();
    }

    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.saveAdditional(nbt, registryLookup);

        PlayerOwned.writeOwnerUUID(nbt, this.ownerUUID);
    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        super.loadAdditional(nbt, registryLookup);

        this.ownerUUID = PlayerOwned.readOwnerUUID(nbt);
    }

}
