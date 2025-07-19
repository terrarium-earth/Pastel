package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.registries.PastelDamageTypeTags;
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

    @Inject(method = "test(Lnet/minecraft/world/level/storage/loot/LootContext;)Z", at = @At(value = "RETURN"),
            cancellable = true)
    private void testDropPlayerLoot(LootContext lootContext, CallbackInfoReturnable<Boolean> cir) {
        if (!cir.getReturnValue()) {
            DamageSource damageSource = lootContext.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
            if (damageSource != null && damageSource.is(PastelDamageTypeTags.DROPS_LOOT_LIKE_PLAYERS)) {
                cir.setReturnValue(true);
            }
        }
    }

}
