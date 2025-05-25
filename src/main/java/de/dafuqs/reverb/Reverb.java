package de.dafuqs.reverb;

import de.dafuqs.reverb.sound.SoundEffects;
import de.dafuqs.reverb.sound.distortion.DistortionEffect;
import de.dafuqs.reverb.sound.distortion.StaticDistortionEffect;
import de.dafuqs.reverb.sound.reverb.ReverbEffect;
import de.dafuqs.reverb.sound.reverb.StaticReverbEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.*;

public class Reverb {
	
	public static final String MOD_ID = "reverb";
	
	public static final ResourceKey<Registry<SoundEffects>> SOUND_EFFECTS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, "sound_effects"));
	public static final Registry<SoundEffects> SOUND_EFFECTS = new RegistryBuilder<>(SOUND_EFFECTS_KEY).sync(true).create();
	
	public static void onInitialize(IEventBus modEventBus) {
		Registry.register(ReverbEffect.REVERB_EFFECT_CODEC, ResourceLocation.fromNamespaceAndPath(MOD_ID, "static"), StaticReverbEffect.CODEC);
		Registry.register(DistortionEffect.DISTORTION_EFFECT_CODEC, ResourceLocation.fromNamespaceAndPath(MOD_ID, "static"), StaticDistortionEffect.CODEC);
	}
	
}
