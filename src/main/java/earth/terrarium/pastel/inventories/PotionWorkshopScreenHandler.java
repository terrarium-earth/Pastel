package earth.terrarium.pastel.inventories;

import com.cmdpro.databank.DatabankUtils;
import earth.terrarium.pastel.blocks.potion_workshop.PotionWorkshopBlockEntity;
import earth.terrarium.pastel.inventories.slots.DisabledSlot;
import earth.terrarium.pastel.inventories.slots.ReagentSlot;
import earth.terrarium.pastel.inventories.slots.StackFilterSlot;
import earth.terrarium.pastel.registries.PastelAdvancements;
import earth.terrarium.pastel.registries.PastelItems;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PotionWorkshopScreenHandler extends AbstractContainerMenu {

    protected final Level world;
    private final Container inventory;
    private final ContainerData propertyDelegate;

    public PotionWorkshopScreenHandler(int syncId, Inventory playerInventory) {
        this(PastelScreenHandlerTypes.POTION_WORKSHOP, syncId, playerInventory);
    }

    public PotionWorkshopScreenHandler(
        int syncId, Inventory playerInventory, PotionWorkshopBlockEntity potionWorkshopBlockEntity,
        ContainerData propertyDelegate
    ) {
        this(
            PastelScreenHandlerTypes.POTION_WORKSHOP, syncId, playerInventory, potionWorkshopBlockEntity,
            propertyDelegate
        );
    }

    public PotionWorkshopScreenHandler(MenuType<?> type, int i, Inventory playerInventory) {
        this(
            type, i, playerInventory, new SimpleContainer(PotionWorkshopBlockEntity.INVENTORY_SIZE),
            new SimpleContainerData(3)
        );
    }

    protected PotionWorkshopScreenHandler(
        MenuType<?> type, int syncId, Inventory playerInventory, Container inventory, ContainerData propertyDelegate) {
        super(type, syncId);
        this.inventory = inventory;
        this.world = playerInventory.player.level();

        checkContainerDataCount(propertyDelegate, 3);
        this.propertyDelegate = propertyDelegate;
        this.addDataSlots(propertyDelegate);

        checkContainerSize(inventory, PotionWorkshopBlockEntity.INVENTORY_SIZE);
        inventory.startOpen(playerInventory.player);

        // mermaids gem slot
        this.addSlot(new StackFilterSlot(inventory, 0, 26, 85, PastelItems.MERMAIDS_GEM.get()));

        // base ingredient inventory
        this.addSlot(new Slot(inventory, 1, 134, 41));

        // ingredient slots
        this.addSlot(new Slot(inventory, 2, 26, 23));
        this.addSlot(new Slot(inventory, 3, 11, 42));
        this.addSlot(new Slot(inventory, 4, 41, 42));

        // reagent slots
        if (DatabankUtils.hasAdvancement(playerInventory.player, PastelAdvancements.FOURTH_BREWING_SLOT)) {
            this.addSlot(new ReagentSlot(inventory, 5, 51, 19));
            this.addSlot(new ReagentSlot(inventory, 6, 74, 19));
            this.addSlot(new ReagentSlot(inventory, 7, 97, 19));
            this.addSlot(new ReagentSlot(inventory, 8, 120, 19));
        } else {
            this.addSlot(new ReagentSlot(inventory, 5, 58, 19));
            this.addSlot(new ReagentSlot(inventory, 6, 85, 19));
            this.addSlot(new ReagentSlot(inventory, 7, 112, 19));
            this.addSlot(new DisabledSlot(inventory, 8, -2000, 19));
        }

        // output inventory
        for (int j = 0; j < 2; ++j) {
            for (int k = 0; k < 6; ++k) {
                this.addSlot(new Slot(inventory, 9 + k + j * 6, 62 + k * 18, 67 + j * 18));
            }
        }

        // player inventory
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, 120 + j * 18));
            }
        }

        // player hotbar
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 178));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return this.inventory.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack slotStackCopy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            slotStackCopy = slotStack.copy();
            if (index < PotionWorkshopBlockEntity.FIRST_INVENTORY_SLOT) {
                // workshop (not output inv)
                if (!this.moveItemStackTo(
                    slotStack, PotionWorkshopBlockEntity.FIRST_INVENTORY_SLOT + 12, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < PotionWorkshopBlockEntity.FIRST_INVENTORY_SLOT + 12) {
                // workshop (output inv)
                if (!this.moveItemStackTo(
                    slotStack, PotionWorkshopBlockEntity.FIRST_INVENTORY_SLOT + 12, this.slots.size(), false)) {
                    return ItemStack.EMPTY;
                }
                // from player inv
                // is reagent?
            } else if (!this.moveItemStackTo(
                slotStack, PotionWorkshopBlockEntity.FIRST_REAGENT_SLOT, PotionWorkshopBlockEntity.FIRST_INVENTORY_SLOT,
                false
            )) {
                if (!slotStack.isEmpty()) {
                    this.moveItemStackTo(slotStack, 0, PotionWorkshopBlockEntity.FIRST_REAGENT_SLOT, false);
                }
                return ItemStack.EMPTY;
                // others
            } else if (slotStack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return slotStackCopy;
    }

    public Container getInventory() {
        return this.inventory;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.inventory.stopOpen(player);
    }

    public int getBrewTime() {
        return this.propertyDelegate.get(0);
    }

    public int getMaxBrewTime() {
        return this.propertyDelegate.get(1);
    }

    public int getPotionColor() {
        return this.propertyDelegate.get(2);
    }
}
