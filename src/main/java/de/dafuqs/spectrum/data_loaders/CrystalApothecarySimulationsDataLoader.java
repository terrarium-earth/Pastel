package de.dafuqs.spectrum.data_loaders;

import com.google.gson.*;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import de.dafuqs.spectrum.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.resource.*;
import net.minecraft.util.*;
import net.minecraft.util.profiler.*;

import java.util.*;

public class CrystalApothecarySimulationsDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final String ID = "crystal_apothecary_simulations";
	public static final CrystalApothecarySimulationsDataLoader INSTANCE = new CrystalApothecarySimulationsDataLoader();
	
	public static final HashMap<Block, SimulatedBlockGrowthEntry> COMPENSATIONS = new HashMap<>();
	
	public record SimulatedBlockGrowthEntry(Collection<Block> validNeighbors, int ticksForCompensationLootPerValidNeighbor, ItemStack compensatedStack) {
		
		public static final Codec<SimulatedBlockGrowthEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
				Registries.BLOCK.getCodec().listOf().xmap(list -> (Collection<Block>) list, set -> set.stream().toList()).fieldOf("valid_neighbor_blocks").forGetter(c -> c.validNeighbors),
				Codec.INT.optionalFieldOf("ticks_for_compensation_loot_per_valid_neighbor", 10000).forGetter(c -> c.ticksForCompensationLootPerValidNeighbor),
				ItemStack.CODEC.fieldOf("compensated_stack").forGetter(c -> c.compensatedStack)
		).apply(i, SimulatedBlockGrowthEntry::new));
		
	}
	
	private CrystalApothecarySimulationsDataLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		COMPENSATIONS.clear();
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject object = jsonElement.getAsJsonObject();
			
			DataResult<Block> buddingBlock = Registries.BLOCK.getCodec().decode(JsonOps.INSTANCE, object.get("budding_block")).map(Pair::getFirst);
			if (buddingBlock.error().isPresent() || buddingBlock.result().isEmpty()) {
				SpectrumCommon.logError("Crystal Apothecary Simulation error: " + buddingBlock.error().get() + ". Ignoring that one.");
				return;
			}
			
			DataResult<SimulatedBlockGrowthEntry> entry = SimulatedBlockGrowthEntry.CODEC.decode(JsonOps.INSTANCE, jsonElement).map(Pair::getFirst);
			if (entry.error().isPresent() || entry.result().isEmpty()) {
				SpectrumCommon.logError("Crystal Apothecary Simulation error: " + entry.error().get() + ". Ignoring that one.");
				return;
			}
			
			COMPENSATIONS.put(buddingBlock.result().get(), entry.result().get());
		});
	}
	
	@Override
	public Identifier getFabricId() {
		return SpectrumCommon.locate(ID);
	}
	
}
