package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.sound.TakeOffBeltSoundInstance;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record PlayTakeOffBeltSoundInstancePayload() implements CustomPacketPayload {

    public static final Type<PlayTakeOffBeltSoundInstancePayload> ID = PastelC2SPackets
        .makeId(
            "play_takeoff_belt_sound_instance"
        );

    public static final StreamCodec<FriendlyByteBuf, PlayTakeOffBeltSoundInstancePayload> CODEC = StreamCodec
        .of(
            (buf, value) -> {
            },
            buf -> new PlayTakeOffBeltSoundInstancePayload()
        );

    public static void sendPlayTakeOffBeltSoundInstance(ServerPlayer playerEntity) {
        PacketDistributor.sendToPlayer(playerEntity, new PlayTakeOffBeltSoundInstancePayload());
    }

    public static void execute(PlayTakeOffBeltSoundInstancePayload payload, IPayloadContext context) {
        TakeOffBeltSoundInstance.startSoundInstance();
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
