package earth.terrarium.pastel.blocks;

import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class ActionableBlockEntity extends BlockEntity {

    protected FriendlyStackHandler inventory;
    private final int invSize;

    public ActionableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int size, boolean listen) {
        super(type, pos, blockState);
        this.invSize = size;

        if (size == -1)
            return;

        this.inventory = new FriendlyStackHandler(size);
        if (listen)
            inventory.addListener(i -> inventoryChanged());
    }

    public ActionableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState, int size) {
        this(type, pos, blockState, size, false);
    }

    public void inventoryChanged() {
        assert level!=null;
        if (level.isClientSide())
            return;

        setChanged();
    }

    public void updateInClientWorld() {
        if (level instanceof ServerLevel serverWorld)
            serverWorld.getChunkSource().blockChanged(worldPosition);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        if (inventory == null)
            return;

        var inner = new CompoundTag();
        inventory.save(inner, registries);
        tag.put("inventory", inner);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        if (invSize >= 0 && tag.contains("inventory"))
            inventory.load(tag.getCompound("inventory"), registries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        var tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }
}
