package de.dafuqs.spectrum.registries.events;

import de.dafuqs.spectrum.attachments.*;
import de.dafuqs.spectrum.attachments.data.*;
import de.dafuqs.spectrum.attachments.data.azure_dike.*;
import de.dafuqs.spectrum.helpers.*;
import de.dafuqs.spectrum.items.trinkets.*;
import de.dafuqs.spectrum.progression.*;
import de.dafuqs.spectrum.registries.*;
import net.minecraft.advancements.*;
import net.minecraft.core.component.*;
import net.minecraft.server.level.*;
import net.minecraft.stats.*;
import net.minecraft.tags.*;
import net.minecraft.world.damagesource.*;
import net.minecraft.world.effect.*;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.item.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.*;
import top.theillusivec4.curios.api.*;

import java.util.*;
import java.util.concurrent.atomic.*;

public class SpectrumEntityEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::entityTick);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::playerTick);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::clonePlayer);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::allowDamage);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::canPlayerSleep);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::playerSleepEnd);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::equipmentChange);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::entityDeath);
        NeoForge.EVENT_BUS.addListener(SpectrumEntityEvents::finishUsingItem);
    }

    private static void playerWakeUp(PlayerWakeUpEvent event) {
        var player = event.getEntity();

        MiscPlayerData.get(player).resetSleepingState(false);
        player.removeEffect(SpectrumStatusEffects.SOMNOLENCE);
    }

    private static void playerTick(PlayerTickEvent.Post event) {
        MiscPlayerData.get(event.getEntity()).tick();
    }

    private static void entityTick(EntityTickEvent.Post event) {
        var entity = event.getEntity();

        if (entity instanceof LivingEntity living && !living.level().isClientSide()) {
            PrimordialFireData.serverTick(living);
            AzureDikeProvider.getAzureDikeComponent(living).serverTick(living);
        }
    }

    private static void clonePlayer(PlayerEvent.Clone event) {
        var original = event.getOriginal();
        AzureDikeData newDike;

        if (event.isWasDeath()) {
            newDike = AzureDikeData.CLONER.copy(AzureDikeProvider.getAzureDikeComponent(original), original, original.registryAccess());
        }
        else {
            newDike = AzureDikeProvider.getAzureDikeComponent(original);
        }

        event.getEntity().setData(AzureDikeData.ATTACHMENT, newDike);
    }

    private static void allowDamage(LivingIncomingDamageEvent event) {
        var entity = event.getEntity();
        var source = event.getSource();

        // If the player is damaged by lava and wears an ashen circlet:
        // prevent damage and grant fire resistance
        if (source.is(DamageTypes.LAVA)) {
            Optional<ItemStack> ashenCircletStack = SpectrumTrinketItem.getFirstEquipped(entity, SpectrumItems.ASHEN_CIRCLET.get());
            if (ashenCircletStack.isPresent()) {
                if (AshenCircletItem.getCooldownTicks(ashenCircletStack.get(), entity.level()) == 0) {
                    AshenCircletItem.grantFireResistance(ashenCircletStack.get(), entity);
                    event.setCanceled(true);
                }
            }
        } else if (source.is(DamageTypeTags.IS_FIRE) && SpectrumTrinketItem.hasEquipped(entity, SpectrumItems.ASHEN_CIRCLET.get())) {
            event.setCanceled(true);
        }
    }

    private static void canPlayerSleep(CanPlayerSleepEvent event) {
        var player = event.getEntity();
        var reason = event.getProblem();

        if (reason != Player.BedSleepingProblem.NOT_POSSIBLE_NOW && MiscPlayerData.get(player).isSleeping()) {
            event.setProblem(null);
        }
        else if((reason == Player.BedSleepingProblem.NOT_POSSIBLE_NOW || reason == Player.BedSleepingProblem.NOT_SAFE)
                && player.hasEffect(SpectrumStatusEffects.SOMNOLENCE)) { // Somnolence lets you sleep whenever and wherever.
            event.setProblem(null);
        }
    }

    private static void playerSleepEnd(PlayerWakeUpEvent event) {
        var player = event.getEntity();

        // If the player wears a Whispy Cirlcet and sleeps
        // they get fully healed and all negative status effects removed
        // When the sleep timer reached 100 the player is fully asleep

        if (player instanceof ServerPlayer serverPlayerEntity
                && serverPlayerEntity.getSleepTimer() == 100
                && SpectrumTrinketItem.hasEquipped(player, SpectrumItems.WHISPY_CIRCLET.get())) {

            player.setHealth(player.getMaxHealth());
            WhispyCircletItem.removeNegativeStatusEffects(player);
        }
    }

    private static void equipmentChange(LivingEquipmentChangeEvent event) {
        var livingEntity = event.getEntity();
        var oldEquipment = event.getFrom();
        var newEquipment = event.getTo();
        var equipmentSlot = event.getSlot();

        var oldInexorable = SpectrumEnchantmentHelper.getLevel(livingEntity.level().registryAccess(), SpectrumEnchantments.INEXORABLE, oldEquipment);
        var newInexorable = SpectrumEnchantmentHelper.getLevel(livingEntity.level().registryAccess(), SpectrumEnchantments.INEXORABLE, newEquipment);

        var effectType = equipmentSlot == EquipmentSlot.CHEST ? SpectrumAttributeTags.INEXORABLE_ARMOR_EFFECTIVE : SpectrumAttributeTags.INEXORABLE_HANDHELD_EFFECTIVE;

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
            entity.removeEffect(SpectrumStatusEffects.DEADLY_POISON);
    }

    private static void entityDeath(LivingDeathEvent event) {
        var killedEntity = event.getEntity();
        var killer = event.getSource().getEntity();
        var damageSource = event.getSource();

        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return;
        }


        var curios = CuriosApi.getCuriosInventory(killedEntity);

        if (curios.isPresent()) {
            var totem = curios.get().findFirstCurio(SpectrumItems.TOTEM_PENDANT.get());
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
            if (player.level().getLevelData().isHardcore() || HardcoreDeathTracker.isInHardcore(player)) {
                HardcoreDeathTracker.addHardcoreDeath(player.serverLevel(), player.getGameProfile());
            }
            evaluateAndDropPlayerHead(player, damageSource);
        }

        if (killer instanceof ServerPlayer serverPlayerEntity && SpectrumTrinketItem.hasEquipped(serverPlayerEntity, SpectrumItems.JEOPARDANT.get())) {
            SpectrumAdvancementCriteria.JEOPARDANT_KILL.trigger(serverPlayerEntity, killedEntity);
        }
    }

    private static void evaluateAndDropPlayerHead(ServerPlayer player, DamageSource source) {
        if (!player.isSpectator()) {
            // TODO: Can we evaluate a SpectrumLootPoolModifiers.treasureHunter() here instead?
            // code reuse is always nice
            ServerLevel serverWorld = player.serverLevel();

            boolean shouldDropHead = source.is(SpectrumDamageTypeTags.ALWAYS_DROPS_MOB_HEAD);
            if (!shouldDropHead && source.getEntity() instanceof LivingEntity livingAttacker) {
                int damageSourceTreasureHunt = SpectrumEnchantmentHelper.getEquipmentLevel(
                        serverWorld.registryAccess(),
                        SpectrumEnchantments.TREASURE_HUNTER,
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
}
