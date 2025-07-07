package earth.terrarium.pastel.helpers.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.energy.color.InkColor;
import earth.terrarium.pastel.registries.PastelRegistries;
import net.minecraft.FileUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.LevelResource;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

/**
 * Do not let Ash see this she will actually fucking kill me
 */
public class WeGotDatagenAtHome {

    private static boolean actioned;

    public static void action(ServerLevel level) {
        if (actioned)
            return;

        //genColored(level);
        actioned = true;
    }

    public static void genColored(ServerLevel level) {
        var path = root(level);
        var template = path.resolve("template");
        path = path.resolve("pastel");
        path = path.resolve("trees");

        var treeTemplate = FileUtil.createPathToResource(template, "colored_tree", ".json");

        try {
            FileUtil.createDirectoriesSafe(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        InputStreamReader input = tryGetInput(treeTemplate);
        if (input==null)
            return;

        var json = GsonHelper.parse(input);
        try {
            for (InkColor color : PastelRegistries.INK_COLOR) {
                var treeOutput = FileUtil.createPathToResource(path, color.getLootName(), ".json");
                apply(json.deepCopy(), treeOutput, "MEOW", color.getLootName());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static @Nullable InputStreamReader tryGetInput(Path treeTemplate) {
        InputStreamReader input;
        try {
            input = new InputStreamReader(new FileInputStream(treeTemplate.toFile()));
        } catch (FileNotFoundException e) {
            PastelCommon.logError(e.getMessage());
            return null;
        }
        return input;
    }

    private static void apply(JsonObject json, Path output, @Nullable String target, @Nullable String replacement) throws IOException {
        if (target != null && replacement != null)
            recursiveReplace(json, target, replacement);

        var out = new JsonWriter(new OutputStreamWriter(new FileOutputStream(output.toFile()), StandardCharsets.UTF_8));
        out.setIndent("  ");
        GsonHelper.writeValue(out, json, null);
        out.close();
    }

    private static void apply(JsonObject json, Path output) throws IOException {
        apply(json, output, null, null);
    }

    private static @NotNull Path root(ServerLevel level) {
        return level.getServer().storageSource.getLevelPath(LevelResource.GENERATED_DIR).normalize();
    }

    private static void recursiveReplace(JsonObject root, String target, String replacement) {
        var targets = new ArrayList<String>();

        for (Map.Entry<String, JsonElement> entry : root.entrySet()) {
            var element = entry.getValue();

            if (element.isJsonObject()) {
                recursiveReplace(element.getAsJsonObject(), target, replacement);
                continue;
            }

            if (element.isJsonPrimitive()) {

                if (element.getAsString().contains(target))
                    targets.add(entry.getKey());
            }
        }

        for (String key : targets) {
            var element = root.remove(key);
            var value = element.getAsJsonPrimitive().getAsString();
            value = StringUtils.replace(value, target, replacement);
            root.remove(key);
            root.addProperty(key, value);
        }
    }
}
