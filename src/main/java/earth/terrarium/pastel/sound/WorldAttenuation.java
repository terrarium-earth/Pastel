package earth.terrarium.pastel.sound;

import com.google.common.collect.EvictingQueue;
import earth.terrarium.pastel.registries.PastelBiomes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public class WorldAttenuation {

    private static final Map<Tracked, Queue<Float>> ATTENUATION_DATA = new HashMap<>();

    public static Data getData() {
        var level = Minecraft.getInstance().level;
        var time = -1L;

        if (Minecraft.getInstance().level != null)
            time = level.getGameTime();

        return Cache
            .peek(time)
            .orElse(Cache.swap(Data.create(), time));
    }

    public static void tick(Level level, Entity camera, boolean active) {
        if (!active)
            return;

        var camPos = BlockPos
            .containing(camera.position())
            .above();
        var center = camPos.above(2);

        int checked = 0;
        float volBlocking = 0;
        float pitchMods = 0;
        float occlusion = 0;
        var volumetrics = new Vec3(0, 0, 0);

        // We shift up a few blocks because the ground is often the ground
        for (
            BlockPos check : BlockPos.randomInCube(level.getRandom(), 32, center, 9)
        ) {
            var volumetry = 1.0;

            if (!level.isInWorldBounds(check))
                continue;

            checked++;

            if (!level
                .getFluidState(check)
                .isEmpty()) {
                volBlocking += 2F;
                pitchMods += 1;
                occlusion += 0.5F;
                volumetrics
                    .add(
                        check
                            .subtract(center)
                            .getCenter()
                            .normalize()
                            .scale(0.25)
                    );
                continue;
            }

            var state = level.getBlockState(check);
            var hardness = state.getDestroySpeed(level, check);

            if (!state.canOcclude() || state.is(BlockTags.LEAVES)) {
                volBlocking += 0.5F;
                volumetry = 0.5F;
            } else if (hardness > 20F) {
                volBlocking += Math.min(5F, hardness / 10F);
                pitchMods += hardness / 30F;
            } else if (!state.isAir()) {
                volBlocking += 1F;
                volumetry = 0;
            }

            if (!state.isAir()) {
                occlusion += (float) Math.min(hardness / Math.sqrt(check.distSqr(center)) / 2, 5F);
            }

            volumetrics = volumetrics
                .add(
                    check
                        .subtract(center)
                        .getCenter()
                        .normalize()
                        .scale(volumetry)
                );
        }

        volumetrics = volumetrics.scale(1.0 / checked);

        if (!level
            .getFluidState(camPos)
            .isEmpty()) {
            pitchMods = checked * 0.75F;
            volBlocking *= 1.2F;
            occlusion = pitchMods;
        }

        if (level
            .getBiome(camPos)
            .is(PastelBiomes.BLACK_LANGAST) || level
                .getBiome(camPos)
                .is(PastelBiomes.DEEP_BARRENS))
            occlusion += checked / 2F;

        Tracked.VOLUME.remember(volBlocking, checked);
        Tracked.PITCH.remember(pitchMods, checked);
        Tracked.OCCLUSION.remember(occlusion, checked);
        for (
            Direction.Axis axis : Direction.Axis.values()
        ) {
            Tracked.remember(axis, (float) volumetrics.get(axis));
        }
    }

    private static float sample(Level level, BlockPos check) {
        var state = level.getBlockState(check);
        var vol = 0F;

        if (state.isAir()) {
            vol = 1;
        } else if (!state.canOcclude() || state.is(BlockTags.LEAVES)) {
            vol = 0.5F;
        }

        return vol;
    }

    public record Data(float volume, float pitch, float filtering, float x, float y, float z) {

        private static Data create() {
            return new Data(
                Tracked.VOLUME.get() * 2F,
                Tracked.PITCH.get(),
                Tracked.OCCLUSION.get() * 1.334F,
                Tracked.X.get(),
                Tracked.Y.get(),
                Tracked.Z.get()
            );
        }
    }

    private record Cache(Data data, long timeStamp) {
        private static Cache cache;

        private static Data swap(Data data, long time) {
            if (time == -1)
                return data;

            cache = new Cache(data, time);
            return data;
        }

        private static Optional<Data> peek(long time) {
            if (cache == null)
                return Optional.empty();

            return Optional.ofNullable(cache.timeStamp == time ? cache.data : null);
        }
    }

    private enum Tracked {
        VOLUME,
        PITCH,
        OCCLUSION,
        X(10),
        Y(10),
        Z(10);

        private final int size;

        Tracked(int size) {
            this.size = size;
        }

        Tracked() {
            this(20);
        }

        private void remember(float volBlocking, int checked) {
            ATTENUATION_DATA
                .get(this)
                .add(1 - Math.clamp(volBlocking / checked, 0, 1));
        }

        private static void remember(Direction.Axis axis, float val) {
            var tracked = switch (axis) {
                case X -> X;
                case Y -> Y;
                case Z -> Z;
            };
            ATTENUATION_DATA
                .get(tracked)
                .add(val);
        }

        private float get() {
            var dataFlow = ATTENUATION_DATA.get(this);

            var averageAttenuation = 0F;
            for (
                Float attenuation : dataFlow
            ) {
                if (attenuation.isNaN()) {
                    averageAttenuation += 1F;
                    continue;
                }

                averageAttenuation += attenuation;
            }

            return averageAttenuation / dataFlow.size();
        }
    }

    static {
        for (
            Tracked tracked : Tracked.values()
        ) {
            ATTENUATION_DATA.put(tracked, EvictingQueue.create(tracked.size));
        }
    }
}
