package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pastel_network.*;
import de.dafuqs.spectrum.blocks.pastel_network.network.*;
import de.dafuqs.spectrum.networking.*;
import net.fabricmc.api.*;
import net.fabricmc.fabric.api.client.networking.v1.*;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.client.*;
import net.minecraft.client.world.*;
import net.minecraft.network.*;
import net.minecraft.network.codec.*;
import net.minecraft.network.packet.*;
import net.minecraft.server.network.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;

import java.util.*;

public record PastelNetworkEdgeSyncPayload(UUID networkUUID, Graph<BlockPos, DefaultEdge> graph) implements CustomPayload {
	
	public static final PacketCodec<RegistryByteBuf, Graph<BlockPos, DefaultEdge>> GRAPH_PACKET_CODEC = new PacketCodec<>() {
		
		@Override
		public void encode(RegistryByteBuf buf, Graph<BlockPos, DefaultEdge> graph) {
			ArrayList<BlockPos> vertices = new ArrayList<>(graph.vertexSet());
			
			// Vertices
			buf.writeInt(vertices.size());
			for (BlockPos vertex : vertices) {
				buf.writeBlockPos(vertex);
			}
			
			// Edges (as index in vertices)
			for (int i = 0; i < vertices.size(); i++) {
				var vertex = vertices.get(i);
				
				Set<DefaultEdge> edges = graph.edgesOf(vertex);
				buf.writeInt(edges.size());
				for (DefaultEdge edge : edges) {
					BlockPos target = graph.getEdgeTarget(edge);
					buf.writeInt(vertices.indexOf(target));
				}
			}
		}
		
		@Override
		public Graph<BlockPos, DefaultEdge> decode(RegistryByteBuf buf) {
			Graph<BlockPos, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
			
			int verticeCount = buf.readInt();
			ArrayList<BlockPos> vertices = new ArrayList<>(verticeCount);
			for (int i = 0; i < verticeCount; i++) {
				vertices.add(buf.readBlockPos());
				graph.addVertex(buf.readBlockPos());
			}
			
			for (BlockPos source : vertices) {
				int edgeCount = buf.readInt();
				for (int j = 0; j < edgeCount; j++) {
					BlockPos target = vertices.get(buf.readInt());
					graph.addEdge(source, target);
				}
			}
			
			return graph;
		}
	};
	
	public static final Id<PastelNetworkEdgeSyncPayload> ID = SpectrumC2SPackets.makeId("pastel_network_edge_sync");
	public static final PacketCodec<RegistryByteBuf, PastelNetworkEdgeSyncPayload> CODEC = PacketCodec.tuple(
			Uuids.PACKET_CODEC, PastelNetworkEdgeSyncPayload::networkUUID,
			GRAPH_PACKET_CODEC, PastelNetworkEdgeSyncPayload::graph,
			PastelNetworkEdgeSyncPayload::new
	);
	
	public static void send(ServerPastelNetwork network, BlockPos pos) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(network.getWorld(), pos)) {
			ServerPlayNetworking.send(player, new PastelNetworkEdgeSyncPayload(network.getUUID(), network.getGraph()));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PastelNetworkEdgeSyncPayload payload, ClientPlayNetworking.Context context) {
		MinecraftClient client = context.client();
		client.execute(() -> {
			Optional<? extends PastelNetwork<ClientWorld>> network = Pastel.getClientInstance().getNetwork(payload.networkUUID);
			if (network.isPresent()) {
				
				
				network.get().setGraph(payload.graph);
			} else {
				PastelNetwork<ClientWorld> newNetwork = Pastel.getClientInstance().createNetwork(client.world, payload.networkUUID);
				newNetwork.setGraph(payload.graph);
			}
		});
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
