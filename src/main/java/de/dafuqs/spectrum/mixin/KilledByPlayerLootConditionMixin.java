package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumDamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemKilledByPlayerCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LootItemKilledByPlayerCondition.class)
public abstract class KilledByPlayerLootConditionMixin {
	
	@Inject(method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", at = @At(value = "RETURN"), cancellable = true)
	private void spectrum$testDropPlayerLoot(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
		if (!cir.getReturnValue()) {
			DamageSource damageSource = lootContext.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
			if (damageSource != null && damageSource.is(SpectrumDamageTypeTags.DROPS_LOOT_LIKE_PLAYERS)) {
				cir.setReturnValue(true);
			}
		}
	}
	
}
