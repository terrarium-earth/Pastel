package earth.terrarium.pastel.sound;

import com.google.common.collect.EvictingQueue;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.Queue;

public class WorldAttenuation {

    private static final Queue<Float> VOLUME = EvictingQueue.create(20);
    private static final Queue<Float> PITCH = EvictingQueue.create(20);

    public static Data getData() {
        return new Data(getVolume(), getPitch());
    }

    private static float getVolume() {
        if (VOLUME.isEmpty())
            return 1F;

        var averageAttenuation = 0F;
        for (Float attenuation : VOLUME) {
            averageAttenuation += attenuation;
        }


        return averageAttenuation / VOLUME.size() * 2;
    }

    private static float getPitch() {
        if (PITCH.isEmpty())
            return 1F;

        var averageAttenuation = 0F;
        for (Float attenuation : PITCH) {
            averageAttenuation += attenuation;
        }


        return averageAttenuation / PITCH.size();
    }

    public static void tick(Level level, Entity camera, boolean active) {
        if (!active)
            return;

        var camPos = BlockPos.containing(camera.position()).above();

        int checked = 0;
        float volBlocking = 0;
        float pitchMods = 0;

        // We shift up a few blocks because the ground is often the ground
        for (BlockPos check : BlockPos.randomInCube(level.getRandom(), 32, camPos.above(2), 9)) {
            if (!level.isInWorldBounds(check))
                continue;

            checked++;

            if (!level.getFluidState(check).isEmpty()) {
                volBlocking += 2F;
                pitchMods += 1;
                continue;
            }

            var state = level.getBlockState(check);
            var hardness = state.getDestroySpeed(level, check);

            if(!state.canOcclude() || state.is(BlockTags.LEAVES)) {
                volBlocking += 0.5F;
            }
            else if(hardness > 20F) {
                volBlocking += Math.min(5F, hardness / 10F);
                pitchMods += hardness / 30F;
            }
            else if (!state.isAir()) {
                volBlocking += 1F;
            }
        }

        if (!level.getFluidState(camPos).isEmpty()) {
            pitchMods = checked * 0.75F;
            volBlocking *= 1.2F;
        }

        VOLUME.add(1 - Math.clamp(volBlocking / checked, 0, 1));
        PITCH.add(1 - Math.clamp(pitchMods / checked, 0, 1));
    }

    public record Data(float volume, float pitch) {}
}
