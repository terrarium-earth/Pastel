package earth.terrarium.pastel.registries;

import earth.terrarium.pastel.api.interaction.projectile_behavior.DefaultProjectileBehavior;
import earth.terrarium.pastel.api.interaction.projectile_behavior.FlatDamageProjectileBehavior;
import earth.terrarium.pastel.api.interaction.projectile_behavior.ItemProjectileBehaviorRegistry;
import earth.terrarium.pastel.api.interaction.projectile_behavior.MusicDiscProjectileBehavior;

public class PastelItemProjectileBehaviors {

    public static void register() {
        ItemProjectileBehaviorRegistry.register(DefaultProjectileBehavior.TYPE);
        ItemProjectileBehaviorRegistry.register(FlatDamageProjectileBehavior.TYPE);
        ItemProjectileBehaviorRegistry.register(MusicDiscProjectileBehavior.TYPE);
    }
}
