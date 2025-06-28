package earth.terrarium.pastel.items;

import net.minecraft.world.item.ItemStack;

/**
 * Implement this interface to your CrossbowItem to get working reloading/shooting animations equal to a vanilla crossbow
 * Additionally you can individualize a few properties to match your liking
 */
@Deprecated(forRemoval = true)
public interface ArrowheadCrossbow {

	/**
	 * The higher this value, the more velocity the shot projectile gets
	 * Note that this directly relates to damage with most projectiles, like arrows
	 * The normal crossbow equals a velocity mod of 1.0
	 */
	default float getProjectileVelocityModifier(ItemStack stack) {
		return 1.0F;
	}

	/**
	 * The lower this value, the more precise projectiles become
	 * The normal crossbow equals a divergence of 1.0
	 */
	default float getDivergenceMod(ItemStack stack) {
		return 1.0F;
	}
	
}
