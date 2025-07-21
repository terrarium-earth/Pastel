package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.registries.PastelMobEffects;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.StringUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MobEffectUtil.class)
public class StatusEffectUtilMixin {

    @WrapOperation(method = "formatDuration", at = @At(value = "INVOKE",
                                                       target = "Lnet/minecraft/network/chat/Component;literal" +
                                                                "(Ljava/lang/String;)" +
                                                                "Lnet/minecraft/network/chat/MutableComponent;"))
    private static MutableComponent modifyDurationText(
        String string, Operation<MutableComponent> original, @Local int i,
        @Local(argsOnly = true) MobEffectInstance effect, @Local(argsOnly = true, ordinal = 0) float multiplier,
        @Local(argsOnly = true, ordinal = 1) float tickRate
    ) {
        if (effect.getEffect() == PastelMobEffects.ETERNAL_SLUMBER) {
            return Component.translatable(
                "effect.pastel.eternal_slumber.duration", StringUtil.formatTickDuration(i, tickRate));
        }
        return original.call(string);
    }

    @WrapOperation(method = "formatDuration", at = @At(value = "INVOKE",
                                                       target = "Lnet/minecraft/network/chat/Component;translatable" +
                                                                "(Ljava/lang/String;)" +
                                                                "Lnet/minecraft/network/chat/MutableComponent;"))
    private static MutableComponent modifyDurationTextInfinite(
        String string, Operation<MutableComponent> original, @Local(argsOnly = true) MobEffectInstance effect) {
        if (effect.getEffect() == PastelMobEffects.ETERNAL_SLUMBER) {
            return Component.translatable("effect.pastel.eternal_slumber.duration_inf");
        }
        return original.call(string);
    }
}
