package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.capabilities.ExperienceHandler;
import earth.terrarium.pastel.blocks.chests.BlackHoleChestBlockEntity;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.registries.PastelBlockEntities;
import net.minecraft.server.level.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Optional;

public record BlackHoleChestStatusUpdatePayload(
    BlockPos pos, boolean isFull, boolean canStoreExperience, long storedExperience, long maxStoredExperience
) implements CustomPacketPayload {

    public static final Type<BlackHoleChestStatusUpdatePayload> ID = PastelC2SPackets.makeId(
        "black_hole_chest_status_update");
    public static final StreamCodec<FriendlyByteBuf, BlackHoleChestStatusUpdatePayload> CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, BlackHoleChestStatusUpdatePayload::pos,
        ByteBufCodecs.BOOL, BlackHoleChestStatusUpdatePayload::isFull,
        ByteBufCodecs.BOOL, BlackHoleChestStatusUpdatePayload::canStoreExperience,
        ByteBufCodecs.VAR_LONG, BlackHoleChestStatusUpdatePayload::storedExperience,
        ByteBufCodecs.VAR_LONG, BlackHoleChestStatusUpdatePayload::maxStoredExperience,
        BlackHoleChestStatusUpdatePayload::new
    );

    public static void sendBlackHoleChestUpdate(BlackHoleChestBlockEntity chest) {
        var xpStack = chest.getItem(BlackHoleChestBlockEntity.EXPERIENCE_STORAGE_PROVIDER_ITEM_SLOT);

        long storedXP = 0;
        long maxStoredXP = 0;

        var storage = chest.getExperienceStorage();
        if (storage.isPresent()) {
            storedXP = storage.get()
                              .getStoredAmount();
            maxStoredXP = storage.get()
                                 .getCapacity();
        }

        PacketDistributor.sendToPlayersTrackingChunk(
            (ServerLevel) chest.getLevel(), new ChunkPos(chest.getBlockPos()),
            new BlackHoleChestStatusUpdatePayload(
                chest.getBlockPos(), chest.isFullServer(),
                chest.getExperienceStorage()
                     .isPresent(), storedXP, maxStoredXP
            )
        );
    }

    @SuppressWarnings("resource")
    public static void execute(BlackHoleChestStatusUpdatePayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();
        Optional<BlackHoleChestBlockEntity> entity = level.getBlockEntity(
            payload.pos, PastelBlockEntities.BLACK_HOLE_CHEST.get());
        entity.ifPresent(chest -> {
            chest.setFull(payload.isFull);
            chest.setHasXPStorage(payload.canStoreExperience);
            chest.setXPData(payload.storedExperience, payload.maxStoredExperience);
        });
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
