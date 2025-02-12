package de.dafuqs.spectrum.blocks.pastel_network.network;

import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.*;
import net.minecraft.nbt.*;
import net.minecraft.registry.*;
import net.minecraft.server.world.*;
import net.minecraft.world.*;
import org.jetbrains.annotations.*;

import java.util.*;

// Persisted together with the overworld. Resetting the overworld will also reset all networks
public class ServerPastelNetworkManager extends PersistentState implements PastelNetworkManager<ServerWorld, ServerPastelNetwork> {
	
	private static final String PERSISTENT_STATE_ID = "spectrum_pastel_network_manager";
	
	private final List<ServerPastelNetwork> networks = new ArrayList<>();
	
	public ServerPastelNetworkManager() {
		super();
	}
	
	@Override
	public boolean isDirty() {
		return true;
	}
	
	public static ServerPastelNetworkManager get(ServerWorld world) {
		// TODO: We need to spoof a datafixer type, null will prevent data from being read
		PersistentState.Type<ServerPastelNetworkManager> type = new PersistentState.Type<>(ServerPastelNetworkManager::new, (nbtCompound, lookup) -> ServerPastelNetworkManager.fromNbt(nbtCompound), null);
		return world.getPersistentStateManager().getOrCreate(type, PERSISTENT_STATE_ID);
	}
	
	@Override
	public ServerPastelNetwork createNetwork(ServerWorld world, UUID uuid) {
		ServerPastelNetwork network = new ServerPastelNetwork(world, uuid);
		this.networks.add(network);
		return network;
	}
	
	@Override
	public Optional<ServerPastelNetwork> getNetwork(UUID uuid) {
		return networks.stream().filter(n -> n.uuid.equals(uuid)).findFirst();
	}
	
	
	@Override
	public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
		NbtList networkList = new NbtList();
		for (ServerPastelNetwork network : this.networks) {
			CodecHelper.toNbt(ServerPastelNetwork.CODEC, network, networkList::add);
		}
		nbt.put("Networks", networkList);
		return nbt;
	}
	
	public static ServerPastelNetworkManager fromNbt(NbtCompound nbt) {
		ServerPastelNetworkManager manager = new ServerPastelNetworkManager();
		for (NbtElement element : nbt.getList("Networks", NbtElement.COMPOUND_TYPE)) {
			Optional<ServerPastelNetwork> network = CodecHelper.fromNbt(ServerPastelNetwork.CODEC, element);
			if (network.isPresent()) {
				manager.networks.add(network.get());
			}
		}
		return manager;
	}
	
	public void tick() {
		// using a for here instead of foreach
		// to prevent ConcurrentModificationExceptions
		//noinspection ForLoopReplaceableByForEach
		for (int i = 0; i < this.networks.size(); i++) {
			this.networks.get(i).tick();
		}
	}
	
	@Contract("_, null -> new")
	public PastelNetwork<ServerWorld> joinOrCreateNetwork(PastelNodeBlockEntity node, @Nullable UUID uuid) {
		if (uuid != null) {
			//noinspection ForLoopReplaceableByForEach
			for (int i = 0; i < this.networks.size(); i++) {
				ServerPastelNetwork network = this.networks.get(i);
				if (network.getUUID().equals(uuid)) {
					network.addNode(node);
					return network;
				}
			}
		}
		
		ServerPastelNetwork network = createNetwork((ServerWorld) node.getWorld(), uuid);
		network.addNode(node);
		return network;
	}
	
	/**
	 * Connect a set of Pastel Nodes - or disconnect them, if they already were connected
	 *
	 * @return true if they successfully connected / disconnected
	 */
	public boolean toggleNodeConnection(PastelNodeBlockEntity firstNode, PastelNodeBlockEntity secondNode) {
		Optional<ServerPastelNetwork> firstNetwork = firstNode.getServerNetwork();
		Optional<ServerPastelNetwork> secondNetwork = secondNode.getServerNetwork();
		
		if (secondNetwork.isPresent() && secondNetwork.equals(firstNetwork)) {
			return firstNetwork.get().removeEdge(firstNode, secondNode) || firstNetwork.get().addEdge(firstNode, secondNode);
		}
		
		ServerPastelNetwork biggerNetwork, smallerNetwork;
		
		// we have no network yet
		// => Create one
		if (firstNetwork.isEmpty() && secondNetwork.isEmpty()) {
			ServerPastelNetwork newNetwork = createNetwork((ServerWorld) firstNode.getWorld(), firstNode.getNodeId());
			newNetwork.addNode(secondNode);
			secondNode.setNetworkUUID(newNetwork.getUUID());
			newNetwork.addNodeAndConnect(firstNode, secondNode);
			return true;
		}
		
		// Both nodes have an existing network
		// => merge the smaller into the bigger one
		if (firstNetwork.isPresent() && secondNetwork.isPresent()) {
			boolean firstIsBigger = firstNetwork.get().graph.vertexSet().size() > secondNetwork.get().graph.vertexSet().size();
			if (firstIsBigger) {
				biggerNetwork = firstNode.getServerNetwork().get();
				smallerNetwork = secondNode.getServerNetwork().get();
			} else {
				smallerNetwork = firstNode.getServerNetwork().get();
				biggerNetwork = secondNode.getServerNetwork().get();
			}
			
			biggerNetwork.incorporate(smallerNetwork, firstIsBigger ? firstNode.getPos() : secondNode.getPos());
			biggerNetwork.addEdge(firstNode, secondNode);
			this.networks.remove(smallerNetwork);
			PastelNetworkEdgeSyncPayload.send(biggerNetwork, firstNode.getPos());
			return true;
		}
		
		// Only one of the nodes has an existing network
		// => add the single node to that one
		if (firstNetwork.isPresent()) {
			ServerPastelNetwork n = firstNetwork.get();
			n.addNodeAndConnect(secondNode, firstNode);
		} else {
			ServerPastelNetwork n = secondNetwork.get();
			n.addNodeAndConnect(firstNode, secondNode);
		}
		
		return true;
	}
	
	@Override
	public void removeNetwork(UUID uuid) {
		ServerPastelNetwork foundNetwork = null;
		for (ServerPastelNetwork network : this.networks) {
			if (network.uuid.equals(uuid)) {
				foundNetwork = network;
				break;
			}
		}
		if (foundNetwork != null) {
			this.networks.remove(foundNetwork);
			PastelNetworkRemovedPayload.send(foundNetwork);
		}
	}
	
	public void removeNode(PastelNodeBlockEntity node, NodeRemovalReason reason) {
		Optional<ServerPastelNetwork> optional = node.getServerNetwork();
		if (optional.isPresent()) {
			ServerPastelNetwork network = optional.get();
			network.removeNode(node, reason);
		}
	}
	
}
