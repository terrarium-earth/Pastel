package de.dafuqs.reverb.sound.reverb;

import de.dafuqs.reverb.Reverb;
import de.dafuqs.reverb.sound.SoundEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.EXTEfx;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class ReverbFilter {
	
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static int id = -1;
	public static int slot = -1;
	
	public static void update() {
		try {
			id = EXTEfx.alGenEffects();
			slot = EXTEfx.alGenAuxiliaryEffectSlots();
		} catch (Throwable t) {
			LOGGER.log(Level.WARN, "Error updating reverb filter. No audio devices present?");
		}
	}
	
	public static boolean update(SoundInstance soundInstance, ReverbEffect data) {
		if (id == -1 || slot == -1) {
			update();
		}
		
		Minecraft client = Minecraft.getInstance();
		
		if (data.isEnabled(client, soundInstance)) {
			EXTEfx.alAuxiliaryEffectSlotf(slot, EXTEfx.AL_EFFECTSLOT_GAIN, 0);
			EXTEfx.alEffecti(id, EXTEfx.AL_EFFECT_TYPE, EXTEfx.AL_EFFECT_REVERB);
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DENSITY, Mth.clamp(data.getDensity(client, soundInstance), EXTEfx.AL_REVERB_MIN_DENSITY, EXTEfx.AL_REVERB_MAX_DENSITY));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DIFFUSION, Mth.clamp(data.getDiffusion(client, soundInstance), EXTEfx.AL_REVERB_MIN_DIFFUSION, EXTEfx.AL_REVERB_MAX_DIFFUSION));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_GAIN, Mth.clamp(data.getGain(client, soundInstance), EXTEfx.AL_REVERB_MIN_GAIN, EXTEfx.AL_REVERB_MAX_GAIN));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_GAINHF, Mth.clamp(data.getGainHF(client, soundInstance), EXTEfx.AL_REVERB_MIN_GAINHF, EXTEfx.AL_REVERB_MAX_GAINHF));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DECAY_TIME, Mth.clamp(data.getDecayTime(client, soundInstance), EXTEfx.AL_REVERB_MIN_DECAY_TIME, EXTEfx.AL_REVERB_MAX_DECAY_TIME));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_DECAY_HFRATIO, Mth.clamp(data.getDecayHFRatio(client, soundInstance), EXTEfx.AL_REVERB_MIN_DECAY_HFRATIO, EXTEfx.AL_REVERB_MAX_DECAY_HFRATIO));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_REFLECTIONS_GAIN, Mth.clamp(data.getReflectionsGainBase(client, soundInstance), EXTEfx.AL_REVERB_MIN_REFLECTIONS_GAIN, EXTEfx.AL_REVERB_MAX_REFLECTIONS_GAIN));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_REFLECTIONS_DELAY, Mth.clamp(data.getReflectionsDelay(client, soundInstance), EXTEfx.AL_REVERB_MIN_REFLECTIONS_DELAY, EXTEfx.AL_REVERB_MAX_REFLECTIONS_DELAY));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_LATE_REVERB_GAIN, Mth.clamp(data.getLateReverbGainBase(client, soundInstance), EXTEfx.AL_REVERB_MIN_LATE_REVERB_GAIN, EXTEfx.AL_REVERB_MAX_LATE_REVERB_GAIN));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_LATE_REVERB_DELAY, Mth.clamp(data.getLateReverbDelay(client, soundInstance), EXTEfx.AL_REVERB_MIN_LATE_REVERB_DELAY, EXTEfx.AL_REVERB_MAX_LATE_REVERB_DELAY));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_AIR_ABSORPTION_GAINHF, Mth.clamp(data.getAirAbsorptionGainHF(client, soundInstance), EXTEfx.AL_REVERB_MIN_AIR_ABSORPTION_GAINHF, EXTEfx.AL_REVERB_MAX_AIR_ABSORPTION_GAINHF));
			EXTEfx.alEffectf(id, EXTEfx.AL_REVERB_ROOM_ROLLOFF_FACTOR, Mth.clamp(soundInstance.getAttenuation() == SoundInstance.Attenuation.LINEAR ? 2.0F / (Math.max(soundInstance.getVolume(), 1.0F) + 2.0F) : 0.0F, EXTEfx.AL_REVERB_MIN_ROOM_ROLLOFF_FACTOR, EXTEfx.AL_REVERB_MAX_ROOM_ROLLOFF_FACTOR));
			EXTEfx.alEffecti(id, EXTEfx.AL_REVERB_DECAY_HFLIMIT, Mth.clamp(data.getDecayHFLimit(client, soundInstance), EXTEfx.AL_REVERB_MIN_DECAY_HFLIMIT, EXTEfx.AL_REVERB_MAX_DECAY_HFLIMIT));
			EXTEfx.alAuxiliaryEffectSloti(slot, EXTEfx.AL_EFFECTSLOT_EFFECT, id);
			EXTEfx.alAuxiliaryEffectSlotf(slot, EXTEfx.AL_EFFECTSLOT_GAIN, 1);
			
			return true;
		}
		return false;
	}
	
	public static void update(SoundInstance soundInstance, int sourceID) {
		Minecraft client = Minecraft.getInstance();
		
		if (!(client == null || client.level == null)) {
			Optional<SoundEffects> soundEffects = Reverb.SOUND_EFFECTS.getOptional(client.level.dimension().location());
			if (soundEffects.isPresent()) {
				Optional<ReverbEffect> reverb = soundEffects.get().getReverb();
				if (reverb.isPresent()) {
					if (!reverb.get().shouldIgnore(soundInstance.getLocation())) {
						for (int i = 0; i < 2; i++) {
							AL11.alSourcei(sourceID, EXTEfx.AL_DIRECT_FILTER, 0);
							AL11.alSource3i(sourceID, EXTEfx.AL_AUXILIARY_SEND_FILTER, update(soundInstance, reverb.get()) ? slot : 0, 0, 0);
							int error = AL11.alGetError();
							if (error == AL11.AL_NO_ERROR) {
								break;
							} else {
								LOGGER.warn("OpenAl Error {}", error);
							}
						}
					}
				}
			}
		}
	}
	
}
