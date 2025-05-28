package earth.terrarium.pastel.compat.reverb;

import de.dafuqs.reverb.Reverb;
import de.dafuqs.reverb.sound.SoundEffects;
import de.dafuqs.reverb.sound.reverb.StaticReverbEffect;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.registries.SpectrumDimensions;
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
