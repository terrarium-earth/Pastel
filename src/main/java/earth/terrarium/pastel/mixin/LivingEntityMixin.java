package earth.terrarium.pastel.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import earth.terrarium.pastel.PastelCommon;
import earth.terrarium.pastel.api.entity.TouchingWaterAware;
import earth.terrarium.pastel.api.item.SlotReservingItem;
import earth.terrarium.pastel.attachments.data.CitrineJumpsAttachment;
import earth.terrarium.pastel.attachments.data.EverpromiseRibbonData;
import earth.terrarium.pastel.attachments.data.HookshotData;
import earth.terrarium.pastel.attachments.data.MiscPlayerData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.blocks.memory.MemoryItem;
import earth.terrarium.pastel.components.PairedFoodComponent;
import earth.terrarium.pastel.helpers.enchantments.Ench;
import earth.terrarium.pastel.helpers.enchantments.InexorableHelper;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.items.armor.CrystalArmorItem;
import earth.terrarium.pastel.items.tools.ParryingSwordItem;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.items.trinkets.RingOfAerialGraceItem;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.status_effects.EffectProlongingStatusEffect;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
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

import java.util.Stack;

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
    public abstract void remove(Entity.RemovalReason reason);

    @Shadow
    public abstract void travel(Vec3 movementInput);

    @Shadow
    protected ItemStack useItem;

    @Shadow
    public abstract double getAttributeValue(Holder<Attribute> attribute);

    @Shadow
    public boolean dead;

    @Shadow
    protected Stack<DamageContainer> damageContainers;

    @Inject(method = "createLivingAttributes", require = 1, allow = 1, at = @At("RETURN"))
    private static void addAttributes(final CallbackInfoReturnable<AttributeSupplier.Builder> cir) {
        cir.getReturnValue()
           .add(PastelEntityAttributes.MENTAL_PRESENCE);
    }

    @ModifyArg(method = "dropExperience", at = @At(value = "INVOKE",
                                                   target = "Lnet/minecraft/world/entity/ExperienceOrb;award" +
                                                            "(Lnet/minecraft/server/level/ServerLevel;" +
                                                            "Lnet/minecraft/world/phys/Vec3;I)V"),
               index = 2)
    protected int applyExuberance(int originalXP) {
        return (int) (originalXP * getExuberanceMod(this.lastHurtByPlayer));
    }

    @Unique
    private float getExuberanceMod(Player attackingPlayer) {
        if (attackingPlayer != null) {
            int exuberanceLevel = Ench.getEquipmentLevel(
                attackingPlayer.level()
                               .registryAccess(), PastelEnchantments.EXUBERANCE, attackingPlayer
            );
            return 1.0F + exuberanceLevel * PastelCommon.CONFIG.ExuberanceBonusExperiencePercentPerLevel;
        } else {
            return 1.0F;
        }
    }

    @WrapOperation(method = "travel", at = @At(value = "INVOKE", target="Ljava/lang/Math;min(DD)D",ordinal = 0))
    private double noSlowFallingSlowdown(double gravity, double slowdown, Operation<Double> original) {
        if(InexorableHelper.isArmorActive((LivingEntity) (Object) this)){
            return gravity;
        }
        return original.call(gravity,slowdown);
    }

    @Inject(method = "travel",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;shouldDiscardFriction()Z"))
    private void travel(CallbackInfo ci, @Local(ordinal = 1) LocalFloatRef f) {
        var entity = (LivingEntity) (Object) this;
        var override = false;
        var friction = -1F;

        if (SlotReservingItem.isReservingSlot(this.getMainHandItem()) || SlotReservingItem.isReservingSlot(
            this.getOffhandItem())) {
            if (!(entity).onGround()) {
                friction = 0.945F;
                override = true;
            }
        }

        if (!entity.onGround()) {
            var optionalTrinket = PastelTrinketItem.getFirstEquipped(entity, PastelItems.RING_OF_AETHERIAL_GRACE.get());
            if (optionalTrinket.isPresent()) {
                var inkStorage = PastelItems.RING_OF_AETHERIAL_GRACE.get()
                                                                 .getEnergyStorage(optionalTrinket.get());
                var storedInk = inkStorage.getEnergy(inkStorage.getStoredColor());
                friction = (float) Math.max(
                    friction, f.get() + (((RingOfAerialGraceItem) PastelItems.RING_OF_AETHERIAL_GRACE.get()).getBonus(
                        storedInk) / 150F)
                );
                override = true;
            }
        }

        if (entity instanceof Player player) {
            if (override) {
                friction += MiscPlayerData.get(player)
                                          .getFrictionModifiers();
            } else {
                friction = Math.min(
                    f.get() + MiscPlayerData.get(player)
                                            .getFrictionModifiers(), 0.99F);
            }
        }

        if (friction >= 0)
            f.set(Math.min(friction, 0.99F));
    }

    @ModifyExpressionValue(method = "travel", at = @At(value = "INVOKE",
                                                       target = "Lnet/minecraft/world/level/block/state/BlockState;" +
                                                                "getFriction(Lnet/minecraft/world/level/LevelReader;" +
                                                                "Lnet/minecraft/core/BlockPos;" +
                                                                "Lnet/minecraft/world/entity/Entity;)F"))
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

        if (PastelTrinketItem.hasEquipped(entity, PastelItems.RING_OF_AETHERIAL_GRACE.get()))
            return !entity.isUnderWater();

        return original;
    }

    @ModifyExpressionValue(method = "isBlocking", at = @At(value = "INVOKE",
                                                           target = "Lnet/minecraft/world/item/Item;getUseDuration" +
                                                                    "(Lnet/minecraft/world/item/ItemStack;" +
                                                                    "Lnet/minecraft/world/entity/LivingEntity;)I"))
    private int allowInstantBlockForParryingSwords(int original) {
        if (useItem.getItem() instanceof ParryingSwordItem)
            return Integer.MAX_VALUE;

        return original;
    }

    @WrapOperation(method = "handleEntityEvent", at = @At(value = "INVOKE",
                                                          target = "net/minecraft/world/entity/LivingEntity.playSound" +
                                                                   " (Lnet/minecraft/sounds/SoundEvent;FF)V",
                                                          ordinal = 1))
    private void swapBlockSound(
        LivingEntity instance, SoundEvent soundEvent, float v, float p, Operation<Void> original) {
        if (!(instance.getUseItem()
                      .getItem() instanceof ParryingSwordItem parryingSword)) {
            original.call(instance, soundEvent, v, p);
            return;
        }

        if (instance.getTicksUsingItem() <= parryingSword.getPerfectParryWindow(instance, instance.getUseItem())) {
            original.call(
                instance, PastelSounds.PERFECT_PARRY, 1.75F, 0.9F + instance.level().random.nextFloat() * 0.3F);
            original.call(
                instance, PastelSounds.SWORD_BLOCK, 0.667F, 0.5F + instance.level().random.nextFloat() * 0.3F);
        } else {
            original.call(
                instance, PastelSounds.SWORD_BLOCK, 1.0F, 0.8F + instance.level().random.nextFloat() * 0.4F);
        }
    }

    @ModifyReturnValue(method = "onClimbable", at = @At("RETURN"))
    private boolean hookshotClimb(boolean original) {
        if (original)
            return true;

        if (((Object) this) instanceof Player player) {
            var hookData = HookshotData.get(player);
            var hook = hookData.getHookEntity(player.level());

            if (hook == null)
                return original;

            var yDif = Math.abs(hook.getY() - player.getEyeY());
            if (yDif > 3 + Mth.EPSILON)
                return false;

            for (Direction dir : Direction.values()) {
                if (dir.getAxis()
                       .isVertical())
                    continue;

                return player.horizontalCollision;
            }
        }

        return false;
    }

    @Inject(
        method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;" +
                 "Lnet/minecraft/world/food/FoodProperties;)Lnet/minecraft/world/item/ItemStack;",
        at = @At(value = "INVOKE",
                 target = "Lnet/minecraft/world/entity/LivingEntity;addEatEffect" +
                          "(Lnet/minecraft/world/food/FoodProperties;)V"))
    private void applyConcealedEffects(
        Level world, ItemStack stack, FoodProperties foodComponent, CallbackInfoReturnable<ItemStack> cir) {
        var oilEffect = stack.get(PastelDataComponentTypes.CONCEALED_EFFECT);
        if (!world.isClientSide() && oilEffect != null)
            ((LivingEntity) (Object) this).addEffect(oilEffect);
    }

    /**
     * We do not force player sleeping because that would do funny things to the sleep cycle
     */
    @ModifyReturnValue(method = "isSleeping", at = @At("RETURN"))
    private boolean forceSleepingState(boolean original) {
        if (original)
            return true;

        if (hasEffect(PastelMobEffects.ETERNAL_SLUMBER) || hasEffect(PastelMobEffects.FATAL_SLUMBER))
            return !(((LivingEntity) (Object) this) instanceof Player);

        return false;
    }

    @ModifyVariable(method = "setSprinting(Z)V", at = @At("HEAD"), argsOnly = true)
    private boolean setSprinting(boolean sprinting) {
        var entity = (LivingEntity) (Object) this;
        if (sprinting && entity.hasEffect(PastelMobEffects.SCARRED)) {
            return false;
        }
        return sprinting;
    }

    @Inject(
        method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/food/FoodProperties;)" +
                 "Lnet/minecraft/world/item/ItemStack;",
        at = @At(value = "HEAD"))
    private void conditionalFood(Level world, ItemStack stack, FoodProperties foodProperties, CallbackInfoReturnable<ItemStack> cir) {
        PairedFoodComponent component = stack.get(PastelDataComponentTypes.PAIRED_FOOD_COMPONENT);
        if (component != null) {
            component.tryEatFood(world, (LivingEntity) (Object) this, stack);
        }
    }

    @Inject(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z",
            at = @At(value = "INVOKE", target = "Ljava/util/Map;get(Ljava/lang/Object;)Ljava/lang/Object;"))
    private void addStatusEffect(MobEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if (EffectProlongingStatusEffect.canBeExtended(effect.getEffect())) {
            MobEffectInstance effectProlongingInstance = this.getEffect(PastelMobEffects.EFFECT_PROLONGING);
            if (effectProlongingInstance != null) {
                ((MobEffectInstanceInjector) effect).setDuration(
                    EffectProlongingStatusEffect.getExtendedDuration(
                        effect.getDuration(),
                        effectProlongingInstance.getAmplifier()
                    ));
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
            ItemEntity itemEntity = new ItemEntity(
                thisEntity.level(), entityPos.x(), entityPos.y(), entityPos.z(), memoryStack);
            thisEntity.level()
                      .addFreshEntity(itemEntity);

            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    protected void applyInexorableEffects(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (entity.level() != null && entity.level()
                                            .getGameTime() % 20 == 0) {
            InexorableHelper.checkAndRemoveSlowdownModifiers(entity);
        }
    }

    @Redirect(method = "aiStep",
              at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWaterRainOrBubble()Z"))
    private boolean isWet(LivingEntity livingEntity) {
        return livingEntity.isInWater() ? ((TouchingWaterAware) livingEntity).isActuallyTouchingWater()
                                        : livingEntity.isInWaterRainOrBubble();
    }

    @WrapOperation(at = @At(value = "INVOKE", target = "net/minecraft/world/entity/LivingEntity.actuallyHurt(Lnet/minecraft/world/damagesource/DamageSource;F)V"), method = "hurt")
    private void applyDike(LivingEntity instance, DamageSource source, float amount, Operation<Void> original) {
        if (source.is(PastelDamageTypeTags.BYPASSES_DIKE)) {
            original.call(instance, source, amount);
            return;
        }
        var container = this.damageContainers.peek();
        var passedDamage = AzureDikeProvider.absorbDamage(instance, container.getNewDamage());
        container.setNewDamage(passedDamage);
        instance.actuallyHurt(source, passedDamage);
    }

    @Redirect(method="aiStep",
                   at = @At(value = "INVOKE",
                            target = "net/minecraft/world/entity/LivingEntity.onGround()Z",
                            ordinal = 0))
    private boolean doubleJump(LivingEntity instance){
        return true; // todo
    }
}
