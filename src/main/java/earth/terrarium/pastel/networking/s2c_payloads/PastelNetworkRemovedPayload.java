package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.blocks.pastel_network.network.ServerPastelNetwork;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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
		PacketDistributor.sendToPlayersInDimension(network.getLevel(), new PastelNetworkRemovedPayload(network.getUUID()));
	}
	
	@OnlyIn(Dist.CLIENT)
	public static void execute(PastelNetworkRemovedPayload payload, IPayloadContext context) {
		context.enqueueWork(() -> {
			Pastel.getClientInstance().removeNetwork(payload.networkUUID);
		});
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
