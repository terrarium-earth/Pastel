package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowItem.class)
public class BowItemMixin {

    @Inject(method = "shootProjectile", at = @At(value = "HEAD"))
    public void arrowhead$handleRangedWeapon(
        LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw,
        LivingEntity target, CallbackInfo ci
    ) {
        ItemStack activeStack = shooter.getUseItem();

        if (activeStack.getItem() == PastelItems.BEDROCK_BOW.get()) {
            projectile.shootFromRotation(
                shooter, shooter.getXRot(), shooter.getYRot() + yaw, 0.0F, speed * 1.3F, divergence * 0.8F);
        }
    }
}
