package de.dafuqs.reverb.sound.distortion;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import de.dafuqs.reverb.Reverb;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.*;

import java.util.function.Function;

/**
 * A Distortion effect controls
 */
public abstract class DistortionEffect {
	
	public static final ResourceKey<Registry<MapCodec<? extends DistortionEffect>>> DISTORTION_EFFECT_CODEC_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Reverb.MOD_ID, "distortion_effect"));
	public static final Registry<MapCodec<? extends DistortionEffect>> DISTORTION_EFFECT_CODEC = new RegistryBuilder<>(DISTORTION_EFFECT_CODEC_KEY).sync(true).create();
	public static final Codec<DistortionEffect> CODEC = DISTORTION_EFFECT_CODEC.byNameCodec().dispatchStable(DistortionEffect::getCodec, Function.identity());
	
	public abstract MapCodec<? extends DistortionEffect> getCodec();
	
	/**
	 * Whether a Sound Event should be ignored
	 *
	 * @param identifier the Identifier of the Sound Event
	 */
	public abstract boolean shouldIgnore(ResourceLocation identifier);
	
	public abstract boolean isEnabled(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getEdge(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getGain(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getLowpassCutoff(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getEQCenter(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getEQBandWidth(Minecraft client, SoundInstance soundInstance);
	
}
