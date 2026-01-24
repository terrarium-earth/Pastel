package earth.terrarium.pastel.events;

import earth.terrarium.pastel.api.item.ArmorPiercingHandler;
import earth.terrarium.pastel.api.item.SplitDamageHandler;
import earth.terrarium.pastel.attachments.data.ConsumptionRingData;
import earth.terrarium.pastel.attachments.data.JeopardantBonusData;
import earth.terrarium.pastel.attachments.data.LastKillData;
import earth.terrarium.pastel.attachments.data.azure_dike.AzureDikeProvider;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.items.trinkets.AttackRingItem;
import earth.terrarium.pastel.items.trinkets.ConsumptionRingItem;
import earth.terrarium.pastel.items.trinkets.PastelTrinketItem;
import earth.terrarium.pastel.items.trinkets.PuffCircletItem;
import earth.terrarium.pastel.mixin.accessors.LivingEntityAccessor;
import earth.terrarium.pastel.networking.s2c_payloads.PlayParticleWithPatternAndVelocityPayload;
import earth.terrarium.pastel.particle.VectorPattern;
import earth.terrarium.pastel.particle.effect.ColoredCraftingParticleEffect;
import earth.terrarium.pastel.registries.*;
import earth.terrarium.pastel.status_effects.FrenzyStatusEffect;
import it.unimi.dsi.fastutil.objects.Object2LongArrayMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.damagesource.DamageContainer;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import net.neoforged.neoforge.event.entity.living.ArmorHurtEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * The fact that we have enough damage debauchery to justify this is beautiful
 */
