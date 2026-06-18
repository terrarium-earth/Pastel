package earth.terrarium.pastel.inventories.slots;

import earth.terrarium.pastel.api.gui.SlotWithOnClickAction;
import earth.terrarium.pastel.blocks.pedestal.PedestalBlockEntity;
import earth.terrarium.pastel.helpers.Support;
import earth.terrarium.pastel.recipe.pedestal.PedestalRecipe;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;

public class PedestalPreviewSlot extends ReadOnlySlot implements SlotWithOnClickAction {
    public PedestalPreviewSlot(PedestalBlockEntity pedestal, int index, int x, int y) {
        super(pedestal, index, x, y);
    }

    @Override
    public ItemStack getItem() {
        var out = ItemStack.EMPTY;
        var pedestal = (PedestalBlockEntity) container;
        var registries = pedestal.getLevel().registryAccess();

        if (pedestal.recipe.isEmpty())
            return out;

        var rec = pedestal.recipe.get().value();

        if (rec instanceof PedestalRecipe pr) {
            if (pr.matches(pedestal.getInput(), pedestal.getLevel()))
                out = pr.getResultItem(registries);
        } else {
            if (rec.matches(pedestal.getInput().getCraftingGridInput(), pedestal.getLevel()))
                out = rec.assemble(pedestal.getInput().getCraftingGridInput(), registries);
        }

        return out;
    }

    @Override
    public boolean onClicked(ItemStack heldStack, ClickAction type, Player player) {
        if (this.container instanceof PedestalBlockEntity pedestalBlockEntity) {
            if (player instanceof ServerPlayer serverPlayerEntity) {
                if (pedestalBlockEntity.recipe.isPresent()) {
                    Support
                        .grantAdvancementCriterion(
                            serverPlayerEntity,
                            "fail_to_take_item_out_of_pedestal",
                            "try_take_out_item_from_pedestal"
                        );
                }
            }
        }
        return false;
    }
}
