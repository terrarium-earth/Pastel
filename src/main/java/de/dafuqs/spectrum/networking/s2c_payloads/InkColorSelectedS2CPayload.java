package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.api.block.*;
import de.dafuqs.spectrum.api.energy.color.*;
import de.dafuqs.spectrum.helpers.*;
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

public record InkColorSelectedS2CPayload(RegistryEntry<InkColor> color) implements CustomPayload {
	
	public static final Id<InkColorSelectedS2CPayload> ID = SpectrumC2SPackets.makeId("ink_color_selected");
	public static final PacketCodec<RegistryByteBuf, InkColorSelectedS2CPayload> CODEC = PacketCodec.tuple(
			PacketCodecHelper.nullable(PacketCodecs.registryEntry(SpectrumRegistries.INK_COLORS.getKey())), InkColorSelectedS2CPayload::color,
			InkColorSelectedS2CPayload::new
	);
	
	public static void sendInkColorSelected(RegistryEntry<InkColor> color, ServerPlayerEntity player) {
		ServerPlayNetworking.send(player, new InkColorSelectedS2CPayload(color));
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(InkColorSelectedS2CPayload payload, ClientPlayNetworking.Context context) {
		ScreenHandler screenHandler = context.player().currentScreenHandler;
		if (screenHandler instanceof InkColorSelectedPacketReceiver inkColorSelectedPacketReceiver) {
			inkColorSelectedPacketReceiver.onInkColorSelectedPacket(payload.color);
		}
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
	
}
