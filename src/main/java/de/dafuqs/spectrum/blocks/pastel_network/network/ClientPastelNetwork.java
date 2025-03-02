package de.dafuqs.spectrum.blocks.pastel_network.network;

import net.minecraft.client.world.*;
import net.minecraft.util.math.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;

import java.util.*;

public class ClientPastelNetwork extends PastelNetwork<ClientWorld> {
	
	protected long lastChangeTick;
	
	public ClientPastelNetwork(ClientWorld world, UUID uuid, int color) {
		super(world, uuid, color);
		this.lastChangeTick = world.getTime();
	}
	
	public void setGraph(Graph<BlockPos, DefaultEdge> graph) {
		this.graph = graph;
		this.lastChangeTick = world.getTime();
	}
	
}
