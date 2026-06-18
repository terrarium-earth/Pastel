package earth.terrarium.pastel.blocks.pastel_network.network;

import earth.terrarium.pastel.blocks.pastel_network.nodes.PastelNodeBlockEntity;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

// Persisted together with the overworld. Resetting the overworld will also reset all networks
public class ServerPastelNetworkManager extends SavedData
    implements
    PastelNetworkManager<ServerLevel, ServerPastelNetwork> {

    private static final String PERSISTENT_STATE_ID = "pastel_pastel_network_manager";

    private final List<ServerPastelNetwork> networks = new ArrayList<>();

    public ServerPastelNetworkManager() {
        super();
    }

    @Override
    public boolean isDirty() {
        return true;
    }

    public static ServerPastelNetworkManager get(ServerLevel world) {
        // TODO: We need to spoof a datafixer type, null will prevent data from being read
        SavedData.Factory<ServerPastelNetworkManager> type = new SavedData.Factory<>(
            ServerPastelNetworkManager::new,
            (nbtCompound, lookup) -> ServerPastelNetworkManager.fromNbt(nbtCompound),
            null
        );
        return world
            .getDataStorage()
            .computeIfAbsent(type, PERSISTENT_STATE_ID);
    }

    public ServerPastelNetwork createNetwork(ServerLevel world, PastelNodeBlockEntity initialNode) {
        ServerPastelNetwork network = new ServerPastelNetwork(world, initialNode);
        this.networks.add(network);
        initialNode.setNetworkUUID(network.getUUID());
        return network;
    }

    // TODO: detach connection logic from pastel node block entities
    public void connectNodes(PastelNodeBlockEntity child, PastelNodeBlockEntity parent) {
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
            if (parentNetwork
                .get()
                .size() > childNetwork
                    .get()
                    .size()) {
                parentNetwork
                    .get()
                    .incorporate(childNetwork.get(), child, parent);
            } else {
                childNetwork
                    .get()
                    .incorporate(parentNetwork.get(), child, parent);
            }
        }

        // You uh, should not be getting here if both networks are equal.
        // Handle that in the impression please and thanks.
        throw new IllegalStateException("Tried to merge a Pastel Network with itself");
    }

    private static void addAndSync(PastelNodeBlockEntity newNode, PastelNodeBlockEntity reference) {
        assert reference
            .getServerNetwork()
            .isPresent();
        var parentNetwork = reference
            .getServerNetwork()
            .get();
        parentNetwork.addNodeAndConnect(newNode, reference);
        parentNetwork.markDirty(reference.getBlockPos());
    }

    @Override
    public ServerPastelNetwork createNetwork(ServerLevel world, UUID uuid, int color) {
        ServerPastelNetwork network = new ServerPastelNetwork(world, uuid, color);
        this.networks.add(network);
        return network;
    }

    @Override
    public Optional<ServerPastelNetwork> getNetwork(UUID uuid) {
        return networks
            .stream()
            .filter(n -> n.uuid.equals(uuid))
            .findFirst();
    }

    @Override
    public CompoundTag save(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        ListTag networkList = new ListTag();
        for (
            ServerPastelNetwork network : this.networks
        ) {
            var opt = ServerPastelNetwork.CODEC
                .encodeStart(NbtOps.INSTANCE, network)
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

    public static ServerPastelNetworkManager fromNbt(CompoundTag nbt) {
        ServerPastelNetworkManager manager = new ServerPastelNetworkManager();
        for (
            Tag element : nbt.getList("Networks", Tag.TAG_COMPOUND)
        ) {
            var comp = (CompoundTag) element;
            var netNbt = comp.get("network");
            var graphNbt = comp.getCompound("graph");
            var schedulerNbt = comp.getCompound("scheduler");

            Optional<ServerPastelNetwork> network = CodecHelper.fromNbt(ServerPastelNetwork.CODEC, netNbt);
            if (network.isPresent()) {
                network
                    .get()
                    .setGraph(PastelNetwork.graphFromNbt(graphNbt));
                // I truly, really did try to make the transmission codec work. And I failed ~ Azzyypaaras
                network
                    .get()
                    .getTransmissions()
                    .putAll(transDecode(schedulerNbt, network.get()));
                manager.networks.add(network.get());
            }
        }
        return manager;
    }

    private static @NotNull HashMap<PastelTransmission, Integer> transDecode(
        CompoundTag schedulerNbt,
        ServerPastelNetwork network
    ) {
        var transmissions = schedulerNbt.getList("transmissions", Tag.TAG_COMPOUND);
        var timers = schedulerNbt.getIntArray("timers");
        var map = new HashMap<PastelTransmission, Integer>();

        for (
            int i = 0;
            i < transmissions.size();
            i++
        ) {
            var result = PastelTransmission.CODEC
                .decode(NbtOps.INSTANCE, transmissions.get(i))
                .result();

            if (result.isEmpty())
                continue;

            var trans = result
                .get()
                .getFirst();
            trans.setNetwork(network);
            map
                .put(
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
        for (
            int i = 0;
            i < this.networks.size();
            i++
        ) {
            this.networks
                .get(i)
                .tick();
        }
    }

    private static CompoundTag transgender(Map<PastelTransmission, Integer> trans) {
        var transNbt = new CompoundTag();
        var transmissions = new ListTag();
        var timers = new int[trans.size()];
        for (
            Map.Entry<PastelTransmission, Integer> transmissionEntry : trans.entrySet()
        ) {
            var result = PastelTransmission.CODEC
                .encodeStart(NbtOps.INSTANCE, transmissionEntry.getKey())
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
        ServerPastelNetwork foundNetwork = null;
        for (
            ServerPastelNetwork network : this.networks
        ) {
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

            if (network.size() == 1) {
                this.removeNetwork(network.getUUID());
            } else {
                network.removeNode(node, reason);
            }
        }
    }
}
