package earth.terrarium.pastel.data_loaders.dimension;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.imbrifer.EnvironmentalData;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;

public class EnvDataLoader extends SimpleJsonResourceReloadListener {

    public static final EnvDataLoader INSTANCE = new EnvDataLoader();
    public static final Map<ResourceKey<Biome>, EnvironmentalData> DATA = new HashMap<>();
    public static final String ID = "environmental_data";

    private static final Codec<ResourceKey<Biome>> KEY_CODEC = ResourceKey.codec(Registries.BIOME);

    private EnvDataLoader() {
        super(new Gson(), ID);
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        DATA.clear();

        var ops = makeConditionalOps();
        files.forEach((path, parent) -> {
            var parentObject = parent.getAsJsonObject();

            var biome = KEY_CODEC.parse(ops, parentObject.get("biome"));

            if (biome.error()
                     .isPresent()) {
                error(path, biome);
                return;
            }

            var data = EnvironmentalData.CODEC.parse(ops, parentObject.getAsJsonObject("environment"));

            if (data.error()
                    .isPresent()) {
                error(path, data);
            }

            if (biome.result()
                     .isEmpty() || data.result()
                                       .isEmpty())
                return;

            DATA.put(biome.getOrThrow(), data.getOrThrow());
        });
    }

    private static void error(ResourceLocation path, DataResult<?> result) {
        PastelCommon.logError("Env Data loading error [" + path + "]" + result.error()
                                                                              .get());
    }
}
