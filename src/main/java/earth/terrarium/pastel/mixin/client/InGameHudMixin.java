package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.helpers.level.MobEffectHelper;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

	@Shadow @Final private Minecraft minecraft;
	
    @Shadow protected abstract Player getCameraPlayer();

    @Shadow public abstract void render(GuiGraphics context, DeltaTracker tickerCounter);

    @Shadow protected abstract void renderHeart(GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking);

    @ModifyExpressionValue(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;useFancyGraphics()Z"))
    private boolean disableVignietteInDimension(boolean original) {
		var player = Minecraft.getInstance().player;
		var isInDim = player != null && PastelDimensions.DIMENSION_KEY.equals(player.level().dimension());
        return !isInDim && original;
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void disableCrosshairSomnolence(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
		var potency = SleepStatusEffect.getSleepScaling(getCameraPlayer());
        if (potency > 0.25F)
			ci.cancel();
    }

    @Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
    private void disableHotbarSomnolence(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
		var potency = SleepStatusEffect.getSleepScaling(getCameraPlayer());
        if (potency > 0.4F)
			ci.cancel();
    }

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void disableStatusSomnolence(GuiGraphics context, CallbackInfo ci) {
		var potency = SleepStatusEffect.getSleepScaling(getCameraPlayer());
		if (potency > 0.4F)
			ci.cancel();
    }

    @ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blitSprite (Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0))
    private ResourceLocation modifyAmbientEffectBackgrounds(ResourceLocation texture, @Local MobEffectInstance effect) {
		return MobEffectHelper.getTextureLocation(texture, effect, MobEffectHelper.RenderType.HUD_AMBIENT);
    }
    
    @ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blitSprite (Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1))
    private ResourceLocation modifyEffectBackgrounds(ResourceLocation texture, @Local MobEffectInstance effect) {
		return MobEffectHelper.getTextureLocation(texture, effect, MobEffectHelper.RenderType.HUD_DEFAULT);
    }


    @WrapOperation(method = "renderHearts", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Gui;renderHeart(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/gui/Gui$HeartType;IIZZZ)V"))
    private void renderDivinityHearts(Gui instance, GuiGraphics guiGraphics, Gui.HeartType heartType, int x, int y, boolean hardcore, boolean halfHeart, boolean blinking, Operation<Void> original, @Local(argsOnly = true) Player player) {
        if (player.hasEffect(PastelMobEffects.DIVINITY)) {
            renderHeart(guiGraphics, heartType, x, y, true, halfHeart, blinking);
        }
        else {
            original.call(instance, guiGraphics, heartType, x, y, hardcore, halfHeart, blinking);
        }
    }
}
