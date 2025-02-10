package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import com.llamalad7.mixinextras.sugar.*;
import com.llamalad7.mixinextras.sugar.ref.*;
import com.mojang.datafixers.util.*;
import com.mojang.serialization.*;
import de.dafuqs.spectrum.injectors.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.effect.*;
import net.minecraft.registry.entry.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

@Mixin(StatusEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements StatusEffectInstanceInjector {
	
	@Shadow
	public int duration;
	@Shadow
	public int amplifier;
	@Unique
	private boolean incurable;
	
	@ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/RecordCodecBuilder;create(Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;", remap = false))
	private static Codec<StatusEffectInstance> wrapCodec(Codec<StatusEffectInstance> original) {
		return original.mapResult(new Codec.ResultFunction<>() {
			@Override
			public <T> DataResult<Pair<StatusEffectInstance, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<StatusEffectInstance, T>> result) {
				return result.map(pair -> {
					ops.get(input, "incurable").flatMap(ops::getBooleanValue).ifSuccess(v -> pair.getFirst().spectrum$setIncurable(v));
					return pair;
				});
			}
			
			@Override
			public <T> DataResult<T> coApply(DynamicOps<T> ops, StatusEffectInstance inst, DataResult<T> result) {
				return result.map(output -> ops.set(output, "incurable", ops.createBoolean(inst.spectrum$isIncurable())));
			}
		});
	}
	
	@Inject(method = "upgrade", at = @At("HEAD"), cancellable = true)
	private void spectrum$stackableEffects(StatusEffectInstance newEffect, CallbackInfoReturnable<Boolean> cir) {
		RegistryEntry<StatusEffect> effectType = newEffect.getEffectType();
		if (effectType.isIn(SpectrumStatusEffectTags.STACKING)) {
			SpectrumStatusEffects.effectsAreGettingStacked = true;
			StatusEffectInstance existingInstance = (StatusEffectInstance) (Object) this;
			
			int newAmplifier = 1 + existingInstance.getAmplifier() + newEffect.getAmplifier();
			existingInstance.spectrum$setAmplifier(newAmplifier);
			
			cir.setReturnValue(true);
		}
		SpectrumStatusEffects.effectsAreGettingStacked = false;
	}
	
	@Inject(method = "upgrade", at = @At("RETURN"))
	private void readIncurable(StatusEffectInstance that, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) LocalBooleanRef changed) {
		if (incurable != that.spectrum$isIncurable()) {
			spectrum$setIncurable(that.spectrum$isIncurable());
			changed.set(true);
		}
	}
	
	@Override
	public boolean spectrum$isIncurable() {
		return incurable;
	}
	
	@Override
	public void spectrum$setIncurable(boolean incurable) {
		this.incurable = incurable;
	}
	
	@Override
	public void spectrum$setDuration(int newDuration) {
		this.duration = newDuration;
	}
	
	@Override
	public void spectrum$setAmplifier(int newAmplifier) {
		this.amplifier = newAmplifier;
	}
	
}
