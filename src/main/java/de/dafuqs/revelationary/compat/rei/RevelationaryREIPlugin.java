package de.dafuqs.revelationary.compat.rei;

import de.dafuqs.revelationary.api.revelations.CloakSetChanged;
import de.dafuqs.revelationary.config.RevelationaryConfig;
import me.shedaniel.rei.api.client.entry.filtering.base.BasicFilteringRule;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForge;

import java.util.Set;
import java.util.stream.Collectors;

@REIPluginClient
public class RevelationaryREIPlugin implements REIClientPlugin {
    @SuppressWarnings("UnstableApiUsage")
    private BasicFilteringRule.MarkDirty filteringRule;
    private static Set<Item> hiddenStacks = Set.of();

    public RevelationaryREIPlugin() {
        if (!RevelationaryConfig.get().HideCloakedEntriesFromRecipeViewers) return;
        NeoForge.EVENT_BUS.addListener(CloakSetChanged.class, (event) -> {
            hiddenStacks = event.getNewCloaks();
            //noinspection UnstableApiUsage
            filteringRule.markDirty();
        });
    }

    @Override
    public void registerBasicEntryFiltering(@SuppressWarnings("UnstableApiUsage") BasicFilteringRule<?> rule) {
        // not using .show to not interfere with other filtering rules
        if (!RevelationaryConfig.get().HideCloakedEntriesFromRecipeViewers) return;
        //noinspection UnstableApiUsage
        filteringRule = rule.hide(() ->
            hiddenStacks.stream()
                    .map(EntryStacks::of)
                    .collect(Collectors.toList())
        );
    }
}
