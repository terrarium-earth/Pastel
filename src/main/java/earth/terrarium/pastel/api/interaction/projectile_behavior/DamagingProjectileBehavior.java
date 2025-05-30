package earth.terrarium.pastel.api.interaction.projectile_behavior;

import earth.terrarium.pastel.entity.entity.*;
import earth.terrarium.pastel.registries.*;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.entity.projectile.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.*;
import net.minecraft.world.phys.*;

public interface DamagingProjectileBehavior extends ItemProjectileBehavior {
	@Override
	default ItemStack onEntityHit(ItemProjectileEntity projectile, ItemStack stack, Entity owner, EntityHitResult hitResult) {
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
					EnchantmentHelper.doPostAttackEffectsWithItemSource(serverWorld, target, livingTarget.getLastDamageSource(), stack);
				}
				if (target != owner && target instanceof Player && owner instanceof ServerPlayer serverPlayerOwner && !projectile.isSilent()) {
					serverPlayerOwner.connection.send(new ClientboundGameEventPacket(ClientboundGameEventPacket.ARROW_HIT_PLAYER, 0.0F));
				}
				projectile.playSound(SpectrumSoundEvents.BLOCK_CITRINE_CLUSTER_HIT, 1.0F, 1.2F / (projectile.level().getRandom().nextFloat() * 0.2F + 0.9F));
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
	default ItemStack onBlockHit(ItemProjectileEntity projectile, ItemStack stack, Entity owner, BlockHitResult hitResult) {
		if (destroyItemOnHit()) {
			stack.shrink(1);
		}
		return stack;
	}
}
