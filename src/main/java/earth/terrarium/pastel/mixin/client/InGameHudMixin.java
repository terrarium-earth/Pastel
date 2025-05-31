package earth.terrarium.pastel.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.helpers.StatusEffectHelper;
import earth.terrarium.pastel.registries.SpectrumDimensions;
import earth.terrarium.pastel.registries.SpectrumStatusEffects;
import earth.terrarium.pastel.render.HudRenderers;
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
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class InGameHudMixin {

	@Shadow @Final private Minecraft minecraft;
	
    @Shadow protected abstract Player getCameraPlayer();

    @Shadow public abstract void render(GuiGraphics context, DeltaTracker tickerCounter);

    @ModifyExpressionValue(method = "renderCameraOverlays", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;useFancyGraphics()Z"))
    private boolean spectrum$disableVignietteInDimension(boolean original) {
		var player = Minecraft.getInstance().player;
		var isInDim = player != null && SpectrumDimensions.DIMENSION_KEY.equals(player.level().dimension());
        return !isInDim && original;
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void spectrum$disableCrosshairSomnolence(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
		var potency = SleepStatusEffect.getSleepScaling(getCameraPlayer());
        if (potency > 0.25F)
			ci.cancel();
    }

    @Inject(method = "renderItemHotbar", at = @At("HEAD"), cancellable = true)
    private void spectrum$disableHotbarSomnolence(GuiGraphics context, DeltaTracker tickCounter, CallbackInfo ci) {
		var potency = SleepStatusEffect.getSleepScaling(getCameraPlayer());
        if (potency > 0.4F)
			ci.cancel();
    }

    @Inject(method = "renderPlayerHealth", at = @At("HEAD"), cancellable = true)
    private void spectrum$disableStatusSomnolence(GuiGraphics context, CallbackInfo ci) {
		var potency = SleepStatusEffect.getSleepScaling(getCameraPlayer());
		if (potency > 0.4F)
			ci.cancel();
    }

    @ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blitSprite (Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 0))
    private ResourceLocation modifyAmbientEffectBackgrounds(ResourceLocation texture, @Local MobEffectInstance effect) {
		return StatusEffectHelper.getTextureLocation(texture, effect, StatusEffectHelper.RenderType.HUD_AMBIENT);
    }
    
    @ModifyArg(method = "renderEffects", at = @At(value = "INVOKE", target = "net/minecraft/client/gui/GuiGraphics.blitSprite (Lnet/minecraft/resources/ResourceLocation;IIII)V", ordinal = 1))
    private ResourceLocation modifyEffectBackgrounds(ResourceLocation texture, @Local MobEffectInstance effect) {
		return StatusEffectHelper.getTextureLocation(texture, effect, StatusEffectHelper.RenderType.HUD_DEFAULT);
    }

    @ModifyVariable(method = "renderHearts", at = @At("STORE"), ordinal = 7)
    private int spectrum$showDivinityHardcoreHearts(int i, GuiGraphics context, Player player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking) {
        if (player.hasEffect(SpectrumStatusEffects.DIVINITY)) {
            return 9 * 5;
        }
        return i;
    }
}
