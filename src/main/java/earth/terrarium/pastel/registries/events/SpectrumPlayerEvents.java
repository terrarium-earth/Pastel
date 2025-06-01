package earth.terrarium.pastel.registries.events;

import earth.terrarium.pastel.api.item.*;
import earth.terrarium.pastel.attachments.data.*;
import earth.terrarium.pastel.attachments.data.azure_dike.*;
import earth.terrarium.pastel.items.tools.*;
import earth.terrarium.pastel.items.trinkets.*;
import earth.terrarium.pastel.registries.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.*;

public class SpectrumPlayerEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(SpectrumPlayerEvents::canPlayerSleep);
        NeoForge.EVENT_BUS.addListener(SpectrumPlayerEvents::playerWakeUp);
        NeoForge.EVENT_BUS.addListener(SpectrumPlayerEvents::playerSleepEnd);
        NeoForge.EVENT_BUS.addListener(SpectrumPlayerEvents::playerTick);
        NeoForge.EVENT_BUS.addListener(SpectrumPlayerEvents::clonePlayer);
        NeoForge.EVENT_BUS.addListener(SpectrumPlayerEvents::handleSplitMerge);
        NeoForge.EVENT_BUS.addListener(SpectrumPlayerEvents::forceCritical);
    }

    private static void forceCritical(CriticalHitEvent event) {
        var player = event.getEntity();
        var target = event.getTarget();
        var misc = MiscPlayerData.get(player);

        if (NectarLanceItem.sleepCrits(player, target) || misc.isParrying() || misc.isLunging()) {
            if (misc.isParrying())
                misc.setParryTicks(0);

            if (misc.consumePerfectCounter())
                event.setDamageMultiplier(event.getDamageMultiplier() + 0.5F);

            event.setCriticalHit(true);
        }

    }

    // This could be a capability but that is also probably overengineering this
    private static void handleSplitMerge(LivingSwapItemsEvent.Hands event) {
        var mainHand = event.getItemSwappedToOffHand();
        var offHand = event.getItemSwappedToMainHand();

        if (!(event.getEntity() instanceof ServerPlayer player))
            return;

        if (trySplit(event, player, mainHand, offHand))
            return;

        tryMerge(event, player, mainHand, offHand);
    }

    private static boolean trySplit(LivingSwapItemsEvent.Hands event, ServerPlayer player, ItemStack mainHand, ItemStack offHand) {
        ItemStack offering = ItemStack.EMPTY;
        SplittableItem splittable = null;

        if (mainHand.getItem() instanceof SplittableItem split && offHand.isEmpty()) {
            offering = mainHand;
            splittable = split;
        }
        else if (offHand.getItem() instanceof SplittableItem split && mainHand.isEmpty()) {
            offering = offHand;
            splittable = split;
        }

        if (splittable != null) {
            if (!splittable.canSplit(player, player.getUsedItemHand(), offering))
                return false;

            var splitStack = splittable.getSplitResult(player, offering);
            event.setItemSwappedToMainHand(splitStack.copy());
            event.setItemSwappedToOffHand(splitStack.copy());
            return true;
        }
        return false;
    }

    private static void tryMerge(LivingSwapItemsEvent.Hands event, ServerPlayer player, ItemStack mainHand, ItemStack offHand) {
        ItemStack firstHalf = ItemStack.EMPTY;
        ItemStack secondHalf = ItemStack.EMPTY;
        MergeableItem mergeable = null;
        boolean off = false;

        if (mainHand.getItem() instanceof MergeableItem merge) {
            firstHalf = mainHand;
            secondHalf = offHand;
            mergeable = merge;
        }
        else if (offHand.getItem() instanceof MergeableItem merge) {
            firstHalf = offHand;
            secondHalf = mainHand;
            off = true;
            mergeable = merge;
        }

        if (mergeable == null || !mergeable.canMerge(player, firstHalf, secondHalf))
            return;

        var mergeStack = mergeable.getMergeResult(player, firstHalf, secondHalf);

        if (off) {
            event.setItemSwappedToOffHand(mergeStack);
        } else {
            event.setItemSwappedToMainHand(mergeStack);
        }
    }

    private static void playerWakeUp(PlayerWakeUpEvent event) {
        var player = event.getEntity();

        MiscPlayerData.get(player).resetSleepingState(false);
        player.removeEffect(SpectrumStatusEffects.SOMNOLENCE);
    }

    private static void playerTick(PlayerTickEvent.Post event) {
        MiscPlayerData.get(event.getEntity()).tick();
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
}
