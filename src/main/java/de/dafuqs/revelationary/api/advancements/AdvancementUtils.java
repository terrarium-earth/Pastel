package de.dafuqs.revelationary.api.advancements;

import de.dafuqs.revelationary.advancement_criteria.AdvancementGottenCriterion;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.ServerAdvancementManager;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.BiConsumer;

public class AdvancementUtils {
    protected String namespace = "all";
    protected String path = "all";
    protected final ServerPlayer player;
    protected final ServerAdvancementManager advancementLoader;
    protected final PlayerAdvancements advancementTracker;

    protected AdvancementUtils(ServerPlayer player) {
        this.player = player;
        advancementLoader = player.getServer().getAdvancements();
        advancementTracker = player.getAdvancements();
    }

    public static AdvancementUtils forPlayer(ServerPlayer player) {
        return new AdvancementUtils(player);
    }

    public AdvancementUtils withNamespace(String namespace) {
        this.namespace = namespace;
        return this;
    }

    public AdvancementUtils withPath(String path) {
        this.path = path;
        return this;
    }

    public int grant() {
        return act(advancementTracker::award);
    }

    public int revoke() {
        return act(advancementTracker::revoke);
    }

    public int syncTo(ServerPlayer targetPlayer, boolean deleteOld) {
        var count = 0;
        var targetAdvancementTracker = targetPlayer.getAdvancements();

        if (deleteOld) {
            count += act(targetAdvancementTracker::revoke);
        }

        count += act((advancement, criterion) -> {
            if (advancementTracker.getOrStartProgress(advancement).isDone()) {
                targetAdvancementTracker.award(advancement, criterion);
            }
        });

        return count;
    }

    /**
     * Reprocesses all AdvancementGottenCriteria and fixes all instances
     * where the player has an advancement, but a criterion that is set to get triggered
     * by it is not granted. (like after you changed your mods advancement criteria in an update)
     * Can only use used on the logical server
     */
    public void reprocessUnlocks() {
        for (var advancement : advancementLoader.getAllAdvancements()) {
            if (advancement.id().getNamespace().equals(namespace) && !advancementTracker.getOrStartProgress(advancement).isDone()) {
                for (var criterionEntry : advancement.value().criteria().entrySet()) {
                    // 1: instanceof checks for null automatically
                    // 2: AdvancementGottenCriterion.Conditions will always have the appropriate ID, no need to check for that
                    if (criterionEntry.getValue().triggerInstance() instanceof AdvancementGottenCriterion.Conditions gottenConditions) {
                        var gottenAdvancement = advancementLoader.get(gottenConditions.getAdvancementIdentifier());
                        if (gottenAdvancement != null && advancementTracker.getOrStartProgress(gottenAdvancement).isDone()) {
                            advancementTracker.award(advancement, criterionEntry.getKey());
                        }
                    }
                }
            }
        }
    }

    protected int act(BiConsumer<AdvancementHolder, String> action) {
        var count = 0;

        for (var advancement : advancementLoader.getAllAdvancements()) {
            if (advancement.id().getNamespace().equals(namespace) || namespace.equals("all")) {
                if (advancement.id().getPath().startsWith(path) || path.equals("all")) {
                    count++;
                    for (var criterion : advancement.value().criteria().keySet()) {
                        action.accept(advancement, criterion);
                    }
                }
            }
        }

        return count;
    }

    @Deprecated
    public static int revokeAllAdvancements(ServerPlayer player, String namespace , String path) {
        return forPlayer(player).withNamespace(namespace).withPath(path).revoke();
    }

    @Deprecated
    public static int grantAllAdvancements(ServerPlayer player, String namespace , String path) {
        return forPlayer(player).withNamespace(namespace).withPath(path).grant();
    }

    @Deprecated
    public static int syncAdvancements(ServerPlayer sourcePlayer, ServerPlayer targetPlayer, String namespace, String path, Boolean deleteOld) {
        return forPlayer(sourcePlayer).withNamespace(namespace).withPath(path).syncTo(targetPlayer, deleteOld);
    }

    @Deprecated
    public static void reprocessAdvancementUnlocks(ServerPlayer player, String namespace) {
        forPlayer(player).withNamespace(namespace).reprocessUnlocks();
    }
}
