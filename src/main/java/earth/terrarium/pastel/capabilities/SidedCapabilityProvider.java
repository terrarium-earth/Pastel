package earth.terrarium.pastel.capabilities;

import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.ApiStatus;

public interface SidedCapabilityProvider {

    default IItemHandler exposeItemHandlers(Direction dir) {
        return null;
    }

    default IFluidHandler exposeFluidHandlers(Direction dir) {
        return null;
    }

    @ApiStatus.Internal
    default IItemHandler exposeItemHandlersChecked(Direction dir) {
        if (dir == null)
            return null;

        return exposeItemHandlers(dir);
    }

    @ApiStatus.Internal
    default IFluidHandler exposeFluidHandlersChecked(Direction dir) {
        if (dir == null)
            return null;

        return exposeFluidHandlers(dir);
    }
}
