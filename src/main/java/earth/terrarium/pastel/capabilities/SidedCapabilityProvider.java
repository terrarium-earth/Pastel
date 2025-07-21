package earth.terrarium.pastel.capabilities;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;

public interface SidedCapabilityProvider {

    default IItemHandler exposeItemHandlers(Direction dir) {
        return null;
    }

    default IFluidHandler exposeFluidHandlers(Direction dir) {
        return null;
    }
}