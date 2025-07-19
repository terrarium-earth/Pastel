package earth.terrarium.pastel.events;

import earth.terrarium.pastel.api.item.*;
import earth.terrarium.pastel.attachments.*;
import earth.terrarium.pastel.attachments.data.*;
import earth.terrarium.pastel.attachments.data.azure_dike.*;
import earth.terrarium.pastel.capabilities.PastelCapabilities;
import earth.terrarium.pastel.helpers.enchantments.*;
import earth.terrarium.pastel.items.tools.*;
import earth.terrarium.pastel.items.trinkets.*;
import earth.terrarium.pastel.progression.*;
import earth.terrarium.pastel.registries.*;
import net.minecraft.server.level.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.item.*;
import net.minecraft.world.level.*;
import net.neoforged.bus.api.*;
import net.neoforged.neoforge.common.*;
import net.neoforged.neoforge.event.entity.living.*;
import net.neoforged.neoforge.event.entity.player.*;
import net.neoforged.neoforge.event.tick.*;

public class PastelPlayerEvents {

    public static void register() {
        NeoForge.EVENT_BUS.addListener(PastelPlayerEvents::canPlayerSleep);
        NeoForge.EVENT_BUS.addListener(PastelPlayerEvents::playerWakeUp);
        NeoForge.EVENT_BUS.addListener(PastelPlayerEvents::playerSleepEnd);
        NeoForge.EVENT_BUS.addListener(PastelPlayerEvents::playerTick);
        NeoForge.EVENT_BUS.addListener(PastelPlayerEvents::clonePlayer);
        NeoForge.EVENT_BUS.addListener(PastelPlayerEvents::handleSplitMerge);
        NeoForge.EVENT_BUS.addListener(EventPriority.HIGH, PastelPlayerEvents::forceCritical);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelPlayerEvents::applyImprovedCritical);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelPlayerEvents::postPlayerDeath);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, PastelPlayerEvents::removeHardcoreDeath);
    }

    private static void absorbExperience(PlayerXpEvent.PickupXp event) {
        var player = event.getEntity();
        var orb = event.getOrb();

        for (ItemStack stack : player.getHandSlots()) {
            var storage = stack.getCapability(PastelCapabilities.Misc.XP, player.registryAccess());

            if (storage == null)
                continue;

            storage.insert(orb.getValue(), false);
            orb.discard();
        }
    }

    private static void removeHardcoreDeath(PlayerEvent.PlayerChangeGameModeEvent event) {
        var curMode = event.getCurrentGameMode();
        var newMode = event.getNewGameMode();
        var player = event.getEntity();

        if (newMode != GameType.SPECTATOR && curMode == GameType.SPECTATOR && HardcoreDeathTracker.hasHardcoreDeath(player.getGameProfile())) {
            HardcoreDeathTracker.removeHardcoreDeath(player.getGameProfile());
        }
    }

    private static void postPlayerDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer player) {

            if (PastelTrinketItem.hasEquipped(player, PastelItems.JEOPARDANT.get()))
                PastelAdvancementCriteria.JEOPARDANT_KILL.trigger(player, event.getEntity());
        }

        if (event.getEntity() instanceof ServerPlayer player) {
            if (player.level().getLevelData().isHardcore() || HardcoreDeathTracker.isInHardcore(player)) {
                HardcoreDeathTracker.addHardcoreDeath(player.serverLevel(), player);
            }
        }
    }

    private static void applyImprovedCritical(CriticalHitEvent event) {
        var player = event.getEntity();
        var icl = Ench.getLevel(player.level().registryAccess(), PastelEnchantments.IMPROVED_CRITICAL, event.getEntity().getMainHandItem());
        event.setDamageMultiplier(event.getDamageMultiplier() + ImprovedCriticalHelper.getAddtionalCritDamageMultiplier(icl));
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

            if (!event.isCriticalHit()) {
                event.setCriticalHit(true);
                event.setDamageMultiplier(event.getDamageMultiplier() + 0.5F);
            }
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

        if (!off) {
            event.setItemSwappedToMainHand(mergeStack);
            event.setItemSwappedToOffHand(ItemStack.EMPTY);
        } else {
            event.setItemSwappedToMainHand(ItemStack.EMPTY);
            event.setItemSwappedToOffHand(mergeStack);
        }
    }

    private static void playerWakeUp(PlayerWakeUpEvent event) {
        var player = event.getEntity();

        MiscPlayerData.get(player).resetSleepingState(false);
        player.removeEffect(PastelMobEffects.SOMNOLENCE);
    }

    private static void playerTick(PlayerTickEvent.Post event) {
        Player player = event.getEntity();
        MiscPlayerData.get(player).tick();
        InertiaData.tick(player);
    }

    private static void canPlayerSleep(CanPlayerSleepEvent event) {
        var player = event.getEntity();
        var reason = event.getProblem();

        if (reason != Player.BedSleepingProblem.NOT_POSSIBLE_NOW && MiscPlayerData.get(player).isSleeping()) {
            event.setProblem(null);
        }
        else if((reason == Player.BedSleepingProblem.NOT_POSSIBLE_NOW || reason == Player.BedSleepingProblem.NOT_SAFE)
                && player.hasEffect(PastelMobEffects.SOMNOLENCE)) { // Somnolence lets you sleep whenever and wherever.
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
                && PastelTrinketItem.hasEquipped(player, PastelItems.WHISPY_CIRCLET.get())) {

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
