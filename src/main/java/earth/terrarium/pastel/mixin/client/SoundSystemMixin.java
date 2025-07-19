package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.sound.PastelSourceEffects;
import earth.terrarium.pastel.mixin.client.accessors.SourceAccessor;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEngine;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundEngine.class)
public abstract class SoundSystemMixin {

    @Inject(method = "tickNonPaused", at = @At(value = "INVOKE",
                                               target = "net/minecraft/client/Options.getSoundSourceVolume " +
                                                        "(Lnet/minecraft/sounds/SoundSource;)F"))
    public void reverb$tick(
        CallbackInfo ci, @Local ChannelAccess.ChannelHandle sourceManager, @Local SoundInstance soundInstance) {
        sourceManager.execute(source -> PastelSourceEffects.tick(soundInstance, ((SourceAccessor) source).getSource()));
    }

    @Inject(method = "play", at = @At(value = "INVOKE",
                                      target = "net/minecraft/client/sounds/ChannelAccess$ChannelHandle.execute " +
                                               "(Ljava/util/function/Consumer;)V",
                                      ordinal = 0, shift = Shift.AFTER))
    public void reverb$play(
        SoundInstance soundInstance, CallbackInfo ci, @Local ChannelAccess.ChannelHandle sourceManager) {
        sourceManager.execute(source -> PastelSourceEffects.tick(soundInstance, ((SourceAccessor) source).getSource()));
    }

    @Inject(method = "reload", at = @At("TAIL"))
    public void reverb$reloadSounds(CallbackInfo ci) {
        PastelSourceEffects.updateSlots();
    }

}
