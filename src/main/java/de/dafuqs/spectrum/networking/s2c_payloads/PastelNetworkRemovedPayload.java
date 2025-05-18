package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pastel_network.Pastel;
import de.dafuqs.spectrum.blocks.pastel_network.network.ServerPastelNetwork;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;

import java.util.UUID;

public record PastelNetworkRemovedPayload(UUID networkUUID) implements CustomPacketPayload {
	
	public static final Type<PastelNetworkRemovedPayload> ID = SpectrumC2SPackets.makeId("pastel_network_removed");
	public static final StreamCodec<FriendlyByteBuf, PastelNetworkRemovedPayload> CODEC = StreamCodec.composite(
			UUIDUtil.STREAM_CODEC, PastelNetworkRemovedPayload::networkUUID,
			PastelNetworkRemovedPayload::new
	);
	
	public static void send(ServerPastelNetwork network) {
		for (ServerPlayer player : PlayerLookup.world(network.getWorld())) {
			ServerPlayNetworking.send(player, new PastelNetworkRemovedPayload(network.getUUID()));
		}
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(PastelNetworkRemovedPayload payload, ClientPlayNetworking.Context context) {
		context.client().execute(() -> Pastel.getClientInstance().removeNetwork(payload.networkUUID));
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
