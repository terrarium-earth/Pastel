package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.chests.CompactingChestBlockEntity;
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

public record CompactingChestStatusUpdatePayload(BlockPos pos, long timeStamp) implements CustomPacketPayload {

    public static final Type<CompactingChestStatusUpdatePayload> ID = PastelC2SPackets.makeId(
        "compacting_chest_status_update");
    public static final StreamCodec<FriendlyByteBuf, CompactingChestStatusUpdatePayload> CODEC = StreamCodec.composite(
        BlockPos.STREAM_CODEC, CompactingChestStatusUpdatePayload::pos,
        ByteBufCodecs.VAR_LONG, CompactingChestStatusUpdatePayload::timeStamp,
        CompactingChestStatusUpdatePayload::new
    );

    public static void sendCompactingChestStatusUpdate(CompactingChestBlockEntity chest) {
        PacketDistributor.sendToPlayersTrackingChunk(
            (ServerLevel) chest.getLevel(), new ChunkPos(chest.getBlockPos()),
            new CompactingChestStatusUpdatePayload(chest.getBlockPos(), chest.craftingTimeStamp)
        );
    }

    @SuppressWarnings("resource")
    public static void execute(CompactingChestStatusUpdatePayload payload, IPayloadContext context) {
        var level = context.player()
                           .level();
        var entity = level.getBlockEntity(payload.pos, PastelBlockEntities.COMPACTING_CHEST.get());
        entity.ifPresent(
            compactingChestBlockEntity -> compactingChestBlockEntity.craftingTimeStamp = payload.timeStamp());
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
