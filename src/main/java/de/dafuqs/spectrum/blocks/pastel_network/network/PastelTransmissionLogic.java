package de.dafuqs.spectrum.blocks.pastel_network.network;

import de.dafuqs.spectrum.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import de.dafuqs.spectrum.blocks.pastel_network.nodes.PastelNodeType;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.networking.s2c_payloads.PastelNodeStatusUpdatePayload;
import de.dafuqs.spectrum.networking.s2c_payloads.PastelTransmissionPayload;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.neoforged.neoforge.items.*;
import org.jetbrains.annotations.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class PastelTransmissionLogic {
	
	private enum TransferMode {
		PUSH,
		PULL,
		PUSH_PULL
	}

	public static final int DEFAULT_MAX_TRANSFER_AMOUNT = 1;
	public static final int DEFAULT_TRANSFER_TICKS_PER_NODE = 30;
	private final ServerPastelNetwork network;
	
	private DijkstraShortestPath<BlockPos, DefaultEdge> dijkstra;
	private Map<BlockPos, Map<BlockPos, GraphPath<BlockPos, DefaultEdge>>> pathCache = new HashMap<>();
	
	
	public PastelTransmissionLogic(ServerPastelNetwork network) {
		this.network = network;
	}
	
	public void invalidateCache() {
		this.dijkstra = null;
		this.pathCache = new HashMap<>();
	}
	
	public @Nullable GraphPath<BlockPos, DefaultEdge> getPath(Graph<BlockPos, DefaultEdge> graph, PastelNodeBlockEntity source, PastelNodeBlockEntity destination) {
		if (this.dijkstra == null) {
			this.dijkstra = new DijkstraShortestPath<>(graph);
		}
		
		// cache hit?
		Map<BlockPos, GraphPath<BlockPos, DefaultEdge>> e = this.pathCache.getOrDefault(source.getBlockPos(), null);
		if (e != null) {
			if (e.containsKey(destination.getBlockPos())) {
				return e.get(destination.getBlockPos());
			}
		}
		
		// calculate and cache
		ShortestPathAlgorithm.SingleSourcePaths<BlockPos, DefaultEdge> paths = this.dijkstra.getPaths(source.getBlockPos());
		GraphPath<BlockPos, DefaultEdge> path = paths.getPath(destination.getBlockPos());
		if (this.pathCache.containsKey(source.getBlockPos())) {
			this.pathCache.get(source.getBlockPos()).put(destination.getBlockPos(), path);
		} else {
			Map<BlockPos, GraphPath<BlockPos, DefaultEdge>> newMap = new HashMap<>();
			newMap.put(destination.getBlockPos(), path);
			this.pathCache.put(source.getBlockPos(), newMap);
		}
		
		return path;
	}
	
	public void tick(PastelNetwork.NodePriority priority) {
		transferBetween(PastelNodeType.BUFFER, PastelNodeType.GATHER, TransferMode.PULL, priority);
		transferBetween(PastelNodeType.SENDER, PastelNodeType.GATHER, TransferMode.PUSH_PULL, priority);
		transferBetween(PastelNodeType.PROVIDER, PastelNodeType.GATHER, TransferMode.PULL, priority);
		transferBetween(PastelNodeType.STORAGE, PastelNodeType.GATHER, TransferMode.PULL, priority);

		transferBetween(PastelNodeType.SENDER, PastelNodeType.BUFFER, TransferMode.PUSH_PULL, priority);
		transferBetween(PastelNodeType.PROVIDER, PastelNodeType.BUFFER, TransferMode.PULL, priority);
		transferBetween(PastelNodeType.STORAGE, PastelNodeType.BUFFER, TransferMode.PULL, priority);

		transferBetween(PastelNodeType.SENDER, PastelNodeType.STORAGE, TransferMode.PUSH, priority);
	}
	
	private void transferBetween(PastelNodeType sourceType, PastelNodeType destinationType, TransferMode transferMode, PastelNetwork.NodePriority priority) {
		for (PastelNodeBlockEntity sourceNode : this.network.getLoadedNodes(sourceType, priority)) {
			if (!sourceNode.canTransfer()) {
				continue;
			}
			
			var sourceHandler = sourceNode.getConnectedHandler();
			if (sourceHandler != null) {
				tryTransferToType(sourceNode, sourceHandler, destinationType, transferMode);
			}
		}
	}
	
	private void tryTransferToType(PastelNodeBlockEntity sourceNode, IItemHandler sourceHandler, PastelNodeType type, TransferMode transferMode) {
		for (PastelNodeBlockEntity targetNode : this.network.getLoadedNodes(type, PastelNetwork.NodePriority.GENERIC)) {
			if (!targetNode.canTransfer()) {
				continue;
			}
			
			var targetHandler = targetNode.getConnectedHandler();
			if (targetHandler != null) {
				boolean success = transferBetween(sourceNode, sourceHandler, targetNode, targetHandler, transferMode);
				if (success && transferMode != TransferMode.PULL) {
					return;
				}
			}
		}
	}
	
	private boolean transferBetween(PastelNodeBlockEntity sourceNode, IItemHandler sourceStorage, PastelNodeBlockEntity destinationNode, IItemHandler destinationStorage, TransferMode transferMode) {
		long totalAvailableStorage = -destinationNode.getItemCountUnderway();
		for (int d = 0; d < destinationStorage.getSlots(); d++) {
			var stack = destinationStorage.getStackInSlot(d);

			if (stack.isEmpty()) {
				totalAvailableStorage += destinationStorage.getSlotLimit(d);
			}
			else {
				totalAvailableStorage += stack.getMaxStackSize();
			}
		}

		if (totalAvailableStorage <= 0) // Fail if there isn't enough space
			return false;

		Predicate<ItemStack> filter = sourceNode.getTransferFilterTo(destinationNode);
		var proposals = new HashMap<ItemEntry, Long>();
		for (int s = 0; s < sourceStorage.getSlots(); s++) {
			var stack = sourceStorage.getStackInSlot(s);

			if (stack.isEmpty()) // We don't consider empty stacks..... duh
				continue;

			if (!filter.test(stack))
				continue;

			var entry = new ItemEntry(stack);
			proposals.put(entry, proposals.getOrDefault(entry, 0L) + entry.stack.getCount());
		}

		for (ItemEntry proposal : proposals.keySet()) {
			var proposedAmount = Math.min(Math.min(proposals.get(proposal), sourceNode.getMaxTransferredAmount()), totalAvailableStorage);

			if (proposedAmount == 0)
				continue;

			var proposedStack = proposal.stack.copyWithCount((int) proposedAmount);
			var simulatedAmount = proposedAmount - ItemHandlerHelper.insertItemStacked(destinationStorage, proposedStack, true).getCount();
			simulatedAmount = Math.min(simulatedAmount, InventoryHelper.getStackCountInInventory(sourceStorage, proposedStack));

			if (simulatedAmount == 0) //If this is 0 then we failed to move anything in simulation
				continue;

			var trans = createTransmissionOnValidPath(sourceNode, destinationNode, proposedStack, simulatedAmount, sourceNode.getTransferTime());
			if (trans.isPresent()) {
				InventoryHelper.extractFromInventory(sourceStorage, proposedStack, (int) simulatedAmount); // We only extract if a transmission was created
				network.addTransmission(trans.get(), trans.get().getTransmissionDuration());
				PastelTransmissionPayload.sendPastelTransmissionParticle(this.network, trans.get().getTransmissionDuration(), trans.get());

				if (transferMode == TransferMode.PULL) {
					destinationNode.markTransferred();
				} else if (transferMode == TransferMode.PUSH) {
					sourceNode.markTransferred();
				} else {
					destinationNode.markTransferred();
					sourceNode.markTransferred();
				}

				destinationNode.addItemCountUnderway(simulatedAmount);
				return true;
			}
		}
		return false;
	}
	
	public Optional<PastelTransmission> createTransmissionOnValidPath(PastelNodeBlockEntity source, PastelNodeBlockEntity destination, ItemStack variant, long amount, int vertexTime) {
		GraphPath<BlockPos, DefaultEdge> graphPath = getPath(this.network.getGraph(), source, destination);
		if (graphPath != null) {
			PastelNodeStatusUpdatePayload.sendPastelNodeStatusUpdate(List.of(source), true);
			return Optional.of(new PastelTransmission(graphPath.getVertexList(), variant, amount, vertexTime));
		}
		return Optional.empty();
	}

	// This sucks
	private record ItemEntry(ItemStack stack) {

		@Override
			public boolean equals(Object obj) {
				if (!(obj instanceof ItemEntry(ItemStack entry)))
					return false;

				return ItemStack.isSameItemSameComponents(this.stack, entry);
			}

			@Override
			public int hashCode() {
				return ItemStack.hashItemAndComponents(stack);
			}
		}
}
