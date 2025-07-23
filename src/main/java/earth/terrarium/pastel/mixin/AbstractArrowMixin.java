package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.pastel.entity.entity.GlassArrowEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE",
                                                     target = "Lnet/minecraft/world/entity/projectile/AbstractArrow;isCritArrow()Z"))
    public boolean disableCritParticles(boolean original) {
        if (((Object) this) instanceof GlassArrowEntity)
            return false;
        return original;
    }

    @ModifyExpressionValue(method = "tick", at = @At(value = "INVOKE",
                                                 target = "Lnet/minecraft/world/phys/AABB;contains(Lnet/minecraft/world/phys/Vec3;)Z"))
    public boolean preventHomingGround(boolean original) {
        if ((Object) this instanceof GlassArrowEntity gr)
            return original && !gr.activelyHoming();
        return original;
    }
}
