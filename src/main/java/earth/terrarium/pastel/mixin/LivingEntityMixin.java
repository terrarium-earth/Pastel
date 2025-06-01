package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import earth.terrarium.pastel.SpectrumCommon;
import earth.terrarium.pastel.api.damage_type.StackTracking;
import earth.terrarium.pastel.api.entity.TouchingWaterAware;
import earth.terrarium.pastel.api.item.ArmorPiercingItem;
import earth.terrarium.pastel.api.item.ArmorWithHitEffect;
import earth.terrarium.pastel.api.item.SlotReservingItem;
import earth.terrarium.pastel.api.item.SplitDamageItem;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.attachments.data.EverpromiseRibbonData;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.components.PairedFoodComponent;
import earth.terrarium.pastel.helpers.ParticleHelper;
import earth.terrarium.pastel.helpers.SpectrumEnchantmentHelper;
import earth.terrarium.pastel.helpers.StatusEffectHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.helpers.enchantments.DisarmingHelper;
import earth.terrarium.pastel.helpers.enchantments.InexorableHelper;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.items.tools.ParryingSwordItem;
import earth.terrarium.pastel.items.trinkets.AetherGracedNectarGlovesItem;
import earth.terrarium.pastel.items.trinkets.PuffCircletItem;
import earth.terrarium.pastel.items.trinkets.RingOfAerialGraceItem;
import earth.terrarium.pastel.items.trinkets.SpectrumTrinketItem;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithPatternAndVelocityPayload;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.SpectrumDamageTypeTags;
import earth.terrarium.pastel.registries.SpectrumDamageTypes;
import earth.terrarium.pastel.registries.SpectrumDataComponentTypes;
import earth.terrarium.pastel.registries.SpectrumEnchantments;
import earth.terrarium.pastel.registries.SpectrumEntityAttributes;
import earth.terrarium.pastel.registries.SpectrumEntityTypeTags;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import earth.terrarium.pastel.registries.SpectrumStatusEffects;
import earth.terrarium.pastel.status_effects.EffectProlongingStatusEffect;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.neoforged.neoforge.common.Tags;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.world.damagesource.CombatRules;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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

