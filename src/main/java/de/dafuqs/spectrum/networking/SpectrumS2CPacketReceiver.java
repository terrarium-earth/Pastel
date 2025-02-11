package de.dafuqs.spectrum.networking;

import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;

import java.util.*;

@Environment(EnvType.CLIENT)
public class SpectrumS2CPacketReceiver {
	
	@SuppressWarnings("deprecation")
	public static void registerS2CReceivers() {
		
		ClientPlayNetworking.registerGlobalReceiver(SpectrumS2CPackets.PASTEL_NETWORK_EDGE_SYNC, (client, handler, buf, responseSender) -> {
			UUID uuid = buf.readUuid();
			NbtCompound nbt = buf.readNbt();
			
			client.execute(() -> {
				Optional<? extends PastelNetwork<ClientWorld>> network = Pastel.getClientInstance().getNetwork(uuid);
				if (network.isPresent()) {
					network.get().setGraph(PastelNetwork.graphFromNbt(nbt));
				} else {
					PastelNetwork<ClientWorld> pn = Pastel.getClientInstance().createNetwork(client.world, uuid);
					pn.setGraph(PastelNetwork.graphFromNbt(nbt));
				}
			});
		});
		
		ClientPlayNetworking.registerGlobalReceiver(SpectrumS2CPackets.PASTEL_NETWORK_REMOVED, (client, handler, buf, responseSender) -> {
			UUID uuid = buf.readUuid();
			
			client.execute(() -> {
				Pastel.getClientInstance().removeNetwork(uuid);
			});
		});
	}
	
}
