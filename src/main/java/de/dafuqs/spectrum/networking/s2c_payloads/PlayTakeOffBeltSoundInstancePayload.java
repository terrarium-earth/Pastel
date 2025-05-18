package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import de.dafuqs.spectrum.sound.TakeOffBeltSoundInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

public record PlayTakeOffBeltSoundInstancePayload() implements CustomPacketPayload {
	
	public static final Type<PlayTakeOffBeltSoundInstancePayload> ID = SpectrumC2SPackets.makeId("play_take_off_belt_sound_instance");
	public static final StreamCodec<FriendlyByteBuf, PlayTakeOffBeltSoundInstancePayload> CODEC = StreamCodec.of((buf, value) -> {
	}, buf -> new PlayTakeOffBeltSoundInstancePayload());
	
	public static void sendPlayTakeOffBeltSoundInstance(ServerPlayer playerEntity) {
		ServerPlayNetworking.send(playerEntity, new PlayTakeOffBeltSoundInstancePayload());
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PlayTakeOffBeltSoundInstancePayload payload, ClientPlayNetworking.Context context) {
		TakeOffBeltSoundInstance.startSoundInstance();
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
