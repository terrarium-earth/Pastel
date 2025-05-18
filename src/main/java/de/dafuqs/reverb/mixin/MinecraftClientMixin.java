package de.dafuqs.reverb.mixin;

import de.dafuqs.reverb.Reverb;
import de.dafuqs.reverb.sound.SoundEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.sounds.Music;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
	
	@Shadow
	public LocalPlayer player;
	
	@Shadow
	public ClientLevel level;
	
	@Inject(method = "getSituationalMusic", at = @At("HEAD"), cancellable = true)
	private void reverb$getMusicType(CallbackInfoReturnable<Music> ci) {
		if (this.player != null) {
			Optional<SoundEffects> soundEffects = Reverb.SOUND_EFFECTS.getOptional(level.dimension().location());
			if (soundEffects.isPresent()) {
				Optional<Music> musicSound = soundEffects.get().getMusic();
				musicSound.ifPresent(ci::setReturnValue);
			}
		}
	}
	
}
