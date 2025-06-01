package earth.terrarium.pastel.mixin;

import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import de.dafuqs.additionalentityattributes.AdditionalEntityAttributes;
import earth.terrarium.pastel.api.entity.PlayerEntityAccessor;
import earth.terrarium.pastel.api.item.ExperienceStorageItem;
import earth.terrarium.pastel.attachments.data.LastKillData;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.entity.entity.SpectrumFishingBobberEntity;
import earth.terrarium.pastel.helpers.SpectrumEnchantmentHelper;
import earth.terrarium.pastel.helpers.enchantments.ImprovedCriticalHelper;
import earth.terrarium.pastel.items.tools.LightGreatswordItem;
import earth.terrarium.pastel.items.tools.NectarLanceItem;
import earth.terrarium.pastel.items.trinkets.AttackRingItem;
import earth.terrarium.pastel.items.trinkets.SpectrumTrinketItem;
import earth.terrarium.pastel.progression.SpectrumAdvancementCriteria;
import earth.terrarium.pastel.registries.SpectrumDamageTypeTags;
import earth.terrarium.pastel.registries.SpectrumEnchantments;
import earth.terrarium.pastel.registries.SpectrumEntityAttributes;
import earth.terrarium.pastel.registries.SpectrumItems;
import earth.terrarium.pastel.registries.SpectrumSoundEvents;
import earth.terrarium.pastel.registries.SpectrumStatusEffects;
import earth.terrarium.pastel.status_effects.FrenzyStatusEffect;
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
	
	@Inject(method = "updateSwimming()V", at = @At("HEAD"), cancellable = true)
	public void spectrum$updateSwimming(CallbackInfo ci) {
		if (SpectrumTrinketItem.hasEquipped(this, SpectrumItems.RING_OF_DENSER_STEPS.get())) {
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
		if (SpectrumTrinketItem.hasEquipped(player, SpectrumItems.JEOPARDANT.get())) {
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
		if (stack.getItem() == SpectrumItems.DRACONIC_TWINSWORD.get() && getChanneling(stack) > 0) {
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
}
