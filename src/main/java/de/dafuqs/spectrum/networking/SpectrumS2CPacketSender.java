package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.*;
import net.minecraft.server.network.*;
import net.minecraft.util.math.*;

public class SpectrumS2CPacketSender {
	
	public static void syncPastelNetworkEdges(ServerPastelNetwork serverPastelNetwork, BlockPos pos) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeUuid(serverPastelNetwork.getUUID());
		buf.writeNbt(serverPastelNetwork.graphToNbt());
		
		for (ServerPlayerEntity player : PlayerLookup.tracking(serverPastelNetwork.getWorld(), pos)) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PASTEL_NETWORK_EDGE_SYNC, buf);
		}
	}
	
	public static void syncPastelNetworkRemoved(ServerPastelNetwork network) {
		PacketByteBuf buf = PacketByteBufs.create();
		buf.writeUuid(network.getUUID());
		
		for (ServerPlayerEntity player : PlayerLookup.all(network.getWorld().getServer())) {
			ServerPlayNetworking.send(player, SpectrumS2CPackets.PASTEL_NETWORK_REMOVED, buf);
		}
	}
	
}