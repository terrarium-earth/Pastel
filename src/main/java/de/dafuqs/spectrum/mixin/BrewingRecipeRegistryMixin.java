package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.*;
import net.minecraft.component.*;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.recipe.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(BrewingRecipeRegistry.class)
public abstract class BrewingRecipeRegistryMixin {
	
	@Inject(method = "isValidIngredient(Lnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
	private void spectrum$disallowPigmentPotionBrewing(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		PotionContentsComponent potionContentsComponent = stack.getOrDefault(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT);
		if (potionContentsComponent.potion().isPresent() && potionContentsComponent.potion().get().equals(SpectrumPotions.PIGMENT_POTION)) {
			cir.setReturnValue(false);
		}
	}
	
}
