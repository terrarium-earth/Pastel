package de.dafuqs.spectrum.blocks.pastel_network.network;

import de.dafuqs.spectrum.blocks.pastel_network.nodes.*;
import net.minecraft.world.*;

import java.util.*;

public interface PastelNetworkManager<W extends World, N extends PastelNetwork<W>> {
	
	N createNetwork(W world, UUID uuid, int color);
	
	Optional<? extends N> getNetwork(UUID uuid);
	
	// Utility method
	default Optional<? extends N> getNetworkOrEmpty(Optional<UUID> uuid) {
		return uuid.flatMap(this::getNetwork);
	}
	
	void removeNetwork(UUID uuid);
}
