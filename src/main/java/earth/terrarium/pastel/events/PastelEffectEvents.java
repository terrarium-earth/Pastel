package earth.terrarium.pastel.events;

import earth.terrarium.pastel.helpers.StatusEffectHelper;
import earth.terrarium.pastel.injectors.MobEffectInstanceInjector;
import earth.terrarium.pastel.items.trinkets.AetherGracedNectarGlovesItem;
import net.minecraft.network.protocol.game.ClientboundUpdateMobEffectPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import static earth.terrarium.pastel.registries.PastelStatusEffects.*;

public class PastelEffectEvents {

    public static void register(IEventBus pastelBus) {
        pastelBus.addListener(EventPriority.LOW, PastelEffectEvents::applyEffectImmunity);
        pastelBus.addListener(EventPriority.LOWEST, PastelEffectEvents::applyNectarGlovesImmunity);

    }

    private static void applyEffectImmunity(MobEffectEvent.Applicable event) {
        if (!event.getApplicationResult())
            return; // Do not bother with this if the effect wouldn't be applied

        var proposal = event.getEffectInstance();
        var entity = event.getEntity();

        var immunity = entity.getEffect(IMMUNITY);

        if (immunity == null)
            return;

        if (!StatusEffectHelper.isIncurable(proposal)) {
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
            return;
        }

        if (drainImmunity(immunity, proposal.getAmplifier() + 1))
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

        if (StatusEffectHelper.isIncurable(proposal))
            cost *= 2;

        if (AetherGracedNectarGlovesItem.tryBlockEffect(entity, cost))
            event.setResult(MobEffectEvent.Applicable.Result.DO_NOT_APPLY);
    }

    /**
     * @return Whether immunity should be removed outright
     */
    private static boolean drainImmunity(MobEffectInstance immunity, float multiplier) {
        var cost = Math.round(600 * multiplier);
        if (immunity.getDuration() <= cost) {
            return true;
        }

        ((MobEffectInstanceInjector) immunity).setDuration(Math.max(5, immunity.getDuration() - cost));
        return false;
    }

    private static void updateEffectInClient(LivingEntity entity, MobEffectInstance effect) {
        if (!entity.level().isClientSide()) {
            ((ServerLevel) entity.level()).getChunkSource().broadcastAndSend(entity, new ClientboundUpdateMobEffectPacket(entity.getId(), effect, false));
        }
    }
}
