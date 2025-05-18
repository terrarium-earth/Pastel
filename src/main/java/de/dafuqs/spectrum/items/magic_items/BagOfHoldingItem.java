package de.dafuqs.spectrum.items.magic_items;

import de.dafuqs.spectrum.inventories.BagOfHoldingScreenHandler;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BagOfHoldingItem extends Item {
	
	public BagOfHoldingItem(Properties settings) {
		super(settings);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		ItemStack itemStack = user.getItemInHand(hand);
		
		PlayerEnderChestContainer enderChestInventory = user.getEnderChestInventory();
		if (enderChestInventory != null) {
			user.openMenu(new SimpleMenuProvider((syncId, inventory, playerx) -> new BagOfHoldingScreenHandler(syncId, playerx.getInventory(), playerx.getEnderChestInventory()), Component.translatable("container.enderchest")));
			
			return InteractionResultHolder.consume(itemStack);
		} else {
			return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide);
		}
	}
	
	
}
