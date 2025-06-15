package earth.terrarium.pastel.networking.s2c_payloads;

import earth.terrarium.pastel.blocks.pastel_network.Pastel;
import earth.terrarium.pastel.blocks.pastel_network.network.ClientPastelNetwork;
import earth.terrarium.pastel.blocks.pastel_network.network.ServerPastelNetwork;
import earth.terrarium.pastel.networking.SpectrumC2SPackets;
import net.minecraft.client.multiplayer.*;
import net.minecraft.world.level.*;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.*;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
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
		PacketDistributor.sendToPlayersTrackingChunk(network.getLevel(), new ChunkPos(pos), new PastelNetworkEdgeSyncPayload(network.getUUID(), network.getColor(), network.getGraph()));
	}
	
	public static void execute(PastelNetworkEdgeSyncPayload payload, IPayloadContext context) {
		context.enqueueWork(() -> {
			var level = context.player().level();
			Optional<? extends ClientPastelNetwork> network = Pastel.getClientInstance().getNetwork(payload.networkUUID);
			if (network.isPresent()) {
				network.get().setGraph(payload.graph);
			} else {
				ClientPastelNetwork newNetwork = Pastel.getClientInstance().createNetwork((ClientLevel) level, payload.networkUUID, payload.color);
				newNetwork.setGraph(payload.graph);
			}
		});
	}
	
	@Override
	public Type<? extends CustomPacketPayload> type() {
		return ID;
	}
}
