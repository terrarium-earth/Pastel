package de.dafuqs.spectrum.mixin;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import de.dafuqs.spectrum.api.entity.PlayerEntityAccessor;
import de.dafuqs.spectrum.api.item.ExperienceStorageItem;
import de.dafuqs.spectrum.attachments.data.LastKillData;
import de.dafuqs.spectrum.attachments.data.MiscPlayerData;
import de.dafuqs.spectrum.components.InertiaComponent;
import de.dafuqs.spectrum.entity.entity.SpectrumFishingBobberEntity;
import de.dafuqs.spectrum.helpers.SpectrumEnchantmentHelper;
import de.dafuqs.spectrum.helpers.enchantments.ImprovedCriticalHelper;
import de.dafuqs.spectrum.items.tools.LightGreatswordItem;
import de.dafuqs.spectrum.items.tools.NectarLanceItem;
import de.dafuqs.spectrum.items.trinkets.AttackRingItem;
import de.dafuqs.spectrum.items.trinkets.SpectrumTrinketItem;
import de.dafuqs.spectrum.progression.SpectrumAdvancementCriteria;
import de.dafuqs.spectrum.registries.SpectrumDamageTypeTags;
import de.dafuqs.spectrum.registries.SpectrumDataComponentTypes;
import de.dafuqs.spectrum.registries.SpectrumEnchantments;
import de.dafuqs.spectrum.registries.SpectrumEntityAttributes;
import de.dafuqs.spectrum.registries.SpectrumItems;
import de.dafuqs.spectrum.registries.SpectrumSoundEvents;
import de.dafuqs.spectrum.registries.SpectrumStatusEffects;
import de.dafuqs.spectrum.status_effects.FrenzyStatusEffect;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityAccessor {
	
	protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
		super(entityType, world);
	}
	
	@Shadow
	public abstract Iterable<ItemStack> getHandSlots();
	
	@Shadow
	private int sleepCounter;
	
	@Shadow
	public abstract boolean hurt(DamageSource source, float amount);
	
	@Shadow
	protected abstract boolean canPlayerFitWithinBlocksAndEntitiesWhen(Pose pose);
	
	@Unique
	public SpectrumFishingBobberEntity fishingBobber;
	
	@WrapOperation(method = "getDestroySpeed", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getDestroySpeed(Lnet/minecraft/world/level/block/state/BlockState;)F"))
	private float spectrum$modifygetBlockBreakingSpeed(Inventory inventory, BlockState state, Operation<Float> original) {
		ItemStack stack = inventory.items.get(inventory.selected);
		RegistryAccess drm = registryAccess();
		Tool tool = stack.get(DataComponents.TOOL);
		float speed = original.call(inventory, state);
		
		// RAZING GAMING
		int razingLevel = SpectrumEnchantmentHelper.getLevel(drm, SpectrumEnchantments.RAZING, stack);
		if (razingLevel > 0 && tool != null && tool.getMiningSpeed(state) > tool.defaultMiningSpeed()) {
			float hardness = state.getBlock().defaultDestroyTime();
			speed = (float) Math.max(1 + hardness, Math.pow(2, 1 + razingLevel / 8F));
		}
		
		// INERTIA GAMING
		// inertia mining speed calculation logic is capped at 5 levels.
		// Higher and the formula would do weird stuff
		int inertiaLevel = SpectrumEnchantmentHelper.getLevel(drm, SpectrumEnchantments.INERTIA, stack);
		inertiaLevel = Math.min(4, inertiaLevel);
		if (inertiaLevel > 0) {
			var inertia = stack.getOrDefault(SpectrumDataComponentTypes.INERTIA, InertiaComponent.DEFAULT);
			if (state.is(inertia.lastMined())) {
				var additionalSpeedPercent = 2.0 * Math.log(inertia.count()) / Math.log((6 - inertiaLevel) * (6 - inertiaLevel) + 1);
				speed *= 0.5F + (float) additionalSpeedPercent;
			} else {
				speed /= 4;
			}
		}
		
		return speed;
	}
	
	@Inject(method = "updateSwimming()V", at = @At("HEAD"), cancellable = true)
	public void spectrum$updateSwimming(CallbackInfo ci) {
		if (SpectrumTrinketItem.hasEquipped(this, SpectrumItems.RING_OF_DENSER_STEPS)) {
			this.setSwimming(false);
			ci.cancel();
		}
	}
	
	@Inject(method = "killedEntity", at = @At("HEAD"))
	private void spectrum$rememberKillOther(ServerLevel world, LivingEntity other, CallbackInfoReturnable<Boolean> cir) {
		Player entity = (Player) (Object) this;
		LastKillData.rememberKillTick(entity, entity.level().getGameTime());
		
		MobEffectInstance frenzy = entity.getEffect(SpectrumStatusEffects.FRENZY);
		if (frenzy != null) {
			((FrenzyStatusEffect) frenzy.getEffect()).onKill(entity, frenzy.getAmplifier());
		}
	}
	
	@Inject(method = "hurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;hurt(Lnet/minecraft/world/damagesource/DamageSource;F)Z"))
	private void spectrum$stopSleep(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if (amount > 0) {
			Player entity = (Player) (Object) this;
			MiscPlayerData.get(entity).notifyHit();
		}
	}
	
	@Inject(method = "attack", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/player/Player.getAttributeValue (Lnet/minecraft/core/Holder;)D"))
	protected void spectrum$calculateModifiers(Entity target, CallbackInfo ci) {
		Player player = (Player) (Object) this;
		
		Multimap<Holder<Attribute>, AttributeModifier> map = Multimaps.newMultimap(Maps.newLinkedHashMap(), ArrayList::new);
		
		AttributeModifier jeopardantModifier;
		if (SpectrumTrinketItem.hasEquipped(player, SpectrumItems.JEOPARDANT)) {
			jeopardantModifier = new AttributeModifier(AttackRingItem.ATTACK_RING_DAMAGE_ID, AttackRingItem.getAttackModifierForEntity(player), AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		} else {
			jeopardantModifier = new AttributeModifier(AttackRingItem.ATTACK_RING_DAMAGE_ID, 0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
		}
		map.put(Attributes.ATTACK_DAMAGE, jeopardantModifier);
		
		int improvedCriticalLevel = SpectrumEnchantmentHelper.getLevel(target.level().registryAccess(), SpectrumEnchantments.IMPROVED_CRITICAL, player.getMainHandItem());
		if (improvedCriticalLevel > 0) {
			AttributeModifier improvedCriticalModifier = new AttributeModifier(SpectrumEntityAttributes.CRIT_MODIFIER_ID, ImprovedCriticalHelper.getAddtionalCritDamageMultiplier(improvedCriticalLevel), AttributeModifier.Operation.ADD_VALUE);
			map.put(AdditionalEntityAttributes.CRITICAL_BONUS_DAMAGE, improvedCriticalModifier);
		}
		
		player.getAttributes().addTransientAttributeModifiers(map);
	}
	
	@ModifyExpressionValue(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getEntitiesOfClass(Ljava/lang/Class;Lnet/minecraft/world/phys/AABB;)Ljava/util/List;"))
	protected List<LivingEntity> spectrum$increaseSweepRadius(List<LivingEntity> original, Entity target) {
		var stack = this.getItemInHand(InteractionHand.MAIN_HAND);
		if (stack.getItem() == SpectrumItems.DRACONIC_TWINSWORD) {
			var channeling = getChanneling(stack) + 1;
			var size = channeling * 2 + 0.5;
			var entities = this.level().getEntitiesOfClass(LivingEntity.class, target.getBoundingBox().inflate(size, 0.4 * channeling, size));
			if (!level().isClientSide() && (channeling - 1) > 0) {
				for (LivingEntity living : entities) {
					if (living.canBeSeenAsEnemy()) {
						for (int i = 0; i < 5; i++) {
							((ServerLevel) level()).sendParticles(ParticleTypes.ENCHANTED_HIT,
									living.getRandomX(1.25),
									living.getY() + living.getBbHeight() * random.nextFloat(),
									living.getRandomZ(1.25),
									random.nextInt(2), 0, random.nextFloat() / 6F, 0, 0);
						}
					}
				}
			}
			
			return entities;
		}
		return original;
	}
	
	@ModifyVariable(method = "attack", at = @At(value = "STORE"), index = 8, require = 1)
	private boolean spectrum$binglebongle(boolean value, Entity target) {
		if (hasForcedCrits(target)) {
			return true;
		}
		
		return value;
	}
	
	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getDeltaMovement()Lnet/minecraft/world/phys/Vec3;"))
	private void spectrum$perfectCounter(Entity target, CallbackInfo ci, @Local(ordinal = 0) LocalFloatRef damage) {
		var player = (Player) (Object) this;
		if (MiscPlayerData.get(player).consumePerfectCounter()) {
			damage.set(damage.get() * 1.5F);
		}
	}
	
	@Unique
	protected boolean hasForcedCrits(Entity target) {
		var player = (Player) (Object) this;
		var component = MiscPlayerData.get(player);
		
		if (NectarLanceItem.sleepCrits(player, target)) {
			return true;
		} else if (component.isParrying()) {
			component.setParryTicks(0);
			return true;
		} else return component.isLunging();
	}
	
	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.playSound (Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", ordinal = 2))
	protected void spectrum$switchCritSound(Level instance, Player except, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original) {
		var player = (Player) (Object) this;
		var stack = this.getItemInHand(InteractionHand.MAIN_HAND);
		var component = MiscPlayerData.get(player);
		if (stack.getItem() instanceof LightGreatswordItem && component.isLunging()) {
			original.call(instance, except, x, y, z, SpectrumSoundEvents.LUNGE_CRIT, category, 1F, 1F + random.nextFloat() * 0.2F);
			return;
		}
		original.call(instance, except, x, y, z, sound, category, volume, pitch);
	}
	
	@WrapOperation(method = "attack", at = @At(value = "INVOKE", target = "net/minecraft/world/level/Level.playSound (Lnet/minecraft/world/entity/player/Player;DDDLnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V", ordinal = 1))
	protected void spectrum$switchSweepSound(Level instance, Player except, double x, double y, double z, SoundEvent sound, SoundSource category, float volume, float pitch, Operation<Void> original) {
		var stack = this.getItemInHand(InteractionHand.MAIN_HAND);
		if (stack.getItem() == SpectrumItems.DRACONIC_TWINSWORD && getChanneling(stack) > 0) {
			this.level().playSound(except, x, y, z, SpectrumSoundEvents.ELECTRIC_DISCHARGE, category, 0.75F, 0.9F + random.nextFloat() * 0.2F);
			return;
		}
		original.call(instance, except, x, y, z, sound, category, volume, pitch);
	}
	
	@Unique
	protected int getChanneling(ItemStack stack) {
		return SpectrumEnchantmentHelper.getLevel(level().registryAccess(), Enchantments.CHANNELING, stack);
	}
	
	@Inject(at = @At("TAIL"), method = "jumpFromGround")
	protected void spectrum$jumpAdvancementCriterion(CallbackInfo ci) {
		
		if ((Object) this instanceof ServerPlayer serverPlayerEntity) {
			SpectrumAdvancementCriteria.TAKE_OFF_BELT_JUMP.trigger(serverPlayerEntity);
		}
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
	
	@Override
	public void setSpectrumBobber(SpectrumFishingBobberEntity bobber) {
		this.fishingBobber = bobber;
	}
	
	@Override
	public void setSleepTimer(int ticks) {
		this.sleepCounter = ticks;
	}
	
	@Override
	public SpectrumFishingBobberEntity getSpectrumBobber() {
		return this.fishingBobber;
	}
	
	@Inject(at = @At("HEAD"), method = "isHurt", cancellable = true)
	public void canFoodHeal(CallbackInfoReturnable<Boolean> cir) {
		Player player = (Player) (Object) this;
		if (player.hasEffect(SpectrumStatusEffects.SCARRED)) {
			cir.setReturnValue(false);
		}
	}
	
	// If the player holds an ExperienceStorageItem in their hands
	// experience is tried to get put in there first
	@ModifyVariable(at = @At("HEAD"), method = "giveExperiencePoints", argsOnly = true)
	public int addExperience(int experience) {
		if (experience < 0) { // draining XP, like Botanias Rosa Arcana
			return experience;
		}
		
		// if the player has a ExperienceStorageItem in hand add the XP to that
		Player player = (Player) (Object) this;
		for (ItemStack stack : getHandSlots()) {
			if (!player.isUsingItem() && stack.getItem() instanceof ExperienceStorageItem) {
				experience = ExperienceStorageItem.addStoredExperience(level().registryAccess(), stack, experience);
				player.takeXpDelay = 0;
				if (experience == 0) {
					break;
				}
			}
		}
		return experience;
	}
	
	@ModifyVariable(method = "getDestroySpeed",
			slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;hasEffect(Lnet/minecraft/core/Holder;)Z"),
					to = @At("TAIL")
			),
			at = @At(value = "LOAD"),
			ordinal = 1
	)
	public float applyInexorableEffects(float value) {
		if (isInexorableActive())
			return 1F;
		
		return value;
	}
	
	@ModifyReturnValue(method = "getDestroySpeed", at = @At("RETURN"))
	public float applyInexorableAntiSlowdowns(float original) {
		if (isInexorableActive()) {
			var player = (Player) (Object) this;
			var f = original;
			
			boolean hasAquaAffinity = SpectrumEnchantmentHelper.getEquipmentLevel(player.level().registryAccess(), Enchantments.AQUA_AFFINITY, player) > 0;
			if (player.isEyeInFluid(FluidTags.WATER) && !hasAquaAffinity)
				f *= 5;
			
			if (!player.onGround())
				f *= 5;
			
			return f;
		}
		
		return original;
		
	}
	
	@Inject(method = "stopSleepInBed", at = @At(value = "HEAD"))
	public void spectrum$applyWakeUpEffects(boolean skipSleepTimer, boolean updateSleepingPlayers, CallbackInfo ci) {
		var player = (Player) (Object) this;
		if (!player.level().isClientSide())
			MiscPlayerData.get(player).resetSleepingState(true);
	}
	
	@WrapOperation(method = "updatePlayerPose", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;setPose(Lnet/minecraft/world/entity/Pose;)V"))
	public void spectrum$forceSwimmingState(Player instance, Pose entityPose, Operation<Void> original) {
		var component = MiscPlayerData.get(instance);
		if ((component.shouldLieDown() || instance.hasEffect(SpectrumStatusEffects.FATAL_SLUMBER)) && canPlayerFitWithinBlocksAndEntitiesWhen(Pose.SWIMMING)) {
			instance.setPose(Pose.SWIMMING);
			return;
		}
		original.call(instance, entityPose);
	}
	
	@Unique
	private boolean isInexorableActive() {
		Player player = (Player) (Object) this;
		return SpectrumEnchantmentHelper.hasEnchantment(player.level().registryAccess(), SpectrumEnchantments.INEXORABLE, player.getItemInHand(player.getUsedItemHand()));
	}
	
}
