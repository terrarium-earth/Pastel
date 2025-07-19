package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.registries.PastelDamageTypeTags;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CombatRules.class)
public class CombatRulesMixin {

    @Inject(method = "getDamageAfterAbsorb", at = @At("HEAD"))
    private static void modifyArmorAndToughness(LivingEntity entity, float damage, DamageSource source, float armorValue, float armorToughness, CallbackInfoReturnable<Float> cir) {
        if (source.is(PastelDamageTypeTags.CALCULATES_DAMAGE_BASED_ON_TOUGHNESS)) {
            armorValue = armorToughness * 1.334F;
            armorToughness = Float.MAX_VALUE;
        }
        if (source.is(PastelDamageTypeTags.PARTLY_IGNORES_PROTECTION)) {
            armorValue /= 2F;
        }
    }
}
