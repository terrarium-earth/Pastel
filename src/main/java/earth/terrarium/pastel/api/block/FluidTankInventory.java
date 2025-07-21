package earth.terrarium.pastel.api.block;

import earth.terrarium.pastel.capabilities.item.FriendlyStackHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

public interface FluidTankInventory {

    /**
     * Retrieves the item list of this inventory.
     * Must return the same instance every time it's called.
     */
    FriendlyStackHandler getInventory();

    FluidTank getTank();

    /**
     * Creates an inventory from the item list.
     */
    static FluidTankInventory of(FriendlyStackHandler items, FluidTank tank) {
        return new FluidTankInventory() {
            @Override
            public FriendlyStackHandler getInventory() {
                return items;
            }

            @Override
            public FluidTank getTank() {
                return tank;
            }
        };
    }

}
