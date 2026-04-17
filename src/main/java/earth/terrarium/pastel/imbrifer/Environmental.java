package earth.terrarium.pastel.imbrifer;

import earth.terrarium.pastel.data_loaders.dimension.ColorGradingLoader;
import earth.terrarium.pastel.data_loaders.dimension.EnvDataLoader;
import earth.terrarium.pastel.registries.PastelBiomes;
import earth.terrarium.pastel.registries.PastelLevels;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Cursor3D;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;

import java.util.function.Supplier;

public class Environmental {

    private static final InterpMemory<float[]> GRADING_QUEUE = new InterpMemory<>();
    private static final InterpMemory<float[]> ENV_QUEUE = new InterpMemory<>();
    private static final InterpMemory<EnvironmentalOverride> OVERRIDE_QUEUE = new InterpMemory<>();

    private static final Minecraft client = Minecraft.getInstance();
    private static final Supplier<Float> delta = () -> client.getTimer()
                                                             .getGameTimeDeltaPartialTick(false);
    private static long envLoop, overLoop;
    private static long over;
    private static boolean overActive;

    public static void tick(Entity entity) {
        var blending = client.options.biomeBlendRadius()
                                     .get();
        var level = client.level;

        assert level != null;
        envLoop = level.getGameTime() % 3;
        overLoop = level.getGameTime() % 4;

        if (envLoop == 0)
            updateBiomeData(entity.blockPosition(), blending);

        updateOverrides(entity);
        if (GRADING_QUEUE.ready())
            ColorGrading.update(GRADING_QUEUE.last(), GRADING_QUEUE.current(), (envLoop + delta.get()) / 3F);
    }

    private static void updateOverrides(Entity entity) {
        if (overActive && over < 20) {
            over++;
        } else if (!overActive && over > 0) {
            over--;
        }

        if (overLoop != 0)
            return;

        var current = EnvironmentalOverride.get(entity);
        overActive = current != EnvironmentalOverride.INACTIVE;

        if (!overActive && over > 0)
            return; // delay flushing the queue until the fadeout is done

        OVERRIDE_QUEUE.accept(current);
    }

    private static void updateBiomeData(BlockPos center, int blendingRadius) {
        if (blendingRadius <= 0) {
            var biome = findBiome(center);
            processAndAcceptEnv(center, EnvDataLoader.DATA.getOrDefault(biome, EnvironmentalData.NOOP)
                                                          .asArray()
            );
            GRADING_QUEUE.accept(ColorGradingLoader.DATA.getOrDefault(biome, ColorGrading.DEFAULT)
                                                        .asArray());
            return;
        }

        var envStack = new InterpolationStack(4);
        var gradingStack = new InterpolationStack(5);

        var cursor = new Cursor3D(
            center.getX() - blendingRadius, center.getY() - blendingRadius,
            center.getZ() - blendingRadius, center.getX() + blendingRadius,
            center.getY() + blendingRadius, center.getZ() + blendingRadius
        );

        var test = new BlockPos.MutableBlockPos();
        while (cursor.advance()) {
            test.set(cursor.nextX(), cursor.nextY(), cursor.nextZ());

            var biome = findBiome(test);
            envStack.insert(EnvDataLoader.DATA.getOrDefault(biome, EnvironmentalData.NOOP)
                                              .asArray());
            gradingStack.insert(ColorGradingLoader.DATA.getOrDefault(biome, ColorGrading.DEFAULT)
                                                       .asArray());
        }

        processAndAcceptEnv(center, envStack.get());
        GRADING_QUEUE.accept(gradingStack.get());
    }

    private static void processAndAcceptEnv(BlockPos ref, float[] env) {
        assert client.level != null;

        // Black langasts with depth darkening become actually just black
        if (findBiome(ref).equals(PastelBiomes.BLACK_LANGAST)) {
            ENV_QUEUE.accept(env);
            return;
        }

        var depthDarkening = 0F;
        var topSpace = client.level.getMaxBuildHeight() - ref.getY();
        depthDarkening += Mth.clampedLerp(0.334F, 0F, topSpace / 48F);

        var bottomSpace = ref.getY() - client.level.getMinBuildHeight();
        depthDarkening += Mth.clampedLerp(0.667F, 0F, bottomSpace / 64F);
        var depthFog = Mth.clampedLerp(0.337F, 1F, bottomSpace / 48F);

        env[0] = Math.clamp(env[0] + depthDarkening / 2F, 0, 1);
        env[1] = Math.clamp(env[1] - depthDarkening / 3F, 0.01F, 1);
        env[2] -= (1 - depthFog) * 2;
        env[3] *= depthFog;

        env[2] = Math.min(env[2], env[3]);

        ENV_QUEUE.accept(env);
    }

