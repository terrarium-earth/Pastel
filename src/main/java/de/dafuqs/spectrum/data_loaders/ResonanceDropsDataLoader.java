package de.dafuqs.spectrum.data_loaders;

import com.google.gson.*;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.api.interaction.*;
import net.fabricmc.fabric.api.resource.*;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.item.*;
import net.minecraft.registry.*;
import net.minecraft.resource.*;
import net.minecraft.util.*;
import net.minecraft.util.profiler.*;

import java.util.*;

public class ResonanceDropsDataLoader extends JsonDataLoader implements IdentifiableResourceReloadListener {
	
	public static final Identifier ID = SpectrumCommon.locate("resonance_drops");
	
	protected static final List<ResonanceDropProcessor> RESONANCE_DROPS = new ArrayList<>();
	
	private final RegistryWrapper.WrapperLookup registryLookup;
	public static boolean preventNextXPDrop;
	
	public ResonanceDropsDataLoader(RegistryWrapper.WrapperLookup registryLookup) {
		super(new Gson(), ID.getPath());
		this.registryLookup = registryLookup;
	}
	
	@Override
	protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
		RESONANCE_DROPS.clear();
		
		RegistryOps<JsonElement> ops = registryLookup.getOps(JsonOps.INSTANCE);
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject json = jsonElement.getAsJsonObject();
			
			Identifier processorType = Identifier.tryParse(JsonHelper.getString(json, "type"));
			ResonanceDropProcessor.Serializer serializer = ResonanceDropProcessors.get(processorType);
			if (serializer == null) {
				SpectrumCommon.logError("Unknown ResonanceDropProcessor " + processorType + " in file " + identifier);
				return;
			}
			try {
				RESONANCE_DROPS.add(serializer.fromJson(ops, json));
			} catch (Exception e) {
				SpectrumCommon.logError("Error parsing ResonanceDropProcessor " + identifier + ": " + e.getLocalizedMessage());
			}
		});
	}
	
	@Override
	public Identifier getFabricId() {
		return ID;
	}
	
	public static void applyResonance(BlockState minedState, BlockEntity blockEntity, List<ItemStack> droppedStacks) {
		for (ResonanceDropProcessor entry : RESONANCE_DROPS) {
		entry.process(minedState, blockEntity, droppedStacks);
		}
	}
	
}
