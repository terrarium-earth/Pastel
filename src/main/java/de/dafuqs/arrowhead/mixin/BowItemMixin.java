package de.dafuqs.arrowhead.mixin;

import de.dafuqs.arrowhead.api.ArrowheadBow;
import de.dafuqs.arrowhead.api.BowShootingCallback;
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
	public void arrowhead$handleRangedWeapon(LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw, LivingEntity target, CallbackInfo ci) {
		ItemStack activeStack = shooter.getUseItem();

		if (activeStack.getItem() instanceof ArrowheadBow arrowheadBow) {
			projectile.shootFromRotation(shooter, shooter.getXRot(), shooter.getYRot() + yaw, 0.0F, speed * arrowheadBow.getProjectileVelocityModifier(activeStack), divergence * arrowheadBow.getDivergenceMod(activeStack));
		}

		for(BowShootingCallback callback : BowShootingCallback.callbacks) {
			callback.trigger(shooter.level(), shooter, activeStack, activeStack.getUseDuration(shooter) - shooter.getUseItemRemainingTicks(), projectile);
		}
	}
	
}