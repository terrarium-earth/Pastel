package earth.terrarium.pastel.data_loaders.dimension;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.imbrifer.ColorGrading;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.biome.Biome;

import java.util.HashMap;
import java.util.Map;

// TODO: Rename the rest of these to just be XYZLoader
public class ColorGradingLoader extends SimpleJsonResourceReloadListener {

    public static final ColorGradingLoader INSTANCE = new ColorGradingLoader();

    public static final Map<ResourceKey<Biome>, ColorGrading> DATA = new HashMap<>();

    public static final String ID = "color_grading";

    private static final Codec<ResourceKey<Biome>> KEY_CODEC = ResourceKey.codec(Registries.BIOME);

    private ColorGradingLoader() {
        super(new Gson(), ID);
    }

    @Override
    protected void apply(
        Map<ResourceLocation, JsonElement> files,
        ResourceManager resourceManager,
        ProfilerFiller profiler
    ) {
        DATA.clear();

        var ops = makeConditionalOps();
        files.forEach((path, parent) -> {
            var parentObject = parent.getAsJsonObject();

            var fallback = parentObject.get("default") != null;
            var biome = KEY_CODEC.parse(ops, parentObject.get("biome"));

            if (biome
                .error()
                .isPresent() && !fallback) {
                error(path, biome);
                return;
            }

            var data = ColorGrading.CODEC.parse(ops, parentObject.getAsJsonObject("color_grading"));

            if (data
                .error()
                .isPresent()) {
                error(path, data);
            }

            if (fallback && data
                .result()
                .isPresent()) {
                ColorGrading.DEFAULT = data.getOrThrow();
                return;
            }

            if (biome
                .result()
                .isEmpty() || data
                    .result()
                    .isEmpty())
                return;

            DATA.put(biome.getOrThrow(), data.getOrThrow());
        });
    }

    private static void error(ResourceLocation path, DataResult<?> result) {
        PastelCommon
            .logError(
                "Color Grading loading error [" + path + "]" + result
                    .error()
                    .get()
            );
    }
}
