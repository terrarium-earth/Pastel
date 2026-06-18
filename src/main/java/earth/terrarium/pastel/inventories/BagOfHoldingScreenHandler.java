package earth.terrarium.pastel.inventories;

import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ClickType;

public class BagOfHoldingScreenHandler extends ChestMenu {

    public BagOfHoldingScreenHandler(int syncId, Inventory playerInventory) {
        this(syncId, playerInventory, new SimpleContainer(9 * 3));
    }

    public BagOfHoldingScreenHandler(int syncId, Inventory playerInventory, Container inventory) {
        super(PastelScreenHandlerTypes.BAG_OF_HOLDING, syncId, playerInventory, inventory, 3);
    }

    @Override
    public void clicked(int slotIndex, int button, ClickType actionType, Player player) {
        if (slotIndex > 0 && isValidSlotIndex(slotIndex) && this.slots
            .get(slotIndex)
            .getItem()
            .is(PastelItems.BAG_OF_HOLDING.get())) {
            return;
        }
        super.clicked(slotIndex, button, actionType, player);
    }

}
