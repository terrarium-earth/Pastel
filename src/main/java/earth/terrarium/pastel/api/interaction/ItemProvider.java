package earth.terrarium.pastel.api.interaction;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public interface ItemProvider {
	
	int getItemCount(Player player, ItemStack stack, Item requestedItem);
	
	int provideItems(Player player, ItemStack stack, Item requestedItem, int amount);
	
}
