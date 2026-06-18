package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.pastel.entity.entity.GlassArrowEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ProjectileWeaponItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
    ProjectileWeaponItem.class
)
public class ProjectileWeaponItemMixin {

    @WrapOperation(
        method = "shoot", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/item/ProjectileWeaponItem;shootProjectile(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/projectile/Projectile;IFFFLnet/minecraft/world/entity/LivingEntity;)V"
        )
    )
    private void cancelInaccuracy(
        ProjectileWeaponItem instance,
        LivingEntity shooter,
        Projectile projectile,
        int i,
        float velocity,
        float inaccuracy,
        float angle,
        LivingEntity target,
        Operation<Void> original
    ) {
        if (projectile instanceof GlassArrowEntity arrow) {
            var finalV = velocity * arrow.getVariant().getAttributes().speed();
            original.call(instance, shooter, projectile, i, finalV, 0F, angle, target);
            return;
        }
        original.call(instance, shooter, projectile, i, velocity, inaccuracy, angle, target);
    }
}
