package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pastel_network.*;
import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;

import java.util.*;

public record PastelNetworkRemovedPayload(UUID networkUUID) implements CustomPayload {
	
	public static final Id<PastelNetworkRemovedPayload> ID = SpectrumC2SPackets.makeId("pastel_network_removed");
	public static final PacketCodec<PacketByteBuf, PastelNetworkRemovedPayload> CODEC = PacketCodec.tuple(
			Uuids.PACKET_CODEC, PastelNetworkRemovedPayload::networkUUID,
			PastelNetworkRemovedPayload::new
	);
	
	public static void send(ServerPastelNetwork network) {
		for (ServerPlayerEntity player : PlayerLookup.world(network.getWorld())) {
			ServerPlayNetworking.send(player, new PastelNetworkRemovedPayload(network.getUUID()));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PastelNetworkRemovedPayload payload, ClientPlayNetworking.Context context) {
		context.client().execute(() -> Pastel.getClientInstance().removeNetwork(payload.networkUUID));
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
