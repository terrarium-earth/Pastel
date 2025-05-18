package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import de.dafuqs.spectrum.status_effects.SleepStatusEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobEntityMixin {

    @Shadow private @Nullable LivingEntity target;

    @Inject(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;level()Lnet/minecraft/world/level/Level;", ordinal = 0), cancellable = true)
    public void slowDownAIticks(CallbackInfo ci) {
        var entity = (Mob) (Object) this;

        if ((entity.hasEffect(SpectrumStatusEffects.ETERNAL_SLUMBER) || entity.hasEffect(SpectrumStatusEffects.FATAL_SLUMBER)) && !SleepStatusEffect.isImmuneish(entity)) {
            target = null;
            ci.cancel();
            return;
        }
        
        var potency = SleepStatusEffect.getSleepScaling(entity);

        if (potency <= 0 || entity.getRandom().nextFloat() > potency * 0.15)
            return;

        if (entity.getRandom().nextFloat() < potency * 0.75)
            target = null;

        ci.cancel();
    }
}
