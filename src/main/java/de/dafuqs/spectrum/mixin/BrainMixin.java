package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import de.dafuqs.spectrum.status_effects.SleepStatusEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Brain.class)
public abstract class BrainMixin<E extends LivingEntity> {

    @Shadow public abstract void setActiveActivityIfPossible(Activity activity);

    @Shadow public abstract <U> void eraseMemory(MemoryModuleType<U> type);

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    public void slowDownBrainTicks(ServerLevel world, E entity, CallbackInfo ci) {
        if (entity.hasEffect(SpectrumStatusEffects.ETERNAL_SLUMBER) || entity.hasEffect(SpectrumStatusEffects.FATAL_SLUMBER)) {
            ci.cancel();
            return;
        }

        var effect = entity.getEffect(SpectrumStatusEffects.SOMNOLENCE);
        if (effect == null)
            return;
        
        var scaling = SleepStatusEffect.getSleepScaling(entity);
        if (scaling <= 0 || entity.getRandom().nextFloat() > Math.min(scaling * 0.05, 0.3))
            return;
        
        if (entity.getRandom().nextFloat() < scaling * 0.5) {
            eraseMemory(MemoryModuleType.ANGRY_AT);
            setActiveActivityIfPossible(Activity.REST);
        }

        ci.cancel();
    }
}
