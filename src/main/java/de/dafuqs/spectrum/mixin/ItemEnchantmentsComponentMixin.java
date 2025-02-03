package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.*;
import de.dafuqs.spectrum.*;
import de.dafuqs.spectrum.injectors.*;
import net.minecraft.component.type.*;
import net.minecraft.enchantment.*;
import net.minecraft.registry.entry.*;
import net.minecraft.text.*;
import net.minecraft.util.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

import java.util.function.*;

@Mixin(ItemEnchantmentsComponent.class)
public abstract class ItemEnchantmentsComponentMixin implements ItemEnchantmentsComponentInjector {
	
	@ModifyVariable(method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At("STORE"))
	private RegistryEntry<Enchantment> spectrum$appendTooltip$hideRevealedEnchantmentCloak(RegistryEntry<Enchantment> entry) {
		var comp = (ItemEnchantmentsComponent) (Object) this;
		return comp.spectrum$cloakState(entry) == ItemEnchantmentsComponentInjector.CloakState.HIDDEN ? null : entry;
	}
	
	@WrapOperation(method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/Enchantment;getName(Lnet/minecraft/registry/entry/RegistryEntry;I)Lnet/minecraft/text/Text;"))
	private Text spectrum$modifyCloakingEnchantmentName(RegistryEntry<Enchantment> entry, int level, Operation<Text> original) {
		var comp = (ItemEnchantmentsComponent) (Object) this;
		switch (comp.spectrum$cloakState(entry)) {
			case REVEALED:
				return original.call(entry, level);
			case OBFUSCATED: {
				var text = original.call(entry, level);
				if (SpectrumCommon.CONFIG.NameForUnrevealedEnchantments.isBlank() && text instanceof MutableText mutableText)
					return mutableText.formatted(Formatting.byCode('k'));
				else
					return Text.literal(SpectrumCommon.CONFIG.NameForUnrevealedEnchantments).setStyle(text.getStyle());
			}
		}
		return null;
	}
	
	@WrapOperation(method = "appendTooltip(Lnet/minecraft/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/item/tooltip/TooltipType;)V", at = @At(value = "INVOKE", target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V"))
	private void spectrum$ignoreNullEnchantmentName(Consumer<Text> instance, Object obj, Operation<Void> original) {
		if (obj != null) original.call(instance, obj);
	}
	
}