package earth.terrarium.pastel.blocks.pastel_network.ink.network;

import earth.terrarium.pastel.blocks.pastel_network.ink.nodes.PastelInkNodeBlockEntity;
import earth.terrarium.pastel.blocks.pastel_network.network.NodeRemovalReason;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelNetwork;
import earth.terrarium.pastel.blocks.pastel_network.network.PastelNetworkManager;
import earth.terrarium.pastel.helpers.data.CodecHelper;
import earth.terrarium.pastel.networking.s2c_payloads.PastelNetworkRemovedPayload;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import org.jetbrains.annotations.NotNull;

import java.util.*;

// Persisted together with the overworld. Resetting the overworld will also reset all networks
public class ServerPastelInkNetworkManager extends SavedData
    implements PastelNetworkManager<ServerLevel, ServerPastelInkNetwork> {

    private static final String PERSISTENT_STATE_ID = "pastel_pastel_ink_network_manager";

    private final List<ServerPastelInkNetwork> networks = new ArrayList<>();

    public ServerPastelInkNetworkManager() {
        super();
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public static ServerPastelInkNetworkManager get(ServerLevel world) {
        // TODO: We need to spoof a datafixer type, null will prevent data from being read
        Factory<ServerPastelInkNetworkManager> type = new Factory<>(
            ServerPastelInkNetworkManager::new, (nbtCompound, lookup) -> ServerPastelInkNetworkManager.fromNbt(nbtCompound),
            null
        );
        return world.getDataStorage()
                    .computeIfAbsent(type, PERSISTENT_STATE_ID);
    }

    public ServerPastelInkNetwork createNetwork(ServerLevel world, PastelInkNodeBlockEntity initialNode) {
        ServerPastelInkNetwork network = new ServerPastelInkNetwork(world, initialNode);
        this.networks.add(network);
        initialNode.setNetworkUUID(network.getUUID());
        return network;
    }

    // TODO: detach connection logic from pastel node block entities
    public void connectNodes(PastelInkNodeBlockEntity child, PastelInkNodeBlockEntity parent) {
        var parentNetwork = parent.getServerNetwork();
        var childNetwork = child.getServerNetwork();

        if (childNetwork.isEmpty() && parentNetwork.isEmpty()) {
            parentNetwork = Optional.of(createNetwork((ServerLevel) parent.getLevel(), parent));

        }

        if (childNetwork.isEmpty()) {
            addAndSync(child, parent);
            return;
        } else if (parentNetwork.isEmpty()) {
            addAndSync(parent, child);
            return;
        } else if (childNetwork.get() != parentNetwork.get()) {
            if (parentNetwork.get()
                             .size() > childNetwork.get()
                                                   .size()) {
                parentNetwork.get()
                             .incorporate(childNetwork.get(), child, parent);
            } else {
                childNetwork.get()
                            .incorporate(parentNetwork.get(), child, parent);
            }
        }

        // You uh, should not be getting here if both networks are equal.
        // Handle that in the impression please and thanks.
        throw new IllegalStateException("Tried to merge a Pastel Network with itself");
    }

    private static void addAndSync(PastelInkNodeBlockEntity newNode, PastelInkNodeBlockEntity reference) {
        assert reference.getServerNetwork()
                        .isPresent();
        var parentNetwork = reference.getServerNetwork()
                                     .get();
        parentNetwork.addNodeAndConnect(newNode, reference);
        parentNetwork.markDirty(reference.getBlockPos());
    }

    @Override
    public ServerPastelInkNetwork createNetwork(ServerLevel world, UUID uuid, int color) {
        ServerPastelInkNetwork network = new ServerPastelInkNetwork(world, uuid, color);
        this.networks.add(network);
        return network;
    }

    @Override
    public Optional<ServerPastelInkNetwork> getNetwork(UUID uuid) {
        return networks.stream()
                       .filter(n -> n.uuid.equals(uuid))
                       .findFirst();
    }


    @Override
    public CompoundTag save(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        ListTag networkList = new ListTag();
        for (ServerPastelInkNetwork network : this.networks) {
            var opt = ServerPastelInkNetwork.CODEC.encodeStart(NbtOps.INSTANCE, network)
                                                  .result();
            if (opt.isPresent()) {
                var wrapper = new CompoundTag();
                wrapper.put("network", opt.get());
                wrapper.put("graph", network.graphToNbt());
                wrapper.put("scheduler", transgender(network.getTransmissions()));
                // Trans missions?... do... do they really?
                networkList.add(wrapper);
            }
        }
        nbt.put("Networks", networkList);
        return nbt;
    }

    public static ServerPastelInkNetworkManager fromNbt(CompoundTag nbt) {
        ServerPastelInkNetworkManager manager = new ServerPastelInkNetworkManager();
        for (Tag element : nbt.getList("Networks", Tag.TAG_COMPOUND)) {
            var comp = (CompoundTag) element;
            var netNbt = comp.get("network");
            var graphNbt = comp.getCompound("graph");
            var schedulerNbt = comp.getCompound("scheduler");

            Optional<ServerPastelInkNetwork> network = CodecHelper.fromNbt(ServerPastelInkNetwork.CODEC, netNbt);
            if (network.isPresent()) {
                network.get()
                       .setGraph(PastelNetwork.graphFromNbt(graphNbt));
                // I truly, really did try to make the transmission codec work. And I failed ~ Azzyypaaras
                network.get()
                       .getTransmissions()
                       .putAll(transDecode(schedulerNbt, network.get()));
                manager.networks.add(network.get());
            }
        }
        return manager;
    }

    private static @NotNull HashMap<PastelInkTransmission, Integer> transDecode(
        CompoundTag schedulerNbt, ServerPastelInkNetwork network) {
        var transmissions = schedulerNbt.getList("transmissions", Tag.TAG_COMPOUND);
        var timers = schedulerNbt.getIntArray("timers");
        var map = new HashMap<PastelInkTransmission, Integer>();

        for (int i = 0; i < transmissions.size(); i++) {
            var result = PastelInkTransmission.CODEC.decode(NbtOps.INSTANCE, transmissions.get(i))
                                                 .result();

            if (result.isEmpty())
                continue;

            var trans = result.get()
                              .getFirst();
            trans.setNetwork(network);
            map.put(
                trans,
                timers[i]
            );
        }
        return map;
    }

    public void tick() {
        // using a for here instead of foreach
        // to prevent ConcurrentModificationExceptions
        //noinspection ForLoopReplaceableByForEach
        for (int i = 0; i < this.networks.size(); i++) {
            this.networks.get(i)
                         .tick();
        }
    }

    private static CompoundTag transgender(Map<PastelInkTransmission, Integer> trans) {
        var transNbt = new CompoundTag();
        var transmissions = new ListTag();
        var timers = new int[trans.size()];
        for (Map.Entry<PastelInkTransmission, Integer> transmissionEntry : trans.entrySet()) {
            var result = PastelInkTransmission.CODEC.encodeStart(NbtOps.INSTANCE, transmissionEntry.getKey())
                                                 .result();
            if (result.isPresent()) {
                transmissions.add(result.get());
                timers[transmissions.size() - 1] = transmissionEntry.getValue();
            }
        }

        transNbt.put("transmissions", transmissions);
        transNbt.putIntArray("timers", timers);
        return transNbt;
    }

    @Override
    public void removeNetwork(UUID uuid) {
        ServerPastelInkNetwork foundNetwork = null;
        for (ServerPastelInkNetwork network : this.networks) {
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

    public void removeNode(PastelInkNodeBlockEntity node, NodeRemovalReason reason) {
        Optional<ServerPastelInkNetwork> optional = node.getServerNetwork();
        if (optional.isPresent()) {
            ServerPastelInkNetwork network = optional.get();

            if (network.size() == 1) {
                this.removeNetwork(network.getUUID());
            } else {
                network.removeNode(node, reason);
            }
        }
    }
}
