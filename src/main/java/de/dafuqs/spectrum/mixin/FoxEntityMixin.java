package de.dafuqs.spectrum.mixin;

import com.llamalad7.mixinextras.injector.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(FoxEntity.class)
public class FoxEntityMixin {

    @ModifyReturnValue(method = "isSleeping()Z", at = @At("RETURN"))
    public boolean spectrum$forceFoxSleepingState(boolean original) {
        if (original)
            return true;

        var entity = (LivingEntity) (Object) this;
        return entity.hasStatusEffect(SpectrumStatusEffects.ETERNAL_SLUMBER) || entity.hasStatusEffect(SpectrumStatusEffects.FATAL_SLUMBER);
    }
}
