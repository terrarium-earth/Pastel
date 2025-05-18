package de.dafuqs.spectrum.networking.s2c_payloads;

import de.dafuqs.spectrum.blocks.pastel_network.Pastel;
import de.dafuqs.spectrum.blocks.pastel_network.network.ClientPastelNetwork;
import de.dafuqs.spectrum.blocks.pastel_network.network.ServerPastelNetwork;
import de.dafuqs.spectrum.networking.SpectrumC2SPackets;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public record PastelNetworkEdgeSyncPayload(UUID networkUUID, int color, Graph<BlockPos, DefaultEdge> graph) implements CustomPacketPayload {
	
	public static final StreamCodec<RegistryFriendlyByteBuf, Graph<BlockPos, DefaultEdge>> GRAPH_PACKET_CODEC = new StreamCodec<>() {
		
		@Override
		public void encode(RegistryFriendlyByteBuf buf, Graph<BlockPos, DefaultEdge> graph) {
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
		public Graph<BlockPos, DefaultEdge> decode(RegistryFriendlyByteBuf buf) {
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
	
	public static final Type<PastelNetworkEdgeSyncPayload> ID = SpectrumC2SPackets.makeId("pastel_network_edge_sync");
	public static final StreamCodec<RegistryFriendlyByteBuf, PastelNetworkEdgeSyncPayload> CODEC = StreamCodec.composite(
			UUIDUtil.STREAM_CODEC, PastelNetworkEdgeSyncPayload::networkUUID,
			ByteBufCodecs.INT, PastelNetworkEdgeSyncPayload::color,
			GRAPH_PACKET_CODEC, PastelNetworkEdgeSyncPayload::graph,
			PastelNetworkEdgeSyncPayload::new
	);
	
	public static void send(ServerPastelNetwork network, BlockPos pos) {
		for (ServerPlayer player : PlayerLookup.tracking(network.getWorld(), pos)) {
			ServerPlayNetworking.send(player, new PastelNetworkEdgeSyncPayload(network.getUUID(), network.getColor(), network.getGraph()));
		}
	}
	
	@Environment(EnvType.CLIENT)
	public static void execute(PastelNetworkEdgeSyncPayload payload, ClientPlayNetworking.Context context) {
		Minecraft client = context.client();
		client.execute(() -> {
			Optional<? extends ClientPastelNetwork> network = Pastel.getClientInstance().getNetwork(payload.networkUUID);
			if (network.isPresent()) {
				network.get().setGraph(payload.graph);
			} else {
				ClientPastelNetwork newNetwork = Pastel.getClientInstance().createNetwork(client.level, payload.networkUUID, payload.color);
				newNetwork.setGraph(payload.graph);
			}
		});
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
