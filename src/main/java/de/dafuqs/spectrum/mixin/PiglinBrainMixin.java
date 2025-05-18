package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumItemTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public abstract class PiglinBrainMixin {
	
	@Inject(at = @At("HEAD"), method = "isWearingGold", cancellable = true)
	private static void spectrum$piglinSafeEquipment(LivingEntity entity, CallbackInfoReturnable<Boolean> cir) {
		for (ItemStack itemStack : entity.getArmorSlots()) {
			if (itemStack.is(SpectrumItemTags.PIGLIN_SAFE_EQUIPMENT)) {
				cir.setReturnValue(true);
				break;
			}
		}
	}
	
}