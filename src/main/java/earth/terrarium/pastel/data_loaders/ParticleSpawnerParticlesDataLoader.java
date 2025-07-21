package earth.terrarium.pastel.data_loaders;

import com.cmdpro.databank.DatabankUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import earth.terrarium.pastel.PastelCommon;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParticleSpawnerParticlesDataLoader extends SimpleJsonResourceReloadListener {
	
	public static final String ID = "particle_spawner_particle";
	public static final ParticleSpawnerParticlesDataLoader INSTANCE = new ParticleSpawnerParticlesDataLoader();
	
	/**
	 * Defines an entry that appears in the particle spawners gui to be selected as particle
	 * Theoretically the particle spawner can spawn all kinds of particle (my modifying its nbt)
	 * But we are limiting us to a few reasonable ones there
	 *
	 * @param particleType      The particle type to dynamically fetch textures from
	 * @param textureIdentifier The texture shown in its gui entry
	 * @param supportsColoring  Weather the Particle Spawner enables CMY coloring for this particle (should be true, if grayscale)
	 * @param unlockIdentifier  The advancement identifier required to being able to select this entry
	 */
	public record ParticleSpawnerEntry(ParticleType<?> particleType, ResourceLocation textureIdentifier, boolean supportsColoring, @Nullable ResourceLocation unlockIdentifier) {
	}
	
	protected static final List<ParticleSpawnerEntry> PARTICLES = new ArrayList<>();
	
	private ParticleSpawnerParticlesDataLoader() {
		super(new Gson(), ID);
	}
	
	@Override
	protected void apply(Map<ResourceLocation, JsonElement> prepared, ResourceManager manager, ProfilerFiller profiler) {
		PARTICLES.clear();
		prepared.forEach((identifier, jsonElement) -> {
			JsonObject jsonObject = jsonElement.getAsJsonObject();
			
			String particleTypeString = jsonObject.get("particle_type").getAsString();
			ParticleType<?> particleType = BuiltInRegistries.PARTICLE_TYPE.get(ResourceLocation.tryParse(particleTypeString));
			ResourceLocation guiTexture = ResourceLocation.tryParse(jsonObject.get("gui_texture").getAsString());
			@Nullable ResourceLocation unlockIdentifier = jsonObject.has("unlock_identifier") ? ResourceLocation.tryParse(jsonObject.get("unlock_identifier").getAsString()) : null;
			boolean supportsColoring = GsonHelper.getAsBoolean(jsonObject, "supports_coloring", false);
			
			if (particleType == null) {
				PastelCommon.logError("Particle Spawner Particle '" + particleTypeString + "' not found. Will be ignored.");
				return;
			}
			
			PARTICLES.add(new ParticleSpawnerEntry(particleType, guiTexture, supportsColoring, unlockIdentifier));
		});
	}
	
	public static List<ParticleSpawnerEntry> getAllUnlocked(Player player) {
		List<ParticleSpawnerEntry> list = new ArrayList<>();
		for (ParticleSpawnerParticlesDataLoader.ParticleSpawnerEntry entry : PARTICLES) {
			if (DatabankUtils.hasAdvancement(player, entry.unlockIdentifier())) {
				list.add(entry);
			}
		}
		return list;
	}
	
}