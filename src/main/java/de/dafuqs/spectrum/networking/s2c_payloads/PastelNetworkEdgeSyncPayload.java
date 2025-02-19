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

public record PastelNetworkEdgeSyncPayload(UUID networkUUID, int color, Graph<BlockPos, DefaultEdge> graph) implements CustomPayload {
	
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
			Set<DefaultEdge> edges = graph.edgeSet();
			buf.writeInt(edges.size());
			for (DefaultEdge edge : edges) {
				buf.writeInt(vertices.indexOf(graph.getEdgeSource(edge)));
				buf.writeInt(vertices.indexOf(graph.getEdgeTarget(edge)));
			}
		}
		
		@Override
		public Graph<BlockPos, DefaultEdge> decode(RegistryByteBuf buf) {
			Graph<BlockPos, DefaultEdge> graph = new SimpleGraph<>(DefaultEdge.class);
			
			int vertexCount = buf.readInt();
			BlockPos[] vertices = new BlockPos[vertexCount];
			for (int i = 0; i < vertexCount; i++) {
				BlockPos vertex = buf.readBlockPos();
				vertices[i] = vertex;
				graph.addVertex(vertex);
			}
			
			int edgeCount = buf.readInt();
			for (int i = 0; i < edgeCount; i++) {
				BlockPos source = vertices[buf.readInt()];
				BlockPos target = vertices[buf.readInt()];
				graph.addEdge(source, target);
			}
			
			return graph;
		}
	};
	
	public static final Id<PastelNetworkEdgeSyncPayload> ID = SpectrumC2SPackets.makeId("pastel_network_edge_sync");
	public static final PacketCodec<RegistryByteBuf, PastelNetworkEdgeSyncPayload> CODEC = PacketCodec.tuple(
			Uuids.PACKET_CODEC, PastelNetworkEdgeSyncPayload::networkUUID,
			PacketCodecs.INTEGER, PastelNetworkEdgeSyncPayload::color,
			GRAPH_PACKET_CODEC, PastelNetworkEdgeSyncPayload::graph,
			PastelNetworkEdgeSyncPayload::new
	);
	
	public static void send(ServerPastelNetwork network, BlockPos pos) {
		for (ServerPlayerEntity player : PlayerLookup.tracking(network.getWorld(), pos)) {
			ServerPlayNetworking.send(player, new PastelNetworkEdgeSyncPayload(network.getUUID(), network.getColor(), network.getGraph()));
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
				PastelNetwork<ClientWorld> newNetwork = Pastel.getClientInstance().createNetwork(client.world, payload.networkUUID, payload.color);
				newNetwork.setGraph(payload.graph);
			}
		});
	}
	
	@Override
	public Id<? extends CustomPayload> getId() {
		return ID;
	}
}
