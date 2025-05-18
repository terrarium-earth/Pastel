package de.dafuqs.reverb.sound.reverb;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import de.dafuqs.reverb.Reverb;
import net.fabricmc.fabric.api.event.registry.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.MappedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/**
 * A Reverb effect controls
 */
public abstract class ReverbEffect {
	
	public static final ResourceKey<Registry<MapCodec<? extends ReverbEffect>>> REVERB_EFFECT_CODEC_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(Reverb.MOD_ID, "reverb_effect"));
	public static final MappedRegistry<MapCodec<? extends ReverbEffect>> REVERB_EFFECT_CODEC = FabricRegistryBuilder.createSimple(REVERB_EFFECT_CODEC_KEY).attribute(RegistryAttribute.SYNCED).buildAndRegister();
	public static final Codec<ReverbEffect> CODEC = REVERB_EFFECT_CODEC.byNameCodec().dispatchStable(ReverbEffect::getCodec, Function.identity());
	
	public abstract MapCodec<? extends ReverbEffect> getCodec();
	
	/**
	 * Whether a Sound Event should be ignored
	 *
	 * @param identifier the Identifier of the Sound Event
	 */
	public abstract boolean shouldIgnore(ResourceLocation identifier);
	
	public abstract boolean isEnabled(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getAirAbsorptionGainHF(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getDecayHFRatio(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getDensity(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getDiffusion(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getGain(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getGainHF(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getLateReverbGainBase(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getDecayTime(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getReflectionsGainBase(Minecraft client, SoundInstance soundInstance);
	
	public abstract int getDecayHFLimit(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getLateReverbDelay(Minecraft client, SoundInstance soundInstance);
	
	public abstract float getReflectionsDelay(Minecraft client, SoundInstance soundInstance);
	
}
