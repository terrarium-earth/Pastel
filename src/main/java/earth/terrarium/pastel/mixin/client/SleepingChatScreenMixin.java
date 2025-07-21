package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InBedChatScreen.class)
public class SleepingChatScreenMixin {

    @Inject(method = "init", at = @At(value = "INVOKE",
                                      target = "Lnet/minecraft/client/gui/components/Button;builder" +
                                               "(Lnet/minecraft/network/chat/Component;" +
                                               "Lnet/minecraft/client/gui/components/Button$OnPress;)" +
                                               "Lnet/minecraft/client/gui/components/Button$Builder;"),
            cancellable = true)
    private void removeSleepButton(CallbackInfo ci) {
        if (Minecraft.getInstance().cameraEntity instanceof Player player && MiscPlayerData.get(player)
                                                                                           .isSleeping())
            ci.cancel();
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE",
                                                   target = "Lnet/minecraft/client/gui/components/Button;render" +
                                                            "(Lnet/minecraft/client/gui/GuiGraphics;IIF)V"))
    private boolean stopButtonRendering(Button instance, GuiGraphics drawContext, int mouseX, int mouseY, float v) {
        return !(Minecraft.getInstance().cameraEntity instanceof Player player) || !MiscPlayerData.get(player)
                                                                                                  .isSleeping();
    }
}
