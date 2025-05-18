package de.dafuqs.arrowhead.api;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public interface CrossbowShootingCallback {
	
	List<CrossbowShootingCallback> callbacks = new ArrayList<>();
	
	/**
	 * Fires after the projectile has gotten its initial velocity set and before vanilla enchantments are run
	 * Only triggers serverside
	 * @param world the world
	 * @param shooter the LivingEntity that shot the crossbow
	 * @param crossbow the crossbow stack
	 * @param projectileEntity the projectile that was shot (initialized, but not yet spawned in the world)
	 */
	void trigger(Level world, LivingEntity shooter, ItemStack crossbow, Projectile projectileEntity);
	
	/**
	 * Register a ProjectileLaunchCallback
	 * It will now receive trigger events
	 * @param callback the callback to register
	 */
	static void register(CrossbowShootingCallback callback) {
		callbacks.add(callback);
	}
	
	/**
	 * Unregister a ProjectileLaunchCallback
	 * It will not receive trigger events anymore
	 * @param callback the callback to unregister
	 */
	static void unregister(CrossbowShootingCallback callback) {
		callbacks.remove(callback);
	}
	
}