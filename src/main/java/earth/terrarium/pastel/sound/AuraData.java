package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.registries.PastelBlockTags;
import earth.terrarium.pastel.registries.PastelSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.Level;

import java.util.function.BiPredicate;

public record AuraData(
    SoundEvent sound, BiPredicate<BlockPos, Level> filter, boolean pitchShift, int min, int scaling, int maxDistance,
    float volMult
) {

    public static final AuraData AZURITE = new AuraData(
        PastelSounds.OST_AZURE,
        (pos, level) -> level.getBlockState(pos)
                             .is(PastelBlockTags.AZURITE_ORES),
        true,
        10,
        64,
        32,
        0.7F
    );
}
