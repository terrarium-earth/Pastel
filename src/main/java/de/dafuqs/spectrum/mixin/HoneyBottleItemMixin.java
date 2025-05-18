package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.HoneyBottleItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HoneyBottleItem.class)
public class HoneyBottleItemMixin {
	
	@Inject(method = "finishUsingItem", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeEffect(Lnet/minecraft/core/Holder;)Z"))
	private void spectrum$cureDeadlyPoison(ItemStack stack, Level world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir) {
		user.removeEffect(SpectrumStatusEffects.DEADLY_POISON);
	}
}
