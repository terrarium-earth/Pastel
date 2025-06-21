package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.entity.TouchingWaterAware;
import earth.terrarium.pastel.api.item.ArmorWithHitEffect;
import earth.terrarium.pastel.api.item.SlotReservingItem;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.attachments.data.EverpromiseRibbonData;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.components.PairedFoodComponent;
import earth.terrarium.pastel.helpers.ParticleHelper;
import earth.terrarium.pastel.helpers.PastelEnchantmentHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.enchantments.DisarmingHelper;
import earth.terrarium.pastel.helpers.enchantments.InexorableHelper;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.items.tools.ParryingSwordItem;
import earth.terrarium.pastel.items.trinkets.PuffCircletItem;
import earth.terrarium.pastel.items.trinkets.RingOfAerialGraceItem;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithPatternAndVelocityPayload;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.PastelDamageTypeTags;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelDataComponentTypes;
import earth.terrarium.pastel.registries.PastelEnchantments;
import earth.terrarium.pastel.registries.PastelEntityAttributes;
import earth.terrarium.pastel.registries.PastelItems;
import earth.terrarium.pastel.registries.PastelSoundEvents;
import earth.terrarium.pastel.registries.PastelStatusEffects;
import earth.terrarium.pastel.status_effects.EffectProlongingStatusEffect;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	
	@Shadow
	@Nullable
	protected Player lastHurtByPlayer;
	
	@Shadow
	public abstract boolean hasEffect(Holder<MobEffect> effect);
	
	@Shadow
	public abstract ItemStack getMainHandItem();
	
	@Shadow
	@Nullable
	public abstract MobEffectInstance getEffect(Holder<MobEffect> effect);
	
	@Shadow
	public abstract void readAdditionalSaveData(CompoundTag nbt);
	
	@Shadow
	public abstract boolean hurt(DamageSource source, float amount);
	
	@Shadow
	public abstract ItemStack getOffhandItem();
	
	@Shadow
	public abstract int getArmorValue();
	
	@Shadow
	public abstract void remove(Entity.RemovalReason reason);
	
	@Shadow
	public abstract void travel(Vec3 movementInput);
	
	@Shadow
	protected ItemStack useItem;
	
	@Shadow
	public abstract double getAttributeValue(Holder<Attribute> attribute);
	
	@Shadow
	protected abstract @Nullable SoundEvent getDeathSound();
	
	@Shadow
	protected abstract float getSoundVolume();
	
	@Shadow
	protected boolean dead;
	
	// FabricDefaultAttributeRegistry seems to only allow adding full containers and only single entity types?
	@Inject(method = "createLivingAttributes", require = 1, allow = 1, at = @At("RETURN"))
	private static void addAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue().add(PastelEntityAttributes.MENTAL_PRESENCE);
	}

	@ModifyArg(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"), index = 2)
	protected int applyExuberance(int originalXP) {
		return (int) (originalXP * getExuberanceMod(this.lastHurtByPlayer));
	}

	@Unique
	private float getExuberanceMod(Player attackingPlayer) {
		if (attackingPlayer != null) {
			int exuberanceLevel = PastelEnchantmentHelper.getEquipmentLevel(attackingPlayer.level().registryAccess(), PastelEnchantments.EXUBERANCE, attackingPlayer);
			return 1.0F + exuberanceLevel * PastelCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
		} else {
			return 1.0F;
		}
	}

	@ModifyVariable(method = "travel", at = @At(value = "STORE"), ordinal = 0)
	private boolean noSlowFallingSlowdown(boolean b) {
		if (!b) {
			return false;
		}
		return !InexorableHelper.isArmorActive((LivingEntity) (Object) this);
	}

	@Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;shouldDiscardFriction()Z"))
	private void travel(CallbackInfo ci, @Local(ordinal = 1) LocalFloatRef f) {
		var entity = (LivingEntity) (Object) this;
		var override = false;
		var friction = -1F;

		if (SlotReservingItem.isReservingSlot(this.getMainHandItem()) || SlotReservingItem.isReservingSlot(this.getOffhandItem())) {
			if (!(entity).onGround()) {
				friction = 0.945F;
				override = true;
			}
		}

		if (!entity.onGround()) {
			var optionalTrinket = PastelTrinketItem.getFirstEquipped(entity, PastelItems.RING_OF_AERIAL_GRACE.get());
			if (optionalTrinket.isPresent()) {
				var inkStorage = PastelItems.RING_OF_AERIAL_GRACE.get().getEnergyStorage(optionalTrinket.get());
				var storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
				friction = (float) Math.max(friction, 0.91 + (((RingOfAerialGraceItem) PastelItems.RING_OF_AERIAL_GRACE.get()).getBonus(storedInk) / 150F));
				override = true;
			}
		}

		if (entity instanceof Player player) {
			if (override) {
				friction += MiscPlayerData.get(player).getFrictionModifiers();
			} else {
				f.set(Math.min(f.get() + MiscPlayerData.get(player).getFrictionModifiers(), 0.99F));
			}
		}

		if (friction >= 0)
			f.set(Math.min(friction, 0.99F));
	}

	@ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getFriction(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F"))
	private float increaseSlipperiness(float original) {
		var entity = (LivingEntity) (Object) this;
		var random = entity.getRandom();
		var potency = SleepStatusEffect.getSleepScaling(entity);
		if (potency != -1) {
			potency *= 2;

			if (entity instanceof Player && random.nextFloat() < potency * 0.05) {
				return 0.35F + random.nextFloat() * 0.45F;
			}

			original = (float) Math.min(original + 0.3 + (potency / 25F), 0.9975F);
		}
		return original;
	}

	@ModifyReturnValue(method = "canStandOnFluid", at = @At("RETURN"))
	private boolean modifyFluidWalking(boolean original) {
		var entity = (LivingEntity) (Object) this;

		if (PastelTrinketItem.hasEquipped(entity, PastelItems.RING_OF_AERIAL_GRACE.get()))
			return !entity.isUnderWater();

		return original;
	}

	@ModifyExpressionValue(method = "isBlocking", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getUseDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"))
	private int allowInstantBlockForParryingSwords(int original) {
		if (useItem.getItem() instanceof ParryingSwordItem)
			return Integer.MAX_VALUE;

		return original;
	}

	@WrapOperation(method = "handleEntityEvent", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.playSound (Lnet/minecraft/sounds/SoundEvent;FF)V", ordinal = 1))
	private void swapBlockSound(LivingEntity instance, SoundEvent soundEvent, float v, float p, Operation<Void> original) {
		if (!(instance.getUseItem().getItem() instanceof ParryingSwordItem parryingSword)) {
			original.call(instance, soundEvent, v, p);
			return;
		}

		if (instance.getTicksUsingItem() <= parryingSword.getPerfectParryWindow(instance, instance.getUseItem())) {
			original.call(instance, PastelSoundEvents.PERFECT_PARRY, 1.75F, 0.9F + instance.level().random.nextFloat() * 0.3F);
			original.call(instance, PastelSoundEvents.SWORD_BLOCK, 0.667F, 0.5F + instance.level().random.nextFloat() * 0.3F);
		} else {
			original.call(instance, PastelSoundEvents.SWORD_BLOCK, 1.0F, 0.8F + instance.level().random.nextFloat() * 0.4F);
		}
	}


	@Inject(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEatEffect(Lnet/minecraft/world/food/FoodProperties;)V"))
	private void applyConcealedEffects(Level world, ItemStack stack, FoodProperties foodComponent, CallbackInfoReturnable<ItemStack> cir) {
		var oilEffect = stack.get(PastelDataComponentTypes.CONCEALED_EFFECT);
		if (!world.isClientSide() && oilEffect != null)
			((LivingEntity) (Object) this).addEffect(oilEffect);
	}

	@ModifyVariable(method = "hurtArmor", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private float damageArmor(float amount, DamageSource source) {
		if (source.is(PastelDamageTypeTags.INCREASED_ARMOR_DAMAGE)) {
			return amount * 10;
		}
		return amount;
	}

	@ModifyExpressionValue(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;calculateFallDamage(FF)I"))
	private int puffCircletDamageNegation(int original) {
		// TODO: fixme
		LivingEntity thisEntity = (LivingEntity) (Object) this;
		float cost = Math.min(original, PuffCircletItem.FALL_DAMAGE_NEGATING_COST);
		// check if damage reduction is applicable to this entity
		if (original <= 0 || thisEntity.isInvulnerableTo(thisEntity.damageSources().fall()) || AzureDikeProvider.getAzureDikeCharges(thisEntity) <= cost) return original;

		// check if this entity is protected by puff circlet
		if (!PastelTrinketItem.hasEquipped(thisEntity, PastelItems.PUFF_CIRCLET.get())) return original;

		// do damage reduction
		AzureDikeProvider.absorbDamage(thisEntity, cost);

		// yoink
		Vec3 velocity = thisEntity.getDeltaMovement();
		thisEntity.setDeltaMovement(velocity.x(), 0.5, velocity.z());
		Level world = thisEntity.level();
		if (world.isClientSide) { // it is split here so the particles spawn immediately, without network lag
			ParticleHelper.playParticleWithPatternAndVelocityClient(thisEntity.level(), thisEntity.position(), ColoredCraftingParticleEffect.WHITE, VectorPattern.EIGHT, 0.4);
			ParticleHelper.playParticleWithPatternAndVelocityClient(thisEntity.level(), thisEntity.position(), ColoredCraftingParticleEffect.BLUE, VectorPattern.EIGHT_OFFSET, 0.5);
		} else if (thisEntity instanceof ServerPlayer serverPlayerEntity) {
			PlayParticleWithPatternAndVelocityPayload.playParticleWithPatternAndVelocity(serverPlayerEntity, (ServerLevel) thisEntity.level(), thisEntity.position(), ColoredCraftingParticleEffect.WHITE, VectorPattern.EIGHT, 0.4);
			PlayParticleWithPatternAndVelocityPayload.playParticleWithPatternAndVelocity(serverPlayerEntity, (ServerLevel) thisEntity.level(), thisEntity.position(), ColoredCraftingParticleEffect.BLUE, VectorPattern.EIGHT_OFFSET, 0.5);
		}
		thisEntity.level().playSound(null, thisEntity.blockPosition(), PastelSoundEvents.PUFF_CIRCLET_PFFT, SoundSource.PLAYERS, 1.0F, 1.0F);

		return 0;
	}

	@ModifyVariable(at = @At("HEAD"), method = "hurt", argsOnly = true)
	private float modifyDamage(float amount, DamageSource source) {
		@Nullable MobEffectInstance vulnerability = getEffect(PastelStatusEffects.VULNERABILITY);
		if (vulnerability != null) {
			amount *= 1 + (PastelStatusEffects.VULNERABILITY_ADDITIONAL_DAMAGE_PERCENT_PER_LEVEL * vulnerability.getAmplifier());
		}
		return amount;
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.actuallyHurt (Lnet/minecraft/world/damagesource/DamageSource;F)V", ordinal = 0), method = "hurt")
	private void applyDike1(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
		if (source.is(PastelDamageTypeTags.BYPASSES_DIKE)) {
			original.call(instance, source, amount);
			return;
		}
		instance.actuallyHurt(source, AzureDikeProvider.absorbDamage(instance, amount));
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.actuallyHurt (Lnet/minecraft/world/damagesource/DamageSource;F)V", ordinal = 1), method = "hurt")
	private void applyDike2(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
		if (source.is(PastelDamageTypeTags.BYPASSES_DIKE)) {
			original.call(instance, source, amount);
			return;
		}
		instance.actuallyHurt(source, AzureDikeProvider.absorbDamage(instance, amount));
	}

	/**
	 * We do not force player sleeping because that would do funny things to the sleep cycle
	 */
	@ModifyReturnValue(method = "isSleeping", at = @At("RETURN"))
	private boolean forceSleepingState(boolean original) {
		if (original)
			return true;

		if (hasEffect(PastelStatusEffects.ETERNAL_SLUMBER) || hasEffect(PastelStatusEffects.FATAL_SLUMBER))
			return !(((LivingEntity) (Object) this) instanceof Player);

		return false;
	}

	@Inject(at = @At("RETURN"), method = "hurt")
	private void applyDisarmingEnchantment(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		// true if the entity got hurt
		var entity = (LivingEntity) (Object) this;
		if (amount > 0 && cir.getReturnValue() != null && cir.getReturnValue()) {
			// Disarming does not trigger when dealing damage to enemies using thorns
			if (!source.is(DamageTypes.THORNS)) {
				if (source.getEntity() instanceof LivingEntity livingSource) {
					int disarmingLevel = PastelEnchantmentHelper.getLevel(entity.level().registryAccess(), PastelEnchantments.DISARMING, livingSource.getMainHandItem());
					if (disarmingLevel > 0 && Math.random() < disarmingLevel * PastelCommon.CONFIG.DisarmingChancePerLevelMobs) {
						DisarmingHelper.disarmEntity(entity);
					}
				}
			}
		}
	}

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	private void applyBonusDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity target = (LivingEntity) (Object) this;

		// SetHealth damage does exactly that
		if (amount > 0 && source.is(PastelDamageTypeTags.USES_SET_HEALTH)) {
			float h = target.getHealth();
			target.setHealth(h - amount);
			target.getCombatTracker().recordDamage(source, amount);
			if (target.isDeadOrDying()) {
				if (!dead) {
					var deathSound = getDeathSound();
					if (deathSound != null)
						target.playSound(deathSound, getSoundVolume(), target.getVoicePitch());
				}
				target.die(source);
			}
			cir.setReturnValue(true);
        }
	}

	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.isDeadOrDying ()Z", ordinal = 1))
	private void TriggerArmorWithHitEffect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity thisEntity = (LivingEntity) (Object) this;
		Level world = thisEntity.level();
		if (!world.isClientSide) {
			if (thisEntity instanceof Mob thisMobEntity) {
				for (ItemStack armorItemStack : thisMobEntity.getArmorSlots()) {
					if (armorItemStack.getItem() instanceof ArmorWithHitEffect armorWithHitEffect) {
						armorWithHitEffect.onHit(armorItemStack, source, thisMobEntity, amount);
					}
				}
			} else if (thisEntity instanceof ServerPlayer thisPlayerEntity) {
				for (ItemStack armorItemStack : thisPlayerEntity.getArmorSlots()) {
					if (armorItemStack.getItem() instanceof ArmorWithHitEffect armorWithHitEffect) {
						armorWithHitEffect.onHit(armorItemStack, source, thisPlayerEntity, amount);
					}
				}
			}
		}
	}

	@ModifyVariable(method = "setSprinting(Z)V", at = @At("HEAD"), argsOnly = true)
	private boolean setSprinting(boolean sprinting) {
		var entity = (LivingEntity) (Object) this;
		if (sprinting && entity.hasEffect(PastelStatusEffects.SCARRED)) {
			return false;
		}
		return sprinting;
	}

	@Inject(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "HEAD"))
	private void conditionalFood(Level world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		PairedFoodComponent component = stack.get(PastelDataComponentTypes.PAIRED_FOOD_COMPONENT);
		if (component != null) {
			component.tryEatFood(world, (LivingEntity) (Object) this, stack);
		}
	}

	@Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	private void addStatusEffect(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
		if (EffectProlongingStatusEffect.canBeExtended(effect.getEffect())) {
			MobEffectInstance effectProlongingInstance = this.getEffect(PastelStatusEffects.EFFECT_PROLONGING);
			if (effectProlongingInstance != null) {
				((MobEffectInstanceInjector) effect).setDuration(EffectProlongingStatusEffect.getExtendedDuration(effect.getDuration(), effectProlongingInstance.getAmplifier()));
			}
		}
	}

	@Inject(method = "dropAllDeathLoot", at = @At("HEAD"), cancellable = true)
	protected void drop(ServerLevel world, DamageSource damageSource, CallbackInfo ci) {
		LivingEntity thisEntity = (LivingEntity) (Object) this;

		if (EverpromiseRibbonData.hasRibbon(thisEntity)) {
			ItemStack memoryStack = MemoryItem.getMemoryForEntity(thisEntity);
			MemoryItem.setTicksToManifest(memoryStack, 20);
			MemoryItem.setSpawnAsAdult(memoryStack, true);
			MemoryItem.markAsBrokenPromise(memoryStack, true);

			Vec3 entityPos = thisEntity.position();
			ItemEntity itemEntity = new ItemEntity(thisEntity.level(), entityPos.x(), entityPos.y(), entityPos.z(), memoryStack);
			thisEntity.level().addFreshEntity(itemEntity);

			ci.cancel();
		}
	}

	@Inject(method = "tick", at = @At("TAIL"))
	protected void applyInexorableEffects(CallbackInfo ci) {
		LivingEntity entity = (LivingEntity) (Object) this;
		if (entity.level() != null && entity.level().getGameTime() % 20 == 0) {
			InexorableHelper.checkAndRemoveSlowdownModifiers(entity);
		}
	}

	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWaterRainOrBubble()Z"))
	private boolean isWet(LivingEntity livingEntity) {
		return livingEntity.isInWater() ? ((TouchingWaterAware) livingEntity).isActuallyTouchingWater() : livingEntity.isInWaterRainOrBubble();
	}
	
}