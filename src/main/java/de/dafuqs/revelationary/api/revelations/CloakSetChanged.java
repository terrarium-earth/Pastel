package de.dafuqs.revelationary.api.revelations;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.Event;

import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class CloakSetChanged extends Event {
    private final Set<Item> addedCloaks;
    private final Set<Item> removedCloaks;
    private final Set<Item> newCloaks;

    // TODO: This event was seemingly never invoked? but if it is invoked in the future, the previous invoker was wrapped in Minecraft.getInstance().execute
    // the diffs matter for JEI, the new cloaks set matters for REI
    public CloakSetChanged(Set<Item> addedCloaks, Set<Item> removedCloaks, Set<Item> newCloaks) {
        this.addedCloaks = addedCloaks;
        this.removedCloaks = removedCloaks;
        this.newCloaks = newCloaks;
    }

    public Set<Item> getAddedCloaks() {
        return addedCloaks;
    }

    public Set<Item> getRemovedCloaks() {
        return removedCloaks;
    }

    public Set<Item> getNewCloaks() {
        return newCloaks;
    }
}
