package earth.terrarium.pastel.sound;

import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.registries.SpectrumDimensions;
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

@OnlyIn(Dist.CLIENT)
public class ReverbFilter {

    public static final Logger LOGGER = LogManager.getLogger();

    public static int id = -1;
    public static int slot = -1;

    public static void updateSlots() {
        try {
            id = EXTEfx.alGenEffects();
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

        if (client.level.dimension().location() != SpectrumDimensions.DIMENSION_ID) {
            return;
        }

        if (!(SpectrumCommon.CONFIG.DimensionReverbDecayTime > 0) && !(SpectrumCommon.CONFIG.DimensionReverbDensity > 0)) {
            return;
        }

        ResourceLocation identifier = soundInstance.getLocation();

        if (identifier.getPath().contains("ui.") ||
                identifier.getPath().contains("music.") ||
                identifier.getPath().contains("block.lava.pop") ||
                identifier.getPath().contains("weather.") ||
                identifier.getPath().startsWith("atmosfera") ||
                identifier.getPath().startsWith("dynmus")
        ) {
            return;
        }

        for (int i = 0; i < 2; i++) {
            AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER, 0);

            if (id == -1 || slot == -1) {
                updateSlots();
            }

            EXTEfx.alAuxiliaryEffectSlotf(slot, EXTEfx.AL_EFFECTSLOT_GAIN, 0);
            EXTEfx.alEffecti(id, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_REVERB);
            EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DENSITY, Mth.clamp(SpectrumCommon.CONFIG.DimensionReverbDensity, EXTEfx.AL_REVERB_MIN_DENSITY, EXTEfx.AL_REVERB_MAX_DENSITY));
            EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DECAY_TIME, Mth.clamp(SpectrumCommon.CONFIG.DimensionReverbDecayTime, EXTEfx.AL_REVERB_MIN_DECAY_TIME, EXTEfx.AL_REVERB_MAX_DECAY_TIME));
            EXTEfx.alAuxiliaryEffectSloti(slot, EXTEfx.AL_EFFECTSLOT_EFFECT, id);
            EXTEfx.alAuxiliaryEffectSlotf(slot, EXTEfx.AL_EFFECTSLOT_GAIN, 1);
            AL11.alSource3i(sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER, slot, 0, 0);
            int error = AL11.alGetError();
            if (error == AL11.AL_NO_ERROR) {
                break;
            } else {
                LOGGER.warn("OpenAl Error {}", error);
            }
        }
    }
}
