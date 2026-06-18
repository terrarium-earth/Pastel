package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.registries.PastelLevels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

@OnlyIn(
    Dist.CLIENT
)
public class PastelSourceEffects {

    public static final Logger LOGGER = LogManager.getLogger();

    public static int effect = -1;

    public static int filter = -1;

    public static int slot = -1;

    public static void updateSlots() {
        try {
            effect = EXTEfx.alGenEffects();
            filter = EXTEfx.alGenFilters();
            slot = EXTEfx.alGenAuxiliaryEffectSlots();
        } catch (Throwable t) {
            LOGGER.log(Level.WARN, "Error updating reverb filter. No audio devices present?");
        }
    }

    public static void tick(SoundInstance soundInstance, int sourceID) {
        Minecraft client = Minecraft.getInstance();

        if (client.level == null) {
            return;
        }

        if (client.level
            .dimension()
            .location() != PastelLevels.DIMENSION_ID) {
            return;
        }

        if (!(PastelCommon.CONFIG.DimensionReverbDecayTime > 0) && !(PastelCommon.CONFIG.DimensionReverbDensity > 0)) {
            return;
        }

        ResourceLocation identifier = soundInstance.getLocation();

        if (identifier
            .getPath()
            .contains("ui.") || identifier
                .getPath()
                .contains("music.") || identifier
                    .getPath()
                    .contains("block.lava.pop") || identifier
                        .getPath()
                        .contains("weather.") || identifier
                            .getPath()
                            .startsWith("atmosfera") || identifier
                                .getPath()
                                .startsWith("dynmus")
        ) {
            return;
        }

        AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER, 0);

        if (effect == -1 || filter == -1 || slot == -1) {
            updateSlots();
        }

        EXTEfx.alAuxiliaryEffectSlotf(slot, EXTEfx.AL_EFFECTSLOT_GAIN, 0);
        var data = WorldAttenuation.getData();

        updateEffects(data);
        updateFilters(data);

        EXTEfx.alAuxiliaryEffectSloti(slot, EXTEfx.AL_EFFECTSLOT_EFFECT, effect);
        EXTEfx.alAuxiliaryEffectSlotf(slot, EXTEfx.AL_EFFECTSLOT_GAIN, 1);
        AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER, filter);
        AL11.alSource3i(sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER, slot, 0, 0);
        int error = AL11.alGetError();
        if (error != AL11.AL_NO_ERROR) {
            LOGGER.warn("OpenAl Error {}", error);
        }
    }

    private static void updateEffects(WorldAttenuation.Data data) {
        EXTEfx.alEffecti(effect, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_REVERB);
        EXTEfx
            .alEffectf(
                effect,
                EXTEfx.AL_REVERB_DENSITY,
                Mth
                    .clamp(
                        PastelCommon.CONFIG.DimensionReverbDensity * data.pitch(),
                        EXTEfx.AL_REVERB_MIN_DENSITY,
                        EXTEfx.AL_REVERB_MAX_DENSITY
                    )
            );
        EXTEfx
            .alEffectf(
                effect,
                EXTEfx.AL_REVERB_DECAY_TIME,
                Mth
                    .clamp(
                        PastelCommon.CONFIG.DimensionReverbDecayTime * Math.max(data.volume() * 1.65F, 0.2F),
                        EXTEfx.AL_REVERB_MIN_DECAY_TIME,
                        EXTEfx.AL_REVERB_MAX_DECAY_TIME
                    )
            );
    }

    private static void updateFilters(WorldAttenuation.Data data) {
        EXTEfx.alFilteri(filter, EXTEfx.AL_FILTER_TYPE, EXTEfx.AL_FILTER_LOWPASS);
        EXTEfx.alFilterf(filter, EXTEfx.AL_LOWPASS_GAIN, Math.clamp(data.filtering() * 2F, 0, 1));
        EXTEfx.alFilterf(filter, EXTEfx.AL_LOWPASS_GAINHF, Math.clamp(data.filtering() * data.pitch(), 0, 1));
    }
}