    public static EnvironmentalData getEnvData() {
        if (!ENV_QUEUE.ready())
            return EnvironmentalData.NOOP;

        var interpolated = new float[4];
        for (int i = 0; i < interpolated.length; i++) {
            interpolated[i] = Mth.lerp((envLoop + delta.get()) / 3F, ENV_QUEUE.last()[i], ENV_QUEUE.current()[i]);
        }

        var delta = overDelta();
        var override = processOverrides().dataOverride()
                                         .asArray();
        interpolated[0] += Math.clamp(Mth.lerp(delta, 0, override[0]), -1, 1);
        interpolated[1] += Mth.lerp(delta, 0, override[1]);
        interpolated[2] += Mth.lerp(delta, 0, override[2]);
        interpolated[3] += Mth.lerp(delta, 0, override[3]);

        interpolated[0] = Math.clamp(interpolated[0], -0.1F, 1);
        interpolated[1] = Math.clamp(interpolated[1], 0, 1);
        interpolated[2] = Math.max(interpolated[2], -10F);
        interpolated[3] = Math.max(interpolated[3], 0.125F);

        return EnvironmentalData.fromArray(interpolated);
    }

    // We got C# at home
    public static void applyColor(float[] out) {
        var override = processOverrides();
        var color = override.color()
                            .colorMod();
        var blend = override.color()
                            .blend();
        var delta = overDelta();

        out[0] = Mth.lerp(delta * blend, out[0], color.x);
        out[1] = Mth.lerp(delta * blend, out[1], color.y);
        out[2] = Mth.lerp(delta * blend, out[2], color.z);
    }

    private static float overDelta() {
        var mutation = overActive ?
                       over + delta.get() :
                       over - delta.get();

        return Math.clamp(mutation / 20F, 0, 1);
    }

    private static EnvironmentalOverride processOverrides() {
        if (!OVERRIDE_QUEUE.ready())
            return EnvironmentalOverride.INACTIVE;

        var cur = OVERRIDE_QUEUE.current()
                                .asArray();
        var last = OVERRIDE_QUEUE.last()
                                 .asArray();

        var interpolated = new float[8];
        for (int i = 0; i < cur.length; i++) {
            interpolated[i] = Mth.lerp(
                (overLoop + delta.get()) / 4F,
                last[i], cur[i]
            );
        }

        return EnvironmentalOverride.fromArray(interpolated);
    }

    public static float getNear(float original, boolean gentle) {
        float fogNear = getEnvData().fogNear();
        if (gentle)
            return original * (fogNear / 10);

        return fogNear * original;
    }

    public static float getFar(float original) {
        return getEnvData().fogFar() * original;
    }

    private static ResourceKey<Biome> findBiome(BlockPos pos) {
        assert client.level != null;
        return client.level.getBiome(pos)
                           .getKey();
    }

    private static class InterpolationStack {

        private float[] stack;
        private int insets;

        public InterpolationStack(int size) {
            stack = new float[size];
        }

        public void insert(float[] inset) {
            for (int i = 0; i < inset.length; i++) {
                stack[i] += inset[i];
            }

            insets++;
        }

        public void purge() {
            insets = 0;
            stack = new float[stack.length];
        }

        public float[] get() {
            var result = new float[stack.length];

            for (int i = 0; i < stack.length; i++) {
                result[i] = stack[i] / insets;
            }

            return result;
        }
    }

    public static State isActive() {
        if (client.level == null)
            return State.INACTIVE;

        if (client.level.dimension()
                        .equals(PastelLevels.DIMENSION_KEY))
            return State.ACTIVE;

        if (overActive || over > 0)
            return State.PARTIAL;

        return State.INACTIVE;
    }

    public enum State {
        INACTIVE(false),
        PARTIAL(true),
        ACTIVE(true);

        public final boolean overrides;

        State(boolean overrides) {
            this.overrides = overrides;
        }

        public boolean force() {
            return this == ACTIVE;
        }
    }
}
