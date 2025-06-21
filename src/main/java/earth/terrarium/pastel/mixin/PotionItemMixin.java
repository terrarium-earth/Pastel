package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.components.CustomPotionDataComponent;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PotionItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PotionItem.class)
public abstract class PotionItemMixin {
	
	@ModifyReturnValue(method = "getUseDuration", at = @At("RETURN"))
	private int spectrum$modifyDrinkTime(int drinkTime, ItemStack stack) {
		CustomPotionDataComponent component = stack.get(PastelDataComponentTypes.CUSTOM_POTION_DATA);
		if (component != null) {
			int additionalDrinkDuration = component.additionalDrinkDuration();
			drinkTime += Math.max(4, drinkTime + additionalDrinkDuration);
		}
		return drinkTime;
	}

}
