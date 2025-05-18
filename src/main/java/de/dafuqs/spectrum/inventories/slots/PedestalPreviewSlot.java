package de.dafuqs.spectrum.inventories.slots;

import de.dafuqs.spectrum.api.gui.SlotWithOnClickAction;
import de.dafuqs.spectrum.blocks.pedestal.PedestalBlockEntity;
import de.dafuqs.spectrum.helpers.Support;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.item.ItemStack;

public class PedestalPreviewSlot extends ReadOnlySlot implements SlotWithOnClickAction {
	public PedestalPreviewSlot(Container inventory, int index, int x, int y) {
		super(inventory, index, x, y);
	}
	
	@Override
	public ItemStack getItem() {
		if (this.container instanceof PedestalBlockEntity pedestalBlockEntity) {
			return pedestalBlockEntity.getCurrentCraftingRecipeOutput();
		}
		
		return ItemStack.EMPTY;
	}
	
	@Override
	public boolean onClicked(ItemStack heldStack, ClickAction type, Player player) {
		if (this.container instanceof PedestalBlockEntity pedestalBlockEntity) {
			if (player instanceof ServerPlayer serverPlayerEntity) {
				if (pedestalBlockEntity.currentRecipe != null) {
					Support.grantAdvancementCriterion(serverPlayerEntity, "fail_to_take_item_out_of_pedestal", "try_take_out_item_from_pedestal");
				}
			}
		}
		return false;
	}
}
