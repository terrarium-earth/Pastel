package earth.terrarium.pastel.mixin;

import earth.terrarium.pastel.api.entity.NonLivingAttackable;
import earth.terrarium.pastel.api.item.MergeableItem;
import earth.terrarium.pastel.api.item.SplittableItem;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {
	
	@Shadow public ServerPlayer player;
	
	@Inject(method = "handlePlayerAction", at = @At(value = "INVOKE", target = "net/minecraft/server/level/ServerPlayer.getItemInHand (Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;", ordinal = 0), cancellable = true)
	private void handleSwapInteractions(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
		
		var mainStack = player.getItemInHand(InteractionHand.MAIN_HAND);
		var offStack = player.getItemInHand(InteractionHand.OFF_HAND);
		var mainItem = mainStack.getItem();
		var offItem = offStack.getItem();
		
		if (mainItem instanceof SplittableItem splittable && splittable.canSplit(player, InteractionHand.MAIN_HAND, mainStack)) {
			splitItem(mainStack, splittable);
			ci.cancel();
		} else if (offItem instanceof SplittableItem splittable && splittable.canSplit(player, InteractionHand.OFF_HAND, offStack)) {
			splitItem(offStack, splittable);
			ci.cancel();
		} else if (mainItem instanceof MergeableItem mergeable && offItem instanceof MergeableItem && mergeable.canMerge(player, mainStack, offStack)) {
			mergeItems(mainStack, offStack, mergeable);
			ci.cancel();
		}
	}

	@Unique
	private void splitItem(ItemStack stack, SplittableItem splittable) {
		var split = splittable.getSplitResult(player, stack);
		player.setItemInHand(InteractionHand.MAIN_HAND, split);
		player.setItemInHand(InteractionHand.OFF_HAND, split.copy());
		player.stopUsingItem();
		splittable.playSound(player);
	}

	@Unique
	private void mergeItems(ItemStack firstHalf, ItemStack secondHalf, MergeableItem mergeable) {
		player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
		player.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
		player.setItemInHand(InteractionHand.MAIN_HAND, mergeable.getMergeResult(player, firstHalf, secondHalf));
		player.stopUsingItem();
		mergeable.playSound(player);
	}
	
}
