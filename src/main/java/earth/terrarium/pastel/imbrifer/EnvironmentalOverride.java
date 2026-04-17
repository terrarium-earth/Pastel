package earth.terrarium.pastel.imbrifer;

import net.minecraft.world.entity.Entity;
import org.apache.commons.lang3.ArrayUtils;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public record EnvironmentalOverride(
    Predicate<Entity> predicate, ColorData color, EnvironmentalData dataOverride, int priority
) {
    private static final List<EnvironmentalOverride> OVERRIDES = new ArrayList<>();

    public static final ColorData BLANK = new ColorData(new Vector3f(), 0);
    public static final EnvironmentalOverride INACTIVE = new EnvironmentalOverride(
        null, BLANK, EnvironmentalData.NOOP, -999);
    static final EnvironmentalData NOOP = new EnvironmentalData(0F, 0, 0, 0);

    public EnvironmentalOverride(Predicate<Entity> predicate, ColorData data, int priority) {
        this(predicate, data, EnvironmentalData.NOOP, priority);
    }

    public EnvironmentalOverride(Predicate<Entity> predicate, EnvironmentalData dataOverride, int priority) {
        this(predicate, BLANK, dataOverride, priority);
    }

    public static EnvironmentalOverride fromArray(float[] override) {
        return new EnvironmentalOverride(
            null,
            new ColorData(
                new Vector3f(
                    override[1],
                    override[2],
                    override[3]
                ),
                override[0]
            ),
            new EnvironmentalData(
                override[4],
                override[5],
                override[6],
                override[7]
            ),
            -999
        );
    }

    public float[] asArray() {
        var color = color().colorMod;

        return ArrayUtils.addAll(
            new float[]{
                color().blend,
                color.x,
                color.y,
                color.z
            }, dataOverride.asArray()
        );
    }

    public static EnvironmentalOverride get(Entity camera) {
        EnvironmentalOverride effect = INACTIVE;

        for (EnvironmentalOverride override : OVERRIDES) {
            if (!override.predicate.test(camera))
                continue;

            if (effect == INACTIVE) {
                effect = override;
                continue;
            }

            if (override.priority > effect.priority)
                effect = override;
        }

        return effect;
    }

    public static void register(EnvironmentalOverride override) {
        OVERRIDES.add(override);
    }

    public record ColorData(Vector3f colorMod, float blend) {
        public boolean isBlank() {
            return this == BLANK;
        }
    }
}