public class PastelDamageEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelDamageEvents::unblockableBypass);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PastelDamageEvents::handleUnblockable);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PastelDamageEvents::splitDamage);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelDamageEvents::rateLimitDamage);
        NeoForge.EVENT_BUS.addListener(PastelDamageEvents::updateRateLimits);
        NeoForge.EVENT_BUS.addListener(PastelDamageEvents::modifyArmorDamage);
        NeoForge.EVENT_BUS.addListener(PastelDamageEvents::handlePiercing);
        NeoForge.EVENT_BUS.addListener(PastelDamageEvents::vulnerability);
        NeoForge.EVENT_BUS.addListener(PastelDamageEvents::handlePuffCirclet);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelDamageEvents::applyKillBonuses);
        NeoForge.EVENT_BUS.addListener(PastelDamageEvents::fuckWithWards);
        NeoForge.EVENT_BUS.addListener(PastelDamageEvents::vampirism);
    }

    private static void vampirism(LivingDamageEvent.Post event) {
        if (event.getEntity()
                 .getType()
                 .is(PastelEntityTypeTags.SOULLESS)) return;
        var attacker = event.getSource()
                            .getDirectEntity();
        if (attacker instanceof ServerPlayer player && player.getData(ConsumptionRingData.ATTACHMENT)) {
            ConsumptionRingItem.applyOverheal(player, event.getNewDamage());
        }
    }

    private static void fuckWithWards(LivingDamageEvent.Post event) {
        if (event.getSource()
                 .is(PastelDamageTypeTags.DISRUPTS_WARDS)) return;
        var entity = event.getEntity();
        if (entity.hasEffect(MobEffects.DAMAGE_RESISTANCE)) {
            var oldResistance = entity.getEffect(MobEffects.DAMAGE_RESISTANCE);
            if (oldResistance == null || oldResistance.isInfiniteDuration()) return;
            var duration = oldResistance.getDuration() - 100;
            var potency = oldResistance.getAmplifier();
            entity.removeEffect(MobEffects.DAMAGE_RESISTANCE);
            entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, duration, potency));
        }
    }

    private static void applyKillBonuses(LivingDamageEvent.Pre event) {
        var entity = event.getSource()
                          .getEntity();

        if (!(entity instanceof LivingEntity attacker)) return;

        // should be a more performant check than iterating over your entire curios inv every time something takes
        // damage
        if (attacker.getData(JeopardantBonusData.ATTACHMENT)) {
            event.setNewDamage((float) (event.getNewDamage() * (AttackRingItem.getAttackModifierForEntity(attacker) +
                                                                1)));
        }

        LastKillData.rememberKillTick(
            attacker, entity.level()
                            .getGameTime()
        );
        var frenzy = attacker.getEffect(PastelMobEffects.FRENZY);
        if (frenzy != null) {
            ((FrenzyStatusEffect) frenzy.getEffect()
                                        .value()).onKill(attacker, frenzy.getAmplifier());
        }
    }

    private static final int RATE_COOLDOWN = 1;
    private static final Map<UUID, Object2LongArrayMap<UUID>> RATE_LIMITS = new HashMap<>();

    private static void rateLimitDamage(LivingIncomingDamageEvent event) {
        var target = event.getEntity();
        var source = event.getSource();
        var attacker = source.getEntity();

        if (attacker == null || !source.is(PastelDamageTypeTags.RATE_LIMITED)) return;

        if (event.getContainer()
                 .getNewDamage() < Mth.EPSILON) return;

        var memory = RATE_LIMITS.computeIfAbsent(attacker.getUUID(), u -> new Object2LongArrayMap<>());

        if (memory.containsKey(target.getUUID())) {
            event.setCanceled(true);
            return;
        }

        memory.put(
            target.getUUID(), target.level()
                                    .getGameTime()
        );
    }

    private static void updateRateLimits(ServerTickEvent.Pre event) {
        var time = event.getServer()
                        .getLevel(ServerLevel.OVERWORLD)
                        .getGameTime();

        var remove = new ArrayList<UUID>();

        for (UUID attacker : RATE_LIMITS.keySet()) {
            if (RATE_LIMITS.get(attacker)
                           .isEmpty()) remove.add(attacker);
        }

        remove.forEach(RATE_LIMITS::remove);
        remove.clear();

        for (Object2LongArrayMap<UUID> limits : RATE_LIMITS.values()) {
            for (var attacked : limits.object2LongEntrySet()) {

                if (time - attacked.getLongValue() > RATE_COOLDOWN) remove.add(attacked.getKey());
            }

            remove.forEach(limits::removeLong);
            remove.clear();
        }
    }

    private static void unblockableBypass(EntityInvulnerabilityCheckEvent event) {
        if (event.getSource()
                 .is(PastelDamageTypeTags.UNBLOCKABLE)) {
            event.setInvulnerable(false);
        }
    }

    private static void handleUnblockable(LivingIncomingDamageEvent event) {
        var target = event.getEntity();
        var container = event.getContainer();
        var damage = container.getOriginalDamage();
        var source = event.getSource();

        if (!source.is(PastelDamageTypeTags.UNBLOCKABLE) || target.isDeadOrDying()) return;

        target.setHealth(target.getHealth() - container.getOriginalDamage());
        if (target.isDeadOrDying()) {
            target.getCombatTracker()
                  .recordDamage(source, damage);
            target.die(source);
        }

        event.setCanceled(true); // No further processing required for this
    }

    private static final Set<LivingEntity> RECURSIVE_TARGETS = new HashSet<>();

    private static void splitDamage(LivingIncomingDamageEvent event) {
        var target = event.getEntity();

        var invuln = event.getContainer()
                          .getPostAttackInvulnerabilityTicks();
        if (RECURSIVE_TARGETS.contains(target)) {
            event.getContainer()
                 .setPostAttackInvulnerabilityTicks(
                     0); // We only do I-frames after all the partitions have been processed
            return;
        }

        var entity = event.getSource()
                          .getEntity();

        if (!(entity instanceof LivingEntity attacker) || event.getAmount() <= Mth.EPSILON) return;

        var weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        var split = weapon.getCapability(PastelCapabilities.Misc.SPLIT_DAMAGE);

        if (split == null) return;

        RECURSIVE_TARGETS.add(target);

        var composition = split.getDamageComposition(attacker, target, weapon, event.getAmount());
        for (SplitDamageHandler.Partition partition : composition.get()) {
            target.hurt(partition.source(), partition.damage());
        }

        RECURSIVE_TARGETS.remove(target);
        event.setAmount(0);
        event.getContainer()
             .setPostAttackInvulnerabilityTicks(invuln);
    }

    private static void vulnerability(LivingDamageEvent.Pre event) {
        var target = event.getEntity();
        var container = event.getContainer();

        var vuln = target.getEffect(PastelMobEffects.VULNERABILITY);

        if (vuln == null) return;

        container.setNewDamage(container.getNewDamage() * PastelMobEffects.vulnerabilityMod(vuln));
    }

    private static void modifyArmorDamage(ArmorHurtEvent event) {
        var entity = event.getEntity();
        var containers = ((LivingEntityAccessor) entity).getDamageContainers();
        if (containers == null) return;

        var container = containers.peek();

        if (container == null) return;

        var source = container.getSource();

        if (source.is(PastelDamageTypeTags.INCREASED_ARMOR_DAMAGE)) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                event.setNewDamage(slot, event.getNewDamage(slot) * 10F);
            }
        } else if (source.is(PastelDamageTypeTags.DOES_NOT_DAMAGE_ARMOR)) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                event.setNewDamage(slot, 0);
            }
        }
    }

    private static void handlePuffCirclet(LivingFallEvent event) {
        var entity = event.getEntity();

        if (!CuriosApi.getCuriosInventory(entity)
                      .map(i -> i.isEquipped(PastelItems.PUFF_CIRCLET.asItem()))
                      .orElse(false)) return;

        var damage = ((LivingEntityAccessor) entity).callCalculateFallDamage(
            event.getDistance(), event.getDamageMultiplier());
        var cost = Math.min(damage, PuffCircletItem.FALL_DAMAGE_NEGATING_COST);
        var random = entity.getRandom();

        if (cost <= 0 || AzureDikeProvider.getAzureDikeCharges(entity) < cost) return;

        AzureDikeProvider.absorbDamage(entity, cost);
        if (!entity.level()
                   .isClientSide()) {
            PlayParticleWithPatternAndVelocityPayload.playParticleWithPatternAndVelocity(
                null, (ServerLevel) entity.level(), entity.position(), ColoredCraftingParticleEffect.WHITE,
                VectorPattern.EIGHT, 0.4
            );
            PlayParticleWithPatternAndVelocityPayload.playParticleWithPatternAndVelocity(
                null,
                (ServerLevel) entity.level(),
                entity.position(),
                ColoredCraftingParticleEffect.BLUE,
                VectorPattern.EIGHT_OFFSET, 0.5
            );
        }
        entity.level()
              .playSound(
                  null, entity.blockPosition(), PastelSounds.PUFF_CIRCLET_PFFT, SoundSource.PLAYERS,
                  Support.varFloat(random, 0.2F), Support.varFloatCentered(random, 0.2F)
              );
        event.setCanceled(true);
    }

    private static void handlePiercing(LivingIncomingDamageEvent event) {
        var container = event.getContainer();
        var entity = event.getSource()
                          .getEntity();

        if (!(entity instanceof LivingEntity attacker)) return;

        var weapon = attacker.getMainHandItem();

        if (weapon.isEmpty()) return;

        if (weapon.getCapability(PastelCapabilities.Misc.SPLIT_DAMAGE) instanceof ArmorPiercingHandler ap) {
            var target = event.getEntity();

            container.addModifier(
                DamageContainer.Reduction.ENCHANTMENTS,
                (damageContainer, f) -> f * (1 - ap.getProtReduction(
                    target, weapon))
            );
            container.addModifier(
                DamageContainer.Reduction.ARMOR,
                (damageContainer, f) -> f * (1 - ap.getDefenseMultiplier(
                    target, weapon))
            );
        }
    }
}
