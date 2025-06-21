package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import earth.terrarium.pastel.attachments.data.LastKillData;
import earth.terrarium.pastel.attachments.data.PrimordialFireData;
import earth.terrarium.pastel.helpers.enchantments.InexorableHelper;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import earth.terrarium.pastel.status_effects.FrenzyStatusEffect;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
	
	@Inject(method = "killedEntity", at = @At("HEAD"))
	private void rememberKillOther(ServerLevel world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
		Entity entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity livingEntity) {
			LastKillData.rememberKillTick(livingEntity, livingEntity.level().getGameTime());
			
			MobEffectInstance frenzy = livingEntity.getEffect(PastelStatusEffects.FRENZY);
			if (frenzy != null) {
				((FrenzyStatusEffect) frenzy.getEffect()).onKill(livingEntity, frenzy.getAmplifier());
			}
		}
	}
	
	@ModifyVariable(method = "makeStuckInBlock", at = @At(value = "LOAD"), argsOnly = true)
	private Vec3 applyInexorableAntiBlockSlowdown(Vec3 multiplier) {
		var entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity livingEntity && InexorableHelper.isArmorActive(livingEntity)) {
			return Vec3.ZERO;
		}
		return multiplier;
	}
	
	@Inject(method = "getBlockSpeedFactor", at = @At("RETURN"), cancellable = true)
	private void applyInexorableAntiSlowdown(CallbackInfoReturnable<Float> cir) {
		var entity = (Entity) (Object) this;
		if (entity instanceof LivingEntity livingEntity && InexorableHelper.isArmorActive(livingEntity)) {
			cir.setReturnValue(Math.max(cir.getReturnValue(), 1F));
		}
	}
	
	@Inject(method = "spawnAtLocation(Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/entity/item/ItemEntity;", at = @At("HEAD"), cancellable = true)
	public void dropStack(ItemStack stack, CallbackInfoReturnable<ItemEntity> cir) {
		if ((Object) this instanceof LivingEntity thisLivingEntity) {
			if (thisLivingEntity.isDeadOrDying() && thisLivingEntity.getLastHurtByMob() instanceof Player killer) {
				var hasInventoryInsertion = thisLivingEntity.level().registryAccess()
						.lookup(Registries.ENCHANTMENT)
						.flatMap(impl -> impl.get(PastelEnchantments.INVENTORY_INSERTION))
						.map(e -> EnchantmentHelper.getEnchantmentLevel(e, killer) > 0)
						.orElse(false);
				if (hasInventoryInsertion) {
					Item item = stack.getItem();
					int count = stack.getCount();
					
					if (killer.getInventory().add(stack)) {
						killer.level().playSound(null, killer.getX(), killer.getY(), killer.getZ(),
								SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS,
								0.2F, ((killer.getRandom().nextFloat() - killer.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
						
						if (stack.isEmpty()) {
							killer.awardStat(Stats.ITEM_PICKED_UP.get(item), count);
							cir.cancel();
						}
						killer.awardStat(Stats.ITEM_PICKED_UP.get(item), count - stack.getCount());
					}
				}
			}
		}
	}
	
	@ModifyReturnValue(method = "getPose", at = @At("RETURN"))
	public Pose forceSleepPose(Pose original) {
		var entity = (Entity) (Object) this;
		
		if (!(entity instanceof LivingEntity living))
			return original;
		
		if (!(entity instanceof Player) && (living.hasEffect(PastelStatusEffects.ETERNAL_SLUMBER) || living.hasEffect(PastelStatusEffects.FATAL_SLUMBER)))
			return Pose.SLEEPING;
		
		return original;
	}
	
	@ModifyReturnValue(method = "isOnFire", at = @At("RETURN"))
	public boolean considerPrimfireAsFire(boolean original) {
		var entity = (Entity) (Object) this;
		
		if (entity instanceof LivingEntity living && PrimordialFireData.isOnPrimordialFire(living))
			return true;
		
		return original;
	}
}
