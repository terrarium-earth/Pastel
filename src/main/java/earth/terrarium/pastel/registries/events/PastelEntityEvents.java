package earth.terrarium.pastel.registries.events;

import earth.terrarium.pastel.api.item.ArmorPiercingHandler;
import earth.terrarium.pastel.api.item.SplitDamageHandler;
import earth.terrarium.pastel.attachments.data.*;
import earth.terrarium.pastel.attachments.data.azure_dike.*;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.helpers.*;
import earth.terrarium.pastel.items.tools.*;
import earth.terrarium.pastel.items.trinkets.*;
import earth.terrarium.pastel.registries.*;
import net.minecraft.advancements.*;
import net.minecraft.core.component.*;
import net.minecraft.server.level.*;
import net.minecraft.stats.*;
import net.minecraft.tags.*;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.minecraft.world.phys.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.tick.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public class PastelEntityEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::entityTick);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::allowDamage);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::equipmentChange);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::entityDeath);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::finishUsingItem);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::parryingSwordBlock);

        // I guess this is the damage corner now
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelEntityEvents::jeopardantBonus); // Process it as late as possible for a small amount of tomfoolery
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PastelEntityEvents::splitDamage);
        NeoForge.EVENT_BUS.addListener(PastelEntityEvents::splitDamage);
    }

    private static final Set<LivingEntity> RECURSIVE_TARGETS = new HashSet<>();
    private static void splitDamage(LivingIncomingDamageEvent event) {
        var target = event.getEntity();

        if (RECURSIVE_TARGETS.contains(target)) {
            event.getContainer().setPostAttackInvulnerabilityTicks(0); // We only do I-frames after all the partitions have been processed
            return;
        }

        var entity = event.getSource().getEntity();

        if (!(entity instanceof LivingEntity attacker) || event.getAmount() <= Mth.EPSILON)
            return;

        var weapon = attacker.getMainHandItem();

        if (weapon.isEmpty())
            return;

        var split = weapon.getCapability(PastelCapabilities.Miscellaneous.SPLIT_DAMAGE);

        if (split == null)
            return;

        RECURSIVE_TARGETS.add(target);

        var composition = split.getDamageComposition(attacker, target, weapon, event.getAmount());
        for (SplitDamageHandler.Partition partition : composition.get()) {
            target.hurt(partition.source(), partition.damage());
        }

        event.setAmount(0);
        RECURSIVE_TARGETS.remove(target);
    }

    private static void handlePiercing(LivingIncomingDamageEvent event) {
        var container = event.getContainer();
        var entity = event.getSource().getEntity();

        if (!(entity instanceof LivingEntity attacker))
            return;

        var weapon = attacker.getMainHandItem();

        if (weapon.isEmpty())
            return;

        if (weapon.getCapability(PastelCapabilities.Miscellaneous.SPLIT_DAMAGE) instanceof ArmorPiercingHandler ap) {
            var target = event.getEntity();

            container.addModifier(DamageContainer.Reduction.ENCHANTMENTS, (damageContainer, f) -> f * (1 - ap.getProtReduction(target, weapon)));
            container.addModifier(DamageContainer.Reduction.ARMOR, (damageContainer, f) -> f * (1 - ap.getDefenseMultiplier(target, weapon)));
        }
    }

    private static void jeopardantBonus(LivingDamageEvent.Pre event) {
        var entity = event.getSource().getEntity();

        if (!(entity instanceof LivingEntity attacker))
            return;

        if (PastelTrinketItem.hasEquipped(attacker, PastelItems.JEOPARDANT.get())) {
            event.setNewDamage((float) (event.getNewDamage() * (AttackRingItem.getAttackModifierForEntity(attacker) + 1)));
        }
    }

    private static void parryingSwordBlock(LivingShieldBlockEvent event) {
        var damage = event.getDamageContainer().getOriginalDamage();
        var shielder = event.getEntity();
        var weapon = shielder.getUseItem();

        if (!(weapon.getItem() instanceof ParryingSwordItem parryingSword))
            return;

        if (event.getDamageSource().is(PastelDamageTypeTags.BYPASSES_PARRYING)) {
            event.setBlocked(false);
            event.setBlockedDamage(0);
            return;
        }
        boolean perfect = false;

        if (!shielder.isBlocking() || !checkShieldFacing(event.getDamageSource(), event.getEntity(), -0.25))
            return; // Parrying swords have a tighter blocking range than shields

        var useTime = shielder.getTicksUsingItem();

        if (shielder instanceof Player player && parryingSword.canBluffParry(weapon, player, useTime)) {
            perfect = parryingSword.canPerfectParry(weapon, player, useTime);
            var misc = MiscPlayerData.get(player);
            misc.setParryTicks(15);

            if (perfect)
                misc.markForPerfectCounter();
        }

        if (parryingSword.canDeflect(event.getDamageSource(), perfect)) {
            var mult = parryingSword.getBlockingMultiplier(event.getDamageSource(), weapon, shielder, useTime);
            if (mult > 0)
                shielder.level().broadcastEntityEvent(shielder, (byte) 29); // without this, the shielding sound does not play on non-perfect parries

            event.setBlocked(true);
            event.setBlockedDamage(damage - damage * mult);
        }
    }

    private static void entityTick(EntityTickEvent.Post event) {
        var entity = event.getEntity();

        if (entity instanceof LivingEntity living && !living.level().isClientSide()) {
            PrimordialFireData.serverTick(living);
            AzureDikeProvider.getAzureDikeComponent(living).serverTick(living);
        }
    }

    private static void allowDamage(LivingIncomingDamageEvent event) {
        var entity = event.getEntity();
        var source = event.getSource();

        // If the player is damaged by lava and wears an ashen circlet:
        // prevent damage and grant fire resistance
        if (source.is(DamageTypes.LAVA)) {
            Optional<ItemStack> ashenCircletStack = PastelTrinketItem.getFirstEquipped(entity, PastelItems.ASHEN_CIRCLET.get());
            if (ashenCircletStack.isPresent()) {
                if (AshenCircletItem.getCooldownTicks(ashenCircletStack.get(), entity.level()) == 0) {
                    AshenCircletItem.grantFireResistance(ashenCircletStack.get(), entity);
                    event.setCanceled(true);
                }
            }
        } else if (source.is(DamageTypeTags.IS_FIRE) && PastelTrinketItem.hasEquipped(entity, PastelItems.ASHEN_CIRCLET.get())) {
            event.setCanceled(true);
        }
    }

    private static void equipmentChange(LivingEquipmentChangeEvent event) {
        var livingEntity = event.getEntity();
        var oldEquipment = event.getFrom();
        var newEquipment = event.getTo();
        var equipmentSlot = event.getSlot();

        var oldInexorable = PastelEnchantmentHelper.getLevel(livingEntity.level().registryAccess(), PastelEnchantments.INEXORABLE, oldEquipment);
        var newInexorable = PastelEnchantmentHelper.getLevel(livingEntity.level().registryAccess(), PastelEnchantments.INEXORABLE, newEquipment);

        var effectType = equipmentSlot == EquipmentSlot.CHEST ? PastelAttributeTags.INEXORABLE_ARMOR_EFFECTIVE : PastelAttributeTags.INEXORABLE_HANDHELD_EFFECTIVE;

        //TODO make inexorable use enchantment effects or something
        //TODO also move the enchantment cloaking logic from LivingEntityMixin into here
        if (oldInexorable > 0 && newInexorable <= 0) {
            livingEntity.getActiveEffects()
                    .stream()
                    .filter(instance -> {
                        AtomicBoolean result = new AtomicBoolean(false);
                        instance.getEffect().value().createModifiers(instance.getAmplifier(), (attribute, modifier) -> {
                            if (attribute.is(effectType))
                                result.set(true);
                        });
                        return result.get();
                    })
                    .forEach(instance -> instance.getEffect().value().onEffectStarted(livingEntity, instance.getAmplifier()));
        }

    }

    private static void finishUsingItem(LivingEntityUseItemEvent.Finish event) {
        var entity = event.getEntity();
        var item = event.getItem();

        if (item.is(Items.HONEY_BOTTLE))
            entity.removeEffect(PastelStatusEffects.DEADLY_POISON);
    }

    private static void entityDeath(LivingDeathEvent event) {
        var killedEntity = event.getEntity();
        var damageSource = event.getSource();

        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }

        var curios = CuriosApi.getCuriosInventory(killedEntity);

        if (curios.isPresent()) {
            var totem = curios.get().findFirstCurio(PastelItems.TOTEM_PENDANT.get());
            if (totem.isPresent()) {
                ItemStack totemStack = totem.get().stack();

                if (totemStack.getCount() > 0) {
                    // increase stat
                    if (killedEntity instanceof ServerPlayer serverPlayerEntity) {
                        serverPlayerEntity.awardStat(Stats.ITEM_USED.get(Items.TOTEM_OF_UNDYING));
                        CriteriaTriggers.USED_TOTEM.trigger(serverPlayerEntity, totemStack);
                    }
                    // consume pendant
                    totemStack.shrink(1);
                    // Heal and add effects
                    killedEntity.setHealth(1.0F);
                    killedEntity.removeAllEffects();
                    killedEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 1));
                    killedEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 100, 1));
                    killedEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                    killedEntity.level().broadcastEntityEvent(killedEntity, EntityEvent.TALISMAN_ACTIVATE);
                    event.setCanceled(true);
                    return;
                }
            }
        }

        if (killedEntity instanceof ServerPlayer player) { //At this point it can only be concluded that this bitch dead as hell
            evaluateAndDropPlayerHead(player, damageSource);
        }
    }

    private static void evaluateAndDropPlayerHead(ServerPlayer player, DamageSource source) {
        if (!player.isSpectator()) {
            // TODO: Can we evaluate a PastelLootPoolModifiers.treasureHunter() here instead?
            // code reuse is always nice
            ServerLevel serverWorld = player.serverLevel();

            boolean shouldDropHead = source.is(PastelDamageTypeTags.ALWAYS_DROPS_MOB_HEAD);
            if (!shouldDropHead && source.getEntity() instanceof LivingEntity livingAttacker) {
                int damageSourceTreasureHunt = PastelEnchantmentHelper.getEquipmentLevel(
                        serverWorld.registryAccess(),
                        PastelEnchantments.TREASURE_HUNTER,
                        livingAttacker);

                shouldDropHead = damageSourceTreasureHunt > 0 && serverWorld.getRandom().nextFloat() < 0.2 * damageSourceTreasureHunt;
            }

            if (shouldDropHead) {
                ItemStack headItemStack = new ItemStack(Items.PLAYER_HEAD);
                headItemStack.set(DataComponents.PROFILE, new ResolvableProfile(player.getGameProfile()));

                ItemEntity headEntity = new ItemEntity(serverWorld, player.getX(), player.getY(), player.getZ(), headItemStack);
                serverWorld.addFreshEntity(headEntity);
            }
        }
    }

    private static boolean checkShieldFacing(DamageSource damageSource, LivingEntity entity, double leniency) {
        Vec3 vec32 = damageSource.getSourcePosition();
        if (vec32 != null) {
            Vec3 vec3 = entity.calculateViewVector(0.0F, entity.getYHeadRot());
            Vec3 vec31 = vec32.vectorTo(entity.position());
            vec31 = new Vec3(vec31.x, 0.0, vec31.z).normalize();
            return vec31.dot(vec3) < leniency;
            // This is largely ripped from LivingEntity. Only real change is leniency.
            // Remember: 1 = facing the same direction, -1 = facing opposite directions
        }
        return false;
    }
}
