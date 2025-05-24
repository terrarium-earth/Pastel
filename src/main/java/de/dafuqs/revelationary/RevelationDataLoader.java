package de.dafuqs.revelationary;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

public class RevelationDataLoader extends SimpleJsonResourceReloadListener {
	public static final RevelationDataLoader INSTANCE = new RevelationDataLoader();
	
	private RevelationDataLoader() {
		super(new Gson(), "revelations");
	}
	
	@Override
	protected void apply(Map<ResourceLocation, JsonElement> prepared, ResourceManager manager, ProfilerFiller profiler) {
		prepared.forEach((identifier, jsonElement) -> RevelationRegistry.registerFromJson(jsonElement.getAsJsonObject()));
		RevelationRegistry.deepTrim();
	}
}