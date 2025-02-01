package de.dafuqs.spectrum.mixin.client;

import com.llamalad7.mixinextras.sugar.*;
import de.dafuqs.spectrum.helpers.*;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.entity.effect.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.*;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin {
	
	@ModifyArg(method = "drawStatusEffectBackgrounds(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 0))
	private Identifier spectrum$modifyWideBackground(Identifier texture, @Local StatusEffectInstance effect) {
		return StatusEffectHelper.getTexture(texture, effect);
	}
	
	@ModifyArg(method = "drawStatusEffectBackgrounds(Lnet/minecraft/client/gui/DrawContext;IILjava/lang/Iterable;Z)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1))
	private Identifier spectrum$modifyBackground(Identifier texture, @Local(ordinal = 2) int height, @Local(argsOnly = true) Iterable<StatusEffectInstance> statusEffects, @Local(ordinal = 2) int i) {
		// This is so cursed... since statusEffectInstance is being compiled out, we get to do this instead
		HandledScreen<?> handledScreen = (HandledScreen<?>) (Object) this;
		Iterator<StatusEffectInstance> instance = statusEffects.iterator();
		for (int y = handledScreen.y; y < i && instance.hasNext(); y += height)
			instance.next();
		if (instance.hasNext())
			return StatusEffectHelper.getTexture(texture, instance.next());
		return texture;
	}
	
}
