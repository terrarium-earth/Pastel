package earth.terrarium.pastel.attachments.data;

import earth.terrarium.pastel.*;
import net.minecraft.core.*;
import net.minecraft.network.protocol.common.custom.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.level.*;
import net.neoforged.neoforge.network.*;

public final class AttachmentUtil {

    public static void syncToPlayer(CustomPacketPayload payload, Player player) {
        if (player instanceof ServerPlayer sp)
            PacketDistributor.sendToPlayer(sp, payload);
    }

    public static void syncToTracking(CustomPacketPayload payload, Level level, BlockPos pos) {
        if (level instanceof ServerLevel sl)
            PacketDistributor.sendToPlayersTrackingChunk(sl, new ChunkPos(pos), payload);
    }

    public static <L extends CustomPacketPayload> CustomPacketPayload.Type<L> create(String name) {
        return new CustomPacketPayload.Type<>(PastelCommon.locate(name));
    }
}
