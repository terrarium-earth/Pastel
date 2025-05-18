package de.dafuqs.arrowhead.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public interface BowShootingCallback {
	
	List<BowShootingCallback> callbacks = new ArrayList<>();
	
	/**
	 * Fires after the projectile has gotten its initial velocity set and before vanilla enchantments are run
	 * Only triggers serverside
	 * @param world the world
	 * @param shooter the shooter that shot the bow
	 * @param weaponStack the bow stack
	 * @param remainingUseTicks the remaining use time of the bow at the time of release
	 * @param projectile the projectile that was shot (initialized, but not yet spawned in the world)
	 */
	void trigger(Level world, LivingEntity shooter, ItemStack weaponStack, int remainingUseTicks, Projectile projectile);
	
	/**
	 * Register a ProjectileLaunchCallback
	 * It will now receive trigger events
	 * @param callback the callback to register
	 */
	static void register(BowShootingCallback callback) {
		callbacks.add(callback);
	}
	
	/**
	 * Unregister a ProjectileLaunchCallback
	 * It will not receive trigger events anymore
	 * @param callback the callback to unregister
	 */
	static void unregister(BowShootingCallback callback) {
		callbacks.remove(callback);
	}
	
}