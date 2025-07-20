package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.injectors.ClientboundUpdateMobEffectPacketInjector;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "handleUpdateMobEffect", at = @At(value = "INVOKE",
                                                       target = "Lnet/minecraft/world/entity/LivingEntity;" +
                                                                "forceAddEffect(Lnet/minecraft/world/effect" +
                                                                "/MobEffectInstance;" +
                                                                "Lnet/minecraft/world/entity/Entity;)V"))
    public void readAndApplyIncurableFlag(
        ClientboundUpdateMobEffectPacket packet, CallbackInfo ci, @Local MobEffectInstance effect) {
        if (((ClientboundUpdateMobEffectPacketInjector) packet).isIncurable()) {
            effect.getCures()
                  .add(PastelMobEffects.Cures.INCURABLE);
        } else {
            effect.getCures()
                  .remove(PastelMobEffects.Cures.INCURABLE);
        }
    }

}
