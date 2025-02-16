package de.dafuqs.spectrum.mixin.client;

import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin {
	
	@ModifyVariable(method = "drawStatusEffectBackgrounds(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V", at = @At("STORE"))
	private StatusEffectInstance spectrum$saveEffect(StatusEffectInstance value, @Share("effect") LocalRef<StatusEffectInstance> effect) {
		effect.set(value);
		return value;
	}
	
	@ModifyArg(method = "drawStatusEffectBackgrounds(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
	private Identifier spectrum$modifyWideBackground(Identifier texture, @Local StatusEffectInstance effect) {
		return StatusEffectHelper.getTexture(texture, effect, StatusEffectHelper.RenderType.GUI_LARGE);
	}
	
	@ModifyArg(method = "drawStatusEffectBackgrounds(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1))
	private Identifier spectrum$modifyBackground(Identifier texture, @Share("effect") LocalRef<StatusEffectInstance> effect) {
		return StatusEffectHelper.getTexture(texture, effect.get(), StatusEffectHelper.RenderType.GUI_SMALL);
	}
	
}
