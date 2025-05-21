package de.dafuqs.spectrum.capabilities;

import net.minecraft.core.*;
import net.neoforged.neoforge.fluids.capability.*;
import net.neoforged.neoforge.items.*;

public interface SidedCapabilityProvider {

    default IItemHandler exposeItemHandlers(Direction dir) {
        return null;
    }

    default IFluidHandler exposeFluidHandlers(Direction dir) {
        return null;
    }
}