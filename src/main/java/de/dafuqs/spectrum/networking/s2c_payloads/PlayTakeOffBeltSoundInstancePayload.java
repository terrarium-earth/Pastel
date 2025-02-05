package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.sound.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;

public record PlayTakeOffBeltSoundInstancePayload() implements CustomPayload {
	
	public static final Id<PlayTakeOffBeltSoundInstancePayload> ID = SpectrumC2SPackets.makeId("play_take_off_belt_sound_instance");
	public static final PacketCodec<PacketByteBuf, PlayTakeOffBeltSoundInstancePayload> CODEC = PacketCodec.ofStatic((buf, value) -> {
	}, buf -> new PlayTakeOffBeltSoundInstancePayload());
	
	public static void sendPlayTakeOffBeltSoundInstance(ServerPlayerEntity playerEntity) {
		ServerPlayNetworking.send(playerEntity, new PlayTakeOffBeltSoundInstancePayload());
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PlayTakeOffBeltSoundInstancePayload payload, ClientPlayNetworking.Context context) {
		TakeOffBeltSoundInstance.startSoundInstance();
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
