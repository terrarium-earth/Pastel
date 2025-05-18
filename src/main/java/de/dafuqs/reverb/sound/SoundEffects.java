package de.dafuqs.reverb.sound;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.dafuqs.reverb.sound.distortion.DistortionEffect;
import de.dafuqs.reverb.sound.reverb.ReverbEffect;
import net.minecraft.sounds.Music;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class SoundEffects {
	
	public static final Codec<SoundEffects> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
			ReverbEffect.CODEC.optionalFieldOf("reverb").stable().forGetter((soundEffects) -> soundEffects.reverb),
			DistortionEffect.CODEC.optionalFieldOf("distortion").stable().forGetter((soundEffects) -> soundEffects.distortion),
			Music.CODEC.optionalFieldOf("music").stable().forGetter((soundEffects) -> soundEffects.music)
	).apply(instance, instance.stable(SoundEffects::new)));
	
	private final Optional<ReverbEffect> reverb;
	private final Optional<DistortionEffect> distortion;
	private final Optional<Music> music;
	
	public SoundEffects() {
		this(Optional.empty(), Optional.empty(), Optional.empty());
	}
	
	public SoundEffects(Optional<ReverbEffect> reverb, Optional<DistortionEffect> distortion, Optional<Music> music) {
		this.reverb = reverb;
		this.distortion = distortion;
		this.music = music;
	}
	
	public Optional<ReverbEffect> getReverb() {
		return reverb;
	}
	
	public Optional<DistortionEffect> getDistortion() {
		return distortion;
	}
	
	public Optional<Music> getMusic() {
		return music;
	}
	
}
