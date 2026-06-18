package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import earth.terrarium.pastel.entity.entity.BidentBaseEntity;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(
    ThrownTrident.class
)
public abstract class TridentEntityMixin extends AbstractArrow {

    protected TridentEntityMixin(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }

    @WrapOperation(
        method = "onHitEntity", at = @At(
            value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;hurt" + "(Lnet/minecraft/world/damagesource/DamageSource;F)Z"
        )
    )
    private boolean makeBidentDamageReasonable(
        Entity instance,
        DamageSource source,
        float amount,
        Operation<Boolean> original
    ) {
        if (((Object) this) instanceof BidentBaseEntity bidentEntity) {
            var stack = bidentEntity.getTrackedStack();
            float damage = (float) getDamage(stack);

            DamageSource damageSource = PastelDamageTypes.impaling(level(), bidentEntity, getOwner());
            if (this.level() instanceof ServerLevel serverWorld) {
                damage += EnchantmentHelper
                    .modifyDamage(
                        serverWorld,
                        this.getWeaponItem(),
                        instance,
                        damageSource,
                        damage
                    );
            }

            return instance.hurt(damageSource, damage * 2);
        }
        return original.call(instance, source, amount);
    }

    @Unique private double getDamage(ItemStack stack) {
        // TODO: is that correct?
        ItemAttributeModifiers attributeModifiersComponent = stack
            .getOrDefault(
                DataComponents.ATTRIBUTE_MODIFIERS,
                ItemAttributeModifiers.EMPTY
            );
        return attributeModifiersComponent.compute(1.0D, EquipmentSlot.MAINHAND);
    }

}
