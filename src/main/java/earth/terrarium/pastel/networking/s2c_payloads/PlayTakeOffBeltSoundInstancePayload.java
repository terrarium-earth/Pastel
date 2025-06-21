package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.networking.PastelC2SPackets;
import earth.terrarium.pastel.sound.TakeOffBeltSoundInstance;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record PlayTakeOffBeltSoundInstancePayload() implements CustomPacketPayload {
	
	public static final Type<PlayTakeOffBeltSoundInstancePayload> ID = PastelC2SPackets.makeId("play_take_off_belt_sound_instance");
	public static final StreamCodec<FriendlyByteBuf, PlayTakeOffBeltSoundInstancePayload> CODEC = StreamCodec.of((buf, value) -> {
	}, buf -> new PlayTakeOffBeltSoundInstancePayload());
	
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
