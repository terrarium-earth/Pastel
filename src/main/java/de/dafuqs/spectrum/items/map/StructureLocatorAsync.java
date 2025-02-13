package de.dafuqs.spectrum.items.map;

import de.dafuqs.spectrum.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.world.*;
import net.minecraft.structure.*;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.chunk.placement.*;
import net.minecraft.world.gen.structure.*;

import java.util.*;
import java.util.concurrent.*;

public class StructureLocatorAsync {
	
	private final ServerWorld world;
	private final StructureLocatorAsync.Acceptor acceptor;
	private final RegistryEntry<Structure> registryEntry;
	private final int maxRadius;
	private final ThreadPoolExecutor threadPool;
	
	private ChunkPos center;
	
	public StructureLocatorAsync(ServerWorld world, StructureLocatorAsync.Acceptor acceptor, Identifier targetId, ChunkPos center, int maxRadius) {
		this.world = world;
		this.acceptor = acceptor;
		this.center = center;
		this.registryEntry = getRegistryEntry(world, targetId);
		this.maxRadius = maxRadius;
		
		BlockingQueue<Runnable> queuedChunks = new ArrayBlockingQueue<>(maxRadius * maxRadius * 2, true);
		this.threadPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 15, TimeUnit.SECONDS, queuedChunks);
		
		submit(null); // Concentric rings
		populateAll();
	}
	
	public void move(int deltaX, int deltaZ) {
		if (deltaX != 0 || deltaZ != 0) {
			center = new ChunkPos(center.x + deltaX, center.z + deltaZ);
			populateDelta(deltaX, deltaZ);
		}
	}
	
	public void cancel() {
		threadPool.shutdownNow();
	}
	
	private void populateAll() {
		for (int radius = 1; radius <= maxRadius; radius++) {
			int minX = center.x - radius;
			int maxX = center.x + radius;
			int minZ = center.z - radius;
			int maxZ = center.z + radius;
			for (int i = 0; i < radius * 2; i++) {
				// This iterates kind of like a spiral, starting with each corner and moving inward.
				submit(new ChunkPos(minX + i, maxZ)); // Top-left     -> Top-right
				submit(new ChunkPos(maxX, maxZ - i)); // Top-right    -> Bottom-right
				submit(new ChunkPos(maxX - i, minZ)); // Bottom-right -> Bottom-left
				submit(new ChunkPos(minX, minZ + i)); // Bottom-left  -> Top-left
			}
		}
	}
	
	private void populateDelta(int deltaX, int deltaZ) {
		if (deltaX > maxRadius || deltaZ > maxRadius) {
			populateAll();
			return;
		}
		
		int signX = deltaX < 0 ? -1 : 1;
		int endX = center.x + (maxRadius * signX);
		int startX = endX - deltaX;
		int minZ = center.z - maxRadius;
		int maxZ = center.z + maxRadius;
		for (int x = startX; x <= endX; x += signX)
			for (int z = minZ; z <= maxZ; z++)
				submit(new ChunkPos(x, z));
		
		int signZ = deltaZ < 0 ? -1 : 1;
		int endZ = center.z + (maxRadius * signZ);
		int startZ = endZ - deltaZ;
		int minX = center.x - maxRadius - Math.min(deltaX, 0);
		int maxX = center.x + maxRadius - Math.max(deltaX, 0);
		for (int z = startZ; z <= endZ; z += signZ)
			for (int x = minX; x <= maxX; x++)
				submit(new ChunkPos(x, z));
	}
	
	private void submit(ChunkPos pos) {
		threadPool.submit(() -> locateStructures(pos));
	}
	
	private RegistryEntry<Structure> getRegistryEntry(World world, Identifier id) {
		Registry<Structure> registry = world.getRegistryManager().getOptional(RegistryKeys.STRUCTURE).orElse(null);
		if (registry == null) return null;
		
		Structure structure = registry.get(id);
		if (structure == null) return null;
		
		return registry.getEntry(structure);
	}
	
	private void locateStructures(ChunkPos pos) {
		SpectrumCommon.logInfo(String.format("Checking chunk %s", pos));
		if (pos == null) {
			checkConcentricRingsStructures();
		} else {
			checkRandomSpreadStructures(pos);
		}
	}
	
	private void checkConcentricRingsStructures() {
		// TODO this currently only finds one concentric structure. Do we want to find more? I doubt 128 stronghold pointers is useful,
		// so maybe we could cap it at a certain amount.
		
		StructurePlacementCalculator calculator = world.getChunkManager().getStructurePlacementCalculator();
		
		double minDistance = Double.MAX_VALUE;
		ChunkPos concentricStart = null;
		
		for (StructurePlacement placement : calculator.getPlacements(registryEntry)) {
			if (placement instanceof ConcentricRingsStructurePlacement concentricRingsStructurePlacement) {
				List<ChunkPos> positions = calculator.getPlacementPositions(concentricRingsStructurePlacement);
				if (positions != null) {
					for (ChunkPos pos : positions) {
						double dx = (double) pos.x - (double) center.x;
						double dz = (double) pos.z - (double) center.z;
						double distance = dx * dx + dz * dz;
						if (distance < minDistance) {
							minDistance = distance;
							concentricStart = pos;
						}
					}
				}
			}
		}
		
		if (concentricStart != null) {
			acceptTarget(concentricStart);
		}
	}
	
	private void checkRandomSpreadStructures(ChunkPos pos) {
		StructureAccessor accessor = world.getStructureAccessor();
		Structure structure = registryEntry.value();
		
		for (StructureSet set : world.getRegistryManager().get(RegistryKeys.STRUCTURE_SET)) {
			if (set.placement().getType() instanceof RandomSpreadStructurePlacement) {
				StructurePresence presence = accessor.getStructurePresence(pos, structure, set.placement(), false);
				if (presence == StructurePresence.START_NOT_PRESENT)
					continue;
				
				Chunk chunk = world.getChunk(pos.x, pos.z, ChunkStatus.STRUCTURE_STARTS);
				StructureStart start = accessor.getStructureStart(ChunkSectionPos.from(chunk), structure, chunk);
				if (start != null)
					acceptTarget(pos);
			}
		}
	}
	
	private synchronized void acceptTarget(ChunkPos target) {
		SpectrumCommon.logInfo(String.format("Accepting chunk %s", target));
		acceptor.accept(world, target);
	}
	
	public interface Acceptor {
		void accept(WorldAccess world, ChunkPos target);
	}
	
}
