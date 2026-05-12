package earth.terrarium.pastel.blocks.pastel_network.network;

import earth.terrarium.pastel.api.energy.InkStorage;
import earth.terrarium.pastel.api.energy.InkStorageItem;
import earth.terrarium.pastel.api.item.ItemReference;
import earth.terrarium.pastel.blocks.cinderhearth.CinderhearthBlockEntity;
import earth.terrarium.pastel.blocks.crystallarieum.CrystallarieumBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlock;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeType;
import earth.terrarium.pastel.helpers.interaction.InventoryHelper;
import earth.terrarium.pastel.items.PigmentItem;
import earth.terrarium.pastel.networking.s2c_payloads.PastelNodeStatusUpdatePayload;
import earth.terrarium.pastel.networking.s2c_payloads.PastelTransmissionPayload;
import earth.terrarium.pastel.registries.PastelItemTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;

import java.util.*;
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

    public @Nullable GraphPath<BlockPos, DefaultEdge> getPath(
        Graph<BlockPos, DefaultEdge> graph, PastelNodeBlockEntity source, PastelNodeBlockEntity destination) {
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
        transferBetween(PastelNodeType.BUFFER, PastelNodeType.GATHER, TransferMode.PULL, priority);
        transferBetween(PastelNodeType.SENDER, PastelNodeType.GATHER, TransferMode.PUSH_PULL, priority);
        transferBetween(PastelNodeType.PROVIDER, PastelNodeType.GATHER, TransferMode.PULL, priority);
        transferBetween(PastelNodeType.STORAGE, PastelNodeType.GATHER, TransferMode.PULL, priority);

        transferBetween(PastelNodeType.SENDER, PastelNodeType.BUFFER, TransferMode.PUSH_PULL, priority);
        transferBetween(PastelNodeType.PROVIDER, PastelNodeType.BUFFER, TransferMode.PULL, priority);
        transferBetween(PastelNodeType.STORAGE, PastelNodeType.BUFFER, TransferMode.PULL, priority);

        transferBetween(PastelNodeType.SENDER, PastelNodeType.STORAGE, TransferMode.PUSH, priority);
    }

    private void transferBetween(
        PastelNodeType sourceType, PastelNodeType destinationType, TransferMode transferMode,
        PastelNetwork.NodePriority priority
    ) {
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

    private void tryTransferToType(
        PastelNodeBlockEntity sourceNode, IItemHandler sourceHandler, PastelNodeType type, TransferMode transferMode) {
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

    private boolean transferBetween(
        PastelNodeBlockEntity sourceNode, IItemHandler sourceStorage, PastelNodeBlockEntity destinationNode,
        IItemHandler destinationStorage, TransferMode transferMode
    ) {
        long totalAvailableStorage = -destinationNode.getItemCountUnderway();
        //TODO: Remove Below when ink networking and-or 2.0
        if(destinationNode.getNodeType() == PastelNodeType.GATHER) {
            int inkStorageAt = -1;
            //TLDR: We do some shenanigans, and need to reference the actual BE we are targeting with the gather node.
            BlockEntity targetBE = Objects.requireNonNull(destinationNode.getLevel())
                                          .getBlockEntity(destinationNode.getBlockPos().offset(destinationNode.getBlockState().getValue(
                                              PastelNodeBlock.FACING).getOpposite().getNormal()));
            //Find if destination has ink storage, if it does, note the slot.
            for(int i =0; i< destinationStorage.getSlots();i++) {
                ItemStack inSlot = destinationStorage.getStackInSlot(i);
                if(inSlot.getItem() instanceof InkStorageItem<?>) {
                    inkStorageAt = i;
                    break;
                }
            }

            //Crystalariums are weird... they dont have normal inventories, so special case to find the ink holder here.
            if(targetBE instanceof CrystallarieumBlockEntity crystally) {
                //Note: The variable for the slot is protected, so we cant see it...
                //If this breaks later when someone changes it I claim innocence! Not my fault!
                if(crystally.getItem(1).getItem() instanceof InkStorageItem<?>) {
                    inkStorageAt = 1;
                }
            }
            //CinderHearths are... also weird...
            if(targetBE instanceof CinderhearthBlockEntity cinder) {
                //Hey atleast this one is public.
                if(cinder.getItem(CinderhearthBlockEntity.INK_PROVIDER_SLOT_ID).getItem() instanceof InkStorageItem<?>) {
                    inkStorageAt = CinderhearthBlockEntity.INK_PROVIDER_SLOT_ID;
                }
            }
            //If we dont have an ink storage in container, try to transfer items below.
            if(inkStorageAt != -1) {
                //Copy of normal-node code.
                Predicate<ItemStack> filter = sourceNode.getTransferFilterTo(destinationNode);
                var proposals = new HashMap<ItemEntry, Long>();
                for (int s = 0; s < sourceStorage.getSlots(); s++) {
                    var stack = sourceStorage.extractItem(s, DEFAULT_MAX_TRANSFER_AMOUNT, true); // Simulate extraction

                    if (stack.isEmpty()) // We don't consider empty stacks..... duh
                        continue;
                    //We dont care about anything that isnt a pigment in this section.
                    if(!stack.is(PastelItemTags.PIGMENTS)) {
                        continue;
                    }
                    if (!filter.test(stack))
                        continue;

                    var entry = new ItemEntry(stack);
                    proposals.put(entry, proposals.getOrDefault(entry, 0L) + entry.stack.getCount());
                }
                //Try to transfer ink into the container, 1 at a time, If we do, bail
                for (ItemEntry proposal : proposals.keySet()) {
                    PigmentItem pigment = (PigmentItem)proposal.stack.getItem();
                    ItemStack inkStorageItem = destinationStorage.getStackInSlot(inkStorageAt);
                    if(inkStorageItem.getItem() instanceof InkStorageItem<?> inkStorer) {
                        InkStorage store = inkStorer.getEnergyStorage(inkStorageItem);
                        int toTransfer = Math.min(proposal.stack.getCount(), 1);
                        //We dont want to give the item to the chest, we're doing the logic manually.
                        var trans = createTransmissionOnValidPath(
                            sourceNode, destinationNode, proposal.stack.copy(), 0, sourceNode.getTransferTime());
                        if (trans.isPresent() && store.accepts(pigment.getInkColor()) && store.getRoom(pigment.getInkColor()) >= 100L) {
                            store.addEnergy(pigment.getInkColor(), 100L);
                            inkStorer.setEnergyStorage(inkStorageItem, store);
                            InventoryHelper.extractFromInventory(sourceStorage, ItemReference.of(pigment), toTransfer);
                            network.addTransmission(
                                trans.get(), trans.get()
                                                  .getTransmissionDuration()
                            );
                            PastelTransmissionPayload.sendPastelTransmissionParticle(
                                this.network, trans.get()
                                                   .getTransmissionDuration(), trans.get()
                            );
                            destinationNode.markTransferred();
                            destinationNode.addItemCountUnderway(toTransfer);
                            return true;
                        }
                    }
                }
            }
        }
        //TODO: Remove above when 2.0 and-or ink Networking

        for (int d = 0; d < destinationStorage.getSlots(); d++) {
            var stack = destinationStorage.getStackInSlot(d);

            if (stack.isEmpty()) {
                totalAvailableStorage += destinationStorage.getSlotLimit(d);
            }
            else {
                totalAvailableStorage += Math.min(
                        destinationStorage.getSlotLimit(d), stack.getMaxStackSize())
                        - stack.getCount();
            }
        }

        if (totalAvailableStorage <= 0) // Fail if there isn't enough space
            return false;

        Predicate<ItemStack> filter = sourceNode.getTransferFilterTo(destinationNode);
        var proposals = new HashMap<ItemEntry, Long>();
        for (int s = 0; s < sourceStorage.getSlots(); s++) {
            var stack = sourceStorage.extractItem(s, DEFAULT_MAX_TRANSFER_AMOUNT, true); // Simulate extraction

            if (stack.isEmpty()) // We don't consider empty stacks..... duh
                continue;

            if (!filter.test(stack))
                continue;

            var entry = new ItemEntry(stack);
            proposals.put(entry, proposals.getOrDefault(entry, 0L) + entry.stack.getCount());
        }

        for (ItemEntry proposal : proposals.keySet()) {
            var proposedAmount = Math.min(
                Math.min(proposals.get(proposal), sourceNode.getMaxTransferredAmount()), totalAvailableStorage);

            if (proposedAmount == 0)
                continue;

            var proposedStack = proposal.stack.copyWithCount((int) proposedAmount);
            var simulatedAmount = proposedAmount - ItemHandlerHelper.insertItemStacked(
                                                                        destinationStorage, proposedStack, true)
                                                                    .getCount();
            simulatedAmount = Math.min(
                simulatedAmount, InventoryHelper.getStackCountInInventory(sourceStorage, proposedStack));

            if (simulatedAmount == 0) //If this is 0 then we failed to move anything in simulation
                continue;

            var trans = createTransmissionOnValidPath(
                sourceNode, destinationNode, proposedStack, simulatedAmount, sourceNode.getTransferTime());
            if (trans.isPresent()) {
                InventoryHelper.extractFromInventory(
                    sourceStorage, ItemReference.of(proposedStack),
                    (int) simulatedAmount
                ); // We only extract if a transmission was created
                network.addTransmission(
                    trans.get(), trans.get()
                                      .getTransmissionDuration()
                );
                PastelTransmissionPayload.sendPastelTransmissionParticle(
                    this.network, trans.get()
                                       .getTransmissionDuration(), trans.get()
                );

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

    public Optional<PastelTransmission> createTransmissionOnValidPath(
        PastelNodeBlockEntity source, PastelNodeBlockEntity destination, ItemStack variant, long amount,
        int vertexTime
    ) {
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
