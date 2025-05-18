package de.dafuqs.spectrum.inventories;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class QuickNavigationGridScreenHandler extends AbstractContainerMenu {
	
	public QuickNavigationGridScreenHandler(@Nullable MenuType<?> type, int syncId) {
		super(type, syncId);
	}
	
	@Override
	public ItemStack quickMoveStack(Player player, int index) {
		return ItemStack.EMPTY;
	}
	
}
