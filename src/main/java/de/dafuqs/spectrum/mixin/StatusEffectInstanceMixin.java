package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import de.dafuqs.spectrum.injectors.StatusEffectInstanceInjector;
import de.dafuqs.spectrum.registries.SpectrumStatusEffectTags;
import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEffectInstance.class)
public abstract class StatusEffectInstanceMixin implements StatusEffectInstanceInjector {
	
	@Shadow
	public int duration;
	@Shadow
	public int amplifier;
	@Unique
	private boolean incurable;
	
	@ModifyExpressionValue(method = "<clinit>", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/codecs/RecordCodecBuilder;create(Ljava/util/function/Function;)Lcom/mojang/serialization/Codec;", remap = false))
	private static Codec<MobEffectInstance> wrapCodec(Codec<MobEffectInstance> original) {
		return original.mapResult(new Codec.ResultFunction<>() {
			@Override
			public <T> DataResult<Pair<MobEffectInstance, T>> apply(DynamicOps<T> ops, T input, DataResult<Pair<MobEffectInstance, T>> result) {
				return result.map(pair -> {
					ops.get(input, "incurable").flatMap(ops::getBooleanValue).ifSuccess(v -> pair.getFirst().spectrum$setIncurable(v));
					return pair;
				});
			}
			
			@Override
			public <T> DataResult<T> coApply(DynamicOps<T> ops, MobEffectInstance inst, DataResult<T> result) {
				return result.map(output -> ops.set(output, "incurable", ops.createBoolean(inst.spectrum$isIncurable())));
			}
		});
	}
	
	@Inject(method = "update", at = @At("HEAD"), cancellable = true)
	private void spectrum$stackableEffects(MobEffectInstance newEffect, CallbackInfoReturnable<Boolean> cir) {
		Holder<MobEffect> effectType = newEffect.getEffect();
		if (effectType.is(SpectrumStatusEffectTags.STACKING)) {
			SpectrumStatusEffects.effectsAreGettingStacked = true;
			MobEffectInstance existingInstance = (MobEffectInstance) (Object) this;
			
			int newAmplifier = 1 + existingInstance.getAmplifier() + newEffect.getAmplifier();
			existingInstance.spectrum$setAmplifier(newAmplifier);
			
			cir.setReturnValue(true);
		}
		SpectrumStatusEffects.effectsAreGettingStacked = false;
	}
	
	@Inject(method = "update", at = @At("RETURN"))
	private void readIncurable(MobEffectInstance that, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 0) LocalBooleanRef changed) {
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
