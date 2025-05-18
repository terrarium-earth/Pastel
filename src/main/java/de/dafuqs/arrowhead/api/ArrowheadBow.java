package de.dafuqs.arrowhead.api;

import net.minecraft.world.item.ItemStack;

/**
 * Implement this interface to your BowItem to get working pulling animations equal to a vanilla bow
 * Additionally you can individualize a few properties to match your liking
 */
public interface ArrowheadBow {
	
	/**
	 * The higher this value, the more does the player's view zoom in when using
	 * The normal bow has a zoom of 20
	 */
	default float getZoom(ItemStack stack) {
		return 20F;
	}
	
	/**
	 * The higher this value, the more velocity the shot projectile gets
	 * Note that this directly relates to damage with most projectiles, like arrows
	 * The normal bow equals a velocity mod of 1.0
	 */
	default float getProjectileVelocityModifier(ItemStack stack) {
		return 1.0F;
	}
	
	/**
	 * The lower this value, the more precise projectiles become
	 * The normal bow equals a divergence mod of 1.0
	 */
	default float getDivergenceMod(ItemStack stack) {
		return 1.0F;
	}
	
}
