package earth.terrarium.pastel.logistics.network;

import earth.terrarium.pastel.blocks.pastel_nodes.PastelNodeBlockEntity;
import earth.terrarium.pastel.helpers.data.CodecHelper;
import earth.terrarium.pastel.logistics.api.NodeRemovalReason;
import earth.terrarium.pastel.logistics.api.PastelNetworkManager;
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
    implements PastelNetworkManager<ServerLevel, ServerPastelNetwork> {

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
            ServerPastelNetworkManager::new, (nbtCompound, lookup) -> ServerPastelNetworkManager.fromNbt(nbtCompound),
            null
        );
        return world.getDataStorage()
                    .computeIfAbsent(type, PERSISTENT_STATE_ID);
    }


    @Override
    public CompoundTag save(CompoundTag nbt, HolderLookup.Provider registryLookup) {
        ListTag networkList = new ListTag();
        for (ServerPastelNetwork network : this.networks) {
            var opt = ServerPastelNetwork.CODEC.encodeStart(NbtOps.INSTANCE, network)
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
        for (Tag element : nbt.getList("Networks", Tag.TAG_COMPOUND)) {
            var comp = (CompoundTag) element;
            var netNbt = comp.get("network");
            var graphNbt = comp.getCompound("graph");
            var schedulerNbt = comp.getCompound("scheduler");

            Optional<ServerPastelNetwork> network = CodecHelper.fromNbt(ServerPastelNetwork.CODEC, netNbt);
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

    private static @NotNull HashMap<Transmission, Integer> transDecode(
        CompoundTag schedulerNbt, ServerPastelNetwork network) {
        var transmissions = schedulerNbt.getList("transmissions", Tag.TAG_COMPOUND);
        var timers = schedulerNbt.getIntArray("timers");
        var map = new HashMap<Transmission, Integer>();

        for (int i = 0; i < transmissions.size(); i++) {
            var result = Transmission.CODEC.decode(NbtOps.INSTANCE, transmissions.get(i))
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

    private static CompoundTag transgender(Map<Transmission, Integer> trans) {
        var transNbt = new CompoundTag();
        var transmissions = new ListTag();
        var timers = new int[trans.size()];
        for (Map.Entry<Transmission, Integer> transmissionEntry : trans.entrySet()) {
            var result = Transmission.CODEC.encodeStart(NbtOps.INSTANCE, transmissionEntry.getKey())
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
}