import java.util.Optional;

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
	private static void spectrum$addAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
		cir.getReturnValue().add(SpectrumEntityAttributes.MENTAL_PRESENCE);
	}

	@ModifyArg(method = "dropExperience", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ExperienceOrb;award(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/phys/Vec3;I)V"), index = 2)
	protected int spectrum$applyExuberance(int originalXP) {
		return (int) (originalXP * spectrum$getExuberanceMod(this.lastHurtByPlayer));
	}

	@Unique
	private float spectrum$getExuberanceMod(Player attackingPlayer) {
		if (attackingPlayer != null) {
			int exuberanceLevel = SpectrumEnchantmentHelper.getEquipmentLevel(attackingPlayer.level().registryAccess(), SpectrumEnchantments.EXUBERANCE, attackingPlayer);
			return 1.0F + exuberanceLevel * SpectrumCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
		} else {
			return 1.0F;
		}
	}

	@ModifyVariable(method = "travel", at = @At(value = "STORE"), ordinal = 0)
	private boolean spectrum$noSlowFallingSlowdown(boolean b) {
		if (!b) {
			return false;
		}
		return !InexorableHelper.isArmorActive((LivingEntity) (Object) this);
	}

	@Inject(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;shouldDiscardFriction()Z"))
	private void spectrum$travel(CallbackInfo ci, @Local(ordinal = 1) LocalFloatRef f) {
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
			var optionalTrinket = SpectrumTrinketItem.getFirstEquipped(entity, SpectrumItems.RING_OF_AERIAL_GRACE.get());
			if (optionalTrinket.isPresent()) {
				var inkStorage = SpectrumItems.RING_OF_AERIAL_GRACE.get().getEnergyStorage(optionalTrinket.get());
				var storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
				friction = (float) Math.max(friction, 0.91 + (((RingOfAerialGraceItem) SpectrumItems.RING_OF_AERIAL_GRACE.get()).getBonus(storedInk) / 150F));
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
	private float spectrum$increaseSlipperiness(float original) {
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
	private boolean spectrum$modifyFluidWalking(boolean original) {
		var entity = (LivingEntity) (Object) this;

		if (SpectrumTrinketItem.hasEquipped(entity, SpectrumItems.RING_OF_AERIAL_GRACE.get()))
			return !entity.isUnderWater();

		return original;
	}

	@ModifyExpressionValue(method = "isBlocking", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;getUseDuration(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/LivingEntity;)I"))
	private int spectrum$allowInstantBlockForParryingSwords(int original) {
		if (useItem.getItem() instanceof ParryingSwordItem)
			return Integer.MAX_VALUE;

		return original;
	}

	@WrapOperation(method = "handleEntityEvent", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.playSound (Lnet/minecraft/sounds/SoundEvent;FF)V", ordinal = 1))
	private void spectrum$swapBlockSound(LivingEntity instance, SoundEvent soundEvent, float v, float p, Operation<Void> original) {
		if (!(instance.getUseItem().getItem() instanceof ParryingSwordItem parryingSword)) {
			original.call(instance, soundEvent, v, p);
			return;
		}

		if (instance.getTicksUsingItem() <= parryingSword.getPerfectParryWindow(instance, instance.getUseItem())) {
			original.call(instance, SpectrumSoundEvents.PERFECT_PARRY, 1.75F, 0.9F + instance.level().random.nextFloat() * 0.3F);
			original.call(instance, SpectrumSoundEvents.SWORD_BLOCK, 0.667F, 0.5F + instance.level().random.nextFloat() * 0.3F);
		} else {
			original.call(instance, SpectrumSoundEvents.SWORD_BLOCK, 1.0F, 0.8F + instance.level().random.nextFloat() * 0.4F);
		}
	}


	@Inject(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEatEffect(Lnet/minecraft/world/food/FoodProperties;)V"))
	private void spectrum$applyConcealedEffects(Level world, ItemStack stack, FoodProperties foodComponent, CallbackInfoReturnable<ItemStack> cir) {
		var oilEffect = stack.get(SpectrumDataComponentTypes.CONCEALED_EFFECT);
		if (!world.isClientSide() && oilEffect != null)
			((LivingEntity) (Object) this).addEffect(oilEffect);
	}

	@ModifyReturnValue(method = "canBeAffected", at = @At("RETURN"))
	private boolean spectrum$canHaveStatusEffect(boolean original, @Local(argsOnly = true) MobEffectInstance statusEffectInstance) {
		var instance = (LivingEntity) (Object) this;

		if (original && this.hasEffect(SpectrumStatusEffects.IMMUNITY) && statusEffectInstance.getEffect().value().getCategory() == MobEffectCategory.HARMFUL) {
			if (StatusEffectHelper.isIncurable(statusEffectInstance)) {
				var immunity = getEffect(SpectrumStatusEffects.IMMUNITY);
				var cost = 600 * (statusEffectInstance.getAmplifier() + 1);

				if (immunity.getDuration() >= cost) {
					((MobEffectInstanceInjector) immunity).spectrum$setDuration(Math.max(5, immunity.getDuration() - cost));
					if (!instance.level().isClientSide()) {
						((ServerLevel) instance.level()).getChunkSource().broadcastAndSend(instance, new ClientboundUpdateMobEffectPacket(instance.getId(), immunity, false));
					}
					return false;
				} else {
					return true;
				}
			}

			return false;
		}
		return original;
	}

	@ModifyVariable(method = "hurtArmor", at = @At("HEAD"), ordinal = 0, argsOnly = true)
	private float spectrum$damageArmor(float amount, DamageSource source) {
		if (source.is(SpectrumDamageTypeTags.DOES_NOT_DAMAGE_ARMOR)) {
			return 0;
		} else if (source.is(SpectrumDamageTypeTags.INCREASED_ARMOR_DAMAGE)) {
			return amount * 10;
		}
		return amount;
	}

	@ModifyArg(method = "getDamageAfterMagicAbsorb", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/damagesource/CombatRules;getDamageAfterMagicAbsorb(FF)F"), index = 1)
	private float spectrum$modifyAppliedDamage(float protection, @Local(argsOnly = true) DamageSource source) {
		var pair = getArmorPiercing(source);

		if (pair.isPresent()) {
			var ap = pair.get().getA();
			var stack = pair.get().getB();

			var modProt = Math.max(protection, 20F) / 25F;
			protection = Math.max(modProt - ap.getProtReduction((LivingEntity) (Object) this, stack), 0) * 20F;
		}

		return protection;
	}

	@ModifyVariable(method = "getDamageAfterArmorAbsorb", at = @At("STORE"), ordinal = 0, argsOnly = true)
	private float spectrum$applyArmorToDamage(float amount, DamageSource source) {
		float defense = getArmorValue();
		float toughness = getToughness();
		var modified = false;
		var pair = getArmorPiercing(source);
		var entity = (LivingEntity) (Object) this;

		if (pair.isPresent()) {
			var ap = pair.get().getA();
			var stack = pair.get().getB();

			defense *= ap.getDefenseMultiplier(entity, stack);
			toughness *= ap.getToughnessMultiplier(entity, stack);
			modified = true;
		}

		if (source.is(SpectrumDamageTypeTags.CALCULATES_DAMAGE_BASED_ON_TOUGHNESS)) {
			amount = CombatRules.getDamageAfterAbsorb(entity, amount, source, toughness * 1.334F, Float.MAX_VALUE);
		} else if (source.is(SpectrumDamageTypeTags.PARTLY_IGNORES_PROTECTION)) {
			amount = CombatRules.getDamageAfterAbsorb(entity, amount, source, defense / 2, toughness);
		}

		if (modified) {
			amount = CombatRules.getDamageAfterAbsorb(entity, amount, source, defense, toughness);
		}

		return amount;
	}

	@Unique
	private Optional<Tuple<ArmorPiercingItem, ItemStack>> getArmorPiercing(DamageSource source) {
		if (!(source instanceof StackTracking stackTracking))
			return Optional.empty();

		var stackOptional = stackTracking.spectrum$getTrackedStack();

		if (stackOptional.isEmpty())
			return Optional.empty();

		var stack = stackOptional.get();

		if (!(stack.getItem() instanceof ArmorPiercingItem ap))
			return Optional.empty();

		return Optional.of(new Tuple<>(ap, stack));
	}

	@Unique
	private float getToughness() {
		return (float) this.getAttributeValue(Attributes.ARMOR_TOUGHNESS);
	}

	@ModifyExpressionValue(method = "causeFallDamage", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;calculateFallDamage(FF)I"))
	private int spectrum$puffCircletDamageNegation(int original) {
		// TODO: fixme
		LivingEntity thisEntity = (LivingEntity) (Object) this;
		float cost = Math.min(original, PuffCircletItem.FALL_DAMAGE_NEGATING_COST);
		// check if damage reduction is applicable to this entity
		if (original <= 0 || thisEntity.isInvulnerableTo(thisEntity.damageSources().fall()) || AzureDikeProvider.getAzureDikeCharges(thisEntity) <= cost) return original;

		// check if this entity is protected by puff circlet
		if (!SpectrumTrinketItem.hasEquipped(thisEntity, SpectrumItems.PUFF_CIRCLET.get())) return original;

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
		thisEntity.level().playSound(null, thisEntity.blockPosition(), SpectrumSoundEvents.PUFF_CIRCLET_PFFT, SoundSource.PLAYERS, 1.0F, 1.0F);

		return 0;
	}

	@ModifyVariable(at = @At("HEAD"), method = "hurt", argsOnly = true)
	private float spectrum$modifyDamage(float amount, DamageSource source) {
		@Nullable MobEffectInstance vulnerability = getEffect(SpectrumStatusEffects.VULNERABILITY);
		if (vulnerability != null) {
			amount *= 1 + (SpectrumStatusEffects.VULNERABILITY_ADDITIONAL_DAMAGE_PERCENT_PER_LEVEL * vulnerability.getAmplifier());
		}
		return amount;
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.actuallyHurt (Lnet/minecraft/world/damagesource/DamageSource;F)V", ordinal = 0), method = "hurt")
	private void spectrum$applyDike1(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
		if (source.is(SpectrumDamageTypeTags.BYPASSES_DIKE)) {
			original.call(instance, source, amount);
			return;
		}
		instance.actuallyHurt(source, AzureDikeProvider.absorbDamage(instance, amount));
	}

	@WrapOperation(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.actuallyHurt (Lnet/minecraft/world/damagesource/DamageSource;F)V", ordinal = 1), method = "hurt")
	private void spectrum$applyDike2(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
		if (source.is(SpectrumDamageTypeTags.BYPASSES_DIKE)) {
			original.call(instance, source, amount);
			return;
		}
		instance.actuallyHurt(source, AzureDikeProvider.absorbDamage(instance, amount));
	}

	@Inject(method = "tickEffects", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;remove()V"))
	private void spectrum$fatalSlumberKill(CallbackInfo ci, @Local MobEffectInstance effectInstance) {
		if (effectInstance.getEffect() == SpectrumStatusEffects.FATAL_SLUMBER) {
			var entity = (LivingEntity) (Object) this;

			if (entity.level().isClientSide())
				return;

			if (entity.isSpectator() || entity instanceof Player player && player.getAbilities().instabuild)
				return;

			var damage = Float.MAX_VALUE;
			if (SleepStatusEffect.isImmuneish(entity)) {
				if (entity instanceof Player)
					damage = entity.getHealth() * 0.95F;
				else
					damage = entity.getMaxHealth() * 0.3F;
			}

			entity.hurt(SpectrumDamageTypes.sleep(entity.level(), null), damage);
			if (entity.isAlive() && entity instanceof ServerPlayer serverPlayerEntity && !serverPlayerEntity.isCreative()) {
				Support.grantAdvancementCriterion(serverPlayerEntity, "lategame/survive_fatal_slumber", "survived_fatal_slumber");
			}
		}
	}

	/**
	 * We do not force player sleeping because that would do funny things to the sleep cycle
	 */
	@ModifyReturnValue(method = "isSleeping", at = @At("RETURN"))
	private boolean spectrum$forceSleepingState(boolean original) {
		if (original)
			return true;

		if (hasEffect(SpectrumStatusEffects.ETERNAL_SLUMBER) || hasEffect(SpectrumStatusEffects.FATAL_SLUMBER))
			return !(((LivingEntity) (Object) this) instanceof Player);

		return false;
	}

	// TODO: WHAT THE FUCK
	@Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
	private void spectrum$modifyOrCancelEffects(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
		var entity = (LivingEntity) (Object) this;
		var effectType = effect.getEffect();

		if ((!entity.hasEffect(SpectrumStatusEffects.IMMUNITY)) && AetherGracedNectarGlovesItem.testEffectFor(entity, effectType)) {
			var cost = (effect.getAmplifier() + 1) * AetherGracedNectarGlovesItem.HARMFUL_EFFECT_COST;

			if (StatusEffectHelper.isIncurable(effect))
				cost *= 3;

			if (AetherGracedNectarGlovesItem.tryBlockEffect(entity, cost)) {
				cir.setReturnValue(false);
				return;
			}
		}

		MobEffectInstanceInjector effectInjector = (MobEffectInstanceInjector) effect;
		var resistanceModifier = Mth.clamp(SleepStatusEffect.getSleepResistance(effect, entity), 0.1F, 10F);
		if (effectType == SpectrumStatusEffects.ETERNAL_SLUMBER) {
			if (SleepStatusEffect.isImmuneish(entity)) {
				effectInjector.spectrum$setDuration(Math.round(effect.getDuration() / resistanceModifier));
			} else if (!entity.getType().is(SpectrumEntityTypeTags.SLEEP_RESISTANT)) {
				effectInjector.spectrum$setDuration(MobEffectInstance.INFINITE_DURATION);
			}
		} else if (effectType == SpectrumStatusEffects.FATAL_SLUMBER) {
			if (SleepStatusEffect.isImmuneish(entity) && entity.getType().is(Tags.EntityTypes.BOSSES)) {
				effectInjector.spectrum$setDuration(20 * 60);
			} else {
				effectInjector.spectrum$setDuration(Math.max(Math.round(effect.getDuration() * resistanceModifier * 3), 20 * 10));
			}
		}
	}

	@Inject(at = @At("RETURN"), method = "hurt")
	private void spectrum$applyDisarmingEnchantment(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		// true if the entity got hurt
		var entity = (LivingEntity) (Object) this;
		if (amount > 0 && cir.getReturnValue() != null && cir.getReturnValue()) {
			// Disarming does not trigger when dealing damage to enemies using thorns
			if (!source.is(DamageTypes.THORNS)) {
				if (source.getEntity() instanceof LivingEntity livingSource) {
					int disarmingLevel = SpectrumEnchantmentHelper.getLevel(entity.level().registryAccess(), SpectrumEnchantments.DISARMING, livingSource.getMainHandItem());
					if (disarmingLevel > 0 && Math.random() < disarmingLevel * SpectrumCommon.CONFIG.DisarmingChancePerLevelMobs) {
						DisarmingHelper.disarmEntity(entity);
					}
				}
			}
		}
	}

	@Inject(method = "hurt", at = @At("HEAD"), cancellable = true)
	private void spectrum$applyBonusDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		LivingEntity target = (LivingEntity) (Object) this;

		// SetHealth damage does exactly that
		if (amount > 0 && source.is(SpectrumDamageTypeTags.USES_SET_HEALTH)) {
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
			return;
		}

		// If this entity is hit with a SplitDamageItem, damage() gets called recursively for each type of damage dealt
		if (!SpectrumDamageTypes.recursiveDamageFlag && amount > 0 && source.getDirectEntity() instanceof LivingEntity livingSource) {
			ItemStack mainHandStack = livingSource.getMainHandItem();
			if (mainHandStack.getItem() instanceof SplitDamageItem splitDamageItem) {
				SpectrumDamageTypes.recursiveDamageFlag = true;
				SplitDamageItem.DamageComposition composition = splitDamageItem.getDamageComposition(livingSource, target, mainHandStack, amount);

				boolean damaged = false;
				for (Tuple<DamageSource, Float> entry : composition.get()) {
					int invincibilityFrameStore = target.hurtTime;
					damaged |= hurt(entry.getA(), entry.getB());
					target.hurtTime = invincibilityFrameStore;
				}

				SpectrumDamageTypes.recursiveDamageFlag = false;
				cir.setReturnValue(damaged);
			}
		}
	}

	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.isDeadOrDying ()Z", ordinal = 1))
	private void spectrum$TriggerArmorWithHitEffect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
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
	private boolean spectrum$setSprinting(boolean sprinting) {
		var entity = (LivingEntity) (Object) this;
		if (sprinting && entity.hasEffect(SpectrumStatusEffects.SCARRED)) {
			return false;
		}
		return sprinting;
	}

	@Inject(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;", at = @At(value = "HEAD"))
	private void spectrum$conditionalFood(Level world, ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
		PairedFoodComponent component = stack.get(SpectrumDataComponentTypes.PAIRED_FOOD_COMPONENT);
		if (component != null) {
			component.tryEatFood(world, (LivingEntity) (Object) this, stack);
		}
	}

	@Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z", at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
	private void spectrum$addStatusEffect(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
		if (EffectProlongingStatusEffect.canBeExtended(effect.getEffect())) {
			MobEffectInstance effectProlongingInstance = this.getEffect(SpectrumStatusEffects.EFFECT_PROLONGING);
			if (effectProlongingInstance != null) {
				((MobEffectInstanceInjector) effect).spectrum$setDuration(EffectProlongingStatusEffect.getExtendedDuration(effect.getDuration(), effectProlongingInstance.getAmplifier()));
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
	private boolean spectrum$isWet(LivingEntity livingEntity) {
		return livingEntity.isInWater() ? ((TouchingWaterAware) livingEntity).spectrum$isActuallyTouchingWater() : livingEntity.isInWaterRainOrBubble();
	}
	
}