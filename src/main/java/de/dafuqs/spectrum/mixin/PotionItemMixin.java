package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import de.dafuqs.spectrum.components.CustomPotionDataComponent;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {
	
	@ModifyReturnValue(method = "getUseDuration", at = @At("RETURN"))
	private int spectrum$modifyDrinkTime(int drinkTime, ItemStack stack) {
		CustomPotionDataComponent component = stack.get(SpectrumDataComponentTypes.CUSTOM_POTION_DATA);
		if (component != null) {
			int additionalDrinkDuration = component.additionalDrinkDuration();
			drinkTime += Math.max(4, drinkTime + additionalDrinkDuration);
		}
		return drinkTime;
	}

}
