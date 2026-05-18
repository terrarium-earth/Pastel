package earth.terrarium.pastel.blocks.pastel_network.ink.network;

import earth.terrarium.pastel.api.energy.InkStorageBlockEntity;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.api.energy.color.InkColors;
import earth.terrarium.pastel.api.item.ItemReference;
import earth.terrarium.pastel.blocks.pastel_network.ink.nodes.PastelInkNodeBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelNetwork;
import earth.terrarium.pastel.blocks.pastel_network.ink.nodes.PastelInkNodeType;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.networking.s2c_payloads.PastelNodeStatusUpdatePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PastelTransmissionPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
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
import java.util.concurrent.atomic.AtomicReference;

public class PastelInkTransmissionLogic {

    private enum TransferMode {
        PUSH,
        PULL,
        PUSH_PULL
    }

    public static final int DEFAULT_MAX_TRANSFER_AMOUNT = 100;
    private final ServerPastelInkNetwork network;

    private DijkstraShortestPath<BlockPos, DefaultEdge> dijkstra;
    private Map<BlockPos, Map<BlockPos, GraphPath<BlockPos, DefaultEdge>>> pathCache = new HashMap<>();


    public PastelInkTransmissionLogic(ServerPastelInkNetwork network) {
        this.network = network;
    }

    public void invalidateCache() {
        this.dijkstra = null;
        this.pathCache = new HashMap<>();
    }

    public @Nullable GraphPath<BlockPos, DefaultEdge> getPath(
        Graph<BlockPos, DefaultEdge> graph, PastelInkNodeBlockEntity source, PastelInkNodeBlockEntity destination) {
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
        ShortestPathAlgorithm.SingleSourcePaths<BlockPos, DefaultEdge> paths = this.dijkstra.getPaths(
            source.getBlockPos());
        GraphPath<BlockPos, DefaultEdge> path = paths.getPath(destination.getBlockPos());
        if (this.pathCache.containsKey(source.getBlockPos())) {
            this.pathCache.get(source.getBlockPos())
                          .put(destination.getBlockPos(), path);
        } else {
            Map<BlockPos, GraphPath<BlockPos, DefaultEdge>> newMap = new HashMap<>();
            newMap.put(destination.getBlockPos(), path);
            this.pathCache.put(source.getBlockPos(), newMap);
        }

        return path;
    }

    public void tick(PastelNetwork.NodePriority priority) {
        transferBetween(PastelInkNodeType.PROVIDER, PastelInkNodeType.GATHER, TransferMode.PULL, priority);
    }

    private void transferBetween(
        PastelInkNodeType sourceType, PastelInkNodeType destinationType, TransferMode transferMode,
        PastelNetwork.NodePriority priority
    ) {
        for (PastelInkNodeBlockEntity sourceNode : this.network.getLoadedNodes(sourceType, priority)) {
            if (!sourceNode.canTransfer()) {
                continue;
            }

            var sourceHandler = sourceNode.getConnectedHandler();
            if (sourceHandler != null) {
                tryTransferToType(sourceNode, sourceHandler, destinationType, transferMode);
            }
        }
    }

    private void tryTransferToType(
        PastelInkNodeBlockEntity sourceNode, InkStorageBlockEntity sourceHandler, PastelInkNodeType type, TransferMode transferMode) {
        for (PastelInkNodeBlockEntity targetNode : this.network.getLoadedNodes(type, PastelNetwork.NodePriority.GENERIC)) {
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

    private boolean transferBetween(
        PastelInkNodeBlockEntity sourceNode, InkStorageBlockEntity sourceStorage, PastelInkNodeBlockEntity destinationNode,
        InkStorageBlockEntity destinationStorage, TransferMode transferMode
    ) {

        AtomicReference<InkColor> targetColor = new AtomicReference<InkColor>(null);
        for (int i = 0; i < InkColors.BUILTIN_COLORS.size(); i++) {
            if (targetColor.get() == null) {
                InkColor col = InkColors.BUILTIN_COLORS.get(i);
                long inkAmt = sourceStorage.getEnergyStorage().getEnergy(col);
                if (inkAmt > 0L &&
                    destinationStorage.getEnergyStorage().accepts(col) &&
                        destinationStorage.getEnergyStorage().getRoom(col) >= inkAmt) {
                    targetColor.set(col);
                }
            }
        }

        if (targetColor.get() == null) {
            return false;
        }
        InkColor movingColor = targetColor.get();
        long targetAmnt = Math.min(Math.min(sourceStorage.getEnergyStorage()
                                                         .getEnergy(movingColor), DEFAULT_MAX_TRANSFER_AMOUNT),
                                   destinationStorage.getEnergyStorage()
                                                     .getRoom(movingColor));

        var trans = createTransmissionOnValidPath(
            sourceNode, destinationNode, movingColor, targetAmnt, sourceNode.getTransferTime());
        if (trans.isPresent()) {
            sourceStorage.getEnergyStorage()
                         .drainEnergy(movingColor, targetAmnt);
            sourceStorage.setInkDirty();
            network.addTransmission(
                trans.get(), trans.get()
                                  .getTransmissionDuration()
            );
            PastelTransmissionPayload.sendPastelTransmissionParticle(
                this.network, trans.get()
                                   .getTransmissionDuration(), trans.get()
            );
            destinationNode.markTransferred();
            destinationNode.addItemCountUnderway(targetAmnt);
            return true;
        }
        return false;
    }

    public Optional<PastelInkTransmission> createTransmissionOnValidPath(
        PastelInkNodeBlockEntity source, PastelInkNodeBlockEntity destination, InkColor variant, long amount,
        int vertexTime
    ) {
        GraphPath<BlockPos, DefaultEdge> graphPath = getPath(this.network.getGraph(), source, destination);
        if (graphPath != null) {
            PastelNodeStatusUpdatePayload.sendPastelInkNodeStatusUpdate(List.of(source), true);
            return Optional.of(new PastelInkTransmission(graphPath.getVertexList(), variant, amount, vertexTime));
        }
        return Optional.empty();
    }
}
