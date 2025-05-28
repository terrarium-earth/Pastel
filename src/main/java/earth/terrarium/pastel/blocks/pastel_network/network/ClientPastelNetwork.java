package earth.terrarium.pastel.blocks.pastel_network.network;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

import java.util.UUID;

public class ClientPastelNetwork extends PastelNetwork<ClientLevel> {
	
	protected long lastChangeTick;
	
	public ClientPastelNetwork(ClientLevel world, UUID uuid, int color) {
		super(world, uuid, color);
		this.lastChangeTick = world.getGameTime();
	}
	
	public void setGraph(Graph<BlockPos, DefaultEdge> graph) {
		this.graph = graph;
		this.lastChangeTick = world.getGameTime();
	}
	
}
