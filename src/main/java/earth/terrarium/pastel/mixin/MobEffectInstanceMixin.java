package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.helpers.level.MobEffectHelper;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(
    MobEffectInstance.class
)
public abstract class MobEffectInstanceMixin implements MobEffectInstanceInjector {

    @Shadow
    public int duration;

    @Shadow
    public int amplifier;

    @Inject(
        method = "update", at = @At(
            "HEAD"
        ), cancellable = true
    )
    private void stackableEffects(MobEffectInstance newEffect, CallbackInfoReturnable<Boolean> cir) {
        Holder<MobEffect> effectType = newEffect.getEffect();
        if (effectType.is(PastelMobEffectTags.STACKING)) {
            PastelMobEffects.effectsAreGettingStacked = true;
            MobEffectInstance existingInstance = (MobEffectInstance) (Object) this;

            int newAmplifier = 1 + existingInstance.getAmplifier() + newEffect.getAmplifier();
            ((MobEffectInstanceInjector) existingInstance).setAmplifier(newAmplifier);

            cir.setReturnValue(true);
        }
        PastelMobEffects.effectsAreGettingStacked = false;
    }

    @ModifyReturnValue(
        method = "save", at = @At(
            "RETURN"
        )
    )
    public Tag saveIncurable(Tag original) {
        ((CompoundTag) original)
            .putBoolean(
                "Incurable",
                MobEffectHelper.resistsRemoval(MobEffectInstance.class.cast(this))
            );
        return original;
    }

    @ModifyReturnValue(
        method = "load", at = @At(
            "RETURN"
        )
    )
    private static MobEffectInstance loadIncurable(MobEffectInstance original, @Local
    CompoundTag nbt) {
        if (nbt.getBoolean("Incurable")) {
            original
                .getCures()
                .add(PastelMobEffects.Cures.INCURABLE);
        }

        return original;
    }

    @Override
    public void setDuration(int newDuration) {
        this.duration = newDuration;
    }

    @Override
    public void setAmplifier(int newAmplifier) {
        this.amplifier = newAmplifier;
    }

}
