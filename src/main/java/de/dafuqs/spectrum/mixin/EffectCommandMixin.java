package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.sugar.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.command.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(EffectCommand.class)
public class EffectCommandMixin {

    @Inject(method = "executeClear(Lnet/minecraft/server/command/ServerCommandSource;Ljava/util/Collection;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;clearStatusEffects()Z"))
    private static void clearIncurableEffects(ServerCommandSource source, Collection<? extends Entity> targets, CallbackInfoReturnable<Integer> cir, @Local Entity target) {
        if (target instanceof LivingEntity living) {
            for (StatusEffectInstance effect : living.getStatusEffects()) {
                if (effect.spectrum$isIncurable())
                    effect.spectrum$setIncurable(false);
            }
        }
    }
	
	@Inject(method = "executeClear(Lnet/minecraft/server/command/ServerCommandSource;Ljava/util/Collection;Lnet/minecraft/registry/entry/RegistryEntry;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;removeStatusEffect(Lnet/minecraft/registry/entry/RegistryEntry;)Z"))
    private static void clearIncurableEffects(ServerCommandSource source, Collection<? extends Entity> targets, RegistryEntry<StatusEffect> statusEffect, CallbackInfoReturnable<Integer> cir, @Local Entity target, @Local StatusEffect ref) {
        if (target instanceof LivingEntity living) {
			var effect = living.getStatusEffect(living.getWorld().getRegistryManager().get(RegistryKeys.STATUS_EFFECT).getEntry(ref));
            if (effect != null) {
                if (effect.spectrum$isIncurable())
                    effect.spectrum$setIncurable(false);
            }
        }
    }
}
