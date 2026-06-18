package earth.terrarium.pastel.api.interaction.projectile_behavior;

import com.mojang.serialization.Codec;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.entity.entity.ItemProjectileEntity;
import earth.terrarium.pastel.registries.PastelDataMaps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

// Todo: Convert to Registry
public interface ItemProjectileBehavior {
    Map<ResourceLocation, ItemProjectileBehavior> CUSTOM_BEHAVIORS = new HashMap<>();

    Codec<ItemProjectileBehavior> CODEC = ResourceLocation.CODEC
        .xmap(
            CUSTOM_BEHAVIORS::get,
            behavior -> {
                for (
                    Map.Entry<ResourceLocation, ItemProjectileBehavior> entry : CUSTOM_BEHAVIORS.entrySet()
                ) {
                    if (entry.getValue() == behavior) {
                        return entry.getKey();
                    }
                }
                return PastelCommon.ofPastel("default");
            }
        );

    static ItemProjectileBehavior get(ItemStack stack) {
        ItemProjectileBehavior behavior = stack
            .getItemHolder()
            .getData(PastelDataMaps.PROJECTILE_BEHAVIOR);
        return behavior == null ? DefaultProjectileBehavior.INSTANCE : behavior;
    }

    /**
     * Invoked when the projectile hits an entity.
     *
     * @param projectile The ItemProjectile
     * @param stack      The stack contained in the ItemProjectile. Quick access to projectile.getStack()
     * @param owner      The owner of the projectile
     * @param hitResult  The EntityHitResult. Contains the entity hit and position
     * @return The stack that should be dropped. If the stack has a count > 0, it automatically gets dropped at the
     *         position of the impact. If the item should get consumed, decrement the stack from the parameters and
     *         return it
     *         here
     */
    ItemStack onEntityHit(
        ItemProjectileEntity projectile,
        ItemStack stack,
        @Nullable Entity owner,
        EntityHitResult hitResult
    );

    /**
     * Invoked when the projectile hits a block
     *
     * @param projectile The ItemProjectile
     * @param stack      The stack contained in the ItemProjectile. Quick access to projectile.getStack()
     * @param owner      The owner of the projectile
     * @param hitResult  The EntityHitResult. Contains the entity hit and position
     * @return The stack that should be dropped. If the stack has a count > 0, it automatically gets dropped at the
     *         position of the impact. If the item should get consumed, decrement the stack from the parameters and
     *         return it
     *         here
     */
    ItemStack onBlockHit(
        ItemProjectileEntity projectile,
        ItemStack stack,
        @Nullable Entity owner,
        BlockHitResult hitResult
    );

    /**
     * Projectile behavior type used for serialization to and from json
     *
     * @return The type of the projectile behavior, used for serialization and deserialization
     */
    ProjectileBehaviorType<?> type();
}
