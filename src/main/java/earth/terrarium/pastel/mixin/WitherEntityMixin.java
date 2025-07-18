package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(WitherBoss.class)
public abstract class WitherEntityMixin extends LivingEntity {

    protected WitherEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/ItemEntity;setExtendedLifetime()V"),
            method = "dropCustomDeathLoot", locals = LocalCapture.CAPTURE_FAILSOFT)
    private void spawnEntity(
        ServerLevel world, DamageSource source, boolean causedByPlayer, CallbackInfo ci, ItemEntity itemEntity) {
        Entity attackerEntity = source.getEntity();
        if (attackerEntity instanceof LivingEntity livingAttacker) {
            int cloversFavorLevel = Ench.getLevel(
                world.registryAccess(), PastelEnchantments.CLOVERS_FAVOR, livingAttacker.getMainHandItem());
            if (cloversFavorLevel > 0) {
                int additionalCount = (int) (cloversFavorLevel / 2.0F + world.random.nextFloat() * cloversFavorLevel);
                itemEntity.getItem()
                          .setCount(itemEntity.getItem()
                                              .getCount() + additionalCount);
            }
        }
    }

    @ModifyReturnValue(method = "addEffect", at = @At("TAIL"))
    private boolean allowWitherNaps(
        boolean original, @Local(argsOnly = true) MobEffectInstance effect, @Local(argsOnly = true) Entity source) {
        if (effect.getEffect()
                  .is(PastelMobEffectTags.SOPORIFIC)) {
            return super.addEffect(effect, source);
        }
        return original;
    }
}
