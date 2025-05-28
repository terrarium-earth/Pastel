package earth.terrarium.pastel.mixin;

import de.dafuqs.arrowhead.api.ArrowheadCrossbow;
import de.dafuqs.arrowhead.api.CrossbowShootingCallback;
import earth.terrarium.pastel.registries.events.SpectrumMiscEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowItem.class)
public class CrossbowItemMixin {
	
	@ModifyVariable(method = "shootProjectile", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	public float arrowhead$handleCrossbowSpeed(float originalSpeed, LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
		ItemStack activeStack = shooter.getItemInHand(shooter.getUsedItemHand());
		if (activeStack.getItem() instanceof ArrowheadCrossbow arrowheadCrossbow) {
			originalSpeed *= arrowheadCrossbow.getProjectileVelocityModifier(activeStack);
		}
		return originalSpeed;
	}
	
	@ModifyVariable(method = "shootProjectile", at = @At("HEAD"), ordinal = 1, argsOnly = true)
	public float arrowhead$handleCrossbowDivergence(float originalDivergence, LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw, @Nullable LivingEntity target) {
		ItemStack activeStack = shooter.getItemInHand(shooter.getUsedItemHand());
		if (activeStack.getItem() instanceof ArrowheadCrossbow arrowheadCrossbow) {
			originalDivergence *= arrowheadCrossbow.getDivergenceMod(activeStack);
		}
		return originalDivergence;
	}
	
	@Inject(method = "shootProjectile", at = @At(value = "TAIL"))
	public void arrowhead$crossbowCallbacks(LivingEntity shooter, Projectile projectile, int index, float speed, float divergence, float yaw, LivingEntity target, CallbackInfo ci) {
		SpectrumMiscEvents.onCrossbowShot(shooter, projectile);
	}
	
}
