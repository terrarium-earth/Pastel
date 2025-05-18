package de.dafuqs.spectrum.compat.reverb;

import de.dafuqs.reverb.Reverb;
import de.dafuqs.reverb.sound.SoundEffects;
import de.dafuqs.reverb.sound.reverb.StaticReverbEffect;
import de.dafuqs.spectrum.SpectrumCommon;
import de.dafuqs.spectrum.registries.SpectrumDimensions;
import net.minecraft.core.Registry;

import java.util.Optional;

public class DimensionReverb {

	public static void setup() {
		if (SpectrumCommon.CONFIG.DimensionReverbDecayTime > 0 || SpectrumCommon.CONFIG.DimensionReverbDensity > 0) {
			Registry.register(Reverb.SOUND_EFFECTS, SpectrumDimensions.DIMENSION_ID, new SoundEffects(
					Optional.of(new StaticReverbEffect.Builder()
							.setDecayTime(SpectrumCommon.CONFIG.DimensionReverbDecayTime)
							.setDensity(SpectrumCommon.CONFIG.DimensionReverbDensity).build()
					), Optional.empty(), Optional.empty()));
		}
	}

}
