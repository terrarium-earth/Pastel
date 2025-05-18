package de.dafuqs.revelationary.api.revelations;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Item;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
@FunctionalInterface
public interface CloakSetChanged {
    Event<CloakSetChanged> EVENT = EventFactory.createArrayBacked(CloakSetChanged.class,
            (listeners) -> (addedCloaks, removedCloaks, newCloaks) -> {
                Minecraft.getInstance().execute(() -> {
                    for (CloakSetChanged listener : listeners) listener.onChange(addedCloaks, removedCloaks, newCloaks);
                });
            });
    // the diffs matter for JEI, the new cloaks set matters for REI
    void onChange(Set<Item> addedCloaks, Set<Item> removedCloaks, Set<Item> newCloaks);
}
