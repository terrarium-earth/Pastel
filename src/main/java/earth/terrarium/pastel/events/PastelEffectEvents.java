package earth.terrarium.pastel.events;

import earth.terrarium.pastel.helpers.level.MobEffectHelper;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.items.trinkets.AetherGracedNectarGlovesItem;
import earth.terrarium.pastel.registries.PastelDamageTypes;
import earth.terrarium.pastel.registries.PastelMobEffectTags;
import earth.terrarium.pastel.registries.PastelMobEffects;
import earth.terrarium.pastel.status_effects.SleepStatusEffect;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static earth.terrarium.pastel.registries.PastelMobEffects.*;

public class PastelEffectEvents {

    public static void register(IEventBus pastelBus) {
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PastelEffectEvents::sleepDurationModifications);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGHEST, PastelEffectEvents::fatalSlumberKill);
        NeoForge.EVENT_BUS.addListener(PastelEffectEvents::actionIncurable);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, PastelEffectEvents::applyEffectImmunity);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelEffectEvents::convertSleepEffects);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelEffectEvents::applyNectarGlovesImmunity);
    }

    private static void fatalSlumberKill(MobEffectEvent.Expired event) {
        var effect = event.getEffectInstance();
        var entity = event.getEntity();
        var level = entity.level();
        var player = entity instanceof Player;

        if (event.getEffectInstance() == null || !effect.getEffect()
                                                        .equals(FATAL_SLUMBER) || level.isClientSide())
            return;

        if (entity.isSpectator() || player && ((Player) entity).isCreative())
            return;

        var damage = 777777777F;
        if (SleepStatusEffect.isResistedBy(entity)) {
            if (entity.getType()
                      .is(Tags.EntityTypes.BOSSES))
                damage = entity.getHealth() * 0.34F;
            else
                damage = entity.getHealth() * 0.95F;
        }

        entity.hurt(PastelDamageTypes.sleep(level, null), damage);
        if (entity.isAlive() && entity instanceof ServerPlayer sPlayer) {
            Support.grantAdvancementCriterion(sPlayer, "lategame/survive_fatal_slumber", "survived_fatal_slumber");
        }
    }

    private static void actionIncurable(MobEffectEvent.Remove event) {
        var removed = event.getEffectInstance();
        var entity = event.getEntity();
        var cure = event.getCure();

        if (removed == null || !MobEffectHelper.resistsRemoval(removed))
            return;

        if (cure.equals(Cures.SEDATIVES)) {
            return; // Incurable does not prevent Sedatives from working
        }

        cutDuration(entity, removed); // Reduce effect duration and prevent the removal
        event.setCanceled(true);
    }

    static final Map<UUID, List<MobEffectInstance>> QUEUED_ADDITIONS = new HashMap<>();

    private static void convertSleepEffects(MobEffectEvent.Remove event) {
        var removed = event.getEffectInstance();
        var id = event.getEntity()
                      .getUUID();

        if (removed == null)
            return;

        if (removed.getEffect()
                   .equals(FATAL_SLUMBER)) {
            QUEUED_ADDITIONS.computeIfAbsent(id, i -> new ArrayList<>())
                            .add(new MobEffectInstance(PastelMobEffects.ETERNAL_SLUMBER, 6000));
            return;
        }

        if (!Cures.SEDATIVES.equals(event.getCure()))
            return;

        if (removed.getEffect()
                   .equals(SOMNOLENCE)) {
            QUEUED_ADDITIONS.computeIfAbsent(id, i -> new ArrayList<>())
                            .add(new MobEffectInstance(PastelMobEffects.ETERNAL_SLUMBER, removed.getDuration()));
        } else if (removed.getEffect()
                          .equals(CALMING)) {
            QUEUED_ADDITIONS.computeIfAbsent(id, i -> new ArrayList<>())
                            .add(new MobEffectInstance(SOMNOLENCE, removed.getDuration()));
        } // Miniscule amount of trolling
    }

    private static void applyEffectImmunity(MobEffectEvent.Applicable event) {
        if (!event.getApplicationResult())
            return; // Do not bother with this if the effect wouldn't be applied

        var proposal = event.getEffectInstance();
        var entity = event.getEntity();

        var immunity = entity.getEffect(IMMUNITY);

        if (immunity == null || proposal.getEffect()
                                        .is(PastelMobEffectTags.BYPASSES_IMMUNITY))
            return;

        if (!MobEffectHelper.resistsRemoval(proposal)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            return;
        }

        if (MobEffectHelper.drainImmunity(immunity, proposal.getAmplifier() + 1))
            entity.removeEffect(IMMUNITY);

        event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
        updateEffectInClient(entity, immunity);
    }

    private static void applyNectarGlovesImmunity(MobEffectEvent.Applicable event) {
        var proposal = event.getEffectInstance();
        var entity = event.getEntity();

        if (!event.getApplicationResult() || !AetherGracedNectarGlovesItem.testEffectFor(entity, proposal.getEffect()))
            return;

        var cost = (proposal.getAmplifier() + 1) * AetherGracedNectarGlovesItem.HARMFUL_EFFECT_COST;

        if (MobEffectHelper.resistsRemoval(proposal))
            cost *= 2;

        if (AetherGracedNectarGlovesItem.tryBlockEffect(entity, cost))
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }

    private static void sleepDurationModifications(MobEffectEvent.Added event) {
        var effect = event.getEffectInstance();
        var modifiable = (MobEffectInstanceInjector) effect;
        var entity = event.getEntity();

        var resistant = SleepStatusEffect.isResistedBy(entity);
        var sleepResist = Mth.clamp(SleepStatusEffect.getSleepResistance(effect, entity), 0.1F, 10F);
        boolean actioned = false;

        if (effect.getEffect()
                  .equals(ETERNAL_SLUMBER)) {
            sleepEternal(resistant, modifiable, effect, sleepResist);
            actioned = true;
        } else if (effect.getEffect()
                         .equals(FATAL_SLUMBER)) {
            sleepFatal(
                resistant && entity.getType()
                                   .is(Tags.EntityTypes.BOSSES), modifiable, effect, sleepResist
            );
            actioned = true;
        }

        if (actioned)
            updateEffectInClient(entity, effect);
    }

    private static void sleepEternal(
        boolean resistant, MobEffectInstanceInjector modifiable, MobEffectInstance effect, float sleepResist) {
        if (resistant) {
            modifiable.setDuration(Math.round(effect.getDuration() / sleepResist));
        } else {
            modifiable.setDuration(MobEffectInstance.INFINITE_DURATION);
        }
    }

    private static void sleepFatal(
        boolean applicableBoss, MobEffectInstanceInjector modifiable, MobEffectInstance effect, float sleepResist) {
        if (applicableBoss) {
            modifiable.setDuration(120 * 20);
        } // Two minutes
        else {
            modifiable.setDuration(Math.max(200, Math.round(effect.getDuration() * sleepResist * 3)));
        } // Fatal slumber never lasts less than 10 seconds
    }

    private static void updateEffectInClient(LivingEntity entity, MobEffectInstance effect) {
        if (!entity.level()
                   .isClientSide()) {
            ((ServerLevel) entity.level()).getChunkSource()
                                          .broadcastAndSend(
                                              entity, new ClientboundUpdateMobEffectPacket(
                                                  entity.getId(), effect,
                                                  false
                                              )
                                          );
        }
    }
}
