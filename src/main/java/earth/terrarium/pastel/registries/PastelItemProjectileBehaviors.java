package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.interaction.projectile_behavior.*;

public class PastelItemProjectileBehaviors {
	
	public static void register() {
		ItemProjectileBehaviorRegistry.register(DefaultProjectileBehavior.TYPE);
		ItemProjectileBehaviorRegistry.register(FlatDamageProjectileBehavior.TYPE);
		ItemProjectileBehaviorRegistry.register(MusicDiscProjectileBehavior.TYPE);
	}
}