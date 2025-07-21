package earth.terrarium.pastel.blocks.pastel_network.network;

import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.UUID;

public interface PastelNetworkManager<W extends Level, N extends PastelNetwork<W>> {

    N createNetwork(W world, UUID uuid, int color);

    Optional<? extends N> getNetwork(UUID uuid);

    // Utility method
    default Optional<? extends N> getNetworkOrEmpty(Optional<UUID> uuid) {
        return uuid.flatMap(this::getNetwork);
    }

    void removeNetwork(UUID uuid);
}
