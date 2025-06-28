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
}
