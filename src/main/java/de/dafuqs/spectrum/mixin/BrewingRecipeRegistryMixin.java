package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumPotions;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PotionBrewing.class)
public abstract class BrewingRecipeRegistryMixin {
	
	@Inject(method = "isIngredient", at = @At("HEAD"), cancellable = true)
	private void spectrum$disallowPigmentPotionBrewing(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
		PotionContents potionContentsComponent = stack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
		if (potionContentsComponent.potion().isPresent() && potionContentsComponent.potion().get().equals(SpectrumPotions.PIGMENT_POTION)) {
			cir.setReturnValue(false);
		}
	}
	
}
