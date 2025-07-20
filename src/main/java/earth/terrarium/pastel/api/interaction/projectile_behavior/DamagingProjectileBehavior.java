package earth.terrarium.pastel.api.interaction.projectile_behavior;

import earth.terrarium.pastel.entity.entity.ItemProjectileEntity;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public interface DamagingProjectileBehavior extends ItemProjectileBehavior {
    @Override
    default ItemStack onEntityHit(
        ItemProjectileEntity projectile, ItemStack stack, Entity owner, EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();

        if (owner instanceof LivingEntity livingOwner) {
            livingOwner.setLastHurtMob(target);
        }

        if (dealDamage(projectile, owner, target)) {
            int targetFireTicks = target.getRemainingFireTicks();
            if (projectile.isOnFire()) {
                target.setRemainingFireTicks(targetFireTicks);
            }

            if (target instanceof LivingEntity livingTarget) {
                if (owner.level() instanceof ServerLevel serverWorld) {
                    EnchantmentHelper.doPostAttackEffectsWithItemSource(
                        serverWorld, target, livingTarget.getLastDamageSource(), stack);
                }
                if (target != owner && target instanceof Player && owner instanceof ServerPlayer serverPlayerOwner &&
                    !projectile.isSilent()) {
                    serverPlayerOwner.connection.send(
                        new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
                }
                projectile.playSound(
                    PastelSoundEvents.BLOCK_CITRINE_CLUSTER_HIT, 1.0F, 1.2F / (projectile.level()
                                                                                         .getRandom()
                                                                                         .nextFloat() * 0.2F + 0.9F)
                );
            }
        }

        if (destroyItemOnHit()) {
            stack.shrink(1);
        }
        return stack;
    }

    boolean destroyItemOnHit();

    boolean dealDamage(ThrowableItemProjectile projectile, Entity owner, Entity target);

    @Override
    default ItemStack onBlockHit(
        ItemProjectileEntity projectile, ItemStack stack, Entity owner, BlockHitResult hitResult) {
        if (destroyItemOnHit()) {
            stack.shrink(1);
        }
        return stack;
    }
}
