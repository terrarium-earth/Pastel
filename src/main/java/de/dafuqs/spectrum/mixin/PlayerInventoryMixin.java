package de.dafuqs.spectrum.mixin;

import de.dafuqs.spectrum.api.item.InventoryInsertionAcceptor;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class PlayerInventoryMixin {
	
	@Inject(at = @At("HEAD"), method = "add(Lnet/minecraft/world/item/ItemStack;)Z", cancellable = true)
	private void addStack(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
		Inventory playerInventory = (Inventory) (Object) this;
		
		for (int i = 0; i < playerInventory.getContainerSize(); i++) {
			ItemStack inventoryStack = playerInventory.getItem(i);
			if (inventoryStack.getItem() instanceof InventoryInsertionAcceptor inventoryInsertionAcceptor) {
				if (inventoryInsertionAcceptor.acceptsItemStack(inventoryStack, stack)) {
					int remainingCount = inventoryInsertionAcceptor.acceptItemStack(inventoryStack, stack, playerInventory.player);
					stack.setCount(remainingCount);
					if (remainingCount == 0) {
						cir.cancel();
						break;
					}
				}
			}
		}
	}
	
	@Inject(at = @At("HEAD"), method = "placeItemBackInInventory(Lnet/minecraft/world/item/ItemStack;Z)V", cancellable = true)
	private void offer(ItemStack stack, boolean notifiesClient, CallbackInfo ci) {
		Inventory playerInventory = (Inventory) (Object) this;
		
		for (int i = 0; i < playerInventory.getContainerSize(); i++) {
			ItemStack inventoryStack = playerInventory.getItem(i);
			if (inventoryStack.getItem() instanceof InventoryInsertionAcceptor inventoryInsertionAcceptor) {
				if (inventoryInsertionAcceptor.acceptsItemStack(inventoryStack, stack)) {
					int remainingCount = inventoryInsertionAcceptor.acceptItemStack(inventoryStack, stack, playerInventory.player);
					stack.setCount(remainingCount);
					if (remainingCount == 0) {
						ci.cancel();
						break;
					}
				}
			}
		}
	}
	
}
