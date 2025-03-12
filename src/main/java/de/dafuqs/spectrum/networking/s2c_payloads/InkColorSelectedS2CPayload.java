package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.networking.*;
import de.dafuqs.spectrum.registries.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.registry.entry.*;
import net.minecraft.screen.*;
import net.minecraft.server.network.*;

import java.util.*;

public record InkColorSelectedS2CPayload(Optional<RegistryEntry<InkColor>> inkColor) implements CustomPayload {
	
	public static final Id<InkColorSelectedS2CPayload> ID = SpectrumC2SPackets.makeId("ink_color_selected");
	public static final PacketCodec<RegistryByteBuf, InkColorSelectedS2CPayload> CODEC = PacketCodec.tuple(
			PacketCodecs.optional(PacketCodecs.registryEntry(SpectrumRegistries.INK_COLOR.getKey())), InkColorSelectedS2CPayload::inkColor,
			InkColorSelectedS2CPayload::new
	);
	
	public static void sendInkColorSelected(Optional<RegistryEntry<InkColor>> inkColor, ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new InkColorSelectedS2CPayload(inkColor));
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(InkColorSelectedS2CPayload payload, ClientPlayNetworking.Context context) {
		ScreenHandler screenHandler = context.player().currentScreenHandler;
		if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
			inkColorSelectedPacketReceiver.onInkColorSelectedPacket(payload.inkColor());
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}
