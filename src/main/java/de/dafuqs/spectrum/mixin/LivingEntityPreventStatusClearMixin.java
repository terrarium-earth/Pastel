package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.v2.*;
import com.llamalad7.mixinextras.injector.wrapoperation.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.*;
import net.minecraft.network.packet.s2c.play.*;
import net.minecraft.registry.entry.*;
import net.minecraft.server.world.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import java.util.*;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityPreventStatusClearMixin {
	
	@Shadow
	public abstract void remove(Entity.RemovalReason reason);
	
	@Shadow
	public abstract boolean addStatusEffect(StatusEffectInstance effect);
	
	@Shadow
	public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();
	
	@Inject(method = "clearStatusEffects", at = @At("HEAD"))
	private void spectrum$detectFatalSlumber(CallbackInfoReturnable<Boolean> cir, @Share("hasFatalSlumber") LocalBooleanRef hasFatalSlumber) {
		hasFatalSlumber.set(getActiveStatusEffects().containsKey(SpectrumStatusEffects.FATAL_SLUMBER));
	}
	
	@Inject(method = "clearStatusEffects", at = @At("TAIL"))
	private void spectrum$applyEternalSlumberIfFatalSlumberRemoved(CallbackInfoReturnable<Boolean> cir, @Share("hasFatalSlumber") LocalBooleanRef hasFatalSlumber) {
		if (hasFatalSlumber.get()) {
			addStatusEffect(new StatusEffectInstance(SpectrumStatusEffects.ETERNAL_SLUMBER, 6000));
		}
	}

	@WrapWithCondition(method = "clearStatusEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;onStatusEffectRemoved(Lnet/minecraft/entity/effect/StatusEffectInstance;)V"))
	private boolean spectrum$preventStatusClear(LivingEntity instance, StatusEffectInstance effect, @Share("blockRemoval") LocalBooleanRef blockRemoval) {
		if (StatusEffectHelper.isIncurable(effect)) {
			if (affectedByImmunity(instance, effect.getAmplifier()))
				return true;
			
			SpectrumStatusEffects.cutDuration(instance, effect);
			
			blockRemoval.set(true);
			return false;
		}
		return true;
	}
	
	@WrapWithCondition(method = "clearStatusEffects", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"))
	private boolean spectrum$preventStatusClear2(Iterator instance, @Share("blockRemoval") LocalBooleanRef blockRemoval) {
		if (blockRemoval.get()) {
			blockRemoval.set(false);
			return false;
		}
		return true;
	}
	
	@WrapOperation(method = "removeStatusEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;removeStatusEffectInternal(Lnet/minecraft/registry/entry/RegistryEntry;)Lnet/minecraft/entity/effect/StatusEffectInstance;"))
	private StatusEffectInstance spectrum$preventStatusRemoval(LivingEntity instance, RegistryEntry<StatusEffect> effectRegistryEntry, Operation<StatusEffectInstance> original) {
		var effect = instance.getStatusEffect(effectRegistryEntry);
		boolean cancel;
		
		if (effect == null)
			return original.call(instance, effectRegistryEntry);
		
		cancel = StatusEffectHelper.isIncurable(effect);
		
		if (cancel) {
			cancel = !affectedByImmunity(instance, effect.getAmplifier());
		}
		
		if (cancel)
			return null;
		
		return original.call(instance, effectRegistryEntry);
	}
	
	@Unique
	private static boolean affectedByImmunity(LivingEntity instance, int amplifier) {
		var immunity = instance.getStatusEffect(SpectrumStatusEffects.IMMUNITY);
		var cost = 1200 + 600 * amplifier;
		
		if (immunity != null && immunity.getDuration() >= cost) {
			immunity.spectrum$setDuration(Math.max(5, immunity.getDuration() - cost));
			if (!instance.getWorld().isClient()) {
				((ServerWorld) instance.getWorld()).getChunkManager().sendToNearbyPlayers(instance, new EntityStatusEffectS2CPacket(instance.getId(), immunity, false));
			}
			return true;
		}
		return false;
	}
	
}