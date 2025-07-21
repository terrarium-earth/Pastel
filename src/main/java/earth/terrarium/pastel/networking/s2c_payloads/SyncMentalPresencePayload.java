package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.networking.PastelC2SPackets;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record SyncMentalPresencePayload(double value) implements CustomPacketPayload {

    public static final Type<SyncMentalPresencePayload> ID = PastelC2SPackets.makeId("sync_mental_presence");
    public static final StreamCodec<FriendlyByteBuf, SyncMentalPresencePayload> CODEC = StreamCodec.composite(
        ByteBufCodecs.DOUBLE, SyncMentalPresencePayload::value,
        SyncMentalPresencePayload::new
    );

    public static void sendMentalPresenceSync(ServerPlayer player, double value) {
        PacketDistributor.sendToPlayer(player, new SyncMentalPresencePayload(value));
    }

    public static void execute(SyncMentalPresencePayload payload, IPayloadContext context) {
        var player = context.player();
        MiscPlayerData.get(player)
                      .setLastSyncedSleepPotency(payload.value);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
