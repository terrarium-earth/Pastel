package de.dafuqs.spectrum.items.map;

import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.world.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.structure.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

public class StructureLocatorAsync {
	
	private final ServerWorld world;
	private final Optional<RegistryEntryList<Structure>> structures;
	private final AtomicBoolean pinging;
	private final long delayMillis;
	
	private long nextTime = 0;
	
	public StructureLocatorAsync(ServerWorld world, Identifier targetId, long delayMillis) {
		this.world = world;
		this.structures = world.getRegistryManager().get(RegistryKeys.STRUCTURE).getEntry(targetId).map(RegistryEntryList::of);
		this.pinging = new AtomicBoolean(false);
		this.delayMillis = delayMillis;
	}
	
	public void ping(BlockPos pos, BiConsumer<WorldAccess, ChunkPos> acceptor) {
		if (structures.isEmpty() || pinging.get())
			return;
		
		long time = System.currentTimeMillis();
		if (time < nextTime) return;
		nextTime = time + delayMillis;
		
		CompletableFuture.runAsync(() -> {
			pinging.set(true);
			ChunkGenerator generator = world.getChunkManager().getChunkGenerator();
			Pair<BlockPos, RegistryEntry<Structure>> pair = generator.locateStructure(world, structures.get(), pos, 100, false);
			if (pair != null) {
				acceptor.accept(world, new ChunkPos(pair.getFirst()));
			}
			pinging.set(false);
		}, Util.getMainWorkerExecutor());
	}
	
}
