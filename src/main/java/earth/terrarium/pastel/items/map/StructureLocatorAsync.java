package earth.terrarium.pastel.items.map;

import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;

public class StructureLocatorAsync {
	
	private final ServerLevel world;
	private final Optional<HolderSet<Structure>> structures;
	private final AtomicBoolean pinging;
	private final long delayMillis;
	
	private long nextTime = 0;
	
	public StructureLocatorAsync(ServerLevel world, ResourceLocation targetId, long delayMillis) {
		this.world = world;
		this.structures = world.registryAccess().registryOrThrow(Registries.STRUCTURE).getHolder(targetId).map(HolderSet::direct);
		this.pinging = new AtomicBoolean(false);
		this.delayMillis = delayMillis;
	}
	
	public void ping(BlockPos pos, BiConsumer<LevelAccessor, BlockPos> acceptor) {
		if (structures.isEmpty() || pinging.get())
			return;
		
		long time = System.currentTimeMillis();
		if (time < nextTime) return;
		nextTime = time + delayMillis;
		
		CompletableFuture.runAsync(() -> {
			pinging.set(true);
			ChunkGenerator generator = world.getChunkSource().getGenerator();
			Pair<BlockPos, Holder<Structure>> pair = generator.findNearestMapStructure(world, structures.get(), pos, 100, false);
			// TODO Get the centerpoint of the structure region
			if (pair != null) {
				acceptor.accept(world, pair.getFirst());
			}
			pinging.set(false);
		}, Util.backgroundExecutor());
	}
	
}
