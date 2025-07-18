package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.*;
import earth.terrarium.pastel.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.projectile.*;

public record FlatDamageProjectileBehavior(boolean destroyItemOnHit, float damage)
    implements DamagingProjectileBehavior {
    public static final ProjectileBehaviorType<FlatDamageProjectileBehavior> TYPE = new ProjectileBehaviorType<>(
        PastelCommon.ofPastel("damage"),
        RecordCodecBuilder.mapCodec(instance -> instance.group(
                                                            Codec.BOOL.fieldOf("destroy_item_on_hit")
                                                                      .forGetter(FlatDamageProjectileBehavior::destroyItemOnHit),
                                                            Codec.FLOAT.fieldOf("damage")
                                                                       .forGetter(FlatDamageProjectileBehavior::damage)
                                                        )
                                                        .apply(instance, FlatDamageProjectileBehavior::new))
    );

    @Override
    public boolean dealDamage(ThrowableItemProjectile projectile, Entity owner, Entity target) {
        return target.hurt(
            target.damageSources()
                  .thrown(projectile, owner), damage
        );
    }

    @Override
    public ProjectileBehaviorType<?> type() {
        return TYPE;
    }
}
