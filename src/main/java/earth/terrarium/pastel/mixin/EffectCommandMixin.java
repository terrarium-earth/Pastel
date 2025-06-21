package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.helpers.MobEffectHelper;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.commands.EffectCommands;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(EffectCommands.class)
public class EffectCommandMixin {

    @Inject(method = "clearEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeAllEffects()Z"))
    private static void clearIncurableEffects(CommandSourceStack source, Collection<? extends Entity> targets, CallbackInfoReturnable<Integer> cir, @Local Entity target) {
        if (target instanceof LivingEntity living) {
            for (MobEffectInstance effect : living.getActiveEffects()) {
                if (MobEffectHelper.resistsRemoval(effect))
                    effect.getCures().remove(PastelMobEffects.Cures.INCURABLE);
            }
        }
    }
	
	@Inject(method = "clearEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;removeEffect(Lnet/minecraft/core/Holder;)Z"))
    private static void clearIncurableEffects(CommandSourceStack source, Collection<? extends Entity> targets, Holder<MobEffect> statusEffect, CallbackInfoReturnable<Integer> cir, @Local Entity target, @Local MobEffect ref) {
        if (target instanceof LivingEntity living) {
			var effect = living.getEffect(living.level().registryAccess().registryOrThrow(Registries.MOB_EFFECT).wrapAsHolder(ref));
            if (effect != null) {
                if (MobEffectHelper.resistsRemoval(effect))
                    effect.getCures().remove(PastelMobEffects.Cures.INCURABLE);
            }
        }
    }
}
