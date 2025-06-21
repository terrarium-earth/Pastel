package earth.terrarium.pastel.api.interaction;

import earth.terrarium.pastel.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.*;

public interface ItemProvider {
	ItemCapability<ItemProvider, Void> CAPABILITY = ItemCapability.createVoid(PastelCommon.ofPastel("item_provider"), ItemProvider.class);
	
	int getItemCount(Player player, ItemStack stack, Item requestedItem);
	
	int provideItems(Player player, ItemStack stack, Item requestedItem, int amount);
}
