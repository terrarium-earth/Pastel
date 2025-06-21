package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Fox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Fox.class)
public class FoxEntityMixin {

    @ModifyReturnValue(method = "isSleeping()Z", at = @At("RETURN"))
    public boolean forceFoxSleepingState(boolean original) {
        if (original)
            return true;

        var entity = (LivingEntity) (Object) this;
        return entity.hasEffect(PastelStatusEffects.ETERNAL_SLUMBER) || entity.hasEffect(PastelStatusEffects.FATAL_SLUMBER);
    }
}
