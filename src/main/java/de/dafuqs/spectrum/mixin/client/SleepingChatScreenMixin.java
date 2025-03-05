package de.dafuqs.spectrum.mixin.client;

import com.llamalad7.mixinextras.injector.v2.*;
import de.dafuqs.spectrum.cca.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.widget.*;
import net.minecraft.entity.player.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(SleepingChatScreen.class)
public class SleepingChatScreenMixin {

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;builder(Lnet/minecraft/text/Text;Lnet/minecraft/client/gui/widget/ButtonWidget$PressAction;)Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;"), cancellable = true)
    private void spectrum$removeSleepButton(CallbackInfo ci) {
        if (MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player && MiscPlayerDataComponent.get(player).isSleeping())
			ci.cancel();
    }

    @WrapWithCondition(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ButtonWidget;render(Lnet/minecraft/client/gui/DrawContext;IIF)V"))
    private boolean spectrum$stopButtonRendering(ButtonWidget instance, DrawContext drawContext, int mouseX, int mouseY, float v) {
        return !(MinecraftClient.getInstance().cameraEntity instanceof PlayerEntity player) || !MiscPlayerDataComponent.get(player).isSleeping();
    }
}
