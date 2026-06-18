package earth.terrarium.pastel.mixin;

import com.sammy.malum.common.data.attachment.SoulWardData;
import earth.terrarium.pastel.entity.entity.DarkStakeEntity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(
    SoulWardData.class
)
public abstract class SoulWardDataMixin {
    @Shadow
    public abstract void addCooldown(LivingEntity living, float multiplier);

    @Inject(
        method = "recoverSoulWard", at = @At(
            "HEAD"
        ), cancellable = true
    )
    private void soulWardDisruptedByStakes(LivingEntity entity, double amount, CallbackInfo ci) {
        if (entity
            .level()
            .getRandom()
            .nextBoolean() && !entity
                .level()
                .getEntitiesOfClass(
                    DarkStakeEntity.class,
                    entity
                        .getBoundingBox()
                        .inflate(DarkStakeEntity.EFFECT_RADIUS)
                )
                .isEmpty()) {
            this.addCooldown(entity, 1.0F);
            ci.cancel();
        }
    }
}
