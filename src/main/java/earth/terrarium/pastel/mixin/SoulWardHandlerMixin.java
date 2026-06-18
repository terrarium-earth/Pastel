package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.sammy.malum.common.data.attachment.SoulWardData;
import com.sammy.malum.core.handlers.SoulWardHandler;
import earth.terrarium.pastel.registries.PastelDamageTypeTags;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(
    SoulWardHandler.class
)
public class SoulWardHandlerMixin {
    @Inject(
        method = "shieldPlayer", at = @At(
            value = "INVOKE", target = "Lcom/sammy/malum/common/data/attachment/SoulWardData;" + "reduceSoulWard(D)V"
        )
    )
    private static void disruptSoulWard(
        LivingDamageEvent.Pre event,
        CallbackInfo ci,
        @Local(
            name = "soulWardDamage"
        )
        double soulWardDamage
    ) {
        if (event
            .getSource()
            .is(PastelDamageTypeTags.DISRUPTS_WARDS)) soulWardDamage *= 2;
    }

    @WrapOperation(
        method = "shieldPlayer", at = @At(
            value = "INVOKE", target = "Lcom/sammy/malum/common/data/attachment/SoulWardData;" + "addCooldown(Lnet/minecraft/world/entity/LivingEntity;" + "F)V"
        )
    )
    private static void extraCooldownOnDisrupt(
        SoulWardData instance,
        LivingEntity living,
        float multiplier,
        Operation<Void> original,
        LivingDamageEvent.Pre event
    ) {
        original
            .call(
                instance,
                living,
                multiplier * (event
                    .getSource()
                    .is(PastelDamageTypeTags.DISRUPTS_WARDS) ? 2 : 1)
            );
    }
}
